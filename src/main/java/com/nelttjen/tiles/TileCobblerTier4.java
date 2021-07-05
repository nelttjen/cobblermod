package com.nelttjen.tiles;


import com.nelttjen.config.CfgHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;


public class TileCobblerTier4 extends TileEntity {
    private int generateTick;
    private int count;
    private int countTimeout;
    private int outputTimeout;
    private int bufferMax;
    private int contains_cobble;
    private ISidedInventory inv;

    public TileCobblerTier4() {
        this.contains_cobble = 0;
        this.bufferMax = CfgHandler.tier4maxBuffer;
        this.outputTimeout = CfgHandler.tier4cooldownRate;
        this.generateTick = CfgHandler.tier4genRate;
    }

    @Override
    public void updateEntity(){
        if (this.worldObj.isRemote) {return;}
        if (this.bufferMax != CfgHandler.tier4maxBuffer || this.generateTick != CfgHandler.tier4genRate || this.outputTimeout != CfgHandler.tier4cooldownRate){
            this.bufferMax = CfgHandler.tier4maxBuffer;
            this.generateTick = CfgHandler.tier4genRate;
            this.outputTimeout = CfgHandler.tier4cooldownRate;
        }
        if (this.contains_cobble > this.bufferMax) {
            this.contains_cobble = this.bufferMax;
        }
        if (this.count >= this.generateTick) {
            if (this.contains_cobble < this.bufferMax) {
                this.contains_cobble += 1;
            }
            this.count = 0;
        }
        this.count++;
        if (this.countTimeout >= this.outputTimeout) {
            ForgeDirection forgeDirection = ForgeDirection.UP;
            try {inv = (ISidedInventory) worldObj.getTileEntity(xCoord + forgeDirection.offsetX, yCoord + forgeDirection.offsetY, zCoord + forgeDirection.offsetZ);} catch (Exception e) {
                IInventory invBasic;
                try {invBasic = (IInventory) worldObj.getTileEntity(xCoord + forgeDirection.offsetX, yCoord + forgeDirection.offsetY, zCoord + forgeDirection.offsetZ);} catch (Exception e2) {this.countTimeout = 0;return; }
                if (invBasic != null) {
                    for (int slot = 0; slot < invBasic.getSizeInventory(); slot++){
                        if (invBasic.getStackInSlot(slot) == null && invBasic.isItemValidForSlot(slot, cobbleStack(Math.min(this.contains_cobble, 64)))){
                            createCobbleStack(slot, invBasic, Math.min(this.contains_cobble, 64));
                            removeCobble(Math.min(this.contains_cobble, 64));
                            break;
                        }
                        else if (invBasic.getStackInSlot(slot) != null && invBasic.isItemValidForSlot(slot, cobbleStack(Math.min(this.contains_cobble, 64)))){
                            int exist = invBasic.getStackInSlot(slot).stackSize;
                            int toFullStack = 64 - exist;
                            if (toFullStack == 0) {continue;}
                            if (toFullStack <= this.contains_cobble){
                                createCobbleStack(slot, invBasic, 64);
                                removeCobble(toFullStack);
                                break;
                            }
                            else {
                                createCobbleStack(slot, invBasic, exist + this.contains_cobble);
                                removeCobble(this.contains_cobble);
                                break;
                            }
                        }
                    }
                }
            }
            if (inv != null) {
                for (int slot = 0; slot < inv.getSizeInventory(); slot++){
                    if (inv.getStackInSlot(slot) == null && inv.isItemValidForSlot(slot, cobbleStack(Math.min(this.contains_cobble, 64))) && inv.canInsertItem(slot, cobbleStack(Math.min(this.contains_cobble, 64)), forgeDirection.getOpposite().ordinal())){
                        createCobbleStack(slot, inv, Math.min(this.contains_cobble, 64));
                        removeCobble(Math.min(this.contains_cobble, 64));
                        break;
                    }
                    else if (inv.getStackInSlot(slot) != null && inv.isItemValidForSlot(slot, cobbleStack(Math.min(this.contains_cobble, 64))) && inv.canInsertItem(slot, cobbleStack(Math.min(this.contains_cobble, 64)), forgeDirection.getOpposite().ordinal())){
                        int exist = inv.getStackInSlot(slot).stackSize;
                        int toFullStack = 64 - exist;
                        if (toFullStack == 0) {continue;}
                        if (toFullStack <= this.contains_cobble){
                            createCobbleStack(slot, inv, 64);
                            removeCobble(toFullStack);
                            break;
                        }
                        else {
                            createCobbleStack(slot, inv, exist + this.contains_cobble);
                            removeCobble(this.contains_cobble);
                            break;
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

    private ItemStack cobbleStack(int amount) {
        return new ItemStack(Blocks.cobblestone, amount);
    }


    @Override
    public void writeToNBT(NBTTagCompound nbt){
        nbt.setInteger("contains_cobble", this.contains_cobble);
        nbt.setInteger("bufferMax", this.bufferMax);

        nbt.setInteger("generateTick", this.generateTick);
        nbt.setInteger("cooldown", this.outputTimeout);

        super.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.contains_cobble = nbt.getInteger("contains_cobble");
        this.bufferMax = nbt.getInteger("bufferMax");

        this.generateTick = nbt.getInteger("generateTick");
        this.outputTimeout = nbt.getInteger("cooldown");

        super.readFromNBT(nbt);
    }

    public int getContains_cobble() {
        return this.contains_cobble;
    }

    public void removeCobble(int amount){
        this.contains_cobble -= amount;
    }

    public int getBufferMax(){
        return this.bufferMax;
    }
}
