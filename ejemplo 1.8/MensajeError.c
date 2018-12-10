#include <stdio.h>
#include <stdlib.h>
#include "MensajeError.h"

void mensajeFinalError(const char * mensaje) {
    fputs(mensaje, stderr);
    fptuc('\n', stderr);
    exit(1);
}

void mensajeFinalSistema(const char* mensaje) {
    perror(mensaje);
    exit(1);
}