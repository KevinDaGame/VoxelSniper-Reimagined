package com.github.kevindagame.brush;

import com.github.kevindagame.util.Shapes;
import com.github.kevindagame.voxelsniper.block.BlockFace;
import com.google.common.collect.Lists;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.snipe.Undo;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.block.IBlock;
import com.github.kevindagame.voxelsniper.material.VoxelMaterial;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#clean-snow-brush">...</a>
 *
 * @author psanker
 */
public class CleanSnowBrush extends AbstractBrush {

    private boolean smoothSphere = false;

    /**
     *
     */
    public CleanSnowBrush() {
        this.setName("Clean Snow");
    }

    private void cleanSnow(final SnipeData v) {
        this.positions = Shapes.ball(this.getTargetBlock().getLocation(), v.getBrushSize(), smoothSphere);
    }

    @Override
    protected boolean actPerform(SnipeData v) {
        var undo = new Undo();
        for(var position: positions) {
            IBlock b = position.getBlock();
            IBlock blockDown = b.getRelative(BlockFace.DOWN);
            if ((b.getMaterial() == VoxelMaterial.SNOW) && ((blockDown.getMaterial() == VoxelMaterial.SNOW) || (blockDown.getMaterial().isAir()))) {
                undo.put(b);
                b.setMaterial(VoxelMaterial.AIR);
            }
        }
        v.owner().storeUndo(undo);
        return true;
    }

    @Override
    protected final void arrow(final SnipeData v) {
        this.cleanSnow(v);
    }

    @Override
    protected final void powder(final SnipeData v) {
        this.cleanSnow(v);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName(this.getName());
        vm.size();
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        if (params[0].equalsIgnoreCase("info")) {
            v.sendMessage(Messages.CLEAN_SNOW_BURSH_USAGE.replace("%triggerHandle%", triggerHandle));
            return;
        }

        if (params[0].equalsIgnoreCase("smooth")) {
            this.smoothSphere = !this.smoothSphere;
            v.sendMessage(Messages.SMOOTHSPHERE_ALGORITHM.replace("%smoothSphere%", String.valueOf(this.smoothSphere)));
            return;
        }

        v.sendMessage(Messages.BRUSH_INVALID_PARAM.replace("%triggerHandle%", triggerHandle));
    }

    @Override
    public List<String> registerArguments() {

        return new ArrayList<>(Lists.newArrayList("smooth"));
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.cleansnow";
    }
}
