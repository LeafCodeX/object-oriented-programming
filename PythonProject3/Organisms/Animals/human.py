import random
from .animal import Animal
import pygame
import copy
from Navigation.directions import Directions


class Human(Animal):
    __nazwa = "Human"
    __sila = 5
    __inicjatywa = 4
    __tekstura = None
    __sciezka_tekstury = "Images/Human.png"

    def __init__(self, swiat, pozycja):
        if self.__tekstura is None and self.__sciezka_tekstury:
            self.__tekstura = pygame.image.load(self.__sciezka_tekstury)
        super().__init__(swiat, pozycja, self.__nazwa, self.__tekstura, self.__sila, self.__inicjatywa)
        self._cooldown = 0
        self._magic_elixir_duration = 0
        self._magic_elixir_active = False
        self._kierunek_ruchu = Directions.POLNOC

    def WygenerujKopie(self):
        return None

    def UstawKierunek(self, kierunek):
        self._kierunek_ruchu = kierunek

    def Akcja(self):
        self.__ObsluzUmiejetnoscSpecjalna()
        print("TEMP: HumanCD = ", self._cooldown, "| DurationSA = ", self._magic_elixir_duration, "| Strength = ", self._sila, "!")
        if self.Zywy():
            x_cel, y_cel = self._DajPoleDocelowe()
            super()._WykonajRuch(self._swiat.DajMape().DajPole(x_cel, y_cel))

    def _DajPoleDocelowe(self):
        poledocelowe = copy.copy(self.DajPozycje())
        poledocelowe.OffsetPunkt(self._kierunek_ruchu)
        self._swiat.ZapetlijPunkt(poledocelowe)
        return poledocelowe.DajX(), poledocelowe.DajY()

    def AktywujUmiejetnoscSpecjalna(self):
        if self._cooldown == 0 and self._magic_elixir_active == False:
            self._cooldown = 10
            self._magic_elixir_duration = 5
            self._sila += 5
            self._magic_elixir_active = True
            print("The magic Elixir has been activated! Remaining: ",
                self._magic_elixir_duration, " round/s!")

    def DoZapisu(self):
        return super().DoZapisu() + " " + str(self._magic_elixir_duration) + " " + str(self._cooldown) + " " + str(self._magic_elixir_active)

    def _CzyTenSamGatunek(self, partner):
        return False

    def __ObsluzUmiejetnoscSpecjalna(self):
        if self._cooldown > 0:
            self._cooldown -= 1
            if self._magic_elixir_duration > 0:
                self._sila -= 1
                self._magic_elixir_duration -= 1
                print("The Magic Elixir is active! Remaining: " + str(self._magic_elixir_duration) + " round/s!")
            elif self._magic_elixir_duration == 0 and self._magic_elixir_active == True:
                self._magic_elixir_active = False
                print("The Magic Elixir has expired! Remaining cooldown: " + str(self._cooldown) + " round/s!")
            else:
                print("The Magic Elixir can be re-activated in " + str(self._cooldown) + " round/s!")  

    def setCooldown(self, cooldown):
        self._cooldown = cooldown

    def getCooldown(self):
        return self._cooldown
    
    def setMagicElixirDuration(self, magic_elixir_duration):
        self._magic_elixir_duration = magic_elixir_duration

    def getMagicElixirDuration(self):
        return self._magic_elixir_duration
    
    def setMagicElixirActive(self, magic_elixir_active):
        self._magic_elixir_active = magic_elixir_active

    def getMagicElixirActive(self):
        return self._magic_elixir_active
