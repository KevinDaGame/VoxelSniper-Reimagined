package com.thevoxelbox.voxelsniper.util;

import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.function.BiConsumer;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.hover.content.Entity;
import net.md_5.bungee.api.chat.hover.content.Item;
import net.md_5.bungee.api.chat.hover.content.Text;

import org.jetbrains.annotations.NotNull;

public class BukkitComponentConverter extends ComponentConverter<TextComponent> {
    private static final Map<TextDecoration, BiConsumer<BaseComponent, Boolean>> decorationMap = ImmutableMap.of(
            TextDecoration.OBFUSCATED, BaseComponent::setObfuscated,
            TextDecoration.BOLD, BaseComponent::setBold,
            TextDecoration.STRIKETHROUGH, BaseComponent::setStrikethrough,
            TextDecoration.UNDERLINED, BaseComponent::setUnderlined,
            TextDecoration.ITALIC, BaseComponent::setItalic
        );

    public BukkitComponentConverter() {
        super(new TextComponent());
    }

    private static TextComponent applyStyle(TextComponent comp, Style style) {
        TextColor color = style.color();
        if (color != null) comp.setColor(ChatColor.of(color.asHexString()));
        for (TextDecoration deco : TextDecoration.values()) {
            decorationMap.get(deco).accept(comp, style.hasDecoration(deco));
        }

        Key font = style.font();
        if (font != null) comp.setFont(font.toString());
        comp.setInsertion(style.insertion());

        net.kyori.adventure.text.event.ClickEvent click = style.clickEvent();
        if (click != null) {
            comp.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(click.action().name()), click.value()));
        }

        HoverEvent<?> hover = style.hoverEvent();
        if (hover != null) {
            Action action;
            Content content;
            if (hover.action() == HoverEvent.Action.SHOW_TEXT) {
                action = Action.SHOW_TEXT;
                content = new Text(new BaseComponent[]{new BukkitComponentConverter().convert((Component)hover.value())});
            } else if (hover.action() == HoverEvent.Action.SHOW_ITEM) {
                action = Action.SHOW_ITEM;
                HoverEvent.ShowItem item = (HoverEvent.ShowItem) hover.value();
                BinaryTagHolder nbt = item.nbt();
                content = new Item(item.item().toString(), item.count(), nbt != null ? ItemTag.ofNbt(nbt.string()) : null);
            } else if (hover.action() == HoverEvent.Action.SHOW_ENTITY) {
                action = Action.SHOW_ENTITY;
                HoverEvent.ShowEntity entity = (HoverEvent.ShowEntity) hover.value();
                TextComponent name = entity.name() != null ? new BukkitComponentConverter().convert(entity.name()) : null;
                content = new Entity(entity.type().toString(), entity.id().toString(), name);
            } else {
                return comp;
            }
            comp.setHoverEvent(new net.md_5.bungee.api.chat.HoverEvent(action, content));
        }
        return comp;
    }

    @Override
    public void component(@NotNull String text) {
        this.component.addExtra(applyStyle(new TextComponent(text), this.getCurrentStyle()));
    }
}
