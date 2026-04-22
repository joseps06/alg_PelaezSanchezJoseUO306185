package p7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class AlmacenajeContenedoresRyP {

    private int capacidadC;
    private Integer[] conjuntoS;
    private int mejorK;
    private List<List<Integer>> mejorDistribucion;
    private int[] pesosActuales;

    private int[] sumaRestante; // NUEVO: para cotas

    public AlmacenajeContenedoresRyP(int c, Integer[] toS) {
        this.capacidadC = c;
        this.conjuntoS = toS;

        Arrays.sort(this.conjuntoS, Collections.reverseOrder());

        this.mejorK = conjuntoS.length;
        this.mejorDistribucion = new ArrayList<>();
        this.pesosActuales = new int[conjuntoS.length];

        // Precalcular suma restante
        sumaRestante = new int[conjuntoS.length + 1];
        sumaRestante[conjuntoS.length] = 0;
        for (int i = conjuntoS.length - 1; i >= 0; i--) {
            sumaRestante[i] = sumaRestante[i + 1] + conjuntoS[i];
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Indica el fichero.");
            return;
        }

        try {
            Scanner sc = new Scanner(new File(args[0]));
            int c = sc.nextInt();

            List<Integer> lista = new ArrayList<>();
            while (sc.hasNextInt()) {
                lista.add(sc.nextInt());
            }

            Integer[] toS = lista.toArray(new Integer[0]);

            AlmacenajeContenedoresRyP p = new AlmacenajeContenedoresRyP(c, toS);
            p.resolver();

        } catch (FileNotFoundException e) {
            System.out.println("Error fichero.");
        }
    }

    public void resolver() {
        backtracking(0, new ArrayList<>());
        mostrarSolucion();
    }

    private void backtracking(int indexObject, List<List<Integer>> contenedores) {

        int usados = contenedores.size();

        // COTA INFERIOR
        int capacidadRestante = sumaRestante[indexObject];
        int cotaInferior = (int) Math.ceil((double) capacidadRestante / capacidadC);

        // PODA Branch & Bound
        if (usados + cotaInferior >= mejorK) {
            return;
        }

        // Caso base
        if (indexObject == conjuntoS.length) {
            mejorK = usados;
            guardarCopiaMejorSolucion(contenedores);
            return;
        }

        int objeto = conjuntoS[indexObject];

        // Evitar simetrías (mejora importante)
        Set<Integer> cargasProbadas = new HashSet<>();

        // 1. Contenedores existentes
        for (int i = 0; i < contenedores.size(); i++) {

            if (cargasProbadas.contains(pesosActuales[i])) continue;

            if (pesosActuales[i] + objeto <= capacidadC) {

                cargasProbadas.add(pesosActuales[i]);

                // avanzar
                pesosActuales[i] += objeto;
                contenedores.get(i).add(objeto);

                backtracking(indexObject + 1, contenedores);

                // retroceder
                contenedores.get(i).remove(contenedores.get(i).size() - 1);
                pesosActuales[i] -= objeto;
            }
        }

        // 2. Nuevo contenedor
        contenedores.add(new ArrayList<>(List.of(objeto)));
        pesosActuales[contenedores.size() - 1] = objeto;

        backtracking(indexObject + 1, contenedores);

        // retroceder
        pesosActuales[contenedores.size() - 1] = 0;
        contenedores.remove(contenedores.size() - 1);
    }

    private void guardarCopiaMejorSolucion(List<List<Integer>> contenedores) {
        mejorDistribucion = new ArrayList<>();
        for (List<Integer> bin : contenedores) {
            mejorDistribucion.add(new ArrayList<>(bin));
        }
    }

    private void mostrarSolucion() {
        System.out.println("Lista de contenedores:");

        for (int i = 0; i < mejorDistribucion.size(); i++) {
            System.out.print("Contenedor " + (i + 1) + ": ");
            for (int x : mejorDistribucion.get(i)) {
                System.out.print(x + " ");
            }
            System.out.println();
        }

        System.out.println("Número mínimo: " + mejorK);
    }
}