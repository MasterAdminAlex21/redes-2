import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.io.*;
import javax.swing.JFileChooser;

public class cliente{
 public static void main(String[] args) {
  String host=(args.length<1)?null:args[0];
  try{
   Registry registry=LocateRegistry.getRegistry(host);
   JFileChooser jf=new JFileChooser();
   int r=jf.showOpenDialog(null);
   if(r==JFileChooser.APPROVE_OPTION){
    File f=jf.getSelectedFile();
    String nombre=f.getName();
    String path=f.getAbsolutePath();
    busca busq=(busca)registry.lookup("busquedaArc");
   // boolean exisMD5=busq.busquedaMD5(createChecksum(nombre));
    boolean exisArc=busq.busquedaArc(nombre,MD5Checksum.createChecksum(path));
    if(exisArc){
     System.out.println("Existe el archivo");
    }else{
     System.out.println("No existe archivo");
    }
   }//fin if


  }catch(Exception e){
   e.printStackTrace();
  }//fin catch
 }//fin main
}//fin clase