package com.thevoxelbox.voxelsniper.brush;

import com.google.common.base.Objects;
import com.thevoxelbox.voxelsniper.bukkit.VoxelMessage;
import com.thevoxelbox.voxelsniper.snipe.SnipeData;
import com.thevoxelbox.voxelsniper.snipe.Undo;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Collectors;

/**
 * http://www.voxelwiki.com/minecraft/VoxelSniper#The_Erosion_Brush
 *
 * @author Piotr
 * @author MikeMatrix
 */
public class ErodeBrush extends Brush {

    private static final Vector[] FACES_TO_CHECK = {new Vector(0, 0, 1), new Vector(0, 0, -1), new Vector(0, 1, 0), new Vector(0, -1, 0), new Vector(1, 0, 0), new Vector(-1, 0, 0)};
    private String presetName = "NONE";
    private ErosionPreset currentPreset = new ErosionPreset(0, 1, 0, 1);

    /**
     *
     */
    public ErodeBrush() {
        this.setName("Erode");
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.erosion(v, this.currentPreset);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.erosion(v, this.currentPreset.getInverted());
    }

    @SuppressWarnings("deprecation")
    private void erosion(final SnipeData v, final ErosionPreset erosionPreset) {
        final BlockChangeTracker blockChangeTracker = new BlockChangeTracker(this.getTargetBlock().getWorld());

        final Vector targetBlockVector = this.getTargetBlock().getLocation().toVector();

        for (int i = 0; i < erosionPreset.getErosionRecursion(); ++i) {
            erosionIteration(v, erosionPreset, blockChangeTracker, targetBlockVector);
        }

        for (int i = 0; i < erosionPreset.getFillRecursion(); ++i) {
            fillIteration(v, erosionPreset, blockChangeTracker, targetBlockVector);
        }

        final Undo undo = new Undo();
        for (final BlockWrapper blockWrapper : blockChangeTracker.getAll()) {
            undo.put(blockWrapper.getBlock());
            blockWrapper.getBlock().setBlockData(blockWrapper.getBlockData(), true);
        }

        v.owner().storeUndo(undo);
    }

    private void erosionIteration(final SnipeData v, final ErosionPreset erosionPreset, final BlockChangeTracker blockChangeTracker, final Vector targetBlockVector) {
        final int currentIteration = blockChangeTracker.nextIteration();
        for (int x = this.getTargetBlock().getX() - v.getBrushSize(); x <= this.getTargetBlock().getX() + v.getBrushSize(); ++x) {
            for (int z = this.getTargetBlock().getZ() - v.getBrushSize(); z <= this.getTargetBlock().getZ() + v.getBrushSize(); ++z) {
                for (int y = this.getTargetBlock().getY() - v.getBrushSize(); y <= this.getTargetBlock().getY() + v.getBrushSize(); ++y) {
                    final Vector currentPosition = new Vector(x, y, z);
                    if (currentPosition.isInSphere(targetBlockVector, v.getBrushSize())) {
                        final BlockWrapper currentBlock = blockChangeTracker.get(currentPosition, currentIteration);

                        if (currentBlock.isEmpty() || currentBlock.isLiquid()) {
                            continue;
                        }

                        int count = 0;
                        for (final Vector vector : ErodeBrush.FACES_TO_CHECK) {
                            final Vector relativePosition = currentPosition.clone().add(vector);
                            final BlockWrapper relativeBlock = blockChangeTracker.get(relativePosition, currentIteration);

                            if (relativeBlock.isEmpty() || relativeBlock.isLiquid()) {
                                count++;
                            }
                        }

                        if (count >= erosionPreset.getErosionFaces()) {
                            blockChangeTracker.put(currentPosition, new BlockWrapper(currentBlock.getBlock(), new BukkitMaterial( Material.AIR)), currentIteration);
                        }
                    }
                }
            }
        }
    }

