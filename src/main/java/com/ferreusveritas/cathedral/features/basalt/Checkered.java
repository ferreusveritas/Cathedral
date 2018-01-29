package com.ferreusveritas.cathedral.features.basalt;

import net.minecraft.util.IStringSerializable;

public class Checkered {
	
	/*checkeredSlab.carverHelper.addVariation("tile.basalt_checkeredslab-plain.name", 0, "checkered", Cathedral.MODID);
	checkeredSlab.carverHelper.addVariation("tile.basalt_checkeredslab-small.name", 2, "checkered-small", Cathedral.MODID);
	checkeredSlab.carverHelper.addVariation("tile.basalt_checkeredslab-tiles.name", 3, "checkered-tiles", Cathedral.MODID);
	checkeredSlab.carverHelper.addVariation("tile.basalt_checkeredslab-smalltiles.name", 4, "checkered-tiles-small", Cathedral.MODID);
	checkeredSlab.carverHelper.registerAll(checkeredSlab, "checkeredslab", ItemCarvableSlab.class);
	checkeredSlab.registerSlabTop();*/

	//BlockCarvable.addBlocks(names, checkeredBlock, "checkered");	
	
	public static enum EnumType implements IStringSerializable {
		NORMAL		(0, "normal"),
		BORDER		(1, "border"),
		SMALL		(2, "small"),
		TILES		(3, "tiles"),
		TILESSMALL	(4, "tiles-small");
		
		/** Array of the Block's BlockStates */
		private static final Checkered.EnumType[] META_LOOKUP = new Checkered.EnumType[values().length];
		/** The BlockState's metadata. */
		private final int meta;
		/** The EnumType's name. */
		private final String name;
		private final String unlocalizedName;
		
		private EnumType(int index, String name) {
			this.meta = index;
			this.name = name;
			this.unlocalizedName = name;
		}
		
		/** Returns the EnumType's metadata value. */
		public int getMetadata() {
			return this.meta;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
		
		/** Returns an EnumType for the BlockState from a metadata value. */
		public static Checkered.EnumType byMetadata(int meta) {
			if (meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}
			
			return META_LOOKUP[meta];
		}
		
		@Override
		public String getName() {
			return this.name;
		}
		
		public String getUnlocalizedName() {
			return this.unlocalizedName;
		}
		
		static {
			for (Checkered.EnumType blockcheckered$enumtype : values()) {
				META_LOOKUP[blockcheckered$enumtype.getMetadata()] = blockcheckered$enumtype;
			}
		}

	}
	
}
