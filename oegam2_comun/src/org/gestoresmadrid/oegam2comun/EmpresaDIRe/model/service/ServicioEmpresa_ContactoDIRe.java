package org.gestoresmadrid.oegam2comun.EmpresaDIRe.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.empresaDIRe.model.vo.EmpresaDIRe_ContactoVO;

public interface ServicioEmpresa_ContactoDIRe extends Serializable {

	EmpresaDIRe_ContactoVO getEmpresaDIRe_ContactoPorExpediente(BigDecimal numExpediente, Boolean tramiteCompleto);

	List<EmpresaDIRe_ContactoVO> getEmpresaDIRe_ContactosPorExpediente(BigDecimal numExpediente);

	int Guardar_Contacto(EmpresaDIRe_ContactoVO objeto);

	int Borrar_Contacto(EmpresaDIRe_ContactoVO objeto);

}