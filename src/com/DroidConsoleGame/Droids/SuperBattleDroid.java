package com.DroidConsoleGame.Droids;

import java.util.Random;

public class SuperBattleDroid extends Droid {
    int missles;

    // Constructor
    public SuperBattleDroid(String name, int health, int damage, int missiles) {
        super(name, health, damage);
        this.missles = missiles;
    }

    // Method for launching a missile
    public void launchMissile(Droid target) {
        // Use missiles
        this.missles = -1;
        System.out.println(this.name + " attacks " + target.name + " with missiles for " + (this.damage * 2) + " damage.");
        if (target instanceof Droideka&&((Droideka) target).isShieldActive()) { //Attacks but shields on
            super.attack(target);
        } else { // Attacks and no shields
            target.health -= damage * 2;
            if (target.health <= 0) {
                System.out.println(target.name + " has been destroyed.");
            }

        }
    }

    @Override
    public void attack(Droid target) {
        Random random = new Random();
        int chance = random.nextInt(100);
        if (chance <= 30 && missles > 0) {
            this.launchMissile(target);
        } else {
            // Use regular attack
            super.attack(target);
        }
    }

}
