package li.cil.oc.common.block

import java.util

import li.cil.oc.OpenComputers
import li.cil.oc.Settings
import li.cil.oc.common.GuiType
import li.cil.oc.common.tileentity
import li.cil.oc.integration.util.Wrench
import li.cil.oc.util.Color
import li.cil.oc.util.Tooltip
import net.minecraft.block.properties.IProperty
import net.minecraft.block.properties.PropertyBool
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockPos
import net.minecraft.util.EnumFacing
import net.minecraft.world.World
import net.minecraftforge.common.property.IUnlistedProperty
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

import scala.collection.mutable

object Case {
  final val Running = PropertyBool.create("running")
}

class Case(val tier: Int) extends RedstoneAware with traits.PowerAcceptor with traits.Rotatable {
  def getRunning(state: IBlockState) =
    if (state.getBlock == this)
      state.getValue(Case.Running).asInstanceOf[Boolean]
    else
      false

  def withRunning(state: IBlockState, running: Boolean) = state.
    withProperty(Case.Running, boolean2Boolean(running))

  override protected def setDefaultExtendedState(state: IBlockState) = setDefaultState(state)

  override protected def createProperties(listed: mutable.ArrayBuffer[IProperty], unlisted: mutable.ArrayBuffer[IUnlistedProperty[_]]): Unit = {
    super.createProperties(listed, unlisted)
    listed += Case.Running
  }

  // ----------------------------------------------------------------------- //

  @SideOnly(Side.CLIENT)
  override def getRenderColor(state: IBlockState) = Color.rgbValues(Color.byTier(tier))

  // ----------------------------------------------------------------------- //

  override def rarity = Array(EnumRarity.COMMON, EnumRarity.UNCOMMON, EnumRarity.RARE, EnumRarity.EPIC).apply(tier)

  override protected def tooltipBody(metadata: Int, stack: ItemStack, player: EntityPlayer, tooltip: util.List[String], advanced: Boolean) {
    tooltip.addAll(Tooltip.get(getClass.getSimpleName, slots))
  }

  private def slots = tier match {
    case 0 => "2/1/1"
    case 1 => "2/2/2"
    case 2 | 3 => "3/2/3"
    case _ => "0/0/0"
  }

  // ----------------------------------------------------------------------- //

  override def energyThroughput = Settings.get.caseRate(tier)

  override def createNewTileEntity(world: World, metadata: Int) = new tileentity.Case(tier)

  // ----------------------------------------------------------------------- //

  override def localOnBlockActivated(world: World, pos: BlockPos, player: EntityPlayer, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float) = {
    if (!player.isSneaking && !Wrench.holdsApplicableWrench(player, pos)) {
      if (!world.isRemote) {
        player.openGui(OpenComputers, GuiType.Case.id, world, pos.getX, pos.getY, pos.getZ)
      }
      true
    }
    else if (player.getCurrentEquippedItem == null) {
      if (!world.isRemote) {
        world.getTileEntity(pos) match {
          case computer: tileentity.Case if !computer.machine.isRunning => computer.machine.start()
          case _ =>
        }
      }
      true
    }
    else false
  }

  override def removedByPlayer(world: World, pos: BlockPos, player: EntityPlayer, willHarvest: Boolean) =
    world.getTileEntity(pos) match {
      case c: tileentity.Case => c.canInteract(player.getName) && super.removedByPlayer(world, pos, player, willHarvest)
      case _ => super.removedByPlayer(world, pos, player, willHarvest)
    }
}
