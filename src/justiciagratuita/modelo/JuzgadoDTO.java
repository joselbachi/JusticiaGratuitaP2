/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.modelo;

import java.time.LocalDate;

/**
 *
 * @author joseluis.bachiller
 */
public class JuzgadoDTO {

    private int id;
    private String descripcion;
    private TjuzgadoDTO tipo;
    private LocalDate FecAlta;
    private LocalDate FecModifica;

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

    public TjuzgadoDTO getTipo() {
        return tipo;
    }

    public void setTipo(TjuzgadoDTO tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFecAlta() {
        return FecAlta;
    }

    public void setFecAlta(LocalDate FecAlta) {
        this.FecAlta = FecAlta;
    }

    public LocalDate getFecModifica() {
        return FecModifica;
    }

    public void setFecModifica(LocalDate FecModifica) {
        this.FecModifica = FecModifica;
    }
    
    @Override
    public String toString() {
        return descripcion;
    }

}
