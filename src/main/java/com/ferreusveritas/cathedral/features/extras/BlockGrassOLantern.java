package com.ferreusveritas.cathedral.features.extras;

import com.ferreusveritas.cathedral.CathedralMod;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;

public class BlockGrassOLantern extends Block {

	public static String name = "grassolantern";
	
	public BlockGrassOLantern() {
		super(Material.GRASS);
		setRegistryName(name);
		setUnlocalizedName(name);
		setSoundType(SoundType.GROUND);
		setCreativeTab(CathedralMod.tabCathedral);
		
		setLightLevel(1.0f);
	}
	
	
	///////////////////////////////////////////
	// RENDERING
	///////////////////////////////////////////
	
	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return layer == BlockRenderLayer.CUTOUT_MIPPED || layer == BlockRenderLayer.SOLID;
	}
	
}
