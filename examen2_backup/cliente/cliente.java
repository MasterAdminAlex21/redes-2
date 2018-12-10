package cliente;

import java.io.*;
import java.util.*;
import java.net.*;
import tablero.*;
//import java.nio.channels.*;
//import java.nio.Bytebuffer;
//import java.util.Iterator;

public class cliente{
 public static void main(String[] args) {
  InetAddress gpo=null;
  try{
   MulticastSocket cl=new MulticastSocket(9876);
   System.out.println("Cliente escuchando por: "+cl.getLocalPort());
   cl.setReuseAddress(true);
   try{
    gpo=InetAddress.getByName("228.1.1.1");
   }catch(UnknownHostException uh){
    System.err.println("Direccion invalida");
   }
   cl.joinGroup(gpo);
   System.out.println("Unido al grupo");
   DatagramPacket p=new DatagramPacket(new byte[25],25);
   cl.receive(p);
   String serv=new String(p.getData());
   System.out.println("Servidor: "+serv);
   String[] aux=serv.split("/");
   cl.close();
   try{
    Socket player=new Socket(aux[1],9999);
    DataOutputStream dos=new DataOutputStream(player.getOutputStream());
    DataInputStream dis=new DataInputStream(player.getInputStream());
    String cone="Conexion del cliente";
    dos.writeUTF(cone);
    dos.flush();
    
    String confir=dis.readUTF();
    if(confir.equals("Ya no hay lugar")){
     System.out.println(confir);
     dos.close();
     dis.close();
     player.close();
    }else{
     while(!(dis.readUTF()).equals("listo"));
     tablero propio=new tablero();
     tablero oponente=new tablero();
     propio.posiciones();
     String[] pos=propio.posi();
     for(int i=0;i<pos.length;i++){
      dos.writeUTF(pos[i]);
      dos.flush();
     }
     Scanner leer=new Scanner(System.in);
     String coord,val,exit;
     while(true){
      //aqui empiezan a jugar
      System.out.println("tablero propio:");
      propio.imptab();

      System.out.println("tablero oponente");
      oponente.imptab();
      while(!(exit=dis.readUTF()).equals("1")){
       if(exit.equals("perdio")){
        System.out.println("Usted ha sido derrotado!!!");
       dis.close();
     dos.close();
     player.close();
       }else if(exit.equals("gano")){
        System.out.println("Usted ha ha ganado'!!!");
       dis.close();
     dos.close();
     player.close();
       }
      }
      System.out.println("Ingrese coordenadas: ");
      coord=leer.nextLine();
      dos.writeUTF(coord);
      dos.flush();
      val=dis.readUTF();
      oponente.daños2(coord,val);
      coord=dis.readUTF();
      val=dis.readUTF();
      System.out.println(coord+" "+val);
      if(coord.equals("gano")){
       System.out.println("Usted ha ganado!!!!");
       break;
/*       dis.close();
       dos.close();
       player.close();*/
      }else if(coord.equals("perdio")){
       System.out.println("Usted ha sido derrotado!!!");
       break;
/*       dis.close();
       dos.close();
       player.close();*/
      }else{
       propio.daños2(coord,val);
      }
     }
     dis.close();
     dos.close();
     player.close();
    }
   }catch(Exception e){e.printStackTrace();}
  }catch(Exception e1){
   e1.printStackTrace();
  }
 }
}