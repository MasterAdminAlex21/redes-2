import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.JFileChooser;

public class cliente{
 public static void main(String[] args) {
  try{
   System.out.println("ingrese direccion del servidor: ");
   Scanner leer=new Scanner(System.in);
   String serv=leer.nextLine();
   System.out.println("Ingrese puerto: ");
   int port=leer.nextInt();
   Socket cl=new Socket(serv,port);
   JFileChooser jf=new JFileChooser();
   int r=jf.showOpenDialog(null);
   if(r==JFileChooser.APPROVE_OPTION){
    File f=jf.getSelectedFile();
    String archivo=f.getAbsolutePath();
    String nombre=f.getName();
    long tam=f.length();
    DataInputStream dis=new DataInputStream(cl.getInputStream());
    DataInputStream disf=new DataInputStream(new FileInputStream(archivo));
    int num=dis.readInt();
    System.out.println("recibe num_cli: "+num);
    int id=dis.readInt();
    System.out.println("recibe id: "+id);
    long size=tam/num;
    if(id==0) {size-=1;} 
    DataOutputStream dos=new DataOutputStream(cl.getOutputStream());
    dos.writeUTF(nombre);
    dos.flush();
    System.out.println("envia nombre "+nombre);
    dos.writeLong((long)size);
    dos.flush();
    System.out.println("envia size "+(long)size);
    byte[] b=new byte[1];
    long env=0;
    int n=0;
    int pos=(int)(id-1)*(int)(size);
    
    System.out.println("posicion de inicio "+pos);
    while(env<pos){
     n=disf.read(b);
     env+=n;
    }
    int cont=0;
    env=0;

    while(env<=(int)size){
     n=disf.read(b);
    // System.out.print(""+new String(b));
     dos.write(b);
     dos.flush();
     env+=n;
     pos+=n;
     cont+=n;
    }
   // System.out.println("contador "+cont);
    disf.close();
    dis.close();
    dos.close();
    cl.close();
    System.out.println("envio "+env+" datos");
   }//fin if
  }catch(Exception e){
   e.printStackTrace();
  }//fin catch
 }//fin main
}