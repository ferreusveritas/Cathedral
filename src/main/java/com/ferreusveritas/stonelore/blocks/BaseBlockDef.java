package com.ferreusveritas.stonelore.blocks;

import net.minecraft.block.Block;

public class BaseBlockDef {
	public BaseBlockDef(int select, Block block, int metaData, String name, String blockName, float hardness, float resistance){
		this.select = select;
		this.block = block;
		this.metaData = metaData;
		this.name = name;
		this.blockName = blockName;
		this.hardness = hardness;
		this.resistance = resistance;
	}
	
	public int select;
	public Block block;
	public int metaData;
	public String name;
	public String blockName;
	public float hardness;
	public float resistance;

};