package com.ferreusveritas.cathedral.renderers;

import org.lwjgl.opengl.GL11;

import com.ferreusveritas.cathedral.Cathedral;
import com.ferreusveritas.cathedral.features.gargoyle.BlockGargoyle;
import com.ferreusveritas.cathedral.features.gargoyle.EntityGargoyle;
import com.ferreusveritas.cathedral.model.QuadWavefrontObject;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class RendererGargoyle implements ISimpleBlockRenderingHandler {

	private QuadWavefrontObject gargoyleModel;
	public static int id;

	public RendererGargoyle() {
		id = RenderingRegistry.getNextAvailableRenderId();
		gargoyleModel = new QuadWavefrontObject(new ResourceLocation(Cathedral.MODID, "models/gargoyle.obj"));
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		if(block instanceof BlockGargoyle){
			BlockGargoyle gBlock = (BlockGargoyle)block;
			IIcon icon = gBlock.getIconForMaterial(metadata);

			gargoyleModel.setBakeLighting(false);
			gargoyleModel.setIcon(icon);
			GL11.glTranslatef(0.5f, 0.0f, 0.5f);
			gargoyleModel.renderOnly("gargoyleFloor");
			GL11.glTranslatef(-0.5f, 0.0f, -0.5f);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		EntityGargoyle tileentity = (EntityGargoyle)blockAccess.getTileEntity(x, y, z);

		if (tileentity instanceof EntityGargoyle) {
			EntityGargoyle gargoyle = tileentity;
			IIcon icon = block.getIcon(blockAccess, x, y, z, 0);
			//IIcon icon = Blocks.quartz_block.getBlockTextureFromSide(1);
			
			int placement = gargoyle.getDirection();
			float angle = (placement & 7) * 45.0f;
			float offsetX = 0.0f;
			float offsetZ = 0.0f;
			
			gargoyleModel.resetTransform();
			gargoyleModel.rotateY(angle);
			gargoyleModel.setBakeLighting(true);
			gargoyleModel.setIcon(icon);

			Tessellator tessellator = Tessellator.instance;
			tessellator.addTranslation(x + 0.5f, y, z + 0.5f);
            tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, x, y, z));
			
			if((placement & 8) != 0){
					World world = gargoyle.getWorldObj();
					Block belowBlock = world.getBlock( x, y - 1, z );
					AxisAlignedBB box = belowBlock.getCollisionBoundingBoxFromPool(world,  x, y - 1, z);

					if(box != null){
						switch(placement & 7){
						case 0: offsetZ = -1.0f + (float)(box.maxZ - z); break;
						case 2: offsetX = -1.0f + (float)(box.maxX - x); break;
						case 4: offsetZ = (float)(box.minZ - z); break;
						case 6: offsetX = (float)(box.minX - x); break;
						default:
						}
					}

				tessellator.addTranslation(offsetX, 0, offsetZ);
				gargoyleModel.tessellateOnly(tessellator, "gargoyleFloor");
				tessellator.addTranslation(-offsetX, 0, -offsetZ);
				
			} else if ((placement & 16) != 0){
				gargoyleModel.tessellateOnly(tessellator, "gargoyleCeiling");
			} else {
				gargoyleModel.tessellateOnly(tessellator, "gargoyleWall");
			}
			
			tessellator.addTranslation(-(x + 0.5f), -y, -(z + 0.5f));
		}

		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return id;
	}

}
