#pragma once
#include "Plant.h"

class Guarana : public Plant {
public:
    Guarana(World* currentWorld, int posX, int posY, int age, bool newBorn);
    void draw() override;
    std::string getName() override;
    Organism* clone(int posX, int posY) override;
    ~Guarana() override;
};