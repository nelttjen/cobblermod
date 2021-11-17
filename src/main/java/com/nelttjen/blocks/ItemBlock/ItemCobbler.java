package com.nelttjen.blocks.ItemBlock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;


public class ItemCobbler extends ItemBlockWithMetadata {

    public ItemCobbler(Block block) {super(block, block);}

    @Override
    public String getUnlocalizedName(ItemStack item) {
        return this.getUnlocalizedName() + item.getItemDamage();

    }
}
