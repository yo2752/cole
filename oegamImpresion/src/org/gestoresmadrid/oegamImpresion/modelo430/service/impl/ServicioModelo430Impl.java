package org.gestoresmadrid.oegamImpresion.modelo430.service.impl;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.impresion.model.vo.ImpresionVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.persona.service.ServicioComunPersona;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.vehiculo.service.ServicioComunVehiculo;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.modelo430.service.ServicioModelo430;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionDocumentos;
import org.gestoresmadrid.oegamImpresion.service.ServicioImpresionTramiteTrafico;
import org.gestoresmadrid.oegamImpresion.utiles.CampoPdfBean;
import org.gestoresmadrid.oegamImpresion.utiles.ConstantesPDF;
import org.gestoresmadrid.oegamImpresion.utiles.PdfMaker;
import org.gestoresmadrid.oegamImpresion.utilidades.UtilidadesConversiones;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioModelo430Impl implements ServicioModelo430 {

	private static final long serialVersionUID = 2626139116309701886L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioModelo430Impl.class);

	@Autowired
	ServicioImpresionDocumentos servicioImpresion;

	@Autowired
	ServicioImpresionTramiteTrafico servicioImpresionTramiteTrafico;

	@Autowired
	ServicioComunTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioComunPersona servicioPersona;

	@Autowired
	ServicioComunVehiculo servicioVehiculo;

	@Autowired
	ServicioComunDireccion servicioDireccion;

	@Autowired
	UtilidadesConversiones utilidadesConversiones;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Override
	public ResultadoImpresionBean imprimirModelo430(Long idImpresion) {
		ResultadoImpresionBean resultadoImpr = new ResultadoImpresionBean(Boolean.FALSE);
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			ImpresionVO impresion = servicioImpresion.getDatosImpresion(idImpresion);
			if (impresion != null && StringUtils.isNotBlank(impresion.getTipoTramite())) {
				List<BigDecimal> numExpedientes = servicioImpresionTramiteTrafico.obtenerNumExpedientes(idImpresion);
				if (numExpedientes != null && !numExpedientes.isEmpty()) {
					if (TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(impresion.getTipoTramite())) {
						resultadoImpr = imprimirModelo430(numExpedientes);
					} else {
						resultadoImpr.setError(Boolean.TRUE);
						resultadoImpr.setMensaje("El tipo trámite para la impresión no es el correcto");
					}
					resultadoImpr.setListaTramites(numExpedientes);
					if (resultadoImpr != null && !resultadoImpr.getError()) {
						resultado = servicioImpresion.prepararFinalizarImpresion(numExpedientes, resultadoImpr, getTipoDocumento(), impresion.getContrato(), impresion.getTipoTramite(), impresion
								.getNombreDocumento(), false);
						if (resultadoImpr != null && !resultadoImpr.getError()) {
							resultado = servicioImpresion.finalizarImpresion(idImpresion, resultadoImpr, getTipoDocumento(), impresion.getIdUsuario(), impresion.getContrato(), impresion
									.getTipoTramite(), impresion.getNombreDocumento());
						}
					}
				} else {
					resultadoImpr.setError(Boolean.TRUE);
					resultadoImpr.setMensaje("No existe números de expedientes a imprimir");
				}
				if (resultado != null && resultado.getError()) {
					resultadoImpr.setError(Boolean.TRUE);
					resultadoImpr.setMensaje("No se ha podido guardar el fichero generado en la impresión");
				}
				resultadoImpr.setTipoTramite(impresion.getTipoTramite());
				resultadoImpr.setTipoDocumento(impresion.getTipoDocumento());
				resultadoImpr.setNombreDocumento(impresion.getNombreDocumento());
			} else {
				resultadoImpr.setError(Boolean.TRUE);
				resultadoImpr.setMensaje("No se han encontrado datos de la impresión o no tiene tipo de trámite");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir el Modelo 430, error: ", e);
			resultadoImpr.setError(Boolean.TRUE);
			resultadoImpr.setMensaje("Ha sucedido un error a la hora de imprimir el Modelo 430,.");
		}
		return resultadoImpr;
	}

	private ResultadoImpresionBean imprimirModelo430(List<BigDecimal> listaExpediente) {
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
			log.error("Ha sucedido un error a la hora de imprimir el Modelo 430 de Ctit, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el Modelo 430 de Ctit.");
		}
		return resultado;
	}

	private byte[] imprimir(TramiteTrafTranVO tramite) {
		PdfMaker pdf = new PdfMaker();
		byte[] bytePdf = pdf.abrirPdf(getRuta(tramite));
		Set<String> camposPlantilla = pdf.getAllFields(bytePdf);

		PersonaVO datosColegiado = servicioPersona.obtenerColegiadoCompleto(tramite.getContrato().getColegiado().getNumColegiado(), tramite.getContrato().getColegiado().getUsuario().getNif());

		ArrayList<CampoPdfBean> camposFormateados = rellenarPdf(tramite, camposPlantilla, tramite.getContrato(), datosColegiado, pdf);

		bytePdf = pdf.setCampos(bytePdf, camposFormateados);

		return bytePdf;
	}

	private ArrayList<CampoPdfBean> rellenarPdf(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ContratoVO contrato, PersonaVO datosColegiado, PdfMaker pdf) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();

		rellenarDatosAdquiriente(tramite, camposPlantilla, camposFormateados);

		rellenarDatosTransmitente(tramite, camposPlantilla, camposFormateados, TipoInterviniente.TransmitenteTrafico.getValorEnum());

		rellenarDatosVehiculo(tramite, camposPlantilla, camposFormateados);

		rellenarDatosFinal(tramite, camposPlantilla, camposFormateados);

		return camposFormateados;
	}

	private void rellenarDatosAdquiriente(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		IntervinienteTraficoVO interviniente = null;
		IntervinienteTraficoVO intervinienteRepre = null;
		if (TipoTransferencia.tipo5.getValorEnum().equals(tramite.getTipoTransferencia())) {
			interviniente = buscarInterviniente(tramite, TipoInterviniente.Compraventa.getValorEnum());
			intervinienteRepre = buscarInterviniente(tramite, TipoInterviniente.RepresentanteCompraventa.getValorEnum());
		} else {
			interviniente = buscarInterviniente(tramite, TipoInterviniente.Adquiriente.getValorEnum());
			intervinienteRepre = buscarInterviniente(tramite, TipoInterviniente.RepresentanteAdquiriente.getValorEnum());
		}

		if (intervinienteRepre != null && interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_ADQUIRIENTE)) {
				String nombreApellidos = (intervinienteRepre.getPersona().getNombre() != null ? intervinienteRepre.getPersona().getNombre() : "") + " " + (intervinienteRepre.getPersona()
						.getApellido1RazonSocial() != null ? intervinienteRepre.getPersona().getApellido1RazonSocial() : "") + " " + (intervinienteRepre.getPersona().getApellido2() != null
								? intervinienteRepre.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_ADQUIRIENTE, nombreApellidos, false, false, ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_ADQUIRIENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_ADQUIRIENTE, intervinienteRepre.getId().getNif(), false, false, ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_REPR_ADQUIRIENTE)) {
				String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
						.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
								.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_REPR_ADQUIRIENTE, nombreApellidos, false, false, ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_REPR_ADQUIRIENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_REPR_ADQUIRIENTE, interviniente.getId().getNif(), false, false, ConstantesPDF._9));
			}
		} else if (interviniente != null) {
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

		if (interviniente != null && interviniente.getDireccion() != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_ADQUIRIENTE)) {
				String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(interviniente.getDireccion().getIdMunicipio(), interviniente.getDireccion().getIdProvincia());
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_ADQUIRIENTE, utilidadesConversiones.ajustarCamposIne(nombreMunicipio), false, false, ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_PROVINCIA_ADQUIRIENTE)) {
				String nombreProvincia = servicioDireccion.obtenerNombreProvincia(interviniente.getDireccion().getIdProvincia());
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PROVINCIA_ADQUIRIENTE, nombreProvincia, false, false, ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_CALLE_ADQUIRIENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CALLE_ADQUIRIENTE, utilidadesConversiones.ajustarCamposIne(interviniente.getDireccion().getNombreVia()), false, false,
						ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NUM_DOMICILIO_ADQUIRIENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUM_DOMICILIO_ADQUIRIENTE, interviniente.getDireccion().getNumero(), false, false, ConstantesPDF._9));
			}
		}
	}

	private void rellenarDatosRepreAdquiriente(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);
		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_REPR_ADQUIRIENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_REPR_ADQUIRIENTE, interviniente.getId().getNif(), false, false, ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_REPR_ADQUIRIENTE)) {
				String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
						.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
								.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_REPR_ADQUIRIENTE, nombreApellidos, false, false, ConstantesPDF._9));
			}
		}
	}

	private void rellenarDatosTransmitente(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = buscarInterviniente(tramite, tipoInterviniente);

		if (interviniente != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TRANSMITENTE)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_TRANSMITENTE, interviniente.getId().getNif(), false, false, ConstantesPDF._9));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TRANSMITENTE)) {
				String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
						.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
								.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TRANSMITENTE, nombreApellidos, false, false, ConstantesPDF._9));
			}

			if (interviniente.getDireccion() != null) {
				if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_TRANSMITENTE)) {
					String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(interviniente.getDireccion().getIdMunicipio(), interviniente.getDireccion().getIdProvincia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_TRANSMITENTE, utilidadesConversiones.ajustarCamposIne(nombreMunicipio), false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PROVINCIA_TRANSMITENTE)) {
					String nombreProvincia = servicioDireccion.obtenerNombreProvincia(interviniente.getDireccion().getIdProvincia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PROVINCIA_TRANSMITENTE, nombreProvincia, false, false, ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_CALLE_TRANSMITENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CALLE_TRANSMITENTE, utilidadesConversiones.ajustarCamposIne(interviniente.getDireccion().getNombreVia()), false, false,
							ConstantesPDF._9));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_NUM_DOMICILIO_TRANSMITENTE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUM_DOMICILIO_TRANSMITENTE, interviniente.getDireccion().getNumero(), false, false, ConstantesPDF._9));
				}
			}
		}
	}

	private void rellenarDatosVehiculo(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (tramite.getVehiculo() != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_VEHICULO)) {
				String tipoVehiculo = servicioVehiculo.obtenerTipoVehiculoDescripcion(tramite.getVehiculo().getTipoVehiculo());
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_VEHICULO, tipoVehiculo, false, false, ConstantesPDF._10));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_MATRICULA)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MATRICULA, tramite.getVehiculo().getMatricula(), true, false, ConstantesPDF._10));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_MARCA)) {
				String marca = servicioVehiculo.obtenerNombreMarca(tramite.getVehiculo().getCodigoMarca(), false);
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MARCA, marca, false, false, ConstantesPDF._10));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_MODELO)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MODELO, tramite.getVehiculo().getModelo(), false, false, ConstantesPDF._10));
			}
		}

		rellenarBaseImponible(tramite, camposPlantilla, camposFormateados);
		rellenarPorcentaje(tramite, camposPlantilla, camposFormateados);
	}

	private void rellenarPorcentaje(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		String porcentajeAdquisicion = tramite.getPAdquisicion() != null ? tramite.getPAdquisicion().toString() : "";
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PORCENTAJE_ADQUISICION, porcentajeAdquisicion, false, false, ConstantesPDF._9));
	}

	private void rellenarBaseImponible(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		String baseImponible = tramite.getValorReal() != null ? tramite.getValorReal().toString().replace(".", ",") : "";
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_BASE_IMPONIBLE, baseImponible, false, false, ConstantesPDF._9));
	}

	private void rellenarDatosFinal(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (tramite.getFechaContrato() != null) {
			Fecha fechaContrato = utilesFecha.getFechaFracionada(tramite.getFechaContrato());
			if (camposPlantilla.contains(ConstantesPDF.ID_DIA_CONTRATO430)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DIA_CONTRATO430, fechaContrato.getDia(), false, false, ConstantesPDF._12));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_MES_CONTRATO430)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MES_CONTRATO430, utilesFecha.convertirMesNumberToDesc(Integer.valueOf(fechaContrato.getMes())), false, false,
						ConstantesPDF._12));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_ANIO_CONTRATO430)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ANIO_CONTRATO430, fechaContrato.getAnio(), false, false, ConstantesPDF._12));
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_PROVINCIA_PRESENTADOR)) {
			String nombreProvincia = servicioDireccion.obtenerNombreProvincia(tramite.getContrato().getIdProvincia());
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PROVINCIA_PRESENTADOR, nombreProvincia, false, false, ConstantesPDF._11));
		}
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
		String plantilla = TipoImpreso.TransmisionModelo430.getNombreEnum();
		URL ruta = this.getClass().getResource(gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS) + plantilla);
		return ruta.getFile();
	}

	private String getTipoDocumento() {
		return TipoImpreso.TransmisionModelo430.toString();
	}
}