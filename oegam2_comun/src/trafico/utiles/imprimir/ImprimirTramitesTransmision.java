package trafico.utiles.imprimir;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import oegam.constantes.ConstantesPQ;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.utiles.CampoPdfBean;
import trafico.modelo.ModeloCreditosTrafico;
import trafico.modelo.ModeloTransmision;
import trafico.utiles.ComprobadorDatosSensibles;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.PdfMaker;
import trafico.utiles.UtilResources;
import trafico.utiles.enumerados.TipoImpreso;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ImprimirTramitesTransmision extends ImprimirGeneral{

	private static final ILoggerOegam log = LoggerOegam.getLogger(ImprimirTramitesTransmision.class);

	private ModeloTransmision modeloTransmision;
	private ModeloCreditosTrafico modeloCreditosTrafico;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	public ResultBean transmisionMandatoGenerico(String numExpediente, String interviniente) throws OegamExcepcion {

		TramiteTraficoTransmisionBean detalleTransmision = new TramiteTraficoTransmisionBean(true);
		ResultBean resultadoMetodo = new ResultBean();
		//String password = ficheroPropiedades.getMensaje(PASSWORD);
		String ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS);
		Boolean error = false;
		String mensaje = "";

		//Obtenemos el detalle del tr�mite
		Map<String, Object> resultadoDetalle = getModeloTransmision().obtenerDetalleConDescripciones(new BigDecimal(numExpediente));
		resultadoMetodo = (ResultBean) resultadoDetalle.get(ConstantesPQ.RESULTBEAN);
		detalleTransmision = (TramiteTraficoTransmisionBean) resultadoDetalle.get(ConstantesPQ.BEANPANTALLA);

		// -- Inicio comprobaci�n de datos sensibles
		try {
			List<String> expedientesDatosSensibles = new ComprobadorDatosSensibles().isSensibleData(new String[]{numExpediente});
			if(expedientesDatosSensibles!=null && !expedientesDatosSensibles.isEmpty()){
				for (String expedienteSensible : expedientesDatosSensibles){
					resultadoMetodo.setMensaje("Se ha recibido un error t�cnico. No intenten presentar el tramite " + expedienteSensible + ". Les rogamos comuniquen con el Colegio.");
					log.error("El expediente "+expedienteSensible+" ha intentado obtener el mandado, y contiene datos sensibles");
				}
				resultadoMetodo.setError(true);
				return resultadoMetodo;
			}
		} catch (Exception e) {}
		// -- Fin de comprobaci�n de datos sensibles

		IntervinienteTrafico tipoRepresenta = new IntervinienteTrafico();
		IntervinienteTrafico tipoRepresentanteRepresenta = new IntervinienteTrafico();

		if (interviniente.equals("Transmitente")){
			tipoRepresenta = detalleTransmision.getTransmitenteBean();
			tipoRepresentanteRepresenta = detalleTransmision.getRepresentanteTransmitenteBean();
		}else if(interviniente.equals("Adquiriente")){
			tipoRepresenta = detalleTransmision.getAdquirienteBean();
			tipoRepresentanteRepresenta = detalleTransmision.getRepresentanteAdquirienteBean();
		}else if(interviniente.equals("Compraventa")){
			tipoRepresenta =detalleTransmision.getPoseedorBean();
			tipoRepresentanteRepresenta = detalleTransmision.getRepresentantePoseedorBean();
		}

		// Incidencia : 1613
		// Autor : Ricardo Rodriguez
		// Fecha : 07/05/2012
		// Descripcion : Extensa. Ver notas de la incidencia en el Mantis
		// Resumen : Verificar que el tr�mite posee en el momento de la impresi�n los datos m�nimos
		// requeridos para la correcta cumplimentaci�n del mandato gen�rico y/o espec�fico
		// INICIO NEW
		if(tipoRepresenta == null || tipoRepresenta.getPersona() == null ||
				tipoRepresenta.getPersona().getNif() == null ||
				tipoRepresenta.getPersona().getNif().equals("") ||
				tipoRepresenta.getPersona().getApellido1RazonSocial() == null ||
				tipoRepresenta.getPersona().getApellido1RazonSocial().equals("") ||
				tipoRepresenta.getPersona().getSexo() == null || 
				tipoRepresenta.getPersona().getSexo().equals("")){
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("No se han recuperado los datos m�nimos requeridos del " +
					interviniente);
			return resultadoMetodo;
		}
		if(tipoRepresenta.getPersona().getSexo().equalsIgnoreCase("X")){
			// El interviniente es persona jur�dica. Verifica los datos m�nimos de su representante:
			if(tipoRepresentanteRepresenta == null || tipoRepresentanteRepresenta.getPersona() == null ||
					tipoRepresentanteRepresenta.getPersona().getNif() == null ||
					tipoRepresentanteRepresenta.getPersona().getNif().equals("") ||
					tipoRepresentanteRepresenta.getPersona().getApellido1RazonSocial() == null ||
					tipoRepresentanteRepresenta.getPersona().getApellido1RazonSocial().equals("") ||
					tipoRepresentanteRepresenta.getPersona().getSexo() == null || 
					tipoRepresentanteRepresenta.getPersona().getSexo().equals("")){
				resultadoMetodo.setError(true);
				resultadoMetodo.setMensaje("No se han recuperado los datos m�nimos requeridos del " +
						" representante del " + interviniente);
				return resultadoMetodo;
			}
		}
		// FIN NEW

		if(resultadoMetodo.getError()){
			return resultadoMetodo;
		}

		byte[] byte1 = null;
		PdfMaker pdf = null;
		pdf = new PdfMaker();
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<>();

		//Abro la plantilla del PDF que corresponda
			ruta += TipoImpreso.MatriculacionMandatoGenerico.getNombreEnum();

		UtilResources util = new UtilResources();
		String file = util.getFilePath(ruta);

		//Abro la plantilla del PDF que corresponda
		byte1 = pdf.abrirPdf(file);

		//Obtenemos todos los campos de la plantilla correspondiente y los mapeamos con los valores del bean.
		Set<String> camposPlantilla = pdf.getAllFields(byte1);

		//Datos presentacion en Tramitetrafico Bean, Com�n para todos los m�todos
		if(null==detalleTransmision){
			error = true;
			mensaje = "Los datos del tr�mite est�n vac�os";
		} else{
			camposFormateados.addAll(obtenerValoresMandato(ConstantesPDF._11, tipoRepresenta, tipoRepresentanteRepresenta,detalleTransmision.getTramiteTraficoBean().getIdContrato(), camposPlantilla));
			if(camposFormateados==null){
				error = true;
				mensaje = "Se ha producido un error al formatear los campos.";
			} else{
				byte1 = pdf.setCampos(byte1, camposFormateados);
			}
		}

		//Una vez que hemos completado todos los cambios del PDF procedemos a comprobar que haya habido errores 
		//y lo pasamos el resultado del m�todo
		if(error==true || byte1 == null){
			error = true;
			mensaje += ". Puede que no est� completo el tr�mite impreso.";
		}

		//fin textos externos en impreso

		resultadoMetodo.setError(error);
		resultadoMetodo.setMensaje(mensaje);

		resultadoMetodo.addAttachment("pdf", byte1);

		return resultadoMetodo;
	}

	public ResultBean transmisionMandatoEspecifico(String numExpediente, String interviniente) throws OegamExcepcion {
		TramiteTraficoTransmisionBean detalleTransmision = new TramiteTraficoTransmisionBean(true);
		ResultBean resultadoMetodo = new ResultBean();
		String ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS);
		Boolean error = false;
		String mensaje = "";

		//Obtenemos el detalle del tr�mite
		Map<String, Object> resultadoDetalle = getModeloTransmision().obtenerDetalleConDescripciones(new BigDecimal(numExpediente));
		resultadoMetodo = (ResultBean) resultadoDetalle.get(ConstantesPQ.RESULTBEAN);
		detalleTransmision = (TramiteTraficoTransmisionBean) resultadoDetalle.get(ConstantesPQ.BEANPANTALLA);

		// -- Inicio comprobaci�n de datos sensibles
		try {
			List<String> expedientesDatosSensibles = new ComprobadorDatosSensibles().isSensibleData(new String[]{numExpediente});
			if(expedientesDatosSensibles!=null && expedientesDatosSensibles.size()>0){
				for (String expedienteSensible : expedientesDatosSensibles){
					resultadoMetodo.setMensaje("Se ha recibido un error t�cnico. No intenten presentar el tramite " + expedienteSensible + ". Les rogamos comuniquen con el Colegio.");
					log.error("El expediente "+expedienteSensible+" ha intentado obtener el mandado, y contiene datos sensibles");
				}
				resultadoMetodo.setError(true);
				return resultadoMetodo;
			}
		} catch (Exception e) {}
		// -- Fin de comprobaci�n de datos sensibles

		IntervinienteTrafico tipoRepresenta = new IntervinienteTrafico();
		IntervinienteTrafico tipoRepresentanteRepresenta = new IntervinienteTrafico();

		if (interviniente.equals("Transmitente")){
			tipoRepresenta = detalleTransmision.getTransmitenteBean();
			tipoRepresentanteRepresenta = detalleTransmision.getRepresentanteTransmitenteBean();
		}else if(interviniente.equals("Adquiriente")){
			tipoRepresenta = detalleTransmision.getAdquirienteBean();
			tipoRepresentanteRepresenta = detalleTransmision.getRepresentanteAdquirienteBean();
		}else if(interviniente.equals("Compraventa")){
			tipoRepresenta =detalleTransmision.getPoseedorBean();
			tipoRepresentanteRepresenta = detalleTransmision.getRepresentantePoseedorBean();
		}

		// Incidencia : 1613
		// Autor : Ricardo Rodriguez
		// Fecha : 07/05/2012
		// Descripcion : Extensa. Ver notas de la incidencia en el Mantis
		// Resumen : Verificar que el tr�mite posee en el momento de la impresi�n los datos m�nimos
		// requeridos para la correcta cumplimentaci�n del mandato gen�rico y/o espec�fico
		// INICIO NEW
		if(tipoRepresenta == null || tipoRepresenta.getPersona() == null ||
				tipoRepresenta.getPersona().getNif() == null ||
				tipoRepresenta.getPersona().getNif().equals("") ||
				tipoRepresenta.getPersona().getApellido1RazonSocial() == null ||
				tipoRepresenta.getPersona().getApellido1RazonSocial().equals("") ||
				tipoRepresenta.getPersona().getSexo() == null || 
				tipoRepresenta.getPersona().getSexo().equals("")){
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("No se han recuperado los datos m�nimos requeridos del " +
					interviniente);
			return resultadoMetodo;
		}
		if(tipoRepresenta.getPersona().getSexo().equalsIgnoreCase("X")){
			// El interviniente es persona jur�dica. Verifica los datos m�nimos de su representante:
			if(tipoRepresentanteRepresenta == null || tipoRepresentanteRepresenta.getPersona() == null ||
					tipoRepresentanteRepresenta.getPersona().getNif() == null ||
					tipoRepresentanteRepresenta.getPersona().getNif().equals("") ||
					tipoRepresentanteRepresenta.getPersona().getApellido1RazonSocial() == null ||
					tipoRepresentanteRepresenta.getPersona().getApellido1RazonSocial().equals("") ||
					tipoRepresentanteRepresenta.getPersona().getSexo() == null || 
					tipoRepresentanteRepresenta.getPersona().getSexo().equals("")){
				resultadoMetodo.setError(true);
				resultadoMetodo.setMensaje("No se han recuperado los datos m�nimos requeridos del " +
						" representante del " + interviniente);
				return resultadoMetodo;
			}
		}
		// FIN NEW

		if(resultadoMetodo.getError()){
			return resultadoMetodo;
		}

		byte[] byte1 = null;
		PdfMaker pdf = null;
		pdf = new PdfMaker();
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<>();
		CampoPdfBean campoAux = new CampoPdfBean();

		//Abro la plantilla del PDF que corresponda
			ruta += TipoImpreso.MatriculacionMandatoEspecifico.getNombreEnum();

		UtilResources util = new UtilResources();
		String file = util.getFilePath(ruta);

		//Abro la plantilla del PDF que corresponda
		byte1 = pdf.abrirPdf(file);

		//Obtenemos todos los campos de la plantilla correspondiente y los mapeamos con los valores del bean.
		Set<String> camposPlantilla = pdf.getAllFields(byte1);

		//Datos presentacion en Tramitetrafico Bean, Com�n para todos los m�todos
		if(null==detalleTransmision){
			error = true;
			mensaje = "Los datos del tr�mite est�n vac�os";
		} else{
			camposFormateados.addAll(obtenerValoresMandato(ConstantesPDF._11, tipoRepresenta, tipoRepresentanteRepresenta,detalleTransmision.getTramiteTraficoBean().getIdContrato(), camposPlantilla));

			if(camposFormateados==null){
				error = true;
				mensaje = "Se ha producido un error al formatear los campos.";
			} else{
				byte1 = pdf.setCampos(byte1, camposFormateados);
			}
		}

		//Va sin acento por que no la muestra en la plantilla.
		if(camposPlantilla.contains(ConstantesPDF.ID_ASUNTO1_MOTIVO)){
			campoAux = new CampoPdfBean(ConstantesPDF.ID_ASUNTO1_MOTIVO, "TRANSFERENCIA: ",
					false, false, ConstantesPDF._11);
			camposFormateados.add(campoAux);
		}

		if(camposPlantilla.contains(ConstantesPDF.ID_ASUNTO1_EXPLICACION)){
			String asuntoValor = "";

			if (detalleTransmision.getTramiteTraficoBean().getVehiculo().getBastidor()!=null) {
				asuntoValor = "BAST: " + detalleTransmision.getTramiteTraficoBean().getVehiculo().getBastidor();
			}

			if (detalleTransmision.getTramiteTraficoBean().getVehiculo().getMatricula()!=null) {
				asuntoValor = asuntoValor + "  MAT: " + detalleTransmision.getTramiteTraficoBean().getVehiculo().getMatricula().toString();
			}

			campoAux = new CampoPdfBean(ConstantesPDF.ID_ASUNTO1_EXPLICACION,asuntoValor,false, false, ConstantesPDF._11);

			camposFormateados.add(campoAux);
		}

		//Una vez que hemos completado todos los cambios del PDF procedemos a comprobar que haya habido errores
		//y lo pasamos el resultado del m�todo
		if(error || byte1 == null){
			error = true;
			mensaje += ". Puede que no est� completo el tr�mite impreso.";
		}

		//Genero y formateo las nubes de puntos

		byte1 = pdf.setCampos(byte1, camposFormateados);

		if(null==byte1){
			error = true;
			mensaje = "No se ha cargado la plantilla de Mandato.";
		}
		//fin textos externos en impreso

		resultadoMetodo.setError(error);
		resultadoMetodo.setMensaje(mensaje);

		resultadoMetodo.addAttachment("pdf", byte1);

		return resultadoMetodo;
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */

	public ModeloTransmision getModeloTransmision() {
		if (modeloTransmision == null) {
			modeloTransmision = new ModeloTransmision();
		}
		return modeloTransmision;
	}

	public void setModeloTransmision(ModeloTransmision modeloTransmision) {
		this.modeloTransmision = modeloTransmision;
	}

	public ModeloCreditosTrafico getModeloCreditosTrafico() {
		if (modeloCreditosTrafico == null) {
			modeloCreditosTrafico = new ModeloCreditosTrafico();
		}
		return modeloCreditosTrafico;
	}

	public void setModeloCreditosTrafico(ModeloCreditosTrafico modeloCreditosTrafico) {
		this.modeloCreditosTrafico = modeloCreditosTrafico;
	}

}