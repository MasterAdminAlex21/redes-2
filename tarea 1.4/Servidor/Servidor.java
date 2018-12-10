import java.net.*;
import java.io.*;
//eco con datagramas
public class Servidor{
 public static void main(String[] args){
  try{
   DatagramSocket s = new DatagramSocket(2000);
   System.out.println("Servidor iniciado: esperando...");
   for(;;){
    DatagramPacket p = new DatagramPacket(new byte[20],20);
    s.receive(p);
    String msj="",aux;
    int tam=Integer.parseInt(new String(p.getData(),0,p.getLength()));
    System.out.println("tamaño msg: "+tam);
    if(tam>20){
      int i=0;
      while(i+19<tam){
        s.receive(p);
        aux=new String(p.getData(),0,19);
        msj=msj+aux;
        i+=19;
      }
      s.receive(p);
      aux=new String(p.getData(),0,p.getLength());
      msj=msj+aux;

    }else{
      s.receive(p);
      msj=new String(p.getData(),0,p.getLength());
    }

    System.out.println("Mensaje recibido desde: "+p.getAddress()+":"+p.getPort()+"\n"+msj);

    //aqui se devuelve el mensaje

    byte[] b;
    int port=p.getPort();
    //b=Integer.toString(tam).getBytes();
    //p=new DatagramPacket(b,b.length,p.getAddress(),port);
    //s.send(p);
    if(tam>20){
     int i=0;
     while(i+19<tam){
      aux=msj.substring(i,i+19);
      b=aux.getBytes();
      p=new DatagramPacket(b,b.length,p.getAddress(),port);
      i+=19;
      s.send(p);
     }
     aux=msj.substring(i,i+(msj.length()-i));
     b=aux.getBytes();
     p=new DatagramPacket(b,b.length,p.getAddress(),port);
     s.send(p);
    }else{
      b=msj.getBytes();
      p=new DatagramPacket(b,b.length,p.getAddress(),port);
      s.send(p);
    }
    System.out.println("\n\nMensaje reenviado al cliente\n");

    //aqui termina la wea
    /*
    System.out.println("Datagrama recibido desde: "+p.getAddress()+": "+p.getPort());
    String msj=new String(p.getData(),0,p.getLength());
    System.out.println("Con el mensaje: "+msj);
    //*/
    
    /*int port=p.getPort(); //descomentar todo esto
    byte[] b=msj.getBytes();
    p = new DatagramPacket(b,b.length,p.getAddress(),port);
    s.send(p);
    */
   }
  }catch(Exception e){
   e.printStackTrace();
  }
 }
}