package org.gestoresmadrid.oegamComun.contrato.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.dao.ContratoDao;
import org.gestoresmadrid.core.contrato.model.enumerados.EstadoUsuario;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.contratoPreferencias.model.dao.ContratoPreferenciasDao;
import org.gestoresmadrid.core.docbase.enumerados.OrdenDocBaseEnum;
import org.gestoresmadrid.core.general.model.dao.ContratoUsuarioDao;
import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioPK;
import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.oegamComun.contrato.service.ServicioComunContrato;
import org.gestoresmadrid.oegamComun.contrato.service.ServicioEvolucionContrato;
import org.gestoresmadrid.oegamComun.evolucionUsuario.service.ServicioEvolucionUsuario;
import org.gestoresmadrid.oegamComun.usuario.service.ServicioComunUsuario;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioComunContratoImpl implements ServicioComunContrato{

	private static final long serialVersionUID = 3737815327749591371L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioComunContratoImpl.class);
	
	@Autowired
	ContratoDao contratoDao;
	
	@Autowired
	ContratoPreferenciasDao contratoPreferenciasDao;
	
	@Autowired
	ServicioComunUsuario servicioComunUsuario;
	
	@Autowired
	ContratoUsuarioDao contratoUsuarioDao;
	
	@Autowired 
	ServicioEvolucionContrato servicioEvolucionContrato;
	
	@Autowired
	ServicioEvolucionUsuario servicioEvolucionUsuario;
	
	@Override
	@Transactional
	public ResultadoBean asociarUsuarioContrato(String[] idUsuarios, Long idContrato) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		ContratoUsuarioVO cont;
		try {
			ContratoVO contratoVO = getContrato(idContrato);
			if (contratoVO != null) {
				for (String elemento : idUsuarios) {

					ContratoUsuarioPK contratopk = new ContratoUsuarioPK();
					contratopk.setIdContrato(idContrato);
					contratopk.setIdUsuario(elemento);
					// Comprobar si ya existe el usuario en el contrato , en ese caso no se guardará.
					UsuarioVO usuarioSession = servicioComunUsuario.getUsuario(new Long(elemento));
					// añadir la evolucion
					// Timestamp fecha = new Timestamp(Calendar.getInstance().getTimeInMillis());
					Calendar fechaCambio = Calendar.getInstance();

					cont = buscaUsuarioContrato(contratopk.getIdUsuario(), contratopk.getIdContrato());
					if (cont == null) {
						ContratoUsuarioVO contratoUsuario = new ContratoUsuarioVO();
						contratoUsuario.setId(contratopk);
						contratoUsuario.setEstadoUsuarioContrato(BigDecimal.ONE);
						contratoUsuarioDao.guardar(contratoUsuario);
						servicioEvolucionUsuario.guardarEvolucionUsuario(new BigDecimal(elemento), new Timestamp(fechaCambio.getTimeInMillis()), idContrato, TipoActualizacion.CRE, new BigDecimal(
								EstadoUsuario.Asociado.getValorEnum()), new BigDecimal(EstadoUsuario.SinAsociar.getValorEnum()), null, usuarioSession.getNif());
						fechaCambio.add(Calendar.SECOND, 1);

					} else { // Actualizar el estado a 1
								// Comprobar si el usuario ya esta agregado al contrato
						if (cont.getEstadoUsuarioContrato().equals(BigDecimal.ZERO) || cont.getEstadoUsuarioContrato().equals(new BigDecimal("6"))) {
							cont.setEstadoUsuarioContrato(BigDecimal.ONE);
							contratoUsuarioDao.actualizar(cont);
							servicioEvolucionUsuario.guardarEvolucionUsuario(new BigDecimal(elemento), new Timestamp(fechaCambio.getTimeInMillis()), idContrato, TipoActualizacion.MOD, new BigDecimal(
									EstadoUsuario.Asociado.getValorEnum()), new BigDecimal(EstadoUsuario.SinAsociar.getValorEnum()), idContrato, usuarioSession.getNif());
							fechaCambio.add(Calendar.SECOND, 1);
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("El usuario ya se ha asociado al contrato ");
						}
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos para el contrato: " + idContrato);
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de agregar  el usuario del contrato con id: " + idContrato + ",error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de agregar el usuario del contrato: " + idContrato);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}
	
	@Override
	@Transactional(readOnly=true)
	public ContratoUsuarioVO buscaUsuarioContrato(String idUsuario, Long idContrato) {
		try {
			return contratoUsuarioDao.getContratoUsuario(idUsuario, idContrato);
		} catch (Exception e) {
			log.error("Error al obtener el contrato colegiado", e);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public ContratoVO getContrato(Long idContrato) {
		try {
			return contratoDao.getContrato(idContrato.longValue(), false);
		} catch (Exception e) {
			log.error("Error al obtener los datos del contrato: " + idContrato + ", error:" , e);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public OrdenDocBaseEnum obtenerOrdenDocBase(Long idContrato) {
		BigDecimal ordenDocBase = contratoPreferenciasDao.obtenerOrdenDocBase(idContrato);
		if(ordenDocBase != null){
			return OrdenDocBaseEnum.convertir(ordenDocBase.toString());
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<ContratoVO> getContratosPorColegiado(String numColegiado, BigDecimal estado) {
		try {
			return contratoDao.getListaContratosColegiado(numColegiado, estado);
		} catch (Exception e) {
			log.error("Error al obtener los  contratos del colegiado: " + numColegiado + ", error:" , e);
		}
		return Collections.emptyList();
	}
	@Override
	@Transactional(readOnly=true)
	public ContratoVO getContratoPorColegiado(String numColegiado) {
		try {
			ContratoVO contrato = contratoDao.getContratosPorColegiado(numColegiado).get(0);
			return contrato; 
		} catch (Exception e) {
			log.error("Error al obtener el  contrato del colegiado: " + numColegiado + ", error:" , e);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public ContratoUsuarioVO getContratoPorUsuario(String idUsuario) {
		try {
			return contratoUsuarioDao.getContratosPorUsuario(new BigDecimal(idUsuario)).get(0);
		} catch (Exception e) {
			log.error("Error al obtener el contrato usuario", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public List<ContratoUsuarioVO> getContratosPorUsuario(String idUsuario) {
		try {
			return contratoUsuarioDao.getContratosPorUsuario(new BigDecimal(idUsuario));
		} catch (Exception e) {
			log.error("Error al obtener el contrato usuario", e);
		}
		return null;
	}
	

	@Override
	@Transactional(readOnly=true)
	public String getJefaturaContrato(Long idContrato) {
		String jefatura = "";
		try {
			jefatura = contratoDao.getJefaturaContrato(idContrato);
		} catch (Exception ex) {
			log.error("Error al recuperar la jefatura por idContrato: ", ex);
		}
		return jefatura;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<DatoMaestroBean> getComboContratosHabilitados() {
		return contratoDao.getComboContratosHabilitados();
	}

}
