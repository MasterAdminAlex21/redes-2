#include<stdio.h>
#include<stdlib.h>
#include<pthread.h>
#include<time.h>
#include<string.h>

struct rangos{
 int id;
 int inf;
 int sup;
 int datos[4096];
 int num_datos;
};

int *datos,tam;

void *ordena(void *);
int pivot(int *unarray, int izq, int der);
void Quicksort(int *unarray, int izq, int der);

int main(int argc, char* argv[]){
 if(argc!=4){
  printf("uso: %s <maximo> <num_datos> <num_cubetas>\n",argv[0] );
  exit(0);
 }if(atoi(argv[1])<=0 || atoi(argv[2])<=0 || atoi(argv[3])<=0){
  printf("Ingrese un valor positivo\n");
  exit(0);
 }

 srand(time(NULL));

 int max=atoi(argv[1]);
 int cubetas=atoi(argv[3]);
 tam=atoi(argv[2]);

 pthread_t hilos[cubetas];
 datos=(int*)malloc(sizeof(int) * tam);
 //printf("%d maximo\n%d datos\n%d cubetas\n",max,tam,cubetas);
 printf("Vector inicial: \n");
 for(int i=0;i<tam;i++){
  datos[i]=rand()%max;
  printf("%d  ",datos[i] );
 }printf("\n\n");
// printf("tamoaño de datos: %d\n",tam );

 //defino los rangos
 int lim=max/cubetas;
 //printf("%d limite\n",lim );
 struct rangos *rango[cubetas];
 for(int i=0;i<cubetas;i++){
  rango[i]=(struct rangos*)malloc(sizeof(struct rangos));
  rango[i]->id=i;
  if(i==0){
   rango[i]->inf=0;
  }else{
   rango[i]->inf=i*lim +1;
  }
  rango[i]->sup=(i+1)*lim;
  rango[i]->num_datos=0;
  pthread_create(&hilos[i],NULL,&ordena,rango[i]);

 }
 for(int i=0;i<cubetas;i++){
  pthread_join(hilos[i],NULL);
 }
 sleep(3);
 printf("\n\nVector ordenado:\n");
 for(int i=0;i<cubetas;i++){
  for(int j=0;j<rango[i]->num_datos;j++){
   printf("%d  ",rango[i]->datos[j] );
  }
 }
 printf("\n");

}



//creo la funcion para ordenar

void *ordena(void *ran){
 struct rangos *r=(struct rangos*)ran;
 printf("\nhilo %d -> rango: %d-%d\n",r->id,r->inf,r->sup);
 for(int i=0,j=0;i<tam;i++){
  if(datos[i]>=r->inf && datos[i]<=r->sup){
   r->datos[j]=datos[i];
   datos[i]=-1;
   r->num_datos+=1;
   j+=1;
  }
 }
 for(int i=0;i<r->num_datos;i++){
  printf("%d  ",r->datos[i] );
 }
 printf("numero de datos en el hilo: %d\n",r->num_datos );
 int aux, i, j;

         for (i = 0; i < r->num_datos -1 ; i++) {
                 for (j = i + 1; j < r->num_datos ; j++) {
                         if (r->datos[i] > r->datos[j]) {
                                aux = r->datos[i];
                                r->datos[i] = r->datos[j];
                                r->datos[j] = aux;
                         }
                }
         }

 pthread_exit(0);
}


