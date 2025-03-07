package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.dao.DocPermDistItvDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsumoMaterial;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioEmailEstadisticasDistintivos;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.ConsumoMaterialDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioEmailEstadisticasDistintivosImpl implements ServicioEmailEstadisticasDistintivos {

	private static final long serialVersionUID = -6781899702880676272L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioEmailEstadisticasDistintivosImpl.class);

	private static final String RECIPIENT = "estadisticas.stock.correo.recipient";
	private static final String SUBJECT = "estadisticas.stock.correo.subject";

	@Autowired
	ServicioConsumoMaterial servicioConsumoMaterial;

	@Autowired
	ServicioCorreo servicioCorreo;

	@Autowired
	DocPermDistItvDao docPermDistItvDao;

	@Autowired
	TramiteTraficoDao tramiteTraficoDao;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public ResultBean executeEmailEstadisticasDistintivos() {
		ResultBean result = new ResultBean(false);
		try {
			Date fechaEstadistica = utilesFecha.getPrimerLaborableAnterior().getDate();

			boolean agregar = "SI".equals(gestorPropiedades.valorPropertie("impresion.distintivosPermisos.jefatura")) ? true : false;

			HashMap<String, List<ConsumoMaterialDto>> consumos = servicioConsumoMaterial.obtenerConsumoMaterial(fechaEstadistica);

			ResultBean resultCorreo = null;
			if (consumos != null && consumos.size() > 0) {
				resultCorreo = enviarCorreo(fechaEstadistica, consumos, agregar);
			} else {
				resultCorreo = enviarCorreoNoHayDatos(fechaEstadistica);
			}

			if (resultCorreo.getError()) {
				result.setError(true);
			} else {
				result.setError(false);
			}

		} catch (Throwable e) {
			log.error("Ha ocurrido un error ha la hora de generar el mail con las estadisticas de impresiones de permisos, fichas y distintivos, error:  ",e);
			result.setError(true);
		}

		return result;
	}

	private ResultBean enviarCorreoNoHayDatos(Date fechaEstadistica) {
		String subject = null;
		String recipient = null;
		String direccionesOcultas = null;

		ResultBean resultado = null;
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		StringBuilder texto = null;

		try {
			subject = gestorPropiedades.valorPropertie(SUBJECT);
			recipient = gestorPropiedades.valorPropertie(RECIPIENT);

			texto = new StringBuilder("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");
			texto.append("Desde la Oficina Electrónica de Gestión Administrativa (OEGAM), le comunicamos ");
			texto.append(" la estadística de materiales impresos: ").append("<BR>").append("Fecha Estadística: " + format.format(fechaEstadistica)).append("<BR>");
			texto.append("<h3>No ha habido ninguna impresión</h3>");

			resultado = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, recipient, null, direccionesOcultas, null);
			if (resultado.getError()) {
				log.error("No se enviaron correos de estadisticas");
				for (String textoError : resultado.getListaMensajes()) {
					log.error(textoError);
				}
			}

		} catch (OegamExcepcion | IOException e) {
			log.error("No se enviaron correos de estadisticas", e);
		}

		return resultado;
	}

	private ResultBean enviarCorreo(Date fechaEstadistica, HashMap<String, List<ConsumoMaterialDto>> consumos, boolean agregar) {
		String subject = null;
		String recipient = null;
		String direccionesOcultas = null;

		ResultBean resultado = null;
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		StringBuilder texto = null;

		try {
			subject = gestorPropiedades.valorPropertie(SUBJECT);
			recipient = gestorPropiedades.valorPropertie(RECIPIENT);

			texto = new StringBuilder("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");
			texto.append("Desde la Oficina Electrónica de Gestión Administrativa (OEGAM), le comunicamos ");
			texto.append(" la estadística de materiales impresos: ").append("<BR>").append("Fecha Estadística: " + format.format(fechaEstadistica)).append("<BR>");

			for (String key : consumos.keySet()) {
				JefaturaTraficoVO jtVO = servicioConsumoMaterial.getNombreJefaturaForJefatura(key);
				texto.append("<BR>");
				texto.append("<thead>");
				texto.append("<table border=\"1\">");
				texto.append("<tr bgcolor=\"#a52642\" style=\"text-align=center\" >");
				texto.append("<th colspan=\"3\">");
				texto.append("<font color=\"#ffffff\" >");
				texto.append(jtVO.getDescripcion());
				texto.append("</font>");
				texto.append("</th>");
				texto.append("</tr>");

				texto.append("<tr bgcolor=\"#a52642\" >");
				texto.append("<th><font color=\"#ffffff\" >Material</font></th>");
				texto.append("<th><font color=\"#ffffff\" >Tipo</font></th>");
				texto.append("<th><font color=\"#ffffff\" >Consumo</font></th>");
				texto.append("</tr>");
				texto.append("</thead>");

				List<ConsumoMaterialDto> consumoDtos = consumos.get(key);
				Long sumaConsumos = 0L;
				for (ConsumoMaterialDto item : consumoDtos) {
					texto.append("<tr>");
					texto.append("<font color=\"#000000\" >");
					texto.append("<td>" + item.getNombreMaterial() + "</td>");
					texto.append("<td align=\"center\">" + item.getTipoMaterial() + "</td>");
					texto.append("<td align=\"center\">" + item.getConsumo() + "</td>");
					texto.append("</font>");
					texto.append("</tr>");
					sumaConsumos += item.getConsumo();
				}
				texto.append("<tr bgcolor=\"#a52642\" >");
				texto.append("<td colspan=\"2\"><font color=\"#ffffff\" >Total</font></td>");
				texto.append("<td align=\"center\"><font color=\"#ffffff\" >" + sumaConsumos + "</font></td>");
				texto.append("</tr>");
				texto.append("</table>");
			}
			texto.append("<BR>");

			StringBuffer resumen = resumenPorJefaturaTipo(fechaEstadistica);
			if (resumen != null) {
				texto.append(resumen);
			}

			resultado = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, recipient, null, direccionesOcultas, null);
			if (resultado.getError()) {
				log.error("No se enviaron correos de estadisticas");
				for (String textoError : resultado.getListaMensajes()) {
					log.error(textoError);
				}
			}
		} catch (OegamExcepcion | IOException e) {
			log.error("No se enviaron correos de estadisticas", e);
		}

		return resultado;
	}

	private StringBuffer resumenPorJefaturaTipo(Date fechaEstadistica) {
		boolean hayDatos = false;
		List<Long> idDocPermDistItv = docPermDistItvDao.getIdsImpresionesPorFecha(fechaEstadistica);
		StringBuffer sb = new StringBuffer("Cantidad de permisos y fichas técnicas impresos.<br>");
		if (idDocPermDistItv != null && !idDocPermDistItv.isEmpty()) {
			List<Object[]> listaTramitesFichas = tramiteTraficoDao.getCountNumTramitesFichasPorJefatura(idDocPermDistItv);
			StringBuffer sbFichas = crearTableResumen(listaTramitesFichas, fechaEstadistica, false);
			if (sbFichas != null) {
				sb.append(sbFichas);
				sb.append("<BR>");
				hayDatos = true;
			}
		}

		if (idDocPermDistItv != null && !idDocPermDistItv.isEmpty()) {
			List<Object[]> listaTramitesPermisos = tramiteTraficoDao.getCountNumTramitesPermisosPorJefatura(idDocPermDistItv);
			StringBuffer sbPermisos = crearTableResumen(listaTramitesPermisos, fechaEstadistica, true);
			if (sbPermisos != null) {
				sb.append(sbPermisos);
				sb.append("<BR>");
				hayDatos = true;
			}
		}
		if (hayDatos) {
			return sb;
		}
		return null;
	}

	private StringBuffer crearTableResumen(List<Object[]> lista, Date fecha, boolean permisos) {
		StringBuffer sb = new StringBuffer("<table border=\"1\">");
		sb.append("<thead>");
		sb.append("<tr bgcolor=\"#a52642\" style=\"text-align:center\" >");
		sb.append("<th colspan=\"2\">");
		sb.append("<font color=\"#ffffff\" >");
		if (permisos) {
			sb.append("RESUMEN PERMISOS");
		} else {
			sb.append("RESUMEN FICHAS");
		}
		sb.append("</font>");
		sb.append("</th>");
		sb.append("</tr>");

		sb.append("<tr bgcolor=\"#a52642\" >");
		sb.append("<th><font color=\"#ffffff\" >Jefaturav</th>");
		sb.append("<th><font color=\"#ffffff\" >Total</font></th>");
		sb.append("</tr>");
		sb.append("</thead>");

		for (Object[] objects : lista) {
			JefaturaTraficoVO jefatura = (JefaturaTraficoVO) objects[0];
			Integer total = (Integer) objects[1];

			sb.append("<tr>");
			sb.append("<font color=\"#000000\" >");
			sb.append("<td align=\"center\">" + jefatura.getDescripcion() + "</td>");
			sb.append("<td align=\"center\">" + total + "</td>");
			sb.append("</font>");
			sb.append("</tr>");
		}

		sb.append("</table>");

		return sb;
	}
}
