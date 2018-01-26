package com.ferreusveritas.cathedral.features.basalt;

import com.ferreusveritas.cathedral.CathedralMod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.MathHelper;

public class BlockBasalt extends Block {

	public final static String name = "basalt"; 
	
	public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.<EnumType>create("variant", EnumType.class);

	public BlockBasalt() {
		this(name);
	}
	
	public BlockBasalt(String name) {
		super(Material.ROCK);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CathedralMod.tabBasalt);
		setHardness(CathedralMod.basalt.basaltHardness);
		setResistance(CathedralMod.basalt.basaltResistance);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {VARIANT});
	}
	
	/** Convert the given metadata into a BlockState for this Block */
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, EnumType.byMetadata(meta));
	}
	
	/** Convert the BlockState into the correct metadata value */
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT).getMetadata();
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(VARIANT).getMetadata();
	}
	
	public void getSubBlocks(net.minecraft.creativetab.CreativeTabs itemIn, net.minecraft.util.NonNullList<ItemStack> items) {
		for(EnumType type : EnumType.values()) {
			items.add(new ItemStack(this, 1, type.getMetadata()));
		}
	};
	
	public static enum EnumType implements IStringSerializable {
		
		ROSETTE		( 0, "rosette"),
		PAVER		( 1, "paver"),
		WORNBRICK	( 2, "wornbrick"),
		ORNATE		( 3, "ornate"),
		POISON		( 4, "poison"),
		SUNKENPANEL	( 5, "sunkenpanel"),
		TILES		( 6, "tiles"),
		SLABS		( 7, "slabs"),
		VAULT		( 8, "vault"),
		SMALLBRICKS	( 9, "smallbricks"),
		SMALLCHAOTI	(10, "smallchaotic"),
		SMALLTILES	(11, "smalltiles"),
		BLOCK		(12, "block"),
		SUNKEN		(13, "sunken"),
		KNOT		(14, "knot"); 
		
		private final int meta;
		private final String name;
		private final String unlocalizedName;
		
		private EnumType(int index, String name) {
			this.meta = index;
			this.name = name;
			this.unlocalizedName = name;
		}
		
		public int getMetadata() {
			return meta;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
		public static EnumType byMetadata(int meta) {
			return values()[MathHelper.clamp(meta, 0, values().length - 1)];
		}
		
		@Override
		public String getName() {
			return name;
		}
		
		public String getUnlocalizedName() {
			return unlocalizedName;
		}

	}

}
