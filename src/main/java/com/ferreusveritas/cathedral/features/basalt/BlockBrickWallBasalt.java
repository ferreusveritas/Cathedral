package com.ferreusveritas.cathedral.features.basalt;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

/**
 * @author Harley O'Connor
 */
public class BlockBrickWallBasalt extends BlockWall {

    public BlockBrickWallBasalt(Block modelBlock, String name) {
        super(modelBlock);

        // Re-construct state container to remove unneeded variant property.
        this.blockState = this.newBlockState();
        this.setDefaultState(this.blockState.getBaseState().withProperty(UP, Boolean.FALSE).withProperty(NORTH,
                Boolean.FALSE).withProperty(EAST, Boolean.FALSE).withProperty(SOUTH, Boolean.FALSE).withProperty(WEST,
                Boolean.FALSE));

        this.setUnlocalizedName(name);
        this.setRegistryName(name);
    }

    private BlockStateContainer newBlockState() {
        return new BlockStateContainer(this, UP, NORTH, EAST, WEST, SOUTH);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public void getSubBlocks(CreativeTabs tabs, NonNullList<ItemStack> stacks) {
        stacks.add(new ItemStack(this));
    }

}
