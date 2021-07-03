package com.nelttjen.config;

import com.nelttjen.lib.Variables;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class CfgHandler {
    private static Configuration configuration;

    //variables for other thngs
    public static int tier1genRate;
    public static int tier1cooldownRate;
    public static int tier1maxBuffer;

    public static int tier2genRate;
    public static int tier2cooldownRate;
    public static int tier2maxBuffer;

    public static int tier3genRate;
    public static int tier3cooldownRate;
    public static int tier3maxBuffer;

    public static int tier4genRate;
    public static int tier4cooldownRate;
    public static int tier4maxBuffer;

    public static int tier5genRate;
    public static int tier5cooldownRate;
    public static int tier5maxBuffer;

    public static int superTierGenRate;
    public static int superTierCooldownRate;
    public static int superTierMaxBufer;
    public static int superTierGenerate;

    public static boolean doesCreativeGenExist;
    public static int creativeTimeOut;

    public static boolean canShiftClick;
    public static boolean useRussianLocalization;

    public static void init(String configDir){
        if (configuration == null){
            File path = new File(configDir + "/" + Variables.MODID + ".cfg");

            configuration = new Configuration(path);
            loadConfig();
        }

    }





    private static void loadConfig(){
        useRussianLocalization = configuration.getBoolean("useRussianLocalization", "L", false, "Does mod need to use Russian localization of chat messages? (I was very lazy to do this to depends on minecraft language)");
        canShiftClick = configuration.getBoolean("canShiftClick", "I", true, "Can player ShiftLClick with empty hand or CobbleStack to get CobbleStack from generator block");

        tier1genRate = configuration.getInt("tier1generationTick", "I", 40, 1, 2147483647,"Generation rate to 1 cobblestone for Tier 1 Cobble Gen (in ticks)");
        tier2genRate = configuration.getInt("tier2generationTick", "I", 20, 1, 2147483647,"Generation rate to 1 cobblestone for Tier 2 Cobble Gen (in ticks)");
        tier3genRate = configuration.getInt("tier3generationTick", "I", 10, 1, 2147483647,"Generation rate to 1 cobblestone for Tier 3 Cobble Gen (in ticks)");
        tier4genRate = configuration.getInt("tier4generationTick", "I", 5, 1, 2147483647,"Generation rate to 1 cobblestone for Tier 4 Cobble Gen (in ticks)");
        tier5genRate = configuration.getInt("tier5generationTick", "I", 1, 1, 2147483647,"Generation rate to 1 cobblestone for Tier 5 Cobble Gen (in ticks)");

        tier1cooldownRate = configuration.getInt("tier1cooldownTick", "I", 20, 1, 2147483647, "Tier 1 Cobbler will checks one in n tick for available inventories");
        tier2cooldownRate = configuration.getInt("tier2cooldownTick", "I", 20, 1, 2147483647, "Tier 2 Cobbler will checks one in n tick for available inventories");
        tier3cooldownRate = configuration.getInt("tier3cooldownTick", "I", 20, 1, 2147483647, "Tier 3 Cobbler will checks one in n tick for available inventories");
        tier4cooldownRate = configuration.getInt("tier4cooldownTick", "I", 20, 1, 2147483647, "Tier 4 Cobbler will checks one in n tick for available inventories");
        tier5cooldownRate = configuration.getInt("tier5cooldownTick", "I", 20, 1, 2147483647, "Tier 5 Cobbler will checks one in n tick for available inventories");

        tier1maxBuffer = configuration.getInt("tier1maxBufferSize", "I", 1000, 64, 2147483647, "Tier 1 Cobbler maximum internal storage");
        tier2maxBuffer = configuration.getInt("tier2maxBufferSize", "I", 5000, 64, 2147483647, "Tier 2 Cobbler maximum internal storage");
        tier3maxBuffer = configuration.getInt("tier3maxBufferSize", "I", 10000, 64, 2147483647, "Tier 3 Cobbler maximum internal storage");
        tier4maxBuffer = configuration.getInt("tier4maxBufferSize", "I", 50000, 64, 2147483647, "Tier 4 Cobbler maximum internal storage");
        tier5maxBuffer = configuration.getInt("tier5maxBufferSize", "I", 100000, 64, 2147483647, "Tier 5 Cobbler maximum internal storage");

        superTierGenRate = configuration.getInt("tierSuperGenerationTick", "I", 1, 1, 2147483647,"Generation rate to n cobblestone for Tier Super Cobble Gen (in ticks)");
        superTierCooldownRate = configuration.getInt("tierSuperCooldownTick", "I", 1, 1, 2147483647, "Tier Super Cobbler will checks one in n tick for available inventories (Recomended same as \"tierSuperGenerationTick\")");
        superTierMaxBufer = configuration.getInt("tierSuperMaxBufferSize", "I", 128, 64, 2147483647, "Tier Super Cobbler maximum internal storage");
        superTierGenerate = configuration.getInt("tierSuperGenerateCobble", "I", 64, 1, 64, "How many cobblestone will Tier Super Cobbler generate in x tick");

        doesCreativeGenExist = configuration.getBoolean("doesCreativeGenExist", "C",true, "Disable if you don't want to see this MLG ULTRA GENERATOR");
        creativeTimeOut = configuration.getInt("creativeTimeOut", "C", 1, 1, 2147483647, "Creative generator will checks one in n tick for all available inventories and slots");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangeEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equalsIgnoreCase(Variables.MODID)) {
            loadConfig();
        }
    }

    public static Configuration getConfiguration () {
        return configuration;
    }

}
