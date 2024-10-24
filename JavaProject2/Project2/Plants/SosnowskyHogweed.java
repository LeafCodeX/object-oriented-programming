package Project2.Plants;

import Project2.*;
import Project2.Extensions.*;
import java.awt.*;
import java.util.*;

public class SosnowskyHogweed extends Plant {
    private static final int STRENGTH = 10;
    private static final int INITIATIVE = 0;

    public SosnowskyHogweed(World world, Placement position, int age) {
        super(OrganismType.SOSNOWSKY_HOGWEED, world, position, age, STRENGTH, INITIATIVE);
        setColor(new Color(102, 102, 0));
        setChanceOfBreedingSpreading(0.05);
    }

    @Override
    public void Action() {
        int posX = getPosition().getX();
        int posY = getPosition().getY();
        randomizePlacement(getPosition());
        for (int i = 0; i < 4; i++) {
            Organism tmpOrganism = null;
            if (i == 0 && !isDirectionLocked(Direction.TURN_DOWN)) tmpOrganism = getWorld().WhatIsOnPlacement(new Placement(posX, posY + 1));
            else if (i == 1 && !isDirectionLocked(Direction.TURN_UP)) tmpOrganism = getWorld().WhatIsOnPlacement(new Placement(posX, posY - 1));
            else if (i == 2 && !isDirectionLocked(Direction.TURN_LEFT)) tmpOrganism = getWorld().WhatIsOnPlacement(new Placement(posX - 1, posY));
            else if (i == 3 && !isDirectionLocked(Direction.TURN_RIGHT)) tmpOrganism = getWorld().WhatIsOnPlacement(new Placement(posX + 1, posY));
            if (tmpOrganism != null && tmpOrganism.isAnimal()) {
                getWorld().RemoveOrganism(tmpOrganism);
                Information.AddInformation(OrganismPlacement() + " kills " + tmpOrganism.OrganismPlacement() + "!");
            }
        }
        Random random = new Random();
        int tmpRandom = random.nextInt(100);
        if (tmpRandom < getChanceOfBreedingSpreading() * 100) Breeding();
    }

    @Override
    public String getName() {
        return "SosnowskyHogweed";
    }

    @Override
    public boolean Ability(Organism collidingEntity, Organism victim) {
        if (collidingEntity.getStrength() >= 10) {
            getWorld().RemoveOrganism(this);
            Information.AddInformation(collidingEntity.OrganismPlacement() + " survived eating " + this.OrganismPlacement() + "!");
            collidingEntity.Move(victim.getPosition());
        }
        if (collidingEntity.getStrength() < 10) {
            getWorld().RemoveOrganism(collidingEntity);
            Information.AddInformation(this.OrganismPlacement() + " kills " + collidingEntity.OrganismPlacement() + "!");
        }
        return true;
    }
}
