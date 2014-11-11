package li.cil.oc.integration.util

import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import lordfokas.stargatetech2.api.bus.BusEvent.AddToNetwork
import lordfokas.stargatetech2.api.bus.BusEvent.RemoveFromNetwork

object StargateTech2 {
  def addDevice(world: World, x: Int, y: Int, z: Int) = MinecraftForge.EVENT_BUS.post(new AddToNetwork(world, x, y, z))

  def removeDevice(world: World, x: Int, y: Int, z: Int) = MinecraftForge.EVENT_BUS.post(new RemoveFromNetwork(world, x, y, z))
}