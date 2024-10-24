package Project2;

import java.awt.*;
import java.util.*;
import Project2.Extensions.*;

public abstract class Organism {
    private int strength;
    private int initiative;
    private int age;
    private Color color;
    private boolean isAlive;
    private boolean[] direction;
    private boolean isBreedingSpreading;
    protected World world;
    private Placement position;
    private OrganismType organismType;
    private double chanceOfBreedingSpreading;
    private static final int ENTITIES = 11;
    public abstract String getName();
    public abstract void Action();
    public abstract void Collision(Organism collidingOrganism);
    public abstract boolean isAnimal();

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Placement getPosition() {
        return position;
    }

    public void setPosition(Placement position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public OrganismType getOrganismType() {
        return organismType;
    }

    public void setOrganismType(OrganismType organismType) {
        this.organismType = organismType;
    }

    public boolean getIsBreedingSpreading() {
        return isBreedingSpreading;
    }

    public void setIsBreedingSpreading(boolean isBreedingSpreading) {
        this.isBreedingSpreading = isBreedingSpreading;
    }

    public double getChanceOfBreedingSpreading() {
        return chanceOfBreedingSpreading;
    }

    public void setChanceOfBreedingSpreading(double chanceOfBreedingSpreading) {
        this.chanceOfBreedingSpreading = chanceOfBreedingSpreading;
    }

    public Organism(OrganismType organismType, World world, Placement position, int age, int strength, int initiative) {
        this.organismType = organismType;
        this.world = world;
        this.position = position;
        this.age = age;
        this.strength = strength;
        this.initiative = initiative;
        isAlive = false;
        direction = new boolean[]{true, true, true, true};
    }

    public String OrganismPlacement() {
        return (getName() + " (" + position.getX() + ","
                + position.getY() + ")");
    }

    public boolean Ability(Organism collidingEntity, Organism victim) {
        return false;
    }

    public void Move(Placement nextPosition) {
        int x = nextPosition.getX();
        int y = nextPosition.getY();
        world.getBoard()[position.getY()][position.getX()] = null;
        world.getBoard()[y][x] = this;
        position.setX(x);
        position.setY(y);
    }

    static OrganismType RandType() {
        Random random = new Random();
        int entity = random.nextInt(ENTITIES - 1);
        if (entity == 0) return OrganismType.ANTELOPE;
        if (entity == 1) return OrganismType.SOSNOWSKY_HOGWEED;
        if (entity == 2) return OrganismType.GUARANA;
        if (entity == 3) return OrganismType.FOX;
        if (entity == 4) return OrganismType.DANDELION;
        if (entity == 5) return OrganismType.SHEPP;
        if (entity == 6) return OrganismType.GRASS;
        if (entity == 7) return OrganismType.DEADLY_NIGHTSHADE;
        if (entity == 8) return OrganismType.WOLF;
        else return OrganismType.TURTLE;
    }

    public Placement randomizePlacement(Placement position) {
        UnlockAllDirections();
        int posX = position.getX();
        int posY = position.getY();
        int sizeX = world.getSizeX();
        int sizeY = world.getSizeY();
        int counterPossibleDirections = 0;

        if (posX == 0) LockDirection(Direction.TURN_LEFT);
        else counterPossibleDirections++;
        if (posX == sizeX - 1) LockDirection(Direction.TURN_RIGHT);
        else counterPossibleDirections++;
        if (posY == 0) LockDirection(Direction.TURN_UP);
        else counterPossibleDirections++;
        if (posY == sizeY - 1) LockDirection(Direction.TURN_DOWN);
        else counterPossibleDirections++;

        if (counterPossibleDirections == 0) return position;
        while (true) {
            Random random = new Random();
            int upperbound = 100;
            int randomize = random.nextInt(upperbound);
            if (randomize < 25 && !isDirectionLocked(Direction.TURN_LEFT)) return new Placement(posX - 1, posY);
            else if (randomize >= 25 && randomize < 50 && !isDirectionLocked(Direction.TURN_RIGHT)) return new Placement(posX + 1, posY);
            else if (randomize >= 50 && randomize < 75 && !isDirectionLocked(Direction.TURN_UP)) return new Placement(posX, posY - 1);
            else if (randomize >= 75 && !isDirectionLocked(Direction.TURN_DOWN)) return new Placement(posX, posY + 1);
        }
    }

    public Placement randomizeNotOccupiedPlacement(Placement position) {
        UnlockAllDirections();
        int pozX = position.getX();
        int pozY = position.getY();
        int sizeX = world.getSizeX();
        int sizeY = world.getSizeY();
        int counterPossibleDirections = 0;

        if (pozX == 0) LockDirection(Direction.TURN_LEFT);
        else {
            if (world.IsPlacementOccupied(new Placement(pozX - 1, pozY)) == false) counterPossibleDirections++;
            else LockDirection(Direction.TURN_LEFT);
        }
        if (pozX == sizeX - 1) LockDirection(Direction.TURN_RIGHT);
        else {
            if (world.IsPlacementOccupied(new Placement(pozX + 1, pozY)) == false) counterPossibleDirections++;
            else LockDirection(Direction.TURN_RIGHT);
        }
        if (pozY == 0) LockDirection(Direction.TURN_UP);
        else {
            if (world.IsPlacementOccupied(new Placement(pozX, pozY - 1)) == false) counterPossibleDirections++;
            else LockDirection(Direction.TURN_UP);
        }
        if (pozY == sizeY - 1) LockDirection(Direction.TURN_DOWN);
        else {
            if (world.IsPlacementOccupied(new Placement(pozX, pozY + 1)) == false) counterPossibleDirections++;
            else LockDirection(Direction.TURN_DOWN);
        }
        if (counterPossibleDirections == 0) return new Placement(pozX, pozY);
        while (true) {
            Random random = new Random();
            int upperbound = 100;
            int randomize = random.nextInt(upperbound);
            if (randomize < 25 && !isDirectionLocked(Direction.TURN_LEFT)) return new Placement(pozX - 1, pozY);
            else if (randomize >= 25 && randomize < 50 && !isDirectionLocked(Direction.TURN_RIGHT)) return new Placement(pozX + 1, pozY);
            else if (randomize >= 50 && randomize < 75 && !isDirectionLocked(Direction.TURN_UP)) return new Placement(pozX, pozY - 1);
            else if (randomize >= 75 && !isDirectionLocked(Direction.TURN_DOWN)) return new Placement(pozX, pozY + 1);
        }
    }

    public enum OrganismType { HUMAN, WOLF, SHEPP, FOX, TURTLE, ANTELOPE, GRASS, DANDELION, GUARANA, DEADLY_NIGHTSHADE, SOSNOWSKY_HOGWEED; }

    protected void LockDirection(Direction direction) {
        this.direction[direction.getValue()] = false;
    }

    protected void UnlockDirection(Direction direction) {
        this.direction[direction.getValue()] = true;
    }

    protected void UnlockAllDirections() {
        UnlockDirection(Direction.TURN_LEFT);
        UnlockDirection(Direction.TURN_RIGHT);
        UnlockDirection(Direction.TURN_UP);
        UnlockDirection(Direction.TURN_DOWN);
    }

    protected boolean isDirectionLocked(Direction direction) {
        return !(this.direction[direction.getValue()]);
    }

    public enum Direction { 
        TURN_LEFT(0), TURN_RIGHT(1), TURN_UP(2), TURN_DOWN(3), STAY_PUT(4);
        private final int value;

        private Direction(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
