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
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
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
import trafico.utiles.enumerados.NoSujeccionOExencion;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service("imprimirSolicitudNRE06Servicio")
@Transactional
public class ImprimirNRE06Servicio implements ImprimirNRE06ServicioI {

	@Autowired
	private ServicioTramiteTrafico servicioTrafico;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;

	// CONSTANTES CAMPOS PDF
	private static final String TIPO_EXENCION = "Cuadro combinado2";
	private static final String NIF_TITULAR = "nif titular";
	private static final String APELLIDOS_TITULAR = "apellidos titular";
	private static final String NOMBRE_TITULAR = "nombre titular";
	private static final String NIF_COLEGIADO = "nif colegiado";
	private static final String APELLIDOS_COLEGIADO = "apellidos Colegiado";
	private static final String NOMBRE_COLEGIADO = "Nombre colegiado";
	private static final String FECHA_PRESENTACION_DIA = "dia";
	private static final String FECHA_PRESENTACION_MES = "mes";
	private static final String FECHA_PRESENTACION_ANIO = "año";
	private static final String NUMERO_EXPEDIENTE = "número exp OEgAM";
	private static final String NUMERO_BASTIDOR = "Bastidor";

	private static final String RUTA = "/trafico/plantillasPDF/PlantillaNRE06.pdf";

	private static final ILoggerOegam log = LoggerOegam.getLogger(ImprimirNRE06Servicio.class);

	public ResultBean generarSolicitud(String numExpediente) {
		int[] vectPags = new int[ConstantesPDF._1];
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<>();
		ResultBean resultadoMetodo = new ResultBean();
		PdfMaker pdf = new PdfMaker();
		vectPags[0] = ConstantesPDF._1;

		//-------------------------------------------------------------------------------------------------

		// Recupero el trámite
		TramiteTraficoVO tramite = servicioTrafico.getTramite(new BigDecimal(numExpediente), true);
		log.info("Se ha recuperado correctamente el tramite para el expediente: " + numExpediente);

		// Validamos que el trámite sea correcto
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

		//Proceso el PDF - Estas validaciones solo se pueden hacer cuando ya se tiene el detalle del tramite
		String tipoExencion = calcularexencion(traficoTramiteTransmisionBean, traficoTramiteMatriculacionBean, tramite);
		if (tipoExencion != null)
			procesarTipoExencion(camposPlantilla, camposFormateados, tipoExencion);
		else {
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("El documento no está disponible para este tipo de exención o no procede para la categoría EU del vehículo.");
			return resultadoMetodo;
		}

		Boolean titularCorrecto = procesarTitular(camposPlantilla, camposFormateados, traficoTramiteTransmisionBean, traficoTramiteMatriculacionBean, tramite);
		if (!titularCorrecto){
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("No existe titular o adquiriente para este trámite");
			return resultadoMetodo;
		}

		// Resto del PDF
		procesarColegiado(camposPlantilla, camposFormateados, tramite);
		procesarFechaPresentacion(camposPlantilla, camposFormateados, tramite);
		procesarNumeroExpNumeroBastidor(camposPlantilla, camposFormateados, tramite);

		byte1 = pdf.setCampos(byte1, camposFormateados);
		resultadoMetodo.setError(false);
		resultadoMetodo.addAttachment("pdf", byte1);

		return resultadoMetodo;
	}

