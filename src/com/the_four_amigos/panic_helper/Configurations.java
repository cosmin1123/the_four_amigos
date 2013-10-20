package com.the_four_amigos.panic_helper;

/**
 * Created with IntelliJ IDEA.
 * User: cosmin
 * Date: 10/19/13
 * Time: 7:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class Configurations {
    public static float alarmGravity = 1.0f; //vom citi din fisier
    public static boolean [] myConfiguration = {false, false, false, false, false, false, false, false, false};
    private float gravity = 1.0f;

    public void setGravity(float alarmGravity) {
        this.gravity = alarmGravity;
    }

    public float getGravity() {
        return gravity;
    }
}
