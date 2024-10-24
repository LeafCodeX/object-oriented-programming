package Project2.Animals;

import Project2.*;
import Project2.Extensions.*;
import java.util.*;
import java.awt.*;

public class Fox extends Animal {
    private static final int STRENGTH = 3;
    private static final int INITIATIVE = 7;
    private static final int MOVE_LENGTH = 1;
    private static final int CHANCE_MOVE = 1;

    public Fox(World world, Placement position, int age) {
        super(OrganismType.FOX, world, position, age, STRENGTH, INITIATIVE);
        this.setMoveLength(MOVE_LENGTH);
        this.setChanceToMove(CHANCE_MOVE);
        setColor(new Color(255, 128, 0));
    }

    @Override
    public String getName() {
        return "Fox";
    }

    @Override
    public Placement randomizePlacement(Placement position) {
        UnlockAllDirections();
        int posX = position.getX();
        int posY = position.getY();
        int sizeX = getWorld().getSizeX();
        int sizeY = getWorld().getSizeY();
        int counterPossibleDirections = 0;
        Organism tmpOrganism;
        if (posX == 0) LockDirection(Direction.TURN_LEFT);
        else {
            tmpOrganism = getWorld().getBoard()[posY][posX - 1];
            if (tmpOrganism != null && tmpOrganism.getStrength() > this.getStrength()) LockDirection(Direction.TURN_LEFT);
            else counterPossibleDirections++;
        }
        if (posX == sizeX - 1) LockDirection(Direction.TURN_RIGHT);
        else {
            tmpOrganism = getWorld().getBoard()[posY][posX + 1];
            if (tmpOrganism != null && tmpOrganism.getStrength() > this.getStrength()) LockDirection(Direction.TURN_RIGHT);
            else counterPossibleDirections++;
        }
        if (posY == 0) LockDirection(Direction.TURN_UP);
        else {
            tmpOrganism = getWorld().getBoard()[posY - 1][posX];
            if (tmpOrganism != null && tmpOrganism.getStrength() > this.getStrength()) LockDirection(Direction.TURN_UP);
            else counterPossibleDirections++;
        }
        if (posY == sizeY - 1) LockDirection(Direction.TURN_DOWN);
        else {
            tmpOrganism = getWorld().getBoard()[posY + 1][posX];
            if (tmpOrganism != null && tmpOrganism.getStrength() > this.getStrength()) LockDirection(Direction.TURN_DOWN);
            else counterPossibleDirections++;
        }
        if (counterPossibleDirections == 0) return new Placement(posX, posY);
        while (true) {
            Random random = new Random();
            int upperbound = 100;
            int tmpRandom = random.nextInt(upperbound);
            if (tmpRandom < 25 && !isDirectionLocked(Direction.TURN_LEFT)) return new Placement(posX - 1, posY);
            else if (tmpRandom >= 25 && tmpRandom < 50 && !isDirectionLocked(Direction.TURN_RIGHT)) return new Placement(posX + 1, posY);
            else if (tmpRandom >= 50 && tmpRandom < 75 && !isDirectionLocked(Direction.TURN_UP)) return new Placement(posX, posY - 1);
            else if (tmpRandom >= 75 && !isDirectionLocked(Direction.TURN_DOWN)) return new Placement(posX, posY + 1);
        }
    }
}
