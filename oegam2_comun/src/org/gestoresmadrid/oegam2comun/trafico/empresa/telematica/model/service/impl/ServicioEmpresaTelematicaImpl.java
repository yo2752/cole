package org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.model.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.trafico.empresa.telematica.model.dao.EmpresaTelematicaDao;
import org.gestoresmadrid.core.trafico.empresa.telematica.model.vo.EmpresaTelematicaVO;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.model.service.ServicioEmpresaTelematica;
import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.view.beans.EstadoEmpresaTelematica;
import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.view.beans.ResultadoEmpresaTelematicaBean;
import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.view.dto.EmpresaTelematicaDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioEmpresaTelematicaImpl implements ServicioEmpresaTelematica {

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioEmpresaTelematicaImpl.class);

	@Autowired
	private Conversor conversor;

	@Autowired
	EmpresaTelematicaDao empresaDao;

	@Autowired
	ServicioContrato servicioContrato;

	@Override
	@Transactional
	public ResultBean comprobarEmpresaTelematica(String nombreEmpresa, String cifEmpresa, String codPostal,
			String numColegiado, Long idContrato, String idMunicipio, String idProvinca) {

		List<EmpresaTelematicaVO> lista = null;
		ResultBean salida = new ResultBean(Boolean.FALSE);
		try {
			lista = empresaDao.getListaEmpresas(nombreEmpresa, cifEmpresa, codPostal, numColegiado, idContrato, idMunicipio, idProvinca);
			if (lista.size() == 1) {
				salida.addAttachment("listaEmpresas", lista);
				return salida;
			} else {
				salida.setError(Boolean.TRUE);
			}

		} catch (Exception e) {
			salida.setError(Boolean.TRUE);
			salida.setMensaje("Ha sucedido un error a la hora de consultar las empresas telemáticas " + e);
		}

		return salida;
	}

	@Override
	@Transactional(readOnly=true)
	public ResultadoEmpresaTelematicaBean getEmpresaTelematicaPantalla(String id) {
		ResultadoEmpresaTelematicaBean resultado = new ResultadoEmpresaTelematicaBean(Boolean.FALSE);
		try {
			if(id != null){
				EmpresaTelematicaVO empresaTelematicaVO = getEmpresaTelematicaVO(id);
				if (empresaTelematicaVO != null) {
					EmpresaTelematicaDto empresaTelematica = conversor.transform(empresaTelematicaVO, EmpresaTelematicaDto.class);
					resultado.setEmpresaTelematica(empresaTelematica);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("No existe una empresa telemática para el id: " + id + ".");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensajeError("No se puede obtener una empresa telemática con el id vacío.");
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de obtener una empresa telemática, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de obtener una empresa telemática.");
		}
		return resultado;
	}

	@Override
	public EmpresaTelematicaVO getEmpresaTelematicaVO(String id) {
		if (id != null) {
			return empresaDao.getEmpresa(id);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultadoEmpresaTelematicaBean guardarEmpresaTelematica(EmpresaTelematicaDto empresaTelematicaDto, BigDecimal idUsuario, String numColegiado) {
		ResultadoEmpresaTelematicaBean resultado = new ResultadoEmpresaTelematicaBean(Boolean.FALSE);
		try {
			ContratoDto contrato = servicioContrato.getContratoDto(new BigDecimal(empresaTelematicaDto.getIdContrato()));
			empresaTelematicaDto.setNumColegiado(contrato.getColegiadoDto().getNumColegiado());
			resultado = validarDatosMinimosGuardado(empresaTelematicaDto);
			if (!resultado.getError()) {
				EmpresaTelematicaVO empresaTelematica = conversor.transform(empresaTelematicaDto, EmpresaTelematicaVO.class);
				if (empresaTelematica != null) {
					if (!resultado.getError()) {
						empresaTelematica.setEstado(new Long(EstadoEmpresaTelematica.Activo.getValorEnum()));
						empresaTelematicaDto.setEstado(EstadoEmpresaTelematica.Activo.getValorEnum());
						if (empresaTelematica.getId() != null) {
							resultado = actualizarEmpresaTelematica(empresaTelematica);
						} else {
							empresaTelematica.setFechaAlta(new Date());
							resultado = altaNuevaEmpresaTelematica(empresaTelematica);
						}
						if (!resultado.getError()) {
							resultado.setId(empresaTelematica.getId());
						}
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensajeError("Ha sucedido un error a la hora de convertir la empresa telemática de pantalla.");
				}
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de guardar la empresa telemática, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de guardar la empresa telemática.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoEmpresaTelematicaBean actualizarEmpresaTelematica(EmpresaTelematicaVO empresaTelematica) {
		ResultadoEmpresaTelematicaBean resultado = new ResultadoEmpresaTelematicaBean(Boolean.FALSE);
		empresaDao.actualizar(empresaTelematica);

		return resultado;
	}

	private ResultadoEmpresaTelematicaBean validarDatosMinimosGuardado(EmpresaTelematicaDto empresaTelematicaDto) {
		ResultadoEmpresaTelematicaBean resultado = new ResultadoEmpresaTelematicaBean(Boolean.FALSE);
		if (empresaTelematicaDto == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Debe de rellenar algún dato de la empresa telemática para poder realizar su guardado.");
		} else if (empresaTelematicaDto.getNumColegiado() == null || empresaTelematicaDto.getNumColegiado().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("No se puede añadir la empresa telemática. Es obligatorio el número de colegiado");
		} else if (empresaTelematicaDto.getEmpresa() == null || empresaTelematicaDto.getEmpresa().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("No se puede añadir la empresa telemática. Es obligatorio el nombre");
		} else if (empresaTelematicaDto.getCifEmpresa() == null || empresaTelematicaDto.getCifEmpresa().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("No se puede añadir la empresa telemática. Es obligatorio el CIF");
		} else if (empresaTelematicaDto.getCodigoPostal() == null || empresaTelematicaDto.getCodigoPostal().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("No se puede añadir la empresa telemática. Es obligatorio el código postal");
		} else if (empresaTelematicaDto.getMunicipio() == null || empresaTelematicaDto.getMunicipio().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("No se puede añadir la empresa telemática. Es obligatorio el municipio");
		} else if (empresaTelematicaDto.getProvincia() == null || empresaTelematicaDto.getProvincia().isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("No se puede añadir la empresa telemática. Es obligatorio la provincia");
		}

		return resultado;
	}

	private ResultadoEmpresaTelematicaBean altaNuevaEmpresaTelematica(EmpresaTelematicaVO empresaTelematica) throws Exception {
		ResultadoEmpresaTelematicaBean resultado = new ResultadoEmpresaTelematicaBean(Boolean.FALSE);
		try {
			empresaDao.guardar(empresaTelematica);
		} catch (Exception e) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Error a la hora de guardar la empresa telemática");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoEmpresaTelematicaBean cambiarEstados(String codSeleccionados, String estadoNuevo, BigDecimal usuario) {
		ResultadoEmpresaTelematicaBean salida = new ResultadoEmpresaTelematicaBean(Boolean.FALSE);

		try {
			for (String elemento : codSeleccionados.split("-")) {
				if (modificarEstado(elemento, estadoNuevo)) {
					salida.getListaMensajeOK().add("Se ha cambiado correctamente el estado de la empresa con id " + elemento);
				} else {
					salida.getListaMensajeError().add("No se ha podido actualizar el estado de la empresa con id " + elemento);
				}
			}
		} catch (Exception e) {
			salida.setError(Boolean.TRUE);
			salida.setMensajeError("No se ha podido actualizar los cambios de estado");
		}

		return salida;
	}

	private boolean modificarEstado(String codigo, String nuevoEstado) {
		try {
			EmpresaTelematicaVO empresa = getEmpresaTelematicaVO(codigo);
			empresa.setEstado(new Long(nuevoEstado));
			empresaDao.actualizar(empresa);
		} catch (Throwable e) {
			return false;
		}

		return true;
	}

	@Override
	@Transactional
	public Boolean esEmpresaTelematica(String nombreEmpresa, String cifEmpresa, String codPostal, String numColegiado, Long idContrato, String idMunicipio, String idProvincia) {
		List<EmpresaTelematicaVO> lista = null;
		try {
			lista = empresaDao.getListaEmpresas(nombreEmpresa, cifEmpresa, codPostal, numColegiado, idContrato, idMunicipio, idProvincia);
			if (lista.size() == 1 || lista.size() > 1) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de comprobar la empresa supertelematica, error: ",e);
		}
		return Boolean.FALSE;
	}

	@Transactional(readOnly=true)
	@Override
	public List<EmpresaTelematicaDto> getListaEmpresasSTContrato(Long idContrato) {
		try {
			List<EmpresaTelematicaVO> lista = empresaDao.getListaEmpresasContrato(idContrato);
			if (lista != null && !lista.isEmpty()) {
				return conversor.transform(lista, EmpresaTelematicaDto.class);
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la la hora de obtener la lista de empresas supertelematica para el contrato: " + idContrato + ", error: ", e);
		}
		return Collections.emptyList();
	}
}