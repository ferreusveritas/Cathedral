package com.ferreusveritas.stonelore.blocks;

import java.util.List;

import com.ferreusveritas.stonelore.StoneLore;
import com.ferreusveritas.stonelore.renderers.RendererChain;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockChain extends Block {

	IIcon side;
	int subBlocks = 7;
	
	public BlockChain() {
		super(Material.iron);
		setBlockBounds(0.375f, 0f, 0.375f, 0.625f, 1f, 0.625f);
		setLightOpacity(0);
		setResistance(5.0F);
		setHardness(1.0F);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
		for(int meta = 0; meta < subBlocks; meta++){
				list.add(new ItemStack(item, 1, meta));
		}
	}
	
	@Override
	public int damageDropped(int i) {
		return i;
	}
	
    @Override
	public boolean renderAsNormalBlock() {
        return false;
    }
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
    @SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		side = register.registerIcon(StoneLore.MODID + ":" + "chain");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return this.side;
	}
    
	public int getChainColor(int subBlock){
		switch(subBlock){
			case 0: return 0xd7d7d7;//Iron
			case 1: return 0xe0b820;//Gold
			case 2: return 0xc3a84e;//Dwemer
			case 3: return 0xbe6131;//Copper
			case 4: return 0xa76b21;//Bronze
			case 5: return 0xd6dadd;//Silver
			case 6: return 0x44b8b8;//Enderium
			default: return 0xFF00FF;//Nothing
		}
	}
	
	@Override
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		return getChainColor(blockAccess.getBlockMetadata(x, y, z));
	}

	@Override
	public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
		return true;
	}
	
	@Override
	public int getRenderType() {
		return RendererChain.id;
	}

}
