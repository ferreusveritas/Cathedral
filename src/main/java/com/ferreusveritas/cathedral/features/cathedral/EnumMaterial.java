package com.ferreusveritas.cathedral.features.cathedral;

import com.ferreusveritas.cathedral.CathedralMod;
import com.ferreusveritas.cathedral.features.IVariantEnumType;
import com.ferreusveritas.cathedral.features.dwarven.FeatureTypes.EnumCarvedType;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public enum EnumMaterial implements IStringSerializable, IVariantEnumType {

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
	
	@Override
	public int getMetadata() {
		return meta;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public static final PropertyEnum<EnumMaterial> VARIANT = PropertyEnum.<EnumMaterial>create("variant", EnumMaterial.class);
	
	public static EnumMaterial byMetadata(int meta) {
		return values()[MathHelper.clamp(meta, 0, values().length - 1)];
	}

	public static EnumMaterial byName(String name) {
		for(EnumMaterial mat : values()) {
			if(mat.getName().equals(name)) {
				return mat;
			}
		}
		
		return STONE;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getUnlocalizedName() {
		return unlocalizedName;
	}
	
	public float getHardness() {
		return hardness;
	}
	
	public float getExplosionResistance(Entity exploder) {
		switch (this) {
			case STONE:        return Blocks.STONE.getExplosionResistance(exploder);
			case SANDSTONE:    return Blocks.SANDSTONE.getExplosionResistance(exploder);
			case REDSANDSTONE: return Blocks.RED_SANDSTONE.getExplosionResistance(exploder);
			case OBSIDIAN:     return Blocks.OBSIDIAN.getExplosionResistance(exploder);
			case NETHERBRICK:  return Blocks.NETHER_BRICK.getExplosionResistance(exploder);
			case QUARTZ:       return Blocks.QUARTZ_BLOCK.getExplosionResistance(exploder);
			case ENDSTONE:     return Blocks.END_STONE.getExplosionResistance(exploder);
			case PACKEDICE:    return Blocks.PACKED_ICE.getExplosionResistance(exploder);
			case SNOW:         return Blocks.SNOW.getExplosionResistance(exploder);
			case MARBLE:       return 10.0f;
			case LIMESTONE:    return 10.0f;
			case BASALT:       return 20.0f;
			case DWEMER:       return 20.0f;
			default:           return 1.5f;
		}
	}
	
	public ItemStack getRawMaterialBlock() {
		switch(this) {
			case STONE:        return new ItemStack(Blocks.STONE);
			case SANDSTONE:    return new ItemStack(Blocks.SANDSTONE);
			case REDSANDSTONE: return new ItemStack(Blocks.RED_SANDSTONE);
			case OBSIDIAN:     return new ItemStack(Blocks.OBSIDIAN);
			case NETHERBRICK:  return new ItemStack(Blocks.NETHER_BRICK);
			case QUARTZ:       return new ItemStack(Blocks.QUARTZ_BLOCK);
			case ENDSTONE:     return new ItemStack(Blocks.END_STONE);
			case PACKEDICE:    return new ItemStack(Blocks.PACKED_ICE);
			case SNOW:         return new ItemStack(Blocks.SNOW);
			case MARBLE:       return new ItemStack(Block.REGISTRY.getObject(new ResourceLocation("chisel", "marble2")), 1, 7);
			case LIMESTONE:    return new ItemStack(Block.REGISTRY.getObject(new ResourceLocation("chisel", "limestone2")), 1, 7);
			case BASALT:       return new ItemStack(Block.REGISTRY.getObject(new ResourceLocation("chisel", "basalt2")), 1, 7);
			case DWEMER:       return new ItemStack(CathedralMod.dwarven.blockCarved, 1, EnumCarvedType.PANEL.getMetadata());
			default:           return ItemStack.EMPTY;
		}
	}
	
	public ResourceLocation getFlatTexture() {
		switch(this) {
			case STONE:        return new ResourceLocation("blocks/stone");
			case SANDSTONE:    return new ResourceLocation("chisel:blocks/sandstoneyellow/raw");
			case REDSANDSTONE: return new ResourceLocation("chisel:blocks/sandstonered/raw");
			case OBSIDIAN:     return new ResourceLocation("chisel:blocks/obsidian/panel");
			case NETHERBRICK:  return new ResourceLocation("cathedral:blocks/cathedral/netherbrick_default");
			case QUARTZ:       return new ResourceLocation("chisel:blocks/quartz/raw");
			case ENDSTONE:     return new ResourceLocation("chisel:blocks/endstone/raw");
			case PACKEDICE:    return new ResourceLocation("cathedral:blocks/extras/block_packedice_base");
			case SNOW:         return new ResourceLocation("blocks/snow");
			case MARBLE:       return new ResourceLocation("blocks/stone");//TODO
			case LIMESTONE:    return new ResourceLocation("blocks/stone");//TODO
			case BASALT:       return new ResourceLocation("chisel:blocks/basalt/raw");
			case DWEMER:       return new ResourceLocation("blocks/stone");//TODO
			default:           return new ResourceLocation("blocks/dirt");
		}
	}
	
	public ResourceLocation getBevelledTexture() {
		switch(this) {
			case STONE:        return new ResourceLocation("chisel:blocks/stone/tiles-large");
			case SANDSTONE:    return new ResourceLocation("chisel:blocks/sandstoneyellow/tiles-large");
			case REDSANDSTONE: return new ResourceLocation("chisel:blocks/sandstonered/tiles-large");
			case OBSIDIAN:     return new ResourceLocation("chisel:blocks/obsidian/panel");
			case NETHERBRICK:  return new ResourceLocation("cathedral:blocks/cathedral/netherbrick_default");
			case QUARTZ:       return new ResourceLocation("chisel:blocks/quartz/tiles-large");
			case ENDSTONE:     return new ResourceLocation("chisel:blocks/endstone/tiles-large");
			case PACKEDICE:    return new ResourceLocation("cathedral:blocks/extras/block_packedice_base");
			case SNOW:         return new ResourceLocation("blocks/snow");
			case MARBLE:       return new ResourceLocation("chisel:blocks/marble/tiles-large");
			case LIMESTONE:    return new ResourceLocation("chisel:blocks/limestone/tiles-large");
			case BASALT:       return new ResourceLocation("cathedral:blocks/basalt/block_carved_paver");
			case DWEMER:       return new ResourceLocation("cathedral:blocks/dwemer/block_carved_panel");
			default:           return new ResourceLocation("blocks/dirt");
		}
	}
	
}