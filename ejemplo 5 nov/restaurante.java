//restaurante.java
import java.util.concurrent.Semaphore;

public class restaurante{
 private Semaphore mesas;
 public restaurante(int contadorMesas){
  mesas=new Semaphore(contadorMesas);
 }

 public void obtenerMesa(int idCliente){
  try{
   System.out.println("cliente #"+idCliente+" esta intentando obtener una mesa");
   mesas.acquire();
   System.out.println("cliente #"+idCliente+" consigue una mesa");
  }catch(InterruptedException ie){
   ie.printStackTrace();
  }
 }

 public void regresaMesa(int idCliente){
  System.out.println("Cliente #"+idCliente+" devolvio mesa");
  mesas.release();
 } 

 public static void main(String[] args) {
  restaurante r=new restaurante(2);
  for(int i=1;i<5;i++){
   cliente c=new cliente(r,i);
   c.start();
  }
 }


}