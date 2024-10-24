#include "DeadlyNigtshade.h"

DeadlyNigtshade::DeadlyNigtshade(World* currentWorld, int posX, int posY, int age, bool newBorn)
    : Plant(currentWorld, 99, posX, posY, age, newBorn) {
};

void DeadlyNigtshade::draw() {
    std::cout << char(78);
};

std::string DeadlyNigtshade::getName() {
    return ("DeadlyNigtshade");
};

Organism* DeadlyNigtshade::clone(int posX, int posY) {
    return new DeadlyNigtshade(currentWorld, posX, posY, 1, true);
};

DeadlyNigtshade::~DeadlyNigtshade() {
};