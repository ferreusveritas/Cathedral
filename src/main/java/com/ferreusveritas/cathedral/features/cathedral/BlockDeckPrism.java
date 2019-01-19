package com.ferreusveritas.cathedral.features.cathedral;

import java.util.Optional;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.common.blocks.MimicProperty;
import com.ferreusveritas.cathedral.common.blocks.MimicProperty.IMimic;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDeckPrism extends Block implements ITileEntityProvider, IMimic {
	
	public static final String name = "deckprism";
	
	public BlockDeckPrism() {
		super(Material.GLASS);
		setRegistryName(new ResourceLocation(ModConstants.MODID, name));
		setUnlocalizedName(name);
		setCreativeTab(CathedralMod.tabCathedral);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[] {MimicProperty.MIMIC});
	}
	
	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess access, BlockPos pos) {
		return state instanceof IExtendedBlockState ? ((IExtendedBlockState)state).withProperty(MimicProperty.MIMIC, getMimic(access, pos)) : state;
	}
	
	@Override
	public IBlockState getMimic(IBlockAccess access, BlockPos pos) {
		Optional<TileEntityDeckPrism> tile = getDeckPrismTileEntity(access, pos);
		if(tile.isPresent()) {
			return tile.get().getBaseBlock();
		}
		
		return Blocks.STONE.getDefaultState(); //Default to stone
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityDeckPrism();
	}
	
	public EnumDyeColor getPrismColor(IBlockAccess access, BlockPos pos) {
		Optional<TileEntityDeckPrism> prismTile = getDeckPrismTileEntity(access, pos);
		return prismTile.isPresent() ? prismTile.get().getGlassColor() : null;
	}
	
	public void setPrismColor(World world, BlockPos pos, EnumDyeColor color) {
		Optional<TileEntityDeckPrism> prismTile = getDeckPrismTileEntity(world, pos);
		if(prismTile.isPresent()) {
			prismTile.get().setGlassColor(color);
		}
	}
	
	public IBlockState getBaseBlock(IBlockAccess access, BlockPos pos) {
		Optional<TileEntityDeckPrism> prismTile = getDeckPrismTileEntity(access, pos);
		return prismTile.isPresent() ? prismTile.get().getBaseBlock() : Blocks.AIR.getDefaultState();
	}
	
	public void setBaseBlock(World world, BlockPos pos, IBlockState baseBlock) {
		Optional<TileEntityDeckPrism> prismTile = getDeckPrismTileEntity(world, pos);
		if(prismTile.isPresent()) {
			prismTile.get().setBaseBlock(baseBlock);
		}
	}
	
	protected Optional<TileEntityDeckPrism> getDeckPrismTileEntity(IBlockAccess access, BlockPos pos) {
		TileEntity tile = access.getTileEntity(pos);
		if(tile instanceof TileEntityDeckPrism) {
			return Optional.of((TileEntityDeckPrism)tile);
		}
		
		return Optional.empty();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess access, BlockPos pos) {
		return getBaseBlock(access, pos).getBoundingBox(access, pos);
	}
	
	@Override
	public int getLightValue(IBlockState state, IBlockAccess access, BlockPos pos) {
		//IBlockState baseBlock = getBaseBlock(access, pos);
		//System.out.println("getLightValue: " + pos + " " + baseBlock);
		return 0;//baseBlock.getBlock().getLightValue(baseBlock);
	}
	
	@Override
	public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
		return 0;
	}
	
	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.SOLID;//super.canRenderInLayer(state, layer);//true;//For troubleshooting
	}
	
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return super.getBlockLayer();
    }
    
    @Override
    public boolean isTranslucent(IBlockState state) {
    	return false;
    }
    
	@Override
	public boolean isFullCube(IBlockState state) {
		return true;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;//super.getRenderType(state);
	}
    
    @Override
    protected boolean canSilkHarvest() {
    	return true;
    }
    
    @Override
    public float getExplosionResistance(Entity exploder) {
    	return 10.0f;
    }
    
    @Override
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
    	return getBaseBlock(world, pos).getBlock().getExplosionResistance(exploder);
    }
    
    @Override
    public float getBlockHardness(IBlockState blockState, World world, BlockPos pos) {
    	return getBaseBlock(world, pos).getBlockHardness(world, pos);
    }
    
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    	super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }
}
