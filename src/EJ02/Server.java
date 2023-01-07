/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EJ02;

import EJ01.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bpiris
 */
public class Server {
    
    //VARIABLES DEL SERVIDOR
    static int PORT=1500;
    static private Socket socket;
    static int idUser=0;

    public static void main (String[] args) throws IOException{
        
        //INICIALIZAMOS EL SOCKET
        ServerSocket server=new ServerSocket(PORT);
        System.out.println("Servidor Iniciado");
    
        //SI ESTA DISPONIBLE EL SERVICIO DEL SOCKET, PERMITIRÁ ACCESO A NUEVO CLIENTE
        //A LA APLICACION DEL SERVER
        while(true){
        idUser++;
        socket=server.accept();
        System.out.println("Cliente "+idUser+" conectado");
        serverMethod(idUser);
        }    
    }
  
  
    //METODO QUE SE EJECUTARÁ TODA LA APLICACION DEL SERVIDOR
    public static void serverMethod(int idUser){
        try {
           
           //VARIABLE BOOLEANA PARA SALIR DEL WHILE Y CERRAR CONEXION CON SOCKET
            Boolean finish=false;
            
            //OBTENEMOS EL INPUT Y EL OUTPUT DEL SOCKET
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out= new DataOutputStream(socket.getOutputStream()); 
            
            //RECOGEMOS LA RUTA DEL CLIENTE
            String path=in.readUTF();
            
            //CREAMOS UN OBJETO FILE CON LA RUTA
            File f=new File(path);
            
            //SI EXISTE EL ARCHIVO, LE LEEMOS Y MANDAMOS LOS BYTES AL CLIENTE
            //SI NO EXISTE, DEVOLVEMOS UN BOOLEANO DE QUE NO EXISTE
            if (f.exists()){
                out.writeBoolean(true);
                BufferedReader br=new BufferedReader(new FileReader(path));
                String line="";
                String content="";
                
                while((line=br.readLine())!=null){
                    content+=line;
                }
                
                br.close();
                byte[] contentFile=content.getBytes();
                
                out.writeInt(contentFile.length);
                
                for(int i=0;i<contentFile.length;i++){
                    out.writeByte(contentFile[i]);
                }
                
            }else{
                out.writeBoolean(false);
            }

            socket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

}
