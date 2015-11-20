# Point de passage

!["Par l� !" - "Non, par l� !"](oredict:oc:waypoint)

Le point de passage n'a aucune utilit� en soi, mais dans la fa�on dont il peut �tre utilis�. Les [am�liorations de navigation](../item/navigationUpgrade.md) peuvent d�tecter les points de passage, ainsi les appareils �quip�s d'une am�lioration de navigation peuvent utiliser ces points de passage pour parcourir le monde. C'est particuli�rement utile pour �crire des programmes facilement r�-utilisables par des appreils comme les [robots](robot.md) et les [drones](../item/drone.md).

Remarquez que la position r�elle renvoy�e lors de la requ�te par l'am�lioration de navigation est *le bloc en face du point de passage* (indiqu� par les effets de particule). De cette mani�re vous pouvez le placer au dessus d'un coffre, et vous r�f�rer � la position du point de passage comme �tant "au dessus du coffre", sans avoir besoin de prendre en compte dans votre programme la rotation du point de passage.

Un point de passage a deux propri�t�s qui peuvent �tre utilis�es en l'interrogeant avec une am�lioration de navigation : le puissance actuelle du signal de redstone qu'il re�oit, et un libell� �ditable. Le libell� est une cha�ne de caract�res de 32 caract�res qui peut �tre �dit�e soit par l'interface soit par le composant expos� par le bloc du point de passage. Ces deux propri�t�s peuvent ensuite �tre utilis�e sur l'appareil pour d�terminer que faire avec le point de passage. Par exemple, un programme de tri pourrait �tre configur� pour traiter tous les blocs avec un signal de redstone �lev� en tant qu'entr�e, et ceux avec un signal faible comme une sortie.
