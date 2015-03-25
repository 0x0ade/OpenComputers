package li.cil.oc.common.block

import java.util
import java.util.Random

import li.cil.oc.common.item.data.PrintData
import li.cil.oc.common.tileentity
import li.cil.oc.integration.util.NEI
import li.cil.oc.util.ExtendedAABB
import li.cil.oc.util.ExtendedAABB._
import li.cil.oc.util.InventoryUtils
import net.minecraft.block.properties.IProperty
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLiving.SpawnPlacementType
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.BlockPos
import net.minecraft.util.EnumFacing
import net.minecraft.util.MovingObjectPosition
import net.minecraft.util.Vec3
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import net.minecraftforge.common.property.IExtendedBlockState
import net.minecraftforge.common.property.IUnlistedProperty
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

import scala.collection.convert.WrapAsJava._
import scala.collection.mutable.ArrayBuffer
import scala.reflect.ClassTag

class Print(protected implicit val tileTag: ClassTag[tileentity.Print]) extends RedstoneAware with traits.CustomDrops[tileentity.Print] with traits.Extended {
  setLightOpacity(0)
  setHardness(1)
  setCreativeTab(null)
  NEI.hide(this)

  // ----------------------------------------------------------------------- //

  override protected def setDefaultExtendedState(state: IBlockState) = setDefaultState(state)

  override protected def addExtendedState(state: IBlockState, world: IBlockAccess, pos: BlockPos) =
    (state, world.getTileEntity(pos)) match {
      case (extendedState: IExtendedBlockState, print: tileentity.Print) =>
        super.addExtendedState(extendedState.withProperty(property.PropertyTile.Tile, print), world, pos)
      case _ => None
    }

  override protected def createProperties(listed: ArrayBuffer[IProperty], unlisted: ArrayBuffer[IUnlistedProperty[_]]) {
    super.createProperties(listed, unlisted)
    unlisted += property.PropertyTile.Tile
  }

  // ----------------------------------------------------------------------- //

  @SideOnly(Side.CLIENT) override
  def colorMultiplier(world: IBlockAccess, pos: BlockPos, tint: Int): Int = tint

  override protected def tooltipBody(metadata: Int, stack: ItemStack, player: EntityPlayer, tooltip: util.List[String], advanced: Boolean): Unit = {
    super.tooltipBody(metadata, stack, player, tooltip, advanced)
    val data = new PrintData(stack)
    data.tooltip.foreach(s => tooltip.addAll(s.lines.toIterable))
  }

  override def isOpaqueCube = false

  override def isFullCube = false

  override def shouldSideBeRendered(world: IBlockAccess, pos: BlockPos, side: EnumFacing) = true

  override def isBlockSolid(world: IBlockAccess, pos: BlockPos, side: EnumFacing) = isSideSolid(world, pos, side)

  override def isSideSolid(world: IBlockAccess, pos: BlockPos, side: EnumFacing): Boolean = {
    world.getTileEntity(pos) match {
      case print: tileentity.Print =>
        val shapes = if (print.state) print.data.stateOn else print.data.stateOff
        for (shape <- shapes) {
          val bounds = shape.bounds.rotateTowards(print.facing)
          val fullX = bounds.minX == 0 && bounds.maxX == 1
          val fullY = bounds.minY == 0 && bounds.maxY == 1
          val fullZ = bounds.minZ == 0 && bounds.maxZ == 1
          if (side match {
            case EnumFacing.DOWN => bounds.minY == 0 && fullX && fullZ
            case EnumFacing.UP => bounds.maxY == 1 && fullX && fullZ
            case EnumFacing.NORTH => bounds.minZ == 0 && fullX && fullY
            case EnumFacing.SOUTH => bounds.maxZ == 1 && fullX && fullY
            case EnumFacing.WEST => bounds.minX == 0 && fullY && fullZ
            case EnumFacing.EAST => bounds.maxX == 1 && fullY && fullZ
            case _ => false
          }) return true
        }
      case _ =>
    }
    false
  }

