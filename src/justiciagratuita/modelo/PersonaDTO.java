/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.modelo;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author joseluis.bachiller
 */
public class PersonaDTO extends BaseDTO {
    
    public static final String NIF = "NIF";
    public static final String NIE = "NIE";
    public static final String PASAPORTE = "PASAPORTE";
    
    protected final IntegerProperty id;
    protected final StringProperty nombre;
    protected final StringProperty pApellido;
    protected final StringProperty sApellido;
    //private final StringProperty idTipoIdentificador;
    protected final StringProperty identificador;
    protected final StringProperty direccion = new SimpleStringProperty();
    protected final IntegerProperty codigoPostal;
    protected final StringProperty localidad = new SimpleStringProperty();
    protected final StringProperty provincia = new SimpleStringProperty();
    protected final StringProperty telefono = new SimpleStringProperty();
    protected final StringProperty movil = new SimpleStringProperty();
    protected final ObjectProperty<LocalDate> fecNac;
    
    /* datos persona */
    protected final ObjectProperty<TdocumentoDTO> tipoIdentificador;
    
    /**
     * Default constructor.
     */
    public PersonaDTO() {
        this(-1, null, null, null, new TdocumentoDTO(null), null);
    }
    
    public PersonaDTO(int id, String nombre, String pApellido, String sapellido, String idTtipoIdentificador, String identificador) {
        this(id, nombre, pApellido, sapellido,  new TdocumentoDTO(idTtipoIdentificador), identificador);
    }
    
    public PersonaDTO(int id, String nombre, String pApellido, String sapellido, TdocumentoDTO tipoIdentificador, String identificador) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.pApellido = new SimpleStringProperty(pApellido);
        this.sApellido = new SimpleStringProperty(sapellido);
        this.tipoIdentificador = new SimpleObjectProperty(tipoIdentificador);
        this.identificador = new SimpleStringProperty(identificador);
        // quitar después de las pruebas
        this.codigoPostal = new SimpleIntegerProperty(42002);
        
        this.fecNac = new SimpleObjectProperty<LocalDate>(LocalDate.now());
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
    

/*    public String getIdTipoIdentificador() {
        return tipoIdentificador.get().getId();
    }
*/
    public TdocumentoDTO getTipoIdentificador() {
        return tipoIdentificador.get();
    }
    
    public void setTipoIdentificador(TdocumentoDTO value) {
        tipoIdentificador.set(value);
    }

    public ObjectProperty<TdocumentoDTO> TipoIdentificadorProperty() {
        return tipoIdentificador;
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
    
    /**
     * Devuelve la cadena completa del ducumento identificador (tipo ident + identificador)
     * @return cadena de identificador o nulo si no existen datos.
     */
    public String getDocumento() {
        if (tipoIdentificador != null && identificador != null ) {
            return tipoIdentificador.get().getId()+ "-" + identificador.get();
        }
        return null;
    }
    

    public String getsApellido() {
        if (sApellido != null) {
            return sApellido.get();
        } else {
            return null;
        }
    }

    public void setsApellido(String value) {
        sApellido.set(value);
    }

    public StringProperty sApellidoProperty() {
        return sApellido;
    }
    
    public String getApellidos() {
        if (sApellido != null) {
            return pApellido.get() + " " + sApellido.get();
        } else {
            return pApellido.get();
        }
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
    
    public LocalDate getFecNac() {
        return fecNac.get();
    }

    public void setFecNac(LocalDate birthday) {
        this.fecNac.set(birthday);
    }

    public ObjectProperty<LocalDate> fecNacProperty() {
        return fecNac;
    }
    
    public String apellNombreCompleto(){
        if (util.ValidationsUtil.isCadenaVacia(sApellido.get())) {
            return pApellido.get()+", "+nombre.get();
        } else {
            return pApellido.get()+" "+sApellido.get()+", "+nombre.get();
        }
    }
    
    public String nombreApellCompleto(){
        if (util.ValidationsUtil.isCadenaVacia(sApellido.get())) {
            return nombre.get()+", "+pApellido.get();
        } else {
            return nombre.get()+", "+pApellido.get()+" "+sApellido.get();
        }
    }
    
    @Override
    public String toString() {
        return apellNombreCompleto();
    }

}
