package com.ferreusveritas.cathedral.blocks;

import com.ferreusveritas.cathedral.Cathedral;

import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;

public class BlockBars extends BlockPane {
	
	public static String name = "bars";
	
	public static final PropertyEnum<BlockBars.EnumType> VARIANT = PropertyEnum.<BlockBars.EnumType>create("variant", BlockBars.EnumType.class);
	
	public BlockBars(String name) {
		super(Material.IRON, true);
		
		setUnlocalizedName("bars");
		setRegistryName(Cathedral.MODID + "_" + name);
		setHarvestLevel("pickaxe", 0);
		setSoundType(SoundType.METAL);
		setResistance(20.0F);
		setHardness(2.5F);
		setCreativeTab(Cathedral.tabBasalt);
	}
	
	public BlockBars(){
		this(name);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {NORTH, EAST, WEST, SOUTH, VARIANT});
	}
	
	/** Convert the given metadata into a BlockState for this Block */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, BlockBars.EnumType.byMetadata(meta));
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
	
	/*public void addVariations(){
		ICarvingRegistry carving = CarvingUtils.getChiselRegistry();
		for(int meta = 0; meta < subBlocks; meta++){
			carving.addVariation("dwemerBars", this, meta, 1);
		}
	}*/
	
	static {
		@SuppressWarnings("unused")
		String names[] = {
				"dwembars",//0
				"dwembars-ornate",//1
				"dwembars-footer",//2
				"dwembars-header",//3
				"dwembars-mask",//4
				"dwembars-rombus",//5
		};
	}	
	
	public static enum EnumType implements IStringSerializable {
		NORMAL(0, "normal"),
		ORNATE(1, "ornate"),
		FOOTER(2, "footer"),
		HEADER(3, "header"),
		MASK(4, "mask"),
		ROMBUS(5, "rombus");
		
		/** Array of the Block's BlockStates */
		private static final BlockBars.EnumType[] META_LOOKUP = new BlockBars.EnumType[values().length];
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
		public static BlockBars.EnumType byMetadata(int meta) {
			if (meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}
			
			return META_LOOKUP[meta];
		}
		
		@Override
		public String getName() {
			return this.name;
		}
		
		public String getUnlocalizedName() {
			return this.unlocalizedName;
		}
		
		static {
			for (BlockBars.EnumType blockbars$enumtype : values()) {
				META_LOOKUP[blockbars$enumtype.getMetadata()] = blockbars$enumtype;
			}
		}
	}
	
}