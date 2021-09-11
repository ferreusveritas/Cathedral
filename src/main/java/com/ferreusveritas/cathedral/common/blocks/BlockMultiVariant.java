package com.ferreusveritas.cathedral.common.blocks;

import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.proxy.ModelHelper;
import com.ferreusveritas.cathedral.util.InterModCommsUtils;
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

public abstract class BlockMultiVariant<E extends Enum<E> & StandardEnum> extends Block {
	
	public PropertyEnum<E> variant;
	public final E[] values;
	
	public BlockMultiVariant(Material materialIn, Class<E> e, String name) {
		super(materialIn);
		variant = PropertyEnum.<E>create("variant", e);
		values = e.getEnumConstants();
		setRegistryName(name);
		setUnlocalizedName(name);
	}
	
	abstract public void makeVariantProperty();
	
	@Override
	protected BlockStateContainer createBlockState() {
		makeVariantProperty();
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
		for(E type : values) {
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
		for(E type: values) {
			ModelHelper.regModel(Item.getItemFromBlock(this), type.getMetadata(), new ResourceLocation(ModConstants.MODID, this.getRegistryName().getResourcePath() + "." + type.getUnlocalizedName()));
		}
	}
	
	public void addChiselVariation(String group) {
		for (E type: values) {
			InterModCommsUtils.addChiselVariation(group, this, type.getMetadata());
		}
	}
	
}
