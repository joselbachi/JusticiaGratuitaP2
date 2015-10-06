/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.modelo;

/**
 *
 * @author joseluis.bachiller
 */
public class TdocumentoDTO {
    
    private String id;
    private String descripcion;
    
    /**
     * Default constructor.
     * @param id = descripción. Se pone lo mismo para el documento y la descripción
     */
    public TdocumentoDTO(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
      
    @Override
    public String toString() {
        return descripcion;
    }
}
