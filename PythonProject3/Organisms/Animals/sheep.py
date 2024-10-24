from .animal import Animal
import pygame


class Sheep(Animal):
    __nazwa = "Sheep"
    __sila = 4
    __inicjatywa = 4
    __tekstura = None
    __sciezka_tekstury = "Images/Sheep.png"

    def __init__(self, swiat, pozycja):
        if self.__tekstura is None and self.__sciezka_tekstury:
            self.__tekstura = pygame.image.load(self.__sciezka_tekstury)
        super().__init__(swiat, pozycja, self.__nazwa, self.__tekstura, self.__sila, self.__inicjatywa)

    def WygenerujKopie(self):
        return Sheep(self._swiat, self.DajPozycje())

    def _CzyTenSamGatunek(self, partner):
        return isinstance(partner, Sheep)
