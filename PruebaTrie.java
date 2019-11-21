
package tries;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
public class PruebaTrie {
    
    public static void cargaPalabrasArreglo(String[] arr, String nomArch){
        try{
            File f = new File(nomArch);
            Scanner sc = new Scanner(f);
            int i = 0;
            while(sc.hasNext() && i < arr.length){
                arr[i] = sc.next();
                i++;
            }//while
        }//try
        catch(Exception e){
            System.out.println(e);
        }//catch
    }//method
    
    public static char[] generaLlaves(String nomArch){
        ArrayList<Character> arr = new ArrayList();        
        try{
            File f = new File(nomArch);
            Scanner sc = new Scanner(f);
            String linea;
            while (sc.hasNext()){
                linea = sc.nextLine();
                if (!linea.contains("#")){
                    for (int i = 0; i < linea.length(); i++){
                        if (!arr.contains(linea.charAt(i))){
                            arr.add(linea.charAt(i));
                        }//if
                    }//for                    
                }//if
            }//while
        }//try
        catch(Exception e){
            System.out.println(e);
        }//catch
        char [] ar = new char [arr.size()];
        for (int i = 0; i < arr.size(); i++)
            ar [i] = arr.get(i);
        return ar;
    }//method
    
    public static void cargaPal(String nomArch, Trie t){
        try{
            File f = new File(nomArch);
            Scanner sc = new Scanner(f);
            String s;
            while(sc.hasNext()){
                s = sc.nextLine();
                if (!s.contains("#"))
                    t.inserta(s);                
            }
        }
        catch(Exception e){
            System.out.println(e);            
        }
    }
    
    
    public static void imprimeArreglo(String [] arr){
        for (int i = 0; i < arr.length; i++)
            System.out.println(arr[i]);
    }
    
    
    public static <T extends Comparable <T>> void mergeSort(T [] datos){
        int [] ar = {0};
        mergeSort(datos, 0, datos.length - 1, ar);
        System.out.println("mergeSort reviso " + ar[0] + " datos conentrada de " + datos.length);
    }
    private static <T extends Comparable <T>> void mergeSort(T [] datos, int min, int max, int[] sum){
        T [] temp;
        int indice, izq, der;
        if (min >= max - 1)
            return;
        int tam = max - min + 1, mitad = (max + min)/2;
        temp = (T[])(new Comparable [tam]);
        mergeSort(datos, min, mitad, sum);
        mergeSort(datos, mitad + 1, max, sum);

        izq = min; der = mitad + 1;
        for(int i = 0; i < tam; i++){
            sum[0]++;
            if(izq <= mitad && der <= max){
                if(datos[izq].compareTo(datos[der]) < 0)
                    temp[i] = datos[izq++];
                else
                    temp[i] = datos[der++];
            }
            else{
                if(izq <= mitad)
                    temp[i] = datos[izq++];
                else   
                    temp[i] = datos[der++];
            } 
        }


        for(int i = 0; i < temp.length; i++){
            sum[0]++;
            datos[min + i] = temp[i];
        }
        
    }
    
    
    public static void main(String args[]){
        long st, ord, imp, end,ini;
        String [] arreglo = new String[100000];
        String nombreArch = "wiki-100k.txt";
        st = System.nanoTime();
        cargaPalabrasArreglo(arreglo, nombreArch);
        ord = System.nanoTime();
        mergeSort(arreglo);
        end = System.nanoTime();
        //imprimeArr(arreglo);
        imp = System.nanoTime();
        System.out.println("TIEMPOS DE: merge sort:");
        System.out.println("Tiempo para insertar palabras: "+(ord - st));
        System.out.println("Tiempo para ordenar palabras: " + (end - ord));
        System.out.println("Tiempo para insertar palabras y ordenarlas: "+(end - st));
        char keys [] = generaLlaves(nombreArch);
        Trie miTRIE = new Trie(keys);
        ini = System.nanoTime();
        cargaPal(nombreArch, miTRIE);
        end = System.nanoTime();        
        System.out.println("PRUEBA TRIE:");
        System.out.println("Tiempo para insertar palabras YA ordenadas: "+(end - ini));
  
    }
}