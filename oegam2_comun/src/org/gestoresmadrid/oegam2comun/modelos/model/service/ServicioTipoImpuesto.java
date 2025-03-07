package org.gestoresmadrid.oegam2comun.modelos.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegam2comun.modelos.view.dto.ConceptoDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ModeloDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.TipoImpuestoDto;

import escrituras.beans.ResultBean;

public interface ServicioTipoImpuesto extends Serializable {

	ResultBean buscarTipoImpuestoPorConceptoYModelo(ConceptoDto concepto, ModeloDto modelo);

	TipoImpuestoDto buscarTipoImpuestoPorConcepto(String concepto);

}