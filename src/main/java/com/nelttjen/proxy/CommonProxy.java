package com.nelttjen.proxy;

import com.nelttjen.CobblerMod;
import com.nelttjen.blocks.*;
import com.nelttjen.blocks.ItemBlock.ItemCobbler;
import com.nelttjen.blocks.ItemBlock.ItemCompressedCobble;
import com.nelttjen.config.CfgHandler;
import com.nelttjen.lib.Variables;

import com.nelttjen.tiles.TileCobbler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CommonProxy {

    private void loadCfg(FMLPreInitializationEvent event) {
        String cfgDir = event.getModConfigurationDirectory().toString();
        CfgHandler.init(cfgDir);
        FMLCommonHandler.instance().bus().register(new CfgHandler());
    }

    private static void setCreativeCobbler(boolean sure) {
        if (sure) {
            CreativeCobbler creativeCobbler = new CreativeCobbler("creative_cobbler", CobblerMod.COBBLER, 1.5f, 5, "pickaxe", 1);
            GameRegistry.registerBlock(creativeCobbler, "creative_cobbler");
            GameRegistry.registerTileEntity(creativeCobbler.getTileEntityClass(), "tile_creative_cobbler");
        }
    }

    public void preInit(FMLPreInitializationEvent event)
    {
        loadCfg(event);
        Cobbler cobbler = new Cobbler("blockCobbler", CobblerMod.COBBLER, 1.5f, 5, "pickaxe", 1);
        GameRegistry.registerBlock(cobbler, ItemCobbler.class, cobbler.getUnlocalizedName());
        GameRegistry.registerTileEntity(cobbler.getTileEntityClass(), "tile_blockCobbler");

        setCreativeCobbler(CfgHandler.doesCreativeGenExist);
    }

    public void init(FMLInitializationEvent event)
    {

    }

    public void postInit(FMLPostInitializationEvent event) {

    }

}
