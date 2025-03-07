package org.gestoresmadrid.oegamImpresion.pdf.service.impl;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.NoSujeccionOExencion;
import org.gestoresmadrid.core.model.enumerados.ReduccionNoSujeccionOExencion05;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoMotivoExencion;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteMatriculacion;
import org.gestoresmadrid.core.model.enumerados.TiposReduccion576;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Alimentacion;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Color;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Combustible;
import org.gestoresmadrid.core.vehiculo.model.enumerados.ConceptoTutela;
import org.gestoresmadrid.core.vehiculo.model.enumerados.PaisFabricacion;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Procedencia;
import org.gestoresmadrid.core.vehiculo.model.enumerados.TiposInspeccionItv;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.persona.service.ServicioComunPersona;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.vehiculo.service.ServicioComunVehiculo;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.pdf.service.ServicioPdfMatw;
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
public class ServicioPdfMatwImpl implements ServicioPdfMatw {

	private static final long serialVersionUID = 4890263407453643860L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPdfMatwImpl.class);

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
	public ResultadoImpresionBean imprimirPdf(List<BigDecimal> listaExpediente, boolean borrador) {
		ResultadoImpresionBean resultado = new ResultadoImpresionBean(Boolean.FALSE);
		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		try {
			for (BigDecimal numExpediente : listaExpediente) {
				TramiteTrafMatrVO tramite = servicioTramiteTrafico.getTramiteMatriculacionVO(numExpediente, true);
				if (tramite != null) {
					try {
						byte[] bytePdf = imprimir(tramite, borrador, resultado);
						listaByte.add(bytePdf);
					} catch (Exception e) {
						log.error("Error al imprimir el tramite de matriculacion con numExpediente: " + numExpediente, e);
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
			log.error("Ha sucedido un error a la hora de imprimir el Pdf de Matriculacion, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el Pdf de Matriculacion.");
		}
		return resultado;
	}

	private byte[] imprimir(TramiteTrafMatrVO tramite, boolean borrador, ResultadoImpresionBean resultado) {
		PdfMaker pdf = new PdfMaker();
		byte[] bytePdf = pdf.abrirPdf(getRuta());
		Set<String> camposPlantilla = pdf.getAllFields(bytePdf);

		PersonaVO datosColegiado = servicioPersona.obtenerColegiadoCompleto(tramite.getContrato().getColegiado().getNumColegiado(), tramite.getContrato().getColegiado().getUsuario().getNif());

		ArrayList<CampoPdfBean> camposFormateados = rellenarPdf(tramite, camposPlantilla, tramite.getContrato(), datosColegiado, pdf);

		bytePdf = etiquetas(tramite, bytePdf, pdf, datosColegiado, tramite.getContrato(), borrador, resultado);

		bytePdf = pdf.setCampos(bytePdf, camposFormateados);

		return bytePdf;
	}

	private ArrayList<CampoPdfBean> rellenarPdf(TramiteTrafMatrVO tramite, Set<String> camposPlantilla, ContratoVO contrato, PersonaVO datosColegiado, PdfMaker pdf) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();

		rellenarSinCondig(tramite, camposPlantilla, camposFormateados);

		rellenarDatosPresentacion(tramite, camposPlantilla, camposFormateados);

		rellenarDatosGestor(camposPlantilla, camposFormateados, contrato, datosColegiado);

		rellenarDatosTitular(tramite, camposPlantilla, camposFormateados, TipoInterviniente.Titular.getValorEnum());

		rellenarDatosRepresentanteTitular(tramite, camposPlantilla, camposFormateados, TipoInterviniente.RepresentanteTitular.getValorEnum());

		rellenarDatosVehiculo(tramite, camposPlantilla, camposFormateados);

		rellenarDatosArrendatario(tramite, camposPlantilla, camposFormateados, TipoInterviniente.Arrendatario.getValorEnum());

		rellenarDatosConductorHabitual(tramite, camposPlantilla, camposFormateados, TipoInterviniente.ConductorHabitual.getValorEnum());

		rellenarResultadoMatricula(tramite, camposPlantilla, camposFormateados);

		rellenarInformacionAdicional(tramite, camposPlantilla, camposFormateados);

		rellenarDatosFinal(camposPlantilla, camposFormateados, datosColegiado);

		rellenarMargenes(tramite, camposPlantilla, camposFormateados, datosColegiado, pdf);

		return camposFormateados;
	}

	private byte[] etiquetas(TramiteTrafMatrVO tramite, byte[] bytePdf, PdfMaker pdf, PersonaVO datosColegiado, ContratoVO contrato, boolean borrador, ResultadoImpresionBean resultado) {
		int[] vectPags = new int[ConstantesPDF._1];
		vectPags[0] = ConstantesPDF._1;

		if (borrador) {
			pdf.establecerFuente(ConstantesPDF.HELVETICA, true, true, ConstantesPDF._26);
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NUBE_PUNTOS, "BORRADOR");
			bytePdf = pdf.setCampo(bytePdf, ConstantesPDF.ID_NUBE_PUNTOS_2, "BORRADOR");
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._13);
		} else {
			bytePdf = marcaAgua(tramite, bytePdf, pdf, vectPags);
			bytePdf = nubes(tramite, bytePdf, pdf, vectPags, resultado);
		}

		bytePdf = insertarFirmaColegio(tramite, bytePdf, pdf, vectPags, datosColegiado, contrato);

		return bytePdf;
	}

	private void rellenarSinCondig(TramiteTrafMatrVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (tramite.getVehiculo() != null && tramite.getVehiculo().getIdServicio() != null) {
			String otrosSinCodig = servicioVehiculo.obtenerSinCodig(tramite.getVehiculo().getIdVehiculo(), tramite.getNumExpediente());
			if (StringUtils.isNotBlank(otrosSinCodig)) {
				String sinCodigTexto = "DIFERENCIAS EN CÓDIGO ITV: " + otrosSinCodig;
				sinCodigTexto = sinCodigTexto.substring(0, sinCodigTexto.lastIndexOf(","));
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_SINCODIG, sinCodigTexto, false, false, ConstantesPDF._10));
			}
		}
	}

	private void rellenarDatosPresentacion(TramiteTrafMatrVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_TRAMITE)) {
			String tipoTramiteMat = TipoTramiteMatriculacion.convertirTexto(tramite.getTipoTramiteMatr());
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRAMITE, tipoTramiteMat != null ? tipoTramiteMat : "", false, false, ConstantesPDF._8));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_NUME_EXPEDIENTE)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUME_EXPEDIENTE, tramite.getNumExpediente() != null ? tramite.getNumExpediente().toString() : "", false, false, ConstantesPDF._10));
		}

		if (tramite.getVehiculo() != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_BASTIDOR_VIN)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_BASTIDOR_VIN, tramite.getVehiculo().getBastidor(), false, false, ConstantesPDF._10));
			}
			if (camposPlantilla.contains(ConstantesPDF.ID_NIVE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NIVE, tramite.getVehiculo().getNive() != null ? tramite.getVehiculo().getNive() : "", false, false, ConstantesPDF._10));
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CEM_MATEW)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CEM_MATEW, tramite.getCem() != null ? tramite.getCem() : "", true, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_IVTM_CEMU_MTW)) {
			if (tramite.getIvtmMatriculacionVO() != null && tramite.getIvtmMatriculacionVO().getNrc() != null) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_IVTM_CEMU_MTW, tramite.getIvtmMatriculacionVO().getNrc(), false, false, ConstantesPDF._10));
			} else {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_IVTM_CEMU_MTW, "DOCUMENTO ESCANEADO EN LA PLATAFORMA", false, false, ConstantesPDF._6));
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CEMA_MTW)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CEMA_MTW, tramite.getCema() != null ? tramite.getCema() : "", false, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_PRESENTACION_MTW)) {
			if (tramite.getFechaPresentacion() != null) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_PRESENTACION_MTW, utilesFecha.formatoFecha(tramite.getFechaPresentacion()), false, false, ConstantesPDF._10));
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_MOTIVO_EXENCION)) {
			String tipoMotivoExencion = TipoMotivoExencion.convertirTexto(tramite.getnRegIedtm());
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MOTIVO_EXENCION, tipoMotivoExencion != null ? tipoMotivoExencion.substring(5, tipoMotivoExencion.length()) : "", false, false,
					ConstantesPDF._8));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_TASA_MATW)) {
			if (tramite.getTasa() != null && tramite.getTasa().getCodigoTasa() != null) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TASA_MATW, tramite.getTasa().getCodigoTasa(), true, false, ConstantesPDF._9));
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_EXENCION_IVTM_MTW)) {
			if (tramite.getJustificadoIvtm() != null && ("SI".equals(tramite.getJustificadoIvtm()) || "S".equals(tramite.getJustificadoIvtm()))) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_EXENCION_IVTM_MTW, ConstantesPDF.VALOR_SI, true, false, ConstantesPDF._8));
			} else {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_EXENCION_IVTM_MTW, ConstantesPDF.VALOR_NO, true, false, ConstantesPDF._8));
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_JEFATURA_TRAFICO_MTW)) {
			if (tramite.getJefaturaTrafico() != null) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_JEFATURA_TRAFICO_MTW, tramite.getJefaturaTrafico().getJefaturaProvincial(), false, false, ConstantesPDF._8));
			}
		}

		if (tramite.getVehiculo() != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_TARJETA_ITV_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TARJETA_ITV_MTW, tramite.getVehiculo().getIdTipoTarjetaItv() != null ? tramite.getVehiculo().getIdTipoTarjetaItv() : "",
						false, false, ConstantesPDF._8));
			}
		}
	}

	private void rellenarDatosTitular(TramiteTrafMatrVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_CAMBIO_DOMICILIO_MTW)) {
				if (interviniente.getCambioDomicilio() != null && ("SI".equals(interviniente.getCambioDomicilio()) || "S".equals(interviniente.getCambioDomicilio()))) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CAMBIO_DOMICILIO_MTW, ConstantesPDF.VALOR_S, true, false, ConstantesPDF._8));
				} else {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CAMBIO_DOMICILIO_MTW, ConstantesPDF.VALOR_N, true, false, ConstantesPDF._8));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_AUTONOMO_TITULAR_MTW)) {
				if (interviniente.getAutonomo() != null && ("SI".equals(interviniente.getAutonomo()) || "S".equals(interviniente.getAutonomo()) || "true".equals(interviniente.getAutonomo()))) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_AUTONOMO_TITULAR_MTW, ConstantesPDF.VALOR_S, false, false, ConstantesPDF._8));
				} else {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_AUTONOMO_TITULAR_MTW, ConstantesPDF.VALOR_N, false, false, ConstantesPDF._8));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_CODIGO_IAE_TITULAR_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CODIGO_IAE_TITULAR_MTW, (interviniente.getCodigoIae() != null ? interviniente.getCodigoIae() : ""), false, false,
						ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR_MTW)) {
				String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
						.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
								.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR_MTW, nombreApellidos, false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR_MTW, interviniente.getId().getNif(), false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_NACIMIENTO_TITULAR_MTW)) {
				if (interviniente.getPersona().getFechaNacimiento() != null) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_NACIMIENTO_TITULAR_MTW, utilesFecha.formatoFecha(interviniente.getPersona().getFechaNacimiento()), false, false,
							ConstantesPDF._10));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_SEXO_TITULAR_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_SEXO_TITULAR_MTW, interviniente.getPersona().getSexo(), false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_COMPLETO_TITULAR_MTW)) {
				if (interviniente.getDireccion() != null) {
					String domicilioCompleto = rellenarDireccion(interviniente.getDireccion());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_COMPLETO_TITULAR_MTW, domicilioCompleto != null ? domicilioCompleto : "", false, false, ConstantesPDF._8));
				}
			}
		}
	}

	private void rellenarDatosRepresentanteTitular(TramiteTrafMatrVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_REPRESENTANTE_TITULAR_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_REPRESENTANTE_TITULAR_MTW, interviniente.getId().getNif(), false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_TUTELA_TITULAR_MTW)) {
				String conceptoRepre = ConceptoTutela.convertirTexto(interviniente.getConceptoRepre());
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TUTELA_TITULAR_MTW, (ConceptoTutela.Tutela.getNombreEnum().equals(conceptoRepre) ? "SI" : "NO"), false, false,
						ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_TUTELA_TITULAR_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TUTELA_TITULAR_MTW, (interviniente.getIdMotivoTutela() != null ? interviniente.getIdMotivoTutela() : ""), false, false,
						ConstantesPDF._8));
			}
		}
	}

	private void rellenarDatosGestor(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, ContratoVO contrato, PersonaVO datosColegiado) {
		if (datosColegiado != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_GESTOR_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_GESTOR_MTW, datosColegiado.getId().getNumColegiado() + " - " + datosColegiado.getNombre() + " " + datosColegiado
						.getApellido1RazonSocial() + " " + datosColegiado.getApellido2(), false, false, ConstantesPDF._9));
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_GESTORIA_MTW)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_GESTORIA_MTW, contrato.getRazonSocial() + " (" + contrato.getColegiado().getUsuario().getNif() + ")", false, false,
					ConstantesPDF._8));
		}
	}

	private void rellenarDatosVehiculo(TramiteTrafMatrVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (tramite.getVehiculo() != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_SUBASTA_MTW)) {
				if (tramite.getVehiculo().getSubastado() != null && ("SI".equals(tramite.getVehiculo().getSubastado()) || "S".equals(tramite.getVehiculo().getSubastado()))) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_SUBASTA_MTW, ConstantesPDF.VALOR_S, true, false, ConstantesPDF._8));
				} else {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_SUBASTA_MTW, ConstantesPDF.VALOR_N, true, false, ConstantesPDF._8));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_USADO_MTW)) {
				if (tramite.getVehiculo().getVehiUsado() != null && ("SI".equals(tramite.getVehiculo().getVehiUsado()) || "S".equals(tramite.getVehiculo().getVehiUsado()))) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_USADO_MTW, ConstantesPDF.VALOR_S, true, false, ConstantesPDF._8));
				} else {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_USADO_MTW, ConstantesPDF.VALOR_N, true, false, ConstantesPDF._8));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_IMPORTADO_MTW)) {
				if (tramite.getVehiculo().getImportado() != null && ("SI".equals(tramite.getVehiculo().getImportado()) || "S".equals(tramite.getVehiculo().getImportado()))) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_IMPORTADO_MTW, ConstantesPDF.VALOR_S, true, false, ConstantesPDF._8));
				} else {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_IMPORTADO_MTW, ConstantesPDF.VALOR_N, true, false, ConstantesPDF._8));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_PAIS_IMPORT_MTW)) {
				if (tramite.getVehiculo().getImportado() != null && ("SI".equals(tramite.getVehiculo().getImportado()) || "S".equals(tramite.getVehiculo().getImportado()))) {
					String procedencia = Procedencia.convertirTexto(tramite.getVehiculo().getProcedencia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PAIS_IMPORT_MTW, procedencia, true, false, ConstantesPDF._8));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_CODIGO_ITV_MTW)) {
				String codItvAux = tramite.getVehiculo().getCoditv();
				if (codItvAux == null || codItvAux.length() > ConstantesPDF.CODIGO_ITV || codItvAux.equals(ConstantesPDF.SIN_CODIG) || codItvAux.equals(ConstantesPDF.SIN_CODIGO)) {
					codItvAux = ConstantesPDF.SIN_CODIGO_MATW;
				}
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CODIGO_ITV_MTW, codItvAux, false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_VEHICULO_MTW)) {
				String tipoVehiculoDes = servicioVehiculo.obtenerTipoVehiculoDescripcion(tramite.getVehiculo().getTipoVehiculo());
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_VEHICULO_MTW, tipoVehiculoDes, false, false, ConstantesPDF._6));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_VEHICULO_IND_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_VEHICULO_IND_MTW, tramite.getVehiculo().getClasificacionItv() != null ? tramite.getVehiculo().getClasificacionItv() : "",
						false, false, ConstantesPDF._6));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_CATEGORIA_EU_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CATEGORIA_EU_MTW, tramite.getVehiculo().getIdDirectivaCee(), false, false, ConstantesPDF._10));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_CARROCERIA_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CARROCERIA_MTW, tramite.getVehiculo().getCarroceria(), false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_MARCA_MTW)) {
				String marca = servicioVehiculo.obtenerNombreMarca(tramite.getVehiculo().getCodigoMarca(), true);
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MARCA_MTW, marca, false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_MARCA_BASE_MTW)) {
				String marca = servicioVehiculo.obtenerNombreMarca(tramite.getVehiculo().getCodigoMarcaBase(), true);
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MARCA_BASE_MTW, marca, false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_FABRICANTE_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FABRICANTE_MTW, tramite.getVehiculo().getFabricante(), false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_FABRICANTE_BASE_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FABRICANTE_BASE_MTW, tramite.getVehiculo().getFabricanteBase(), false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_MODELO_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MODELO_MTW, tramite.getVehiculo().getModelo(), false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_SERVICIO_MTW)) {
				String servicioDes = servicioVehiculo.obtenerServicioDescripcion(tramite.getVehiculo().getIdServicio());
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_SERVICIO_MTW, servicioDes, false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.FECHA_CADUCIDAD_ITV)) {
				if (tramite.getVehiculo().getFechaItv() != null) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.FECHA_CADUCIDAD_ITV, utilesFecha.formatoFecha(tramite.getVehiculo().getFechaItv()), false, false, ConstantesPDF._10));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_VARIANTE_VERSION_MTW)) {
				String tipoVarianteVersion = "";
				tipoVarianteVersion += tramite.getVehiculo().getTipoItv() != null ? tramite.getVehiculo().getTipoItv() + " / " : "";
				tipoVarianteVersion += tramite.getVehiculo().getVariante() != null ? tramite.getVehiculo().getVariante() + " / " : "";
				tipoVarianteVersion += tramite.getVehiculo().getVersion() != null ? tramite.getVehiculo().getVersion() : "";
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_VARIANTE_VERSION_MTW, tipoVarianteVersion, false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_VARIANTE_VERSION_BASE_MTW)) {
				String tipoVarianteVersionBase = "";
				tipoVarianteVersionBase += tramite.getVehiculo().getTipoBase() != null ? tramite.getVehiculo().getTipoBase() + " / " : "";
				tipoVarianteVersionBase += tramite.getVehiculo().getVarianteBase() != null ? tramite.getVehiculo().getVarianteBase() + " / " : "";
				tipoVarianteVersionBase += tramite.getVehiculo().getVersionBase() != null ? tramite.getVehiculo().getVersionBase() : "";
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_VARIANTE_VERSION_BASE_MTW, tipoVarianteVersionBase, false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_TARA_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TARA_MTW, tramite.getVehiculo().getTara(), false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_MASA_SERVICIO_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MASA_SERVICIO_MTW, tramite.getVehiculo().getMom() != null ? tramite.getVehiculo().getMom().toString() : "", false, false,
						ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_MASA_SERVICIO_BASE_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MASA_SERVICIO_BASE_MTW, tramite.getVehiculo().getMomBase() != null ? tramite.getVehiculo().getMomBase().toString() : "", false,
						false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_MASA_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MASA_MTW, tramite.getVehiculo().getPesoMma(), false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_MASA_MAX_MTW)) {
				String mtma = tramite.getVehiculo().getMtmaItv();
				if (null == mtma || "".equals(mtma)) {
					mtma = "0";
				}
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MASA_MAX_MTW, mtma, false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NUMERO_PLAZAS_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUMERO_PLAZAS_MTW, tramite.getVehiculo().getPlazas() != null ? tramite.getVehiculo().getPlazas().toString() : "", false, false,
						ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_PLAZAS_PIE_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PLAZAS_PIE_MTW, tramite.getVehiculo().getnPlazasPie() != null ? tramite.getVehiculo().getnPlazasPie().toString() : "0", false,
						false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DIST_EJES_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DIST_EJES_MTW, tramite.getVehiculo().getDistanciaEjes() != null ? tramite.getVehiculo().getDistanciaEjes().toString() : "",
						false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_VIA_ANT_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_VIA_ANT_MTW, tramite.getVehiculo().getViaAnterior() != null ? tramite.getVehiculo().getViaAnterior().toString() : "", false,
						false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_VIA_POS_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_VIA_POS_MTW, tramite.getVehiculo().getViaPosterior() != null ? tramite.getVehiculo().getViaPosterior().toString() : "", false,
						false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NUM_HOMOLOGACION_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUM_HOMOLOGACION_MTW, tramite.getVehiculo().getnHomologacion(), false, false, ConstantesPDF._7));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NUM_HOMOLOGACION_BASE_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUM_HOMOLOGACION_BASE_MTW, tramite.getVehiculo().getnHomologacionBase(), false, false, ConstantesPDF._7));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_CILINDRADA_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CILINDRADA_MTW, tramite.getVehiculo().getCilindrada() != null ? tramite.getVehiculo().getCilindrada() : "", false, false,
						ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_POTENCIA_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_POTENCIA_MTW, tramite.getVehiculo().getPotenciaFiscal() != null ? utilidadesConversiones.stringToBigDecimalDosDecimales(
						utilidadesConversiones.bigDecimalToStringDosDecimales(tramite.getVehiculo().getPotenciaFiscal(), ConstantesPDF._13)).toString() : "", false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_POTENCIA_MAX_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_POTENCIA_MAX_MTW, tramite.getVehiculo().getPotenciaNeta() != null ? utilidadesConversiones.stringToBigDecimalTresDecimales(
						utilidadesConversiones.bigDecimalToStringTresDecimales(tramite.getVehiculo().getPotenciaNeta(), ConstantesPDF._13)).toString() : "", false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_RELACION_POTENCIA_PESO_MTW)) {
				if (tramite.getVehiculo().getTipoVehiculo() != null && (tramite.getVehiculo().getTipoVehiculo().equals("50") || tramite.getVehiculo().getTipoVehiculo().equals("51") || tramite
						.getVehiculo().getTipoVehiculo().equals("52") || tramite.getVehiculo().getTipoVehiculo().equals("53") || tramite.getVehiculo().getTipoVehiculo().equals("54") || tramite
								.getVehiculo().getTipoVehiculo().equals("90") || tramite.getVehiculo().getTipoVehiculo().equals("91") || tramite.getVehiculo().getTipoVehiculo().equals("92"))) {
					if (tramite.getVehiculo().getPotenciaPeso() != null) {
						String parteEntera = "";
						String parteDecimal = "";
						String tcamp = "";

						BigDecimal relacionString = tramite.getVehiculo().getPotenciaPeso().setScale(4, BigDecimal.ROUND_DOWN);

						if (relacionString.toString().indexOf('.') != -1) {
							parteEntera = relacionString.toString().substring(0, relacionString.toString().indexOf('.'));
						} else {
							parteEntera = relacionString.toString();
						}

						parteEntera = utilidadesConversiones.rellenarCeros(parteEntera, 2);
						parteDecimal = relacionString.toString().substring(relacionString.toString().indexOf('.') + 1);
						tcamp = parteEntera + '.' + parteDecimal;
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_RELACION_POTENCIA_PESO_MTW, tcamp, false, false, ConstantesPDF._8));
					}
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_CONSUMO_MTW)) {
				String campoConsumo = "";
				if (tramite.getVehiculo().getConsumo() != null) {
					BigDecimal consumo = tramite.getVehiculo().getConsumo();
					String property = gestorPropiedades.valorPropertie(ConstantesPDF.PROPERTY_COMPLETA_CEROS_ELECTRICOS);
					if (property != null && property.equals(ConstantesPDF.VALOR_CIERTO_PROPERTY_COMPLETA_CEROS_ELECTRICOS)) {
						DecimalFormat formato4 = new DecimalFormat("#0000");
						campoConsumo = formato4.format(consumo);
					} else {
						campoConsumo = consumo.toString();
					}
				}
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CONSUMO_MTW, campoConsumo, false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_FABRICACION_MTW)) {
				String fabr = "";
				if (tramite.getVehiculo().getPaisFabricacion() != null) {
					fabr = PaisFabricacion.convertirPaisFabricacionBeanEnumerado(tramite.getVehiculo().getPaisFabricacion());
				}
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FABRICACION_MTW, fabr, false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_CARBURANTE_MTW)) {
				if (tramite.getVehiculo().getIdCarburante() != null) {
					String combustible = Combustible.convertirAString(tramite.getVehiculo().getIdCarburante());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CARBURANTE_MTW, combustible, false, false, ConstantesPDF._8));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_ALIMENTACION_MTW)) {
				String alimentacion = Alimentacion.convertirTexto(tramite.getVehiculo().getTipoAlimentacion());
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_ALIMENTACION_MTW, alimentacion != null ? alimentacion : "", true, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_CATEGORIA_ELECTRICA_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CATEGORIA_ELECTRICA_MTW, tramite.getVehiculo().getCategoriaElectrica() != null ? tramite.getVehiculo().getCategoriaElectrica()
						: "", true, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_CODIGO_ECO_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CODIGO_ECO_MTW, tramite.getVehiculo().getCodigoEco() != null ? tramite.getVehiculo().getCodigoEco() : "", true, false,
						ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_REDUCCION_ECO_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_REDUCCION_ECO_MTW, tramite.getVehiculo().getReduccionEco() != null ? tramite.getVehiculo().getCodigoEco() : "", true, false,
						ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_ECO_INNOVACION_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ECO_INNOVACION_MTW, tramite.getVehiculo().getEcoInnovacion() != null ? tramite.getVehiculo().getCodigoEco() : "", true, false,
						ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_AUTONOMIA_ELECTRICA_MTW)) {
				String autonomiaElectrica = tramite.getVehiculo().getAutonomiaElectrica() != null ? tramite.getVehiculo().getAutonomiaElectrica().toString() : "";
				String property = gestorPropiedades.valorPropertie(ConstantesPDF.PROPERTY_COMPLETA_CEROS_ELECTRICOS);
				if (property != null && property.equals(ConstantesPDF.VALOR_CIERTO_PROPERTY_COMPLETA_CEROS_ELECTRICOS)) {
					DecimalFormat formato4 = new DecimalFormat("#0000");
					if (autonomiaElectrica != null && !autonomiaElectrica.isEmpty()) {
						autonomiaElectrica = formato4.format(new BigDecimal(autonomiaElectrica));
					}
				}
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_AUTONOMIA_ELECTRICA_MTW, autonomiaElectrica, true, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_CO2_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CO2_MTW, tramite.getVehiculo().getCo2(), false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NIVEL_EMISIONES_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NIVEL_EMISIONES_MTW, tramite.getVehiculo().getNivelEmisiones() != null ? tramite.getVehiculo().getNivelEmisiones().toString()
						: "", false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_COLOR_MTW)) {
				String color = Color.convertirTexto(tramite.getVehiculo().getIdColor());
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_COLOR_MTW, color, false, false, ConstantesPDF._7));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_PRIMERA_MATR_MTW)) {
				if (tramite.getVehiculo().getFechaPrimMatri() != null) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_PRIMERA_MATR_MTW, utilesFecha.formatoFecha(tramite.getVehiculo().getFechaPrimMatri()), false, false,
							ConstantesPDF._10));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.MATRICULA_ORIGEN_MTW)) {
				if (tramite.getVehiculo().getMatriculaOrigen() != null) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.MATRICULA_ORIGEN_MTW, "", false, false, ConstantesPDF._8));
				} else {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.MATRICULA_ORIGEN_MTW, tramite.getVehiculo().getMatriculaOrigExtr() != null ? tramite.getVehiculo().getMatriculaOrigExtr() : "",
							false, false, ConstantesPDF._8));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.TIPO_INSPECCION_ITV)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.TIPO_INSPECCION_ITV, tramite.getVehiculo().getIdMotivoItv() != null ? TiposInspeccionItv.getDescripcion(tramite.getVehiculo()
						.getIdMotivoItv()) : "", false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.KM_ITV_MTW)) {
				if (tramite.getVehiculo().getKmUso() != null) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.KM_ITV_MTW, tramite.getVehiculo().getKmUso().toString(), false, false, ConstantesPDF._8));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.HORAS_ITV_MTW)) {
				if (tramite.getVehiculo().getHorasUso() != null) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.HORAS_ITV_MTW, tramite.getVehiculo().getHorasUso().toString(), false, false, ConstantesPDF._8));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_COMPLETO_MTW_VEHICULO)) {
				if (tramite.getVehiculo().getDireccion() != null) {
					String domicilioCompleto = rellenarDireccion(tramite.getVehiculo().getDireccion());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_COMPLETO_MTW_VEHICULO, domicilioCompleto != null ? domicilioCompleto : "", false, false, ConstantesPDF._8));
				} else {
					IntervinienteTraficoVO titular = buscarInterviniente(tramite, TipoInterviniente.Titular.getValorEnum());
					if (titular != null && titular.getDireccion() != null) {
						String domicilioCompleto = rellenarDireccion(titular.getDireccion());
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_COMPLETO_MTW_VEHICULO, domicilioCompleto != null ? domicilioCompleto : "", false, false, ConstantesPDF._8));
					}
				}
			}
		}
	}

	private void rellenarDatosArrendatario(TramiteTrafMatrVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_CAMBIO_DOMICILIO_ARRENDATARIO_MTW)) {
				if (interviniente.getCambioDomicilio() != null && ("SI".equals(interviniente.getCambioDomicilio()) || "S".equals(interviniente.getCambioDomicilio()))) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CAMBIO_DOMICILIO_ARRENDATARIO_MTW, ConstantesPDF.VALOR_S, true, false, ConstantesPDF._8));
				} else {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CAMBIO_DOMICILIO_ARRENDATARIO_MTW, ConstantesPDF.VALOR_N, true, false, ConstantesPDF._8));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_ARRENDATARIO_MTW)) {
				String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
						.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
								.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_ARRENDATARIO_MTW, nombreApellidos, false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_ARRENDATARIO_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_ARRENDATARIO_MTW, interviniente.getId().getNif(), false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_NACIMIENTO_ARRENDATARIO_MTW)) {
				if (interviniente.getPersona().getFechaNacimiento() != null) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_NACIMIENTO_ARRENDATARIO_MTW, utilesFecha.formatoFecha(interviniente.getPersona().getFechaNacimiento()), false, false,
							ConstantesPDF._8));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_SEXO_ARRENDATARIO_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_SEXO_ARRENDATARIO_MTW, interviniente.getPersona().getSexo(), false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_HORA_INICIO_ARRENDATARIO_MTW)) {
				String fechaHoraInicio = interviniente.getFechaInicio() != null ? utilesFecha.formatoFecha(interviniente.getFechaInicio()) + "  " : "";
				fechaHoraInicio += interviniente.getHoraInicio() != null ? interviniente.getHoraInicio() : "";
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_HORA_INICIO_ARRENDATARIO_MTW, fechaHoraInicio, false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_HORA_FIN_ARRENDATARIO_MTW)) {
				String fechaHoraFin = interviniente.getFechaFin() != null ? utilesFecha.formatoFecha(interviniente.getFechaFin()) + "  " : "";
				fechaHoraFin += interviniente.getHoraFin() != null ? interviniente.getHoraFin() : "";
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_HORA_FIN_ARRENDATARIO_MTW, fechaHoraFin, false, false, ConstantesPDF._8));
			}

			rellenarDatosRepresentanteArrendatario(tramite, camposPlantilla, camposFormateados, TipoInterviniente.RepresentanteArrendatario.getValorEnum());

			if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_COMPLETO_ARRENDATARIO_MTW)) {
				if (interviniente.getDireccion() != null) {
					String domicilioCompleto = rellenarDireccion(interviniente.getDireccion());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_COMPLETO_ARRENDATARIO_MTW, domicilioCompleto != null ? domicilioCompleto : "", false, false, ConstantesPDF._8));
				}
			}
		}
	}

	private void rellenarDatosRepresentanteArrendatario(TramiteTrafMatrVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_REPRESENTANTE_ARRENDATARIO)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_REPRESENTANTE_ARRENDATARIO, interviniente.getId().getNif(), false, false, ConstantesPDF._8));
			}
		}
	}

	private void rellenarDatosConductorHabitual(TramiteTrafMatrVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_CAMBIO_DOMICILIO_CONDUCTOR_HABITUAL_MTW)) {
				if (interviniente.getCambioDomicilio() != null && ("SI".equals(interviniente.getCambioDomicilio()) || "S".equals(interviniente.getCambioDomicilio()))) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CAMBIO_DOMICILIO_CONDUCTOR_HABITUAL_MTW, ConstantesPDF.VALOR_S, true, false, ConstantesPDF._8));
				} else {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CAMBIO_DOMICILIO_CONDUCTOR_HABITUAL_MTW, ConstantesPDF.VALOR_N, true, false, ConstantesPDF._8));
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_CONDUCTOR_HABITUAL_MTW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_CONDUCTOR_HABITUAL_MTW, interviniente.getId().getNif(), false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_HORA_FIN_CONDUCTOR_HABITUAL_MTW)) {
				String fechaHoraFin = interviniente.getFechaFin() != null ? utilesFecha.formatoFecha(interviniente.getFechaFin()) + "  " : "";
				fechaHoraFin += interviniente.getHoraFin() != null ? interviniente.getHoraFin() : "";
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_HORA_FIN_CONDUCTOR_HABITUAL_MTW, fechaHoraFin, false, false, ConstantesPDF._8));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_COMPLETO_CONDUCTOR_HABITUAL_MTW)) {
				if (interviniente.getDireccion() != null) {
					String domicilioCompleto = rellenarDireccion(interviniente.getDireccion());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_COMPLETO_CONDUCTOR_HABITUAL_MTW, domicilioCompleto != null ? domicilioCompleto : "", false, false,
							ConstantesPDF._8));
				}
			}
		}
	}

	private void rellenarResultadoMatricula(TramiteTrafMatrVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (tramite.getVehiculo() != null && tramite.getVehiculo().getMatricula() != null) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_RESULTADO_MATW, "MATRICULA: " + tramite.getVehiculo().getMatricula(), true, false, ConstantesPDF._10));
		}
	}

	private void rellenarInformacionAdicional(TramiteTrafMatrVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (camposPlantilla.contains(ConstantesPDF.ID_INFORMACION_MTW)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_INFORMACION_MTW, tramite.getAnotaciones(), false, false, ConstantesPDF._8));
		}
	}

	private void rellenarDatosFinal(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, PersonaVO datosColegiado) {
		if (datosColegiado != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_GESTOR_MATW)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_GESTOR_MATW, datosColegiado.getNombre() + " " + datosColegiado.getApellido1RazonSocial() + " " + datosColegiado
						.getApellido2(), true, false, ConstantesPDF._9));
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_ACTUAL_MATW)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_ACTUAL_MATW, utilesFecha.getFechaHoy(), false, false, ConstantesPDF._11));
		}
	}

	private byte[] insertarFirmaColegio(TramiteTrafMatrVO tramite, byte[] bytePdf, PdfMaker pdf, int[] vectPags, PersonaVO datosColegiado, ContratoVO contrato) {
		String fechaDeHoy = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		IntervinienteTraficoVO titular = buscarInterviniente(tramite, TipoInterviniente.Titular.getValorEnum());

		String firmaColegio = contrato.getCif() + "#" + (tramite.getVehiculo() != null && StringUtils.isNotBlank(tramite.getVehiculo().getBastidor()) ? tramite.getVehiculo().getBastidor() : "") + "#"
				+ (titular != null ? titular.getId().getNif() : "") + "#" + datosColegiado.getId().getNif() + "#" + contrato.getColegio().getCif() + "#" + fechaDeHoy;

		firmaColegio = utilidadesConversiones.cifrarHMACSHA1(firmaColegio, "ClaveCifrado");
		firmaColegio = utilidadesConversiones.stringToHex(firmaColegio);
		firmaColegio = firmaColegio.substring(0, ConstantesPDF._16);

		List<FieldPosition> posiciones = pdf.getFieldPosition(bytePdf, ConstantesPDF.ID_FIRMA_COLEGIO);
		FieldPosition posicion = posiciones.get(0);
		bytePdf = pdf.crearCodigoBarras128ConTexto(firmaColegio, bytePdf, -ConstantesPDF._1, ConstantesPDF._12, -ConstantesPDF._1, true, posicion.position.getLeft(), posicion.position.getBottom());

		return bytePdf;
	}

	private void rellenarMargenes(TramiteTrafMatrVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, PersonaVO datosColegiado, PdfMaker pdf) {
		if (camposPlantilla.contains(ConstantesPDF.ID_ERRORES_MATE)) {
			if (!new BigDecimal(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente
					.getValorEnum()).equals(tramite.getEstado()) && !new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()).equals(tramite.getEstado())
					&& !new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()).equals(tramite.getEstado())) {
				if (StringUtils.isNotBlank(tramite.getRespuesta())) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ERRORES_MATE, "NO MATRICULABLE TELEMÁTICAMENTE: " + tramite.getRespuesta(), true, false, ConstantesPDF._10));
				}
				pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._13);
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_EXENCION)) {
			String mensajeExencion = "";
			if (StringUtils.isNotBlank(tramite.getIdReduccion05()) && !"-1".equals(tramite.getIdReduccion05())) {
				String reduccionNoSujeccionOExencion05 = ReduccionNoSujeccionOExencion05.convertirTexto(tramite.getIdReduccion05());
				mensajeExencion = "MODELO 05 - " + reduccionNoSujeccionOExencion05;
			} else if (StringUtils.isNotBlank(tramite.getIdNoSujeccion06()) && !"-1".equals(tramite.getIdNoSujeccion06())) {
				String noSujeccionOExencion = NoSujeccionOExencion.convertirTexto(tramite.getIdNoSujeccion06());
				mensajeExencion = "MODELO 06 - " + noSujeccionOExencion;
			} else if (tramite.getReduccion576() != null && "SI".equals(tramite.getReduccion576().toUpperCase())) {
				mensajeExencion = "MODELO 576 - " + TiposReduccion576.FamiliaNumerosa.getNombreEnum();
			} else if (StringUtils.isNotBlank(tramite.getIedtm()) && !"-1".equals(tramite.getIedtm())) {
				mensajeExencion = "NO TIENE MODELO 576";
			} else {
				mensajeExencion = "MODELO 576";
			}

			if (StringUtils.isNotBlank(mensajeExencion)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_EXENCION, mensajeExencion, true, false, ConstantesPDF._10));
			}
		}
	}

	private String rellenarDireccion(DireccionVO direccion) {
		String domicilioCompleto = "";

		if (direccion.getIdTipoVia() != null) {
			String tipoViaNombre = servicioDireccion.obtenerNombreTipoVia(direccion.getIdTipoVia());
			domicilioCompleto = tipoViaNombre;
		}

		if (StringUtils.isNotBlank(direccion.getNombreVia())) {
			domicilioCompleto += " " + utilidadesConversiones.ajustarCamposIne(direccion.getNombreVia());
		}

		if (StringUtils.isNotBlank(direccion.getNumero())) {
			domicilioCompleto += ", " + direccion.getNumero();
		}

		if (StringUtils.isNotBlank(direccion.getLetra())) {
			domicilioCompleto += ", puerta " + direccion.getLetra();
		}

		if (StringUtils.isNotBlank(direccion.getEscalera())) {
			domicilioCompleto += ", esc. " + direccion.getEscalera();
		}

		if (StringUtils.isNotBlank(direccion.getPlanta())) {
			domicilioCompleto += ", planta " + direccion.getPlanta();
		}

		if (StringUtils.isNotBlank(direccion.getPuerta())) {
			domicilioCompleto += ", portal " + direccion.getPuerta();
		}

		if (StringUtils.isNotBlank(direccion.getBloque())) {
			domicilioCompleto += ", blq. " + direccion.getBloque();
		}

		if (direccion.getKm() != null) {
			domicilioCompleto += ", km. " + direccion.getKm();
		}

		if (direccion.getHm() != null) {
			domicilioCompleto += ", hm. " + direccion.getHm();
		}

		if (StringUtils.isNotBlank(direccion.getCodPostalCorreos())) {
			domicilioCompleto += ", CP. " + direccion.getCodPostalCorreos();
		}

		if (StringUtils.isNotBlank(direccion.getPuebloCorreos())) {
			domicilioCompleto += ", " + direccion.getPuebloCorreos();
		}

		if (direccion.getIdProvincia() != null) {
			if (direccion.getIdMunicipio() != null) {
				String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(direccion.getIdMunicipio(), direccion.getIdProvincia());
				domicilioCompleto += ", " + utilidadesConversiones.ajustarCamposIne(nombreMunicipio);
			}
			String nombreProvincia = servicioDireccion.obtenerNombreProvincia(direccion.getIdProvincia());
			domicilioCompleto += ", " + nombreProvincia;
		}
		return domicilioCompleto;
	}

	private byte[] marcaAgua(TramiteTrafMatrVO tramite, byte[] bytePdf, PdfMaker pdf, int[] vectPags) {
		String rutaJpg = ConstantesPDF.RUTA_MARCA_AGUA;
		if (tramite.getVehiculo() != null) {
			if (tramite.getVehiculo().getIdCarburante() != null && tramite.getVehiculo().getIdCarburante().equals("E")) {
				rutaJpg = ConstantesPDF.RUTA_MARCA_AGUA_VEHICULO_ELECTRICO;
			} else if (comprobarDirectivaCeeFinSerie(tramite.getVehiculo())) {
				rutaJpg = ConstantesPDF.RUTA_MARCA_AGUA_FIN_SERIE;
			}
		}
		String rutaMarcaAgua = getRutaMarcaAgua(rutaJpg);
		Image img = pdf.cargarImagen(rutaMarcaAgua);
		bytePdf = pdf.insertarMarcaDeAgua(bytePdf, img, vectPags, ConstantesPDF._110, ConstantesPDF._250, ConstantesPDF._45);
		return bytePdf;
	}

	private byte[] nubes(TramiteTrafMatrVO tramite, byte[] bytePdf, PdfMaker pdf, int[] vectPags, ResultadoImpresionBean resultado) {
		String nube1 = nube1(tramite);
		bytePdf = insertarNubePuntosMatw(nube1, ConstantesPDF.ID_NUBE_PUNTOS, bytePdf, pdf, vectPags);
		String nube2 = nube2(tramite);
		if (StringUtils.isNotBlank(nube2)) {
			bytePdf = insertarNubePuntosMatw(nube2, ConstantesPDF.ID_NUBE_PUNTOS_2, bytePdf, pdf, vectPags);
		}
		resultado.setNube(nube1 + nube2);
		return bytePdf;
	}

	private String nube1(TramiteTrafMatrVO tramite) {
		String nube = obtenerNubeMatriculacionMatw(tramite, 0, ConstantesPDF.POS_INICIO_NUBE2);
		nube = utilesNube.sustituyeCaracteres_MATW(nube);
		return nube;
	}

	private String nube2(TramiteTrafMatrVO tramite) {
		String nube = "";
		IntervinienteTraficoVO conductorHabitual = buscarInterviniente(tramite, TipoInterviniente.ConductorHabitual.getValorEnum());
		if ("SI".equals(tramite.getRenting()) || conductorHabitual != null) {
			nube = obtenerNubeMatriculacionMatw(tramite, ConstantesPDF.POS_INICIO_NUBE2, ConstantesPDF.POS_FIN_NUBE2);
			nube = utilesNube.sustituyeCaracteres_MATW(nube);
		}
		return nube;
	}

	private byte[] insertarNubePuntosMatw(String nube, String tipoNube, byte[] bytePdf, PdfMaker pdf, int[] vectPags) {
		List<FieldPosition> posiciones = pdf.getFieldPosition(bytePdf, tipoNube);
		FieldPosition posicion = posiciones.get(0);
		Image img = pdf.crearNubePuntos(nube);
		img = pdf.escalarImagen(img, posicion.position.getWidth(), posicion.position.getHeight());
		return pdf.insertarImagen(bytePdf, img, vectPags, posicion.position.getLeft(), posicion.position.getBottom());
	}

	private String obtenerNubeMatriculacionMatw(TramiteTrafMatrVO tramite, int manchaInicio, int manchaFin) {
		StringBuffer line = new StringBuffer();
		String ncampo = "";
		String campoNube = "";

		utilesNube.inicializarLongitudCamposNubeMatw();

		for (int i = manchaInicio; i < manchaFin; i++) {
			ncampo = utilesNube.getCampoValorArray(i);
			campoNube = utilesNube.obtenerValorCampoNubeMatw(ncampo, tramite);
			if (campoNube != null) {
				line.append(utilesNube.changeSize(campoNube, utilesNube.getLongitudCampoNube(ncampo)));
			}
		}
		return line.toString();
	}

	private IntervinienteTraficoVO buscarInterviniente(TramiteTrafMatrVO tramite, String tipoInterviniente) {
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

	private boolean comprobarDirectivaCeeFinSerie(VehiculoVO vehiculo) {
		String reglal8 = "L1e.*";
		String reglal9 = "L2e.*";
		String reglal10 = "L6e.*";
		String reglal3 = "L3e.*";
		String reglal4 = "L4e.*";
		String reglal5 = "L5e.*";
		String reglal7 = "L7e.*";
		if (vehiculo.getIdDirectivaCee() != null && !vehiculo.getIdDirectivaCee().isEmpty()) {
			if (vehiculo.getIdDirectivaCee().matches(reglal3) || vehiculo.getIdDirectivaCee().matches(reglal4) || vehiculo.getIdDirectivaCee().matches(reglal5) || vehiculo.getIdDirectivaCee().matches(
					reglal7) || vehiculo.getIdDirectivaCee().matches(reglal8) || vehiculo.getIdDirectivaCee().matches(reglal9) || vehiculo.getIdDirectivaCee().matches(reglal10)) {

				if (vehiculo.getNivelEmisiones() == null || vehiculo.getNivelEmisiones().isEmpty()) {
					return true;
				} else if (vehiculo.getNivelEmisiones().contains("EURO0") || vehiculo.getNivelEmisiones().contains("EURO1") || vehiculo.getNivelEmisiones().contains("EURO2") || vehiculo
						.getNivelEmisiones().contains("EURO3") || vehiculo.getNivelEmisiones().contains("EURO 0") || vehiculo.getNivelEmisiones().contains("EURO 1") || vehiculo.getNivelEmisiones()
								.contains("EURO 2") || vehiculo.getNivelEmisiones().contains("EURO 3") || vehiculo.getNivelEmisiones().contains("EURO I") || vehiculo.getNivelEmisiones().contains(
										"EURO II") || vehiculo.getNivelEmisiones().contains("EURO III") || vehiculo.getNivelEmisiones().contains("EUROI") || vehiculo.getNivelEmisiones().contains(
												"EUROII") || vehiculo.getNivelEmisiones().contains("EUROIII")) {
					String regla1 = "EUROIV.*";
					String regla2 = "EURO IV.*";
					if (!vehiculo.getNivelEmisiones().matches(regla1) && !vehiculo.getNivelEmisiones().matches(regla2)) {
						return true;
					} else {
						return false;
					}
				} else if ((vehiculo.getTipoVehiculo().equals("90") || vehiculo.getTipoVehiculo().equals("91") || vehiculo.getTipoVehiculo().equals("92") || vehiculo.getTipoVehiculo().equals("54")
						|| vehiculo.getTipoVehiculo().equals("50") || vehiculo.getTipoVehiculo().equals("51")) && !vehiculo.getNivelEmisiones().contains("EURO")) {
					return true;
				}
			}
		}
		return false;
	}

	private String getRuta() {
		URL ruta = this.getClass().getResource(gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS) + TipoImpreso.MatriculacionPDFPresentacionTelematica_MATEW.getNombreEnum());
		return ruta.getFile();
	}

	private String getRutaMarcaAgua(String rutaJpg) {
		URL ruta = this.getClass().getResource(gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS) + rutaJpg);
		return ruta.getFile();
	}
}
