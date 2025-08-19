# Conversor de Monedas

## Descripción

Este proyecto es un conversor de monedas desarrollado en Java que permite convertir entre pesos mexicanos (MXN), dólares
estadounidenses (USD), denares macedonios (MKD) y euros (EUR). Utiliza una API para obtener las tasas de cambio
actualizadas y realiza conversiones bidireccionales entre estas monedas.

## Funcionalidad

- Conversión entre MXN, USD, MKD y EUR.
- Conversión en ambas direcciones (por ejemplo, MXN a EUR y EUR a MXN).
- Actualización de tasas de cambio mediante una API externa.

## Requisitos

- Java JDK instalado.
- Archivo `gson-2.8.9.jar` (o versión compatible) para manejo JSON.
- API_KEY válida para la API de tasas de cambio.

## Ejecución

1. Compilar el proyecto:

```
javac -cp gson-2.8.9.jar;. Main.java
```

2. Ejecutar el programa:

```
java -cp gson-2.8.9.jar;. Main
```

## Uso

Al ejecutar el programa, se mostrará un menú con las opciones de conversión disponibles. El usuario debe seleccionar la
opción deseada, ingresar la cantidad a convertir y el programa mostrará el resultado.

### Ejemplo de menú y conversión:

```
Bienvenido al Conversor de Monedas
Seleccione una opción:
1. Convertir MXN a EUR
2. Convertir EUR a MXN
3. Convertir USD a EUR
4. Convertir EUR a USD
5. Convertir MKD a EUR
6. Convertir EUR a MKD
7. Salir

Ingrese su opción: 1
Ingrese la cantidad en MXN: 100
100 MXN equivalen a 4.50 EUR
```

Seleccione otra opción o salga del programa cuando haya terminado.
