package com.ferreusveritas.cathedral.features.dwemer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTallDoor extends Item {

    private final Block block;

    public ItemTallDoor(Block block) {
        this.block = block;
    }

    /**
     * Called when a Block is right-clicked with this Item
     */
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (facing != EnumFacing.UP) {
            return EnumActionResult.FAIL;
        }
        else {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (!block.isReplaceable(worldIn, pos)) {
                pos = pos.offset(facing);
            }

            ItemStack itemstack = player.getHeldItem(hand);

            if (player.canPlayerEdit(pos, facing, itemstack) && this.block.canPlaceBlockAt(worldIn, pos)) {
                EnumFacing enumfacing = EnumFacing.fromAngle((double)player.rotationYaw);
                int x = enumfacing.getFrontOffsetX();
                int z = enumfacing.getFrontOffsetZ();
                boolean isRightHinge = x < 0 && hitZ < 0.5F || x > 0 && hitZ > 0.5F || z < 0 && hitX > 0.5F || z > 0 && hitX < 0.5F;
                placeDoor(worldIn, pos, enumfacing, this.block, isRightHinge);
                SoundType soundtype = worldIn.getBlockState(pos).getBlock().getSoundType(worldIn.getBlockState(pos), worldIn, pos, player);
                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
                return EnumActionResult.SUCCESS;
            }
            else {
                return EnumActionResult.FAIL;
            }
        }
    }

    public static void placeDoor(World worldIn, BlockPos lowerPos, EnumFacing facing, Block door, boolean isRightHinge) {
        BlockPos rightPos = lowerPos.offset(facing.rotateY());
        BlockPos leftPos = lowerPos.offset(facing.rotateYCCW());
        int leftCount = (worldIn.getBlockState(leftPos).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(leftPos.up()).isNormalCube() ? 1 : 0);
        int rightCount = (worldIn.getBlockState(rightPos).isNormalCube() ? 1 : 0) + (worldIn.getBlockState(rightPos.up()).isNormalCube() ? 1 : 0);
        boolean doorOnLeft = worldIn.getBlockState(leftPos).getBlock() == door || worldIn.getBlockState(leftPos.up()).getBlock() == door;
        boolean doorOnRight = worldIn.getBlockState(rightPos).getBlock() == door || worldIn.getBlockState(rightPos.up()).getBlock() == door;

        if ((!doorOnLeft || doorOnRight) && rightCount <= leftCount) {
            if (doorOnRight && !doorOnLeft || rightCount < leftCount) {
                isRightHinge = false;
            }
        }
        else {
            isRightHinge = true;
        }

        BlockPos middlePos = lowerPos.up();
        BlockPos upperPos = lowerPos.up(2);
        boolean powered = worldIn.isBlockPowered(lowerPos) || worldIn.isBlockPowered(middlePos) || worldIn.isBlockPowered(upperPos);
        IBlockState iblockstate = door.getDefaultState()
        		.withProperty(BlockDoor.FACING, facing)
        		.withProperty(BlockDoor.HINGE, isRightHinge ? BlockDoor.EnumHingePosition.RIGHT : BlockDoor.EnumHingePosition.LEFT)
        		.withProperty(BlockDoor.POWERED, Boolean.valueOf(powered))
        		.withProperty(BlockDoor.OPEN, Boolean.valueOf(powered));
        worldIn.setBlockState(lowerPos, iblockstate.withProperty(BlockTallDoor.THIRD, BlockTallDoor.EnumDoorThird.LOWER), 2);
        worldIn.setBlockState(middlePos, iblockstate.withProperty(BlockTallDoor.THIRD, BlockTallDoor.EnumDoorThird.MIDDLE), 2);
        worldIn.setBlockState(upperPos, iblockstate.withProperty(BlockTallDoor.THIRD, BlockTallDoor.EnumDoorThird.UPPER), 2);
        worldIn.notifyNeighborsOfStateChange(lowerPos, door, false);
        worldIn.notifyNeighborsOfStateChange(middlePos, door, false);
        worldIn.notifyNeighborsOfStateChange(upperPos, door, false);
    }
    
}
