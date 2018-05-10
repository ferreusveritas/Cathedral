package com.ferreusveritas.cathedral.features.extras;

import java.util.Random;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.features.extras.FeatureTypes.EnumEndStoneSlabType;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSlabEndstone extends BlockSlab {

    public static final PropertyEnum<EnumEndStoneSlabType> VARIANT = PropertyEnum.<EnumEndStoneSlabType>create("variant", EnumEndStoneSlabType.class);
    
	public BlockSlabEndstone(String name) {
		super(Material.ROCK);
        IBlockState iblockstate = this.blockState.getBaseState();

        if (!this.isDouble()) {
            iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
        }

        this.setDefaultState(iblockstate.withProperty(VARIANT, EnumEndStoneSlabType.BRICKS));
		
		setRegistryName(name);
		setUnlocalizedName(name);
	}
	
    protected BlockStateContainer createBlockState() {
        return this.isDouble() ? new BlockStateContainer(this, new IProperty[] {VARIANT}) : new BlockStateContainer(this, new IProperty[] {HALF, VARIANT});
    }
	
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, EnumEndStoneSlabType.byMetadata(meta & 7));

        if (!this.isDouble()) {
            iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        }

        return iblockstate;
    }
    
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        i = i | ((EnumEndStoneSlabType)state.getValue(VARIANT)).getMetadata();

        if (!this.isDouble() && (state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)) {
            i |= 8;
        }

        return i;
    }
    
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        for (EnumEndStoneSlabType type : EnumEndStoneSlabType.values()) {
        	items.add(new ItemStack(this, 1, type.getMetadata()));
        }
    }
    
	@Override
	public String getUnlocalizedName(int meta) {
        return super.getUnlocalizedName() + "." + EnumEndStoneSlabType.byMetadata(meta).getName();
	}
	
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(CathedralMod.extras.slabEndstone);
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(CathedralMod.extras.slabEndstone, 1, ((EnumEndStoneSlabType)state.getValue(VARIANT)).getMetadata());
    }
	
    public int damageDropped(IBlockState state) {
        return ((EnumEndStoneSlabType)state.getValue(VARIANT)).getMetadata();
    }
	
	@Override
	public boolean isDouble() {
		return false;
	}

	@Override
	public IProperty<?> getVariantProperty() {
		return VARIANT;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
        return EnumEndStoneSlabType.byMetadata(stack.getMetadata() & 7);
	}
	
}

