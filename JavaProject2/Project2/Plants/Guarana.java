package Project2.Plants;

import Project2.*;
import Project2.Extensions.*;
import java.awt.*;

public class Guarana extends Plant {
    private static final int INCREASE_STRENGTH = 3;
    private static final int STRENGTH = 0;
    private static final int INITIATIVE = 0;

    public Guarana(World world, Placement position, int age) {
        super(OrganismType.GUARANA, world, position, age, STRENGTH, INITIATIVE);
        setColor(new Color(153,0,0));
    }

    @Override
    public String getName() {
        return "Guarana";
    }

    @Override
    public boolean Ability(Organism collidingEnity, Organism victim) {
        Placement tmpPlacement = this.getPosition();
        getWorld().RemoveOrganism(this);
        collidingEnity.Move(tmpPlacement);
        Information.AddInformation(collidingEnity.OrganismPlacement() + " ate " + this.OrganismPlacement() + " and increased strength!");
        collidingEnity.setStrength(collidingEnity.getStrength() + INCREASE_STRENGTH);
        return true;
    }
}


