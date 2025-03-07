package org.gestoresmadrid.oegam2.utiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.modelos.model.enumerados.Decision;
import org.gestoresmadrid.core.modelos.model.enumerados.TipoAlmacenTasa;
import org.gestoresmadrid.core.tasas.model.enumeration.TipoTasaDGT;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamDuplicadoPermisoConducir.view.dto.DuplicadoPermisoConducirDto;
import org.gestoresmadrid.oegamPermisoInternacional.view.dto.PermisoInternacionalDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import trafico.beans.TramiteTraficoTransmisionBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class UtilesVistaTasa {

	private static ILoggerOegam log = LoggerOegam.getLogger(UtilesVistaTasa.class);
	private static UtilesVistaTasa utilesVistaTasa;
	private static final String PROPIEDAD_TASA = "gestion.almacen.tasa";

	@Autowired
	private ServicioTasa servicioTasa;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public static UtilesVistaTasa getInstance() {
		if (utilesVistaTasa == null) {
			utilesVistaTasa = new UtilesVistaTasa();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaTasa);
		}
		return utilesVistaTasa;
	}

	public List<TasaDto> getCodigosTasa(Long idContrato, String tipoTasa) {
		if (null == idContrato)
			idContrato = utilesColegiado.getIdContratoSession();
		List<TasaDto> codigosTasaDuplicados = new ArrayList<>();
		String gestionTasaAlmacen = gestorPropiedades.valorPropertie(PROPIEDAD_TASA);
		if ("SI".equals(gestionTasaAlmacen)) {
			if (TipoTasaDGT.CuatroUno.getValorEnum().equals(tipoTasa)) {
				codigosTasaDuplicados = servicioTasa.obtenerTasasBajaContrato(idContrato, tipoTasa);
			} else if (TipoTasaDGT.CuatroCuatro.getValorEnum().equals(tipoTasa)) {
				codigosTasaDuplicados = servicioTasa.obtenerTasasDuplicadoContrato(idContrato, tipoTasa);
			}
		} else {
			codigosTasaDuplicados = servicioTasa.obtenerTasasContrato(idContrato, tipoTasa);
		}
		if (codigosTasaDuplicados == null) {
			codigosTasaDuplicados = new ArrayList<>();
		}
		return codigosTasaDuplicados;
	}

	public List<TasaDto> getCodigosTasaBaja(Long idContrato, String tipoTasa) {
		if (null == idContrato)
			idContrato = utilesColegiado.getIdContratoSession();
		String gestionTasaAlmacen = gestorPropiedades.valorPropertie(PROPIEDAD_TASA);
		List<TasaDto> codigosTasaBajas = new ArrayList<>();
		if ("SI".equals(gestionTasaAlmacen)) {
			codigosTasaBajas = servicioTasa.obtenerTasasBajaContrato(idContrato, tipoTasa);
		} else {
			codigosTasaBajas = servicioTasa.obtenerTasasContrato(idContrato, tipoTasa);
		}
		if (codigosTasaBajas == null) {
			codigosTasaBajas = new ArrayList<>();
		}
		return codigosTasaBajas;
	}

	public List<TasaDto> getCodigosTasaDuplicado(Long idContrato, String tipoTasa) {
		if (null == idContrato)
			idContrato = utilesColegiado.getIdContratoSession();
		String gestionTasaAlmacen = gestorPropiedades.valorPropertie(PROPIEDAD_TASA);
		List<TasaDto> codigosTasaDuplicados = new ArrayList<>();
		if ("SI".equals(gestionTasaAlmacen)) {
			codigosTasaDuplicados = servicioTasa.obtenerTasasDuplicadoContrato(idContrato, tipoTasa);
		} else {
			codigosTasaDuplicados = servicioTasa.obtenerTasasContrato(idContrato, tipoTasa);
		}
		if (codigosTasaDuplicados == null) {
			codigosTasaDuplicados = new ArrayList<>();
		}
		return codigosTasaDuplicados;
	}

	public List<TasaDto> getCodigosTasaDuplicados(Long idContrato) {
		return getCodigosTasa(idContrato, ServicioTasa.TIPO_TASA_4_4);
	}

	public List<TasaDto> getCodigosTasaDuplicadosFT(Long idContrato) {
		return getCodigosTasa(idContrato, ServicioTasa.TIPO_TASA_4_1);
	}

	public List<TasaDto> getCodigosTasaDuplicados(String tasaSeleccionada, Long idContrato) {
		List<TasaDto> codigosTasaDuplicados = getCodigosTasaDuplicado(idContrato, ServicioTasa.TIPO_TASA_4_4);
		if (tasaSeleccionada != null && !tasaSeleccionada.isEmpty() && !"-1".equals(tasaSeleccionada)) {
			TasaDto tasa = new TasaDto();
			tasa.setCodigoTasa(tasaSeleccionada);
			List<TasaDto> respuestaConTasaSeleccionada = codigosTasaDuplicados;

			if (!codigosTasaDuplicados.contains(tasa) && !codigosTasaDuplicados.isEmpty()) {
				respuestaConTasaSeleccionada.add(0, tasa);
			} else {
				respuestaConTasaSeleccionada = new ArrayList<>();
				respuestaConTasaSeleccionada.add(0, tasa);
			}
			return respuestaConTasaSeleccionada;
		} else
			return codigosTasaDuplicados;
	}

	public List<TasaDto> getCodigosTasaDuplicadosFT(String tasaSeleccionada, Long idContrato) {
		List<TasaDto> codigosTasaDuplicados = getCodigosTasaDuplicado(idContrato, ServicioTasa.TIPO_TASA_4_1);
		if (tasaSeleccionada != null && !tasaSeleccionada.isEmpty() && !"-1".equals(tasaSeleccionada)) {
			TasaDto tasa = new TasaDto();
			tasa.setCodigoTasa(tasaSeleccionada);
			List<TasaDto> respuestaConTasaSeleccionada = codigosTasaDuplicados;

			if (!codigosTasaDuplicados.contains(tasa) && !codigosTasaDuplicados.isEmpty()) {
				respuestaConTasaSeleccionada.add(0, tasa);
			} else {
				respuestaConTasaSeleccionada = new ArrayList<>();
				respuestaConTasaSeleccionada.add(0, tasa);
			}
			return respuestaConTasaSeleccionada;
		} else
			return codigosTasaDuplicados;
	}

	public List<TasaDto> getCodigosTasaCambServicio(String tasaSeleccionada, Long idContrato) {
		List<TasaDto> codigosTasaCambServ = getCodigosTasa(idContrato, ServicioTasa.TIPO_TASA_4_4);
		if (tasaSeleccionada != null && !tasaSeleccionada.isEmpty() && !"-1".equals(tasaSeleccionada)) {
			TasaDto tasa = new TasaDto();
			tasa.setCodigoTasa(tasaSeleccionada);
			List<TasaDto> respuestaConTasaSeleccionada = codigosTasaCambServ;

			if (!codigosTasaCambServ.contains(tasa) && !codigosTasaCambServ.isEmpty()) {
				respuestaConTasaSeleccionada.add(0, tasa);
			} else {
				respuestaConTasaSeleccionada = new ArrayList<>();
				respuestaConTasaSeleccionada.add(0, tasa);
			}
			return respuestaConTasaSeleccionada;
		} else
			return codigosTasaCambServ;
	}

	public List<TasaDto> getCodigosTasaBajas(Long idContrato) {
		return getCodigosTasa(idContrato, ServicioTasa.TIPO_TASA_4_1);
	}

	public List<TasaDto> getCodigosTasaBajas(String tasaSeleccionada, Long idContrato) {
		List<TasaDto> codigosTasaBajas = getCodigosTasaBaja(idContrato, ServicioTasa.TIPO_TASA_4_1);
		if (tasaSeleccionada != null && !tasaSeleccionada.isEmpty() && !"-1".equals(tasaSeleccionada)) {
			TasaDto tasa = new TasaDto();
			tasa.setCodigoTasa(tasaSeleccionada);
			List<TasaDto> respuestaConTasaSeleccionada = codigosTasaBajas;
			if (!codigosTasaBajas.contains(tasa) && !codigosTasaBajas.isEmpty()) {
				respuestaConTasaSeleccionada.add(0, tasa);
			} else {
				respuestaConTasaSeleccionada = new ArrayList<>();
			}
			return respuestaConTasaSeleccionada;
		} else {
			return codigosTasaBajas;
		}
	}

	public List<TasaDto> getCodigosTasaMatriculacion(TramiteTrafDto tramite) {
		Long idContratoTramite = null;
		List<TasaDto> codigosTasa = new ArrayList<>();

		idContratoTramite = tramite.getIdContrato() == null ? utilesColegiado.getIdContratoSession() : tramite.getIdContrato().longValue();

		if (tramite.getTasa() != null) {
			if (StringUtils.isNotBlank(tramite.getTasa().getTipoTasa()) || !"-1".equals(tramite.getTasa().getTipoTasa())) {
				String gestionTasaAlmacen = gestorPropiedades.valorPropertie(PROPIEDAD_TASA);
				if ("SI".equals(gestionTasaAlmacen)) {
					codigosTasa = servicioTasa.obtenerTasasMatwContrato(idContratoTramite, tramite.getTasa().getTipoTasa());
				} else {
					codigosTasa = servicioTasa.obtenerTasasContrato(idContratoTramite, tramite.getTasa().getTipoTasa());
				}
			}
			if (tramite.getTasa().getCodigoTasa() != null && !tramite.getTasa().getCodigoTasa().isEmpty() && !"-1".equals(tramite.getTasa().getCodigoTasa())) {
				TasaDto tasa = new TasaDto();
				tasa.setCodigoTasa(tramite.getTasa().getCodigoTasa());
				if (codigosTasa == null || codigosTasa.isEmpty()) {
					codigosTasa = new ArrayList<>();
					codigosTasa.add(tasa);
				} else if (!codigosTasa.contains(tasa)) {
					codigosTasa.add(0, tasa);
				}
			}
		}
		return codigosTasa;
	}

	public List<TasaDto> getCodigosTasaCtit(TramiteTraficoTransmisionBean tramite) {
		try {
			Long idContratoTramite = null;
			List<TasaDto> codigosTasa = new ArrayList<>();
			if (tramite != null && tramite.getTramiteTraficoBean() != null && tramite.getTramiteTraficoBean().getIdContrato() == null) {
				idContratoTramite = utilesColegiado.getIdContratoSession();
			} else {
				idContratoTramite = tramite.getTramiteTraficoBean().getIdContrato().longValue();
			}

			if (tramite.getTramiteTraficoBean() != null && tramite.getTramiteTraficoBean().getTasa() != null) {
				if (StringUtils.isNotBlank(tramite.getTramiteTraficoBean().getTasa().getTipoTasa()) || !"-1".equals(tramite.getTramiteTraficoBean().getTasa().getTipoTasa())) {
					String gestionTasaAlmacen = gestorPropiedades.valorPropertie(PROPIEDAD_TASA);
					if ("SI".equals(gestionTasaAlmacen)) {
						codigosTasa = servicioTasa.obtenerTasasCtitContrato(idContratoTramite, tramite.getTramiteTraficoBean().getTasa().getTipoTasa());
					} else {
						codigosTasa = servicioTasa.obtenerTasasContrato(idContratoTramite, tramite.getTramiteTraficoBean().getTasa().getTipoTasa());
					}
				}
				if (tramite.getTramiteTraficoBean().getTasa().getCodigoTasa() != null
						&& !tramite.getTramiteTraficoBean().getTasa().getCodigoTasa().isEmpty()
						&& !"-1".equals(tramite.getTramiteTraficoBean().getTasa().getCodigoTasa())) {
					TasaDto tasa = new TasaDto();
					tasa.setCodigoTasa(tramite.getTramiteTraficoBean().getTasa().getCodigoTasa());
					if (codigosTasa == null || codigosTasa.isEmpty()) {
						codigosTasa = new ArrayList<>();
						codigosTasa.add(tasa);
					} else if (!codigosTasa.contains(tasa)) {
						codigosTasa.add(0, tasa);
					}
				}
			}
			return codigosTasa;
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de devolver los codigos de tasa, error: ", e);
		}
		return Collections.emptyList(); 
	}

	public List<TasaDto> getCodigosTasaPermisoInternacional(PermisoInternacionalDto permisoInternacional) {
		Long idContratoTramite = null;
		List<TasaDto> codigosTasa = new ArrayList<>();
		if (permisoInternacional.getIdContrato() == null) {
			idContratoTramite = utilesColegiado.getIdContratoSession();
		} else {
			idContratoTramite = permisoInternacional.getIdContrato();
		}
		String gestionTasaAlmacen = gestorPropiedades.valorPropertie(PROPIEDAD_TASA);
		if ("SI".equals(gestionTasaAlmacen)) {
			codigosTasa = servicioTasa.obtenerTasasPermIntContrato(idContratoTramite, TipoTasa.CuatroCinco.getValorEnum());
		} else {
			codigosTasa = servicioTasa.obtenerTasasContrato(idContratoTramite, TipoTasa.CuatroCinco.getValorEnum());
		}
		if (StringUtils.isNotBlank(permisoInternacional.getCodigoTasa())) {
			TasaDto tasa = new TasaDto();
			tasa.setCodigoTasa(permisoInternacional.getCodigoTasa());
			if (codigosTasa == null || codigosTasa.isEmpty()) {
				codigosTasa = new ArrayList<>();
				codigosTasa.add(tasa);
			} else if (!codigosTasa.contains(tasa)) {
				codigosTasa.add(0, tasa);
			}
		}
		return codigosTasa;
	}

	public List<TasaDto> getCodigosTasaDuplicadoPermCond(DuplicadoPermisoConducirDto duplicadoPermisoConducirDto) {
		Long idContratoTramite = null;
		List<TasaDto> codigosTasa = new ArrayList<>();
		idContratoTramite = duplicadoPermisoConducirDto.getIdContrato() == null ? utilesColegiado.getIdContratoSession() : duplicadoPermisoConducirDto.getIdContrato();

		codigosTasa = servicioTasa.obtenerTasasContrato(idContratoTramite, TipoTasa.CuatroCuatro.getValorEnum());
		if (StringUtils.isNotBlank(duplicadoPermisoConducirDto.getCodigoTasa())) {
			TasaDto tasa = new TasaDto();
			tasa.setCodigoTasa(duplicadoPermisoConducirDto.getCodigoTasa());
			if (codigosTasa == null || codigosTasa.isEmpty()) {
				codigosTasa = new ArrayList<>();
				codigosTasa.add(tasa);
			} else if (!codigosTasa.contains(tasa)) {
				codigosTasa.add(0, tasa);
			}
		}
		return codigosTasa;
	}

	public Decision[] getImportadoColegio() {
		return Decision.values();
	}

	public Boolean esGenerarCertificadoTasasNuevo() {
		String esNuevo = gestorPropiedades.valorPropertie("certificados.tasas.nuevo");
		return (esNuevo != null && !esNuevo.isEmpty() && esNuevo.equals("SI"));
	}

	public TipoAlmacenTasa[] getAlmacenTasa() {
		return TipoAlmacenTasa.values();
	}

	public Boolean esGestionAlmacenTasa() {
		String gestionTasaAlmacen = gestorPropiedades.valorPropertie(PROPIEDAD_TASA);
		return "SI".equals(gestionTasaAlmacen);
	}
}