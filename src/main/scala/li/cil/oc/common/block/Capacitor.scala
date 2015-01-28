package li.cil.oc.common.block

import java.util.Random

import li.cil.oc.common.tileentity
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.util.BlockPos
import net.minecraft.world.World

class Capacitor extends SimpleBlock {
  setLightLevel(0.34f)
  setTickRandomly(true)

  // ----------------------------------------------------------------------- //

  override def hasTileEntity(state: IBlockState) = true

  override def createNewTileEntity(world: World, metadata: Int) = new tileentity.Capacitor()

  // ----------------------------------------------------------------------- //

  override def hasComparatorInputOverride = true

  override def getComparatorInputOverride(world: World, pos: BlockPos): Int =
    world.getTileEntity(pos) match {
      case capacitor: tileentity.Capacitor if !world.isRemote =>
        math.round(15 * capacitor.node.localBuffer / capacitor.node.localBufferSize).toInt
      case _ => 0
    }

  override def updateTick(world: World, pos: BlockPos, state: IBlockState, rand: Random): Unit = {
    world.notifyNeighborsOfStateChange(pos, this)
  }

  override def tickRate(world: World) = 1

  override def onNeighborBlockChange(world: World, pos: BlockPos, state: IBlockState, neighborBlock: Block) =
    world.getTileEntity(pos) match {
      case capacitor: tileentity.Capacitor => capacitor.recomputeCapacity()
      case _ =>
    }
}
