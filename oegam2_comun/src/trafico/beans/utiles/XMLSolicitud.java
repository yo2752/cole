package trafico.beans.utiles;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.Marshaller;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import trafico.beans.SolicitudDatosBean;
import trafico.beans.SolicitudVehiculoBean;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.jaxb.solicitud.DATOSSOLICITANTE;
import trafico.beans.jaxb.solicitud.DATOSSOLICITUD;
import trafico.beans.jaxb.solicitud.DATOSVEHICULO;
import trafico.beans.jaxb.solicitud.FORMATOOEGAM2SOLICITUD;
import trafico.beans.jaxb.solicitud.SOLICITUD;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.XMLGAFactory;
import trafico.utiles.constantes.ConstantesTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class XMLSolicitud {

	private static ILoggerOegam log = LoggerOegam.getLogger(XMLSolicitud.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private Utiles utiles;

	@Autowired
	UtilesConversiones utilesConversiones;
	
	@Autowired
	private UtilesColegiado utilesColegiado;
	
	public XMLSolicitud() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public FORMATOOEGAM2SOLICITUD convertirAFORMATOGA(List<SolicitudDatosBean> listaTramitesSolicitados, boolean xmlSesion) {

		FORMATOOEGAM2SOLICITUD formatoGA = instanciarCompleto();

		// Se agrega la cabecera del XML

		// Si se importa el xml de la sesion del usuario
		if (xmlSesion) {
			formatoGA.getCABECERA().getDATOSGESTORIA().setNIF(utilesColegiado.getNifUsuario());
			formatoGA.getCABECERA().getDATOSGESTORIA().setNOMBRE(utilesColegiado.getApellidosNombreUsuario());
			formatoGA.getCABECERA().getDATOSGESTORIA().setPROFESIONAL(listaTramitesSolicitados.get(0).getTramiteTrafico().getNumColegiado());
			String sigla = utilesConversiones.getSiglaProvinciaFromNombre(utilesColegiado.getProvinciaDelContrato());
			formatoGA.getCABECERA().getDATOSGESTORIA().setPROVINCIA(sigla);
			
		} else {

			ServicioContrato servicioContrato = ContextoSpring.getInstance().getBean(ServicioContrato.class);
			ContratoDto contrato = servicioContrato.getContratoDto(listaTramitesSolicitados.get(0).getTramiteTrafico().getIdContrato());

			// Añado Cabecera
			formatoGA.getCABECERA().getDATOSGESTORIA().setNIF(contrato.getCif());
			formatoGA.getCABECERA().getDATOSGESTORIA().setNOMBRE(contrato.getRazonSocial());
			formatoGA.getCABECERA().getDATOSGESTORIA().setPROFESIONAL(contrato.getColegiadoDto().getNumColegiado());
			String sigla = utilesConversiones.getSiglaProvinciaFromNombre(utilesConversiones.getNombreProvincia(contrato.getIdProvincia()));
			formatoGA.getCABECERA().getDATOSGESTORIA().setPROVINCIA(sigla);
		}
		formatoGA.setPlataforma("OEGAM");

		// Se agregan las solicitudes al XML
		List<SOLICITUD> lista = formatoGA.getSOLICITUD();

		for (int i = 0; i < listaTramitesSolicitados.size(); i++) {
			lista.add(convertirBeanToGaDatosSolicitud(listaTramitesSolicitados.get(i)));
		}

		formatoGA.setSolicitud(lista);

		return formatoGA;
	}

	public ResultBean FORMATOGAtoXML(FORMATOOEGAM2SOLICITUD formatoSolicitud, String idSession) {
		ResultBean resultado = new ResultBean();
		// Clasificar objeto en archivo.
		String nodo = gestorPropiedades.valorPropertie(ConstantesGestorFicheros.RUTA_ARCHIVOS_TEMP);
		File salida = new File(nodo + "/temp/trans" + idSession + ".xml");
		try {
			// Crear clasificador
			Marshaller m = new XMLGAFactory().getSolicitudExportacionContext().createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
			m.marshal(formatoSolicitud, salida);
			byte[] bytes = utiles.getBytesFromFile(salida);
			resultado.addAttachment(ConstantesTrafico.BYTESXML, bytes);
		} catch (Exception e) {
			log.error(e);
			resultado.setError(true);
			resultado.setMensaje("Error al generar el fichero XML");
			return resultado;
		} finally {
			salida.delete();
		}
		return resultado;
	}

	private SOLICITUD convertirBeanToGaDatosSolicitud(SolicitudDatosBean detalleTramite) {
		// SOLICITUD
		SOLICITUD solicitud = instanciarSOLICITUD();

		if (detalleTramite == null || detalleTramite.getTramiteTrafico() == null)
			return solicitud;

		TramiteTraficoBean tramite = detalleTramite.getTramiteTrafico();

		// DATOS SOLICITUD
		if (detalleTramite.getTramiteTrafico() != null) {
			DATOSSOLICITUD datosSolicitud = solicitud.getDATOSSOLICITUD();
			datosSolicitud.setREFERENCIAPROPIA(tramite.getReferenciaPropia() != null ? tramite.getReferenciaPropia() : null);
			datosSolicitud.setANOTACIONES(tramite.getAnotaciones() != null ? tramite.getAnotaciones() : null);
			datosSolicitud.setFECHAPRESENTACION(tramite.getFechaPresentacion() != null ? tramite.getFechaPresentacion().toString() : null);

			solicitud.setDATOSSOLICITUD(datosSolicitud);
		}

		// DATOS SOLICITANTE
		if (detalleTramite.getSolicitante() != null) {
			Persona persona = detalleTramite.getSolicitante().getPersona();
			DATOSSOLICITANTE datosSolicitante = solicitud.getDATOSSOLICITANTE();
			if (persona != null) {
				datosSolicitante.setNIFCIF(persona.getNif());
				datosSolicitante.setAPELLIDO1RAZONSOCIAL(persona.getApellido1RazonSocial());
				datosSolicitante.setAPELLIDO2(persona.getApellido2());
				datosSolicitante.setNOMBRE(persona.getNombre());
			}
			solicitud.setDATOSSOLICITANTE(datosSolicitante);
		}

		// DATOSVEHICULO
		if (detalleTramite.getSolicitudesVehiculos() != null) {
			solicitud.setDATOSVEHICULO(convertirBeanToGaDatosVehiculo(detalleTramite));
		}
		return solicitud;
	}

	private List<DATOSVEHICULO> convertirBeanToGaDatosVehiculo(SolicitudDatosBean detalleTramite) {
		// DATOSVEHICULO
		List<DATOSVEHICULO> listaDatosVehiculo = new ArrayList<DATOSVEHICULO>();
		if (detalleTramite.getSolicitudesVehiculos() != null) {
			// Se recupera y se recorre un List <SolicitudVehiculoBean> solicitudesVehiculos
			for (SolicitudVehiculoBean veh : detalleTramite.getSolicitudesVehiculos()) {
				DATOSVEHICULO datosVehiculo = new DATOSVEHICULO();
				// Se asigna a DATOSVEHICULO los valores recuperados
				datosVehiculo.setMATRICULA(veh.getVehiculo().getMatricula());
				datosVehiculo.setNUMEROBASTIDOR(veh.getVehiculo().getBastidor());
				datosVehiculo.setTIPOTASA(veh.getTasa().getTipoTasa());
				datosVehiculo.setCODIGOTASA(veh.getTasa().getCodigoTasa());

				listaDatosVehiculo.add(datosVehiculo);
			}
		}
		return listaDatosVehiculo;
	}

	private SOLICITUD instanciarSOLICITUD() {
		trafico.beans.jaxb.solicitud.ObjectFactory factory = new trafico.beans.jaxb.solicitud.ObjectFactory();
		SOLICITUD solicitud = factory.createSOLICITUD();

		// DATOS SOLICITUD
		DATOSSOLICITUD datosSolicitud = factory.createDATOSSOLICITUD();
		datosSolicitud.setREFERENCIAPROPIA("");
		datosSolicitud.setANOTACIONES("");
		datosSolicitud.setFECHAPRESENTACION("");

		solicitud.setDATOSSOLICITUD(datosSolicitud);

		// DATOS SOLICITANTE
		DATOSSOLICITANTE datosSolicitante = factory.createDATOSSOLICITANTE();
		datosSolicitante.setNIFCIF("");
		datosSolicitante.setAPELLIDO1RAZONSOCIAL("");
		datosSolicitante.setAPELLIDO2("");
		datosSolicitante.setNOMBRE("");

		solicitud.setDATOSSOLICITANTE(datosSolicitante);

		solicitud.setDATOSVEHICULO(new ArrayList<DATOSVEHICULO>());

		return solicitud;
	}

	private FORMATOOEGAM2SOLICITUD instanciarCompleto() {
		trafico.beans.jaxb.solicitud.ObjectFactory factory = new trafico.beans.jaxb.solicitud.ObjectFactory();
		FORMATOOEGAM2SOLICITUD ga = factory.createFORMATOOEGAM2SOLICITUD();

		// CABECERA
		ga.setCABECERA(factory.createCABECERA());
		ga.getCABECERA().setDATOSGESTORIA(factory.createDATOSGESTORIA());
		ga.getCABECERA().getDATOSGESTORIA().setNIF("");
		ga.getCABECERA().getDATOSGESTORIA().setNOMBRE("");
		ga.getCABECERA().getDATOSGESTORIA().setPROFESIONAL("");
		ga.getCABECERA().getDATOSGESTORIA().setPROVINCIA("");

		ga.setFechaCreacion((new Date()).toString());
		ga.setSolicitud(new ArrayList<SOLICITUD>());

		return ga;
	}

}
