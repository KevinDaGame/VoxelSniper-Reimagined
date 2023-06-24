package com.github.kevindagame.voxelsniperforge.blockstate;

import com.github.kevindagame.voxelsniperforge.block.ForgeBlock;
import com.github.kevindagame.voxelsniperforge.mixin.MixinBlockStateBase;

/**
 * This interface is used in mixin to implement ignorePhysics (see {@link MixinBlockStateBase} and {@link ForgeBlock})
 */
public interface ExtendedBlockState {

    void setShouldUpdate(boolean shouldUpdate);
}
