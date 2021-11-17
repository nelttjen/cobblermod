package com.nelttjen;


import com.nelttjen.lib.Variables;
import com.nelttjen.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


@Mod(
        modid = Variables.MODID,
        version = Variables.VERSION,
        name = Variables.MODNAME
)

public class CobblerMod {

    //PROXY SETTINGS
    @SidedProxy(clientSide = Variables.CLIENTSIDE, serverSide = Variables.SERVERSIDE)
    public static CommonProxy proxy;
    //PROXY SETTINGS END

    //CREATE CREATIVE TAB
    public static final CreativeTabs COBBLER = new CreativeTabs("cobblerGroup") {
        @Override
        public Item getTabIconItem() {
            return GameRegistry.findItem(Variables.MODID, "tile.blockCobbler");
        }
    };
    //CREATE CREATIVE TAB END

    //INIT
    //Pre init
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    //init
    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    //postinit
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}

