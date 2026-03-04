package p3;


import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
public class p3pAlgoritmoDyV {
	
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
		return puntosMasCercanosRec(puntos);
		
	}

	private static List<Punto> puntosMasCercanosRec(List<Punto> puntos){
		
    int n = puntos.size();
	if(n<=3){
		return p3pAlgoritmoTrivial.puntosMasCercanos(puntos);
	}

    int mitad = n/2;

    List<Punto> izquierda = puntos.subList(0, mitad);
    List<Punto> derecha = puntos.subList(mitad, n);


    List<Punto> parIzq = puntosMasCercanosRec(new ArrayList<Punto>(izquierda));
    List<Punto> parDer = puntosMasCercanosRec(new ArrayList<Punto>(derecha));

    double distIzq = parIzq.get(0).CalcularDistanciaCon(parIzq.get(1));
    double distDer = parDer.get(0).CalcularDistanciaCon(parDer.get(1));

    double distanciaMinima;
    List<Punto> mejorPar;

    if (distIzq < distDer) {
        distanciaMinima = distIzq;
        mejorPar = parIzq;
    } else {
        distanciaMinima = distDer;
        mejorPar = parDer;
    }


    for (Punto p1 : izquierda) {
        for (Punto p2 : derecha) {
            double dist = p1.CalcularDistanciaCon(p2);
            if (dist < distanciaMinima) {
                distanciaMinima = dist;
                mejorPar = Arrays.asList(p1, p2);
            }
        }
    }

    return mejorPar;
}
	}

	

	
	
		
	
	