#include "Sheep.h"

Sheep::Sheep(World* currentWorld, int strength, int initiative, int posX, int posY, int age, bool newBorn)
    : Animal(currentWorld, strength, initiative, posX, posY, age, newBorn) {
};

void Sheep::draw() {
    std::cout << char(83);
};

std::string Sheep::getName() {
    return ("Sheep");
};

Organism* Sheep::clone(int posX, int posY) {
    return new Sheep(currentWorld, strength, initiative, posX, posY, 1, true);
};

Sheep::~Sheep() {
};