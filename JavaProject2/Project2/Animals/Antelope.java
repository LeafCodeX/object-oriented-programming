package Project2.Animals;

import Project2.*;
import Project2.Extensions.*;
import java.util.*;
import java.awt.*;

public class Antelope extends Animal {
    private static final int STRENGTH = 4;
    private static final int INITIATIVE = 4;
    private static final int MOVE_LENGTH = 2;
    private static final int CHANCE_MOVE = 1;

    public Antelope(World world, Placement position, int age) {
        super(OrganismType.ANTELOPE, world, position, age, STRENGTH, INITIATIVE);
        this.setMoveLength(MOVE_LENGTH);
        this.setChanceToMove(CHANCE_MOVE);
        setColor(new Color(153, 76, 0));
    }

    @Override
    public String getName() {
        return "Antelope";
    }

    @Override
    public boolean Ability(Organism collidingEntity, Organism victim) {
        Random random = new Random();
        int tmpRandom = random.nextInt(100);
        if (tmpRandom < 50) {
            if (this == collidingEntity) {
                Information.AddInformation(OrganismPlacement() + " escaped from " + victim.OrganismPlacement() + "!");
                Placement tmpPlacement = randomizeNotOccupiedPlacement(victim.getPosition());
                if (!tmpPlacement.equals(victim.getPosition()))
                    Move(tmpPlacement);
            } else if (this == victim) {
                Information.AddInformation(OrganismPlacement() + " escaped from " + collidingEntity.OrganismPlacement() + "!");
                Placement tmpPlacement = this.getPosition();
                Move(randomizeNotOccupiedPlacement(this.getPosition()));
                if (getPosition().equals(tmpPlacement)) {
                    getWorld().RemoveOrganism(this);
                    Information.AddInformation(collidingEntity.OrganismPlacement() + " died from " + OrganismPlacement() + " attack!");
                }
                collidingEntity.Move(tmpPlacement);
            }
            return true;
        } else return false;
    }
}