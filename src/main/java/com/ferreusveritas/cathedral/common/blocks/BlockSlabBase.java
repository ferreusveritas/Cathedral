package com.ferreusveritas.cathedral.common.blocks;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.item.ItemStack;

public class BlockSlabBase extends BlockSlab {

	public BlockSlabBase(Material material, String name) {
		super(material);
		setRegistryName(name);
		setUnlocalizedName(name);
	}

	@Override
	public String getUnlocalizedName(int meta) {
		 return super.getUnlocalizedName() + "." + BlockStoneSlab.EnumType.byMetadata(meta).getUnlocalizedName();
	}

	@Override
	public boolean isDouble() {
		return false;
	}

	@Override
	public IProperty<?> getVariantProperty() {
		return null;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		return null;
	}

	/*
	public ArrayList<BaseBlockDef> baseBlocks;
	public int count;
	
	public BlockGenericSlab(ArrayList<BaseBlockDef> baseBlocks) {
		super(baseBlocks.get(0).block.getMaterial());//TODO handle materials better
		opaque = true;

		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);

		this.baseBlocks = baseBlocks;
		count = baseBlocks.size();
		
		setCreativeTab(Cathedral.tabCathedral);
	}
	
    
    // returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
    @Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs p_149666_2_, List list) {
    	for(int i = 0; i < count; i++){
    		list.add(new ItemStack(item, 1, i));
    	}
    }

	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int x, int y, int z) {

		int metadata = par1IBlockAccess.getBlockMetadata(x, y, z);

		boolean isBottom = (metadata & 8) == 0;
		
		if (isBottom) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		} else {
			setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addCollisionBoxesToList(World par1World, int x, int y, int z, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {
		setBlockBoundsBasedOnState(par1World, x, y, z);
		super.addCollisionBoxesToList(par1World, x, y, z, par5AxisAlignedBB, par6List, par7Entity);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		int select = metadata & 7;
		return baseBlocks.get(select).block.getIcon(side, baseBlocks.get(select).metaData);
	}

    @Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
    }
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z){
		int select = world.getBlockMetadata(x, y, z) & 7;
		return baseBlocks.get(select).hardness;
	}

	@Override
	public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ){
		int select = world.getBlockMetadata(x, y, z) & 7;
		return baseBlocks.get(select).resistance;
	}
	
	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {

		int metadata = world.getBlockMetadata(x, y, z);
		boolean isBottom = (metadata & 8) == 0;		
		
		return !isBottom && (side == UP);
	}
	*/
}
