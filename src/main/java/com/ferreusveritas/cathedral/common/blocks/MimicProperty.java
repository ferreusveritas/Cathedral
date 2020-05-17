package com.ferreusveritas.cathedral.common.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.property.IUnlistedProperty;

public class MimicProperty implements IUnlistedProperty<IBlockState> {
	
	public static final MimicProperty MIMIC = new MimicProperty("mimic");
	
	private final String name;
	
	public MimicProperty(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public boolean isValid(IBlockState value) {
		return value != null;
	}
	
	@Override
	public Class<IBlockState> getType() {
		return IBlockState.class;
	}
	
	@Override
	public String valueToString(IBlockState value) {
		return value.toString();
	}
	
	public static interface IMimic {
		IBlockState getMimic(IBlockAccess access, BlockPos pos);
	}
	
	public static interface IMimicProvider {
		IBlockState getBaseBlock();
	}
	
}
