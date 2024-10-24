from .animal import Animal
from Navigation.cybersheepsearching import *
import pygame


class CyberSheep(Animal):
    __nazwa = "CyberSheep"
    __sila = 11
    __inicjatywa = 4
    __tekstura = None
    __sciezka_tekstury = "Images/CyberSheep.png"

    def __init__(self, swiat, pozycja):
        if self.__tekstura is None and self.__sciezka_tekstury:
            self.__tekstura = pygame.image.load(self.__sciezka_tekstury)
        super().__init__(swiat, pozycja, self.__nazwa, self.__tekstura, self.__sila, self.__inicjatywa)

    def WygenerujKopie(self):
        return CyberSheep(self._swiat, self.DajPozycje())

    def _DajPoleDocelowe(self):
        poledocelowe = ZnajdzSciezke(self._swiat.DajMape(), self.DajPozycje())
        if poledocelowe is None:
            return super()._DajPoleDocelowe()
        else:
            return poledocelowe

    def _CzyTenSamGatunek(self, partner):
        return isinstance(partner, CyberSheep)

    def _Umrzyj(self, organizm):
        from ..Plants.sosnowskyhogweed import SosnowskyHogweed
        if isinstance(organizm, SosnowskyHogweed):
            return False
        else:
            return super()._Umrzyj(organizm)
