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
    dp[0][0] = true;
    int maxVehiculos = 0;
    int ultimaLongitudBabor = 0;

    for (int i = 1; i <= vehicles.size(); i++) {
        int vLen = vehicles.get(i - 1); // Vehículo actual
        boolean posible = false;

        for (int l = 0; l <= boatLength; l++) {
            if (dp[i - 1][l]) {
                // Meter coche en BABOR
                if (l + vLen <= boatLength) {
                    dp[i][l + vLen] = true;
                    posible = true;
                }

                // Meter coche en ESTRIBOR
                int ocupadoEstribor = sumatorio[i - 1] - l; 
                if (ocupadoEstribor + vLen <= boatLength) {
                    dp[i][l] = true;
                    posible = true;
                }
            }
        }

        if (!posible) {
            System.out.println("El vehículo " + i + " no cabe. Carga finalizada.");
            break;
        }
        maxVehiculos = i;
    }
}

    public void printData() {
        System.out.printf("Longitud de los carriles: %d", boatLength);
        System.out.println("Longitud de los vehiculos:\n");
        for (int i=0; i<vehicles.size(); i++) {
            System.out.printf("\tVehiculo %d: %d unidades\n", i+1, vehicles.get(i));
        }
    }

    private void loadData (String file) {
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
}