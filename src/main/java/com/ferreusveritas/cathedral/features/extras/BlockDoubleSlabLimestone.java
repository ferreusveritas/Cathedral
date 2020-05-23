package com.ferreusveritas.cathedral.features.extras;

public class BlockDoubleSlabLimestone extends BlockSlabLimestone {

	public BlockDoubleSlabLimestone(String name) {
		super(name);
		this.useNeighborBrightness = false;
	}

	@Override
	public boolean isDouble() {
		return true;
	}
	
}
