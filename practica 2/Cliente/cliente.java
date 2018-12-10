//cliente
package Cliente;

import producto.producto;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

public class cliente extends JFrame{
 private JComboBox<String> productos;
 private JLabel cantidad;
 private JLabel prod;
 private JButton comprar;
 private JButton modificar;
 private JLabel cant;
 private JLabel desc;
 private JLabel imgn;
 private JTextField cantcom;
 private JLabel cantcompr;
 private JTextField precio;
 private JScrollPane barra;

 private Socket cli;
 private ArrayList<producto> tienda;
 private ArrayList<producto> comprado;
 private carrito c;
 private Image im;
 private ImageIcon ic;
 private int tamañoTienda;

 public cliente(Socket cl,ArrayList <producto> t){
  cli=cl;
  tienda=t;
  comprado=new ArrayList<producto>();
  tamañoTienda=tienda.size();
  setTitle("Tienda");
  setSize(350,480);
  setLocation(450,150);
  setDefaultCloseOperation(EXIT_ON_CLOSE);
  setResizable(false);

  //se crean los componentes
  productos=new JComboBox<String>();
  productos.setSize(200,20);
  //añadir los elementos
  for(int i=0;i<tienda.size();i++){
    if(tienda.get(i).obtCant()!=0){
     productos.addItem(tienda.get(i).obtNombre());
    }
  }
  cantidad=new JLabel();
  cantidad.setSize(30,20);
  //cantidad.setEditable(false);

  prod=new JLabel();
  prod.setSize(300,30);
  //prod.setEditable(false);

  cant=new JLabel("Disponible");
  cant.setSize(80,20);

  desc=new JLabel("Descripcion");
  desc.setSize(80,20);

  comprar=new JButton("Comprar");
  comprar.setSize(100,20);

  modificar=new JButton("Ver carrito");
  modificar.setSize(120,20);

  imgn=new JLabel();
  imgn.setSize(300,250);

  cantcom=new JTextField();
  cantcom.setSize(30,20);

  cantcompr=new JLabel("Comprar:");
  cantcompr.setSize(100,20);

  precio=new JTextField();
  precio.setSize(60,20);
  precio.setEditable(false);

  barra=new JScrollPane(desc,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  barra.setBounds(new Rectangle(5,5,80,20));

  //se crea el contenedor
  Container pane=getContentPane();
  pane.setLayout(new GroupLayout(pane)); 

  //se agrega el evento
  det datos=new det();
  productos.addActionListener(datos);

  buy comprarP=new buy();
  comprar.addActionListener(comprarP);

  carro ca=new carro();
  modificar.addActionListener(ca);
  
  //se agregan los elementos 
  pane.add(productos).setLocation(20,30);
  pane.add(cantidad).setLocation(265,30);
  pane.add(prod).setLocation(20,350);
  pane.add(cant).setLocation(250,5);
  pane.add(comprar).setLocation(80,400);
  pane.add(modificar).setLocation(200,400);
  pane.add(imgn).setLocation(20,80);
  pane.add(cantcom).setLocation(20,400);
  pane.add(cantcompr).setLocation(5,380);
  pane.add(precio).setLocation(280,200);
 }

 class det implements ActionListener{
  public void actionPerformed(ActionEvent ev){
    producto pr;
    int lugar=productos.getSelectedIndex();
       pr=tienda.get(lugar);  
       //System.out.println("lugar: "+lugar+" producto: "+pr.obtNombre());
       cantidad.setText(""+pr.obtCant());
       prod.setText(pr.obtDesc());
       precio.setText("$"+pr.obtPrecio());
       im=new ImageIcon("Cliente/"+pr.obtImg()).getImage();
       ic=new ImageIcon(im.getScaledInstance(imgn.getHeight(),imgn.getWidth(),Image.SCALE_DEFAULT));
       imgn.setIcon(ic);
  }//fin actionperformed
 }//fin clase listener

 class buy implements ActionListener{
  public void actionPerformed(ActionEvent ev1){
    int pos=productos.getSelectedIndex(),comprados;
    if(cantcom.getText().equals(""))
      comprados=1;
    else
      comprados=Integer.parseInt(cantcom.getText());
    producto comp=tienda.get(pos);
    comprado.add(new producto(comp.obtNombre(),comp.obtDesc(),comprados,comp.obtPrecio(),comp.obtCod()));
    comp.nuevaCant(comp.obtCant()-comprados);
    //cant.setText(""+comp.obtCant());
    /*tienda.remove(pos);
    tienda.add(pos,new producto(comp.obtNombre(),comp.obtDesc(),comp.obtCant()-1,comp.obtPrecio(),comp.obtCod()));
    cantidad.setText(""+tienda.get(pos).obtCant());
    //productos.removeAllItems();
    for(int i=0;i<tienda.size();i++){
     productos.removeItemAt(i);
     productos.addItem(tienda.get(i).obtNombre());
     System.out.println("se actualiza: "+tienda.get(i).obtNombre()+"\n");
    }*/
    System.out.println("Cantidad de productos: "+comprado.size());
    productos.setSelectedIndex(pos);
    cantcom.setText("");
    JOptionPane.showMessageDialog(new JPanel(),"Producto agregado al carrito","Info",JOptionPane.INFORMATION_MESSAGE);
    if(tienda.get(pos).obtCant()==0){
      //tienda.remove(pos);
      productos.removeItemAt(productos.getSelectedIndex());
    }
      
  }//fin action performed
 }//fin clase buy

 class carro implements ActionListener{
  public void actionPerformed(ActionEvent ev2){
    new carrito(comprado,tienda,productos,cli).setVisible(true);
  }//fin action performed
 }//fin clase carro

 /*public static void main(String[] args) { 
  new cliente().setVisible(true);
 }//*/
}