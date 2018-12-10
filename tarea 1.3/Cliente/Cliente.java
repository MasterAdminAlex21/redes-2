import javax.swing.JFileChooser;
import java.net.*;
import java.io.*;

public class Cliente
{
	public static void main(String[] args)
	{
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Escriba la dirección del servidor: ");
			String host = br.readLine();
			System.out.println("Escriba el puerto: ");
			int pto = Integer.parseInt(br.readLine());
			Socket cl = new Socket(host,pto);
			JFileChooser jf = new JFileChooser();
			//
			jf.setMultiSelectionEnabled(true);
			//
			int r = jf.showOpenDialog(null);
			if(r == JFileChooser.APPROVE_OPTION)
			{
				File[] f = jf.getSelectedFiles();
				//
				long cant=f.length;
				System.out.println("archivos a enviar: "+cant);
				DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
				dos.writeLong(cant);
				dos.flush();
				System.out.println("cantidad enviada");
				String nombre;
				//
				for(int i=0;i<(int)cant;i++){
				String archivo = f[i].getAbsolutePath();
				System.out.println("archivo "+i+": "+archivo);
				nombre = f[i].getName();
				System.out.println("archivo "+i+": "+nombre);
				long tam = f[i].length();

				//
				//
				//DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
				DataInputStream dis = new DataInputStream(new FileInputStream(archivo));
				dos.writeUTF(nombre);
				dos.flush();
				dos.writeLong(tam);
				dos.flush();
				byte[] b = new byte[1024];
				long enviados = 0;
				int porcentaje, n;
				while(enviados < tam)
				{
					n = dis.read(b);
					dos.write(b,0,n);
					dos.flush();
					enviados = enviados + n;
					porcentaje = (int)(enviados*100/tam);
					System.out.print("Enviado " + porcentaje + "%\r");
				}
				//dos.close();
				dis.close();
				System.out.println("\narchivo "+nombre+" enviado\n\n");
				//dos.flush();
			}
			dos.close();
		 }
		 //total.close();
			br.close();
			cl.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}