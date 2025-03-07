package trafico.servicio.implementacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import oegam.constantes.ConstantesPQ;
import trafico.beans.TramiteTraficoMatriculacionBean;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.utiles.CampoPdfBean;
import trafico.modelo.ModeloMatriculacion;
import trafico.modelo.ModeloTransmision;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.PdfMaker;
import trafico.utiles.UtilResources;
import trafico.utiles.enumerados.ReduccionNoSujeccionOExencion05;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service("imprimirSolicitud05Servicio")
@Transactional
public class ImprimirSolicitud05ServicioImp implements ImprimirSolicitu05Servicio {

	@Autowired
	private ServicioTramiteTrafico servicioTrafico;

	@Autowired
	private UtilesColegiado utilesColegiado;

	// CONSTANTES CAMPOS PDF
	private static final String TIPO_EXENCION = "exenciones";
	private static final String NIF_TITULAR = "nif titular";
	private static final String APELLIDOS_NOMBRE_TITULAR = "apellidos titular";
	private static final String CALLE = "calle";
	private static final String VIA = "nombre via";
	private static final String NUMERO = "numero";

	private static final String NUMERO_BASTIDOR = "Bastidor";

	private static final String RUTA = "/trafico/plantillasPDF/Plantilla05.pdf";

	private static final ILoggerOegam log = LoggerOegam.getLogger(ImprimirSolicitud05ServicioImp.class);

	public ResultBean generarSolicitud(String numExpediente){
		int[] vectPags = new int[ConstantesPDF._1];
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<>();
		ResultBean resultadoMetodo = new ResultBean();
		PdfMaker pdf = new PdfMaker();
		vectPags[0] = ConstantesPDF._1;

		//-------------------------------------------------------------------------------------------------

		// Recupero el trámite
		TramiteTraficoVO tramite = servicioTrafico.getTramite(new BigDecimal(numExpediente), true);
		log.info("Se ha recuperado correctamente el tramite para el expediente: " + numExpediente);

		//Validamos que el trámite sea correcto
		if (tramite == null) {
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("No se ha podido recuperar el trámite.");
			return resultadoMetodo;
		} else if (!(tramite.getTipoTramite().equals(TipoTramiteTrafico.Matriculacion.toString()))
				&& !(tramite.getTipoTramite().equals(TipoTramiteTrafico.TransmisionElectronica.toString()))) {
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("Esta solicitud solo está disponible para los trámites de Matriculación y Transmisión Electrónica.");
			return resultadoMetodo;
		} else if (!(tramite.getEstado().toString().equals(EstadoTramiteTrafico.Iniciado.toString()))) {
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("Esta solicitud solo está disponible en el estado iniciado.");
			return resultadoMetodo;
		} else if (tramite.getFechaPresentacion() == null) {
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("Este trámite aun no tiene Fecha de Presentación de matrícula telemática");
			return resultadoMetodo;
		}

		// Obtenemos el detalle del trámite
		TramiteTraficoTransmisionBean traficoTramiteTransmisionBean = null;
		TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean = null;
		Map<String, Object> mapResultado = new HashMap<String, Object>();
		if (tramite.getTipoTramite().equals(TipoTramiteTrafico.Matriculacion.toString())) {
			ModeloMatriculacion modeloMatriculacion = new ModeloMatriculacion();
			mapResultado = modeloMatriculacion.obtenerDetalle(new BigDecimal(numExpediente), utilesColegiado.getNumColegiadoSession(), utilesColegiado.getIdContratoSessionBigDecimal());
			traficoTramiteMatriculacionBean = (TramiteTraficoMatriculacionBean) mapResultado.get(ConstantesPQ.BEANPANTALLA);
		} else {
			ModeloTransmision modeloTransmision = new ModeloTransmision();
			mapResultado = modeloTransmision.obtenerDetalle(new BigDecimal(numExpediente));
			traficoTramiteTransmisionBean = (TramiteTraficoTransmisionBean) mapResultado.get(ConstantesPQ.BEANPANTALLA);
		}

		log.info("Se ha obtenido correctamente el detalle del tramite: " + numExpediente);

		//Abro la plantilla del PDF que corresponda
		UtilResources util = new UtilResources();
		String file = util.getFilePath(RUTA);
		byte[] byte1 = pdf.abrirPdf(file);
		Set<String> camposPlantilla = pdf.getAllFields(byte1);

		// Proceso el PDF - Estas validaciones solo se pueden hacer cuando ya se tiene el detalle del trámite
		String tipoExencion = calcularexencion(traficoTramiteTransmisionBean, traficoTramiteMatriculacionBean, tramite);
		if (tipoExencion != null)
			procesarTipoExencion(camposPlantilla, camposFormateados, tipoExencion);
		else {
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("El documento no esta disponible para este tipo de exención o no procede para la categoría EU del vehículo.");
			return resultadoMetodo;
		}

		Boolean titularCorrecto = procesarTitular(camposPlantilla, camposFormateados, traficoTramiteTransmisionBean, traficoTramiteMatriculacionBean, tramite);
		if (!titularCorrecto) {
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("No existe titular o adquiriente para este trámite");
			return resultadoMetodo;
		}

		// Resto del PDF
		procesarNumeroExpNumeroBastidor(camposPlantilla, camposFormateados, tramite);

		byte1 = pdf.setCampos(byte1, camposFormateados);
		resultadoMetodo.setError(false);
		resultadoMetodo.addAttachment("pdf", byte1);

		return resultadoMetodo;
	}

