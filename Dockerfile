# Usar una imagen base de Ubuntu
FROM ubuntu:20.04

# Instalar dependencias
RUN apt-get update && apt-get install -y \
    openjdk-8-jdk \
    git \
    curl \
    python3 \
    python3-pip

# Clonar el repositorio de Battery Historian
RUN git clone https://github.com/google/battery-historian /opt/battery-historian

# Instalar dependencias de Python
RUN pip3 install --upgrade pip
RUN pip3 install -r /opt/battery-historian/requirements.txt

# Exponer el puerto 9999
EXPOSE 9999

# Definir el comando para ejecutar Battery Historian
CMD ["python3", "/opt/battery-historian/battery-historian", "--port", "9999"]
