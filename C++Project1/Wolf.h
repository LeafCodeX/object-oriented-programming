#pragma once
#include "Animal.h"

class Wolf : public Animal {
public:
    Wolf(World* currentWorld, int strength, int initiative, int posX, int posY, int age, bool newBorn);
    void draw() override;
    std::string getName() override;
    Organism* clone(int posX, int posY) override;
    ~Wolf() override;
};