package trafico.servicio.implementacion;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.utilidades.components.Utiles;

import com.gescogroup.blackbox.EitvQueryWSResponse;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import trafico.dto.TramiteTraficoDto;
import trafico.modelo.impl.ModeloEitvImpl;
import trafico.modelo.impl.ModeloTramiteTrafImpl;
import trafico.modelo.interfaz.ModeloEitvInt;
import trafico.modelo.interfaz.ModeloTramiteTrafInterface;
import trafico.servicio.interfaz.ServicioEitvInt;
import trafico.utiles.XMLConsultaTarjetaEITVFactory;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ServicioEitvImpl implements ServicioEitvInt {

	private ModeloEitvInt modeloEitv;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioEitvImpl.class);
	private static final String UTF_8 = "UTF-8";

	private GestorDocumentos gestorDocumentos;

	public GestorDocumentos getGuardarDocumentos() {
		if (gestorDocumentos == null) {
			gestorDocumentos = ContextoSpring.getInstance().getBean(GestorDocumentos.class);
		}
		return gestorDocumentos;
	}

	@Override
	public void generarSolicitudXmlEitv(BigDecimal numExpediente){
		ModeloTramiteTrafInterface modeloTramite = new ModeloTramiteTrafImpl();
		TramiteTraficoDto tramite = modeloTramite.recuperarDtoPorNumExp(numExpediente);
		if (tramite != null && tramite.getNumColegiado() != null && tramite.getContrato() != null
				&& tramite.getVehiculo() != null && tramite.getVehiculo().getNive() != null
				&& tramite.getVehiculo().getBastidor() != null) {
			try {
				getModelo().generarXmlSolicitudEitv(numExpediente, tramite.getNumColegiado(), new BigDecimal(tramite.getContrato().getIdContrato()), tramite.getVehiculo().getNive(), tramite.getVehiculo().getBastidor());
			} catch (Throwable e) {
				log.error("Error al guardar el XML");
			}
		}
	}

	private ModeloEitvInt getModelo() {
		if (modeloEitv == null) {
			modeloEitv = new ModeloEitvImpl();
		}
		return modeloEitv;
	}

	@Override
	public void guardarXmlRespuesta(EitvQueryWSResponse respuestaWS, String numExpediente) {
		FicheroBean fichero = new FicheroBean();
		fichero.setTipoDocumento(ConstantesGestorFicheros.MATE);
		fichero.setSubTipo(ConstantesGestorFicheros.RESPUESTAEITV);
		fichero.setNombreDocumento(ConstantesGestorFicheros.CONSULTA_EITV+numExpediente);
		fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
		fichero.setFecha(Utilidades.transformExpedienteFecha(numExpediente));

		try {
			byte[] xmlData = ContextoSpring.getInstance().getBean(Utiles.class).doBase64Decode(respuestaWS.getXmldata(), UTF_8);
			fichero.setFicheroByte(xmlData);
			getGuardarDocumentos().guardarFichero(fichero);
		} catch (OegamExcepcion e) {
			log.error("Error al guardar el documento",e, numExpediente);
		} catch (Exception e) {
			log.error("Error al guardar el documento, error al decodificar", e, numExpediente);
		}
	}

	public String recogerXmlEitv(ColaBean solicitud) throws FileNotFoundException {
		File ficheroAenviar = null;
		try {
			ficheroAenviar = getGuardarDocumentos().buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.EITV,
					Utilidades.transformExpedienteFecha(solicitud.getIdTramite()), ConstantesGestorFicheros.NOMBRE_EITV+solicitud.getIdTramite(), ConstantesGestorFicheros.EXTENSION_XML).getFile();
		} catch (OegamExcepcion e) {
			log.error("Error al recuperar el documento.");
		}

		return new XMLConsultaTarjetaEITVFactory().xmlFileToString(ficheroAenviar);
	}

}