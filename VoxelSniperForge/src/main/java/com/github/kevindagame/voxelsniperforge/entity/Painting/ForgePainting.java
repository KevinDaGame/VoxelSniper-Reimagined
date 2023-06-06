package com.github.kevindagame.voxelsniperforge.entity.Painting;

import com.github.kevindagame.voxelsniper.entity.painting.IPainting;
import com.github.kevindagame.voxelsniperforge.entity.ForgeEntity;

import java.util.List;
import java.util.Objects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraftforge.registries.ForgeRegistries;


public class ForgePainting extends ForgeEntity implements IPainting {
    private static final List<ResourceLocation> paintingVariants = ForgeRegistries.PAINTING_VARIANTS.getKeys().stream().toList();

    public ForgePainting(Painting entity) {
        super(entity);
    }

    @Override
    public boolean setArtId(int id) {
        if (id < 0 || id >= paintingVariants.size())
            return false;
        var key = paintingVariants.get(id);
        Painting p = (Painting) this.getEntity();
        // Painting::setVariant is private, try doing it through the NBT data
        var nbt = p.serializeNBT();
        nbt.putString("variant", key.toString());
        p.readAdditionalSaveData(nbt);

        return true;
    }

    @Override
    public int getArtId() {
        String[] values = paintingVariants.stream().map(Objects::toString).toArray(String[]::new);

        var variant =  ((Painting) this.getEntity()).getVariant().get();
        var key = ForgeRegistries.PAINTING_VARIANTS.getKey(variant);
        var idx =  paintingVariants.indexOf(key);
        return (idx == -1) ? 0 : idx;
    }

    @Override
    public int nextPaintingId(boolean back) {
        return (this.getArtId() + (back ? -1 : 1) + paintingVariants.size()) % paintingVariants.size();
    }
}
