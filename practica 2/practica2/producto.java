//clase producto;
package practica2;

import java.io.*;

public class producto implements Serializable{//productos de limpieza
 private String nombre,descripcion;
 private int cantidad;
 private double precio;
 private String codigo;
 private String rutaimgn;

 public producto(String n,String desc, int cant,double prec,String cod){
  nombre=n;
  descripcion=desc;
  cantidad=cant;
  precio=prec;
  codigo=cod;
//agregar la ruta de la imgn;
 }

 public String obtNombre(){
  return nombre;
 }

 public String obtDesc(){
  return descripcion;
 }

 public int obtCant(){
  return cantidad;
 }

 public double obtPrecio(){
  return precio;
 }

 public String obtCod(){
  return codigo;
 }

 public void setImg(String path){
  rutaimgn=path;
 }
 public String obtImg(){
  return rutaimgn;
 }
}