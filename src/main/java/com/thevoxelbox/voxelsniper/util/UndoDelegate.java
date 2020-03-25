
package com.thevoxelbox.voxelsniper.util;

import com.thevoxelbox.voxelsniper.MagicValues;
import com.thevoxelbox.voxelsniper.Undo;
import org.bukkit.BlockChangeDelegate;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

/**
 *
 */
public class UndoDelegate implements BlockChangeDelegate
{
    private final World targetWorld;
    private Undo currentUndo;
        
    public Undo getUndo()
    {
        final Undo pastUndo = currentUndo;
        currentUndo = new Undo();
        return pastUndo;
    }

    public UndoDelegate(World targetWorld)
    {
        this.targetWorld = targetWorld;
        this.currentUndo = new Undo();
    }

    @Override
    public boolean setBlockData(int x, int y, int z, BlockData blockData) {
        this.currentUndo.put(targetWorld.getBlockAt(x, y, z));
        this.targetWorld.getBlockAt(x, y, z).setBlockData(blockData, false);
        return true;
    }

    @Override
    public BlockData getBlockData(int x, int y, int z) {
        return this.targetWorld.getBlockAt(x, y, z).getBlockData();
    }

    @SuppressWarnings("deprecation")
    public boolean setRawTypeId(int x, int y, int z, int typeId)
    {
        this.currentUndo.put(targetWorld.getBlockAt(x, y, z));
        this.targetWorld.getBlockAt(x, y, z).setBlockData(MagicValues.getBlockDataFor(typeId), false);
        return true;
    }

    @SuppressWarnings("deprecation")
    public boolean setRawTypeIdAndData(int x, int y, int z, int typeId, int data)
    {
        this.currentUndo.put(targetWorld.getBlockAt(x, y, z));
        this.targetWorld.getBlockAt(x, y, z).setBlockData(MagicValues.getBlockDataFor(typeId, data), false);
        return true;
    }

    @SuppressWarnings("deprecation")
    public boolean setTypeId(int x, int y, int z, int typeId)
    {
        this.currentUndo.put(targetWorld.getBlockAt(x, y, z));
        this.targetWorld.getBlockAt(x, y, z).setBlockData(MagicValues.getBlockDataFor(typeId));
        return true;
    }

    @SuppressWarnings("deprecation")
    public boolean setTypeIdAndData(int x, int y, int z, int typeId, int data)
    {
        this.currentUndo.put(targetWorld.getBlockAt(x, y, z));
        this.targetWorld.getBlockAt(x, y, z).setBlockData(MagicValues.getBlockDataFor(typeId, data), true);
        return true;
    }
    
    @SuppressWarnings("deprecation")
	public boolean setBlock(Block b)
    {
        this.currentUndo.put(this.targetWorld.getBlockAt(b.getLocation()));
        this.targetWorld.getBlockAt(b.getLocation()).setBlockData(b.getBlockData(), true);
        return true;
    }
    

    @SuppressWarnings("deprecation")
    public int getTypeId(int x, int y, int z)
    {
        return MagicValues.getIdFor(this.targetWorld.getBlockAt(x, y, z).getBlockData());
    }

    @Override
    public int getHeight()
    {
        return this.targetWorld.getMaxHeight();
    }

    @Override
    public boolean isEmpty(int x, int y, int z)
    {
        return this.targetWorld.getBlockAt(x, y, z).isEmpty();
    }
}
