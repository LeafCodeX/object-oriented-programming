from .plant import Plant
import pygame


class Dandelion(Plant):
    __sila = 0
    __liczba_prob_rozmnozenia_sie = 3
    __nazwa = "Dandelion"
    __tekstura = None
    __sciezka_tekstury = "Images/Dandelion.png"

    def __init__(self, swiat, pozycja):
        if self.__tekstura is None and self.__sciezka_tekstury:
            self.__tekstura = pygame.image.load(self.__sciezka_tekstury)
        super().__init__(swiat, pozycja, self.__nazwa, self.__tekstura, self.__sila)

    def Akcja(self):
        for i in range(self.__liczba_prob_rozmnozenia_sie):
            super().Akcja()

    def WygenerujKopie(self):
        return Dandelion(self._swiat, self.DajPozycje())
