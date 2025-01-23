package Ciudades;


public class Main {
    
    public static void main(String[] args) {
        //<>Es importante aclarar que las distancias se hacen en base a la ciudad destino
        //(Nombre, Distancia a destino, Costo para viajar a ella)
        CiudadHeur Bucharest = new CiudadHeur("Bucharest", 0);  //Ciudad de Destino
        CiudadHeur Arad = new CiudadHeur("Arad", 366);          //Ciudad de Partida
        CiudadHeur Craiova = new CiudadHeur("Craiova", 160);
        CiudadHeur Dobreta = new CiudadHeur("Dobreta", 242);
        CiudadHeur Eforie = new CiudadHeur("Eforie", 161);
        CiudadHeur Fagaras = new CiudadHeur("Fagaras", 178);
        CiudadHeur Giurgiu = new CiudadHeur("Giurgiu", 77);
        CiudadHeur Hirsova = new CiudadHeur("Hirsova", 151);
        CiudadHeur Iasi = new CiudadHeur("Iasi", 226);
        CiudadHeur Lugoj = new CiudadHeur("Lugoj", 244);
        CiudadHeur Mehadia = new CiudadHeur("Mehadia", 241);
        CiudadHeur Neamt = new CiudadHeur("Neamt", 234);
        CiudadHeur Oradea = new CiudadHeur("Oradea", 380);
        CiudadHeur Pitesti = new CiudadHeur("Pitesti", 98);
        CiudadHeur Rimnicu = new CiudadHeur("Rimnicu Vilcea", 193);
        CiudadHeur Sibiu = new CiudadHeur("Sibiu", 253);
        CiudadHeur Timisoara = new CiudadHeur("Timisoara", 329);
        CiudadHeur Urziceni = new CiudadHeur("Urziceni", 80);
        CiudadHeur Vaslui = new CiudadHeur("Vaslui", 199);
        CiudadHeur Zerind = new CiudadHeur("Zerind", 374);
        
        /*EN EL SIGUIENTE PASO SIGUEN LOS NEXOS*/
        
        Arad.AgregarCiudad(Zerind, 75);
        Arad.AgregarCiudad(Sibiu, 140);
        Arad.AgregarCiudad(Timisoara, 118);
        Zerind.AgregarCiudad(Arad, 75);
        Zerind.AgregarCiudad(Oradea, 71);
        Sibiu.AgregarCiudad(Arad, 140);
        Sibiu.AgregarCiudad(Oradea, 151);
        Sibiu.AgregarCiudad(Fagaras, 99);
        Sibiu.AgregarCiudad(Rimnicu, 80);
        Timisoara.AgregarCiudad(Arad, 118);
        Timisoara.AgregarCiudad(Lugoj, 111);
        Oradea.AgregarCiudad(Zerind, 71);
        Oradea.AgregarCiudad(Sibiu, 151);
        Lugoj.AgregarCiudad(Timisoara, 111);
        Lugoj.AgregarCiudad(Mehadia, 70);
        Mehadia.AgregarCiudad(Lugoj, 70);
        Mehadia.AgregarCiudad(Dobreta, 75);
        Dobreta.AgregarCiudad(Mehadia, 75);
        Dobreta.AgregarCiudad(Craiova, 120);
        Fagaras.AgregarCiudad(Sibiu, 99);
        Fagaras.AgregarCiudad(Bucharest, 211);
        Rimnicu.AgregarCiudad(Sibiu, 80);
        Rimnicu.AgregarCiudad(Pitesti, 97);
        Rimnicu.AgregarCiudad(Craiova, 146);
        Pitesti.AgregarCiudad(Rimnicu, 97);
        Pitesti.AgregarCiudad(Craiova, 138);
        Pitesti.AgregarCiudad(Bucharest, 101);
        Bucharest.AgregarCiudad(Pitesti, 101);
        Bucharest.AgregarCiudad(Fagaras, 211);
        Bucharest.AgregarCiudad(Giurgiu, 90);
        Bucharest.AgregarCiudad(Urziceni, 85);
        Urziceni.AgregarCiudad(Bucharest, 85);
        Urziceni.AgregarCiudad(Vaslui, 142);
        Urziceni.AgregarCiudad(Hirsova, 98);
        Hirsova.AgregarCiudad(Urziceni, 98);
        Hirsova.AgregarCiudad(Eforie, 86);
        Eforie.AgregarCiudad(Hirsova, 86);
        Vaslui.AgregarCiudad(Urziceni, 142);
        Vaslui.AgregarCiudad(Iasi, 92);
        Iasi.AgregarCiudad(Vaslui, 92);
        Iasi.AgregarCiudad(Neamt, 87);
        Neamt.AgregarCiudad(Iasi, 87);
        

        BVorazHeur Buscador = new BVorazHeur(Arad);//Insertamos Ciudad de Partida
                                                //Este buscador es sin Heurística
        Buscador.busca(Bucharest);
        Buscador.muestra();
        
    }
}  
