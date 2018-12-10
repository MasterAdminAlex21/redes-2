package cliente;
import java.io.*;
import java.net.*;

public class cliente{
 public static void main(String[] args) {
  if(args.length!=1){
   System.err.println("Uso: java cliente numcliente/nomcliente");
   System.exit(0);
  }
  InetAddress gpo=null;
  try{
   MulticastSocket cl=new MulticastSocket(9875);
   cl.setReuseAddress(true);
   try{
    gpo=InetAddress.getByName("228.1.1.1");
   }catch(UnknownHostException ue){
    System.err.println("Servidor inalcanzable");
    System.exit(0);
   }
   cl.joinGroup(gpo);
   System.out.println("Unido al grupo");
   String conec=args[0];
   byte[] b=new byte[1024];
   b=conec.getBytes();
   DatagramPacket p;
   p=new DatagramPacket(b,b.length,gpo,9875);
   cl.send(p);
   for(;;){
    cl.receive(p);
    conec=new String(p.getData());
    String[] conectados=conec.split(" ");
    if(conectados.length<3 && conectados.length>1){
    if(conectados[1].contains(args[0])){
     System.out.println("anterior: "+conectados[0]+"\nsiguiente: "+conectados[2]);
    }
    try{
     Runtime.getRuntime().exec("clear");
    }catch(IOException eio){
     System.err.println("No se ejecuto clear");
    }
   }
  }
  }catch(Exception e){
   e.printStackTrace();
  }
 }
}