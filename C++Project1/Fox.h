#pragma once
#include "Animal.h"

class Fox : public Animal {
public:
    Fox(World* currentWorld, int strength, int initiative, int posX, int posY, int age, bool newBorn);
    void draw() override;
    std::string getName() override;
    void action() override;
    Organism* clone(int posX, int posY) override;
    ~Fox() override;
};