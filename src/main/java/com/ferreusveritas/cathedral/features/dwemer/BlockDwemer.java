package com.ferreusveritas.cathedral.features.dwemer;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.common.blocks.BlockBase;
import com.ferreusveritas.cathedral.common.blocks.StandardEnum;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;

public class BlockDwemer extends BlockBase {

	public static final String name = "dwemer";
	
	public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.<EnumType>create("variant", EnumType.class);
	
	public BlockDwemer() {
		this(name);
	}
	
	public BlockDwemer(String name) {
		super(Material.ROCK, name);
		setCreativeTab(CathedralMod.tabDwemer);
		setHardness(CathedralMod.basalt.basaltHardness);
		setResistance(CathedralMod.basalt.basaltResistance);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {VARIANT});
	}
	
	/** Convert the given metadata into a BlockState for this Block */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, EnumType.byMetadata(meta));
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
	
	public static enum EnumType implements StandardEnum {
		
		EMBEDDED,
		PILLAR,
		ALTAR,
		CARVING1,
		CARVING2,
		LAYERED,
		SCALEPILLAR,	
		WORMGEAR,
		RAYS,
		KNOT,
		MASK,
		PANEL,
		VENT;	
		
		public int getMetadata() {
			return ordinal();
		}
		
		@Override
		public String toString() {
			return getName();
		}
		
		public static EnumType byMetadata(int meta) {
			return values()[MathHelper.clamp(meta, 0, values().length - 1)];
		}
		
	}

}
