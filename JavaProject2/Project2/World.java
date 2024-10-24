package Project2;

import Project2.Animals.*;
import Project2.Extensions.*;
import java.io.*;
import java.util.*;

public class World {
    private List<Organism> organisms;
    private boolean isHumanAlive, isInEndGameStatus;
    private Organism[][] board;
    private int sizeX, sizeY;
    private int whichRound;
    private boolean pause;
    private Human human;
    private Program program;

    public List<Organism> getOrganisms() {
        return organisms;
    }

    public boolean getIsHumanAlive() {
        return isHumanAlive;
    }

    public void setHumanAlive(boolean isHumanAlive) {
        this.isHumanAlive = isHumanAlive;
    }

    public boolean getIsInEndGameStatus() {
        return isInEndGameStatus;
    }

    public void setInEndGameStatus(boolean isInEndGameStatus) {
        this.isInEndGameStatus = isInEndGameStatus;
    }

    public Organism[][] getBoard() {
        return board;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getWhichRound() {
        return whichRound;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public Human getHuman() {
        return human;
    }

    public void setHuman(Human human) {
        this.human = human;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public World(Program program) {
        this.sizeX = 0;
        this.sizeY = 0;
        whichRound = 0;
        isHumanAlive = true;
        isInEndGameStatus = false;
        pause = true;
        organisms = new ArrayList<>();
        this.program = program;
    }

    public World(int sizeX, int sizeY, Program program) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        whichRound = 0;
        isHumanAlive = true;
        isInEndGameStatus = false;
        pause = true;
        board = new Organism[sizeY][sizeX];
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                board[i][j] = null;
            }
        }
        organisms = new ArrayList<>();
        this.program = program;
    }


