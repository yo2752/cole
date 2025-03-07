package org.gestoresmadrid.oegam2comun.mandato.model.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.mandato.model.dao.MandatoDao;
import org.gestoresmadrid.core.mandato.model.vo.MandatoVO;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.personas.model.dao.PersonaDao;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.personas.model.vo.PersonaPK;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.mandato.model.service.ServicioEvolucionMandato;
import org.gestoresmadrid.oegam2comun.mandato.model.service.ServicioMandato;
import org.gestoresmadrid.oegam2comun.mandato.view.dto.MandatoDto;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioEvolucionPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.personas.view.dto.EvolucionPersonaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioMandatoImpl implements ServicioMandato {

	private static final long serialVersionUID = 7647122056031460029L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMandatoImpl.class);

	@Autowired
	Conversor conversor;

	@Autowired
	MandatoDao mandatoDao;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	ServicioPersona servicioPersona;

	@Autowired
	ServicioEvolucionPersona servicioEvolucionPersona;

	@Autowired
	ServicioEvolucionMandato servicioEvolucionMandato;

	@Autowired
	PersonaDao personaDao;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional(readOnly = true)
	public MandatoVO getMandato(Long idMandato) {
		MandatoVO mandato = null;
		try {
			mandato = mandatoDao.getMandato(idMandato);
		} catch (Exception e) {
			log.error("Error al obtener el colegiado: " + idMandato, e.getMessage());
		} finally {
			mandatoDao.evict(mandato);
		}
		return mandato;
	}

	@Override
	@Transactional(readOnly = true)
	public MandatoDto getMandatoDto(Long idMandato) {
		MandatoDto mandato = null;
		try {
			MandatoVO mandatoVO = getMandato(idMandato);
			if (mandatoVO != null) {
				mandato = conversor.transform(mandatoVO, MandatoDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener el mandato: " + idMandato, e.getMessage());
		}
		return mandato;
	}

	@Override
	@Transactional
	public ResultBean guardarMandato(MandatoDto mandatoDto, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		boolean nuevo = true;
		String codigoMandato = "";
		try {
			ContratoVO contrato = servicioContrato.getContrato(new BigDecimal(mandatoDto.getIdContrato()));
			mandatoDto.setNumColegiado(contrato.getColegiado().getNumColegiado());
			// Obtenemos el numero de mandato
			// String codigoMandato = servicioPersona.obtenerCodigoMandato(mandatoDto.getNumColegiado());
			if (mandatoDto.getCodigoMandato() != null && !mandatoDto.getCodigoMandato().isEmpty()) {
				codigoMandato = mandatoDto.getCodigoMandato();
			} else {
				codigoMandato = obtenerCodigoMandato(mandatoDto.getNumColegiado());
			}
			mandatoDto.setCodigoMandato(codigoMandato);
			if (mandatoDto.getIdMandato() != null) {
				nuevo = false;
			}
			MandatoVO mandatoVO = conversor.transform(mandatoDto, MandatoVO.class);
			rellenarDatosGuardadoInicial(mandatoVO, nuevo);
			resultado = guardar(mandatoVO);
			if (resultado != null && !resultado.getError()) {
				mandatoDto.setVisible(mandatoVO.getVisible());
				mandatoDto.setIdMandato(mandatoVO.getIdMandato());
				guardarEvolucion(mandatoVO.getIdMandato(), mandatoVO.getCodigoMandato(), idUsuario, "", nuevo);
				guardarCondigoMandatoPersona(mandatoDto.getCodigoMandato(), mandatoDto.getNumColegiado(), mandatoDto.getCif(), mandatoDto.getEmpresa(), idUsuario);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el mandato , error: ", e.getMessage());
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el mandato.");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private String obtenerCodigoMandato(String numColegiado) {
		return mandatoDao.obtenerCodigoMandato(numColegiado);
	}

	private ResultBean guardar(MandatoVO mandatoVo) {
		ResultBean resultado = new ResultBean(false);
		try {
			mandatoDao.guardarOActualizar(mandatoVo);
		} catch (Exception e) {
			resultado.setError(true);
			resultado.setMensaje("Error de BD al dar de alta el mandato:" + e.getMessage());
		}
		return resultado;
	}

	private void guardarEvolucion(Long idMandato, String codigoMandato, BigDecimal idUsuario, String descripcion, boolean nuevo) {
		try {
			TipoActualizacion tipoActuacion = TipoActualizacion.MOD;
			if (nuevo) {
				tipoActuacion = TipoActualizacion.CRE;
			}
			servicioEvolucionMandato.guardarEvolucionMandato(idMandato, codigoMandato, idUsuario, tipoActuacion, "");
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la evoluciónd del mandato, error: ", e.getMessage());
		}
	}

	public void rellenarDatosGuardadoInicial(MandatoVO mandatoVo, boolean nuevo) throws Exception {
		if (mandatoVo.getFechaAlta() == null) {
			Date fecha = new Date();
			mandatoVo.setFechaAlta(fecha);
		}
		if (nuevo) {
			mandatoVo.setVisible(Boolean.TRUE);
		}
	}

	@Override
	@Transactional
	public ResultBean eliminarVariosMandatos(String[] idMandatos, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			for (String idMandatoString : idMandatos) {
				ResultBean result = eliminar(new Long(idMandatoString), idUsuario);
				if (result != null) {
					if (result.getError()) {
						resultado.setError(true);
					}

					if (result.getMensaje() != null && !result.getMensaje().isEmpty()) {
						resultado.addMensajeALista(result.getMensaje());
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar mandatos, error: ", e.getMessage());
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de eliminar mandatos.");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean eliminar(Long idMandato, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(false);
		try {
			MandatoVO mandatoVO = getMandato(idMandato);
			mandatoVO.setVisible(Boolean.FALSE);
			if (!mandatoDao.borrar(mandatoVO)) {
				resultado.setMensaje("El mandato con id " + idMandato + " no se ha borrado");
			} else {
				guardarEvolucion(idMandato, mandatoVO.getCodigoMandato(), idUsuario, "Eliminado", false);
				// Eliminamos el código persona de la persona
				guardarCondigoMandatoPersona(null, mandatoVO.getNumColegiado(), mandatoVO.getCif(), "", idUsuario);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar mandatos, error: ", e.getMessage());
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de eliminar mandatos.");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private void guardarCondigoMandatoPersona(String codigoMandato, String numColegiado, String cif, String empresa, BigDecimal idUsuario) {
		String codigoMandatoAntiguo = null;
		boolean personaNueva = false;
		// Comprobamos que el CIF este en la tabla persona
		PersonaVO personaVO = servicioPersona.getPersonaVO(cif, numColegiado);

		if (personaVO == null) {
			personaNueva = true;
			personaVO = new PersonaVO();
			PersonaPK id = new PersonaPK();
			id.setNif(cif);
			id.setNumColegiado(numColegiado);
			personaVO.setCodigoMandato(codigoMandato);
			personaVO.setEstado(new Long(1));
			personaVO.setApellido1RazonSocial(empresa);
			personaVO.setId(id);
			personaVO.setTipoPersona(TipoPersona.Juridica.getValorEnum());
			personaDao.guardar(personaVO);
		} else {
			codigoMandatoAntiguo = personaVO.getCodigoMandato();
			personaVO.setCodigoMandato(codigoMandato);
			personaDao.actualizar(personaVO);
		}

		EvolucionPersonaDto evolucionPersonaDto = new EvolucionPersonaDto();
		evolucionPersonaDto.setFechaHora(utilesFecha.getFechaHoraActualLEG());
		evolucionPersonaDto.setNumColegiado(numColegiado);
		evolucionPersonaDto.setNif(cif);
		evolucionPersonaDto.setTipoTramite(ServicioPersona.TIPO_TRAMITE_MANTENIMIENTO);
		
		if (codigoMandato == null) {
			if (codigoMandatoAntiguo == null) {
				evolucionPersonaDto.setOtros("Eliminación Código Mandato desde Mandatos");
			} else {
				evolucionPersonaDto.setOtros("Eliminación Código Mandato desde Mandatos (Cod.Man antiguo " + codigoMandatoAntiguo + ")");
			}
		} else {
			if (codigoMandatoAntiguo == null) {
				evolucionPersonaDto.setOtros("Nuevo Código Mandato desde Mandatos");
			} else {
				evolucionPersonaDto.setOtros("Modificación Código Mandato desde Mandatos (Cod.Man antiguo " + codigoMandatoAntiguo + ")");
			}
		}
		
		UsuarioDto usuario = new UsuarioDto();
		usuario.setIdUsuario(idUsuario);
		evolucionPersonaDto.setUsuario(usuario);
		
		if (personaNueva) {
			evolucionPersonaDto.setTipoActuacion(TipoActualizacion.CRE.getNombreEnum());
		} else {
			evolucionPersonaDto.setTipoActuacion(TipoActualizacion.MOD.getNombreEnum());
		}
		
		servicioEvolucionPersona.guardarEvolucion(evolucionPersonaDto);
	}
}
