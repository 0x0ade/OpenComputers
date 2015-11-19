# Nanomachines

![Nanomachines, son.](oredict:oc:nanomachines)

Ces petits bonhommes cr�ent une interface avec votre syst�me nerveux pour vous faire aller toujours plus loin, toujours plus haut, toujours plus fort ! Ou vous tuer. Parfois les deux en m�me temps ! En clair, les nanomachines fournissent un syst�me bas� sur de l'�nergie pour appliquer des effets (bons ou mauvais) sur le joueur dans lequel elles r�sident. Pour "installer" des nanomachines, mangez les !

Une fois inject�es, un nouvel indicateur d'�nergie dans votre affichage t�te haute vous indiquera combien il reste d'�nergie � vos nanomachines. Vous pouvez les recharger en vous tenant pr�s d'un [chargeur](../block/charger.md). Plus vous utiliserez vos nanomachines, plus elles consommeront d'�nergie.

Les nanomachines fournissent un certain nombre "d'entr�es" qui peuvent �tre activ�es, ce qui provoque diff�rents effets sur le joueur, en allant des effets visuels comme des particules apparaissant pr�s du joueur, � des effets de potion et d'autres comportement rares et particuliers !

En fonction de la configuration des nanomachines, les entr�es d�clenchent diff�rents effets, les "connexions" �tant al�atoires par configuration. Cela signifie que vous devrez essayer d'activer diff�rentes entr�es pour d�couvrir ce qu'elles activent. Si vous n'�tes pas satisfaits d'une configuration, vous pouvez reconfigurer vos nanomachines en en injectant d'autres (mangez en d'autres). Pour vous d�barrasser compl�tement des nanomachines en vous, buvez du [grog](acid.md). Faites attention, activer trop d'entr�es � la fois peut avoir de s�rieuses cons�quences sur votre organisme !

Par d�faut, les nanomachines seront passives. Vous devrez les contr�ler en utilisant des messages sans fil, donc transporter une [tablette](tablet.md) avec une [carte de r�seau sans-fil](wlanCard.md) est vivement recommand�. Les nanomachines r�agiront seulement aux signaux sans fil �mis par des appareils situ�s � moins de 2 m�tres, mais elles r�agiront aux messages provenant de n'importe quel port, et de n'importe quel appareil !

Les nanomachines r�agissent � un protocole propri�taire tr�s simple : chaque paquet doit �tre constitu� de plusieurs parties, la premi�re �tant le "header" (en-t�te) et qui doit valoir `nanomachines`. La deuxi�me partie doit �tre le nom de la commande. Les parties suppl�mentaires sont les param�tres de la commande. Les commandes suivantes sont disponibles, avec le format `nomDeLaCommande(argument1, ...)` :

- `setResponsePort(port:number)` - Affecte le port sur lequel les nanomachines doivent renvoyer une r�ponse, pour les commandes qui renvoient une r�ponse.
- `getPowerState()` - Demande l'�tat actuel et la capacit� maximale de l'�nergie stock�e dans les nanomachines.
- `getHealth()` - Demande le niveau de sant� du joueur.
- `getHunger()` - Demande le niveau de faim du joueur.
- `getAge()` - Demande l'�ge du joueur en secondes.
- `getName()` - Demande le nom du joueur affich� en jeu.
- `getExperience()` - Demande le niveau d'exp�rience du joueur.
- `getTotalInputCount()` - Demande le nombre total d'entr�es disponibles.
- `getSafeActiveInputs()` - Demande le nombre d'entr�es actives *sans risque*.
- `getMaxActiveInputs()` - Demande le nombre *maximal* d'entr�es actives.
- `getInput(index:number)` - Demande l'�tat actuel de l'entr�e avec l'index en param�tre.
- `setInput(index:number, value:boolean)` - Affecte l'�tat de l'entr�e avec en param�tres l'index et l'�tat.
- `getActiveEffects()` - Demande une liste des effets actifs. Remarquez que certains effets pourraient ne pas s'afficher dans la liste.

Par exemple, dans OpenOS :
- `component.modem.broadcast(1, "nanomachines", "setInput", 1, true)` activera la premi�re entr�e.
- `component.modem.broadcast(1, "nanomachines", "getHealth")` renverra l'information sur la sant� du joueur.