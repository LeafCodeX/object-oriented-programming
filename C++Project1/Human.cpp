#include <conio.h>
#include "Human.h"

#define ARROW_UP 72
#define ARROW_DOWN 80
#define ARROW_LEFT 75
#define ARROW_RIGHT 77
#define R 114

Human::Human(World* currentWorld, int strength, int initiative, int posX, int posY, int age)
    : Animal(currentWorld, strength, initiative, posX, posY, age, false) {
};

void Human::draw() {
    std::cout << char(219);
};

std::string Human::getName() {
    return ("Human");
};

void Human::action() {
    age++;
    int newPosition[2];
    newPosition[0] = position[0];
    newPosition[1] = position[1];
    std::cout << "Make a move: ";
    char playerMove = _getch();
    playerMove = _getch();
    if (playerMove == ARROW_LEFT || playerMove == 97) newPosition[0]--; // w lewo
    else if (playerMove == ARROW_RIGHT || playerMove == 100) newPosition[0]++; // w prawo
    else if (playerMove == ARROW_UP || playerMove == 119) newPosition[1]--; // w gore
    else if (playerMove == ARROW_DOWN || playerMove == 115) newPosition[1]++; // w dol
    else if (playerMove == R) specialAbility(); // r - specjalna umiejêtnoœæ (magiczny eliksir)
    std::cout << std::endl;
    if (currentWorld->getHumanAbilityCD() > 5) strength--;
    if ((newPosition[0] < 0 || newPosition[1] < 0) || (newPosition[0] >= currentWorld->getN()) || (newPosition[1] >= currentWorld->getM())) {
        std::cout << "You can't make such a move. You lost that move haha!\n\n";
        return;
    }
    if (currentWorld->entitySpace[newPosition[0]][newPosition[1]] == NULL) {
        currentWorld->entitySpace[newPosition[0]][newPosition[1]] = this;
        currentWorld->entitySpace[position[0]][position[1]] = NULL;
        position[0] = newPosition[0];
        position[1] = newPosition[1];
    }
    else collision(currentWorld->entitySpace[newPosition[0]][newPosition[1]]); // walka o polce z innym organizmem
};

void Human::specialAbility() {
    if (currentWorld->getHumanAbilityCD() == 0) {
        std::cout << "\nYou're using a magic potion of strength!\n";
        this->strength += 5;
        currentWorld->setHumanAbilityCD(10);
    }
    else if (currentWorld->getHumanAbilityCD() < 6) std::cout << "\nMagic potion is on cooldown.\n";
    else std::cout << "\nMagic potion is already in use. \n";
}

Organism* Human::clone(int posX, int posY) {
    return new Human(currentWorld, strength, initiative, posX, posY, 1);
};

Human::~Human() {
    currentWorld->setInGame();
};