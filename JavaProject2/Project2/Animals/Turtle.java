package Project2.Animals;

import Project2.*;
import Project2.Extensions.*;
import java.awt.*;

public class Turtle extends Animal {
    private static final int STRENGTH = 2;
    private static final int INITIATIVE = 1;
    private static final int MOVE_LENGTH = 1;
    private static final double CHANCE_MOVE = 0.25;

    public Turtle(World world, Placement placement, int age) {
        super(OrganismType.TURTLE, world, placement, age, STRENGTH, INITIATIVE);
        this.setMoveLength(MOVE_LENGTH);
        this.setChanceToMove(CHANCE_MOVE);
        setColor(new Color(0, 153, 0));
    }

    @Override
    public String getName() {
        return "Turtle";
    }

    @Override
    public boolean Ability(Organism collidingEntity, Organism victim) {
        if (this == victim) {
            if (collidingEntity.getStrength() < 5 && collidingEntity.isAnimal()) {
                Information.AddInformation(OrganismPlacement() + " blocked attack from " + collidingEntity.OrganismPlacement() + "!");
                return true;
            } else return false;
        } else {
            if (collidingEntity.getStrength() >= victim.getStrength()) return false;
            else {
                if (victim.getStrength() < 5 && victim.isAnimal()) {
                    Information.AddInformation(OrganismPlacement() + " blocked attack from " + victim.OrganismPlacement() + "!");
                    return true;
                } else return false;
            }
        }
    }
}
