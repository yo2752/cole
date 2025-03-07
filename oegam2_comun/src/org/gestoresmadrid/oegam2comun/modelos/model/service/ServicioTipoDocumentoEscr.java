package org.gestoresmadrid.oegam2comun.modelos.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.modelos.view.dto.ModeloDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.TipoDocumentoEscrDto;

public interface ServicioTipoDocumentoEscr extends Serializable{

	List<TipoDocumentoEscrDto> getListaDocumentosPorModelo(ModeloDto modelo);

}
