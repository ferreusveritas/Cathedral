package com.ferreusveritas.cathedral.common.blocks;

import net.minecraft.util.IStringSerializable;

public interface StandardEnum extends IStringSerializable {

	String name();//Will be overridden by enum

	default public int getMetadata() {
		return ((Enum<?>)this).ordinal();
	}
	
	default public String getName() {
		return name().toLowerCase();
	}

	default public String getUnlocalizedName() {
		return getName();
	}
	
}
