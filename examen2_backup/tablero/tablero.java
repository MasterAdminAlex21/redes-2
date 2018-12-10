package tablero;

import java.io.*;
import java.util.*;

public class tablero{
 String[] tab;
 String[] pos;
 ArrayList<String> movs;

 public tablero(){
  tab=new String[16];
  pos=new String[4];
  movs=new ArrayList<>();
  char[] col={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o'};
  tab[0]="  1 2 3 4 5 6 7 8 9 A B C D E F" ;
  for(int i=1;i<16;i++){
   tab[i]=""+col[i-1]+" - - - - - - - - - - - - - - -";
  }
 }

 public void posiciones(){
  System.out.println("Ingrese la posicion de los barcos y presione ENTER: ");
  Scanner leer=new Scanner(System.in);
  //seran 4 barcos, 2 de 3 casillas, y 2 de 2
  System.out.println("Carguero(3 casillas)(inicio medio fin): ");
  pos[0]=leer.nextLine();
  System.out.println("Carguero(3 casillas)(inicio medio fin): ");
  pos[1]=leer.nextLine();
  System.out.println("Submarino(2 casillas)(inicio fin): ");
  pos[2]=leer.nextLine();
  System.out.println("Submarino(2 casillas)(inicio fin): ");
  pos[3]=leer.nextLine();
 }

public String[] retTab(){
 return tab;
}

public String[] posi(){
  return pos;
}

public ArrayList<String> hist(){
  return movs;
}

 public boolean daños(String posi){
  for(int i=0;i<movs.size();i++){
    if(posi.equals(movs.get(i))){
      System.out.println("Elija otra coordenada");
      return true;
    }
  }
  int col=0,fila=0;
  if(posi.contains("a")){
   fila=1;
  }else if(posi.contains("b")){
   fila=2;
  }else if(posi.contains("c")){
   fila=3;
  }else if(posi.contains("d")){
   fila=4;
  }else if(posi.contains("e")){
   fila=5;
  }else if(posi.contains("f")){
   fila=6;
  }else if(posi.contains("g")){
   fila=7;
  }else if(posi.contains("h")){
   fila=8;
  }else if(posi.contains("i")){
   fila=9;
  }else if(posi.contains("j")){
   fila=10;
  }else if(posi.contains("k")){
   fila=11;
  }else if(posi.contains("l")){
   fila=12;
  }else if(posi.contains("m")){
   fila=13;
  }else if(posi.contains("n")){
   fila=14;
  }else if(posi.contains("o")){
   fila=15;
  }

  for(int i=1;i<15;i++){
   if(posi.contains(""+i)){
    col=i;
    break;
   }else if(posi.contains("A")){
    col=10;
    break;
   }else if(posi.contains("B")){
    col=11;
    break;
   }else if(posi.contains("C")){
    col=12;
    break;
   }else if(posi.contains("D")){
    col=13;
    break;
   }else if(posi.contains("E")){
    col=14;
    break;
   }else if(posi.contains("F")){
    col=15;
    break;
   }
  }
  char atk='X';
  for(int i=0;i<4;i++){
   if(pos[i].contains(posi)){
    atk='O';
    pos[i]=pos[i].replace(posi,"");
    break;
   }else{
    atk='X';
   }
  }
  tab[fila]=tab[fila].substring(0,col*2)+atk+tab[fila].substring(col*2+1,31);
  for(int i=0;i<4;i++){
    if(pos[i].equals(" ") || pos[i].equals("  ")){
      pos[i]="destruido";
    }
  }
  int cont=0;
  for(int i=0;i<4;i++){
    if(pos[i].equals("destruido")){
      cont+=1;
    }
  }
  if(cont==4){
    System.out.println("haz perdido");
    return false;
  }
  movs.add(posi);
  return true;
  //return tab;
 }

 public void imptab(){
  for(int i=0;i<16;i++){
    if(i>=1 && i<5){
      System.out.println(tab[i]+"\t"+pos[i-1]);
    }else{
     System.out.println(tab[i]);
    }
  }
 }

 public void daños2(String posi, String val){

  System.out.println("val: "+val+" posi: "+posi);

  int col=0,fila=0;
  if(posi.contains("a")){
   fila=1;
  }else if(posi.contains("b")){
   fila=2;
  }else if(posi.contains("c")){
   fila=3;
  }else if(posi.contains("d")){
   fila=4;
  }else if(posi.contains("e")){
   fila=5;
  }else if(posi.contains("f")){
   fila=6;
  }else if(posi.contains("g")){
   fila=7;
  }else if(posi.contains("h")){
   fila=8;
  }else if(posi.contains("i")){
   fila=9;
  }else if(posi.contains("j")){
   fila=10;
  }else if(posi.contains("k")){
   fila=11;
  }else if(posi.contains("l")){
   fila=12;
  }else if(posi.contains("m")){
   fila=13;
  }else if(posi.contains("n")){
   fila=14;
  }else if(posi.contains("o")){
   fila=15;
  }

  for(int i=1;i<15;i++){
   if(posi.contains(""+i)){
    col=i;
    break;
   }else if(posi.contains("A")){
    col=10;
    break;
   }else if(posi.contains("B")){
    col=11;
    break;
   }else if(posi.contains("C")){
    col=12;
    break;
   }else if(posi.contains("D")){
    col=13;
    break;
   }else if(posi.contains("E")){
    col=14;
    break;
   }else if(posi.contains("F")){
    col=15;
    break;
   }
  }
  tab[fila]=tab[fila].substring(0,col*2)+val+tab[fila].substring(col*2+1,31);
  

 }
}