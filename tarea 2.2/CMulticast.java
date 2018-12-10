import java.io.*;
import java.util.*;
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
   //hasta aqui se queda bien
   //y empieza mi desmadre
   Scanner lee=new Scanner(System.in);
   String resp,msj;
   DatagramPacket p=null;
   byte[]b;
   for(;;){
    System.out.println("Desea transmitir un mensaje?(si/no): ");
    resp=lee.nextLine();
    if(resp.toLowerCase().equals("si")){
     System.out.println("Escriba el mensaje: ");
     msj=lee.nextLine();
     b=msj.getBytes();
     p=new DatagramPacket(b,b.length,gpo,9876);
     cl.send(p);
    }//fin if
//    while(p!=null){
     p=new DatagramPacket(new byte[1024],1024);
     cl.receive(p);
     msj=new String(p.getData());
     System.out.println("Datagrama Recibido...\n \""+msj+"\"");
     System.out.println("Servidor descubierto: "+p.getAddress()+":"+p.getPort());
     p=null;
   // }
   }//fin for
  }catch(Exception e){
   e.printStackTrace();
  }//fin catch
 }//fin main
}//fin clase