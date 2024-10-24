#include "Antelope.h"

Antelope::Antelope(World* currentWorld, int strength, int initiative, int posX, int posY, int age, bool newBorn)
    : Animal(currentWorld, strength, initiative, posX, posY, age, newBorn) {
};

void Antelope::draw() {
    std::cout << char(65);
};

std::string Antelope::getName() {
    return ("Antelope");
};

int* Antelope::pathFindNewField() {
    int* returnPosition = new int[2];
    returnPosition[0] = position[0];
    returnPosition[1] = position[1];
    int field;
    bool invalidMove;
    do {
        field = rand() % 4;
        invalidMove = false;
        switch (field) {
        case 0: // W gore o 2
            if (returnPosition[1] <= 1) invalidMove = true;
            else returnPosition[1] -= 2;
            break;
        case 1: // W prawo o 2
            if (returnPosition[0] >= currentWorld->getN() - 2) invalidMove = true;
            else returnPosition[0] += 2;
            break;
        case 2: // W dol o 2
            if (returnPosition[1] >= currentWorld->getM() - 2) invalidMove = true;
            else returnPosition[1] += 2;
            break;
        case 3: // W lewo o 2
            if (returnPosition[0] <= 1)
                invalidMove = true;
            else returnPosition[0] -= 2;
            break;
        }
    } while (invalidMove);
    return returnPosition;
};

void Antelope::collision(Organism* collidingEntity) { // podjecie proby ucieczki - 50% szans
    int randomTick = rand() % 2;
    if ((randomTick > 0) && !(doSpeciesMatch(*collidingEntity)) && (collidingEntity->getStrength() > strength)) {
        std::cout << "Escape of " << this->getName() << " (" << position[0] << ";" << position[1] << ") from " << collidingEntity->getName() << " (" << collidingEntity->getX() << "," << collidingEntity->getY() << ").\n";
        int* newPosition = pathFindNewUnoccupiedField();
        if ((newPosition[0] != -1) && (newPosition[1] != -1)) {
            currentWorld->entitySpace[newPosition[0]][newPosition[1]] = this;
            currentWorld->entitySpace[position[0]][position[1]] = NULL;
            position[0] = newPosition[0];
            position[1] = newPosition[1];
        }
    }
    else Animal::collision(collidingEntity);
};

Organism* Antelope::clone(int posX, int posY) {
    return new Antelope(currentWorld, strength, initiative, posX, posY, 1, true);
};

Antelope::~Antelope() {
};