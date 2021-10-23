package com.nelttjen.tiles;

import com.nelttjen.config.CfgHandler;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileCobbler extends TileEntity {

    private ISidedInventory inv;

    private final int tier;
    private int countTimeout;
    private int generate;
    private int generateTick;
    private int outputTimeout;
    private int bufferMax;
    private int contains_cobble;
    private int count;

    public TileCobbler(int tier){
        this.contains_cobble = 0;
        this.tier = tier;

        this.bufferMax = 0;
        this.generateTick = 0;
        this.outputTimeout = 0;
        this.generate = 1;
        this.setConfiguration(tier);
    }

    @Override
    public void updateEntity(){
        if (this.worldObj.isRemote) {return;}
        if (this.contains_cobble > this.bufferMax) {
            this.contains_cobble = this.bufferMax;
        }
        if (this.count >= this.generateTick) {
            if (this.contains_cobble < this.bufferMax) {
                this.contains_cobble += this.generate;
            }
            this.count = 0;
        }
        this.count++;
        if (this.countTimeout >= this.outputTimeout){
            ForgeDirection forgeDirection = ForgeDirection.UP;
            try {inv = (ISidedInventory) worldObj.getTileEntity(xCoord + forgeDirection.offsetX, yCoord + forgeDirection.offsetY, zCoord + forgeDirection.offsetZ);} catch (Exception e) {
                IInventory invBasic;
                try {invBasic = (IInventory) worldObj.getTileEntity(xCoord + forgeDirection.offsetX, yCoord + forgeDirection.offsetY, zCoord + forgeDirection.offsetZ);} catch (Exception e2) {this.countTimeout = 0;return; }
                if (invBasic != null) {
                    for (int slot = 0; slot < invBasic.getSizeInventory(); slot++){
                        if (invBasic.getStackInSlot(slot) == null && invBasic.isItemValidForSlot(slot, cobbleStack(Math.min(this.contains_cobble, 64)))){
                            createCobbleStack(slot, invBasic, Math.min(Math.min(this.contains_cobble, 64), invBasic.getInventoryStackLimit()));
                            removeCobble(Math.min(Math.min(this.contains_cobble, 64), invBasic.getInventoryStackLimit()));
                            break;
                        }
                        else if (invBasic.getStackInSlot(slot) != null && invBasic.isItemValidForSlot(slot, cobbleStack(Math.min(this.contains_cobble, 64))) && invBasic.getInventoryStackLimit() > invBasic.getStackInSlot(slot).stackSize){
                            if (invBasic.getStackInSlot(slot).getItem() == Item.getItemFromBlock(Blocks.cobblestone)) {
                                int exist = invBasic.getStackInSlot(slot).stackSize;
                                int toFullStack = 64 - exist;
                                if (toFullStack == 0) {
                                    continue;
                                }
                                if (toFullStack <= this.contains_cobble) {
                                    createCobbleStack(slot, invBasic, 64);
                                    removeCobble(toFullStack);
                                    break;
                                } else {
                                    createCobbleStack(slot, invBasic, exist + this.contains_cobble);
                                    removeCobble(this.contains_cobble);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            if (inv != null) {
                for (int slot = 0; slot < inv.getSizeInventory(); slot++){
                    if (inv.getStackInSlot(slot) == null && inv.isItemValidForSlot(slot, cobbleStack(Math.min(this.contains_cobble, 64))) && inv.canInsertItem(slot, cobbleStack(Math.min(this.contains_cobble, 64)), forgeDirection.getOpposite().ordinal())){
                        createCobbleStack(slot, inv, Math.min(Math.min(this.contains_cobble, 64), inv.getInventoryStackLimit()));
                        removeCobble(Math.min(Math.min(this.contains_cobble, 64), inv.getInventoryStackLimit()));
                        break;
                    }
                    else if (inv.getStackInSlot(slot) != null && inv.isItemValidForSlot(slot, cobbleStack(Math.min(this.contains_cobble, 64))) && inv.canInsertItem(slot, cobbleStack(Math.min(this.contains_cobble, 64)), forgeDirection.getOpposite().ordinal()) && inv.getInventoryStackLimit() > inv.getStackInSlot(slot).stackSize){
                        if (inv.getStackInSlot(slot).getItem() == Item.getItemFromBlock(Blocks.cobblestone)) {
                            int exist = inv.getStackInSlot(slot).stackSize;
                            int toFullStack = 64 - exist;
                            if (toFullStack == 0) {
                                continue;
                            }
                            if (toFullStack <= this.contains_cobble) {
                                createCobbleStack(slot, inv, 64);
                                removeCobble(toFullStack);
                                break;
                            } else {
                                createCobbleStack(slot, inv, exist + this.contains_cobble);
                                removeCobble(this.contains_cobble);
                                break;
                            }
                        }
                    }
                }
            }
            this.countTimeout = 0;
        }
        this.countTimeout++;
        this.markDirty();
    }

    private void createCobbleStack(int slot, IInventory outInv, int amount) {
        ItemStack cobbleStack = cobbleStack(amount);
        outInv.setInventorySlotContents(slot, cobbleStack);
    }

    public void removeCobble(int amount) {
        this.contains_cobble -= amount;
    }


    private ItemStack cobbleStack(int amount) {
        return new ItemStack(Blocks.cobblestone, amount);
    }

    public void setConfiguration(int tier) {
        if (tier == 1) {
            this.bufferMax = CfgHandler.tier1maxBuffer;
            this.generateTick = CfgHandler.tier1genRate;
            this.outputTimeout = CfgHandler.tier1cooldownRate;
        } else if (tier == 2) {
            this.bufferMax = CfgHandler.tier2maxBuffer;
            this.generateTick = CfgHandler.tier2genRate;
            this.outputTimeout = CfgHandler.tier2cooldownRate;
        } else if (tier == 3) {
            this.bufferMax = CfgHandler.tier3maxBuffer;
            this.generateTick = CfgHandler.tier3genRate;
            this.outputTimeout = CfgHandler.tier3cooldownRate;
        } else if (tier == 4) {
            this.bufferMax = CfgHandler.tier4maxBuffer;
            this.generateTick = CfgHandler.tier4genRate;
            this.outputTimeout = CfgHandler.tier4cooldownRate;
        } else if (tier == 5) {
            this.bufferMax = CfgHandler.tier5maxBuffer;
            this.generateTick = CfgHandler.tier5genRate;
            this.outputTimeout = CfgHandler.tier5cooldownRate;
        } else if (tier == 6) {
            this.bufferMax = CfgHandler.superTierMaxBufer;
            this.outputTimeout = CfgHandler.superTierCooldownRate;
            this.generateTick = CfgHandler.superTierGenRate;
            this.generate = CfgHandler.superTierGenerate;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt){
        nbt.setInteger("contains_cobble", this.contains_cobble);
        nbt.setInteger("bufferMax", this.bufferMax);

        nbt.setInteger("generateTick", this.generateTick);
        nbt.setInteger("cooldown", this.outputTimeout);
        nbt.setInteger("generate", this.generate);

        super.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.contains_cobble = nbt.getInteger("contains_cobble");
        this.bufferMax = nbt.getInteger("bufferMax");

        this.generateTick = nbt.getInteger("generateTick");
        this.outputTimeout = nbt.getInteger("cooldown");
        this.generate = nbt.getInteger("generate");

        super.readFromNBT(nbt);
    }

    public int getContains_cobble() {
        return this.contains_cobble;
    }

    public int getBufferMax(){
        return this.bufferMax;
    }

    public int getTier() {
        return this.tier; // != 6 ? String.valueOf(this.tier) : "Super";
    }

    public void getCountTimeout() {
        System.out.println(this.countTimeout);
        System.out.println(this.outputTimeout);
    }
}
