#include "Wolf.h"

Wolf::Wolf(World* currentWorld, int strength, int initiative, int posX, int posY, int age, bool newBorn)
    : Animal(currentWorld, strength, initiative, posX, posY, age, newBorn) {
};

void Wolf::draw() {
    std::cout << char(87);
};

std::string Wolf::getName() {
    return ("Wolf");
};

Organism* Wolf::clone(int posX, int posY) {
    return new Wolf(currentWorld, strength, initiative, posX, posY, 1, true);
};

Wolf::~Wolf() {

};