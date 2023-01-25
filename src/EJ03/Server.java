/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EJ03;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bpiris
 */
//AÑADIMOS THREAD PARA FUNCIONAR CON HILOS
public class Server extends Thread {
    
    //VARIABLES DEL SERVIDOR
    static int PORT=1500;
    static private Socket socket;
    static int idUser=0;
    static String USER="user";
     static String PASSWORD="pass";
    
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
    public void run(){
        try {
           
           //VARIABLE BOOLEANA PARA SALIR DEL WHILE Y CERRAR CONEXION CON SOCKET
            Boolean finish=false;
            
            //OBTENEMOS EL INPUT Y EL OUTPUT DEL SOCKET
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out= new DataOutputStream(socket.getOutputStream()); 
            
           //PEDIMOS USUARIO Y CONTRASEÑA
            out.writeUTF("INTRODUCE USUARIO");
            String user=in.readUTF();
            out.writeUTF("INTRODUCE CONTRASEÑA");
            String password=in.readUTF();
            
          
          //COMPROBAMOS USUARIO Y CONTRASEÑA  
            if(user.equals(USER) && password.equals(PASSWORD)){
                
                //MOSTRAMOS MENÚ
                out.writeBoolean(true);
                out.writeUTF("SELECCIONA UNA DE ESTAS OPCIONES: \n 1.Ver directorio actual \n 2.Leer archivo \n 3.Salir");
                
                int op=in.readInt();
                
                //OPCION 1
                if(op==1){
                     File directorio=new File ("./Directorio");
                     File[] arrayFicheros=directorio.listFiles();
                     
                 String stringFicheros="";
                   
                     
                     for(int i=0;i<arrayFicheros.length;i++){
                         if(arrayFicheros[i].isFile()){
                           stringFicheros+=arrayFicheros[i].getName()+"\n";
                         }
                    
                    }
                     
                     out.writeUTF(stringFicheros); 
                }else 
                    
                   //OPCION 2
                if(op==2){
                     out.writeUTF("Introduce Archivo a leer");
                     String archivo=in.readUTF();
                     
                     
                            //CREAMOS UN OBJETO FILE CON LA RUTA
                   File f=new File("Directorio/"+archivo);
                   System.out.println(f.getAbsolutePath());

                   //SI EXISTE EL ARCHIVO, LE LEEMOS Y MANDAMOS LOS BYTES AL CLIENTE
                   //SI NO EXISTE, DEVOLVEMOS UN BOOLEANO DE QUE NO EXISTE
                   if (f.exists()){
                       out.writeBoolean(true);
                       BufferedReader br=new BufferedReader(new FileReader("Directorio/"+archivo));
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
                }else
                    
                    //OPCION 3
                 if(op==3){
                 System.out.println("Cliente "+idUser+" desconectado");
                 }
                
            

              
            
           }else{
                out.writeBoolean(false);
            }
            
           
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

}
