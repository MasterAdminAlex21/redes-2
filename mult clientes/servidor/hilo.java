package servidor;

import java.io.*;
import java.net.*;
import java.util.*;

public class hilo extends Thread{
 private Socket cl;
 private DataInputStream dis;
 private DataOutputStream dos;
 private int id;
 private String nom;
 private boolean recibe;
 private int num_cli;
 public hilo(Socket c,int id_cli, int cli_tot){
  cl=c;
  id=id_cli;
  num_cli=cli_tot;
  try{
   dis=new DataInputStream(cl.getInputStream());
   recibe=false;
  }catch(Exception e){
   e.printStackTrace();
  }//fin catch
 }

 public String getNom(){
  return nom;
 }

 public boolean termino(){
  return recibe;
 }

 @Override
 public void run(){
  try{
   DataOutputStream aux=new DataOutputStream(cl.getOutputStream());
   aux.writeInt(num_cli);
   aux.flush();
   System.out.println("hilo "+id+" envio num_cli");
   aux.writeInt(id);
   aux.flush();
   System.out.println("hilo "+id+" envio id");
   String nombre=dis.readUTF();
   System.out.println("hilo "+id+" recibio nombre "+nombre);
   nom=nombre;
   nombre="./servidor/"+nombre+".part"+id;
   dos=new DataOutputStream(new FileOutputStream(nombre));
   System.out.println("hilo "+id+" crea archivo temporal");
   long tam=dis.readLong();
   System.out.println("hilo "+id+" recibe tamaño "+tam);
   long rec=0;
   int n=0;
   byte[] b=new byte[1];
   while(rec<tam){
    n=dis.read(b);
    dos.write(b);
    dos.flush();
    rec=rec+n;
   }
   System.out.println("hilo "+id+" termina de recibir "+rec+" bytes" );
   dos.close();
   aux.close();
   dis.close();
   cl.close();
   System.out.println("hilo "+id+" terminado");
   recibe=true;
  }catch(Exception e){
   e.printStackTrace();
  }
 }
}