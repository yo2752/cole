package org.gestoresmadrid.oegamImpresion.declaracion.responsable.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.dao.DuplicadoPermisoConducirDao;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.enumerados.TiposDuplicadosPermisos;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.vo.DuplicadoPermisoConducirVO;
import org.gestoresmadrid.core.trafico.interga.model.dao.IntervinienteIntergaDao;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.TipoTramiteInterga;
import org.gestoresmadrid.core.trafico.interga.model.vo.IntervinienteIntergaVO;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.declaracion.responsable.service.ServicioDeclaracionRespDPC;
import org.gestoresmadrid.oegamImpresion.utiles.CampoPdfBean;
import org.gestoresmadrid.oegamImpresion.utiles.ConstantesPDF;
import org.gestoresmadrid.oegamImpresion.utiles.PdfMaker;
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
public class ServicioDeclaracionRespDPCImpl implements ServicioDeclaracionRespDPC {

	private static final long serialVersionUID = -2202689577139587639L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDeclaracionRespDPCImpl.class);

	@Autowired
	DuplicadoPermisoConducirDao duplicadoPermisoConducirDao;

	@Autowired
	IntervinienteIntergaDao intervinientePermInterDao;

	@Autowired
	ServicioComunDireccion servicioDireccion;

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
				DuplicadoPermisoConducirVO duplicadoPermisoConducirVO = duplicadoPermisoConducirDao.getDuplPermCondPorNumExpediente(numExpediente);
				if (duplicadoPermisoConducirVO != null) {
					try {
						byte[] bytePdf = imprimir(duplicadoPermisoConducirVO);
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

	private byte[] imprimir(DuplicadoPermisoConducirVO duplicadoPermisoConducirVO) throws ParserConfigurationException, JRException {
		PdfMaker pdf = new PdfMaker();
		byte[] bytePdf = pdf.abrirPdf(getRuta());
		Set<String> camposPlantilla = pdf.getAllFields(bytePdf);

		ArrayList<CampoPdfBean> camposFormateados = rellenarPdf(duplicadoPermisoConducirVO, camposPlantilla, duplicadoPermisoConducirVO.getContrato());

		bytePdf = pdf.setCampos(bytePdf, camposFormateados);

		return bytePdf;
	}

	private ArrayList<CampoPdfBean> rellenarPdf(DuplicadoPermisoConducirVO duplicadoPermisoConducirVO, Set<String> camposPlantilla, ContratoVO contrato) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();

		rellenarGestor(camposPlantilla, camposFormateados, contrato);

		rellenarTitular(camposPlantilla, camposFormateados, duplicadoPermisoConducirVO);

		rellenarDatosFinal(camposPlantilla, camposFormateados, contrato);

		return camposFormateados;
	}

	private void rellenarGestor(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, ContratoVO contrato) {
		if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_GESTOR)) {
			int inicioNombre = contrato.getColegiado().getUsuario().getApellidosNombre().indexOf(", ");
			String nombre = contrato.getColegiado().getUsuario().getApellidosNombre().substring(inicioNombre + 2, contrato.getColegiado().getUsuario().getApellidosNombre().length());
			String apellidos = contrato.getColegiado().getUsuario().getApellidosNombre().substring(0, inicioNombre);
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_GESTOR, nombre + " " + apellidos, false, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_DNI_GESTOR)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_GESTOR, contrato.getColegiado().getUsuario().getNif(), false, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_COLEGIO)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_COLEGIO, contrato.getColegio().getColegio(), false, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CALIDAD)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CALIDAD, "GESTOR ADTVO.", false, false, ConstantesPDF._10));
		}
	}

	private void rellenarTitular(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, DuplicadoPermisoConducirVO duplicadoPermisoConducirVO) {
		IntervinienteIntergaVO titular = intervinientePermInterDao.getIntervinienteTraficoTipo(duplicadoPermisoConducirVO.getNumExpediente(), TipoTramiteInterga.Duplicado_Permiso_Conducir
				.getValorEnum());

		if (titular != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR)) {
				String nombreApellidos = (titular.getPersona().getNombre() != null ? titular.getPersona().getNombre() : "") + " " + (titular.getPersona().getApellido1RazonSocial() != null ? titular
						.getPersona().getApellido1RazonSocial() : "") + " " + (titular.getPersona().getApellido2() != null ? titular.getPersona().getApellido2() : "");
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_TITULAR, nombreApellidos, false, false, ConstantesPDF._10));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR, titular.getPersona().getId().getNif(), false, false, ConstantesPDF._10));
			}
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CLASE_PERMISO)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CLASE_PERMISO, TiposDuplicadosPermisos.convertirNombre(duplicadoPermisoConducirVO.getTipoDuplicado()), false, false,
					ConstantesPDF._10));
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
		return gestorPropiedades.valorPropertie("trafico.rutaSrc.plantillasPDF") + TipoImpreso.DeclaracionResponsabilidadColegiadoConducir.getNombreEnum();
	}
}
