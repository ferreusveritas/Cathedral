package com.ferreusveritas.cathedral.blocks;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import team.chisel.api.block.ICarvable;
import team.chisel.api.block.VariationData;


public class BlockCarvableGlass extends BlockGlass implements ICarvable {

	public BlockCarvableGlass() {
		super(Material.GLASS, false);
	}

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalVariations() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public VariationData getVariationData(int variation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VariationData[] getVariations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getVariationIndex(IBlockState state) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	public CarvableHelper carverHelper;
	private boolean isAlpha = false;
	private CTM ctm = CTM.getInstance();

	public BlockCarvableGlass() {
		super(Material.glass, false);

		carverHelper = new CarvableHelper(this);
		setCreativeTab(ChiselTabs.tabOtherChiselBlocks);
	}

	public BlockCarvableGlass setStained(boolean a) {
		this.isAlpha = a;
		return this;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isNormalCube() {
		return true;
	}

	@Override
	public int getRenderType() {
		return ClientUtils.renderCTMId;
	}

	@Override
	public IIcon getIcon(int side, int metadata) {
		return carverHelper.getIcon(side, metadata);
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		ForgeDirection face = ForgeDirection.getOrientation(side).getOpposite();
		int meX = x + face.offsetX;
		int meY = y + face.offsetY;
		int meZ = z + face.offsetZ;
		Block block = world.getBlock(meX, meY, meZ);
		int meta = world.getBlockMetadata(meX, meY, meZ);
		Block otherBlock = ctm.getBlockOrFacade(world, x, y, z, face.ordinal());
		int otherMeta = ctm.getBlockOrFacadeMetadata(world, x, y, z, face.ordinal());
		return block != otherBlock || meta != otherMeta;
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
		carverHelper.registerBlockIcons(Cathedral.MODID, this, register);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
		carverHelper.registerSubBlocks(this, tabs, list);
	}

	@Override
	public IVariationInfo getManager(IBlockAccess world, int x, int y, int z, int metadata) {
		return carverHelper.getVariation(metadata);
	}

	@Override
	public IVariationInfo getManager(int meta) {
		return carverHelper.getVariation(meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return isAlpha ? 1 : 0;
	}
	*/
}
