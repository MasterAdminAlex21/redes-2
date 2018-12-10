//serializacion
package ejemplo16;
import java.io.Serializable;
public class Usuario implements Serializable{
 
  String nombre,apaterno,amaterno;
  transient String pwd;
  int edad;

  public Usuario(String nombre, String apaterno, String amaterno, String pwd, int edad){
   this.nombre=nombre;
   this.apaterno=apaterno;
   this.amaterno=amaterno;
   this.pwd=pwd;
   this.edad=edad;
  }

  public String getNombre(){
   return nombre;
  }

  public String getApaterno(){
   return apaterno;
  }

  public String getAmaterno(){
   return amaterno;
  }

  public String getPwd(){
   return pwd;
  }

  public int getEdad(){
   return edad;
  }

}