#include<sys/types.h>
#include<stdio.h>
#include<stdlib.h>

int main(){
 int i=0;
 switch(fork()){
 case -1:
  perror("error en el fork");
  exit(-1);
 break;
 case 0://codigo del hijo
  while(i<10){
   sleep(1);
   printf("\t\tSoy el hijo %d\n",i++);
  }
 break;
 default://codigo del padre
 while(i<10){
  printf("Soy le padre %d\n",i++);
  sleep(2);
 }
 break;
 }
 exit(0);
}