package com.ferreusveritas.cathedral.features.cathedral;

import java.util.Optional;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.common.blocks.MimicProperty;
import com.ferreusveritas.cathedral.common.blocks.MimicProperty.IMimic;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockDeckPrism extends Block implements ITileEntityProvider, IMimic {
	
	public static final String name = "deckprism";
	
	public static final IUnlistedProperty<EnumDyeColor> COLOR = new IUnlistedProperty<EnumDyeColor>() {
		@Override
		public String getName() {
			return "color";
		}

		@Override
		public boolean isValid(EnumDyeColor value) {
			return value != null;
		}

		@Override
		public Class<EnumDyeColor> getType() {
			return EnumDyeColor.class;
		}

		@Override
		public String valueToString(EnumDyeColor value) {
			return value.getDyeColorName();
		}
	};
	
	public BlockDeckPrism() {
		super(Material.GLASS);
		setHardness(1.5f);
		setResistance(10f);
		setSoundType(SoundType.GLASS);
		setRegistryName(new ResourceLocation(ModConstants.MODID, name));
		setUnlocalizedName(name);
		setCreativeTab(CathedralMod.tabCathedral);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[0], new IUnlistedProperty[] {MimicProperty.MIMIC, COLOR});
	}
	
	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess access, BlockPos pos) {
		return state instanceof IExtendedBlockState ? 
			((IExtendedBlockState)state)
				.withProperty(MimicProperty.MIMIC, getMimic(access, pos))
				.withProperty(COLOR, getPrismColor(access, pos))
			: state;
	}
	
	@Override
	public IBlockState getMimic(IBlockAccess access, BlockPos pos) {
		return getDeckPrismTileEntity(access, pos).map(t -> t.getBaseBlock()).orElseGet(() -> Blocks.STONE.getDefaultState());//Default to stone in cases where the tile entity is AWOL
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityDeckPrism();
	}
	
	public EnumDyeColor getPrismColor(IBlockAccess access, BlockPos pos) {
		return getDeckPrismTileEntity(access, pos).map(t -> t.getGlassColor()).orElse(EnumDyeColor.WHITE);
	}
	
	public void setPrismColor(World world, BlockPos pos, EnumDyeColor color) {
		getDeckPrismTileEntity(world, pos).ifPresent(pt -> pt.setGlassColor(color));
	}
	
	public IBlockState getBaseBlock(IBlockAccess access, BlockPos pos) {
		return getDeckPrismTileEntity(access, pos).map(pt -> pt.getBaseBlock()).orElse(Blocks.AIR.getDefaultState());
	}
	
	public void setBaseBlock(World world, BlockPos pos, IBlockState baseBlock) {
		getDeckPrismTileEntity(world, pos).ifPresent(pt -> pt.setBaseBlock(baseBlock));
	}
	
	protected Optional<TileEntityDeckPrism> getDeckPrismTileEntity(IBlockAccess access, BlockPos pos) {
		TileEntity tile = access.getTileEntity(pos);
		return tile instanceof TileEntityDeckPrism ? Optional.of((TileEntityDeckPrism)tile) : Optional.empty();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess access, BlockPos pos) {
		return getBaseBlock(access, pos).getBoundingBox(access, pos);
	}
	
	@Override
	public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
		return 0;//This is the entire point of this block
	}
	
	@Override
	public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
		return layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.SOLID;//The donut is rendered in SOLID.  The prism itself is translucent
	}
	
	@Override
	public boolean isTranslucent(IBlockState state) {
		return false;//Although this block has translucent parts we need this block to render with ambient occlusion
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;//If we don't do this then when a players head is in the empty part of a slab then it messes up
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;//Although this block has translucent parts we need this block to render with ambient occlusion and to disable culling
	}
	
	@Override
	protected boolean canSilkHarvest() {
		return false;//No reason to silk harvest this block as the prism is directly obtainable
	}
	
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		int meta = getPrismColor(world, pos).getMetadata();
		world.setBlockState(pos, getBaseBlock(world, pos));
		Block.spawnAsEntity(world, pos, new ItemStack(Item.getItemFromBlock(this), 1, meta));
		return false;
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for(EnumDyeColor color: EnumDyeColor.values()) {
			items.add(new ItemStack(this, 1, color.getMetadata()));
		}
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		int meta = getPrismColor(world, pos).getMetadata();
		return new ItemStack(this, 1, meta);
	}
	
}
