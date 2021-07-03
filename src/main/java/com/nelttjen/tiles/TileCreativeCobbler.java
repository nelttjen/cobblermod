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
import org.lwjgl.Sys;


public class TileCreativeCobbler extends TileEntity {
    private int countTimeout;
    private int timeOut;
    public ISidedInventory inv;
    public TileCreativeCobbler() {
        this.timeOut = CfgHandler.creativeTimeOut;
    }

    @Override
    public void updateEntity(){
        if (this.worldObj.isRemote) {return;}
        if (this.timeOut != CfgHandler.creativeTimeOut){
            this.timeOut = CfgHandler.creativeTimeOut;
        }
        if (this.countTimeout >= this.timeOut) {
            for (ForgeDirection forgeDirection : ForgeDirection.VALID_DIRECTIONS) {
                try {inv = (ISidedInventory) worldObj.getTileEntity(xCoord + forgeDirection.offsetX, yCoord + forgeDirection.offsetY, zCoord + forgeDirection.offsetZ);} catch (Exception e) {
                    IInventory invBasic;
                    try {invBasic = (IInventory) worldObj.getTileEntity(xCoord + forgeDirection.offsetX, yCoord + forgeDirection.offsetY, zCoord + forgeDirection.offsetZ);} catch (Exception e2) {return;}
                    if (invBasic != null){
                        for (int slot = 0; slot < invBasic.getSizeInventory(); slot++){
                            if (invBasic.getStackInSlot(slot) == null && invBasic.isItemValidForSlot(slot, cobbleStack())){
                                createCobbleStack(slot, invBasic);
                            }
                        }
                    }
                }
                if (inv != null){
                    for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
                        if (inv.getStackInSlot(slot) == null && inv.isItemValidForSlot(slot, cobbleStack()) && inv.canInsertItem(slot, cobbleStack(), forgeDirection.getOpposite().ordinal())){
                            createCobbleStack(slot, inv);
                        }
                    }
                }

            }
            this.countTimeout = 0;
        }
        this.countTimeout++;
        this.markDirty();
    }

    private void createCobbleStack(int slot, IInventory outInv) {
        outInv.setInventorySlotContents(slot, cobbleStack());
    }

    private ItemStack cobbleStack() {
        return new ItemStack(Blocks.cobblestone, 64);
    }


    @Override
    public void writeToNBT(NBTTagCompound nbt){

        nbt.setInteger("cooldown", this.timeOut);

        super.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.timeOut = nbt.getInteger("cooldown");

        super.readFromNBT(nbt);
    }
}
