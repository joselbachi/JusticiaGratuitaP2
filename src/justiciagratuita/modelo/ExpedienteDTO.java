/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import util.StringUtil;

/**
 *
 * @author joseluis.bachiller
 */
public class ExpedienteDTO extends BaseDTO {
    
    private final String FORMT_NUMEXPED = "%04d";

    private final IntegerProperty id;
    private final IntegerProperty numTurno; // sólo número secuencial.
    private final IntegerProperty numExped; // sólo número secuencial.
    private final IntegerProperty anyo;
    private final IntegerProperty idComision;
    private final BooleanProperty violencia;
    private final ObjectProperty<LocalDateTime> fecEntradaCol;
    private final ObjectProperty<LocalDate> fecEnvioDel;
    private final ObjectProperty<LocalDate> fecEntradaDel;
    private final ObjectProperty<LocalDate> fecResolucion;
    private final ObjectProperty<LocalDate> fecImpugnacion;
    private final StringProperty observaciones;
    private final StringProperty numTurnoComp; 
    private final ObjectProperty<EstadoExpDTO> estado;
    
    /* actores del expediente */
    private final ObjectProperty<PersonaDTO> solicitante;
    private JuzgadoDTO juzgado;
    private LetradoDTO letrado;
    private ProcuradorDTO procurador;
    /* datos expediente */ 
    private final ObjectProperty<TasuntoDTO> asunto;
    
    /**
     * Default constructor.
     */
    public ExpedienteDTO() {
        this(-1,  -1, -1, null, null, null, null);
    }

    public ExpedienteDTO(int id,  int numTurno, int anyo, LocalDateTime fecEntradaCol, EstadoExpDTO estado, PersonaDTO solicitante, TasuntoDTO asunto) {
        this.id = new SimpleIntegerProperty(id);
        this.solicitante = new SimpleObjectProperty<PersonaDTO>(solicitante);
//        this.idJuzgado = new SimpleIntegerProperty();
//        this.idLetrado = new SimpleIntegerProperty();
//        this.idProcurador = new SimpleIntegerProperty();
        this.numTurno = new SimpleIntegerProperty(numTurno);
        this.anyo = new SimpleIntegerProperty(anyo);
        this.idComision = new SimpleIntegerProperty();
        this.violencia = new SimpleBooleanProperty(false);
        this.fecEntradaCol = new SimpleObjectProperty<LocalDateTime>(fecEntradaCol);
        this.fecEnvioDel = new SimpleObjectProperty<LocalDate>();
        this.fecEntradaDel = new SimpleObjectProperty<LocalDate>();
        this.fecResolucion = new SimpleObjectProperty<LocalDate>();
        this.fecImpugnacion = new SimpleObjectProperty<LocalDate>();
        this.observaciones = new  SimpleStringProperty();
        this.estado = new SimpleObjectProperty<EstadoExpDTO>(estado);
        this.numExped = new SimpleIntegerProperty();
        this.numTurnoComp = new SimpleStringProperty(this.anyo.intValue()+"/"+String.format(FORMT_NUMEXPED,this.numTurno.intValue()));
//        this.numExpediente = new SimpleStringProperty(this.anyo.intValue()+"/"+String.format(FORMT_NUMEXPED,this.numExped.intValue()));
        this.asunto = new SimpleObjectProperty<TasuntoDTO>(asunto);
    }

    /**
     * Devuelve el número interno de expediente (secuencial según se va creado)
     * @return número de expediente o -1 en caso de que no exista expediente
     */
    public int getId() {
        return id.intValue();
    }

    public void setId(int id) {
        this.id.set(id);
    }
    
    public IntegerProperty idProperty() {
        return id;
    }

    public JuzgadoDTO getJuzgado() {
        return juzgado;
    }
    
    public void setJuzgado(JuzgadoDTO juzgado) {
        this.juzgado = juzgado;
    }
    
    public String getJuzgadoNombre() {
        if (juzgado != null ) {
            return juzgado.getDescripcion();
        } else {
            return null;
        }
    }

    public LetradoDTO getLetrado() {
        return letrado;
    }
    
    public void setLetrado(LetradoDTO letrado){
        this.letrado = letrado;
    }

/*    public String getLetradoNombre() {
        return letrado.toString();
    }
*/
    public ProcuradorDTO getProcurador() {
        return procurador;
    }
    
