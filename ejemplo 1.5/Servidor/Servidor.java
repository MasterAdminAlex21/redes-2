//datos primitivos

import java.net.*;
import java.io.*;

public class Servidor{
 public static void main(String[] args){
  try{
   DatagramSocket s = new DatagramSocket();
   System.out.println("Servidor iniciado. Esperando cliente...");
   for(;;){
    DatagramPacket p = new DatagramPacket();
    s.receive();
    System.out.println("Datagrama recibido desde "+p.getAddress()+": "+p.getPort());
    DataInputStream dis = new DataInputStream(new ByteArrayInputStream(p.getData()));
    int x=dis.readInt();
    float f=dis.readFloat();
    long z=dis.readLong();
    System.out.println("\n\nEntero: "+x+"Flotante: "+f+"Entero largo: "+z);
   }
   //s.close();
  }catch(Exception e){
   e.printStackTrace();
  }
 }
}