//cliente.java
import java.util.Random;

class cliente extends Thread{
 private restaurante r;
 private int idCliente;
 private static final Random aleatorio=new Random();

 public cliente(restaurante r,int idCliente){
  this.r=r;
  this.idCliente=idCliente;
 }

 public void run(){
  r.obtenerMesa(idCliente);
  try{
   //come durante 1-30 seg
   int tiempoComida=aleatorio.nextInt(30)+1;
   System.out.println("Cliente #"+idCliente+" comera por "+tiempoComida+" segundos");
   Thread.sleep(tiempoComida*1000);
   System.out.println("cliente #"+idCliente+" termino de comer");
  }catch(InterruptedException ie){
   ie.printStackTrace();
  }finally{
   r.regresaMesa(idCliente);
  }
 }


}