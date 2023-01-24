/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EJ01;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
public class Server extends Thread{
    
    //VARIABLES DEL SERVIDOR
    static int PORT=2000;
    static private Socket socket;
    static int idUser=0;
    
    Server(Socket socket){
        this.socket=socket;
    }

    public static void main (String[] args) throws IOException{
        
   
        //INICIALIZAMOS EL SOCKET
        Socket newSocket;
        ServerSocket server=new ServerSocket(PORT);
        System.out.println("Servidor Iniciado");
       
        //SI ESTA DISPONIBLE EL SERVICIO DEL SOCKET, PERMITIRÁ ACCESO A NUEVO CLIENTE
        //A LA APLICACION DEL SERVER
        while(true){
        newSocket=server.accept();
        new Server(newSocket).start();
        idUser++;
        System.out.println("Cliente "+idUser+" conectado");
        }
        
    }
  
    //METODO QUE SE EJECUTARÁ TODA LA APLICACION DEL SERVIDOR
    public  void run(){
        try {
            
           //VARIABLE BOOLEANA PARA SALIR DEL WHILE Y CERRAR CONEXION CON SOCKET
            Boolean finish=false;
            
            //OBTENEMOS EL INPUT Y EL OUTPUT DEL SOCKET
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out= new DataOutputStream(socket.getOutputStream()); 
          
            //GENERAMOS UN NUMERO RANDOM DE 0 100
            Random rm=new Random();
            int randomNumber=rm.nextInt(100);
            System.out.println("User "+idUser+ " Número Aleatorio: "+randomNumber);
          
          
            //SE REPETIRÁ EL PROCESO MIENTRAS QUE EL CLIENTE NO NOS INDIQUE EL 
            //NÚMERO CORRECTO
            do{
                //ENVIAMOS AL CLIENTE EL MENSAJE INFORMATIVO
                out.writeUTF("User "+idUser+", introduce un número entre 1 y 100");
                
                //RECOGEMOS EL VALOR DEL CLIENTE Y LO MOSTRAMOS
                int num=in.readInt();
                System.out.println("User "+idUser+" Número introducido: "+num);
                
                
                //SI EL NUMERO NO ES IGUAL, INDICAREMOS SI ES MAYOR O MENOR
                
                if(num!=randomNumber){
                   if(num<randomNumber){
                        out.writeUTF("El numero insertado es Menor");
                   }else{
                        out.writeUTF("El numero insertado es Mayor");
                   }
                   
                //SI EL NUMERO ES EL MISMO, DEVOLVEMOS EL MENSAJE 
                //Y CAMBIAMOS EL BOOLEANO A TRUE
                
                }else{
                    out.writeUTF("El numero insertado es Igual");
                    finish=true; 
                }
                //ENVIAMOS AL CLIENTE EL VALOR DEL BOOLEANO
                
               out.writeBoolean(finish);
               
            }while(!finish);
            
            //CUANDO ACABE EL PROGRAMA, CERRAMOS EL SOCKET
            
            //socket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

}
