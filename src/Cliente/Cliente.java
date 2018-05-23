/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import Herramientas.Poblacion;
import Herramientas.Individuo;
import Herramientas.Conexion;
import Herramientas.Herramientas;
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
public class Cliente extends Conexion{
     public Cliente() throws IOException{
         super("cliente");
     }
     
        public static void main(String args[]) throws IOException{
            Cliente cli = new Cliente();
            Individuo.clausulas=Herramientas.leeArchivo();
            Poblacion em = new Poblacion(50);
            cli.iniciarCliente(em);
            
    }
     public void iniciarCliente(Poblacion em){
     try {
               
         //ESTE CLIENTE ES DE MENTIRITAS SOLO PARA VERIFICAR QUE SE PUEDAN ENVIAR Y RECIBIR OBJETOS
         //EL VERDADERO CLIENTE DEBE HACERSE EN EL "GeneticoSatV1"
         
         
                //Enviar un objeto al servidor
                salidaServidor= new ObjectOutputStream(cs.getOutputStream());     
                salidaServidor.writeObject(em);
                
                //Recibir un objeto del servidor
                InputStream is = cs.getInputStream();
                ObjectInputStream entrada = new ObjectInputStream(is);
                String p = (String) entrada.readObject();
                System.out.println();
                
                cs.close();
                
                
                
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            } catch (ClassNotFoundException ex) {
             Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
         }
     }   
        
}
