package li.cil.oc.client

import java.net.MalformedURLException
import java.net.URL
import java.net.URLConnection
import java.net.URLStreamHandler
import java.util.Timer
import java.util.TimerTask
import java.util.UUID

import com.google.common.base.Charsets
import li.cil.oc.OpenComputers
import li.cil.oc.Settings
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.SoundCategory
import net.minecraft.client.audio.SoundManager
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.event.sound.SoundLoadEvent
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.client.FMLClientHandler
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent
import net.minecraftforge.fml.relauncher.ReflectionHelper
import paulscode.sound.SoundSystem
import paulscode.sound.SoundSystemConfig

import scala.collection.mutable
import scala.io.Source

object Sound {
  private val sources = mutable.Map.empty[TileEntity, PseudoLoopingStream]

  private val commandQueue = mutable.PriorityQueue.empty[Command]

  private var lastVolume = FMLClientHandler.instance.getClient.gameSettings.getSoundLevel(SoundCategory.BLOCKS)

  private val updateTimer = new Timer("OpenComputers-SoundUpdater", true)
  if (Settings.get.soundVolume > 0) {
    updateTimer.scheduleAtFixedRate(new TimerTask {
      override def run() {
        updateVolume()
        processQueue()
      }
    }, 500, 50)
  }

  // Set in init event.
  var manager: SoundManager = _

  def soundSystem: SoundSystem = ReflectionHelper.getPrivateValue(classOf[SoundManager], manager, "sndSystem", "field_148620_e", "e")

  private def updateVolume() {
    val volume = FMLClientHandler.instance.getClient.gameSettings.getSoundLevel(SoundCategory.BLOCKS)
    if (volume != lastVolume) {
      lastVolume = volume
      sources.synchronized {
        for (sound <- sources.values) {
          sound.updateVolume()
        }
      }
    }
  }

  private def processQueue() {
    if (commandQueue.nonEmpty) {
      commandQueue.synchronized {
        while (commandQueue.nonEmpty && commandQueue.head.when < System.currentTimeMillis()) {
          try commandQueue.dequeue()() catch {
            case t: Throwable => OpenComputers.log.warn("Error processing sound command.", t)
          }
        }
      }
    }
  }

  def startLoop(tileEntity: TileEntity, name: String, volume: Float = 1f, delay: Long = 0) {
    if (Settings.get.soundVolume > 0) {
      commandQueue.synchronized {
        commandQueue += new StartCommand(System.currentTimeMillis() + delay, tileEntity, name, volume)
      }
    }
  }

  def stopLoop(tileEntity: TileEntity) {
    if (Settings.get.soundVolume > 0) {
      commandQueue.synchronized {
        commandQueue += new StopCommand(tileEntity)
      }
    }
  }

  def updatePosition(tileEntity: TileEntity) {
    if (Settings.get.soundVolume > 0) {
      commandQueue.synchronized {
        commandQueue += new UpdatePositionCommand(tileEntity)
      }
    }
  }

  @SubscribeEvent
  def onSoundLoad(event: SoundLoadEvent) {
    manager = event.manager
  }

  private var hasPreloaded = Settings.get.soundVolume <= 0

  @SubscribeEvent
  def onTick(e: ClientTickEvent) {
    if (!hasPreloaded && soundSystem != null) {
      hasPreloaded = true
      new Thread(new Runnable() {
        override def run(): Unit = {
          val preloadConfigLocation = new ResourceLocation(Settings.resourceDomain, "sounds/preload.cfg")
          val preloadConfigResource = Minecraft.getMinecraft.getResourceManager.getResource(preloadConfigLocation)
          for (location <- Source.fromInputStream(preloadConfigResource.getInputStream)(Charsets.UTF_8).getLines()) {
            val url = getClass.getClassLoader.getResource(location)
            if (url != null) try {
              val sourceName = "preload_" + location
              soundSystem.newSource(false, sourceName, url, location, true, 0, 0, 0, SoundSystemConfig.ATTENUATION_NONE, 16)
              soundSystem.activate(sourceName)
              soundSystem.removeSource(sourceName)
            } catch {
              case _: Throwable => // Meh.
            }
            else OpenComputers.log.warn(s"Couldn't preload sound $location!")
          }
        }
      })
    }
  }

