#pragma once
#include "Plant.h"

class DeadlyNigtshade : public Plant {
public:
    DeadlyNigtshade(World* currentWorld, int posX, int posY, int age, bool newBorn);
    void draw() override;
    std::string getName() override;
    Organism* clone(int posX, int posY) override;
    ~DeadlyNigtshade() override;
};