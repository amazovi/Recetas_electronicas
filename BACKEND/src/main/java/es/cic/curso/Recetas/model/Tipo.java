package es.cic.curso.Recetas.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tipo")
public class Tipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 1, max = 50, message = "El nombre debe tener entre 1 y 50 caracteres")
    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    @Column(name = "descripcion")
    private String descripcion;

    @Size(max = 255, message = "Los usos comunes no pueden tener más de 255 caracteres")
    @Column(name = "usos_comunes")
    private String usosComunes;

    @Size(max = 255, message = "Las interacciones no pueden tener más de 255 caracteres")
    @Column(name = "interacciones")
    private String interacciones;

    @OneToMany(mappedBy = "tipo", orphanRemoval = true)
    @JsonManagedReference
    private List<Medicamento> medicamentos;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsosComunes() {
        return usosComunes;
    }

    public void setUsosComunes(String usosComunes) {
        this.usosComunes = usosComunes;
    }

    public String getInteracciones() {
        return interacciones;
    }

    public void setInteracciones(String interacciones) {
        this.interacciones = interacciones;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

}