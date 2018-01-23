package com.ferreusveritas.cathedral.renderers;

import org.lwjgl.opengl.GL11;

import com.ferreusveritas.cathedral.blocks.BlockRoofTiles;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;

public class RendererRoofTiles implements ISimpleBlockRenderingHandler {
	
	public static int id;

	public RendererRoofTiles() {
		id = RenderingRegistry.getNextAvailableRenderId();
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		
            for (int k = 0; k < 2; ++k)
            {
                if (k == 0)
                {
                	renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
                }

                if (k == 1)
                {
                	renderer.setRenderBounds(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);
                }

                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 0));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 1));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, -1.0F);
                renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 2));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, 1.0F);
                renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 3));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 4));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(1.0F, 0.0F, 0.0F);
                renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSide(block, 5));
                tessellator.draw();
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }
		
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
		BlockRoofTiles blockRoofTiles = (BlockRoofTiles)block;
		
		int metadata = world.getBlockMetadata(x, y, z);

		if((metadata & 8) == 8){
			blockRoofTiles.setBlockBoundsBasedOnState(world, x, y, z);
			renderer.renderStandardBlock(blockRoofTiles, x, y, z);
		}
		else {
		
			renderer.uvRotateTop = (metadata + 5) & 3;
		
			//Render Slab
			blockRoofTiles.setBoundsForSlab(world, x, y, z);
			renderer.setRenderBoundsFromBlock(blockRoofTiles);
			renderer.renderStandardBlock(blockRoofTiles, x, y, z);
			
			renderer.field_152631_f = true;// Fixes random block texture rotations for small textures... Used in renderBlockFence
        
			//Render remaining pieces
			boolean straightStairs = blockRoofTiles.setBoundsForRemainingStep(world, x, y, z);
			renderer.setRenderBoundsFromBlock(blockRoofTiles);
			renderer.renderStandardBlock(blockRoofTiles, x, y, z);
			
			if (straightStairs && blockRoofTiles.setBoundsForRemainingCrossStep(world, x, y, z)) {
				renderer.setRenderBoundsFromBlock(blockRoofTiles);//render the little "1/8th" block that makes this an inside step
				renderer.renderStandardBlock(blockRoofTiles, x, y, z);
			}

			renderer.field_152631_f = false;
			renderer.uvRotateTop = 0;
		}
		
        return true;
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
