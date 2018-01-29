package com.ferreusveritas.cathedral.features.dwemer;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.common.blocks.BlockBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;

public class BlockDwemerLight extends BlockBase {

	public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.<EnumType>create("variant", EnumType.class);
	
	public BlockDwemerLight(String name) {
		super(Material.ROCK, name);
		setCreativeTab(CathedralMod.tabDwemer);
		setHardness(CathedralMod.basalt.basaltHardness);
		setResistance(CathedralMod.basalt.basaltResistance);
		setLightLevel(1.0F);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {VARIANT});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, EnumType.byMetadata(meta));
	}
	
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
	
	public static enum EnumType implements IStringSerializable {
		
		PATH	(0, "path"),
		VENT	(1, "vent"),
		GAS		(2, "gas");
		
		/** Array of the Block's BlockStates */
		private static final EnumType[] META_LOOKUP = new EnumType[values().length];
		/** The BlockState's metadata. */
		private final int meta;
		/** The EnumType's name. */
		private final String name;
		private final String unlocalizedName;
		
		private EnumType(int index, String name) {
			this.meta = index;
			this.name = name;
			this.unlocalizedName = name;
		}
		
		/** Returns the EnumType's metadata value. */
		public int getMetadata() {
			return this.meta;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
		
		/** Returns an EnumType for the BlockState from a metadata value. */
		public static EnumType byMetadata(int meta) {
			return META_LOOKUP[MathHelper.clamp(meta, 0, META_LOOKUP.length - 1)];
		}
		
		@Override
		public String getName() {
			return this.name;
		}
		
		public String getUnlocalizedName() {
			return this.unlocalizedName;
		}
		
		static {
			for (EnumType type : EnumType.values()) {
				META_LOOKUP[type.getMetadata()] = type;
			}
		}

	}
}
