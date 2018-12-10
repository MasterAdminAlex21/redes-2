//cliente
package Cliente;

import producto.producto;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

public class conectar{

 public static void main(String[] args) {
  producto p;
   try{
    Socket cl=new Socket("127.0.0.1",9090);
    System.out.println("conexion establecida...");
    ArrayList<producto> tienda=new ArrayList<producto>();
    ObjectInputStream ois=new ObjectInputStream(cl.getInputStream());
    DataInputStream dis=new DataInputStream(cl.getInputStream());
    int tam=dis.readInt(),n;
    //String img=dis.readLine();
    //System.out.println(tam+"\naqui recibe los objetos");
    DataOutputStream dos;
    String name;
    long tam1,recv;
    File f=new File("Cliente/imgns");
    if(!f.isDirectory()){
     f.mkdir();
    }
    for(int i=0;i<tam;i++){
      p=(producto)ois.readObject();
      tienda.add(p);
      System.out.println(p.obtNombre()+" recibido");
      //recibo las imgns
      recv=0;
      name="Cliente/imgns/"+dis.readUTF();
      tam1=dis.readLong();
      dos=new DataOutputStream(new FileOutputStream(name));
      byte[] b=new byte[1024];
      while(recv<tam1){
        n=dis.read(b);
        dos.write(b,0,n);
        dos.flush();
        recv=recv+n;
      }

    }
   
    new cliente(cl,tienda).setVisible(true);
       
   }catch(Exception ex){
    //aqui va el aviso queno encontro el servidor
     JOptionPane.showMessageDialog(new JPanel(),"Servidor no encontrado","Error",JOptionPane.ERROR_MESSAGE);
  }
 }
}