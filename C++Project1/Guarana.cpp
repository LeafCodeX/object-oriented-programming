#include "Guarana.h"

Guarana::Guarana(World* currentWorld, int posX, int posY, int age, bool newBorn)
    : Plant(currentWorld, 0, posX, posY, age, newBorn) {
};

void Guarana::draw() {
    std::cout << char(71);
};

std::string Guarana::getName() {
    return ("Guarana");
};

Organism* Guarana::clone(int posX, int posY) {
    return new Guarana(currentWorld, posX, posY, 1, true);
};

Guarana::~Guarana() {
};