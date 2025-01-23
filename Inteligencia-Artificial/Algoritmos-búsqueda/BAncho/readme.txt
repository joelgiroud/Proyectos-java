El algoritmo de búsqueda en anchura (BFS) es un algoritmo de grafos que busca los caminos más cortos entre vértices. Este algoritmo es uno de los más simples para buscar en un grafo. 
El algoritmo BFS funciona de la siguiente manera:

1  Se define un vértice inicial, llamado raíz 
2  Se crea una cola para decidir qué vértice explorar a continuación 
3  Se agrega la raíz a la cola y se marca como visitada 
4  Mientras la cola no esté vacía, se extrae el primer nodo de la cola 
5  Se exploran todos los vecinos del nodo extraído 
6  Se agregan los vecinos no visitados a la cola y se marcan como visitados 
7  Se dibuja una arista entre el nodo extraído y sus vecinos 

Situaciones óptimas:
*  Encontrar la ruta más corta en un grafo no ponderado.
*  Explorar por niveles o capas.
*  Detectar conectividad.
*  Resolver acertijos y juegos.
*  Modelar propagaciones.

Complejidad: O(V + E)
