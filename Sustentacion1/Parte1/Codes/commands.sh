

# Actualizar los paquetes del sistema
sudo apt update

# Instalar las herramientas de compilación necesarias
sudo apt install build-essential

# Verificar la versión de gcc
gcc --version

# Lista los modulos del kernel de Linux que estan cargados actualmente
lsmod

#Listar los archivos del proyecto
ls -l
 
# Compilar el modulo utilizando el makefile
make

#Comprobar los archivos generados
ls -l

# Comprobar si el modulo simple.ko esta cargado en el kernel
lsmod | grep simple

# Cargar el modulo simple.ko en el kernel
sudo insmod simple.ko

# Comprobar si el modulo simple.ko esta cargado en el kernel
lsmod | grep simple

# Verificar los mensajes del kernel relacionados con el modulo simple.ko
sudo dmesg | tail -n 20

# Eliminar el modulo simple.ko del kernel
sudo rmmod simple

# Comprobar si el modulo simple.ko esta cargado en el kernel
lsmod | grep simple
sudo dmesg | tail -n 20



