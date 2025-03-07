package org.gestoresmadrid.oegamImpresion.pdf417.service.impl;

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
import org.gestoresmadrid.core.model.enumerados.ModoAdjudicacion;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.persona.service.ServicioComunPersona;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.vehiculo.service.ServicioComunVehiculo;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.pdf417.service.ServicioPdf417Ctit;
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
public class ServicioPdf417CtitImpl implements ServicioPdf417Ctit {

	private static final long serialVersionUID = -7232629774341553902L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPdf417CtitImpl.class);

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
	GestorPropiedades gestorPropiedades;

	@Override
	public ResultadoImpresionBean imprimirPdf417(List<BigDecimal> listaExpediente, boolean borrador) {
		ResultadoImpresionBean resultado = new ResultadoImpresionBean(Boolean.FALSE);
		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		boolean mensajeFinalizacionTrasNotificacion = false;
		try {
			for (BigDecimal numExpediente : listaExpediente) {
				TramiteTrafTranVO tramite = servicioTramiteTrafico.getTramiteTransmisionVO(numExpediente, true);
				if (tramite != null) {
					try {
						if (validarFinalizacionTrasNotificacion(tramite)) {
							byte[] bytePdf = imprimir(tramite, borrador, resultado);
							listaByte.add(bytePdf);
							// Guardamos el tipo de transferencia para luego descontar créditos
							if (resultado != null && StringUtils.isBlank(resultado.getTipoTransferencia())) {
								resultado.setTipoTransferencia(tramite.getTipoTransferencia());
							}
						} else {
							log.error("Limitaciones para Impresion PDF 417 de Transmisiones tipo 'FINALIZACION TRAS UNA NOTIFICACION': " + numExpediente);
							resultado.addListaTramitesError(numExpediente);
							mensajeFinalizacionTrasNotificacion = true;
						}
					} catch (Exception e) {
						log.error("Error al imprimir el tramite de ctit con numExpediente: " + numExpediente, e);
						resultado.addListaTramitesError(numExpediente);
					}
				} else {
					resultado.addListaTramitesError(numExpediente);
				}
			}
			if (listaByte != null && !listaByte.isEmpty()) {
				byte[] pdf = PdfMaker.concatenarPdf(listaByte);
				pdf = PdfMaker.encriptarPdf(pdf, "", "", true, false, false, false, false);
				resultado.setPdf(pdf);
			} else {
				if (mensajeFinalizacionTrasNotificacion && listaExpediente.size() == 1) {
					resultado.setMensaje("Limitaciones para Impresion PDF 417 de Transmisiones tipo 'FINALIZACION TRAS UNA NOTIFICACION'.");
				} else {
					resultado.setMensaje("No se ha generado nada para imprimir.");
				}
				resultado.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir el Pdf de Ctit, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el Pdf de Ctit.");
		}
		return resultado;
	}

	private boolean validarFinalizacionTrasNotificacion(TramiteTrafTranVO tramite) {
		String jefaturas = gestorPropiedades.valorPropertie("jefatura.finalizacion.tras.notificacion");
		if (TipoTransferencia.tipo2.getValorEnum().equals(tramite.getTipoTransferencia())) {
			if (StringUtils.isNotBlank(jefaturas) && ("TODOS".equals(jefaturas) || jefaturasTipoA(jefaturas, tramite.getJefaturaTrafico()))) {
				return true;
			} /*
				 * else if (tramite.getVehiculo() != null && StringUtils.isNotBlank(tramite.getVehiculo().getIdServicio()) && "B06".equals(tramite.getVehiculo().getIdServicio()) && StringUtils.isNotBlank( tramite.getFactura()) && (StringUtils.isNotBlank(tramite.getCetItp()) &&
				 * !"00000000".equals(tramite.getCetItp()))) { return true; }
				 */
			else if (tramite.getVehiculo() != null && StringUtils.isNotBlank(tramite.getFactura())) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	private boolean jefaturasTipoA(String jefaturas, JefaturaTraficoVO jefaturaTrafico) {
		if (jefaturaTrafico != null) {
			String[] listaJefaturas = jefaturas.split(",");
			if (listaJefaturas != null && listaJefaturas.length > 0) {
				for (String jef : listaJefaturas) {
					if (jef != null && jef.equals(jefaturaTrafico.getJefaturaProvincial())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private byte[] imprimir(TramiteTrafTranVO tramite, boolean borrador, ResultadoImpresionBean resultado) {
		PdfMaker pdf = new PdfMaker();
		byte[] bytePdf = pdf.abrirPdf(getRuta(tramite));
		Set<String> camposPlantilla = pdf.getAllFields(bytePdf);

		PersonaVO datosColegiado = servicioPersona.obtenerColegiadoCompleto(tramite.getContrato().getColegiado().getNumColegiado(), tramite.getContrato().getColegiado().getUsuario().getNif());

		ArrayList<CampoPdfBean> camposFormateados = rellenarPdf(tramite, camposPlantilla, tramite.getContrato(), datosColegiado, pdf);

		bytePdf = etiquetas(tramite, bytePdf, pdf, datosColegiado, tramite.getContrato(), borrador, resultado);

		bytePdf = pdf.setCampos(bytePdf, camposFormateados);

		return bytePdf;
	}

	private ArrayList<CampoPdfBean> rellenarPdf(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ContratoVO contrato, PersonaVO datosColegiado, PdfMaker pdf) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();

		rellenarTipoTransferencia(tramite, camposPlantilla, camposFormateados);

		rellenarDatosPresentacion(tramite, camposPlantilla, camposFormateados);

		rellenarDatosGestor(tramite, camposPlantilla, camposFormateados, contrato, datosColegiado);

		String tipoTransferencia = tramite.getTipoTransferencia();
		if (TipoTransferencia.tipo5.getValorEnum().equals(tipoTransferencia)) {
			rellenarDatosAdquiriente(tramite, camposPlantilla, camposFormateados, TipoInterviniente.Compraventa.getValorEnum());
		} else {
			rellenarDatosAdquiriente(tramite, camposPlantilla, camposFormateados, TipoInterviniente.Adquiriente.getValorEnum());
		}

		rellenarDatosRepreAdquiriente(tramite, camposPlantilla, camposFormateados, TipoInterviniente.RepresentanteAdquiriente.getValorEnum());

		rellenarDatosTransmitente(tramite, camposPlantilla, camposFormateados, TipoInterviniente.TransmitenteTrafico.getValorEnum());

		rellenarDatosRepreTransmitente(tramite, camposPlantilla, camposFormateados, TipoInterviniente.RepresentanteTransmitente.getValorEnum());

		rellenarDatosVehiculo(tramite, camposPlantilla, camposFormateados);

		rellenarResultadoTramitabilidad(tramite, camposPlantilla, camposFormateados);

		rellenarDireccionVehiculo(tramite, camposPlantilla, camposFormateados);

		rellenarDatosFinal(camposPlantilla, camposFormateados);

		rellenarMargenes(tramite, camposPlantilla, camposFormateados, datosColegiado, pdf);

		return camposFormateados;
	}

	private byte[] etiquetas(TramiteTrafTranVO tramite, byte[] bytePdf, PdfMaker pdf, PersonaVO datosColegiado, ContratoVO contrato, boolean borrador, ResultadoImpresionBean resultado) {
		int[] vectPags = new int[ConstantesPDF._1];
		vectPags[0] = ConstantesPDF._1;
		if (borrador) {
			pdf.establecerFuente(ConstantesPDF.HELVETICA, true, true, ConstantesPDF._26);
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NUBE_PUNTOS, "BORRADOR");
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._13);
		} else {
			bytePdf = nube(tramite, bytePdf, pdf, vectPags, contrato, resultado);
			bytePdf = marcaAgua(bytePdf, pdf, vectPags);
			bytePdf = insertarFirmas(tramite, bytePdf, pdf, vectPags, datosColegiado, contrato);
		}

		return bytePdf;
	}

	private byte[] nube(TramiteTrafTranVO tramite, byte[] bytePdf, PdfMaker pdf, int[] vectPags, ContratoVO contrato, ResultadoImpresionBean resultado) {
		String nube = null;
		String nubeFinal = null;
		String nubeFinalConN = null;
		if ("5".equals(tramite.getTipoTransferencia())) {
			nube = utilesNube.obtenerNubeCtitParaBaja(tramite);
			nubeFinal = utilesNube.sustituyeCaracteres_Plus(nube);
		} else {
			nube = utilesNube.obtenerNubeCtit(tramite, contrato);
			String caracteresNuevaAplicacion = gestorPropiedades.valorPropertie("sustituye.caracteres.nueva.aplicacion");
			if (caracteresNuevaAplicacion != null && "SI".equals(caracteresNuevaAplicacion)) {
				nubeFinal = utilesNube.sustituyeCaracteresNuevaAplicacion(nube);
			} else {
				nubeFinal = utilesNube.sustituyeCaracteres(nube);
			}
			nubeFinalConN = utilesNube.sustituyeCaracteresConN(nube);
		}
		resultado.setNube(nubeFinal);
		resultado.setNubeConN(nubeFinalConN);
		bytePdf = insertarNubePuntos(nubeFinal, ConstantesPDF.ID_NUBE_PUNTOS, bytePdf, pdf, vectPags);
		return bytePdf;
	}

	private byte[] insertarNubePuntos(String nube, String tipoNube, byte[] bytePdf, PdfMaker pdf, int[] vectPags) {
		List<FieldPosition> posiciones = pdf.getFieldPosition(bytePdf, tipoNube);
		FieldPosition posicion = posiciones.get(0);
		Image img = pdf.crearNubePuntos(nube);
		img = pdf.escalarImagen(img, posicion.position.getWidth(), posicion.position.getHeight());
		return pdf.insertarImagen(bytePdf, img, vectPags, posicion.position.getLeft(), posicion.position.getBottom());
	}

	private void rellenarTipoTransferencia(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		String tipoTransferencia = "";
		if ("1".equals(tramite.getTipoTransferencia())) {
			tipoTransferencia = ConstantesPDF.ID_TIPO_TRANSMISION_1;
		} else if ("2".equals(tramite.getTipoTransferencia())) {
			tipoTransferencia = ConstantesPDF.ID_TIPO_TRANSMISION_2;
		} else if ("3".equals(tramite.getTipoTransferencia())) {
			tipoTransferencia = ConstantesPDF.ID_TIPO_TRANSMISION_3;
		} else if ("4".equals(tramite.getTipoTransferencia())) {
			tipoTransferencia = ConstantesPDF.ID_TIPO_TRANSMISION_4;
		} else if ("5".equals(tramite.getTipoTransferencia())) {
			//tipoTransferencia = ConstantesPDF.ID_TIPO_TRANSMISION_5;
			tipoTransferencia = "SOLICITUD BAJA TEMPORAL POR TRANSFERENCIA";
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_TRANSFERENCIA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRANSFERENCIA, tipoTransferencia.toUpperCase(), true, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_SOLICITUD)) {
			if ("5".equals(tramite.getTipoTransferencia())) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_SOLICITUD, "Solicitud Baja Temporal por Transferencia", false, false, ConstantesPDF._7));
			} else {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_SOLICITUD, tipoTransferencia, false, false, ConstantesPDF._7));
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_TRANSFERENCIA_2)) {
			if ("5".equals(tramite.getTipoTransferencia())) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRANSFERENCIA_2, "Solicitud Baja Temporal por Transferencia", false, false, ConstantesPDF._9));
			} else {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRANSFERENCIA_2, tipoTransferencia, false, false, ConstantesPDF._9));
			}
		}
	}

	private void rellenarDatosPresentacion(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (camposPlantilla.contains(ConstantesPDF.ID_TASA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TASA, tramite.getTasa() != null && tramite.getTasa().getCodigoTasa() != null ? tramite.getTasa().getCodigoTasa() : "", true, false,
					ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_TASA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TASA, tramite.getTasa() != null && tramite.getTasa().getTipoTasa() != null ? tramite.getTasa().getTipoTasa() : "", false,
					false, ConstantesPDF._9));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_NUM_EXPEDIENTE)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUM_EXPEDIENTE, tramite.getNumExpediente() != null ? tramite.getNumExpediente().toString() : "", false, false, ConstantesPDF._12));
		}

		if (tramite.getVehiculo() != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_MATRI)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MATRI, tramite.getVehiculo().getMatricula(), true, false, ConstantesPDF._12));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_MATRI)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_MATRI, utilesFecha.formatoFecha(tramite.getVehiculo().getFechaMatriculacion()), false, false, ConstantesPDF._12));
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CEMA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CEMA, tramite.getCema() != null ? tramite.getCema() : "", false, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CET)) {
			if ("5".equals(tramite.getTipoTransferencia())) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CET, StringUtils.isNotBlank(tramite.getCetItp()) ? tramite.getCetItp() : "00000000", false, false, ConstantesPDF._12));
			} else {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CET, tramite.getCetItp() != null ? tramite.getCetItp() : "", false, false, ConstantesPDF._12));
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_PRESENTACION)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_PRESENTACION, utilesFecha.formatoFecha(tramite.getFechaPresentacion()), false, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CLASE_TRANSMISION)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CLASE_TRANSMISION, StringUtils.isNotBlank(tramite.getTipoTransferencia()) ? TipoTransferencia.convertir(tramite
					.getTipoTransferencia()).getNombreEnum() : "", false, false, ConstantesPDF._6));
		}

		if (tramite.getJefaturaTrafico() != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_SUCURSAL_JEFATURA)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_SUCURSAL_JEFATURA, tramite.getJefaturaTrafico().getDescripcion(), false, false, ConstantesPDF._12));
			}
		}
	}

	private void rellenarDatosGestor(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, ContratoVO contrato, PersonaVO datosColegiado) {
		if (camposPlantilla.contains(ConstantesPDF.ID_NUM_PROFESIONAL)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUM_PROFESIONAL, tramite.getNumColegiado(), false, false, ConstantesPDF._9));
		}

		if (datosColegiado != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_PROFESIONAL)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_PROFESIONAL, datosColegiado.getNombre() + " " + datosColegiado.getApellido1RazonSocial() + " " + datosColegiado
						.getApellido2(), true, false, ConstantesPDF._9));
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_GESTORIA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_GESTORIA, contrato.getRazonSocial() + " (" + contrato.getColegiado().getUsuario().getNif() + ")", false, false, ConstantesPDF._8));
		}
	}

	private void rellenarDatosAdquiriente(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
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

			if (camposPlantilla.contains(ConstantesPDF.ID_CV)) {
				IntervinienteTraficoVO intervinienteCV = buscarInterviniente(tramite, TipoInterviniente.Compraventa.getValorEnum());
				if (intervinienteCV != null && interviniente.getId().getNif().equals(intervinienteCV.getId().getNif())) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CV, "SI", true, false, ConstantesPDF._15));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_CODIGO_IAE_ADQUIRIENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CODIGO_IAE_ADQUIRIENTE, (interviniente.getCodigoIae() != null ? interviniente.getCodigoIae() : ""), false, false,
						ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_NACIMIENTO_ADQUIRIENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_NACIMIENTO_ADQUIRIENTE, utilesFecha.formatoFecha(interviniente.getPersona().getFechaNacimiento()), false, false,
						ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_SEXO_ADQUIRIENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_SEXO_ADQUIRIENTE, interviniente.getPersona().getSexo(), false, false, ConstantesPDF._9));
			}

			if (interviniente.getDireccion() != null) {
				if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_VIA_ADQUIRIENTE)) {
					String tipoViaNombre = servicioDireccion.obtenerNombreTipoVia(interviniente.getDireccion().getIdTipoVia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_VIA_ADQUIRIENTE, tipoViaNombre, false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_CALLE_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CALLE_ADQUIRIENTE, utilidadesConversiones.ajustarCamposIne(interviniente.getDireccion().getNombreVia()), false, false,
							ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_NUM_DOMICILIO_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUM_DOMICILIO_ADQUIRIENTE, interviniente.getDireccion().getNumero(), false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_KM_DOMICILIO_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_KM_DOMICILIO_ADQUIRIENTE, interviniente.getDireccion().getKm() != null ? interviniente.getDireccion().getKm().toString()
							: "", false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_HM_DOMICILIO_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_HM_DOMICILIO_ADQUIRIENTE, interviniente.getDireccion().getHm() != null ? interviniente.getDireccion().getHm().toString()
							: "", false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_BLOQUE_DOMICILIO_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_BLOQUE_DOMICILIO_ADQUIRIENTE, interviniente.getDireccion().getBloque(), false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_LETRA_DOMICILIO_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_LETRA_DOMICILIO_ADQUIRIENTE, interviniente.getDireccion().getLetra(), false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_ESCALERA_DOMICILIO_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ESCALERA_DOMICILIO_ADQUIRIENTE, interviniente.getDireccion().getEscalera(), false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PISO_DOMICILIO_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PISO_DOMICILIO_ADQUIRIENTE, interviniente.getDireccion().getPlanta(), false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PUERTA_DOMICILIO_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PUERTA_DOMICILIO_ADQUIRIENTE, interviniente.getDireccion().getPuerta(), false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_ADQUIRIENTE)) {
					String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(interviniente.getDireccion().getIdMunicipio(), interviniente.getDireccion().getIdProvincia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_ADQUIRIENTE, utilidadesConversiones.ajustarCamposIne(nombreMunicipio), false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PUEBLO_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PUEBLO_ADQUIRIENTE, utilidadesConversiones.ajustarCamposIne(interviniente.getDireccion().getPueblo()), false, false,
							ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PROVINCIA_ADQUIRIENTE)) {
					String nombreProvincia = servicioDireccion.obtenerNombreProvincia(interviniente.getDireccion().getIdProvincia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PROVINCIA_ADQUIRIENTE, nombreProvincia, false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_CP_ADQUIRIENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CP_ADQUIRIENTE, interviniente.getDireccion().getCodPostal(), false, false, ConstantesPDF._9));
				}
			}
		}
	}

	private void rellenarDatosRepreAdquiriente(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_REPR_ADQUIRIENTE)) {
				String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
						.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
								.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_REPR_ADQUIRIENTE, nombreApellidos, false, false, ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_REPR_ADQUIRIENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_REPR_ADQUIRIENTE, interviniente.getId().getNif(), false, false, ConstantesPDF._9));
			}
		}
	}

	private void rellenarDatosTransmitente(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TRANSMITENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_TRANSMITENTE, interviniente.getId().getNif(), false, false, ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_CV2)) {
				IntervinienteTraficoVO intervinienteCV = buscarInterviniente(tramite, TipoInterviniente.Compraventa.getValorEnum());
				if (intervinienteCV != null && interviniente.getId().getNif().equals(intervinienteCV.getId().getNif())) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CV2, "SI", true, false, ConstantesPDF._15));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_CODIGO_IAE_TRANSMITENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CODIGO_IAE_TRANSMITENTE, (interviniente.getCodigoIae() != null ? interviniente.getCodigoIae() : ""), false, false,
						ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TRANSMITENTE)) {
				String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
						.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
								.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TRANSMITENTE, nombreApellidos, false, false, ConstantesPDF._9));
			}
		}
	}

	private void rellenarDatosRepreTransmitente(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_REPR_TRANSMITENTE)) {
				String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
						.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
								.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_REPR_TRANSMITENTE, nombreApellidos, false, false, ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_REPR_TRANSMITENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_REPR_TRANSMITENTE, interviniente.getId().getNif(), false, false, ConstantesPDF._9));
			}
		}
	}

	private void rellenarDatosVehiculo(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (tramite.getVehiculo() != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_MARCA)) {
				if (tramite.getVehiculo().getCodigoMarca() != null) {
					String marca = servicioVehiculo.obtenerNombreMarca(tramite.getVehiculo().getCodigoMarca(), false);
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MARCA, marca, false, false, ConstantesPDF._10));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_MODELO)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MODELO, tramite.getVehiculo().getModelo(), false, false, ConstantesPDF._10));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_MODO_ADJUDICACION)) {
				String modoAdjudicacion = ModoAdjudicacion.convertirTexto(tramite.getModoAdjudicacion());
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MODO_ADJUDICACION, modoAdjudicacion, false, false, ConstantesPDF._10));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_SERVICIO)) {
				String servicioDes = servicioVehiculo.obtenerServicioDescripcion(tramite.getVehiculo().getIdServicio());
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_SERVICIO, tramite.getVehiculo().getIdServicio() != null ? tramite.getVehiculo().getIdServicio() : "" + " - "
						+ servicioDes != null ? servicioDes : "", false, false, ConstantesPDF._10));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_RESULTADO_ITV)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_RESULTADO_ITV, tramite.getVehiculo().getConceptoItv(), false, false, ConstantesPDF._10));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_MOTIVO_ITV)) {
				if (StringUtils.isNotBlank(tramite.getVehiculo().getIdMotivoItv())) {
					String descripcion = servicioVehiculo.obtenerDescripcionMotivoItv(tramite.getVehiculo().getIdMotivoItv());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MOTIVO_ITV, descripcion, false, false, ConstantesPDF._10));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_ESTACION_ITV)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ESTACION_ITV, tramite.getVehiculo().getEstacionItv(), false, false, ConstantesPDF._10));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_ITV)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_ITV, utilesFecha.formatoFecha(tramite.getVehiculo().getFechaItv()), false, false, ConstantesPDF._10));
			}
		}
	}

	private void rellenarResultadoTramitabilidad(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (camposPlantilla.contains(ConstantesPDF.ID_ERROR_GEST)) {
			if (tramite.getResCheckCtit() != null) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ERROR_GEST, tramite.getResCheckCtit(), false, false, ConstantesPDF._9));
			}
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

	private void rellenarDatosFinal(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_ACTUAL)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_ACTUAL, utilesFecha.getFechaHoy(), false, false, ConstantesPDF._12));
		}

	}

	private void rellenarMargenes(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, PersonaVO datosColegiado, PdfMaker pdf) {
		if (camposPlantilla.contains(ConstantesPDF.ID_TELEMATICA)) {
			if ("TRAMITABLE".equals(tramite.getResCheckCtit()) && (StringUtils.isNotBlank(tramite.getRespuesta()) || "TRÁMITE OK".equals(tramite.getRespuesta()))) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TELEMATICA, ConstantesPDF.ID_EXP_TELEMATICO, true, false, ConstantesPDF._8));
			} else {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TELEMATICA, ConstantesPDF.ID_EXP_NO_TELEMATICO, true, false, ConstantesPDF._8));
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

	private String getRuta(TramiteTrafTranVO tramite) {
		String plantilla = TipoImpreso.TransmisionPDF417.getNombreEnum();
		/*
		 * if (TipoTransferencia.tipo5.getValorEnum().equals(tramite.getTipoTransferencia())) { plantilla = TipoImpreso.TransmisionPDF417Trade.getNombreEnum(); } else { plantilla = TipoImpreso.TransmisionPDF417.getNombreEnum(); }
		 */
		URL ruta = this.getClass().getResource(gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS) + plantilla);
		return ruta.getFile();
	}

	private String getRutaMarcaAgua(String rutaJpg) {
		URL ruta = this.getClass().getResource(gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS) + rutaJpg);
		return ruta.getFile();
	}
}
