package com.ferreusveritas.cathedral.features.cathedral;

import java.util.List;

import javax.annotation.Nullable;

import com.ferreusveritas.cathedral.CathedralMod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
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
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRailing extends Block {
	
	public static final String name = "railing";
	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyBool POSTCAP = PropertyBool.create("postcap");
	public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.<EnumType>create("variant", EnumType.class);
	
	protected static final AxisAlignedBB[] AABB_BY_INDEX = new AxisAlignedBB[] {
			new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D),
			new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D),
			new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.75D, 1.0D, 1.0D),
			new AxisAlignedBB(0.25D, 0.0D, 0.0D, 0.75D, 1.0D, 0.75D),
			new AxisAlignedBB(0.3125D, 0.0D, 0.0D, 0.6875D, 1.0D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 0.75D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D),
			new AxisAlignedBB(0.25D, 0.0D, 0.25D, 1.0D, 1.0D, 0.75D),
			new AxisAlignedBB(0.25D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.3125D, 1.0D, 1.0D, 0.6875D),
			new AxisAlignedBB(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D),
			new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D),
			new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D),
			new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D),
			new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.5D, 0.75D)
	};
	
	protected static final AxisAlignedBB[] CLIP_AABB_BY_INDEX = new AxisAlignedBB[] {
			AABB_BY_INDEX[0].setMaxY(1.5D),
			AABB_BY_INDEX[1].setMaxY(1.5D),
			AABB_BY_INDEX[2].setMaxY(1.5D),
			AABB_BY_INDEX[3].setMaxY(1.5D),
			AABB_BY_INDEX[4].setMaxY(1.5D),
			AABB_BY_INDEX[5].setMaxY(1.5D),
			AABB_BY_INDEX[6].setMaxY(1.5D),
			AABB_BY_INDEX[7].setMaxY(1.5D),
			AABB_BY_INDEX[8].setMaxY(1.5D),
			AABB_BY_INDEX[9].setMaxY(1.5D),
			AABB_BY_INDEX[10].setMaxY(1.5D),
			AABB_BY_INDEX[11].setMaxY(1.5D),
			AABB_BY_INDEX[12].setMaxY(1.5D),
			AABB_BY_INDEX[13].setMaxY(1.5D),
			AABB_BY_INDEX[14].setMaxY(1.5D),
			AABB_BY_INDEX[15].setMaxY(1.5D),
			AABB_BY_INDEX[16]
	};
	
	public BlockRailing() {
		this(name);
	}
	
	public BlockRailing(String name) {
		super(Material.ROCK);
		setUnlocalizedName(name);
		setRegistryName(name);
		setDefaultState(this.blockState.getBaseState()
				.withProperty(UP, Boolean.valueOf(false))
				.withProperty(NORTH, Boolean.valueOf(false))
				.withProperty(EAST, Boolean.valueOf(false))
				.withProperty(SOUTH, Boolean.valueOf(false))
				.withProperty(WEST, Boolean.valueOf(false))
				.withProperty(POSTCAP, Boolean.valueOf(false))
				.withProperty(VARIANT, EnumType.STONE));
		setCreativeTab(CathedralMod.tabCathedral);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {UP, NORTH, EAST, WEST, SOUTH, POSTCAP, VARIANT});
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		boolean n = canRailConnectTo(world, pos, EnumFacing.NORTH);
		boolean e = canRailConnectTo(world, pos, EnumFacing.EAST);
		boolean s = canRailConnectTo(world, pos, EnumFacing.SOUTH);
		boolean w = canRailConnectTo(world, pos, EnumFacing.WEST);
		boolean up = canRailConnectTo(world, pos, EnumFacing.UP);
		boolean centerPole = !((n && s && !e && !w) || (!n && !s && e && w));
		
		//boolean railabove = world.getBlockState(pos.up()).getBlock() instanceof BlockRailing;
		boolean railbelow = world.getBlockState(pos.down()).getBlock() instanceof BlockRailing;
		
		if(!w && !e && !s && !n) {//There's no connections horizontally
			if(up) {
				return state.withProperty(UP, Boolean.valueOf(true));
			}
			if(railbelow /* && !railabove*/) { //If there's a rail below but not above then
				return state.withProperty(POSTCAP, true);//Make a post cap and that's it
				//this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
			}
		}
		
		return state
				.withProperty(UP, Boolean.valueOf(centerPole || up))
				.withProperty(NORTH, Boolean.valueOf(n))
				.withProperty(EAST, Boolean.valueOf(e))
				.withProperty(SOUTH, Boolean.valueOf(s))
				.withProperty(WEST, Boolean.valueOf(w))
				.withProperty(POSTCAP, Boolean.valueOf(false));
	}
	
	private boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, EnumFacing facing) {
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();
		BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos, facing);
		boolean flag = blockfaceshape == BlockFaceShape.MIDDLE_POLE_THICK || blockfaceshape == BlockFaceShape.MIDDLE_POLE && block instanceof BlockFenceGate;
		return !isExceptionBlockForAttachWithPiston(block) && blockfaceshape == BlockFaceShape.SOLID || flag;
	}
	
	protected static boolean isExceptionBlockForAttachWithPiston(Block block) {
		return Block.isExceptBlockForAttachWithPiston(block)
				|| block == Blocks.BARRIER
				|| block == Blocks.MELON_BLOCK
				|| block == Blocks.PUMPKIN
				|| block == Blocks.LIT_PUMPKIN;
	}
	
	@Override
	public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		Block connector = world.getBlockState(pos.offset(facing)).getBlock();
		return connector instanceof BlockWall || connector instanceof BlockFenceGate || connector instanceof BlockRailing;
	}
	
	private boolean canRailConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		BlockPos other = pos.offset(facing);
		Block block = world.getBlockState(other).getBlock();
		return block.canBeConnectedTo(world, other, facing.getOpposite()) || canConnectTo(world, other, facing.getOpposite());
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
	
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	}
	
	/** Used to determine ambient occlusion and culling when rebuilding chunks for render */
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return side == EnumFacing.DOWN ? super.shouldSideBeRendered(blockState, blockAccess, pos, side) : true;
	}
	
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		state = this.getActualState(state, source, pos);
		return AABB_BY_INDEX[getAABBIndex(state)];
	}
	
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
		if (!isActualState) {
			state = this.getActualState(state, worldIn, pos);
		}
		
		addCollisionBoxToList(pos, entityBox, collidingBoxes, CLIP_AABB_BY_INDEX[getAABBIndex(state)]);
	}
	
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		blockState = this.getActualState(blockState, worldIn, pos);
		return CLIP_AABB_BY_INDEX[getAABBIndex(blockState)];
	}

	private static int getAABBIndex(IBlockState state) {
		int i = 0;
		
		if (((Boolean)state.getValue(POSTCAP)).booleanValue()) {
			return 16;
		}
		
		if (((Boolean)state.getValue(NORTH)).booleanValue()) {
			i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
		}
		
		if (((Boolean)state.getValue(EAST)).booleanValue()) {
			i |= 1 << EnumFacing.EAST.getHorizontalIndex();
		}
		
		if (((Boolean)state.getValue(SOUTH)).booleanValue()) {
			i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
		}
		
		if (((Boolean)state.getValue(WEST)).booleanValue()) {
			i |= 1 << EnumFacing.WEST.getHorizontalIndex();
		}
		
		return i;
	}

	public static enum EnumType implements IStringSerializable {
		
		STONE         (0, "stone"),
		SANDSTONE     (1, "sandstone"),
		REDSANDSTONE  (2, "redsandstone"),
		OBSIDIAN      (3, "obsidian"),
		NETHERBRICK   (4, "netherbrick"),
		QUARTZ        (5, "quartz"),
		ENDSTONE      (6, "endstone"),
		PACKEDICE     (7, "packedice"),
		SNOW          (8, "snow"),
		MARBLE        (9, "marble"),
		LIMESTONE     (10, "limestone"),
		BASALT        (11, "basalt"),
		DWEMER        (12, "dwemer");
		
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
	
	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
		switch (blockState.getValue(VARIANT)) {
			case STONE: return 1.5f;
			case SANDSTONE: return 0.8f;
			case REDSANDSTONE: return 0.8f;
			case OBSIDIAN: return 50.0f;
			case NETHERBRICK: return 2.0f;
			case QUARTZ: return 0.8f;
			case ENDSTONE: return 3.0f;
			case PACKEDICE: return 0.5f;
			case SNOW: return 0.2f;
			case MARBLE: return 1.5f;
			case LIMESTONE: return 2.0f;
			case BASALT: return 2.5f;
			case DWEMER: return 2.5f;
			default: return 1.5f;
		}
	}
	
	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		IBlockState blockState = world.getBlockState(pos);
		
		switch (blockState.getValue(VARIANT)) {
			case STONE: return Blocks.STONE.getExplosionResistance(exploder);
			case SANDSTONE: return Blocks.SANDSTONE.getExplosionResistance(exploder);
			case REDSANDSTONE: return Blocks.RED_SANDSTONE.getExplosionResistance(exploder);
			case OBSIDIAN: return Blocks.OBSIDIAN.getExplosionResistance(exploder);
			case NETHERBRICK: return Blocks.NETHER_BRICK.getExplosionResistance(exploder);
			case QUARTZ: return Blocks.QUARTZ_BLOCK.getExplosionResistance(exploder);
			case ENDSTONE: return Blocks.END_STONE.getExplosionResistance(exploder);
			case PACKEDICE: return Blocks.PACKED_ICE.getExplosionResistance(exploder);
			case SNOW: return Blocks.SNOW.getExplosionResistance(exploder);
			case MARBLE: return 10.0f;
			case LIMESTONE: return 10.0f;
			case BASALT: return 20.0f;
			case DWEMER: return 20.0f;
			default: return 1.5f;
		}
	}

}
