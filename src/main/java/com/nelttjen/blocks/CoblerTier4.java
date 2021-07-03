package com.nelttjen.blocks;

import com.nelttjen.config.CfgHandler;
import com.nelttjen.tiles.TileCobblerTier4;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;


public class CoblerTier4 extends Block implements ITileEntityProvider {

    private IIcon[] icons = new IIcon[3];

    public CoblerTier4(String id, CreativeTabs creativeTab, float hardness, float resistance, String harvestTool, int harvestLevel) {
        super(Material.rock);
        this.setBlockName(id);
        this.setCreativeTab(creativeTab);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setHarvestLevel(harvestTool, harvestLevel);
        this.setBlockTextureName("cobblermod:t4cobbler");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
        if (world.isRemote) {return false;}
        {
            ItemStack playerIS = player.getCurrentEquippedItem();
            TileCobblerTier4 cobbler = (TileCobblerTier4) world.getTileEntity(x, y, z);
            if (playerIS == null) {
                if (player.isSneaking() && CfgHandler.canShiftClick){
                    ItemStack cobbleStack = new ItemStack(Blocks.cobblestone, Math.min(cobbler.getContains_cobble(), 64));
                    player.setCurrentItemOrArmor(0, cobbleStack);
                    cobbler.removeCobble(Math.min(cobbler.getContains_cobble(), 64));
                }
                else {
                    if (!CfgHandler.useRussianLocalization){
                        player.addChatMessage(new ChatComponentTranslation("Cobblestone:" + " " + cobbler.getContains_cobble()));
                        player.addChatMessage(new ChatComponentText("Max internal buffer size:" + " " + cobbler.getBufferMax()));
                    }
                    else {
                        player.addChatMessage(new ChatComponentText("\u0411\u0443\u043b\u044b\u0436\u043d\u0438\u043a:" + " " + cobbler.getContains_cobble()));
                        player.addChatMessage(new ChatComponentText("\u0412\u043c\u0435\u0441\u0442\u0438\u043c\u043e\u0441\u0442\u044c \u0445\u0440\u0430\u043d\u0438\u043b\u0438\u0449\u0430:" + " " + cobbler.getBufferMax()));
                    }
                }

            }

        }
        return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileCobblerTier4();
    }

    public Class<TileCobblerTier4> getTileEntityClass() {
        return TileCobblerTier4.class;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        for (int i = 0; i < 6; i ++) {
            switch (i) {
                case (0):
                    this.icons[0] = reg.registerIcon("cobblermod:cobbler_down");
                case (1):
                    this.icons[1] = reg.registerIcon(this.textureName + "_side");
            }
            this.icons[2] = reg.registerIcon(this.textureName);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        switch (side) {
            case (0):
                return this.icons[0];
            case (1):
                return this.icons[1];
        }
        return this.icons[2];
    }
}