  override def getPickBlock(target: MovingObjectPosition, world: World, pos: BlockPos): ItemStack = {
    world.getTileEntity(pos) match {
      case print: tileentity.Print => print.data.createItemStack()
      case _ => null
    }
  }

  override def addCollisionBoxesToList(world: World, pos: BlockPos, state: IBlockState, mask: AxisAlignedBB, list: util.List[_], entity: Entity): Unit = {
    world.getTileEntity(pos) match {
      case print: tileentity.Print =>
        def add[T](list: util.List[T], value: Any) = list.add(value.asInstanceOf[T])
        val shapes = if (print.state) print.data.stateOn else print.data.stateOff
        for (shape <- shapes) {
          val bounds = shape.bounds.rotateTowards(print.facing).offset(pos)
          if (bounds.intersectsWith(mask)) {
            add(list, bounds)
          }
        }
      case _ => super.addCollisionBoxesToList(world, pos, state, mask, list, entity)
    }
  }

  override def collisionRayTrace(world: World, pos: BlockPos, start: Vec3, end: Vec3): MovingObjectPosition = {
    world.getTileEntity(pos) match {
      case print: tileentity.Print =>
        var closestDistance = Double.PositiveInfinity
        var closest: Option[MovingObjectPosition] = None
        for (shape <- if (print.state) print.data.stateOn else print.data.stateOff) {
          val bounds = shape.bounds.rotateTowards(print.facing).offset(pos)
          val hit = bounds.calculateIntercept(start, end)
          if (hit != null) {
            val distance = hit.hitVec.distanceTo(start)
            if (distance < closestDistance) {
              closestDistance = distance
              closest = Option(hit)
            }
          }
        }
        closest.map(hit => new MovingObjectPosition(hit.hitVec, hit.sideHit, pos)).orNull
      case _ => super.collisionRayTrace(world, pos, start, end)
    }
  }

  override def setBlockBoundsBasedOnState(world: IBlockAccess, pos: BlockPos): Unit = {
    world.getTileEntity(pos) match {
      case print: tileentity.Print => setBlockBounds(if (print.state) print.boundsOn else print.boundsOff)
      case _ => super.setBlockBoundsBasedOnState(world, pos)
    }
  }

  override def setBlockBoundsForItemRender(metadata: Int): Unit = {
    setBlockBounds(ExtendedAABB.unitBounds)
  }

  override def canCreatureSpawn(world: IBlockAccess, pos: BlockPos, `type`: SpawnPlacementType): Boolean = true

  override def tickRate(world: World) = 20

  override def updateTick(world: World, pos: BlockPos, state: IBlockState, rand: Random): Unit = {
    if (!world.isRemote) world.getTileEntity(pos) match {
      case print: tileentity.Print => if (print.state) print.toggleState()
      case _ =>
    }
  }

  override def isBeaconBase(world: IBlockAccess, pos: BlockPos, beacon: BlockPos): Boolean = {
    world.getTileEntity(pos) match {
      case print: tileentity.Print => print.data.isBeaconBase
      case _ => false
    }
  }

  // ----------------------------------------------------------------------- //

  override def hasTileEntity(state: IBlockState) = true

  override def createNewTileEntity(worldIn: World, meta: Int) = new tileentity.Print()

  // ----------------------------------------------------------------------- //

  override def onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    world.getTileEntity(pos) match {
      case print: tileentity.Print => print.activate()
      case _ => super.onBlockActivated(world, pos, state, player, side, hitX, hitY, hitZ)
    }
  }

  override protected def doCustomInit(tileEntity: tileentity.Print, player: EntityLivingBase, stack: ItemStack): Unit = {
    super.doCustomInit(tileEntity, player, stack)
    tileEntity.data.load(stack)
    tileEntity.updateBounds()
  }

  override protected def doCustomDrops(tileEntity: tileentity.Print, player: EntityPlayer, willHarvest: Boolean): Unit = {
    super.doCustomDrops(tileEntity, player, willHarvest)
    if (!player.capabilities.isCreativeMode) {
      InventoryUtils.spawnStackInWorld(tileEntity.position, tileEntity.data.createItemStack())
    }
  }
}
