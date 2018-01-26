package com.ferreusveritas.cathedral.features.cathedral;

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
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;

public class BlockRailing extends Block {

	public static final String name = "railing";
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
	public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.<EnumType>create("variant", EnumType.class);
	
	Block chiselSandstone;
	Block chiselFantasy;
	Block chiselEndstone;
	//Block chiselObsidian;
	Block chiselMarble;
	Block chiselLimestone;
	
	public BlockRailing() {
		this(name);
	}
	
	public BlockRailing(String name) {
		super(Material.ROCK);
        setUnlocalizedName(name);
        setRegistryName(name);
        setDefaultState(getDefaultState().withProperty(VARIANT, EnumType.STONE));
        setCreativeTab(CathedralMod.tabCathedral);
	}

	@Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {UP, NORTH, EAST, WEST, SOUTH, VARIANT});
    }
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		boolean n =  canWallConnectTo(world, pos, EnumFacing.NORTH);
		boolean e = canWallConnectTo(world, pos, EnumFacing.EAST);
		boolean s = canWallConnectTo(world, pos, EnumFacing.SOUTH);
		boolean w = canWallConnectTo(world, pos, EnumFacing.WEST);
		boolean flag4 = n && !e && s && !w || !n && e && !s && w;
		return state
			.withProperty(UP, Boolean.valueOf(!flag4 || !world.isAirBlock(pos.up())))
			.withProperty(NORTH, Boolean.valueOf(n))
			.withProperty(EAST, Boolean.valueOf(e))
			.withProperty(SOUTH, Boolean.valueOf(s))
			.withProperty(WEST, Boolean.valueOf(w));
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

    private boolean canWallConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
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
	
	
	public static enum EnumType implements IStringSerializable {
		
		 STONE         (0, "stone"),
		 SANDSTONE     (1, "sandstone"),
		 NETHERBRICK   (2, "netherbrick"),
		 OBSIDIAN      (3, "obsidian"),
		 DWEMER        (4, "dwemer"),
		 PACKEDICE     (5, "packedice"),
		 ENDSTONE      (6, "endstone"),
		 BASALT        (7, "basalt"),
		 MARBLE        (8, "marble"),
		 FANTASY       (9, "fantasy"),
		 LIMESTONE     (10, "limestone"),
		 QUARTZ        (11, "quartz"),
		 SNOW          (12, "snow");
		
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
	
	/*
	public void addRecipes() {
		chiselSandstone = GameRegistry.findBlock("chisel", "sandstone");
		chiselFantasy = GameRegistry.findBlock("chisel", "fantasyblock2");
		chiselEndstone = GameRegistry.findBlock("chisel", "end_Stone");
		//chiselObsidian = GameRegistry.findBlock("chisel", "obsidian");
		chiselMarble = GameRegistry.findBlock("chisel", "marble");
		chiselLimestone = GameRegistry.findBlock("chisel", "limestone");
		
    	GameRegistry.addRecipe(new ItemStack(this, 8, 0), "XXX", "X X", 'X', Blocks.stone);//1 Stone
    	GameRegistry.addRecipe(new ItemStack(this, 8, 1), "XXX", "X X", 'X', Blocks.sandstone);//2 Sandstone
    	GameRegistry.addRecipe(new ItemStack(this, 8, 2), "XXX", "X X", 'X', Blocks.nether_brick);//3 Netherbrick
    	GameRegistry.addRecipe(new ItemStack(this, 8, 3), "XXX", "X X", 'X', Blocks.obsidian);//4 Obsidian
    	GameRegistry.addRecipe(new ItemStack(this, 8, 4), "XXX", "X X", 'X', new ItemStack(Dwemer.dwemerBlock, 1, 14));//5 Dwemer
    	GameRegistry.addRecipe(new ItemStack(this, 8, 5), "XXX", "X X", 'X', Blocks.packed_ice);//6 Compressed Ice
    	GameRegistry.addRecipe(new ItemStack(this, 8, 6), "XXX", "X X", 'X', Blocks.end_stone);//7 End Stone
    	GameRegistry.addRecipe(new ItemStack(this, 8, 7), "XXX", "X X", 'X', new ItemStack(Basalt.basaltBase, 1, 3));//8 Basalt
    	GameRegistry.addRecipe(new ItemStack(this, 8, 8), "XXX", "X X", 'X', new ItemStack(chiselMarble, 1, 0));//9 Marble
    	GameRegistry.addRecipe(new ItemStack(this, 8, 9), "XXX", "X X", 'X', new ItemStack(chiselFantasy, 1, 0));//10 Fantasy
    	GameRegistry.addRecipe(new ItemStack(this, 8, 10), "XXX", "X X", 'X', new ItemStack(chiselLimestone, 1, 0));//11 Limestone
    	GameRegistry.addRecipe(new ItemStack(this, 8, 11), "XXX", "X X", 'X', new ItemStack(Blocks.quartz_block, 1, 0));//12 Quartz
    	GameRegistry.addRecipe(new ItemStack(this, 8, 12), "XXX", "X X", 'X', new ItemStack(Blocks.snow));//13 Snow

	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata) {
    	switch(metadata) {
    		case 0: return Extras.extraStone.getBlockTextureFromSide(side);
    		case 1: return chiselSandstone.getIcon(side, 12);
    		case 2: return Blocks.nether_brick.getBlockTextureFromSide(side);
    		case 3: return Blocks.obsidian.getBlockTextureFromSide(side); //chiselObsidian.getIcon(side, 0);
    		case 4: return Dwemer.dwemerBlock.getIcon(side, 14);//Basalt
    		case 5: return Blocks.packed_ice.getBlockTextureFromSide(side); 
    		case 6: return chiselEndstone.getIcon(side, 13);
    		case 7: return Basalt.basaltBlock.getIcon(side, 1);//Basalt
    		case 8: return chiselMarble.getIcon(side, 5);//Marble
    		case 9: return chiselFantasy.getIcon(side, 0);//Fantasy
    		case 10: return chiselLimestone.getIcon(side, 0);//Limestone
    		case 11: return Blocks.quartz_block.getIcon(side, 0);//Quartz
    		case 12: return Blocks.snow.getBlockTextureFromSide(0);//Snow

    		default: return Blocks.stone.getBlockTextureFromSide(side);
    	}  
    }
	
	@Override
    public int getRenderType() {
        return RendererStoneRailing.renderId;
    }
	
    //
    // returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
    @Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs p_149666_2_, List list) {
    	for(int i = 0; i < 13; i++){
    		list.add(new ItemStack(item, 1, i));
    	}
    }

	
	@Override
    public float getBlockHardness(World world, int x, int y, int z) {

		int meta = world.getBlockMetadata(x, y, z);
		float hardness;
		
		switch (meta) {
			case 0: hardness = 1.5F; break;//Stone
			case 1: hardness = 0.8F; break;//Sandstone
			case 2: hardness = 2.0F; break;//Netherbrick
			case 3: hardness = 50.0F; break;//Obsidian
			case 4: hardness = 2.5F; break;//Dwemer
			case 5: hardness = 0.5F; break;//Packed Ice
			case 6: hardness = 3.0F; break;//End Stone
			case 7: hardness = 2.5f; break; //Basalt
			case 8: hardness = 1.5f; break; //Marble
			case 9: hardness = 2.0f; break;//Fantasy Block
			case 10: hardness = 2.0f; break;//Limestone
			case 11: hardness = 0.8F; break;//Quartz
			case 12: hardness = 0.2F; break;//Snow

			default: hardness = 1.5f; break;//Stone
		}
		
		return hardness;
	}

	@Override
    public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {

		int meta = world.getBlockMetadata(x, y, z);
		float resist;
		
		switch (meta) {
			case 0: resist = Blocks.stone.getExplosionResistance(par1Entity); break;
			case 1: resist = Blocks.sandstone.getExplosionResistance(par1Entity); break;
			case 2: resist = Blocks.nether_brick.getExplosionResistance(par1Entity); break;
			case 3: resist = Blocks.obsidian.getExplosionResistance(par1Entity); break;
			case 4: resist = Dwemer.dwemerBlock.getExplosionResistance(par1Entity); break;
			case 5: resist = Blocks.packed_ice.getExplosionResistance(par1Entity); break;
			case 6: resist = Blocks.end_stone.getExplosionResistance(par1Entity); break;
			case 7: resist = 20f; break; //Basalt
			case 8: resist = 10f; break; //Marble
			case 9: resist = chiselFantasy.getExplosionResistance(par1Entity); break;
			case 10: resist = chiselLimestone.getExplosionResistance(par1Entity); break;
			case 11: resist = Blocks.quartz_block.getExplosionResistance(par1Entity); break;
			case 12: resist = Blocks.snow.getExplosionResistance(par1Entity); break;
			
			default: resist = Blocks.stone.getExplosionResistance(par1Entity); break;
		}
		
		return resist;
    }	
	
	@Override
    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {

        boolean w = this.canConnectWallTo(blockAccess, x + 1, y, z);
        boolean e = this.canConnectWallTo(blockAccess, x - 1, y, z);
        boolean n = this.canConnectWallTo(blockAccess, x, y, z + 1);
        boolean s = this.canConnectWallTo(blockAccess, x, y, z - 1);
        boolean ns = s && n && !e && !w;
        boolean ew = !s && !n && e && w;
        boolean up = this.canConnectWallTo(blockAccess, x, y + 1, z);
        boolean railabove = blockAccess.getBlock(x, y + 1, z) == this;
        boolean railbelow = blockAccess.getBlock(x, y - 1, z) == this;

        if(!w && !e && !s && !n && up){
            this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 1.0f, 0.75f);//Make Post
        }
        else if(!w && !e && !s && !n && railbelow && !railabove) {
            this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);//Make a post cap and that's it

        }
        else if ((ew || ns) && !railabove) {//Just a straight section of railing
            if (ns) {
                this.setBlockBounds(0.3125f, 0.0f, 0.0f, 0.6875f, 1.0f, 1.0f);
            }
            else { //ew
                this.setBlockBounds(0.0f, 0.0f, 0.3125f, 1.0f, 1.0f, 0.6875f);
            }
        }
        else{
        	
        	float xlow = e ? 0.0f : 0.25f;
        	float xhigh = w ? 1.0f : 0.75f;
        	float zlow = s ? 0.0f : 0.25f;
        	float zhigh = n ? 1.0f : 0.75f;
			
            this.setBlockBounds(xlow, 0.0f, zlow, xhigh, 1.0f, zhigh);
		}
    	
    }
	
	@Override
    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side){
		return side == ForgeDirection.UP;
	}

	@Override
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
    	return false;
    }

	@Override
    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable){
    	return false;
    }

    
	@Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockaccess, int x, int y, int z, int side) {
        return true;
    }
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        this.maxY = this.maxY > 0.75D ? 1.5D : this.maxY;
        
        return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	*/
}
