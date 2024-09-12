package es.cic.curso.Recetas.dto;


import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MedicamentoDTO {

    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 255, message = "El nombre no puede tener más de 255 caracteres")
    private String nombre;

    @Size(max = 1000, message = "La descripción no puede tener más de 1000 caracteres")
    private String descripcion;

    @Size(max = 255, message = "La dosis recomendada no puede tener más de 255 caracteres")
    private String dosisRecomendada;

    @Size(max = 255, message = "La vía de administración no puede tener más de 255 caracteres")
    private String viaAdministracion;

    @Size(max = 1000, message = "Las contraindicaciones no pueden tener más de 1000 caracteres")
    private String contraindicaciones;

    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private BigDecimal precio;

    private Long tipoId;
    private String tipoNombre;

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
    public String getDosisRecomendada() {
        return dosisRecomendada;
    }
    public void setDosisRecomendada(String dosisRecomendada) {
        this.dosisRecomendada = dosisRecomendada;
    }
    public String getViaAdministracion() {
        return viaAdministracion;
    }
    public void setViaAdministracion(String viaAdministracion) {
        this.viaAdministracion = viaAdministracion;
    }
    public String getContraindicaciones() {
        return contraindicaciones;
    }
    public void setContraindicaciones(String contraindicaciones) {
        this.contraindicaciones = contraindicaciones;
    }
    public BigDecimal getPrecio() {
        return precio;
    }
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    public Long getTipoId() {
        return tipoId;
    }
    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }
    public String getTipoNombre() {
        return tipoNombre;
    }
    public void setTipoNombre(String tipoNombre) {
        this.tipoNombre = tipoNombre;
    }


}