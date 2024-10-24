package Project2.Animals;

import Project2.*;
import Project2.Extensions.*;
import java.awt.*;

public class Human extends Animal {
    private static final int STRENGTH = 5;
    private static final int INITIATIVE = 4;
    private static final int MOVE_LENGTH = 1;
    private static final int CHANCE_MOVE = 1;
    private Direction direction;
    private SpecialAbility specialAbility;

    public SpecialAbility getSpecialAbility() {
        return specialAbility;
    }

    public void setDirectionMovement(Direction kierunekRuchu) {
        this.direction = kierunekRuchu;
    }

    @Override
    public String getName() {
        return "Human";
    }

    public Human(World world, Placement position, int age) {
        super(OrganismType.HUMAN, world, position, age, STRENGTH, INITIATIVE);
        this.setMoveLength(MOVE_LENGTH);
        this.setChanceToMove(CHANCE_MOVE);
        direction = Direction.STAY_PUT;
        setColor(new Color(204,0,204));
        specialAbility = new SpecialAbility();
    }

    @Override
    protected Placement PlanMove() {
        int x = getPosition().getX();
        int y = getPosition().getY();
        randomizePlacement(getPosition());
        if (direction == Direction.STAY_PUT || isDirectionLocked(direction)) return getPosition();
        else {
            if (direction == Direction.TURN_DOWN) return new Placement(x, y + 1);
            if (direction == Direction.TURN_UP) return new Placement(x, y - 1);
            if (direction == Direction.TURN_LEFT) return new Placement(x - 1, y);
            if (direction == Direction.TURN_RIGHT) return new Placement(x + 1, y);
            else return new Placement(x, y);
        }
    }

    @Override
    public void Action() {
        for (int i = 0; i < getMoveLength(); i++) {
            Placement futurePosition = PlanMove();
            if (getWorld().IsPlacementOccupied(futurePosition) && getWorld().WhatIsOnPlacement(futurePosition) != this) {
                Collision(getWorld().WhatIsOnPlacement(futurePosition));
                break;
            } else if (getWorld().WhatIsOnPlacement(futurePosition) != this) Move(futurePosition);
        }
        direction = Direction.STAY_PUT;
        specialAbility.check();
    }
}

