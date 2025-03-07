package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.consultaKo.model.service.ServicioConsultaKo;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.evolucionPrmDstvFicha.model.service.ServicioEvolucionPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.model.service.ServicioPermisos;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioPermisosDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.model.service.ServicioEmpresaTelematica;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoDuplicado;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.impr.service.ServicioPersistenciaImprKO;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.google.common.collect.Lists;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioPermisosDgtImpl implements ServicioPermisosDgt {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPermisosDgtImpl.class);

	private static final long serialVersionUID = -3284955271793985894L;

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	ServicioTramiteTraficoDuplicado servicioTramiteTraficoDuplicado;

	@Autowired
	ServicioEvolucionPrmDstvFicha servicioEvolucionPrm;

	@Autowired
	ServicioConsultaKo servicioConsultaKo;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	ServicioEmpresaTelematica servicioEmpresaTelematica;

	@Autowired
	ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	ServicioDocPrmDstvFicha servicioDocPrmDstvFicha;

	@Autowired
	ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;

	@Autowired
	ServicioPermisos servicioPermisos;

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Autowired
	ServicioPersistenciaImprKO servicioPersistenciaImprKO;

	@Override
	public ResultadoPermisoDistintivoItvBean generarDocPermisosContrato(ContratoDto contratoDto, Date fecha, String tipoTramite, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
				resultado = generarDocPermisosMatw(contratoDto, fecha, ipConexion);
			} else if (TipoTramiteTrafico.Duplicado.getValorEnum().equals(tipoTramite)) {
				resultado = generarDocPermisosDuplicado(contratoDto, fecha, ipConexion);
			} else {
				resultado = generarDocPermisosCtit(contratoDto, fecha, ipConexion);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar las peticiones de impresion de permisos para el contrato: " + contratoDto.getIdContrato() + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar las peticiones de impresion de permisos para el contrato: " + contratoDto.getIdContrato());
		}
		return resultado;
	}

	private ResultadoPermisoDistintivoItvBean generarDocPermisosDuplicado(ContratoDto contratoDto, Date fecha,String ipConexion) throws OegamExcepcion {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		String datos = new SimpleDateFormat("ddMMyyyy").format(fecha) + "_" + TipoTramiteTrafico.Duplicado.getValorEnum();
		resultado = servicioTramiteTraficoDuplicado.getTramitesFinalizadosTelematicamentePorContrato(contratoDto.getIdContrato().longValue(), fecha);
		if(!resultado.getError()){
			if (resultado.getListaTramitesDuplicado().size() > 500) {
				final List<List<TramiteTrafDuplicadoVO>> auxListas = Lists.partition(resultado.getListaTramitesDuplicado(), 500);
				int cont = 0;
				for (List<TramiteTrafDuplicadoVO> listaTramites : auxListas) {
					ResultadoPermisoDistintivoItvBean resultGen = servicioDocPrmDstvFicha.generarDoc(contratoDto.getColegiadoDto().getUsuario().getIdUsuario(), new Date(),
							TipoDocumentoImprimirEnum.PERMISO_CIRCULACION, null, 
							contratoDto.getIdContrato().longValue(), contratoDto.getJefaturaTraficoDto().getJefaturaProvincial(),
							Boolean.FALSE);
					if(!resultGen.getError()){
						Long docPermiso = resultGen.getIdDocPermDstvEitv();
						String sDocPermiso = resultGen.getDocId();
						for(TramiteTrafDuplicadoVO tramiteTrafDupliVO : listaTramites){
							tramiteTrafDupliVO.setDocPermiso(docPermiso);
							tramiteTrafDupliVO.setEstadoSolPerm(EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum());
							tramiteTrafDupliVO.setEstadoImpPerm(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
							servicioTramiteTrafico.actualizarTramite(tramiteTrafDupliVO);
							servicioEvolucionPrm.guardarEvolucion(tramiteTrafDupliVO.getNumExpediente(),
									contratoDto.getColegiadoDto().getUsuario().getIdUsuario(),
									TipoDocumentoImprimirEnum.PERMISO_CIRCULACION,OperacionPrmDstvFicha.CREACION, new Date(),
									null, EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum(),sDocPermiso,ipConexion);
						}
						datos += "_" + contratoDto.getIdContrato() + "_" + TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum() + "_" + cont++;
						ResultBean resultBean = servicioCola.crearSolicitud(ProcesosEnum.IMPR_NOCTURNO.getNombreEnum(), datos, 
								gestorPropiedades.valorPropertie(NOMBRE_HOST), TipoTramiteTrafico.Solicitud_Permiso.getValorEnum(), 
								docPermiso.toString(), contratoDto.getColegiadoDto().getUsuario().getIdUsuario(), null, 
								contratoDto.getIdContrato());
						if (resultBean.getError()) {
							resultGen.setError(Boolean.TRUE);
							resultGen.setMensaje(resultBean.getMensaje());
						}
					} else {
						resultado.addError();
						resultado.addListaError(resultGen.getMensaje());
					}
				}
			} else {
				List<TramiteTrafDuplicadoVO> listaTramites = resultado.getListaTramitesDuplicado();
				ResultadoPermisoDistintivoItvBean resultGen = servicioDocPrmDstvFicha.generarDoc(contratoDto.getColegiadoDto().getUsuario().getIdUsuario(), fecha, 
						TipoDocumentoImprimirEnum.PERMISO_CIRCULACION, null, 
						contratoDto.getIdContrato().longValue(), contratoDto.getJefaturaTraficoDto().getJefaturaProvincial(), 
						Boolean.FALSE);
				if(!resultGen.getError()){
					Long docPermiso = resultGen.getIdDocPermDstvEitv();
					String sDocPermiso = resultGen.getDocId();
					for(TramiteTrafDuplicadoVO tramiteTrafDupliVO : listaTramites){
						tramiteTrafDupliVO.setDocPermiso(docPermiso);
						tramiteTrafDupliVO.setEstadoSolPerm(EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum());
						servicioTramiteTrafico.actualizarTramite(tramiteTrafDupliVO);
						servicioEvolucionPrm.guardarEvolucion(tramiteTrafDupliVO.getNumExpediente(),
								contratoDto.getColegiadoDto().getUsuario().getIdUsuario(),
								TipoDocumentoImprimirEnum.PERMISO_CIRCULACION,OperacionPrmDstvFicha.CREACION, new Date(),
								null, EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum(),sDocPermiso,ipConexion);
					}
					datos += "_" + contratoDto.getIdContrato() + "_" + TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum();
					ResultBean resultBean = servicioCola.crearSolicitud(ProcesosEnum.IMPR_NOCTURNO.getNombreEnum(), datos, 
							gestorPropiedades.valorPropertie(NOMBRE_HOST), TipoTramiteTrafico.Solicitud_Permiso.getValorEnum(), 
							docPermiso.toString(), contratoDto.getColegiadoDto().getUsuario().getIdUsuario(), null, 
							contratoDto.getIdContrato());
					if(resultBean.getError()){
						resultado.addError();
						resultado.addListaError(resultBean.getMensaje());
					}
				} else {
					resultado.addError();
					resultado.addListaError(resultGen.getMensaje());
				}
			}
		}
		return resultado;
	}

	private ResultadoPermisoDistintivoItvBean generarDocPermisosCtit(ContratoDto contratoDto, Date fecha, String ipConexion) throws OegamExcepcion {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		String datos = new SimpleDateFormat("ddMMyyyy").format(fecha) + "_" + TipoTramiteTrafico.TransmisionElectronica.getValorEnum();
		resultado = servicioTramiteTrafico.getTramitesFinalizadosTelematicamentePorContrato(contratoDto.getIdContrato().longValue(), fecha);
		if(!resultado.getError()){
			if(resultado.getListaTramitesCtit().size() > 500){
				final List<List<TramiteTrafTranVO>> auxListas = Lists.partition(resultado.getListaTramitesCtit(), 500);
				int cont = 0;
				for(List<TramiteTrafTranVO> listaTramites : auxListas){
					ResultadoPermisoDistintivoItvBean resultGen = servicioDocPrmDstvFicha.generarDoc(contratoDto.getColegiadoDto().getUsuario().getIdUsuario(), new Date(),
							TipoDocumentoImprimirEnum.PERMISO_CIRCULACION, null, 
							contratoDto.getIdContrato().longValue(), contratoDto.getJefaturaTraficoDto().getJefaturaProvincial(), 
							Boolean.FALSE);
					if(!resultGen.getError()){
						Long docPermiso = resultGen.getIdDocPermDstvEitv();
						String sDocPermiso = resultGen.getDocId();
						for(TramiteTrafTranVO tramiteTrafTranVO : listaTramites){
							tramiteTrafTranVO.setDocPermiso(docPermiso);
							tramiteTrafTranVO.setEstadoSolPerm(EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum());
							tramiteTrafTranVO.setEstadoImpPerm(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
							servicioTramiteTrafico.actualizarTramite(tramiteTrafTranVO);
							servicioEvolucionPrm.guardarEvolucion(tramiteTrafTranVO.getNumExpediente(),
									contratoDto.getColegiadoDto().getUsuario().getIdUsuario(),
									TipoDocumentoImprimirEnum.PERMISO_CIRCULACION,OperacionPrmDstvFicha.CREACION, new Date(),
									null, EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum(),sDocPermiso,ipConexion);
						}
						datos += "_" + contratoDto.getIdContrato() + "_" + TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum() + "_" + cont++;
						ResultBean resultBean = servicioCola.crearSolicitud(ProcesosEnum.IMPR_NOCTURNO.getNombreEnum(), datos, 
								gestorPropiedades.valorPropertie(NOMBRE_HOST), TipoTramiteTrafico.Solicitud_Permiso.getValorEnum(), 
								docPermiso.toString(), contratoDto.getColegiadoDto().getUsuario().getIdUsuario(), null, 
								contratoDto.getIdContrato());
						if(resultBean.getError()){
							resultGen.setError(Boolean.TRUE);
							resultGen.setMensaje(resultBean.getMensaje());
						}
					} else {
						resultado.addError();
						resultado.addListaError(resultGen.getMensaje());
					}
				}
			} else {
				List<TramiteTrafTranVO> listaTramites = resultado.getListaTramitesCtit();
				ResultadoPermisoDistintivoItvBean resultGen = servicioDocPrmDstvFicha.generarDoc(contratoDto.getColegiadoDto().getUsuario().getIdUsuario(), fecha, 
						TipoDocumentoImprimirEnum.PERMISO_CIRCULACION, null, 
						contratoDto.getIdContrato().longValue(), contratoDto.getJefaturaTraficoDto().getJefaturaProvincial(), 
						Boolean.FALSE);
				if(!resultGen.getError()){
					Long docPermiso = resultGen.getIdDocPermDstvEitv();
					String sDocPermiso = resultGen.getDocId();
					for(TramiteTrafTranVO tramiteTrafTranVO : listaTramites){
						tramiteTrafTranVO.setDocPermiso(docPermiso);
						tramiteTrafTranVO.setEstadoSolPerm(EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum());
						servicioTramiteTrafico.actualizarTramite(tramiteTrafTranVO);
						servicioEvolucionPrm.guardarEvolucion(tramiteTrafTranVO.getNumExpediente(),
								contratoDto.getColegiadoDto().getUsuario().getIdUsuario(),
								TipoDocumentoImprimirEnum.PERMISO_CIRCULACION,OperacionPrmDstvFicha.CREACION, new Date(),
								null, EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum(),sDocPermiso,ipConexion);
					}
					datos += "_" + contratoDto.getIdContrato() + "_" + TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum();
					ResultBean resultBean = servicioCola.crearSolicitud(ProcesosEnum.IMPR_NOCTURNO.getNombreEnum(), datos, 
							gestorPropiedades.valorPropertie(NOMBRE_HOST), TipoTramiteTrafico.Solicitud_Permiso.getValorEnum(), 
							docPermiso.toString(), contratoDto.getColegiadoDto().getUsuario().getIdUsuario(), null, 
							contratoDto.getIdContrato());
					if(resultBean.getError()){
						resultado.addError();
						resultado.addListaError(resultBean.getMensaje());
					}
				} else {
					resultado.addError();
					resultado.addListaError(resultGen.getMensaje());
				}
			}
		}
		return resultado;
	}

	private ResultadoPermisoDistintivoItvBean generarDocPermisosMatw(ContratoDto contratoDto, Date fecha, String ipConexion) throws OegamExcepcion {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		resultado = servicioTramiteTraficoMatriculacion.listaTramitesFinalizadosTelmPorContrato(contratoDto,fecha, TipoTramiteTrafico.Solicitud_Permiso.getValorEnum(), null);
		if(!resultado.getError()){
			if(resultado.getListaTramitesA() != null && !resultado.getListaTramitesA().isEmpty()) {
				tratarListaTramites(resultado.getListaTramitesA(), fecha, contratoDto, ipConexion, resultado);
			}
			if (resultado.getListaTramitesNormal() != null && !resultado.getListaTramitesNormal().isEmpty()) {
				tratarListaTramites(resultado.getListaTramitesNormal(), fecha, contratoDto, ipConexion, resultado);
			}
		}
		return resultado;
	}
	
	private void tratarListaTramites(List<TramiteTrafMatrVO> listaTramites, Date fecha, ContratoDto contratoDto, String ipConexion, ResultadoPermisoDistintivoItvBean resultado) throws OegamExcepcion {
		String datos = new SimpleDateFormat("ddMMyyyy").format(fecha) + "_" + TipoTramiteTrafico.Matriculacion.getValorEnum();
		if(listaTramites.size() > 250){
			final List<List<TramiteTrafMatrVO>> auxListas = Lists.partition(listaTramites, 250);
			int cont = 0;
			for (List<TramiteTrafMatrVO> listaTramitesVO : auxListas) {
				ResultadoPermisoDistintivoItvBean resultGen = servicioDocPrmDstvFicha.generarDoc(contratoDto.getColegiadoDto().getUsuario().getIdUsuario(), new Date(),
						TipoDocumentoImprimirEnum.PERMISO_CIRCULACION, null, 
						contratoDto.getIdContrato().longValue(), contratoDto.getJefaturaTraficoDto().getJefaturaProvincial(),
						Boolean.FALSE);
				if(!resultGen.getError()){
					Long docPermiso = resultGen.getIdDocPermDstvEitv();
					String sDocPermiso = resultGen.getDocId();
					for(TramiteTrafMatrVO tramiteTrafMatrVO : listaTramitesVO){
						tramiteTrafMatrVO.setDocPermiso(docPermiso);
						tramiteTrafMatrVO.setEstadoSolPerm(EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum());
						tramiteTrafMatrVO.setEstadoImpPerm(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
						servicioTramiteTrafico.actualizarTramite(tramiteTrafMatrVO);
						servicioEvolucionPrm.guardarEvolucion(tramiteTrafMatrVO.getNumExpediente(),
								contratoDto.getColegiadoDto().getUsuario().getIdUsuario(),
								TipoDocumentoImprimirEnum.PERMISO_CIRCULACION,OperacionPrmDstvFicha.CREACION, new Date(),
								null, EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum(),sDocPermiso,ipConexion);
					}
					datos += "_" + contratoDto.getIdContrato() + "_" + TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum() + "_" + cont++;
					ResultBean resultBean = servicioCola.crearSolicitud(ProcesosEnum.IMPR_NOCTURNO.getNombreEnum(), datos, 
							gestorPropiedades.valorPropertie(NOMBRE_HOST), TipoTramiteTrafico.Solicitud_Permiso.getValorEnum(), 
							docPermiso.toString(), contratoDto.getColegiadoDto().getUsuario().getIdUsuario(), null, 
							contratoDto.getIdContrato());
					if(resultBean.getError()){
						resultGen.setError(Boolean.TRUE);
						resultGen.setMensaje(resultBean.getMensaje());
					}
				} else {
					resultado.addError();
					resultado.addListaError(resultGen.getMensaje());
				}
			}
		} else {
			List<TramiteTrafMatrVO> listaTramitesVO = listaTramites;
			ResultadoPermisoDistintivoItvBean resultGen = servicioDocPrmDstvFicha.generarDoc(contratoDto.getColegiadoDto().getUsuario().getIdUsuario(), new Date(),
					TipoDocumentoImprimirEnum.PERMISO_CIRCULACION, null, 
					contratoDto.getIdContrato().longValue(), contratoDto.getJefaturaTraficoDto().getJefaturaProvincial(),
					Boolean.FALSE);
			if(!resultGen.getError()){
				Long docPermiso = resultGen.getIdDocPermDstvEitv();
				String sDocPermiso = resultGen.getDocId();
				for(TramiteTrafMatrVO tramiteTrafMatrVO : listaTramitesVO){
					tramiteTrafMatrVO.setDocPermiso(docPermiso);
					tramiteTrafMatrVO.setEstadoSolPerm(EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum());
					servicioTramiteTrafico.actualizarTramite(tramiteTrafMatrVO);
					servicioEvolucionPrm.guardarEvolucion(tramiteTrafMatrVO.getNumExpediente(),
							contratoDto.getColegiadoDto().getUsuario().getIdUsuario(),
							TipoDocumentoImprimirEnum.PERMISO_CIRCULACION,OperacionPrmDstvFicha.CREACION, new Date(),
							null, EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum(),sDocPermiso,ipConexion);
				}
				datos += "_" + contratoDto.getIdContrato() + "_" + TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum();
				ResultBean resultBean = servicioCola.crearSolicitud(ProcesosEnum.IMPR_NOCTURNO.getNombreEnum(), datos,
						gestorPropiedades.valorPropertie(NOMBRE_HOST), TipoTramiteTrafico.Solicitud_Permiso.getValorEnum(),
						docPermiso.toString(), contratoDto.getColegiadoDto().getUsuario().getIdUsuario(), null, 
						contratoDto.getIdContrato());
				if(resultBean.getError()){
					resultado.addError();
					resultado.addListaError(resultBean.getMensaje());
				}
			} else {
				resultado.addError();
				resultado.addListaError(resultGen.getMensaje());
			}
		}

	}

	@Override
	@Transactional
	public void solicitarPermisoFinalizadoError(TramiteTraficoVO tramiteTrafico, BigDecimal idUsuario, String ipConexion) {
		try {
			ResultadoPermisoDistintivoItvBean resultado = validarSolicitudCambioEstado(tramiteTrafico);
			if(!resultado.getError()){
				String estadoAnt = tramiteTrafico.getEstadoSolPerm();
				tramiteTrafico.setEstadoSolPerm(EstadoPermisoDistintivoItv.Pendiente_Respuesta.getValorEnum());
				servicioTramiteTrafico.actualizarTramite(tramiteTrafico);
				resultado = servicioEvolucionPrm.guardarEvolucion(tramiteTrafico.getNumExpediente(),idUsuario,TipoDocumentoImprimirEnum.PERMISO_CIRCULACION,
						OperacionPrmDstvFicha.SOLICITUD,new Date(),estadoAnt, EstadoPermisoDistintivoItv.Pendiente_Respuesta.getValorEnum(),null,ipConexion);
				if(!resultado.getError()){
					ResultBean resultCola = servicioCola.crearSolicitud(ProcesosEnum.IMPR_DEMANDA.getNombreEnum(), 
							null, gestorPropiedades.valorPropertie(NOMBRE_HOST), TipoTramiteTrafico.Solicitud_Permiso.getValorEnum(), 
							tramiteTrafico.getNumExpediente().toString(), idUsuario, null,new BigDecimal(tramiteTrafico.getContrato().getIdContrato()));
					if(resultCola.getError()){
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultCola.getMensaje());
					}
				}
			} else {
				log.info(resultado.getMensaje());
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de solictar el permiso cuando se ha cambiado el estado, error: ",e);
		}

	}

	private ResultadoPermisoDistintivoItvBean validarSolicitudCambioEstado(TramiteTraficoVO tramiteTrafico) throws ParseException {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		if(servicioPermisos.tienePermisoElContrato(tramiteTrafico.getContrato().getIdContrato(), "OT19M", "OEGAM_TRAF")){
			resultado = servicioTramiteTraficoTransmision.comprobarCheckImpresionPermiso(tramiteTrafico.getNumExpediente());
			if (!resultado.getError()) {
				if (tramiteTrafico.getNumImpresionesPerm() != null && tramiteTrafico.getNumImpresionesPerm() >= 10) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede solicitar el permisos para el expediente" + tramiteTrafico.getNumExpediente() + " porque ha excedido el limite de 10 impresiones.");
				} else if(tramiteTrafico.getEstadoSolPerm() != null && (!EstadoPermisoDistintivoItv.Iniciado.getValorEnum().equals(tramiteTrafico.getEstadoSolPerm()))
						&& !EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum().equals(tramiteTrafico.getEstadoSolPerm())){
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede solicitar el permisos para el expediente" + tramiteTrafico.getNumExpediente() + ", el estado de la solicitud debe de ser Iniciado.");
				}else if(tramiteTrafico.getVehiculo() == null || (tramiteTrafico.getVehiculo().getMatricula() == null || tramiteTrafico.getVehiculo().getMatricula().isEmpty())){
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede solicitar el permisos para el expediente" + tramiteTrafico.getNumExpediente() + " porque el vehículo no tiene matricula guardada.");
				} else if(utilesFecha.compararFechaDate(tramiteTrafico.getFechaPresentacion(), new Date()) == 0){
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede solicitar el permisos para el expediente" + tramiteTrafico.getNumExpediente() + " porque saldra de forma automatica por la noche.");
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No tiene permisos para solicitar el PC.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean solicitarPermiso(BigDecimal numExpediente, BigDecimal idUsuario, Date fecha, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			TramiteTraficoVO tramiteTrafico = servicioTramiteTrafico.getTramite(numExpediente, Boolean.TRUE);
			resultado = validarTramSolicitudPermiso(tramiteTrafico, numExpediente);
			if(!resultado.getError()){
				String estadoAnt = tramiteTrafico.getEstadoSolPerm();
				tramiteTrafico.setEstadoSolPerm(EstadoPermisoDistintivoItv.Pendiente_Respuesta.getValorEnum());
				servicioTramiteTrafico.actualizarTramite(tramiteTrafico);
				resultado = servicioEvolucionPrm.guardarEvolucion(numExpediente,idUsuario,TipoDocumentoImprimirEnum.PERMISO_CIRCULACION,
						OperacionPrmDstvFicha.SOLICITUD,fecha,estadoAnt, EstadoPermisoDistintivoItv.Pendiente_Respuesta.getValorEnum(),null,ipConexion);
				if(!resultado.getError()){
					ResultBean resultCola = servicioCola.crearSolicitud(ProcesosEnum.IMPR_DEMANDA.getNombreEnum(), 
							null, gestorPropiedades.valorPropertie(NOMBRE_HOST), TipoTramiteTrafico.Solicitud_Permiso.getValorEnum(), 
							numExpediente.toString(), idUsuario, null,new BigDecimal(tramiteTrafico.getContrato().getIdContrato()));
					if(resultCola.getError()){
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultCola.getMensaje());
					}
				}
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de solicitar el permiso para el expediente: " + numExpediente + ", error: ",e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar el permiso para el expediente: " + numExpediente );
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public void actualizarEstadoPeticion(BigDecimal numExpediente, EstadoPermisoDistintivoItv estadoNuevo, BigDecimal idUsuario, String ipConexion) {
		try {
			TramiteTraficoVO tramiteTrafico = servicioTramiteTrafico.getTramite(numExpediente, Boolean.TRUE);
			if(tramiteTrafico != null){
				String estadoAnt = tramiteTrafico.getEstadoSolPerm();
				tramiteTrafico.setEstadoSolPerm(estadoNuevo.getValorEnum());
				servicioTramiteTrafico.actualizarTramite(tramiteTrafico);
				ResultadoPermisoDistintivoItvBean resultado = servicioEvolucionPrm.guardarEvolucion(numExpediente,idUsuario,TipoDocumentoImprimirEnum.PERMISO_CIRCULACION,
						OperacionPrmDstvFicha.SOLICITUD,new Date(),estadoAnt, estadoNuevo.getValorEnum(),null,ipConexion);
				if(resultado.getError()){
					log.error(resultado.getMensaje());
				}
			} else {
				log.error("No existen datos para el expediente: "+ numExpediente);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el estado de la solicitud de los permisos, error: ",e, numExpediente.toString());
		}
	}

	private ResultadoPermisoDistintivoItvBean validarTramSolicitudPermiso(TramiteTraficoVO tramiteTrafico, BigDecimal numExpediente) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		if(tramiteTrafico == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos para el expediente" + numExpediente );
		} else if(tramiteTrafico.getNumImpresionesPerm() != null && tramiteTrafico.getNumImpresionesPerm() >= 10){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede solicitar el permisos para el expediente" + numExpediente + " porque ha excedido el limite de 10 impresiones.");
		} else if(tramiteTrafico.getEstadoSolPerm() != null && (!EstadoPermisoDistintivoItv.Iniciado.getValorEnum().equals(tramiteTrafico.getEstadoSolPerm()))
				&& !EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum().equals(tramiteTrafico.getEstadoSolPerm())){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede solicitar el permisos para el expediente" + numExpediente + ", el estado de la solicitud debe de ser Iniciado.");
		}else if(tramiteTrafico.getVehiculo() == null || (tramiteTrafico.getVehiculo().getMatricula() == null || tramiteTrafico.getVehiculo().getMatricula().isEmpty())){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede solicitar el permisos para el expediente" + numExpediente + " porque el vehículo no tiene matricula guardada.");
		} else if(!EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteTrafico.getEstado().toString()) &&
				!EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum().equals(tramiteTrafico.getEstado().toString()) &&
						!EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum().equals(tramiteTrafico.getEstado().toString())){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede solicitar el permisos para el expediente" + numExpediente + " porque el trámite debe de estar en estado finalizado telematicamente, telematicamente impreso o telematicamente revisado.");
		} else {
			if(TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTrafico.getTipoTramite())){
				String colegiadosImprTodos = gestorPropiedades.valorPropertie("colegiado.impresion.impr.matw");
				Boolean esJefatura = Boolean.FALSE;
				if("SI".equals(gestorPropiedades.valorPropertie("impresion.todos.impr.Matw.jefaturas"))){
					String sJefaturasAuto = gestorPropiedades.valorPropertie("jefauras.imprimir.todos.impr.matw");
					if(sJefaturasAuto != null && !sJefaturasAuto.isEmpty()){
						String[] jefaturasAuto = sJefaturasAuto.split(",");
						for(String jefaturoProv : jefaturasAuto){
							if(jefaturoProv.equals(tramiteTrafico.getContrato().getJefaturaTrafico().getJefaturaProvincial())){
								esJefatura = Boolean.TRUE;
								break;
							}
						}
					}
				} else {
					if(colegiadosImprTodos.contains(tramiteTrafico.getContrato().getColegiado().getNumColegiado())){
						esJefatura = Boolean.TRUE;
					}
				}
				if(!esJefatura){
					resultado = servicioTramiteTraficoMatriculacion.comprobarSuperTelematicoMate(tramiteTrafico.getIntervinienteTraficosAsList(), numExpediente, tramiteTrafico.getContrato().getIdContrato());
				}
			} else if(TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTrafico.getTipoTramite())){
				String sJefaturasAutoCtit = gestorPropiedades.valorPropertie("jefauras.imprimir.todos.impr.ctit");
				Boolean esJefaturaCtit = Boolean.FALSE;
				if(sJefaturasAutoCtit != null && !sJefaturasAutoCtit.isEmpty()){
					String[] jefaturasAuto = sJefaturasAutoCtit.split(",");
					for(String jefaturoProv : jefaturasAuto){
						if(jefaturoProv.equals(tramiteTrafico.getContrato().getJefaturaTrafico().getJefaturaProvincial())){
							esJefaturaCtit = Boolean.TRUE;
							break;
						}
					}
				}
				if(esJefaturaCtit){
					resultado = servicioTramiteTraficoTransmision.comprobarCheckImpresionPermiso(numExpediente);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se pueden solicitar permisos de dicha jefatura provincial.");
				}
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean impresionPermisos(String numExpedientes, BigDecimal idUsuario, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			String[] numsExp = numExpedientes.split("-");
			List<TramiteTraficoVO> listaTramites = servicioTramiteTrafico.getListaExpedientesPorListNumExp(utiles.convertirStringArrayToBigDecimal(numsExp), Boolean.TRUE);
			resultado = validarListaTramites(listaTramites);
			if(!resultado.getError()){
				Date fecha = new Date();
				resultado = servicioDocPrmDstvFicha.generarDoc(idUsuario,fecha,TipoDocumentoImprimirEnum.PERMISO_CIRCULACION, null,listaTramites.get(0).getContrato().getIdContrato(), listaTramites.get(0).getContrato().getJefaturaTrafico().getJefaturaProvincial(), Boolean.TRUE);
				if(!resultado.getError()){
					Long idDoc = resultado.getIdDocPermDstvEitv();
					String docId = resultado.getDocId();
					for(TramiteTraficoVO tramiteTraficoVO : listaTramites){
						String estadoAnt = tramiteTraficoVO.getEstadoImpPerm();
						tramiteTraficoVO.setDocPermiso(idDoc);
						tramiteTraficoVO.setEstadoImpPerm(EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum());
						servicioTramiteTrafico.actualizarTramite(tramiteTraficoVO);
						servicioEvolucionPrm.guardarEvolucion(tramiteTraficoVO.getNumExpediente(), idUsuario, 
								TipoDocumentoImprimirEnum.PERMISO_CIRCULACION, OperacionPrmDstvFicha.SOL_IMPRESION, fecha, 
								estadoAnt, EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum(),docId,ipConexion);
					}
					resultado = generarPeticionImpresionPermisos(idDoc,idUsuario, listaTramites.get(0).getContrato().getIdContrato(), listaTramites.get(0).getTipoTramite());
					if(!resultado.getError()){
						resultado.setMensaje("El identificador del documento generado es: " + docId);
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de solicitar la impresion de los permisos, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar la impresion de los permisos.");
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean generarKoTramite(BigDecimal numExpediente, BigDecimal idUsuario, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			Date fecha = new Date();
			TramiteTraficoVO tramiteTraficoVO = servicioTramiteTrafico.getTramite(numExpediente, Boolean.FALSE);
			String estadoAnt = tramiteTraficoVO.getEstadoSolPerm();
			tramiteTraficoVO.setEstadoSolPerm(EstadoPermisoDistintivoItv.GENERADO_KO.getValorEnum());
			tramiteTraficoVO.setDocPermiso(null);
			servicioTramiteTrafico.actualizarTramite(tramiteTraficoVO);
			servicioEvolucionPrm.guardarEvolucion(tramiteTraficoVO.getNumExpediente(),
					idUsuario, TipoDocumentoImprimirEnum.PERMISO_CIRCULACION,OperacionPrmDstvFicha.GENERADO_KO, fecha,
					estadoAnt, EstadoPermisoDistintivoItv.GENERADO_KO.getValorEnum(),null,ipConexion);
			servicioPersistenciaImprKO.crearKoTramite(numExpediente,TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.toString(), idUsuario.longValue());
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el KO del tramite: " + numExpediente + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el KO del tramite: " + numExpediente);
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoPermisoDistintivoItvBean generarPeticionImpresionPermisos(Long idDoc, BigDecimal idUsuario, Long idContrato, String tipoTramite) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			String proceso = null;
			if(TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)){
				proceso = ProcesosEnum.IMP_PRM_MATW.getNombreEnum();
			} else {
				proceso = ProcesosEnum.IMP_PRM_CTIT.getNombreEnum();
			}
			ResultBean resultBean = servicioCola.crearSolicitud(proceso, null, 
					gestorPropiedades.valorPropertie(NOMBRE_HOST), TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum(), 
					idDoc.toString(), idUsuario, null, new BigDecimal(idContrato));
			if(resultBean.getError()){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultBean.getMensaje());
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora encolar la peticion de impresion de los permisos, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar la impresion de los permisos.");
		}
		return resultado;
	}

	private ResultadoPermisoDistintivoItvBean validarListaTramites(List<TramiteTraficoVO> listaTramites) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		if(listaTramites == null || listaTramites.isEmpty()){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos para los expedientes seleccionados.");
		} else {
			Long idContrato = listaTramites.get(0).getContrato().getIdContrato();
			String tipoTramite = listaTramites.get(0).getTipoTramite();
			for (TramiteTraficoVO tramiteTrafico : listaTramites) {
				if (!EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum().equals(tramiteTrafico.getEstadoSolPerm())) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede solicitar la impresion del permiso para el expediente" + tramiteTrafico.getNumExpediente() + ", el estado de la solicitud debe de ser Documentacion Recibida.");
					break;
				}
				/*
				 * else if(tramiteTrafico.getPresentadoJpt() == null &&
				 * !EstadoPresentacionJPT.Presentado.getValorEnum().equals(tramiteTrafico.
				 * getPresentadoJpt()) &&
				 * TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(
				 * tramiteTrafico.getTipoTramite())){ resultado.setError(Boolean.TRUE);
				 * resultado.
				 * setMensaje("No se puede solicitar la impresion del permiso para el expediente"
				 * + tramiteTrafico.getNumExpediente() +
				 * ", debe presentar la documentación del trámite para obtener el permiso.");
				 * break; }
				 */
				 else if (idContrato != tramiteTrafico.getContrato().getIdContrato()) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Para poder imprimir en bloque los permisos todos deben de ser del mismo contrato.");
					break;
				} else if (!tipoTramite.equals(tramiteTrafico.getTipoTramite())) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Para poder imprimir en bloque los permisos todos deben de ser del mismo tipo.");
					break;
				}
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean cambiarEstado(BigDecimal numExpediente, BigDecimal idUsuario, Date fecha, BigDecimal estadoNuevo, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			TramiteTraficoVO tramiteTrafico = servicioTramiteTrafico.getTramite(numExpediente, Boolean.TRUE);
			String estadoAnt = tramiteTrafico.getEstadoSolPerm();
			tramiteTrafico.setEstadoSolPerm(estadoNuevo.toString());
			servicioTramiteTrafico.actualizarTramite(tramiteTrafico);
			resultado = servicioEvolucionPrm.guardarEvolucion(numExpediente,idUsuario,TipoDocumentoImprimirEnum.PERMISO_CIRCULACION,
						OperacionPrmDstvFicha.CAMBIO_ESTADO,fecha,estadoAnt, estadoNuevo.toString(),null,ipConexion);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de revertir el permiso para el expediente: " + numExpediente + ", error: ", e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de revertir el permiso para el expediente: " + numExpediente );
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean revertirPermiso(BigDecimal numExpediente, BigDecimal idUsuario, Date fecha, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			TramiteTraficoVO tramiteTrafico = servicioTramiteTrafico.getTramite(numExpediente, Boolean.TRUE);
			resultado = validarTramRevertirPermiso(tramiteTrafico, numExpediente);
			if(!resultado.getError()){
				String estadoAnt = tramiteTrafico.getEstadoSolPerm();
				String docId = tramiteTrafico.getDocPermisoVO().getDocIdPerm();
				tramiteTrafico.setEstadoSolPerm(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
				tramiteTrafico.setEstadoImpPerm(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
				tramiteTrafico.setDocPermiso(null);
				tramiteTrafico.setDocPermisoVO(null);
				servicioTramiteTrafico.actualizarTramite(tramiteTrafico);
				resultado = servicioEvolucionPrm.guardarEvolucion(numExpediente,idUsuario,TipoDocumentoImprimirEnum.PERMISO_CIRCULACION,
						OperacionPrmDstvFicha.REVERTIR,fecha,estadoAnt, EstadoPermisoDistintivoItv.Iniciado.getValorEnum(),docId,ipConexion);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de revertir el permiso para el expediente: " + numExpediente + ", error: ",e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de revertir el permiso para el expediente: " + numExpediente );
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoPermisoDistintivoItvBean validarTramRevertirPermiso(TramiteTraficoVO tramiteTrafico, BigDecimal numExpediente) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		if(tramiteTrafico == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos para el expediente" + numExpediente );
		} else if(!EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum().equals(tramiteTrafico.getEstadoImpPerm())){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede revertir el permisos para el expediente" + numExpediente + ", porque no se encuentra impreso todavia.");
		} else if(tramiteTrafico.getDocPermiso() == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede revertir el permisos para el expediente" + numExpediente + ", porque no se encuentran datos de su documento.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly=true)
	public List<TramiteTraficoVO> getListaTramitesPorDocId(Long idDocPermisos) {
		try {
			return servicioTramiteTrafico.getListaTramitesPermisosPorDocId(idDocPermisos);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el listado de tramites de los permisos del docId: " + idDocPermisos + ", error: ",e);
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional
	public ResultadoDistintivoDgtBean desasignarPermiso(BigDecimal numExpediente, BigDecimal idUsuario, String ipConexion) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			TramiteTraficoVO tramiteTraf = servicioTramiteTrafico.getTramite(numExpediente, Boolean.TRUE);
			if (tramiteTraf != null) {
				String estadoAnt = tramiteTraf.getEstadoImpPerm();
				String docId = tramiteTraf.getDocPermisoVO().getDocIdPerm();
				tramiteTraf.setEstadoImpPerm(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
				tramiteTraf.setEstadoSolPerm(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
				tramiteTraf.setDocPermiso(null);
				servicioTramiteTrafico.actualizarTramite(tramiteTraf);
				servicioEvolucionPrm.guardarEvolucion(tramiteTraf.getNumExpediente(), idUsuario, TipoDocumentoImprimirEnum.PERMISO_CIRCULACION, OperacionPrmDstvFicha.DESASIGNAR, new Date(),
						estadoAnt, tramiteTraf.getEstadoImpPerm(), docId,ipConexion);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de desasignar los documentos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de desasignar los documentos.");
		}
		return resultado;
	}
}