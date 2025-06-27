# Sistema de Gestión de Citas Hospitalarias

Aplicación de escritorio para la gestión de citas médicas en un entorno hospitalario, desarrollada en Java con interfaz gráfica Swing y persistencia de datos en archivos binarios.

## Características principales

- 📅 Programación de nuevas citas médicas
- 👨‍⚕️ Gestión por especialidades médicas
- 🔍 Búsqueda y visualización de citas por paciente
- ✏️ Actualización de información de citas existentes
- ❌ Cancelación de citas
- 💾 Persistencia de datos entre sesiones
- 🎨 Interfaz intuitiva y amigable

## Requisitos del sistema

- Java JDK 17 o superior
- Sistema operativo: Windows, macOS o Linux
- 500 MB de espacio en disco
- Resolución de pantalla mínima recomendada: 1024x768

## Estructura del proyecto
SistemaCitasHospital/
├── src/
│ ├── modelo/
│ │ └── Cita.java
│ ├── persistencia/
│ │ └── GestorCitas.java
│ └── vista/
│ └── SistemaCitasHospital.java
├── citas.dat (archivo de datos, se crea automáticamente)
└── README.md


## Instalación y ejecución

1. Clona el repositorio o descarga los archivos fuente
2. Compila el proyecto:
   ```bash
   javac -d bin src/modelo/*.java src/persistencia/*.java src/vista/*.java
Ejecuta la aplicación:

bash
java -cp bin vista.SistemaCitasHospital

Si deseas puedes crear un archivo JAR ejecutable:

bash
jar cvfe SistemaCitasHospital.jar vista.SistemaCitasHospital -C bin .
java -jar SistemaCitasHospital.jar

**Uso de la aplicación**

La aplicación cuenta con tres módulos principales:

1. Programar Cita
Genera automáticamente un ID de cita

Permite ingresar datos del paciente (ID y nombre)

Selección de especialidad médica

Configuración de fecha y hora de la cita

Validación de fechas futuras

2. Ver Citas
Visualización de todas las citas activas

Filtrado por ID de paciente

Información detallada de cada cita

3. Gestionar Citas
Búsqueda de citas por ID

Modificación de especialidad, fecha y hora

Cancelación de citas

Validación de cambios


**Tecnologías utilizadas**

Java SE 23

Swing para la interfaz gráfica

Serialización de objetos para persistencia

Java Time API para manejo de fechas

Licencia
Este proyecto está bajo la licencia MIT. Consulta el archivo LICENSE para más detalles.
