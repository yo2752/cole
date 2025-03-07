package org.gestoresmadrid.oegamImpresion.pdf417.service.impl;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import org.gestoresmadrid.oegamImpresion.pdf417.service.ServicioPdf417Baja;
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
public class ServicioPdf417BajaImpl implements ServicioPdf417Baja {

	private static final long serialVersionUID = -2055993089757622666L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPdf417BajaImpl.class);

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
	public ResultadoImpresionBean imprimirPdf417(List<BigDecimal> listaExpediente, boolean borrador) {
		ResultadoImpresionBean resultado = new ResultadoImpresionBean(Boolean.FALSE);
		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		try {
			for (BigDecimal numExpediente : listaExpediente) {
				TramiteTrafBajaVO tramite = servicioTramiteTrafico.getTramiteBajaVO(numExpediente, true);
				if (tramite != null) {
					try {
						byte[] bytePdf = imprimir(tramite, borrador, resultado);
						listaByte.add(bytePdf);
					} catch (Exception e) {
						log.error("Error al imprimir el tramite de baja con numExpediente: " + numExpediente, e);
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
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha generado nada para imprimir.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir el Pdf 417 de Baja, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el Pdf 417 de Baja.");
		}
		return resultado;
	}

	private byte[] imprimir(TramiteTrafBajaVO tramite, boolean borrador, ResultadoImpresionBean resultado) {
		PdfMaker pdf = new PdfMaker();
		byte[] bytePdf = pdf.abrirPdf(getRuta());
		Set<String> camposPlantilla = pdf.getAllFields(bytePdf);

		PersonaVO datosColegiado = servicioPersona.obtenerColegiadoCompleto(tramite.getContrato().getColegiado().getNumColegiado(), tramite.getContrato().getColegiado().getUsuario().getNif());

		ArrayList<CampoPdfBean> camposFormateados = rellenarPdf(tramite, camposPlantilla, tramite.getContrato(), datosColegiado, pdf);

		bytePdf = etiquetas(tramite, bytePdf, pdf, datosColegiado, tramite.getContrato(), borrador, resultado);

		bytePdf = pdf.setCampos(bytePdf, camposFormateados);

		return bytePdf;
	}

	private ArrayList<CampoPdfBean> rellenarPdf(TramiteTrafBajaVO tramite, Set<String> camposPlantilla, ContratoVO contrato, PersonaVO datosColegiado, PdfMaker pdf) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();

		rellenarDatosPresentacion(tramite, camposPlantilla, camposFormateados);

		rellenarDatosGestor(tramite, camposPlantilla, camposFormateados, contrato, datosColegiado);

		rellenarDatosTitular(tramite, camposPlantilla, camposFormateados, TipoInterviniente.Titular.getValorEnum());

		rellenarDatosRepreTitular(tramite, camposPlantilla, camposFormateados, TipoInterviniente.RepresentanteTitular.getValorEnum());

		rellenarDatosAdquiriente(tramite, camposPlantilla, camposFormateados, TipoInterviniente.Adquiriente.getValorEnum());

		rellenarDatosRepreAdquiriente(tramite, camposPlantilla, camposFormateados, TipoInterviniente.RepresentanteAdquiriente.getValorEnum());

		rellenarResultadoBtv(tramite, camposPlantilla, camposFormateados);

		return camposFormateados;
	}

	private byte[] etiquetas(TramiteTrafBajaVO tramite, byte[] bytePdf, PdfMaker pdf, PersonaVO datosColegiado, ContratoVO contrato, boolean borrador, ResultadoImpresionBean resultado) {
		int[] vectPags = new int[ConstantesPDF._1];
		vectPags[0] = ConstantesPDF._1;

		if (borrador) {
			pdf.establecerFuente(ConstantesPDF.HELVETICA, true, true, ConstantesPDF._26);
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NUBE_PUNTOS, "BORRADOR");
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._13);
		} else {
			bytePdf = nube(tramite, bytePdf, pdf, vectPags, resultado);
			bytePdf = marcaAgua(bytePdf, pdf, vectPags);
		}

		return bytePdf;
	}

	private byte[] nube(TramiteTrafBajaVO tramite, byte[] bytePdf, PdfMaker pdf, int[] vectPags, ResultadoImpresionBean resultado) {
		String nube = utilesNube.obtenerNubeBaja(tramite);
		nube = utilesNube.sustituyeCaracteres_Plus(nube);
		resultado.setNube(nube);
		bytePdf = insertarNubePuntos(nube, ConstantesPDF.ID_NUBE_PUNTOS, bytePdf, pdf, vectPags);
		return bytePdf;
	}

	private byte[] marcaAgua(byte[] bytePdf, PdfMaker pdf, int[] vectPags) {
		String rutaMarcaAgua = getRutaMarcaAgua(ConstantesPDF.RUTA_MARCA_AGUA);
		Image img = pdf.cargarImagen(rutaMarcaAgua);
		bytePdf = pdf.insertarMarcaDeAgua(bytePdf, img, vectPags, ConstantesPDF._110, ConstantesPDF._250, ConstantesPDF._45);
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
		if (camposPlantilla.contains(ConstantesPDF.ID_TASA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TASA, tramite.getTasa() != null && tramite.getTasa().getCodigoTasa() != null ? tramite.getTasa().getCodigoTasa() : "", true, false,
					ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_TASA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TASA, tramite.getTasa() != null && tramite.getTasa().getTipoTasa() != null ? tramite.getTasa().getTipoTasa() : "", true, false,
					ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_NUM_EXPEDIENTE)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUM_EXPEDIENTE, tramite.getNumExpediente() != null ? tramite.getNumExpediente().toString() : "", false, false, ConstantesPDF._12));
		}
		
		if (camposPlantilla.contains(ConstantesPDF.ID_CEMA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CEMA, tramite.getCema() != null ? tramite.getCema() : "", false, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_MATRI)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MATRI, tramite.getVehiculo() != null && tramite.getVehiculo().getMatricula() != null ? tramite.getVehiculo().getMatricula() : "",
					true, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_MATRI)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_MATRI, tramite.getVehiculo() != null && tramite.getVehiculo().getFechaMatriculacion() != null ? utilesFecha.formatoFecha(
					tramite.getVehiculo().getFechaMatriculacion()) : "", true, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_PRESENTACION)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_PRESENTACION, utilesFecha.formatoFecha(tramite.getFechaPresentacion()), false, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_BAJA)) {
			String tipoBaja = "";
			if (tramite.getMotivoBaja() != null) {
				if (MotivoBaja.convertirTexto(tramite.getMotivoBaja()).toUpperCase().contains(ConstantesPDF.TEMPORAL)) {
					tipoBaja = ConstantesPDF.TEMPORAL;
				} else if (MotivoBaja.convertirTexto(tramite.getMotivoBaja()).toUpperCase().contains(ConstantesPDF.DEFINITIVA)) {
					tipoBaja = ConstantesPDF.DEFINITIVA;
				}
			}
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_BAJA, tipoBaja, false, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_MOTIVO_BAJA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MOTIVO_BAJA, tramite.getMotivoBaja() != null ? MotivoBaja.convertirTexto(tramite.getMotivoBaja()) : "", false, false,
					ConstantesPDF._9));
		}

		if (tramite.getJefaturaTrafico() != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_SUCURSAL_JEFATURA)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_SUCURSAL_JEFATURA, tramite.getJefaturaTrafico().getDescripcion(), false, false, ConstantesPDF._12));
			}
		}
	}

	private void rellenarDatosGestor(TramiteTrafBajaVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, ContratoVO contrato, PersonaVO datosColegiado) {
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

	private void rellenarDatosTitular(TramiteTrafBajaVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR, interviniente.getId().getNif(), false, false, ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR)) {
				String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
						.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
								.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR, nombreApellidos, false, false, ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_NACIMIENTO_TITULAR)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_NACIMIENTO_TITULAR, utilesFecha.formatoFecha(interviniente.getPersona().getFechaNacimiento()), false, false,
						ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_SEXO_TITULAR)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_SEXO_TITULAR, interviniente.getPersona().getSexo(), false, false, ConstantesPDF._9));
			}

			if (interviniente.getDireccion() != null) {
				if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_VIA_TITULAR)) {
					String tipoViaNombre = servicioDireccion.obtenerNombreTipoVia(interviniente.getDireccion().getIdTipoVia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_VIA_TITULAR, tipoViaNombre, false, false, ConstantesPDF._9));
				}
				if (camposPlantilla.contains(ConstantesPDF.ID_CALLE_TITULAR)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CALLE_TITULAR, utilidadesConversiones.ajustarCamposIne(interviniente.getDireccion().getNombreVia()), false, false,
							ConstantesPDF._9));
				}
				if (camposPlantilla.contains(ConstantesPDF.ID_NUM_DOMICILIO_TITULAR)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUM_DOMICILIO_TITULAR, interviniente.getDireccion().getNumero(), false, false, ConstantesPDF._9));
				}
				if (camposPlantilla.contains(ConstantesPDF.ID_KM_DOMICILIO_TITULAR)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_KM_DOMICILIO_TITULAR, interviniente.getDireccion().getKm() != null ? interviniente.getDireccion().getKm().toString() : "",
							false, false, ConstantesPDF._9));
				}
				if (camposPlantilla.contains(ConstantesPDF.ID_HM_DOMICILIO_TITULAR)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_HM_DOMICILIO_TITULAR, interviniente.getDireccion().getHm() != null ? interviniente.getDireccion().getHm().toString() : "",
							false, false, ConstantesPDF._9));
				}
				if (camposPlantilla.contains(ConstantesPDF.ID_BLOQUE_DOMICILIO_TITULAR)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_BLOQUE_DOMICILIO_TITULAR, interviniente.getDireccion().getBloque(), false, false, ConstantesPDF._9));
				}
				if (camposPlantilla.contains(ConstantesPDF.ID_LETRA_DOMICILIO_TITULAR)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_LETRA_DOMICILIO_TITULAR, interviniente.getDireccion().getLetra(), false, false, ConstantesPDF._9));
				}
				if (camposPlantilla.contains(ConstantesPDF.ID_ESCALERA_DOMICILIO_TITULAR)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ESCALERA_DOMICILIO_TITULAR, interviniente.getDireccion().getEscalera(), false, false, ConstantesPDF._9));
				}
				if (camposPlantilla.contains(ConstantesPDF.ID_PISO_DOMICILIO_TITULAR)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PISO_DOMICILIO_TITULAR, interviniente.getDireccion().getPlanta(), false, false, ConstantesPDF._9));
				}
				if (camposPlantilla.contains(ConstantesPDF.ID_PUERTA_DOMICILIO_TITULAR)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PUERTA_DOMICILIO_TITULAR, interviniente.getDireccion().getPuerta(), false, false, ConstantesPDF._9));
				}
				if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_TITULAR)) {
					String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(interviniente.getDireccion().getIdMunicipio(), interviniente.getDireccion().getIdProvincia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_TITULAR, utilidadesConversiones.ajustarCamposIne(nombreMunicipio), false, false, ConstantesPDF._9));
				}
				if (camposPlantilla.contains(ConstantesPDF.ID_PUEBLO_TITULAR)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PUEBLO_TITULAR, utilidadesConversiones.ajustarCamposIne(interviniente.getDireccion().getPueblo()), false, false,
							ConstantesPDF._9));
				}
				if (camposPlantilla.contains(ConstantesPDF.ID_PROVINCIA_TITULAR)) {
					String nombreProvincia = servicioDireccion.obtenerNombreProvincia(interviniente.getDireccion().getIdProvincia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PROVINCIA_TITULAR, nombreProvincia, false, false, ConstantesPDF._9));
				}
				if (camposPlantilla.contains(ConstantesPDF.ID_CP_TITULAR)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CP_TITULAR, interviniente.getDireccion().getCodPostal(), false, false, ConstantesPDF._9));
				}
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

			if (camposPlantilla.contains(ConstantesPDF.ID_CONCEPTO_TUTELA_REPR_TITULAR)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CONCEPTO_TUTELA_REPR_TITULAR, (interviniente.getConceptoRepre() != null && !"-1".equals(interviniente.getConceptoRepre())
						? interviniente.getConceptoRepre() : ""), false, false, ConstantesPDF._9));
			}

		}
	}

	private void rellenarDatosAdquiriente(TramiteTrafBajaVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_ADQUIRIENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_ADQUIRIENTE, interviniente.getId().getNif(), false, false, ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_ADQUIRIENTE)) {
				String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
						.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
								.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_ADQUIRIENTE, nombreApellidos, false, false, ConstantesPDF._9));
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

	private void rellenarDatosRepreAdquiriente(TramiteTrafBajaVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_REPR_ADQUIRIENTE)) {
				String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
						.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
								.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_REPR_TITULAR, nombreApellidos, false, false, ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_CONCEPTO_TUTELA_REPR_TITULAR)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CONCEPTO_TUTELA_REPR_TITULAR, (interviniente.getConceptoRepre() != null && !"-1".equals(interviniente.getConceptoRepre())
						? interviniente.getConceptoRepre() : ""), false, false, ConstantesPDF._9));
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
		URL ruta = this.getClass().getResource(gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS) + TipoImpreso.BajasPDF417.getNombreEnum());
		return ruta.getFile();
	}

	private String getRutaMarcaAgua(String rutaJpg) {
		URL ruta = this.getClass().getResource(gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS) + rutaJpg);
		return ruta.getFile();
	}
}
