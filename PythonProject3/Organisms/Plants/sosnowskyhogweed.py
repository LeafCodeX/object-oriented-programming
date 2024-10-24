from .poisonousplant import PoisonousPlant
from ..Animals.animal import Animal
import Organisms.Animals.cybersheep as co
from Navigation.square import Square
import pygame


class SosnowskyHogweed(PoisonousPlant):
    __sila = 10
    __nazwa = "SosnowskyHogweed"
    __tekstura = None
    __sciezka_tekstury = "Images/SosnowskyHogweed.png"

    def __init__(self, swiat, pozycja):
        if self.__tekstura is None and self.__sciezka_tekstury:
            self.__tekstura = pygame.image.load(self.__sciezka_tekstury)
        super().__init__(swiat, pozycja, self.__nazwa, self.__tekstura, self.__sila)
        
    def WygenerujKopie(self):
        return SosnowskyHogweed(self._swiat, self.DajPozycje())

    def Akcja(self):
        if not self.Zywy():
            return
        kierunki = self.DajPozycje().DajWektoryJednostkowe()
        for kierunek in kierunki:
            temp_pole = Square(self.DajPozycje().DajX() + kierunek.DajX(), self.DajPozycje().DajY() + kierunek.DajY(),
                             None)
            self._swiat.ZapetlijPunkt(temp_pole)
            organizm = self._swiat.DajMape().DajOrganizm(temp_pole.DajX(), temp_pole.DajY())
            if isinstance(organizm, Animal):
                organizm._Umrzyj(self)

    def _Kolizja(self, organizm):
        if isinstance(organizm, co.CyberSheep):
            self._Umrzyj(organizm)
            return True
        else:
            return super()._Kolizja(organizm)
