package org.gestoresmadrid.oegamImpresion.declaracion.responsable.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.trafico.permiso.internacional.model.dao.PermisoInternacionalDao;
import org.gestoresmadrid.core.trafico.permiso.internacional.model.vo.PermisoInternacionalVO;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.declaracion.responsable.service.ServicioDeclaracionRespPermInter;
import org.gestoresmadrid.oegamImpresion.utiles.CampoPdfBean;
import org.gestoresmadrid.oegamImpresion.utiles.ConstantesPDF;
import org.gestoresmadrid.oegamImpresion.utiles.PdfMaker;
import org.gestoresmadrid.oegamImpresion.utilidades.UtilidadesConversiones;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.jasperreports.engine.JRException;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioDeclaracionRespPermInterImpl implements ServicioDeclaracionRespPermInter {

	private static final long serialVersionUID = -2202689577139587639L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDeclaracionRespPermInterImpl.class);

	@Autowired
	PermisoInternacionalDao permisoInternacionalDao;

	@Autowired
	ServicioComunDireccion servicioDireccion;

	@Autowired
	UtilidadesConversiones utilidadesConversiones;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Override
	@Transactional
	public ResultadoImpresionBean imprimirDeclaracionRespColegiado(List<BigDecimal> listaExpediente) {
		ResultadoImpresionBean resultado = new ResultadoImpresionBean(Boolean.FALSE);
		ArrayList<byte[]> listaByte = new ArrayList<byte[]>();
		try {
			for (BigDecimal numExpediente : listaExpediente) {
				PermisoInternacionalVO permisoInternacionalVO = permisoInternacionalDao.getPermisoInternacionalPorExpediente(numExpediente);
				if (permisoInternacionalVO != null) {
					try {
						byte[] bytePdf = imprimir(permisoInternacionalVO);
						listaByte.add(bytePdf);
					} catch (Exception e) {
						resultado.addListaTramitesError(numExpediente);
						log.error("Error al imprimir la declaracion de responsabilidad del colegiado. Número expediente : " + numExpediente, e);
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
			log.error("Ha sucedido un error a la hora de imprimir la declaracion de responsabilidad del colegiado, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir la declaracion de responsabilidad del colegiado.");
		}
		return resultado;
	}

	private byte[] imprimir(PermisoInternacionalVO permisoInternacionalVO) throws ParserConfigurationException, JRException {
		PdfMaker pdf = new PdfMaker();
		byte[] bytePdf = pdf.abrirPdf(getRuta());
		Set<String> camposPlantilla = pdf.getAllFields(bytePdf);

		ArrayList<CampoPdfBean> camposFormateados = rellenarPdf(permisoInternacionalVO, camposPlantilla, permisoInternacionalVO.getContrato());

		bytePdf = pdf.setCampos(bytePdf, camposFormateados);

		return bytePdf;
	}

	private ArrayList<CampoPdfBean> rellenarPdf(PermisoInternacionalVO permisoInternacionalVO, Set<String> camposPlantilla, ContratoVO contrato) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();

		rellenarFechaSuscripcion(camposPlantilla, camposFormateados);

		rellenarGestor(camposPlantilla, camposFormateados, contrato);

		rellenarDatosFinal(camposPlantilla, camposFormateados, contrato);

		return camposFormateados;
	}

	private String rellenarDireccion(ContratoVO contrato) {
		String domicilioCompleto = "";

		if (contrato.getIdTipoVia() != null) {
			String tipoViaNombre = servicioDireccion.obtenerNombreTipoVia(contrato.getIdTipoVia());
			domicilioCompleto = tipoViaNombre;
		}

		if (StringUtils.isNotBlank(contrato.getVia())) {
			domicilioCompleto += " " + utilidadesConversiones.ajustarCamposIne(contrato.getVia());
		}

		if (StringUtils.isNotBlank(contrato.getNumero())) {
			domicilioCompleto += ", " + contrato.getNumero();
		}

		if (StringUtils.isNotBlank(contrato.getLetra())) {
			domicilioCompleto += ", puerta " + contrato.getLetra();
		}

		if (StringUtils.isNotBlank(contrato.getEscalera())) {
			domicilioCompleto += ", esc. " + contrato.getEscalera();
		}

		if (StringUtils.isNotBlank(contrato.getPuerta())) {
			domicilioCompleto += ", portal " + contrato.getPuerta();
		}

		if (StringUtils.isNotBlank(contrato.getCodPostal())) {
			domicilioCompleto += ", CP. " + contrato.getCodPostal();
		}

		if (contrato.getIdProvincia() != null) {
			if (contrato.getIdMunicipio() != null) {
				String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(contrato.getIdMunicipio(), contrato.getIdProvincia());
				domicilioCompleto += ", " + utilidadesConversiones.ajustarCamposIne(nombreMunicipio);
			}
			String nombreProvincia = servicioDireccion.obtenerNombreProvincia(contrato.getIdProvincia());
			domicilioCompleto += ", " + nombreProvincia;
		}
		return domicilioCompleto;
	}

	private void rellenarGestor(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, ContratoVO contrato) {
		if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR)) {
			int inicioNombre = contrato.getColegiado().getUsuario().getApellidosNombre().indexOf(", ");
			String nombre = contrato.getColegiado().getUsuario().getApellidosNombre().substring(inicioNombre + 2, contrato.getColegiado().getUsuario().getApellidosNombre().length());
			String apellidos = contrato.getColegiado().getUsuario().getApellidosNombre().substring(0, inicioNombre);
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR, nombre + " " + apellidos, false, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR, contrato.getColegiado().getUsuario().getNif(), false, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_ENTIDAD)) {
			String domicilioCompletao = rellenarDireccion(contrato);
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_ENTIDAD, domicilioCompletao, false, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_COLEGIO)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_COLEGIO, contrato.getColegio().getColegio(), false, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_NUM_COLEGIADO_GESTOR)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUM_COLEGIADO_GESTOR, contrato.getColegiado().getNumColegiado(), false, false, ConstantesPDF._10));
		}
	}

	private void rellenarFechaSuscripcion(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados) {
		if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_SUSCRIPCION)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_SUSCRIPCION, "08/07/2019", false, false, ConstantesPDF._11));
		}
	}

	private void rellenarDatosFinal(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, ContratoVO contrato) {
		if (camposPlantilla.contains(ConstantesPDF.ID_DOMICILIO_GA)) {
			String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(contrato.getIdMunicipio(), contrato.getIdProvincia());
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DOMICILIO_GA, nombreMunicipio, false, false, ConstantesPDF._9));
		}

		Fecha fechaActual = utilesFecha.getFechaHoraActualLEG();

		if (camposPlantilla.contains(ConstantesPDF.ID_DIA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DIA, fechaActual.getDia(), false, false, ConstantesPDF._9));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_MES)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MES, utilesFecha.convertirMesNumberToDesc(Integer.valueOf(fechaActual.getMes())).toUpperCase(), false, false, ConstantesPDF._9));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_ANIO)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ANIO, fechaActual.getAnio(), false, false, ConstantesPDF._9));
		}
	}

	private String getRuta() {
		return gestorPropiedades.valorPropertie("trafico.rutaSrc.plantillasPDF") + TipoImpreso.DeclaracionResponsabilidadColegiado.getNombreEnum();
	}
}
