package com.ferreusveritas.cathedral.renderers;

import org.lwjgl.opengl.GL11;

import com.ferreusveritas.cathedral.blocks.BlockStoneRailing;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RendererStoneRailing implements ISimpleBlockRenderingHandler {

	public static int renderId = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;

		if(modelId == renderId) {
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

			for (int k = 0; k < 8; ++k) {

				switch(k){
				case 0: renderer.setRenderBounds(0.0, 0.8125, 0.3125, 1.0, 1.0, 0.6875); break;//Top Rail
				case 1: renderer.setRenderBounds(0.0, 0.0, 0.3125, 1.0, 0.25, 0.6875); break;//Bottom Rail
				case 2: renderer.setRenderBounds(0.0625, 0.25, 0.375, 0.4375, 0.3125, 0.625); break;//bottom1
				case 3: renderer.setRenderBounds(0.125, 0.3125, 0.4375, 0.375, 0.8125, 0.5625); break;//middle1
				case 4: renderer.setRenderBounds(0.0625, 0.75, 0.375, 0.4375, 0.8125, 0.625); break;//top1
				case 5: renderer.setRenderBounds(0.5625, 0.25, 0.375, 0.9375, 0.3125, 0.625); break;//bottom2
				case 6: renderer.setRenderBounds(0.625, 0.3125, 0.4375, 0.875, 0.8125, 0.5625); break;//middle2
				case 7: renderer.setRenderBounds(0.5625, 0.75, 0.375, 0.9375, 0.8125, 0.625); break;//top2
				}

				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, -1.0F, 0.0F);
				renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 0.0F, -1.0F);
				renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(-1.0F, 0.0F, 0.0F);
				renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
				tessellator.draw();
				tessellator.startDrawingQuads();
				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
				tessellator.draw();
			}

			renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

		BlockStoneRailing railing = (BlockStoneRailing)block;

		boolean w = railing.canConnectWallTo(renderer.blockAccess, x + 1, y, z);
		boolean e = railing.canConnectWallTo(renderer.blockAccess, x - 1, y, z);
		boolean n = railing.canConnectWallTo(renderer.blockAccess, x, y, z + 1);
		boolean s = railing.canConnectWallTo(renderer.blockAccess, x, y, z - 1);
		boolean ns = s && n && !e && !w;
		boolean ew = !s && !n && e && w;
		boolean up = railing.canConnectWallTo(renderer.blockAccess, x, y + 1, z);
		boolean railabove = renderer.blockAccess.getBlock(x, y + 1, z) == block;
		boolean railbelow = renderer.blockAccess.getBlock(x, y - 1, z) == block;


		if(!w && !e && !s && !n && up){
			//Make Post
			renderer.setRenderBounds(0.25, 0.0, 0.25, 0.75, 1.0, 0.75);
			renderer.renderStandardBlock(block, x, y, z);
		}
		else if(!w && !e && !s && !n && railbelow && !railabove){
			//Make a post cap and that's it
			renderer.setRenderBounds(0.25, 0.0, 0.25, 0.75, 0.0625, 0.75);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(0.3125, 0.0625, 0.3125, 0.6875, 0.125, 0.6875);
			renderer.renderStandardBlock(block, x, y, z);

			renderer.setRenderBounds(0.25, 0.125, 0.25, 0.75, 0.5, 0.75);
			renderer.renderStandardBlock(block, x, y, z);
		}
		else if ((ew || ns) && !railabove)//Just a straight section of railing
		{
			if (ns)
			{
				//Top Rail
				renderer.setRenderBounds(0.3125, 0.8125, 0.0, 0.6875, 1.0, 1.0);
				renderer.renderStandardBlock(block, x, y, z);		

				//Bottom Rail
				renderer.setRenderBounds(0.3125, 0.0, 0.0, 0.6875, 0.25, 1.0);
				renderer.renderStandardBlock(block, x, y, z);		

				//bottom1
				renderer.setRenderBounds(0.375, 0.25, 0.0625, 0.625, 0.3125, 0.4375);
				renderer.renderStandardBlock(block, x, y, z);

				//middle1
				renderer.setRenderBounds(0.4375, 0.3125, 0.125, 0.5625, 0.8125, 0.375);
				renderer.renderStandardBlock(block, x, y, z);

				//top1
				renderer.setRenderBounds(0.375, 0.75, 0.0625, 0.625, 0.8125, 0.4375);
				renderer.renderStandardBlock(block, x, y, z);

				//bottom2
				renderer.setRenderBounds(0.375, 0.25, 0.5625, 0.625, 0.3125, 0.9375);
				renderer.renderStandardBlock(block, x, y, z);

				//middle2
				renderer.setRenderBounds(0.4375, 0.3125, 0.625, 0.5625, 0.8125, 0.875);
				renderer.renderStandardBlock(block, x, y, z);

				//top2
				renderer.setRenderBounds(0.375, 0.75, 0.5625, 0.625, 0.8125, 0.9375);
				renderer.renderStandardBlock(block, x, y, z);		

			}
			else//ew
			{
				//Top Rail
				renderer.setRenderBounds(0.0, 0.8125, 0.3125, 1.0, 1.0, 0.6875);
				renderer.renderStandardBlock(block, x, y, z);		

				//Bottom Rail
				renderer.setRenderBounds(0.0, 0.0, 0.3125, 1.0, 0.25, 0.6875);
				renderer.renderStandardBlock(block, x, y, z);		

				//bottom1
				renderer.setRenderBounds(0.0625, 0.25, 0.375, 0.4375, 0.3125, 0.625);
				renderer.renderStandardBlock(block, x, y, z);

				//middle1
				renderer.setRenderBounds(0.125, 0.3125, 0.4375, 0.375, 0.8125, 0.5625);
				renderer.renderStandardBlock(block, x, y, z);

				//top1
				renderer.setRenderBounds(0.0625, 0.75, 0.375, 0.4375, 0.8125, 0.625);
				renderer.renderStandardBlock(block, x, y, z);

				//bottom2
				renderer.setRenderBounds(0.5625, 0.25, 0.375, 0.9375, 0.3125, 0.625);
				renderer.renderStandardBlock(block, x, y, z);

				//middle2
				renderer.setRenderBounds(0.625, 0.3125, 0.4375, 0.875, 0.8125, 0.5625);
				renderer.renderStandardBlock(block, x, y, z);

				//top2
				renderer.setRenderBounds(0.5625, 0.75, 0.375, 0.9375, 0.8125, 0.625);
				renderer.renderStandardBlock(block, x, y, z);		

			}
		}
		else{
			//Make Post
			renderer.setRenderBounds(0.25, 0.0, 0.25, 0.75, 1.0, 0.75);
			renderer.renderStandardBlock(block, x, y, z);

			if(w){//x+1
				//Top Rail
				renderer.setRenderBounds(0.75, 0.8125, 0.3125, 1.0, 1.0, 0.6875);
				renderer.renderStandardBlock(block, x, y, z);		

				//Bottom Rail
				renderer.setRenderBounds(0.75, 0.0, 0.3125, 1.0, 0.25, 0.6875);
				renderer.renderStandardBlock(block, x, y, z);		

				//bottom2
				renderer.setRenderBounds(0.5625, 0.25, 0.375, 0.9375, 0.3125, 0.625);
				renderer.renderStandardBlock(block, x, y, z);

				//middle2
				renderer.setRenderBounds(0.625, 0.3125, 0.4375, 0.875, 0.8125, 0.5625);
				renderer.renderStandardBlock(block, x, y, z);

				//top2
				renderer.setRenderBounds(0.5625, 0.75, 0.375, 0.9375, 0.8125, 0.625);
				renderer.renderStandardBlock(block, x, y, z);		

			}

			if(e){//x-1
				//Top Rail
				renderer.setRenderBounds(0.0, 0.8125, 0.3125, 0.25, 1.0, 0.6875);
				renderer.renderStandardBlock(block, x, y, z);		

				//Bottom Rail
				renderer.setRenderBounds(0.0, 0.0, 0.3125, 0.25, 0.25, 0.6875);
				renderer.renderStandardBlock(block, x, y, z);		

				//bottom1
				renderer.setRenderBounds(0.0625, 0.25, 0.375, 0.4375, 0.3125, 0.625);
				renderer.renderStandardBlock(block, x, y, z);

				//middle1
				renderer.setRenderBounds(0.125, 0.3125, 0.4375, 0.375, 0.8125, 0.5625);
				renderer.renderStandardBlock(block, x, y, z);

				//top1
				renderer.setRenderBounds(0.0625, 0.75, 0.375, 0.4375, 0.8125, 0.625);
				renderer.renderStandardBlock(block, x, y, z);

			}

			if(n){//z+1
				//Top Rail
				renderer.setRenderBounds(0.3125, 0.8125, 0.75, 0.6875, 1.0, 1.0);
				renderer.renderStandardBlock(block, x, y, z);		

				//Bottom Rail
				renderer.setRenderBounds(0.3125, 0.0, 0.75, 0.6875, 0.25, 1.0);
				renderer.renderStandardBlock(block, x, y, z);		

				//bottom2
				renderer.setRenderBounds(0.375, 0.25, 0.5625, 0.625, 0.3125, 0.9375);
				renderer.renderStandardBlock(block, x, y, z);

				//middle2
				renderer.setRenderBounds(0.4375, 0.3125, 0.625, 0.5625, 0.8125, 0.875);
				renderer.renderStandardBlock(block, x, y, z);

				//top2
				renderer.setRenderBounds(0.375, 0.75, 0.5625, 0.625, 0.8125, 0.9375);
				renderer.renderStandardBlock(block, x, y, z);		

			}

			if(s){//z-1
				//Top Rail
				renderer.setRenderBounds(0.3125, 0.8125, 0.0, 0.6875, 1.0, 0.25);
				renderer.renderStandardBlock(block, x, y, z);		

				//Bottom Rail
				renderer.setRenderBounds(0.3125, 0.0, 0.0, 0.6875, 0.25, 0.25);
				renderer.renderStandardBlock(block, x, y, z);		

				//bottom1
				renderer.setRenderBounds(0.375, 0.25, 0.0625, 0.625, 0.3125, 0.4375);
				renderer.renderStandardBlock(block, x, y, z);

				//middle1
				renderer.setRenderBounds(0.4375, 0.3125, 0.125, 0.5625, 0.8125, 0.375);
				renderer.renderStandardBlock(block, x, y, z);

				//top1
				renderer.setRenderBounds(0.375, 0.75, 0.0625, 0.625, 0.8125, 0.4375);
				renderer.renderStandardBlock(block, x, y, z);

			}
		}

		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return renderId;
	}

}
