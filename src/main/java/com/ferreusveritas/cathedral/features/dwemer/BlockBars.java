package com.ferreusveritas.cathedral.features.dwemer;

import com.ferreusveritas.cathedral.CathedralMod;

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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;

public class BlockBars extends BlockPane {
	
	public static final String name = "bars";
	
	public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.<EnumType>create("variant", EnumType.class);
	public static final PropertyEnum<EnumCapping> CAPPING = PropertyEnum.<EnumCapping>create("capping", EnumCapping.class);
	
	public BlockBars(String name) {
		super(Material.IRON, true);
		setRegistryName(name);
		setUnlocalizedName(name);
		setDefaultState(getDefaultState().withProperty(VARIANT, EnumType.NORMAL));
		setHarvestLevel("pickaxe", 0);
		setSoundType(SoundType.METAL);
		setResistance(20.0F);
		setHardness(2.5F);
		setCreativeTab(CathedralMod.tabDwemer);
	}
	
	public BlockBars(){
		this(name);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {NORTH, EAST, SOUTH, WEST, VARIANT, CAPPING});
	}
	
    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    	state = super.getActualState(state, worldIn, pos);
    	
    	boolean north = state.getValue(NORTH);
    	boolean east = state.getValue(EAST);
    	boolean south = state.getValue(SOUTH);
    	boolean west = state.getValue(WEST);
    	
    	return state.withProperty(CAPPING,
    							( north && !east && !south && !west) ? EnumCapping.NORTH :
    							(!north &&  east && !south && !west) ? EnumCapping.EAST :
    							(!north && !east &&  south && !west) ? EnumCapping.SOUTH :
    							(!north && !east && !south &&  west) ? EnumCapping.WEST :
    							(!north && !east && !south && !west) ? EnumCapping.POST :
    							EnumCapping.NONE);
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
	
	public static enum EnumType implements IStringSerializable {
		NORMAL(0, "normal"),
		ORNATE(1, "ornate"),
		FOOTER(2, "footer"),
		HEADER(3, "header"),
		MASK(4, "mask"),
		RHOMBUS(5, "rhombus");
		
		private final int meta;
		private final String name;
		private final String unlocalizedName;
		
		private EnumType(int index, String name) {
			this.meta = index;
			this.name = name;
			this.unlocalizedName = name;
		}
		
		public int getMetadata() {
			return meta;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
		public static EnumType byMetadata(int meta) {
			return values()[MathHelper.clamp(meta, 0, values().length - 1)];
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		public String getUnlocalizedName() {
			return unlocalizedName;
		}
		
	}
	
	public static enum EnumCapping implements IStringSerializable {
		NONE("none"),
		POST("post"),
		NORTH("north"),
		EAST("east"),
		SOUTH("south"),
		WEST("west");
		
		private String name;
		
		private EnumCapping(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}
	}
	
}