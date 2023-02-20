package com.DroidConsoleGame;

import com.DroidConsoleGame.Droids.Droid;
import com.DroidConsoleGame.Droids.Droideka;
import com.DroidConsoleGame.Droids.SuperBattleDroid;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


// Main class
class Main {
    static ArrayList<Droid> droids = new ArrayList<Droid>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        //Some Droids
        droids.add(new Droid("Droid 1", 100, 10));
        droids.add(new SuperBattleDroid("Super Droid 1", 200, 15, 5));
        droids.add(new Droideka("Droideka 1", 150, 12));
        droids.add(new Droid("Dummy droid", 500, 1));

        while (running) {
            System.out.println("\n---Menu---");
            System.out.println("1. Create a droid");
            System.out.println("2. Show list of droids");
            System.out.println("3. Start 1-on-1 battle");
            System.out.println("4. Start team-on-team battle");
            System.out.println("5. Record battle in a file");
            System.out.println("6. Play saved battle");
            System.out.println("7. Exit program");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createDroid();
                case 2 -> showDroids();
                case 3 -> start1on1Battle();
                case 4 -> startTeamBattle();
                case 5 -> recordBattle();
                case 6 -> playSavedBattle();
                case 7 -> running = false;
                default -> System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }

    // Method for creating a new droid
    public static void createDroid() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n----Droid Creation----");
        System.out.print("Enter droid name: ");
        String name = scanner.nextLine();
        System.out.print("Enter droid health: ");
        int health = scanner.nextInt();
        System.out.print("Enter droid damage: ");
        int damage = scanner.nextInt();
        System.out.println("Select droid type: ");
        System.out.println("1. Basic Droid");
        System.out.println("2. Droideka");
        System.out.println("3. Super Battle Droid");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        Droid droid = null;
        switch (choice) {
            case 1 -> droid = new Droid(name, health, damage);
            case 2 -> droid = new Droideka(name, health, damage);
            case 3 -> {
                System.out.print("Enter number of missiles: ");
                int missiles = scanner.nextInt();
                droid = new SuperBattleDroid(name, health, damage, missiles);
            }
            default -> System.out.println("Invalid choice.");
        }