	private void procesarTipoExencion(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoExencion) {
		if (camposPlantilla.contains(TIPO_EXENCION)) {
			String campo = tipoExencion;
			CampoPdfBean campoAux = new CampoPdfBean(TIPO_EXENCION, campo, false, false, ConstantesPDF._12);
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
				CampoPdfBean campoAux = new CampoPdfBean(NIF_TITULAR, campo, false, false, ConstantesPDF._12);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(APELLIDOS_TITULAR)) {
				String campo = titular.getApellido1RazonSocial();
				CampoPdfBean campoAux = new CampoPdfBean(APELLIDOS_TITULAR, campo, false, false, ConstantesPDF._12);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(NOMBRE_TITULAR)) {
				String campo = titular.getNombre();
				CampoPdfBean campoAux = new CampoPdfBean(NOMBRE_TITULAR, campo, false, false, ConstantesPDF._12);
				camposFormateados.add(campoAux);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private void procesarColegiado(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, TramiteTraficoVO tramite) {
		if (camposPlantilla.contains(NIF_COLEGIADO)) {
			String campo = tramite.getContrato().getColegiado().getUsuario().getNif();
			CampoPdfBean campoAux = new CampoPdfBean(NIF_COLEGIADO, campo, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(APELLIDOS_COLEGIADO)) {
			String[] nombre = tramite.getContrato().getColegiado().getUsuario().getApellidosNombre().split(",");
			String campo = "";
			if (nombre.length > 0)
				campo = nombre[0];
			CampoPdfBean campoAux = new CampoPdfBean(APELLIDOS_COLEGIADO, campo, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(NOMBRE_COLEGIADO)) {
			String[] nombre = tramite.getContrato().getColegiado().getUsuario().getApellidosNombre().split(",");
			String campo = "";
			if (nombre.length > 1)
				campo = nombre[1];
			CampoPdfBean campoAux = new CampoPdfBean(NOMBRE_COLEGIADO, campo, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}
	}

	private void procesarFechaPresentacion(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, TramiteTraficoVO tramite) {
		Fecha fecha = utilesFecha.getFechaFracionada(tramite.getFechaPresentacion());

		if (camposPlantilla.contains(FECHA_PRESENTACION_DIA)) {
			String campo = fecha.getDia();
			CampoPdfBean campoAux = new CampoPdfBean(FECHA_PRESENTACION_DIA, campo, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(FECHA_PRESENTACION_MES)) {
			String campo = fecha.getMes();
			CampoPdfBean campoAux = new CampoPdfBean(FECHA_PRESENTACION_MES, campo, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(FECHA_PRESENTACION_ANIO)) {
			String campo = fecha.getAnio();
			CampoPdfBean campoAux = new CampoPdfBean(FECHA_PRESENTACION_ANIO, campo, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}
	}

	private void procesarNumeroExpNumeroBastidor(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, TramiteTraficoVO tramite) {
		if (camposPlantilla.contains(NUMERO_EXPEDIENTE)) {
			String campo = tramite.getNumExpediente().toString();
			CampoPdfBean campoAux = new CampoPdfBean(NUMERO_EXPEDIENTE, campo, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(NUMERO_BASTIDOR)) {
			String campo = tramite.getVehiculo().getBastidor();
			CampoPdfBean campoAux = new CampoPdfBean(NUMERO_BASTIDOR, campo, false, false, ConstantesPDF._12);
			camposFormateados.add(campoAux);
		}
	}

	private String calcularexencion(TramiteTraficoTransmisionBean traficoTramiteTransmisionBean, TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean, TramiteTraficoVO tramite){
		String exencion = null;
		NoSujeccionOExencion tipoExencion = null;
		String homologacion = null;

		if (tramite.getTipoTramite().equals(TipoTramiteTrafico.Matriculacion.toString())) {
			tipoExencion = traficoTramiteMatriculacionBean.getIdNoSujeccion06();
			if (traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo() != null && traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHomologacionBean() != null) {
				homologacion = traficoTramiteMatriculacionBean.getTramiteTraficoBean().getVehiculo().getHomologacionBean().getIdHomologacion();
			}
		} else {
			tipoExencion = traficoTramiteTransmisionBean.getIdNoSujeccion06();
			if (traficoTramiteTransmisionBean.getTramiteTraficoBean().getVehiculo() != null && traficoTramiteTransmisionBean.getTramiteTraficoBean().getVehiculo().getHomologacionBean() != null) {
				homologacion = traficoTramiteTransmisionBean.getTramiteTraficoBean().getVehiculo().getHomologacionBean().getIdHomologacion();
			}
		}

		if (tipoExencion == null)
			return exencion;

		String nuevaValidacion06 = gestorPropiedades.valorPropertie("nuevas.validaciones.modelo.06");
		if ("SI".equals(nuevaValidacion06)) {
			return tipoExencion.toString();
		} else {
			if (tipoExencion.toString().equals(NoSujeccionOExencion.NS1.toString()))
					exencion = "NS1";
			else if (tipoExencion.toString().equals(NoSujeccionOExencion.NS2.toString()))
				exencion = "NS2";
			else if (tipoExencion.toString().equals(NoSujeccionOExencion.NS3.toString()))
				exencion = "NS3";
			else if (tipoExencion.toString().equals(NoSujeccionOExencion.NS4.toString()))
				exencion = "NS4";
			else if (tipoExencion.toString().equals(NoSujeccionOExencion.NS5.toString()))
				exencion = "NS5";
			else if (tipoExencion.toString().equals(NoSujeccionOExencion.NS6.toString()))
				exencion = "NS6";
			else if (tipoExencion.toString().equals(NoSujeccionOExencion.NS7.toString()))
				exencion = "NS7";
		}
		return exencion;
	}

}