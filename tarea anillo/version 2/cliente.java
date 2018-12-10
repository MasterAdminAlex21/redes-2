import java.io.*;
import java.net.*;
import java.util.*;

public class cliente{
 public static void main(String[] args) {
  if(args.length !=1){
   System.err.println("Uso: java cliente nombre_cliente");
   System.exit(0);
  }
  InetAddress gpo=null;
  try{
   MulticastSocket cl=new MulticastSocket(9876);
   cl.setReuseAddress(true);
   try{
    gpo=InetAddress.getByName("228.1.1.1");
   }catch(UnknownHostException ue){
    System.err.println("direccion inalcanzable");
    System.exit(0);
   }
   cl.joinGroup(gpo);
   System.out.println("Unido al grupo");
   ArrayList<String> grupo=new ArrayList<>();
   String nombre=args[0];
   byte[] b=new byte[1024];
   b=nombre.getBytes();
   DatagramPacket p;
   p=new DatagramPacket(b,b.length,gpo,9876);
   cl.send(p);
   for(;;){
    cl.receive(p);
    if(grupo.size()!=0){
     for(int i=0;i<grupo.size();i++){
      if(!grupo.get(i).equals(nombre))
       grupo.add(new String(p.getData()));
     }
     System.out.println("Tabla: ");
     for (int i=0;i<grupo.size();i++) {
      System.out.println(""+i+": "+grupo.get(i));
     }
    }else{
     grupo.add(new String(p.getData()));
    }
   }
  }catch(Exception e){
   e.printStackTrace();
  }//fin catch
 }//fin main
}//fin clase