  @SubscribeEvent
  def onWorldUnload(event: WorldEvent.Unload) {
    commandQueue.synchronized(commandQueue.clear())
    sources.synchronized(try sources.foreach(_._2.stop()) catch {
      case _: Throwable => // Ignore.
    })
  }

  private abstract class Command(val when: Long, val tileEntity: TileEntity) extends Ordered[Command] {
    def apply(): Unit

    override def compare(that: Command) = (that.when - when).toInt
  }

  private class StartCommand(when: Long, tileEntity: TileEntity, val name: String, val volume: Float) extends Command(when, tileEntity) {
    override def apply() {
      sources.synchronized {
        sources.getOrElseUpdate(tileEntity, new PseudoLoopingStream(tileEntity, volume)).play(name)
      }
    }
  }

  private class StopCommand(tileEntity: TileEntity) extends Command(0, tileEntity) {
    override def apply() {
      sources.synchronized {
        sources.remove(tileEntity) match {
          case Some(sound) => sound.stop()
          case _ =>
        }
      }
      commandQueue.synchronized {
        // Remove all other commands for this tile entity from the queue. This
        // is inefficient, but we generally don't expect the command queue to
        // be very long, so this should be OK.
        commandQueue ++= commandQueue.dequeueAll.filter(_.tileEntity != tileEntity)
      }
    }
  }

  private class UpdatePositionCommand(tileEntity: TileEntity) extends Command(0, tileEntity) {
    override def apply() {
      sources.synchronized {
        sources.get(tileEntity) match {
          case Some(sound) => sound.updatePosition()
          case _ =>
        }
      }
    }
  }

  private class PseudoLoopingStream(val tileEntity: TileEntity, val volume: Float, val source: String = UUID.randomUUID.toString) {
    var initialized = false

    def updateVolume() {
      soundSystem.setVolume(source, lastVolume * volume * Settings.get.soundVolume)
    }

    def updatePosition() {
      soundSystem.setPosition(source, tileEntity.getPos.getX, tileEntity.getPos.getY, tileEntity.getPos.getZ)
    }

    def play(name: String) {
      val resourceName = s"${Settings.resourceDomain}:$name"
      val sound = Minecraft.getMinecraft.getSoundHandler.getSound(new ResourceLocation(resourceName))
      val resource = sound.cloneEntry.getSoundPoolEntryLocation
      if (!initialized) {
        initialized = true
        soundSystem.newSource(false, source, toUrl(resource), resource.toString, true, tileEntity.getPos.getX, tileEntity.getPos.getY, tileEntity.getPos.getZ, SoundSystemConfig.ATTENUATION_LINEAR, 16)
        updateVolume()
        soundSystem.activate(source)
      }
      soundSystem.play(source)
    }

    def stop() {
      if (soundSystem != null) try {
        soundSystem.stop(source)
        soundSystem.removeSource(source)
      }
      catch {
        case _: Throwable =>
      }
    }
  }

  // This is copied from SoundManager.getURLForSoundResource, which is private.
  private def toUrl(resource: ResourceLocation): URL = {
    val name = s"mcsounddomain:${resource.getResourceDomain}:${resource.getResourcePath}"
    try {
      new URL(null, name, new URLStreamHandler {
        protected def openConnection(url: URL): URLConnection = new URLConnection(url) {
          def connect() {
          }

          override def getInputStream = Minecraft.getMinecraft.getResourceManager.getResource(resource).getInputStream
        }
      })
    }
    catch {
      case _: MalformedURLException => null
    }
  }
}
