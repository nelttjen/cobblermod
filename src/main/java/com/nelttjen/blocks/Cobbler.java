package com.nelttjen.blocks;

import com.nelttjen.config.CfgHandler;
import com.nelttjen.tiles.TileCobbler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class Cobbler extends Block implements ITileEntityProvider{
    TileCobbler cobbler;
    private IIcon[] icons = new IIcon[3 * 6];

    public Cobbler(String id, CreativeTabs creativeTab, float hardness, float resistance, String harvestTool, int harvestLevel) {
        super(Material.rock);
        this.setBlockName(id);
        this.setCreativeTab(creativeTab);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setHarvestLevel(harvestTool, harvestLevel);
        this.setBlockTextureName("cobblermod:cobbler_t");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return false;
        }
        {
            ItemStack playerIS = player.getCurrentEquippedItem();
            cobbler = (TileCobbler) world.getTileEntity(x, y, z);
            if (playerIS == null) {
                if (player.isSneaking() && CfgHandler.canShiftClick) {
                    ItemStack cobbleStack = new ItemStack(Blocks.cobblestone, Math.min(cobbler.getContains_cobble(), 64));
                    player.setCurrentItemOrArmor(0, cobbleStack);
                    cobbler.removeCobble(Math.min(cobbler.getContains_cobble(), 64));
                } else {
                    sendChatInfo(player);
                    cobbler.getCountTimeout();
                }

            }

        }
        return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
    }

    private void sendChatInfo(EntityPlayer player) {
//        player.addChatMessage(new ChatComponentText(I18n.format("chatMessage.cobblerActivate.1") + " " + cobbler.getContains_cobble()));
//        player.addChatMessage(new ChatComponentText(I18n.format("chatMessage.cobblerActivate.2") + " " + cobbler.getBufferMax()));
        if (cobbler.getTier() != 6) {
            player.addChatMessage(new ChatComponentText("Tier: " + cobbler.getTier()));
        }
        else player.addChatMessage(new ChatComponentText("Tier: Super"));
        player.addChatMessage(new ChatComponentText("\u0411\u0443\u043b\u044b\u0436\u043d\u0438\u043a: " + cobbler.getContains_cobble()));
        player.addChatMessage(new ChatComponentText("\u0412\u043c\u0435\u0441\u0442\u0438\u043c\u043e\u0441\u0442\u044c \u0445\u0440\u0430\u043d\u0438\u043b\u0438\u0449\u0430: " + cobbler.getBufferMax()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        for (int b = 1; b < 7; b++) {
            if (b != 6) {
                for (int i = 0; i < 6; i++) {
                    switch (i) {
                        case (0):
                            this.icons[3 * (b - 1)] = reg.registerIcon("cobblermod:cobbler_down");
                        case (1):
                            this.icons[1 + 3 * (b - 1)] = reg.registerIcon(this.textureName + b + "_side");
                    }
                    this.icons[2 + 3 * (b - 1)] = reg.registerIcon(this.textureName + b);
                }
            }
            else {
                for (int i = 0; i < 6; i++) {
                    switch (i) {
                        case (0):
                            this.icons[15] = reg.registerIcon("cobblermod:super_cobbler_down");
                        case (1):
                            this.icons[16] = reg.registerIcon(this.textureName + b + "_side");
                    }
                    this.icons[17] = reg.registerIcon(this.textureName + b);
                }
            }

        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        switch (side) {
            case (0):
                return this.icons[3 * meta];
            case (1):
                return this.icons[1 + 3 * meta];
            }
        return this.icons[2 + 3 * meta];
    }

    @Override
    public int damageDropped(int meta)
    {
        return meta;
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
        for (int i = 0; i < 6; i++){
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileCobbler(meta + 1);
    }

    public Class<TileCobbler> getTileEntityClass() {
        return TileCobbler.class;
    }
}
