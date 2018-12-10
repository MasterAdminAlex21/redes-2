//interfaz
package practica1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

import java.net.*;
import java.io.*;
import java.util.*;//este es para el array list

public class jcliente extends JFrame/* implements ActionListener{//*/{

 //componentes de la ventana del cliente
 private JLabel nombre;//,dir,pto;
 private JTextArea text;
 private JButton seleccionar;
 private JButton enviar,salir;
 private JScrollPane barra;
 private JLabel bytestransf,name;
 private JTextField vel;//,direc,portserv;
 private JComboBox<String> cantbytes,byterate;
 private JCheckBox nable;
 

 //attributos para que jale el cliente
 private String host;
 private String archivos="";
 private ArrayList<String> paths=new ArrayList<String>();
 private ArrayList<String> nombres=new ArrayList<String>();
 private Socket cl;
 private int puerto;
 private File[] f;
 private Font pers;
 private int bytesenv;
 private DataOutputStream dos;
 private ProgressMonitor pm;


 public jcliente(){
  try{
    cl=new Socket("127.0.0.1",7000);
    dos = new DataOutputStream(cl.getOutputStream());
  }catch(Exception ex){
    JOptionPane.showMessageDialog(new JPanel(),"Servidor no encontrado","Error",JOptionPane.ERROR_MESSAGE);
    System.exit(0);
  }
   setTitle("Cliente de archivos");
   setSize(620, 400);
   setLocation(370,200);
   setDefaultCloseOperation(EXIT_ON_CLOSE);
   setResizable(false);

   //se crean los componentes
   //pers=new Font("artwiz snap",Font.ITALIC,16);

   text=new JTextArea();
   text.setEditable(false);
   text.setSize(400, 200);

   seleccionar=new JButton("Seleccionar archivos");
   seleccionar.setSize(180, 25);

   enviar=new JButton("Enviar archivos");
   enviar.setSize(160, 25);
   enviar.setEnabled(false);

   bytestransf= new JLabel("ByteRate");
   bytestransf.setSize(100,25);

   barra=new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
   barra.setBounds(new Rectangle(10,10,400,200));

   cantbytes=new JComboBox<String>();
   cantbytes.setSize(50, 20);
   cantbytes.addItem("1");
   cantbytes.addItem("2");
   cantbytes.addItem("4");
   cantbytes.addItem("8");
   cantbytes.addItem("16");

   byterate=new JComboBox<String>();
   byterate.setSize(50, 20);
   byterate.addItem("KB");
   byterate.addItem("MB");

   nable=new JCheckBox("Algoritmo de Nable");
   nable.setSize(180,20);

   salir=new JButton("Salir");
   salir.setSize(80,20);

   //se crea el panel
   Container pane=getContentPane();
   pane.setLayout(new GroupLayout(pane));

   //se agrega el listener
   abrir a=new abrir();
   seleccionar.addActionListener(a);

   //enviardatos en=new enviardatos();
   //enviar.addActionListener(en);
   work w1=new work();
   enviar.addActionListener(w1);

   selec sel=new selec();
   byterate.addActionListener(sel);

   evcb check=new evcb();
   nable.addActionListener(check);

   cerrar cer= new cerrar();
   salir.addActionListener(cer);

   //se agregan los componentes
   pane.add(barra).setLocation(10,10);
   pane.add(seleccionar).setLocation(420,20);
   pane.add(enviar).setLocation(220,220);
   pane.add(bytestransf).setLocation(425,160);
   pane.add(cantbytes).setLocation(425,190);
   pane.add(byterate).setLocation(485,190);
   pane.add(nable).setLocation(425,100);
   pane.add(salir).setLocation(20,220);
 }

 class selec implements ActionListener{
  public void actionPerformed(ActionEvent e){
    if(byterate.getSelectedIndex()==1){
      cantbytes.removeAllItems();
      cantbytes.addItem("1");
      cantbytes.addItem("2");
      cantbytes.addItem("4");
    }else{
      cantbytes.addItem("8");
      cantbytes.addItem("16");
    }
  }
 }//clase del combobox

 class abrir implements ActionListener{
   public void actionPerformed(ActionEvent e){
    JFileChooser jf = new JFileChooser();
    jf.setMultiSelectionEnabled(true);
    int r=jf.showOpenDialog(null);
    if(r==JFileChooser.APPROVE_OPTION){
      f=jf.getSelectedFiles();
      for(int i=0;i<f.length;i++){
       archivos=archivos + f[i].getName() + "\n";
       paths.add(f[i].getAbsolutePath());
       nombres.add(f[i].getName());
      }
      text.setText(archivos);
      for(int a=0;a<paths.size();a++){
      System.out.println(paths.get(a));}
      
    }
    enviar.setEnabled(true);
   }
 }//clase del action listener

