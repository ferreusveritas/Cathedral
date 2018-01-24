package com.ferreusveritas.cathedral.renderers;

public class RendererTallDoor  implements ISimpleBlockRenderingHandler {

	/*
	public static int id;
	
	public RendererTallDoor(){
		id = RenderingRegistry.getNextAvailableRenderId();
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		
	}
    
	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		
        Tessellator tessellator = Tessellator.instance;
        int meta = blockAccess.getBlockMetadata(x, y, z);
                
        if((meta & 12) == 12){
        	if(blockAccess.getBlock(x, y - 2, z) != block) {
        		return false;
        	}
        }
        else if((meta & 12) == 8){
            if (blockAccess.getBlock(x, y - 1, z) != block) {
                return false;
            }
        } else {
            if (blockAccess.getBlock(x, y, z) != block) {
                return false;
            }
        }
        
        
        float bottomShading = 0.5F;
        float topShading = 1.0F;
        float zFaceShading = 0.8F;
        float xFaceShading = 0.6F;
        int i1 = block.getMixedBrightnessForBlock(blockAccess, x, y, z);
        tessellator.setBrightness(renderer.renderMinY > 0.0D ? i1 : block.getMixedBrightnessForBlock(blockAccess, x, y - 1, z));
        tessellator.setColorOpaque_F(bottomShading, bottomShading, bottomShading);
        renderer.renderFaceYNeg(block, x, y, z, renderer.getBlockIcon(block, blockAccess, x, y, z, 0));
        
        tessellator.setBrightness(renderer.renderMaxY < 1.0D ? i1 : block.getMixedBrightnessForBlock(blockAccess, x, y + 1, z));
        tessellator.setColorOpaque_F(topShading, topShading, topShading);
        renderer.renderFaceYPos(block, x, y, z, renderer.getBlockIcon(block, blockAccess, x, y, z, 1));

        tessellator.setBrightness(renderer.renderMinZ > 0.0D ? i1 : block.getMixedBrightnessForBlock(blockAccess, x, y, z - 1));
        tessellator.setColorOpaque_F(zFaceShading, zFaceShading, zFaceShading);
        IIcon iicon = renderer.getBlockIcon(block, blockAccess, x, y, z, 2);
        renderer.renderFaceZNeg(block, x, y, z, iicon);

        renderer.flipTexture = false;
        tessellator.setBrightness(renderer.renderMaxZ < 1.0D ? i1 : block.getMixedBrightnessForBlock(blockAccess, x, y, z + 1));
        tessellator.setColorOpaque_F(zFaceShading, zFaceShading, zFaceShading);
        iicon = renderer.getBlockIcon(block, blockAccess, x, y, z, 3);
        renderer.renderFaceZPos(block, x, y, z, iicon);

        renderer.flipTexture = false;
        tessellator.setBrightness(renderer.renderMinX > 0.0D ? i1 : block.getMixedBrightnessForBlock(blockAccess, x - 1, y, z));
        tessellator.setColorOpaque_F(xFaceShading, xFaceShading, xFaceShading);
        iicon = renderer.getBlockIcon(block, blockAccess, x, y, z, 4);
        renderer.renderFaceXNeg(block, x, y, z, iicon);

        renderer.flipTexture = false;
        tessellator.setBrightness(renderer.renderMaxX < 1.0D ? i1 : block.getMixedBrightnessForBlock(blockAccess, x + 1, y, z));
        tessellator.setColorOpaque_F(xFaceShading, xFaceShading, xFaceShading);
        iicon = renderer.getBlockIcon(block, blockAccess, x, y, z, 5);
        renderer.renderFaceXPos(block, x, y, z, iicon);

        renderer.flipTexture = false;
        return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return id;
	}
*/
}
