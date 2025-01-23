El algoritmo A* es un algoritmo de búsqueda que encuentra la ruta más corta entre dos puntos en un grafo. Se utiliza para encontrar rutas de costo mínimo y es una mejora del algoritmo de Dijkstra.            El algoritmo A* funciona de la siguiente manera:
Divide el costo de la ruta en dos partes: g(n) y h(n).
g(n) es el costo de la ruta desde el origen hasta un nodo n.
h(n) es el costo estimado de la ruta desde el nodo n hasta el destino.
Calcula el costo total de la ruta, f(n)=g(n)+h(n).
En cada iteración, elige el nodo con el costo total más bajo.

El algoritmo A* se utiliza en muchos ámbitos, como la detección de geolocalizaciones, robótica, diseño de redes, etc...

Complejidad: O(|E|) en el mejor caso; O((|E| + |V|) log|V|) en el peor caso (Dijkstra).
