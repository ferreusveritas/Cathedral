package com.ferreusveritas.cathedral.features.dwemer;

import com.ferreusveritas.cathedral.common.blocks.BlockGlassBase;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;

public class BlockDwemerGlass extends BlockGlassBase {
	
	public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.<EnumType>create("variant", EnumType.class);
	
	public BlockDwemerGlass(String name) {
		super(name);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {VARIANT});
	}
	
	/** Convert the given metadata into a BlockState for this Block */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, BlockDwemerGlass.EnumType.byMetadata(meta));
	}
	
	/** Convert the BlockState into the correct metadata value */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT).getMetadata();
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(VARIANT).getMetadata();
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for(EnumType type : EnumType.values()) {
			items.add(new ItemStack(this, 1, type.getMetadata()));
		}
	}
	
	enum EnumType implements IStringSerializable {
		FENCE(0, "fence"),
		ORNATE(1, "ornate");

		private int metadata;
		private String name;
		
		private EnumType(int meta, String name) {
			this.metadata = meta;
			this.name = name;
		}
		
		public int getMetadata() {
			return metadata;
		}
				
		@Override
		public String getName() {
			return name;
		}

		public static EnumType byMetadata(int metadata) {
			return values()[metadata];
		}

		public String getUnlocalizedName() {
			return name;
		}
		
		
	}
	
}
