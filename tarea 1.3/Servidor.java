import java.net.*;
import java.io.*;

public class Servidor
{
	public static void main(String[] args)
	{
		try
		{
			ServerSocket s =  new ServerSocket(7000);
			s.setReuseAddress(false);
			System.out.println("Esperando Cliente...");
			for(;;)
			{
				Socket cl = s.accept();
				System.out.println("Conexión establecida desde " + cl.getInetAddress() + ":" + cl.getPort());

				//
				DataInputStream total=new DataInputStream(cl.getInputStream());
				long tot=total.readLong();
				total.close();

				DataInputStream dis = new DataInputStream(cl.getInputStream());


				for(int i=0;i<tot;i++){
				//
				byte[] b = new byte[1024];
				String nombre = dis.readUTF();
				System.out.println("Recibimos el archivo " + nombre+"\n");
				long tam = dis.readLong();
				DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));
				long recibidos = 0;
				int n, porcentaje;
				while(recibidos < tam)
				{
					n = dis.read(b);
					dos.write(b,0,n);
					dos.flush();
					recibidos = recibidos + n;
					porcentaje = (int)(recibidos*100/tam);
					System.out.print("Recibido " + porcentaje + "%\r");
				}
				System.out.println("\n\nArchivo Recibido\n");
				dos.close();
			 }
				
				dis.close();
				
				cl.close();
				System.out.println("conexion cerrada\n");

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}