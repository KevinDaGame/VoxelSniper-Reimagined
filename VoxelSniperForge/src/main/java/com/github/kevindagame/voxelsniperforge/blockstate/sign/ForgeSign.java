package com.github.kevindagame.voxelsniperforge.blockstate.sign;

import com.github.kevindagame.voxelsniper.blockstate.sign.ISign;
import com.github.kevindagame.voxelsniperforge.block.ForgeBlock;
import com.github.kevindagame.voxelsniperforge.blockstate.ForgeBlockState;

import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.jetbrains.annotations.NotNull;

public class ForgeSign extends ForgeBlockState implements ISign {

    private final SignBlockEntity tileEntity;

    public ForgeSign(ForgeBlock block, BlockState blockState, SignBlockEntity tileEntity) {
        super(block, blockState);
        this.tileEntity = tileEntity;
    }

    @NotNull
    private static String translateColorCodesToAmpersand(@NotNull String textToTranslate) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setLine(int line, String signText) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String getLine(int line) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
