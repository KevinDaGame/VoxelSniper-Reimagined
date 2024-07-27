package com.github.kevindagame.voxelsniperforge.blockstate.sign;

import com.github.kevindagame.voxelsniper.blockstate.sign.ISign;
import com.github.kevindagame.voxelsniperforge.block.ForgeBlock;
import com.github.kevindagame.voxelsniperforge.blockstate.ForgeBlockState;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;

public class ForgeSign extends ForgeBlockState implements ISign {

    private final SignBlockEntity tileEntity;

    public ForgeSign(ForgeBlock block, BlockState blockState, SignBlockEntity tileEntity) {
        super(block, blockState);
        this.tileEntity = tileEntity;
    }

    @Override
    public void setLine(int line, String signText) {
        boolean front = true;
        Component comp = LegacyComponentSerializer.legacyAmpersand().deserialize(signText);
        var text = tileEntity.getFrontText().setMessage(line, toNative(comp));
        tileEntity.setText(text, front);
    }

    @Override
    public String getLine(int line) {
        boolean filtered = false;
        Component text = fromNative(tileEntity.getFrontText().getMessage(line, filtered));
        return LegacyComponentSerializer.legacyAmpersand().serialize(text);
    }

    public static MutableComponent toNative(@NotNull Component component) {
        if (component == Component.empty()) return net.minecraft.network.chat.Component.empty();
//        return net.minecraft.network.chat.Component.Serializer.fromJson(GsonComponentSerializer.gson().serialize(component));
        throw new NotImplementedException();
    }

    public static Component fromNative(@NotNull net.minecraft.network.chat.Component component) {
//        return GsonComponentSerializer.gson().deserialize(net.minecraft.network.chat.Component.Serializer.toJson(component));
        throw new NotImplementedException();
    }
}
