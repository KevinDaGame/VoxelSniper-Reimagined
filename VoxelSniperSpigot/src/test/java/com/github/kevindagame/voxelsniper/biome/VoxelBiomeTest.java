package com.github.kevindagame.voxelsniper.biome;

import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.voxelsniper.IVoxelsniper;
import com.github.kevindagame.voxelsniper.Version;
import junit.framework.TestCase;
import org.junit.Assert;
import org.mockito.Mockito;

public class VoxelBiomeTest extends TestCase {
    VoxelBiome voxelBiome;
    private VoxelBiome biome;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        var main = Mockito.mock(IVoxelsniper.class);
        Mockito.when(main.getVersion()).thenReturn(Version.V1_16);
        VoxelSniper.voxelsniper = main;
        this.biome = new VoxelBiome("namespace", "key", Version.V1_16);
    }

    public void testGetNamespace() {
        Assert.assertEquals(biome.namespace(), "namespace");
    }

    public void testGetKey() {
        Assert.assertEquals(biome.key(), "key");
    }

    public void testTestGetName() {
        Assert.assertEquals(biome.getName(), "namespace:key");
    }

    public void testGetBiome() {
        Assert.assertEquals(VoxelBiome.getBiome("plains"), VoxelBiome.PLAINS);
    }

    public void testTestGetBiome() {
        Assert.assertEquals(VoxelBiome.getBiome("minecraft", "plains"), VoxelBiome.PLAINS);
    }
}