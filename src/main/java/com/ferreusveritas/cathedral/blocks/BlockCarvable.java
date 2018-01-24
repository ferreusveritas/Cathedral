package com.ferreusveritas.cathedral.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import team.chisel.api.block.ICarvable;
import team.chisel.api.block.VariationData;

public class BlockCarvable extends Block implements ICarvable {

	public BlockCarvable() {
		this(Material.ROCK);
	}
	
	public BlockCarvable(Material materialIn) {
		super(materialIn);
	}

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalVariations() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public VariationData getVariationData(int variation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VariationData[] getVariations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getVariationIndex(IBlockState state) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*public CarvableHelper carverHelper;
	private boolean isAlpha;

	public static void addBlocks(String[] names, BlockCarvable block, String name){
		for(int i = 0; i < names.length; i++){
			block.carverHelper.addVariation("tile." + Cathedral.MODID + "_" + name + "." + names[i] + ".name", i, names[i], Cathedral.MODID);
		}
		block.carverHelper.registerAll(block, name + "block");
	}

	public BlockCarvable() {
		this(Material.rock);
	}

	public BlockCarvable(Material m) {
		super(m);
		if (m == Material.rock || m == Material.iron) {
			setHarvestLevel("pickaxe", 0);
		}
		carverHelper = new CarvableHelper(this);
		setResistance(10.0F);
		setHardness(2.0F);
	}

	public BlockCarvable setStained(boolean a) {
		this.isAlpha = a;
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return isAlpha ? 1 : 0;
	}

	@Override
	public IIcon getIcon(int side, int metadata) {
		return carverHelper.getIcon(side, metadata);
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return carverHelper.getIcon(world, x, y, z, side);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
		carverHelper.registerBlockIcons(Cathedral.MODID, this, register);
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
		carverHelper.registerSubBlocks(this, tabs, list);
	}

	@Override
	public int getRenderType() {
		return ClientUtils.renderCTMId;
	}

	@Override
	public IVariationInfo getManager(IBlockAccess world, int x, int y, int z, int metadata) {
		return carverHelper.getVariation(metadata);
	}

	@Override
	public IVariationInfo getManager(int meta) {
		return carverHelper.getVariation(meta);
	}

	public static class SoundType extends Block.SoundType {

		public final String soundNameStep;
		public final String soundNameBreak;
		public final String soundNamePlace;

		//
		// * Creates a SoundType with automatic names for step and break sounds. Sound names dig.soundName and step.soundName must be specified in the sounds.json
		// * 
		// * @param soundName
		// *            block of the sound. Will automatically be expanded to "mod:dig.soundName" and "mod:step.soundName" respectively)
		// * @param volume
		// *            default 1.0f
		// * @param frequency
		// *            default 1.0f
		//
		public SoundType(String soundName, float volume, float frequency) {
			super(soundName, volume, frequency);
			this.soundNameStep = null;
			this.soundNameBreak = null;
			this.soundNamePlace = null;
		}

		// * Creates a SoundType with manual names for step and break sounds. Sound names must be specified in the sounds.json
		// * 
		// * @param soundNameBreak
		// *            block break sound
		// * @param soundNameStep
		// *            block step sound
		// * @param volume
		// *            default 1.0f
		// * @param frequency
		// *            default 1.0f
		// 
		public SoundType(String soundNameBreak, String soundNameStep, float volume, float frequency) {
			super(soundNameStep, volume, frequency);
			this.soundNameStep = soundNameStep;
			this.soundNameBreak = soundNameBreak;
			this.soundNamePlace = null;
		}

		//
		// * Creates a SoundType with manual names for step, break and place sounds. Sound names must be specified in the sounds.json
		// * 
		// * @param soundNameBreak
		// *            block break sound
		// * @param soundNameStep
		// *            block step sound
		// * @param soundNamePlace
		// *            block place sound
		// * @param volume
		// *            default 1.0f
		// * @param frequency
		// *            default 1.0f
		//
		public SoundType(String soundNameBreak, String soundNameStep, String soundNamePlace, float volume, float frequency) {
			super(soundNameStep, volume, frequency);
			this.soundNameStep = soundNameStep;
			this.soundNameBreak = soundNameBreak;
			this.soundNamePlace = soundNamePlace;
		}

		@Override
		public String getBreakSound() {
			if (soundNameBreak == null)
				return Cathedral.MODID + ":dig." + this.soundName;
			else
				return this.soundNameBreak;
		}

		@Override
		public String getStepResourcePath() {
			if (soundNameStep == null)
				return Cathedral.MODID + ":step." + this.soundName;
			else
				return this.soundNameStep;
		}

		@Override
		public String func_150496_b() {
			if (soundNamePlace == null)
				return getBreakSound();
			else
				return this.soundNamePlace;
		}
	}
*/
}