package com.thevoxelbox.voxelsniper.util;

import java.util.ArrayList;
import java.util.List;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.kyori.adventure.text.flattener.FlattenerListener;
import net.kyori.adventure.text.format.Style;

import org.jetbrains.annotations.NotNull;

public abstract class ComponentConverter<T> implements FlattenerListener {
    protected final T component;
    private final List<Style> pushedStyles = new ArrayList<>();
    private Style currentStyle = null;

    protected ComponentConverter(T component) {
        this.component = component;
    }

    public final T convert(final @NotNull Component message) {
        ComponentFlattener.basic().flatten(message, this);
        return component;
    }

    protected final @NotNull Style getCurrentStyle() {
        if (pushedStyles.isEmpty()) return Style.empty();
        if (this.currentStyle == null) {
            this.currentStyle = this.pushedStyles.get(0);
            for (int i = 1; i < this.pushedStyles.size(); i++) {
                this.currentStyle = this.currentStyle.merge(this.pushedStyles.get(i));
            }
        }
        return this.currentStyle;
    }

    @Override
    public void pushStyle(@NotNull Style style) {
        this.currentStyle = getCurrentStyle().merge(style);
        this.pushedStyles.add(style);
    }

    @Override
    public void popStyle(@NotNull Style style) {
        if (!pushedStyles.isEmpty()) {
            pushedStyles.remove(pushedStyles.size()-1);
        }
        this.currentStyle = null;
    }
}
