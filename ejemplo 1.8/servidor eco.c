//servidor
#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<sys/types.h>
#include<sys/socket.h>
#include<netinet/in.h>
#include<arpa/inet.h>
#include "MensajeError.c"

#define MAXLISTA 5
#define TAMBUFFER 1024

void manejadorTCPCliente(int);

int main(int argc, char **argv){
 if(argc!=2){//revisa el numero de argumentos
  mensajeFinalError("Uso: EcoTCPServidor [<puerto>]");
 }
 in_port_t prtoServ=atoi(argv[1]); 
 //creamos el socket
 int sockServ;
 if((sockServ=socket(AF_INET,SOCK_STREAM,IPPROTO_TCP))<0){
  mensajeFinalError("Fallo la apertura del socket");
 }
 //se construye la estructura de ladireccion
 struct sockaddr_in dirServ;
 memset(&dirServ,0,sizeof(dirServ));
 dirServ.sin_family=AF_INET;
 dirServ.sin_addr.s_addr =htons(INADDR_ANY);
 dirServ.sin_port=htons(prtoServ);

 if(bind(sockServ,(struct sockaddr*)&dirServ,sizeof(dirServ))<0){
  mensajeFinalError("Error al enlazar");
 }

 if(listen(sockServ,MAXLISTA)<0){
  for(;;){
   struct sockaddr_in dirCliente;//direccion del cliente
   socklen_t dirClienteTam=sizeof(dirCliente);//se obtiene el tamaño de la estructura
   int sockCliente=accept(sockServ,(struct sockaddr*)&dirCliente,&dirClienteTam);
   if(sockCliente<0){
    mensajeFinalError("Fallo la conexion");
   }
   char nombreCliente[INET_ADDRSTRLEN];
   if(inet_ntop(AF_INET,&dirCliente.sin_addr.s_addr,nombreCliente,sizeof(nombreCliente))!=NULL){
    printf("Cliente conectado: %s/%d\n",nombreCliente,ntohs(dirCliente.sin_port) );
   }else{
    puts("Imposible conectar");
   }
   manejadorTCPCliente(sockCliente);
  }
 }

}

void manejadorTCPCliente(int sockCliente){
 char bufer[TAMBUFFER];
 ssize_t numBytesRecibidos=recv(sockCliente,bufer,TAMBUFFER,0);
 if(numBytesRecibidos<0){
  mensajeFinalError("Error en la lectura de datos");
 }
 while(numBytesRecibidos>0){
  ssize_t numBytesEnviados=send(sockCliente,bufer,numBytesRecibidos,0);
  if(numBytesEnviados<0){
   mensajeFinalError("Error de envio");
  }else if(numBytesEnviados==0){
   mensajeFinalError("Numero de bytes enviados erroneo");
  }
  numBytesRecibidos=recv(sockCliente,bufer,TAMBUFFER,0);
  if(numBytesRecibidos<0){
   mensajeFinalError("Error en la lectura de datos recibidos");
  }
 }
 close(sockCliente);
}

