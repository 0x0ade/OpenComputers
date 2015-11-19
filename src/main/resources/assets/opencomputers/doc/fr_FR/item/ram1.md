# M�moire

![Do you remember, dancing in September~](oredict:oc:ram1)

La m�moire est, au m�me titre que le [processeur](cpu1.md), un composant essentiel � chaque [ordinateur](../general/computer.md). En fonction de l'architecture du [processeur](cpu1.md), la m�moire a un effet important sur ce que l'[ordinateur](../general/computer.md) peut ou ne peut pas faire. Pour l'architecture Lua standard, par exemple, elle contr�le la quantit� r�elle de m�moire que les scripts Lua peuvent utiliser. Cela signifie que pour ex�cuter des programmes demandant plus de ressources, vous aurez besoin de plus de m�moire.

La m�moire est disponible en diff�rents niveaux avec les capacit�s suivantes, par d�faut :
- Niveau 1: 192Ko
- Niveau 1.5: 256Ko
- Niveau 2: 384Ko
- Niveau 2.5: 512Ko
- Niveau 3: 768Ko
- Niveau 3.5: 1024Ko

Remarquez que ces valeurs s'appliquent seulement � l'architecture Lua. D'autres architectures peuvent fournir d'autres quantit�s de m�moire pour les diff�rents niveaux. Remarquez �galement que les niveaux 1 et 1.5 sont tous les deux consid�r�s comme de la m�moire de niveau 1, et il en va de m�me pour les barettes de m�moire de niveau 2 et 3.

Ces valeurs peuvent �tre chang�es dans la configuration.
