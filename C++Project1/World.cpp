#include "World.h"        
#include "Organism.h"     
#include "Animal.h"      
#include "Human.h"        
#include "Sheep.h"        
#include "Wolf.h"       
#include "Fox.h"          
#include "Turtle.h"       
#include "Antelope.h"     
#include "Plant.h"        
#include "Grass.h"        
#include "Dandelion.h"    
#include "Guarana.h"      
#include "DeadlyNigtshade.h"   // Wilcza jagoda
#include "SosnowskisHogweed.h"      // Barszcz Sosnowskiego
#include <typeinfo>

Entities::Entities(int maxSize) : maxSize(maxSize) {
    queue_tmp = new queue; // zalokowanie i zainicjowanie pamieci na lookup
    queue_tmp->entity = NULL;
    queue_tmp->next = NULL;
};

int Entities::getMaxSize() {
    return maxSize;
};

int Entities::getCurrSize() {
    return currentSize;
};

void Entities::addNew(Organism* entity) {
    if (currentSize >= maxSize) {
        printf("Entity lookup is full!\n");
        return;
    }
    queue* newEntity = new queue;
    newEntity->entity = entity;
    newEntity->next = NULL;
    queue* current = queue_tmp;
    while (current->next != NULL) {
        if (entity->getInitiative() > current->next->entity->getInitiative()) break; // sprawdzenie inicjatyw organizmow
        else if (entity->getInitiative() == current->next->entity->getInitiative()) { // sa rowne, wiec sprawdzamy wiek
            if (entity->getAge() >= current->next->entity->getAge()) break;
        }
        current = current->next;
    }
    newEntity->next = current->next; // dodanie do kolejki nowego
    current->next = newEntity;
    currentSize++;
};

void Entities::remove(Organism* entity) {
    queue* current = queue_tmp;
    for (int i = 0; i < currentSize; i++) {
        if (current->next->entity == entity) break;
        if (i == currentSize - 1) {
            printf("Entity is not present in the world!\n");
            return;
        }
        current = current->next;
    }
    current->next = current->next->next;
    delete entity;
    currentSize--;
};

Entities::~Entities() {
};

void World::placeRandom(Organism* entity) { // proba stworzenia organizmu o ile wolne miejsce
    int newPosition[2];
    newPosition[0] = rand() % getN();
    newPosition[1] = rand() % getM();
    if (entitySpace[newPosition[0]][newPosition[1]] == NULL) { // sukces
        entity->setX(newPosition[0]);
        entity->setY(newPosition[1]);
        entitySpace[newPosition[0]][newPosition[1]] = entity;
        entityLookup->addNew(entity);
    }
    else delete entity;
};

void World::placeSpecific(Organism* entity, int axisN, int axisM) { // nadanie miejsca w pamieci dla obiektu
    entity->setX(axisN);
    entity->setY(axisM);
    entitySpace[axisN][axisM] = entity;
    entityLookup->addNew(entity);
}

World::World(int N, int M) : N(N), M(M) {
    srand(time(NULL));
    entitySpace = new Organism * *[N];
    for (int i = 0; i < N; i++) entitySpace[i] = new Organism * [M];
    for (int i = 0; i < N; i++) { // puste miejsce dla organizmow
        for (int j = 0; j < M; j++) entitySpace[i][j] = NULL;
    }
    entityLookup = new Entities(N * M);
    placeRandom(new Human(this, 5, 4, -1, -1, 19));
    humanAbilityCD = 0;
    inGame = true;
    placeRandom(new Sheep(this, 4, 4, -1, -1, 16, false));
    placeRandom(new Sheep(this, 4, 4, -1, -1, 12, false));
    placeRandom(new Grass(this, -1, -1, 3, false));
    placeRandom(new Dandelion(this, -1, -1, 5, false));
    placeRandom(new Guarana(this, -1, -1, 3, false));
    placeRandom(new Guarana(this, -1, -1, 2, false));
    placeRandom(new Wolf(this, 9, 5, -1, -1, 8, false));
    placeRandom(new Wolf(this, 9, 5, -1, -1, 13, false));
    placeRandom(new DeadlyNigtshade(this, -1, -1, 3, false));
    placeRandom(new SosnowskisHogweed(this, -1, -1, 2, false));
    placeRandom(new Fox(this, 3, 7, -1, -1, 5, false));
    placeRandom(new Fox(this, 3, 7, -1, -1, 3, false));
    placeRandom(new Turtle(this, 2, 1, -1, -1, 18, false));
    placeRandom(new Turtle(this, 2, 1, -1, -1, 26, false));
    placeRandom(new Antelope(this, 4, 4, -1, -1, 8, false));
    placeRandom(new Antelope(this, 4, 4, -1, -1, 14, false));
};

