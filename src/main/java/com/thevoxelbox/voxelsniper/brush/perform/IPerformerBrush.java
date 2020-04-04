/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelsniper.brush.perform;

import com.thevoxelbox.voxelsniper.VoxelMessage;

/**
 * @author Voxel
 */
public interface IPerformerBrush {

    public boolean parsePerformer(String performerHandle, com.thevoxelbox.voxelsniper.snipe.SnipeData v);
    
    public void parsePerformer(String triggerHandle, String[] args, com.thevoxelbox.voxelsniper.snipe.SnipeData v);

    public void showInfo(VoxelMessage vm);
}
