# 🚀 Backend de Login y Gestión de Usuarios en Java - Spring

Este es el backend de un sistema de login y gestión de usuarios desarrollado con **Java** y **Spring Boot**, que permite:

- Registro de usuario.
- Login de usuario con autenticación mediante **JWT**.
- Verificación de **OTP** (One-Time Password) enviado al correo.
- Listado de usuarios registrados.
- **Sanitización** de entradas para prevenir ataques, utilizando dependencias de **OWASP**.

## 📋 Características

- 🔐 **Registro de usuario**: Crea un nuevo usuario con correo electrónico y contraseña.
- 🔑 **Login con JWT**: Autenticación basada en JSON Web Token (JWT) para sesiones seguras.
- 💌 **Verificación OTP**: Envío de un código OTP al correo electrónico para la validación del usuario.
- 👥 **Listado de usuarios**: Permite obtener una lista de los usuarios registrados.
- 🛡️ **Sanitización de entradas**: Uso de OWASP para prevenir inyecciones SQL y otros ataques comunes.

## 🔨 Tecnologías utilizadas

- **Java 21**: Lenguaje de programación utilizado para desarrollar el backend.
- **Spring Boot**: Framework para la creación de aplicaciones empresariales.
- **JWT**: Autenticación basada en JSON Web Token.
- **Spring Security**: Para la configuración de seguridad, protección contra CSRF, y gestión de sesiones.
- **OWASP**: Dependencias y filtros para sanitización de entradas y prevención de ataques.
- **MySQL**: Base de datos utilizada para almacenar los usuarios.
