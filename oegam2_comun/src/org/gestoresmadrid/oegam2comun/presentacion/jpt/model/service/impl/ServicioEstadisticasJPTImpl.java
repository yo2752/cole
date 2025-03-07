package org.gestoresmadrid.oegam2comun.presentacion.jpt.model.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.presentacion.jpt.model.dao.EstadisticasJPTDao;
import org.gestoresmadrid.core.presentacion.jpt.model.dao.TipoEstadisticasJPTDao;
import org.gestoresmadrid.core.presentacion.jpt.model.vo.EstadisticasJPTVO;
import org.gestoresmadrid.core.presentacion.jpt.model.vo.TipoEstadisticasJPTVO;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.TipoEstadisticas;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.model.service.ServicioEstadisticasJPT;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.view.beans.EstadisticasJPTBean;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.view.beans.TipoEstadisticasJPTBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xhtmlrenderer.pdf.ITextRenderer;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.propiedades.PropertiesConstantes;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioEstadisticasJPTImpl implements ServicioEstadisticasJPT {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEstadisticasJPTImpl.class);

	@Autowired
	private TipoEstadisticasJPTDao tipoEstadisticasJPTDaoImpl;

	@Autowired
	private EstadisticasJPTDao estadisticasJPTDao;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioUsuario servicioUsuarioImpl;

	@Autowired
	private Conversor conversor;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	@Override
	@Transactional(readOnly=true)
	public ArrayList<TipoEstadisticasJPTBean> getlistaTipoEstadisticasJPT() {
		List<TipoEstadisticasJPTVO> listaTipoEstadisticasJPTVOs = tipoEstadisticasJPTDaoImpl.getListaTipoEstadisticas(true);
		return convertirListaTipoEstadisticasJPTVOToBean(listaTipoEstadisticasJPTVOs);
	}

	@Override
	@Transactional
	public ArrayList<TipoEstadisticasJPTBean> getlistaTipoEstadisticasJPTNoVisibles() {
		List<TipoEstadisticasJPTVO> listaTipoEstadisticasJPTVOs = tipoEstadisticasJPTDaoImpl.getListaTipoEstadisticas(false);
		return convertirListaTipoEstadisticasJPTVOToBean(listaTipoEstadisticasJPTVOs);
	}

	private ArrayList<TipoEstadisticasJPTBean> convertirListaTipoEstadisticasJPTVOToBean(List<TipoEstadisticasJPTVO> listaTipoEstadisticasJPTVOs) {
		if (listaTipoEstadisticasJPTVOs != null && listaTipoEstadisticasJPTVOs.size() > 0) {
			ArrayList<TipoEstadisticasJPTBean> listaEstadisticasJPTBeans = new ArrayList<TipoEstadisticasJPTBean>();
			for (TipoEstadisticasJPTVO tipoEstadisticasJPTVO : listaTipoEstadisticasJPTVOs) {
				TipoEstadisticasJPTBean tipoEstadisticasJPTBean = conversor.transform(tipoEstadisticasJPTVO, TipoEstadisticasJPTBean.class);
				if (tipoEstadisticasJPTBean != null && tipoEstadisticasJPTBean.getCantidad() == null) {
					tipoEstadisticasJPTBean.setCantidad("0");
				}
				listaEstadisticasJPTBeans.add(tipoEstadisticasJPTBean);
			}
			return listaEstadisticasJPTBeans;
		}
		return null;
	}

	@Override
	public ResultBean comprobarBean(EstadisticasJPTBean estadisticasPresentacionJPTBean) {
		if (estadisticasPresentacionJPTBean != null) {
			List<TipoEstadisticasJPTBean> listaTipoEstadisticasJPTBeans = estadisticasPresentacionJPTBean.getListadoTipoEstadisticasJPTBean();
			if (estadisticasPresentacionJPTBean.getNumColegiado() == 0) {
				return new ResultBean(true, "Debe rellenar el numero de colegiado.");
			}

			if (estadisticasPresentacionJPTBean.getFecha().getDiaInicio() == null || estadisticasPresentacionJPTBean.getFecha().getDiaInicio().isEmpty()) {
				return new ResultBean(true, "Debe rellenar el dia para la fecha de las estadísticas JPT.");
			} else if (estadisticasPresentacionJPTBean.getFecha().getMesInicio() == null || estadisticasPresentacionJPTBean.getFecha().getMesInicio().isEmpty()) {
				return new ResultBean(true, "Debe rellenar el mes para la fecha de las estadísticas JPT.");
			} else if (estadisticasPresentacionJPTBean.getFecha().getAnioInicio() == null || estadisticasPresentacionJPTBean.getFecha().getAnioInicio().isEmpty()) {
				return new ResultBean(true, "Debe rellenar el año para la fecha de las estadísticas JPT.");
			}

			if (estadisticasPresentacionJPTBean.getJefaturaJPT() == null || estadisticasPresentacionJPTBean.getJefaturaJPT().isEmpty()) {
				return new ResultBean(true, "Debe rellenar la Jefatura Provincial para las estadísticas JPT.");
			}

			if (listaTipoEstadisticasJPTBeans != null && listaTipoEstadisticasJPTBeans.size() > 0) {
				for (TipoEstadisticasJPTBean tipoEstadisticasJPTBean : listaTipoEstadisticasJPTBeans) {
					if (!"0".equals(tipoEstadisticasJPTBean.getCantidad())) {
						return null;
					}
				}
				return new ResultBean(true, "Debe rellenar la cantidad para algún tipo de estadísticas JPT.");
			}

		} else {
			return new ResultBean(true, "Debe rellenar algun campo de las Estadísticas para poder obtener el pdf.");
		}
		return null;
	}

	@Override
	@Transactional
	public ResultBean guardarEstadisticas(EstadisticasJPTBean estadisticasPresentacionJPTBean) {
		ResultBean resultado = null;
		List<TipoEstadisticasJPTBean> listaTipoEstadisticasJPTBeans = estadisticasPresentacionJPTBean.getListadoTipoEstadisticasJPTBean();
		EstadisticasJPTVO estadisticasJPTVO = null;
		try {
			for (TipoEstadisticasJPTBean tipoEstadisticasJPTBean : listaTipoEstadisticasJPTBeans) {
				if (!tipoEstadisticasJPTBean.getCantidad().equals("0")) {
					estadisticasJPTVO = setEstadisticasJptVoFromTipoEstadisticasJPTBean(estadisticasPresentacionJPTBean, tipoEstadisticasJPTBean);
					estadisticasJPTDao.guardar(estadisticasJPTVO);
				}
			}
		} catch (Exception e) {
			Log.error("Error guardando estadisticas jpt: " + e.toString());
			resultado = new ResultBean(true, "Ha sucedido un error cuando se ha intentado dar de alta la Estadísticas JPT.");
		}
		return resultado;
	}

	private EstadisticasJPTVO setEstadisticasJptVoFromTipoEstadisticasJPTBean(EstadisticasJPTBean estadisticasJptBean, TipoEstadisticasJPTBean tipoEstadisticasJPTBean) {
		EstadisticasJPTVO estadisticasJPTVO = new EstadisticasJPTVO();
		estadisticasJPTVO.setNumColegiado(new DecimalFormat("0000").format(estadisticasJptBean.getNumColegiado()));

		estadisticasJPTVO.setFecha(utilesFecha.convertirFechaFraccionadaEnDate(estadisticasJptBean.getFecha()));
		estadisticasJPTVO.setCantidad(Long.parseLong(tipoEstadisticasJPTBean.getCantidad()));
		UsuarioVO usuarioVO = new UsuarioVO();
		usuarioVO.setIdUsuario(utilesColegiado.getIdUsuarioSession());
		estadisticasJPTVO.setUsuarioVO(usuarioVO);
		estadisticasJPTVO.setFechaAlta(utilesFecha.convertirFechaEnDate(utilesFecha.getFechaHoraActualLEG()));

		TipoEstadisticasJPTVO tipoEstadisticasJPTVO = new TipoEstadisticasJPTVO();
		tipoEstadisticasJPTVO.setIdTipoEstadistica(tipoEstadisticasJPTBean.getIdTipoEstadistica());

		estadisticasJPTVO.setTipoEstadisticasJPTVO(tipoEstadisticasJPTVO);
		estadisticasJPTVO.setJefaturaJpt(estadisticasJptBean.getJefaturaJPT());

		return estadisticasJPTVO;
	}

	@Override
	@Transactional
	public byte[] generarEstadisticas(Fecha fechaEstadistica, String jefaturaJpt) throws ParseException, Exception, OegamExcepcion {
		boolean generado = false;
		StringBuffer resultadoHtmlAgruVehiculos = new StringBuffer();
		List<TipoEstadisticasJPTVO> listaTipoEstadisticasJPTVOs = tipoEstadisticasJPTDaoImpl.getListaTipoEstadisticasFichero();
		List<TipoEstadisticasJPTVO> listaTipoEstadisticasJPTPistolaVOs = tipoEstadisticasJPTDaoImpl.getListaTipoEstadisticas(false);
		long totalEstadisticas = 0;
		String descJefatura = JefaturasJPTEnum.convertirJefatura(jefaturaJpt);

		String rutaImagen = "file:///" + gestorPropiedades.valorPropertie(PropertiesConstantes.RUTA_DIRECTORIO_DATOS) + "plantillasPDF/documentosBase/logo_colegio.gif";

		resultadoHtmlAgruVehiculos.append("<HTML>");
		resultadoHtmlAgruVehiculos.append("<HEAD>");
		resultadoHtmlAgruVehiculos.append("<br/><img src='" + rutaImagen + "'/><br/>");
		resultadoHtmlAgruVehiculos.append("<TITLE><span style=\"font-size:20pt;font-family:Tunga;margin-left:20px;\"> " + "<table width='100%'><tr align='center'><th> RESUMEN DE LA JEFATURA DE "
				+ descJefatura.toUpperCase() + "</th> </tr></table></span></TITLE><br/>");
		resultadoHtmlAgruVehiculos.append("</HEAD>");
		resultadoHtmlAgruVehiculos.append("<BODY>");
		resultadoHtmlAgruVehiculos.append("<span style=\"font-size:14pt;font-family:Tunga;margin-left:20px;\"> " + "<table width='100%'><tr align='center'><th>Datos del día "
				+ utilesFecha.formatoFecha(ServicioEstadisticasJPT.sFormatoFechaIntervalo, fechaEstadistica.getDate()) + "</th></tr></table></span><br/>");
		resultadoHtmlAgruVehiculos.append("<table border='1' width='100%'> <tr> " + "<th align='center'>Vehículo</th> " + "<th align='center'>Matrículas</th> " + "<th align='center'>Nº Gestores</th>"
				+ "</tr>");
		Long cantidad;
		for (TipoEstadisticasJPTVO tipoEstadisticasJPTVO : listaTipoEstadisticasJPTVOs) {
			cantidad = generaTabla(fechaEstadistica, tipoEstadisticasJPTVO, jefaturaJpt, resultadoHtmlAgruVehiculos);
			if (!cantidad.equals(0)) {
				totalEstadisticas += cantidad;
				generado = true;
			}
		}

		for (TipoEstadisticasJPTVO tipoEstadisticasJPTVO : listaTipoEstadisticasJPTPistolaVOs) {
			cantidad = generaTablaPistola(fechaEstadistica, tipoEstadisticasJPTVO, jefaturaJpt, resultadoHtmlAgruVehiculos);
			if (!cantidad.equals(0)) {
				totalEstadisticas += cantidad;
				generado = true;
			}
		}

		generarTotalEstadisticas(totalEstadisticas, resultadoHtmlAgruVehiculos);

		resultadoHtmlAgruVehiculos.append("</table>");
		resultadoHtmlAgruVehiculos.append("</BODY></HTML>");
		if (generado) {
			byte[] pdf = generarPDFTemporal(resultadoHtmlAgruVehiculos);
			try {
				guardarDocumento(pdf, descJefatura);
			} catch (Exception e) {
				log.error("No se ha guardado el fichero de estadísticas: " + e.getMessage());
			}
			return pdf;
		}
		return null;
	}

	private void generarTotalEstadisticas(Long totalEstadisticas, StringBuffer resultadoHtmlAgruVehiculos) {
		resultadoHtmlAgruVehiculos.append("<tr>");
		resultadoHtmlAgruVehiculos.append("<th align='center'>");
		resultadoHtmlAgruVehiculos.append("TOTAL");
		resultadoHtmlAgruVehiculos.append("</th>");
		resultadoHtmlAgruVehiculos.append("<th align='center'>");
		resultadoHtmlAgruVehiculos.append(String.valueOf(totalEstadisticas));
		resultadoHtmlAgruVehiculos.append("</th>");
		resultadoHtmlAgruVehiculos.append("<th style='background-color:#D3D3D3'>");
		resultadoHtmlAgruVehiculos.append("</th>");
		resultadoHtmlAgruVehiculos.append("</tr>");
	}

	private Long generaTablaPistola(Fecha fechaEstadistica, TipoEstadisticasJPTVO tipoEstadisticasJPTVO, String jefaturaJpt, StringBuffer resultadoHtmlAgruVehiculos) throws ParseException, Exception {
		String[] carpetas = TipoEstadisticas.convertirYerbabuena(tipoEstadisticasJPTVO.getIdTipoEstadistica().toString());
		Long cantidadTotal = (long) 0;
		int numerosGestoresDistintos = 0;
		if (carpetas != null && carpetas.length > 0) {
			for (String carpeta : carpetas) {
				if (!carpeta.isEmpty()) {
					Long cantidad = (long) servicioTramiteTrafico.getCantidadTipoEstadistica(fechaEstadistica.getDate(), carpeta, jefaturaJpt);
					if (cantidad != 0) {
						numerosGestoresDistintos += servicioTramiteTrafico.getNumColegiadosDistintos(fechaEstadistica.getDate(), carpeta, jefaturaJpt);
						cantidadTotal += cantidad;
					}
				}
			}
			if (cantidadTotal != 0) {
				resultadoHtmlAgruVehiculos.append("<tr>");
				resultadoHtmlAgruVehiculos.append("<td align='center'>");
				resultadoHtmlAgruVehiculos.append(tipoEstadisticasJPTVO.getDescripcion());
				resultadoHtmlAgruVehiculos.append("</td>");
				resultadoHtmlAgruVehiculos.append("<td align='center'>");
				resultadoHtmlAgruVehiculos.append(String.valueOf(cantidadTotal));
				resultadoHtmlAgruVehiculos.append("</td>");
				resultadoHtmlAgruVehiculos.append("<td align='center'>");
				resultadoHtmlAgruVehiculos.append(String.valueOf(numerosGestoresDistintos));
				resultadoHtmlAgruVehiculos.append("</td>");
				resultadoHtmlAgruVehiculos.append("</tr>");
			}
		}
		return cantidadTotal;
	}

	private Long generaTabla(Fecha fechaEstadistica, TipoEstadisticasJPTVO tipoEstadisticasJPTVO, String jefaturaJpt, StringBuffer resultadoHtmlAgruVehiculos) throws ParseException {
		int numerosGestoresDistintos = estadisticasJPTDao.getNumColegiadosDistintos(fechaEstadistica.getDate(), tipoEstadisticasJPTVO.getIdTipoEstadistica(), jefaturaJpt);
		Long cantidad = (long) 0;
		if (numerosGestoresDistintos != 0) {
			cantidad = estadisticasJPTDao.getCantidadTipoEstadistica(fechaEstadistica.getDate(), tipoEstadisticasJPTVO.getIdTipoEstadistica(), jefaturaJpt);
			resultadoHtmlAgruVehiculos.append("<tr>");
			resultadoHtmlAgruVehiculos.append("<td align='center'>");
			resultadoHtmlAgruVehiculos.append(tipoEstadisticasJPTVO.getDescripcion());
			resultadoHtmlAgruVehiculos.append("</td>");
			resultadoHtmlAgruVehiculos.append("<td align='center'>");
			resultadoHtmlAgruVehiculos.append(cantidad.toString());
			resultadoHtmlAgruVehiculos.append("</td>");
			resultadoHtmlAgruVehiculos.append("<td align='center'>");
			resultadoHtmlAgruVehiculos.append(String.valueOf(numerosGestoresDistintos));
			resultadoHtmlAgruVehiculos.append("</td>");
			resultadoHtmlAgruVehiculos.append("</tr>");
		}
		return cantidad;
	}

	private byte[] generarPDFTemporal(StringBuffer resultadoHtmlAgruVehiculos) {
		OutputStream outputStream = null;
		byte[] result = null;
		try {
			ITextRenderer iTextRenderer = new ITextRenderer();
			iTextRenderer.setDocumentFromString(resultadoHtmlAgruVehiculos.toString());
			iTextRenderer.layout();
			outputStream = new ByteArrayOutputStream();
			iTextRenderer.createPDF(outputStream);
			result = ((ByteArrayOutputStream) outputStream).toByteArray();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error("No se pudo cerrar el outputstream", e);
				}
			}
		}
		return result;
	}

	private void guardarDocumento(byte[] pdf, String descJefatura) throws Exception, OegamExcepcion {
		FicheroBean ficheroBean = new FicheroBean();
		String fecha = utilesFecha.formatoFecha("ddMMyyyyHHmmss", new Date());
		ficheroBean.setFicheroByte(pdf);
		ficheroBean.setTipoDocumento(ConstantesGestorFicheros.ESTADISTICASJEFATURA);
		ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
		ficheroBean.setFecha(utilesFecha.getFechaActual());
		ficheroBean.setSobreescribir(true);
		ficheroBean.setNombreDocumento("estadisticas_" + descJefatura + "_jpt_" + fecha);

		gestorDocumentos.guardarByte(ficheroBean);
	}

	@Override
	@Transactional
	public void getEstadisticasConIncidencias(EstadisticasJPTBean estadisticasJPTBean) {
		List<TipoEstadisticasJPTBean> listaIncidencias = convertirListaTipoEstadisticasJPTVOToBean(tipoEstadisticasJPTDaoImpl.getListaIncidenciasEstadisticasJpt());

		List<TipoEstadisticasJPTBean> listaEstadisticasJptBean = estadisticasJPTBean.getListadoTipoEstadisticasJPTBean();

		if (estadisticasJPTBean.getIncidencias()) {
			for (TipoEstadisticasJPTBean tipoEstadisticasJPTBean : listaIncidencias) {
				listaEstadisticasJptBean.add(tipoEstadisticasJPTBean);
			}
		} else {
			for (TipoEstadisticasJPTBean tipoEstadisticasJPTBean : listaIncidencias) {
				for (int i = 0; i < listaEstadisticasJptBean.size(); i++) {
					TipoEstadisticasJPTBean tipoEstadisticasJPTAuxBean = listaEstadisticasJptBean.get(i);
					if (tipoEstadisticasJPTBean.getIdTipoEstadistica().equals(tipoEstadisticasJPTAuxBean.getIdTipoEstadistica())) {
						listaEstadisticasJptBean.remove(i);
						break;
					}
				}
			}
		}
		estadisticasJPTBean.setListadoTipoEstadisticasJPTBean(listaEstadisticasJptBean);
	}

	@Override
	@Transactional(readOnly = true)
	public String getJefaturaProvincialPorUsuario(long idUsuario) {
		return servicioUsuarioImpl.getJefaturaProvincial(idUsuario);
	}

	@Override
	public ResultBean comprobarFechaGeneracionEstadisticas(Fecha fechaEstadistica) {
		ResultBean resultBean = null;
		try {
			String sFecha = gestorPropiedades.valorPropertie(ServicioEstadisticasJPT.propertieFechaJpt);
			Date fechaGeneracion = utilesFecha.getFechaDate(sFecha);
			Date fechaEsta = utilesFecha.convertirFechaEnDate(fechaEstadistica);
			int resultado = utilesFecha.compararFechaDate(fechaEsta, fechaGeneracion);
			if (resultado == 2) {
				resultBean = new ResultBean(true, "Para poder generar estadísticas la fecha tiene que ser mayor que la fecha: " + utilesFecha.formatoFecha(ServicioEstadisticasJPT.sFormatoFechaIntervalo, fechaGeneracion));
			}
		} catch (ParseException e) {
			resultBean = new ResultBean(true, "Ha surgido un error a la hora de comparar la fecha de permiso para la generación de estadísticas con la fecha de las estadísticas.");
		}
		return resultBean;
	}

	public TipoEstadisticasJPTDao getTipoEstadisticasJPTDaoImpl() {
		return tipoEstadisticasJPTDaoImpl;
	}

	public void setTipoEstadisticasJPTDaoImpl(TipoEstadisticasJPTDao tipoEstadisticasJPTDaoImpl) {
		this.tipoEstadisticasJPTDaoImpl = tipoEstadisticasJPTDaoImpl;
	}

	public EstadisticasJPTDao getEstadisticasJPTDao() {
		return estadisticasJPTDao;
	}

	public void setEstadisticasJPTDao(EstadisticasJPTDao estadisticasJPTDao) {
		this.estadisticasJPTDao = estadisticasJPTDao;
	}

	public ServicioTramiteTrafico getServicioTramiteTrafico() {
		return servicioTramiteTrafico;
	}

	public void setServicioTramiteTrafico(ServicioTramiteTrafico servicioTramiteTrafico) {
		this.servicioTramiteTrafico = servicioTramiteTrafico;
	}

	public ServicioUsuario getServicioUsuarioImpl() {
		return servicioUsuarioImpl;
	}

	public void setServicioUsuarioImpl(ServicioUsuario servicioUsuarioImpl) {
		this.servicioUsuarioImpl = servicioUsuarioImpl;
	}
}
