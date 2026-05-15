package com.ticketpro.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ticketpro.backend.exception.UbicacionNotFoundException;
import com.ticketpro.backend.model.Ubicacion;
import com.ticketpro.backend.repository.UbicacionRepository;
import com.ticketpro.backend.service.UploadService;

@RestController
@RequestMapping("/api/uploads")
@CrossOrigin(origins = "http://localhost:4200")
public class UploadController {

    // Declaración de variables estáticas 

    private static final String RUTA_UBICACIONES = "src/main/resources/uploads/ubicaciones/";
    private static final String RUTA_EVENTOS = "src/main/resources/uploads/eventos/";
    private static final String RUTA_PROVEEDORES = "src/main/resources/uploads/proveedores";

    // Inyección de dependencias 

    private final UploadService uploadService;
    private final UbicacionRepository ubicacionRepository;

    public UploadController(UploadService uploadService, UbicacionRepository ubicacionRepository) {
        this.uploadService = uploadService;
        this.ubicacionRepository = ubicacionRepository;

    }

    // Método que permite la subida de imágenes para ubicaciones

    @PostMapping("/ubicaciones/{id}")
    public ResponseEntity<String> subirImagenUbicacion(@PathVariable Long id, @RequestParam("imagen") MultipartFile archivo) throws UbicacionNotFoundException {

        // Comprobar si el fichero está vacío o no 

        if(uploadService.ficheroEstaVacio(archivo)) {
            return ResponseEntity.badRequest().body("No se ha seleccionado ningún fichero");
        }

        // Lanzar método para guardar fichero en la base de datos

        String imageUrl = uploadService.guardarFichero(archivo, RUTA_UBICACIONES);

        // Método para obtener una ubicación a partir del id

        Ubicacion ubicacion = ubicacionRepository.findById(id)
            .orElseThrow(() -> new UbicacionNotFoundException("No se ha encontrado ninguna ubicación con id: " + id));

        ubicacion.setImageUrl(imageUrl);
        System.out.println("Se ha añadido la url para la imágen con id: " + id);
        ubicacionRepository.saveAndFlush(ubicacion);

        // Devolver cadena en caso de que la foto se suba correctamente 

        return ResponseEntity.ok(imageUrl);

    }

    // Método que permite subir imágenes para eventos 

    // Método que permite subir imágenes para proveedores

    // Método que permite subir imágenes para categorías

} // class