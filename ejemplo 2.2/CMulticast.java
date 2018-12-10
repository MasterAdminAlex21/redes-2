import java.io.*;
import java.net.*;

public class CMulticast{//cliente multicast
 public static void main(String[] args) {
  InetAddress gpo=null;
  try{
   MulticastSocket cl=new MulticastSocket(9999);
   System.out.println("Cliente escuchando al puerto "+cl.getLocalPort());
   cl.setReuseAddress(true);
   try{
    gpo=InetAddress.getByName("228.1.1.1");
   }catch(UnknownHostException ue){
    System.err.println("Direccion no valida");
   }//fin catch gpo
   cl.joinGroup(gpo);
   System.out.println("Unido al grupo");
   for(;;){
    DatagramPacket p=new DatagramPacket(new byte[10],10);
    cl.receive(p);
    String msj=new String(p.getData());
    System.out.println("Datagrama Recibido...\n \""+msj+"\"");
    System.out.println("Servidor descubierto: "+p.getAddress()+":"+p.getPort());
   }//fin for
  }catch(Exception e){
   e.printStackTrace();
  }//fin catch
 }//fin main
}//fin clase