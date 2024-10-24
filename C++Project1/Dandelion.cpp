#include "Dandelion.h"

Dandelion::Dandelion(World* currentWorld, int posX, int posY, int age, bool newBorn)
    : Plant(currentWorld, 0, posX, posY, age, newBorn) {

};

void Dandelion::draw() {
    std::cout << char(68);
};

std::string Dandelion::getName() {
    return ("Dandelion");
};

void Dandelion::action() {
    age++;
    for (int i = 0; i < 3; i++) Plant::action(); // 3-krotna proba rozmnozenia 
};

Organism* Dandelion::clone(int posX, int posY) {
    return new Dandelion(currentWorld, posX, posY, 1, true);
};

Dandelion::~Dandelion() {

};