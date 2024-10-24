#include "SosnowskisHogweed.h"
#include "Animal.h"

SosnowskisHogweed::SosnowskisHogweed(World* currentWorld, int posX, int posY, int age, bool newBorn)
    : Plant(currentWorld, 10, posX, posY, age, newBorn) {
};

void SosnowskisHogweed::draw() {
    std::cout << "#";
};

std::string SosnowskisHogweed::getName() {
    return ("SosnowskisHogweed");
};

void SosnowskisHogweed::action() {
    age++;
    int currentPosition[2];
    currentPosition[0] = position[0];
    currentPosition[1] = position[1];
    // Dynamic_Cast pozwala sprawdzac, czy inne zwierzeta dziedzicza po klasie Animal ; sprawdzanie wszystkich pozycji i ewentualna eliminacja
    if ((currentPosition[1] != 0) && (currentWorld->entitySpace[currentPosition[0]][currentPosition[1] - 1] != NULL)) {
        if (Animal* animalOrganism = dynamic_cast<Animal*>(currentWorld->entitySpace[currentPosition[0]][currentPosition[1] - 1])) {
            std::cout << this->getName() << " from (" << position[0] << ";" << position[1] << ") kills " << currentWorld->entitySpace[currentPosition[0]][currentPosition[1] - 1]->getName() << " (" << currentWorld->entitySpace[currentPosition[0]][currentPosition[1] - 1]->getX() << ";" << currentWorld->entitySpace[currentPosition[0]][currentPosition[1] - 1]->getY() << ").\n";
            currentWorld->entityLookup->remove(currentWorld->entitySpace[currentPosition[0]][currentPosition[1] - 1]);
            currentWorld->entitySpace[currentPosition[0]][currentPosition[1] - 1] = NULL;
        }
    }
    if ((currentPosition[0] != currentWorld->getN() - 1) && (currentWorld->entitySpace[currentPosition[0] + 1][currentPosition[1]] != NULL)) {
        if (Animal* animalOrganism = dynamic_cast<Animal*>(currentWorld->entitySpace[currentPosition[0] + 1][currentPosition[1]])) {
            std::cout << this->getName() << " from (" << position[0] << ";" << position[1] << ") kills " << currentWorld->entitySpace[currentPosition[0] + 1][currentPosition[1]]->getName() << " (" << currentWorld->entitySpace[currentPosition[0] + 1][currentPosition[1]]->getX() << ";" << currentWorld->entitySpace[currentPosition[0] + 1][currentPosition[1]]->getY() << ").\n";
            currentWorld->entityLookup->remove(currentWorld->entitySpace[currentPosition[0] + 1][currentPosition[1]]);
            currentWorld->entitySpace[currentPosition[0] + 1][currentPosition[1]] = NULL;
        }
    }
    if ((currentPosition[1] != currentWorld->getM() - 1) && (currentWorld->entitySpace[currentPosition[0]][currentPosition[1] + 1] != NULL)) {
        if (Animal* animalOrganism = dynamic_cast<Animal*>(currentWorld->entitySpace[currentPosition[0]][currentPosition[1] + 1])) {
            std::cout << this->getName() << " from (" << position[0] << ";" << position[1] << ") kills " << currentWorld->entitySpace[currentPosition[0]][currentPosition[1] + 1]->getName() << " (" << currentWorld->entitySpace[currentPosition[0]][currentPosition[1] + 1]->getX() << ";" << currentWorld->entitySpace[currentPosition[0]][currentPosition[1] + 1]->getY() << ").\n";

            currentWorld->entityLookup->remove(currentWorld->entitySpace[currentPosition[0]][currentPosition[1] + 1]);
            currentWorld->entitySpace[currentPosition[0]][currentPosition[1] + 1] = NULL;
        }
    }
    if ((currentPosition[0] != 0) && (currentWorld->entitySpace[currentPosition[0] - 1][currentPosition[1]] != NULL)) {
        if (Animal* animalOrganism = dynamic_cast<Animal*>(currentWorld->entitySpace[currentPosition[0] - 1][currentPosition[1]])) {
            std::cout << this->getName() << " from (" << position[0] << ";" << position[1] << ") kills " << currentWorld->entitySpace[currentPosition[0] - 1][currentPosition[1]]->getName() << " (" << currentWorld->entitySpace[currentPosition[0] - 1][currentPosition[1]]->getX() << ";" << currentWorld->entitySpace[currentPosition[0] - 1][currentPosition[1]]->getY() << ").\n";
            currentWorld->entityLookup->remove(currentWorld->entitySpace[currentPosition[0] - 1][currentPosition[1]]);
            currentWorld->entitySpace[currentPosition[0] - 1][currentPosition[1]] = NULL;
        }
    }
    int randomTick = rand() % 100 + 1; // rozsiewanie SosnowskisHogweed
    if (randomTick > 80) {
        std::cout << "Spreding " << this->getName() << " (" << position[0] << ";" << position[1] << ").\n";
        int* newPosition = pathFindNewUnoccupiedField(); // szukanie nowego niezajetego miejsca
        if (newPosition[0] == -1 && newPosition[1] == -1) {
            std::cout << "Spreding failed - no free space available!\n";
            delete newPosition;
            return;
        }
        else clone(newPosition[0], newPosition[1]); // alternatywne miejsce
    }
};

Organism* SosnowskisHogweed::clone(int posX, int posY) {
    return new SosnowskisHogweed(currentWorld, posX, posY, 1, true);
};

SosnowskisHogweed::~SosnowskisHogweed() {
};