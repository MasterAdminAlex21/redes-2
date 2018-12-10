package servidor;
import java.io.*;
import java.net.*;
import java.util.*;

public class multicast extends Thread{
 private InetAddress gpo=null;
 private MulticastSocket s;
 private String direcc;
 //private int pto;
 public multicast(String dir){
  try{
   s=new MulticastSocket(9876);
   s.setReuseAddress(true);
   s.setTimeToLive(1);
   String msj="servidor 1";
   byte[] b=msj.getBytes();
   gpo=InetAddress.getByName("228.1.1.1");
   s.joinGroup(gpo);
   System.out.println("Servidor levantado...");
   direcc=dir;
   //pto=ptoEscucha;
  }catch(Exception e){
   e.printStackTrace();
  }
 }

 @Override
 public void run(){
  try{
   for(;;){
    byte[] b=direcc.getBytes();
    DatagramPacket p=new DatagramPacket(b,b.length,gpo,9876);
    s.send(p);
    try{
      Thread.sleep(5);
    }catch(InterruptedException e){
      e.printStackTrace();
    }
    continue;
   }
  }catch(Exception e){
   e.printStackTrace();
  }
 }
}