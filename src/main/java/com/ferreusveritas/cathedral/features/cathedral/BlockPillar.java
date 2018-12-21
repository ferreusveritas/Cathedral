package com.ferreusveritas.cathedral.features.cathedral;

import com.ferreusveritas.cathedral.CathedralMod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPillar extends Block {
	
	public static final String name = "pillar";
	public static final PropertyBool DOWN = PropertyBool.create("down");
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyEnum<EnumMaterial> VARIANT = PropertyEnum.<EnumMaterial>create("variant", EnumMaterial.class);
	
	
	public BlockPillar() {
		this(name);
	}
	
	public BlockPillar(String name) {
		super(Material.ROCK);
		setRegistryName(name);
		setUnlocalizedName(name);
		setDefaultState(this.blockState.getBaseState());
		setCreativeTab(CathedralMod.tabCathedral);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {DOWN, UP, NORTH, SOUTH, WEST, EAST, VARIANT});
	}
	
	/** Convert the given metadata into a BlockState for this Block */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, EnumMaterial.byMetadata(meta));
	}
	
	/** Convert the BlockState into the correct metadata value */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT).getMetadata();
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		boolean d = canPillarConnectTo(world, pos, EnumFacing.DOWN);
		boolean u = canPillarConnectTo(world, pos, EnumFacing.UP);
		boolean n = canPillarConnectTo(world, pos, EnumFacing.NORTH);
		boolean e = canPillarConnectTo(world, pos, EnumFacing.EAST);
		boolean s = canPillarConnectTo(world, pos, EnumFacing.SOUTH);
		boolean w = canPillarConnectTo(world, pos, EnumFacing.WEST);
		
		return state
				.withProperty(DOWN, Boolean.valueOf(d))
				.withProperty(UP, Boolean.valueOf(u))
				.withProperty(NORTH, Boolean.valueOf(n))
				.withProperty(SOUTH, Boolean.valueOf(s))
				.withProperty(WEST, Boolean.valueOf(w))
				.withProperty(EAST, Boolean.valueOf(e));
	}
	
	private boolean canPillarConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		BlockPos other = pos.offset(facing);
		Block block = world.getBlockState(other).getBlock();
		
		if(block instanceof BlockPillar) {
			return true;//Pillars always connect to pillars
		}
		
		if(facing.getAxis() == Axis.Y) {
			return false;//Pillars only connect to pillars on the up/down axis
		}
		
		return block.canBeConnectedTo(world, other, facing.getOpposite()) || canConnectTo(world, other, facing.getOpposite());
	}
	
	private boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, EnumFacing facing) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();
		BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos, facing);
		return !isExceptionBlockForAttachWithPiston(block) && blockfaceshape == BlockFaceShape.SOLID;
	}
	
	protected static boolean isExceptionBlockForAttachWithPiston(Block block) {
		return Block.isExceptBlockForAttachWithPiston(block)
				|| block == Blocks.BARRIER
				|| block == Blocks.MELON_BLOCK
				|| block == Blocks.PUMPKIN
				|| block == Blocks.LIT_PUMPKIN;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return face.getAxis() == Axis.Y ? BlockFaceShape.CENTER_BIG : BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for(EnumMaterial type : EnumMaterial.values()) {
			items.add(new ItemStack(this, 1, type.getMetadata()));
		}
	}
	
	/** Used to determine ambient occlusion and culling when rebuilding chunks for render */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
		return blockState.getValue(VARIANT).getHardness();
	}
	
	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		return world.getBlockState(pos).getValue(VARIANT).getExplosionResistance(exploder);
	}
	
}
