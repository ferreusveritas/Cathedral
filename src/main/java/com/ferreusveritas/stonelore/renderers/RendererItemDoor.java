package com.ferreusveritas.stonelore.renderers;

import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.ENTITY_BOBBING;
import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.ENTITY_ROTATION;

import org.lwjgl.opengl.GL11;

import com.ferreusveritas.stonelore.StoneLore;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RendererItemDoor implements IItemRenderer {

	private int doorHeight;
	private float doorThick;
	private String asset;
	
	public RendererItemDoor(String asset){
		this(2, 3f / 16f, asset);
	}
	
	public RendererItemDoor(int doorHeight, float doorThick, String asset){
		this.doorHeight = doorHeight;
		this.doorThick = doorThick;
		this.asset = asset;
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		
		if(type == ItemRenderType.ENTITY && helper == ENTITY_ROTATION){
			return true;
		}

		if(type == ItemRenderType.ENTITY && helper == ENTITY_BOBBING){
			return true;
		}
		
		if(type == ItemRenderType.ENTITY && helper == ItemRendererHelper.BLOCK_3D){
			return true;
		}
		
		//System.out.println("shouldUseRenderHelper:" + type);
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();

	    GL11.glEnable(GL11.GL_CULL_FACE);
	    GL11.glCullFace(GL11.GL_BACK);
	    GL11.glFrontFace(GL11.GL_CCW);
	    
		if(type == ItemRenderType.INVENTORY){//In Inventory
			float scale = (16f / doorHeight) * 0.95f;
			GL11.glTranslatef(8.0f, 8.0f, 0.0f);
			GL11.glScalef(scale, -scale, scale);
			GL11.glRotatef(30.0f, 1.0f, 0.0f, 0.0f);
			GL11.glRotatef(-45.0f, 0.0f, 1.0f, 0.0f);
			GL11.glTranslatef(-0.5f, -(float)doorHeight / 2f, 0f);
		} else if(type == ItemRenderType.ENTITY){//In World
			float scale = 1f / doorHeight;
			GL11.glRotatef(90f, 0f, 1f, 0f);
			if(RenderItem.renderInFrame){
				GL11.glScalef(0.875f, 0.875f, 0.875f);
				GL11.glTranslatef(0.0f, -0.5f, 0.0f);
			} else {
				GL11.glTranslatef(0f, -0.25f, 0f);
			}
			GL11.glScalef(scale, scale, scale);
			GL11.glTranslatef(-0.5f, 0f, 0f);
		} else {//In Hand
			float scale = 1f / 2f;
			GL11.glScalef(scale, scale, scale);
			GL11.glTranslatef(0.5f, 0f, 0f);
		}
	
		
		GL11.glTranslatef(0.0f, 0.0f, -doorThick / 2f);
		
		for(int i = 0; i < doorHeight; i++){
			
			Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(StoneLore.MODID, "textures/blocks/" + asset + "_" + i + ".png"));
				
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(1f, 1f);
				GL11.glVertex3f(0f, 0f + i, 0.0f);
				GL11.glTexCoord2f(1f, 0f);
				GL11.glVertex3f(0f, 1f + i, 0.0f);
				GL11.glTexCoord2f(0f, 0f);
				GL11.glVertex3f(1f, 1f + i, 0.0f);
				GL11.glTexCoord2f(0f, 1f);
				GL11.glVertex3f(1f, 0f + i, 0.0f);

				GL11.glTexCoord2f(1f, 1f);
				GL11.glVertex3f(1f, 0f + i, doorThick);
				GL11.glTexCoord2f(1f, 0f);
				GL11.glVertex3f(1f, 1f + i, doorThick);
				GL11.glTexCoord2f(0f, 0f);
				GL11.glVertex3f(0f, 1f + i, doorThick);
				GL11.glTexCoord2f(0f, 1f);
				GL11.glVertex3f(0f, 0f + i, doorThick);
			GL11.glEnd();

		}

		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(StoneLore.MODID, "textures/blocks/" + asset + "_side.png"));
		
		//sides
		for(int i = 0; i < doorHeight; i++){

			GL11.glBegin(GL11.GL_QUADS);

			GL11.glTexCoord2f(doorThick, 1f);
			GL11.glVertex3f(1f, 0f + i, 0f);
			GL11.glTexCoord2f(doorThick, 0f);
			GL11.glVertex3f(1f, 1f + i, 0f);
			GL11.glTexCoord2f(0f, 0f);
			GL11.glVertex3f(1f, 1f + i, doorThick);
			GL11.glTexCoord2f(0f, 1f);
			GL11.glVertex3f(1f, 0f + i, doorThick);

			GL11.glTexCoord2f(doorThick, 1f);
			GL11.glVertex3f(0f, 0f + i, doorThick);
			GL11.glTexCoord2f(doorThick, 0f);
			GL11.glVertex3f(0f, 1f + i, doorThick);
			GL11.glTexCoord2f(0f, 0f);
			GL11.glVertex3f(0f, 1f + i, 0.0f);
			GL11.glTexCoord2f(0f, 1f);
			GL11.glVertex3f(0f, 0f + i, 0.0f);
			
			GL11.glEnd();

		}

		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(StoneLore.MODID, "textures/blocks/" + asset + "_top.png"));

		GL11.glBegin(GL11.GL_QUADS);
			//Bottom
			GL11.glTexCoord2f(doorThick, 1f);
			GL11.glVertex3f(0f, 0f, 0f);
			GL11.glTexCoord2f(doorThick, 0f);
			GL11.glVertex3f(1f, 0f, 0f);
			GL11.glTexCoord2f(0f, 0f);
			GL11.glVertex3f(1f, 0f, doorThick);
			GL11.glTexCoord2f(0f, 1f);
			GL11.glVertex3f(0f, 0f, doorThick);

			//Top
			GL11.glTexCoord2f(doorThick, 1f);
			GL11.glVertex3f(0f, doorHeight, doorThick);
			GL11.glTexCoord2f(doorThick, 0f);
			GL11.glVertex3f(1f, doorHeight, doorThick);
			GL11.glTexCoord2f(0f, 0f);
			GL11.glVertex3f(1f, doorHeight, 0.0f);
			GL11.glTexCoord2f(0f, 1f);
			GL11.glVertex3f(0f, doorHeight, 0.0f);
		GL11.glEnd();
		
		GL11.glPopMatrix();
	}

}
