# Tablette

![Touche moi si tu peux.](item:OpenComputers:item@68)

Les tablettes sont faites en mettant un [bo�tier de tablette](tabletCase1.md) dans un [assembleur](../block/assembler.md), en le configurant comme vous le voulez et en assemblant. Les tablettes se comportent comme des ordinateurs portables qui ne peuvent pas interagir directement avec le monde - par exemple, les [cartes de redstone](redstoneCard1.md) de base ne fonctionnent pas avec une tablette. Cependant, un certain nombre d'am�liorations fonctionne, comme l'[am�lioration de panneau d'E/S](signUpgrade.md) ou l'[am�lioration de piston](pistonUpgrade.md).

La tablette de niveau 2 permet �galement d'installer une am�lioration de conteneur. Il est possible d'acc�der � l'emplacement fourni par le conteneur en ouvrant l'interface alternative de la tablette, en faisant un clic droit accroupi avec la tablette en main. Cela forcera �galement la tablette � s'�teindre.

A la diff�rence des ordinateurs, les tablettes ne g�rent pas la persistence quand le joueur qui les tient quitte le jeu et y r�-acc�de. Elles ne g�rent pas non plus la persistence entre dimensions (un joueur qui va dans le Nether et qui en revient, pas exemple.

Les tablettes peuvent �tre mises dans un [chargeur](../block/charger.md) pour �tre recharg�es, et pour acc�der au premier [disque dur](hdd1.md) de la tablette depuis un [ordinateur](../general/computer.md) connect� au chargeur - dans cette situation, le chargeur se comportera comme un [lecteur de disquettes](../block/diskDrive.md), la tablette �tant consid�r�e comme la [disquette](floppy.md). Cela peut �tre tr�s utile si vous oubliez d'installer un syst�me d'exploitation sur le disque dur inclus dans la tablette, ou si vous avez bloqu� le syst�me d'exploitation d'une tablette.

Une autre fonctionnalit� avanc�e de la tablette est sa capacit� � g�n�rer des signaux avec des informations sur certains blocs du monde, en l'utilisant sur un bloc dans le monde pendant environ une seconde (c'est � dire qu'il faut laisser le bouton droit de la souris enfonc�e pendant une seconde). A bip court vous pr�viendra alors que le signal a �t� g�n�r�. Cela fonctionne uniquement si des am�liorations sont install�es sur la tablette qui envoie le signal avec les informations. Par exemple, le [g�oliseur](../block/geolyzer.md) ajoutera des informations sur le bloc en lui-m�me, comme sa duret�, l'[am�lioration de navigation](navigationUpgrade.md) ajoutera les coordonn�es du bloc relativement au joueur qui tient la tablette. Le bloc � analyser sera surlign� en vert en tenant la tablette.
