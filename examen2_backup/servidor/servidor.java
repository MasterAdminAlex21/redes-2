package servidor;
import java.io.*;
import java.net.*;

public class servidor{//servidor multicast
 public static void main(String[] args) {
  try{
    ServerSocket s=new ServerSocket(9999);
    s.setReuseAddress(true);
    InetAddress ip=InetAddress.getLocalHost();
    multicast m=new multicast(""+ip);

    m.start();
    int conec=0;
   System.out.println("ya creo el multicast");
   conexion[] cliente=new conexion[2];
   while(true){
     //aqui hace las conexiones
    
    Socket cl=s.accept();
    if(conec<2){
      System.out.println("Cliente "+cl.getInetAddress()+" aceptado");
      //conec+=1;
      //conexion cliente=new conexion(cl,conec);
      cliente[conec]=new conexion(cl,conec);
      cliente[conec].start();
      conec+=1;
    }else{
      //aqui enviar un msj para que no se conecte mas clientes
      System.out.println("ya conecto los 2 clientes");
      DataOutputStream os=new DataOutputStream(cl.getOutputStream());
      os.writeUTF("Ya no hay lugar");
      cl.close();

      //y que empieze el juego uwu
    }
    if(conec==2){
      //aqui la comunicacion con los clientes
      m.suspend();
      cliente[0].enviar("listo");
      cliente[1].enviar("listo");
      while(!cliente[0].listo() || !cliente[1].listo()){
        System.out.println("aun no estan listos: j0: "+cliente[0].listo()+" j1: "+cliente[1].listo());
      }
      String[] j1=cliente[0].posis();//new String[4];//cliente[0]
      String[] j2=cliente[1].posis();//new String[4];//cliente[1]

      System.out.println("jugadores listos");
      String coord,val="";
      cliente[0].enviar("1");
      cliente[1].enviar("0");

      for(int i=0;i<4;i++){
        System.out.println("jugador 1: "+j1[i]);
      }

      for(int i=0;i<4;i++){
        System.out.println("jugador 2: "+j2[i]);
      }

      while(true){
        //turno cliente 0
        
        coord=cliente[0].recibe();
        System.out.println(" jugador 1\ncoordenada: "+coord);
        for(int i=0;i<4;i++){
          if(j2[i].contains(coord)){
            j2[i]=j2[i].replace(coord,"");
            val="X";
            break;
          }else{
            val="0";
          }
        }
        System.out.println("val: "+val);
        int cont=0;
        for(int i=0;i<4;i++){
          System.out.println("jugador 1: "+j1[i]);
          if(j2[i].equals(" ") || j2[i].equals("  ")){
            cont+=1;
          }
        }
        if(cont==4){
          //coord="perdio";
          cliente[1].enviar("perdio");
          cliente[1].enviar(val);

          cliente[0].enviar(val);
          cliente[0].enviar("gano");
          cliente[0].enviar(val);
          m.resume();
          break;
        }
        cliente[1].enviar(coord);
        cliente[1].enviar(val);

        cliente[0].enviar("0");
        cliente[1].enviar("1");
        
        cliente[0].enviar(val);

        //turno cliente 1
        coord=cliente[1].recibe();
        System.out.println(" jugador 2\ncoordenada: "+coord);
        for(int i=0;i<4;i++){
          if(j1[i].contains(coord)){
            j1[i]=j1[i].replace(coord,"");
            val="X";
            break;
          }else{
            val="0";
          }
        }
        System.out.println("val: "+val);
        int cont1=0;
        for(int i=0;i<4;i++){
          System.out.println("jugador 2: "+j2[i]);
          if(j2[i].equals(" ")|| j2[i].equals("  ")){
            cont1+=1;
          }
        }
        if(cont1==4){
          //coord="perdio";
          cliente[0].enviar("perdio");
          cliente[0].enviar(val);

          cliente[1].enviar(val);
          cliente[1].enviar("gano");
          cliente[1].enviar(val);
          m.resume();
          break;
        }
        cliente[0].enviar(coord);
        cliente[0].enviar(val);

        cliente[0].enviar("1");
        cliente[1].enviar("0");

        cliente[1].enviar(val);
      }




    }
   }
  }catch(Exception e){
    e.printStackTrace();
  } 
 }//fin main
}//fin clase