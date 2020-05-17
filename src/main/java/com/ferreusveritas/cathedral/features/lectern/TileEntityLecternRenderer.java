package com.ferreusveritas.cathedral.features.lectern;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityLecternRenderer extends TileEntitySpecialRenderer<TileEntityLectern> {

	/** The texture for the book above the enchantment table. */
	private static final ResourceLocation TEXTURE_BOOK = new ResourceLocation("textures/entity/enchanting_table_book.png");
	private final ModelBook modelBook = new ModelBook();

	public void render(TileEntityLectern te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		
		if(te.hasBook()) {
			IBlockState blockState = te.getBlockType().getStateFromMeta(te.getBlockMetadata());

			GlStateManager.pushMatrix();
			GlStateManager.translate((float)x + 0.5f, (float)y + 1.0625f, (float)z + 0.5f);

			float yaw = 360.0f - blockState.getValue(BlockLectern.FACING).rotateY().getHorizontalAngle();
			GlStateManager.rotate(yaw, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(67.5f, 0.0F, 0.0F, 1.0F);
			GlStateManager.translate(0.0f, -0.125f, 0.0f);

			bindTexture(TEXTURE_BOOK);

			GlStateManager.enableCull();

			float openness = 1.2f;
			float pagesOffset = 0.0625f;
			float pageTurn = 0.0f;
			
			modelBook.render((Entity)null, 0.0f, 0.0f, pageTurn, openness, 0.0F, pagesOffset);

			GlStateManager.popMatrix();
		}
		
	}
}