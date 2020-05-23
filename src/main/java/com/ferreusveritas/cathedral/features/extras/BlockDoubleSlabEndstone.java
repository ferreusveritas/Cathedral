package com.ferreusveritas.cathedral.features.extras;

public class BlockDoubleSlabEndstone extends BlockSlabEndstone {

	public BlockDoubleSlabEndstone(String name) {
		super(name);
		this.useNeighborBrightness = false;
	}

	@Override
	public boolean isDouble() {
		return true;
	}
	
}
