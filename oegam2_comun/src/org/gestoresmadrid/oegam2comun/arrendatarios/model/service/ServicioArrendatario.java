package org.gestoresmadrid.oegam2comun.arrendatarios.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.arrendatarios.model.vo.ArrendatarioVO;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.beans.ResultConsultaArrendatarioBean;
import org.gestoresmadrid.oegam2comun.arrendatarios.view.dto.ArrendatarioDto;

public interface ServicioArrendatario extends Serializable {
	
	static final String KEY_NUM_EXPEDIENTE = "keyNumExpediente";
	static final String KEY_ARRENDATARIO_DTO = "keyArrendatarioDto";
	public final String NOMBRE_HOST_SOLICITUD_NODO_2 = "nombreHostSolicitudProcesos2";

	ResultConsultaArrendatarioBean guardarArrendatario(ArrendatarioDto arrendatarioDto, BigDecimal idUsuario);

	ResultConsultaArrendatarioBean getArrendatarioDto(BigDecimal numExpediente);
	
	ResultConsultaArrendatarioBean consultarArrendatario(BigDecimal numExpediente, BigDecimal idUsuario);

	ResultConsultaArrendatarioBean validarArrendatario(ArrendatarioDto arrendatarioDto, BigDecimal idUsuario);	
	
	ArrendatarioDto getArrendatarioDtoPorIdDto(BigDecimal idArrendatario, Boolean tramiteCompleto);
	
	ArrendatarioVO getArrendatarioPorIdConsulta(BigDecimal idArrendatario, Boolean tramiteCompleto);

    ResultConsultaArrendatarioBean cambiarEstado(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal estadoNuevo,
            Boolean accionesAsociadas);

    ArrendatarioVO getArrendatarioPorExpVO(BigDecimal numExpediente, Boolean tramiteCompleto);

	ResultConsultaArrendatarioBean guardarArrendatarioProceso(BigDecimal idConsultaConductor, BigDecimal idUsuario,
			BigDecimal estadoNuevo, String respuesta, String numRegistro);

	ResultConsultaArrendatarioBean modificarArrendatario(BigDecimal numExpediente, BigDecimal idUsuario, Date Fecha,
      Date Fecha2);


	void cambiarEstadoProceso(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal estadoNuevo, String respuesta,
			String numRegistro);


	
}

