package org.gestoresmadrid.oegamImpresion.solicitudes.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.paises.model.vo.PaisVO;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.dao.DuplicadoPermisoConducirDao;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.enumerados.TiposDuplicadosPermisos;
import org.gestoresmadrid.core.trafico.duplicado.permiso.conducir.model.vo.DuplicadoPermisoConducirVO;
import org.gestoresmadrid.core.trafico.interga.model.dao.IntervinienteIntergaDao;
import org.gestoresmadrid.core.trafico.interga.model.enumerados.TipoTramiteInterga;
import org.gestoresmadrid.core.trafico.interga.model.vo.IntervinienteIntergaVO;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.pais.service.ServicioComunPais;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoImpresionBean;
import org.gestoresmadrid.oegamImpresion.solicitudes.service.ServicioSolicitudesDPC;
import org.gestoresmadrid.oegamImpresion.utiles.CampoPdfBean;
import org.gestoresmadrid.oegamImpresion.utiles.ConstantesPDF;
import org.gestoresmadrid.oegamImpresion.utiles.PdfMaker;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.sf.jasperreports.engine.JRException;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioSolicitudesDPCImpl implements ServicioSolicitudesDPC {

	private static final long serialVersionUID = -7885400746605630913L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioSolicitudesDPCImpl.class);

	@Autowired
	DuplicadoPermisoConducirDao duplicadoPermisoConducirDao;

	@Autowired
	IntervinienteIntergaDao intervinientePermInterDao;

	@Autowired
	ServicioComunDireccion servicioDireccion;

	@Autowired
	ServicioComunPais servicioComunPais;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public ResultadoImpresionBean imprimirSolicitudDPC(List<BigDecimal> listaExpediente) {
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
						log.error("Error al imprimir la solicitud. Número expediente : " + numExpediente, e);
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
			log.error("Ha sucedido un error a la hora de imprimir la solicitud, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir la solicitud.");
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

		rellenarTitular(camposPlantilla, camposFormateados, duplicadoPermisoConducirVO);

		rellenarTasa(camposPlantilla, camposFormateados, duplicadoPermisoConducirVO);

		rellenarTipoDuplicado(camposPlantilla, camposFormateados, duplicadoPermisoConducirVO);

		rellenarDatosFinal(camposPlantilla, camposFormateados, contrato);

		return camposFormateados;
	}

	private void rellenarTitular(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, DuplicadoPermisoConducirVO duplicadoPermisoConducirVO) {
		IntervinienteIntergaVO titular = intervinientePermInterDao.getIntervinienteTraficoTipo(duplicadoPermisoConducirVO.getNumExpediente(), TipoTramiteInterga.Duplicado_Permiso_Conducir
				.getValorEnum());

		if (titular != null) {
			if (camposPlantilla.contains(ConstantesPDF.ID_DNI_TITULAR)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DNI_TITULAR, titular.getPersona().getId().getNif(), false, false, ConstantesPDF._10));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_TITULAR)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_TITULAR, titular.getPersona().getNombre() != null ? titular.getPersona().getNombre() : "", false, false,
						ConstantesPDF._10));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_APELLIDO_1)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_APELLIDO_1, titular.getPersona().getApellido1RazonSocial(), false, false, ConstantesPDF._10));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_APELLIDO_2)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_APELLIDO_2, titular.getPersona().getApellido2() != null ? titular.getPersona().getApellido2() : "", false, false,
						ConstantesPDF._10));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_EMAIL_TITULAR)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_EMAIL_TITULAR, titular.getPersona().getNombre(), false, false, ConstantesPDF._10));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_NACIMIENTO_TITULAR)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FECHA_NACIMIENTO_TITULAR, utilesFecha.formatoFecha(titular.getPersona().getFechaNacimiento()), false, false,
						ConstantesPDF._10));
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_PAIS_NACIMIENTO_TITULAR)) {
				if (StringUtils.isNotBlank(titular.getPersona().getPaisNacimiento())) {
					PaisVO pais = servicioComunPais.getPais(titular.getPersona().getPaisNacimiento());
					if (pais != null) {
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PAIS_NACIMIENTO_TITULAR, pais.getNombre().toUpperCase(), false, false, ConstantesPDF._10));
					}
				}
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_NACIONALIDAD_TITULAR)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NACIONALIDAD_TITULAR, titular.getPersona().getNacionalidad(), false, false, ConstantesPDF._10));
			}

			if (titular.getDireccion() != null) {
				rellenarDireccion(camposPlantilla, camposFormateados, titular.getDireccion());
			}
		}
	}

	private void rellenarDireccion(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, DireccionVO direccion) {
		if (camposPlantilla.contains(ConstantesPDF.ID_TIPO_VIA_TITULAR)) {
			String tipoViaNombre = servicioDireccion.obtenerNombreTipoVia(direccion.getIdTipoVia());
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_VIA_TITULAR, tipoViaNombre != null ? tipoViaNombre : "", false, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CALLE_TITULAR)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CALLE_TITULAR, direccion.getNombreVia() != null ? direccion.getNombreVia() : "", false, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_NUM_DOMICILIO_TITULAR)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NUM_DOMICILIO_TITULAR, direccion.getNumero(), false, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_BLOQUE_DOMICILIO_TITULAR)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_BLOQUE_DOMICILIO_TITULAR, direccion.getBloque() != null ? direccion.getBloque() : "", false, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_PORTAL_DOMICILIO_TITULAR)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PORTAL_DOMICILIO_TITULAR, direccion.getPortal() != null ? direccion.getPortal() : "", false, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_ESCALERA_DOMICILIO_TITULAR)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ESCALERA_DOMICILIO_TITULAR, direccion.getEscalera() != null ? direccion.getEscalera() : "", false, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_PISO_DOMICILIO_TITULAR)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PISO_DOMICILIO_TITULAR, direccion.getPlanta() != null ? direccion.getPlanta() : "", false, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_PUERTA_DOMICILIO_TITULAR)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PUERTA_DOMICILIO_TITULAR, direccion.getPuerta() != null ? direccion.getPuerta() : "", false, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_KM_DOMICILIO_TITULAR)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_KM_DOMICILIO_TITULAR, direccion.getKm() != null ? direccion.getKm().toString() : "", false, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_CP_TITULAR)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CP_TITULAR, direccion.getCodPostal() != null ? direccion.getCodPostal() : "", false, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_PROVINCIA_TITULAR)) {
			String nombreProvincia = servicioDireccion.obtenerNombreProvincia(direccion.getIdProvincia());
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PROVINCIA_TITULAR, nombreProvincia, false, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO_TITULAR)) {
			String nombreMunicipio = servicioDireccion.obtenerNombreMunicipio(direccion.getIdMunicipio(), direccion.getIdProvincia());
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO_TITULAR, nombreMunicipio, false, false, ConstantesPDF._10));
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_PUEBLO_TITULAR)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PUEBLO_TITULAR, direccion.getPueblo() != null ? direccion.getPueblo() : "", false, false, ConstantesPDF._10));
		}
	}

	private void rellenarTasa(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, DuplicadoPermisoConducirVO duplicadoPermisoConducirVO) {
		if (camposPlantilla.contains(ConstantesPDF.ID_TASA)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TASA, duplicadoPermisoConducirVO.getCodigoTasa() != null ? duplicadoPermisoConducirVO.getCodigoTasa() : "", false, false,
					ConstantesPDF._10));
		}
	}

	private void rellenarTipoDuplicado(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, DuplicadoPermisoConducirVO duplicadoPermisoConducirVO) {
		if (duplicadoPermisoConducirVO.getTipoDuplicado() != null) {
			if (TiposDuplicadosPermisos.PERDIDA.getValorEnum().equals(duplicadoPermisoConducirVO.getTipoDuplicado())) {
				if (camposPlantilla.contains(ConstantesPDF.ID_EXTRAVIO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_EXTRAVIO, "X", false, false, ConstantesPDF._8));
				}
			} else if (TiposDuplicadosPermisos.DETERIORO.getValorEnum().equals(duplicadoPermisoConducirVO.getTipoDuplicado())) {
				if (camposPlantilla.contains(ConstantesPDF.ID_DETERIORO)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_DETERIORO, "X", false, false, ConstantesPDF._8));
				}
			} else if (TiposDuplicadosPermisos.SUSTRACCION.getValorEnum().equals(duplicadoPermisoConducirVO.getTipoDuplicado())) {
				if (camposPlantilla.contains(ConstantesPDF.ID_SUSTRACCION)) {
					camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_SUSTRACCION, "X", false, false, ConstantesPDF._8));
				}
			}
		}
	}

	private void rellenarDatosFinal(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, ContratoVO contrato) {
		if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_APELLIDOS_GESTOR)) {
			int inicioNombre = contrato.getColegiado().getUsuario().getApellidosNombre().indexOf(", ");
			String nombre = contrato.getColegiado().getUsuario().getApellidosNombre().substring(inicioNombre + 2, contrato.getColegiado().getUsuario().getApellidosNombre().length());
			String apellidos = contrato.getColegiado().getUsuario().getApellidosNombre().substring(0, inicioNombre);
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_NOMBRE_APELLIDOS_GESTOR, nombre + " " + apellidos, false, false, ConstantesPDF._10));
		}
	}

	private String getRuta() {
		return gestorPropiedades.valorPropertie("trafico.rutaSrc.plantillasPDF") + TipoImpreso.SolicitudDuplicadoPermisoConducir.getNombreEnum();
	}
}
