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
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.mandatoGenerico.service.ServicioMandatoGenericoCtit;
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
public class ServicioMandatoGenericoCtitImpl implements ServicioMandatoGenericoCtit {

	private static final long serialVersionUID = -257033832206059812L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMandatoGenericoCtitImpl.class);

	@Autowired
	ServicioComunTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioComunDireccion servicioDireccion;

	@Autowired
	UtilidadesConversiones utilidadesConversiones;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	public ResultadoImpresionBean imprimirMandatoGenerico(List<BigDecimal> listaExpediente, String tipoInterviniente) {
		ResultadoImpresionBean resultado = new ResultadoImpresionBean(Boolean.FALSE);
		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		try {
			for (BigDecimal numExpediente : listaExpediente) {
				TramiteTrafTranVO tramite = servicioTramiteTrafico.getTramiteTransmisionVO(numExpediente, true);
				if (tramite != null) {
					try {
						byte[] bytePdf = imprimir(tramite, tipoInterviniente);
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
				resultado.setPdf(pdf);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha generado nada para imprimir.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir los mandatos genericos de Ctit, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir los mandatos genericos de Ctit.");
		}
		return resultado;
	}

	private byte[] imprimir(TramiteTrafTranVO tramite, String tipoInterviniente) throws ParserConfigurationException, JRException {
		PdfMaker pdf = new PdfMaker();
		byte[] bytePdf = pdf.abrirPdf(getRuta());
		Set<String> camposPlantilla = pdf.getAllFields(bytePdf);

		ArrayList<CampoPdfBean> camposFormateados = rellenarPdf(tramite, camposPlantilla, tramite.getContrato(), tipoInterviniente);

		bytePdf = pdf.setCampos(bytePdf, camposFormateados);

		return bytePdf;
	}

	private ArrayList<CampoPdfBean> rellenarPdf(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ContratoVO contrato, String tipoInterviniente) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();

		rellenarInterviniente(tramite, camposPlantilla, camposFormateados, tipoInterviniente);

		rellenarGestor(tramite, camposPlantilla, camposFormateados, contrato);

		rellenarDatosFinal(tramite, camposPlantilla, camposFormateados, contrato);

		return camposFormateados;
	}

	private void rellenarInterviniente(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, String tipoInterviniente) {
		IntervinienteTraficoVO interviniente = null;
		IntervinienteTraficoVO repreInterviniente = null;

		if (TRANSMITENTE.equals(tipoInterviniente)) {
			interviniente = buscarInterviniente(tramite, TipoInterviniente.TransmitenteTrafico.getValorEnum());
			repreInterviniente = buscarInterviniente(tramite, TipoInterviniente.RepresentanteTransmitente.getValorEnum());
		} else if (ADQUIRIENTE.equals(tipoInterviniente)) {
			interviniente = buscarInterviniente(tramite, TipoInterviniente.Adquiriente.getValorEnum());
			repreInterviniente = buscarInterviniente(tramite, TipoInterviniente.RepresentanteAdquiriente.getValorEnum());
		} else if (COMPRAVENTA.equals(tipoInterviniente)) {
			interviniente = buscarInterviniente(tramite, TipoInterviniente.Compraventa.getValorEnum());
			repreInterviniente = buscarInterviniente(tramite, TipoInterviniente.RepresentanteCompraventa.getValorEnum());
		}

		if (interviniente != null) {
			if (interviniente.getPersona().getSexo() != null && ConstantesPDF.PERSONA_JURIDICA.equals(interviniente.getPersona().getSexo())) {
				if (repreInterviniente != null) {
					if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR)) {
						String nombreApellidos = (repreInterviniente.getPersona().getNombre() != null ? repreInterviniente.getPersona().getNombre() : "") + " " + (repreInterviniente.getPersona()
								.getApellido1RazonSocial() != null ? repreInterviniente.getPersona().getApellido1RazonSocial() : "") + " " + (repreInterviniente.getPersona().getApellido2() != null
										? repreInterviniente.getPersona().getApellido2() : "");
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR, nombreApellidos, false, false, ConstantesPDF._11));
					}

					if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR, repreInterviniente.getId().getNif(), false, false, ConstantesPDF._11));
					}
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_ENTIDAD)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_ENTIDAD, interviniente.getPersona().getApellido1RazonSocial(), false, false, ConstantesPDF._11));
				}

				if (camposPlantilla.contains(ConstantesPDF.CIF_ENTIDAD)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.CIF_ENTIDAD, interviniente.getId().getNif(), false, false, ConstantesPDF._11));
				}
			} else {
				if (repreInterviniente != null) {
					if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR)) {
						String nombreApellidos = (repreInterviniente.getPersona().getNombre() != null ? repreInterviniente.getPersona().getNombre() : "") + " " + (repreInterviniente.getPersona()
								.getApellido1RazonSocial() != null ? repreInterviniente.getPersona().getApellido1RazonSocial() : "") + " " + (repreInterviniente.getPersona().getApellido2() != null
										? repreInterviniente.getPersona().getApellido2() : "");
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR, nombreApellidos, false, false, ConstantesPDF._11));
					}

					if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR, repreInterviniente.getId().getNif(), false, false, ConstantesPDF._11));
					}

					if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_ENTIDAD)) {
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_ENTIDAD, interviniente.getPersona().getApellido1RazonSocial(), false, false, ConstantesPDF._11));
					}

					if (camposPlantilla.contains(ConstantesPDF.CIF_ENTIDAD)) {
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.CIF_ENTIDAD, interviniente.getId().getNif(), false, false, ConstantesPDF._11));
					}

				} else {
					if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR)) {
						String nombreApellidos = (interviniente.getPersona().getNombre() != null ? interviniente.getPersona().getNombre() : "") + " " + (interviniente.getPersona()
								.getApellido1RazonSocial() != null ? interviniente.getPersona().getApellido1RazonSocial() : "") + " " + (interviniente.getPersona().getApellido2() != null
										? interviniente.getPersona().getApellido2() : "");
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR, nombreApellidos, false, false, ConstantesPDF._11));
					}

					if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR, interviniente.getId().getNif(), false, false, ConstantesPDF._11));
					}
				}
			}

			if (interviniente.getDireccion() != null) {
				if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_ENTIDAD)) {
					String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(interviniente.getDireccion().getIdMunicipio(), interviniente.getDireccion().getIdProvincia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_ENTIDAD, nombreMunicipio, false, false, ConstantesPDF._11));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_ENTIDAD)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_ENTIDAD, utilidadesConversiones.ajustarCamposIne(interviniente.getDireccion().getNombreVia()), false, false,
							ConstantesPDF._11));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_NUM_DOMICILIO_ENTIDAD)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUM_DOMICILIO_ENTIDAD, interviniente.getDireccion().getNumero(), false, false, ConstantesPDF._11));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_CP_ENTIDAD)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CP_ENTIDAD, interviniente.getDireccion().getCodPostal(), false, false, ConstantesPDF._11));
				}
			}
		}
	}

	private void rellenarGestor(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, ContratoVO contrato) {
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

	private void rellenarDatosFinal(TramiteTrafTranVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, ContratoVO contrato) {
		if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_GA)) {
			String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(contrato.getIdMunicipio(), contrato.getIdProvincia());
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_GA, nombreMunicipio, false, false, ConstantesPDF._11));

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

	private String getRuta() {
		return gestorPropiedades.valorPropertie("trafico.rutaSrc.plantillasPDF") + TipoImpreso.MatriculacionMandatoGenerico.getNombreEnum();
	}
}
