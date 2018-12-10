//envia datos

#include<stdio.h>
#include<stdlib.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<string.h>
#include<netdb.h>
#include<unistd.h>
#include<netinet/in.h>
#include<arpa/inet.h>

int main(int argc,char **argv){
 char host[20],pto[5];
 printf("Escribe la direccion del servidor: ");
 fgets(host,sizeof(host),stdin);
 fflush(stdin);
 printf("Escribe la direccion del puerto: ");
 fgets(pto,sizeof(pto),stdin);
 fflush(stdin);
 int status;
 struct addrinfo hints;
 struct addrinfo *servinfo;
 memset(&hints,0,sizeof(hints));
 hints.ai_family=AF_UNSPEC;
 hints.ai_socktype=SOCK_STREAM;
 status=getaddrinfo(host,pto,&hints,&servinfo);
 int cd=socket(servinfo->ai_family,servinfo->ai_socktype,servinfo->ai_protocol);
 FILE *f=fopen(cd,"w+");
 if(connect(cd,servinfo->ai_addr,servinfo->ai_addrlen)<0){
  perror("Error en funcion connect\n");
 }

 freeaddrinfo(servinfo);
 int dato1=6;
 long dato2=70;
 float dato3=3.0f;
 char dato4[]="un mensaje";
 int n;
 //dato 1
 n=write(cd,&dato1,sizeof(dato1));
 if(n<0){
  perror("Error de escritura");
 }else if(n==0){
  perror("socket cerrado; error de escritura\n");
 }else{
  fflush(f);
 }
 printf("Se envio el dato: %d\n",dato1);

 //dato2
 n=write(cd,&dato2,sizeof(dato2));
 if(n<0){
  perror("Error de escritura");
 }else if(n==0){
  perror("socket cerrado; error de escritura\n");
 }else{
  fflush(f);
 }
 printf("Se envio el dato: %ld\n",dato2);

 //dato 3
 char datos[20];
 sprintf(datos,"%f",dato3);
 datos[strlen(datos)]='\0';
 n=write(cd,&dato3,sizeof(dato3));
 if(n<0){
  perror("Error de escritura");
 }else if(n==0){
  perror("socket cerrado; error de escritura\n");
 }else{
  fflush(f);
 }
 printf("Se envio el dato: %s\n",dato3);

 //dato 4
 n=write(cd,&dato4,strlen(dato4));
 if(n<0){
  perror("Error de escritura");
 }else if(n==0){
  perror("socket cerrado; error de escritura\n");
 }else{
  fflush(f);
 }
 printf("Se envio el dato: %s\n",dato4);

 close(cd);
 fclose(f);
 return 0;

}//fin main