# Redstone E/S

![Salut Red.](oredict:oc:redstone)

Le bloc d'E/S de redstone peut �tre utilis� pour lire et �mettre des signaux de redstone � distance. Il se comporte comme un hybride des [cartes de redstone](../item/redstoneCard1.md) de niveau 1 et 2 : il peut aussi bien lire et �mettre des signaux analogiques que des signaux empaquet�s (bundle), mais il ne peut pas lire ou �mettre de signaux redstone sans-fil.

En indiquant un c�t� aux m�thodes du composant expos� par ce bloc, les directions sont les points cardinaux, c'est � dire qu'il est recommand� d'utiliser `sides.north`, `sides.east`, etc.

De m�me que les [cartes de redstone](../item/redstoneCard1.md), ce bloc injecte un signal dans les [ordinateurs](../general/computer.md) connect�s quand l'�tat du signal de redstone change - autant pour les signaux analogiques qu'empaquet�s (bundle). Ce bloc peut �galement �tre configur� pour d�marrer des [ordinateurs](../general/computer.md) connect�s quand une certaine puissance de signal est d�pass�e, ce qui permet de d�marrer automatiquement des [ordinateurs](../general/computer.md).
