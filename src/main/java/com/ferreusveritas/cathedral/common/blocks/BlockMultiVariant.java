package com.ferreusveritas.cathedral.common.blocks;

import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.features.basalt.BlockBasalt;
import com.ferreusveritas.cathedral.proxy.ModelHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class BlockMultiVariant<E extends Enum<E> & StandardEnum> extends Block {
	
	public final PropertyEnum<E> variant;
	public final E[] values;
	
	public BlockMultiVariant(Material materialIn, Class<E> e) {
		super(materialIn);
		variant = PropertyEnum.<E>create("variant", e);
		values = e.getEnumConstants();
		String name = values[0].getBlockName();
		setRegistryName(name);
		setUnlocalizedName(name);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {variant});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(variant, byMetadata(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(variant).getMetadata();
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(variant).getMetadata();
	}
	
	public E byMetadata(int meta) {
		return values[MathHelper.clamp(meta, 0, values.length - 1)];

	}
	
	public void getSubBlocks(net.minecraft.creativetab.CreativeTabs itemIn, net.minecraft.util.NonNullList<ItemStack> items) {
		for(E type : values ) {
			items.add(new ItemStack(this, 1, type.getMetadata()));
		}
	};
	
	public Item getItemMultiTexture() {
		return new ItemMultiTexture(this, this, new ItemMultiTexture.Mapper() {
			public String apply(ItemStack stack) {
				return byMetadata(stack.getMetadata()).getUnlocalizedName();
			}
		}).setRegistryName(this.getRegistryName());
	}
	
	public void registerItemModels() {
		for(BlockBasalt.EnumType type: BlockBasalt.EnumType.values()) {
			ModelHelper.regModel(Item.getItemFromBlock(this), type.getMetadata(), new ResourceLocation(ModConstants.MODID, this.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}
	}
}
