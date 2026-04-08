package p6;

import java.io.PrintStream;
import java.io.OutputStream;

public class AlmacenajeContenedoresTiempos {

    public static void main(String[] args) {
        // Lista de archivos de prueba (añadir aquí todos los que se quieran probar)
        String[] ficheros = {"test00.txt", "test01.txt", "test02.txt", "test03.txt",
         "test04.txt", "test05.txt", "test06.txt", "test07.txt", "test08.txt", "test09.txt"};

        System.out.println("==============================================");
        System.out.printf("%-15s | %-20s%n", "FICHERO", "TIEMPO (ms)");
        System.out.println("----------------------------------------------");

        // Guardamos la salida estándar original (la consola)
        PrintStream consolaOriginal = System.out;

        // Creamos una salida "vacía" para que AlmacenajeContenedores no imprima nada
        PrintStream salidaNula = new PrintStream(new OutputStream() {
            public void write(int b) {
                // No hace nada, descarta los caracteres
            }
        });

        for (String nombreFichero : ficheros) {
            try {
                // Redirigimos la salida a la nada para que no imprima la distribución
                System.setOut(salidaNula);

                long t1 = System.currentTimeMillis();
                
                // Llamamos al main pasando el fichero como argumento
                AlmacenajeContenedores.main(new String[]{nombreFichero});
                
                long t2 = System.currentTimeMillis();

                // Restauramos la consola para imprimir nuestra tabla
                System.setOut(consolaOriginal);
                System.out.printf("%-15s | %-20d%n", nombreFichero, (t2 - t1));

            } catch (Exception e) {
                System.setOut(consolaOriginal);
                System.out.printf("%-15s | %-20s%n", nombreFichero, "Error/No encontrado");
            }
        }
        
        System.out.println("==============================================");
    }
}
