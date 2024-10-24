package Project2.Animals;

import Project2.*;
import Project2.Extensions.*;
import java.awt.*;

public class Wolf extends Animal {
    private static int STRENGTH = 9;
    private static final int INITIATIVE = 5;
    private static final int MOVE_LENGTH = 1;
    private static final int CHANCE_MOVE = 1;

    public Wolf(World world, Placement position, int age) {
        super(OrganismType.WOLF, world, position, age, STRENGTH, INITIATIVE);
        this.setMoveLength(MOVE_LENGTH);
        this.setChanceToMove(CHANCE_MOVE);
        setColor(new Color(32,32,32));
    }

    @Override
    public String getName() {
        return "Wolf";
    }
}

