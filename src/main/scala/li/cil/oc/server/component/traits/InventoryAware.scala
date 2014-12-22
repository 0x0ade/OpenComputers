package li.cil.oc.server.component.traits

import li.cil.oc.api.machine.Arguments
import li.cil.oc.util.ExtendedArguments._
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack

trait InventoryAware {
  def inventory: IInventory

  def selectedSlot: Int

  def selectedSlot_=(value: Int): Unit

  def insertionSlots = (selectedSlot until inventory.getSizeInventory) ++ (0 until selectedSlot)

  // ----------------------------------------------------------------------- //

  protected def optSlot(args: Arguments, n: Int) =
    if (args.count > 0 && args.checkAny(0) != null) args.checkSlot(inventory, 0)
    else selectedSlot

  protected def stackInSlot(slot: Int) = Option(inventory.getStackInSlot(slot))

  protected def haveSameItemType(stackA: ItemStack, stackB: ItemStack) =
    stackA.getItem == stackB.getItem &&
      (!stackA.getHasSubtypes || stackA.getItemDamage == stackB.getItemDamage)
}
