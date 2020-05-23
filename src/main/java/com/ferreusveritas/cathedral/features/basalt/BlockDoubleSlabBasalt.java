package com.ferreusveritas.cathedral.features.basalt;

public class BlockDoubleSlabBasalt extends BlockSlabBasalt {

	public BlockDoubleSlabBasalt(String name) {
		super(name);
		this.useNeighborBrightness = false;
	}

	@Override
	public boolean isDouble() {
		return true;
	}
	
}
