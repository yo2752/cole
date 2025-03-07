package org.gestoresmadrid.oegam2comun.licenciasCam.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.licencias.model.vo.LcTramiteVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcTramiteDto;

public interface ServicioLcTramite extends Serializable {

	ResultadoLicenciasBean guardarTramite(LcTramiteDto tramite);

	void guardarOActualizar(LcTramiteVO tramite) throws Exception;

	BigDecimal obtenerNExpediente(String numColegiado) throws Exception;

	boolean eliminarLicencia(LcTramiteDto tramite);

	List<LcTramiteDto> listarTodos();

	ResultadoLicenciasBean getTramiteLc(BigDecimal numExpediente);

	LcTramiteVO getTramiteLcVO(BigDecimal numExpediente, boolean completo);

	void guardarEvolucionTramite(BigDecimal numExpediente, BigDecimal antiguoEstado, BigDecimal nuevoEstado, BigDecimal idUsuario);

	ResultadoLicenciasBean cambiarEstado(boolean evolucion, BigDecimal numExpediente, BigDecimal antiguoEstado, BigDecimal nuevoEstado, BigDecimal idUsuario);

	String generarIdentificadorCam();

	ResultadoLicenciasBean validarTramite(LcTramiteDto tramite);
	
	ResultadoLicenciasBean crearTramite(LcTramiteDto tramiteDto, BigDecimal idUsuario);
}
