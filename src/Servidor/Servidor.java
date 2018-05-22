/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import Cliente.Individuo;
import Cliente.Poblacion;
import Herramientas.Conexion;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
            //Recibir un objeto del cliente
                InputStream is = cs.getInputStream();
                ObjectInputStream entrada = new ObjectInputStream(is);
                Poblacion p = (Poblacion) entrada.readObject();
           
                //Enviar un objeto
                String saludo = "hola cliente";
                salidaCliente= new ObjectOutputStream(cs.getOutputStream());   
                salidaCliente.writeObject(saludo);
           
                
                
                
            ss.close();
            cs.close();
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
