package com.ferreusveritas.cathedral.features.cathedral;

import com.ferreusveritas.cathedral.CathedralMod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.Properties;

public class BlockPillar extends Block {
	
	public static final String name = "pillar";
	
	public static final IUnlistedProperty CONNECTIONS[] = { 
		new Properties.PropertyAdapter<PillarConnectionType>(PropertyEnum.<PillarConnectionType>create(EnumFacing.DOWN.getName(), PillarConnectionType.class)),
		new Properties.PropertyAdapter<PillarConnectionType>(PropertyEnum.<PillarConnectionType>create(EnumFacing.UP.getName(), PillarConnectionType.class)),
		new Properties.PropertyAdapter<PillarConnectionType>(PropertyEnum.<PillarConnectionType>create(EnumFacing.NORTH.getName(), PillarConnectionType.class)),
		new Properties.PropertyAdapter<PillarConnectionType>(PropertyEnum.<PillarConnectionType>create(EnumFacing.SOUTH.getName(), PillarConnectionType.class)),
		new Properties.PropertyAdapter<PillarConnectionType>(PropertyEnum.<PillarConnectionType>create(EnumFacing.WEST.getName(), PillarConnectionType.class)),
		new Properties.PropertyAdapter<PillarConnectionType>(PropertyEnum.<PillarConnectionType>create(EnumFacing.EAST.getName(), PillarConnectionType.class))
	};
	
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
		IProperty[] listedProperties = { EnumMaterial.VARIANT };
		return new ExtendedBlockState(this, listedProperties, CONNECTIONS);
	}
	
	/** Convert the given metadata into a BlockState for this Block */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(EnumMaterial.VARIANT, EnumMaterial.byMetadata(meta));
	}
	
	/** Convert the BlockState into the correct metadata value */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(EnumMaterial.VARIANT).getMetadata();
	}
	
	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (state instanceof IExtendedBlockState) {
			IExtendedBlockState retval = (IExtendedBlockState) state;
			
			for (EnumFacing dir : EnumFacing.VALUES) {
				retval = retval.withProperty(CONNECTIONS[dir.getIndex()], getPillarConnectionType(world, pos, dir));
			}
			return retval;
		}
		
		return state;
	}
	
	private PillarConnectionType getPillarConnectionType(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		BlockPos other = pos.offset(facing);
		IBlockState otherState = world.getBlockState(other);
		Block otherBlock = otherState.getBlock();
		
		if(otherBlock instanceof BlockPillar) {
			return PillarConnectionType.Pillar;//Pillars always connect to pillars
		}
		
		if(facing.getAxis() == Axis.Y) {
			return PillarConnectionType.Solid;//Pillars only connect to pillars on the up/down axis
		} else {
			if(otherBlock instanceof BlockRailing) {
				return PillarConnectionType.Rail;
			}
		}
		
		if(isExceptionBlockForAttachWithPiston(otherBlock)) {
			return PillarConnectionType.None;
		}

		BlockFaceShape blockfaceshape = otherState.getBlockFaceShape(world, pos, facing.getOpposite());
		
		if(blockfaceshape == BlockFaceShape.SOLID) {
			return PillarConnectionType.Solid;
		}

		if(blockfaceshape == BlockFaceShape.MIDDLE_POLE_THIN) {
			return PillarConnectionType.Pane;
		}
		
		return PillarConnectionType.None;
	}
	
	@Override
	public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		BlockPos otherPos = pos.offset(facing);
		IBlockState otherState = world.getBlockState(otherPos);
		BlockFaceShape faceShape = otherState.getBlockFaceShape(world, otherPos, facing.getOpposite());
		return faceShape == BlockFaceShape.MIDDLE_POLE_THIN;
	}
	
	protected static boolean isExceptionBlockForAttachWithPiston(Block block) {
		
		if(block instanceof BlockGlass || block instanceof BlockStainedGlass) {
			return false;
		}
		
		return Block.isExceptBlockForAttachWithPiston(block)
				|| block == Blocks.BARRIER
				|| block == Blocks.MELON_BLOCK
				|| block == Blocks.PUMPKIN
				|| block == Blocks.LIT_PUMPKIN;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return face.getAxis() == Axis.Y ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(EnumMaterial.VARIANT).getMetadata();
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
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	
	
	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
		return blockState.getValue(EnumMaterial.VARIANT).getHardness();
	}
	
	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		return world.getBlockState(pos).getValue(EnumMaterial.VARIANT).getExplosionResistance(exploder);
	}
	
}
