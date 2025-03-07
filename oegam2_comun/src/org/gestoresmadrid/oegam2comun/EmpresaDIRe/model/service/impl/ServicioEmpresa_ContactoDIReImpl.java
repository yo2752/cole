package org.gestoresmadrid.oegam2comun.EmpresaDIRe.model.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.empresaDIRe.model.dao.EmpresaDIRe_ContactoDao;
import org.gestoresmadrid.core.empresaDIRe.model.vo.EmpresaDIRe_ContactoVO;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.model.service.ServicioEmpresa_ContactoDIRe;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.evolucionCayc.model.service.ServicioEvolucionCayc;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioEvolucionPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEmpresa_ContactoDIReImpl implements ServicioEmpresa_ContactoDIRe {

	private static final long serialVersionUID = 7752268719631460462L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEmpresa_ContactoDIReImpl.class);
	@Autowired
	Conversor conversor;
	@Autowired
	ServicioContrato servicioContrato;
	@Autowired
	EmpresaDIRe_ContactoDao empresaDIRe_ContactoDao;
	@Autowired
	ServicioPersona servicioPersona;
	@Autowired
	ServicioUsuario servicioUsuario;
	@Autowired
	ServicioDireccion servicioDireccion;
	@Autowired
	ServicioEvolucionPersona servicioEvolucionPersona;
	@Autowired
	ServicioCola servicioCola;
	@Autowired
	ServicioEvolucionCayc servicioEvolucionCayc;

	@Override
	@Transactional(readOnly = true)
	public EmpresaDIRe_ContactoVO getEmpresaDIRe_ContactoPorExpediente(BigDecimal numExpediente,
			Boolean tramiteCompleto) {
		return empresaDIRe_ContactoDao.buscarPorExpediente(numExpediente);
	}

	@Override
	@Transactional(readOnly = true)
	public List<EmpresaDIRe_ContactoVO> getEmpresaDIRe_ContactosPorExpediente(BigDecimal numExpediente) {
		List<EmpresaDIRe_ContactoVO> lista;
		EmpresaDIRe_ContactoVO temporal = new EmpresaDIRe_ContactoVO();
		temporal.setNumExpediente(numExpediente);
		lista = empresaDIRe_ContactoDao.buscar(temporal);
		return lista;
	}

	@Transactional
	public int Guardar_Contacto(EmpresaDIRe_ContactoVO objeto) {

		empresaDIRe_ContactoDao.Guardar_Contacto(objeto);
		return 0;
	}

	@Transactional
	public int Borrar_Contacto(EmpresaDIRe_ContactoVO objeto) {
		empresaDIRe_ContactoDao.borrar(objeto);
		return 0;
	}

}