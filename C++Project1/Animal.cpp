#include "Animal.h"
#include "SosnowskisHogweed.h"
#include "Guarana.h"
#include "Human.h"
#include <typeinfo>

Animal::Animal(World* currentWorld, int strength, int initiative, int posX, int posY, int age, bool newBorn)
    : Organism(currentWorld, strength, initiative, posX, posY, age, newBorn) {
};

// Sprawdzenie czy organizmy do siebie pasuj¹
bool Animal::doSpeciesMatch(Organism& entity) {
    return (this->getName() == entity.getName());
};

void Animal::breed() {
    int* newPosition = pathFindNewUnoccupiedField();
    if (newPosition[0] == -1 && newPosition[1] == -1) {
        std::cout << "Breeding of " << this->getName() << " failed - no free space available!\n";
        return;
    }
    // Je¿eli znalaz³o siê miejsce
    clone(newPosition[0], newPosition[1]);
    delete newPosition;
};

void Animal::action() {
    if (this->getName().compare("Turtle"))  age++; // Zwiêkszenie wieku zwierzat, za wyj¹tkiem ¿ó³wia, który ma 25% szans ¿e wykona akcje

    int* newPosition = pathFindNewField();
    if (currentWorld->entitySpace[newPosition[0]][newPosition[1]] == NULL) {
        currentWorld->entitySpace[newPosition[0]][newPosition[1]] = this;
        currentWorld->entitySpace[position[0]][position[1]] = NULL;
        position[0] = newPosition[0];
        position[1] = newPosition[1];
    }
    else collision(currentWorld->entitySpace[newPosition[0]][newPosition[1]]); // Kolizja z czyms innym
    delete newPosition;
};

void Animal::collision(Organism* collidingEntity) {
    if (doSpeciesMatch(*collidingEntity)) {
        if (typeid(*collidingEntity).hash_code() == typeid(Human).hash_code()) return;
        std::cout << "Breeding " << this->getName() << " (" << position[0] << ";" << position[1] << ") and " << collidingEntity->getName() << " (" << collidingEntity->getX() << ";" << collidingEntity->getY() << ").\n";
        breed();
        return;
    }
    else {
        if (collidingEntity->shield(this)) { // obrona zolwia
            std::cout << "The " << this->getName() << " from (" << position[0] << ";" << position[1] << ") repelled the attack of the " << collidingEntity->getName() << " (" << collidingEntity->getX() << ";" << collidingEntity->getY() << ").\n";
            return;
        } //walka
        std::cout << "Fight between: " << this->getName() << " (" << position[0] << ";" << position[1] << ") and " << collidingEntity->getName() << " (" << collidingEntity->getX() << ";" << collidingEntity->getY() << ").";
        if (strength >= collidingEntity->getStrength() && typeid(*collidingEntity).hash_code() != typeid(SosnowskisHogweed).hash_code()) { // rozne sily i nie SosnowskisHogweed
            std::cout << " Victory of " << this->getName() << "\n";
            if (typeid(*collidingEntity).hash_code() == typeid(Guarana).hash_code()) {
                std::cout << this->getName() << " (" << position[0] << "," << position[1] << ") strength increased by 3 units.\n";
                strength += 3;
            }
            // Usuniêcie organizmu z planszy
            currentWorld->entitySpace[position[0]][position[1]] = NULL;
            position[0] = collidingEntity->getX();
            position[1] = collidingEntity->getY();
            currentWorld->entitySpace[position[0]][position[1]] = this;
            currentWorld->entityLookup->remove(collidingEntity);
        }
        else { //jesli DeadlyNigtshade
            std::cout << "Defeat of " << this->getName() << ".\n";
            currentWorld->entitySpace[position[0]][position[1]] = NULL;
            currentWorld->entityLookup->remove(this);
        }
    }
};

Animal::~Animal() {
};