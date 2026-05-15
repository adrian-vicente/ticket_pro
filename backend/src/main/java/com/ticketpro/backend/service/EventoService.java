package com.ticketpro.backend.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.ticketpro.backend.config.ValidationConfig;
import com.ticketpro.backend.dto.EventoDTO;
import com.ticketpro.backend.exception.CategoriaNotFoundException;
import com.ticketpro.backend.exception.EventoNotFoundException;
import com.ticketpro.backend.exception.ProveedorNotFoundException;
import com.ticketpro.backend.exception.UbicacionNotFoundException;
import com.ticketpro.backend.model.Categoria;
import com.ticketpro.backend.model.EstadoEvento;
import com.ticketpro.backend.model.Evento;
import com.ticketpro.backend.model.Proveedor;
import com.ticketpro.backend.model.Ubicacion;
import com.ticketpro.backend.repository.CategoriaRepository;
import com.ticketpro.backend.repository.EventoRepository;
import com.ticketpro.backend.repository.ProveedorRepository;
import com.ticketpro.backend.repository.UbicacionRepository;
import java.util.ArrayList;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;
    private final ValidationConfig validationConfig;
    private final CategoriaRepository categoriaRepository;
    private final UbicacionRepository ubicacionRepository;
    private final ProveedorRepository proveedorRepository;

    public EventoService(EventoRepository eventoRepository, ValidationConfig validationConfig, CategoriaRepository categoriaRepository, UbicacionRepository ubicacionRepository, ProveedorRepository proveedorRepository) {
        this.eventoRepository = eventoRepository;
        this.validationConfig = validationConfig;
        this.categoriaRepository = categoriaRepository;
        this.ubicacionRepository = ubicacionRepository;
        this.proveedorRepository = proveedorRepository;
    }

    public Evento toEntity(EventoDTO eventoDTO) throws Exception {

        Evento evento = new Evento();

        if( !validationConfig.isNullOrEqualsZero(eventoDTO.getId()) ) {
            evento.setId(eventoDTO.getId());

        } // if

        if( !validationConfig.isNullOrEqualsZero(eventoDTO.getCategoria_id()) ) {
            Optional<Categoria> categoria_opt = categoriaRepository.findById(eventoDTO.getCategoria_id());
            if( categoria_opt.isEmpty() ) throw new CategoriaNotFoundException("La categoría con id: " + eventoDTO.getCategoria_id() + ", no se ha encontrado en la base de datos.");
            else evento.setCategoria( categoria_opt.get() );

        } // if

        if( !validationConfig.isNullOrEqualsZero(eventoDTO.getUbicacion_id()) ) {
            Optional<Ubicacion> ubicacion_opt = ubicacionRepository.findById(eventoDTO.getUbicacion_id());
            if( ubicacion_opt.isEmpty() ) throw new UbicacionNotFoundException("La ubicación con id: " + eventoDTO.getUbicacion_id() + ", no se ha encontrado en la base de datos.");
            else evento.setUbicacion( ubicacion_opt.get() );

        } // if

        if( !validationConfig.isNullOrEqualsZero(eventoDTO.getProveedor_id()) ) {
            Optional<Proveedor> proveedor_opt = proveedorRepository.findById(eventoDTO.getProveedor_id());
            if( proveedor_opt.isEmpty() ) throw new ProveedorNotFoundException("El proveedor con id: " + eventoDTO.getProveedor_id() + ", no se ha encontrado en la base de datos.");
            else evento.setProveedor( proveedor_opt.get() );

        } // if

        evento.setNombre(eventoDTO.getNombre());
        evento.setDescripcion(eventoDTO.getDescripcion());
        evento.setFecha(eventoDTO.getFecha());
        evento.setHora(eventoDTO.getHora());
        evento.setCapacidad(eventoDTO.getCapacidad());
        evento.setEstado(eventoDTO.getEstado());
        evento.setPrecio(eventoDTO.getPrecio());
        
        return evento;

    } // toEntity

    public EventoDTO toDTO(Evento evento) throws Exception {

        EventoDTO eventoDTO = new EventoDTO();

        if( !validationConfig.isNullOrEqualsZero(evento.getId()) ) {
            eventoDTO.setId(evento.getId());

        } // if

        if( !validationConfig.isCategoriaNullOrNot(evento.getCategoria()) ) eventoDTO.setCategoria_id(evento.getCategoria().getId());
        if( !validationConfig.isUbicacionNullOrNot(evento.getUbicacion()) ) eventoDTO.setUbicacion_id(evento.getUbicacion().getId());
        if( !validationConfig.isProveedorNullOrNot(evento.getProveedor()) ) eventoDTO.setProveedor_id(evento.getProveedor().getId());

        eventoDTO.setNombre(evento.getNombre());
        eventoDTO.setDescripcion(evento.getDescripcion());
        eventoDTO.setFecha(evento.getFecha());
        eventoDTO.setHora(evento.getHora());
        eventoDTO.setCapacidad(evento.getCapacidad());
        eventoDTO.setEstado(evento.getEstado());
        eventoDTO.setPrecio(evento.getPrecio());

        return eventoDTO;

    } // toDTO

    // Método para crear un nuevo evento 

    public EventoDTO crearNuevoEvento(EventoDTO eventoDTO) throws Exception {
        Evento eventoCreado = eventoRepository.save( toEntity( eventoDTO ) );
        System.out.println("El evento con nombre: " + eventoCreado.getNombre() + " se ha creado correctamente.");
        return toDTO(eventoCreado);

    } // crearNuevoEvento

    // Método para obtener todos los eventos.

    public List<EventoDTO> obtenerTodosLosEventos() throws Exception {
        return recorrerListaEventos(eventoRepository.findAll());

    } // obtenerTodosLosEventos

    // Método para obtener un evento a partir del id

    public EventoDTO obtenerEventoPorId(Long id) throws Exception  {
        Evento evento = eventoRepository.findById(id)
            .orElseThrow( () -> new EventoNotFoundException("No se ha encontrado ningún evento con id: " + id) );

        System.out.println("Se ha encontrado correctamente el evento con id: " + id + " y nombre: " + evento.getNombre());
        return toDTO(evento);

    } // obtenerEventoPorId

    // Método para modificar los datos de un evento 

    public EventoDTO modificarEventoExistente(EventoDTO eventoDTO, Long id) throws Exception {
        Evento evento = eventoRepository.findById(id)
            .orElseThrow( () -> new EventoNotFoundException("No se ha encontrado ningún evento con id: " + id) );

        Evento eventoNuevo = toEntity( eventoDTO );

        evento.setNombre(eventoNuevo.getNombre());
        evento.setDescripcion(eventoNuevo.getDescripcion());
        evento.setFecha(eventoNuevo.getFecha());
        evento.setHora(eventoNuevo.getHora());
        evento.setCapacidad(eventoNuevo.getCapacidad());
        evento.setEstado(eventoNuevo.getEstado());
        evento.setPrecio(eventoNuevo.getPrecio());
        evento.setCreatedAt(eventoNuevo.getCreatedAt());
        evento.setProveedor(eventoNuevo.getProveedor());
        evento.setCategoria(eventoNuevo.getCategoria());
        evento.setUbicacion(eventoNuevo.getUbicacion());

        eventoRepository.save(evento);

        return toDTO( evento );

    } // modificarEventoExistente

    // Método para eliminar un evento 

    public void eliminarEventoExistente(Long id) throws EventoNotFoundException {
        Evento evento = eventoRepository.findById(id)
            .orElseThrow(() -> new EventoNotFoundException("No se ha encontrado el evento con id: " + id));

        evento.setEstado(EstadoEvento.INACTIVO);
        System.out.println("Se ha modificado el estado a inactivo correctamente para el evento: " + evento.getNombre());
        eventoRepository.saveAndFlush(evento);

    } 

    // Obtener todos los eventos a partir del id de un proveedor 

    public List<EventoDTO> obtenerEventosProveedorId(Long proveedor_id) throws Exception {
        return recorrerListaEventos(eventoRepository.findByProveedorId(proveedor_id));

    }

    // Obtener listado de los eventos activos: 

    public List<EventoDTO> obtenerEventosActivos() throws Exception {
        return recorrerListaEventos(eventoRepository.findByEstado(EstadoEvento.ACTIVO));

    }
 
    // Método para obtener los eventos filtrados por categoría 

    public List<EventoDTO> obtenerEventosPorCategoria(String nombre) throws Exception {
        return recorrerListaEventos(eventoRepository.findByCategoriaNombre(nombre));

    }

    // Método para obtener eventos filtrados por ubicación

    public List<EventoDTO> obtenerEventosPorUbicacion(String nombre) throws Exception {
        return recorrerListaEventos(eventoRepository.findByUbicacionNombre(nombre));

    }

    public List<EventoDTO> recorrerListaEventos(List<Evento> eventos) throws Exception {
        List<EventoDTO> eventoDTOs = new ArrayList<>();
        if(!eventos.isEmpty()) for(Evento ev : eventos) eventoDTOs.add(toDTO(ev));
        return eventoDTOs;

    }

} // class