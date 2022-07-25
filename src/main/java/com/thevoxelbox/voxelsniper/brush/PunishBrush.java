package com.thevoxelbox.voxelsniper.brush;

import com.google.common.collect.Lists;
import com.thevoxelbox.voxelsniper.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.util.Messages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

/**
 * http://www.voxelwiki.com/minecraft/Voxelsniper#Punish_Brush
 *
 * @author Monofraps
 * @author Deamon
 * @author MikeMatrix
 */
public class PunishBrush extends Brush {

    private static final int MAXIMAL_RANDOM_TELEPORTATION_RANGE = 400;
    private static final int TICKS_PER_SECOND = 20;
    private static final int INFINIPUNISH_SIZE = -3;
    private static final int DEFAULT_PUNISH_LEVEL = 10;
    private static final int DEFAULT_PUSNIH_DURATION = 60;
    private Punishment punishment = Punishment.FIRE;
    private int punishLevel = DEFAULT_PUNISH_LEVEL;
    private int punishDuration = DEFAULT_PUSNIH_DURATION;
    private boolean specificPlayer = false;
    private String punishPlayerName = "";
    private boolean hypnoAffectLandscape = false;
    private boolean hitsSelf = false;

    /**
     * Default Constructor.
     */
    public PunishBrush() {
        this.setName("Punish");
    }

