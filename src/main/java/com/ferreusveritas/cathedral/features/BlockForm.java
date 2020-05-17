package com.ferreusveritas.cathedral.features;

public enum BlockForm {
	
	BLOCK("block"),
	GLASS("glass"),
	PANE("pane"),
	BARS("bars"),
	STAIRS("stairs"),
	SLAB("slab"),
	DOUBLESLAB("doubleslab"),
	DOOR("door"),
	LIGHT("light"),
	RAILING("railing"),
	CHAIN("chain"),
	SHINGLES("shingles"),
	GARGOYLE("gargoyle"),
	CATWALK("catwalk"),
	PILLAR("pillar"),
	LECTERN("lectern");
	
	private String name;
	
	private BlockForm(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
