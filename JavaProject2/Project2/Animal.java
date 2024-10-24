package Project2;

import java.util.*;
import Project2.Extensions.*;

public abstract class Animal extends Organism {
    private int moveLength;
    private double chanceToMove;

    public int getMoveLength() {
        return moveLength;
    }

    public void setMoveLength(int moveLength) {
        this.moveLength = moveLength;
    }

    public double getChanceToMove() {
        return chanceToMove;
    }

    public void setChanceToMove(double chanceToMove) {
        this.chanceToMove = chanceToMove;
    }

    public Animal(OrganismType organismType, World world, Placement placement, int age, int strength, int initiative) {
        super(organismType, world, placement, age, strength, initiative);
        setIsBreedingSpreading(false);
        setChanceOfBreedingSpreading(0.25);
    }

    @Override
    public void Action() {
        for (int i = 0; i < moveLength; i++) {
            Placement futurePlacement = PlanMove();
            if (getWorld().IsPlacementOccupied(futurePlacement) && getWorld().WhatIsOnPlacement(futurePlacement) != this) {
                Collision(getWorld().WhatIsOnPlacement(futurePlacement));
                break;
            } else if (getWorld().WhatIsOnPlacement(futurePlacement) != this) Move(futurePlacement);
        }
    }

    @Override
    public void Collision(Organism attacker) {
        if (getOrganismType() == attacker.getOrganismType()) {
            Random random = new Random();
            int tmpLosowanie = random.nextInt(100);
            if (tmpLosowanie < getChanceOfBreedingSpreading() * 100) Breeding(attacker);
        } else {
            if (attacker.Ability(this, attacker)) return;
            if (Ability(this, attacker)) return;
            if (getStrength() >= attacker.getStrength()) {
                getWorld().RemoveOrganism(attacker);
                Move(attacker.getPosition());
                Information.AddInformation(attacker.OrganismPlacement() + " was eaten by " + OrganismPlacement() + " and died!");
            } else {
                getWorld().RemoveOrganism(this);
                Information.AddInformation(attacker.OrganismPlacement() + " was eaten by " + OrganismPlacement() + " and died!");
            }
        }
    }

    @Override
    public boolean isAnimal() {
        return true;
    }

    protected Placement PlanMove() {
        Random random = new Random();
        int upperbound = 100;
        int tmpRandom = random.nextInt(upperbound);
        if (tmpRandom >= (int) (chanceToMove * 100)) return getPosition();
        else return randomizePlacement(getPosition());
    }

    private void Breeding(Organism partner) {
        if (this.getIsBreedingSpreading() || partner.getIsBreedingSpreading()) return;
        Placement tmpFPlacement = this.randomizeNotOccupiedPlacement(getPosition());
        if (tmpFPlacement.equals(getPosition())) {
            Placement tmpSPlacement = partner.randomizeNotOccupiedPlacement(partner.getPosition());
            if (tmpSPlacement.equals(partner.getPosition())) return;
            else {
                Organism tmpOrganism = PlaceOrganisms.CreateNewOrganism(getOrganismType(), this.getWorld(), tmpSPlacement);
                Information.AddInformation("New " + tmpOrganism.OrganismPlacement() + " was born!");
                getWorld().AddOrganism(tmpOrganism);
                setIsBreedingSpreading(true);
                partner.setIsBreedingSpreading(true);
            }
        } else {
            Organism tmpOrganism = PlaceOrganisms.CreateNewOrganism(getOrganismType(), this.getWorld(), tmpFPlacement);
            Information.AddInformation("Created new " + tmpOrganism.OrganismPlacement() + "!");
            getWorld().AddOrganism(tmpOrganism);
            setIsBreedingSpreading(true);
            partner.setIsBreedingSpreading(true);
        }
    }
}
