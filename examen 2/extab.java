import java.io.*;
import java.util.*;
import tablero.*;

public class extab{
 public static void main(String[] args) {
  tablero t=new tablero();
  t.imptab();
  t.posiciones();
  boolean res=true;
  Scanner leer=new Scanner(System.in);
  String coord;
  while(res){
   System.out.println("Ingrese coordenada: ");
   coord=leer.nextLine();
   res=t.daños(coord);
   t.imptab();
  }
  
 }
}