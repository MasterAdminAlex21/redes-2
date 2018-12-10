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
				DataInputStream dis = new DataInputStream(cl.getInputStream());
				long datos=dis.readLong();
				System.out.println("Archivos a recibir: "+datos);
				String nombre;

				for(int i=0;i<(int)datos;i++){
				//
				//DataInputStream dis = new DataInputStream(cl.getInputStream());
				byte[] b = new byte[1024];
				nombre = dis.readUTF();
				
				System.out.println("Recibimos el archivo " + nombre);
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
				System.out.println("\nArchivo "+nombre+" Recibido\n");
				dos.close();
				//dis.close();
			}
			 dis.close();
				cl.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}