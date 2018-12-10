import java.net.*;
import java.io.*;
//eco con datagramas
public class Servidor{
 public static void main(String[] args){
  try{
   DatagramSocket s = new DatagramSocket(2000);
   System.out.println("Servidor iniciado: esperando...");
   for(;;){
    DatagramPacket p = new DatagramPacket(new byte[2000],2000);
    s.receive(p);
    System.out.println("Datagrama recibido desde: "+p.getAddress()+": "+p.getPort());
    String msj=new String(p.getData(),0,p.getLength());
    System.out.println("Con el mensaje: "+msj);
   }
  }catch(Exception e){
   e.printStackTrace();
  }
 }
}