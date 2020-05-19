package com.ferreusveritas.cathedral.features.cathedral;

import net.minecraft.util.IStringSerializable;

public enum PillarConnectionType implements IStringSerializable {
	None,
	Pillar,
	Rail,
	Solid,
	Pane;

	@Override
	public String getName() {
		return toString();
	}
}
