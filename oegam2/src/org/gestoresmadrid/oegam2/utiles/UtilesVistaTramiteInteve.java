package org.gestoresmadrid.oegam2.utiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.enumerados.TipoInformeInteve;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamInteve.view.dto.TramiteTraficoInteveDto;
import org.gestoresmadrid.oegamInteve.view.dto.TramiteTraficoSolInteveDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class UtilesVistaTramiteInteve implements Serializable {

	private static final long serialVersionUID = 3623532690247343157L;

	private static UtilesVistaTramiteInteve utilesVistaTramiteInteve;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	ServicioTasa servicioTasa;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public static UtilesVistaTramiteInteve getInstance() {
		if (utilesVistaTramiteInteve == null) {
			utilesVistaTramiteInteve = new UtilesVistaTramiteInteve();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaTramiteInteve);
		}
		return utilesVistaTramiteInteve;
	}

	public Boolean esGuardable(TramiteTraficoInteveDto tramite) {
		if (tramite != null
				&& (tramite.getNumExpediente() == null || EstadoTramiteTrafico.Iniciado.getValorEnum().equals(tramite.getEstado())
					|| EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum().equals(tramite.getEstado())
					|| EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum().equals(tramite.getEstado()))) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean esFinalizado(TramiteTraficoInteveDto tramite) {
		if (tramite != null
				&& (EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramite.getEstado()) 
					|| EstadoTramiteTrafico.Finalizado_Parcialmente.getValorEnum().equals(tramite.getEstado()))) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean esEliminable(TramiteTraficoInteveDto tramite) {
		if (tramite != null && tramite.getTramitesInteves() != null && !tramite.getTramitesInteves().isEmpty()) {
			for (TramiteTraficoSolInteveDto inteve : tramite.getTramitesInteves()) {
				if (EstadoTramiteTrafico.Iniciado.getValorEnum().equals(inteve.getEstado())
					|| EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum().equals(inteve.getEstado())) {
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}

	public boolean esDescargable(TramiteTraficoInteveDto tramite) {
		if (tramite != null && tramite.getTramitesInteves() != null && !tramite.getTramitesInteves().isEmpty()) {
			for (TramiteTraficoSolInteveDto inteve : tramite.getTramitesInteves()) {
				if (EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(inteve.getEstado()) 
						|| EstadoTramiteTrafico.Finalizado_Parcialmente.getValorEnum().equals(inteve
						.getEstado())) {
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}

	public Boolean esValidableTramite(TramiteTraficoInteveDto tramite) {
		if (tramite != null &&  EstadoTramiteTrafico.Iniciado.getValorEnum().equals(tramite.getEstado())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean esReiniciableEstados(TramiteTraficoInteveDto tramite) {
		if (tramite != null && !EstadoTramiteTrafico.Pendiente_Envio.getValorEnum().equals(tramite.getEstado())
				&& !EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramite.getEstado())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public Boolean esSolicitableTramite(TramiteTraficoInteveDto tramite) {
		if (tramite != null && EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum().equals(tramite.getEstado())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	public List<EstadoTramiteTrafico> getEstadosTramiteInteve() {
		List<EstadoTramiteTrafico> listaEstados = new ArrayList<>();
		listaEstados.add(EstadoTramiteTrafico.Iniciado);
		listaEstados.add(EstadoTramiteTrafico.Validado_Telematicamente);
		listaEstados.add(EstadoTramiteTrafico.Finalizado_Telematicamente);
		listaEstados.add(EstadoTramiteTrafico.Finalizado_Parcialmente);
		listaEstados.add(EstadoTramiteTrafico.Finalizado_Con_Error);
		return listaEstados;
	}

	public List<EstadoTramiteTrafico> getEstadosTramiteSolictudInteve() {
		List<EstadoTramiteTrafico> listaEstados = new ArrayList<>();
		listaEstados.add(EstadoTramiteTrafico.Iniciado);
		listaEstados.add(EstadoTramiteTrafico.Validado_Telematicamente);
		listaEstados.add(EstadoTramiteTrafico.Finalizado_Telematicamente);
		listaEstados.add(EstadoTramiteTrafico.Finalizado_Con_Error);
		return listaEstados;
	}

	public List<DatoMaestroBean> getComboContratosHabilitados() {
		return servicioContrato.getComboContratosHabilitados();
	}

	public TipoInformeInteve[] getListaTiposInformes() {
		return TipoInformeInteve.values();
	}

	public List<TasaDto> getCodigosTasa(TramiteTraficoSolInteveDto solicitudInteve, ContratoDto contrato) {
		if (solicitudInteve != null && contrato != null) {
			List<TasaDto> listaCodigosTasa = getCodigosTasaInteve(contrato.getIdContrato().longValue(), ServicioTasa.TIPO_TASA_4_1);
			if (solicitudInteve.getCodigoTasa() != null && !solicitudInteve.getCodigoTasa().isEmpty()) {
				TasaDto tasa = new TasaDto();
				tasa.setCodigoTasa(solicitudInteve.getCodigoTasa());
				List<TasaDto> respuestaConTasaSeleccionada = listaCodigosTasa;
				if (!listaCodigosTasa.contains(tasa) && !listaCodigosTasa.isEmpty()) {
					respuestaConTasaSeleccionada.add(0, tasa);
					return respuestaConTasaSeleccionada;
				} else {
					respuestaConTasaSeleccionada = new ArrayList<TasaDto>();
					respuestaConTasaSeleccionada.add(0, tasa);
					return respuestaConTasaSeleccionada;
				}
			} else {
				return listaCodigosTasa;
			}
		}
		return Collections.emptyList();
	}
	public List<TasaDto> getCodigosTasas(ContratoDto contrato) {
		return getCodigosTasaInteve(contrato.getIdContrato().longValue(), ServicioTasa.TIPO_TASA_4_1);
	}

	public List<TasaDto> getCodigosTasaInteve(Long idContrato, String tipoTasa) {
		if (null == idContrato)
			idContrato = utilesColegiado.getIdContratoSession();
		String gestionTasaAlmacen = gestorPropiedades.valorPropertie("gestion.almacen.tasa");
		List<TasaDto> codigosTasaInteve = new ArrayList<>();
		if ("SI".equals(gestionTasaAlmacen)) {
			codigosTasaInteve = servicioTasa.obtenerTasasInteveContrato(idContrato, tipoTasa);
		} else {
			codigosTasaInteve = servicioTasa.obtenerTasasContrato(idContrato, tipoTasa);
		}
		if (codigosTasaInteve == null) {
			codigosTasaInteve = new ArrayList<>();
		}
		return codigosTasaInteve;
	}
}