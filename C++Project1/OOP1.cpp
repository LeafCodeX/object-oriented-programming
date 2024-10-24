#include <iostream>
#include <conio.h>
#include <windows.h>
#include "World.h"

#define SAVE_GAME 115

void edge() {
    for (int i = 0; i < 36; i++) {
        std::cout << "#";
    }
    std::cout << std::endl;
}

void displayAuthorInfo() {
    system("cls");
    edge();
    std::cout << "# Object-Oriented Programming 2023 #\n";
    std::cout << "#     Programming language: C++    #\n";
    std::cout << "#           Name: Marcin           #\n";
    std::cout << "#        Surname: Bajkowski        #\n";
    std::cout << "#          Index: 193696           #\n";
    edge();
    std::cout << "#             Options:             #\n";
    std::cout << "#        1 - Start new game        #\n";
    std::cout << "#          2 - Load game           #\n";
    std::cout << "#             3 - Exit             #\n";
    edge();
    std::cout << "           Select option: ";
}

void newGame() {
    system("cls");
    edge();
    std::cout << "#        STARTING NEW GAME!        #\n";
    edge();
    int n, m;
    std::cout << "Select world size: ";
    std::cin >> n >> m;
    World world(n, m);
    while (world.getInGame()) {
        system("cls");
        world.drawWorld();
        world.executeRound();
        edge();
        std::cout << "#    Do you want save the game?    #\n";
        edge();
        char trySave = _getch();
        if (trySave == SAVE_GAME) {
            world.saveGame();
        }
        system("pause");
    }
    system("cls");
    world.drawWorld();
    std::cout << "Good game, but maybe next time you will be able to achieve more!\n";
    exit(1);
}

void loadGame() {
    system("cls");
    edge();
    std::cout << "#          LOADING GAME!           #\n";
    edge();
    std::ifstream loadWorld;
    loadWorld.open("save.txt", std::ios::in);
    bool loadSaveGame = true;
    if (!loadWorld) {
        std::cout << "       There is no saved game!\n";
        std::cout << std::endl;
        system("pause");
        loadSaveGame = false;
    }
    if (loadSaveGame) {
        int savedRound, savedHumanCD, n, m;
        loadWorld >> savedRound >> savedHumanCD;
        loadWorld >> n >> m;
        World save(n, m, loadWorld);
        save.setRound(savedRound);
        save.setHumanAbilityCD(savedHumanCD);
        while (save.getInGame()) {
            system("cls");
            save.drawWorld();
            save.executeRound();
            edge();
            std::cout << "#    Do you want save the game?    #\n";
            edge();
            char trySave = _getch();
            if (trySave == SAVE_GAME) {
                save.saveGame();
                system("pause");
            }
        }
        system("cls");
        save.drawWorld();
        std::cout << "Good game, but maybe next time you will be able to achieve more!\n";
        exit(1);
    }
}

void selectOption() {
    int option;
    do {
        std::cin >> option;
        switch (option) {
        case 1:
            newGame();
            break;
        case 2:
            loadGame();
            break;
        case 3:
            system("cls");
            edge();
            std::cout << "#               Exit               #\n";
            edge();
            exit(1);
            break;
        default:
            system("cls");
            edge();
            std::cout << "#           Wrong option           #\n";
            edge();
            std::cout << std::endl;
            system("pause");
            displayAuthorInfo();
            break;
        }
    } while (option);
}

int main() {
    displayAuthorInfo();
    selectOption();
    return 0;
}