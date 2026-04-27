package p7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AlmacenajeContenedoresRyP {

    private int capacidadC;
    private Integer[] conjuntoS;
    private int mejorK;
    private List<List<Integer>> mejorDistribucion;
    private int[] pesosActuales; // Para evitar el método sum() lento. Guarda para cada
                                 //contenedor i su peso actual.

    public AlmacenajeContenedoresRyP(int c, Integer[] toS) {
        this.capacidadC = c; //Capacidad de cada contenerdo
        this.conjuntoS = toS; //lista que tieje los pesos de los distintos objetos
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
            
            AlmacenajeContenedoresRyP problema = new AlmacenajeContenedoresRyP(c, toS);
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
        //Poda: Si (Contenedores Actuales + Cota L1) >= Mejor Solución, dejas de explorar
        int sumaFaltante = sumaRestante(indexObject);
        // Estimamos cuántos contenedores extra necesitaremos como mínimo
        int cotaInferior = (int) Math.ceil((double) sumaFaltante / capacidadC);

        if (contenedores.size() + cotaInferior >= mejorK) {
            return; // Esta rama nunca superará a la mejor solución actual
        }

        //Miramos si ya hemos llegado al último objeto, si es así y no hemos podado
        //es porque esta es la mejor distribucion por ahora, por lo que la guardamos
        if (indexObject == conjuntoS.length) {
            mejorK = contenedores.size(); //Cuantos contenedores se han usado, para podar
            guardarCopiaMejorSolucion(contenedores);
            return;
        }

        //Sacamos el peso del objeto en el que estamos
        int pesoActual = conjuntoS[indexObject];

        //Para cada contenedor en orden, miramos si el objeto cabe
        for (int i = 0; i < contenedores.size(); i++) {
            // ¿Cabe en el contenedor i?
            if (pesosActuales[i] + pesoActual <= capacidadC) {
                //Y si cabe lo metemos
                contenedores.get(i).add(pesoActual);
                pesosActuales[i] += pesoActual;

                // Llamada recursiva para el siguiente objeto
                backtracking(indexObject + 1, contenedores);

                // Hacemos el retroceso (backtrack) pa cuando vuelva
                pesosActuales[i] -= pesoActual;
                contenedores.get(i).remove(contenedores.get(i).size() - 1);
            }
        }

        //En caso de que no entrar en ningun contenedor existente creamos uno, pero
        //solo si merece la pena, es decir si no estamos pasando el record de contenedores
        if (contenedores.size() + 1 < mejorK) {
            List<Integer> nuevoContenedor = new ArrayList<>();
            nuevoContenedor.add(pesoActual);
            contenedores.add(nuevoContenedor); // Añadimos la nueva lista
            pesosActuales[contenedores.size() - 1] = pesoActual;

            // Llamada recursiva para el siguiente objeto
            backtracking(indexObject + 1, contenedores);

            // Hacemos el retroceso (backtrack) pa cuando vuelva
            pesosActuales[contenedores.size() - 1] = 0;
            contenedores.remove(contenedores.size() - 1); // Borramos la lista entera
        }
    }

    //Heuristico para la poda. (Contenedores Actuales + Cota L1) siendo Cota
    //L1=sumatorio pesos restantes/capacidad
    //Aqui calculamos el sumatorio de los restantes
    private int sumaRestante(int indexObject) {
    int suma = 0;
    for (int i = indexObject; i < conjuntoS.length; i++) {
        suma += conjuntoS[i];
    }
    return suma;
    
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


    //Estructura backtracking una solucion
    /**
     * public class BacktrackingExample {

        static boolean haySolucion = false;

        public static void backtracking(Estado e) {

            // Caso base: ¿es solución?
            if (e.esSolucion()) {
                System.out.println(e);
                haySolucion = true;
                return; // importante para no seguir explorando
            }

            // Caso recursivo: generar hijos
            while (e.hasNextHijos() && !haySolucion) {

                Estado estadoHijo = e.nextHijo(); // siguiente hijo válido

                if (estadoHijo != null) {
                    backtracking(estadoHijo); // llamada recursiva
                }
            }
        }
    }
     */


    
}