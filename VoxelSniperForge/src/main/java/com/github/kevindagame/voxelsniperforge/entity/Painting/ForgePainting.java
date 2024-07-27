package com.github.kevindagame.voxelsniperforge.entity.Painting;

import com.github.kevindagame.voxelsniper.entity.painting.IPainting;
import com.github.kevindagame.voxelsniperforge.VoxelSniperForge;
import com.github.kevindagame.voxelsniperforge.entity.ForgeEntity;


import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;


public class ForgePainting extends ForgeEntity implements IPainting {
    private static final DeferredRegister<PaintingVariant> paintingVariantDeferredRegister = DeferredRegister.create(Registries.PAINTING_VARIANT, VoxelSniperForge.MODID);
    private static final List<DeferredHolder<PaintingVariant, ? extends PaintingVariant>> paintingVariants = paintingVariantDeferredRegister.getEntries().stream().toList();

    public ForgePainting(Painting entity) {
        super(entity);
    }

    @Override
    public boolean setArtId(int id) {
        if (id < 0 || id >= paintingVariants.size())
            return false;
        var variant = paintingVariants.get(id);
        Painting p = (Painting) this.getEntity();
        p.setVariant(variant);

        return true;
    }

    @Override
    public int getArtId() {
        var variant = ((Painting) this.getEntity()).getVariant();
        var idx = paintingVariants.indexOf(variant);
        return (idx == -1) ? 0 : idx;
    }

    @Override
    public int nextPaintingId(boolean back) {
        return (this.getArtId() + (back ? -1 : 1) + paintingVariants.size()) % paintingVariants.size();
    }
}
