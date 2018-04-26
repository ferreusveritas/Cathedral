package com.ferreusveritas.cathedral.common;

import net.minecraft.util.IStringSerializable;

public interface TypeStandard extends IStringSerializable {

	String name();

	public int getMetadata();
	
	default public String getName() {
		return name().toLowerCase();
	}

	default public String getUnlocalizedName() {
		return getName();
	}
	
}
