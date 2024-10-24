from Navigation.map import Map
from pygame.locals import *
import random
from Organisms.Plants import *
from Organisms.Plants.deadlynightshade import DeadlyNightshade
from Organisms.Plants.sosnowskyhogweed import SosnowskyHogweed
from Organisms.Plants.dandelion import Dandelion
from Organisms.Plants.guarana import Guarana
from Organisms.Plants.grass import Grass
from Navigation.directions import Directions
from Organisms.Animals import *
from Extensions.addtext import *
from Extensions.spawning import SpawnMenu

pygame.init()


class World:
    __kolor_pustego_pola = (103, 103, 103)
    __mala_czcionka = pygame.font.SysFont('San Francisco', 30)
    __bialy = (255, 255, 255)
    __przyciski = list()
    __przyciski.append(__mala_czcionka.render(" Next turn [Ent]", True, __bialy))
    __przyciski.append(__mala_czcionka.render("New Game [N]", True, __bialy))
    __przyciski.append(__mala_czcionka.render("Load Game [L]", True, __bialy))
    __przyciski.append(__mala_czcionka.render("Save Game [S]", True, __bialy))
    __liczba_przyciskow = 4
    __stala_rozmieszczenia_przyciskow = 180

    def __init__(self, x, y, ekran, rozmiar_komorki, margines, tytul, rozmiar_przycisku):
        self._mapa = Map(x, y)
        self._kolejka_organizmow = list()
        self._nowe_organizmy = list()
        self._tura = 1
        self._czlowiek = None
        self._wymiary_komorki = rozmiar_komorki
        self._margines_komorki = margines
        self._ekran = ekran
        self._domyslny_tytul = tytul
        self._obecny_tytul = tytul
        self._wielkosc_przycisku = rozmiar_przycisku
        self.__init()
        self.__menu_spawnowania = SpawnMenu(self, rozmiar_komorki, margines, ekran)

    def DajMape(self):
        return self._mapa

    def WykonajTure(self):
        print("\033c")
        print("!Round " + str(self._tura) + " began!")
        for organizm in self._kolejka_organizmow:
            organizm.Akcja()
        self.__InicjujNoweOrganizmy()
        self.__UsunMartweOrganizmy()
        self.__SortujOrganizmy()
        print("!Round " + str(self._tura) + " ended!")
        self._tura += 1

    def RenderWorld(self, ekran, wymiary_komorki, margines):
        self._wymiary_komorki = wymiary_komorki
        self._margines_komorki = margines
        self.__RysujSiatke(ekran, wymiary_komorki, margines)
        for organizm in self._kolejka_organizmow:
            organizm.Renderuj(ekran, wymiary_komorki, margines)
        self.__RenderujPrzyciski(ekran, wymiary_komorki, margines)

    def DodajOrganizm(self, organizm):
        self._nowe_organizmy.append(organizm)

    def DodajOrganizmNatychmiast(self, organizm):
        self._nowe_organizmy.append(organizm)
        self.__InicjujNoweOrganizmy()
        self.RenderWorld(self._ekran, self._wymiary_komorki, self._margines_komorki)
        return

    def DajSzerokosc(self):
        return self._mapa.DajSzerokosc()

    def DajWysokosc(self):
        return self._mapa.DajWysokosc()

    def ZapetlijPunkt(self, pole):
        if pole.DajX() < 0:
            pole.OffsetX(self.DajSzerokosc())
        if pole.DajX() >= self.DajSzerokosc():
            pole.OffsetX(-self.DajSzerokosc())
        if pole.DajY() < 0:
            pole.OffsetY(self.DajWysokosc())
        if pole.DajY() >= self.DajWysokosc():
            pole.OffsetY(-self.DajWysokosc())

    def PerformAnEvent(self, event):
        if event.type == KEYDOWN:
            if event.key == K_RETURN:
                self.WykonajTure()
            elif event.key == K_UP:
                self._czlowiek.UstawKierunek(Directions.POLNOC)
                self.WykonajTure()
            elif event.key == K_RIGHT:
                self._czlowiek.UstawKierunek(Directions.WSCHOD)
                self.WykonajTure()
            elif event.key == K_DOWN:
                self._czlowiek.UstawKierunek(Directions.POLUDNIE)
                self.WykonajTure()
            elif event.key == K_LEFT:
                self._czlowiek.UstawKierunek(Directions.ZACHOD)
                self.WykonajTure()
            elif event.key == K_SPACE:
                self._czlowiek.AktywujUmiejetnoscSpecjalna()
            elif event.key == K_n:
                self.__NowySwiat(int(DajTekst(self._ekran, "Project 3 - Virtual world design in Python [s193696 Marcin Bajkowski]")),
                                 int(DajTekst(self._ekran, "Project 3 - Virtual world design in Python [s193696 Marcin Bajkowski]")))
            elif event.key == K_l:
                self.__WczytajSwiat()
            elif event.key == K_s:
                self.__ZapiszSwiat()
        elif event.type == pygame.MOUSEBUTTONDOWN:
            mysz = pygame.mouse.get_pos()
            if event.button == 3:
                pole = self.__PixelNaPole(mysz)
                self.__menu_spawnowania.WykonajWydarzenia(mysz, pole)
                return
            komendy = {0: lambda: self.WykonajTure(), \
                       1: lambda: self.__NowySwiat(int(DajTekst(self._ekran, "Input width of the world")),
                                                   int(DajTekst(self._ekran, "Input height of the world"))), \
                       2: lambda: self.__WczytajSwiat(), \
                       3: lambda: self.__ZapiszSwiat()}
            stala = (self._wymiary_komorki + self._margines_komorki) * self.DajSzerokosc()
            y1 = (self._wymiary_komorki + self._margines_komorki) * self.DajWysokosc() + self._margines_komorki
            y2 = (self._wymiary_komorki + self._margines_komorki) * self.DajWysokosc() + self._margines_komorki + self._wielkosc_przycisku - 20
            for i in range(self.__liczba_przyciskow):
                current_x1 = i * (stala / self.__stala_rozmieszczenia_przyciskow * self._wielkosc_przycisku)
                current_x2 = (i + 1) * (stala / self.__stala_rozmieszczenia_przyciskow * self._wielkosc_przycisku)
                if mysz[0] <= current_x2 and mysz[0] >= current_x1 and mysz[1] <= y2 and mysz[1] >= y1:
                    komendy[i]()
                    self.__AktualizujTytul()
                    return

    def __AktualizujTytul(self):
        pygame.display.set_caption(self._obecny_tytul)

    def __PixelNaPole(self, mysz):
        x = mysz[0] // (self._margines_komorki + self._wymiary_komorki)
        y = mysz[1] // (self._margines_komorki + self._wymiary_komorki)
        return self._mapa.DajPole(x, y)

    def __InicjujNoweOrganizmy(self):
        for organizm in self._nowe_organizmy:
            self._kolejka_organizmow.append(organizm)
        self._nowe_organizmy.clear()
        self.__SortujOrganizmy()

    def __NowySwiat(self, szerokosc, wysokosc):
        if szerokosc <= 0 or wysokosc <= 0:
            return False
        self.__Wyczysc(szerokosc, wysokosc)
        self.__init()
        return True

    def __Wyczysc(self, szerokosc, wysokosc, tura=1):
        self._mapa.UstawRozmiar(szerokosc, wysokosc)
        self._tura = tura
        self._kolejka_organizmow.clear()
        self._nowe_organizmy.clear()

    def __ZapiszSwiat(self):
        plik = open("save.txt", "w")
        plik.write("& " + str(self.DajSzerokosc()) + " " + str(self.DajWysokosc()) + " " + str(self._tura) + "\n")
        for organizm in self._kolejka_organizmow:
            plik.write(organizm.DoZapisu() + "\n")
        plik.close()

    def __WczytajSwiat(self):
        try:
            plik = open("save.txt", "r")
        except FileNotFoundError:
            print("Save file not found or corrupted!")
            return
        with plik:
            for linia in plik:
                zawartosc = linia.split()
                if zawartosc[0] == "&":
                    szerokosc = int(zawartosc[1])
                    wysokosc = int(zawartosc[2])
                    tura = int(zawartosc[3])
                    self.__Wyczysc(szerokosc, wysokosc, tura)
                elif zawartosc[0] in ["Antelope", "Fox", "Human", "Sheep", "CyberSheep", "Turtle", "Wolf",
                                      "DeadlyNightshade", "SosnowskyHogweed", "Grass", "Guarana", "Dandelion"]:
                    x = int(zawartosc[1])
                    y = int(zawartosc[2])
                    sila = int(zawartosc[3])
                    pole = self._mapa.DajPole(x, y)
                    organizm = None
                    if zawartosc[0] == "Human":
                        organizm = Human(self, pole)
                        magic_elixir_duration = int(zawartosc[4])
                        organizm.setMagicElixirDuration(magic_elixir_duration)
                        cooldown = int(zawartosc[5])
                        organizm.setCooldown(cooldown)
                        magic_elixir_active = bool(zawartosc[6])
                        organizm.setMagicElixirActive(magic_elixir_active)
                        self._czlowiek = organizm
                    elif zawartosc[0] == "Antelope":
                        organizm = Antelope(self, pole)
                    elif zawartosc[0] == "Fox":
                        organizm = Fox(self, pole)
                    elif zawartosc[0] == "Sheep":
                        organizm = Sheep(self, pole)
                    elif zawartosc[0] == "Turtle":
                        organizm = Turtle(self, pole)
                    elif zawartosc[0] == "Wolf":
                        organizm = Wolf(self, pole)
                    elif zawartosc[0] == "CyberSheep":
                        organizm = CyberSheep(self, pole)
                    elif zawartosc[0] == "DeadlyNightshade":
                        organizm = DeadlyNightshade(self, pole)
                    elif zawartosc[0] == "SosnowskyHogweed":
                        organizm = SosnowskyHogweed(self, pole)
                    elif zawartosc[0] == "Grass":
                        organizm = Grass(self, pole)
                    elif zawartosc[0] == "Guarana":
                        organizm = Guarana(self, pole)
                    elif zawartosc[0] == "Dandelion":
                        organizm = Dandelion(self, pole)
                    pole.UstawOrganizm(organizm)
                    organizm.OffsetSile(sila - organizm.DajSile())
                    self._kolejka_organizmow.append(organizm)
        self.__ZmienWielkoscWyswietlacza()

    def __UsunMartweOrganizmy(self):
        for i in range(len(self._kolejka_organizmow) - 1, -1, -1):
            if not self._kolejka_organizmow[i].Zywy():
                del self._kolejka_organizmow[i]

    def __SortujOrganizmy(self):
        self._kolejka_organizmow.sort(key=lambda organizm: organizm.DajInicjatywe(), reverse=True)

    def __RenderujPrzyciski(self, ekran, wymiary_komorki, margines):
        stala = (wymiary_komorki + margines) * self.DajSzerokosc()
        for i in range(self.__liczba_przyciskow):
            ekran.blit(self.__przyciski[i], (
                i * (stala / self.__stala_rozmieszczenia_przyciskow * self._wielkosc_przycisku),
                (wymiary_komorki + margines) * self.DajWysokosc() + margines))

    def __RysujSiatke(self, ekran, wymiary_komorki, margines):
        prostokat = Rect(-wymiary_komorki, -wymiary_komorki, wymiary_komorki, wymiary_komorki)
        for y in range(self.DajWysokosc()):
            prostokat.y += wymiary_komorki + margines
            prostokat.x = -wymiary_komorki
            for x in range(self.DajSzerokosc()):
                prostokat.x += wymiary_komorki + margines
                pygame.draw.rect(ekran, self.__kolor_pustego_pola, prostokat)

    def __init(self):
        self.__ZmienWielkoscWyswietlacza()
        for x in range(self.DajSzerokosc()):
            for y in range(self.DajWysokosc()):
                i = random.randint(0, 60)
                if i <= 10:
                    pole = self._mapa.DajPole(x, y)
                    organizm = None
                    if i == 0:
                        organizm = Fox(self, pole)
                    elif i == 1:
                        organizm = Grass(self, pole)
                    elif i == 2:
                        organizm = Dandelion(self, pole)
                    elif i == 3:
                        organizm = Guarana(self, pole)
                    elif i == 4:
                        organizm = DeadlyNightshade(self, pole)
                    elif i == 5:
                        organizm = Wolf(self, pole)
                    elif i == 6:
                        organizm = Turtle(self, pole)
                    elif i == 7:
                        organizm = Sheep(self, pole)
                    elif i == 8:
                        organizm = SosnowskyHogweed(self, pole)
                    elif i == 9:
                        organizm = Antelope(self, pole)
                    elif i == 10:
                        organizm = CyberSheep(self, pole)
                    pole.UstawOrganizm(organizm)
                    self._kolejka_organizmow.append(organizm)
        while True:
            x = random.randint(0, self._mapa.DajSzerokosc() - 1)
            y = random.randint(0, self._mapa.DajWysokosc() - 1)
            pole = self._mapa.DajPole(x, y)
            if pole.Pusty():
                self._czlowiek = Human(self, pole)
                pole.UstawOrganizm(self._czlowiek)
                self._kolejka_organizmow.append(self._czlowiek)
                break
        self.__SortujOrganizmy()

    def __ZmienWielkoscWyswietlacza(self):
        komorka = self._wymiary_komorki + self._margines_komorki
        pygame.display.set_mode((komorka * self.DajSzerokosc() + self._margines_komorki,
                                 komorka * self.DajWysokosc() + self._margines_komorki + self._wielkosc_przycisku - 20))
