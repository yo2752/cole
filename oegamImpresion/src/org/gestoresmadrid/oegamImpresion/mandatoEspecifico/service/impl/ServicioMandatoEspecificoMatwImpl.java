package org.gestoresmadrid.oegamImpresion.mandatoEspecifico.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.mandatoEspecifico.service.ServicioMandatoEspecificoMatw;
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
public class ServicioMandatoEspecificoMatwImpl implements ServicioMandatoEspecificoMatw {

	private static final long serialVersionUID = 6062713243624454357L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMandatoEspecificoMatwImpl.class);

	@Autowired
	ServicioComunTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioComunDireccion servicioDireccion;

	@Autowired
	UtilidadesConversiones utilidadesConversiones;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	public ResultadoImpresionBean imprimirMandatoEspecifico(List<BigDecimal> listaExpediente) {
		ResultadoImpresionBean resultado = new ResultadoImpresionBean(Boolean.FALSE);
		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		try {
			for (BigDecimal numExpediente : listaExpediente) {
				TramiteTrafMatrVO tramite = servicioTramiteTrafico.getTramiteMatriculacionVO(numExpediente, true);
				if (tramite != null) {
					try {
						byte[] bytePdf = imprimir(tramite);
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
				resultado.setPdf(pdf);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha generado nada para imprimir.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir los mandatos especificos de Matriculacion, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir los mandatos especificos de Matriculacion.");
		}
		return resultado;
	}

	private byte[] imprimir(TramiteTrafMatrVO tramite) throws ParserConfigurationException, JRException {
		PdfMaker pdf = new PdfMaker();
		byte[] bytePdf = pdf.abrirPdf(getRuta());
		Set<String> camposPlantilla = pdf.getAllFields(bytePdf);

		ArrayList<CampoPdfBean> camposFormateados = rellenarPdf(tramite, camposPlantilla, tramite.getContrato());

		bytePdf = pdf.setCampos(bytePdf, camposFormateados);

		return bytePdf;
	}

	private ArrayList<CampoPdfBean> rellenarPdf(TramiteTrafMatrVO tramite, Set<String> camposPlantilla, ContratoVO contrato) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();

		rellenarTitular(tramite, camposPlantilla, camposFormateados);

		rellenarGestor(tramite, camposPlantilla, camposFormateados, contrato);

		rellenarAsuntos(tramite, camposPlantilla, camposFormateados);

		rellenarDatosFinal(tramite, camposPlantilla, camposFormateados, contrato);

		return camposFormateados;
	}

	private void rellenarTitular(TramiteTrafMatrVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		IntervinienteTraficoVO titular = buscarInterviniente(tramite, TipoInterviniente.Titular.getValorEnum());

		if (titular != null) {
			IntervinienteTraficoVO repreTitular = buscarInterviniente(tramite, TipoInterviniente.RepresentanteTitular.getValorEnum());
			if (titular.getPersona().getSexo() != null && ConstantesPDF.PERSONA_JURIDICA.equals(titular.getPersona().getSexo())) {
				if (repreTitular != null) {
					if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR)) {
						String nombreApellidos = (repreTitular.getPersona().getNombre() != null ? repreTitular.getPersona().getNombre() : "") + " " + (repreTitular.getPersona()
								.getApellido1RazonSocial() != null ? repreTitular.getPersona().getApellido1RazonSocial() : "") + " " + (repreTitular.getPersona().getApellido2() != null ? repreTitular
										.getPersona().getApellido2() : "");
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR, nombreApellidos, false, false, ConstantesPDF._11));
					}

					if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR, repreTitular.getId().getNif(), false, false, ConstantesPDF._11));
					}
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_ENTIDAD)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_ENTIDAD, titular.getPersona().getApellido1RazonSocial(), false, false, ConstantesPDF._11));
				}

				if (camposPlantilla.contains(ConstantesPDF.CIF_ENTIDAD)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.CIF_ENTIDAD, titular.getId().getNif(), false, false, ConstantesPDF._11));
				}
			} else {
				if (repreTitular != null) {
					if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR)) {
						String nombreApellidos = (repreTitular.getPersona().getNombre() != null ? repreTitular.getPersona().getNombre() : "") + " " + (repreTitular.getPersona()
								.getApellido1RazonSocial() != null ? repreTitular.getPersona().getApellido1RazonSocial() : "") + " " + (repreTitular.getPersona().getApellido2() != null ? repreTitular
										.getPersona().getApellido2() : "");
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR, nombreApellidos, false, false, ConstantesPDF._11));
					}

					if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR, repreTitular.getId().getNif(), false, false, ConstantesPDF._11));
					}

					if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_ENTIDAD)) {
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_ENTIDAD, titular.getPersona().getApellido1RazonSocial(), false, false, ConstantesPDF._11));
					}

					if (camposPlantilla.contains(ConstantesPDF.CIF_ENTIDAD)) {
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.CIF_ENTIDAD, titular.getId().getNif(), false, false, ConstantesPDF._11));
					}

				} else {
					if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR)) {
						String nombreApellidos = (titular.getPersona().getNombre() != null ? titular.getPersona().getNombre() : "") + " " + (titular.getPersona().getApellido1RazonSocial() != null
								? titular.getPersona().getApellido1RazonSocial() : "") + " " + (titular.getPersona().getApellido2() != null ? titular.getPersona().getApellido2() : "");
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR, nombreApellidos, false, false, ConstantesPDF._11));
					}

					if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR, titular.getId().getNif(), false, false, ConstantesPDF._11));
					}
				}
			}

			if (titular.getDireccion() != null) {
				if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_ENTIDAD)) {
					String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(titular.getDireccion().getIdMunicipio(), titular.getDireccion().getIdProvincia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_ENTIDAD, nombreMunicipio, false, false, ConstantesPDF._11));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_ENTIDAD)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_ENTIDAD, utilidadesConversiones.ajustarCamposIne(titular.getDireccion().getNombreVia()), false, false,
							ConstantesPDF._11));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PROVINCIA_ENTIDAD)) {
					String provincia = servicioDireccion.obtenerNombreProvincia(titular.getDireccion().getIdProvincia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PROVINCIA_ENTIDAD, provincia, false, false, ConstantesPDF._11));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_NUM_DOMICILIO_ENTIDAD)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUM_DOMICILIO_ENTIDAD, titular.getDireccion().getNumero(), false, false, ConstantesPDF._11));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_CP_ENTIDAD)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CP_ENTIDAD, titular.getDireccion().getCodPostal(), false, false, ConstantesPDF._11));
				}
			}
		}
	}

	private void rellenarGestor(TramiteTrafMatrVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, ContratoVO contrato) {
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

		if (camposPlantilla.contains(ConstantesPDF.ID_NIF_GA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NIF_GA, contrato.getColegiado().getUsuario().getNif(), false, false, ConstantesPDF._11));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_VIA_MANDATARIO)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_VIA_MANDATARIO, contrato.getVia(), false, false, ConstantesPDF._11));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_VIA_GA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_VIA_GA, contrato.getNumero(), false, false, ConstantesPDF._11));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CP_GA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CP_GA, contrato.getCodPostal(), false, false, ConstantesPDF._11));
		}
	}

	private void rellenarAsuntos(TramiteTrafMatrVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (camposPlantilla.contains(ConstantesPDF.ID_ASUNTO1_EXPLICACION)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ASUNTO1_EXPLICACION, "MATRICULACION: ", false, false, ConstantesPDF._11));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_ASUNTO2)) {
			if (tramite.getVehiculo() != null) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ASUNTO2, tramite.getVehiculo().getBastidor(), false, false, ConstantesPDF._11));
			}
		}
	}

	private void rellenarDatosFinal(TramiteTrafMatrVO tramite, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, ContratoVO contrato) {
		if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_GA)) {
			String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(contrato.getIdMunicipio(), contrato.getIdProvincia());
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_GA, nombreMunicipio, false, false, ConstantesPDF._11));
		}
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

	private String getRuta() {
		return gestorPropiedades.valorPropertie("trafico.rutaSrc.plantillasPDF") + TipoImpreso.MatriculacionMandatoEspecifico.getNombreEnum();
	}
}
