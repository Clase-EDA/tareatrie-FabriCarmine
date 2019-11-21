
package tries;

import java.io.File;
import java.util.Scanner;

public class Trie<T> {
    private class alpha{
        NodoTrie raiz;
        public alpha(){
            raiz = null;
        }//method
        
        public void setSig(NodoTrie nod){
            raiz = nod;
        }//method
        
        public NodoTrie getSig(){
                return raiz;
        }
    }
    alpha raiz;
    char [] simbolos;
    
    public Trie(char [] sim){
        raiz = new alpha();
        sort(sim);
        simbolos = sim.clone();
    }
    
    public void sort(char[] ar){
         int [] arr = {0};
        mergeSort(ar, 0, ar.length - 1, arr);
               
    }
    
    
    private static void mergeSort(char [] datos, int min, int max, int[] sum){
        char [] temp;
        int indice, izq, der;
        if (min >= max - 1)
            return;
        int tam = max - min + 1;
        int mitad = (max + min)/2;
        temp = new char[tam];
        mergeSort(datos, min, mitad, sum);
        mergeSort(datos, mitad + 1, max, sum);

        izq = min; der = mitad + 1;
        for(int i = 0; i < tam; i++){
            sum[0]++;
            if(izq <= mitad && der <= max){
                if(datos[izq] < datos[der])
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
    
    public int busca(String llave){
        if (isEmpty())
            return 0;
        return busca(llave, raiz.getSig());
    }
    
    private int busca(String llave, NodoTrie actual){
        if (actual == null){
            return 0;
        }
        if (llave == null || llave.equals("")){            
            return actual.cont;
        }
        else{
            return busca(llave.substring(1), actual.getNodo(llave.charAt(0)));
        }
    }
    
    public void inserta(String llave){
        if (isEmpty()){
            NodoTrie n = new NodoTrie(simbolos);
            raiz.setSig(n);
        }     
        raiz.getSig().add(llave);
    }
    
    public boolean elimina(String llave){
        if (isEmpty())
            return false;
        return elimina(llave, raiz.getSig(), llave);
    }//method
    
    private boolean elimina(String llave, NodoTrie actual, String llaveCompleta){
        //Si no hay mas apuntadores desde sus hijos
        if (llave.length() == 1){
            if (actual.getNodo(llave.charAt(0)) == null || actual.getNodo(llave.charAt(0)).cont <= 0)
                return false;
            if (actual.getNodo(llave.charAt(0)).cont > 1)
            actual.getNodo(llave.charAt(0)).cont --;                
            else{
                if (actual.getNodo(llave.charAt(0)).isEmpty()){                    
                    eliminaDeMas(actual.getNodo(llave.charAt(0)), llaveCompleta);
                }
                else{
                    actual.getNodo(llave.charAt(0)).cont --;
                }
                
            }
            return true;
        }
        else{
            if (actual.getNodo(llave.charAt(0)) == null)
                return false;
            return elimina(llave.substring(1), actual.getNodo(llave.charAt(0)), llaveCompleta);
        }
    }
    
    public void eliminaDeMas(NodoTrie n, String llave){
        while(n.getPapa() != null && llave.length() > 1 && n.emptyExcept(llave.charAt(llave.length()-1))){
            n = n.getPapa();
            n.hijos[n.pos(llave.charAt(llave.length()-1))] = null;
            llave = llave.substring(0,llave.length()-1);
        }
        if (n.getPapa() == null && n.emptyExcept(llave.charAt(0)))
            raiz.setSig(null);
    }
    

    
    public boolean isEmpty(){
        if (raiz.getSig() == null)
            return true;
        else
            return false;
    }
    
    public NodoTrie getRaiz(){
        return raiz.getSig();
    }
    
    public String imprime(){
        StringBuilder res = new StringBuilder();
        imprime(raiz.getSig(), res, "");
        return res.toString();
    }
    
    private void imprime(NodoTrie<T> n, StringBuilder res, String actual){
        int i;
        for (i = 0; i < n.cont; i++){
            res.append(actual).append("\n");
        }
        for (i = 0; i < simbolos.length; i++){
            if (n.hijos[i] != null)
                imprime(n.hijos[i], res, actual + simbolos[i]);
        }
    }
    
    
    
    
}