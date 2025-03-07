package org.gestoresmadrid.oegam2.utiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.enumerados.SexoPersona;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.DocumentosJustificante;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.MotivoJustificante;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.TipoTramiteTraficoJustificante;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvincia;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioPueblo;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.PuebloDto;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioJefaturaTrafico;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioTipoVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.TipoVehiculoDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.ProvinciaDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class UtilesVistaJustificanteProfesional {

	private static ILoggerOegam log = LoggerOegam.getLogger(UtilesVistaJustificanteProfesional.class);

	private static UtilesVistaJustificanteProfesional utilesVistaJustificanteProfesional;

	@Autowired
	private ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	ServicioJefaturaTrafico servicioJefaturaTrafico;

	@Autowired
	ServicioProvincia servicioProvincia;

	@Autowired
	ServicioTipoVehiculo servicioTipoVehiculo;

	@Autowired
	ServicioMunicipio servicioMunicipio;

	@Autowired
	ServicioPueblo servicioPueblo;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;

	public static UtilesVistaJustificanteProfesional getInstance() {
		if (utilesVistaJustificanteProfesional == null) {
			utilesVistaJustificanteProfesional = new UtilesVistaJustificanteProfesional();
			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(utilesVistaJustificanteProfesional);
		}
		return utilesVistaJustificanteProfesional;
	}

	public boolean esGenerableJustificante(TramiteTrafDto tramiteTrafico) {
		if (tramiteTrafico == null) {
			return true;
		}
		if (!utilesColegiado.tienePermisoEspecial() && tienePermisoJustificantes()) {
			if (tramiteTrafico.getNumExpediente() != null && !"".equals(tramiteTrafico.getNumExpediente())) {
				try {
					return !servicioJustificanteProfesional.hayJustificante(null, tramiteTrafico.getNumExpediente(), EstadoJustificante.Iniciado);
				} catch (Exception e) {
					log.error("Error al buscar Justificantes Profesionales Iniciados");
					return false;
				}
			} else {
				return true;
			}
		}
		return false;
	}

	public boolean esGenerableJP(JustificanteProfDto justificanteProfDto) {
		return (justificanteProfDto != null
			&& (justificanteProfDto.getEstado() == null
					|| EstadoJustificante.Iniciado.getValorEnum().equals(justificanteProfDto.getEstado().toString())));
	}

	public boolean tienePermisoJustificantes() {
		if (!utilesColegiado.tienePermisoEspecial()) {
			return (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_JUSTIFICANTES_PROFESIONALES));
		}
		return false;
	}

	public DocumentosJustificante[] getDocumentosJustificante() {
		return DocumentosJustificante.values();
	}

	public List<JefaturaTraficoDto> getListaJefaturasTrafico() {
		return servicioJefaturaTrafico.listadoJefaturas(null);
	}

	public List<ProvinciaDto> getListaProvincias(){
		return servicioProvincia.getListaProvincias();
	}

	public EstadoJustificante[] getEstadoJustificante() {
		return EstadoJustificante.values();
	}

	public TipoTramiteTraficoJustificante[] getComboTipoTramitesJustificantes() {
		return TipoTramiteTraficoJustificante.values();
	}

	public TipoPersona[] getComboTipoPersonas() {
		return TipoPersona.values();
	}

	public SexoPersona[] getComboSexo() {
		return SexoPersona.values();
	}

	public List<DatoMaestroBean> getListaTipoVehiculos(){
		List<TipoVehiculoDto> listaTipos = servicioTipoVehiculo.getListaTiposVehiculos();
		if (listaTipos != null && !listaTipos.isEmpty()) {
			List<DatoMaestroBean> lista = new ArrayList<>();
			for (TipoVehiculoDto tipoVehiculoDto : listaTipos) {
				DatoMaestroBean tipoVehiculoBean = new DatoMaestroBean();
				tipoVehiculoBean.setCodigo(tipoVehiculoDto.getTipoVehiculo());
				tipoVehiculoBean.setDescripcion(tipoVehiculoDto.getTipoVehiculo() + " - " + tipoVehiculoDto.getDescripcion());
				lista.add(tipoVehiculoBean);
			}
			return lista;
		}
		return Collections.emptyList();
	}

	public List<MotivoJustificante> getListaMotivosJustificante(JustificanteProfDto justificanteProfDto) {
		if (justificanteProfDto != null && justificanteProfDto.getTramiteTrafico() != null
				&& justificanteProfDto.getTramiteTrafico().getTipoTramite() != null && !justificanteProfDto.getTramiteTrafico().getTipoTramite().isEmpty()) {
			List<MotivoJustificante> lista = new ArrayList<>();
			for (MotivoJustificante motivo : MotivoJustificante.values()) {
				if(motivo.getTipoTramite().equals(justificanteProfDto.getTramiteTrafico().getTipoTramite())){
					lista.add(motivo);
				}
			}
			return lista;
		}
		return Collections.emptyList();
	}

	public List<MotivoJustificante> getListaMotivosJustificantePopPup(String tipoTramite) {
		if (tipoTramite != null && !tipoTramite.isEmpty()) {
			List<MotivoJustificante> lista = new ArrayList<>();
			for(MotivoJustificante motivo : MotivoJustificante.values()) {
				if(motivo.getTipoTramite().equals(tipoTramite)) {
					lista.add(motivo);
				}
			}
			return lista;
		}
		return Collections.emptyList();
	}

	public boolean esComprobableJustProf(TramiteTrafDto tramiteTrafico) {
		if (!utilesColegiado.tienePermisoEspecial()) {
			return (utilesColegiado.tienePermiso(UtilesColegiado.PERMISO_MANTENIMIENTO_TRANSMISIONES_ELECTRONICAS)
					&& (tramiteTrafico.getEstado() == null || tramiteTrafico.getEstado().equals(EstadoTramiteTrafico.Iniciado)));
		}
		return false;
	}

	public boolean esJustificantesVariasPeticiones() {
		String esJustProfNumPeticiones = gestorPropiedades.valorPropertie("justificantes.nuevos.varias.peticiones");
		return ("SI".equalsIgnoreCase(esJustProfNumPeticiones));
	}

	public boolean esImprimibleJP(JustificanteProfDto justificanteProfDto) {
		return (justificanteProfDto != null && EstadoJustificante.Ok.getValorEnum().equals(justificanteProfDto.getEstado().toString()));
	}

	public boolean esForzarGenerarJP(JustificanteProfDto justificanteProfDto) {
		return (justificanteProfDto != null
				&& utilesColegiado.tienePermisoAdmin()
				&& EstadoJustificante.Pendiente_autorizacion_colegio.getValorEnum().equals(justificanteProfDto.getEstado().toString()));
	}

	public boolean esPteAutoColegioJP(JustificanteProfDto justificanteProfDto) {
		return (justificanteProfDto != null
				&& utilesColegiado.tienePermisoAdmin()
				&& (EstadoJustificante.Finalizado_Con_Error.getValorEnum().equals(justificanteProfDto.getEstado().toString())
					|| EstadoJustificante.Iniciado.getValorEnum().equals(justificanteProfDto.getEstado().toString())));
	}

	public boolean esAnulableJP(JustificanteProfDto justificanteProfDto) {
		return (justificanteProfDto != null
				&& utilesColegiado.tienePermisoAdmin()
				&& (EstadoJustificante.Pendiente_autorizacion_colegio.getValorEnum().equals(justificanteProfDto.getEstado().toString())
					|| EstadoJustificante.Iniciado.getValorEnum().equals(justificanteProfDto.getEstado().toString())));
	}

	public List<MunicipioDto> getListaMunicipiosPorProvincia(IntervinienteTraficoDto intervinienteTraficoDto) {
		if(intervinienteTraficoDto != null && intervinienteTraficoDto.getDireccion() != null
			&& intervinienteTraficoDto.getDireccion().getIdProvincia() != null && !intervinienteTraficoDto.getDireccion().getIdProvincia().isEmpty()){
			return servicioMunicipio.listaMunicipios(intervinienteTraficoDto.getDireccion().getIdProvincia());
		}
		return Collections.emptyList();
	}

	public List<PuebloDto> getListaPueblosPorMunicipioYProvincia(IntervinienteTraficoDto intervinienteTraficoDto) {
		if (intervinienteTraficoDto != null && intervinienteTraficoDto.getDireccion() != null
			&& intervinienteTraficoDto.getDireccion().getIdProvincia() != null && !intervinienteTraficoDto.getDireccion().getIdProvincia().isEmpty()
			&&	intervinienteTraficoDto.getDireccion().getIdMunicipio() != null && !intervinienteTraficoDto.getDireccion().getIdMunicipio().isEmpty()) {
			return servicioPueblo.listaPueblos(intervinienteTraficoDto.getDireccion().getIdProvincia(), intervinienteTraficoDto.getDireccion().getIdMunicipio());
		}
		return Collections.emptyList();
	}

	public List<DatoMaestroBean> getComboContratosHabilitados() {
		return servicioContrato.getComboContratosHabilitados();
	}

	public ServicioJustificanteProfesional getServicioJustificanteProfesional() {
		return servicioJustificanteProfesional;
	}

	public void setServicioJustificanteProfesional(ServicioJustificanteProfesional servicioJustificanteProfesional) {
		this.servicioJustificanteProfesional = servicioJustificanteProfesional;
	}
}