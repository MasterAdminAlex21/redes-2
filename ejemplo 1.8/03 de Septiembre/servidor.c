#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include "MensajeError.h"

#define MAXLISTA 5
#define TAMBUFFER 1024

void manejadorTCPCliente(int);

int main(int argc, char **argv) {
    if(argc != 2) { //Revisamos el número de argumentos
        mensajeFinalError("Uso:EcoTCPServidor [<Puerto>]");
    }
    in_port_t prtoServ = atoi(argv[1]);
    //Creamos el socket de entrada
    int sockServ;
    if(sockServ = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP) < 0) {
        mensajeFinalError("Fallo la apertura del socket");
    }
    //Se construye a estructura de la dirección
    struct sockaddr_in dirServ;
    memset(&dirServ, 0, sizeof(dirServ));
    dirServ.sin_family = AF_INET;
    dirServ.sin_addr.s_addr = htons(INADDR_ANY);
    dirServ.sin_port = htons(prtoServ);
    //Se enlaza a la dirección local
    if(bind(sockServ, (struct sockaddr*)&dirServ, sizeof(dirServ)) < 0) {
        mensajeFinalError("Error al enlazar");
    }
}