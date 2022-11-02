package com.github.kevindagame.voxelsniper;

import org.junit.Test;

public class AbsTest {
    @Test
    public void absTest(){
        var array = new Coord[10][10][10];
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                for(int k = 0; k < 10; k++){
                    array[i][j][k] = new Coord(i - 100, j, k + 100);
                }
            }
        }
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                for(int k = 0; k < 10; k++){
                    System.out.print(array[i][j][k]);
                }
                System.out.println();
            }
        }

    }
}

record Coord(int x, int y, int z){

}
