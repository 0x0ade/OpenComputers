package li.cil.oc.server.component

import cpw.mods.fml.common.eventhandler.Event
import li.cil.oc.Settings
import li.cil.oc.api.driver.EnvironmentHost
import li.cil.oc.api.internal
import li.cil.oc.api.prefab
import li.cil.oc.util.BlockPosition
import li.cil.oc.util.ExtendedWorld._
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntitySign
import net.minecraft.world.WorldServer
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.util.FakePlayerFactory
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.event.world.BlockEvent

abstract class UpgradeSign extends prefab.ManagedEnvironment {
  def host: EnvironmentHost

  protected def getValue(tileEntity: Option[TileEntitySign]): Array[AnyRef] = {
    tileEntity match {
      case Some(sign) => result(sign.signText.mkString("\n"))
      case _ => result(Unit, "no sign")
    }
  }

  protected def setValue(tileEntity: Option[TileEntitySign], text: String): Array[AnyRef] = {
    tileEntity match {
      case Some(sign) =>
        val player = host match {
          case robot: internal.Robot => robot.player
          case _ => FakePlayerFactory.get(host.world.asInstanceOf[WorldServer], Settings.get.fakePlayerProfile)
        }
        if (!canChangeSign(player, sign)) {
          return result(Unit, "not allowed")
        }

        text.lines.padTo(4, "").map(line => if (line.length > 15) line.substring(0, 15) else line).copyToArray(sign.signText)
        host.world.markBlockForUpdate(sign.xCoord, sign.yCoord, sign.zCoord)
        result(sign.signText.mkString("\n"))
      case _ => result(Unit, "no sign")
    }
  }

  protected def findSign(side: ForgeDirection) = {
    val hostPos = BlockPosition(host)
    host.world.getTileEntity(hostPos) match {
      case sign: TileEntitySign => Option(sign)
      case _ => host.world.getTileEntity(hostPos.offset(side)) match {
        case sign: TileEntitySign => Option(sign)
        case _ => None
      }
    }
  }

  private def canChangeSign(player: EntityPlayer, tileEntity: TileEntitySign): Boolean = {
    if (!host.world.canMineBlock(player, tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord)) {
      return false
    }
    val event = new BlockEvent.BreakEvent(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, host.world, tileEntity.getBlockType, tileEntity.getBlockMetadata, player)
    MinecraftForge.EVENT_BUS.post(event)
    !(event.isCanceled || event.getResult == Event.Result.DENY)
  }
}
