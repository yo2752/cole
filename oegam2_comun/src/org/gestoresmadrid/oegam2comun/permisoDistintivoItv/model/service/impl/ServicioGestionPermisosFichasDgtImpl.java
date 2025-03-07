package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioFichasTecnicasDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioGestionPermisosFichasDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioPermisosDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.GestionPermisosFichasDgtBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioGestionPermisosFichasDgtImpl implements ServicioGestionPermisosFichasDgt{

	private static final long serialVersionUID = 1570387641171729419L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioGestionPermisosFichasDgtImpl.class);
	
	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;
	
	@Autowired
	ServicioPermisosDgt servicioPermisosDgt;
	
	@Autowired
	ServicioFichasTecnicasDgt servicioFichasTecnicasDgt;
	
	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;
	
	@Autowired
	Utiles utiles;
	
	@Override
	public ResultadoPermisoDistintivoItvBean cambiarEstadoPermisos(String numExpedientes, BigDecimal estadoNuevo, BigDecimal idUsuario, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(numExpedientes != null && !numExpedientes.isEmpty()){
				String[] numsExp = numExpedientes.split("-");
				Date fechaSol = new Date();
				for(String numExp : numsExp){
					try {
						ResultadoPermisoDistintivoItvBean resultSolicitar = servicioPermisosDgt.cambiarEstado(new BigDecimal(numExp),idUsuario, fechaSol, estadoNuevo, ipConexion);
						if(resultSolicitar.getError()){
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError(resultSolicitar.getMensaje());
						} else {
							resultado.getResumen().addNumOk();
							resultado.getResumen().addListaMensajeOk("El permiso del expediente " + numExp + " se ha cambiado su estado correctamente.");
						}
					} catch (Throwable e) {
						log.error("No se ha podido cambiar el estado del permiso del tramite: "  + numExp + ", porque ha sucedido el siguiente error: ",e);
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError("No se ha podido cambiar el estado del permiso del tramite: "  + numExp);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún trámite para poder solicitar su permiso.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado a los permisos seleccionados, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado a los permisos seleccionados.");
		}
		return resultado;
	}
	
	@Override
	public ResultadoPermisoDistintivoItvBean cambiarEstadoFichas(String numExpedientes, BigDecimal estadoNuevo, BigDecimal idUsuario, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(numExpedientes != null && !numExpedientes.isEmpty()){
				String[] numsExp = numExpedientes.split("-");
				Date fechaSol = new Date();
				for(String numExp : numsExp){
					try {
						ResultadoPermisoDistintivoItvBean resultSolicitar = servicioFichasTecnicasDgt.cambiarEstado(new BigDecimal(numExp),idUsuario, fechaSol, estadoNuevo, ipConexion);
						if(resultSolicitar.getError()){
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError(resultSolicitar.getMensaje());
						} else {
							resultado.getResumen().addNumOk();
							resultado.getResumen().addListaMensajeOk("La ficha del expediente " + numExp + " se ha cambiado su estado correctamente.");
						}
					} catch (Throwable e) {
						log.error("No se ha podido cambiar el estado de la ficha del tramite: "  + numExp + ", porque ha sucedido el siguiente error: ",e);
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError("No se ha podido cambiar el estado de la ficha del tramite: "  + numExp);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún trámite para poder cambiar el estado.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado a las fichas seleccionados, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado a las fichas seleccionados.");
		}
		return resultado;
	}
	
	@Override
	public List<GestionPermisosFichasDgtBean> convertirListaEnBeanPantallaConsultaPermisos(List<TramiteTraficoVO> listaVo) {
		List<GestionPermisosFichasDgtBean> lista = new ArrayList<GestionPermisosFichasDgtBean>();
		for(TramiteTraficoVO tramiteTraficoVO : listaVo){
			GestionPermisosFichasDgtBean dgtBean = new GestionPermisosFichasDgtBean();
			dgtBean.setNumExpediente(tramiteTraficoVO.getNumExpediente());
			dgtBean.setDescContrato(tramiteTraficoVO.getNumColegiado() + " - " + tramiteTraficoVO.getContrato().getVia());
			if(tramiteTraficoVO.getVehiculo()!=null) {
				dgtBean.setMatricula(tramiteTraficoVO.getVehiculo().getMatricula());
			}else {
				dgtBean.setMatricula("");
			}
			if(tramiteTraficoVO.getVehiculo().getNive()!=null) {
				dgtBean.setNive(tramiteTraficoVO.getVehiculo().getNive());
			}else {
				dgtBean.setNive("");
			}
			
			
			if(tramiteTraficoVO.getNumImpresionesPerm() != null){
				dgtBean.setNumImpresiones(tramiteTraficoVO.getNumImpresionesPerm().toString());
			}
			dgtBean.setPermiso(tramiteTraficoVO.getPermiso());
			if(tramiteTraficoVO.getEstadoSolPerm() != null){
				dgtBean.setEstadoSolicitud(EstadoPermisoDistintivoItv.convertirTexto(tramiteTraficoVO.getEstadoSolPerm()));
			} else {
				dgtBean.setEstadoSolicitud(EstadoPermisoDistintivoItv.Iniciado.getNombreEnum());
			}
			if(tramiteTraficoVO.getEstadoImpPerm() != null){
				dgtBean.setEstadoPetImp(EstadoPermisoDistintivoItv.convertirTexto(tramiteTraficoVO.getEstadoImpPerm()));
			} else {
				dgtBean.setEstadoPetImp(EstadoPermisoDistintivoItv.Iniciado.getNombreEnum());
			}
			dgtBean.setTipoTramite(TipoTramiteTrafico.convertirTexto(tramiteTraficoVO.getTipoTramite()));
			if(tramiteTraficoVO.getDocPermisoVO() != null){
				dgtBean.setDocId(tramiteTraficoVO.getDocPermisoVO().getDocIdPerm());
			}
			if(tramiteTraficoVO.getIntervinienteTraficosAsList().size()>0 && tramiteTraficoVO.getIntervinienteTraficosAsList().get(0).getId().getNif() != null){
				dgtBean.setNif(tramiteTraficoVO.getIntervinienteTraficosAsList().get(0).getId().getNif());
			}else {
				dgtBean.setNif("");
			}
			if(tramiteTraficoVO.getJefaturaTrafico() != null){
				dgtBean.setJefaturaTrafico(tramiteTraficoVO.getJefaturaTrafico().getDescripcion());
			}
			dgtBean.setDescContrato(tramiteTraficoVO.getNumColegiado() + " - " + tramiteTraficoVO.getContrato().getVia());
			lista.add(dgtBean);
		}
		return lista;
	}
	
	@Override
	public List<GestionPermisosFichasDgtBean> convertirListaEnBeanPantallaMasivo(List<TramiteTraficoVO> lista) {
		List<GestionPermisosFichasDgtBean> listaBean = new ArrayList<GestionPermisosFichasDgtBean>();
		for(TramiteTraficoVO tramiteTraficoVO : lista){
			GestionPermisosFichasDgtBean dgtBean = new GestionPermisosFichasDgtBean();
			dgtBean.setNumExpediente(tramiteTraficoVO.getNumExpediente());
			dgtBean.setDescContrato(tramiteTraficoVO.getNumColegiado() + " - " + tramiteTraficoVO.getContrato().getVia());
			dgtBean.setMatricula(tramiteTraficoVO.getVehiculo().getMatricula());
			if(tramiteTraficoVO.getDocPermiso() != null){
				dgtBean.setDocPermiso(tramiteTraficoVO.getDocPermisoVO().getDocIdPerm());
			}
			if(tramiteTraficoVO.getDocFichaTecnica() != null){
				dgtBean.setDocFicha(tramiteTraficoVO.getDocFichaTecnicaVO().getDocIdPerm());
			}
			dgtBean.setEstadoTramite(EstadoTramiteTrafico.convertirTexto(tramiteTraficoVO.getEstado().toString()));
			dgtBean.setTipoTramite(TipoTramiteTrafico.convertirTexto(tramiteTraficoVO.getTipoTramite()));
			listaBean.add(dgtBean);
		}
		return listaBean;
	}
	
	@Override
	public ResultadoPermisoDistintivoItvBean generarDocKOPermisos(String codSeleccionados, BigDecimal idUsuario, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				String[] numsExp = codSeleccionados.split("-");
				List<TramiteTraficoVO> listaTramites = servicioTramiteTrafico.getListaExpedientesPorListNumExp(utiles.convertirStringArrayToBigDecimal(numsExp), Boolean.TRUE);
				resultado = validarListaTramitesKO(listaTramites, TipoDocumentoImprimirEnum.PERMISO_CIRCULACION);
				if(!resultado.getError() && (resultado.getListaExpedientesOk() != null && !resultado.getListaExpedientesOk().isEmpty())) {
					for(BigDecimal numExpediente : resultado.getListaExpedientesOk()){
						try {
							ResultadoPermisoDistintivoItvBean resultadoGenKo = servicioPermisosDgt.generarKoTramite(numExpediente, idUsuario, ipConexion);
							if(resultadoGenKo.getError()){
								resultado.getResumen().addNumError();
								resultado.getResumen().addListaMensajeError(resultadoGenKo.getMensaje());
							} else {
								resultado.getResumen().addNumOk();
								resultado.getResumen().addListaMensajeOk("KO para el tramite: " + numExpediente + " generado correctamente.");
							}
						} catch (Exception e) {
							log.error("Ha sucedido un error a la hora de generar el KO para el tramite: " + numExpediente + ", error: ",e);
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError("Ha sucedido un error a la hora de generar el KO para el tramite: " + numExpediente);
						}
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún expediente para poder generar los KO.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el listado con los tramites KO, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el listado con los tramites KO.");
		}
		return resultado;
	}
	
	private ResultadoPermisoDistintivoItvBean validarListaTramitesKO(List<TramiteTraficoVO> listaTramites, TipoDocumentoImprimirEnum tipo) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		if(listaTramites == null || listaTramites.isEmpty()){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos para los expedientes seleccionados.");
		} else {
			for(TramiteTraficoVO tramiteTrafico : listaTramites){
				if(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(tipo.getValorEnum()) && !EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum().equals(tramiteTrafico.getEstadoSolPerm()) &&
						 !EstadoPermisoDistintivoItv.IMPR_No_Encontrado.getValorEnum().equals(tramiteTrafico.getEstadoSolPerm()) ){
					resultado.getResumen().addNumError();
					resultado.getResumen().addListaMensajeError("No se puede generar el KO del expediente " + tramiteTrafico.getNumExpediente() + ", porque el estado de la solicitud debe de ser Finalizado con error o IMPR No Encontrado.");
				} else if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(tipo.getValorEnum()) && !EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum().equals(tramiteTrafico.getEstadoSolFicha()) &&
						 !EstadoPermisoDistintivoItv.IMPR_No_Encontrado.getValorEnum().equals(tramiteTrafico.getEstadoSolFicha()) ){
					resultado.getResumen().addNumError();
					resultado.getResumen().addListaMensajeError("No se puede generar el KO del expediente " + tramiteTrafico.getNumExpediente() + ", porque el estado de la solicitud debe de ser Finalizado con error o IMPR No Encontrado.");
				} else if(tramiteTrafico.getVehiculo() == null || StringUtils.isBlank(tramiteTrafico.getVehiculo().getMatricula())){
					resultado.getResumen().addNumError();
					resultado.getResumen().addListaMensajeError("No se puede generar el KO del expediente " + tramiteTrafico.getNumExpediente() + ", porque no tiene matricula asociada.");
				} else {
					resultado.addListaExpedientesOk(tramiteTrafico.getNumExpediente());
				}
			}
		}
		return resultado;
	}
	
	@Override
	public ResultadoPermisoDistintivoItvBean generarDocKOFichas(String codSeleccionados, BigDecimal idUsuario, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(codSeleccionados != null && !codSeleccionados.isEmpty()){
				String[] numsExp = codSeleccionados.split("-");
				List<TramiteTraficoVO> listaTramites = servicioTramiteTrafico.getListaExpedientesPorListNumExp(utiles.convertirStringArrayToBigDecimal(numsExp), Boolean.TRUE);
				resultado = validarListaTramitesKO(listaTramites, TipoDocumentoImprimirEnum.FICHA_TECNICA);
				if(!resultado.getError() && (resultado.getListaExpedientesOk() != null && !resultado.getListaExpedientesOk().isEmpty())) {
					for(BigDecimal numExpediente : resultado.getListaExpedientesOk()){
						try {
							ResultadoPermisoDistintivoItvBean resultadoGenKo = servicioFichasTecnicasDgt.generarKoTramite(numExpediente, idUsuario, ipConexion);
							if(resultadoGenKo.getError()){
								resultado.getResumen().addNumError();
								resultado.getResumen().addListaMensajeError(resultadoGenKo.getMensaje());
							} else {
								resultado.getResumen().addNumOk();
								resultado.getResumen().addListaMensajeOk("KO para el tramite: " + numExpediente + " generado correctamente.");
							}
						} catch (Exception e) {
							log.error("Ha sucedido un error a la hora de generar el KO para el tramite: " + numExpediente + ", error: ",e);
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError("Ha sucedido un error a la hora de generar el KO para el tramite: " + numExpediente);
						}
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún expediente para poder generar los KO.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el listado con los tramites KO, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el listado con los tramites KO.");
		}
		return resultado;
	}
	
	@Override
	public List<GestionPermisosFichasDgtBean> convertirListaEnBeanPantallaGestionFichas(List<TramiteTraficoVO> listaVo) {
		List<GestionPermisosFichasDgtBean> lista = new ArrayList<GestionPermisosFichasDgtBean>();
		for(TramiteTraficoVO tramiteTraficoVO : listaVo){
			GestionPermisosFichasDgtBean dgtBean = new GestionPermisosFichasDgtBean();
			dgtBean.setNumExpediente(tramiteTraficoVO.getNumExpediente());
			dgtBean.setDescContrato(tramiteTraficoVO.getNumColegiado() + " - " + tramiteTraficoVO.getContrato().getVia());
			if(tramiteTraficoVO.getVehiculo() != null && StringUtils.isNotBlank(tramiteTraficoVO.getVehiculo().getMatricula())) {
				dgtBean.setMatricula(tramiteTraficoVO.getVehiculo().getMatricula());
			}else {
				dgtBean.setMatricula("");
			}
			if(tramiteTraficoVO.getVehiculo() != null && StringUtils.isNotBlank(tramiteTraficoVO.getVehiculo().getNive())) {
				dgtBean.setNive(tramiteTraficoVO.getVehiculo().getNive());
			}
			if(tramiteTraficoVO.getNumImpresionesFicha() != null){
				dgtBean.setNumImpresiones(tramiteTraficoVO.getNumImpresionesFicha().toString());
			}
			dgtBean.setFichaTecnica(tramiteTraficoVO.getFichaTecnica());
			if(tramiteTraficoVO.getEstadoSolFicha() != null){
				dgtBean.setEstadoSolicitud(EstadoPermisoDistintivoItv.convertirTexto(tramiteTraficoVO.getEstadoSolFicha()));
			} else {
				dgtBean.setEstadoSolicitud(EstadoPermisoDistintivoItv.Iniciado.getNombreEnum());
			}
			if(tramiteTraficoVO.getEstadoImpFicha() != null){
				dgtBean.setEstadoPetImp(EstadoPermisoDistintivoItv.convertirTexto(tramiteTraficoVO.getEstadoImpFicha()));
			} else {
				dgtBean.setEstadoPetImp(EstadoPermisoDistintivoItv.Iniciado.getNombreEnum());
			}
			dgtBean.setTipoTramite(TipoTramiteTrafico.convertirTexto(tramiteTraficoVO.getTipoTramite()));
			if(tramiteTraficoVO.getDocFichaTecnicaVO() != null){
				dgtBean.setDocId(tramiteTraficoVO.getDocFichaTecnicaVO().getDocIdPerm());
			}
			if(tramiteTraficoVO.getJefaturaTrafico() != null) {
				dgtBean.setJefaturaTrafico(tramiteTraficoVO.getJefaturaTrafico().getDescripcion());
			}
			if(tramiteTraficoVO.getIntervinienteTraficosAsList().size() >0 && tramiteTraficoVO.getIntervinienteTraficosAsList().get(0).getId().getNif() != null){
				dgtBean.setNif(tramiteTraficoVO.getIntervinienteTraficosAsList().get(0).getId().getNif());
			}else {
				dgtBean.setNif("");
			}
			dgtBean.setDescContrato(tramiteTraficoVO.getNumColegiado() + " - " + tramiteTraficoVO.getContrato().getVia());
			lista.add(dgtBean);
		}
		return lista;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean solicitarPermisos(String numExpedientes, BigDecimal idUsuario, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(numExpedientes != null && !numExpedientes.isEmpty()){
				String[] numsExp = numExpedientes.split("-");
				Date fechaSol = new Date();
				for(String numExp : numsExp){
					try {
						ResultadoPermisoDistintivoItvBean resultSolicitar = servicioPermisosDgt.solicitarPermiso(new BigDecimal(numExp),idUsuario, fechaSol, ipConexion);
						if(resultSolicitar.getError()){
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError(resultSolicitar.getMensaje());
						} else {
							resultado.getResumen().addNumOk();
							resultado.getResumen().addListaMensajeOk("La Solicitud del permisos para el expediente " + numExp + " se ha generado correctamente.");
						}
					} catch (Throwable e) {
						log.error("No se ha podido solicitar el permiso del tramite: "  + numExp + ", porque ha sucedido el siguiente error: ",e);
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError("No se ha podido solicitar el permiso del tramite: "  + numExp);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún trámite para poder solicitar su permiso.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar los permisos de los trámites en bloque, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar los permisos de los trámites.");
		}
		return resultado;
	}
	
	@Override
	public ResultadoPermisoDistintivoItvBean solicitarFichas(String numExpedientes, BigDecimal idUsuario, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(numExpedientes != null && !numExpedientes.isEmpty()){
				String[] numsExp = numExpedientes.split("-");
				Date fechaSol = new Date();
				for(String numExp : numsExp){
					try {
						ResultadoPermisoDistintivoItvBean resultSolicitar = servicioFichasTecnicasDgt.solicitarFichaTecnica(new BigDecimal(numExp),idUsuario, fechaSol, ipConexion);
						if(resultSolicitar.getError()){
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError(resultSolicitar.getMensaje());
						} else {
							resultado.getResumen().addNumOk();
							resultado.getResumen().addListaMensajeOk("La Solicitud de la ficha técnica para el expediente " + numExp + " se ha generado correctamente.");
						}
					} catch (Throwable e) {
						log.error("No se ha podido solicitar la ficha técnica del tramite: "  + numExp + ", porque ha sucedido el siguiente error: ",e, numExp);
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError("No se ha podido solicitar la ficha técnica del tramite: "  + numExp);
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún trámite para poder solicitar su ficha técnica.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar las fichas técnicas de los trámites en bloque, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar las fichas técnicas de los trámites.");
		}
		return resultado;
	}
	
	@Override
	public ResultadoPermisoDistintivoItvBean imprimirPermisos(String numExpedientes, BigDecimal idUsuario, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(numExpedientes != null && !numExpedientes.isEmpty()){
				resultado = servicioPermisosDgt.impresionPermisos(numExpedientes,idUsuario, ipConexion);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún trámite para poder solicitar la impresión de su permiso.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar la impresión de los permisos de los trámites en bloque, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar la impresión de los permisos de los trámites.");
		}
		return resultado;
	}
	
	@Override
	public ResultadoPermisoDistintivoItvBean imprimirFichas(String numExpedientes, BigDecimal idUsuario, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(numExpedientes != null && !numExpedientes.isEmpty()){
				resultado = servicioFichasTecnicasDgt.impresionFichas(numExpedientes,idUsuario, ipConexion);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de seleccionar algún trámite para poder solicitar la impresión de su ficha técnica.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar la impresión de las ficha técnica de los trámites en bloque, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar la impresión de las ficha técnica de los trámites.");
		}
		return resultado;
	}
	
	@Override
	public ResultadoPermisoDistintivoItvBean revertirPermisos(String numExpedientes, BigDecimal idUsuario, Boolean tienePermisosAdmin, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(tienePermisosAdmin){
				if(numExpedientes != null && !numExpedientes.isEmpty()){
					String[] numsExp = numExpedientes.split("-");
					Date fechaSol = new Date();
					for(String numExp : numsExp){
						try {
							ResultadoPermisoDistintivoItvBean resultSolicitar = servicioPermisosDgt.revertirPermiso(new BigDecimal(numExp),idUsuario, fechaSol, ipConexion);
							if(resultSolicitar.getError()){
								resultado.getResumen().addNumError();
								resultado.getResumen().addListaMensajeError(resultSolicitar.getMensaje());
							} else {
								resultado.getResumen().addNumOk();
								resultado.getResumen().addListaMensajeOk("El permiso del expediente " + numExp + " se ha revertido correctamente.");
							}
						} catch (Throwable e) {
							log.error("No se ha podido revertir el permiso del tramite: "  + numExp + ", porque ha sucedido el siguiente error: ",e, numExp);
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError("No se ha podido revertir el permiso del tramite: "  + numExp);
						}
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Debe de seleccionar algún trámite para poder revertir su permiso.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No tiene permisos suficientes para realizar la accion de revertir los permisos.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de revertir los permisos de los trámites en bloque, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de revertir los permisos de los trámites.");
		}
		return resultado;
	}
	
	@Override
	public ResultadoPermisoDistintivoItvBean revertirFichas(String numExpedientes, BigDecimal idUsuario, Boolean tienePermisosAdmin, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if(tienePermisosAdmin){
				if(numExpedientes != null && !numExpedientes.isEmpty()){
					String[] numsExp = numExpedientes.split("-");
					Date fechaSol = new Date();
					for(String numExp : numsExp){
						try {
							ResultadoPermisoDistintivoItvBean resultSolicitar = servicioFichasTecnicasDgt.revertirFichas(new BigDecimal(numExp),idUsuario, fechaSol, ipConexion);
							if(resultSolicitar.getError()){
								resultado.getResumen().addNumError();
								resultado.getResumen().addListaMensajeError(resultSolicitar.getMensaje());
							} else {
								resultado.getResumen().addNumOk();
								resultado.getResumen().addListaMensajeOk("La ficha técnica del expediente " + numExp + " se ha revertido correctamente.");
							}
						} catch (Throwable e) {
							log.error("No se ha podido revertir la ficha técnica del tramite: "  + numExp + ", porque ha sucedido el siguiente error: ",e, numExp);
							resultado.getResumen().addNumError();
							resultado.getResumen().addListaMensajeError("No se ha podido revertir la ficha técnica del tramite: "  + numExp);
						}
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Debe de seleccionar algún trámite para poder revertir su permiso.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No tiene permisos suficientes para realizar la accion de revertir las fichas técnicas.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de revertir los permisos de los trámites en bloque, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de revertir los permisos de los trámites.");
		}
		return resultado;
	}

	@Override
	public ResultadoPermisoDistintivoItvBean desasignarPermisos(String codSeleccionados, BigDecimal idUsuario, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		if(codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] numExp = codSeleccionados.split(";");
			for(String numExpediente : numExp) {
				try {
					ResultadoDistintivoDgtBean resultDesasignar = servicioPermisosDgt.desasignarPermiso(new BigDecimal(numExpediente), idUsuario, ipConexion);
					if(resultDesasignar.getError()){
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError(resultDesasignar.getMensaje());
					} else {
						resultado.getResumen().addNumOk();
						resultado.getResumen().addListaMensajeOk("Documento: " + numExpediente + " se desasignó correctamente.");
					}
				} catch (Exception e) {
					log.error("Ha sucedido un error a la hora de desasignar los ids de los documentos, error: ",e);
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Ha sucedido un error a la hora de desasignar los ids de los documentos.");
				}
			}
		}
		return resultado;
	}
	
	@Override
	public ResultadoPermisoDistintivoItvBean desasignarFichas(String codSeleccionados, BigDecimal idUsuario, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		if(codSeleccionados != null && !codSeleccionados.isEmpty()) {
			String[] numExp = codSeleccionados.split(";");
			for(String numExpediente : numExp) {
				try {
					ResultadoDistintivoDgtBean resultDesasignar = servicioFichasTecnicasDgt.desasignarFicha(new BigDecimal(numExpediente), idUsuario, ipConexion);
					if(resultDesasignar.getError()){
						resultado.getResumen().addNumError();
						resultado.getResumen().addListaMensajeError(resultDesasignar.getMensaje());
					} else {
						resultado.getResumen().addNumOk();
						resultado.getResumen().addListaMensajeOk("Documento: " + numExpediente + " se desasignó correctamente.");
					}
				} catch (Exception e) {
					log.error("Ha sucedido un error a la hora de desasignar los ids de los documentos, error: ",e);
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Ha sucedido un error a la hora de desasignar los ids de los documentos.");
				}
			}
		}
		return resultado;
	}
}
