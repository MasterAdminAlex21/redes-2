import java.io.*;
import java.net.*;

public class CMulticast{//cliente multicast
 public static void main(String[] args) {
  if(args.length<2){
    System.err.println("Uso: "+args[0]+" numero_cliente");
    System.exit(0);
  }
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

    msj="cliente "+args[1];

    byte[] b=msj.getBytes();
    p=new DatagramPacket(b,b.length,gpo,cl.getLocalPort());
    cl.send(p);

    try{
      Thread.sleep(500);
    }catch(InterruptedException er){
      er.printStackTrace();
    }

    //aqui inyectamos el mensaje
   }//fin for
  }catch(Exception e){
   e.printStackTrace();
  }//fin catch
 }//fin main
}//fin clase