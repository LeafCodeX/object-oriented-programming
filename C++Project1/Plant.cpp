#include "Plant.h"

Plant::Plant(World* currentWorld, int strength, int posX, int posY, int age, bool newBorn)
    : Organism(currentWorld, strength, 0, posX, posY, age, newBorn) {
};

void Plant::action() {
    if (this->getName().compare("Dandelion")) age++; // Dandelion - 3 razy siê rozsiewa, wykluczamy go
    int randomTick = rand() % 100 + 1;
    if (randomTick > 80) {
        std::cout << "Spreding " << this->getName() << " (" << position[0] << ";" << position[1] << ").\n";
        int* newPosition = pathFindNewUnoccupiedField();
        if (newPosition[0] == -1 && newPosition[1] == -1) {
            std::cout << "Spreding failed - no free space available!\n";
            delete newPosition;
            return;
        }
        else clone(newPosition[0], newPosition[1]);
    }
};

Plant::~Plant() {
};