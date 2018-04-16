package com.ferreusveritas.cathedral.features.cathedral;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockCatwalk extends Block {

	public static final String name = "catwalk";
	
	public BlockCatwalk(Material materialIn) {
		this(materialIn, name);
	}
	
	public BlockCatwalk(Material materialIn, String name) {
		super(materialIn);
		setRegistryName(name);
		setUnlocalizedName(name);
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