    private void fillIteration(final SnipeData v, final ErosionPreset erosionPreset, final BlockChangeTracker blockChangeTracker, final Vector targetBlockVector) {
        final int currentIteration = blockChangeTracker.nextIteration();
        for (int x = this.getTargetBlock().getX() - v.getBrushSize(); x <= this.getTargetBlock().getX() + v.getBrushSize(); ++x) {
            for (int z = this.getTargetBlock().getZ() - v.getBrushSize(); z <= this.getTargetBlock().getZ() + v.getBrushSize(); ++z) {
                for (int y = this.getTargetBlock().getY() - v.getBrushSize(); y <= this.getTargetBlock().getY() + v.getBrushSize(); ++y) {
                    final Vector currentPosition = new Vector(x, y, z);
                    if (currentPosition.isInSphere(targetBlockVector, v.getBrushSize())) {
                        final BlockWrapper currentBlock = blockChangeTracker.get(currentPosition, currentIteration);

                        if (!(currentBlock.isEmpty() || currentBlock.isLiquid())) {
                            continue;
                        }

                        int count = 0;

                        final Map<BlockWrapper, Integer> blockCount = new HashMap<>();

                        for (final Vector vector : ErodeBrush.FACES_TO_CHECK) {
                            final Vector relativePosition = currentPosition.clone().add(vector);
                            final BlockWrapper relativeBlock = blockChangeTracker.get(relativePosition, currentIteration);

                            if (!(relativeBlock.isEmpty() || relativeBlock.isLiquid())) {
                                count++;
                                final BlockWrapper typeBlock = new BlockWrapper(null, relativeBlock.getMaterial());
                                if (blockCount.containsKey(typeBlock)) {
                                    blockCount.put(typeBlock, blockCount.get(typeBlock) + 1);
                                } else {
                                    blockCount.put(typeBlock, 1);
                                }
                            }
                        }

                        BlockWrapper currentMaterial = new BlockWrapper(null, new BukkitMaterial( Material.AIR));
                        int amount = 0;

                        for (final BlockWrapper wrapper : blockCount.keySet()) {
                            final Integer currentCount = blockCount.get(wrapper);
                            if (amount <= currentCount) {
                                currentMaterial = wrapper;
                                amount = currentCount;
                            }
                        }

                        if (count >= erosionPreset.getFillFaces()) {
                            blockChangeTracker.put(currentPosition, new BlockWrapper(currentBlock.getBlock(), currentMaterial.getMaterial()), currentIteration);
                        }
                    }
                }
            }
        }
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
        vm.custom(ChatColor.GOLD + "Active brush preset is " + ChatColor.YELLOW + this.presetName + ChatColor.GOLD + ".");
    }

