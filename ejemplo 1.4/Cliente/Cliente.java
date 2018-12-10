import java.net.*;
import java.io.*;

//cliente de eco con datagramas
public class Cliente{
 public static void main(String[] args){
  try{
   DatagramSocket cl = new DatagramSocket();
   System.out.println("Cliente iniciado. Escriba el mensaje: ");
   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   String mensaje=br.readLine();
   byte[] b=mensaje.getBytes();
   String dst="127.0.0.1";
   int port=2000;
   DatagramPacket p = new DatagramPacket(b,b.length,InetAddress.getByName(dst),port);
   cl.send(p);
  }catch(Exception e){
   e.printStackTrace();
  }
 }
}