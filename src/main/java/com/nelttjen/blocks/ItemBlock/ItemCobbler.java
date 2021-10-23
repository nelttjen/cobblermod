package com.nelttjen.blocks.ItemBlock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemCobbler extends ItemBlockWithMetadata {

    public ItemCobbler(Block block) {super(block, block);}

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return this.getUnlocalizedName() + item.getItemDamage();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack item, EntityPlayer player, List par3List, boolean advanced) {
        int i = item.getItemDamage();
        par3List.add(I18n.format("tooltip.cobbler.t" + (i + 1)));
    }
}
