package li.cil.oc.client.renderer.block

import java.util.Collections
import javax.vecmath.Vector3f

import li.cil.oc.client.Textures
import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.BakedQuad
import net.minecraft.client.renderer.block.model.ItemCameraTransforms
import net.minecraft.client.renderer.block.model.ItemTransformVec3f
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.item.EnumDyeColor
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.Vec3
import net.minecraftforge.client.model.ISmartBlockModel
import net.minecraftforge.client.model.ISmartItemModel

trait SmartBlockModelBase extends ISmartBlockModel with ISmartItemModel {
  override def handleBlockState(state: IBlockState) = missingModel

  override def handleItemState(stack: ItemStack) = missingModel

  override def getFaceQuads(side: EnumFacing): java.util.List[_] = Collections.emptyList()

  override def getGeneralQuads: java.util.List[_] = Collections.emptyList()

  override def isAmbientOcclusion = true

  override def isGui3d = true

  override def isBuiltInRenderer = false

  // Note: we don't care about the actual texture here, we just need the block
  // texture atlas. So any of our textures we know is loaded into it will do.
  override def getTexture = Textures.getSprite(Textures.Block.CableCap)

  override def getItemCameraTransforms = DefaultBlockCameraTransforms

  protected final val DefaultBlockCameraTransforms = {
    // Value from common block item model.
    val rotation = new Vector3f(10, -45, 170)
    val translation = new Vector3f(0, 1.5f, -2.75f)
    val scale = new Vector3f(0.375f, 0.375f, 0.375f)
    // See ItemTransformVec3f.Deserializer.deserialize0
    translation.scale(0.0625f)
    new ItemCameraTransforms(
      new ItemTransformVec3f(rotation, translation, scale),
      ItemTransformVec3f.DEFAULT,
      ItemTransformVec3f.DEFAULT,
      ItemTransformVec3f.DEFAULT)
  }

  protected def missingModel = Minecraft.getMinecraft.getRenderItem.getItemModelMesher.getModelManager.getMissingModel

  // Standard faces for a unit cube.
  protected final val UnitCube = Array(
    Array(new Vec3(0, 0, 1), new Vec3(0, 0, 0), new Vec3(1, 0, 0), new Vec3(1, 0, 1)),
    Array(new Vec3(0, 1, 0), new Vec3(0, 1, 1), new Vec3(1, 1, 1), new Vec3(1, 1, 0)),
    Array(new Vec3(1, 1, 0), new Vec3(1, 0, 0), new Vec3(0, 0, 0), new Vec3(0, 1, 0)),
    Array(new Vec3(0, 1, 1), new Vec3(0, 0, 1), new Vec3(1, 0, 1), new Vec3(1, 1, 1)),
    Array(new Vec3(0, 1, 0), new Vec3(0, 0, 0), new Vec3(0, 0, 1), new Vec3(0, 1, 1)),
    Array(new Vec3(1, 1, 1), new Vec3(1, 0, 1), new Vec3(1, 0, 0), new Vec3(1, 1, 0))
  )

  // Planes perpendicular to facings. Negative values mean we mirror along that,
  // axis which is done to mirror back faces and the y axis (because up is
  // positive but for our texture coordinates down is positive).
  protected final val Planes = Array(
    (new Vec3(1, 0, 0), new Vec3(0, 0, -1)),
    (new Vec3(1, 0, 0), new Vec3(0, 0, 1)),
    (new Vec3(-1, 0, 0), new Vec3(0, -1, 0)),
    (new Vec3(1, 0, 0), new Vec3(0, -1, 0)),
    (new Vec3(0, 0, 1), new Vec3(0, -1, 0)),
    (new Vec3(0, 0, -1), new Vec3(0, -1, 0))
  )

  protected final val NoTint = -1

