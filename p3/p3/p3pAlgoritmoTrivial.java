package p3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class p3pAlgoritmoTrivial {
	
	public static void main (String[] arg) 
	{
		 if (arg.length == 0) {
		        System.out.println("Debe pasar un fichero como argumento");
		        return;
		    }

		 String nombreFichero = arg[0];
		 List<Punto>puntos = new ArrayList<Punto>();
		 FileUtil.loadFile(nombreFichero, puntos);
		 List<Punto> resultados= puntosMasCercanos(puntos);
		 System.out.println("Punto mas cercanos :" + resultados.get(0) + " " + resultados.get(1));
		 System.out.println("Distancia :" + resultados.get(0).CalcularDistanciaCon(resultados.get(1)) );
	}

	public static List<Punto> puntosMasCercanos(List<Punto> puntos) {
		double distanciaMinima=100000;
		Punto punto1 = null;
		Punto punto2 = null;
		for (int i = 0; i < puntos.size(); i++) {
			for (int j = 0; j < puntos.size(); j++) {
				if(i!=j) {
					double nuevaDistancia=puntos.get(i).CalcularDistanciaCon(puntos.get(j));
					if(nuevaDistancia < distanciaMinima) {
						distanciaMinima=nuevaDistancia;
						punto1=puntos.get(i);
						punto2=puntos.get(j);
					}
				}
			}
		}
		List<Punto> puntosDistanciaMinima = new ArrayList<Punto>();
		puntosDistanciaMinima.add(punto1);
		puntosDistanciaMinima.add(punto2);
		return puntosDistanciaMinima;
	}

	
}
	
	 class Punto{
		double puntoX;
		double puntoY;
		
		public Punto(double puntoX, double puntoY ) {
			this.puntoX=puntoX;
			this.puntoY=puntoY;
			
		}
		public double CalcularDistanciaCon(Punto punto2) {
			double diferenciax= Math.pow(this.puntoX-punto2.getPuntoX(),2);
			double diferenciay= Math.pow(this.puntoY-punto2.getPuntoY(),2);
			return Math.sqrt(diferenciax +diferenciay);
		}
		public double getPuntoX() {
			return puntoX;
		}
		public double getPuntoY() {
			return puntoY;
		}
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("[" + puntoX + " , " + puntoY + "]");
			return sb.toString();
		}
	}
	 abstract class FileUtil {

		public static void loadFile(String nombreFicheroEntrada, List<Punto> puntos) {

			String linea;
			String[] datosPuntos = null;
			
			

			try {
				BufferedReader fichero = new BufferedReader(new FileReader(nombreFicheroEntrada));
				fichero.readLine();
				while (fichero.ready()) {
					linea = fichero.readLine();
					datosPuntos = linea.split(",");
					double puntox=Double.parseDouble(datosPuntos[0]);
					double puntoy=Double.parseDouble(datosPuntos[1]);
					Punto punto = new Punto(puntox,puntoy);
					puntos.add(punto);
					
				}
				fichero.close();
			} catch (FileNotFoundException fnfe) {
				System.out.println("El archivo no se ha encontrado.");
			} catch (IOException ioe) {
				new RuntimeException("Error de entrada/salida.");
			} catch (NumberFormatException ioe) {
				new RuntimeException("Error en los numeros del fichero");
			}
		}
		
	}
	

