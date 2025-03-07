package org.gestoresmadrid.oegamImpresion.declaraciones.service.impl;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.declaraciones.service.ServicioDeclaracionesBaja;
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
public class ServicioDeclaracionesBajaImpl implements ServicioDeclaracionesBaja {

	private static final long serialVersionUID = 2548927021068744345L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDeclaracionesBajaImpl.class);

	@Autowired
	ServicioComunTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioComunDireccion servicioDireccion;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	UtilidadesConversiones utilidadesConversiones;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Override
	public ResultadoImpresionBean imprimirDeclaracion(List<BigDecimal> listaExpediente, String tipoInterviniente, String tipoImpreso) {
		ResultadoImpresionBean resultado = new ResultadoImpresionBean(Boolean.FALSE);
		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		try {
			for (BigDecimal numExpediente : listaExpediente) {
				TramiteTrafBajaVO tramite = servicioTramiteTrafico.getTramiteBajaVO(numExpediente, true);
				if (tramite != null) {
					IntervinienteTraficoVO interviniente = obtenerInterviniente(tramite, tipoInterviniente);
					if (interviniente != null) {
						try {
							byte[] bytePdf = imprimir(tramite, interviniente, tipoImpreso);
							listaByte.add(bytePdf);
						} catch (Exception e) {
							log.error("Error al imprimir el tramite de baja con numExpediente: " + numExpediente, e);
							resultado.addListaTramitesError(numExpediente);
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No existe el Interviniente seleccionado para la Declaración.");
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

	private IntervinienteTraficoVO obtenerInterviniente(TramiteTrafBajaVO tramite, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = null;
		if (TRANSMITENTE.equals(tipoInterviniente) || TITULAR.equals(tipoInterviniente)) {
			interviniente = buscarInterviniente(tramite, TipoInterviniente.Titular.getValorEnum());
		} else if (ADQUIRIENTE.equals(tipoInterviniente)) {
			interviniente = buscarInterviniente(tramite, TipoInterviniente.Adquiriente.getValorEnum());
		}
		return interviniente;
	}

	private byte[] imprimir(TramiteTrafBajaVO tramite, IntervinienteTraficoVO interviniente, String tipoImpreso) {
		PdfMaker pdf = new PdfMaker();
		byte[] bytePdf = pdf.abrirPdf(getRuta(tipoImpreso));
		Set<String> camposPlantilla = pdf.getAllFields(bytePdf);

		ArrayList<CampoPdfBean> camposFormateados = rellenarPdf(tramite, camposPlantilla, pdf, interviniente);

		bytePdf = pdf.setCampos(bytePdf, camposFormateados);

		return bytePdf;
	}

	private ArrayList<CampoPdfBean> rellenarPdf(TramiteTrafBajaVO tramite, Set<String> camposPlantilla, PdfMaker pdf, IntervinienteTraficoVO interviniente) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();

		rellenarInterviniente(tramite, camposPlantilla, camposFormateados, interviniente);

		rellenarOtrosDatos(tramite, camposPlantilla, camposFormateados);

		return camposFormateados;
	}

	private void rellenarOtrosDatos(TramiteTrafBajaVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (tramite.getVehiculo() != null) {
			if (tramite.getVehiculo().getMatricula() != null) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MATRICULA, tramite.getVehiculo().getMatricula(), false, false, ConstantesPDF._11));
			}
		}

		Fecha fechaActual = utilesFecha.getFechaHoraActualLEG();

		if (camposPlantilla.contains(ConstantesPDF.ID_DIA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DIA, fechaActual.getDia(), false, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_MES)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MES, utilesFecha.convertirMesNumberToDesc(Integer.valueOf(fechaActual.getMes())).toUpperCase(), false, false, ConstantesPDF._12));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_ANIO)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ANIO, fechaActual.getAnio(), false, false, ConstantesPDF._12));
		}
	}

	private void rellenarInterviniente(TramiteTrafBajaVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, IntervinienteTraficoVO interviniente) {
		IntervinienteTraficoVO repreInterviniente = null;

		if (TRANSMITENTE.equals(interviniente.getId().getTipoInterviniente())) {
			repreInterviniente = buscarInterviniente(tramite, TipoInterviniente.RepresentanteTitular.getValorEnum());
		} else if (ADQUIRIENTE.equals(interviniente.getId().getTipoInterviniente())) {
			repreInterviniente = buscarInterviniente(tramite, TipoInterviniente.RepresentanteAdquiriente.getValorEnum());
		}

		if (interviniente != null) {
			if ("X".equals(interviniente.getPersona().getSexo())) {
				if (camposPlantilla.contains(ConstantesPDF.ID_ENTIDAD)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ENTIDAD, interviniente.getPersona().getApellido1RazonSocial(), false, false, ConstantesPDF._12));
				}

				if (repreInterviniente != null) {
					if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_GESTOR)) {
						String nombreApellidos = (repreInterviniente.getPersona().getNombre() != null ? repreInterviniente.getPersona().getNombre() : "") + " " + (repreInterviniente.getPersona()
								.getApellido1RazonSocial() != null ? repreInterviniente.getPersona().getApellido1RazonSocial() : "") + " " + (repreInterviniente.getPersona().getApellido2() != null
										? repreInterviniente.getPersona().getApellido2() : "");

						camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_GESTOR, nombreApellidos, false, false, ConstantesPDF._12));
					}

					if (camposPlantilla.contains(ConstantesPDF.ID_NIF)) {
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NIF, repreInterviniente.getId().getNif(), false, false, ConstantesPDF._12));
					}
				}
			} else {
				if (camposPlantilla.contains(ConstantesPDF.ID_ENTIDAD)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ENTIDAD, "", false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_GESTOR)) {
					String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
							.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null ? interviniente
									.getPersona().getApellido2() : "");
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_GESTOR, nombreApellidos, false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_NIF)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NIF, interviniente.getId().getNif(), false, false, ConstantesPDF._12));
				}
			}

			if (interviniente.getDireccion() != null) {
				String nombreVia = utilidadesConversiones.ajustarCamposIne(interviniente.getDireccion().getNombreVia());
				String municipio = servicioDireccion.obtenerNombreMunicipio(interviniente.getDireccion().getIdMunicipio(), interviniente.getDireccion().getIdProvincia());
				String nombreProvincia = servicioDireccion.obtenerNombreProvincia(interviniente.getDireccion().getIdProvincia());

				if (camposPlantilla.contains(ConstantesPDF.ID_CALLE)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CALLE, nombreVia, false, false, ConstantesPDF._12));
				}
				if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO, municipio, false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PROVINCIA)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PROVINCIA, nombreProvincia, false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_LUGAR)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_LUGAR, nombreProvincia, false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_NUMERO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUMERO, interviniente.getDireccion().getNumero(), false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_CODIGO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CODIGO, interviniente.getDireccion().getCodPostal(), false, false, ConstantesPDF._12));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_ENTIDAD)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_ENTIDAD, nombreVia, false, false, ConstantesPDF._12));
				}
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

	private String getRuta(String tipoImpreso) {
		URL ruta = this.getClass().getResource(gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS) + TipoImpreso.convertirTexto(tipoImpreso));
		return ruta.getFile();
	}
}
