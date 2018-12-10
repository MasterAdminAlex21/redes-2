//cliente.java
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class cliente{
 private cliente(){}
  public static void main(String[] args) {
   String host=(args.length<1)?null: args[0];
   try{
    Registry registry=LocateRegistry.getRegistry(host);
    //tambien se puede usar getRegistry(String host,int port)
    Suma stub=(Suma)registry.lookup("Suma");
    int x=5,y=4;
    int response=stub.suma(x,y);
    System.out.println("respuesta suma: "+x+" y "+y+" es "+response);
   }catch(Exception e){e.printStackTrace();}
  }
 
}