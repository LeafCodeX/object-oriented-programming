#pragma once
#include "Animal.h"

class Antelope : public Animal {
public:
    Antelope(World* currentWorld, int strength, int initiative, int posX, int posY, int age, bool newBorn);
    void draw() override;
    std::string getName() override;
    int* pathFindNewField() override;
    void collision(Organism* collidingEntity) override;
    Organism* clone(int posX, int posY) override;
    ~Antelope() override;
};