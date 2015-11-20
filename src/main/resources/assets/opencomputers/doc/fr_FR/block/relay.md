# Relai

![Construit des ponts.](oredict:oc:relay)

Le relai peut �tre utilis� pour permettre � diff�rents sous-r�seaux de s'envoyer des messages r�seau entre eux, sans exposer leurs composants aux [ordinateurs](../general/computer.md) des autres r�seaux. Maintenir l'�tat "local" d'un composant est g�n�ralement une bonne id�e, pour �viter aux [ordinateurs](../general/computer.md) d'utiliser le mauvais [�cran](screen1.md) ou pour �viter qu'une surcharge de composants survienne (ce qui provoque le crash de l'[ordinateur](../general/computer.md) et l'emp�che de d�marrer).

Le relai peut �tre am�lior� en ins�rant une [carte de r�seau sans-fil](../item/wlanCard.md) pour relayer aussi des messages sans-fil. Les messages sans-fil peuvent �tre re�us et relay�s par d'autres relais avec une carte de r�seau sans-fil, ou par des ordinateurs �quip�s d'une carte r�seau sans-fil.

Par ailleurs, le relai peut �tre am�lior� en utilis� des [cartes li�es](../item/linkedCard.md). Dans ce cas, il transmettra aussi les messages � travers le tunnel de la carte li�e; ceci aura le co�t habituel, donc assurez vous que le relai a suffisamment d'�nergie.

Les relais *ne gardent pas* de trace des paquets qu'ils ont transmis r�cemment, donc �vitez les boucles dans votre r�seau, ou vous pourriez recevoir le m�me paquet plusieurs fois. Envoyer des messages trop souvent causera la perte de paquets, � cause de la taille limit�e du tampon des relais.

Les paquets sont seulement renvoy�s un certain nombre de fois, donc encha�ner un certain nombre de relais n'est pas possible. Par d�faut, un paquet sera renvoy� jusqu'� 5 fois.
