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
public class TjuzgadoDTO {
    private String id;
    private String descripcion;

    /**
     * Default constructor.
     * @param id
     * @param descripcion
     */
    public TjuzgadoDTO(String id, String descripcion) {
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
