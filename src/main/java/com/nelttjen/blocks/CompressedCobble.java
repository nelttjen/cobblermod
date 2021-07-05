package com.nelttjen.blocks;

import com.nelttjen.CobblerMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;
import java.util.Locale;

public class CompressedCobble extends Block {


    @SideOnly(Side.CLIENT)
    private IIcon[] icons = new IIcon[8];

    public CompressedCobble(String id) {
        super(Material.rock);
        this.setBlockName(id);
        this.setCreativeTab(CobblerMod.COBBLER);
        this.setHardness(1.5F);
        this.setResistance(5);
        this.setHarvestLevel("pickaxe", 1);
        this.setBlockTextureName("cobblermod:cobblestone");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        for (int i = 0; i < 8; i++) {
            this.icons[i] = reg.registerIcon(this.textureName + (i + 1));
        }
    }


    @Override
    public int damageDropped(int meta)
    {
        return meta;
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
        for (int i = 0; i < 8; i++){
            list.add(new ItemStack(item, 1, i));
        }
    }
    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon (int side, int meta){
        if (meta < 0 || meta > this.icons.length){
            return this.icons[0];
        }
        return this.icons[meta];
    }

}
