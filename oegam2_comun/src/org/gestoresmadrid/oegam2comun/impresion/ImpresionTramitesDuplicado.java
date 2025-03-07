package org.gestoresmadrid.oegam2comun.impresion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TextoNotificacion;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.impresion.util.ImpresionGeneral;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoDuplicado;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDuplicadoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import trafico.beans.utiles.CampoPdfBean;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.PdfMaker;
import trafico.utiles.UtilResources;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ImpresionTramitesDuplicado extends ImpresionGeneral {

	private static ILoggerOegam log = LoggerOegam.getLogger(ImpresionTramitesDuplicado.class);

	private TramiteTrafDuplicadoDto detalleDuplicado;
	private ArrayList<TramiteTrafDuplicadoDto> listaTramitesDuplicados;

	private ArrayList<CampoPdfBean> camposFormateados;
	private Set<String> camposPlantilla;

	private String ruta;

	private byte[] bytePdf;
	private PdfMaker pdf;

	private String mensaje;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	// Tipo impreso: Listado de bastidores
	public ResultBean matriculacionListadoBastidoresDuplicado(List<String> numExpedientes, BigDecimal idUsuario) throws OegamExcepcion, Throwable {
		ResultBean resultadoMetodo = new ResultBean();
		pdf = new PdfMaker();

		listaTramitesDuplicados = new ArrayList<TramiteTrafDuplicadoDto>();

		int numExpedientesEscritos = 0;
		// --------------------------------------------------------------------------------

		for (String numExpediente : numExpedientes) {
			obtenerDetalleTramiteDuplicado(numExpediente, TipoImpreso.MatriculacionListadoBastidores.toString());
		}

		TramiteTrafDuplicadoDto tramite = listaTramitesDuplicados.get(0);
		boolean mismoTitular = esMismoTitular(tramite);

		calcularRuta(TipoImpreso.MatriculacionListadoBastidores.toString(), mismoTitular);

		byte[] byteFinal = generarPDFListadoBastidores(tramite, mismoTitular, numExpedientesEscritos, resultadoMetodo, idUsuario);

		if (byteFinal != null) {
			resultadoMetodo.addAttachment(ResultBean.TIPO_PDF, byteFinal);
			resultadoMetodo.addAttachment(ResultBean.NOMBRE_FICHERO, "listadoMatriculas.pdf");
			resultadoMetodo.setError(false);
		} else {
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("Error al generar el PDF del listado de Matrículas");
		}

		return resultadoMetodo;
	}

	private void obtenerDetalleTramiteDuplicado(String numExpediente, String tipoImpreso) throws Throwable {
		detalleDuplicado = new TramiteTrafDuplicadoDto();
		ServicioTramiteTraficoDuplicado servicioTramiteTraficoDuplicado = ContextoSpring.getInstance().getBean(ServicioTramiteTraficoDuplicado.class);
		detalleDuplicado = servicioTramiteTraficoDuplicado.getTramiteDuplicado(new BigDecimal(numExpediente), true);
		if (tipoImpreso != null && tipoImpreso.equals(TipoImpreso.MatriculacionListadoBastidores.toString())) {
			listaTramitesDuplicados.add(detalleDuplicado);
		}
	}

	private boolean esMismoTitular(TramiteTrafDuplicadoDto tramite) {
		boolean mismoTitular = true;
		String dniTitular = null != tramite.getTitular().getPersona().getNif() ? tramite.getTitular().getPersona().getNif() : "";
		for (TramiteTrafDuplicadoDto linea : listaTramitesDuplicados) {
			if (null == linea.getTitular().getPersona().getNif() || !linea.getTitular().getPersona().getNif().equals(dniTitular)) {
				mismoTitular = false;
				break;
			}
		}
		return mismoTitular;
	}

	private void calcularRuta(String tipoImpreso, boolean mismoTitular) throws OegamExcepcion {
		UtilResources util = new UtilResources();
		ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS);

		if (tipoImpreso.equals(TipoImpreso.MatriculacionListadoBastidores.toString())) {
			if (mismoTitular) {
				ruta = util.getFilePath(ruta + TipoImpreso.ListadoMatriculas1_BD.getNombreEnum());
			} else {
				ruta = util.getFilePath(ruta + TipoImpreso.ListadoMatriculas2_BD.getNombreEnum());
			}
		} else if (tipoImpreso.equals(TipoImpreso.MatriculacionMandatoGenerico.toString())) {
			ruta = util.getFilePath(ruta + TipoImpreso.MatriculacionMandatoGenerico.getNombreEnum());
		} else if (tipoImpreso.equals(TipoImpreso.MatriculacionMandatoEspecifico.toString())) {
			ruta = util.getFilePath(ruta + TipoImpreso.MatriculacionMandatoEspecifico.getNombreEnum());
		}
	}

	private byte[] generarPDFListadoBastidores(TramiteTrafDuplicadoDto tramite, boolean mismoTitular, int numExpedientesEscritos, ResultBean resultadoMetodo, BigDecimal idUsuario) {
		byte[] byteFinal = null;

		int numLinea = 0;
		int numPag = 0;
		float xPosInicial = ConstantesPDF._f73;
		float xPosFinal = ConstantesPDF._f537;
		float yPos = ConstantesPDF._f610;
		// -------------------------------------------------------------------------------------------------------------------
		int numExpetientesTotales = listaTramitesDuplicados.size();
		int numPags = calcularNumPags(numExpetientesTotales);
		bytePdf = pdf.abrirPdf(ruta);
		numPag++;
		ServicioContrato servicioContrato = ContextoSpring.getInstance().getBean(ServicioContrato.class);
		ContratoVO contrato = servicioContrato.getContrato(tramite.getIdContrato());

		procesarCabeceraListadoBastidores(tramite, contrato.getRazonSocial(), numPags, numPag, numExpetientesTotales, contrato.getCif());
		if (mismoTitular) {
			procesarTitularListadoBastidores(tramite);
		}

		for (TramiteTrafDuplicadoDto tramiteLista : listaTramitesDuplicados) {
			cambiarEstadoTramitesDelListadoMatriculas(tramiteLista, resultadoMetodo, idUsuario);
			if (numLinea > ConstantesPDF._24) {
				if (numExpedientesEscritos == ConstantesPDF._25) {
					byteFinal = bytePdf;
				} else {
					byteFinal = pdf.concatenarPdf(byteFinal, bytePdf);
				}
				numLinea = 0;
				yPos = ConstantesPDF._f612;
				pdf = new PdfMaker();
				bytePdf = pdf.abrirPdf(ruta);
				numPag++;
				procesarCabeceraListadoBastidores(tramiteLista, contrato.getRazonSocial(), numPags, numPag, numExpetientesTotales, contrato.getCif());
				if (mismoTitular) {
					procesarTitularListadoBastidores(tramiteLista);
				}
			}
			numExpedientesEscritos++;
			procesarDatosListadoBastidores(mismoTitular, tramite, numLinea, tramiteLista, numExpedientesEscritos);
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._10);
			bytePdf = pdf.dibujarLinea(bytePdf, ConstantesPDF._1, ConstantesPDF._0_5F, xPosInicial, yPos, xPosFinal, yPos);
			yPos = yPos - ConstantesPDF._18_8F;
			numLinea++;
		}

		if (numExpedientesEscritos <= ConstantesPDF._25) {
			byteFinal = bytePdf;
		} else {
			vaciarDatosListadoBastidores(numLinea);
			byteFinal = pdf.concatenarPdf(byteFinal, bytePdf);
		}
		return byteFinal;
	}

	private void vaciarDatosListadoBastidores(int numLinea) {
		for (int i = numLinea; i <= ConstantesPDF._25; i++) {
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NUM + "." + i, "");
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NUM_EXPEDIENTE + "." + i, "");
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_FECHA + "." + i, "");
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_DGT + "." + i, "");
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_BASTIDOR + "." + i, "");
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NIF + "." + i, "");
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_TITULAR + "." + i, "");
		}
	}

	private void procesarCabeceraListadoBastidores(TramiteTrafDuplicadoDto tramite, String razonSocial, int numPags, int numPag, int numExpetientesTotales, String cif) {
		pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._10);

		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_TOTAL_PAG, Integer.toString(numPags));
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_PAG, Integer.toString(numPag));
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_TOTAL_EXPEDIENTES, Integer.toString(numExpetientesTotales));
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_CODIGO_COLEGIADO, tramite.getNumColegiado());
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_GESTORIA, razonSocial);
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NIF_GESTORIA, cif);
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_TRAMITE, " DUPLICADO ");
	}

	private void procesarDatosListadoBastidores(boolean mismoTitular, TramiteTrafDuplicadoDto tramite, int numLinea, TramiteTrafDuplicadoDto tramiteLista, int numExpedientesEscritos) {
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NUM + "." + numLinea, Integer.toString(numExpedientesEscritos));
		pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, mismoTitular ? ConstantesPDF._10 : ConstantesPDF._8);
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NUM_EXPEDIENTE + "." + numLinea, tramiteLista.getNumExpediente().toString());
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_FECHA + "." + numLinea, tramiteLista.getFechaPresentacion().toString());
		if (null != tramiteLista.getRespuesta() && "" != tramiteLista.getRespuesta()) {
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._6);
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_DGT + "." + numLinea, tramiteLista.getRespuesta());
		} else {
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._6);
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_DGT + "." + numLinea, EstadoTramiteTrafico.convertirTexto(tramiteLista.getEstado()));
		}
		pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._10);
		if (tramiteLista.getVehiculoDto() != null) {
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_BASTIDOR + "." + numLinea, tramiteLista.getVehiculoDto().getMatricula());
		} else {
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_BASTIDOR + "." + numLinea, "");
		}
		
		if (!mismoTitular) {
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._8);
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NIF + "." + numLinea, tramiteLista.getTitular().getPersona().getNif());
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_TITULAR + "." + numLinea, (null != tramiteLista.getTitular().getPersona().getApellido1RazonSocial() ? tramiteLista.getTitular()
					.getPersona().getApellido1RazonSocial() : "")
					+ (null != tramiteLista.getTitular().getPersona().getApellido2() ? " " + tramiteLista.getTitular().getPersona().getApellido2() : "")
					+ (null != tramiteLista.getTitular().getPersona().getNombre() ? ", " + tramiteLista.getTitular().getPersona().getNombre() : ""));
		}
	}

	private void procesarTitularListadoBastidores(TramiteTrafDuplicadoDto tramite) {
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NIF, tramite.getTitular().getPersona().getNif());
		bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_TITULAR, (null != tramite.getTitular().getPersona().getApellido1RazonSocial() ? tramite.getTitular().getPersona().getApellido1RazonSocial()
				: "")
				+ (null != tramite.getTitular().getPersona().getApellido2() ? " " + tramite.getTitular().getPersona().getApellido2() : "")
				+ (null != tramite.getTitular().getPersona().getNombre() ? ", " + tramite.getTitular().getPersona().getNombre() : ""));
	}

	private void cambiarEstadoTramitesDelListadoMatriculas(TramiteTrafDuplicadoDto tramite, ResultBean resultadoMetodo, BigDecimal idUsuario) {
		String estado = tramite.getEstado();
		if (estado.equals(EstadoTramiteTrafico.Finalizado_Excel.getValorEnum())) {
			cambiarEstadoTramiteMostrandoResultadoParaImprimir(tramite, resultadoMetodo, EstadoTramiteTrafico.Finalizado_Excel_Impreso, idUsuario);
		}
	}

	private void cambiarEstadoTramiteMostrandoResultadoParaImprimir(TramiteTrafDuplicadoDto tramite, ResultBean resultadoMetodo, EstadoTramiteTrafico estado, BigDecimal idUsuario) {
		ServicioTramiteTrafico servicioTramiteTrafico = ContextoSpring.getInstance().getBean(ServicioTramiteTrafico.class);
		resultadoMetodo = servicioTramiteTrafico.cambiarEstadoConEvolucion(tramite.getNumExpediente(), EstadoTramiteTrafico.convertir(tramite.getEstado()), estado, true,
				TextoNotificacion.Cambio_Estado.getNombreEnum(), idUsuario);
		if (resultadoMetodo.getError()) {
			resultadoMetodo.setMensaje(" realizando el cambio del estado del trámite impreso " + tramite.getNumExpediente());
			resultadoMetodo.setError(true);
		}
	}

	public ResultBean impresionMandatosDuplicado(String numExpediente, String tipoImpreso) throws OegamExcepcion, Throwable {
		ResultBean resultadoMetodo = new ResultBean();

		camposFormateados = new ArrayList<CampoPdfBean>();
		pdf = new PdfMaker();

		mensaje = "";

		boolean error = false;
		// --------------------------------------------------------------------------------

		obtenerDetalleTramiteDuplicado(numExpediente, tipoImpreso);

		if (detalleDuplicado != null) {
			calcularRuta(tipoImpreso, false);
			bytePdf = pdf.abrirPdf(ruta);
			camposPlantilla = pdf.getAllFields(bytePdf);
			camposFormateados.addAll(obtenerValoresMandatoMatwDuplicadoYSol(ConstantesPDF._11, detalleDuplicado.getTitular(), detalleDuplicado.getRepresentanteTitular(), camposPlantilla,
					detalleDuplicado.getIdContrato(), detalleDuplicado.getNumColegiado()));
			procesarAsuntos();
			bytePdf = pdf.setCampos(bytePdf, camposFormateados);
		} else {
			error = true;
			mensaje = "Los datos del trámite están vacíos";
		}

		if (error == true || bytePdf == null) {
			error = true;
			mensaje += ". Puede que no esté completo el trámite impreso.";
		}

		resultadoMetodo.setError(error);
		resultadoMetodo.setMensaje(mensaje);
		resultadoMetodo.addAttachment(ResultBean.TIPO_PDF, bytePdf);
		return resultadoMetodo;
	}

	private void procesarAsuntos() {
		if (camposPlantilla.contains(ConstantesPDF.ID_ASUNTO1_MOTIVO)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ASUNTO1_MOTIVO, "DUPLICADO: ", false, false, ConstantesPDF._11));
		}
		if (camposPlantilla.contains(ConstantesPDF.ID_ASUNTO1_EXPLICACION)) {
			if (detalleDuplicado.getVehiculoDto() != null) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ASUNTO1_EXPLICACION, detalleDuplicado.getVehiculoDto().getBastidor(), false, false, ConstantesPDF._11));
			}
		}
	}

	public TramiteTrafDuplicadoDto getDetalleDuplicado() {
		return detalleDuplicado;
	}

	public void setDetalleDuplicado(TramiteTrafDuplicadoDto detalleDuplicado) {
		this.detalleDuplicado = detalleDuplicado;
	}

	public ArrayList<TramiteTrafDuplicadoDto> getListaTramitesDuplicados() {
		return listaTramitesDuplicados;
	}

	public void setListaTramitesDuplicados(ArrayList<TramiteTrafDuplicadoDto> listaTramitesDuplicados) {
		this.listaTramitesDuplicados = listaTramitesDuplicados;
	}

	public ArrayList<CampoPdfBean> getCamposFormateados() {
		return camposFormateados;
	}

	public void setCamposFormateados(ArrayList<CampoPdfBean> camposFormateados) {
		this.camposFormateados = camposFormateados;
	}

	public Set<String> getCamposPlantilla() {
		return camposPlantilla;
	}

	public void setCamposPlantilla(Set<String> camposPlantilla) {
		this.camposPlantilla = camposPlantilla;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public byte[] getBytePdf() {
		return bytePdf;
	}

	public void setBytePdf(byte[] bytePdf) {
		this.bytePdf = bytePdf;
	}

	public PdfMaker getPdf() {
		return pdf;
	}

	public void setPdf(PdfMaker pdf) {
		this.pdf = pdf;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}