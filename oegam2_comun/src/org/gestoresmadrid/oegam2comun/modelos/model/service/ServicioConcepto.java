package org.gestoresmadrid.oegam2comun.modelos.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.intervinienteModelos.model.vo.IntervinienteModelosVO;
import org.gestoresmadrid.core.modelo600_601.model.vo.Modelo600_601VO;
import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.Modelo600_601Dto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ConceptoDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ModeloDto;

import escrituras.beans.ResultBean;

public interface ServicioConcepto extends Serializable{

	List<ConceptoDto> getlistaConceptosPorModelo(ModeloDto modelo);

	ConceptoDto getConceptoPorAbreviatura(String abreviatura);

	List<ConceptoDto> getlistaConceptosPorModelo(String modelo, String version);

	boolean esConceptoBienes(ConceptoDto concepto);

	ResultBean gestionarRemesaPorConcepto(RemesaVO remesaVO);

	ResultBean gestionarModeloPorConcepto(Modelo600_601VO modeloVO);

	ResultBean validarConceptoModelo(Modelo600_601VO modeloVO);

	void convertirBaseImponibleDependiendoConcepto(Modelo600_601VO modeloVO, Modelo600_601Dto modeloDto);

	ResultBean validarModelo600PorConcepto(Modelo600_601VO modeloVO);

	ResultBean validarRemesa600PorConcepto(RemesaVO remesaVO);

	void obtenerBaseImponibleRemesa600(RemesaVO remesaVO, IntervinienteModelosVO sujetoPasivoVO, IntervinienteModelosVO transmitenteVO, Modelo600_601VO modelo600_601VO);

}
