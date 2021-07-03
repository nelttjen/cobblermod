package com.nelttjen.tiles;


import com.nelttjen.config.CfgHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
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
    private IInventory outInv;

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
            for (ForgeDirection forgeDirection : ForgeDirection.VALID_DIRECTIONS) {
                Block block = worldObj.getBlock(xCoord + forgeDirection.offsetX, yCoord + forgeDirection.offsetY, zCoord + forgeDirection.offsetZ);
                if (block instanceof BlockContainer && forgeDirection == ForgeDirection.UP) {
                    try {outInv = (IInventory) worldObj.getTileEntity(xCoord + forgeDirection.offsetX, yCoord + forgeDirection.offsetY, zCoord + forgeDirection.offsetZ);} catch (Exception e) {return;}
                    for (int slot = 0; slot < outInv.getSizeInventory(); slot++) {
                        if (this.contains_cobble == 0) {
                            break;
                        }
                        if (outInv.getStackInSlot(slot) != null) {
                            if (outInv.getStackInSlot(slot).getItem() == Item.getItemFromBlock(Blocks.cobblestone) && outInv.getStackInSlot(slot).stackSize < 64 && outInv.isItemValidForSlot(slot, new ItemStack(Blocks.cobblestone, 64))) {
                                int needToFullStack = 64 - outInv.getStackInSlot(slot).stackSize;
                                if (needToFullStack <= this.contains_cobble) {
                                    createCobbleStack(slot, outInv, 64);
                                    removeCobble(needToFullStack);
                                    break;
                                } else {
                                    createCobbleToExistStack(slot, outInv, this.contains_cobble);
                                    removeCobble(Math.min(this.contains_cobble, 64));
                                    break;
                                }
                            }
                        }
                        else if (outInv.getStackInSlot(slot) == null && outInv.isItemValidForSlot(slot, new ItemStack(Blocks.cobblestone, 64))) {
                            createCobbleStack(slot, outInv, this.contains_cobble);
                            removeCobble(Math.min(this.contains_cobble, 64));
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

    private void createCobbleToExistStack(int slot, IInventory outInv, int contains_cobble){
        int canMake = outInv.getStackInSlot(slot).stackSize + contains_cobble;
        ItemStack cobbleStack = cobbleStack(canMake);
        outInv.setInventorySlotContents(slot, cobbleStack);
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
