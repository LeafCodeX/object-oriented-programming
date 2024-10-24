#include "Organism.h"

Organism::Organism(World* currentWorld, int strength, int initiative, int posX, int posY, int age, bool newBorn)
    : currentWorld(currentWorld), strength(strength), initiative(initiative), age(age), newBorn(newBorn) {
    this->position[0] = posX;
    this->position[1] = posY;
    if (posX == -1 && posY == -1) return;
    currentWorld->entitySpace[posX][posY] = this;
    currentWorld->entityLookup->addNew(this); // kolejka
};

int* Organism::pathFindNewField() {
    int* returnPosition = new int[2];
    returnPosition[0] = position[0];
    returnPosition[1] = position[1];
    int field;
    bool invalidMove;
    do {
        field = rand() % 4;
        invalidMove = false;
        switch (field) {
        case 0:
            if (returnPosition[1] == 0) invalidMove = true;  // Góra
            else returnPosition[1]--;
            break;
        case 1:
            if (returnPosition[0] == currentWorld->getN() - 1) invalidMove = true; // Prawo
            else returnPosition[0]++;
            break;
        case 2:
            if (returnPosition[1] == currentWorld->getM() - 1) invalidMove = true; // Dó³
            else returnPosition[1]++;
            break;
        case 3:
            if (returnPosition[0] == 0) invalidMove = true; // Lewo
            else returnPosition[0]--;
            break;
        }
    } while (invalidMove);
    return returnPosition;
};

int* Organism::pathFindNewUnoccupiedField() {
    int* returnPosition = new int[2];
    returnPosition[0] = position[0];
    returnPosition[1] = position[1];

    // Góra
    if ((returnPosition[1] != 0) && (currentWorld->entitySpace[returnPosition[0]][returnPosition[1] - 1] == NULL)) {
        returnPosition[1]--;
        return returnPosition;
    }
    // Prawo
    else if ((returnPosition[0] != currentWorld->getN() - 1) && (currentWorld->entitySpace[returnPosition[0] + 1][returnPosition[1]] == NULL)) {
        returnPosition[0]++;
        return returnPosition;
    }
    // Dó³
    else if ((returnPosition[1] != currentWorld->getM() - 1) && (currentWorld->entitySpace[returnPosition[0]][returnPosition[1] + 1] == NULL)) {
        returnPosition[1]++;
        return returnPosition;
    }
    // Lewo
    else if ((returnPosition[0] != 0) && (currentWorld->entitySpace[returnPosition[0] - 1][returnPosition[1]] == NULL)) {
        returnPosition[0]--;
        return returnPosition;
    }
    returnPosition[0] = -1;
    returnPosition[1] = -1;
    return returnPosition;
};

int Organism::getStrength() {
    return strength;
};

int Organism::getInitiative() {
    return initiative;
};

void Organism::setX(int x) {
    position[0] = x;
};

int Organism::getX() {
    return position[0];
};

void Organism::setY(int y) {
    position[1] = y;
};

int Organism::getY() {
    return position[1];
};

int Organism::getAge() {
    return age;
};

bool Organism::getNewBorn() {
    return newBorn;
};

bool Organism::shield(Organism* entity) { // tarcza zolwia
    return false;
};

void Organism::collision(Organism* collidingEntity)
{
};

Organism::~Organism() {

};