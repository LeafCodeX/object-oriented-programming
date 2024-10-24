package Project2.Extensions;

import Project2.*;
import Project2.Animals.*;
import Project2.Plants.*;

public class PlaceOrganisms {
    public static Organism CreateNewOrganism (Organism.OrganismType organismType, World world, Placement placement) {
        switch (organismType) {
            case WOLF: return new Wolf(world, placement, world.getWhichRound());
            case SHEPP: return new Sheep(world, placement, world.getWhichRound());
            case FOX: return new Fox(world, placement, world.getWhichRound());
            case TURTLE: return new Turtle(world, placement, world.getWhichRound());
            case ANTELOPE: return new Antelope(world, placement, world.getWhichRound());
            case HUMAN: return new Human(world, placement, world.getWhichRound());
            case GRASS: return new Grass(world, placement, world.getWhichRound());
            case DANDELION: return new Dandelion(world, placement, world.getWhichRound());
            case GUARANA: return new Guarana(world, placement, world.getWhichRound());
            case DEADLY_NIGHTSHADE: return new DeadlyNightshade(world, placement, world.getWhichRound());
            case SOSNOWSKY_HOGWEED: return new SosnowskyHogweed(world, placement, world.getWhichRound());
            default: return null;
        }
    }
}
