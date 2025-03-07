package org.gestoresmadrid.oegamImpresion.mandatoEspecifico.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.trafico.interga.model.dao.IntervinienteIntergaDao;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.TipoTramiteInterga;
import org.gestoresmadrid.core.trafico.interga.model.vo.IntervinienteIntergaVO;
import org.gestoresmadrid.core.trafico.permiso.internacional.model.dao.PermisoInternacionalDao;
import org.gestoresmadrid.core.trafico.permiso.internacional.model.vo.PermisoInternacionalVO;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.mandatoEspecifico.service.ServicioMandatoEspecificoPermInter;
import org.gestoresmadrid.oegamImpresion.utiles.CampoPdfBean;
import org.gestoresmadrid.oegamImpresion.utiles.ConstantesPDF;
import org.gestoresmadrid.oegamImpresion.utiles.PdfMaker;
import org.gestoresmadrid.oegamImpresion.utilidades.UtilidadesConversiones;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.jasperreports.engine.JRException;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioMandatoEspecificoPermInterImpl implements ServicioMandatoEspecificoPermInter {

	private static final long serialVersionUID = 1523616728718796210L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMandatoEspecificoPermInterImpl.class);

	@Autowired
	PermisoInternacionalDao permisoInternacionalDao;

	@Autowired
	IntervinienteIntergaDao intervinientePermInterDao;

	@Autowired
	ServicioComunDireccion servicioDireccion;

	@Autowired
	UtilidadesConversiones utilidadesConversiones;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Override
	@Transactional
	public ResultadoImpresionBean imprimirMandatoEspecifico(List<BigDecimal> listaExpediente) {
		ResultadoImpresionBean resultado = new ResultadoImpresionBean(Boolean.FALSE);
		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		try {
			for (BigDecimal numExpediente : listaExpediente) {
				PermisoInternacionalVO permiso = permisoInternacionalDao.getPermisoInternacionalPorExpediente(numExpediente);
				if (permiso != null) {
					try {
						byte[] bytePdf = imprimir(permiso);
						listaByte.add(bytePdf);
					} catch (Exception e) {
						log.error("Error al imprimir el mandato del permiso internacional con numExpediente: " + numExpediente, e);
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
			log.error("Ha sucedido un error a la hora de imprimir los mandatos especificos de Permisos Internacionales, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir los mandatos especificos de Permisos Internacionales.");
		}
		return resultado;
	}

	private byte[] imprimir(PermisoInternacionalVO permiso) throws ParserConfigurationException, JRException {
		PdfMaker pdf = new PdfMaker();
		byte[] bytePdf = pdf.abrirPdf(getRuta());
		Set<String> camposPlantilla = pdf.getAllFields(bytePdf);

		ArrayList<CampoPdfBean> camposFormateados = rellenarPdf(permiso, camposPlantilla, permiso.getContrato());

		bytePdf = pdf.setCampos(bytePdf, camposFormateados);

		return bytePdf;
	}

	private ArrayList<CampoPdfBean> rellenarPdf(PermisoInternacionalVO permiso, Set<String> camposPlantilla, ContratoVO contrato) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();

		rellenarInterviniente(permiso, camposPlantilla, camposFormateados);

		rellenarGestor(permiso, camposPlantilla, camposFormateados, contrato);

		rellenarAsuntos(camposPlantilla, camposFormateados);

		rellenarDatosFinal(camposPlantilla, camposFormateados, contrato);

		return camposFormateados;
	}

	private void rellenarInterviniente(PermisoInternacionalVO permiso, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		IntervinienteIntergaVO titular = buscarInterviniente(permiso);

		if (titular != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR)) {
				String nombreApellidos = (titular.getPersona().getNombre() != null ? titular.getPersona().getNombre() : "") + " " + (titular.getPersona().getApellido1RazonSocial() != null ? titular
						.getPersona().getApellido1RazonSocial() : "") + " " + (titular.getPersona().getApellido2() != null ? titular.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR, nombreApellidos, false, false, ConstantesPDF._11));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR, titular.getNif(), false, false, ConstantesPDF._11));
			}

			if (titular.getDireccion() != null) {
				if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_ENTIDAD)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_ENTIDAD, utilidadesConversiones.ajustarCamposIne(titular.getDireccion().getNombreVia()), false, false,
							ConstantesPDF._11));
				}

				if (camposPlantilla.contains(ConstantesPDF.ID_PROVINCIA_ENTIDAD)) {
					String provincia = servicioDireccion.obtenerNombreProvincia(titular.getDireccion().getIdProvincia());
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PROVINCIA_ENTIDAD, provincia, false, false, ConstantesPDF._11));
				}
				
				if (camposPlantilla.contains(ConstantesPDF.ID_PROVINCIA_ENTIDAD)) {
					String provincia = servicioDireccion.obtenerNombreProvincia(titular.getDireccion().getIdProvincia());
					String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(titular.getDireccion().getIdMunicipio(), titular.getDireccion().getIdProvincia());
					if (StringUtils.isNotBlank(nombreMunicipio)) {
						provincia += " (" + nombreMunicipio + ")";
					}
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

	private void rellenarGestor(PermisoInternacionalVO permiso, Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, ContratoVO contrato) {
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
		
		if (camposPlantilla.contains(ConstantesPDF.ID_DESPACHO)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DESPACHO, contrato.getRazonSocial(), false, false, ConstantesPDF._11));
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

	private void rellenarAsuntos(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (camposPlantilla.contains(ConstantesPDF.ID_ASUNTO1_EXPLICACION)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ASUNTO1_EXPLICACION, "PERMISO INTERNACIONAL", false, false, ConstantesPDF._11));
		}
	}

	private void rellenarDatosFinal(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, ContratoVO contrato) {
		if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_GA)) {
			String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(contrato.getIdMunicipio(), contrato.getIdProvincia());
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_GA, nombreMunicipio, false, false, ConstantesPDF._11));
		}
	}

	private IntervinienteIntergaVO buscarInterviniente(PermisoInternacionalVO permiso) {
		return intervinientePermInterDao.getIntervinienteTrafico(permiso.getNumExpediente(), null, null, TipoTramiteInterga.Permiso_Internacional.getValorEnum());
	}

	private String getRuta() {
		return gestorPropiedades.valorPropertie("trafico.rutaSrc.plantillasPDF") + TipoImpreso.MandatoEspecificoInterga.getNombreEnum();
	}
}
