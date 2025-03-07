package org.gestoresmadrid.oegamComun.impr.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.impr.model.vo.ImprVO;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.impr.service.ServicioGestionarImpr;
import org.gestoresmadrid.oegamComun.impr.service.ServicioImpr;
import org.gestoresmadrid.oegamComun.impr.service.ServicioImprKO;
import org.gestoresmadrid.oegamComun.impr.view.bean.GestionImprFilterBean;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoImprBean;
import org.gestoresmadrid.oegamComun.utiles.UtilesVistaTramites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioGestionarImprImpl implements ServicioGestionarImpr{

	private static final long serialVersionUID = 8998112415448261178L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioGestionarImprImpl.class);

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioImpr servicioImpr;

	@Autowired
	ServicioImprKO servicioImprKO;

	@Autowired
	UtilesVistaTramites utiles;

	@Override
	public List<GestionImprFilterBean> convertirListaEnBeanPantalla(List<ImprVO> lista) {
		if (lista != null && !lista.isEmpty()) {
			List<GestionImprFilterBean> listaBean = new ArrayList<GestionImprFilterBean>();
			for (ImprVO imprVO : lista) {
				GestionImprFilterBean imprBean = conversor.transform(imprVO, GestionImprFilterBean.class);
				listaBean.add(imprBean);
			}
			return listaBean;
		}
		return Collections.emptyList();
	}

	@Override
	public ResultadoImprBean cambiarEstadoImpr(String codSeleccionados, String estadoNuevo, Long idUsuario) {
		ResultadoImprBean resultado = new ResultadoImprBean(Boolean.FALSE);
		try {
			if (StringUtils.isNotBlank(codSeleccionados)){
				if (estadoNuevo != null && !estadoNuevo.isEmpty()) {
					String[] ids = codSeleccionados.split("_");
					for (String id : ids) {
						try {
							ResultadoImprBean resultValidar = servicioImpr.cambiarEstado(new Long(id), estadoNuevo, idUsuario);
							if (resultValidar.getError()) {
								resultado.addResumenError(resultValidar.getMensaje());
							} else {
								resultado.addResumenOK("Para el IMPR: " + id + " se ha cambiado el estado correctamente.");
							}
						} catch (Exception e) {
							log.error("Ha sucedido un error a la hora de cambiar el estado del IMPR con id: " + id + ", error: ", e);
							resultado.addResumenError("Ha sucedido un error a la hora de cambiar el estado del IMPR con id: " + id);
						}
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Debe seleccionar algún estado nuevo para cambiar el estado de los trámites seleccionados.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún tramite para cambiar su estado.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de los tramites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado de los tramites.");
		}
		return resultado;
	}

	@Override
	public ResultadoImprBean modificarCarpetaImpr(String codSeleccionados, String carpetaNueva, Long idUsuario) {
		ResultadoImprBean resultado = new ResultadoImprBean(Boolean.FALSE);
		try {
			if (StringUtils.isNotBlank(codSeleccionados)){
				if (StringUtils.isNotBlank(carpetaNueva)) {
					String[] ids = codSeleccionados.split("_");
					for (String id : ids) {
						try {
							ResultadoImprBean resultValidar = servicioImpr.modificarCarpeta(new Long(id), carpetaNueva, idUsuario);
							if (resultValidar.getError()) {
								resultado.addResumenError(resultValidar.getMensaje());
							} else {
								resultado.addResumenOK("Para el IMPR: " + id + " se ha modificado la carpeta correctamente.");
							}
						} catch (Exception e) {
							log.error("Ha sucedido un error a la hora de modificar la carpeta del IMPR con id: " + id + ", error: ", e);
							resultado.addResumenError("Ha sucedido un error a la hora de modificar la carpeta del IMPR con id: " + id);
						}
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Debe indicar un nombre para la carpeta de los trámites seleccionados.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún tramite para modificar la carpeta.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de modificar la carpeta de los tramites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de modificar la carpeta de los tramites.");
		}
		return resultado;
	}

	@Override
	public ResultadoImprBean solicitarImpr(String codSeleccionados, Long idUsuario) {
		ResultadoImprBean resultado = new ResultadoImprBean(Boolean.FALSE);
		try {
			resultado= servicioImpr.dividirImprSolicitudesPantalla(codSeleccionados);
			if(!resultado.getError()) {
				if(resultado.getListaEncarpetado() != null && !resultado.getListaEncarpetado().isEmpty()) {
					servicioImpr.solicitarImprEncarpetadosPantalla(idUsuario, resultado);
				}
			}
		}catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar los IMPR seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar los IMPR seleccionados.");
		}
		return resultado;
	}

	@Override
	public ResultadoImprBean generarKo(String codSeleccionados, Long idUsuario) {
		ResultadoImprBean resultado = new ResultadoImprBean(Boolean.FALSE);
		try {
			if(StringUtils.isNotBlank(codSeleccionados)){
				String[] ids = codSeleccionados.split("_");
				List<ImprVO> listaTramites = servicioImpr.getListaExpedientesPorListNumExp(utiles.convertirStringArrayToLong(ids));
				resultado = validarListaTramitesKO(listaTramites);
				if(!resultado.getError() && (resultado.getListaIdOk() != null && !resultado.getListaIdOk().isEmpty())) {
					for(BigDecimal id : resultado.getListaIdOk()){
						try {
							ResultadoImprBean resultadoGenKo = servicioImprKO.generarKoTramite(id, idUsuario);
							if(resultadoGenKo.getError()){
								resultado.addResumenError(resultadoGenKo.getMensaje());
							} else {
								resultado.addResumenOK("KO para el tramite con id: " + id + " generado correctamente.");
							}
						} catch (Exception e) {
							log.error("Ha sucedido un error a la hora de generar el KO para el tramite con id: " + id + ", error: ",e);
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Ha sucedido un error a la hora de generar el KO para el tramite con id: " + id);
						}
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún expediente para poder generar los KO.");
			}
		}catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar los IMPR seleccionados, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar los IMPR seleccionados.");
		}
		return resultado;
	}

	private ResultadoImprBean validarListaTramitesKO(List<ImprVO> listaTramites) {
		ResultadoImprBean resultado = new ResultadoImprBean(Boolean.FALSE);
		if(listaTramites == null || listaTramites.isEmpty()){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos para los expedientes seleccionados.");
		} else {
			for(ImprVO imprVO : listaTramites){
				if(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(imprVO.getTipoImpr()) && !EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum().equals(imprVO.getEstadoSolicitud()) &&
						 !EstadoPermisoDistintivoItv.IMPR_No_Encontrado.getValorEnum().equals(imprVO.getEstadoSolicitud()) ){
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede generar el KO para el id " + imprVO.getId() + ", porque el estado de la solicitud debe de ser Finalizado con error o IMPR No Encontrado.");
				} else if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(imprVO.getTipoImpr()) && !EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum().equals(imprVO.getEstadoSolicitud()) &&
						 !EstadoPermisoDistintivoItv.IMPR_No_Encontrado.getValorEnum().equals(imprVO.getEstadoSolicitud()) ){
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede generar el KO para el id " + imprVO.getId() + ", porque el estado de la solicitud debe de ser Finalizado con error o IMPR No Encontrado.");
				} else if(StringUtils.isBlank(imprVO.getMatricula())){
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede generar el KO del id " + imprVO.getId() + ", porque no tiene matricula asociada.");
				} else {
					resultado.addListaIdsOk(new BigDecimal(imprVO.getId()));
				}
			}
		}
		return resultado;
	}

	@Override
	public ResultadoImprBean desasignarDocumento(String codSeleccionados, Long idUsuario) {
		ResultadoImprBean resultado = new ResultadoImprBean(Boolean.FALSE);
		try {
			if (StringUtils.isNotBlank(codSeleccionados)){
				String[] ids = codSeleccionados.split("_");
				for (String id : ids) {
					try {
						ResultadoImprBean resultValidar = servicioImpr.desasignarDocumento(new Long(id), idUsuario);
						if (resultValidar.getError()) {
							resultado.addResumenError(resultValidar.getMensaje());
						} else {
							resultado.addResumenOK("Para el IMPR: " + id + " se ha modificado la carpeta correctamente.");
						}
					} catch (Exception e) {
						log.error("Ha sucedido un error a la hora de modificar la carpeta del IMPR con id: " + id + ", error: ", e);
						resultado.addResumenError("Ha sucedido un error a la hora de modificar la carpeta del IMPR con id: " + id);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún tramite para modificar la carpeta.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de modificar la carpeta de los tramites, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de modificar la carpeta de los tramites.");
		}
		return resultado;
	}

}