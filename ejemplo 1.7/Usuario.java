//externalizable
import java.io.*;
import java.util.*;

class Usuario implements Externalizable{
 private String usuario;
 private String password;

 public Usuario(){
  System.out.println("Creando usuario vacio");
 }

 Usuario(String u, String p){
  System.out.println("Creando usuario "+u+", "+p);
  usuario =u;
  password=p;
 }

 public void writeExternal(ObjectOutput out) throws IOException{
  System.out.print("Usuario.writeExternal");
  //se indica que atributos se almacenan
  out.writeObject(usuario);
 }

 public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException{
  System.out.println("Usuario.readExternal");
  //se indican que atributos se recuperan
  usuario=(String)in.readObject();
 }

 public void muestraUsuario(){
  String cad="Usuario: "+usuario+"\nPassword: ";
  if(password==null){
   cad=cad+"No disponible";
  }else{
   cad=cad+password;
  }
  System.out.println(cad);
 }
}// fin clase usuario

class ListaUsuarios implements Serializable{
 private LinkedList lista=new LinkedList();
 int valor;
 ListaUsuarios(String[] usuarios,String[] passwords){
  for(int i=0;i<usuarios.length;i++){
   lista.add(new Usuario(usuarios[i],passwords[i]));
  }
 }

 public void muestraUsuario(){
  ListIterator li=lista.listIterator();
  Usuario u;
  while(li.hasNext()){
   u=(Usuario)li.next();
   u.muestraUsuario();
  }
 }
}//fin clase listaUsuarios

class DemoExternalizable{
 public static void main(String[] args) throws IOException,ClassNotFoundException{
  System.out.println("creando el objeto");
  String[] usuarios={"A","B","C"};
  String[] passwords={"1","2","3"};
  ListaUsuarios Ip=new ListaUsuarios(usuarios,passwords);
  ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("objetos.out"));
  o.writeObject(Ip);
  o.close();
  System.out.println("\nRecuperando objetos");
  ObjectInputStream in=new ObjectInputStream(new FileInputStream("objetos.out"));
  Ip=(ListaUsuarios)in.readObject();
  Ip.muestraUsuario(); 
  in.close();
 }
}