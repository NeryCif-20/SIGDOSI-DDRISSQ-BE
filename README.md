# SIGDOSI-DDRISSQ
Aqui se detallan los requerimientos e instrucciones necesarias para levantar el backend correctamente.

## Requisitos Previos
Se necesita tener las siguientes herramientas de forma local:
1. Docker ≥ 29.X.X
2. Docker Compose ≥ 5.X.X
3. JDK ≥ 25.X.X LTS

Puede verificar las versiones con los siguientes comandos:
```bash
docker -v
docker compose version
java --version
```

## Variables de Entorno
Se pueden configurar las variables de dos formas:
- Crea un archivo `.env` en la raiz del repositorio, copia el formato del archivo `.env.template` y configura las variables según sea el caso.
- Configura las variables directamente en el sistema operativo basandote en el archivo `.env.template`.

## Levantar para Desarrollo
Una vez configuradas las variables de entorno, ejecuta el siguiente comando:
```bash
./gradlew bootRun
```
Esto hará que se ejecute el backend y se levante la base de datos para desarrollo. No es necesario tener gradle instalado de forma local, este repositorio ya incluye un wrapper de gradle.