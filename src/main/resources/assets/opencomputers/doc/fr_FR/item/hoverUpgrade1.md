# Am�lioration de vol plan�

![Flotter comme une plume.](oredict:oc:hoverUpgrade1)

L'am�lioration de vol plan� permet aux [robots](../block/robot.md) de voler beaucoup plus au dessus du sol que ce qu'ils pourraient le faire normalement. Contrairement aux [drones](drone.md), ils sont limit�s par d�faut � une hauteur de vol de 8 blocs. Ca n'est g�n�ralement pas un probl�me, parce qu'ils peuvent tout de m�me monter aux murs. Leurs r�gles de d�placement peuvent �tre r�sum�es ainsi :
- Les robots peuvent seulement bouger si la position de d�part ou d'arriv�e est valide (par exemple pour un pont).
- La position en dessous d'un robot est toujours valide (ils peuvent toujours descendre).
- Les positions jusqu'� `flightHeight` au dessus d'un bloc solide sont valides (capacit�s de vol limit�es).
- N'importe quelle position avec un bloc adjacent ayant une face solide tourn�e vers la nouvelle position est valide (les robots peuvent "grimper").

Ces r�gles, � part la r�gle 2 pour la clart� (ils peuvent toujours descendre), peuvent �tre visualis�es comme �a :
![Visualisation des r�gles de d�placement des robots.](opencomputers:doc/img/robotMovement.png)

Si vous ne souhaitez pas vous pr�occuper des limitations de hauteur de vol, ces am�liorations sont ce que vous cherchez.
