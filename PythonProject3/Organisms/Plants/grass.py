from .plant import Plant
import pygame


class Grass(Plant):
    __sila = 0
    __nazwa = "Grass"
    __tekstura = None
    __sciezka_tekstury = "Images/Grass.png"

    def __init__(self, swiat, pozycja):
        if self.__tekstura is None and self.__sciezka_tekstury:
            self.__tekstura = pygame.image.load(self.__sciezka_tekstury)
        super().__init__(swiat, pozycja, self.__nazwa, self.__tekstura, self.__sila)

    def WygenerujKopie(self):
        return Grass(self._swiat, self.DajPozycje())
