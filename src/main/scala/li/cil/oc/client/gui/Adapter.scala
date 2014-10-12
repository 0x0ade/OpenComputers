package li.cil.oc.client.gui

import li.cil.oc.Localization
import li.cil.oc.common.container
import li.cil.oc.common.tileentity
import net.minecraft.entity.player.InventoryPlayer

class Adapter(playerInventory: InventoryPlayer, val adapter: tileentity.Adapter) extends DynamicGuiContainer(new container.Adapter(playerInventory, adapter)) {
  override def drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int) = {
    super.drawGuiContainerForegroundLayer(mouseX, mouseY)
    fontRendererObj.drawString(
      Localization.localizeImmediately(adapter.getInventoryName),
      8, 6, 0x404040)
  }
}
