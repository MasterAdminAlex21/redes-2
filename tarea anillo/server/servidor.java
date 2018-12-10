package server;
import java.io.*;
import java.net.*;
import java.util.*;

public class servidor{
 public static void main(String[] args) {
  InetAddress gpo=null;
  try{
   MulticastSocket s=new MulticastSocket(9875);
   s.setReuseAddress(true);
   s.setTimeToLive(1);
   gpo=InetAddress.getByName("228.1.1.1");
   s.joinGroup(gpo);
   ArrayList<String> con=new ArrayList<>();
   recibe r=new recibe(gpo,s,con);
   envia en=new envia(gpo,s,con);

   r.start();
   en.start();
   /*String cl;
   for(;;){
    DatagramPacket p=new DatagramPacket(new Byte[1024],1024);
    s.receive(p);
    System.out.println("Cliente agregado");
    cl=new String(s.getData());
    con.add(cl);


    byte b[1024];
    if(con.size()==1){
     cl="unico cliente conectado: "+cl;
     b=cl.getBytes();
     p=new DatagramPacket(b,b.length,gpo,p.getPort());
     s.send(p)
    }else{
     for(int i=0;i<con.size();i++){
      if(i==0){
       cl=con.get(con.size()-1)+" "+con.get(i)+" "con.get(i+1)
      }else if(i==con.size()-1){
       cl=con.get(i-1)+" "+con.get(i)+" "+con.get(0);
      }
      b=cl.getBytes();
      p=new DatagramPacket(b,b.length,gpo,p.getPort());
      s.send(p);
     }
    }
   }*/
  }catch(Exception e){
   e.printStackTrace();
  }
 }
}