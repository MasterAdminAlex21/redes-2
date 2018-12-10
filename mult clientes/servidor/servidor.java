package servidor;

import java.io.*;
import java.net.*;
import java.util.*;

public class servidor{
 public static void main(String[] args) {
  if(args.length!=1){
   System.out.println("Uso: java servidor <numero_clientes>");
   System.exit(0);
  }
  try{
   ServerSocket s=new ServerSocket(9999);
   s.setReuseAddress(true);
   System.out.println("Servidor listo...");
   int num_cli=Integer.parseInt(args[0]);
   int id=0;
   String arch;
   hilo[] cliente=new hilo[num_cli];
   while(true){
    if(id==num_cli){
     for(int i=0;i<num_cli;i++){
      while(cliente[i].isAlive()){
      }
     }
     arch=cliente[0].getNom();
     DataOutputStream dos=new DataOutputStream(new FileOutputStream("./servidor/"+arch));
     id=0;
     byte[] b=new byte[1];
     for(int i=0;i<num_cli;i++){
      File temp=new File("./servidor/"+arch+".part"+(i+1));
      DataInputStream leer=new DataInputStream(new FileInputStream("./servidor/"+arch+".part"+(i+1)));
      System.out.println("archivo abierto: servidor/"+arch+".part"+(i+1));
      boolean eof=false;
      long n=temp.length();
      int cont=0,c=0;
      while(cont<n){
        c=leer.read(b);
        dos.write(b);
        dos.flush();
        cont+=c;
      }
       leer.close();
       System.out.println("\nCierra el archivo");
       String comando="rm ./servidor/"+arch+".part"+(i+1);
       Runtime.getRuntime().exec(comando);
      }
      dos.close();
     }
    Socket cl=s.accept();
    id+=1;
    cliente[id-1]=new hilo(cl,id,num_cli);
    cliente[id-1].start();
    System.out.println("Cliente aceptado");


   }//fin while   
  }catch(Exception e){
   e.printStackTrace();
  }//fin catch
 }
}