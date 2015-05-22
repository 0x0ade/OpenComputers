package li.cil.oc.common.item

import java.util

import li.cil.oc.OpenComputers
import li.cil.oc.api
import li.cil.oc.common.item.traits.Delegate
import li.cil.oc.util.BlockPosition
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.EnumFacing
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class Manual(val parent: Delegator) extends Delegate {
  @SideOnly(Side.CLIENT)
  override def tooltipLines(stack: ItemStack, player: EntityPlayer, tooltip: util.List[String], advanced: Boolean): Unit = {
    tooltip.add(EnumChatFormatting.DARK_GRAY.toString + "v" + OpenComputers.Version)
    super.tooltipLines(stack, player, tooltip, advanced)
  }

  override def onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer): ItemStack = {
    if (world.isRemote) {
      if (player.isSneaking) {
        api.Manual.reset()
      }
      api.Manual.openFor(player)
    }
    super.onItemRightClick(stack, world, player)
  }

  override def onItemUse(stack: ItemStack, player: EntityPlayer, position: BlockPosition, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    val world = player.getEntityWorld
    api.Manual.pathFor(world, position.toBlockPos) match {
      case path: String =>
        if (world.isRemote) {
          api.Manual.openFor(player)
          api.Manual.reset()
          api.Manual.navigate(path)
        }
        true
      case _ => super.onItemUse(stack, player, position, side, hitX, hitY, hitZ)
    }
  }
}
