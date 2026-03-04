package p3p;

import java.io.*;
import java.util.*;

public class PuntosTrivial {

    public static class Punto {
        double x, y;
        public Punto(double x, double y) { this.x = x; this.y = y; }
    }

    public static double distancia(Punto p1, Punto p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    public static void main(String[] args) throws IOException {
        String fichero = args[0];
        List<Punto> puntos = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fichero));
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] coords = linea.trim().split("\\s+|,");
            double x = Double.parseDouble(coords[0]);
            double y = Double.parseDouble(coords[1]);
            puntos.add(new Punto(x, y));
        }
        br.close();

        double minDist = Double.MAX_VALUE;
        Punto p1 = null, p2 = null;

        for (int i = 0; i < puntos.size(); i++) {
            for (int j = i+1; j < puntos.size(); j++) {
                double d = distancia(puntos.get(i), puntos.get(j));
                if (d < minDist) {
                    minDist = d;
                    p1 = puntos.get(i);
                    p2 = puntos.get(j);
                }
            }
        }

        System.out.printf("PUNTOS MÁS CERCANOS: [%.6f, %.6f] [%.6f, %.6f]%n", 
                           p1.x, p1.y, p2.x, p2.y);
        System.out.printf("SU DISTANCIA MÍNIMA= %.6f%n", minDist);
    }
    
}
