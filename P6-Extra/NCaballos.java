import java.util.ArrayList;
import java.util.List;

public class NCaballos {

    private List<List<String>> soluciones;

    public List<List<String>> resolverNCaballos(int n, int k) {
        soluciones = new ArrayList<>();
        char[][] tablero = crearTablero(n);
        
        // Iniciamos el backtracking desde la casilla (0,0)
        backtracking(0, 0, k, n, tablero);
        return soluciones;
    }

    private void backtracking(int fila, int col, int caballosRestantes, int n, char[][] tablero) {
        // Caso base 1: Hemos colocado todos los caballos requeridos
        if (caballosRestantes == 0) {
            soluciones.add(construirSolucion(tablero));
            return;
        }

        // Caso base 2: Si nos salimos del tablero (fila >= n), no hay más espacio
        if (fila >= n) return;

        // Calculamos la siguiente posición (recorrido celda por celda)
        int siguienteFila = (col == n - 1) ? fila + 1 : fila;
        int siguienteCol = (col == n - 1) ? 0 : col + 1;

        // OPCIÓN 1: Intentar colocar un caballo ('K' de Knight)
        if (esSeguro(fila, col, n, tablero)) {
            tablero[fila][col] = 'K';
            backtracking(siguienteFila, siguienteCol, caballosRestantes - 1, n, tablero);
            tablero[fila][col] = '.'; // Backtracking: quitar caballo
        }

        // OPCIÓN 2: No colocar nada en esta celda y seguir adelante
        backtracking(siguienteFila, siguienteCol, caballosRestantes, n, tablero);
    }

    private boolean esSeguro(int f, int c, int n, char[][] tablero) {
        // Movimientos en "L" (comprobamos todas las direcciones por seguridad)
        int[] df = {-2, -2, -1, -1, 1, 1, 2, 2};
        int[] dc = {-1, 1, -2, 2, -2, 2, -1, 1};

        for (int i = 0; i < 8; i++) {
            int nf = f + df[i];
            int nc = c + dc[i];

            if (nf >= 0 && nf < n && nc >= 0 && nc < n) {
                if (tablero[nf][nc] == 'K') return false;
            }
        }
        return true;
    }

    // --- MÉTODOS AUXILIARES (Idénticos a N-Reinas) ---

    private char[][] crearTablero(int n) {
        char[][] tablero = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tablero[i][j] = '.';
            }
        }
        return tablero;
    }

    private List<String> construirSolucion(char[][] tablero) {
        List<String> solucion = new ArrayList<>();
        for (char[] fila : tablero) {
            solucion.add(new String(fila));
        }
        return solucion;
    }

    private static void pintarTablero(List<List<String>> resultado) {
        for (int i = 0; i < resultado.size(); i++) {
            System.out.println("Solución " + (i + 1) + ":");
            for (String fila : resultado.get(i)) {
                System.out.println(fila);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        NCaballos algoritmo = new NCaballos();
        
        int n = 3; // Tamaño del tablero
        int k = 4; // Número de caballos a colocar
        
        List<List<String>> resultado = algoritmo.resolverNCaballos(n, k);
        
        System.out.println("Se encontraron " + resultado.size() + " soluciones para N=" + n + " y K=" + k + "\n");
        
        pintarTablero(resultado);
    }
}
