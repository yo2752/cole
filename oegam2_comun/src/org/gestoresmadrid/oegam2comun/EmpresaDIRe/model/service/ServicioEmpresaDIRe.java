package org.gestoresmadrid.oegam2comun.EmpresaDIRe.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.empresaDIRe.model.vo.EmpresaDIReVO;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.beans.ConsultaEmpresaDIReBean;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.beans.ResultConsultaEmpresaDIReBean;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.view.dto.EmpresaDIReDto;

import utilidades.estructuras.Fecha;

public interface ServicioEmpresaDIRe extends Serializable {

	ResultConsultaEmpresaDIReBean guardarEmpresaDIRe(EmpresaDIReDto empresaDIReDto, BigDecimal idUsuarioDeSesion);

	ResultConsultaEmpresaDIReBean validarEmpresaDIRe(EmpresaDIReDto empresaDIReDto, BigDecimal idUsuarioDeSesion);

	ResultConsultaEmpresaDIReBean getEmpresaDIReDto(BigDecimal numExpediente);

	ResultConsultaEmpresaDIReBean consultarEmpresaDIRe(BigDecimal numExpediente, BigDecimal idUsuarioDeSesion);

	ResultConsultaEmpresaDIReBean consultar(String codSeleccionados, BigDecimal idUsuarioDeSesion);

	ResultConsultaEmpresaDIReBean eliminar(String codSeleccionados, BigDecimal usuario);

	ResultConsultaEmpresaDIReBean cambiarEstado(String codSeleccionados, String estadoNuevo,
			BigDecimal idUsuarioDeSesion);

	ResultConsultaEmpresaDIReBean validar(String codSeleccionados, BigDecimal idUsuarioDeSesion);

	ResultConsultaEmpresaDIReBean modificarFechas(String codSeleccionados, BigDecimal idUsuarioDeSesion,
			Fecha fechaIniModificacion, Fecha fechaFinModificacion);

	EmpresaDIReDto getEmpresaDIRePorIdDto(BigDecimal idEmpresaDIRe, Boolean tramiteCompleto);

	void cambiarEstadoProceso(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal estadoNuevo, String respuesta,
			String numRegistro);

	EmpresaDIReVO getEmpresaDIRePorExpVO(BigDecimal numExpediente, Boolean tramiteCompleto);

	ResultConsultaEmpresaDIReBean validarArrendatario(EmpresaDIReDto empresaDIReDto, BigDecimal idUsuario);

	EmpresaDIReVO getEmpresaDIRePorIdConsulta(BigDecimal idArrendatario, Boolean tramiteCompleto);

	ResultConsultaEmpresaDIReBean cambiarEstado(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal estadoNuevo,
			Boolean accionesAsociadas);

	ResultConsultaEmpresaDIReBean guardarArrendatarioProceso(BigDecimal numExpediente, BigDecimal idUsuario,
			BigDecimal estadoNuevo, String respuesta, String numRegistro);

	ResultConsultaEmpresaDIReBean modificarEmpresaDIRe(BigDecimal numExpediente, BigDecimal idUsuario, Date dateIni,
			Date dateFin);


	List<ConsultaEmpresaDIReBean> convertirListaEnBeanPantalla(
			List<EmpresaDIReVO> list);
	
	
	
	
}

