/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.modelo;

import java.time.LocalDateTime;

/**
 *
 * @author joseluis.bachiller
 */
public class EstadoExpDTO extends BaseDTO {
    
    private int id;
    private String descripcion;
    private String descCorta;
    private LocalDateTime FecBaja;

    public EstadoExpDTO(int id, String descripcion, String descCorta) {
        this.id = id;
        this.descripcion = descripcion;
        this.descCorta = descCorta;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescCorta() {
        return descCorta;
    }

    public void setDescCorta(String descCorta) {
        this.descCorta = descCorta;
    }

    public LocalDateTime getFecBaja() {
        return FecBaja;
    }

    public void setFecBaja(LocalDateTime FecBaja) {
        this.FecBaja = FecBaja;
    }
    
    
}
