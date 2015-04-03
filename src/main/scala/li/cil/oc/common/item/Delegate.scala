package li.cil.oc.common.item

import java.util

import li.cil.oc.Localization
import li.cil.oc.Settings
import li.cil.oc.api
import li.cil.oc.client.KeyBindings
import li.cil.oc.util.BlockPosition
import li.cil.oc.util.ItemCosts
import li.cil.oc.util.Rarity
import li.cil.oc.util.Tooltip
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumAction
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

trait Delegate {
  def parent: Delegator

  def unlocalizedName = getClass.getSimpleName

  protected def tooltipName = Option(unlocalizedName)

  protected def tooltipData = Seq.empty[Any]

  var showInItemList = true

  val itemId = parent.add(this)

  def maxStackSize = 64

  def createItemStack(amount: Int = 1) = new ItemStack(parent, amount, itemId)

  // ----------------------------------------------------------------------- //

  def onItemUseFirst(stack: ItemStack, player: EntityPlayer, position: BlockPosition, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean = false

  def onItemUse(stack: ItemStack, player: EntityPlayer, position: BlockPosition, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean = false

  def onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer): ItemStack = {
    if (player.isSneaking) {
      if (stack.hasTagCompound && stack.getTagCompound.hasKey(Settings.namespace + "data")) {
        stack.setTagCompound(null)
        player.swingItem()
      }
    }
    stack
  }

  def getItemUseAction(stack: ItemStack): EnumAction = EnumAction.none

  def getMaxItemUseDuration(stack: ItemStack) = 0

  def onEaten(stack: ItemStack, world: World, player: EntityPlayer): ItemStack = stack

  def onPlayerStoppedUsing(stack: ItemStack, player: EntityPlayer, duration: Int) {}

  def update(stack: ItemStack, world: World, player: Entity, slot: Int, selected: Boolean) {}

  // ----------------------------------------------------------------------- //

  def rarity(stack: ItemStack) = Rarity.byTier(tierFromDriver(stack))

  protected def tierFromDriver(stack: ItemStack) =
    api.Driver.driverFor(stack) match {
      case driver: api.driver.Item => driver.tier(stack)
      case _ => 0
    }

  def color(stack: ItemStack, pass: Int) = 0xFFFFFF

  def displayName(stack: ItemStack): Option[String] = None

  @SideOnly(Side.CLIENT)
  def tooltipLines(stack: ItemStack, player: EntityPlayer, tooltip: java.util.List[String], advanced: Boolean) {
    if (tooltipName.isDefined) {
      tooltip.addAll(Tooltip.get(tooltipName.get, tooltipData: _*))
      tooltipExtended(stack, tooltip)
    }
    tooltipCosts(stack, tooltip)
  }

  // For stuff that goes to the normal 'extended' tooltip, before the costs.
  protected def tooltipExtended(stack: ItemStack, tooltip: java.util.List[String]) {}

  protected def tooltipCosts(stack: ItemStack, tooltip: java.util.List[String]) {
    if (ItemCosts.hasCosts(stack)) {
      if (KeyBindings.showMaterialCosts) {
        ItemCosts.addTooltip(stack, tooltip.asInstanceOf[util.List[String]])
      }
      else {
        tooltip.add(Localization.localizeImmediately(
          Settings.namespace + "tooltip.MaterialCosts",
          KeyBindings.getKeybindName(KeyBindings.materialCosts)))
      }
    }
    if (stack.hasTagCompound && stack.getTagCompound.hasKey(Settings.namespace + "data")) {
      val data = stack.getTagCompound.getCompoundTag(Settings.namespace + "data")
      if (data.hasKey("node") && data.getCompoundTag("node").hasKey("address")) {
        tooltip.add("§8" + data.getCompoundTag("node").getString("address").substring(0, 13) + "...§7")
      }
    }
  }

  def showDurabilityBar(stack: ItemStack) = false

  def durability(stack: ItemStack) = 0.0

  def getModel(stack: ItemStack, player: EntityPlayer, useRemaining: Int): ModelResourceLocation = null
}
