# 🌿 EcoGuardia App - Gestión y Reporte Ciudadano v1.0

![Android Studio](https://img.shields.io/badge/Android%20Studio-Hedgehog-brightgreen)
![Java](https://img.shields.io/badge/Language-Java-orange)
![Licencia](https://img.shields.io/badge/License-MIT-blue)

**EcoGuardia** es una aplicación móvil desarrollada en Android (Java) orientada a la gestión ambiental y reporte ciudadano. Permite a los usuarios reportar microbasurales, consultar puntos verdes de reciclaje, enviar evidencia fotográfica y comunicarse de manera directa con las entidades públicas locales.

---

## 📋 Especificaciones Técnicas

* **Lenguaje de Programación:** Java 11
* **MinSDK:** 24 (Android 7.0 Nougat)
* **TargetSDK:** 34 (Android 14)
* **Gradle Plugin (AGP):** v8.2.0
* **Arquitectura:** MVC / Android Components

---

## 🚀 Listado de Intents Implementados

La aplicación implementa un total de **8 Intents** (5 implícitos y 3 explícitos) con sus correspondientes validaciones de seguridad y gestión de permisos en tiempo de ejecución.

### 1. Intents Implícitos (Interacción con el Sistema)

| N° | Evento Implicito | Descripción / Acción | Pasos de Prueba |
|---|---|---|---|
| **1** | **Google Maps** | `geo:lat,lng?q=...` | Presionar *"1. Ver Punto Verde en Mapa"*. Abre la app de Google Maps fijando un marcador de reciclaje. |
| **2** | **Navegador Web** | `ACTION_VIEW` | Presionar *"2. Tutorial de Reciclaje (Web)"*. Abre el navegador del sistema con información sobre educación ambiental. |
| **3** | **Marcador Telefónico** | `ACTION_DIAL` | Presionar *"3. Llamar a Aseo y Ornato"*. Abre la app de teléfono prellenando el número de atención ciudadana. |
| **4** | **Correo Electrónico** | `ACTION_SENDTO` (`mailto:`) | Presionar *"4. Enviar Correo de Reporte"*. Abre la app de correo prellenando el destinatario, asunto y cuerpo del mensaje. |
| **5** | **Cámara del Dispositivo** | `MediaStore.ACTION_IMAGE_CAPTURE` | Presionar *"5. Tomar Foto de Evidencia"*. Solicita permisos de cámara, captura una foto y la muestra en la vista previa. |

### 2. Intents Explícitos (Navegación Interna)

| N° | Evento Explícito | Descripción / Acción | Pasos de Prueba |
|---|---|---|---|
| **1** | **`MainActivity` ➔ `DetalleReporte`** | Envío de datos mediante `putExtra` | Presionar *"Ver Detalle de Reporte"*. Navega a la vista secundaria mostrando título, descripción y coordenadas enviadas. |
| **2** | **`MainActivity` ➔ `FormularioReporteActivity`** | Comunicación con resultado (`registerForActivityResult`) | Presionar *"Nuevo Reporte"*. Abre un formulario y, al confirmar, regresa a la vista principal desplegando un Toast con el mensaje retornado. |
| **3** | **`MainActivity` ➔ `SyncService`** | Servicio en segundo plano ejecutado con **Threads** | Presionar *"Sincronizar Datos"*. Inicia un `Service` en segundo plano que simula la sincronización asíncrona mediante hilos de ejecución. |

---

## 🛡️ Validaciones e Integridad

* **Control de Permisos:** Se valida la concesión del permiso `android.permission.CAMERA` en tiempo de ejecución antes de lanzar la cámara.
* **Manejo de Excepciones:** Se capturan errores `ActivityNotFoundException` al ejecutar intents implícitos para prevenir cierres inesperados (*crashes*) si el dispositivo carece de alguna aplicación predeterminada.
* **Control de Nulos:** Comprobación de valores `null` al desempaquetar datos (`getExtras()`) entre actividades.

---

## 📸 Capturas de Pantalla / Demostración

| Pantalla Principal | Detalle de Reporte | Uso de Cámara |
| :---: | :---: | :---: |
| ![Main Screen](https://via.placeholder.com/200x400.png?text=Main+Activity) | ![Detail Screen](https://via.placeholder.com/200x400.png?text=Detalle+Activity) | ![Camera Screen](https://via.placeholder.com/200x400.png?text=Camara+Preview) |

---

## 📦 Generación y Ubicación del APK Debug

Para compilar y probar la aplicación directamente en un dispositivo Android o emulador:

1. Abrir el proyecto en **Android Studio**.
2. En la barra superior, seleccionar **Build ➔ Build Bundle(s) / APK(s) ➔ Build APK(s)**.
3. El archivo binario resultante se encontrará generado en la siguiente ruta dentro del proyecto:
   ```bash
   app/build/outputs/apk/debug/app-debug.apk