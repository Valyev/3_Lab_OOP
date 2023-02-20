package com.DroidConsoleGame.Droids;

import java.util.Random;

// Subclass for Droideka droids, shield droid
public class Droideka extends Droid {
    boolean shieldActive;

    // Constructor
    public Droideka(String name, int health, int damage) {
        super(name, health, damage);
        this.shieldActive = false;
    }

    // Method for activating shield
    public void activateShield() {
        this.shieldActive = true;
        System.out.println(this.name + " activates shield!");
    }
    public void deactiveteShield(){
        this.shieldActive=false;
    }

    public boolean isShieldActive() {
        return shieldActive;
    }


    // Method for attacking another droid
    @Override
    public void attack(Droid target) {
        Random random = new Random();
        super.attack(target);
        // If 15% then activate shield
        int chance = random.nextInt(100);
        if (chance <= 15) {
            this.activateShield();
        }

    }
}
