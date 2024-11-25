# Feedback5

link al repositorio: https://github.com/mbp4/Feedback5.git

## Descripción 

En este proyecto se nos pedia hacer mejoras en la aplicación de novelas para poder hacer una aplicación que gaste menos bateria, que consuma menos memoria, etc.

## Desarrollo

Para la optimización de la aplicacion vamos a tener en cuenta: 

  -> La memoria

  -> El rendimiento de la red

  -> Consumo de batería

### Optimización de la memoria

En este caso al utilizar el Memory Profiller se llegó a la conclusión de que no habia fugas de memoria ya que en ningún momento se mostraba un aviso, por lo que simulamos uno.

Para poder ver como pueden provocarse fugas de memoria lo hacemos mediante hilos, los cuales van a estar consumiendo memoria una y otra vez sin soltarlo, lo que se hizo fue hacer que el botón de la clase "Acerca De" donde se muestra un texto con el autor de la aplicación y un boton que te devuelve a la pantalla principal. 

Para poder captar la fuga en el momento exacto y verlo de una manera mas visual se ha hecho uso de la aplicación "LeakCanary" lo cual nos da un aviso como el siguiente al ejecutar la aplicación: 

<img width="271" alt="Captura de pantalla 2024-11-25 a las 9 22 33" src="https://github.com/user-attachments/assets/10508337-ef5a-4653-9d60-76e0ecf1dbf2">

Por otra parte se da una opción para que este codigo se realice sin fugas de memoria, que si se comprueba podemos observar que no da ninguna fuga. 

El código que se encuentra comentado es el que da fugas y el que no esta comentado no las provoca.

### Rendimiento de la red

En la aplicación hacemos uso de Firebase, un servicio online que sirve de base de datos donde almacenamos las novelas, en este caso para mejorar el rendimiento de la red vamos a utilizar snapshots en lugar de usar el código antiguo (se encuentra comentado), esto hace una mejora significativa en el uso de la red. 

Con los snapshots actualizamos la lista automáticamente sin necesidad de crear métodos adicionales, lo que si se necesita es cerrar el Listener ya que si no se producen fugas de memoria.

### Consumo de bateria




