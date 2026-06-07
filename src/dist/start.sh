#!/usr/bin/env bash
set -e

echo "========================================"
echo "  MyHomeWeb - Iniciando..."
echo "========================================"
echo

# Ir al directorio donde esta este script (donde esta el JAR)
cd "$(dirname "$0")"

# Crear carpeta data si no existe
mkdir -p data

# Verificar Java
if ! command -v java &>/dev/null; then
    echo "ERROR: Java no encontrado en PATH."
    echo "Instala Java 21+ y asegurate de que esta en el PATH."
    echo ""
    echo "Descarga: https://adoptium.net/"
    exit 1
fi

# Buscar el JAR
JAR=$(ls -1 *.jar 2>/dev/null | head -n1)

if [ -z "$JAR" ]; then
    echo "ERROR: No se encontro el fichero .jar"
    echo "Asegurate de que el .jar esta en la misma carpeta que este script."
    exit 1
fi

echo "Ejecutando: $JAR"
echo "Base de datos: data/myhomeweb.db"
echo ""
echo " URL: http://localhost:19484"
echo ""
echo "Pulsa Ctrl+C para detener."
echo "========================================"

# Abrir navegador automaticamente tras un pequeno delay
(sleep 5 && open "http://localhost:19484" 2>/dev/null || xdg-open "http://localhost:19484" 2>/dev/null) &

java -jar "$JAR"
