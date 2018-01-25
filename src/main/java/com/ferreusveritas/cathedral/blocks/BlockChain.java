package com.ferreusveritas.cathedral.blocks;

import com.ferreusveritas.cathedral.Cathedral;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockChain extends Block {
	
	public final static String name = "chain"; 
	
	public static final PropertyEnum<BlockChain.EnumType> VARIANT = PropertyEnum.<BlockChain.EnumType>create("variant", BlockChain.EnumType.class);
	
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
		setCreativeTab(Cathedral.tabCathedral);
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

	public static enum EnumType implements IStringSerializable {
		IRON	(0, "iron", 0xd7d7d7),
		GOLD	(1, "gold", 0xe0b820),
		DWEMER	(2, "dwemer", 0xc3a84e),
		COPPER	(3, "copper", 0xbe6131),
		BRONZE	(4, "bronze", 0xa76b21),
		SILVER	(5, "silver", 0xd6dadd),
		ENDERIUM(6, "enderium", 0x44b8b8);

		/** Array of the Block's BlockStates */
		private static final BlockChain.EnumType[] META_LOOKUP = new BlockChain.EnumType[values().length];
		/** The BlockState's metadata. */
		private final int meta;
		/** The EnumType's name. */
		private final String name;
		private final String unlocalizedName;
		private final int color;
		
		private EnumType(int index, String name, int color) {
			this.meta = index;
			this.name = name;
			this.unlocalizedName = name;
			this.color = color;
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
		public static BlockChain.EnumType byMetadata(int meta) {
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
		
		public int getColor() {
			return this.color;
		}
		
		static {
			for (BlockChain.EnumType blockchain$enumtype : values()) {
				META_LOOKUP[blockchain$enumtype.getMetadata()] = blockchain$enumtype;
			}
		}
	}
	
}
