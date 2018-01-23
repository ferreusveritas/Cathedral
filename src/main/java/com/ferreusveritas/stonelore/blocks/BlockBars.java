package com.ferreusveritas.stonelore.blocks;

import java.util.List;

import com.cricketcraft.chisel.api.carving.CarvingUtils;
import com.cricketcraft.chisel.api.carving.ICarvingRegistry;
import com.ferreusveritas.stonelore.StoneLore;
import com.ferreusveritas.stonelore.renderers.RendererBars;

import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBars extends BlockPane {

	String name = "bars";
	int subBlocks = 6;
	
	@SideOnly(Side.CLIENT)
	private IIcon[] topIcons;
	private IIcon[] sideIcons;
	
	public BlockBars(Material material, boolean drops) {
		super("", "", material, drops);
		setBlockName(StoneLore.MODID + "_" + name);
		if (material == Material.rock || material == Material.iron) {
			setHarvestLevel("pickaxe", 0);
		}
		setResistance(20.0F);
		setHardness(2.5F);
		setCreativeTab(StoneLore.tabBasalt);
	}

	public BlockBars(){
		this(Material.iron, true);
	}
	
	public void addVariations(){
		ICarvingRegistry carving = CarvingUtils.getChiselRegistry();
		for(int meta = 0; meta < subBlocks; meta++){
			carving.addVariation("dwemerBars", this, meta, 1);
		}
	}
	
    
    @SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		
		topIcons = new IIcon[subBlocks];
		sideIcons = new IIcon[subBlocks];

		String names[] = {
				"dwembars",//0
				"dwembars-ornate",//1
				"dwembars-footer",//2
				"dwembars-header",//3
				"dwembars-mask",//4
				"dwembars-rombus",//5
		};
		
		for(int i = 0; i < subBlocks; i++){
			topIcons[i] = register.registerIcon(StoneLore.MODID + ":" + names[i] + "-top");
			sideIcons[i] = register.registerIcon(StoneLore.MODID + ":" + names[i] + "-side");
		}
		
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
		for(int meta = 0; meta < subBlocks; meta++){
				list.add(new ItemStack(item, 1, meta));
		}
	}

	@Override
	public int getRenderType() {
		return RendererBars.id;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {					
		if(side == 1){ return topIcons[metadata]; }
		return sideIcons[metadata];		
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

}