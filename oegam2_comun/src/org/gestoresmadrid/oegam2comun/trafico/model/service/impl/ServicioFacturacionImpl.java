package org.gestoresmadrid.oegam2comun.trafico.model.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.facturacion.model.dao.FacturaIrpfDao;
import org.gestoresmadrid.core.facturacion.model.dao.FacturaIvaDao;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoFactDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafFacturacionVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioFacturacion;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTrafFacturacionDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import general.utiles.Anagrama;
import hibernate.entities.facturacion.FacturaIrpf;
import hibernate.entities.facturacion.FacturaIva;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioFacturacionImpl implements ServicioFacturacion {

	private static final long serialVersionUID = -8817459395181534075L;

	protected static final ILoggerOegam log = LoggerOegam.getLogger(ServicioFacturacionImpl.class);

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private TramiteTraficoFactDao tramiteFactDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Autowired
	FacturaIrpfDao facturaIrpfDao;

	@Autowired
	FacturaIvaDao facturaIvaDao;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public TramiteTrafFacturacionDto getTramiteTraficoFact(BigDecimal numExpediente, String codigoTasa) {
		try {
			TramiteTrafFacturacionVO tramiteTrafFacturacion = tramiteFactDao.getTramiteTraficoFactura(numExpediente, codigoTasa);
			if (tramiteTrafFacturacion != null) {
				return conversor.transform(tramiteTrafFacturacion, TramiteTrafFacturacionDto.class);
			}
		} catch (Exception e) {
			log.error("Error al obtener el trámite de facturacion", e, numExpediente.toString());
		}
		return null;
	}

	public ResultBean eliminarFacturacionTramite(BigDecimal numExpediente, String codigoTasa) {
		TramiteTrafFacturacionDto datosFacturacionDto = new TramiteTrafFacturacionDto();
		ResultBean resultado = new ResultBean();

		try {
			TramiteTrafFacturacionVO tramiteTrafFacturacion = tramiteFactDao.getTramiteTraficoFactura(numExpediente, codigoTasa);
			if (tramiteTrafFacturacion != null) {
			tramiteFactDao.evict(tramiteTrafFacturacion);
			tramiteFactDao.borrar(tramiteTrafFacturacion);
			} else {
				resultado.setError(true);
				resultado.setMensaje("Error al borrar el titular de facturación");
			}
		} catch (Exception e) {
			log.error("Error al eliminar la facturación del trámite: " + datosFacturacionDto.getNumExpediente());
		}
		return resultado;
	}

	@Transactional
	@Override
	public ResultBean guardar(TramiteTrafFacturacionDto datosFacturacionDto, BigDecimal idUsuario, boolean utilizarTitular) {
		ResultBean resultado = new ResultBean();
		ResultBean resultadoPersona = new ResultBean();
		try {
			if (!utilizarTitular) {
				UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
				resultadoPersona = guardarPersona(datosFacturacionDto.getPersona(), datosFacturacionDto.getNif(), datosFacturacionDto.getNumExpediente(), usuario,
						datosFacturacionDto.getTipoTramite(), datosFacturacionDto.getDireccion());
			}
			if (!resultadoPersona.getError()) {
				TramiteTrafFacturacionVO datosFacturacion = conversor.transform(datosFacturacionDto, TramiteTrafFacturacionVO.class);
				tramiteFactDao.guardarOActualizar(datosFacturacion);
			} else {
				resultado.setError(true);
				resultado.setMensaje("Error al guardar el titular de facturación");
			}
		} catch (Exception e) {
			log.error("Error al guardar el titular de la facturación: " + datosFacturacionDto.getNumExpediente());
		}
		return resultado;
	}

	@Transactional
	@Override
	public ResultBean guardarMultiple(List<TramiteTrafFacturacionDto> listaFacturacion, BigDecimal idUsuario, PersonaDto persona, DireccionDto direccion) {
		ResultBean resultado = new ResultBean();
		BigDecimal numExpediente = null;
		String tipoTramite = null;
		try {
			if (listaFacturacion == null || listaFacturacion.isEmpty()) {
				resultado.setError(true);
				resultado.setMensaje("Error al guardar facturación");
			} else {
				numExpediente = listaFacturacion.get(0).getNumExpediente();
				tipoTramite = listaFacturacion.get(0).getTipoTramite();

				ResultBean resultadoPersona;
				if (persona != null) {
					UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
					resultadoPersona = guardarPersona(persona, persona.getNif(), numExpediente, usuario, tipoTramite, direccion);
				} else {
					resultadoPersona = new ResultBean();
				}

				if (!resultadoPersona.getError()) {
					for (TramiteTrafFacturacionDto datosFacturacionDto : listaFacturacion) {
						TramiteTrafFacturacionVO datosFacturacion = conversor.transform(datosFacturacionDto, TramiteTrafFacturacionVO.class);
						tramiteFactDao.guardarOActualizar(datosFacturacion);
					}
				} else {
					resultado.setError(true);
					resultado.setMensaje("Error al guardar el titular de facturación");
				}
			}
		} catch (Exception e) {
			log.error("Error al guardar el titular de la facturación: " + numExpediente);
		}
		return resultado;
	}

	private ResultBean guardarPersona(PersonaDto persona, String nif, BigDecimal numExpediente, UsuarioDto usuario, String tipoTramite, DireccionDto direccion) {
		ResultBean respuesta = new ResultBean();

		PersonaVO personaVO = conversor.transform(persona, PersonaVO.class);

		personaVO.getId().setNumColegiado(utilesColegiado.getNumColegiadoSession());
		personaVO.getId().setNif(nif);
		String anagrama = Anagrama.obtenerAnagramaFiscal(personaVO.getApellido1RazonSocial(), personaVO.getId().getNif());
		personaVO.setAnagrama(anagrama);

		ResultBean resultPersona = servicioPersona.guardarActualizar(personaVO, numExpediente, tipoTramite, usuario, ServicioPersona.CONVERSION_PERSONA_REPRESENTANTE);

		if (!resultPersona.getError()) {
			DireccionVO direccionVO = conversor.transform(direccion, DireccionVO.class);
			ResultBean resultDireccion = servicioDireccion.guardarActualizarPersona(direccionVO, personaVO.getId().getNif(), personaVO.getId().getNumColegiado(), tipoTramite,
					ServicioDireccion.CONVERSION_DIRECCION_INE);
			if (resultDireccion.getError()) {
				respuesta.addMensajeALista(resultDireccion.getMensaje());
			}
		} else {
			respuesta.addMensajeALista(resultPersona.getMensaje());
		}
		return respuesta;
	}

	@Override
	@Transactional
	public List<FacturaIva> listaIvas() {
		return facturaIvaDao.listaIvas();
	}

	@Override
	@Transactional
	public List<FacturaIrpf> listaIrpfs() {
		return facturaIrpfDao.listaIrpfs();
	}

}