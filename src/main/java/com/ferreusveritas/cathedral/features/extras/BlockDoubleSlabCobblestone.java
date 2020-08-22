package com.ferreusveritas.cathedral.features.extras;

public class BlockDoubleSlabCobblestone extends BlockSlabCobblestone {

	public BlockDoubleSlabCobblestone(String name) {
		super(name);
		this.useNeighborBrightness = false;
	}

	@Override
	public boolean isDouble() {
		return true;
	}
	
}
