package com.github.kevindagame.voxelsniperforge.blockdata;

import com.github.kevindagame.voxelsniper.blockdata.IBlockData;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;
import com.github.kevindagame.voxelsniperforge.blockdata.redstoneWire.ForgeRedstoneWire;
import com.github.kevindagame.voxelsniperforge.material.BlockMaterial;
import com.google.common.base.Preconditions;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import java.util.Map;

import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.registries.ForgeRegistries;

public class ForgeBlockData implements IBlockData, Cloneable {
    protected BlockState state;
    private Map<Property<?>, Comparable<?>> parsedStates;

    protected ForgeBlockData(BlockState state) {
        this.state = state;
    }

    @Override
    public VoxelMaterial getMaterial() {
        return BlockMaterial.fromForgeBlock(state.getBlock());
    }

    @Override
    public String getAsString() {
        var str = this.state.toString();
       return str.substring(str.indexOf('['));
    }

    @Override
    public String toString() {
        return getAsString();
    }

    @Override
    public boolean matches(IBlockData newData) {
        if (newData == null) {
            return false;
        }
        if (!(newData instanceof ForgeBlockData forge)) {
            return false;
        }

        if (this.state.getBlock() != forge.state.getBlock()) {
            return false;
        }

        // Fastpath an exact match
        boolean exactMatch = this.equals(newData);

        // If that failed, do a merge and check
        if (!exactMatch && forge.parsedStates != null) {
            return this.merge(newData).equals(this);
        }

        return exactMatch;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ForgeBlockData && this.state.equals(((ForgeBlockData)obj).state);
    }

    @Override
    public IBlockData merge(IBlockData newData) {
        ForgeBlockData forge = (ForgeBlockData) newData;
        Preconditions.checkArgument(forge.parsedStates != null, "Data not created via string parsing");
        Preconditions.checkArgument(this.state.getBlock() == forge.state.getBlock(), "States have different types (got %s, expected %s)", newData, this);

        ForgeBlockData clone = this.clone();
        clone.parsedStates = null;

        for (Property parsed : forge.parsedStates.keySet()) {
            clone.state = clone.state.setValue(parsed, forge.state.getValue(parsed));
        }

        return clone;
    }

    @Override
    public IBlockData getCopy() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public ForgeBlockData clone() {
        try {
            return (ForgeBlockData) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    public static ForgeBlockData createNewData(BlockMaterial material, String data) {
        Block block = material.getBlock();

        BlockState blockData;
        Map<Property<?>, Comparable<?>> parsed = null;

        // Data provided, use it
        if (data != null) {
            try {
                // Material provided, force that material in
                if (block != null) {
                    data = ForgeRegistries.BLOCKS.getKey(block) + data;
                }

                StringReader reader = new StringReader(data);
                BlockStateParser.BlockResult arg = BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), reader, false);
                Preconditions.checkArgument(!reader.canRead(), "Found trailing data: " + data);

                blockData = arg.blockState();
                parsed = arg.properties();
            } catch (CommandSyntaxException ex) {
                throw new IllegalArgumentException("Could not parse data: " + data, ex);
            }
        } else {
            blockData = block.defaultBlockState();
        }

        ForgeBlockData craft = ForgeBlockData.fromData(blockData);
        craft.parsedStates = parsed;
        return craft;
    }

    public BlockState getState() {
        return state;
    }

    protected final <T extends Comparable<T>> T get(Property<T> ibs) {
        return this.state.getValue(ibs);
    }

    protected final <T extends Comparable<T>, V extends T> void set(Property<T> ibs, V v) {
        this.parsedStates = null;
        this.state = this.state.setValue(ibs, v);
    }

    public static ForgeBlockData fromData(BlockState blockData) {
        Block b = blockData.getBlock();
        if (b instanceof RedStoneWireBlock)
            return new ForgeRedstoneWire(blockData);
        return new ForgeBlockData(blockData);
    }
}
