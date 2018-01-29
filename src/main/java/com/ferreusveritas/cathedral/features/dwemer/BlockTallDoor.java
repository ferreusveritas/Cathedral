package com.ferreusveritas.cathedral.features.dwemer;

import com.ferreusveritas.cathedral.common.blocks.BlockBase;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class BlockTallDoor extends BlockBase {

	public BlockTallDoor(Material materialIn, String name) {
		super(materialIn, name);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {BlockBase.META});
	}
	
	/** Convert the given metadata into a BlockState for this Block */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(BlockBase.META, meta);
	}
	
	/** Convert the BlockState into the correct metadata value */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(BlockBase.META);
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(BlockBase.META);
	}
	
	/*
    @SideOnly(Side.CLIENT)
    private IIcon[] upperDoorIcon;
    @SideOnly(Side.CLIENT)
    private IIcon[] middleDoorIcon;
    @SideOnly(Side.CLIENT)
    private IIcon[] lowerDoorIcon;
    @SideOnly(Side.CLIENT)
    private IIcon sideDoorIcon;
    @SideOnly(Side.CLIENT)
    private IIcon topDoorIcon;
    
    public Item droppedItem;
    
    protected static final int DirW = 0;
    protected static final int DirN = 1;
    protected static final int DirE = 2;
    protected static final int DirS = 3;
    
    protected static final int Bottom = 0;
    protected static final int Top = 1;
    protected static final int North = 2;
    protected static final int South = 3;
    protected static final int West = 4;
    protected static final int East = 5;
    
    protected static final int TopSect = 3;
    protected static final int MidSect = 2;
    protected static final int BotSect = 1;
    
    public BlockTallDoor() {
        super(Material.iron);
        float half = 0.5F;
        float full = 1.0F;
        this.setBlockBounds(0.5F - half, 0.0F, 0.5F - half, 0.5F + half, full, 0.5F + half);
    }
    
    public void setDroppedItem(Item dropped){
    	this.droppedItem = dropped;
    }
    
    // Gets the block's texture. Args: side, meta
    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return this.lowerDoorIcon[0];
    }

    @Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
    	
    	if(side == 0 || side == 1){
    	    return this.topDoorIcon;
    	}
    	
        int fullMeta = this.getFullMetadata(blockAccess, x, y, z);
        int dir = (fullMeta >> 2) & 3;
        boolean state = (fullMeta & 16) != 0;
        boolean flipped = false;
        int section = fullMeta & 3; 
            
            if (state) {
                if(dir == DirN || dir == DirS){
                	if(side == North || side == South){
                		return this.sideDoorIcon;
                	}
                } else if(dir == DirW || dir == DirE){
                	if(side == East || side == West){
                		return this.sideDoorIcon;
                	}
                }

                if (dir == DirW && side == North) { flipped = !flipped; }
                else if (dir == DirN && side == East) { flipped = !flipped; }
                else if (dir == DirE && side == South) { flipped = !flipped; }
                else if (dir == DirS && side == West) { flipped = !flipped; }
            }
            else {//Closed
            
                if(dir == DirN || dir == DirS){
                	if(side == East || side == West){
                		return this.sideDoorIcon;
                	}
                } else if(dir == DirW || dir == DirE){
                	if(side == North || side == South){
                		return this.sideDoorIcon;
                	}
                }
            	
                if (dir == DirW && side == East) { flipped = !flipped; }
                else if (dir == DirN && side == South) { flipped = !flipped; }
                else if (dir == DirE && side == West) { flipped = !flipped; }
                else if (dir == DirS && side == North) { flipped = !flipped; }
                if ((fullMeta & 32) != 0) { flipped = !flipped; }
            }
            
            switch(section){
            	case 3: return this.upperDoorIcon[flipped?1:0];
            	case 2: return this.middleDoorIcon[flipped?1:0];
            	default: return this.lowerDoorIcon[flipped?1:0];
            }
            
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.upperDoorIcon = new IIcon[2];
        this.middleDoorIcon = new IIcon[2];
        this.lowerDoorIcon = new IIcon[2];
        
        String upperTextureName = this.getTextureName() + "_2";
        String middleTextureName = this.getTextureName() + "_1";
        String lowerTextureName = this.getTextureName() + "_0";
        String sideTextureName = this.getTextureName() + "_side";
        String topTextureName = this.getTextureName() + "_top";
        
        this.upperDoorIcon[0] = iconRegister.registerIcon(upperTextureName);
        this.middleDoorIcon[0] = iconRegister.registerIcon(middleTextureName);
        this.lowerDoorIcon[0] = iconRegister.registerIcon(lowerTextureName);
        this.sideDoorIcon = iconRegister.registerIcon(sideTextureName);
        this.topDoorIcon = iconRegister.registerIcon(topTextureName);
        this.upperDoorIcon[1] = new IconFlipped(this.upperDoorIcon[0], true, false);
        this.middleDoorIcon[1] = new IconFlipped(this.middleDoorIcon[0], true, false);
        this.lowerDoorIcon[1] = new IconFlipped(this.lowerDoorIcon[0], true, false);
        
    }

    // Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
    // adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
    @Override
	public boolean isOpaqueCube() {
        return false;
    }

    @Override
	public boolean getBlocksMovement(IBlockAccess blockAccess, int x, int y, int z) {
        int fullMeta = this.getFullMetadata(blockAccess, x, y, z);
        return (fullMeta & 4) != 0;
    }


    // If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)

    @Override
	public boolean renderAsNormalBlock() {
        return false;
    }
    
    // The type of render function that is called for this block
    @Override
	public int getRenderType() {
        return RendererTallDoor.id;
    }

    
    // Returns the bounding box of the wired rectangular prism to render.
    
    @Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getSelectedBoundingBoxFromPool(world, x, y, z);
    }

    // Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
    // cleared to be reused)
    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }


    // Updates the blocks bounds based on its current state. Args: world, x, y, z

    @Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
        this.setDoorRotation(this.getFullMetadata(blockAccess, x, y, z));
    }

    //getDoorOrientation
    public int getDoorOrientation(IBlockAccess blockAccess, int x, int y, int z) {
        return (this.getFullMetadata(blockAccess, x, y, z) >> 2) & 3;
    }

    //isDoorOpen
    public boolean isDoorOpen(IBlockAccess blockAccess, int x, int y, int z) {
        return (this.getFullMetadata(blockAccess, x, y, z) & 16) != 0;
    }

    //setDoorRotation
    private void setDoorRotation(int fullMeta) {
        float thick = 0.25f;//4 texels(assuming 16x16 pixel textures)
        int dir = (fullMeta >> 2) & 3;
        boolean state = (fullMeta & 16) != 0;
        boolean isLeftHinge = (fullMeta & 32) != 0;
        
        if (dir == DirW) {
            if (state) {
                if (!isLeftHinge) {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, thick);
                } else {
                    this.setBlockBounds(0.0F, 0.0F, 1.0F - thick, 1.0F, 1.0F, 1.0F);
                }
            } else {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, thick, 1.0F, 1.0F);
            }
        }
        else if (dir == DirN) {
            if (state) {
                if (!isLeftHinge) {
                    this.setBlockBounds(1.0F - thick, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                } else {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, thick, 1.0F, 1.0F);
                }
            } else {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, thick);
            }
        }
        else if (dir == DirE) {
            if (state) {
                if (!isLeftHinge) {
                    this.setBlockBounds(0.0F, 0.0F, 1.0F - thick, 1.0F, 1.0F, 1.0F);
                } else {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, thick);
                }
            } else {
                this.setBlockBounds(1.0F - thick, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
        }
        else if (dir == DirS) {
            if (state) {
                if (!isLeftHinge) {
                    this.setBlockBounds(0.0F, 0.0F, 0.0F, thick, 1.0F, 1.0F);
                } else {
                    this.setBlockBounds(1.0F - thick, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                }
            } else {
                this.setBlockBounds(0.0F, 0.0F, 1.0F - thick, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    // Called when a player hits the block. Args: world, x, y, z, player
    @Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {}

    // Called upon block activation (right click on the block.)
    @Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xOffset, float yOffset, float zOffset){
    		boolean state = isOpen(world, x, y, z);
    		operateDoor(world, player, x, y, z, !state);
            return true;
    }

    //onPoweredBlockChange
    public void onPoweredBlockChange(World world, int x, int y, int z, boolean power){
      	operateDoor(world, (EntityPlayer)null, x, y, z, power);
    }

    public boolean isOpen(World world, int x, int y, int z){
        return (this.getFullMetadata(world, x, y, z) & 16) != 0;//0=closed,1=open
    }
    
    public int getDirFromFullMetadata(int fullMetaData){
    	return (fullMetaData >> 2) & 3;
    }
    
    public int getSectionFromMetadata(int metaData){
    	int section = (metaData & 12) >> 2;
    	return section == 0?1:section;
    }
    
    public void operateDoor(World world, EntityPlayer player, int x, int y, int z, boolean newState){
    	operateDoor(world, player, x, y, z, newState, true);
    }
    
    public void operateDoor(World world, EntityPlayer player, int x, int y, int z, boolean newState, boolean cascade){
    	
        int fullMeta = this.getFullMetadata(world, x, y, z);
        int bottomMeta = (fullMeta >> 2) & 7;
        boolean state = (bottomMeta & 4) != 0;//0=closed,1=open

        if(cascade){
        	boolean hinge = (fullMeta >> 5 & 1) == 1;
        	int dir = (getDirFromFullMetadata(fullMeta) + (hinge ? 0 : 2)) % 4;
        	int xdir = 0;
        	int zdir = 0;

        	if((dir & 1) == 1){//1 or 3
        		xdir = -dir + 2;
        	} else {//0 or 2
        		zdir = dir - 1;
        	}

        	if(world.getBlock(x + xdir, y, z + zdir) == this){
        		operateDoor(world, player, x + xdir, y, z + zdir, newState, false);
        	} 
        }
        
        if(state != newState){
            bottomMeta &= ~4;//Unset open bit
            bottomMeta |= newState?4:0;//Conditionally set open bit
        	
           	if((fullMeta & 3) == TopSect){
            	world.setBlockMetadataWithNotify(x, y - 2, z, bottomMeta, 2);
            	world.markBlockRangeForRenderUpdate(x, y - 2, z, x, y, z);
        	} else if((fullMeta & 3) == MidSect){
            	world.setBlockMetadataWithNotify(x, y - 1, z, bottomMeta, 2);
                world.markBlockRangeForRenderUpdate(x, y - 1, z, x, y + 1, z);
        	} else {//BotSect
                world.setBlockMetadataWithNotify(x, y, z, bottomMeta, 2);
                world.markBlockRangeForRenderUpdate(x, y, z, x, y + 2, z);
        	}

       		world.playAuxSFXAtEntity(player, 1003, x, y, z, 0);
        }
    }
    
    // Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
    // their own) Args: x, y, z, neighbor Block
    @Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighBlock) {
        int meta = world.getBlockMetadata(x, y, z);
        int section = getSectionFromMetadata(meta);
        
        int b1 = 0;
        int b2 = 0;
        
        switch(section){
        	case 3: b1 = -1; b2 = -2; break; //11
        	case 2:	b1 = -1; b2 = +1; break; //10
        	case 1:	b1 = +1; b2 = +2; break; //01
        }
        
        Block block1 = world.getBlock(x, y + b1, z);
        Block block2 = world.getBlock(x, y + b2, z);
        
    	if(block1 != this || block2 != this){
    		world.setBlockToAir(x, y, z);
    		if(block1 != this){	world.setBlockToAir(x, y + b1, z); }
    		if(block2 != this){	world.setBlockToAir(x, y + b2, z); }
    		
            if (!world.isRemote && section == 1) {
            	this.dropBlockAsItem(world, x, y, z, meta, 0);
            }
    		return;
    	}
        
        boolean powered = world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered(x, y + 1, z) || world.isBlockIndirectlyGettingPowered(x, y + 2, z);

        if ((powered || neighBlock.canProvidePower()) && neighBlock != this) {
            this.onPoweredBlockChange(world, x, y, z, powered);
        }
        
    }

    @Override
	public Item getItemDropped(int meta, Random random, int fortune) {
    	if(getSectionFromMetadata(meta) == BotSect){
    		return droppedItem;
    	}
    	
    	return null;
    }

    // Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
    // x, y, z, startVec, endVec
    @Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 startVec, Vec3 endVec) {
        this.setBlockBoundsBasedOnState(world, x, y, z);
        return super.collisionRayTrace(world, x, y, z, startVec, endVec);
    }

    // Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
    @Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return y >= world.getHeight() - 2 ? false : super.canPlaceBlockAt(world, x, y, z) && super.canPlaceBlockAt(world, x, y + 1, z) && super.canPlaceBlockAt(world, x, y + 2, z);
    }

    // Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
    // and stop pistons
    @Override
	public int getMobilityFlag() {
        return 2;
    }

    public int getFullMetadata(IBlockAccess blockAccess, int x, int y, int z) {
        int meta = blockAccess.getBlockMetadata(x, y, z);
        int bottomMeta;//direction and state
        int middleMeta;
        //int topMeta;//hinge 0=right,1=left
        int section = getSectionFromMetadata(meta);
        
        switch(section){
        	case TopSect:
        		bottomMeta = blockAccess.getBlockMetadata(x, y - 2, z);
        		middleMeta = blockAccess.getBlockMetadata(x, y - 1, z);
        		//topMeta = meta;
        		break;
        	case MidSect:
                bottomMeta = blockAccess.getBlockMetadata(x, y - 1, z);
                middleMeta = meta;
                //topMeta = blockAccess.getBlockMetadata(x, y + 1, z);
                break;
        	case BotSect:
        	default:
                bottomMeta = meta;
                middleMeta = blockAccess.getBlockMetadata(x, y + 1, z);
                //topMeta = blockAccess.getBlockMetadata(x, y + 2, z);
                break;
        }

        return (section & 3) | ((bottomMeta & 7) << 2) | ((middleMeta & 1) << 5);
    }

    // Gets an item for the block being called on. Args: world, x, y, z
    @Override
	@SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z) {
        return droppedItem;
    }

    // Called when the block is attempted to be harvested
    @Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
    	
        int section = getSectionFromMetadata(meta);
        
        switch(section){
        case TopSect:
        	world.setBlockToAir(x, y - 1, z);
        	world.setBlockToAir(x, y - 2, z);
        	break;
        case MidSect:
        	world.setBlockToAir(x, y + 1, z);
        	world.setBlockToAir(x, y - 1, z);
        	break;
        case BotSect:
        	world.setBlockToAir(x, y + 1, z);
        	world.setBlockToAir(x, y + 2, z);
        }
        
        //if (player.capabilities.isCreativeMode && (meta & 8) != 0 && world.getBlock(x, y - 1, z) == this) {
        //    world.setBlockToAir(x, y - 1, z);
        //}
    }*/
	
}
