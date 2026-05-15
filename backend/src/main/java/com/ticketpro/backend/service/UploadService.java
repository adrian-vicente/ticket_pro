package com.ticketpro.backend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {

    // Método conjunto que permite validar y guardar fichero en la base de datos

    public String guardarFichero(MultipartFile archivo, String rutaCarpeta) {
        try {

            // Crear la carpeta en caso de qué no exista 

            Path directorio = Paths.get(rutaCarpeta);
            if(!Files.exists(directorio)) Files.createDirectories(directorio);

            // Generar un nombre único para el fichero subido 

            String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();

            // Obtener la ruta final para guardar el fichero 

            Path rutaArchivo = directorio.resolve(nombreArchivo);
            
            // Guardar el fichero en la base de datos 

            Files.copy(archivo.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);

            // Obtener la ruta accesible desde el frontend 

            String imageUrl = rutaCarpeta + nombreArchivo;

            // Devolver el nombre de la imágen tras haber sido guardada 

            return imageUrl;

        } catch (IOException e) {
            // TODO: handle exception
            System.out.println("Se ha producido un error: " + e.getMessage());

        } // TRY - CATCH

        return null;

    }

    // Método que permite validar si el fichero está vacío o no 

    public boolean ficheroEstaVacio(MultipartFile archivo) {
        return archivo.isEmpty();
    }

} // class