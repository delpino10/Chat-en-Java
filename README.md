# Chat en Java

Este es un proyecto de un sistema de chat simple desarrollado en Java. Permite la comunicación en tiempo real entre múltiples clientes conectados a un servidor central.

## Características

- Comunicación en tiempo real.
- Soporte para múltiples clientes.
- Interfaz basada en consola.
- Arquitectura cliente-servidor.

## Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalado lo siguiente:

- [Java JDK 11 o superior](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
  
##Uso

- El servidor debe iniciarse primero. Escuchará en el puerto predeterminado (por ejemplo, 12345).
- Cada cliente que se conecte deberá ingresar la dirección IP del servidor y el puerto.
- Una vez conectado, el cliente puede enviar mensajes que serán visibles para todos los demás clientes conectados.

- ##Estructura del Proyecto

- src/
├── com.tu.paquete/
│   ├── Servidor.java           # Código del servidor
│   ├── Cliente.java            # Código del cliente
│   ├── GestorCliente.java      # Clase que gestiona los mensajes entre clientes y servidor


