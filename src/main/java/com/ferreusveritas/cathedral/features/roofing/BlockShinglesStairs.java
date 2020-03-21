package com.ferreusveritas.cathedral.features.roofing;

import com.ferreusveritas.cathedral.CathedralMod;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/*
M	8421	F	Desc
0	0000	E	Stairs
1	0001	W	Stairs
2	0010	S	Stairs
3	0011	N	Stairs
4	0100	E	Inverted Stairs
5	0101	W	Inverted Stairs
6	0110	S	Inverted Stairs
7	0111	N	Inverted Stairs
8	1000	E	Wall Slab
9	1001	W	Wall Slab
10	1010	S	Wall Slab
11	1011	N	Wall Slab
12	1100		Fence
13	1101		Bottom Slab
14	1110		Top Slab
15	1111		Solid Block
 */

public class BlockShinglesStairs extends BlockStairs {

	EnumDyeColor color;
	
	public BlockShinglesStairs(EnumDyeColor color, String name) {
		super(Blocks.STONE_STAIRS.getDefaultState());
		setRegistryName(name);
		setUnlocalizedName(name);
		this.useNeighborBrightness = true;
		//setDefaultState(getDefaultState().withProperty(FORM, EnumForm.STAIRS));
		this.color = color;
		setCreativeTab(CathedralMod.tabRoofing);
	}

	@Override
	protected BlockStateContainer createBlockState() {
        //return new BlockStateContainer(this, new IProperty[] {FORM, FACING, HALF, SHAPE});
        return new BlockStateContainer(this, new IProperty[] {FACING, HALF, SHAPE});
	}
	
	/** Convert the given metadata into a BlockState for this Block */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return super.getStateFromMeta(meta);
	}
	
	/** Convert the BlockState into the correct metadata value */
	@Override
	public int getMetaFromState(IBlockState state) {
		return super.getMetaFromState(state);
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
		return false;
	}

}