    @Override
    // TODO: Implement changing of individual variables | fill erode fillrecursion eroderecursion
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "Erode Brush Parameters:");
            v.sendMessage(ChatColor.AQUA + "/b " + triggerHandle + " [preset]  -- Change active erode brush preset");
            return;
        }

        try {
            Preset preset = Preset.valueOf(params[0].toUpperCase());
            this.currentPreset = preset.getPreset();
            this.presetName = preset.name();
            v.sendMessage(ChatColor.GOLD + "Brush preset changed to " + ChatColor.YELLOW + this.presetName + ChatColor.GOLD + ".");
        } catch (IllegalArgumentException e) {
            v.getVoxelMessage().brushMessage(ChatColor.RED + "That preset does not exist.");
        }
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Arrays.stream(Preset.values()).map(e -> e.name()).collect(Collectors.toList()));
    }

    /**
     * @author MikeMatrix
     */
    private enum Preset {
        NONE(new ErosionPreset(0, 1, 0, 1)),
        MELT(new ErosionPreset(2, 1, 5, 1)),
        FILL(new ErosionPreset(5, 1, 2, 1)),
        SMOOTH(new ErosionPreset(3, 1, 3, 1)),
        LIFT(new ErosionPreset(6, 0, 1, 1)),
        FLOATCLEAN(new ErosionPreset(6, 1, 6, 1));

        private final ErosionPreset preset;

        Preset(final ErosionPreset preset) {
            this.preset = preset;
        }

        /**
         * Generates a concat string of all options.
         *
         * @param seperator Seperator for delimiting entries.
         * @return
         */
        public static String getValuesString(String seperator) {
            StringBuilder valuesString = new StringBuilder();

            boolean delimiterHelper = true;
            for (final Preset preset : Preset.values()) {
                if (delimiterHelper) {
                    delimiterHelper = false;
                } else {
                    valuesString.append(seperator);
                }
                valuesString.append(preset.name());
            }
            return valuesString.toString();
        }

        public ErosionPreset getPreset() {
            return this.preset;
        }
    }

    /**
     * @author MikeMatrix
     */
    private static final class BlockChangeTracker {

        private final Map<Integer, Map<Vector, BlockWrapper>> blockChanges;
        private final Map<Vector, BlockWrapper> flatChanges;
        private final World world;
        private int nextIterationId = 0;

        public BlockChangeTracker(final World world) {
            this.blockChanges = new HashMap<>();
            this.flatChanges = new HashMap<>();
            this.world = world;
        }

        public BlockWrapper get(final Vector position, final int iteration) {
            BlockWrapper changedBlock = null;

            for (int i = iteration - 1; i >= 0; --i) {
                if (this.blockChanges.containsKey(i) && this.blockChanges.get(i).containsKey(position)) {
                    changedBlock = this.blockChanges.get(i).get(position);
                    return changedBlock;
                }
            }

            changedBlock = new BlockWrapper(position.toLocation(this.world).getBlock());

            return changedBlock;
        }

        public Collection<BlockWrapper> getAll() {
            return this.flatChanges.values();
        }

        public int nextIteration() {
            return this.nextIterationId++;
        }

        public void put(final Vector position, final BlockWrapper changedBlock, final int iteration) {
            if (!this.blockChanges.containsKey(iteration)) {
                this.blockChanges.put(iteration, new HashMap<>());
            }

            this.blockChanges.get(iteration).put(position, changedBlock);
            this.flatChanges.put(position, changedBlock);
        }
    }

    /**
     * @author MikeMatrix
     */
    private static final class BlockWrapper {

        private final Block block;
        private final BlockData blockData;

        @SuppressWarnings("deprecation")
        public BlockWrapper(final Block block) {
            this.block = block;
            this.blockData = block.getBlockData();
        }

        public BlockWrapper(final Block block, final IMaterial material) {
            this.block = block;
            this.blockData = material.createBlockData();
        }

        /**
         * @return the block
         */
        public Block getBlock() {
            return this.block;
        }

        /**
         * @return the data
         */
        public BlockData getBlockData() {
            return this.blockData;
        }

        /**
         * @return the material
         */
        public IMaterial getMaterial() {
            return this.blockData.getMaterial();
        }

        /**
         * @return if the block is Empty.
         */
        public boolean isEmpty() {
            return this.getMaterial() == new BukkitMaterial( Material.AIR);
        }

        /**
         * @return if the block is a Liquid.
         */
        public boolean isLiquid() {
            switch (this.getMaterial()) {
                case WATER:
                case LAVA:
                    return true;
                default:
                    return false;
            }
        }

    }

    /**
     * @author MikeMatrix
     */
    private static final class ErosionPreset {

        private final int erosionFaces;
        private final int erosionRecursion;
        private final int fillFaces;
        private final int fillRecursion;

        public ErosionPreset(final int erosionFaces, final int erosionRecursion, final int fillFaces, final int fillRecursion) {
            this.erosionFaces = erosionFaces;
            this.erosionRecursion = erosionRecursion;
            this.fillFaces = fillFaces;
            this.fillRecursion = fillRecursion;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(erosionFaces, erosionRecursion, fillFaces, fillRecursion);
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof ErosionPreset) {
                ErosionPreset other = (ErosionPreset) obj;
                return Objects.equal(this.erosionFaces, other.erosionFaces) && Objects.equal(this.erosionRecursion, other.erosionRecursion) && Objects.equal(this.fillFaces, other.fillFaces) && Objects.equal(this.fillRecursion, other.fillRecursion);
            }
            return false;
        }

        /**
         * @return the erosionFaces
         */
        public int getErosionFaces() {
            return this.erosionFaces;
        }

        /**
         * @return the erosionRecursion
         */
        public int getErosionRecursion() {
            return this.erosionRecursion;
        }

        /**
         * @return the fillFaces
         */
        public int getFillFaces() {
            return this.fillFaces;
        }

        /**
         * @return the fillRecursion
         */
        public int getFillRecursion() {
            return this.fillRecursion;
        }

        public ErosionPreset getInverted() {
            return new ErosionPreset(this.fillFaces, this.fillRecursion, this.erosionFaces, this.erosionRecursion);
        }
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.erode";
    }
}
