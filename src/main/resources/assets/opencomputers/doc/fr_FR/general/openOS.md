# OpenOS

OpenOS est un syst�me d'exploitation basique disponible dans OpenComputers. Il est n�cessaire pour faire d�marrer un [ordinateur](computer.md) pour la premi�re fois, et peut �tre fabriqu� en pla�ant une [disquette](../item/floppy.md) vide et un manuel d'OpenComputers dans une table de craft.

Une fois cr��e, la [disquette](../item/floppy.md) peut �tre plac�e dans un [lecteur de disquette](../block/diskDrive.md) connect�e � un syst�me [informatique](computer.md) [correctement configur�](quickstart.md), ce qui permettra � l'[ordinateur](computer.md) de lancer OpenOS.
Une fois d�marr�, il est conseill� d'installer OpenOS sur un [disque dur](../item/hdd1.md) vierge, ce qui �vite la n�cessit� d'une [disquette](../item/floppy.md) et donne acc�s � un syst�me de fichiers en lecture/�criture (la [disquette](../item/floppy.md) d'OpenOS et les autres disquettes "trouv�es" sont en lecture seule). Un [bo�tier](../block/case3.md) de niveau 3 n'a pas besoin de [lecteur de disquette](../block/diskDrive.md), car il a un emplacement int�gr� pour les [disquettes](../item/floppy.md).

OpenOS peut �tre install� en �crivant simplement `install`, et en suivant les informations affich�es � l'�cran pour finaliser l'installation. La [disquette](../item/floppy.md) peut �tre retir�e une fois que le syst�me a �t� red�marr�. OpenOS peut �tre install� sur tous les appareils sauf les [drones](../item/drone.md) et les [micro-contr�leurs](../block/microcontroller.md) (ils n�cessitent tous les deux une programmation manuelle d'une [EEPROM](../item/eeprom.md) pour fournir des fonctionnalit�s, parce qu'ils n'ont pas de syst�me de fichiers int�gr�).

OpenOS a de nombreuses fonctions int�gr�es, la plus utile d'entre elles �tant la commande `lua`, qui ouvre un interpr�teur Lua. C'est un bon endroit de test pour essayer diverses commandes, et exp�rimenter l'API des composants, avant d'�crire les commandes dans un script .lua. Retenez l'information affich�e lors du d�marrage de l'interpr�teur, elle vous expliquera comment afficher le r�sultat des commandes que vous entrez, et comment en sortir.

Pour plus d'information sur la programmation, r�f�rez vous � la page sur la [programmation en Lua](lua.md). Pour ex�cuter des scripts Lua, saisissez simplement le nom du fichier et appuyez sur Entr�e (par exemple, `script.lua` peut �tre ex�cut� en tapant la commande `script` dans le terminal).
