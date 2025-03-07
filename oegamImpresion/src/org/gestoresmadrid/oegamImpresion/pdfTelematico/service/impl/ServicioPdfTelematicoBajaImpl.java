package org.gestoresmadrid.oegamImpresion.pdfTelematico.service.impl;

import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.persona.service.ServicioComunPersona;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.vehiculo.service.ServicioComunVehiculo;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.pdfTelematico.service.ServicioPdfTelematicoBaja;
import org.gestoresmadrid.oegamImpresion.utiles.CampoPdfBean;
import org.gestoresmadrid.oegamImpresion.utiles.ConstantesPDF;
import org.gestoresmadrid.oegamImpresion.utiles.PdfMaker;
import org.gestoresmadrid.oegamImpresion.utilidades.UtilesNube;
import org.gestoresmadrid.oegamImpresion.utilidades.UtilidadesConversiones;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.AcroFields.FieldPosition;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPdfTelematicoBajaImpl implements ServicioPdfTelematicoBaja {

	private static final long serialVersionUID = -2055993089757622666L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPdfTelematicoBajaImpl.class);

	@Autowired
	ServicioComunTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioComunPersona servicioPersona;

	@Autowired
	ServicioComunVehiculo servicioVehiculo;

	@Autowired
	ServicioComunDireccion servicioDireccion;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilidadesConversiones utilidadesConversiones;

	@Autowired
	UtilesNube utilesNube;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	public ResultadoImpresionBean imprimirPdfTelematico(List<BigDecimal> listaExpediente) {
		ResultadoImpresionBean resultado = new ResultadoImpresionBean(Boolean.FALSE);
		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		try {
			for (BigDecimal numExpediente : listaExpediente) {
				TramiteTrafBajaVO tramite = servicioTramiteTrafico.getTramiteBajaVO(numExpediente, true);
				if (tramite != null) {
					try {
						byte[] bytePdf = imprimir(tramite);
						listaByte.add(bytePdf);
					} catch (Exception e) {
						log.error("Error al imprimir el tramite de baja con numExpediente: " + numExpediente, e);
						// resultado.setMensaje("Error a la hora de imprimir el tramite: " + numExpediente);
						resultado.addListaTramitesError(numExpediente);
					}
				} else {
					resultado.addListaTramitesError(numExpediente);
					// resultado.setMensaje("No existe el tramite con numExpediente: " + numExpediente);
				}
			}
			if (listaByte != null && !listaByte.isEmpty()) {
				byte[] pdf = PdfMaker.concatenarPdf(listaByte);
				pdf = PdfMaker.encriptarPdf(pdf, "", "", true, false, false, false, false);
				resultado.setPdf(pdf);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha generado nada para imprimir.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir el Pdf Telematico de Baja, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el Pdf Telematico de Baja.");
		}
		return resultado;
	}

	private byte[] imprimir(TramiteTrafBajaVO tramite) {
		PdfMaker pdf = new PdfMaker();
		byte[] bytePdf = pdf.abrirPdf(getRuta());
		Set<String> camposPlantilla = pdf.getAllFields(bytePdf);

		PersonaVO datosColegiado = servicioPersona.obtenerColegiadoCompleto(tramite.getContrato().getColegiado().getNumColegiado(), tramite.getContrato().getColegiado().getUsuario().getNif());

		ArrayList<CampoPdfBean> camposFormateados = rellenarPdf(tramite, camposPlantilla, tramite.getContrato(), datosColegiado, pdf);

		bytePdf = etiquetas(tramite, bytePdf, pdf, datosColegiado, tramite.getContrato());

		bytePdf = pdf.setCampos(bytePdf, camposFormateados);

		return bytePdf;
	}

	private ArrayList<CampoPdfBean> rellenarPdf(TramiteTrafBajaVO tramite, Set<String> camposPlantilla, ContratoVO contrato, PersonaVO datosColegiado, PdfMaker pdf) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();

		rellenarDatosPresentacion(tramite, camposPlantilla, camposFormateados);

		rellenarDatosGestor(camposPlantilla, camposFormateados, contrato, datosColegiado);

		rellenarDatosTitular(tramite, camposPlantilla, camposFormateados, TipoInterviniente.Titular.getValorEnum());

		rellenarDatosRepreTitular(tramite, camposPlantilla, camposFormateados, TipoInterviniente.RepresentanteTitular.getValorEnum());

		rellenarDatosAdquiriente(tramite, camposPlantilla, camposFormateados, TipoInterviniente.Adquiriente.getValorEnum());

		rellenarDatosRepreAdquiriente(tramite, camposPlantilla, camposFormateados, TipoInterviniente.RepresentanteAdquiriente.getValorEnum());

		rellenarResultadoTramitabilidad(tramite, camposPlantilla, camposFormateados);

		rellenarResultadoBtv(tramite, camposPlantilla, camposFormateados);

		rellenarDatosFinal(camposPlantilla, camposFormateados, datosColegiado);

		return camposFormateados;
	}

	private byte[] etiquetas(TramiteTrafBajaVO tramite, byte[] bytePdf, PdfMaker pdf, PersonaVO datosColegiado, ContratoVO contrato) {
		int[] vectPags = new int[ConstantesPDF._1];
		vectPags[0] = ConstantesPDF._1;

		bytePdf = nube(tramite, bytePdf, pdf, vectPags);

		bytePdf = insertarFirmas(tramite, bytePdf, pdf, vectPags, datosColegiado, contrato);

		return bytePdf;
	}

	private byte[] nube(TramiteTrafBajaVO tramite, byte[] bytePdf, PdfMaker pdf, int[] vectPags) {
		String nube = utilesNube.obtenerNubeBaja(tramite);
		nube = utilesNube.sustituyeCaracteres_Plus(nube);
		bytePdf = insertarNubePuntos(nube, ConstantesPDF.ID_NUBE_PUNTOS, bytePdf, pdf, vectPags);
		return bytePdf;
	}

	private byte[] insertarNubePuntos(String nube, String tipoNube, byte[] bytePdf, PdfMaker pdf, int[] vectPags) {
		List<FieldPosition> posiciones = pdf.getFieldPosition(bytePdf, tipoNube);
		FieldPosition posicion = posiciones.get(0);
		Image img = pdf.crearNubePuntos(nube);
		img = pdf.escalarImagen(img, posicion.position.getWidth(), posicion.position.getHeight());
		return pdf.insertarImagen(bytePdf, img, vectPags, posicion.position.getLeft(), posicion.position.getBottom());
	}

	private void rellenarDatosPresentacion(TramiteTrafBajaVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (tramite.getVehiculo() != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_MATRI)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MATRI, tramite.getVehiculo().getMatricula(), true, false, ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_BASTIDOR)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_BASTIDOR, tramite.getVehiculo().getBastidor(), false, false, ConstantesPDF._9));
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_PAIS_DESTINO)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PAIS_DESTINO, tramite.getPais() != null ? tramite.getPais().getNombre() : "", false, false, ConstantesPDF._9));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_PRESENTACION)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_PRESENTACION, utilesFecha.formatoFecha(tramite.getFechaPresentacion()), false, false, ConstantesPDF._9));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_MOTIVO_BAJA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MOTIVO_BAJA, tramite.getMotivoBaja() != null ? MotivoBaja.convertirTexto(tramite.getMotivoBaja()) : "", false, false,
					ConstantesPDF._9));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_TASA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TASA, tramite.getTasa() != null && tramite.getTasa().getCodigoTasa() != null ? tramite.getTasa().getCodigoTasa() : "", true, false,
					ConstantesPDF._9));
		}

		if (tramite.getJefaturaTrafico() != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_SUCURSAL_JEFATURA)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_SUCURSAL_JEFATURA, tramite.getJefaturaTrafico().getDescripcion(), false, false, ConstantesPDF._9));
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_NUM_EXPEDIENTE)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUM_EXPEDIENTE, tramite.getNumExpediente() != null ? tramite.getNumExpediente().toString() : "", false, false, ConstantesPDF._9));
		}
	}

	private void rellenarDatosGestor(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, ContratoVO contrato, PersonaVO datosColegiado) {
		if (datosColegiado != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_GESTOR)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_GESTOR, datosColegiado.getId().getNumColegiado() + " - " + datosColegiado.getNombre() + " " + datosColegiado
						.getApellido1RazonSocial() + " " + datosColegiado.getApellido2(), false, false, ConstantesPDF._9));
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_GESTORIA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_GESTORIA, contrato.getRazonSocial() + " (" + contrato.getColegiado().getUsuario().getNif() + ")", false, false, ConstantesPDF._8));
		}
	}

	private void rellenarDatosTitular(TramiteTrafBajaVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR)) {
				String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
						.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
								.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR, nombreApellidos, false, false, ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR, interviniente.getId().getNif(), false, false, ConstantesPDF._9));
			}
		}
	}

	private void rellenarDatosRepreTitular(TramiteTrafBajaVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_REPR_TITULAR)) {
				String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
						.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
								.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_REPR_TITULAR, nombreApellidos, false, false, ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_REPR_TITULAR)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_REPR_TITULAR, interviniente.getId().getNif(), false, false, ConstantesPDF._9));
			}
		}
	}

	private void rellenarDatosAdquiriente(TramiteTrafBajaVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_CV)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CV, "SI", false, false, ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_ADQUIRIENTE)) {
				String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
						.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
								.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_ADQUIRIENTE, nombreApellidos, false, false, ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_ADQUIRIENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_ADQUIRIENTE, interviniente.getId().getNif(), false, false, ConstantesPDF._9));
			}
		}
	}

	private void rellenarDatosRepreAdquiriente(TramiteTrafBajaVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_REPR_ADQUIRIENTE)) {
				String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
						.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
								.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_REPR_TITULAR, nombreApellidos, false, false, ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_REPR_ADQUIRIENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_REPR_ADQUIRIENTE, interviniente.getId().getNif(), false, false, ConstantesPDF._9));
			}
		}
	}

	private void rellenarResultadoTramitabilidad(TramiteTrafBajaVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (camposPlantilla.contains(ConstantesPDF.ID_RESULTADO_TRAMITABILIDAD)) {
			if (tramite.getResCheckBtv() != null) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_RESULTADO_TRAMITABILIDAD, tramite.getResCheckBtv(), true, false, ConstantesPDF._12));
			}
		}
	}

	private void rellenarResultadoBtv(TramiteTrafBajaVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (camposPlantilla.contains(ConstantesPDF.ID_RESULT_BTV)) {
			if (tramite.getRespuesta() != null) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_RESULT_BTV, tramite.getRespuesta(), true, false, ConstantesPDF._9));
			}
		}
	}

	private void rellenarDatosFinal(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, PersonaVO datosColegiado) {
		if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_ACTUAL)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_ACTUAL, utilesFecha.getFechaHoy(), false, false, ConstantesPDF._12));
		}

		if (datosColegiado != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_PROFESIONAL)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_PROFESIONAL, datosColegiado.getNombre() + " " + datosColegiado.getApellido1RazonSocial() + " " + datosColegiado
						.getApellido2(), true, false, ConstantesPDF._9));
			}
		}
	}

	private byte[] insertarFirmas(TramiteTrafBajaVO tramite, byte[] bytePdf, PdfMaker pdf, int[] vectPags, PersonaVO datosColegiado, ContratoVO contrato) {
		String fechaDeHoy = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		bytePdf = insertarFirmaGestor(tramite, bytePdf, pdf, vectPags, datosColegiado, contrato, fechaDeHoy);
		bytePdf = insertarFirmaColegio(tramite, bytePdf, pdf, vectPags, datosColegiado, contrato, fechaDeHoy);
		return bytePdf;
	}

	private byte[] insertarFirmaGestor(TramiteTrafBajaVO tramite, byte[] bytePdf, PdfMaker pdf, int[] vectPags, PersonaVO datosColegiado, ContratoVO contrato, String fechaHoy) {
		String firmaGestor = (tramite.getVehiculo() != null && StringUtils.isNotBlank(tramite.getVehiculo().getBastidor()) ? tramite.getVehiculo().getBastidor() : "") + "#" + datosColegiado.getId()
				.getNif() + "#" + fechaHoy;

		firmaGestor = utilidadesConversiones.cifrarHMACSHA1(firmaGestor, "ClaveCifrado");
		firmaGestor = utilidadesConversiones.stringToHex(firmaGestor);
		firmaGestor = firmaGestor.substring(0, ConstantesPDF._16);

		bytePdf = insertarBarcodeFirma(ConstantesPDF.ID_FIRMA_GESTOR, bytePdf, vectPags, firmaGestor, pdf);

		return bytePdf;
	}

	private byte[] insertarFirmaColegio(TramiteTrafBajaVO tramite, byte[] bytePdf, PdfMaker pdf, int[] vectPags, PersonaVO datosColegiado, ContratoVO contrato, String fechaHoy) {
		IntervinienteTraficoVO titular = buscarInterviniente(tramite, TipoInterviniente.Titular.getValorEnum());

		String firmaColegio = contrato.getCif() + "#" + (tramite.getVehiculo() != null && StringUtils.isNotBlank(tramite.getVehiculo().getBastidor()) ? tramite.getVehiculo().getBastidor() : "") + "#"
				+ (titular != null ? titular.getId().getNif() : "") + "#" + datosColegiado.getId().getNif() + "#" + contrato.getColegio().getCif() + "#" + fechaHoy;

		firmaColegio = utilidadesConversiones.cifrarHMACSHA1(firmaColegio, "ClaveCifrado");
		firmaColegio = utilidadesConversiones.stringToHex(firmaColegio);
		firmaColegio = firmaColegio.substring(0, ConstantesPDF._16);

		bytePdf = insertarBarcodeFirma(ConstantesPDF.ID_FIRMA_COLEGIO, bytePdf, vectPags, firmaColegio, pdf);

		return bytePdf;
	}

	private byte[] insertarBarcodeFirma(String nombreCampo, byte[] byte1, int[] vectPags, String barcode, PdfMaker pdf) {
		List<FieldPosition> posiciones = pdf.getFieldPosition(byte1, nombreCampo);
		FieldPosition posicion = posiciones.get(0);
		byte1 = pdf.crearCodigoBarras128ConTexto(barcode, byte1, -ConstantesPDF._1, ConstantesPDF._12, -ConstantesPDF._1, true, posicion.position.getLeft(), posicion.position.getBottom());
		return byte1;
	}

	private IntervinienteTraficoVO buscarInterviniente(TramiteTrafBajaVO tramite, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = null;
		if (tramite.getIntervinienteTraficosAsList() != null && !tramite.getIntervinienteTraficosAsList().isEmpty()) {
			for (IntervinienteTraficoVO inter : tramite.getIntervinienteTraficosAsList()) {
				if (tipoInterviniente.equals(inter.getId().getTipoInterviniente())) {
					interviniente = inter;
					break;
				}
			}
		}
		return interviniente;
	}

	private String getRuta() {
		URL ruta = this.getClass().getResource(gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS) + TipoImpreso.BajasTelematicas.getNombreEnum());
		return ruta.getFile();
	}
}
