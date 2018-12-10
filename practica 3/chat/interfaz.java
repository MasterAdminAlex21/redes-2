//interfaz chat
import java.io.*;
import java.net.*; 

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.lang.*;
import java.awt.event.*;

public class interfaz extends JFrame{

 private JTextArea chat;
 private JLabel conectados,lbprivado;
 private JComboBox msgprivado;
 private JTextArea msg;
 private JRadioButton privado;
 private JButton enviar;

 public interfaz{
  setTitle("Chat");
  setSize(500,500);
  setLocation(150,100);
  setDefaultCloseOperation(EXIT_ON_CLOSE);
  setResizable(false);

  //se crean los componentes
  
 }//fin constructor interfaz

}//fin clase
