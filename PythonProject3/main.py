import pygame
import sys
from pygame.locals import *
from World.world import World

pygame.init()
pygame.font.init()

#szerokosc = int(input("Enter the width: "))
#wysokosc = int(input("Enter the height: "))
width = 20
height = 20
sizeSquare = 30
margins = 5
backgroundColor = (40, 40, 40)
buttonWidth = 47
defaultTitle = "Project 3 - Virtual world design in Python [s193696 Marcin Bajkowski]"

screen = pygame.display.set_mode(((sizeSquare + margins) * width + margins,
                                 (sizeSquare + margins) * height + margins + buttonWidth))
pygame.display.set_caption(defaultTitle)
screen.fill(backgroundColor)
pygame.display.update()

world = World(width, height, screen, sizeSquare, margins, defaultTitle, buttonWidth)

exit = False
while not exit:
    for event in pygame.event.get():
        if event.type == QUIT or (event.type == KEYDOWN and event.key == K_q):
            print("\033c")
            exit = True
            break
        world.PerformAnEvent(event)
    
    screen.fill(backgroundColor)
    world.RenderWorld(screen, sizeSquare, margins)
    pygame.display.update()
    pygame.time.delay(16)

pygame.display.quit()
pygame.quit()
sys.exit()
