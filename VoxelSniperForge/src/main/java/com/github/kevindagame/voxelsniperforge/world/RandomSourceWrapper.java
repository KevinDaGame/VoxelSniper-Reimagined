package com.github.kevindagame.voxelsniperforge.world;

import java.util.Random;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;

import org.jetbrains.annotations.NotNull;

public final class RandomSourceWrapper implements RandomSource {

    private final Random random;

    public RandomSourceWrapper(Random random) {
        this.random = random;
    }

    @NotNull
    @Override
    public RandomSource fork() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @NotNull
    @Override
    public PositionalRandomFactory forkPositional() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public synchronized void setSeed(long seed) {
        this.random.setSeed(seed);
    }

    @Override
    public int nextInt() {
        return this.random.nextInt();
    }

    @Override
    public int nextInt(int bound) {
        return this.random.nextInt(bound);
    }

    @Override
    public long nextLong() {
        return this.random.nextLong();
    }

    @Override
    public boolean nextBoolean() {
        return this.random.nextBoolean();
    }

    @Override
    public float nextFloat() {
        return this.random.nextFloat();
    }

    @Override
    public double nextDouble() {
        return this.random.nextDouble();
    }

    @Override
    public synchronized double nextGaussian() {
        return this.random.nextGaussian();
    }
}