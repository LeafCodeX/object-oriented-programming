package Project2.Plants;

import Project2.*;
import Project2.Extensions.*;
import java.util.*;
import java.awt.*;

public class Dandelion extends Plant {
    private static final int STRENGTH = 0;
    private static final int INITIATIVE = 0;
    private static final int NUMBER_OF_SEEDS = 3;

    public Dandelion(World world, Placement position, int age) {
        super(OrganismType.DANDELION, world, position, age, STRENGTH, INITIATIVE);
        setColor(new Color(255, 214, 75));
    }

    @Override
    public void Action() {
        Random random = new Random();
        for (int i = 0; i < NUMBER_OF_SEEDS; i++) {
            int tmpRandom = random.nextInt(100);
            if (tmpRandom < getChanceOfBreedingSpreading()) Breeding();
        }
    }

    @Override
    public String getName() {
        return "Dandelion";
    }
}

