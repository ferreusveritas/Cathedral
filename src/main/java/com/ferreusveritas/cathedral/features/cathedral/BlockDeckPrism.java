package com.ferreusveritas.cathedral.features.cathedral;

import java.util.Optional;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.common.blocks.MimicProperty;
import com.ferreusveritas.cathedral.common.blocks.MimicProperty.IMimic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleDigging;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean addDestroyEffects(World world, BlockPos pos, ParticleManager manager) {
		IBlockState state = Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, getPrismColor(world, pos));
		
		TextureAtlasSprite texture = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state);
		
		if (!state.getBlock().isAir(state, world, pos)) {
			for (int x = 0; x < 4; ++x) {
				for (int y = 0; y < 4; ++y) {
					for (int z = 0; z < 4; ++z) {
						double delX = ((double) x + 0.5D) / 4.0D;
						double delY = ((double) y + 0.5D) / 4.0D;
						double delZ = ((double) z + 0.5D) / 4.0D;
						
						ParticleDigging particle = (ParticleDigging) manager.spawnEffectParticle(EnumParticleTypes.BLOCK_CRACK.getParticleID(), (double) pos.getX() + delX, (double) pos.getY() + delY, (double) pos.getZ() + delZ, delX - 0.5D, delY - 0.5D, delZ - 0.5D, Block.getStateId(state));
						if (particle != null) {
							particle.setBlockPos(pos).setParticleTexture(texture);
						}
					}
				}
			}
		}
		
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean addHitEffects(IBlockState state, World world, RayTraceResult target, ParticleManager manager) {
		
		BlockPos pos = target.getBlockPos();
		if(pos != BlockPos.ORIGIN) {
			IBlockState particlesState = Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, getPrismColor(world, pos));
			
			if(target.sideHit != null && target.sideHit.getAxis().isHorizontal()) {
				particlesState = getBaseBlock(world, pos);
			} else {
				double xx = Math.abs(target.hitVec.x % 1.0);
				double zz = Math.abs(target.hitVec.z % 1.0);
				if(xx < 0.3125 || xx > 0.6875 || zz < 0.3125 || zz > 0.6875) {
					particlesState = getBaseBlock(world, pos);
				}
			}
			
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			AxisAlignedBB aabb = state.getBoundingBox(world, pos);
			double xpos = (double)x + world.rand.nextDouble() * (aabb.maxX - aabb.minX - 0.2) + 0.1 + aabb.minX;
			double ypos = (double)y + world.rand.nextDouble() * (aabb.maxY - aabb.minY - 0.2) + 0.1 + aabb.minY;
			double zpos = (double)z + world.rand.nextDouble() * (aabb.maxZ - aabb.minZ - 0.2) + 0.1 + aabb.minZ;
			
			switch(target.sideHit) {
				case DOWN:  ypos = (double)y + aabb.minY - 0.1; break;
				case UP:    ypos = (double)y + aabb.maxY + 0.1; break;
				case NORTH: zpos = (double)z + aabb.minZ - 0.1; break;
				case SOUTH: zpos = (double)z + aabb.maxZ + 0.1; break;
				case WEST:  xpos = (double)x + aabb.minX - 0.1; break;
				case EAST:  xpos = (double)x + aabb.maxX + 0.1; break;
			}
			
			ParticleDigging particle = (ParticleDigging) manager.spawnEffectParticle(EnumParticleTypes.BLOCK_CRACK.getParticleID(), xpos, ypos, zpos, 0.0, 0.0, 0.0, Block.getStateId(particlesState));
			if(particle != null) {
				particle.setBlockPos(pos).multiplyVelocity(0.2f).multipleParticleScaleBy(0.6F);
			}
		}
		
		return true;
	}
	
	@Override
	public boolean addLandingEffects(IBlockState state, WorldServer world, BlockPos pos, IBlockState iblockstate, EntityLivingBase entity, int numberOfParticles) {
		state = Blocks.STAINED_GLASS.getDefaultState().withProperty(BlockStainedGlass.COLOR, getPrismColor(world, pos));
		world.spawnParticle(EnumParticleTypes.BLOCK_DUST, entity.posX, entity.posY, entity.posZ, numberOfParticles, 0.0D, 0.0D, 0.0D, 0.15D, Block.getStateId(state));
		return true;
	}
}
