#pragma once
#include "Plant.h"

class Dandelion : public Plant {
public:
    Dandelion(World* currentWorld, int posX, int posY, int age, bool newBorn);
    void draw() override;
    std::string getName() override;
    void action() override;
    Organism* clone(int posX, int posY) override;
    ~Dandelion() override;
};