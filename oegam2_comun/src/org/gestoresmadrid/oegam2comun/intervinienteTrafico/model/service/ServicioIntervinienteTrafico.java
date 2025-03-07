package org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;

import escrituras.beans.ResultBean;

public interface ServicioIntervinienteTrafico extends Serializable {

	public static String INTERVINIENTE = "INTERVINIENTE";

	public static String CONVERSION_CONSULTA_INTERVINIENTE = "consultaInterviniente";

	IntervinienteTraficoVO getIntervinienteTrafico(BigDecimal numExpediente, String tipoInterviniente, String nif, String numColegiado);

	IntervinienteTraficoDto getIntervinienteTraficoDto(BigDecimal numExpediente, String tipoInterviniente, String nif, String numColegiado);

	boolean existenIntervinientesPorIdDireccion(String nif, String numColegiado, Long idDireccion);

	ResultBean guardarActualizar(IntervinienteTraficoVO intervinientePantalla, String conversion);

	ResultBean guardarMantenimientoInterviniente(IntervinienteTraficoDto intervinienteTraficoDto);

	IntervinienteTraficoDto crearIntervinienteNif(String nif, String numColegiado);

	void eliminar(IntervinienteTraficoVO interviniente);

	boolean esModificada(IntervinienteTraficoVO intervinienteVO, IntervinienteTraficoVO intervinienteBBDD);

	List<IntervinienteTraficoDto> getExpedientesDireccion(String nif, String numColegiado, Long idDireccion);

	ResultBean actualizarDireccion(String numColegiado, String nif, Long idDireccionPrincipal, Long idDireccionBorrar);

	ResultBean eliminarInterviniete(String nif, String numColegiado, String numExpediente, String tipoInterviniente);

	Integer comprobarTramitesEmpresaST(Long idContrato, Date fecha, String cifEmpresa, String codigoPostal,
			String municipio, String provincia, String tipoTramiteSolicitud);

	List<TramiteTraficoVO> getListaTramitesPorNifTipoIntervienteYFecha(String nif, BigDecimal idContrato,
			String tipoInterviniente, Date fecha, String tipoTramite, String codigoPostal, String idMunicipio,
			String idProvincia, String tipoTramiteSolicitud);
}
