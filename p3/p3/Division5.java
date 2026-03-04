package p3;

public class Division5
{

static long cont;

    // Recursión tipo a > b^k con bucle O(n)
    public static void rec5(int n) {
        if (n <= 0) {
            cont++;
        } else {
            for (int i = 0; i < n; i++) cont++; // O(n)
            rec5(n / 2); // primera llamada
            rec5(n / 2); // segunda llamada → a > b^k
        }
    }

    public static void main(String[] arg) {
        long t1, t2, cont;
        int nVeces = Integer.parseInt(arg[0]);

        for (int n = 1000; n <= 8000; n *= 2) { // valores de n
            t1 = System.currentTimeMillis();

            for (int repeticiones = 1; repeticiones <= nVeces; repeticiones++) {
                cont = 0;
                rec5(n);
            }

            t2 = System.currentTimeMillis();

            System.out.println(" n=" + n + " **TIEMPO=" + (t2 - t1) + " ms ** nVeces=" + nVeces);
        }
    }
} //class