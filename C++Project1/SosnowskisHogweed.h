#pragma once
#include "Plant.h"

class SosnowskisHogweed : public Plant {
public:
    SosnowskisHogweed(World* currentWorld, int posX, int posY, int age, bool newBorn);
    void draw() override;
    std::string getName() override;
    void action() override;
    Organism* clone(int posX, int posY) override;
    ~SosnowskisHogweed() override;
};