#include "Fox.h"

Fox::Fox(World* currentWorld, int strength, int initiative, int posX, int posY, int age, bool newBorn)
    : Animal(currentWorld, strength, initiative, posX, posY, age, newBorn) {
};

void Fox::draw() {
    std::cout << char(70);
};

std::string Fox::getName() {
    return ("Fox");
};

void Fox::action() {
    age++;
    int* newPosition = pathFindNewField(); // specyfikacja dla lisa (dobry wech - nie ruszy sie na pole zajmowane przez silniejszego osobnika)
    if (currentWorld->entitySpace[newPosition[0]][newPosition[1]] == NULL) {
        currentWorld->entitySpace[newPosition[0]][newPosition[1]] = this;
        currentWorld->entitySpace[position[0]][position[1]] = NULL;
        position[0] = newPosition[0];
        position[1] = newPosition[1];
    }
    else {
        if (currentWorld->entitySpace[newPosition[0]][newPosition[1]]->getStrength() <= getStrength()) { // ruch jesli jest w stanie go pokonac
            collision(currentWorld->entitySpace[newPosition[0]][newPosition[1]]);
        }
        else  std::cout << this->getName() << " isn't moving from (" << position[0] << ";" << position[1] << "). The selected field is occupied by " << currentWorld->entitySpace[newPosition[0]][newPosition[1]]->getName() << " (" << currentWorld->entitySpace[newPosition[0]][newPosition[1]]->getX() << ";" << currentWorld->entitySpace[newPosition[0]][newPosition[1]]->getY() << ").\n";
    }
    delete newPosition;
};

Organism* Fox::clone(int posX, int posY) {
    return new Fox(currentWorld, strength, initiative, posX, posY, 1, true);
};

Fox::~Fox() {
};