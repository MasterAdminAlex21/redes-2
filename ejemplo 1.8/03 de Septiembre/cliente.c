#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include "MensajeError.h"

#define TAMBUFFER 2000

int main (int argc, char **argv) {
    if(argc < 3 || argc > 4) {
        mensajeFinalError("Uso:EcoTCPCliente <Dirección del servidor> <Palabra de Eco> [<Puerto>]");
    }
    char *servIP = argv[1];
    char *cadenaEco = argv[2];
    //Argumento opcional, se agrega por defecto
    in_port_t puerto = (argc == 4)? atoi(argv[3]):7;
    //Crea el socket del cliente TCP
    int s = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
    if(s < 0) {
        mensajeFinalError("Error de apertura del conector");
    }
    struct sockaddr_in dirServ;
    memset(&dirServ, 0, sizeof(dirServ));
    dirServ.sin_family = AF_INET;
    int valRet = inet_pton(AF_INET, servIP, &dirServ.sin_addr.s_addr);
    if(valRet < 0) {
        mensajeFinalError("Dirección del Servidor Erronea");
    }
    dirServ.sin_port = htons(puerto);
    //Establecemos la comunicación con el servidor de Eco
    if(connect(s, (Struct sockaddr*)&dirServ, sizeof(dirServ)) < 0) {
        mensajeFinalError("Error en la conexón");
    }
    size_t longCadenaEco = strlen(cadenaEco);
    //Envia el mensaje al Servidor
    ssize_t numBytes = send(s, cadenaEco, longCadenaEco, 0);
    if(numBytes < 0) {
        mensajeFinalError("Fallo el envio");
    }
    else if(numBytes != longCadenaEco) {
        mensajeFinalError("Numero de bytes enviados erroneo");
    }
    //Recibimos de vuelta la cadena desde el servidor
    unsigned int totalBytesRec = 0;
    while(totalBytesRec < longCadenaEco) {
        char buffer[TAMBUFFER];
        memset(buffer, 0, TAMBUFFER);
        numBytes = recv(s, buffer, TAMBUFFER, 0);
        if(numBytes < 0) {
            mensajeFinalError("Recepción Fallida");
        }
        else if(numBytes == 0) {
            mensajeFinalError("Conexión cerrada prematuramente");
        }
        totalBytesRec += numBytes;
        printf("Recibido %s\n", buffer);
    }
    close(s);
    return 0;
}