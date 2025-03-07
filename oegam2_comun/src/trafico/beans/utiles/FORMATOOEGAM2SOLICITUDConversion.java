package trafico.beans.utiles;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import escrituras.beans.Persona;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.SolicitudDatosBean;
import trafico.beans.SolicitudVehiculoBean;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.VehiculoBean;
import trafico.beans.jaxb.solicitud.DATOSSOLICITANTE;
import trafico.beans.jaxb.solicitud.DATOSVEHICULO;
import trafico.beans.jaxb.solicitud.FORMATOOEGAM2SOLICITUD;
import trafico.beans.jaxb.solicitud.SOLICITUD;

//Mantis 14125. David Sierra: Importacion datos de XML a SolicitudDatosBean
@Component
public class FORMATOOEGAM2SOLICITUDConversion {

/* INICIO ATRIBUTOS */
	private static SimpleDateFormat formatoFechas = new SimpleDateFormat("dd/MM/yyyy");	

	/* FIN ATRIBUTOS */

	public FORMATOOEGAM2SOLICITUDConversion() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/* INICIO MÉTODOS DE CONVERSIÓN */
	public List<SolicitudDatosBean> convertirFORMATOOEGAM2SOLICITUDtoListaSolicitudDatosBean(FORMATOOEGAM2SOLICITUD solicitud, String numColegiado, BigDecimal idContrato) {
		// Se pasan los datos del XML a SolicitudDatosBean
		List<SolicitudDatosBean> listaTramiteTraficoSolicitud = new ArrayList<>();
		SolicitudDatosBean solicitudDatos = null;

		if (solicitud != null) {
			// XML SOLICITUD
			for (SOLICITUD sol : solicitud.getSOLICITUD()) {
				solicitudDatos = convertirFORMATOOEGAM2SOLICITUDtoSolicitudDatosBean(sol, numColegiado, idContrato);
				listaTramiteTraficoSolicitud.add(solicitudDatos);
			}
		}
		return listaTramiteTraficoSolicitud;
	}

	private SolicitudDatosBean convertirFORMATOOEGAM2SOLICITUDtoSolicitudDatosBean(SOLICITUD solicitud, String numColegiado, BigDecimal idContrato) {
		SolicitudDatosBean solicitudDatos = null;
		UtilesFecha utilesFecha = ContextoSpring.getInstance().getBean(UtilesFecha.class);
		// SOLICITUD
		if (solicitud != null) {
			TramiteTraficoBean tramiteTrafico = new TramiteTraficoBean();
			tramiteTrafico.setNumColegiado(numColegiado);
			tramiteTrafico.setIdContrato(idContrato);

			solicitudDatos = new SolicitudDatosBean();

			// Datos solicitud
			if (solicitud.getDATOSSOLICITUD() != null) {
				tramiteTrafico.setReferenciaPropia(solicitud.getDATOSSOLICITUD().getREFERENCIAPROPIA());
				tramiteTrafico.setAnotaciones(solicitud.getDATOSSOLICITUD().getANOTACIONES());
				try {
					tramiteTrafico.setFechaPresentacion(utilesFecha.getFechaConDate(formatoFechas.parse(solicitud.getDATOSSOLICITUD().getFECHAPRESENTACION())));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			// Datos solicitante
			if (solicitud.getDATOSSOLICITANTE() != null) {
				solicitudDatos.setSolicitante(convertirDATOSSOLICITUDoIntervinienteTraficoBean(solicitud.getDATOSSOLICITANTE()));
			}

			// Datos vehículo
			if (solicitud.getDATOSVEHICULO() != null) {
				List <SolicitudVehiculoBean> listaSolicitudesVehiculos=new ArrayList<>();
				for (DATOSVEHICULO veh : solicitud.getDATOSVEHICULO()) {
					listaSolicitudesVehiculos.add(convertirDATOSVEHICULOToVehiculoBean(veh));
				}
				solicitudDatos.setSolicitudesVehiculos(listaSolicitudesVehiculos);
			}
			solicitudDatos.setTramiteTrafico(tramiteTrafico);
		}
		return solicitudDatos;
	}

	private static SolicitudVehiculoBean convertirDATOSVEHICULOToVehiculoBean(DATOSVEHICULO datosVehiculo) {
		// DATOS VEHICULO se agregan a SolicitudVehiculoBean
		SolicitudVehiculoBean solicitudVehiculo =null;
		solicitudVehiculo = new SolicitudVehiculoBean();
	
		if (datosVehiculo != null) {
			Tasa tasa = new Tasa();
			// En la pantalla actualmente este valor es fijo, se utiliza un label (4.1)
			// tasa.setTipoTasa(datosVehiculo.getTIPOTASA());
			tasa.setCodigoTasa(datosVehiculo.getCODIGOTASA());
			VehiculoBean vehiculo = new VehiculoBean();
			vehiculo.setMatricula(datosVehiculo.getMATRICULA());
			vehiculo.setBastidor(datosVehiculo.getNUMEROBASTIDOR());
			solicitudVehiculo.setVehiculo(vehiculo);
			solicitudVehiculo.setTasa(tasa);
		}
		return solicitudVehiculo;
	}

	private static IntervinienteTrafico convertirDATOSSOLICITUDoIntervinienteTraficoBean(DATOSSOLICITANTE datosSolicitante) {
		// DATOS SOLICITANTE
		Persona persona = new Persona();
		persona.setNif(datosSolicitante.getNIFCIF());
		persona.setApellido1RazonSocial(datosSolicitante.getAPELLIDO1RAZONSOCIAL());
		persona.setApellido2(datosSolicitante.getAPELLIDO2());
		persona.setNombre(datosSolicitante.getNOMBRE());

		IntervinienteTrafico solicitante = new IntervinienteTrafico();
		solicitante.setPersona(persona);
		return solicitante;
	}

	/* FIN MÉTODOS DE CONVERSIÓN */

}