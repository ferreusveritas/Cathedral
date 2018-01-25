package com.ferreusveritas.cathedral.features.extras;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockCatwalk extends Block {

	public BlockCatwalk(Material materialIn) {
		super(materialIn);
	}

	/*
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
    */
}
