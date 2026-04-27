package p5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Ferry
{
    private int boatLength; //Longitud de los carriles del barco
    private List<Integer> vehicles; //Lista de vehiculos
    private boolean[][] dp; //Matriz con las posibles soluciones
    private int[] sumatorio; //Suma acumulada de las longitudes de los vehiculos

    public Ferry(int length, List<Integer> vehicles) {
        this.boatLength = length;
        this.vehicles = vehicles;
        this.dp = new boolean[vehicles.size()+1][boatLength+1];

        this.sumatorio = new int[vehicles.size() + 1];

        //Inicializamos sumatorio
        this.sumatorio[0] = 0;
        for (int i=1; i<=vehicles.size(); i++) {
            this.sumatorio[i] = sumatorio[i-1] + vehicles.get(i-1);
        }

    }

    public void run() {
        // Caso base: 0 vehículos cargados -> 0 metros en babor es posible.
    dp[0][0] = true;

    // Recorremos cada vehículo de la lista
    for (int i = 1; i <= vehicles.size(); i++) {
        int vLen = vehicles.get(i - 1); // Longitud del vehículo actual
        boolean algunEstadoPosible = false;

        for (int j = 0; j <= boatLength; j++) {
            
            // Intentar meter el vehículo i en BABOR
            // Comprobamos si podíamos tener (j - vLen) en babor en el paso anterior
            if (j >= vLen && dp[i - 1][j - vLen]) {
                dp[i][j] = true;
            }

            // Intentar meter el vehículo i en ESTRIBOR
            // Si antes podíamos tener j metros en babor, ¿cabe este coche ahora en estribor?
            // Espacio ocupado en estribor antes de este coche = sumatorio total anterior - babor
            int ocupadoEstriborAntes = sumatorio[i - 1] - j;
            
            if (dp[i - 1][j] && (ocupadoEstriborAntes + vLen <= boatLength)) {
                dp[i][j] = true;
            }

            // Si hemos activado algún True en esta fila, el proceso puede continuar
            if (dp[i][j]) {
                algunEstadoPosible = true;
            }
        }

        // Si el vehículo i no cupo en ningún lado, paramos.
        // No se pueden saltar vehículos.
        if (!algunEstadoPosible) break;
    }
    }   


    /**
     * Filas (i): Representan los coches que vamos cargando (Coche 0, Coche 1, Coche 2...).
     * Columnas (j): Representan los metros OCUPADOS en el carril de Babor (de 0 hasta L).
     */
    /**
     * Un estado es "Verdadero" si podemos llegar a él usando una de estas dos 
     * opciones con el coche actual:
     * Meterlo en Babor: 
     * Miramos si en el paso 
     * anterior (i-1) teniamos j - longitud_coche metros 
     * en Babor. Si es que sí, y el coche cabe, marcamos T.
     * Si quieres que después de meter el coche i el carril de Babor mida j, 
     * significa que antes de este coche, Babor medía j - longitud_coche_i.
     * (Miramos si es True la fila de arriba en esa posición: dp[i-1][j - longitud_coche])
     * 
     * Meterlo en Estribor: 
     * Miramos si en el paso anterior era posible tener los mismos metros j en 
     * Babor. Si es que sí, calculamos cuánto habría en Estribor (SumaTotal - j). 
     * Si el coche cabe ahí, marcamos T.
     * Si pones el coche en Estribor, la longitud de Babor no cambia. 
     * Por tanto, si antes Babor medía j, ahora sigue midiendo j.
     * (Miramos justo arriba: dp[i-1][j] para ver si es True y si es True
     * calculamos si cabria en estribor)
     */


    /**
     * Ejemplo:
     * Fila 0 (Estado inicial): dp[0][0] = True (0 coches, 0m en babor).
     * Fila 1 (Coche de 4m):¿Puedo ponerlo en Babor? Miro dp[0][0]. Es True. 
     * Entonces dp[1][4] = True.¿Puedo ponerlo en Estribor? Miro dp[0][0]. Es True. 
     * ¿Cabe en Estribor? $0 + 4 \le 10$. Sí. Entonces dp[1][0] = True (Babor sigue en 0).
     */
    

    public void printData() {
        System.out.printf("Longitud de los carriles: %d", boatLength);
        System.out.println("Longitud de los vehiculos:\n");
        for (int i=0; i<vehicles.size(); i++) {
            System.out.printf("\tVehiculo %d: %d unidades\n", i+1, vehicles.get(i));
        }
    }

    public void loadData (String file) {
        this.vehicles = new ArrayList<Integer>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            this.boatLength = Integer.valueOf(reader.readLine());
            for (String s : reader.readLine().split(" ")) {
                this.vehicles.add(Integer.valueOf(s));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void printPossibleAssignation() {
        int k = getMaximumNumberOfVehicles(); // Devuelve la última fila i con algún True
    int jFinal = -1;

    // Buscamos en la fila k qué columna j es True
    for (int j = 0; j <= boatLength; j++) {
        if (dp[k][j]) {
            jFinal = j;
            break;
        }
    }

    if (jFinal != -1) {
        System.out.println("\nPosible asignación:");
        processAssignation(k, jFinal);
    }
    }


    public int getMaximumNumberOfVehicles() {
    // Recorremos hacia a atras
    for (int i = vehicles.size(); i >= 0; i--) {
        // en cada fila miramos si hay aklgun true
        for (int j = 0; j <= boatLength; j++) {
            if (dp[i][j]) {
                // el primer true que encontramos es el max
                return i; 
            }
        }
    }
    return 0; // Si no hay nada (ni el coche 1 cupo), el máximo es 0
}


    private void processAssignation(int i, int j) {
    if (i == 0) return; // Caso base: llegamos al principio

    int vLen = vehicles.get(i - 1);

    // vino de babor
    if (j >= vLen && dp[i - 1][j - vLen]) {
        processAssignation(i - 1, j - vLen); // Recursión primero para imprimir en orden 1..k
        System.out.printf("Vehículo %d (longitud %d) a babor.\n", i, vLen);
    } 
    // vino de estribor
    else {
        processAssignation(i - 1, j);
        System.out.printf("Vehículo %d (longitud %d) a estribor.\n", i, vLen);
    }
}

    public void printSolutionTable() {
        for (int i=0; i<dp.length; i++) {
            for (int j=0; j<dp.length; j++) {
                System.out.print(dp[i][j]);
                System.out.print(" ");
            }
            System.out.print("\n");
        }
    }


    
    
    //ESTRUCTURA GENERAL PROG DINAMICA
    /**
     * // 1. Definir estructura
        dp = new ...

        // 2. Casos base
        dp[...] = ...

        // 3. Rellenar tabla
        for (...) {
            for (...) {
                dp[...] = ... // recurrencia
            }
        }

        // 4. Resultado
        return dp[...]
     */

}