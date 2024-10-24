from .poisonousplant import PoisonousPlant
import pygame


class DeadlyNightshade(PoisonousPlant):
    __sila = 99
    __nazwa = "DeadlyNightshade"
    __tekstura = None
    __sciezka_tekstury = "Images/DeadlyNightshade.png"

    def __init__(self, swiat, pozycja):
        if self.__tekstura is None and self.__sciezka_tekstury:
            self.__tekstura = pygame.image.load(self.__sciezka_tekstury)
        super().__init__(swiat, pozycja, self.__nazwa, self.__tekstura, self.__sila)

    def WygenerujKopie(self):
        return DeadlyNightshade(self._swiat, self.DajPozycje())
