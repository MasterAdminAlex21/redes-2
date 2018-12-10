//recibe archivos

#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h> //perror, printf
#include <string.h>
#include <netinet/in.h> //htons htonl
#include <stdlib.h> //exit
#include <unistd.h>//close
#include <arpa/inet.h>//inet_ntoa

int main(int agrc, char* argv[]){
    struct sockaddr_in sdir,cdir;
    bzero((char *)&sdir, sizeof(sdir));

    sdir.sin_family=AF_INET;
    sdir.sin_port=htons(9876);
    sdir.sin_addr.s_addr=htonl(INADDR_ANY);

    socklen_t ctam = sizeof(cdir);
    int sd,cd,v=1,op,n;

    if((sd=socket(AF_INET,SOCK_STREAM,0))<0)
     perror("Error en la funcion socket()\n");
    op=setsockopt(sd,SOL_SOCKET,SO_REUSEADDR,&v,sizeof(v));
    if(op<0)
  perror("Error en la opcion de socket\n");
    if(bind(sd,(struct sockaddr *)&sdir,sizeof(sdir))<0){
     close(sd);
        perror("El puerto ya esta en uso\n");
    }//if

    printf("Servicio iniciado..");
    listen(sd,5);
    for(;;){
        if((cd=accept(sd,(struct sockaddr *)&cdir,&ctam))<0){
         perror("Error en funcion accept()\n");
            continue;
        }//if
        printf("\nCliente conectado desde ->%s:%d\n Recibiendo datos...\n",inet_ntoa(cdir.sin_addr),ntohs(cdir.sin_port));
        char buffer[1024];
        bzero(buffer,sizeof(buffer));

        //recibe el nombre
        n=read(cd,buffer,sizeof(buffer));
        printf("Recibiendo archivo: %s\n",buffer );
        FILE *arc;
        arc=fopen(buffer,"w");
        if(arc==NULL){
            perror("Error al crear archivo\n");
        }
        long total=0,aux1=0;
        bzero(buffer,sizeof(buffer));
        //recibe los datos
        while(n>0){
            n=read(cd,buffer,sizeof(buffer));
            //printf("%d->",n );
            fwrite(buffer,sizeof(char),n,arc);
            //fprintf(arc,"%s",buffer);
            bzero(buffer,sizeof(buffer));
            total+=n;
            aux1+=1;
        }
        printf("\n%ld paquetes recibidos\n%ld bytes recibidos\nArchivo recibido\n",aux1,total);
        close(cd);
    }//for
    close(sd);
    return 0;
}//main


