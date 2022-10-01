/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kevindagame.brush.perform;

import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.VoxelMessage;

/**
 * @author Voxel
 */
public interface IPerformerBrush {

    boolean parsePerformer(String performerHandle, SnipeData v);

    void parsePerformer(String triggerHandle, String[] args, SnipeData v);

    void showInfo(VoxelMessage vm);
}
