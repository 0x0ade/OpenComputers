package li.cil.oc.common.block

import net.minecraft.world.IBlockAccess
import net.minecraftforge.common.util.ForgeDirection

trait SpecialBlock extends SimpleBlock {
  override def isNormalCube(world: IBlockAccess, x: Int, y: Int, z: Int) = false

  override def isOpaqueCube = false

  override def renderAsNormalBlock = false

  final override def isBlockSolid(world: IBlockAccess, x: Int, y: Int, z: Int, side: Int) = isBlockSolid(world, x, y, z, toLocal(world, x, y, z, ForgeDirection.getOrientation(side)))

  def isBlockSolid(world: IBlockAccess, x: Int, y: Int, z: Int, side: ForgeDirection) = super.isBlockSolid(world, x, y, z, side.ordinal())
}
