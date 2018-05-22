/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Herramientas;
import java.io.*;
import java.nio.*;
import java.net.*;
/**
 *
 * @author ubaldo
 */
public class Conexion {
    private final int puerto = 20011;
    private String host ="localhost";
    protected ServerSocket ss;
    protected String mensaje;
    protected Socket cs;
    protected ObjectOutputStream salidaServidor;
    protected ObjectOutputStream salidaCliente;

    public Conexion(String a)throws IOException {
        if(a.equalsIgnoreCase("servidor"))
        {
            ss = new ServerSocket(puerto);
            cs = new Socket(); 
        }
        else
        {
            cs = new Socket(host, puerto); 
        }
    }
    
    
    
}
