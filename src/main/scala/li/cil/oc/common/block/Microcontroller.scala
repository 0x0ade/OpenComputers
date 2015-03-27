package li.cil.oc.common.block

import java.util

import li.cil.oc.Settings
import li.cil.oc.api
import li.cil.oc.client.KeyBindings
import li.cil.oc.common.Tier
import li.cil.oc.common.item.data.MicrocontrollerData
import li.cil.oc.common.tileentity
import li.cil.oc.integration.util.NEI
import li.cil.oc.integration.util.Wrench
import li.cil.oc.util.InventoryUtils
import li.cil.oc.util.Rarity
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockPos
import net.minecraft.util.EnumFacing
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.World

import scala.reflect.ClassTag

class Microcontroller(protected implicit val tileTag: ClassTag[tileentity.Microcontroller]) extends RedstoneAware with traits.PowerAcceptor with traits.Rotatable with traits.StateAware with traits.CustomDrops[tileentity.Microcontroller] {
  setCreativeTab(null)
  NEI.hide(this)

  override protected def setDefaultExtendedState(state: IBlockState) = setDefaultState(state)

  // ----------------------------------------------------------------------- //

  override def getPickBlock(target: MovingObjectPosition, world: World, pos: BlockPos) =
    world.getTileEntity(pos) match {
      case mcu: tileentity.Microcontroller => mcu.info.copyItemStack()
      case _ => null
    }

  // ----------------------------------------------------------------------- //

  override protected def tooltipTail(metadata: Int, stack: ItemStack, player: EntityPlayer, tooltip: util.List[String], advanced: Boolean) {
    super.tooltipTail(metadata, stack, player, tooltip, advanced)
    if (KeyBindings.showExtendedTooltips) {
      val info = new MicrocontrollerData(stack)
      for (component <- info.components if component != null) {
        tooltip.add("- " + component.getDisplayName)
      }
    }
  }

  override def rarity(stack: ItemStack) = {
    val data = new MicrocontrollerData(stack)
    Rarity.byTier(data.tier)
  }

  // ----------------------------------------------------------------------- //

  override def energyThroughput = Settings.get.caseRate(Tier.One)

  override def createNewTileEntity(world: World, metadata: Int) = new tileentity.Microcontroller()

  // ----------------------------------------------------------------------- //

  override def localOnBlockActivated(world: World, pos: BlockPos, player: EntityPlayer, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float) = {
    if (!Wrench.holdsApplicableWrench(player, pos)) {
      if (!player.isSneaking) {
        if (!world.isRemote) {
          world.getTileEntity(pos) match {
            case mcu: tileentity.Microcontroller =>
              if (mcu.machine.isRunning) mcu.machine.stop()
              else mcu.machine.start()
            case _ =>
          }
        }
        true
      }
      else if (api.Items.get(player.getHeldItem) == api.Items.get("eeprom")) {
        if (!world.isRemote) {
          world.getTileEntity(pos) match {
            case mcu: tileentity.Microcontroller =>
              val newEeprom = player.inventory.decrStackSize(player.inventory.currentItem, 1)
              mcu.changeEEPROM(newEeprom) match {
                case Some(oldEeprom) => InventoryUtils.addToPlayerInventory(oldEeprom, player)
                case _ =>
              }
          }
        }
        true
      }
      else false
    }
    else false
  }

  override protected def doCustomInit(tileEntity: tileentity.Microcontroller, player: EntityLivingBase, stack: ItemStack): Unit = {
    super.doCustomInit(tileEntity, player, stack)
    if (!tileEntity.world.isRemote) {
      tileEntity.info.load(stack)
      tileEntity.snooperNode.changeBuffer(tileEntity.info.storedEnergy - tileEntity.snooperNode.localBuffer)
    }
  }

  override protected def doCustomDrops(tileEntity: tileentity.Microcontroller, player: EntityPlayer, willHarvest: Boolean): Unit = {
    super.doCustomDrops(tileEntity, player, willHarvest)
    tileEntity.saveComponents()
    tileEntity.info.storedEnergy = tileEntity.snooperNode.localBuffer.toInt
    Block.spawnAsEntity(tileEntity.world, tileEntity.getPos, tileEntity.info.createItemStack())
  }
}
