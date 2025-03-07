package org.gestoresmadrid.oegam2comun.intervinienteModelos.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.gestoresmadrid.core.intervinienteModelos.model.vo.IntervinienteModelosVO;
import org.gestoresmadrid.core.modelo600_601.model.vo.Modelo600_601VO;
import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;
import org.gestoresmadrid.oegam2comun.intervinienteModelos.view.dto.IntervinienteModelosDto;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.Modelo600_601Dto;
import org.gestoresmadrid.oegam2comun.remesa.view.dto.RemesaDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

import escrituras.beans.ResultBean;

public interface ServicioIntervinienteModelos extends Serializable{
	
	public static String CONVERSION_PERSONA_REMESA = "personaRemesa";

	ResultBean getIntervinientePorNifYContrato(String nif,	BigDecimal idContrato);

	IntervinienteModelosDto getIntervinientePorNifYNumColegiado(String nif,	String numColegiado);

	ResultBean convertirIntervinientesVoToDto(RemesaDto remesaDto, RemesaVO remesaVO);

	ResultBean guardarIntervinientesRemesa(IntervinienteModelosVO intervinienteModVO, RemesaVO remesaVO, String tipoTramiteModelo, UsuarioDto usuario, boolean esConceptoBienes, BigDecimal porcentaje);

	ResultBean getIntervinientesPorIdRemesa(Long idRemesa);

	ResultBean getIntervinientesPorId(Long idInterviniente);

	ResultBean getIntervinientePorNifYIdRemesaYTipoInterviniente(String nif, Long idRemesa, String tipoInterviniente);

	ResultBean eliminarInterviniente(Long idInterviniente);

	ResultBean validarGenerarModelosIntervinientes(RemesaVO remesaVO);

	ResultBean actualizarInterv(IntervinienteModelosVO intervinienteModelosVO);

	ResultBean guardarIntervinientesModelo600_601(IntervinienteModelosVO intervinienteModelosVO, Long idModelo);

	ResultBean convertirIntervinientesModeloVoToDto(Modelo600_601VO modeloVO, Modelo600_601Dto modeloDto);

	ResultBean getIntervinientePorNifYIdModeloYTipoInterviniente(String nif, Long idModelo, String tipoInterviniente);

	ResultBean guardarIntervinientesModelo(IntervinienteModelosVO intervinienteModelosVO, Modelo600_601VO modeloVO, String tipoTramiteModelo,UsuarioDto usuario);

	ResultBean validarIntervinientesModelos(Modelo600_601VO modeloVO);

	ResultBean getIntervinientesRemesaPorId(Long idInterviniente);

	List<IntervinienteModelosDto> getListaIntervinientesPorModelo(Long idModelo);

	List<IntervinienteModelosVO> getListaIntervinientesVOPorModelo(Long idModelo);

	void evict(IntervinienteModelosVO intervinienteModelosVO);

	ResultBean eliminarIntervientesModelos(Set<IntervinienteModelosVO> listaIntervinientes);

}
