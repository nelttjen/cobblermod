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
import org.lwjgl.Sys;

public class CommonProxy {

    Cobbler cobbler;
    static CompressedCobble compressedCobble;

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

    private void createCobblers() {
        cobbler = new Cobbler("blockCobbler", CobblerMod.COBBLER, 1.5f, 5, "pickaxe", 1);
        GameRegistry.registerBlock(cobbler, ItemCobbler.class, cobbler.getUnlocalizedName());
        GameRegistry.registerTileEntity(cobbler.getTileEntityClass(), "tile_blockCobbler");
    }

    private static void createCompressedCobble() {
        compressedCobble = new CompressedCobble("cobbler_cobblestone");
        GameRegistry.registerBlock(compressedCobble, ItemCompressedCobble.class, compressedCobble.getUnlocalizedName());
    }

    private void registerCobblerRecipes() {
        GameRegistry.addRecipe(new ItemStack(GameRegistry.findBlock(Variables.MODID, cobbler.getUnlocalizedName()), 1, 0),
            "SSS", "LGW", "SSS",
            ('S'), Blocks.cobblestone,
            ('L'), Items.lava_bucket,
            ('G'), Blocks.glass,
            ('W'), Items.water_bucket);
        Object[] materials = new Object[5];
        materials[0] = Items.iron_ingot;
        materials[1] = Items.gold_ingot;
        materials[2] = Items.diamond;
        materials[3] = Items.emerald;
        materials[4] = Items.nether_star;
        for (int i = 0; i < 5; i++) {
            GameRegistry.addRecipe(new ItemStack(GameRegistry.findBlock(Variables.MODID, cobbler.getUnlocalizedName()), 1, i + 1),
                    "MMM", "MCM", "MMM",
                    ('M'), materials[i],
                    ('C'), new ItemStack(GameRegistry.findBlock(Variables.MODID, cobbler.getUnlocalizedName()), 1, i));
        }
    }

    private static void recipeCompressedCobble() {
        GameRegistry.addRecipe(new ItemStack(GameRegistry.findBlock(Variables.MODID, compressedCobble.getUnlocalizedName()), 1, 0), new Object[]{
                "XXX", "XXX", "XXX", ('X'), Blocks.cobblestone
        });
        for (int i = 0; i < 7; i++){
            GameRegistry.addRecipe(new ItemStack(GameRegistry.findBlock(Variables.MODID, compressedCobble.getUnlocalizedName()), 1, i + 1), new Object[]{
                    "XXX", "XXX", "XXX", ('X'), new ItemStack(GameRegistry.findBlock(Variables.MODID, compressedCobble.getUnlocalizedName()), 1, i)
            });
        }
    }

    public void preInit(FMLPreInitializationEvent event)
    {
        loadCfg(event);

        createCobblers();
        setCreativeCobbler(CfgHandler.doesCreativeGenExist);
        if (CfgHandler.createCobble && !Loader.isModLoaded("ExtraUtilities")) {
            createCompressedCobble();
        }
    }

    public void init(FMLInitializationEvent event)
    {
        registerCobblerRecipes();
        if (CfgHandler.createCobble && !Loader.isModLoaded("ExtraUtilities")) {
            recipeCompressedCobble();
        }
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

}
