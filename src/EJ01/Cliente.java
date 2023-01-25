/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EJ01;

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
    static int port=2000;
     
      public static void main (String[] args) throws IOException{
          
          //VARIABLE BOOLEANA PARA SALIR DEL WHILE
          boolean finish=false;
          
          //VARIABLE STRING PARA RECOGER LOS MENSAJES DEL SERVIDOR
           String text;
           
           //VARIABLE SCANNER PARA RECOGER POR PANTALLA EL VALOR
           Scanner sc=new Scanner(System.in);
           
          //NOS CONECTAMOS AL SOCKET
           Socket socket=new Socket(host,port);
           
           //OBTENEMOS EL INPUT Y EL OUTPUT DEL SOCKET
           DataInputStream in= new DataInputStream(socket.getInputStream());
           DataOutputStream out= new DataOutputStream(socket.getOutputStream());
  
          //SE REPETIRÁ EL PROCESO MIENTRAS QUE EL SERVIDOR NO NOS DEVUELVA UN 
          //TRUE COMO RESPUESTA
           do{
           //RECOGEMOS Y MOSTRAMOS EL MENSAJE ENVIADO POR EL SERVIDOR
           text= in.readUTF();
           System.out.println(text);
           
           //INTRODUCIMOS EL NUMERO Y SE LO ENVIAMOS AL SERVIDOR
           
            while (!sc.hasNextInt()) {
                System.out.println(text);
                sc.next(); 
           }
           int num=sc.nextInt();
           out.writeInt(num);
           
           //RECOGEMOS Y MOSTRAMOS LA RESUESTA DEL SERVIDOR
           text= in.readUTF();
           System.out.println(text);
           
           //RECOGEMOS EL BOOLEANO CON EL RESULTADO DEL SERVIDOR
           finish=in.readBoolean();
           
           }while(!finish);
           
           //CERRAMOS EL SCANNER Y LA CONEXION CON EL SOCKET      
           sc.close();
           socket.close();
      
    }
}
