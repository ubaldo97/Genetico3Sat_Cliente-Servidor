/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Cliente.Individuo;
import Cliente.Poblacion;
import Herramientas.Conexion;
import Herramientas.Mascaras;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ruben
 */
public class Servidor extends Conexion {
    public Servidor() throws IOException{
        super("servidor");
    }
    
    public static void main(String args[]) throws IOException, ClassNotFoundException{
         Servidor serv = new Servidor();
         serv.iniciarServidor();
    }
    public void iniciarServidor() throws ClassNotFoundException{
     try {
            
            cs = ss.accept();
            //Recibir la probabilidad de muta           
             InputStream is = cs.getInputStream();
                ObjectInputStream entrada= new ObjectInputStream(is);
                Double probMuta = (Double) entrada.readObject();
            
                 //Recibir el numero de generaciones        
             InputStream is2 = cs.getInputStream();
                ObjectInputStream entrada2= new ObjectInputStream(is2);
                int numGeneraciones = (int) entrada2.readObject();
          
            
            for(int i=0;i<numGeneraciones;i++){
                //Recibir la cantidad de muestreo
                InputStream is3 = cs.getInputStream();
                ObjectInputStream entrada3 = new ObjectInputStream(is3);
                int cantidadM = (int) entrada3.readObject();
                
                //Recibir la poblacion del cliente
                InputStream is4 = cs.getInputStream();
                ObjectInputStream entrada4 = new ObjectInputStream(is4);
                Poblacion p = (Poblacion) entrada4.readObject();
               
                int[] mask = Mascaras.generarMascaraAleatoria(100);
                for(int j=cantidadM;j<p.getIndividuos().size();j++){
                    
                  Individuo madre = Seleccion.seleccionTorneoMax(p);
                  Individuo padre = Seleccion.seleccionAleatoria(p);
                     // cruza
                    Individuo nuevoi = new Individuo(); 
                        //    nuevoi= Cruza.cruzaBinaria(mask,madre,padre);
//                     // muta (evaluar la probabilidad)
//                    if(Math.random()<=probMuta){
//                      Muta.mutaAleatoria(nuevoi);
//                       }
                         //Enviar un objet          
                salidaCliente= new ObjectOutputStream(cs.getOutputStream());   
                salidaCliente.writeObject(nuevoi);
                }
                    
                }
             ss.close();
             cs.close();
            
            } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
                        
        
    }
    
    
}
