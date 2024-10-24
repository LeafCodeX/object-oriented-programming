package Project2.Plants;

import Project2.*;
import Project2.Extensions.*;
import java.util.*;
import java.awt.*;

public class DeadlyNightshade extends Plant {
    private static final int STRENGTH = 99;
    private static final int INITIATIVE = 0;

    public DeadlyNightshade(World world, Placement position, int age) {
        super(OrganismType.DEADLY_NIGHTSHADE, world, position, age, STRENGTH, INITIATIVE);
        setColor(new Color(51, 51, 255));
        setChanceOfBreedingSpreading(0.05);
    }

    @Override
    public void Action() {
        Random random = new Random();
        int upperbound = 100;
        int tmpRandom = random.nextInt(upperbound);
        if (tmpRandom < getChanceOfBreedingSpreading() * 100) Breeding();
    }

    @Override
    public String getName() {
        return "DeadlyNightshade";
    }

    @Override
    public boolean Ability(Organism collidingEntity, Organism victim) {
        Information.AddInformation(collidingEntity.OrganismPlacement() + " ate " + this.OrganismPlacement() + "!");
        if (collidingEntity.getStrength() >= 99) {
            getWorld().RemoveOrganism(this);
            Information.AddInformation(collidingEntity.OrganismPlacement() + " survived eating " + this.OrganismPlacement() + "!");
        }
        if (collidingEntity.isAnimal()) {
            getWorld().RemoveOrganism(collidingEntity);
            Information.AddInformation(collidingEntity.OrganismPlacement() + " died eating " + this.OrganismPlacement() + "!");
        }
        return true;
    }
}
