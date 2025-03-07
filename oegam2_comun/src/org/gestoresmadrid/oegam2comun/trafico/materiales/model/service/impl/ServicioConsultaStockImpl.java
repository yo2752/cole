package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.trafico.materiales.model.dao.MaterialDao;
import org.gestoresmadrid.core.trafico.materiales.model.dao.MaterialStockDao;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.Delegacion;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialStockVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialVO;
import org.gestoresmadrid.core.trafico.model.dao.JefaturaTraficoDao;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaMateriales;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaStock;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioCrearSolicitudesPedido;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.StockResultadosBean;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.StockInfoRest;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.MaterialStockDto;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioJefaturaTrafico;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioConsultaStockImpl implements ServicioConsultaStock {

	private static final long serialVersionUID = 3547051349146683939L;

	private ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaStockImpl.class);

	private static final String RECIPIENT = "envio.stock.correo.recipient";
	private static final String SUBJECT = "envio.stock.correo.subject";

	private String TIPO_PERMISO_CIRCULACION = "PC";
	private String TIPO_PERMISO_CIRCULACION_TEXTO = "Permiso circulación";

	@Resource
	MaterialStockDao materialStockDao;

	@Resource
	JefaturaTraficoDao jefaturaTraficoDao;

	@Resource
	MaterialDao materialDao;

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioConsultaMateriales servicioConsultaMateriales;

	@Autowired
	ServicioCorreo servicioCorreo;

	@Autowired
	ServicioJefaturaTrafico servicioJefatura;

	@Autowired
	ServicioCrearSolicitudesPedido servicioCrearSolicitudesPedido;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	public List<StockResultadosBean> convertirListaEnBeanPantalla(List<MaterialStockVO> lista) {
		if (lista != null && !lista.isEmpty()) {
			List<StockResultadosBean> listaBean = new ArrayList<StockResultadosBean>();

			for (MaterialStockVO materialStockVO : lista) {
				StockResultadosBean stockResultadosBean = conversor.transform(materialStockVO, StockResultadosBean.class);

				JefaturaTraficoVO jeTrVO = jefaturaTraficoDao.getJefatura(materialStockVO.getJefaturaProvincial().getJefaturaProvincial());
				String jefatura = jeTrVO.getDescripcion();
				stockResultadosBean.setJefaturaProvincial(jefatura);
				stockResultadosBean.setIdJefatura(jeTrVO.getJefaturaProvincial());

				MaterialVO finder = new MaterialVO();
				finder.setMaterialId(materialStockVO.getMaterialVO().getMaterialId());
				MaterialVO material = materialDao.buscar(finder).get(0);
				stockResultadosBean.setMaterialVO(material.getName());

				listaBean.add(stockResultadosBean);
			}
			return listaBean;
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public void gestionarMaterialStock(String jefatura, Long material) {
		List<MaterialStockVO> encontrados = materialStockDao.findStockByJefaturaMaterial(jefatura, material);
		if (encontrados.size() == 0) {
			Log.info("Nuevo Material para la jefatura " + jefatura);
		}
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean aniadirStock(String jefatura, String tipo, Long cantidad) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			MaterialStockVO materialStockVO = materialStockDao.findStockPorJefaturaTipo(jefatura, tipo);
			if (materialStockVO != null) {
				materialStockVO.setUnidades(materialStockVO.getUnidades() + cantidad);
				materialStockVO.setFecUltConsumo(new Date());
				materialStockDao.guardarOActualizar(materialStockVO);
			}
		} catch (Exception e) {
			log.error("Ha sucedidio un error a la hora de añadir el stock, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedidio un error a la hora de añadir el stock.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public void descontarStock(String jefatura, String tipo, Long cantidadExpediente) {
		// TODO sincronizar con el consejo
		MaterialStockVO materialStockVO = materialStockDao.findStockPorJefaturaTipo(jefatura, tipo);

		if (null != materialStockVO && hayStock(jefatura, tipo, cantidadExpediente)) {
			materialStockVO.setUnidades(materialStockVO.getUnidades() - cantidadExpediente);
			materialStockVO.setFecUltConsumo(new Date());
			materialStockDao.actualizar(materialStockVO);

			// Sincronizar con el consejo de forma manual desde la pantalla
			// servicioCrearSolicitudesPedido.crearSolicitudActualizarStock(materialStockVO.getMaterialStockId());

		}
	}

	@Override
	public MaterialStockDto getDtoFromInfoRest(StockInfoRest stockInfoRest) {
		MaterialStockDto materialStockDto = conversor.transform(stockInfoRest, MaterialStockDto.class);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		try {
			materialStockDto.setFecUltRecarga(sdf.parse(stockInfoRest.getFecha()));
		} catch (ParseException e) {
			log.error("Ha ocurrido un error " + e.getMessage());
			e.printStackTrace();
		}

		return materialStockDto;
	}

	@Override
	public MaterialStockVO getVOFromDto(MaterialStockDto materialStockDto) {
		MaterialStockVO materialStockVO = conversor.transform(materialStockDto, MaterialStockVO.class);
		return materialStockVO;
	}

	@Override
	public MaterialStockDto getDtoFromVO(MaterialStockVO materialStockVO) {
		MaterialStockDto materialStockDto = conversor.transform(materialStockVO, MaterialStockDto.class);
		return materialStockDto;
	}

	@Override
	@Transactional(readOnly = true)
	public MaterialStockVO getVOFromInfoRest(StockInfoRest stockInfoRest) {
		MaterialStockVO materialStockVO = conversor.transform(stockInfoRest, MaterialStockVO.class);
		materialStockVO.setTipo(ponerTipo(materialStockVO.getMaterialVO().getMaterialId()));

		String jefatura = Delegacion.convertirTexto(stockInfoRest.getDelegacion().getId());
		materialStockVO.setJefaturaProvincial(jefaturaTraficoDao.getJefatura(jefatura));

		if (StringUtils.isEmpty(stockInfoRest.getObservaciones())) {
			materialStockVO.setObservaciones(null);
		}

		return materialStockVO;
	}

	@Override
	@Transactional(readOnly = true)
	public MaterialStockVO obtenerStockInventario(Long stockInventario) {
		MaterialStockVO materialStockVO = materialStockDao.findStockBystockInvId(stockInventario);
		return materialStockVO;
	}

	@Override
	@Transactional(readOnly = true)
	public List<StockResultadosBean> obtenerStock() {
		List<MaterialStockVO> lista = materialStockDao.findStock();
		return convertirListaEnBeanPantalla(lista);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean hayStock(String jefatura, String tipo, Long unidadesDeseadas) {
		MaterialStockVO materialStockVO = materialStockDao.findStockPorJefaturaTipo(jefatura, tipo);
		Long remaider = null;
		if (null != materialStockVO) {
			remaider = materialStockVO.getUnidades() - unidadesDeseadas;
			if (remaider >= 0) {
				log.info("Hay Stock para esta solicitud");
			} else {
				log.error("No hay Stock para esta solicitud");
			}
		}

		return (remaider >= 0) ? true : false;
	}

	@Override
	@Transactional(readOnly = true)
	public Long cantidadStock(String jefatura, Long materialId) {
		MaterialStockVO materialStockVO = materialStockDao.findStockPorJefaturaMaterial(jefatura, materialId);
		if (materialStockVO != null) {
			return materialStockVO.getUnidades();
		}
		return new Long(0);
	}

	@Override
	@Transactional(readOnly = true)
	public Long cantidadStockPorTipo(String jefatura, String tipo) {
		MaterialStockVO materialStockVO = materialStockDao.findStockPorJefaturaTipo(jefatura, tipo);
		if (materialStockVO != null) {
			return materialStockVO.getUnidades();
		}
		return new Long(0);
	}

	@Override
	@Transactional(readOnly = true)
	public MaterialStockVO obtenerStock(String jefatura, Long materialId) {
		List<MaterialStockVO> materialStockVO = materialStockDao.findStockByJefaturaMaterial(jefatura, materialId);
		if (materialStockVO.size() == 1) {
			return materialStockVO.get(0);
		}

		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<MaterialStockVO> obtenerStockToUpdate() {
		String fechaTarget = "";

		Date fecha = ponerFecha(fechaTarget);
		List<MaterialStockVO> materialStockVO = materialStockDao.findStockByFecha(fecha);
		return materialStockVO;
	}

	Date ponerFecha(String fecha) {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		if (fecha == null || fecha.isEmpty()) {
			return new Date();
		}

		Date date = null;
		try {
			date = format.parse(fecha);
		} catch (ParseException e) {
			log.error("Fecha erronea");
			e.printStackTrace();
		}
		return date;
	}

	@Override
	public MaterialStockDto getStockDto(Long stockId) {
		MaterialStockVO materialStockVO = materialStockDao.findStockByPrimaryKey(stockId);
		return this.getDtoFromVO(materialStockVO);
	}

	@Override
	@Transactional
	public MaterialStockVO obtenerStockByPrimaryKey(Long materialStockId) {
		MaterialStockVO materialStockVO = materialStockDao.findStockByPrimaryKey(materialStockId);
		return materialStockVO;
	}

	@Transactional(readOnly = true)
	private String ponerTipo(Long materialId) {
		String tipoDistintivo = "";

		MaterialVO materialVO = servicioConsultaMateriales.getMaterialForPrimaryKey(materialId);
		tipoDistintivo = materialVO.getTipo();
		return tipoDistintivo;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public ResultBean enviarCorreoEnvioStock(ArrayList<String> listaAvisoStockEnviar) {
		ResultBean result = new ResultBean(false);
		try {
			result = enviarCorreo(listaAvisoStockEnviar);

			if (result.getError()) {
				result.setError(true);
			} else {
				result.setError(false);
			}

		} catch (Exception e) {
			log.error("Ha ocurrido un error " + e.getMessage());
			e.printStackTrace();
			result.setError(true);
		}

		return result;
	}

	private ResultBean enviarCorreo(ArrayList<String> listaAvisoStockEnviar) {
		String subject = null;
		String recipient = null;

		ResultBean resultado = null;

		StringBuilder texto = null;

		try {
			subject = gestorPropiedades.valorPropertie(SUBJECT);
			recipient = gestorPropiedades.valorPropertie(RECIPIENT);

			texto = new StringBuilder("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");
			texto.append("Desde la Oficina Electrónica de Gestión Administrativa (OEGAM), le comunicamos ");
			texto.append("que los siguientes materiales por jefatura están por debajo del límite establecido. ").append("<BR>");

			texto.append("<BR>");
			texto.append("<table border=\"1\">");

			texto.append("<thead>");
			texto.append("<tr bgcolor=\"#a52642\" >");
			texto.append("<th><font color=\"#ffffff\" >Jefatura</font></th>");
			texto.append("<th><font color=\"#ffffff\" >Tipo</font></th>");
			texto.append("<th><font color=\"#ffffff\" >Cantidad</font></th>");
			texto.append("</tr>");
			texto.append("</thead>");

			for (String avisoStock : listaAvisoStockEnviar) {
				String[] aviso = avisoStock.split("-");

				texto.append("<tr>");
				texto.append("<font color=\"#000000\" >");
				if ("CO".equals(aviso[0])) {
					texto.append("<td align=\"center\">COLEGIO</td>");
				} else {
					JefaturaTraficoDto jefaturaDto = servicioJefatura.getJefatura(aviso[0]);
					if (jefaturaDto != null) {
						texto.append("<td align=\"center\">" + jefaturaDto.getDescripcion() + "</td>");
					} else {
						texto.append("<td align=\"center\">" + aviso[0] + "</td>");
					}
				}
				if (TIPO_PERMISO_CIRCULACION.equals(aviso[1])) {
					texto.append("<td align=\"center\">" + TIPO_PERMISO_CIRCULACION_TEXTO + "</td>");
				} else {
					texto.append("<td align=\"center\">" + TipoDistintivo.convertirValor(aviso[1]) + "</td>");
				}

				if (aviso.length == 3) {
					texto.append("<td align=\"center\">" + aviso[2] + "</td>");
				} else if (aviso.length == 4 && aviso[3] != null && !aviso[3].isEmpty()) {
					texto.append("<td align=\"center\">" + aviso[3] + "</td>");
				} else {
					texto.append("<td align=\"center\"></td>");
				}

				texto.append("</font>");
				texto.append("</tr>");
			}
			texto.append("</table>");
			texto.append("<BR>");

			FicheroBean adjuntos = new FicheroBean();
			resultado = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, recipient, null, null, null, adjuntos);
			if (resultado.getError()) {
				log.error("No se enviaron correos de envio de stock");
				for (String textoError : resultado.getListaMensajes()) {
					log.error(textoError);
				}
			}

		} catch (OegamExcepcion | IOException e) {
			log.error("No se enviaron correos de envio de stock", e);
		}
		return resultado;
	}

}
