package p4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ColoreoGrafo {

    public static Map<String, String> realizarVoraz(Map<String, List<String>> grafo) {
        Map<String, String> solucion = new HashMap<String, String>();
        int n = grafo.size();
        String[] colores = {"red", "blue", "green",
            "yellow", "orange", "purple", "cyan", "magenta", "lime"};

        solucion.put("0", colores[0]);
        for (int i=1; i<n; i++) {
            String nodoActual = String.valueOf(i);
            //No indicamos de que tipo es la lista pa que vaya el JSON
            List<?> vecinos = grafo.get(nodoActual);

            Set<String> coloresProhibidos = new HashSet<>();
        
            if (vecinos != null) {
                //Lo mismo aqui pongo Object pa que vaya el JSON
                for (Object vecino : vecinos) {
                    //Ahora lo tenemos que pasar a String porque el map es string
                    //a es String por lo tanto si usamos object esta mal
                    String idVecino = String.valueOf(vecino);
                    // Si el vecino ya tiene color asignado, lo añadimos a prohibidos
                    if (solucion.containsKey(idVecino)) {
                        coloresProhibidos.add(solucion.get(idVecino));
                    }
                }
            }

            // Buscamos en nuestra paleta el primer color que no esté prohibido
            for (String color : colores) {
                if (!coloresProhibidos.contains(color)) {
                    solucion.put(nodoActual, color); // Asignamos el color
                    break; // Saltamos al siguiente nodo (i++)
                }
            }
        }
        return solucion;
    }

}
