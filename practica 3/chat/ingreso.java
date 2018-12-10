//ventana de ingreso
package chat;
import java.io.*;
import java.net.*;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.lang.*;
import java.awt.event.*;

public class ingreso extends JFrame{
 private JButton aceptar;
 private JTextField nick;
 private JLabel nick1;
 private InetAddress gpo;
 private MulticastSocket cl;

 public ingreso(){
  setTitle("Registro");
  setSize(300,100);
  setLocation(450,300);
  setDefaultCloseOperation(EXIT_ON_CLOSE);
  setResizable(false);

  //se crean los componentes
  aceptar=new JButton("Aceptar");
  aceptar.setSize(100,20);

  nick1=new JLabel("Nickname:");
  nick1.setSize(80,20);

  nick=new JTextField();
  nick.setSize(150,20);

  //se crea el contenedor
  Container pane=getContentPane();
  pane.setLayout(new GroupLayout(pane));

  //se agregan los componentes
  pane.add(nick1).setLocation(20,20);
  pane.add(nick).setLocation(110,20);
  pane.add(aceptar).setLocation(100,50);

 }//fin constructor ingreso

 class ing implements ActionListener{
  public void actionPerformed(ActionEvent e1){
   //aqui hacemos la conexion
   gpo=null;
   try{
    gpo=InetAddress.getByName("228.1.1");
   }catch(UnknownHostException ue){
    JOptionPane.showMessageDialog(new JPanel(),"Servidor desconocido","Error",JOptionPane.ERROR_MESSAGE);
   }
   //se oculta esta ventana
   //se llama a interfaz
   
  }//fin actionperformed
 }//fin clase ing

 public static void main(String[] args) {
  new ingreso().setVisible(true);
 }

}//fin clase