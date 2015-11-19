# Bien d�marrer

Aussi connu en tant que "comment construire votre premier ordinateur". Pour faire d�marrer votre premier [ordinateur](computer.md), vous aurez d'abord besoin de l'installer correctement. Il y a plusieurs types diff�rents d'ordinateurs dans OpenComputers, mais commen�ons avec la base : l'ordinateur standard.

**Avertissement**: ce sera un pas-�-pas, et il fournira des informations sur la mani�re de chercher des questions vous-m�me plus tard, donc �a sera plut�t long. Si vous n'avez jamais construit d'ordinateur dans la vie r�elle, et/ou que vous �tes compl�tement nouveau sur ce mod, il est vivement recommand� que vous lisiez tout ceci.

Premi�rement, vous aurez besoin d'un [bo�tier](../block/case1.md). C'est le bloc qui contiendra tous les composants, en d�finissant le comportement de l'ordinateur que vous construisez.

![Un bo�tier d'ordinateur de niveau 2.](oredict:oc:case2)

Par exemple, vous aurez besoin de choisir quel niveau de [carte graphique](../item/graphicsCard1.md) vous voulez utiliser, si vous avez besoin d'une [carte r�seau](../item/lanCard.md), une [carte de redstone](../item/redstoneCard1.md) ou, si vous faites des essais en mode cr�atif, peut �tre m�me une [carte de d�bogueur](../item/debugCard.md).

Quand vous ouvrez l'interface du [bo�tier d'ordinateur](../block/case1.md), vous verrez quelques emplacements � droite. Le nombre d'emplacements, et le niveau des composants qui peuvent �tre plac�s dedans (indiqu� par le petit chiffre romain dans l'emplacement) d�pend du niveau du bo�tier lui-m�me.
![Interface d'un bo�tier de niveau 2.](opencomputers:doc/img/configuration_case1.png)
Quand ils sont vides, les [bo�tiers](../block/case1.md) sont assez inutiles. Vous pouvez essayer d'allumer votre [ordinateur](computer.md), mais il affichera imm�diatement un message d'erreur dans votre tchat, et fera entendre son m�contentement en vous bipant dessus. Heureusement que le message d'erreur vous dit quoi faire pour r�soudre la situation : il demande de l'�nergie. Connectez votre [ordinateur](computer.md) � un peu d'�nergie, soit directement soit via un [convertisseur �nerg�tique](../block/powerConverter.md).

Maintenant, si vous essayez de le d�marrer, il vous dira qu'il a besoin d'un [processeur](../item/cpu1.md). Il en existe de diff�rents niveaux - une tendance dont vous remarquerez la pr�sence partout dans OpenComputers. Pour les [processeurs](../item/cpu1.md), un meilleur niveau signifie plus de composants � la fois, ainsi qu'une vitesse plus �lev�e. Donc choississez un niveau, et mettez le dans votre [bo�tier](../block/case1.md).

Ensuite, il vous sera demand� d'ins�rer de la [m�moire (RAM)](../item/ram1.md). Remarquez que le bip est diff�rent maintenant : long-court. De plus hauts niveaux de [m�moire (RAM)](../item/ram1.md) permettent plus de m�moire disponible pour les programmes s'ex�cutant sur votre [ordinateur](computer.md). Pour lancer [OpenOS](openOS.md), ce qui est le but de cette introduction, vous devrez utiliser au moins deux barettes de [m�moire (RAM)](../item/ram1.md) de niveau 1.

On progresse bien. Pour le moment, votre [bo�tier](../block/case1.md) ressemblera � quelque chose comme �a :
![Un ordinateur partiellement configur�.](opencomputers:doc/img/configuration_case2.png)
Et voil�, mettre l'ordinateur sous tension n'affichera plus de message d'erreur ! H�las, il ne fait quand m�me pas grand chose. Au moins, il �met 2 bips. Ce qui veut dire que le lancement de l'[ordinateur](computer.md) a �chou�. En d'autres termes : techniquement, il fonctionne ! C'est l� qu'intervient un outil tr�s utile : l'[analyseur](../item/analyzer.md). Cet outil permet d'inspecter beaucoup de blocs d'OpenComputers, ainsi que quelques blocs d'autres mods. Pour l'utiliser sur l'[ordinateur](computer.md), utilisez l'[analyseur](../item/analyzer.md) sur le bo�tier en vous accroupissant.

Vous devriez maintenant voir l'erreur qui a caus� le crash de l'[ordinateur](computer.md) :
`no bios found; install configured EEPROM`
(aucun BIOS trouv�; installez une EEPROM configur�e)

L'accent ici est mis sur *configured*. Crafter une [EEPROM](../item/eeprom.md) est plut�t simple. Pour la configurer, vous aurez g�n�ralement � utiliser un [ordinateur](computer.md) - mais c'est un peu compliqu� pour le moment, donc on va utiliser une recette pour fabriquer une [EEPROM](../item/eeprom.md) configur�e nomm�e "Lua BIOS". La recette classique est une [EEPROM](../item/eeprom.md) et un [manuel](../item/manual.md). Mettez l'[EEPROM](../item/eeprom.md) configur�e dans l'[ordinateur](computer.md), eeeeet...

Non. Toujours rien. Mais nous savons quoi faire : le joueur utilise l'[analyseur](../item/analyzer.md), c'est super efficace ! Maintenant nous avons un message d'erreur diff�rent :
`no bootable medium found; file not found`
(pas de m�dia bootable; fichier non trouv�)

Bien. Cela veut dire que le BIOS fonctionne. C'est juste qu'il ne trouve pas de syst�me de fichier � partir duquel il peut d�marrer, comme une [disquette](../item/floppy.md) ou un [disque dur](../item/hdd1.md). Le BIOS Lua en particulier attend un tel syst�me de fichier pour y trouver un fichier nomm� `init.lua` � la racine. Avec l'[EEPROM](../item/eeprom.md), vous �crivez g�n�ralement dans un syst�me de fichier avec un [ordinateur](computer.md). Vous l'avez probablement devin� : nous devons maintenant fabriquer notre disque de syst�me d'exploitation. Prenez une [disquette](../item/floppy.md) vierge et un [manuel](../item/manual.md), assemblez les, et vous obtiendrez une disquette d'[OpenOS](openOS.md).

Maintenant, si vous avez utilis� un [bo�tier](../block/case2.md) de niveau 2 comme dans les captures d'�cran si dessus, vous n'aurez nulle part o� mettre cette disquette. Si vous avez un [bo�tier](../block/case2.md) cr�atif ou de niveau 3, vous pouvez placer la disquette directement dans le [bo�tier](../block/case1.md). Sinon vous devrez mettre un [lecteur de disquettes](../block/diskDrive.md) � c�t� du bo�tier (ou le connecter avec des [c�bles](../block/cable.md)). Une fois votre disquette ins�r�e, vous savez quoi faire. Appuyez sur le bouton d'alimentation.

Il vit ! Ou il devrait, en tout cas. Si ce n'est pas le cas quelque chose ne va pas, et vous devriez investiguer en utilisant l'[analyzer](../item/analyzer.md). Mais en supposant qu'il fonctionne maintenant, vous avez presque fini. La partie la plus compliqu�e est termin�e. Tout ce qui reste � faire est de lui permettre d'accepter des entr�es, et d'afficher des sorties.

Pour permettre � l'[ordinateur](computer.md) d'afficher des informations, vous devrez vous munir d'un [�cran](../block/screen1.md) et d'une [carte graphique](../item/graphicsCard1.md).
![Non, ce n'est pas un �cran plat.](oredict:oc:screen2)

Placez l'[�cran](../block/screen1.md) � c�t� de votre [bo�tier](../block/case1.md) ou, � nouveau, connectez le en utilisant des [c�bles](../block/cable.md). Puis placez une [carte graphique](../item/graphicsCard1.md) de votre choix dans le [bo�tier](../block/case1.md). Vous devriez maintenant voir un curseur clignotant sur l'[�cran](../block/screen1.md). Finalement, placez un [clavier](../block/keyboard.md) soit sur l'[�cran](../block/screen1.md) lui-m�me, soit juste en face de l'[�cran](../block/screen1.md), pour activer l'entr�e de donn�es au [clavier](../block/keyboard.md).

Avec �a, vous avez fini. L'[ordinateur](computer.md) fonctionne et pr�t � l'action. Essayez le maintenant ! Tapez `lua` dans l'interface syst�me et appuyez sur Entr�e, et vous serez accueilli par quelques informations sur la mani�re d'utiliser l'interpr�teur Lua. Vous pouvez y tester des commandes Lua de base. Pour plus d'informations � ce sujet, allez sur [la page Lua](lua.md)

![Il vit!](opencomputers:doc/img/configuration_done.png)

Amusez vous � construire des [ordinateurs](computer.md) plus complexes, jouer avec des [serveurs](../item/server1.md) et assembler des [robots](../block/robot.md), des [drones](../item/drone.md), des [micro-contr�leurs](../block/microcontroller.md) et des [tablettes](../item/tablet.md) dans l'[assembleur �lectronique](../block/assembler.md).

Bon code !
