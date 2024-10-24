#pragma once
#include "Plant.h"

class Grass : public Plant {
public:
    Grass(World* currentWorld, int posX, int posY, int age, bool newBorn);
    void draw() override;
    std::string getName() override;
    Organism* clone(int posX, int posY) override;
    ~Grass() override;
};