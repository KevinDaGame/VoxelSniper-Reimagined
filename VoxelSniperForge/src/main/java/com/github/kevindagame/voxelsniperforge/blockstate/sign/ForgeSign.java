package com.github.kevindagame.voxelsniperforge.blockstate.sign;

import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.blockstate.sign.ISign;
import com.github.kevindagame.voxelsniperforge.blockstate.ForgeBlockState;

import org.jetbrains.annotations.NotNull;

public class ForgeSign extends ForgeBlockState implements ISign {

    public ForgeSign(IBlock block, Sign blockState) {
        super(block, blockState);
    }

    @NotNull
    private static String translateColorCodesToAmpersand(@NotNull String textToTranslate) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private Sign getSign() {
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
