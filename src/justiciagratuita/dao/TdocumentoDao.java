/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justiciagratuita.dao;


import java.util.ArrayList;
import java.util.List;

import justiciagratuita.modelo.TdocumentoDTO;

/**
 *
 * @author joseluis.bachiller
 */
public class TdocumentoDao extends BaseDao {

    
    public TdocumentoDTO getTdocumentoByDesc(String descripcion) {
        TdocumentoDTO objeto = new TdocumentoDTO(descripcion);
        return objeto;
    }
    
    public TdocumentoDTO getTdocumentoById(String id) {
        TdocumentoDTO objeto = new TdocumentoDTO(id);
        return objeto;
    }
    
    public List<TdocumentoDTO> ListaTasunto() {

                
        List<TdocumentoDTO> lista = new ArrayList();

        TdocumentoDTO objeto = new TdocumentoDTO("NIF");
        lista.add(objeto);
        objeto = new TdocumentoDTO("NIE");
        lista.add(objeto);
        objeto = new TdocumentoDTO("PASAPORTE");
        lista.add(objeto);
        return lista;
    }
}
