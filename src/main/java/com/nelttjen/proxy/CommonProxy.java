package com.nelttjen.proxy;

import com.nelttjen.CobblerMod;
import com.nelttjen.blocks.*;
import com.nelttjen.blocks.ItemBlock.ItemCompressedCobble;
import com.nelttjen.config.CfgHandler;
import com.nelttjen.lib.Variables;
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
    static CoblerTier1 cobblerTier1;
    static CoblerTier2 cobblerTier2;
    static CoblerTier3 cobblerTier3;
    static CoblerTier4 cobblerTier4;
    static CoblerTier5 cobblerTier5;
    static CoblerTierSuper cobblerTierSuper;
    static CreativeCobbler creativeCobbler;
    static CompressedCobble compressedCobble;
    private static void loadBlocks() {
        cobblerTier1 = new CoblerTier1("cobbler_tier_1", CobblerMod.COBBLER, 1.5f, 5, "pickaxe", 1);
        cobblerTier2 = new CoblerTier2("cobbler_tier_2", CobblerMod.COBBLER, 1.5f, 5, "pickaxe", 1);
        cobblerTier3 = new CoblerTier3("cobbler_tier_3", CobblerMod.COBBLER, 1.5f, 5, "pickaxe", 1);
        cobblerTier4 = new CoblerTier4("cobbler_tier_4", CobblerMod.COBBLER, 1.5f, 5, "pickaxe", 1);
        cobblerTier5 = new CoblerTier5("cobbler_tier_5", CobblerMod.COBBLER, 1.5f, 5, "pickaxe", 1);
        cobblerTierSuper = new CoblerTierSuper("cobbler_tier_super", CobblerMod.COBBLER, 1.5f, 5, "pickaxe", 1);

    }
    private static void registerBlocks() {
        GameRegistry.registerBlock(cobblerTier1, "cobbler_tier_1");
        GameRegistry.registerBlock(cobblerTier2, "cobbler_tier_2");
        GameRegistry.registerBlock(cobblerTier3, "cobbler_tier_3");
        GameRegistry.registerBlock(cobblerTier4, "cobbler_tier_4");
        GameRegistry.registerBlock(cobblerTier5, "cobbler_tier_5");
        GameRegistry.registerBlock(cobblerTierSuper, "cobbler_tier_super");
    }
    private static void registerTileEntities() {
        GameRegistry.registerTileEntity(cobblerTier1.getTileEntityClass(), "tile_cobbler_tier_1");
        GameRegistry.registerTileEntity(cobblerTier2.getTileEntityClass(), "tile_cobbler_tier_2");
        GameRegistry.registerTileEntity(cobblerTier3.getTileEntityClass(), "tile_cobbler_tier_3");
        GameRegistry.registerTileEntity(cobblerTier4.getTileEntityClass(), "tile_cobbler_tier_4");
        GameRegistry.registerTileEntity(cobblerTier5.getTileEntityClass(), "tile_cobbler_tier_5");
        GameRegistry.registerTileEntity(cobblerTierSuper.getTileEntityClass(), "tile_cobbler_tier_super");
    }
    private static void setCreativeCobbler(boolean sure) {
        if (sure) {
            creativeCobbler = new CreativeCobbler("creative_cobbler", CobblerMod.COBBLER, 1.5f, 5, "pickaxe", 1);
            GameRegistry.registerBlock(creativeCobbler, "creative_cobbler");
            GameRegistry.registerTileEntity(creativeCobbler.getTileEntityClass(), "tile_creative_cobbler");
        }
    }
    private static void createCompressedCobble() {
        compressedCobble = new CompressedCobble("cobbler_cobblestone");
        GameRegistry.registerBlock(compressedCobble, ItemCompressedCobble.class, compressedCobble.getUnlocalizedName());
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
    private static void registerShapedRecipes() {
//        Object tier1 = new Object[]{
//                "SSS", "LGW", "SSS",
//                ('S'), Blocks.stone,
//                ('L'), Items.lava_bucket,
//                ('G'), Blocks.glass,
//                ('W'), Items.water_bucket
//        };
        GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(GameRegistry.findBlock(Variables.MODID, "cobbler_tier_1"))), new Object[]{
                "SSS", "LGW", "SSS",
                ('S'), Blocks.cobblestone,
                ('L'), Items.lava_bucket,
                ('G'), Blocks.glass,
                ('W'), Items.water_bucket
        });
        GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(GameRegistry.findBlock(Variables.MODID, "cobbler_tier_2"))), new Object[]{
                "III", "ICI", "III",
                ('I'), Items.iron_ingot,
                ('C'), Item.getItemFromBlock(GameRegistry.findBlock(Variables.MODID, "cobbler_tier_1"))
        });
        GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(GameRegistry.findBlock(Variables.MODID, "cobbler_tier_3"))), new Object[]{
                "GGG", "GCG", "GGG",
                ('G'), Items.gold_ingot,
                ('C'), Item.getItemFromBlock(GameRegistry.findBlock(Variables.MODID, "cobbler_tier_2"))
        });
        GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(GameRegistry.findBlock(Variables.MODID, "cobbler_tier_4"))), new Object[]{
                "DDD", "DCD", "DDD",
                ('D'), Items.diamond,
                ('C'), Item.getItemFromBlock(GameRegistry.findBlock(Variables.MODID, "cobbler_tier_3"))
        });
        GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(GameRegistry.findBlock(Variables.MODID, "cobbler_tier_5"))), new Object[]{
                "EEE", "ECE", "EEE",
                ('E'), Items.emerald,
                ('C'), Item.getItemFromBlock(GameRegistry.findBlock(Variables.MODID, "cobbler_tier_4"))
        });
        GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(GameRegistry.findBlock(Variables.MODID, "cobbler_tier_super"))), new Object[]{
                "SSS", "SCS", "SSS",
                ('S'), Items.nether_star,
                ('C'), Item.getItemFromBlock(GameRegistry.findBlock(Variables.MODID, "cobbler_tier_5"))
        });
    }
    private void loadCfg(FMLPreInitializationEvent event) {
        String cfgDir = event.getModConfigurationDirectory().toString();
        CfgHandler.init(cfgDir);
        FMLCommonHandler.instance().bus().register(new CfgHandler());
    }
    public void preInit(FMLPreInitializationEvent event)
    {
        loadBlocks();
        registerBlocks();
        registerTileEntities();
        loadCfg(event);

        setCreativeCobbler(CfgHandler.doesCreativeGenExist);

        if (CfgHandler.createCobble && !Loader.isModLoaded("ExtraUtilities")) {
            createCompressedCobble();
        }
    }

    public void init(FMLInitializationEvent event)
    {
        registerShapedRecipes();

        if (CfgHandler.createCobble && !Loader.isModLoaded("ExtraUtilities")) {
            recipeCompressedCobble();
        }
    }

    public void postInit(FMLPostInitializationEvent event) {

    }

}
