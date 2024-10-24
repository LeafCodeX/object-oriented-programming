#include "Grass.h"

Grass::Grass(World* currentWorld, int posX, int posY, int age, bool newBorn)
    : Plant(currentWorld, 0, posX, posY, age, newBorn) {
};

void Grass::draw() {
    std::cout << char(177);
};

std::string Grass::getName() {
    return ("Grass");
};

Organism* Grass::clone(int posX, int posY) {
    return new Grass(currentWorld, posX, posY, 1, true);
};

Grass::~Grass() {
};