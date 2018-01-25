package com.ferreusveritas.cathedral.features.basalt;

import com.ferreusveritas.cathedral.Cathedral;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class BlockBasalt extends Block {

	public final static String name = "basalt"; 
	
	public static final PropertyEnum<Basalt.EnumType> VARIANT = PropertyEnum.<Basalt.EnumType>create("variant", Basalt.EnumType.class);

	public BlockBasalt() {
		this(name);
	}
	
	public BlockBasalt(String name) {
		super(Material.ROCK);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(Cathedral.tabBasalt);
		setHardness(Cathedral.basalt.basaltHardness);
		setResistance(Cathedral.basalt.basaltResistance);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {VARIANT});
	}
	
	/** Convert the given metadata into a BlockState for this Block */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, Basalt.EnumType.byMetadata(meta));
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
	
	public void getSubBlocks(net.minecraft.creativetab.CreativeTabs itemIn, net.minecraft.util.NonNullList<ItemStack> items) {
		for(Basalt.EnumType type : Basalt.EnumType.values()) {
			items.add(new ItemStack(this, 1, type.getMetadata()));
		}
	};
	
}
