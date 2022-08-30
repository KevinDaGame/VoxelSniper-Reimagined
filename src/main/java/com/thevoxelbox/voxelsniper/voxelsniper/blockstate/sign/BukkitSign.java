package com.thevoxelbox.voxelsniper.voxelsniper.blockstate.sign;

import com.thevoxelbox.voxelsniper.voxelsniper.blockstate.BukkitBlockState;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.jetbrains.annotations.NotNull;

public class BukkitSign extends BukkitBlockState implements ISign {
    private static final char DEFAULT_LEGACY_CHAR = '&';

    public BukkitSign(Sign blockState) {
        super(blockState);
    }

    private Sign getSign() {
        return (Sign)this.blockState;
    }

    @Override
    public void setLine(int line, String signText) {
        this.getSign().setLine(line, ChatColor.translateAlternateColorCodes(DEFAULT_LEGACY_CHAR, signText));
    }

    @Override
    public String getLine(int line) {
        return translateColorCodesToAmpersand(this.getSign().getLine(line));
    }

    @NotNull
    private static String translateColorCodesToAmpersand(@NotNull String textToTranslate) {
        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == ChatColor.COLOR_CHAR && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(b[i + 1]) > -1) {
                b[i] = DEFAULT_LEGACY_CHAR;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }
}
