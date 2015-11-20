# Lua

Le [manuel de r�f�rence](http://www.lua.org/manual/5.2/manual.html) en Lua et le livre [Programming Lua](http://www.lua.org/pil/) (la premi�re �dition est disponible gratuitement en ligne) sont un bon endroit pour d�marrer avec les bases de Lua et se familiariser avec la synntaxe de base et les biblioth�ques standard. [OpenOS](openOS.md) s'efforce d'�muler les biblioth�ques standard au plus pr�s, avec quelques �carts, comme la biblioth�que de d�bogage qui est principalement absente (pour des raisons de s�curit� li�es au mode "bac � sable"). Ces diff�rences sont [document�es sur le wiki](http://ocdoc.cil.li/api:non-standard-lua-libs).

Les biblioth�ques non-standard devront �tre import�es avec `require` pour les utiliser dans un script. Par exemple :

`local component = require("component")`
`local rs = component.redstone`

Cela vous permettra d'appeller toutes les fonctions fournies par le composant de [redstone](../item/redstoneCard1.md). Par exemple :

`rs.setOutput(require("sides").front, 15)`

**Important**: quand vous travaillez avec l'interpr�teur Lua, n'utilisez *pas* `local`, car les variables seront locales � la ligne. Cela signifie que si vous tapiez les lignes ci-dessus les unes apr�s les autres dans l'interpr�teur, la troisi�me ligne lancerait une erreur, en vous disant que `rs` a une valeur `nil`. Mais dans ce cas, pourquoi seulement la troisi�me ligne ? Parce que, pour faciliter les tests, l'interpr�teur essaye de charger les variables inconnues comme des biblioth�ques. Donc m�me si l'affectation � `component` � la premi�re ligne ne ferait rien, l'utilisation de `component` � la deuxi�me ligne chargerait cette biblioth�que et l'utiliserait. Les biblioth�ques ne sont pas utilis�es automatiquement lorsque que vous utilisez des scripts Lua, afin de r�duire l'usage de la m�moire, car c'est une ressource limit�e.

OpenOS fournit plusieurs biblioth�ques personnalis�es qui peuvent �tre utilis�es dans un grand nombre d'applications, allant du contr�le et la manipulation des composants attach�s � l'[ordinateur](computer.md), aux APIs de r�f�rence pour les couleurs, utilis�es pour le contr�le de c�bles de redstone empaquet�s, et les codes du [clavier](../block/keyboard.md). Des biblioth�ques personnalis�es peuvent �tre employ�e dans un script Lua en utilisant la fonction `require()`, comme ci-dessus. Certaines biblioth�ques personnalis�es n�cessitent des composants sp�cifiques pour fonctionner, comme la biblioth�que `internet` qui requiert une [carte internet](../item/internetCard.md). Dans ce cas particulier, la biblioth�que est m�me fournie par la carte, c'est � dire que la biblioth�que appara�tra une fois que vous aurez install� une carte internet - techniquement parlant, elle est contenue dans un petit fichier en lecture seule sur la carte internet.