    public void setProcurador(ProcuradorDTO procurador){
        this.procurador = procurador;
    }

    /*
    public String getLetradoNombre() {
        return letrado.toString();
    }
    */
    
    /**
     * Recupera el código de procurador 
     * @return código o null si no es necesario procurador
     */
/*
    public int getIdProcurador() {
        return idProcurador.intValue();
    }

    public void setIdProcurador(int idProcurador) {
        this.idProcurador.set(idProcurador);
    }
    
    public IntegerProperty idProcuradorProperty() {
        return idProcurador;
    }
*/
    /**
     * Junto con el año constituye el número de turno completo
     * @return número de turno
     */
    public int getNumTurno() {
        return numTurno.intValue();
    }

    /**
     * Número secuencial, junto con el año constituye el número de turno completo
     * @param numTurno 
     */
    public void setNumTurno(int numTurno) {
        this.numTurno.set(numTurno);
    }
    
    public IntegerProperty numTurnoProperty() {
        return numTurno;
    }
    
    /**
     * Junto con el año constituye el número de expediente
     * @return número de turno
     */
    public int getNumExped() {
        return numExped.intValue();
    }

    /**
     * Número secuencial, junto con el año constituye el número de expediente
     * @param numExped 
     */
    public void setNumExped(int numExped) {
        this.numExped.set(numExped);
    }
    
    /**
     * Número secuencial, junto con el año constituye el número de expediente
     * @return número de expediente (null si aún no ha sido asignado);
     */
    public IntegerProperty numExpedProperty() {
        return numExped;
    }

    /**
     * Año del expediente, junto con el número de turno constituye el número de expediente
     * @return 
     */
    public int getAnyo() {
        return anyo.intValue();
    }

    /**
     * Año del expediente, junto con el número de turno constituye el número de expediente
     * @param anyo año del expediente
     */
    public void setAnyo(int anyo) {
        this.anyo.set(anyo);
    }
    
    public IntegerProperty anyoProperty() {
        return anyo;
    }

    /**
     * Código de la comisión que va a evaluar el expediente
     * @return código de comisión
     */
    public int getIdComision() {
        return idComision.intValue();
    }

    /**
     * Código de la comisión que va a evaluar el expediente
     * @param idComision 
     */
    public void setIdComision(int idComision) {
        this.idComision.set(idComision);
    }
    
    public IntegerProperty idComisionProperty() {
        return idComision;
    }

    /**
     * Indicador de si se trata de un expediente de violencia de género
     * @return 
     */
    public Boolean getViolencia() {
        return violencia.getValue();
    }

    /**
     *  Indicador de si se trata de un expediente de violencia de género
     * @param violencia 
     */
    public void setViolencia(Boolean violencia) {
        this.violencia.set(violencia);
    }
    
    public BooleanProperty violenciaProperty() {
        return violencia;
    }

    /**
     * Fecha de entrada de la solicitud en el colegio de abogados
     * @return 
     */
    public LocalDateTime getFecEntradaCol() {
        return fecEntradaCol.get();
    }
    
    /**
     * Fecha de entrada de la solicitud en el colegio de abogados
     * @return 
     */
    public ObjectProperty<LocalDateTime> fecEntradaColProperty() {
        return fecEntradaCol;
    }

    /**
     * Fecha de entrada de la solicitud en el colegio de abogados
     * @param fecEntradaCol 
     */
    public void setFecEntradaCol(LocalDateTime fecEntradaCol) {
        this.fecEntradaCol.set(fecEntradaCol);
    }

    /**
     * Fecha en la que se envia el expediente a la Subdelegación
     * @return 
     */
    public LocalDate getFecEnvioDel() {
        return fecEnvioDel.get();
    }
    
    /**
     * Fecha en la que se envia el expediente a la Subdelegación
     * @return 
     */
    public ObjectProperty<LocalDate> fecEnvioDelProperty() {
        return fecEnvioDel;
    }

    /**
     * Fecha en la que se envia el expediente a la Subdelegación
     * @param fecEnvioDel 
     */
    public void setFecEnvioDel(LocalDate fecEnvioDel) {
        this.fecEnvioDel.set(fecEnvioDel);
    }

    /**
     * Fecha de entrada en el registro de la Subdelegación
     * @return 
     */
    public ObjectProperty<LocalDate> fecEntradaDelProperty() {
        return fecEntradaDel;
    }
    
