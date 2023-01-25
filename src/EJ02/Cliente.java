/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EJ02;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author bpiris
 */
public class Cliente  {
    
    static String host="localhost";
    static int port=1500;
     
      public static void main (String[] args) throws IOException{

           //VARIABLE SCANNER PARA RECOGER POR PANTALLA EL VALOR
           Scanner sc=new Scanner(System.in);
           
          //NOS CONECTAMOS AL SOCKET
           Socket socket=new Socket(host,port);
           
           //OBTENEMOS EL INPUT Y EL OUTPUT DEL SOCKET
           DataInputStream in= new DataInputStream(socket.getInputStream());
           DataOutputStream out= new DataOutputStream(socket.getOutputStream());
           
           sc.useDelimiter("\n");
           
           System.out.println("Introduce la ruta del archivo");
           
           String path=sc.next();
           out.writeUTF(path);
           
           boolean exists=in.readBoolean();
           
           if(exists){
               
               int longFile=in.readInt();
               byte[] content=new byte[longFile];
               
               for(int i=0;i<longFile;i++){
                   content[i]=in.readByte();
                }
               
               String contentFile=new String(content);
               
               System.out.println(contentFile);
                
               
           }else{
               System.out.println("No se ha encontrado el archivo");
           }
  
           //CERRAMOS EL SCANNER Y LA CONEXION CON EL SOCKET      
           sc.close();
           socket.close();
      
    }
}
