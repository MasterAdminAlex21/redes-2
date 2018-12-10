#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(){
 char *cadena;
 cadena=(char*)calloc(200,sizeof(char));
 printf("ingrese cadena: ");
 scanf("%[^\n]",cadena);
 char *token,*aux;
 printf("%s\n",cadena );
 int i=0;
 while(token!=NULL){
  memcpy(aux,token,60);
  token=strtok_r(cadena,"/",&cadena);
  printf("cadena %d: %s\n",i+1,token );
  i+=1;
  if(i==15) break;
 }
 printf("%s %d\n",aux,strlen(aux) );
}