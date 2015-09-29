/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.modelo;

import javafx.beans.property.IntegerProperty;

/**
 *
 * @author joseluis.bachiller
 */
public class LetradoDTO extends PersonaDTO {
    private IntegerProperty numColeg;

    public int getNumColeg() {
        return numColeg.get();
    }

    public void setNumColeg(int numColeg) {
        this.numColeg.set(numColeg);
    }
    
    public IntegerProperty numColegProperty() {
        return numColeg;
    }
    
    public LetradoDTO parse (PersonaDTO persona) {
        id.set(persona.getId());
        nombre.set(persona.getNombre());
        pApellido.set(persona.getpApellido());
        sApellido.set(persona.getsApellido());
        tipoIdentificador.set(persona.getTipoIdentificador());
        identificador.set(persona.getIdentificador());
        direccion.set(persona.getDireccion());
        codigoPostal.set(persona.getCodigoPostal());
        localidad.set(persona.getLocalidad());
        provincia.set(persona.getProvincia());
        telefono.set(persona.getTelefono());
        movil.set(persona.getMovil());
        fecNac.set(persona.getFecNac());
        return this;
    }
}
