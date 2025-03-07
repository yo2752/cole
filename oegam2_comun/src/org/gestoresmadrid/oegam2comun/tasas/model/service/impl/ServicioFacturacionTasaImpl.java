package org.gestoresmadrid.oegam2comun.tasas.model.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersonaDireccion;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioFacturacionTasa;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioPersistenciaTasaPegatina;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioFacturacion;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTrafFacturacionDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioFacturacionTasaImpl implements ServicioFacturacionTasa {

	private static final long serialVersionUID = -4110473497734257312L;

	protected static final ILoggerOegam log = LoggerOegam.getLogger(ServicioFacturacionTasaImpl.class);

	@Autowired
	private ServicioFacturacion servicioFacturacion;

	@Autowired
	private ServicioPersistenciaTasaPegatina servicioPersistenciaTasaPegatina;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioPersonaDireccion servicioPersonaDireccion;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public TramiteTrafFacturacionDto getFacturacionTasa(BigDecimal numExpediente, String codigoTasa) {
		TramiteTrafFacturacionDto tramiteTrafFacturacionDto = null;
		try {
			tramiteTrafFacturacionDto = servicioFacturacion.getTramiteTraficoFact(numExpediente, codigoTasa);
			if (tramiteTrafFacturacionDto != null) {
				PersonaDto personaDto = servicioPersona.getPersona(tramiteTrafFacturacionDto.getNif(), tramiteTrafFacturacionDto.getNumColegiado());
				if (personaDto != null) {
					tramiteTrafFacturacionDto.setPersona(personaDto);
					ResultBean resultado = servicioPersonaDireccion.buscarPersonaDireccionDto(personaDto.getNumColegiado(), personaDto.getNif());
					if (!resultado.getError()) {
						PersonaDireccionDto personaDireccionDto = (PersonaDireccionDto) resultado.getAttachment(ServicioPersonaDireccion.PERSONADIRECCION);
						if (personaDireccionDto != null) {
							tramiteTrafFacturacionDto.setDireccion(personaDireccionDto.getDireccion());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error al obtener facturación de la tasa", e, numExpediente.toString());
		}
		return tramiteTrafFacturacionDto;
	}

	@Override
	@Transactional
	public ResultBean guardar(TramiteTrafFacturacionDto tramiteTrafFacturacionDto, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean();
		try {
			BigDecimal numExpediente = servicioTramiteTrafico.generarNumExpedienteFacturacionTasa(tramiteTrafFacturacionDto.getNumColegiado());
			tramiteTrafFacturacionDto.setGenerado(false);
			tramiteTrafFacturacionDto.setTipoTramite(TipoTramiteTrafico.FacturacionTasa.getValorEnum());
			tramiteTrafFacturacionDto.setNumExpediente(numExpediente);
			resultado = servicioFacturacion.guardar(tramiteTrafFacturacionDto, idUsuario, false);
			if (!resultado.getError()) {
				Date fechaAplicacion = utilesFecha.convertirFechaEnDate(tramiteTrafFacturacionDto.getFechaAplicacion());
				resultado = servicioPersistenciaTasaPegatina.actualizarTasaPegatinaFechaAplicacion(tramiteTrafFacturacionDto.getCodTasa(), fechaAplicacion);
			} else {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		} catch (Exception e) {
			log.error("Error al guardar la facturación de la tasa: " + tramiteTrafFacturacionDto.getNumExpediente(), e);
			resultado.setError(true);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	public ServicioFacturacion getServicioFacturacion() {
		return servicioFacturacion;
	}

	public void setServicioFacturacion(ServicioFacturacion servicioFacturacion) {
		this.servicioFacturacion = servicioFacturacion;
	}

	public ServicioPersistenciaTasaPegatina getServicioPersistenciaTasaPegatina() {
		return servicioPersistenciaTasaPegatina;
	}

	public void setServicioPersistenciaTasaPegatina(ServicioPersistenciaTasaPegatina servicioPersistenciaTasaPegatina) {
		this.servicioPersistenciaTasaPegatina = servicioPersistenciaTasaPegatina;
	}

	public ServicioTramiteTrafico getServicioTramiteTrafico() {
		return servicioTramiteTrafico;
	}

	public void setServicioTramiteTrafico(ServicioTramiteTrafico servicioTramiteTrafico) {
		this.servicioTramiteTrafico = servicioTramiteTrafico;
	}

	public ServicioPersona getServicioPersona() {
		return servicioPersona;
	}

	public void setServicioPersona(ServicioPersona servicioPersona) {
		this.servicioPersona = servicioPersona;
	}
}
