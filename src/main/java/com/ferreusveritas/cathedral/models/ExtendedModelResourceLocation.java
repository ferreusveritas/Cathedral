package com.ferreusveritas.cathedral.models;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;

/**
 * This class allows the smuggling of a simple key string.  This makes the {@link ICustomModelLoader}
 * able to easily identify it as a load candidate. 
 * 
 * @author ferreusveritas
 * 
 */
public class ExtendedModelResourceLocation extends ModelResourceLocation {
	
	private final String key;
	
	public ExtendedModelResourceLocation(String domain, String path, String variant, String key) {
		super(new ResourceLocation(domain, path), variant);
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	@Override
	public boolean equals(Object other) {
		return super.equals(other) && (other instanceof ExtendedModelResourceLocation ? ((ExtendedModelResourceLocation)other).getKey().equals(getKey()) : false );
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() ^ getKey().hashCode();
	}
}