World::World(int N, int M, std::ifstream& loadFile) : N(N), M(M) {
    srand(time(NULL));
    entitySpace = new Organism * *[N];
    for (int i = 0; i < N; i++) entitySpace[i] = new Organism * [M];
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++)  entitySpace[i][j] = NULL;
    }
    entityLookup = new Entities(N * M);
    inGame = true;
    std::string word;
    int oldStrength, oldInitiative, oldN, oldM, oldAge, oldBool;
    while (loadFile >> word) {
        loadFile >> oldStrength >> oldInitiative >> oldN >> oldM >> oldAge >> oldBool;
        if (!word.compare("Antelope")) placeSpecific(new Antelope(this, oldStrength, oldInitiative, -1, -1, oldAge, oldBool), oldN, oldM);
        else if (!word.compare("DeadlyNigtshade")) placeSpecific(new DeadlyNigtshade(this, -1, -1, oldAge, oldBool), oldN, oldM);
        else if (!word.compare("Dandelion")) placeSpecific(new Dandelion(this, -1, -1, oldAge, oldBool), oldN, oldM);
        else if (!word.compare("Fox")) placeSpecific(new Fox(this, oldStrength, oldInitiative, -1, -1, oldAge, oldBool), oldN, oldM);
        else if (!word.compare("Grass")) placeSpecific(new Grass(this, -1, -1, oldAge, oldBool), oldN, oldM);
        else if (!word.compare("Guarana")) placeSpecific(new Guarana(this, -1, -1, oldAge, oldBool), oldN, oldM);
        else if (!word.compare("SosnowskisHogweed")) placeSpecific(new SosnowskisHogweed(this, -1, -1, oldAge, oldBool), oldN, oldM);
        else if (!word.compare("Human")) placeSpecific(new Human(this, oldStrength, oldInitiative, -1, -1, oldAge), oldN, oldM);
        else if (!word.compare("Sheep")) placeSpecific(new Sheep(this, oldStrength, oldInitiative, -1, -1, oldAge, oldBool), oldN, oldM);
        else if (!word.compare("Turtle")) placeSpecific(new Turtle(this, oldStrength, oldInitiative, -1, -1, oldAge, oldBool), oldN, oldM);
        else if (!word.compare("Wolf")) placeSpecific(new Wolf(this, oldStrength, oldInitiative, -1, -1, oldAge, oldBool), oldN, oldM);
    }
};

int World::getN() {
    return N;
};

int World::getM() {
    return M;
};

int World::getRound() {
    return round;
}

int World::getHumanAbilityCD() {
    return humanAbilityCD;
}

void World::setHumanAbilityCD(int i) {
    humanAbilityCD = i;
}

bool World::getInGame() {
    return inGame;
}

void World::setInGame() {
    if (inGame) inGame = 0;
    else inGame = 1;
}

void World::setRound(int turn) {
    this->round = turn;
}

void World::drawWorld() {
    std::cout << "Round (" << round << "), Legend: \n";
    std::cout << "Main character: (. - Human)\n";
    std::cout << "Animals: (A - Antelope),(F - Fox),(S - Sheep),(T - Turtle),(W - Wolf)\n";
    std::cout << "Plants: (" << char(177) << " - Grass), (# - SosnowskisHogweed), (N - DeadlyNigtshade), (D - Dandelion), (G - Guarana)\n#";
    for (int i = 0; i < N; i++) {
        std::cout << "###";
    }
    std::cout << "#\n";
    for (int i = 0; i < M; i++) {
        std::cout << "#";
        for (int j = 0; j < N; j++) {
            if (entitySpace[j][i] != NULL) {
                std::cout << "(";
                entitySpace[j][i]->draw();
                std::cout << ")";
            }
            else std::wcout << "( )";
        }
        std::cout << "#\n";
    }
    std::cout << "#";
    for (int i = 0; i < N; i++) {

        std::cout << "###";
    }
    std::cout << "#\n";
};

void World::executeRound() {
    std::cout << "Game in progress (" << round << "): \n";
    queue* current = entityLookup->queue_tmp->next;
    if (humanAbilityCD > 0)  humanAbilityCD--;
    while (current != NULL) { // nowe organizmy sa bez ruchu
        if (current->entity->newBorn == true) current->entity->newBorn = false;
        else current->entity->action();
        current = current->next;
    }
    round++;
};

void World::saveGame() {
    std::ofstream fileToSave;
    fileToSave.open("save.txt", std::ios::out);
    if (!fileToSave) {
        std::cout << "Problems with opening the file. Couldnt save the world.\n";
        return;
    }
    else {
        fileToSave << round << " " << humanAbilityCD << std::endl;
        fileToSave << N << " " << M << std::endl;
        queue* current = entityLookup->queue_tmp->next;
        while (current != NULL) {
            fileToSave << current->entity->getName() << " " << current->entity->getStrength() << " " << current->entity->getInitiative() << " " << current->entity->getX() << " " << current->entity->getY() << " " << current->entity->getAge() << " " << current->entity->getNewBorn() << "\n";
            current = current->next;
        }
        fileToSave.close();
        std::cout << "Game successfully saved to the file!\n";
    }
};

World::~World() {
};