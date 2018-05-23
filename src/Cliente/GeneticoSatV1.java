/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Herramientas.Mascaras;
import Herramientas.Grafica;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Herramientas.Herramientas;

import Herramientas.Conexion;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author ruben
 */
public class GeneticoSatV1 extends Conexion{
     private int tamPob;
    private Double probMuta;
    private int numGeneraciones;
    private Poblacion pobActual;
    private int porMuestra;

  
    
    public GeneticoSatV1(int tamPob, Double probMuta, int numGeneraciones) throws IOException {
        super("cliente");  
        this.tamPob = tamPob;
        this.probMuta = probMuta;
        this.numGeneraciones = numGeneraciones;
        this.pobActual = new Poblacion(tamPob);
        this.porMuestra = 20;
            
    }
     
    
       public static void main(String args[]) throws IOException{
         try {
             Individuo.clausulas=Herramientas.leeArchivo();
           
             
             
             GeneticoSatV1 gen = new GeneticoSatV1(65,0.38,1000);
             gen.evolucionar();
         } catch (ClassNotFoundException ex) {
             Logger.getLogger(GeneticoSatV1.class.getName()).log(Level.SEVERE, null, ex);
         }
        
            
    }
    
    public void evolucionar() throws IOException, ClassNotFoundException{
            //enviar las clausulas para que el servidor pueda trabajar
               ArrayList<Integer[]> cl = new ArrayList();
             cl = Individuo.clausulas;
         salidaServidor= new ObjectOutputStream(cs.getOutputStream());     
         salidaServidor.writeObject(cl);
       
            Poblacion nuevaPoblacion;
            this.pobActual.calcularMayorMenor();
            Individuo mejor = this.pobActual.getMayor();          
            ArrayList<Integer> datosG = new ArrayList<>();
            
             //Enviar la probabilidad de muta
         salidaServidor= new ObjectOutputStream(cs.getOutputStream());     
         salidaServidor.writeObject(probMuta);
            
         //Enviar el numero de generaciones
         salidaServidor= new ObjectOutputStream(cs.getOutputStream());     
         salidaServidor.writeObject(numGeneraciones);
            
            
// agregar el ciclo para las generaciones
for(int g=0; g<this.numGeneraciones;g++){
    // proceso iterativo de construccion de la
    // nueva población
    nuevaPoblacion = new Poblacion();
    // generar el muestreo
    int cantidadM = (int)(this.tamPob*this.porMuestra/100);
    nuevaPoblacion.recibirMuestra(this.pobActual.generarGrupoAleatorio(cantidadM));
    
     //Enviar la cantidad de muestreo
         salidaServidor= new ObjectOutputStream(cs.getOutputStream());     
         salidaServidor.writeObject(cantidadM);
    
    
      //Enviar la población actual
         salidaServidor= new ObjectOutputStream(cs.getOutputStream());     
         salidaServidor.writeObject(pobActual);
         
    for(int i=cantidadM;i<this.tamPob;i++){
             
          //Recibir un objeto del servidor
                InputStream is = cs.getInputStream();
                ObjectInputStream entrada = new ObjectInputStream(is);
                Individuo nuevoi = (Individuo) entrada.readObject();      
        // agregamos el individuo a la nueva poblacion
        nuevaPoblacion.getIndividuos().add(nuevoi);
    }
    // actualizamos la población actual
    
    this.pobActual = new Poblacion(nuevaPoblacion);
   // if (this.pobActual.getMayor().getFitness()>mejor.getFitness()) {
        mejor = this.pobActual.getMayor();
        datosG.add(mejor.getFitness());
    //}
    System.out.println("Mejor "+g+": "+this.pobActual.getMayor().getFitness());
    
} 
  //cs.close();      
System.out.println("Mejor mejor: "+mejor.getFitness());

         try {
             Herramientas.guardarMejorIndividuo(mejor);
         } catch (IOException ex) {
             Logger.getLogger(GeneticoSatV1.class.getName()).log(Level.SEVERE, null, ex);
         }
    Grafica grafica = new Grafica("Generación","Fitness","Fit");
    grafica.agregarSerie( "fit",datosG);
    grafica.crearYmostrarGrafica();
        
       
       }

    /**
     * @param porMuestra the porMuestra to set
     */
    public void setPorMuestra(int porMuestra) {
        this.porMuestra = porMuestra;
    }
}
