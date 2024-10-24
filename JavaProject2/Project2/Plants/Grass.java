package Project2.Plants;

import Project2.*;
import Project2.Extensions.*;
import java.awt.*;

public class Grass extends Plant {
    private static final int STRENGTH = 0;
    private static final int INITIATIVE = 0;

    public Grass(World world, Placement position, int age) {
        super(OrganismType.GRASS, world, position, age, STRENGTH, INITIATIVE);
        setColor(new Color(0, 204, 0));
    }

    @Override
    public String getName() {
        return "Grass";
    }
}
