//compilar con gcc -Wall -lpthread -o hilos hilos.c 

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <pthread.h>

#define NUM_HILOS 5
int l=0;

void *codigo_hilo(void *id){
 for(int i=0;i<50;i++){
  printf("hilo %d: i=%d, l=%d\n",*(int*)id,i,l++);
 }
 pthread_exit(id);
}

int main(){
 int h;
 pthread_t hilos[NUM_HILOS];
 int id[NUM_HILOS]={1,2,3,4,5};
 int error;
 int *salida;

 for(h=0;h<NUM_HILOS;h++){
  error=pthread_create(&hilos[h],NULL,codigo_hilo,&id[h]);
  if(error){
   fprintf(stderr, "Error %d: %s\n",error,strerror(error) );
   exit(-1);
  }
 }

 for(h=0;h<NUM_HILOS;h++){
  error=pthread_join(hilos[h],(void**)&salida);
  if(error){
   fprintf(stderr, "Error %d: %s\n",error,strerror(error) );
  }else{
   printf("Hilo %d terminado\n",*salida );
  }
 }
}