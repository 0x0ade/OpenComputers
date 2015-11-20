# Ecrans

![Tu vois �a ?](oredict:oc:screen1)

Un �cran est utilis� en combinaison avec une [carte graphique](../item/graphicsCard1.md), pour permettre aux [ordinateurs](../general/computer.md) d'afficher du texte. Les diff�rents niveaux d'�crans ont diff�rentes capacit�s, comme le support de diff�rentes r�solutions et couleurs. La qualit� des �crans va d'une faible r�solution en affichage monochrome � une haute r�solution en 256 couleurs.

La r�solution disponible et la profondeur de couleurs d�pend du composant de plus bas niveau. Si vous utilisez une [carte graphique (niveau 1)](../item/graphicsCard1.md) avec un [�cran (niveau 3)](screen3.md), seules les couleurs et la r�solutions de niveau 1 seront disponibles. Cependant, en utilisant une [carte graphique](../item/graphicsCard1.md) de niveau 3 avec un �cran de niveau 1, m�me si la r�solution et la profondeur de couleur seront limit�es au niveau 1, les op�rations de la [carte graphique](../item/graphicsCard1.md) seront plus rapides qu'en utilisant une [carte graphique](../item/graphicsCard1.md) de niveau 1.

Les �crans peuvent �tre plac�s les uns contre les autres pour former un �cran multi-bloc, tant qu'ils sont dans la m�me direction. S'ils sont plac�s vers le haut ou le bas ils doivent �galement �tre tourn�s dans le m�me sens. Leur orientation est indiqu�e par une fl�che dans la barre d'inventaire en tenant l'�cran en main.

La taille d'un �cran n'a pas d'impact sur la r�solution disponible, seulement son niveau. Pour g�rer la mani�re dont les �crans se connectent entr eux, il est possible de les colorer avec n'importe quel colorant. Faites simplement un clic-droit sur l'�cran avec un colorant en main. Le colorant ne sera pas consomm�, mais les �crans ne garderont pas leur couleur une fois d�truits. Des �crans de diff�rente couleur ne se connecteront pas. Des �crans de diff�rents niveaux ne connecteront jamais, m�me s'ils sont de la m�me couleur.

Les �crans de niveau 2 et 3 supportent �galement les entr�es de la souris. Il est possible de cliquer soit dans l'interface de l'�cran (qui peut seulement �tre ouvert si un [clavier](keyboard.md) est connect� � l'�cran), soit en s'accroupissant pour cliquer dessus (avec la main vide dans le doute). Le fait de s'accroupir est optionnel si l'�cran n'a pas de [clavier](keyboard.md). Remarquez qu'il est possible de contr�ler l'ouverture de l'interface - ou en tout cas son activation - par l'API du composant expos�e aux [ordinateurs](../general/computer.md). Les �crans de niveau 3 permettent une d�tection plus pr�cise de l'endroit touch�, si �a a �t� activ� dans leur composant. Cela permet de d�tecter si la partie haute ou basse d'un caract�re a �t� cliqu�, par exemple, ce qui peut �tre utile si vous vous servez de caract�res sp�ciaux en Unicode pour simuler de meilleures r�solutions.

Les r�solutions et profondeurs de couleur des �crans sont les suivantes :
- Tier 1: 50x16, couleur sur 1-bit
- Tier 2: 80x25, couleur sur 4-bit
- Tier 3: 160x50, couleur sur 8-bit
