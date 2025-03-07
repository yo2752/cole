package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.impl;

import java.math.BigDecimal;
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
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.consultaKo.model.service.ServicioConsultaKo;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.evolucionPrmDstvFicha.model.service.ServicioEvolucionPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioDocPrmDstvFicha;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service.ServicioFichasTecnicasDgt;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.empresa.telematica.model.service.ServicioEmpresaTelematica;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoDuplicado;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.impr.service.ServicioPersistenciaImprKO;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
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
public class ServicioFichasTecnicasDgtImpl implements ServicioFichasTecnicasDgt{

	private static final long serialVersionUID = 5973321265527022767L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioFichasTecnicasDgtImpl.class);

	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;
	
	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;
	
	@Autowired
	ServicioTramiteTraficoDuplicado servicioTramiteTraficoDuplicado;
	
	@Autowired
	ServicioEvolucionPrmDstvFicha servicioEvolucionFicha;
	
	@Autowired
	ServicioEmpresaTelematica servicioEmpresaTelematica;
	
	@Autowired
	ServicioIntervinienteTrafico servicioIntervinienteTrafico;
	
	@Autowired
	ServicioConsultaKo servicioConsultaKo;
	
	@Autowired
	ServicioCola servicioCola;
	
	@Autowired
	ServicioDocPrmDstvFicha servicioDocPrmDstvFicha;
	
	@Autowired
	GestorPropiedades gestorPropiedades;
	
	@Autowired
	Utiles utiles;
	
	@Autowired
	ServicioPersistenciaImprKO servicioPersistenciaImprKO;

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean cambiarEstado(BigDecimal numExpediente, BigDecimal idUsuario, Date fecha, BigDecimal estadoNuevo, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado =  new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			TramiteTraficoVO tramiteTrafico = servicioTramiteTrafico.getTramite(numExpediente, Boolean.TRUE);
			String estadoAnt = tramiteTrafico.getEstadoSolPerm();
			tramiteTrafico.setEstadoSolFicha(estadoNuevo.toString());
			servicioTramiteTrafico.actualizarTramite(tramiteTrafico);
			resultado = servicioEvolucionFicha.guardarEvolucion(numExpediente,idUsuario,TipoDocumentoImprimirEnum.FICHA_TECNICA,
						OperacionPrmDstvFicha.CAMBIO_ESTADO,fecha,estadoAnt, estadoNuevo.toString(),null,ipConexion);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de revertir la ficha para el expediente: " + numExpediente + ", error: ",e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de revertir la ficha para el expediente: " + numExpediente );
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}
	
	@Override
	public ResultadoPermisoDistintivoItvBean generarDocFichasContratoCoches(ContratoDto contratoDto, Date fecha, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado  = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			String datos = new SimpleDateFormat("ddMMyyyy").format(fecha) + "_" + TipoTramiteTrafico.Matriculacion.getValorEnum();
			resultado = generarDocFichas(contratoDto, datos, Boolean.FALSE, fecha, ipConexion);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar las peticiones de impresion de fichas de coches para el contrato: " + contratoDto.getIdContrato() + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar las peticiones de fichas de coches para el contrato: " + contratoDto.getIdContrato());
		}
		return resultado;
	}
	
	@Override
	public ResultadoPermisoDistintivoItvBean generarDocFichasContratoCochesDupli(ContratoDto contratoDto, Date fecha, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado  = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			String datos = new SimpleDateFormat("ddMMyyyy").format(fecha) + "_" + TipoTramiteTrafico.Duplicado.getValorEnum();
			resultado = generarDocFichasDupli(contratoDto, datos, Boolean.FALSE, fecha, ipConexion);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar las peticiones de impresion de fichas de coches para el contrato: " + contratoDto.getIdContrato() + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar las peticiones de fichas de coches para el contrato: " + contratoDto.getIdContrato());
		}
		return resultado;
	}
	
	private ResultadoPermisoDistintivoItvBean generarDocFichasDupli(ContratoDto contratoDto, String datos, Boolean esMotos, Date fecha, String ipConexion) throws OegamExcepcion{
			ResultadoPermisoDistintivoItvBean resultado = servicioTramiteTraficoDuplicado.listaTramitesFinalizadosTelmPorContrato(contratoDto,fecha, TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum(), esMotos);
			if(!resultado.getError()){
				if(resultado.getListaTramitesDuplicado().size() > 500){
					final List<List<TramiteTrafDuplicadoVO>> auxListas = Lists.partition(resultado.getListaTramitesDuplicado(), 500);
					int cont = 0;
					for(List<TramiteTrafDuplicadoVO> listaTramites : auxListas){
						ResultadoPermisoDistintivoItvBean resultGen = servicioDocPrmDstvFicha.generarDoc(contratoDto.getColegiadoDto().getUsuario().getIdUsuario(), new Date(), 
								TipoDocumentoImprimirEnum.FICHA_TECNICA, null, 
								contratoDto.getIdContrato().longValue(), contratoDto.getJefaturaTraficoDto().getJefaturaProvincial(), 
								Boolean.FALSE);
						if(!resultGen.getError()){
							Long docFicha = resultGen.getIdDocPermDstvEitv();
							String sDocFicha = resultGen.getDocId();
							for(TramiteTrafDuplicadoVO tramiteTrafDuplicadoVO : listaTramites){
								tramiteTrafDuplicadoVO.setDocFichaTecnica(docFicha);
								tramiteTrafDuplicadoVO.setEstadoSolFicha(EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum());
								tramiteTrafDuplicadoVO.setEstadoImpFicha(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
								servicioTramiteTrafico.actualizarTramite(tramiteTrafDuplicadoVO);
								servicioEvolucionFicha.guardarEvolucion(tramiteTrafDuplicadoVO.getNumExpediente(),
										contratoDto.getColegiadoDto().getUsuario().getIdUsuario(),
										TipoDocumentoImprimirEnum.FICHA_TECNICA,OperacionPrmDstvFicha.CREACION, new Date(),
										null, EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum(),sDocFicha,ipConexion);
							}
							datos += "_" + contratoDto.getIdContrato() + "_" + TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum() + "_" + cont++;
							ResultBean resultBean = servicioCola.crearSolicitud(ProcesosEnum.IMPR_NOCTURNO.getNombreEnum(), datos, 
									gestorPropiedades.valorPropertie(NOMBRE_HOST), TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum(), 
									docFicha.toString(), contratoDto.getColegiadoDto().getUsuario().getIdUsuario(), null, 
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
					List<TramiteTrafDuplicadoVO> listaTramites = resultado.getListaTramitesDuplicado();
					ResultadoPermisoDistintivoItvBean resultGen = servicioDocPrmDstvFicha.generarDoc(contratoDto.getColegiadoDto().getUsuario().getIdUsuario(), new Date(), 
							TipoDocumentoImprimirEnum.FICHA_TECNICA, null, 
							contratoDto.getIdContrato().longValue(), contratoDto.getJefaturaTraficoDto().getJefaturaProvincial(), 
							Boolean.FALSE);
					if(!resultGen.getError()){
						Long docFicha = resultGen.getIdDocPermDstvEitv();
						String sDocFicha = resultGen.getDocId();
						for(TramiteTrafDuplicadoVO tramiteTrafDuplicadoVO : listaTramites){
							tramiteTrafDuplicadoVO.setDocFichaTecnica(docFicha);
							tramiteTrafDuplicadoVO.setEstadoSolFicha(EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum());
							servicioTramiteTrafico.actualizarTramite(tramiteTrafDuplicadoVO);
							servicioEvolucionFicha.guardarEvolucion(tramiteTrafDuplicadoVO.getNumExpediente(),
									contratoDto.getColegiadoDto().getUsuario().getIdUsuario(),
									TipoDocumentoImprimirEnum.FICHA_TECNICA,OperacionPrmDstvFicha.CREACION, new Date(),
									null, EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum(),sDocFicha,ipConexion);
						}
						datos += "_" + contratoDto.getIdContrato() + "_" + TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum();
						ResultBean resultBean = servicioCola.crearSolicitud(ProcesosEnum.IMPR_NOCTURNO.getNombreEnum(), datos, 
								gestorPropiedades.valorPropertie(NOMBRE_HOST), TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum(), 
								docFicha.toString(), contratoDto.getColegiadoDto().getUsuario().getIdUsuario(), null, 
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
			}
			return resultado;
		}

	@Override
	public ResultadoPermisoDistintivoItvBean generarDocFichasContratoMotos(ContratoDto contratoDto, Date fecha, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado  = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			String datos = new SimpleDateFormat("ddMMyyyy").format(fecha) + "_" + TipoTramiteTrafico.Matriculacion.getValorEnum()+"_MOTOS";
			resultado = generarDocFichas(contratoDto, datos, Boolean.TRUE,fecha, ipConexion);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar las peticiones de impresion de fichas de motos para el contrato: " + contratoDto.getIdContrato() + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar las peticiones de fichas de motos para el contrato: " + contratoDto.getIdContrato());
		}
		return resultado;
	}
	
	private ResultadoPermisoDistintivoItvBean generarDocFichas(ContratoDto contratoDto, String datos, Boolean esMotos, Date fecha, String ipConexion) throws OegamExcepcion {
		ResultadoPermisoDistintivoItvBean resultado = servicioTramiteTraficoMatriculacion.listaTramitesFinalizadosTelmPorContrato(contratoDto,fecha, TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum(), esMotos);
		if(!resultado.getError()){
			if(resultado.getListaTramitesA() != null && !resultado.getListaTramitesA().isEmpty()) {
				tratarListaTramites(resultado.getListaTramitesA(),fecha,contratoDto,datos,ipConexion,resultado);
			}
			if (resultado.getListaTramitesNormal() != null && !resultado.getListaTramitesNormal().isEmpty()) {
				tratarListaTramites(resultado.getListaTramitesNormal(),fecha,contratoDto,datos,ipConexion,resultado);
			}
		}
		return resultado;
	}

	private void tratarListaTramites(List<TramiteTrafMatrVO> listaTramites, Date fecha, ContratoDto contratoDto, String ipConexion, String datos, ResultadoPermisoDistintivoItvBean resultado) throws OegamExcepcion {
		if(listaTramites.size() > 500){
			final List<List<TramiteTrafMatrVO>> auxListas = Lists.partition(listaTramites, 500);
			int cont = 0;
			for(List<TramiteTrafMatrVO> listaTramitesVO : auxListas){
				ResultadoPermisoDistintivoItvBean resultGen = servicioDocPrmDstvFicha.generarDoc(contratoDto.getColegiadoDto().getUsuario().getIdUsuario(), new Date(), 
						TipoDocumentoImprimirEnum.FICHA_TECNICA, null, 
						contratoDto.getIdContrato().longValue(), contratoDto.getJefaturaTraficoDto().getJefaturaProvincial(), 
						Boolean.FALSE);
				if(!resultGen.getError()){
					Long docFicha = resultGen.getIdDocPermDstvEitv();
					String sDocFicha = resultGen.getDocId();
					for(TramiteTrafMatrVO tramiteTrafMatrVO : listaTramitesVO){
						tramiteTrafMatrVO.setDocFichaTecnica(docFicha);
						tramiteTrafMatrVO.setEstadoSolFicha(EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum());
						tramiteTrafMatrVO.setEstadoImpFicha(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
						servicioTramiteTrafico.actualizarTramite(tramiteTrafMatrVO);
						servicioEvolucionFicha.guardarEvolucion(tramiteTrafMatrVO.getNumExpediente(),
								contratoDto.getColegiadoDto().getUsuario().getIdUsuario(),
								TipoDocumentoImprimirEnum.FICHA_TECNICA,OperacionPrmDstvFicha.CREACION, new Date(),
								null, EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum(),sDocFicha,ipConexion);
					}
					datos += "_" + contratoDto.getIdContrato() + "_" + TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum() + "_" + cont++;
					ResultBean resultBean = servicioCola.crearSolicitud(ProcesosEnum.IMPR_NOCTURNO.getNombreEnum(), datos, 
							gestorPropiedades.valorPropertie(NOMBRE_HOST), TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum(), 
							docFicha.toString(), contratoDto.getColegiadoDto().getUsuario().getIdUsuario(), null, 
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
					TipoDocumentoImprimirEnum.FICHA_TECNICA, null, 
					contratoDto.getIdContrato().longValue(), contratoDto.getJefaturaTraficoDto().getJefaturaProvincial(), 
					Boolean.FALSE);
			if(!resultGen.getError()){
				Long docFicha = resultGen.getIdDocPermDstvEitv();
				String sDocFicha = resultGen.getDocId();
				for(TramiteTrafMatrVO tramiteTrafMatrVO : listaTramitesVO){
					tramiteTrafMatrVO.setDocFichaTecnica(docFicha);
					tramiteTrafMatrVO.setEstadoSolFicha(EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum());
					servicioTramiteTrafico.actualizarTramite(tramiteTrafMatrVO);
					servicioEvolucionFicha.guardarEvolucion(tramiteTrafMatrVO.getNumExpediente(),
							contratoDto.getColegiadoDto().getUsuario().getIdUsuario(),
							TipoDocumentoImprimirEnum.FICHA_TECNICA,OperacionPrmDstvFicha.CREACION, new Date(),
							null, EstadoPermisoDistintivoItv.SOLICITANDO_IMPR.getValorEnum(),sDocFicha,ipConexion);
				}
				datos += "_" + contratoDto.getIdContrato() + "_" + TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum();
				ResultBean resultBean = servicioCola.crearSolicitud(ProcesosEnum.IMPR_NOCTURNO.getNombreEnum(), datos, 
						gestorPropiedades.valorPropertie(NOMBRE_HOST), TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum(), 
						docFicha.toString(), contratoDto.getColegiadoDto().getUsuario().getIdUsuario(), null, 
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
		
	}

	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean generarKoTramite(BigDecimal numExpediente, BigDecimal idUsuario, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			Date fecha = new Date();
			TramiteTraficoVO tramiteTraficoVO = servicioTramiteTrafico.getTramite(numExpediente, Boolean.FALSE);
			String estadoAnt = tramiteTraficoVO.getEstadoSolFicha();
			tramiteTraficoVO.setEstadoSolFicha(EstadoPermisoDistintivoItv.GENERADO_KO.getValorEnum());
			tramiteTraficoVO.setDocPermiso(null);
			servicioTramiteTrafico.actualizarTramite(tramiteTraficoVO);
			servicioEvolucionFicha.guardarEvolucion(tramiteTraficoVO.getNumExpediente(),
					idUsuario, TipoDocumentoImprimirEnum.FICHA_TECNICA,OperacionPrmDstvFicha.GENERADO_KO, fecha,
					estadoAnt, EstadoPermisoDistintivoItv.GENERADO_KO.getValorEnum(),null,ipConexion);
			servicioPersistenciaImprKO.crearKoTramite(numExpediente,TipoDocumentoImprimirEnum.FICHA_TECNICA.toString(), idUsuario.longValue());
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
	
	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean solicitarFichaTecnica(BigDecimal numExpediente, BigDecimal idUsuario, Date fecha, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado =  new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			TramiteTraficoVO tramiteTrafico = servicioTramiteTrafico.getTramite(numExpediente, Boolean.TRUE);
			resultado = validarTramSolicitudFicha(tramiteTrafico, numExpediente);
			if(!resultado.getError()){
				String estadoAnt = tramiteTrafico.getEstadoSolPerm();
				tramiteTrafico.setEstadoSolFicha(EstadoPermisoDistintivoItv.Pendiente_Respuesta.getValorEnum());
				servicioTramiteTrafico.actualizarTramite(tramiteTrafico);
				resultado = servicioEvolucionFicha.guardarEvolucion(numExpediente,idUsuario,TipoDocumentoImprimirEnum.FICHA_TECNICA,
						OperacionPrmDstvFicha.SOLICITUD,fecha,estadoAnt, EstadoPermisoDistintivoItv.Pendiente_Respuesta.getValorEnum(),null,ipConexion);
				if(!resultado.getError()){
					ResultBean resultCola = servicioCola.crearSolicitud(ProcesosEnum.IMPR_DEMANDA.getNombreEnum(), 
							null, gestorPropiedades.valorPropertie(NOMBRE_HOST), TipoTramiteTrafico.Solicitud_Ficha_Tecnica.getValorEnum(), 
							numExpediente.toString(), idUsuario, null,new BigDecimal(tramiteTrafico.getContrato().getIdContrato()));
					if(resultCola.getError()){
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultCola.getMensaje());
					}
				}
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de solicitar la ficha técnica para el expediente: " + numExpediente + ", error: ",e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar la ficha técnica para el expediente: " + numExpediente );
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
				String estadoAnt = tramiteTrafico.getEstadoSolFicha();
				tramiteTrafico.setEstadoSolFicha(estadoNuevo.getValorEnum());
				servicioTramiteTrafico.actualizarTramite(tramiteTrafico);
				ResultadoPermisoDistintivoItvBean resultado = servicioEvolucionFicha.guardarEvolucion(numExpediente,idUsuario,TipoDocumentoImprimirEnum.FICHA_TECNICA,
						OperacionPrmDstvFicha.SOLICITUD,new Date(),estadoAnt, estadoNuevo.getValorEnum(),null,ipConexion);
				if(resultado.getError()){
					log.error(resultado.getMensaje());
				}
			} else {
				log.error("No existen datos para el expediente: "+ numExpediente);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el estado de la solicitud de fichas tecnicas, error: ",e, numExpediente.toString());
		}
	}

	private ResultadoPermisoDistintivoItvBean validarTramSolicitudFicha(TramiteTraficoVO tramiteTrafico, BigDecimal numExpediente) {
		ResultadoPermisoDistintivoItvBean resultado =  new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		if(tramiteTrafico == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos para el expediente" + numExpediente );
		} else if(tramiteTrafico.getNumImpresionesFicha() != null && tramiteTrafico.getNumImpresionesFicha() >= 10){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede solicitar la ficha técnica para el expediente" + numExpediente + " porque ha excedido el limite de 10 impresiones.");
		} else if(tramiteTrafico.getEstadoSolFicha() != null && (!EstadoPermisoDistintivoItv.Iniciado.getValorEnum().equals(tramiteTrafico.getEstadoSolFicha())
				&& !EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum().equals(tramiteTrafico.getEstadoSolFicha()))){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede solicitar la ficha técnica para el expediente" + numExpediente + ", el estado de la solicitud debe de ser Iniciado.");
		} else if(tramiteTrafico.getVehiculo() == null || (tramiteTrafico.getVehiculo().getMatricula() == null || tramiteTrafico.getVehiculo().getMatricula().isEmpty())){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede solicitar la ficha técnica para el expediente" + numExpediente + " porque el vehículo no tiene matricula guardada.");
		} else if(!EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum().equals(tramiteTrafico.getEstado().toString()) &&
				!EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum().equals(tramiteTrafico.getEstado().toString()) &&
				!EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum().equals(tramiteTrafico.getEstado().toString())){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede solicitar la ficha técnica para el expediente" + numExpediente + " porque el trámite debe de estar en estado finalizado telematicamente, telematicamente impreso o telematicamente revisado.");
		} else if (!TipoTramiteTrafico.Duplicado.getValorEnum().equals(tramiteTrafico.getTipoTramite()) && (tramiteTrafico.getVehiculo().getNive() == null || tramiteTrafico.getVehiculo().getNive().isEmpty())){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede solicitar la ficha técnica para el expediente" + numExpediente + " porque el NIVE debe ir relleno.");	
		} else {
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
			}
			if(!esJefatura){
				if(colegiadosImprTodos.contains(tramiteTrafico.getContrato().getColegiado().getNumColegiado())){
					resultado = servicioTramiteTraficoMatriculacion.comprobarSuperTelematicoMate(tramiteTrafico.getIntervinienteTraficosAsList(), numExpediente, tramiteTrafico.getContrato().getIdContrato());
				}
			}
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean impresionFichas(String numExpedientes, BigDecimal idUsuario, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			String[] numsExp = numExpedientes.split("-");
			List<TramiteTraficoVO> listaTramites = servicioTramiteTrafico.getListaExpedientesPorListNumExp(utiles.convertirStringArrayToBigDecimal(numsExp), Boolean.TRUE);
			resultado = validarListaTramites(listaTramites);
			if(!resultado.getError()){
				Date fecha = new Date();
				resultado = servicioDocPrmDstvFicha.generarDoc(idUsuario,fecha,TipoDocumentoImprimirEnum.FICHA_TECNICA, null,listaTramites.get(0).getContrato().getIdContrato(), listaTramites.get(0).getContrato().getJefaturaTrafico().getJefaturaProvincial(),Boolean.TRUE);
				if(!resultado.getError()){
					Long idDoc = resultado.getIdDocPermDstvEitv();
					String docId = resultado.getDocId();
					for(TramiteTraficoVO tramiteTraficoVO : listaTramites){
						String estadoAnt = tramiteTraficoVO.getEstadoImpFicha();
						tramiteTraficoVO.setDocFichaTecnica(idDoc);
						tramiteTraficoVO.setEstadoImpFicha(EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum());
						servicioTramiteTrafico.actualizarTramite(tramiteTraficoVO);
						servicioEvolucionFicha.guardarEvolucion(tramiteTraficoVO.getNumExpediente(), idUsuario, 
								TipoDocumentoImprimirEnum.FICHA_TECNICA, OperacionPrmDstvFicha.SOL_IMPRESION, fecha, 
								estadoAnt, EstadoPermisoDistintivoItv.Pendiente_Impresion.getValorEnum(),docId,ipConexion);
					}
					resultado = generarPeticionImpresionFichas(idDoc,idUsuario, listaTramites.get(0).getContrato().getIdContrato());
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

	private ResultadoPermisoDistintivoItvBean generarPeticionImpresionFichas(Long idDoc, BigDecimal idUsuario, Long idContrato) {
		ResultadoPermisoDistintivoItvBean resultado = new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			ResultBean resultBean = servicioCola.crearSolicitud(ProcesosEnum.IMP_FICHA.getNombreEnum(), null, 
					gestorPropiedades.valorPropertie(NOMBRE_HOST), TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum(), 
					idDoc.toString(), idUsuario, null, new BigDecimal(idContrato));
			if(resultBean.getError()){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultBean.getMensaje());
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora encolar la peticion de impresion de las fichas técnicas, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de solicitar la impresion de las fichas técnicas.");
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
			for(TramiteTraficoVO tramiteTrafico : listaTramites){
				 if(!EstadoPermisoDistintivoItv.Doc_Recibido.getValorEnum().equals(tramiteTrafico.getEstadoSolFicha())){
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede solicitar la impresion de la ficha técnica para el expediente" + tramiteTrafico.getNumExpediente() + ", el estado de la solicitud debe de ser Documentacion Recibida.");
					break;
				} else if(idContrato != tramiteTrafico.getContrato().getIdContrato()){
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Para poder imprimir en bloque las ficha técnicas, todos deben de ser del mismo contrato.");
					break;
				}
			}
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultadoPermisoDistintivoItvBean revertirFichas(BigDecimal numExpediente, BigDecimal idUsuario, Date fecha, String ipConexion) {
		ResultadoPermisoDistintivoItvBean resultado =  new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		try {
			TramiteTraficoVO tramiteTrafico = servicioTramiteTrafico.getTramite(numExpediente, Boolean.TRUE);
			resultado = validarTramRevertirFicha(tramiteTrafico, numExpediente);
			if(!resultado.getError()){
				String estadoAnt = tramiteTrafico.getEstadoSolFicha();
				String docId = tramiteTrafico.getDocPermisoVO().getDocIdPerm();
				tramiteTrafico.setEstadoSolFicha(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
				tramiteTrafico.setEstadoImpFicha(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
				tramiteTrafico.setDocFichaTecnica(null);
				tramiteTrafico.setDocFichaTecnicaVO(null);
				servicioTramiteTrafico.actualizarTramite(tramiteTrafico);
				resultado = servicioEvolucionFicha.guardarEvolucion(numExpediente,idUsuario,TipoDocumentoImprimirEnum.FICHA_TECNICA,
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

	private ResultadoPermisoDistintivoItvBean validarTramRevertirFicha(TramiteTraficoVO tramiteTrafico, BigDecimal numExpediente) {
		ResultadoPermisoDistintivoItvBean resultado =  new ResultadoPermisoDistintivoItvBean(Boolean.FALSE);
		if(tramiteTrafico == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos para el expediente" + numExpediente );
		} else if(!EstadoPermisoDistintivoItv.Impresion_OK.getValorEnum().equals(tramiteTrafico.getEstadoImpFicha())){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede revertir la ficha técnica para el expediente" + numExpediente + ", porque no se encuentra impresa todavia.");
		} else if(tramiteTrafico.getDocFichaTecnica() == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede revertir la ficha técnica para el expediente" + numExpediente + ", porque no se encuentran datos de su documento.");
	
		}
		return resultado;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<TramiteTraficoVO> getListaTramitesPorDocId(Long idDocFichaTecnica) {
		try {
			return servicioTramiteTrafico.getListaTramitesFichasTecnicasPorDocId(idDocFichaTecnica);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el listado de tramites de las fichas técnicas del docId: " + idDocFichaTecnica + ", error: ",e);
		}
		return Collections.emptyList();
	}
	
	@Override
	@Transactional
	public ResultadoDistintivoDgtBean desasignarFicha(BigDecimal numExpediente, BigDecimal idUsuario, String ipConexion) {
		ResultadoDistintivoDgtBean resultado = new ResultadoDistintivoDgtBean(Boolean.FALSE);
		try {
			TramiteTraficoVO  tramiteTraf = servicioTramiteTrafico.getTramite(numExpediente, Boolean.TRUE);
			if(tramiteTraf != null) {
				String estadoAnt = tramiteTraf.getEstadoImpFicha();
				String docId = tramiteTraf.getDocFichaTecnicaVO().getDocIdPerm();
				tramiteTraf.setEstadoImpFicha(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
				tramiteTraf.setEstadoSolFicha(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
				tramiteTraf.setDocFichaTecnica(null);
				servicioTramiteTrafico.actualizarTramite(tramiteTraf);
				servicioEvolucionFicha.guardarEvolucion(tramiteTraf.getNumExpediente(), idUsuario, TipoDocumentoImprimirEnum.FICHA_TECNICA, OperacionPrmDstvFicha.DESASIGNAR, new Date(),
						estadoAnt, tramiteTraf.getEstadoImpFicha(), docId,ipConexion);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de desasignar los documentos, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de desasignar los documentos.");
		}
		return resultado;
	}
	
}
