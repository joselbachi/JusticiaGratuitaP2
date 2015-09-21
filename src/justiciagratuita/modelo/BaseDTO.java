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
public class BaseDTO {
    
    private LocalDateTime FecAlta;
    private LocalDateTime FecModifica;

    public LocalDateTime getFecAlta() {
        return FecAlta;
    }

    public void setFecAlta(LocalDateTime FecAlta) {
        this.FecAlta = FecAlta;
    }

    public LocalDateTime getFecModifica() {
        return FecModifica;
    }

    public void setFecModifica(LocalDateTime FecModifica) {
        this.FecModifica = FecModifica;
    }
    
    
    
}
