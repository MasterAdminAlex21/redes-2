//servidor serializacion
package ejemplo16;
import java.net.*;
import java.io.*;

public class Servidor{
 public static void main(String[] args){
  ObjectOutputStream oos = null;
  ObjectInputStream ois = null;
  try{
   ServerSocket s = new ServerSocket(9999);
   System.out.println("Servidor iniciado...");
   for(;;){
    Socket cl=s.accept();
    System.out.println("Cliente conectado desde: "+cl.getInetAddress()+": "+cl.getPort());
    oos=new ObjectOutputStream(cl.getOutputStream());
    ois= new ObjectInputStream(cl.getInputStream());
    Usuario u=(Usuario)ois.readObject();
    System.out.println("objeto recibido... obteniendo informacion");
    System.out.println("nombre: "+u.getNombre());
    System.out.println("Apellido paterno: "+u.getApaterno());
    System.out.println("Apellido materno: "+u.getAmaterno());
    System.out.println("Edad: "+u.getEdad());
    System.out.println("Pasword: "+u.getPwd());
    System.out.println("devolviendo objeto...");
    oos.writeObject(u);
    oos.flush();

    oos.close();
    ois.close();
    cl.close();

   }
  }catch(Exception e){
   e.printStackTrace();
  }
 }
}