    public void SaveWorld(String fileName) {
        try {
            fileName += ".txt";
            File file = new File(fileName);
            file.createNewFile();
            PrintWriter pw = new PrintWriter(file);
            pw.print(sizeX + " ");
            pw.print(sizeY + " ");
            pw.print(whichRound + " ");
            for (int i = 0; i < organisms.size(); i++) {
                pw.print(organisms.get(i).getOrganismType() + " ");
                pw.print(organisms.get(i).getPosition().getX() + " ");
                pw.print(organisms.get(i).getPosition().getY() + " ");
                pw.print(organisms.get(i).getStrength() + " ");
                pw.print(organisms.get(i).getAge() + " ");
                if (organisms.get(i).getOrganismType() == Organism.OrganismType.HUMAN) {
                    pw.print(" " + human.getSpecialAbility().getMagicElixirDuration() + " ");
                    pw.print(human.getSpecialAbility().getCooldown() + " ");
                    pw.print(human.getSpecialAbility().getIsMagicElixirActive() + " ");
                    pw.print(human.getSpecialAbility().getCanMagicElixirBeActivated());
                }
                pw.println();
            }
            pw.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public static World OpenSavedWorld(String nameOfFile) {
        try {
            nameOfFile += ".txt";
            File file = new File(nameOfFile);
            Scanner scanner = new Scanner(file);
            String line = scanner.nextLine();
            String[] properties = line.split(" ");
            int sizeX = Integer.parseInt(properties[0]);
            int sizeY = Integer.parseInt(properties[1]);
            World tmpWorld = new World(sizeX, sizeY, null);
            int whichRound = Integer.parseInt(properties[2]);
            tmpWorld.whichRound = whichRound;
            tmpWorld.human = null;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                properties = line.split(" ");
                Organism.OrganismType organismType = Organism.OrganismType.valueOf(properties[0]);
                int x = Integer.parseInt(properties[1]);
                int y = Integer.parseInt(properties[2]);
                Organism tmpOrganism = PlaceOrganisms.CreateNewOrganism (organismType, tmpWorld, new Placement(x, y));
                int strength = Integer.parseInt(properties[3]);
                tmpOrganism.setStrength(strength);
                int age = Integer.parseInt(properties[4]);
                tmpOrganism.setAge(age);
                if (organismType == Organism.OrganismType.HUMAN) {
                    tmpWorld.human = (Human) tmpOrganism;
                    int magicElixirDuration = Integer.parseInt(properties[6]);
                    tmpWorld.human.getSpecialAbility().setMagicElixirDuration(magicElixirDuration);
                    int cooldown = Integer.parseInt(properties[7]);
                    tmpWorld.human.getSpecialAbility().setCooldown(cooldown);
                    boolean magicElixirActive = Boolean.parseBoolean(properties[8]);
                    tmpWorld.human.getSpecialAbility().setMagicElixirActive(magicElixirActive);
                    boolean canMagicElixirBeActivated = Boolean.parseBoolean(properties[9]);
                    tmpWorld.human.getSpecialAbility().setCanMagicElixirBeActivated(canMagicElixirBeActivated);
                }
                tmpWorld.AddOrganism(tmpOrganism);
            }
            scanner.close();
            return tmpWorld;
        } catch (
                IOException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    public void CreateWorld(double probability) {
        int numberOfOrganisms = (int) Math.floor(sizeX * sizeY * probability);
        Placement placement = RoundNewPosition();
        Organism tmpOrganism = PlaceOrganisms.CreateNewOrganism(Organism.OrganismType.HUMAN, this, placement);
        AddOrganism(tmpOrganism);
        human = (Human) tmpOrganism;
        for (int i = 0; i < numberOfOrganisms - 1; i++) {
            placement = RoundNewPosition();
            if (placement != new Placement(-1, -1)) {
                AddOrganism(PlaceOrganisms.CreateNewOrganism(Organism.RandType(), this, placement));
            } else return;
        }
    }

    public void MakeRound() {
        if (isInEndGameStatus) return;
        whichRound++;
        Information.AddInformation("Round " + whichRound + "!");
        SortOrganisms();
        for (int i = 0; i < organisms.size(); i++) {
            if (organisms.get(i).getAge() != whichRound && organisms.get(i).getIsAlive() == false) organisms.get(i).Action();
        }
        for (int i = 0; i < organisms.size(); i++) {
            if (organisms.get(i).getIsAlive() == true) {
                organisms.remove(i);
                i--;
            }
        }
        for (int i = 0; i < organisms.size(); i++) organisms.get(i).setIsBreedingSpreading(false);
    }

    private void SortOrganisms() {
        Collections.sort(organisms, new Comparator<Organism>() {
            @Override
            public int compare(Organism o1, Organism o2) {
                if (o1.getInitiative() != o2.getInitiative()) return Integer.valueOf(o2.getInitiative()).compareTo(o1.getInitiative());
                else return Integer.valueOf(o1.getAge()).compareTo(o2.getAge());
            }
        });
    }

    public Placement RoundNewPosition() {
        Random random = new Random();
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (board[i][j] == null) {
                    while (true) {
                        int x = random.nextInt(sizeX);
                        int y = random.nextInt(sizeY);
                        if (board[y][x] == null) return new Placement(x, y);
                    }
                }
            }
        }
        return new Placement(-1, -1);
    }

    public boolean IsPlacementOccupied(Placement placement) {
        if (board[placement.getY()][placement.getX()] == null) return false;
        else return true;
    }

    public Organism WhatIsOnPlacement(Placement placement) {
        return board[placement.getY()][placement.getX()];
    }

    public void AddOrganism(Organism organism) {
        organisms.add(organism);
        board[organism.getPosition().getY()][organism.getPosition().getX()] = organism;
    }

    public void RemoveOrganism(Organism organism) {
        board[organism.getPosition().getY()][organism.getPosition().getX()] = null;
        organism.setAlive(true);
        if (organism.getOrganismType() == Organism.OrganismType.HUMAN) {
            isHumanAlive = false;
            human = null;
        }
    }

    public boolean existSosnowskyHogweed() {
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (board[i][j] != null && board[i][j].getOrganismType() == Organism.OrganismType.SOSNOWSKY_HOGWEED) return true;
            }
        }
        return false;
    }
}
