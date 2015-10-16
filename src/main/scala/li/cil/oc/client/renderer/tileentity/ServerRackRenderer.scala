package li.cil.oc.client.renderer.tileentity

import li.cil.oc.client.Textures
import li.cil.oc.common.tileentity.ServerRack
import li.cil.oc.util.RenderState
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

object ServerRackRenderer extends TileEntitySpecialRenderer {
  private final val v1 = 2 / 16f
  private final val fs = 3 / 16f

  override def renderTileEntityAt(tileEntity: TileEntity, x: Double, y: Double, z: Double, f: Float, damage: Int) {
    RenderState.checkError(getClass.getName + ".renderTileEntityAt: entering (aka: wasntme)")

    val rack = tileEntity.asInstanceOf[ServerRack]
    RenderState.pushAttrib()

    RenderState.disableEntityLighting()
    RenderState.makeItBlend()
    RenderState.color(1, 1, 1, 1)

    RenderState.pushMatrix()

    GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5)

    rack.yaw match {
      case EnumFacing.WEST => GL11.glRotatef(-90, 0, 1, 0)
      case EnumFacing.NORTH => GL11.glRotatef(180, 0, 1, 0)
      case EnumFacing.EAST => GL11.glRotatef(90, 0, 1, 0)
      case _ => // No yaw.
    }

    GL11.glTranslated(-0.5, 0.5, 0.505 - 0.5f / 16f)
    GL11.glScalef(1, -1, 1)

    if (rack.anyRunning) {
      for (i <- 0 until rack.getSizeInventory if rack.isRunning(i)) {
        renderFrontOverlay(Textures.Block.RackFrontOn, i)
      }
      for (i <- 0 until rack.getSizeInventory if System.currentTimeMillis() - rack.lastAccess(i) < 400 && rack.world.rand.nextDouble() > 0.1) {
        renderFrontOverlay(Textures.Block.RackFrontActivity, i)
      }
    }
    if (rack.anyErrored) {
      for (i <- 0 until rack.getSizeInventory if rack.hasErrored(i) && RenderUtil.shouldShowErrorLight(rack.hashCode * (i + 1))) {
        renderFrontOverlay(Textures.Block.RackFrontError, i)
      }
    }

    RenderState.enableEntityLighting()

    RenderState.popMatrix()
    RenderState.popAttrib()

    RenderState.checkError(getClass.getName + ".renderTileEntityAt: leaving")
  }

  private def renderFrontOverlay(texture: ResourceLocation, i: Int): Unit = {
    val t = Tessellator.getInstance
    val r = t.getWorldRenderer

    Textures.Block.bind()
    r.startDrawingQuads()

    val l = v1 + i * fs
    val h = v1 + (i + 1) * fs

    val icon = Textures.getSprite(texture)
    r.addVertexWithUV(0, h, 0, icon.getMinU, icon.getInterpolatedV(h * 16))
    r.addVertexWithUV(1, h, 0, icon.getMaxU, icon.getInterpolatedV(h * 16))
    r.addVertexWithUV(1, l, 0, icon.getMaxU, icon.getInterpolatedV(l * 16))
    r.addVertexWithUV(0, l, 0, icon.getMinU, icon.getInterpolatedV(l * 16))

    t.draw()
  }
}