package org.gestoresmadrid.oegam2comun.EmpresaDIRe.model.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.empresaDIRe.model.dao.EmpresaDIRe_AdministradorDao;
import org.gestoresmadrid.core.empresaDIRe.model.vo.EmpresaDIRe_AdministradorVO;
import org.gestoresmadrid.oegam2comun.EmpresaDIRe.model.service.ServicioEmpresa_AdministradorDIRe;
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
public class ServicioEmpresa_AdministradorDIReImpl implements ServicioEmpresa_AdministradorDIRe {

	private static final long serialVersionUID = 7752268719631460462L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEmpresa_AdministradorDIReImpl.class);
	@Autowired
	Conversor conversor;
	@Autowired
	ServicioContrato servicioContrato;

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
	@Autowired
	EmpresaDIRe_AdministradorDao empresaDIRe_AdministradorDao;

	@Override
	@Transactional(readOnly = true)
	public EmpresaDIRe_AdministradorVO getEmpresaDIRe_AdministradorPorExpediente1(BigDecimal numExpediente,
			Boolean tramiteCompleto) {
		return empresaDIRe_AdministradorDao.buscarPorExpediente(numExpediente);
	}

	@Override
	@Transactional(readOnly = true)
	public List<EmpresaDIRe_AdministradorVO> getEmpresaDIRe_AdministradorPorExpediente(BigDecimal numExpediente) {
		List<EmpresaDIRe_AdministradorVO> lista;
		EmpresaDIRe_AdministradorVO temporal = new EmpresaDIRe_AdministradorVO();
		temporal.setNumExpediente(numExpediente);
		lista = empresaDIRe_AdministradorDao.buscar(temporal);
		return lista;
	}

	@Transactional
	public int Borrar_Administrador(EmpresaDIRe_AdministradorVO objeto) {
		empresaDIRe_AdministradorDao.borrar(objeto);
		return 0;
	}

	@Transactional
	public int Guardar_Administrador(EmpresaDIRe_AdministradorVO objeto) {
		empresaDIRe_AdministradorDao.Guardar_Administrador(objeto);
		return 0;
	}

	@Override
	public EmpresaDIRe_AdministradorVO getEmpresaDIRe_AdministradorPorExpediente(BigDecimal numExpediente,
			Boolean tramiteCompleto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmpresaDIRe_AdministradorVO getEmpresaDIRe_ContactoPorExpediente(BigDecimal numExpediente,
			Boolean tramiteCompleto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmpresaDIRe_AdministradorVO> getEmpresaDIRe_ContactosPorExpediente(BigDecimal numExpediente) {
		// TODO Auto-generated method stub
		return null;
	}

	public static ILoggerOegam getLog() {
		return log;
	}

}