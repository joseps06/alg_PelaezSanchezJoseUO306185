import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


/**
 * Implementaremos en esta clase una resolución para el Problema de las N-Reinas utilizando un algoritmo de Backtracking.
 * El objetivo es colocar N reinas en un tablero de ajedrez de NxN sin que ninguna se ataque entre sí; es decir, que no compartan fila, columna ni diagonal.
 * Vamos a evitar recorrer el tablero iterativamente para validar posiciones. 
 * En su lugar, vamos a registrar las columnas y diagonales que ya están bajo ataque utilizando tres arreglos booleanos: 
 * uno para las columnas, otro para las diagonales principales (de arriba a abajo, izquierda a derecha) y otro para las diagonales secundarias (de arriba a abajo, derecha a izquierda).
 */
public class LaberintoTodas {

    private int[][] tablero;

    private int pInicioX;
    private int pInicioY;
    private int pFinalX;
    private int pFinalY;

    private int mejor;

    private final int tSize=7;


    public LaberintoTodas() {

    }

    public static void main(String[] args) {
        String file = "P6-Extra/Opcional/caso2.txt"; //Cambiar ruta en funcion de desde donde ejecute
        int posI = 0;
        int posF = 48;
        System.out.printf("Se intentará llegar de la posición %d a la %d\n", posI, posF);
        LaberintoTodas lb = new LaberintoTodas();
        lb.ejecutar(file, posI, posF);
    }

    public void ejecutar(String file, int posI, int posF) {
        loadData(file);
        printSolucion(tablero);
        parseInicioFinal(posI, posF);
        mejor = tSize*tSize;

        backTracking(pInicioX, pInicioY, tablero);
    }


    private void backTracking(int pX, int pY, int[][] tableroAct) {
        //Caso base
        if (pX == pFinalX && pY == pFinalY) {
            printSolucion(tableroAct);
            int doses = contarDosesTablero(tableroAct);
            if (mejor>doses) {
                mejor = doses;

                // Backtracking (desmarcar antes de salir)
                tableroAct[pX][pY] = 0;
                return;
            }
        }

            // Control de límites
        if (pX < 0 || pY < 0 || pX >= tSize || pY >= tSize) {
            return;
        }
        // Si es obstáculo o ya visitado
        if (tableroAct[pX][pY] != 0) {
            return;
        }
        // Marcar camino
        tableroAct[pX][pY] = 2;

        // Movimientos en 4 direcciones
        backTracking(pX + 1, pY, tableroAct); // abajo
        backTracking(pX - 1, pY, tableroAct); // arriba
        backTracking(pX, pY + 1, tableroAct); // derecha
        backTracking(pX, pY - 1, tableroAct); // izquierda

        // Backtracking: desmarcar
        tableroAct[pX][pY] = 0;


    }


    private void printSolucion(int[][] tableroAct) {
        System.out.printf("Se ha encontrado una solución con %d pasos: \n", contarDosesTablero(tableroAct));
        for (int i=0; i<tSize; i++) {
            for (int j=0; j<tSize; j++) {
                System.out.print(tableroAct[i][j]);
            }
            System.out.print("\n");
        }
    }


    private int contarDosesTablero(int[][] tableroAct) {
        int cont = 0;
        for (int i=0; i<tSize; i++) {
            for (int j=0; j<tSize; j++) {
                if (tableroAct[i][j]==2) {
                    cont++;
                }
            }
        }
        return cont;
    }


    private void parseInicioFinal(int pInicio, int pFinal) {
        int cont = 0;
        for (int i=0; i<tSize; i++) {
            for (int j=0; j<tSize; j++) {
                if (cont == pInicio) {
                    this.pInicioX=i;
                    this.pInicioY=j;
                }
                if (cont == pFinal) {
                    this.pFinalX=i;
                    this.pFinalY=j;
                }
                cont++;
            }
        }
    }

    public void loadData (String file) {
        this.tablero = new int[7][7];
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            int i=0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                for (int j = 0; j < tSize; j++) {
                    tablero[i][j] = Integer.parseInt(parts[j]);
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    } 


}