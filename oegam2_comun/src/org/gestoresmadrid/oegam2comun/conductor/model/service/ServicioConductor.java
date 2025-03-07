package org.gestoresmadrid.oegam2comun.conductor.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import org.gestoresmadrid.core.conductor.model.vo.ConductorVO;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ResultConsultaArrendatarioBean;
import org.gestoresmadrid.oegam2comun.conductor.view.beans.ResultConsultaConductorBean;
import org.gestoresmadrid.oegam2comun.conductor.view.dto.ConductorDto;

public interface ServicioConductor extends Serializable {

	static final String KEY_NUM_EXPEDIENTE = "keyNumExpediente";
	static final String KEY_CONDUCTOR_DTO = "keyConductorDto";
	public final String NOMBRE_HOST_SOLICITUD_NODO_2 = "nombreHostSolicitudProcesos2";

	ResultConsultaConductorBean guardarConductor(ConductorDto conductorDto, BigDecimal idUsuario);
	ResultConsultaConductorBean getConductorDto (BigDecimal numExpediente);	
	ConductorVO getConductorPorExpVO (BigDecimal numExpediente, Boolean tramiteCompleto);
	ResultConsultaConductorBean validarConductor(ConductorDto conductorDto, BigDecimal idUsuario);
	ResultConsultaConductorBean consultarConductor(BigDecimal bigDecimal, BigDecimal idUsuario);
	ResultConsultaConductorBean cambiarEstado(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal estadoNuevo,
			Boolean accionesAsociadas);
	ConductorVO getConductorPorIdConsulta(BigDecimal idConductor, Boolean tramiteCompleto);
	ConductorDto getConductorDtoPorIdDto(BigDecimal idConductor, Boolean tramiteCompleto);
	ResultConsultaConductorBean generarXmlApp(BigDecimal numExpediente);

	void cambiarEstadoProceso(BigDecimal idConsultaConductor, BigDecimal idUsuario, BigDecimal estadoNuevo, String respuesta, String numRegistro);
	ResultConsultaConductorBean modificarConductor(BigDecimal numExpediente, BigDecimal idUsuario, Date dateIni, Date dateFin);
	ResultConsultaArrendatarioBean guardarConductorProceso(BigDecimal numExpediente, BigDecimal idUsuario,
			BigDecimal estadoNuevo, String respuesta, String numRegistro);

}