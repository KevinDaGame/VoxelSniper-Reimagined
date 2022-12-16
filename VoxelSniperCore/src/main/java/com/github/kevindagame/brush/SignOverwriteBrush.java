package com.github.kevindagame.brush;

import com.github.kevindagame.VoxelSniper;
import com.github.kevindagame.snipe.SnipeData;
import com.github.kevindagame.util.Messages;
import com.github.kevindagame.util.VoxelMessage;
import com.github.kevindagame.voxelsniper.blockstate.IBlockState;
import com.github.kevindagame.voxelsniper.blockstate.sign.ISign;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.io.*;
import java.util.Arrays;

/**
 * Overwrites signs. <a href="https://github.com/KevinDaGame/VoxelSniper-Reimagined/wiki/Brushes#sign-overwrite-brush-brush">...</a>
 *
 * @author Monofraps
 */
public class SignOverwriteBrush extends AbstractBrush {

    private static final int MAX_SIGN_LINE_LENGTH = 15;
    private static final int NUM_SIGN_LINES = 4;
    // these are no array indices
    private static final int SIGN_LINE_1 = 1;
    private static final int SIGN_LINE_2 = 2;
    private static final int SIGN_LINE_3 = 3;
    private static final int SIGN_LINE_4 = 4;
    private final String[] signTextLines = new String[NUM_SIGN_LINES];
    private final boolean[] signLinesEnabled = new boolean[NUM_SIGN_LINES];
    private boolean rangedMode = false;

    /**
     *
     */
    public SignOverwriteBrush() {
        this.setName("Sign Overwrite Brush");

        clearBuffer();
        resetStates();
    }

    /**
     * Sets the text of a given sign.
     *
     * @param sign
     */
    private void setSignText(final ISign sign) {
        for (int i = 0; i < this.signTextLines.length; i++) {
            if (this.signLinesEnabled[i]) {
                sign.setLine(i, this.signTextLines[i]);
            }
        }

        sign.update();
    }

    /**
     * Sets the text of the target sign if the target block is a sign.
     *
     * @param v
     */
    private void setSingle(final SnipeData v) {
        if (this.getTargetBlock().getState() instanceof ISign) {
            setSignText((ISign) this.getTargetBlock().getState());
        } else {
            v.sendMessage(Messages.TARGET_BLOCK_NO_SIGN);
        }
    }

