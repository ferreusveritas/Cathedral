package com.ferreusveritas.cathedral.models;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.ferreusveritas.cathedral.ModConstants;
import com.ferreusveritas.cathedral.features.cathedral.EnumMaterial;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModelUtils {

	/**
	 * Sets up a model that maps EnumMaterial values as variants
	 * 
	 * @param block
	 * @param resName
	 * @param loader
	 */
	@SideOnly(Side.CLIENT)
	public static void setupCustomMaterialModel(Block block, String resName, @Nonnull Function<ResourceLocation, IModel> loader) {
		ModelLoaderRegistry.registerLoader(new ModelLoaderKeyed(resName, loader));
		Function<EnumMaterial, ModelResourceLocation> resMaker = mat -> new ExtendedModelResourceLocation(ModConstants.MODID, resName, mat.getName(), resName);
		Map<EnumMaterial, ModelResourceLocation> matModelMap = Lists.newArrayList(EnumMaterial.values()).stream().collect(Collectors.toMap(mat -> mat, resMaker));
		IStateMapper mapper = blk -> blk.getBlockState().getValidStates().stream().collect(Collectors.toMap(b -> b, b -> matModelMap.get( b.getValue(EnumMaterial.VARIANT) )));
		ModelLoader.setCustomStateMapper(block, mapper);
	}
	
	/**
	 * Sets up a model with only "normal" mapping (no variants)
	 * 
	 * @param block
	 * @param resName
	 * @param loader
	 */
	@SideOnly(Side.CLIENT)
	public static void setupCustomModel(Block block, String resName, @Nonnull Function<ResourceLocation, IModel> loader) {
		ModelLoaderRegistry.registerLoader(new ModelLoaderKeyed(resName, loader));
		ModelResourceLocation resLoc = new ExtendedModelResourceLocation(ModConstants.MODID, resName, null, resName);
		IStateMapper mapper = blk -> blk.getBlockState().getValidStates().stream().collect(Collectors.toMap(b -> b, b -> resLoc));
		ModelLoader.setCustomStateMapper(block, mapper);
	}
	
	@SideOnly(Side.CLIENT)
	public static void setGenericStateMapper(Block block, ModelResourceLocation modelLocation) {
		ModelLoader.setCustomStateMapper(block, state -> {
			return block.getBlockState().getValidStates().stream().collect(Collectors.toMap(b -> b, b -> modelLocation));
		});
	}
	
}
