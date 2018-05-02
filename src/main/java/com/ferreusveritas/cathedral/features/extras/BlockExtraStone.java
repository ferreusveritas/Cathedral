package com.ferreusveritas.cathedral.features.extras;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.features.extras.FeatureTypes.EnumStoneType;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class BlockExtraStone extends Block {

	public final static String name = "extrastone";

	public static final PropertyEnum<EnumStoneType> VARIANT = PropertyEnum.<EnumStoneType>create("variant", EnumStoneType.class);

	public BlockExtraStone() {
		this(name);
	}

	public BlockExtraStone(String name) {
		super(Material.ROCK);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CathedralMod.tabBasalt);
		setHardness(CathedralMod.basalt.basaltHardness);
		setResistance(CathedralMod.basalt.basaltResistance);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {VARIANT});
	}

	/** Convert the given metadata into a BlockState for this Block */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, EnumStoneType.byMetadata(meta));
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
	public void getSubBlocks(net.minecraft.creativetab.CreativeTabs itemIn, net.minecraft.util.NonNullList<ItemStack> items) {
		for(EnumStoneType type : EnumStoneType.values()) {
			items.add(new ItemStack(this, 1, type.getMetadata()));
		}
	};

}

