//paso de varios parametros
//hilo retorno.c

#include<pthread.h>
#include<stdio.h>
#include<malloc.h>

struct sumando{
 int a;
 int b;
};

void *suma(void *arg){
 int res=0;
 struct sumando *datos=(struct sumando*)arg;
 printf("\nDato a: %d",datos->a);
 printf("\nDato b: %d",datos->b);
 res=datos->a + datos->b;
 return (void *)res;
}

int main(){
 pthread_t thread;
 int resultado;
 struct sumando *x=(struct sumando*)malloc(sizeof(struct sumando));
 x->a=3;
 x->b=2;
 pthread_create(&thread,NULL,&suma,x);
 pthread_join(thread,(void **)&resultado);
 printf("\nEl resultado de sumar %d y %d es %d\n",x->a,x->b,resultado);
 free(x);
 return(0);
}