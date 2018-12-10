package servidor;
import java.net.*;
import java.io.*;
import java.util.*;

public class conexion extends Thread{
 private Socket s;
 private int id_cli,con;
 private DataInputStream dis;
 private DataOutputStream dos;
 private String[] posi;
 private boolean ready=false;
 private String coord;
 
 public conexion(Socket sock,int id){
  s=sock;
  id_cli=id;
  try{
   dis=new DataInputStream(s.getInputStream());
   dos=new DataOutputStream(s.getOutputStream());
   String msg=dis.readUTF();
   //dis.flush();
   System.out.println(msg);
   dos.writeUTF("aceptado");
   dos.flush();
  
  }catch(Exception e){
   e.printStackTrace();
  }
 }

 public boolean listo(){
  return ready;
 }

 public String[] posis(){
  return posi;
 }

 public void enviar(String env){
  try{
    dos.writeUTF(env);
    dos.flush();
  }catch(Exception e){

  }
 }

 public void desconectar(){
  try{
   s.close();
  }catch(Exception e){
   e.printStackTrace();
  }
 }

 public String recibe(){
  try{
    return dis.readUTF();
  }catch(Exception e){
    e.printStackTrace();
    return null;
  }
 }



 @Override
 public void run(){
  try{
     posi=new String[4];
   for(int i=0;i<4;i++){
    posi[i]=dis.readUTF();
    //dis.flush();
   }
   System.out.println("hilo "+id_cli+" con coord: \n"+posi[0]+"\n"+posi[1]+"\n"+posi[2]+"\n"+posi[3]+"\n");
   ready=true;
  }catch(Exception e){
  }
 }
}