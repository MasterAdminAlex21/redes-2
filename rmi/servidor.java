//servidor.java
//package servidor;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
//import Suma;

public class servidor implements Suma{
 public servidor(){}

 public int suma(int a,int b){
  return a+b;
 }

 public static void main(String[] args) {
  try{
   java.rmi.registry.LocateRegistry.createRegistry(1099);//puerto por defecto del rmiregistry
   System.out.println("RMI listo");
  }catch(Exception e){
   System.out.println("Excepcion del servidor");
   e.printStackTrace();
  }
  try{
   System.setProperty("java.rmi.server.codebase","file:\\home\\megumi\\redes-2\\rmi\\servidor\\Suma");
   servidor obj=new servidor();
   Suma stub=(Suma)UnicastRemoteObject.exportObject(obj,0);
   Registry registry=LocateRegistry.getRegistry();
   registry.bind("Suma",stub);
   System.err.println("Servidor listo...");
  }catch(Exception e1){
   System.err.println("Excepcion del servidor: "+e1.toString());
   e1.printStackTrace();
  }
 }
}