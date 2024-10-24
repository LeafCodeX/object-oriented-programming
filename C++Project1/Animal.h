#pragma once
#include "Organism.h"

class Animal : public Organism {
public:
    Animal(World* currentWorld, int strength, int initiative, int pos_x, int pos_y, int age, bool newborn);
    bool doSpeciesMatch(Organism& entity);
    void breed();
    std::string getName() override = 0;
    void draw() override = 0;
    void action() override;
    virtual void collision(Organism* collidingEntity) override;
    ~Animal();
};