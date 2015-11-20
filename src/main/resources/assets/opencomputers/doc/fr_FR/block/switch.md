# Routeur

![[relays](relay.md).](oredict:oc:switch)

*Ce bloc est d�pr�ci� et sera retir� dans une version future.* Transformez les en [relai](relay.md) pour �viter de les perdre.

Le routeur peut �tre utilis� pour permettre � diff�rents sous-r�seaux de s'envoyer des messages r�seau entre eux, sans exposer leurs composants aux [ordinateurs](../general/computer.md) des autres r�seaux. Maintenir l'�tat "local" d'un composant est g�n�ralement une bonne id�e, pour �viter aux [ordinateurs](../general/computer.md) d'utiliser le mauvais [�cran](screen1.md) ou pour �viter qu'une surcharge de composants survienne (ce qui provoque le crash de l'[ordinateur](../general/computer.md) et l'emp�che de d�marrer).

Il y a �galement une version sans-fil de ce bloc, appel�e le [point d'acc�s](accessPoint.md), qui peut aussi relayer des messages sans-fil. Les messages sans-fil peuvent �tre re�us et relay�s par d'autres [points d'acc�s](accessPoint.md), ou par des [ordinateurs](../general/computer.md) �quip�s d'une [carte r�seau sans-fil](../item/wlanCard.md).

Les routeurs et [points d'acc�s](accessPoint.md) ne gardent *pas* de trace des paquets qu'ils ont r�cemment relay�, donc �vitez les boucles dans votre r�seau ou vous pourriez recevoir le m�me paquet plusieurs fois. A cause de la taille limit�e de la m�moire tampon des routeurs, une perte des paquets peut survenir si vous essayez d'envoyer des messages r�seau trop fr�quemment. Vous pouvez am�liorer vos routeurs et points d'acc�s pour augmenter la vitesse � laquelle ils relaient les messages, ainsi que la taille interne de la file des messages.

Les paquets sont seulement renvoy�s un certain nombre de fois, donc encha�ner un certain nombre de [routeurs](switch.md) ou de points d'acc�s n'est pas possible. Par d�faut, un paquet sera renvoy� jusqu'� 5 fois.