    @SuppressWarnings("deprecation")
    private void applyPunishment(final LivingEntity entity, final SnipeData v) {
        switch (this.punishment) {
            case FIRE:
                entity.setFireTicks(PunishBrush.TICKS_PER_SECOND * this.punishDuration);
                break;
            case LIGHTNING:
                entity.getWorld().strikeLightning(entity.getLocation());
                break;
            case BLINDNESS:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case DRUNK:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case SLOW:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case JUMP:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case ABSORPTION:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case DAMAGE_RESISTANCE:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case FAST_DIGGING:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case FIRE_RESISTANCE:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case HEAL:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case HEALTH_BOOST:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case HUNGER:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case INCREASE_DAMAGE:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case INVISIBILITY:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case NIGHT_VISION:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case POISON:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case REGENERATION:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case SATURATION:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case SLOW_DIGGING:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case SPEED:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case WATER_BREATHING:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case WEAKNESS:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case WITHER:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case KILL:
                entity.setHealth(0d);
                break;
            case RANDOMTP:
                final Random random = new Random();
                final Location targetLocation = entity.getLocation();
                targetLocation.setX(targetLocation.getX() + (random.nextInt(MAXIMAL_RANDOM_TELEPORTATION_RANGE) - (MAXIMAL_RANDOM_TELEPORTATION_RANGE / 2)));
                targetLocation.setZ(targetLocation.getZ() + (random.nextInt(PunishBrush.MAXIMAL_RANDOM_TELEPORTATION_RANGE) - PunishBrush.MAXIMAL_RANDOM_TELEPORTATION_RANGE / 2));
                entity.teleport(targetLocation);
                break;
            case ALL_POTION:
                entity.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                entity.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                entity.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, PunishBrush.TICKS_PER_SECOND * this.punishDuration, this.punishLevel), true);
                break;
            case FORCE:
                final Vector playerVector = this.getTargetBlock().getLocation().toVector();
                final Vector direction = entity.getLocation().toVector().clone();
                direction.subtract(playerVector);
                final double length = direction.length();
                final double stregth = (1 - (length / v.getBrushSize())) * this.punishLevel;
                direction.normalize();
                direction.multiply(stregth);
                entity.setVelocity(direction);
                break;
            case HYPNO:
                if (entity instanceof Player) {
                    final Location location = entity.getLocation();
                    Location target = location.clone();
                    for (int z = this.punishLevel; z >= -this.punishLevel; z--) {
                        for (int x = this.punishLevel; x >= -this.punishLevel; x--) {
                            for (int y = this.punishLevel; y >= -this.punishLevel; y--) {
                                target.setX(location.getX() + x);
                                target.setY(location.getY() + y);
                                target.setZ(location.getZ() + z);
                                if (this.hypnoAffectLandscape && target.getBlock().getType() == Material.AIR) {
                                    continue;
                                }
                                target = location.clone();
                                target.add(x, y, z);
                                ((Player) entity).sendBlockChange(target, v.getVoxelSubstance());
                            }
                        }
                    }
                }
                break;
            default:
                Bukkit.getLogger().warning("Could not determine the punishment of punish brush!");
                break;
        }
    }

    @Override
    protected final void arrow(final SnipeData v) {
        if (!v.owner().getPlayer().hasPermission("voxelsniper.punish")) {
            v.sendMessage("The server says no!");
            return;
        }

        this.punishDuration = v.getVoxelHeight();
        this.punishLevel = v.getcCen();

        if (this.specificPlayer) {
            final Player punishedPlayer = Bukkit.getPlayer(this.punishPlayerName);
            if (punishedPlayer == null) {
                v.sendMessage(("No player " + "%this.punishPlayerName%" + " found.").replace("%this.punishPlayerName%",this.punishPlayerName));
                return;
            }

            this.applyPunishment(punishedPlayer, v);
            return;
        }

        final int brushSizeSquare = v.getBrushSize() * v.getBrushSize();
        final Location targetLocation = new Location(v.getWorld(), this.getTargetBlock().getX(), this.getTargetBlock().getY(), this.getTargetBlock().getZ());

        final List<LivingEntity> entities = v.getWorld().getLivingEntities();
        int numPunishApps = 0;
        for (final LivingEntity entity : entities) {
            if (v.owner().getPlayer() != entity || hitsSelf) {
                if (v.getBrushSize() >= 0) {
                    try {
                        if (entity.getLocation().distanceSquared(targetLocation) <= brushSizeSquare) {
                            numPunishApps++;
                            this.applyPunishment(entity, v);
                        }
                    } catch (final Exception exception) {
                        exception.printStackTrace();
                        v.sendMessage("An error occured.");
                        return;
                    }
                } else if (v.getBrushSize() == PunishBrush.INFINIPUNISH_SIZE) {
                    numPunishApps++;
                    this.applyPunishment(entity, v);
                }
            }
        }
        v.sendMessage(Messages.PUNISHMENT_APPLIED_NUMBER.replace("%numPunishApps%",String.valueOf(numPunishApps)));
    }

    @Override
    protected final void powder(final SnipeData v) {
        if (!v.owner().getPlayer().hasPermission("voxelsniper.punish")) {
            v.sendMessage("The server says no!");
            return;
        }

        final int brushSizeSquare = v.getBrushSize() * v.getBrushSize();
        final Location targetLocation = new Location(v.getWorld(), this.getTargetBlock().getX(), this.getTargetBlock().getY(), this.getTargetBlock().getZ());

        final List<LivingEntity> entities = v.getWorld().getLivingEntities();

        for (final LivingEntity entity : entities) {
            if (entity.getLocation().distanceSquared(targetLocation) < brushSizeSquare) {
                entity.setFireTicks(0);
                entity.removePotionEffect(PotionEffectType.BLINDNESS);
                entity.removePotionEffect(PotionEffectType.CONFUSION);
                entity.removePotionEffect(PotionEffectType.SLOW);
                entity.removePotionEffect(PotionEffectType.JUMP);
            }
        }

    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.custom(Messages.PUNISHMENT_NAME.replace("%punishment%",String.valueOf(this.punishment.toString())));
        vm.size();
        vm.center();
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.PUNISH_BRUSH_USAGE.replace("%triggerHandle%",triggerHandle));
            final StringBuilder punishmentOptions = new StringBuilder();
            for (final Punishment punishment : Punishment.values()) {
                if (punishmentOptions.length() != 0) {
                    punishmentOptions.append(" | ");
                }
                punishmentOptions.append(punishment.name());
            }
            v.sendMessage(Component.text(punishmentOptions.toString()).color(NamedTextColor.GOLD));
            return;
        }

        if (params[0].equalsIgnoreCase("-player")) {
            if (params.length == 1) {
                this.specificPlayer = false;
                v.sendMessage(Messages.NO_LONGER_TARGETING_A_SPECIFIC_PLAYER);
            } else {
                this.specificPlayer = true;
                this.punishPlayerName = params[1];
                v.sendMessage(Messages.NOW_TARGETING_A_SPECIFIC_PLAYER + params[1]);
            }
            return;
        }

        if (params[0].equalsIgnoreCase("-self")) {
            this.hitsSelf = !this.hitsSelf;
            v.sendMessage("Punishments will now " + (this.hitsSelf ? "affect you." : "not affect you."));
            return;
        }

        if (params[0].equalsIgnoreCase("-hypno")) {
            this.hypnoAffectLandscape = !this.hypnoAffectLandscape;
            v.sendMessage("Hypno will now " + (this.hypnoAffectLandscape ? "affect landscape only" : "give the full experience"));
            return;
        }

        try {
            this.punishment = Punishment.valueOf(params[0].toUpperCase());
            v.sendMessage(Messages.PUNISHMENT_SELECTED.replace("%punishment%", this.punishment.name()));
        } catch (final IllegalArgumentException exception) {
            v.sendMessage(Messages.PUNISHMENT_DOES_NOT_EXIST.replace("%triggerHandle%",triggerHandle));
        }
    }

    @Override
    public List<String> registerArguments() {

        List<String> punishArguments = Arrays.stream(Punishment.values()).map(e -> e.name()).collect(Collectors.toList());
        punishArguments.addAll(Lists.newArrayList("-hypno", "-self", "-player"));

        return new ArrayList<>(punishArguments);
    }

    @Override
    public HashMap<String, List<String>> registerArgumentValues() {
        // Number variables
        HashMap<String, List<String>> argumentValues = new HashMap<>();
        
        argumentValues.put("player", Lists.newArrayList("[playerName]"));
        
        return argumentValues;
    }

    /**
     * @author Monofraps
     */
    private enum Punishment {
        // Monofraps
        FIRE, LIGHTNING, BLINDNESS, DRUNK, KILL, RANDOMTP, ALL_POTION,
        // Deamon
        SLOW, JUMP, ABSORPTION, DAMAGE_RESISTANCE, FAST_DIGGING, FIRE_RESISTANCE, HEAL, HEALTH_BOOST, HUNGER, INCREASE_DAMAGE, INVISIBILITY, NIGHT_VISION, POISON, REGENERATION,
        SATURATION, SLOW_DIGGING, SPEED, WATER_BREATHING, WEAKNESS, WITHER,
        // MikeMatrix
        FORCE, HYPNO
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.punish";
    }
}
