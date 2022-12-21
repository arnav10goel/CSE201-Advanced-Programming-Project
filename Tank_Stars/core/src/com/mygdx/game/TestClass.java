package com.mygdx.game;
import com.mygdx.game.Screens.Scene;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestClass {
    @Test
    public void testRange(){
        float w = 4;
        float pos = 2;
        float res = Scene.calc_range(w, pos);
        assertEquals(2, res);
    }
    @Test
    public void testRange2(){
        float w = 0;
        float pos = 0;
        float res = Scene.calc_range(w, pos);
        assertEquals(0, res);
    }
    @Test
    public void testFinPos(){
        double velocity = 10;
        double angle = 0;
        double res = Scene.calc_final_position(velocity, (int) angle);
        assertEquals(0, res);
    }

    @Test
    public void testFinPos2(){
        double velocity = 0;
        double angle = 15;
        double res = Scene.calc_final_position(velocity, (int) angle);
        assertEquals(0, res);
    }
}
