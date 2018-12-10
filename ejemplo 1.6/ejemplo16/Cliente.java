//serializacion
package ejemplo16;
import java.net.*;
import java.io.*;

public class Cliente{
 public static void main(String[] args){
  String host="127.0.0.1";
  int pto=9999;
  ObjectOutputStream oos = null;
  ObjectInputStream ois=null;

  try{
   Socket cl=new Socket(host,pto);
   System.out.println("conexion establecida...");
   oos=new ObjectOutputStream(cl.getOutputStream());
   ois= new ObjectInputStream(cl.getInputStream());
   Usuario u=new Usuario("Pepito","Perez","Juarez","12345",23);
   System.out.println("Enviando objeto...");
   oos.writeObject(u);
   oos.flush();
   System.out.println("Preparado para recibir el objeto...");
   Usuario u2=(Usuario)ois.readObject();
   System.out.println("nombre: "+u2.getNombre());
   System.out.println("Apellido paterno: "+u2.getApaterno());
   System.out.println("Apellido materno: "+u2.getAmaterno());
   System.out.println("Edad: "+u2.getEdad());
   System.out.println("Pasword: "+u2.getPwd());

   oos.close();
   ois.close();
   cl.close();
  }catch(Exception e){
   e.printStackTrace();
  }
 }
}