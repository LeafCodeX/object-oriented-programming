#pragma once
#include <iostream>
#include <fstream>
#include <time.h>
#include <typeinfo>

class Organism; // kazda runda odbywa sie w kolejce, gdzie przechdzimy przez organizmy
struct queue {
    Organism* entity;
    queue* next;
};

class Entities {
private:
    int maxSize;
    int currentSize = 0;
public:
    Entities(int maxSize);
    queue* queue_tmp;
    int getMaxSize();
    int getCurrSize();
    void addNew(Organism* entity);
    void remove(Organism* entity);
    ~Entities();
};

class World {
private:
    int N, M;
    int round = 0;
    int humanAbilityCD;
    bool inGame;
public:
    World(int N, int M);
    World(int N, int M, std::ifstream& loadFile);
    int getN();
    int getM();
    int getHumanAbilityCD();
    int getRound();
    bool getInGame();
    void setInGame();
    void setHumanAbilityCD(int i);
    void setRound(int turn);
    void drawWorld();
    void executeRound();
    void placeRandom(Organism* entity);
    void placeSpecific(Organism* entity, int axisN, int axisM);
    void saveGame();
    Organism*** entitySpace;                // wskaünik na array dwu wymiarowy ze wskaünikami
    Entities* entityLookup;
    ~World();
};