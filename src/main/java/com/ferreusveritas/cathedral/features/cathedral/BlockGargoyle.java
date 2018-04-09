package com.ferreusveritas.cathedral.features.cathedral;

import com.ferreusveritas.cathedral.CathedralMod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
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
        iblockstate = iblockstate.withProperty(FACING, placer.getHorizontalFacing().getOpposite());
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
	
	/*	

	// Called when the block is placed in the world.
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack){
		int l = MathHelper.floor_double((360.0F - entity.rotationYaw) * 8.0F / 360.0F + 4.5D) & 7;

		EntityGargoyle garg = (EntityGargoyle)world.getTileEntity(x,  y,  z);
		if(garg != null){
			garg.setDirection(l);
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float px, float py, float pz){
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < 11; i++){
			list.add(new ItemStack(item, 1, i));
		}
	}

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

	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side){
		return false;
	}

	@Override
	public boolean isOpaqueCube(){
		return false;
	}

	@Override
	public boolean renderAsNormalBlock(){
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_) {
		return new EntityGargoyle();
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player){

		TileEntity tileentity = world.getTileEntity(x, y, z);

		if (tileentity instanceof EntityGargoyle) {
			EntityGargoyle gargoyle = (EntityGargoyle)tileentity;
			ItemStack stack = new ItemStack(this, 1, gargoyle.getMaterial());
			return stack;
		}

		return null;
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest){
		if(willHarvest){
			return true;
		}
		return super.removedByPlayer(world, player, x, y, z, willHarvest);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta){
		super.harvestBlock(world, player, x, y, z, meta);
		world.setBlockToAir(x, y, z);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		TileEntity tileentity = world.getTileEntity(x, y, z);

		if (tileentity instanceof EntityGargoyle) {
			EntityGargoyle gargoyle = (EntityGargoyle)tileentity;

			int material = gargoyle.getMaterial();

			ItemStack itemstack = new ItemStack(this, 1, material);
			//itemstack.setTagCompound(new NBTTagCompound());
            //NBTTagCompound nbttagcompound = new NBTTagCompound();
            //itemstack.getTagCompound().setInteger("Material", material);
			ret.add(itemstack);
		}

		return ret;
	}

	//Experimental
	
	IIcon sideIcon;
	
	@Override
	public int getRenderType() {
		return RendererGargoyle.id;
	}

	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side)
    {
		TileEntity tileentity = blockAccess.getTileEntity(x, y, z);
		
		if (tileentity instanceof EntityGargoyle) {
			EntityGargoyle gargoyle = (EntityGargoyle)tileentity;

			int material = gargoyle.getMaterial();
			
			if(material < icons.length){
				return icons[material];
			}
		}

		return Blocks.cobblestone.getBlockTextureFromSide(0);
    }
	
	@SideOnly(Side.CLIENT)
	public IIcon getIconForMaterial(int material){
		if(material >= 0 && material < icons.length){
			return icons[material];
		}
		return Blocks.cobblestone.getBlockTextureFromSide(0);
	}

	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {

		int i = 0;
		for(String tex: Gargoyle.types){
			icons[i++] = register.registerIcon(Cathedral.MODID + ":" + "gargoyle-" + tex);
		}
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
