//servidor serializacion
package practica2;

import producto.producto;
import java.net.*;
import java.io.*;
import java.util.*;

public class Servidor{


 public static void main(String[] args) throws FileNotFoundException,IOException{
 	ArrayList<producto> tienda=new ArrayList<producto>();
 	producto p;
 	//String path="imgns/prod";
 	int j=0;
 	String aux;
 	String[] data=new String[6];
 	ArrayList<File> f=new ArrayList<File>();
    ArrayList<String> ignora=new ArrayList<String>();
 	for(int i=1;;i++){
 		j=0;
// 		File arc=new File(".");
// 		String ruta=arc.getCanonicalPath();
// 		System.out.println(ruta);
 		File f1=new File("practica2/tienda/prod"+i+".txt");
 		if(!f1.isFile()){
	  	break;
	    }
	  FileReader fr = new FileReader("practica2/tienda/prod"+i+".txt");
	  BufferedReader br = new BufferedReader(fr);
	  while((aux=br.readLine())!=null){
	  	data[j]=aux;
	  	j+=1;
	  }
      System.out.println("cantidad: "+data[2]);
      //if(Integer.parseInt(data[2])!=0){
	   p=new producto(data[0],data[1],Integer.parseInt(data[2]),Double.parseDouble(data[3]),data[4]);
	   p.setImg(data[5]);
	   tienda.add(p);
       f.add(new File("practica2/"+p.obtImg()));
      //}
	  fr.close();
 	}

 	//empieza lo del servidor

 	ObjectOutputStream oos = null;
  try{
   ServerSocket s = new ServerSocket(9090);
   System.out.println("Servidor iniciado...");
   for(;;){
    Socket cl=s.accept();
    System.out.println("Cliente conectado desde: "+cl.getInetAddress()+": "+cl.getPort());
    oos=new ObjectOutputStream(cl.getOutputStream());
    //ois= new ObjectInputStream(cl.getInputStream());
    //aqui mando los objetos
    DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
    DataInputStream fis;
    dos.writeInt(tienda.size());
    dos.flush();
    /*String imgns="imgns";
    dos.write(imgns.getBytes());
    */
    long tam;
    File imgn;
    for(j=0;j<tienda.size();j++){
    	p=tienda.get(j);
    	oos.writeObject(p);
    	oos.flush();
    	System.out.println("objeto " +j+": "+p.obtNombre()+" cant: "+p.obtCant()+" enviado");
    	//envio la imagen
    	imgn=f.get(j);
    	tam=imgn.length();
    	fis=new DataInputStream(new FileInputStream(imgn.getAbsolutePath()));
    	dos.writeUTF(imgn.getName());
    	dos.flush();
    	dos.writeLong(tam);
    	dos.flush();
    	byte[] b=new byte[1024];
    	long env=0;
    	int n;
    	while(env<tam){
    		n=fis.read(b);
    		dos.write(b,0,n);
    		dos.flush();
    		env=env+n;
    	}
    	Thread.sleep(500);
    }

    System.out.println("Datos enviados\n\n");

    ObjectInputStream ois=new ObjectInputStream(cl.getInputStream());
    DataInputStream dis=new DataInputStream(cl.getInputStream());
    int taml=dis.readInt();

    ArrayList<producto> actualizado=new ArrayList<producto>();
    for(j=0;j<taml;j++){
    	p=(producto)ois.readObject();
    	actualizado.add(p);
    	//System.out.println(p.obtNombre()+"\n"+p.obtDesc()+"\n"+p.obtPrecio()+"\n"+p.obtCant()+"\n"+p.obtCod()+"\n");
    }

    //realiza las actualizaciones

    /*for(j=0;j<tienda.size();j++){
    	p=tienda.get(j);
    	for(int i=0;i<actualizado.size();i++){
    		if(p.obtNombre().equals(actualizado.get(i).obtNombre())){
    			if(p.obtCant()>actualizado.get(j).obtCant()){
    				tienda.remove(j);
    				tienda.add(j,actualizado.get(i));
    				break;
    			}
    		}
    	}
    }*/
    tienda=actualizado;

     /*for(j=0;j<tienda.size();j++){
    	p=tienda.get(j);
    	System.out.println(p.obtNombre()+"\n"+p.obtDesc()+"\n"+p.obtPrecio()+"\n"+p.obtCant()+"\n"+p.obtCod()+"\n");
    }*/

    for(j=0;j<tienda.size();j++){
     FileWriter fw=new FileWriter("practica2/tienda/prod"+(j+1)+".txt");
     p=tienda.get(j);
     fw.write(p.obtNombre()+"\n"+p.obtDesc()+"\n"+p.obtCant()+"\n"+p.obtPrecio()+"\n"+p.obtCod()+"\n"+p.obtImg());
     fw.close();	
    }
    //oos.writeObject(u);
    //oos.flush();

    dos.close();
    oos.close();
    ois.close();
    cl.close();
    System.out.println("Conexion cerrada\nesperando nueva conexion...");

   }
  }catch(Exception e){
   e.printStackTrace();
  }
 	//termina lo del servidor
 }

}