	private void procesarTipoExencion(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoExencion) {
		if (camposPlantilla.contains(TIPO_EXENCION)) {
			String campo = tipoExencion;
			CampoPdfBean campoAux = new CampoPdfBean(TIPO_EXENCION, campo, false, false, ConstantesPDF._10);
			camposFormateados.add(campoAux);
		}
	}

	private Boolean procesarTitular(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, TramiteTraficoTransmisionBean traficoTramiteTransmisionBean, TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean, TramiteTraficoVO tramite) {
		try {
			Persona titular = null;
			if (tramite.getTipoTramite().equals(TipoTramiteTrafico.Matriculacion.toString())) {
				titular = traficoTramiteMatriculacionBean.getTitularBean().getPersona();
			} else {
				titular = traficoTramiteTransmisionBean.getAdquirienteBean().getPersona();
			}

			if (titular.getNif() == null || titular.getApellido1RazonSocial() == null)
				return false;

			if (camposPlantilla.contains(NIF_TITULAR)) {
				String campo = titular.getNif();
				CampoPdfBean campoAux = new CampoPdfBean(NIF_TITULAR, campo, false, false, ConstantesPDF._10);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(APELLIDOS_NOMBRE_TITULAR)) {
				String campo = (titular.getApellido1RazonSocial()) + " "+ (titular.getApellido2()) + ", "+ (titular.getNombre());
				CampoPdfBean campoAux = new CampoPdfBean(APELLIDOS_NOMBRE_TITULAR, campo, false, false, ConstantesPDF._10);
				camposFormateados.add(campoAux);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	private void procesarNumeroExpNumeroBastidor(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, TramiteTraficoVO tramite) {
		if (camposPlantilla.contains(NUMERO_BASTIDOR)) {
			String campo = tramite.getVehiculo().getBastidor();
			CampoPdfBean campoAux = new CampoPdfBean(NUMERO_BASTIDOR, campo, false, false, ConstantesPDF._10);
			camposFormateados.add(campoAux);
		}
	}

	private String calcularexencion(TramiteTraficoTransmisionBean traficoTramiteTransmisionBean, TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean, TramiteTraficoVO tramite){
		String exencion = null;
		ReduccionNoSujeccionOExencion05 tipoExencion = null;

		if (tramite.getTipoTramite().equals(TipoTramiteTrafico.Matriculacion.toString())) {
			tipoExencion = traficoTramiteMatriculacionBean.getIdReduccion05();
		} else {
			tipoExencion = traficoTramiteTransmisionBean.getIdReduccion05();
		}

		if (tipoExencion == null)
			return exencion;

		if ((tipoExencion.toString().equals(ReduccionNoSujeccionOExencion05.RE1.toString())))
			exencion = "RE1";
		else if ((tipoExencion.toString().equals(ReduccionNoSujeccionOExencion05.NS1.toString())))
			exencion = "NS1";
		else if (tipoExencion.toString().equals(ReduccionNoSujeccionOExencion05.NS2.toString()))
			exencion = "NS2";
		else if (tipoExencion.toString().equals(ReduccionNoSujeccionOExencion05.ER1.toString()))
			exencion = "ER1";
		else if (tipoExencion.toString().equals(ReduccionNoSujeccionOExencion05.ER2.toString()))
			exencion = "ER2";
		else if (tipoExencion.toString().equals(ReduccionNoSujeccionOExencion05.ER3.toString()))
			exencion = "ER3";
		else if (tipoExencion.toString().equals(ReduccionNoSujeccionOExencion05.ER4.toString()))
			exencion = "ER4";

		return exencion;
	}

}