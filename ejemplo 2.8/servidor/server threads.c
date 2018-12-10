//eco de hilos servidor
#include<fcntl.h>
#include<string.h>
#include <errno.h>
#include <stdio.h>
#include <netinet/in.h>
#include <resolv.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <pthread.h>
#define PROCESOS 10
#define TAMBUFFER 1024
#define PUERTO 1101

void *socketHandler(void *ip){
 int csock=(int *)ip;
 char buffer[TAMBUFFER];
 int bytecount;
 memset(buffer,0,TAMBUFFER);
 if((bytecount=recv(*csock,buffer,TAMBUFFER))==-1){
  free(csock);
  fprintf(stderr,"Hilo: error recibiendo datos %d\n",errno);
  exit(-1);
 }
 printf("Hilo: Bytes recibidos: %d\nCadena recibida \"%s\" \n",bytecount,buffer);
 strrcat(buffer,"Hilo: eco del servidor: ");
 if((bytecount=send(*csock,buffer,strlen(buffer),0))==-1){
  free(csock);
  fprintf(stderr, "Hilo: error anviando datos %d\n",errno );
  exit(-1);
 }
 printf("Hilo: Bytes enviados %d\n",bytecount );
 return 0;
}//socketHandle

int main(int argc, int **argv){
 struct sockaddr_in my_addr,saddr;
 int hsock, *p_int,err;
 socklen_t addr_size=0;
 int *csock;
 pthread_t thread_id=0;
 hsock=socket(AF_INET,SOCK_STREAM,0);
 if(hsock==-1){
  printf("Error creando el socket %d\n",errno );
  exit(-1);
 }
 p_int=(int *)malloc(sizeof(int));
 *p_int=1;
 if((setsockop(hsock,SOL_SOCKET,SO_REUSEADDR,(char*)p_int,sizeof(int))==-1) || () )
}