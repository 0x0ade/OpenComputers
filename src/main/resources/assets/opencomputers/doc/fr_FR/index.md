# Manuel d'OpenComputers

OpenComputers est un mod qui ajoute au jeu des [ordinateurs](general/computer.md), des [serveurs](item/server1.md), des [robots](block/robot.md), et des [drone](item/drone.md) persistants, modulaires et tr�s configurables. Tous les appareils peuvent �tre programm�s en utilisant Lua 5.2, permettant d'avoir des syst�mes � complexit� variable en fonction de leur usage.

Pour apprendre � utiliser ce manuel, allez sur [la page parlant du manuel](item/manual.md) (Ce texte en vert est un lien, vous pouvez cliquer dessus).

## Table des mati�res

### Appareils
- [Ordinateurs](general/computer.md)
- [Serveurs](item/server1.md)
- [Micro-contr�leurs](block/microcontroller.md)
- [Robots](block/robot.md)
- [Drones](item/drone.md)

### Logiciel et programmation
- [OpenOS](general/openOS.md)
- [Lua](general/lua.md)

### Blocs et objets
- [Objets](item/index.md)
- [Blocs](block/index.md)

### Guides
- [Bien d�marrer](general/quickstart.md)

## Vue d'ensemble

Comme indiqu� plus haut, les ordinateurs d'OpenComputers sont persistants, ce qui signifie qu'un [ordinateur](general/computer.md) en fonctionnement garde son �tat quand le chunk dans lequel il se trouve est d�charg�. Cela signifie que si un joueur s'�loigne de cet [ordinateur](general/computer.md), ou se d�connecte, l'[ordinateur](general/computer.md) se souviendra de son dernier �tat connu et reprendra � ce point quand le joueur s'en rapprochera. La persistance fonctionne pour tous les appareils sauf les [tablettes](item/tablet.md)

Tous les appareils sont modulaires et peuvent �tre assembl�s avec une grande vari�t� de composants, comme les [ordinateurs](general/computer.md) de la vie r�elle. Les joueurs qui aiment bricoler seront capables d'optimiser leurs appareils comme ils le souhaitent. Si on le d�sire, les appareils peuvent �tre [d�mont�s](block/disassembler.md) et reconstruits si la configuration initiale n'�tait pas satisfaisante. Pour les [ordinateurs](general/computer.md) et les [serveurs](item/server1.md), les composants peuvent �tre �chang�s � la vol�e simplement en ouvrant l'interface correspondante.

Les appareils d'OpenComputers sont compatibles avec diff�rents mods pour la manipulation de blocs et d'entit�s (� travers l'[adaptateur](block/adapter.md), ou des am�liorations sp�cifiques d'un [robot](block/robot.md) ou d'un [drone](item/drone.md)). L'�nergie peut �tre fournie gr�ce � une large gamme de mods, incluant, sans limitation, les Redstone Flux, les EU d'IndustrialCraft2, les Joules de Mekanism, l'�nergie d'Applied Energistics 2 autant que la charge de Factorization.

Les appareils d'OpenComputers ont des fonctionnalit�s suppl�mentaires ainsi que quelques limitations. Les [ordinateurs](general/computer.md) sont la base, et sont capables de contenir un bon nombre de composants, contr�l�s par le niveau du processeur utilis�. Les [ordinateurs](general/computer.md) ont �galement acc�s aux composants par leurs six faces. Les [serveurs](item/server1.md) sont capables de se connecter � plus de composants (en interne ou en externe) qu'un [ordinateur](general/computer.md), en utilisant des [bus de composants](item/componentBus1.md); cependant, � cause du [support de serveur](block/serverRack.md), le [serveur](item/server1.md) est seulement capable d'acc�der aux composants par une unique face du [support de serveur](block/serverRack.md), tel que configur� dans l'interface du [server rack](block/serverRack.md). Les [micro-contr�leurs](block/microcontroller.md) sont encore plus limit�s (compar�s aux [ordinateurs](general/computer.md)) par leur manque d'emplacements de [disque dur](item/hdd1.md) et de [lecteur de disquette](block/diskDrive.md), ce qui veut dire qu'[OpenOS](general/openOS.md) ne peut pas �tre install� sur un [micro-contr�leur](block/microcontroller.md). Les [micro-contr�leurs](block/microcontroller.md) ont un emplacement pour une [EEPROM](item/eeprom.md), et peuvent �tre programm�s avec un syst�me d'exploitation plus sp�cifique pour un ensemble limit� de t�ches.

Les [robots](block/robot.md) sont des [ordinateurs](general/computer.md) mobiles, et sont capables d'interagir avec le monde (mais ne peuvent pas interagir avec d'autres blocs d'OpenComputers). Contrairement aux [ordinateurs](general/computer.md), une fois qu'un robot est construit, les composants � l'int�rieur du [robot](block/robot.md) ne peuvent pas �tre retir�s. Pour contourner cette limitation, les [robots](block/robot.md) peuvent �tre construits avec des conteneurs d'[am�lioration](item/upgradeContainer1.md) ou de [carte](item/cardContainer1.md), ce qui permettra d'�changer � la vol�e des cartes ou des am�liorations, si n�cessaire. [OpenOS](general/openOS.md) peut �tre install� sur les [robots](block/robot.md) en pla�ant un [lecteur de disquette](block/diskDrive.md) dans un emplacement de conteneur, ce qui permettra l'insertion de [disquettes](item/floppy.md), ou en pla�ant un [disque dur](item/hdd1.md) avec [OpenOS](general/openOS.md) pr�-install� dans l'un des emplacements de [disque dur](item/hdd1.md). Pour compl�tement reconfigurer un [robot](block/robot.md), il devra �tre [d�sassembl�](block/disassembler.md) avant. Les [drones](item/drone.md) sont une version limit�e des [robots](block/robot.md). Ils se d�placent diff�remment, ont moins d'emplacements d'inventaire, et n'ont pas de syst�me d'exploitation (� l'instar des [micro-contr�leurs](block/microcontroller.md), les [drones](item/drone.md) peuvent �tre configur�s avec une [EEPROM](item/eeprom.md) programm�e pour un ensemble limit� de t�ches). Pour la plupart, les [robots](block/robot.md) et les [drones](item/drone.md) partagent les m�mes am�liorations et composants; cependant, les am�liorations se comportent diff�remment avec les [drones](item/drone.md), comme les [am�liorations d'inventaire](item/inventoryUpgrade.md) qui fournissent seulement 4 emplacements par am�lioration, pour un total de 8 emplacements, tandis que les [robots](block/robot.md) sont capables d'accepter plus d'[am�liorations d'inventaire](item/inventoryUpgrade.md) (pour un total de 4) ainsi que l'obtention de plus d'emplacements par am�lioration (16 emplacements par am�lioration).

Ce manuel contient des informations d�taill�es concernant tous les blocs et objets, comment mettre en place diff�rents types de syst�mes et d'appareils, ainsi qu'une introduction � la programmation.
