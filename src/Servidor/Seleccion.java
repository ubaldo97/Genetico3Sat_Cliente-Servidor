/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.util.Random;
import Herramientas.Individuo;
import Herramientas.Poblacion;
/**
 *
 * @author ruben
 */
public class Seleccion {
     public static Individuo seleccionAleatoria(Poblacion pob) {
        Random ran = new Random();
        return pob.getIndividuos().get(ran.nextInt(pob.getIndividuos().size()));

    }
      
      public static Individuo seleccionRuleta(Poblacion pob){
      
        double pos = pob.getFitnessPoblacion()*Math.random();
        double suma=0;
        // ahora costruimos la ruleta
        for (Individuo ind: pob.getIndividuos()){
          suma += ind.getFitness();
          if(suma>=pos){
            return new Individuo(ind);
          }
        }
        return new Individuo(pob.getIndividuos().get(0));
    }

    public static Individuo seleccionTorneoMax(Poblacion pob){
      if (pob.getMayor() ==null || pob.getMenor()==null){
       pob.calcularMayorMenor();
      }
      return pob.getMayor();
    }
    
    
    
}
