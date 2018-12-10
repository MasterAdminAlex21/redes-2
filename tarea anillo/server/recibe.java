package server;
import java.io.*;
import java.net.*;
import java.util.*;

public class recibe extends Thread{

 private InetAddress gpo;
 private MulticastSocket s;
 private ArrayList<String>  con;
 private String cl;
 private DatagramPacket p;
 private byte[] b;

 public recibe(InetAddress g,MulticastSocket ser,ArrayList<String> lista){
  gpo=g;
  s=ser;
  con=lista;
 }

 @Override
 public void run(){
  try{
    for(;;){
     //System.out.println("tamaño array: "+con.size());
     DatagramPacket p=new DatagramPacket(new byte[1024],1024);
     s.receive(p);
     System.out.println("Cliente agregado");
     cl=new String(p.getData());
     con.add(cl);

     if(con.size()!=0){
     b=new byte[1024];
     if(con.size()==1){
      cl="unico cliente conectado: "+con.get(0);
      b=cl.getBytes();
      p=new DatagramPacket(b,b.length,gpo,9876);
      s.send(p);
     }else{
      for(int i=0;i<con.size();i++){
       if(i==0){
        cl=con.get(con.size()-1)+" "+con.get(i)+" "+con.get(i+1);
       }else if(i==con.size()-1){
        cl=con.get(i-1)+" "+con.get(i)+" "+con.get(0);
       }else{
        cl=con.get(i-1)+" "+con.get(i)+" "+con.get(i+1);
       }
       b=cl.getBytes();
       p=new DatagramPacket(b,b.length,gpo,9876);
       s.send(p);
      }
     }
       System.out.println("mensaje enviado: "+cl);
   }
 }
  }catch(Exception e){
    e.printStackTrace();
  }
 }
}