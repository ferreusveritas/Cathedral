package com.ferreusveritas.cathedral.features.cathedral;

import com.ferreusveritas.cathedral.CathedralMod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGargoyle extends Block {

	public static final String name = "gargoyle";

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<EnumMount> MOUNT = PropertyEnum.<EnumMount>create("mount", EnumMount.class);
	
	public BlockGargoyle() {
		this(name);
	}
	
	public BlockGargoyle(String name) {
		super(Material.ROCK);
		setRegistryName(name);
		setUnlocalizedName(name);
		setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(MOUNT, EnumMount.FLOOR));
		//setBlockBounds(0.125F, 0F, 0.125F, 0.875F, 1.0F, 0.875F);
		//setStepSound(soundTypeStone);
		setCreativeTab(CathedralMod.tabCathedral);
		setHardness(1.0f);
		setResistance(3.0f);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FACING, MOUNT});
	}
	
	/** Convert the given metadata into a BlockState for this Block */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		int facingNum = meta & 3;
		int mountNum = (meta >> 2) & 3;
				
		return getDefaultState().withProperty(FACING, EnumFacing.HORIZONTALS[facingNum]).withProperty(MOUNT, EnumMount.values()[mountNum]);
	}
	
	/** Convert the BlockState into the correct metadata value */
	@Override
	public int getMetaFromState(IBlockState state) {

		int facingNum = 0;
		
		switch(state.getValue(FACING)) {
			case NORTH: facingNum = 0; break;
			case EAST: facingNum = 1; break;
			case SOUTH: facingNum = 2; break;
			case WEST: facingNum = 3; break;
			case UP: 
			case DOWN:
			default: facingNum = 0; break;
		}
		
		return (state.getValue(MOUNT).ordinal() << 2) | facingNum;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        IBlockState iblockstate = super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
        
        switch(facing) {
			case UP:	iblockstate = iblockstate.withProperty(MOUNT, EnumMount.FLOOR).withProperty(FACING, placer.getHorizontalFacing().getOpposite()); break;
			case DOWN:	iblockstate = iblockstate.withProperty(MOUNT, EnumMount.CEILING).withProperty(FACING, placer.getHorizontalFacing().getOpposite()); break;
			default: 	iblockstate = iblockstate.withProperty(MOUNT, EnumMount.WALL).withProperty(FACING, facing); break;
        }
        
		return iblockstate;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return true;
	}
	
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	/** Used to determine ambient occlusion and culling when rebuilding chunks for render */
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
	
	/*	

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer) {
		return true;
	}

	*/

    public static enum EnumMount implements IStringSerializable {
    	CEILING("ceiling"),
    	WALL("wall"),
    	FLOOR("floor"),
    	FENCE("fence");

    	String name;
    	
    	private EnumMount(String name) {
    		this.name = name;
		}
    	
		@Override
		public String getName() {
			return name;
		}
    }
	
}
