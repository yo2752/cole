package org.gestoresmadrid.oegam2comun.EmpresaDIRe.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.empresaDIRe.model.vo.EmpresaDIRe_AdministradorVO;

public interface ServicioEmpresa_AdministradorDIRe extends Serializable {

	EmpresaDIRe_AdministradorVO getEmpresaDIRe_AdministradorPorExpediente(BigDecimal numExpediente, Boolean tramiteCompleto);

	List<EmpresaDIRe_AdministradorVO> getEmpresaDIRe_AdministradorPorExpediente(BigDecimal numExpediente);

	int Guardar_Administrador(EmpresaDIRe_AdministradorVO objeto);

	int Borrar_Administrador(EmpresaDIRe_AdministradorVO objeto);

	EmpresaDIRe_AdministradorVO getEmpresaDIRe_ContactoPorExpediente(BigDecimal numExpediente, Boolean tramiteCompleto);

	List<EmpresaDIRe_AdministradorVO> getEmpresaDIRe_ContactosPorExpediente(BigDecimal numExpediente);

	EmpresaDIRe_AdministradorVO getEmpresaDIRe_AdministradorPorExpediente1(BigDecimal numExpediente, Boolean tramiteCompleto);

}