 class enviardatos implements ActionListener{
   public void actionPerformed(ActionEvent e){
      //aqui empieza aenviar las weas esas
      enviar();
   }
 }//clase del action listener

 class evcb implements ActionListener{
  public void actionPerformed(ActionEvent evt){
    try{
        DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
        if(nable.isSelected()){
          cl.setTcpNoDelay(true);
          dos.writeInt(0);
          dos.flush();
        }
        if(!nable.isSelected()){
          cl.setTcpNoDelay(false);
          dos.writeInt(1);
          dos.flush();
        }
      }catch(Exception ex1){
        ex1.printStackTrace();
        JOptionPane.showMessageDialog(new JPanel(),"Error al habilitar o inhabilitar TcpNoDelay","ERROR",JOptionPane.WARNING_MESSAGE);
      }
  }
 }//fin evcb

 class cerrar implements ActionListener{
  public void actionPerformed(ActionEvent evc){
    //dos.close();
    try{
      dos.writeInt(-1);
      dos.flush();
      dos.writeInt(0);
      dos.flush();
        cl.close();
        System.exit(0);
      }catch(Exception exc){

      }
  }
 }//din action listener

 //

 //el worker
 class work implements ActionListener{
  public void actionPerformed(ActionEvent evw){
    final SwingWorker w=new SwingWorker(){
      protected Object doInBackground() throws Exception{
        enviar();
        return null;
      }
    };
    w.execute();
  }
 }

 public void enviar(){
  try{
   /*BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
   Socket cl=new Socket(h,p);
   DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
   //*/
   dos.writeInt(-1);
   dos.flush();

   dos.writeInt(1);
   dos.flush();
   //
   long cant=paths.size();
   dos.writeLong(cant);
   dos.flush();
   bytesenv=Integer.parseInt(cantbytes.getSelectedItem().toString());
   bytesenv*=1024;
   if(byterate.getSelectedIndex()==1){
    bytesenv*=10;
   }
   dos.writeInt(bytesenv);
   dos.flush();
   DataInputStream dis;
   for(int i=0;i<(int)cant;i++){
    String archivo = paths.get(i);
    String nombre=nombres.get(i);
    File f1=new File(paths.get(i));
    long tam =f1.length();
     //aqui a ver si funciona una barra de progreso

     pm=new ProgressMonitor(null,"enviando ","enviando",0,100);
    
    //
    dis = new DataInputStream(new FileInputStream(archivo));
    dos.writeUTF(nombre);
    dos.flush();
    dos.writeLong(tam);
    dos.flush();
    byte[] b = new byte[bytesenv];
    long enviados = 0;
    int porcentaje=0, n;
   
    while(enviados < tam)
    {
     n = dis.read(b);
     dos.write(b,0,n);
     dos.flush();
     enviados = enviados + n;
     porcentaje = (int)(enviados*100/tam);
     //porc.setText(nombre+"\r: "+porcentaje+"\r%");
     //bar.setValue(porcentaje);
     monitor(nombre,porcentaje);
    }

    dis.close();
    //JOptionPane.showMessageDialog(new JPanel(),"Archivo "+nombres.get(i)+" enviado","Enviado",JOptionPane.INFORMATION_MESSAGE);

    try{
      Thread.sleep(1000);
    }catch(InterruptedException err){
      err.printStackTrace();
    }
   }
   //dos.close();
   //br.close();
   //cl.close();z
   archivos="";
   paths.clear();
   nombres.clear();
   text.setText("");
   enviar.setEnabled(false);
   JOptionPane.showMessageDialog(new JPanel(),"Archivos enviados","Enviado",JOptionPane.INFORMATION_MESSAGE);
  }catch(Exception e){
    JOptionPane.showMessageDialog(new JPanel(),"Servidor no encontrado","Error",JOptionPane.ERROR_MESSAGE);
    //e.printStackTrace();
  }
 }

 private void monitor(String nombre, int porcentaje){
    pm.setNote(nombre);
    pm.setProgress(porcentaje);
 }



 public static void main(String[] args){
  new jcliente().setVisible(true);
 }

}