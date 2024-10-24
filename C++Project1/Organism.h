#pragma once
#include "World.h"

class Organism {
protected:
    World* currentWorld;
    int strength;
    int initiative;
    int position[2];
    int age;
public:
    bool newBorn;
    Organism(World* currentWorld, int strength, int initiative, int pos_x, int pos_y, int age, bool newBorn);
    virtual int* pathFindNewField();
    int* pathFindNewUnoccupiedField();

    int getStrength();
    int getInitiative();
    void setX(int x);
    void setY(int y);
    int getX();
    int getY();
    int getAge();
    bool getNewBorn();

    virtual bool shield(Organism* entity);
    virtual Organism* clone(int posX, int posY) = 0;
    virtual std::string getName() = 0;
    virtual void draw() = 0;
    virtual void action() = 0;
    virtual void collision(Organism* collidingEntity);
    virtual ~Organism();
};