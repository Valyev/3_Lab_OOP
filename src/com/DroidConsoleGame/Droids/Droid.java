package com.DroidConsoleGame.Droids;

// Droid base class
public class Droid {
    String name;
    int health;
    int damage;

    // Constructor
    public Droid(String name, int health, int damage) {
        this.name = name;
        this.health = health;
        this.damage = damage;
    }

    // Method for attacking another droid
    public void attack(Droid target) {
        if (target instanceof Droideka && ((Droideka) target).isShieldActive()) {
            // Shield is active, no damage taken
            System.out.println(target.name + "'s shield blocks the attack.");
            ((Droideka) target).deactiveteShield();
        } else {
            // Attack target
            target.health -= damage;
            System.out.println(this.name + " attacks " + target.name + " for " + damage + " damage.");
            if (target.health <= 0) {
                System.out.println(target.name + " has been destroyed.");
            }
        }
    }



    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }
}

