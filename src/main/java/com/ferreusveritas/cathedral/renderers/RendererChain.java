package com.ferreusveritas.cathedral.renderers;

import org.lwjgl.opengl.GL11;

import com.ferreusveritas.cathedral.blocks.BlockChain;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RendererChain implements ISimpleBlockRenderingHandler {

	public static int id;

	public RendererChain() {
		id = RenderingRegistry.getNextAvailableRenderId();
	}

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer) {
		BlockChain chainBlock = (BlockChain) block;
		
		Tessellator tessellator = Tessellator.instance;
	
		int color = chainBlock.getChainColor(meta);
		float r = (color >> 16 & 255) / 255.0F;
		float g = (color >> 8 & 255) / 255.0F;
		float b = (color & 255) / 255.0F;
	
		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_F(r, g, b);
		tessellator.addTranslation(-0.5f, -0.5f, -0.5f);
		renderChain(tessellator, chainBlock.getBlockTextureFromSide(0));
		tessellator.addTranslation(0.5f, 0.5f, 0.5f);
		
		GL11.glPushMatrix();
		GL11.glRotated(45.0f, 0, 1, 0);
		
		tessellator.draw();
		
		GL11.glPopMatrix();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		BlockChain chainBlock = (BlockChain) block;

		float bright = 1.0F;
		int color = chainBlock.colorMultiplier(world, x, y, z);
		float r = (color >> 16 & 255) / 255.0F;
		float g = (color >> 8 & 255) / 255.0F;
		float b = (color & 255) / 255.0F;

		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(chainBlock.getMixedBrightnessForBlock(world, x, y, z));
		tessellator.setColorOpaque_F(bright * r, bright * g, bright * b);
		tessellator.addTranslation(x, y, z);
		renderChain(tessellator, chainBlock.getBlockTextureFromSide(0));
		tessellator.addTranslation(-x, -y, -z);
		
		return true;
	}

	public void renderChain(Tessellator tessellator, IIcon icon){
		double umin1 = icon.getInterpolatedU(0);
		double umax1 = icon.getInterpolatedU(6);
		double vmin1 = icon.getMinV();
		double vmax1 = icon.getMaxV();
		
		double umin2 = icon.getInterpolatedU(6);
		double umax2 = icon.getInterpolatedU(12);
		double vmin2 = icon.getMinV();
		double vmax2 = icon.getMaxV();
		
		float rad = 0.707106781f * 3f / 16f;
		
		tessellator.addVertexWithUV(0.5 - rad, 0.0, 0.5 - rad, umax1, vmax1);
		tessellator.addVertexWithUV(0.5 - rad, 1.0, 0.5 - rad, umax1, vmin1);
		tessellator.addVertexWithUV(0.5 + rad, 1.0, 0.5 + rad, umin1, vmin1);
		tessellator.addVertexWithUV(0.5 + rad, 0.0, 0.5 + rad, umin1, vmax1);
		
		tessellator.addVertexWithUV(0.5 - rad, 0.0, 0.5 + rad, umax2, vmax2);
		tessellator.addVertexWithUV(0.5 - rad, 1.0, 0.5 + rad, umax2, vmin2);
		tessellator.addVertexWithUV(0.5 + rad, 1.0, 0.5 - rad, umin2, vmin2);
		tessellator.addVertexWithUV(0.5 + rad, 0.0, 0.5 - rad, umin2, vmax2);

		tessellator.addVertexWithUV(0.5 + rad, 0.0, 0.5 + rad, umax1, vmax1);
		tessellator.addVertexWithUV(0.5 + rad, 1.0, 0.5 + rad, umax1, vmin1);
		tessellator.addVertexWithUV(0.5 - rad, 1.0, 0.5 - rad, umin1, vmin1);
		tessellator.addVertexWithUV(0.5 - rad, 0.0, 0.5 - rad, umin1, vmax1);
		
		tessellator.addVertexWithUV(0.5 + rad, 0.0, 0.5 - rad, umax2, vmax2);
		tessellator.addVertexWithUV(0.5 + rad, 1.0, 0.5 - rad, umax2, vmin2);
		tessellator.addVertexWithUV(0.5 - rad, 1.0, 0.5 + rad, umin2, vmin2);
		tessellator.addVertexWithUV(0.5 - rad, 0.0, 0.5 + rad, umin2, vmax2);
	}
	
	@Override
	public boolean shouldRender3DInInventory(int renderId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return id;
	}


}
