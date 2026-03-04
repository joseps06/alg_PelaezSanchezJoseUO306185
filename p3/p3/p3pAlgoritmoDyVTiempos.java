package p3;
import p3.p3pAlgoritmoTrivial;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class p3pAlgoritmoDyVTiempos {
	
	public static void main (String[] arg) 
	{
		 long t1,t2;
		 int nVeces= Integer.parseInt (arg [0]);	
	
	     for (int n=1024;n<=1024000;n*=2)
	    {
			t1 = System.currentTimeMillis ();
			
			List<Punto> puntos= new ArrayList<>();
			rellenar(puntos,n);
			for (int repeticiones=1; repeticiones<=nVeces;repeticiones++)
			{ 
				p3pAlgoritmoDyV.puntosMasCercanos(puntos);
			} 
			puntos.clear();

			t2 = System.currentTimeMillis ();

			System.out.println (" n="+n+ "**TIEMPO="+(t2-t1)+"**nVeces="+nVeces);
		 
		}
	}
	private static void rellenar(List<Punto> puntos, int n) 
	{
	Random r= new Random();
	for(int i=0;i<n;i++){
		double num1 = Math.round(r.nextDouble() *1000000.0)/1000000.0;
		double num2 = Math.round(r.nextDouble() *1000000.0)/1000000.0;
		Punto punto= new Punto(num1, num2);
		puntos.add(punto);
	}   

	}
}
	