    /**
     * Fecha de entrada en el registro de la Subdelegación
     * @return 
     */
    public LocalDate getFecEntradaDel() {
        return fecEntradaDel.get();
    }

    /**
     * Fecha de entrada en el registro de la Subdelegación
     * @param fecEntradaDel 
     */
    public void setFecEntradaDel(LocalDate fecEntradaDel) {
        this.fecEntradaDel.set(fecEntradaDel);
    }

    /**
     * Fecha de la resolución por parte de la comisión
     * @return 
     */
    public ObjectProperty<LocalDate> fecResolucionProperty() {
        return fecResolucion;
    }
    
    /**
     * Fecha de la resolución por parte de la comisión
     * @return 
     */
    public LocalDate getFecResolucion() {
        return fecResolucion.get();
    }

    /**
     * Fecha de la resolución por parte de la comisión
     * @param fecResolucion
     */
    public void setFecResolucion(LocalDate fecResolucion) {
        this.fecResolucion.set(fecResolucion);
    }

    /**
     * Fecha en la que se ha producido la impugnación
     * @return 
     */
    public ObjectProperty<LocalDate> fecImpugancionProperty() {
        return fecImpugnacion;
    }
    
    /**
     * Fecha en la que se ha producido la impugnación
     * @return 
     */
    public LocalDate getFecImpugancion() {
        return fecImpugnacion.get();
    }

    /**
     * Fecha en la que se ha producido la impugnación
     * @param fecImpugnacion 
     */
    public void setFecImpugancion(LocalDate fecImpugnacion) {
        this.fecImpugnacion.set(fecImpugnacion);
    }
    
    public StringProperty observacionesProperty() {
        return observaciones;
    }

    public String getObservaciones() {
        return observaciones.get();
    }

    public void setObservaciones(String observaciones) {
        this.observaciones.set(StringUtil.limpiaCadena(observaciones));
    }

    public ObjectProperty<EstadoExpDTO> estadoProperty() {
        return estado;
    }
    
    public EstadoExpDTO getEstado() {
        return estado.get();
    }
    
    public void setEstado (EstadoExpDTO estado) {
        this.estado.set(estado);
    }
    
    /**
     * Devuelve el número de expediente
     * @return año+\+número de expediente
     */
    public StringProperty numExpedienteProperty() {
        return new SimpleStringProperty(getNumExpediente());
    }
    
    /**
     * Devuelve el número de expediente formateado a yyyy\xxxx
     * @return año+\+número de expediente
     */
    public String getNumExpediente() {
        if (this.numExped.intValue() > 1) {
            return this.anyo.intValue()+"\\"+String.format(FORMT_NUMEXPED,this.numExped.intValue());
        } else {
            return "";
        }
    }

    /**
     * Devuelve el número de turno completo
     * @return año+\+número de turno
     */
    public StringProperty numTurnoCompProperty() {
        return numTurnoComp;
    }
    
    /**
     * Devuelve el número de turnno formateado a yyyy\xxxx
     * @return año+\+número de turno
     */
    public String getNumTurnoComp() {
        return this.anyo.intValue()+"\\"+String.format(FORMT_NUMEXPED, this.numTurno.intValue());
    }
    
    public ObjectProperty<PersonaDTO> getSolicitanteProperty() {
        return this.solicitante;
    }
    
    public PersonaDTO getSolicitante() {
        return this.solicitante.get();
    }
    
    public void setSolicitante(PersonaDTO solicitante) {
        this.solicitante.set(solicitante);
    }

    public String getDocumentoSolic() {
        if (solicitante != null && solicitante.get() != null) {
            return solicitante.get().getDocumento();
        }
        return null;
    }

    /*
    public LocalDate getFecEntrada() {
        return fecEntrada.get();
    }

    public void setFecEntrada(LocalDate fecEntrada) {
        this.fecEntrada.set(fecEntrada);
    }

    public ObjectProperty<LocalDate> fecEntradaProperty() {
        return fecEntrada;
    }
*/
    public void setAsunto(TasuntoDTO asunto) {
        this.asunto.set(asunto);
    }
            
    public ObjectProperty<TasuntoDTO> asuntoProperty() {
        return asunto;
    }

    public TasuntoDTO getAsunto() {
        return asunto.get();
    }
    
/*    public int getIdAsunto() {
        return asunto.getId();
    }
*/

}
