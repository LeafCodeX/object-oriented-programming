#pragma once
#include "Animal.h"

class Turtle : public Animal {
public:
    Turtle(World* currentWorld, int strength, int initiative, int posX, int posY, int age, bool newBorn);
    void draw() override;
    std::string getName() override;
    void action() override;
    bool shield(Organism* entity) override;
    Organism* clone(int posX, int posY) override;
    ~Turtle() override;
};