package p3p;

import java.io.*;
import java.util.*;

public class PuntosDyV {

    public static class Punto {
        double x, y;
        public Punto(double x, double y) { this.x = x; this.y = y; }
    }

    public static double distancia(Punto p1, Punto p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public static class Resultado {
        Punto p1, p2;
        double dist;
        public Resultado(Punto p1, Punto p2, double dist) {
            this.p1 = p1; this.p2 = p2; this.dist = dist;
        }
    }

    public static Resultado puntosCercanos(Punto[] px, Punto[] py) {
        int n = px.length;
        if (n <= 3) { // caso base trivial
            double minDist = Double.MAX_VALUE;
            Punto p1 = null, p2 = null;
            for (int i=0; i<n; i++) {
                for (int j=i+1; j<n; j++) {
                    double d = distancia(px[i], px[j]);
                    if (d < minDist) { minDist = d; p1 = px[i]; p2 = px[j]; }
                }
            }
            return new Resultado(p1,p2,minDist);
        }

        int mid = n/2;
        Punto mitad = px[mid];

        Punto[] lx = Arrays.copyOfRange(px, 0, mid);
        Punto[] rx = Arrays.copyOfRange(px, mid, n);

        List<Punto> lyList = new ArrayList<>();
        List<Punto> ryList = new ArrayList<>();
        for (Punto p : py) {
            if (p.x <= mitad.x) lyList.add(p);
            else ryList.add(p);
        }
        Punto[] ly = lyList.toArray(new Punto[0]);
        Punto[] ry = ryList.toArray(new Punto[0]);

        Resultado r1 = puntosCercanos(lx, ly);
        Resultado r2 = puntosCercanos(rx, ry);

        Resultado minRes = r1.dist < r2.dist ? r1 : r2;

        // banda central
        List<Punto> banda = new ArrayList<>();
        for (Punto p : py) {
            if (Math.abs(p.x - mitad.x) < minRes.dist) banda.add(p);
        }

        // comparar puntos de la banda (O(n))
        for (int i = 0; i < banda.size(); i++) {
            for (int j = i+1; j < Math.min(i+7,banda.size()); j++) {
                double d = distancia(banda.get(i), banda.get(j));
                if (d < minRes.dist) {
                    minRes = new Resultado(banda.get(i), banda.get(j), d);
                }
            }
        }

        return minRes;
    }

    public static void main(String[] args) throws IOException {
        String fichero = args[0];
        List<Punto> puntosList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fichero));
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] coords = linea.trim().split("\\s+|,");
            double x = Double.parseDouble(coords[0]);
            double y = Double.parseDouble(coords[1]);
            puntosList.add(new Punto(x, y));
        }
        br.close();

        Punto[] px = puntosList.toArray(new Punto[0]);
        Punto[] py = px.clone();
        Arrays.sort(px, Comparator.comparingDouble(p -> p.x));
        Arrays.sort(py, Comparator.comparingDouble(p -> p.y));

        Resultado res = puntosCercanos(px, py);
        System.out.printf("PUNTOS MÁS CERCANOS: [%.6f, %.6f] [%.6f, %.6f]%n",
                          res.p1.x,res.p1.y,res.p2.x,res.p2.y);
        System.out.printf("SU DISTANCIA MÍNIMA= %.6f%n", res.dist);
    }
    
}
