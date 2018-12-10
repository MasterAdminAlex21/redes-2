import java.io.*;
import java.net.*;
public class ex{

 public static void main(String[] args){

  String msg="Hola";

  int port=2010;

  try{

   InetAddress dst=InetAddress.getByName("148.204.56.130");

   DatagramSocket cl=new DatagramSocket();

   byte[] b=msg.getBytes();

   DatagramPacket d=new DatagramPacket(b,b.length,dst,port);

   cl.send(d);

   cl.close();
   

  }catch(Exception e){

   e.printStackTrace();

  }

 }

}