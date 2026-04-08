package p6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AlmacenajeContenedores {

    private int capacidadC;
    private Integer[] conjuntoS;
    private int mejorK;
    private List<List<Integer>> mejorDistribucion;
    private int[] pesosActuales; // Para evitar el método sum() lento

    public AlmacenajeContenedores(int c, Integer[] toS) {
        this.capacidadC = c;
        this.conjuntoS = toS;
        // Ordenamos de mayor a menor para podar mucho antes el árbol
        Arrays.sort(this.conjuntoS, Collections.reverseOrder());

        this.mejorK = conjuntoS.length;
        this.mejorDistribucion = new ArrayList<>();
        this.pesosActuales = new int[conjuntoS.length];
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Por favor, indica el nombre del fichero.");
            return;
        }

        try {
            Scanner sc = new Scanner(new File(args[0]));
            int c = sc.nextInt();
            List<Integer> listaAux = new ArrayList<>();
            while (sc.hasNextInt()) {
                listaAux.add(sc.nextInt());
            }
            Integer[] toS = listaAux.toArray(new Integer[0]);
            
            AlmacenajeContenedores problema = new AlmacenajeContenedores(c, toS);
            problema.resolver();
            
        } catch (FileNotFoundException e) {
            System.err.println("Fichero no encontrado.");
        }
    }

    public void resolver() {
        backtracking(0, new ArrayList<>());
        mostrarSolucion();
    }

    private void backtracking(int indexObject, List<List<Integer>> contenedores) {
        // Poda por optimalidad: si ya llevamos tantos o más contenedores que la mejor solución, paramos
        if (contenedores.size() >= mejorK) {
            return;
        }

        // Caso base
        if (indexObject == conjuntoS.length) {
            if (contenedores.size() < mejorK) {
                mejorK = contenedores.size();
                guardarCopiaMejorSolucion(contenedores);
            }
            return;
        }

        int objetoActual = conjuntoS[indexObject];

        // 1. Intentar meter en contenedores existentes
        for (int i = 0; i < contenedores.size(); i++) {
            if (pesosActuales[i] + objetoActual <= capacidadC) {
                // AVANZAR
                pesosActuales[i] += objetoActual;
                contenedores.get(i).add(objetoActual);
                
                backtracking(indexObject + 1, contenedores);
                
                // RETROCEDER (Backtrack)
                contenedores.get(i).remove(contenedores.get(i).size() - 1);
                pesosActuales[i] -= objetoActual;
            }
        }

        // 2. Intentar meterlo en un nuevo contenedor (solo si no superamos mejorK)
        if (contenedores.size() + 1 < mejorK) {
            List<Integer> nuevoContenedor = new ArrayList<>();
            nuevoContenedor.add(objetoActual);
            contenedores.add(nuevoContenedor);
            pesosActuales[contenedores.size() - 1] = objetoActual;

            backtracking(indexObject + 1, contenedores);

            // RETROCEDER (Backtrack)
            pesosActuales[contenedores.size() - 1] = 0;
            contenedores.remove(contenedores.size() - 1);
        }
    }

    private void guardarCopiaMejorSolucion(List<List<Integer>> contenedores) {
        mejorDistribucion = new ArrayList<>();
        for (List<Integer> bin : contenedores) {
            mejorDistribucion.add(new ArrayList<>(bin));
        }
    }

    private void mostrarSolucion() {
        System.out.println("Lista de contenedores y objetos contenidos:");
        for (int i = 0; i < mejorDistribucion.size(); i++) {
            System.out.print("Contenedor " + (i + 1) + ": ");
            for (Integer obj : mejorDistribucion.get(i)) {
                System.out.print(obj + " ");
            }
            System.out.println();
        }
        System.out.println("El número de contenedores necesario es " + mejorK + ".");
    }
}