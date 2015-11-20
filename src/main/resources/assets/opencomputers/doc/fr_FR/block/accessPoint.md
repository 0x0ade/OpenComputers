# Point d'acc�s

![AAA](oredict:oc:accessPoint)

*Ce bloc est d�pr�ci� et sera retir� dans une version future.* Transformez les en [relai](relay.md) pour �viter de les perdre.

Le point d'acc�s est la version sans-fil du [routeur](switch.md). Il peut �tre utilis� pour s�parer des sous-r�seaux pour que les machines qui les composent ne voient pas les [composants](../general/computer.md) des autres r�seaux, tout en leur permettant d'envoyer des messages r�seau aux machines d'autres r�seaux.

En plus de �a, ce bloc peut faire office de r�p�teur : il peut renvoyer des messages filaires en tant que messages filaires � d'autres appareils, ou des messages sans-fil en tant que messages filaires ou sans-fil.

Les [routeurs](switch.md) et points d'acc�s ne gardent *pas* de trace des paquets qu'ils ont r�cemment relay�, donc �vitez les boucles dans votre r�seau ou vous pourriez recevoir le m�me paquet plusieurs fois. A cause de la taille limit�e de la m�moire tampon des routeurs, une perte des paquets peut survenir si vous essayez d'envoyer des messages r�seau trop fr�quemment. Vous pouvez am�liorer vos routeurs et points d'acc�s pour augmenter la vitesse � laquelle ils relaient les messages, ainsi que la taille interne de la file des messages.

Les paquets sont seulement renvoy�s un certain nombre de fois, donc encha�ner un certain nombre de [routeurs](switch.md) ou de points d'acc�s n'est pas possible. Par d�faut, un paquet sera renvoy� jusqu'� 5 fois.
