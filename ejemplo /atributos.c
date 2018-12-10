//compilacion gcc -Wall -lpthread atributos atributos.c
#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include "errores.h"
#include <limits.h>

void *codigo_hilo(void *arg){
 pthread_attr_t *atributos=arg;
 int detachstate, tam_pila, error;
 error=pthread_attr_getdetachstate(atributos,&detachstate);
 if(error)
  error_fatal(error,"pthread_attr_getdetachstate");
 else if(detachstate==PTHREAD_CREATE_DETACHED)
  printf("Hilo separado\n");
 else
  printf("Hilo NO separado\n");
 error=pthread_attr_getstacksize(atributos,&tam_pila);
 if(error)
  error_fatal(error,"pthread_attr_getstacksize");
 else
  printf("Hilo. Tamaño de la pila %d bytes= %d x %d\n",tam_pila,tam_pila/PTREAD_STACK_MIN,PTREAD_STACK_MIN );
 return NULL;//equivalente a pthread_exit(NULL)
}

int main(){
 pthread_t hilo;
 pthread_attr_f atributos;
 //inicializacion de atributos
 error=pthread_attr_init(&atributos);
 if(error)
  error_fatal(error,"pthread_attr_init");
 //activacion del atributo

 error=pthread_attr_setdetachstate(&atributos,PTHREAD_CREATE_DETACHED);
 if(error)
  
}