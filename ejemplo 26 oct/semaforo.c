#include <semaphore.h>
#include <pthread.h>
#include <stdio.h>

#define HILOS 20

sem_t okComprarLeche;
int lecheDisponible;

void* comprador(void *arg)
{
    sem_wait(&okComprarLeche);
    if(!lecheDisponible || lecheDisponible<3){
        // Comprar algo de leche
        ++lecheDisponible;
    }
    sem_post(&okComprarLeche);
}

int main(int argc, char **argv)
{
    pthread_t hilos[HILOS];
        int i;

    lecheDisponible = 0;
    // Inicializamos el semáforo
    if(sem_init(&okComprarLeche, 0, 1)){
        printf("No se pudo inicializar el semáforo\n");
        return -1;
    }
    for(i = 0; i < HILOS; ++i){
        if(pthread_create(&hilos[i], NULL, &comprador, NULL)){
            printf("No se pudo crear el hilo número %d\n", i);
            return -1;
        }
    }

    for(i = 0; i < HILOS; ++i){
        if(pthread_join(hilos[i], NULL)){
            printf("No se pudo ligar el hilo número  %d\n", i);
            return -1;
        }
    }

    sem_destroy(&okComprarLeche);

    // Asegurémonos de no tener demasiada leche.
    printf("Total de leche: %d\n", lecheDisponible);

    return 0;
}