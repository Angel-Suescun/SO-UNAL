# Parte 1: Módulo simple del kernel de Linux

## Descripción

En esta práctica se construye un módulo básico para el kernel de Linux. El módulo se puede compilar, cargar y retirar del kernel para observar algunos valores y funciones proporcionados por Linux.

## Archivos de la carpeta `Codes`

### `simple.c`

Este es el archivo principal del proyecto y contiene el código fuente del módulo.

- Incluye archivos del kernel necesarios para utilizar `jiffies`, `HZ`, `gcd` y otras funciones.
- `simple_init()` se ejecuta cuando el módulo se carga. Guarda el valor inicial de `jiffies` y muestra mensajes con información del kernel.
- `simple_exit()` se ejecuta cuando el módulo se elimina. Calcula el tiempo transcurrido desde la carga del módulo y muestra el máximo común divisor de `3300` y `24`.
- `module_init()` y `module_exit()` registran las funciones que se ejecutan al cargar y retirar el módulo.
- Al final se especifican la licencia, la descripción y el autor del módulo.

### `Makefile`

Este archivo contiene las instrucciones para compilar el módulo usando el sistema de compilación del kernel.

- `obj-m := simple.o` indica que se debe generar el módulo `simple.ko` a partir de `simple.c`.
- `KDIR` establece la ubicación de los archivos del kernel instalados en el sistema.
- `all` compila el módulo.
- `clean` elimina los archivos generados durante la compilación.

### `commands.sh`

Este script reúne los comandos utilizados para preparar, compilar y probar el módulo.

- Actualiza los paquetes e instala las herramientas de compilación.
- Muestra información de GCC y de los módulos cargados.
- Compila el proyecto mediante el `Makefile`.
- Carga el módulo con `insmod` y verifica que esté activo con `lsmod`.
- Consulta los mensajes generados por el módulo mediante `dmesg`.
- Retira el módulo con `rmmod` y comprueba que ya no esté cargado.

## Requisitos

Se necesita un sistema Linux con las herramientas de compilación y los headers del kernel instalados:

```bash
sudo apt update
sudo apt install build-essential linux-headers-$(uname -r)
```

## Compilación y ejecución

Desde la carpeta `Codes`, se puede ejecutar el flujo principal con:

```bash
make
sudo insmod simple.ko
sudo dmesg | tail -n 20
sudo rmmod simple
sudo dmesg | tail -n 20
```

Para eliminar los archivos generados:

```bash
make clean
```

## Resultado esperado

Al cargar el módulo se muestran mensajes indicando que fue cargado, el valor de `jiffies` y el valor de `HZ`. Al retirarlo se muestra el valor final de `jiffies`, el máximo común divisor calculado y el tiempo transcurrido en segundos.

