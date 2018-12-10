//carrito para modificar y terminar compra
package Cliente;

import producto.producto;

import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

import java.net.*;
import java.io.*;
import java.util.*;

public class carrito extends JFrame{
 private JTextArea lista;
 private JButton modificar;
 private JButton finalizar;
 private JTextArea ncant;
 private JComboBox<String> nprod,prod1;
 private JButton aceptar;
 private JTextField quitar;
 private JLabel quitcant;
 private JScrollPane barra;

 private ArrayList<producto> carro;
 private ArrayList<producto> tienda1;
 private Container pane;
 private Socket clien;

 public carrito(ArrayList<producto> c, ArrayList<producto> tien,JComboBox<String> productos,Socket cli){
  clien=cli;
  prod1=productos;
  carro=c;
  tienda1=tien;
  setTitle("Modificar carrito");
  setSize(600,480);
  setLocation(400,150);
  setDefaultCloseOperation(DISPOSE_ON_CLOSE);

  //se crean lo componentes
  lista=new JTextArea();
  lista.setSize(350,350);
  lista.setEditable(false);

  modificar=new JButton("Modificar");
  modificar.setSize(100,20);

  finalizar=new JButton("Finalizar");
  finalizar.setSize(100,20);

  //auxiliares para modificar
  ncant=new JTextArea();
  ncant.setSize(50,20);

  nprod=new JComboBox<String>();
  nprod.setVisible(false);
  nprod.setSize(200,20);

  quitcant=new JLabel("Cantidad:");
  quitcant.setSize(80,20);
  quitcant.setVisible(false);

  quitar=new JTextField();
  quitar.setSize(30,20);
  quitar.setVisible(false);
  //agregar los elementos

  aceptar=new JButton("Eliminar");
  aceptar.setSize(100,20);
  aceptar.setVisible(false);

  barra=new JScrollPane(lista,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  barra.setBounds(new java.awt.Rectangle(5,5,350,350));

  //se agrega el listener
  modify m=new modify();
  modificar.addActionListener(m);

  acep ac1=new acep();
  aceptar.addActionListener(ac1);

  fin end=new fin();
  finalizar.addActionListener(end);

  //se crea el contenedor
  pane=getContentPane();
  pane.setLayout(new GroupLayout(pane));

  //se agregan lo componentes
  pane.add(lista).setLocation(10,40);
  pane.add(finalizar).setLocation(20,400);
  pane.add(modificar).setLocation(170,400);
  pane.add(nprod).setLocation(380,50);
  pane.add(aceptar).setLocation(380,150);
  pane.add(quitcant).setLocation(380,80);
  pane.add(quitar).setLocation(450,80);

  //se imprime la lista
  String list="";
  double total=0;
    for(int i=0;i<carro.size();i++){
      total+=carro.get(i).obtPrecio()*carro.get(i).obtCant();
    list=list+carro.get(i).obtNombre()+"    Cantidad:"+carro.get(i).obtCant()+"    Costo: $"+(carro.get(i).obtPrecio()*carro.get(i).obtCant())+"\n";
       }
       list=list+"\n\nTotal: $"+total;
   lista.setText(list);

 }

 class modify implements ActionListener{
  public void actionPerformed(ActionEvent ev3){
   
   nprod.setEnabled(true);
   nprod.setVisible(true);
   aceptar.setVisible(true);
   quitar.setVisible(true);
   quitcant.setVisible(true);

   for(int i=0;i<carro.size();i++){
    nprod.addItem(carro.get(i).obtNombre());
   }
  }//fin action performed
 }//fin clase action listener

 class acep implements ActionListener{
  public void actionPerformed(ActionEvent ev4){
   int pos1=nprod.getSelectedIndex();//
   producto pr=carro.get(pos1),aux;
   if(Integer.parseInt(quitar.getText())>pr.obtCant() || quitar.getText().equals("") || Integer.parseInt(quitar.getText())<0){
    //joption de error
    JOptionPane.showMessageDialog(new JPanel(),"Ingrese la cantidad correcta","ERROR",JOptionPane.ERROR_MESSAGE);
   }else{
    int quitcan=Integer.parseInt(quitar.getText());
    //carro.remove(pos1);
    pr.nuevaCant(pr.obtCant()-quitcan);
    if(pr.obtCant()==0){
      carro.remove(pos1);
    }
    int i;
    nprod.removeAllItems();
    for(i=0;i<carro.size();i++){
     //nprod.removeItemAt(i);
     nprod.addItem(carro.get(i).obtNombre());
     
    }
    
    for(i=0;i<tienda1.size();i++){
     aux=tienda1.get(i);
     if(aux.obtNombre().equals(pr.obtNombre())){
      //tienda1.remove(i);
      //tienda1.add(i,new producto(aux.obtNombre(),aux.obtDesc(),aux.obtCant()+1,aux.obtPrecio(),aux.obtCod()));
      aux.nuevaCant(aux.obtCant()+quitcan);
      break;
     }
    }
/*    if(i==tienda1.size()){
     tienda1.add(pr);
    }*/


    String list="";
    double total=0;
    for(i=0;i<carro.size();i++){
      total+=carro.get(i).obtPrecio()*carro.get(i).obtCant();
    list=list+carro.get(i).obtNombre()+"    Cantidad:"+carro.get(i).obtCant()+"    Costo: $"+(carro.get(i).obtPrecio()*carro.get(i).obtCant())+"\n";
       }
       list=list+"\n\nTotal: $"+total;
   lista.setText(list);
  }
  /*//prod1.removeAllItems();
  for(i=0;i<tienda1.size();i++){
   prod1.removeItemAt(i);
   prod1.addItem(tienda1.get(i).obtNombre());
  }*/
  }//fin action performed
 }//fin clase action listener

 class fin implements ActionListener{
  public void actionPerformed(ActionEvent ev5){
   //aqui la wea magica para finalizar y enviar al servidor los productos
   try{
    ObjectOutputStream oos=new ObjectOutputStream(clien.getOutputStream());
    DataOutputStream dos=new DataOutputStream(clien.getOutputStream());
    dos.writeInt(tienda1.size());
    dos.flush();
    producto pro1;
    for(int x=0;x<tienda1.size();x++){
     //aqui regreso los objetos restantes, ya actualizados
     pro1=tienda1.get(x);
     oos.writeObject(pro1);
     oos.flush();
    }
    dos.close();
    clien.close();

    //generamos el pdf
    String ticket="";
    String ruta="../../../ticket";
    try{
      FileOutputStream arch=new FileOutputStream(ruta+".pdf");
      Document doc=new Document();
      PdfWriter.getInstance(doc,arch);
      doc.open();
      doc.add(new Paragraph(lista.getText()));
      doc.close();
      JOptionPane.showMessageDialog(new JPanel(),"Ticket Generado","Ticket",JOptionPane.INFORMATION_MESSAGE);
    }catch(Exception er1){
      JOptionPane.showMessageDialog(new JPanel(),"Ticket no Generado","Ticket",JOptionPane.INFORMATION_MESSAGE);
    }
    System.exit(1);
   }catch(Exception ex1){
    ex1.printStackTrace();
   }
  }//fin action performed
 }//fin clase avtion listener

 /*public static void main(String[] args) {
  new carrito().setVisible(true)
; }*/
}