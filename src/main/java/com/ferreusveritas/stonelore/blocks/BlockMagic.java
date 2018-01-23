package com.ferreusveritas.stonelore.blocks;

import com.ferreusveritas.stonelore.StoneLore;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockMagic extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon icon;

	public BlockMagic() {
		super(Material.rock);

	}

	@Override
	public
	boolean isOpaqueCube(){
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		String name = "magic";
		icon = register.registerIcon(StoneLore.MODID + ":" + name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return icon;
	}



}
