package com.nelttjen.blocks;

import com.nelttjen.config.CfgHandler;
import com.nelttjen.tiles.TileCobblerTier3;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
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


public class CoblerTier3 extends Block implements ITileEntityProvider {

    private IIcon[] icons = new IIcon[3];

    public CoblerTier3(String id, CreativeTabs creativeTab, float hardness, float resistance, String harvestTool, int harvestLevel) {
        super(Material.rock);
        this.setBlockName(id);
        this.setCreativeTab(creativeTab);
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setHarvestLevel(harvestTool, harvestLevel);
        this.setBlockTextureName("cobblermod:t3cobbler");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
        if (world.isRemote) {return false;}
        {
            ItemStack playerIS = player.getCurrentEquippedItem();
            TileCobblerTier3 cobbler = (TileCobblerTier3) world.getTileEntity(x, y, z);
            if (playerIS == null) {
                if (player.isSneaking() && CfgHandler.canShiftClick){
                    ItemStack cobbleStack = new ItemStack(Blocks.cobblestone, Math.min(cobbler.getContains_cobble(), 64));
                    player.setCurrentItemOrArmor(0, cobbleStack);
                    cobbler.removeCobble(Math.min(cobbler.getContains_cobble(), 64));
                }
                else {
                    player.addChatMessage(new ChatComponentText(I18n.format("chatMessage.cobblerActivate.1") + " " + cobbler.getContains_cobble()));
                    player.addChatMessage(new ChatComponentText(I18n.format("chatMessage.cobblerActivate.2") + " " + cobbler.getBufferMax()));
                }

            }

        }
        return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileCobblerTier3();
    }

    public Class<TileCobblerTier3> getTileEntityClass() {
        return TileCobblerTier3.class;
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
