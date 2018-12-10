//compilacion gcc -Wall -lpthread -o exp exponencial.c
#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include "errores.h"

//presenta el resultado final del calculo
void fin_Calculo(void *arg){
 double resultado=*(double*)arg;
 printf("Resultado final: %g\n",resultado);
}

//calculo de la serie de taylor
void calculo(void *arg){
 int error,estado_ant,tipo_ant;
 double x=*(double*)arg,resultado=1;
 double sumando=1;
 long i,j;
 //parte obligatoria del calculo
 error=pthread_setcancelstate(PTHREAD_CANCEL_DISABLE,&estado_ant);
 if(error)
  error_fatal(error,"pthread_setcancelstate");
 for(i=0;i<10;i++){
  sumando*=x/i;
  resultado+=sumando;
 }
 printf("Primera aproximacion de exp(%g)=%g\n",x,resultado );
 pthread_cleanup_push(fin_Calculo,&resultado);
 //una vez se ejecuta la parte obligatoria, habilitamos la cancelacion
 error=pthread_setcanceltype(PTHREAD_CANCEL_DEFERRED,&tipo_ant);
 if(error)
  error_fatal(error,"pthread_setcanceltype");
 error=pthread_setcancelstate(PTHREAD_CANCEL_ENABLE,&estado_ant);
 if(error)
  error_fatal(error,"pthread_setcancelstate");
 //en esta parte afinamos el proceso, si hay tiempo
 printf("Refinamiento del calculo\n");
 for(;;){
  pthread_testcancel();//punto de cancelacion
  //se ejecuta otro calculo
  for(j=0;j<10;j++){
   sumando*=x/i++;
   resultado+=sumando;
  }
 }
 pthread_cleanup_pop(1);
}

int main(int argc,char *argv[]){
 pthread_t hilo;
 int error,plazo;
 double x;

 //analisis de argumentos
 if(argc!=3){
  printf("Forma de uso: %s x plazo\n",argv[0] );
  exit(-1);
 }
 else{
  x=atof(argv[1]);
  plazo=atoi(argv[2]);
 }

 //creacion del hilo que realiza el calculo
 error=pthread_create(&hilo,NULL,calculo,&x);
 if(error)
  error_fatal(error,"pthread_create");

 //una vez concluido el plazo se cancela el hilo que calcula
 sleep(plazo);
 error=pthread_cancel(hilo);
 if(error)
  error_fatal(error,"pthread_cancel");

 //esperamos hasta que la cancelacion se haga efectiva
 error=pthread_join(hilo,NULL);
 if(error)
  error_fatal(error,"pthread_join");
}