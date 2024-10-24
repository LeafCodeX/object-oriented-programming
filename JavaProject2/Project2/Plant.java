package Project2;

import java.util.*;

import Project2.Extensions.*;

public abstract class Plant extends Organism {

    protected Plant(OrganismType organismType, World world, Placement placement, int age, int strength, int initiative) {
        super(organismType, world, placement, age, strength, initiative);
        setChanceOfBreedingSpreading(0.1);
    }

    @Override
    public void Action() {
        Random random = new Random();
        int upperbound = 100;
        int tmpRandom = random.nextInt(upperbound);
        if (tmpRandom < getChanceOfBreedingSpreading() * 100) Breeding();
    }

    @Override
    public boolean isAnimal() {
        return false;
    }


    protected void Breeding() {
        Placement tmpPlacement = this.randomizeNotOccupiedPlacement(getPosition());
        if (tmpPlacement.equals(getPosition())) return;
        else {
            Organism tmpOrganism = PlaceOrganisms.CreateNewOrganism(getOrganismType(), this.getWorld(), tmpPlacement);
            Information.AddInformation("New " + tmpOrganism.OrganismPlacement()+ " was born!");
            getWorld().AddOrganism(tmpOrganism);
        }
    }

    @Override
    public void Collision(Organism other) {}
}

