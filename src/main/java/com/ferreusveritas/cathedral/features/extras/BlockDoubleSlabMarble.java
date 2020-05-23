package com.ferreusveritas.cathedral.features.extras;

public class BlockDoubleSlabMarble extends BlockSlabMarble {

	public BlockDoubleSlabMarble(String name) {
		super(name);
		this.useNeighborBrightness = false;
	}

	@Override
	public boolean isDouble() {
		return true;
	}
	
}
