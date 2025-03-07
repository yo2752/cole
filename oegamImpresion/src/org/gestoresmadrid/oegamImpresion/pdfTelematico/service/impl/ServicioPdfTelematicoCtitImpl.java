package org.gestoresmadrid.oegamImpresion.pdfTelematico.service.impl;

import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.persona.service.ServicioComunPersona;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.vehiculo.service.ServicioComunVehiculo;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.pdfTelematico.service.ServicioPdfTelematicoCtit;
import org.gestoresmadrid.oegamImpresion.utiles.CampoPdfBean;
import org.gestoresmadrid.oegamImpresion.utiles.ConstantesPDF;
import org.gestoresmadrid.oegamImpresion.utiles.PdfMaker;
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
public class ServicioPdfTelematicoCtitImpl implements ServicioPdfTelematicoCtit {

	private static final long serialVersionUID = -7232629774341553902L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPdfTelematicoCtitImpl.class);

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
	private GestorPropiedades gestorPropiedades;

	@Override
	public ResultadoImpresionBean imprimirPdfTelematico(List<BigDecimal> listaExpediente) {
		ResultadoImpresionBean resultado = new ResultadoImpresionBean(Boolean.FALSE);
		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		try {
			for (BigDecimal numExpediente : listaExpediente) {
				TramiteTrafTranVO tramite = servicioTramiteTrafico.getTramiteTransmisionVO(numExpediente, true);
				if (tramite != null) {
					try {
						byte[] bytePdf = imprimir(tramite);
						listaByte.add(bytePdf);
					} catch (Exception e) {
						log.error("Error al imprimir el tramite de ctit con numExpediente: " + numExpediente, e);
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
			log.error("Ha sucedido un error a la hora de imprimir el Pdf de Ctit, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el Pdf de Ctit.");
		}
		return resultado;
	}

	private byte[] imprimir(TramiteTrafTranVO tramite) {
		PdfMaker pdf = new PdfMaker();
		byte[] bytePdf = pdf.abrirPdf(getRuta(tramite));
		Set<String> camposPlantilla = pdf.getAllFields(bytePdf);

		PersonaVO datosColegiado = servicioPersona.obtenerColegiadoCompleto(tramite.getContrato().getColegiado().getNumColegiado(), tramite.getContrato().getColegiado().getUsuario().getNif());

		ArrayList<CampoPdfBean> camposFormateados = rellenarPdf(tramite, camposPlantilla, tramite.getContrato(), datosColegiado, pdf);

		bytePdf = etiquetas(tramite, bytePdf, pdf, datosColegiado, tramite.getContrato());

		bytePdf = pdf.setCampos(bytePdf, camposFormateados);

		return bytePdf;
	}

	private ArrayList<CampoPdfBean> rellenarPdf(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ContratoVO contrato, PersonaVO datosColegiado, PdfMaker pdf) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();

		rellenarTipoTransmision(tramite, camposPlantilla, camposFormateados);

		rellenarDatosPresentacion(tramite, camposPlantilla, camposFormateados);

		rellenarDatosGestor(camposPlantilla, camposFormateados, contrato, datosColegiado);

		rellenarDatosAdquiriente(tramite, camposPlantilla, camposFormateados);

		// rellenarDatosRepreAdquiriente(tramite, camposPlantilla, camposFormateados, TipoInterviniente.RepresentanteAdquiriente.getValorEnum());

		rellenarDatosTransmitente(tramite, camposPlantilla, camposFormateados, TipoInterviniente.TransmitenteTrafico.getValorEnum());

		rellenarDatosRepreTransmitente(tramite, camposPlantilla, camposFormateados, TipoInterviniente.RepresentanteTransmitente.getValorEnum());

		rellenarDatosCotitulares(tramite, camposPlantilla, camposFormateados);

		rellenarDatosArrendatario(tramite, camposPlantilla, camposFormateados, TipoInterviniente.Arrendatario.getValorEnum());

		rellenarDatosConductorHabitual(tramite, camposPlantilla, camposFormateados, TipoInterviniente.ConductorHabitual.getValorEnum());

		rellenarCompraVenta(tramite, camposPlantilla, camposFormateados);

		rellenarServicio(tramite, camposPlantilla, camposFormateados);

		rellenarDireccionVehiculo(tramite, camposPlantilla, camposFormateados);

		rellenarResultadoTramitabilidad(tramite, camposPlantilla, camposFormateados);

		rellenarResultadoCtit(tramite, camposPlantilla, camposFormateados);

		rellenarDatosFinal(camposPlantilla, camposFormateados, datosColegiado);

		return camposFormateados;
	}

	private byte[] etiquetas(TramiteTrafTranVO tramite, byte[] bytePdf, PdfMaker pdf, PersonaVO datosColegiado, ContratoVO contrato) {
		int[] vectPags = new int[ConstantesPDF._1];
		vectPags[0] = ConstantesPDF._1;

		bytePdf = marcaAgua(bytePdf, pdf, vectPags);

		bytePdf = insertarFirmas(tramite, bytePdf, pdf, vectPags, datosColegiado, contrato);

		return bytePdf;
	}

	private void rellenarTipoTransmision(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_TRANSMISION)) {
			if ("1".equals(tramite.getTipoTransferencia())) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRANSMISION, ConstantesPDF.ID_TIPO_TRANSMISION_1, true, false, ConstantesPDF._10));
			} else if ("2".equals(tramite.getTipoTransferencia())) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRANSMISION, ConstantesPDF.ID_TIPO_TRANSMISION_2, true, false, ConstantesPDF._10));
			} else if ("3".equals(tramite.getTipoTransferencia())) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRANSMISION, ConstantesPDF.ID_TIPO_TRANSMISION_3, true, false, ConstantesPDF._10));
			} else if ("4".equals(tramite.getTipoTransferencia())) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRANSMISION, ConstantesPDF.ID_TIPO_TRANSMISION_4, true, false, ConstantesPDF._10));
			} else if ("5".equals(tramite.getTipoTransferencia())) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRANSMISION, ConstantesPDF.ID_TIPO_TRANSMISION_5, true, false, ConstantesPDF._10));
			}
		}
	}

	private void rellenarDatosPresentacion(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (tramite.getVehiculo() != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_MATRI)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MATRI, tramite.getVehiculo().getMatricula(), true, false, ConstantesPDF._12));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_MATRI)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_MATRI, utilesFecha.formatoFecha(tramite.getVehiculo().getFechaMatriculacion()), false, false, ConstantesPDF._12));
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_NUM_EXPEDIENTE)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUM_EXPEDIENTE, tramite.getNumExpediente() != null ? tramite.getNumExpediente().toString() : "", false, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_PRESENTACION)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_PRESENTACION, utilesFecha.formatoFecha(tramite.getFechaPresentacion()), false, false, ConstantesPDF._12));
		}

		if (tramite.getJefaturaTrafico() != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_JEFATURA_TRAFICO)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_JEFATURA_TRAFICO, tramite.getJefaturaTrafico().getProvincia().getNombre(), false, false, ConstantesPDF._12));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_SUCURSAL_JEFATURA)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_SUCURSAL_JEFATURA, tramite.getJefaturaTrafico().getDescripcion(), false, false, ConstantesPDF._12));
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_TASA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TASA, tramite.getTasa() != null && tramite.getTasa().getCodigoTasa() != null ? tramite.getTasa().getCodigoTasa() : "", true, false,
					ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CEMA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CEMA, tramite.getTipoTransferencia() != null ? tramite.getTipoTransferencia() : "", false, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_TASA_INFORME)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TASA_INFORME, tramite.getTasa1() != null && tramite.getTasa1().getCodigoTasa() != null ? tramite.getTasa1().getCodigoTasa() : "",
					true, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_TASA_CAMBIO)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TASA_CAMBIO, tramite.getTasa2() != null && tramite.getTasa2().getCodigoTasa() != null ? tramite.getTasa2().getCodigoTasa() : "",
					true, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CET)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CET, tramite.getCetItp() != null ? tramite.getCetItp() : "", false, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_FACTURA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FACTURA, tramite.getFactura() != null ? tramite.getFactura() : "", false, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CEM)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CEM, tramite.getCem() != null ? tramite.getCem() : "", true, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CEMA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CEMA, tramite.getCema() != null ? tramite.getCema() : "", false, false, ConstantesPDF._12));
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

	private void rellenarCompraVenta(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {

		if (camposPlantilla.contains(ConstantesPDF.ID_COMPRAVENTA_ADQUIRIENTE)) {
			IntervinienteTraficoVO adquiriente = buscarInterviniente(tramite, TipoInterviniente.Adquiriente.getValorEnum());
			IntervinienteTraficoVO transmitente = buscarInterviniente(tramite, TipoInterviniente.TransmitenteTrafico.getValorEnum());
			IntervinienteTraficoVO compraventa = buscarInterviniente(tramite, TipoInterviniente.Compraventa.getValorEnum());
			if (adquiriente != null && compraventa != null && adquiriente.getId().getNif().equals(compraventa.getId().getNif())) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_COMPRAVENTA_ADQUIRIENTE, ConstantesPDF.VALOR_SI, true, false, ConstantesPDF._6));
			}
			if (transmitente != null && compraventa != null && transmitente.getId().getNif().equals(compraventa.getId().getNif())) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_COMPRAVENTA_TRANSMITENTE, ConstantesPDF.VALOR_SI, true, false, ConstantesPDF._6));
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_DNI_REPRESENTANTE_ADQUIRIENTE)) {
			IntervinienteTraficoVO repreCompraventa = buscarInterviniente(tramite, TipoInterviniente.RepresentanteCompraventa.getValorEnum());
			if (repreCompraventa != null) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_REPRESENTANTE_ADQUIRIENTE, repreCompraventa.getId().getNif(), false, false, ConstantesPDF._12));
			}
		}
	}

	private void rellenarDatosAdquiriente(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		IntervinienteTraficoVO interviniente = null;
		if (TipoTransferencia.tipo5.getValorEnum().equals(tramite.getTipoTransferencia())) {
			interviniente = buscarInterviniente(tramite, TipoInterviniente.Compraventa.getValorEnum());
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_COMPRAVENTA_ADQUIRIENTE, ConstantesPDF.VALOR_SI, true, false, ConstantesPDF._6));
		} else {
			interviniente = buscarInterviniente(tramite, TipoInterviniente.Adquiriente.getValorEnum());
		}
		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_ADQUIRIENTE)) {
				String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
						.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
								.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_ADQUIRIENTE, nombreApellidos, false, false, ConstantesPDF._9));
			}
			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_ADQUIRIENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_ADQUIRIENTE, interviniente.getId().getNif(), false, false, ConstantesPDF._9));
			}
			if (camposPlantilla.contains(ConstantesPDF.ID_CAMBIO_DOMICILIO_ADQUIRIENTE)) {
				if (interviniente.getCambioDomicilio() != null && ("SI".equals(interviniente.getCambioDomicilio()) || "S".equals(interviniente.getCambioDomicilio()))) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CAMBIO_DOMICILIO_ADQUIRIENTE, ConstantesPDF.VALOR_SI, true, false, ConstantesPDF._6));
				} else {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CAMBIO_DOMICILIO_ADQUIRIENTE, ConstantesPDF.VALOR_NO, true, false, ConstantesPDF._6));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_AUTONOMO_ADQUIRIENTE)) {
				if (interviniente.getAutonomo() != null && ("SI".equals(interviniente.getAutonomo()) || "S".equals(interviniente.getAutonomo()) || "true".equals(interviniente.getAutonomo()))) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_AUTONOMO_ADQUIRIENTE, ConstantesPDF.VALOR_SI, false, false, ConstantesPDF._6));
				} else {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_AUTONOMO_ADQUIRIENTE, ConstantesPDF.VALOR_NO, false, false, ConstantesPDF._6));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_CODIGO_IAE_ADQUIRIENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CODIGO_IAE_ADQUIRIENTE, (interviniente.getCodigoIae() != null ? interviniente.getCodigoIae() : ""), false, false,
						ConstantesPDF._12));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_NACIMIENTO_ADQUIRIENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_NACIMIENTO_ADQUIRIENTE, utilesFecha.formatoFecha(interviniente.getPersona().getFechaNacimiento()), false, false,
						ConstantesPDF._12));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_SEXO_ADQUIRIENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_SEXO_ADQUIRIENTE, interviniente.getPersona().getSexo(), false, false, ConstantesPDF._12));
			}

			if (interviniente.getDireccion() != null) {
				if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_VIA_ADQUIRIENTE)) {
					String tipoViaNombre = servicioDireccion.obtenerNombreTipoVia(interviniente.getDireccion().getIdTipoVia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_VIA_ADQUIRIENTE, tipoViaNombre, false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_CALLE_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CALLE_ADQUIRIENTE, utilidadesConversiones.ajustarCamposIne(interviniente.getDireccion().getNombreVia()), false, false,
							ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_NUM_DOMICILIO_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUM_DOMICILIO_ADQUIRIENTE, interviniente.getDireccion().getNumero(), false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_KM_DOMICILIO_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_KM_DOMICILIO_ADQUIRIENTE, interviniente.getDireccion().getKm() != null ? interviniente.getDireccion().getKm().toString()
							: "", false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_HM_DOMICILIO_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_HM_DOMICILIO_ADQUIRIENTE, interviniente.getDireccion().getHm() != null ? interviniente.getDireccion().getHm().toString()
							: "", false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_BLOQUE_DOMICILIO_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_BLOQUE_DOMICILIO_ADQUIRIENTE, interviniente.getDireccion().getBloque(), false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_LETRA_DOMICILIO_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_LETRA_DOMICILIO_ADQUIRIENTE, interviniente.getDireccion().getLetra(), false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_ESCALERA_DOMICILIO_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ESCALERA_DOMICILIO_ADQUIRIENTE, interviniente.getDireccion().getEscalera(), false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PISO_DOMICILIO_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PISO_DOMICILIO_ADQUIRIENTE, interviniente.getDireccion().getPlanta(), false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PUERTA_DOMICILIO_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PUERTA_DOMICILIO_ADQUIRIENTE, interviniente.getDireccion().getPuerta(), false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_ADQUIRIENTE)) {
					String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(interviniente.getDireccion().getIdMunicipio(), interviniente.getDireccion().getIdProvincia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_ADQUIRIENTE, utilidadesConversiones.ajustarCamposIne(nombreMunicipio), false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PUEBLO_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PUEBLO_ADQUIRIENTE, utilidadesConversiones.ajustarCamposIne(interviniente.getDireccion().getPueblo()), false, false,
							ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PROVINCIA_ADQUIRIENTE)) {
					String nombreProvincia = servicioDireccion.obtenerNombreProvincia(interviniente.getDireccion().getIdProvincia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PROVINCIA_ADQUIRIENTE, nombreProvincia, false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_CP_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CP_ADQUIRIENTE, interviniente.getDireccion().getCodPostal(), false, false, ConstantesPDF._12));
				}
			}
		}
	}

	private void rellenarDatosRepreAdquiriente(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			if ("FISICA".equals(interviniente.getPersona().getTipoPersona())) {
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
	}

	private void rellenarDatosTransmitente(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_COMPRAVENTA_TRANSMITENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_COMPRAVENTA_TRANSMITENTE, ConstantesPDF.VALOR_SI, true, false, ConstantesPDF._6));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_CODIGO_IAE_TRANSMITENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CODIGO_IAE_TRANSMITENTE, (interviniente.getCodigoIae() != null ? interviniente.getCodigoIae() : ""), false, false,
						ConstantesPDF._12));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TRANSMITENTE)) {
				String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
						.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
								.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TRANSMITENTE, nombreApellidos, false, false, ConstantesPDF._12));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TRANSMITENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_TRANSMITENTE, interviniente.getId().getNif(), false, false, ConstantesPDF._12));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_NACIMIENTO_TRANSMITENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_NACIMIENTO_TRANSMITENTE, utilesFecha.formatoFecha(interviniente.getPersona().getFechaNacimiento()), false, false,
						ConstantesPDF._12));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_SEXO_TRANSMITENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_SEXO_TRANSMITENTE, interviniente.getPersona().getSexo(), false, false, ConstantesPDF._12));
			}
		}
	}

	private void rellenarDatosRepreTransmitente(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_REPRESENTANTE_TRANSMITENTE, ConstantesPDF.VALOR_SI, true, false, ConstantesPDF._6));
		}
	}

	private void rellenarDatosCotitulares(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		List<IntervinienteTraficoVO> cotitulares = buscarCotitulares(tramite);
		if (cotitulares != null && !cotitulares.isEmpty()) {
			if (cotitulares.size() > 1) {
				rellenarDatosCotitular1(tramite, camposPlantilla, camposFormateados, cotitulares.get(0));
			}
			if (cotitulares.size() > 1) {
				rellenarDatosCotitular2(tramite, camposPlantilla, camposFormateados, cotitulares.get(0));
			}
		}

	}

	private void rellenarDatosCotitular1(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, IntervinienteTraficoVO interviniente) {
		if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_PRIMER_COTITULAR)) {
			String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
					.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
							.getPersona().getApellido2() : "");
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_PRIMER_COTITULAR, nombreApellidos, false, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_DNI_PRIMER_COTITULAR)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_PRIMER_COTITULAR, interviniente.getId().getNif(), false, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_NACIMIENTO_PRIMER_COTITULAR)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_NACIMIENTO_PRIMER_COTITULAR, utilesFecha.formatoFecha(interviniente.getPersona().getFechaNacimiento()), false, false,
					ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_SEXO_PRIMER_COTITULAR)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_SEXO_PRIMER_COTITULAR, interviniente.getPersona().getSexo(), false, false, ConstantesPDF._12));
		}
	}

	private void rellenarDatosCotitular2(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, IntervinienteTraficoVO interviniente) {
		if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_SEGUNDO_COTITULAR)) {
			String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
					.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
							.getPersona().getApellido2() : "");
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_SEGUNDO_COTITULAR, nombreApellidos, false, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_DNI_SEGUNDO_COTITULAR)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_SEGUNDO_COTITULAR, interviniente.getId().getNif(), false, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_NACIMIENTO_SEGUNDO_COTITULAR)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_NACIMIENTO_SEGUNDO_COTITULAR, utilesFecha.formatoFecha(interviniente.getPersona().getFechaNacimiento()), false, false,
					ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_SEXO_SEGUNDO_COTITULAR)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_SEXO_SEGUNDO_COTITULAR, interviniente.getPersona().getSexo(), false, false, ConstantesPDF._12));
		}
	}

	private void rellenarDatosArrendatario(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_CAMBIO_DOMICILIO_ARRENDATARIO)) {
				if (interviniente.getCambioDomicilio() != null && ("SI".equals(interviniente.getCambioDomicilio()) || "S".equals(interviniente.getCambioDomicilio()))) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CAMBIO_DOMICILIO_ARRENDATARIO, ConstantesPDF.VALOR_S, true, false, ConstantesPDF._6));
				} else {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CAMBIO_DOMICILIO_ARRENDATARIO, ConstantesPDF.VALOR_N, true, false, ConstantesPDF._6));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_ARRENDATARIO)) {
				String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
						.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
								.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_ARRENDATARIO, nombreApellidos, false, false, ConstantesPDF._12));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_ARRENDATARIO)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_ARRENDATARIO, interviniente.getId().getNif(), false, false, ConstantesPDF._12));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_NACIMIENTO_ARRENDATARIO)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_NACIMIENTO_ARRENDATARIO, utilesFecha.formatoFecha(interviniente.getPersona().getFechaNacimiento()), false, false,
						ConstantesPDF._12));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_SEXO_ARRENDATARIO)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_SEXO_ARRENDATARIO, interviniente.getPersona().getSexo(), false, false, ConstantesPDF._12));
			}

			if (interviniente.getDireccion() != null) {
				if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_VIA_ARRENDATARIO)) {
					String tipoViaNombre = servicioDireccion.obtenerNombreTipoVia(interviniente.getDireccion().getIdTipoVia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_VIA_ARRENDATARIO, tipoViaNombre, false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_CALLE_ARRENDATARIO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CALLE_ARRENDATARIO, utilidadesConversiones.ajustarCamposIne(interviniente.getDireccion().getNombreVia()), false, false,
							ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_NUM_DOMICILIO_ARRENDATARIO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUM_DOMICILIO_ARRENDATARIO, interviniente.getDireccion().getNumero(), false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_KM_DOMICILIO_ARRENDATARIO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_KM_DOMICILIO_ARRENDATARIO, interviniente.getDireccion().getKm() != null ? interviniente.getDireccion().getKm().toString()
							: "", false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_HM_DOMICILIO_ARRENDATARIO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_HM_DOMICILIO_ARRENDATARIO, interviniente.getDireccion().getHm() != null ? interviniente.getDireccion().getHm().toString()
							: "", false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_BLOQUE_DOMICILIO_ARRENDATARIO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_BLOQUE_DOMICILIO_ARRENDATARIO, interviniente.getDireccion().getBloque(), false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_LETRA_DOMICILIO_ARRENDATARIO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_LETRA_DOMICILIO_ARRENDATARIO, interviniente.getDireccion().getLetra(), false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_ESCALERA_DOMICILIO_ARRENDATARIO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ESCALERA_DOMICILIO_ARRENDATARIO, interviniente.getDireccion().getEscalera(), false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PISO_DOMICILIO_ARRENDATARIO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PISO_DOMICILIO_ARRENDATARIO, interviniente.getDireccion().getPlanta(), false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PUERTA_DOMICILIO_ARRENDATARIO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PUERTA_DOMICILIO_ARRENDATARIO, interviniente.getDireccion().getPuerta(), false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_ARRENDATARIO)) {
					String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(interviniente.getDireccion().getIdMunicipio(), interviniente.getDireccion().getIdProvincia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_ARRENDATARIO, utilidadesConversiones.ajustarCamposIne(nombreMunicipio), false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PUEBLO_ARRENDATARIO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PUEBLO_ARRENDATARIO, utilidadesConversiones.ajustarCamposIne(interviniente.getDireccion().getPueblo()), false, false,
							ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PROVINCIA_ARRENDATARIO)) {
					String nombreProvincia = servicioDireccion.obtenerNombreProvincia(interviniente.getDireccion().getIdProvincia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PROVINCIA_ARRENDATARIO, nombreProvincia, false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_CP_ARRENDATARIO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CP_ARRENDATARIO, interviniente.getDireccion().getCodPostal(), false, false, ConstantesPDF._12));
				}
			}

		} else if (TipoTransferencia.tipo5.getValorEnum().equals(tramite.getTipoTransferencia())) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_COMPRAVENTA_ADQUIRIENTE, ConstantesPDF.VALOR_SI, true, false, ConstantesPDF._6));
		}
	}

	private void rellenarDatosConductorHabitual(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_CONDUCTOR_HABITUAL)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_CONDUCTOR_HABITUAL, interviniente.getId().getNif(), false, false, ConstantesPDF._12));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_NACIMIENTO_CONDUCTOR_HABITUAL)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_NACIMIENTO_CONDUCTOR_HABITUAL, utilesFecha.formatoFecha(interviniente.getPersona().getFechaNacimiento()), false, false,
						ConstantesPDF._12));
			}
		}
	}

	private void rellenarServicio(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (tramite.getVehiculo() != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_SERVICIO)) {
				String servicioDes = servicioVehiculo.obtenerServicioDescripcion(tramite.getVehiculo().getIdServicio());
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_SERVICIO, tramite.getVehiculo().getIdServicio() != null ? tramite.getVehiculo().getIdServicio() : "" + " - "
						+ servicioDes != null ? servicioDes : "", false, false, ConstantesPDF._8));
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CAMBIO_SERVICIO)) {
			String servicioDes = servicioVehiculo.obtenerServicioDescripcion(tramite.getVehiculo().getIdServicioAnterior());
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CAMBIO_SERVICIO, tramite.getVehiculo().getIdServicioAnterior() != null ? tramite.getVehiculo().getIdServicioAnterior() : "" + " - "
					+ servicioDes != null ? servicioDes : "", false, false, ConstantesPDF._8));
		}
	}

	private void rellenarDireccionVehiculo(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if ("SI".equals(tramite.getConsentimientocambio())) {
			if (tramite.getVehiculo() != null && tramite.getVehiculo().getDireccion() != null) {
				if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_VIA_VEHICULO)) {
					String tipoViaNombre = servicioDireccion.obtenerNombreTipoVia(tramite.getVehiculo().getDireccion().getIdTipoVia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_VIA_VEHICULO, tipoViaNombre, false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_CALLE_VEHICULO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CALLE_VEHICULO, utilidadesConversiones.ajustarCamposIne(tramite.getVehiculo().getDireccion().getNombreVia()), false, false,
							ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_NUM_DOMICILIO_VEHICULO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUM_DOMICILIO_VEHICULO, tramite.getVehiculo().getDireccion().getNumero(), false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_KM_DOMICILIO_VEHICULO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_KM_DOMICILIO_VEHICULO, tramite.getVehiculo().getDireccion().getKm() != null ? tramite.getVehiculo().getDireccion().getKm()
							.toString() : "", false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_HM_DOMICILIO_VEHICULO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_HM_DOMICILIO_VEHICULO, tramite.getVehiculo().getDireccion().getHm() != null ? tramite.getVehiculo().getDireccion().getHm()
							.toString() : "", false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_BLOQUE_DOMICILIO_VEHICULO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_BLOQUE_DOMICILIO_VEHICULO, tramite.getVehiculo().getDireccion().getBloque(), false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_LETRA_DOMICILIO_VEHICULO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_LETRA_DOMICILIO_VEHICULO, tramite.getVehiculo().getDireccion().getLetra(), false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_ESCALERA_DOMICILIO_VEHICULO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ESCALERA_DOMICILIO_VEHICULO, tramite.getVehiculo().getDireccion().getEscalera(), false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PISO_DOMICILIO_VEHICULO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PISO_DOMICILIO_VEHICULO, tramite.getVehiculo().getDireccion().getPlanta(), false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PUERTA_DOMICILIO_VEHICULO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PUERTA_DOMICILIO_VEHICULO, tramite.getVehiculo().getDireccion().getPuerta(), false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_VEHICULO)) {
					String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(tramite.getVehiculo().getDireccion().getIdMunicipio(), tramite.getVehiculo().getDireccion().getIdProvincia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_VEHICULO, utilidadesConversiones.ajustarCamposIne(nombreMunicipio), false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PUEBLO_VEHICULO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PUEBLO_VEHICULO, utilidadesConversiones.ajustarCamposIne(tramite.getVehiculo().getDireccion().getPueblo()), false, false,
							ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PROVINCIA_VEHICULO)) {
					String nombreProvincia = servicioDireccion.obtenerNombreProvincia(tramite.getVehiculo().getDireccion().getIdProvincia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PROVINCIA_VEHICULO, nombreProvincia, false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_CP_VEHICULO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CP_VEHICULO, tramite.getVehiculo().getDireccion().getCodPostal(), false, false, ConstantesPDF._9));
				}
			}
		}
	}

	private void rellenarResultadoTramitabilidad(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (camposPlantilla.contains(ConstantesPDF.ID_RESULTADO_TRAMITABILIDAD)) {
			if (tramite.getResCheckCtit() != null) {
				if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("modificar.pdf.transmision")) && (new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()).equals(
						tramite.getEstado()) || new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()).equals(tramite.getEstado()) || new BigDecimal(
								EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()).equals(tramite.getEstado()))) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_RESULTADO_TRAMITABILIDAD, gestorPropiedades.valorPropertie("pdf.transmision.tramitable"), true, false, ConstantesPDF._12));
				} else {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_RESULTADO_TRAMITABILIDAD, tramite.getResCheckCtit(), true, false, ConstantesPDF._12));
				}
			}
		}
	}

	private void rellenarResultadoCtit(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (camposPlantilla.contains(ConstantesPDF.ID_RESULTADO_CTIT)) {
			if (tramite.getRespuesta() != null) {
				String mensajeRespuestaCTIT = "";
				if (ConstantesPDF.MENSAJE_CTIT_RESULTADO_OK.equals(tramite.getRespuesta())) {
					mensajeRespuestaCTIT = ConstantesPDF.MENSAJE_CTIT_RESULTADO_OK;
					if (!"SI".equals(tramite.getImprPermisoCircu())) {
						mensajeRespuestaCTIT += ", " + ConstantesPDF.MENSAJE_CTIT_RESULTADO_OK_PERMISO_NO_SOLICITADO;
					} else if (ConstantesPDF.MENSAJE_CTIT_EMBARGO_O_PRECINTO.equals(tramite.getRespuesta())) {
						mensajeRespuestaCTIT += ", " + ConstantesPDF.MENSAJE_CTIT_RESULTADO_OK_PRECINTO;
					}
				} else {
					mensajeRespuestaCTIT = ConstantesPDF.MENSAJE_CTIT_RESULTADO_ERROR;
				}
				if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("modificar.pdf.transmision")) && (new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()).equals(
						tramite.getEstado()) || new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()).equals(tramite.getEstado()) || new BigDecimal(
								EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()).equals(tramite.getEstado()))) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_RESULTADO_CTIT, gestorPropiedades.valorPropertie("pdf.transmision.tramite.ok"), true, false, ConstantesPDF._12));
				} else {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_RESULTADO_CTIT, mensajeRespuestaCTIT, true, false, ConstantesPDF._12));
				}
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

	private byte[] insertarFirmas(TramiteTrafTranVO tramite, byte[] bytePdf, PdfMaker pdf, int[] vectPags, PersonaVO datosColegiado, ContratoVO contrato) {
		String fechaDeHoy = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		bytePdf = insertarFirmaGestor(tramite, bytePdf, pdf, vectPags, datosColegiado, contrato, fechaDeHoy);
		bytePdf = insertarFirmaColegio(tramite, bytePdf, pdf, vectPags, datosColegiado, contrato, fechaDeHoy);
		return bytePdf;
	}

	private byte[] insertarFirmaGestor(TramiteTrafTranVO tramite, byte[] bytePdf, PdfMaker pdf, int[] vectPags, PersonaVO datosColegiado, ContratoVO contrato, String fechaHoy) {
		String firmaGestor = (tramite.getVehiculo() != null && StringUtils.isNotBlank(tramite.getVehiculo().getBastidor()) ? tramite.getVehiculo().getBastidor() : "") + "#" + datosColegiado.getId()
				.getNif() + "#" + fechaHoy;

		firmaGestor = utilidadesConversiones.cifrarHMACSHA1(firmaGestor, "ClaveCifrado");
		firmaGestor = utilidadesConversiones.stringToHex(firmaGestor);
		firmaGestor = firmaGestor.substring(0, ConstantesPDF._16);

		bytePdf = insertarBarcodeFirma(ConstantesPDF.ID_FIRMA_GESTOR, bytePdf, vectPags, firmaGestor, pdf);

		return bytePdf;
	}

	private byte[] insertarFirmaColegio(TramiteTrafTranVO tramite, byte[] bytePdf, PdfMaker pdf, int[] vectPags, PersonaVO datosColegiado, ContratoVO contrato, String fechaHoy) {
		IntervinienteTraficoVO interviniente = null;
		if (TipoTransferencia.tipo5.getValorEnum().equals(tramite.getTipoTransferencia())) {
			interviniente = buscarInterviniente(tramite, TipoInterviniente.Compraventa.getValorEnum());
		} else {
			interviniente = buscarInterviniente(tramite, TipoInterviniente.Adquiriente.getValorEnum());
		}

		String firmaColegio = contrato.getCif() + "#" + (tramite.getVehiculo() != null && StringUtils.isNotBlank(tramite.getVehiculo().getBastidor()) ? tramite.getVehiculo().getBastidor() : "") + "#"
				+ (interviniente != null ? interviniente.getId().getNif() : "") + "#" + datosColegiado.getId().getNif() + "#" + contrato.getColegio().getCif() + "#" + fechaHoy;

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

	private byte[] marcaAgua(byte[] bytePdf, PdfMaker pdf, int[] vectPags) {
		String rutaMarcaAgua = getRutaMarcaAgua(ConstantesPDF.RUTA_MARCA_AGUA);
		Image img = pdf.cargarImagen(rutaMarcaAgua);
		bytePdf = pdf.insertarMarcaDeAgua(bytePdf, img, vectPags, ConstantesPDF._110, ConstantesPDF._250, ConstantesPDF._45);
		return bytePdf;
	}

	private IntervinienteTraficoVO buscarInterviniente(TramiteTrafTranVO tramite, String tipoInterviniente) {
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

	private List<IntervinienteTraficoVO> buscarCotitulares(TramiteTrafTranVO tramite) {
		List<IntervinienteTraficoVO> cotitulares = new ArrayList<IntervinienteTraficoVO>();
		if (tramite.getIntervinienteTraficosAsList() != null && !tramite.getIntervinienteTraficosAsList().isEmpty()) {
			for (IntervinienteTraficoVO inter : tramite.getIntervinienteTraficosAsList()) {
				if (TipoInterviniente.CotitularTransmision.getValorEnum().equals(inter.getId().getTipoInterviniente())) {
					cotitulares.add(inter);
				}
			}
		}
		return cotitulares;
	}

	private String getRuta(TramiteTrafTranVO tramite) {
		URL ruta = this.getClass().getResource(gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS) + getPlantillaPDFPresentacionTelematica(tramite));
		return ruta.getFile();
	}

	private String getPlantillaPDFPresentacionTelematica(TramiteTrafTranVO tramite) {
		String plantilla = TipoImpreso.TransmisionPDFPresentacionTelematica.getNombreEnum();
		IntervinienteTraficoVO transmitente = buscarInterviniente(tramite, TipoInterviniente.TransmitenteTrafico.getValorEnum());

		boolean exentoItp = tramite.getExentoItp() != null && "SI".equals(tramite.getExentoItp().toUpperCase()) ? true : false;
		boolean noSujetoItp = tramite.getNoSujetoItp() != null && "SI".equals(tramite.getNoSujetoItp().toUpperCase()) ? true : false;
		boolean vehiculoMayorDe10Anios = vehiculoMayorDe10Anios(tramite.getVehiculo());
		boolean ccaa = comprobarCCAA(tramite);
		boolean autonomo = transmitente.getAutonomo() != null && ("SI".equals(transmitente.getAutonomo().toUpperCase()) || "S".equals(transmitente.getAutonomo())) ? true : false;

		if (TipoTransferencia.tipo5.getValorEnum().equals(tramite.getTipoTransferencia())) {
			plantilla = TipoImpreso.TransmisionTradePresentacionTelematica.getNombreEnum();
			// Exento itp: SI - NoSujetoItp: NO
			if (exentoItp && !noSujetoItp) {
				// Mayor 10 años: SI
				if (vehiculoMayorDe10Anios && ccaa) {
					// Autonomo: SI
					if (autonomo) {
						plantilla = TipoImpreso.TransmisionTradePresentacionTelematicaExentoNoObligadoConAutonomo.getNombreEnum();
						// Autonomo: NO
					} else {
						plantilla = TipoImpreso.TransmisionTradePresentacionTelematicaExentoNoObligado.getNombreEnum();
					}
					// Mayor 10 años: NO
				} else {
					// Autonomo: SI
					if (autonomo) {
						plantilla = TipoImpreso.TransmisionTradePresentacionTelematicaExentoConAutonomo.getNombreEnum();
						// Autonomo: NO
					} else {
						plantilla = TipoImpreso.TransmisionTradePresentacionTelematicaExento.getNombreEnum();
					}
				}
			}
			// Exento itp: NO - NoSujetoItp: SI
			if (!exentoItp && noSujetoItp) {
				// Mayor 10 años: SI
				if (vehiculoMayorDe10Anios && ccaa) {
					// Autonomo: SI
					if (autonomo) {
						plantilla = TipoImpreso.TransmisionTradePresentacionTelematicaNoSujetoNoObligadoConAutonomo.getNombreEnum();
						// Autonomo: NO
					} else {
						plantilla = TipoImpreso.TransmisionTradePresentacionTelematicaNoSujetoNoObligado.getNombreEnum();
					}
					// Mayor 10 años: NO
				} else {
					// Autonomo: SI
					if (autonomo) {
						plantilla = TipoImpreso.TransmisionTradePresentacionTelematicaNoSujetoConAutonomo.getNombreEnum();
						// Autonomo: NO
					} else {
						plantilla = TipoImpreso.TransmisionTradePresentacionTelematicaNoSujeto.getNombreEnum();
					}
				}
			}
			// Mayor 10 años: SI - Exento itp: NO - NoSujetoItp: NO
			if (vehiculoMayorDe10Anios && ccaa && !exentoItp && !noSujetoItp) {
				// Autonomo: SI
				if (autonomo) {
					plantilla = TipoImpreso.TransmisionTradePresentacionTelematicaNoObligadoConAutonomo.getNombreEnum();
					// Autonomo: NO
				} else {
					plantilla = TipoImpreso.TransmisionTradePresentacionTelematicaNoObligado.getNombreEnum();
				}
			}
			// Autonomo: SI - Mayor 10 años: NO - Exento itp: NO - NoSujetoItp: NO
			if (autonomo && !vehiculoMayorDe10Anios && !exentoItp && !noSujetoItp) {
				plantilla = TipoImpreso.TransmisionTradePresentacionTelematicaConAutonomo.getNombreEnum();
			}
			// Autonomo: NO - Mayor 10 años: NO - Exento itp: NO - NoSujetoItp: NO
			if (!autonomo && !vehiculoMayorDe10Anios && !exentoItp && !noSujetoItp) {
				plantilla = TipoImpreso.TransmisionTradePresentacionTelematica.getNombreEnum();
			}
		} else {
			// Exento itp: SI - NoSujetoItp: NO
			if (exentoItp && !noSujetoItp) {
				// Mayor 10 años: SI
				if (vehiculoMayorDe10Anios && ccaa) {
					// Autonomo: SI
					if (autonomo) {
						plantilla = TipoImpreso.TransmisionPDFPresentacionTelematicaExentoNoObligadoConAutonomo.getNombreEnum();
						// Autonomo: NO
					} else {
						plantilla = TipoImpreso.TransmisionPDFPresentacionTelematicaExentoNoObligado.getNombreEnum();
					}
					// Mayor 10 años: NO
				} else {
					// Autonomo: SI
					if (autonomo) {
						plantilla = TipoImpreso.TransmisionPDFPresentacionTelematicaExentoConAutonomo.getNombreEnum();
						// Autonomo: NO
					} else {
						plantilla = TipoImpreso.TransmisionPDFPresentacionTelematicaExento.getNombreEnum();
					}
				}
			}
			// Exento itp: NO - NoSujetoItp: SI
			if (!exentoItp && noSujetoItp) {
				// Mayor 10 años: SI
				if (vehiculoMayorDe10Anios && ccaa) {
					// Autonomo: SI
					if (autonomo) {
						plantilla = TipoImpreso.TransmisionPDFPresentacionTelematicaNoSujetoNoObligadoConAutonomo.getNombreEnum();
						// Autonomo: NO
					} else {
						plantilla = TipoImpreso.TransmisionPDFPresentacionTelematicaNoSujetoNoObligado.getNombreEnum();
					}
					// Mayor 10 años: NO
				} else {
					// Autonomo: SI
					if (autonomo) {
						plantilla = TipoImpreso.TransmisionPDFPresentacionTelematicaNoSujetoConAutonomo.getNombreEnum();
						// Autonomo: NO
					} else {
						plantilla = TipoImpreso.TransmisionPDFPresentacionTelematicaNoSujeto.getNombreEnum();
					}
				}
			}
			// Mayor 10 años: SI - Exento itp: NO - NoSujetoItp: NO
			if (vehiculoMayorDe10Anios && ccaa && !exentoItp && !noSujetoItp) {
				// Autonomo: SI
				if (autonomo) {
					plantilla = TipoImpreso.TransmisionPDFPresentacionTelematicaNoObligadoConAutonomo.getNombreEnum();
					// Autonomo: NO
				} else {
					plantilla = TipoImpreso.TransmisionPDFPresentacionTelematicaNoObligado.getNombreEnum();
				}
			}
			// Autonomo: SI - Mayor 10 años: NO - Exento itp: NO - NoSujetoItp: NO
			if (autonomo && !vehiculoMayorDe10Anios && !exentoItp && !noSujetoItp) {
				plantilla = TipoImpreso.TransmisionPDFPresentacionTelematicaConAutonomo.getNombreEnum();
			}
			// Autonomo: NO - Mayor 10 años: NO - Exento itp: NO - NoSujetoItp: NO
			if (!autonomo && !vehiculoMayorDe10Anios && !exentoItp && !noSujetoItp) {
				plantilla = TipoImpreso.TransmisionPDFPresentacionTelematica.getNombreEnum();
			}
		}
		return plantilla;
	}

	private String getRutaMarcaAgua(String rutaJpg) {
		URL ruta = this.getClass().getResource(gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS) + rutaJpg);
		return ruta.getFile();
	}

	private boolean vehiculoMayorDe10Anios(VehiculoVO vehiculo) {
		boolean esMayor = false;
		try {
			if (vehiculo.getFechaPrimMatri() == null) {
				vehiculo.setFechaPrimMatri(vehiculo.getFechaMatriculacion());
			}

			Calendar fechaVehiculo = Calendar.getInstance();
			fechaVehiculo.setTime(vehiculo.getFechaPrimMatri());
			fechaVehiculo.add(Calendar.YEAR, 10);

			Calendar fechaHoy = new GregorianCalendar();
			if (fechaVehiculo.after(fechaHoy)) {
				esMayor = false;
			} else {
				esMayor = true;
			}
			return esMayor;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean comprobarCCAA(TramiteTrafTranVO tramite) {
		return noObligadoCCAA(recuperarProvinciaCET(tramite));
	}

	private boolean noObligadoCCAA(int codProvincia) {
		switch (codProvincia) {
			case 8:
				return true;
			case 17:
				return true;
			case 24:
				return true;
			case 43:
				return true;
			default:
				return false;
		}
	}

	private Integer recuperarProvinciaCET(TramiteTrafTranVO tramite) {
		int respuestaCET = 0;
		if (tramite != null) {
			IntervinienteTraficoVO adquiriente = buscarInterviniente(tramite, TipoInterviniente.Adquiriente.getValorEnum());
			IntervinienteTraficoVO compraventa = buscarInterviniente(tramite, TipoInterviniente.Compraventa.getValorEnum());

			if (StringUtils.isNotBlank(tramite.getIdProvinciaCet()) && !"-1".equals(tramite.getIdProvinciaCet())) {
				respuestaCET = Integer.valueOf(tramite.getIdProvinciaCet());
			} else if (tramite.getVehiculo() != null && tramite.getVehiculo().getDireccion() != null && StringUtils.isNotBlank(tramite.getVehiculo().getDireccion().getIdProvincia()) && !"-1".equals(
					tramite.getVehiculo().getDireccion().getIdProvincia())) {
				respuestaCET = Integer.valueOf(tramite.getVehiculo().getDireccion().getIdProvincia());
			} else if (adquiriente != null && adquiriente.getDireccion() != null && StringUtils.isNotBlank(adquiriente.getDireccion().getIdProvincia()) && !"-1".equals(adquiriente.getDireccion()
					.getIdProvincia())) {
				respuestaCET = Integer.valueOf(adquiriente.getDireccion().getIdProvincia());
			} else if (compraventa != null && compraventa.getDireccion() != null && StringUtils.isNotBlank(compraventa.getDireccion().getIdProvincia()) && !"-1".equals(compraventa.getDireccion()
					.getIdProvincia())) {
				respuestaCET = Integer.valueOf(compraventa.getDireccion().getIdProvincia());
			}
		}
		return respuestaCET;
	}
}
