package com.ferreusveritas.cathedral.features.dwemer;

import com.ferreusveritas.cathedral.Cathedral;

import net.minecraft.block.Block;
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

public class BlockDwemer extends Block {

	public static final String name = "dwemer";
	
	public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.<EnumType>create("variant", EnumType.class);
	
	public BlockDwemer() {
		this(name);
	}
	
	public BlockDwemer(String name) {
		super(Material.ROCK);
		setRegistryName(name);
		setUnlocalizedName(name);
		setCreativeTab(Cathedral.tabBasalt);
		setHardness(Cathedral.basalt.basaltHardness);
		setResistance(Cathedral.basalt.basaltResistance);
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
	
	public static enum EnumType implements IStringSerializable {
		
		EMBEDDED   	( 0, "embedded"),
		PILLAR     	( 1, "pillar"),
		ALTAR     	( 2, "altar"),
		DECOR      	( 3, "decor"),
		CARVING1   	( 4, "carving1"),
		CARVING2   	( 5, "carving2"),
		LAYERED    	( 6, "layered"),
		SCALEPILLAR	( 7, "scalepillar"),
		WORMGEAR   	( 8, "wormgear"),
		RAYS       	( 9, "rays"),
		KNOT       	(10, "knot"),
		MASK       	(11, "mask"),
		DOORTOP    	(12, "doortop"),
		DOORBOTTOM 	(13, "doorbottom"),
		PANEL      	(14, "panel");
		
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
			for (EnumType type : values()) {
				META_LOOKUP[type.getMetadata()] = type;
			}
		}

	}

}
