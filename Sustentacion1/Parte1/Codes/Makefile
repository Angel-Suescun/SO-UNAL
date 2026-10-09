# Makefile para compilar el modulo simple.c
obj-m := simple.o

# Directorio de construcción del kernel
KDIR := /lib/modules/$(shell uname -r)/build

# Directorio actual
PWD := $(shell pwd)

# Comandos para compilar y limpiar el módulo
all:
	$(MAKE) -C $(KDIR) M=$(PWD) modules

clean:
	$(MAKE) -C $(KDIR) M=$(PWD) clean