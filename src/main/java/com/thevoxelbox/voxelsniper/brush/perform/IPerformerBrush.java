/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.util.VoxelMessage;

/**
 * @author Voxel
 */
public interface IPerformerBrush {

    boolean parsePerformer(String performerHandle, com.thevoxelbox.voxelsniper.snipe.SnipeData v);

    void parsePerformer(String triggerHandle, String[] args, com.thevoxelbox.voxelsniper.snipe.SnipeData v);

    void showInfo(VoxelMessage vm);
}
