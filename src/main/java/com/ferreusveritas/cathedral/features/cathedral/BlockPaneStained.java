package com.ferreusveritas.cathedral.features.cathedral;

import com.ferreusveritas.cathedral.CathedralMod;

import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPaneStained extends BlockPane {
	
	public static final String name = "pane";
	
	public static final PropertyEnum<BlockGlassStained.EnumType> VARIANT = PropertyEnum.<BlockGlassStained.EnumType>create("variant", BlockGlassStained.EnumType.class);
	
	public BlockPaneStained(String name) {
		super(Material.GLASS, true);
		setRegistryName(name);
		setUnlocalizedName(name);
		setDefaultState(getDefaultState().withProperty(VARIANT, BlockGlassStained.EnumType.AMBERRHOMBUS));
		setSoundType(SoundType.GLASS);
		setHardness(0.3F);
		setCreativeTab(CathedralMod.tabCathedral);
	}
	
	public BlockPaneStained(){
		this(name);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {NORTH, EAST, SOUTH, WEST, VARIANT});
	}
	
	/** Convert the given metadata into a BlockState for this Block */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, BlockGlassStained.EnumType.byMetadata(meta));
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
		for(BlockGlassStained.EnumType type : BlockGlassStained.EnumType.values()) {
			items.add(new ItemStack(this, 1, type.getMetadata()));
		}
	}
	
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }
	
	public static enum EnumCapping implements IStringSerializable {
		NONE,
		POST,
		NORTH,
		EAST,
		SOUTH,
		WEST;

		@Override
		public String getName() {
			return name().toLowerCase();
		}
	}
	
}