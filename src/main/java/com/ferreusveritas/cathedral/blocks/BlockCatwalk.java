package com.ferreusveritas.cathedral.blocks;

import com.ferreusveritas.cathedral.Cathedral;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockCatwalk extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	private IIcon sideIcon;
	
	public BlockCatwalk(Material material) {
		super(material);
		this.setBlockBounds(0.0F, 0.875F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

    @SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		
		String name = "dwemer-catwalk";
		
		topIcon = register.registerIcon(Cathedral.MODID + ":" + name + "-top");
		sideIcon = register.registerIcon(Cathedral.MODID + ":" + name + "-side");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		if(side == 1 || side == 0){ return topIcon; }
		return sideIcon;		
	}
    
    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    @Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }
	
    @Override
	public boolean isOpaqueCube()
    {
        return false;
    }
}