    /**
     * Sets all signs in a range of box{x=z=brushSize*2+1 ; z=voxelHeight*2+1}.
     *
     * @param v
     */
    private void setRanged(final SnipeData v) {
        final int minX = getTargetBlock().getX() - v.getBrushSize();
        final int maxX = getTargetBlock().getX() + v.getBrushSize();
        final int minY = getTargetBlock().getY() - v.getVoxelHeight();
        final int maxY = getTargetBlock().getY() + v.getVoxelHeight();
        final int minZ = getTargetBlock().getZ() - v.getBrushSize();
        final int maxZ = getTargetBlock().getZ() + v.getBrushSize();

        boolean signFound = false; // indicates whether or not a sign was set

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    IBlockState blockState = this.getWorld().getBlock(x, y, z).getState();
                    if (blockState instanceof ISign) {
                        setSignText((ISign) blockState);
                        signFound = true;
                    }
                }
            }
        }

        if (!signFound) {
            v.sendMessage(Messages.NO_SIGN_FOUND);
        }
    }

    @Override
    protected boolean actPerform(SnipeData v) {
        switch(snipeAction) {
            case ARROW -> {
                if (this.rangedMode) {
                    setRanged(v);
                } else {
                    setSingle(v);
                }
                return true;
            }
            case GUNPOWDER -> {
                if (this.getTargetBlock().getState() instanceof ISign sign) {

                    for (int i = 0; i < this.signTextLines.length; i++) {
                        if (this.signLinesEnabled[i]) {
                            this.signTextLines[i] = sign.getLine(i);
                        }
                    }

                    displayBuffer(v);
                } else {
                    v.sendMessage(Messages.TARGET_BLOCK_NO_SIGN);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    protected final void arrow(final SnipeData v) {
        positions.add(getTargetBlock().getLocation());
    }

    @Override
    protected final void powder(final SnipeData v) {
        positions.add(getTargetBlock().getLocation());
    }

    @Override
    public final void parseParameters(final String triggerHandle, final String[] params, final SnipeData v) {
        boolean textChanged = false;

        for (int i = 0; i < params.length; i++) {
            String parameter = params[i];

            try {
                if (parameter.equalsIgnoreCase("info")) {
                    v.sendMessage(Messages.SIGN_BRUSH_USAGE);

                } else if (parameter.startsWith("-1")) {
                    textChanged = true;
                    i = parseSignLineFromParam(params, SIGN_LINE_1, v, i);
                } else if (parameter.startsWith("-2")) {
                    textChanged = true;
                    i = parseSignLineFromParam(params, SIGN_LINE_2, v, i);
                } else if (parameter.startsWith("-3")) {
                    textChanged = true;
                    i = parseSignLineFromParam(params, SIGN_LINE_3, v, i);
                } else if (parameter.startsWith("-4")) {
                    textChanged = true;
                    i = parseSignLineFromParam(params, SIGN_LINE_4, v, i);
                } else if (parameter.equalsIgnoreCase("-clear") || parameter.equalsIgnoreCase("-c")) {
                    clearBuffer();
                    v.sendMessage(Messages.SIGN_BUFFER_CLEARED);
                } else if (parameter.equalsIgnoreCase("-clearall") || parameter.equalsIgnoreCase("-ca")) {
                    clearBuffer();
                    resetStates();
                    v.sendMessage(Messages.SIGN_BUFFER_CLEARED_ENABLED);
                } else if (parameter.equalsIgnoreCase("-multiple") || parameter.equalsIgnoreCase("-m")) {
                    if ((i + 1) >= params.length) {
                        v.sendMessage(Messages.MISSING_PARAMETER.replace("%parameter%", parameter));
                        continue;
                    }

                    rangedMode = (params[++i].equalsIgnoreCase("on") || params[++i].equalsIgnoreCase("yes"));
                    v.sendMessage(Messages.SIGN_OVERWRITE_BRUSH_RANGE_MODE.replace("%rangedMode%", (rangedMode ? "enabled" : "disabled")));
                    if (this.rangedMode) {
                        v.sendMessage(Messages.BRUSH_SIZE_SET.replace("%getBrushSize%", String.valueOf(v.getBrushSize())));
                        v.sendMessage(Messages.BRUSH_HEIGHT_SET.replace("%getVoxelHeight%", String.valueOf(v.getVoxelHeight())));
                    }
                } else if (parameter.equalsIgnoreCase("-save") || parameter.equalsIgnoreCase("-s")) {
                    if ((i + 1) >= params.length) {
                        v.sendMessage(Messages.MISSING_PARAMETER.replace("%parameter%", parameter));
                        continue;
                    }

                    String fileName = params[++i];
                    saveBufferToFile(fileName, v);
                } else if (parameter.equalsIgnoreCase("-open") || parameter.equalsIgnoreCase("-o")) {
                    if ((i + 1) >= params.length) {
                        v.sendMessage(Messages.MISSING_PARAMETER.replace("%parameter%", parameter));
                        continue;
                    }

                    String fileName = params[++i];
                    loadBufferFromFile(fileName, "", v);
                    textChanged = true;
                }
            } catch (Exception exception) {
                v.sendMessage(Messages.PARAMETER_PARSE_ERROR.replace("%parameter%", parameter));
                exception.printStackTrace();
            }
        }

        if (textChanged) {
            displayBuffer(v);
        }
    }

    /**
     * Parses parameter input text of line [param:lineNumber]. Iterates though the given array until the next top level param (a parameter which starts with a
     * dash -) is found.
     *
     * @param params
     * @param lineNumber
     * @param v
     * @param i
     * @return
     */
    private int parseSignLineFromParam(final String[] params, final int lineNumber, final SnipeData v, int i) {
        final int lineIndex = lineNumber - 1;
        final String parameter = params[i];

        boolean statusSet = false;

        if (parameter.contains(":")) {
            this.signLinesEnabled[lineIndex] = parameter.substring(parameter.indexOf(":")).equalsIgnoreCase(":enabled");
            v.sendMessage(Messages.SIGN_LINE_ENABLED_DISABLED.replace("%lineNumber%", String.valueOf(lineNumber)).replace("%state%", (this.signLinesEnabled[lineIndex] ? "enabled" : "disabled")));
            statusSet = true;
        }

        if ((i + 1) >= params.length) {
            // return if the user just wanted to set the status
            if (statusSet) {
                return i;
            }

            v.sendMessage(Messages.SIGN_BRUSH_EMPTY_LINE.replace("%lineNumber%", String.valueOf(lineNumber)));
            signTextLines[lineIndex] = "";
            return i;
        }

        StringBuilder newText = new StringBuilder();

        // go through the array until the next top level parameter is found
        for (i++; i < params.length; i++) {
            final String currentParameter = params[i];

            if (currentParameter.startsWith("-")) {
                i--;
                break;
            } else {
                newText.append(currentParameter).append(" ");
            }
        }

        newText = new StringBuilder(newText.toString());

        // remove last space or return if the string is empty and the user just wanted to set the status
        if ((newText.length() > 0) && newText.toString().endsWith(" ")) {
            newText = new StringBuilder(newText.substring(0, newText.length() - 1));
        } else if (newText.length() == 0) {
            if (statusSet) {
                return i;
            }
            v.sendMessage(Messages.SIGN_BRUSH_EMPTY_LINE.replace("%lineNumber%", String.valueOf(lineNumber)));
        }

        // check the line length and cut the text if needed
        if (newText.length() > MAX_SIGN_LINE_LENGTH) {
            v.sendMessage(Messages.SIGN_OVERWRITE_EXCEED_CHAR_LIMIT.replace("%lineNumber%", String.valueOf(lineNumber)).replace("%MAX_SIGN_LINE_LENGTH%", String.valueOf(MAX_SIGN_LINE_LENGTH)));
            newText = new StringBuilder(newText.substring(0, MAX_SIGN_LINE_LENGTH));
        }

        this.signTextLines[lineIndex] = newText.toString();
        return i;
    }

    private void displayBuffer(final SnipeData v) {
        v.sendMessage(Messages.SIGN_BUFFER_TEXT_SET);
        for (int i = 0; i < this.signTextLines.length; i++) {
            TextComponent text = LegacyComponentSerializer.legacyAmpersand().deserialize(this.signTextLines[i]);
            if (this.signLinesEnabled[i]) {
                v.sendMessage(Messages.SIGN_OVERWRITE_ENABLED.replace("%signTextLines%", text));
            } else {
                v.sendMessage(Messages.SIGN_OVERWRITE_DISABLED.replace("%signTextLines%", text));
            }
        }
    }

    /**
     * Saves the buffer to file.
     *
     * @param fileName
     * @param v
     */
    private void saveBufferToFile(final String fileName, final SnipeData v) {
        final File store = VoxelSniper.voxelsniper.getFileHandler().getDataFile("/" + fileName + ".vsign");

        if (store.exists()) {
            v.sendMessage(Messages.FILE_ALREADY_EXISTS);
            return;
        }

        try {
            store.createNewFile();
            FileWriter outFile = new FileWriter(store);
            BufferedWriter outStream = new BufferedWriter(outFile);

            for (int i = 0; i < this.signTextLines.length; i++) {
                outStream.write(this.signLinesEnabled[i] + "\n");
                outStream.write(this.signTextLines[i] + "\n");
            }

            outStream.close();
            outFile.close();

            v.sendMessage(Messages.FILE_SAVE_SUCCESSFUL);
        } catch (IOException exception) {
            v.sendMessage(Messages.FILE_SAVE_FAIL.replace("%exception.getMessage%", String.valueOf(exception.getMessage())));
            exception.printStackTrace();
        }
    }

    /**
     * Loads a buffer from a file.
     *
     * @param fileName
     * @param userDomain
     * @param v
     */
    private void loadBufferFromFile(final String fileName, final String userDomain, final SnipeData v) {
        final File store = VoxelSniper.voxelsniper.getFileHandler().getDataFile("/" + fileName + ".vsign");
        if (!store.exists()) {
            v.sendMessage(Messages.THIS_FILE_DOES_NOT_EXIST);
            return;
        }

        try {
            FileReader inFile = new FileReader(store);
            BufferedReader inStream = new BufferedReader(inFile);

            for (int i = 0; i < this.signTextLines.length; i++) {
                this.signLinesEnabled[i] = Boolean.parseBoolean(inStream.readLine());
                this.signTextLines[i] = inStream.readLine();
            }

            inStream.close();
            inFile.close();

            v.sendMessage(Messages.FILE_LOAD_SUCCESSFUL);
        } catch (IOException exception) {
            v.sendMessage(Messages.FILE_LOAD_FAIL.replace("%exception.getMessage%", String.valueOf(exception.getMessage())));
            exception.printStackTrace();
        }
    }

    /**
     * Clears the internal text buffer. (Sets it to empty strings)
     */
    private void clearBuffer() {
        Arrays.fill(this.signTextLines, "");
    }

    /**
     * Resets line enabled states to enabled.
     */
    private void resetStates() {
        Arrays.fill(this.signLinesEnabled, true);
    }

    @Override
    public final void info(final VoxelMessage vm) {
        vm.brushName("Sign Overwrite Brush");

        vm.custom(Messages.SIGN_OVERWRITE_BUFFER_TEXT);
        for (int i = 0; i < this.signTextLines.length; i++) {
            if (this.signLinesEnabled[i]) {
                vm.custom(Messages.SIGN_OVERWRITE_ENABLED.replace("%signTextLines%", this.signTextLines[i]));
            } else {
                vm.custom(Messages.SIGN_OVERWRITE_DISABLED.replace("%signTextLines%", this.signTextLines[i]));
            }
        }

        vm.custom(Messages.SIGN_OVERWRITE_BRUSH_RANGE_MODE.replace("%rangedMode%", (rangedMode ? "enabled" : "disabled")));
        if (rangedMode) {
            vm.size();
            vm.height();
        }
    }

    @Override
    public String getPermissionNode() {
        return "voxelsniper.brush.signoverwrite";
    }
}
