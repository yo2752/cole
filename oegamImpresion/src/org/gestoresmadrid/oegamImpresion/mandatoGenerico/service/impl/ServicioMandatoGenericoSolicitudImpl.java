package org.gestoresmadrid.oegamImpresion.mandatoGenerico.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVO;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.mandatoGenerico.service.ServicioMandatoGenericoSolicitud;
import org.gestoresmadrid.oegamImpresion.utiles.CampoPdfBean;
import org.gestoresmadrid.oegamImpresion.utiles.ConstantesPDF;
import org.gestoresmadrid.oegamImpresion.utiles.PdfMaker;
import org.gestoresmadrid.oegamImpresion.utilidades.UtilidadesConversiones;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioMandatoGenericoSolicitudImpl implements ServicioMandatoGenericoSolicitud {

	private static final long serialVersionUID = -7327427976690075515L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMandatoGenericoSolicitudImpl.class);

	@Autowired
	ServicioComunTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioComunDireccion servicioDireccion;

	@Autowired
	UtilidadesConversiones utilidadesConversiones;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	public ResultadoImpresionBean imprimirMandatoGenerico(List<BigDecimal> listaExpediente) {
		ResultadoImpresionBean resultado = new ResultadoImpresionBean(Boolean.FALSE);
		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		try {
			for (BigDecimal numExpediente : listaExpediente) {
				TramiteTrafSolInfoVO tramite = servicioTramiteTrafico.getTramiteSolictudVO(numExpediente, true);
				if (tramite != null) {
					try {
						byte[] bytePdf = imprimir(tramite);
						listaByte.add(bytePdf);
					} catch (Exception e) {
						log.error("Error al imprimir el tramite de solicitud con numExpediente: " + numExpediente, e);
						resultado.addListaTramitesError(numExpediente);
					}
				} else {
					resultado.addListaTramitesError(numExpediente);
				}
			}
			if (listaByte != null && !listaByte.isEmpty()) {
				byte[] pdf = PdfMaker.concatenarPdf(listaByte);
				resultado.setPdf(pdf);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha generado nada para imprimir.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir los mandatos genericos de Solicitud, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir los mandatos genericos de Solicitud.");
		}
		return resultado;
	}

	private byte[] imprimir(TramiteTrafSolInfoVO tramite) throws ParserConfigurationException, JRException {
		PdfMaker pdf = new PdfMaker();
		byte[] bytePdf = pdf.abrirPdf(getRuta());
		Set<String> camposPlantilla = pdf.getAllFields(bytePdf);

		ArrayList<CampoPdfBean> camposFormateados = rellenarPdf(tramite, camposPlantilla, tramite.getContrato());

		bytePdf = pdf.setCampos(bytePdf, camposFormateados);

		return bytePdf;
	}

	private ArrayList<CampoPdfBean> rellenarPdf(TramiteTrafSolInfoVO tramite, Set<String> camposPlantilla, ContratoVO contrato) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();

		rellenarInterviniente(tramite, camposPlantilla, camposFormateados);

		rellenarGestor(tramite, camposPlantilla, camposFormateados, contrato);

		rellenarDatosFinal(tramite, camposPlantilla, camposFormateados, contrato);

		return camposFormateados;
	}

	private void rellenarInterviniente(TramiteTrafSolInfoVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		IntervinienteTraficoVO solicitante = buscarInterviniente(tramite, TipoInterviniente.Solicitante.getValorEnum());

		if (solicitante != null) {
			if (solicitante.getPersona().getSexo() != null && ConstantesPDF.PERSONA_JURIDICA.equals(solicitante.getPersona().getSexo())) {
				if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_ENTIDAD)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_ENTIDAD, solicitante.getPersona().getApellido1RazonSocial(), false, false, ConstantesPDF._11));
				}

				if (camposPlantilla.contains(ConstantesPDF.CIF_ENTIDAD)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.CIF_ENTIDAD, solicitante.getId().getNif(), false, false, ConstantesPDF._11));
				}
			} else {
				if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR)) {
					String nombreApellidos = (solicitante.getPersona().getNombre() != null ? solicitante.getPersona().getNombre() : "") + " " + (solicitante.getPersona()
							.getApellido1RazonSocial() != null ? solicitante.getPersona().getApellido1RazonSocial() : "") + " " + (solicitante.getPersona().getApellido2() != null ? solicitante
									.getPersona().getApellido2() : "");
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR, nombreApellidos, false, false, ConstantesPDF._11));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR, solicitante.getId().getNif(), false, false, ConstantesPDF._11));
				}
			}

			if (solicitante.getDireccion() != null) {
				if (camposPlantilla.contains(ConstantesPDF.ID_PROVINCIA_ENTIDAD)) {
					String provincia = servicioDireccion.obtenerNombreProvincia(solicitante.getDireccion().getIdProvincia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PROVINCIA_ENTIDAD, provincia, false, false, ConstantesPDF._11));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_ENTIDAD)) {
					String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(solicitante.getDireccion().getIdMunicipio(), solicitante.getDireccion().getIdProvincia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_ENTIDAD, nombreMunicipio, false, false, ConstantesPDF._11));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_ENTIDAD)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_ENTIDAD, utilidadesConversiones.ajustarCamposIne(solicitante.getDireccion().getNombreVia()), false, false,
							ConstantesPDF._11));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_NUM_DOMICILIO_ENTIDAD)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUM_DOMICILIO_ENTIDAD, solicitante.getDireccion().getNumero(), false, false, ConstantesPDF._11));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_CP_ENTIDAD)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CP_ENTIDAD, solicitante.getDireccion().getCodPostal(), false, false, ConstantesPDF._11));
				}
			}
		}
	}

	private void rellenarGestor(TramiteTrafSolInfoVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, ContratoVO contrato) {
		if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_GESTOR)) {
			int inicioNombre = contrato.getColegiado().getUsuario().getApellidosNombre().indexOf(", ");
			String nombre = contrato.getColegiado().getUsuario().getApellidosNombre().substring(inicioNombre + 2, contrato.getColegiado().getUsuario().getApellidosNombre().length());
			String apellidos = contrato.getColegiado().getUsuario().getApellidosNombre().substring(0, inicioNombre);
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_GESTOR, nombre + " " + apellidos, false, false, ConstantesPDF._11));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_NUM_COLEGIADO_GESTOR)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUM_COLEGIADO_GESTOR, contrato.getColegiado().getNumColegiado(), false, false, ConstantesPDF._11));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_COLEGIO)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_COLEGIO, contrato.getColegio().getColegio(), false, false, ConstantesPDF._11));
		}

		if (camposPlantilla.contains(ConstantesPDF.NIF_GA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.NIF_GA, contrato.getColegiado().getUsuario().getNif(), false, false, ConstantesPDF._11));
		}
	}

	private void rellenarDatosFinal(TramiteTrafSolInfoVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, ContratoVO contrato) {
		if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_GA)) {
			String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(contrato.getIdMunicipio(), contrato.getIdProvincia());
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_GA, nombreMunicipio, false, false, ConstantesPDF._11));

		}
	}

	private IntervinienteTraficoVO buscarInterviniente(TramiteTrafSolInfoVO tramite, String tipoInterviniente) {
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
		return gestorPropiedades.valorPropertie("trafico.rutaSrc.plantillasPDF") + TipoImpreso.MatriculacionMandatoGenerico.getNombreEnum();
	}
}
