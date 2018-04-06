package com.ferreusveritas.cathedral.features.cathedral;

import com.ferreusveritas.cathedral.CathedralMod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;

public class BlockChain extends Block {
	
	public final static String name = "chain"; 
	
	public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.<EnumType>create("variant", EnumType.class);
	
	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.375f, 0f, 0.375f, 0.625f, 1f, 0.625f);
	
	public BlockChain() {
		this(name);
	}
	
	public BlockChain(String name) {
		super(Material.IRON);
		setLightOpacity(0);
		setResistance(5.0F);
		setHardness(1.0F);
		setRegistryName(name);
		setUnlocalizedName(name);
		setCreativeTab(CathedralMod.tabCathedral);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {VARIANT});
	}
	
	/** Convert the given metadata into a BlockState for this Block */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, BlockChain.EnumType.byMetadata(meta));
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
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	public int getChainColor(int subBlock){
		return BlockChain.EnumType.byMetadata(subBlock).color;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}
	
	@Override
	public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
		return true;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	public static enum EnumType implements IStringSerializable {
		IRON	(0, "Iron", 0xd7d7d7),
		GOLD	(1, "Gold", 0xe0b820),
		DWEMER	(2, "Dwemer", 0xc3a84e),
		COPPER	(3, "Copper", 0xbe6131),
		BRONZE	(4, "Bronze", 0xa76b21),
		SILVER	(5, "Silver", 0xd6dadd),
		ENDERIUM(6, "Enderium", 0x44b8b8);

		private final int meta;
		private final String name;
		private final String unlocalizedName;
		private final String orename;
		private final int color;
		
		private EnumType(int index, String name, int color) {
			this.meta = index;
			this.name = name.toLowerCase();
			this.unlocalizedName = name.toLowerCase();
			this.orename = name;			
			this.color = color;
		}
		
		public int getMetadata() {
			return meta;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
		public String getOreName() {
			return orename;
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
		
		public int getColor() {
			return color;
		}
		
	}
	
}
