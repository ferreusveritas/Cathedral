package com.ferreusveritas.cathedral.features.basalt;

public class BlockDoubleSlabCheckered extends BlockSlabCheckered {

	public BlockDoubleSlabCheckered(String name) {
		super(name);
		this.useNeighborBrightness = false;
	}

	@Override
	public boolean isDouble() {
		return true;
	}
	
}
