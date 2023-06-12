package com.github.kevindagame.voxelsniperforge.mixin;

import com.github.kevindagame.voxelsniperforge.blockstate.ExtendedBlockState;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class MixinBlockStateBase implements ExtendedBlockState {
    private boolean shouldUpdate = true;

    @Override
    public final void setShouldUpdate(boolean shouldUpdate) {
        this.shouldUpdate = shouldUpdate;
    }

    @Inject(
            method = "onPlace",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    public void onPlaceHook(Level p_60697_, BlockPos p_60698_, BlockState p_60699_, boolean p_60700_, CallbackInfo ci) {
        if (!this.shouldUpdate) {
            ci.cancel();
        }
    }
}