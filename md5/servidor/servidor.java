import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;

public class servidor implements busca{

 public boolean busquedaArc(String archivo,byte[] md5){
  File f=new File(archivo);
  if(f.exists()){//si el archivo existe
   try{
    byte[] md5_actual=MD5Checksum.createChecksum(f.getName());
    String MD5_or=new String(md5);
    String MD5_ac=new String(md5_actual);
    if(MD5_ac.equals(MD5_or)){
     return true;
    }else{
     return false;
    }
   }catch(Exception e1){}
  }else{
   return false;
  }
  return false;
 }//fin busquedaArc

 public static void main(String[] args) {
  try{
   java.rmi.registry.LocateRegistry.createRegistry(1099);
   System.out.println("RMI listo");
  }catch(Exception e){
   System.out.println("Error del servidor");
   e.printStackTrace();
  }//fin catch
  try{
   System.setProperty("java.rmi.server.codebase","file:\\home\\megumi\\md5\\busca");
   servidor obj=new servidor();
   busca bu=(busca)UnicastRemoteObject.exportObject(obj,0);
   Registry reg=LocateRegistry.getRegistry();
   //reg.bind("busquedaMD5",bu);
   reg.bind("busquedaArc",bu);
   System.err.println("servidor listo");
  }catch(Exception e1){
   System.err.println("Error del servidor");
   e1.printStackTrace();
  }//fin catch
 }//fin main
}//fin clase