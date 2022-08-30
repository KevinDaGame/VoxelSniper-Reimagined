package com.thevoxelbox.voxelsniper.voxelsniper.blockstate.sign;

import com.thevoxelbox.voxelsniper.voxelsniper.blockstate.IBlockState;

public interface ISign extends IBlockState {
    /**
     *
     * @param line The line of the sign to edit
     * @param signText The text to put on the sign, using ampersand (&) for color codes
     */
    void setLine(int line, String signText);

    /**
     *
     * @param line The line of the sign to edit
     * @return The text from the sign, using ampersand (&) for color codes
     */
    String getLine(int line);
}
