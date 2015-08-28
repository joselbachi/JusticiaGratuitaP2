/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.modelo;

import java.util.Calendar;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author joseluis.bachiller
 */
public class PersonaDTO extends BaseDTO {
    
    private final IntegerProperty id;
    private final StringProperty nombre;
    private final StringProperty pApellido;
    private final StringProperty sApellido;
    private final StringProperty idTipoIdentificador;
    private final StringProperty identificador;
    private final StringProperty direccion = new SimpleStringProperty();
    private IntegerProperty codigoPostal = new SimpleIntegerProperty();
    private final StringProperty localidad = new SimpleStringProperty();
    private final StringProperty provincia = new SimpleStringProperty();
    private final StringProperty telefono = new SimpleStringProperty();
    private final StringProperty movil = new SimpleStringProperty();
    private Calendar birthday;
    
    /**
     * Default constructor.
     */
    public PersonaDTO() {
        this(-1, null, null, null, null, null);
    }
    
    public PersonaDTO(int id, String nombre, String pApellido, String sapellido, String idTipoIdentificador, String identificador) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.pApellido = new SimpleStringProperty(pApellido);
        this.sApellido = new SimpleStringProperty(sapellido);
        this.idTipoIdentificador = new SimpleStringProperty(idTipoIdentificador);
        this.identificador = new SimpleStringProperty(identificador);
        // quitar después de las pruebas
        this.codigoPostal = new SimpleIntegerProperty(42002);
        
        this.birthday = Calendar.getInstance();
    }
    

    public String getMovil() {
        return movil.get();
    }

    public void setMovil(String value) {
        movil.set(value);
    }

    public StringProperty movilProperty() {
        return movil;
    }

    public String getTelefono() {
        return telefono.get();
    }

    public void setTelefono(String value) {
        telefono.set(value);
    }

    public StringProperty telefonoProperty() {
        return telefono;
    }
    

    public String getProvincia() {
        return provincia.get();
    }

    public void setProvincia(String value) {
        provincia.set(value);
    }

    public StringProperty provinciaProperty() {
        return provincia;
    }
    

    public String getLocalidad() {
        return localidad.get();
    }

    public void setLocalidad(String value) {
        localidad.set(value);
    }

    public StringProperty localidadProperty() {
        return localidad;
    }
    

    public int getCodigoPostal() {
        return codigoPostal.get();
    }

    public void setCodigoPostal(int value) {
        codigoPostal.set(value);
    }

    public IntegerProperty codigoPostalProperty() {
        return codigoPostal;
    }
    

    public String getDireccion() {
        return direccion.get();
    }

    public void setDireccion(String value) {
        direccion.set(value);
    }

    public StringProperty direccionProperty() {
        return direccion;
    }
    

    public String getIdTipoIdentificador() {
        return idTipoIdentificador.get();
    }

    public void setIdTipoIdentificador(String value) {
        idTipoIdentificador.set(value);
    }

    public StringProperty idTipoIdentificadorProperty() {
        return idTipoIdentificador;
    }
    
    public String getIdentificador() {
        return identificador.get();
    }

    public void setIdentificador(String value) {
        identificador.set(value);
    }

    public StringProperty identificadorProperty() {
        return identificador;
    }
    

    public String getsApellido() {
        return sApellido.get();
    }

    public void setsApellido(String value) {
        sApellido.set(value);
    }

    public StringProperty sApellidoProperty() {
        return sApellido;
    }
    

    public String getpApellido() {
        return pApellido.get();
    }

    public void setpApellido(String value) {
        pApellido.set(value);
    }

    public StringProperty pApellidoProperty() {
        return pApellido;
    }

    public void setId(int id) {
        this.id.set(id);
    }
    
    public int getId () {
        return id.intValue();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }
    
    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    //public ObjectProperty<Calendar> birthdayProperty() {
    //    return birthday;
    //}
}
