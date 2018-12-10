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
   /*byte[] b=mensaje.getBytes();
   String dst="127.0.0.1";
   int port=2000;
   DatagramPacket p = new DatagramPacket(b,b.length,InetAddress.getByName(dst),port);
   cl.send(p);*/
   String dst="127.0.0.1";
   DatagramPacket p;
   int port=2000;
   byte[] b;
   //aqui empieza mi desmadre
   int tam=mensaje.length();
   byte[] tama=Integer.toString(tam).getBytes();
   p=new DatagramPacket(tama,tama.length,InetAddress.getByName(dst),port);
   cl.send(p);
   if(tam>20){
    int i=0;
    String temp;
    while(i+19<tam){
     temp=mensaje.substring(i,i+19);
     b=temp.getBytes();
     p=new DatagramPacket(b,b.length,InetAddress.getByName(dst),port);
     i+=19;
     cl.send(p);
    }
    temp=mensaje.substring(i,i+(mensaje.length() - i));
     b=temp.getBytes();
     p=new DatagramPacket(b,b.length,InetAddress.getByName(dst),port);
     cl.send(p);
   }else{
    b=mensaje.getBytes();
    p=new DatagramPacket(b,b.length,InetAddress.getByName(dst),port);
    cl.send(p);
   }
   System.out.println("Mensaje enviado\n");

   //aqui termina

   //ahora aqui recibe el mensaje
   mensaje="";
   String aux;
   p=new DatagramPacket(new byte[20],20);
   if(tam>20){
    int i=0;
    while(i+19<tam){
      cl.receive(p);
      aux=new String(p.getData(),0,19);
      mensaje=mensaje+aux;
      i+=19;
    }
    cl.receive(p);
    aux=new String(p.getData(),0,p.getLength());
    mensaje=mensaje+aux;
   }else{
    cl.receive(p);
    mensaje=new String(p.getData(),0,p.getLength());
   }

   System.out.println("\n\nMensaje de eco recibido:\n"+mensaje+"\n\n");
   /*p=new DatagramPacket(new byte[20],20);
   cl.receive(p)
   mensaje="";
   tam=Integer.parseInt(new String(p.getData(),0,p.length()));//*/
   //y aqui termina

  /* cl.receive(p);
   String mens=new String(p.getData(),0,p.getLength());
   System.out.println("msg: "+mens);*/
  }catch(Exception e){
   e.printStackTrace();
  }
 }
}