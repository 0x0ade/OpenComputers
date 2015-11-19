# Imprimante 3D

![L'impression en 2D, c'est tellement d�pass�.](oredict:oc:printer)

Les imprimantes 3D vous permettent d'imprimer n'importe quel bloc de n'importe forme, avec n'importe quelle texture. Pour d�marrer avec les imprimantes 3D, vous devrez placer un bloc d'imprimante 3D � c�t� d'un ordinateur. Cela vous donnera acc�s � l'API de composant `printer3d`, ce qui permet de param�trer et d'imprimer des [mod�les](print.md) en utilisant les fonction fournies.

Une mani�re plus simple d'installer une imprimante 3D est d'utiliser le gestionnaire de paquets Open Programs (OPPM). Une fois install� (`oppm install oppm`), assurez vous d'avoir une [carte internet](../item/internetCard.md) dans votre [ordinateur](../general/computer.md) and lancez la commande suivante :
`oppm install print3d-examples`

Vous pouvez trouver des exemples dans `/usr/share/models/` sous forme de fichiers .3dm. Jetez un oeil dans ces fichiers d'exemple pour voir les options disponibles, en particulier le fichier `example.3dm`. Par ailleurs, vous pouvez t�l�charger les programmes `print3d` et `print3d-examples` depuis [OpenPrograms](https://github.com/OpenPrograms) en utilisant `wget` et une [carte internet](../item/internetCard.md).

Afin d'imprimer les mod�les, une imprimante 3D doit �tre configur�e par un [ordinateur](../general/computer.md). S'il est mis en mode sans-arr�t, l'ordinateur ne sera plus requis par la suite. Vous devrez �galement lui fournir une [cartouche d'encre](../item/inkCartridge.md) et un peu de [cham�lium](../item/chamelium.md) pour les mati�res premi�res. La quantit� de cham�lium utilis�e d�pend du volume de l'impression 3D, tandis que la quantit� d'encre utilis�e d�pend de la surface de l'objet imprim�.

Pour imprimer un objet, utilisez la commande suivante :
`print3d /path/to/file.3dm`
en fournissant le chemin vers le fichier .3dm.

Vous pourrez trouver la documentation relative � la cr�ation de vos propres mod�les dans `/usr/share/models/example.3dm`.
