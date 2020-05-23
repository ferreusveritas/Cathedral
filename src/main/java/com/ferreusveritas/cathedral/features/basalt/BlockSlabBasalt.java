package com.ferreusveritas.cathedral.features.basalt;

import java.util.Random;

import com.ferreusveritas.cathedral.CathedralMod;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSlabBasalt extends BlockSlab {
	
	public static final PropertyEnum<BlockSlabBasalt.EnumType> VARIANT = PropertyEnum.<BlockSlabBasalt.EnumType>create("variant", BlockSlabBasalt.EnumType.class);
	
	public BlockSlabBasalt(String name) {
		super(Material.ROCK);
		IBlockState iblockstate = this.blockState.getBaseState();
		
		if (!this.isDouble()) {
			iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
		}
		
		this.setDefaultState(iblockstate.withProperty(VARIANT, BlockSlabBasalt.EnumType.BRICKS));
		
		this.useNeighborBrightness = true;
		
		setRegistryName(name);
		setUnlocalizedName(name);
	}
	
	protected BlockStateContainer createBlockState() {
		return this.isDouble() ? new BlockStateContainer(this, new IProperty[] {VARIANT}) : new BlockStateContainer(this, new IProperty[] {HALF, VARIANT});
	}
	
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, BlockSlabBasalt.EnumType.byMetadata(meta & 7));
		
		if (!this.isDouble()) {
			iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
		}
		
		return iblockstate;
	}
	
	public int getMetaFromState(IBlockState state) {
		int i = 0;
		i = i | ((BlockSlabBasalt.EnumType)state.getValue(VARIANT)).getMetadata();
		
		if (!this.isDouble() && (state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)) {
			i |= 8;
		}
		
		return i;
	}
	
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (BlockSlabBasalt.EnumType type : BlockSlabBasalt.EnumType.values()) {
			items.add(new ItemStack(this, 1, type.getMetadata()));
		}
	}
	
	@Override
	public String getUnlocalizedName(int meta) {
		return super.getUnlocalizedName() + "." + BlockSlabBasalt.EnumType.byMetadata(meta).getName();
	}
	
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(CathedralMod.basalt.slabCarved);
	}

	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
		return new ItemStack(CathedralMod.basalt.slabCarved, 1, ((BlockSlabBasalt.EnumType)state.getValue(VARIANT)).getMetadata());
	}
	
	public int damageDropped(IBlockState state) {
		return ((BlockSlabBasalt.EnumType)state.getValue(VARIANT)).getMetadata();
	}
	
	@Override
	public boolean isDouble() {
		return false;
	}
	
	@Override
	public IProperty<?> getVariantProperty() {
		return VARIANT;
	}
	
	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		return BlockSlabBasalt.EnumType.byMetadata(stack.getMetadata() & 7);
	}
	
	public static enum EnumType implements IStringSerializable {
		
		RAW("raw", new ResourceLocation("chisel", "basalt2"), 7),
		SMALLTILES("smalltiles", new ResourceLocation("chisel", "basalt"), 8),
		LAYERS("layers", new ResourceLocation("chisel", "basalt"), 15),
		BRICKS("bricks", new ResourceLocation("chisel", "basalt2"), 0),
		SMALLBRICKS("smallbricks", new ResourceLocation("chisel", "basalt2"), 1),
		TILES("tiles", new ResourceLocation("chisel", "basalt2"), 3),
		SLABS("slabs", new ResourceLocation("chisel", "basalt"), 7);
		
		private ResourceLocation baseResourceLocation;
		private String name;
		private int baseMeta;
		
		EnumType(String name, ResourceLocation baseResourceLocation, int baseMeta) {
			this.name = name;
			this.baseResourceLocation = baseResourceLocation;
			this.baseMeta = baseMeta;
		}
		
		public int getMetadata() {
			return ordinal();
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		public static EnumType byMetadata(int meta) {
			return values()[meta];
		}
		
		public String getUnlocalizedName() {
			return name;
		}
		
		public ResourceLocation getBaseResourceLocation() {
			return baseResourceLocation;
		}
		
		public int getBaseMeta() {
			return baseMeta;
		}
	}
	
}
