
def saluda():
    print("Hola amigos")

                    # PROGRAMACION FUNCIONAL
variable=saluda     # Variable que apunta a una función
variable()          # Qué hago aquí? Ejecuto la función desde la variable

def generar_saludo_formal(nombre):
    return "Buenos días " + nombre

def generar_saludo_informal(nombre):
    return "Hola " + nombre

def imprimir_saludo(funcion_generadora_de_saludo, nombre):
    print(funcion_generadora_de_saludo(nombre)) # AQUI ES DONDE EJECUTO generar_saludo_formal y generar_saludo_informal

imprimir_saludo(generar_saludo_formal, "Federico")
imprimir_saludo(generar_saludo_informal, "Federico")