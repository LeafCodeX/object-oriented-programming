package Project2.Animals;

import Project2.*;
import Project2.Extensions.*;
import java.awt.*;

public class Sheep extends Animal {
    private static final int STRENGTH = 4;
    private static final int INITIATIVE = 4;
    private static final int MOVE_LENGTH = 1;
    private static final int CHANCE_MOVE = 1;

    public Sheep(World world, Placement position, int age) {
        super(OrganismType.SHEPP, world, position, age, STRENGTH, INITIATIVE);
        this.setMoveLength(MOVE_LENGTH);
        this.setChanceToMove(CHANCE_MOVE);
        setColor(new Color(192,192,192));
    }

    @Override
    public String getName() {
        return "Sheep";
    }
}
