package com.github.kevindagame.voxelsniper.permissions;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record Permission(String name, Permission parent) {
    public Permission(@NotNull String name, @Nullable Permission parent) {
        this.name = name;
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public List<Permission> getChildren() {
        return PermissionLoader.getInstance().getAllPermissions().stream().filter(p -> this.equals(p.parent)).toList();
    }
}