        if (droid != null) {
            droids.add(droid);
            System.out.println("Droid created successfully.");
        }
    }

    // Method for showing the list of created droids
    public static void showDroids() {
        if (droids.size() > 0) {
            System.out.println("\n----Droids----");
            for (Droid droid : droids) {
                System.out.println(droid.getName() + ": Health - " + droid.getHealth() + ", Damage - " + droid.getDamage());
            }
        } else {
            System.out.println("No droids created yet.");
        }
    }

    // Method for starting a 1-on-1 battle
    public static void start1on1Battle() {
        if (droids.size() > 1) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n----1-on-1 Battle----");
            System.out.println("Select first droid: ");
            int i = 1;
            for (Droid droid : droids) {
                if (droid.getHealth() > 0) {
                    System.out.println(i + ". " + droid.getName());
                }
                i++;
            }
            System.out.print("Enter your choice: ");
            int choice1 = scanner.nextInt() - 1;
            Droid droid1 = droids.get(choice1);
            System.out.println("Select second droid: ");
            i = 1;
            for (Droid droid : droids) {
                if (droid != droid1 && droid.getHealth() > 0) {
                    System.out.println(i + ". " + droid.getName());
                }
                i++;
            }
            System.out.print("Enter your choice: ");
            int choice2 = scanner.nextInt() - 1;
            Droid droid2 = droids.get(choice2);
            //starts 1v1 battle
            simulate1v1Battle(droid1, droid2);
        } else {
            System.out.println("There are not enough droids to start a 1-on-1 battle.");
        }
    }


    // Method for starting a team-on-team battle
    public static void startTeamBattle() {
        int alivedroids = 0;
        for (Droid droid : droids) {
            if (droid.getHealth() > 0) {
                alivedroids += 1;
            }
        }
        if (alivedroids > 3) {
            Scanner scanner = new Scanner(System.in);
            ArrayList<Droid> team1 = new ArrayList<Droid>();
            ArrayList<Droid> team2 = new ArrayList<Droid>();
            System.out.println("\n----Team Battle----");
            ArrayList<ArrayList> Lobby = planTeamBattle(scanner, team1, team2);
            // Simulate the team-on-team battle
            simulateTeamBattle(Lobby.get(0), Lobby.get(1));
        } else {
            // If there are not enough droids to form two teams, print an error message
            System.out.println("Error: Not enough droids to start a team battle.");
        }
    }

    private static ArrayList<ArrayList> planTeamBattle(Scanner scanner, ArrayList<Droid> team1, ArrayList<Droid> team2) {
        System.out.println("Select droids for team 1: ");
        int i = 1;
        for (Droid droid : droids) {
            if (droid.getHealth() > 0) {
                System.out.println(i + ". " + droid.getName());
            }
            i++;
        }
        while (true) {
            System.out.print("Enter your choice (0 to finish): ");
            int choice = scanner.nextInt() - 1;
            if (choice == -1) {
                break;
            }
            Droid droid = droids.get(choice);
            team1.add(droid);
        }
        System.out.println("Select droids for team 2: ");
        i = 1;
        for (Droid droid : droids) {
            if (!team1.contains(droid) && droid.getHealth() > 0) {
                System.out.println(i + ". " + droid.getName());
            }
            i++;
        }
        while (true) {
            System.out.print("Enter your choice (0 to finish): ");
            int choice = scanner.nextInt() - 1;
            if (choice == -1) {
                break;
            }
            Droid droid = droids.get(choice);
            team2.add(droid);
        }
        ArrayList<ArrayList> Lobby = new ArrayList<ArrayList>();
        Lobby.add(team1);
        Lobby.add(team2);
        return Lobby;

    }

    // Method for recording the details of a droid battle to a file
    public static void recordBattle() {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Droid> team1 = new ArrayList<Droid>();
        ArrayList<Droid> team2 = new ArrayList<Droid>();
        System.out.println("\n---Record the Battle---");
        System.out.println("To make 1v1 battle, choose one per team");
        planTeamBattle(scanner, team1, team2);
        int i;
        File file = new File("battle_record.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            // Write the names of the droids in team 1 to the first line of the file
            for (i = 0; i < team1.size(); i++) {
                writer.write(team1.get(i).getName());
                if (i < team1.size() - 1) {
                    writer.write(",");
                }
            }
            writer.newLine();
            // Write the names of the droids in team 2 to the second line of the file
            for (i = 0; i < team2.size(); i++) {
                writer.write(team2.get(i).getName());
                if (i < team2.size() - 1) {
                    writer.write(",");
                }
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to file.");
            e.printStackTrace();
        }
    }

    // Method for playing a saved battle
    public static void playSavedBattle() {
        File file = new File("battle_record.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            // Read the first line of the file, which contains the names of the droids in team 1
            line = reader.readLine();
            String[] names1 = line.split(",");
            ArrayList<Droid> team1 = new ArrayList<>();
            // Create a new droid for each name in team 1
            for (String name : names1) {
                for (Droid droid : droids) {
                    if (Objects.equals(droid.getName(), name)) {
                        team1.add(droid);
                    }
                }
            }
            // Read the second line of the file, which contains the names of the droids in team 2
            line = reader.readLine();
            String[] names2 = line.split(",");
            ArrayList<Droid> team2 = new ArrayList<>();
            // Create a new droid for each name in team 2
            for (String name : names2) {
                for (Droid droid : droids) {
                    if (Objects.equals(droid.getName(), name)) {
                        team2.add(droid);
                    }
                }
            }
            reader.close();
            if (team1.size() == 1 && team2.size() == 1) {
                //1v1 battle
                simulate1v1Battle(team1.get(0), team2.get(0));
            } else {
                // Simulate the team-on-team battle using the simulateBattle method
                simulateTeamBattle(team1, team2);
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
            e.printStackTrace();
        }
    }

    private static void simulate1v1Battle(Droid droid1, Droid droid2) {
        //Cycle for battle
        while (droid1.getHealth() > 0 && droid2.getHealth() > 0) {
            droid1.attack(droid2);
            if (droid2.getHealth() <= 0) {
                break;
            }
            droid2.attack(droid1);
        }

        if (droid1.getHealth() > 0) {
            System.out.println(droid1.getName() + " wins the battle.");
        } else {
            System.out.println(droid2.getName() + " wins the battle.");
        }
    }

    public static void simulateTeamBattle(ArrayList<Droid> team1, ArrayList<Droid> team2) {
        while (team1.size() > 0 && team2.size() > 0) {
            // Get the first droid from each team
            Droid attacker = team1.get(0);
            Droid defender = team2.get(0);
            // Attacker attacks defender
            attacker.attack(defender);
            // If defender is defeated, remove it from the team
            if (defender.getHealth() <= 0) {
                team2.remove(defender);
            }
            // If team 2 still has droids remaining, get the first droid from each team and have the attacker attack the defender
            if (team2.size() > 0) {
                attacker = team2.get(0);
                defender = team1.get(0);
                attacker.attack(defender);
                // If defender is defeated, remove it from the team
                if (defender.getHealth() <= 0) {
                    team1.remove(defender);
                }
            }
        }
        // Announce the winning team
        if (team1.size() > 0) {
            System.out.println("Team 1 wins the battle.");
        } else {
            System.out.println("Team 2 wins the battle.");
        }
    }


}

