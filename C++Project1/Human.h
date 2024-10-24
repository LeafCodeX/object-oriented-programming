#pragma once
#include "Animal.h"

class Human : public Animal {
public:
    Human(World* currentWorld, int strength, int initiative, int posX, int posY, int age);
    void draw() override;
    std::string getName() override;
    void action() override;
    void specialAbility();
    Organism* clone(int posX, int posY) override;
    ~Human();
};