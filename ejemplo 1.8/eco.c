#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<unistd.h>
#include<arpa/inet.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<netinet/in.h>

int main(int argc, char *argv){
 //aqui vauna madre que noapunte xd :'v
 if(argc<3 || argv>4){
  mensajeFinalError("Uso: EcoTCPCliente <Direccion del servidor> <Palabra de Eco> [<puerto>]");
 }

 char *servIP=argv[1];
 char *cadenaEco=argv[2];



 //crea el socket del cliente tpc
 int s=socket(AF_INET,SOCK_STREAM,IPPROTO_TCP);
 if(s<0){
  mensajeFinalError("Error de apertura del socket");
 }
 //creamos la direccion del servidor de entrada

 struct sockaddr_in dirServ;
 memset(&dirServ,0,sizeof(dirServ));
 dirServ.sin_family=AF_INET;
 int valRet=inet_pton(AF_INET,servIP,&dirServ.sin_addr.s_addr):
 if(valRet==0){
  mensajeFinalError("Direccion del servidor erronea");
 }else if(valRet<0){
  mensajeFinalError("Error en el inet_pton");
 }
 dirServ.sin_port=htons(puerto);
 //establecemos la conexion con el servidor de eco

 if(connect(s,(struct sockaddr*)&dirServ.sizeof(dirServ))<0){
  mensajeFinalError("Error en la conexion");
 }
 size_t longCadenEco=strlen(cadenaEco);

 //envia el mensaje al servidor
 ssize_t numbytes=send(s,cadenaEco,longCadenEco,0);
 if(numbytes<0){
  mensajeFinalError("Fallo envio");
 }else if(numbytes!=longCadenEco){
  mensajeFinalError("Numero de bytes enviados erroneo");
 }

 //recibimos la cadena de vuelta desde el servidor
 unsigned int totalBytesRec=0;
 while(totalBytesRec<longCadenEco){
  char bufer[TAMBUFFER];
  memset(bufer,0,TAMBUFFER);
  numbytes=recv(s,buffer,TAMBUFFER,0);
  if(numbytes<0){
   mensajeFinalError("Recepcion Fallida");
  }else if(numbytes==0){
   mensajeFinalError("Conexion cerrada prematuramente");
  }
  totalBytesRec+=numbytes;
  printf("Recibido %s\n",bufer);
 }
}