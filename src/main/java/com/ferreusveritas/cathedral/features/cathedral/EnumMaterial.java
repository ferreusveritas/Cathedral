package com.ferreusveritas.cathedral.features.cathedral;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public enum EnumMaterial implements IStringSerializable {

	STONE         (0, "stone", 1.5f),
	SANDSTONE     (1, "sandstone", 0.8f),
	REDSANDSTONE  (2, "redsandstone", 0.8f),
	OBSIDIAN      (3, "obsidian", 50.0f),
	NETHERBRICK   (4, "netherbrick", 2.0f),
	QUARTZ        (5, "quartz", 0.8f),
	ENDSTONE      (6, "endstone", 3.0f),
	PACKEDICE     (7, "packedice", 0.5f),
	SNOW          (8, "snow", 0.2f),
	MARBLE        (9, "marble", 1.5f),
	LIMESTONE     (10, "limestone", 2.0f),
	BASALT        (11, "basalt", 2.5f),
	DWEMER        (12, "dwemer", 2.5f);
	
	private final int meta;
	private final String name;
	private final String unlocalizedName;
	private final float hardness;
	
	private EnumMaterial(int index, String name, float hardness) {
		this.meta = index;
		this.name = name;
		this.unlocalizedName = name;
		this.hardness = hardness;
	}
	
	public int getMetadata() {
		return meta;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public static EnumMaterial byMetadata(int meta) {
		return values()[MathHelper.clamp(meta, 0, values().length - 1)];
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public String getUnlocalizedName() {
		return unlocalizedName;
	}
	
	public float getHardness() {
		return hardness;
	}
	
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		switch (this) {
			case STONE: return Blocks.STONE.getExplosionResistance(exploder);
			case SANDSTONE: return Blocks.SANDSTONE.getExplosionResistance(exploder);
			case REDSANDSTONE: return Blocks.RED_SANDSTONE.getExplosionResistance(exploder);
			case OBSIDIAN: return Blocks.OBSIDIAN.getExplosionResistance(exploder);
			case NETHERBRICK: return Blocks.NETHER_BRICK.getExplosionResistance(exploder);
			case QUARTZ: return Blocks.QUARTZ_BLOCK.getExplosionResistance(exploder);
			case ENDSTONE: return Blocks.END_STONE.getExplosionResistance(exploder);
			case PACKEDICE: return Blocks.PACKED_ICE.getExplosionResistance(exploder);
			case SNOW: return Blocks.SNOW.getExplosionResistance(exploder);
			case MARBLE: return 10.0f;
			case LIMESTONE: return 10.0f;
			case BASALT: return 20.0f;
			case DWEMER: return 20.0f;
			default: return 1.5f;
		}
	}
	
}