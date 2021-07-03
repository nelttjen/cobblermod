package com.nelttjen.blocks;

import com.nelttjen.tiles.TileCreativeCobbler;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


public class CreativeCobbler extends Block implements ITileEntityProvider {

    public CreativeCobbler(String id, CreativeTabs creativeTab, float hardness, float resistance, String harvestTool, int harvestLevel) {
        super(Material.rock);
        this.setBlockName(id);
        this.setCreativeTab(creativeTab);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setHarvestLevel(harvestTool, harvestLevel);
        this.setBlockTextureName("cobblermod:creative_cobbler");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileCreativeCobbler();
    }

    public Class<TileCreativeCobbler> getTileEntityClass() {
        return TileCreativeCobbler.class;
    }

}