  /**
   * Generates a list of arrays, each containing the four vertices making up a
   * face of the box with the specified size.
   */
  protected def makeBox(from: Vec3, to: Vec3) = {
    val minX = math.min(from.xCoord, to.xCoord)
    val minY = math.min(from.yCoord, to.yCoord)
    val minZ = math.min(from.zCoord, to.zCoord)
    val maxX = math.max(from.xCoord, to.xCoord)
    val maxY = math.max(from.yCoord, to.yCoord)
    val maxZ = math.max(from.zCoord, to.zCoord)
    UnitCube.map(face => face.map(vertex => new Vec3(
      math.max(minX, math.min(maxX, vertex.xCoord)),
      math.max(minY, math.min(maxY, vertex.yCoord)),
      math.max(minZ, math.min(maxZ, vertex.zCoord)))))
  }

  /**
   * Create the BakedQuads for a set of quads defined by the specified vertices.
   * <p/>
   * Usually used to generate the quads for a cube previously generated using makeBox().
   */
  protected def bakeQuads(box: Array[Array[Vec3]], texture: TextureAtlasSprite, color: Option[EnumDyeColor]) = {
    val tintIndex = color.fold(NoTint)(_.getDyeDamage)
    EnumFacing.values.map(side => {
      val vertices = box(side.getIndex)
      val data = quadData(vertices, side, texture, 0)
      new BakedQuad(data, tintIndex, side)
    })
  }

  /**
   * Create a single BakedQuad of a unit cube's specified side.
   */
  protected def bakeQuad(side: EnumFacing, texture: TextureAtlasSprite, color: Option[EnumDyeColor], rotation: Int) = {
    val tintIndex = color.fold(NoTint)(_.getDyeDamage)
    val vertices = UnitCube(side.getIndex)
    val data = quadData(vertices, side, texture, rotation)
    new BakedQuad(data, tintIndex, side)
  }

  // Generate raw data used for a BakedQuad based on the specified facing, vertices, texture and rotation.
  // The UV coordinates are generated from the positions of the vertices, i.e. they are simply cube-
  // mapped. This is good enough for us.
  protected def quadData(vertices: Array[Vec3], facing: EnumFacing, texture: TextureAtlasSprite, rotation: Int): Array[Int] = {
    val (uAxis, vAxis) = Planes(facing.getIndex)
    val rot = (rotation + 4) % 4
    vertices.map(vertex => {
      var u = vertex.dotProduct(uAxis)
      var v = vertex.dotProduct(vAxis)
      if (uAxis.xCoord + uAxis.yCoord + uAxis.zCoord < 0) u = 1 + u
      if (vAxis.xCoord + vAxis.yCoord + vAxis.zCoord < 0) v = 1 + v
      for (i <- 0 until rot) {
        // (u, v) = (v, -u)
        val tmp = u
        u = v
        v = (-(tmp - 0.5)) + 0.5
      }
      rawData(vertex.xCoord, vertex.yCoord, vertex.zCoord, facing, texture, texture.getInterpolatedU(u * 16), texture.getInterpolatedV(v * 16))
    }).flatten
  }

  // See FaceBakery#storeVertexData.
  protected def rawData(x: Double, y: Double, z: Double, face: EnumFacing, texture: TextureAtlasSprite, u: Float, v: Float) = {
    Array(
      java.lang.Float.floatToRawIntBits(x.toFloat),
      java.lang.Float.floatToRawIntBits(y.toFloat),
      java.lang.Float.floatToRawIntBits(z.toFloat),
      getFaceShadeColor(face),
      java.lang.Float.floatToRawIntBits(u),
      java.lang.Float.floatToRawIntBits(v),
      0
    )
  }

  // See FaceBakery.
  protected def getFaceShadeColor(face: EnumFacing): Int = {
    val brightness = getFaceBrightness(face)
    val color = (brightness * 255).toInt max 0 min 255
    0xFF000000 | color << 16 | color << 8 | color
  }

  protected def getFaceBrightness(face: EnumFacing): Float = {
    face match {
      case EnumFacing.DOWN => 0.5f
      case EnumFacing.UP => 1.0f
      case EnumFacing.NORTH | EnumFacing.SOUTH => 0.8f
      case EnumFacing.WEST | EnumFacing.EAST => 0.6f
    }
  }
}
