#include "Turtle.h"

Turtle::Turtle(World* currentWorld, int strength, int initiative, int posX, int posY, int age, bool newBorn)
    : Animal(currentWorld, strength, initiative, posX, posY, age, newBorn) {
};

void Turtle::draw() {
    std::cout << char(84);
};

std::string Turtle::getName() {
    return ("Turtle");
};

void Turtle::action() {
    age++;
    int randomTick = rand() % 100 + 1;
    if (randomTick > 75) Animal::action(); // 75% na brak zmiany polozenia
};

bool Turtle::shield(Organism* entity) {
    return (entity->getStrength() < 5); // odpieranie atkow
};

Organism* Turtle::clone(int posX, int posY) {
    return new Turtle(currentWorld, strength, initiative, posX, posY, 1, true);
};

Turtle::~Turtle() {
};
