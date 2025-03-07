package org.oegam.gestor.distintivos.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.gestoresmadrid.core.cola.model.dao.ColaDao;
import org.gestoresmadrid.core.contrato.model.vo.CorreoContratoTramiteVO;
import org.gestoresmadrid.core.docPermDistItv.model.dao.DocPermDistItvDao;
import org.gestoresmadrid.core.docPermDistItv.model.dao.VehNoMatOegamDao;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.OperacionPrmDstvFicha;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.docPermDistItv.model.vo.VehNoMatOegamVO;
import org.gestoresmadrid.core.evolucionDocPrmDstvFicha.model.dao.EvolucionDocPrmDstvFichaDao;
import org.gestoresmadrid.core.evolucionDocPrmDstvFicha.model.vo.EvolucionDocPrmDstvFichaVO;
import org.gestoresmadrid.core.evolucionPrmDstvFicha.model.dao.EvolucionPrmDstvFichaDao;
import org.gestoresmadrid.core.evolucionPrmDstvFicha.model.dao.EvolucionVehNoMatOegamDao;
import org.gestoresmadrid.core.evolucionPrmDstvFicha.model.vo.EvolucionPrmDstvFichaVO;
import org.gestoresmadrid.core.evolucionPrmDstvFicha.model.vo.EvolucionVehNoMatVO;
import org.gestoresmadrid.core.proceso.model.dao.ProcesoDao;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.materiales.model.vo.JobVO;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoMatrDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.hibernate.HibernateException;
import org.oegam.gestor.distintivos.constants.ConstantesGestorFicheros;
import org.oegam.gestor.distintivos.constants.GestorImprDocConst.EXCEPTIONS;
import org.oegam.gestor.distintivos.constants.GestorImprDocConst.EXCEPTION_MESSAGE;
import org.oegam.gestor.distintivos.controller.exception.CustomGenericException;
import org.oegam.gestor.distintivos.controller.exception.NoDataFoundException;
import org.oegam.gestor.distintivos.enumerates.EstadoJob;
import org.oegam.gestor.distintivos.integracion.bean.FileResultBean;
import org.oegam.gestor.distintivos.model.Emisor;
import org.oegam.gestor.distintivos.model.Job;
import org.oegam.gestor.distintivos.model.Result;
import org.oegam.gestor.distintivos.service.ConsultaJobService;
import org.oegam.gestor.distintivos.service.GestionJobService;
import org.oegam.gestor.distintivos.service.GuardarDocumentosService;
import org.oegam.gestor.distintivos.service.ServicioCorreo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ConsultaJobServiceImpl implements ConsultaJobService {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ConsultaJobServiceImpl.class);
	
	private static final String SUFI_JUSTIFICANTE     = "_Gestoria";
	private static final String NOMBRE_FICH_IMPRESION = "DocumentosImpresos";

    @Resource DocPermDistItvDao           docPermDistItvDao;
    @Resource TramiteTraficoDao           tramiteTraficoDao;
    @Resource TramiteTraficoMatrDao       tramiteTraficoMatrDao;
    @Resource EvolucionDocPrmDstvFichaDao evolucionDocPrmDstvFichaDao;
    @Resource EvolucionPrmDstvFichaDao    evolucionPrmDstvFichaDao;

	@Autowired GestionJobService        	gestionJobService;
	@Autowired GuardarDocumentosService 	gestorDocumentos;
	@Autowired ServicioCorreo           	servicioCorreo;
	@Autowired
	GestorPropiedades gestorPropiedades;
	
	@Autowired VehNoMatOegamDao 			vehNoMatOegamDao;
	@Autowired EvolucionVehNoMatOegamDao	evolucionVehNoMatOegamDao;

	@Autowired
	UtilesFecha utilesFecha;
	
	@Autowired
	ColaDao colaDao;
	
	@Autowired
	ProcesoDao procesoDao;
	
	@Override
	@Transactional(readOnly=true)
	public Result obtenerAllJobs() throws HibernateException {
		Result result = new Result(Result.OK);
		
		Long estado = new Long(EstadoJob.Iniciado.getValorEnum());
		List<Job> jobs = gestionJobService.obtenerJobs(estado);
		
		result.getMsg().put("jobs", jobs);
		
		return result;
	}

	@Override
	public Result obtenerAllJobsByJefatura(String jefatura) throws HibernateException {
		Result result = new Result(Result.OK);
		
		List<Job> jobs = gestionJobService.findJobByJefatura(jefatura);
		result.getMsg().put("jobs", jobs);
		
		return result;
	}
	
	@Override
	@Transactional(readOnly=true)
	public Result obtenerJobPorJefatura(Emisor jefatura, String tipoRassb) {
		Result result = new Result(Result.OK);
		if(Emisor.Colegio.getValorEnum().equals(jefatura.getValorEnum())){
			List<Job> jobs = gestionJobService.findJobColegioByTipoRassb(jefatura.getValorEnum(), tipoRassb);
			result.getMsg().put("jobs", jobs);
		} else {
			List<Job> jobs = gestionJobService.findJobByJefatura(jefatura.getValorEnum());
			result.getMsg().put("jobs", jobs);
		}
		return result;
	}

	@Override
	public Result obtenerAllJobsByColegio(Boolean colegio) throws HibernateException {
		Result result = new Result(Result.OK);
		
		List<Job> jobs = gestionJobService.findJobByColegio(colegio);
		result.getMsg().put("jobs", jobs);
		
		return result;
	}

	@Transactional
	public Result obtenerJob(Long idjob, String output, String jefatura)
			throws OegamExcepcion, HibernateException, IOException, CustomGenericException {
		Result jobInfo = new Result(Result.OK) ;

		JobVO jobVO = gestionJobService.obtenerJobVOByPrimaryKey(idjob);
		
		if(jobVO == null) {
			throw new CustomGenericException(EXCEPTIONS.JOBNOTEXISTS, EXCEPTION_MESSAGE.JOBNOTEXISTS, HttpStatus.NO_CONTENT);
		}
		
		try {
			isAuthorized(jefatura, jobVO); 
		} catch (NoDataFoundException ex) {
			throw ex;
		}
		
		if (output != null && "pdf".equals(output)) {
			
			String tipoDoc       = jobVO.getTipoDocumento();
			Long docPermDstvEitv = jobVO.getDocDistintivo();
			String docIdPerm     = jobVO.getDocIdPerm();
			String subTipo       = jobVO.getSubtipo();
			
			if (TipoDocumentoImprimirEnum.JUSTIFICANTE.getValorEnum().equals(tipoDoc)) {
				String fichero =  String.format("%s%s", docIdPerm, SUFI_JUSTIFICANTE);
				
				String subTipoJust = null;
					
				if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(subTipo)){
					subTipoJust = ConstantesGestorFicheros.JUSTIFICANTE_PERMISO;
				} else if (TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(subTipo)){
					subTipoJust = ConstantesGestorFicheros.JUSTIFICANTE_DISTINTIVO;
				} else if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(subTipo)){
					subTipoJust = ConstantesGestorFicheros.JUSTIFICANTE_EITV;
				} else if (TipoDocumentoImprimirEnum.FICHA_PERMISO.getValorEnum().equals(subTipo)){
					subTipoJust = ConstantesGestorFicheros.JUSTIFICANTE_PERMISO_FICHA;
				}
				
				FileResultBean fileResultBean = null;
				if("2631".equals(jobVO.getContrato().getColegiado().getNumColegiado())){
					fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipoAM(
							ConstantesGestorFicheros.MATE, 
							subTipoJust,
				            utilesFecha.getFechaConDate(jobVO.getFechaDocumento()),
				            fichero, 
				            ConstantesGestorFicheros.EXTENSION_PDF);
				} else {
					fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(
														ConstantesGestorFicheros.MATE, 
														subTipoJust,
											            utilesFecha.getFechaConDate(jobVO.getFechaDocumento()),
											            fichero, 
											            ConstantesGestorFicheros.EXTENSION_PDF);
				}
				log.info("Imprimiendo fichero ===> " + fichero);
				jobInfo.setStatus(Result.OK);
				byte[] document = FileUtils.readFileToByteArray(fileResultBean.getFile()); 
				
				jobInfo.getMsg().put("ficheros", document);
				
			} else if (!TipoDocumentoImprimirEnum.JUSTIFICANTE.getValorEnum().equals(tipoDoc)) {
				subTipo = null;
				
				if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(tipoDoc)){
					subTipo = ConstantesGestorFicheros.PERMISOS_DEFINITIVO;
				} else if (TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(tipoDoc)){
					subTipo = ConstantesGestorFicheros.DISTINTIVOS;
				} else if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(tipoDoc)){
					subTipo = ConstantesGestorFicheros.FICHA_TECNICA_DEFINITIVA;
				}

				String fichero = NOMBRE_FICH_IMPRESION + "_" + docPermDstvEitv + "_" + jobVO.getContrato().getColegiado().getNumColegiado();
				FileResultBean fileResultBean  = null;
				if("2631".equals(jobVO.getContrato().getColegiado().getNumColegiado())){
					fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipoAM(
							ConstantesGestorFicheros.MATE, 
							subTipo ,
				            utilesFecha.getFechaConDate(jobVO.getFechaDocumento()),
				            fichero, 
				            ConstantesGestorFicheros.EXTENSION_PDF);
				} else {
					fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(
														ConstantesGestorFicheros.MATE, 
														subTipo ,
											            utilesFecha.getFechaConDate(jobVO.getFechaDocumento()),
											            fichero, 
											            ConstantesGestorFicheros.EXTENSION_PDF);
				}
				
				if (fileResultBean != null && fileResultBean.getFile() != null) {
					byte[] document = FileUtils.readFileToByteArray(fileResultBean.getFile());
					jobInfo.getMsg().put("ficheros", document);
				} else {
					jobInfo.setStatus(Result.KO);
				}
			} else {
				throw new CustomGenericException(EXCEPTIONS.TIPONOTEXISTS, EXCEPTION_MESSAGE.TIPONOTEXISTS, HttpStatus.NO_CONTENT);
			}
		} else {
			Job job = gestionJobService.obtenerJobByPrimaryKey(jobVO.getJobId());
			jobInfo.getMsg().put("job", job);
		}
		
		return jobInfo;
	}

	@Transactional
	public Result actualizarJob(Long idjob, String jefatura) throws HibernateException, CustomGenericException, OegamExcepcion {

		EstadoPermisoDistintivoItv estadoImpresion = EstadoPermisoDistintivoItv.Impresion_OK;
		JobVO jobVO = gestionJobService.obtenerJobVOByPrimaryKey(idjob);
		
		if(jobVO == null) {
			throw new CustomGenericException(EXCEPTIONS.JOBNOTEXISTS, EXCEPTION_MESSAGE.JOBNOTEXISTS, HttpStatus.NO_CONTENT);
		}
		
		try {
			isAuthorized(jefatura, jobVO); 
		} catch (NoDataFoundException ex) {
			throw ex;
		}

		Result jobInfo = new Result(Result.OK);
		if(EstadoJob.Iniciado.getValorEnum().equals(jobVO.getEstado().toString())){
			jobVO.setEstado(new Long(EstadoJob.Descargado.getValorEnum()));
			@SuppressWarnings("unused")
			JobVO newJobVO = gestionJobService.guardarJob(jobVO);
			jobInfo.getMsg().put("status", Long.valueOf(EstadoJob.Descargado.getValorEnum()));
		} else if(EstadoJob.Descargado.getValorEnum().equals(jobVO.getEstado().toString())) {
			BigDecimal usuario = new BigDecimal(jobVO.getUsuario().getIdUsuario());
			DocPermDistItvVO docPermDistItvVO = actualizarDocPermDistItv(jobVO.getDocDistintivo(), false) ;
			
			if (docPermDistItvVO != null) {
				Date today = new Date();
				
				String tipoDocumento = jobVO.getTipoDocumento();
				if (TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(tipoDocumento)) {
					actualizarEstadoListasDistintivos(docPermDistItvVO, estadoImpresion, usuario, today);
					EstadoJob estadoJob = EstadoJob.Impreso;
					jobVO.setEstado(Long.valueOf(estadoJob.getValorEnum()));
					JobVO newJobVO = gestionJobService.guardarJob(jobVO);
					Job job = gestionJobService.obtenerJobByPrimaryKey(newJobVO.getJobId());
					jobInfo.getMsg().put("job", job);
					jobInfo.getMsg().put("status", Long.valueOf(EstadoJob.Impreso.getValorEnum()));
				} else if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(tipoDocumento)) {
					actualizarEstadoListasPermisos(docPermDistItvVO, estadoImpresion, usuario, today);
					EstadoJob estadoJob = EstadoJob.Impreso;
					jobVO.setEstado(Long.valueOf(estadoJob.getValorEnum()));
					JobVO newJobVO = gestionJobService.guardarJob(jobVO);
					Job job = gestionJobService.obtenerJobByPrimaryKey(newJobVO.getJobId());
					jobInfo.getMsg().put("job", job);
					jobInfo.getMsg().put("status", Long.valueOf(EstadoJob.Impreso.getValorEnum()));
				} else if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(tipoDocumento)) {
					actualizarEstadoListasFichas(docPermDistItvVO, estadoImpresion, usuario, today);
					EstadoJob estadoJob = EstadoJob.Impreso;
					jobVO.setEstado(Long.valueOf(estadoJob.getValorEnum()));
					JobVO newJobVO = gestionJobService.guardarJob(jobVO);
					Job job = gestionJobService.obtenerJobByPrimaryKey(newJobVO.getJobId());
					jobInfo.getMsg().put("job", job);
					jobInfo.getMsg().put("status", Long.valueOf(EstadoJob.Impreso.getValorEnum()));
				} else if (TipoDocumentoImprimirEnum.JUSTIFICANTE.getValorEnum().equals(tipoDocumento)) {
					docPermDistItvVO = actualizarDocPermDistItv(jobVO.getDocDistintivo(), true) ;
					guardarEvolucionDocumento(
							jobVO.getSubtipo(), 
							new Date(), 
							OperacionPrmDstvFicha.IMPRESION, 
							docPermDistItvVO.getDocIdPerm(), 
							usuario);
					
					enviarMailImpresionDocumento(docPermDistItvVO);
					List<JobVO> listaJobsToDelete = gestionJobService.findJobByDocDistintivo(docPermDistItvVO.getIdDocPermDistItv());
					
					Job job = gestionJobService.obtenerJobByPrimaryKey(jobVO.getJobId());
					jobInfo.getMsg().put("job", job);
					
					boolean borrar = true;
					for (JobVO item: listaJobsToDelete) {
						if (!TipoDocumentoImprimirEnum.JUSTIFICANTE.getValorEnum().equals(item.getTipoDocumento())) {
							borrar = borrar && Long.valueOf(EstadoJob.Impreso.getValorEnum()).equals(item.getEstado());
						}
					}
	
					if (borrar) {
						gestionJobService.deleteJobs(listaJobsToDelete);
						jobInfo.getMsg().put("status", Long.valueOf(EstadoJob.Impreso.getValorEnum()));
					}
				} else {
					log.error("Tipo de documento no encontrado.");
				}
			}
		}
		return jobInfo;
	}

	private void actualizarEstadoListasFichas(DocPermDistItvVO docPermDistItvVO, EstadoPermisoDistintivoItv estado, BigDecimal idUsuario, Date fecha) throws OegamExcepcion {
		if(docPermDistItvVO.getListaTramitesEitv() != null && !docPermDistItvVO.getListaTramitesEitv().isEmpty()){
			for (TramiteTraficoVO item: docPermDistItvVO.getListaTramitesEitv()) {
				String oldEstadoImpresion = item.getEstadoImpFicha();
				String newEstadoImpresion = estado.getValorEnum();
				item.setEstadoImpFicha(newEstadoImpresion);
				tramiteTraficoDao.guardarOActualizar(item);
				guardarEvolucionTramite (
						item.getNumExpediente(), 
						idUsuario, 
						TipoDocumentoImprimirEnum.FICHA_TECNICA, 
						OperacionPrmDstvFicha.IMPRESION, 
						fecha, 
						oldEstadoImpresion, 
						newEstadoImpresion, 
						docPermDistItvVO.getDocIdPerm());
			}
		}
	}

	private void actualizarEstadoListasPermisos(DocPermDistItvVO docPermDistItvVO, EstadoPermisoDistintivoItv estado, BigDecimal idUsuario, Date fecha) throws OegamExcepcion {
		if(docPermDistItvVO.getListaTramitesPermiso() != null && !docPermDistItvVO.getListaTramitesPermiso().isEmpty()){
			for (TramiteTraficoVO item: docPermDistItvVO.getListaTramitesPermiso()) {
				String oldEstadoImpresion = item.getEstadoImpPerm();
				String newEstadoImpresion = estado.getValorEnum();
				item.setEstadoImpPerm(newEstadoImpresion);
				tramiteTraficoDao.guardarOActualizar(item);
				guardarEvolucionTramite(
						item.getNumExpediente(), 
						idUsuario, 
						TipoDocumentoImprimirEnum.PERMISO_CIRCULACION, 
						OperacionPrmDstvFicha.IMPRESION, 
						fecha, oldEstadoImpresion, 
						estado.getValorEnum(), 
						docPermDistItvVO.getDocIdPerm());
			}
		}
	}

	private void actualizarEstadoListasDistintivos(DocPermDistItvVO docPermDistItvVO, EstadoPermisoDistintivoItv estadoImpresion, BigDecimal idUsuario, Date fecha) {
		if(docPermDistItvVO.getListaTramitesDistintivo() != null && !docPermDistItvVO.getListaTramitesDistintivo().isEmpty()){
			for(TramiteTrafMatrVO tramiteTrafMatrVO : docPermDistItvVO.getListaTramitesDistintivo()){
				String oldEstadoImpresion = tramiteTrafMatrVO.getEstadoImpDstv();
				tramiteTrafMatrVO.setEstadoImpDstv(estadoImpresion.getValorEnum());
				tramiteTraficoMatrDao.guardarOActualizar(tramiteTrafMatrVO);
				guardarEvolucionTramite(
						tramiteTrafMatrVO.getNumExpediente(), 
						idUsuario, 
						TipoDocumentoImprimirEnum.DISTINTIVO, 
						OperacionPrmDstvFicha.IMPRESION, 
						fecha, oldEstadoImpresion, 
						estadoImpresion.getValorEnum(), 
						docPermDistItvVO.getDocIdPerm());
			}
		}
		if(docPermDistItvVO.getListaDuplicadoDistintivos() != null && !docPermDistItvVO.getListaDuplicadoDistintivos().isEmpty()){
			for(VehNoMatOegamVO vehNoMatOegamVO : docPermDistItvVO.getListaDuplicadoDistintivos()){
				String estadoAnt = vehNoMatOegamVO.getEstadoImpresion();
				vehNoMatOegamVO.setEstadoImpresion(estadoImpresion.getValorEnum());
				vehNoMatOegamVO.setFechaImpresion(fecha);
				vehNoMatOegamDao.actualizar(vehNoMatOegamVO);
				guardarEvolcionVehNotMatOegam(vehNoMatOegamVO.getId(),estadoAnt,estadoImpresion.getValorEnum(),idUsuario,OperacionPrmDstvFicha.IMPRESION,docPermDistItvVO.getDocIdPerm(),fecha);
			}
		}
	}

	private void guardarEvolcionVehNotMatOegam(Long id, String estadoAnt, String estadoNuevo, BigDecimal idUsuario, OperacionPrmDstvFicha tipoOperacion, String docId, Date fecha) {
		EvolucionVehNoMatVO evolucionVehNoMatVO = new EvolucionVehNoMatVO();
		evolucionVehNoMatVO.setFechaCambio(fecha);
		evolucionVehNoMatVO.setIdUsuario(idUsuario.longValue());
		evolucionVehNoMatVO.setId(id);
		evolucionVehNoMatVO.setTipoActuacion(tipoOperacion.getValorEnum());
		evolucionVehNoMatVO.setEstadoAnt(estadoAnt);
		evolucionVehNoMatVO.setEstadoNuevo(estadoNuevo);
		evolucionVehNoMatVO.setDocId(docId);
		evolucionVehNoMatOegamDao.guardar(evolucionVehNoMatVO);
	}

	private DocPermDistItvVO actualizarDocPermDistItv(Long docDistintivo, boolean actualizar) {
		DocPermDistItvVO docPermDistItvVO = docPermDistItvDao.getPermDistItvPorId(docDistintivo, true);
		
		if (!actualizar) {
			return docPermDistItvVO;
		}
		
		DocPermDistItvVO newDoc = null;
		if (docPermDistItvVO != null) {
			EstadoPermisoDistintivoItv estadoImpresion = EstadoPermisoDistintivoItv.Impresion_OK;
			BigDecimal newEstado = new BigDecimal(estadoImpresion.getValorEnum());
			docPermDistItvVO.setEstado(newEstado);
			
			docPermDistItvVO.setFechaImpresion(new Date());
			
			Long numDescarga = new Long(1);
			docPermDistItvVO.setNumDescarga(numDescarga);
			
			newDoc = docPermDistItvDao.guardarOActualizar(docPermDistItvVO);
		} else {
			log.error("docPermDistItv no encontrado");
		}

		return newDoc;
	}

	@Transactional
	private void guardarEvolucionDocumento(String documento, Date fecha, OperacionPrmDstvFicha operacion, String docId, BigDecimal idUsuario) {
		try {
			EvolucionDocPrmDstvFichaVO evoDocPrmDstvFichaVO = new EvolucionDocPrmDstvFichaVO();
			evoDocPrmDstvFichaVO.setDocID(docId);
			evoDocPrmDstvFichaVO.setFechaCambio(fecha);
			evoDocPrmDstvFichaVO.setIdUsuario(idUsuario);
			evoDocPrmDstvFichaVO.setOperacion(operacion.getValorEnum());
			evoDocPrmDstvFichaVO.setTipoDocumento(documento);
			evolucionDocPrmDstvFichaDao.guardar(evoDocPrmDstvFichaVO);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la evolucion del docPrmDstvFicha con docId: " + docId +", error: ", e);
		}
		
	}

	@Transactional
	private void guardarEvolucionTramite(BigDecimal numExpediente, BigDecimal idUsuario,
			TipoDocumentoImprimirEnum tipoDocumento, OperacionPrmDstvFicha operacion, 
			Date fecha, String estadoAnt, String estadoNuevo, String docId) {
		
			EvolucionPrmDstvFichaVO evolucionPrmDstvFichaVO = new EvolucionPrmDstvFichaVO();
			if(estadoAnt != null && !estadoAnt.isEmpty()){
				evolucionPrmDstvFichaVO.setEstadoAnterior(new BigDecimal(estadoAnt));
			}
			evolucionPrmDstvFichaVO.setEstadoNuevo(new BigDecimal(estadoNuevo));
			evolucionPrmDstvFichaVO.setFechaCambio(fecha);
			evolucionPrmDstvFichaVO.setIdUsuario(idUsuario);
			evolucionPrmDstvFichaVO.setNumExpediente(numExpediente);
			evolucionPrmDstvFichaVO.setOperacion(operacion.getValorEnum());
			evolucionPrmDstvFichaVO.setTipoDocumento(tipoDocumento.getValorEnum());
			evolucionPrmDstvFichaVO.setDocID(docId);
			evolucionPrmDstvFichaDao.guardar(evolucionPrmDstvFichaVO);
	}

	private boolean isAuthorized(String jefatura, JobVO jobVO) throws NoDataFoundException {
		if ("CO".equals(jefatura)) {
			if (jobVO.getColegio().equals("N")) {
				String msg = String.format(EXCEPTION_MESSAGE.JEFANOTAUTHOR);
				log.error("Error ==> " + EXCEPTIONS.JEFANOTAUTHOR + " - " + msg);
				throw new NoDataFoundException(EXCEPTIONS.JEFANOTAUTHOR, msg, HttpStatus.UNAUTHORIZED);
			}
		} else { 
			if (null != jefatura) {
				if (!jefatura.equals(jobVO.getJefaturaProvincial().getJefaturaProvincial()) || 
					 "S".equals(jobVO.getColegio())) {
					String msg = String.format(EXCEPTION_MESSAGE.JEFANOTAUTHOR);
					log.error("Error ==> " + EXCEPTIONS.JEFANOTAUTHOR + " - " + msg);
					throw new NoDataFoundException(EXCEPTIONS.JEFANOTAUTHOR, msg, HttpStatus.UNAUTHORIZED);
				} 
			} else {
				String msg = String.format(EXCEPTION_MESSAGE.JEFANOTAUTHOR);
				log.error("Error ==> " + EXCEPTIONS.JEFANOTAUTHOR + " - " + msg);
				throw new NoDataFoundException(EXCEPTIONS.JEFANOTAUTHOR, msg, HttpStatus.UNAUTHORIZED);
			}
		}
		return true;
	}


	public void enviarMailImpresionDocumento(DocPermDistItvVO docPermDistItvVO) {
		try{
			String subject = null;
			StringBuffer sb = null; 
			if(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(docPermDistItvVO.getTipo())){
				subject = gestorPropiedades.valorPropertie(SUBJECT_PERMISO_IMPRESO);
				sb = getResumenEnvio(docPermDistItvVO);
			}else if(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(docPermDistItvVO.getTipo())){
				 subject = gestorPropiedades.valorPropertie(SUBJECT_DISTINTIVO_IMPRESO);
				 sb = getResumenEnvioDstv(docPermDistItvVO);
			}else if(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(docPermDistItvVO.getTipo())){
				 subject = gestorPropiedades.valorPropertie(SUBJECT_FICHA_PERMISOS);
				 sb = getResumenEnvio(docPermDistItvVO);
			} else if(TipoDocumentoImprimirEnum.FICHA_PERMISO.getValorEnum().equals(docPermDistItvVO.getTipo())){
				subject = "Impresión Permiso y Fichas solicitado";
				sb = getResumenEnvio(docPermDistItvVO);
			}
			
			String direccionCorreo = docPermDistItvVO.getContrato().getCorreoElectronico();
			String tipoTramite = TipoDocumentoImprimirEnum.convertirValorATipoTramite(docPermDistItvVO.getTipo());
			
			if (docPermDistItvVO.getContrato().getCorreosTramites() != null && !docPermDistItvVO.getContrato().getCorreosTramites().isEmpty()) {
				for (CorreoContratoTramiteVO correoContratoTramite : docPermDistItvVO.getContrato().getCorreosTramites()) {
					if (tipoTramite.equalsIgnoreCase(correoContratoTramite.getTipoTramite().getTipoTramite())){
						direccionCorreo = correoContratoTramite.getCorreoElectronico();
						break;
					}
				}
			}
			
			servicioCorreo.enviarCorreo(sb.toString(),Boolean.FALSE, null, subject, direccionCorreo, null, null, null);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de enviar el correo a la jefatura para la solicitud de impresion de los permisos de circulación, error:",e);
		}
	}
	
	private StringBuffer getResumenEnvioDstv(DocPermDistItvVO docPermDistItvVO) {
		StringBuffer resultadoHtml = new StringBuffer();
		resultadoHtml.append("<br>");
		resultadoHtml.append("Estimado Colegiado, ");
		resultadoHtml.append("<br>");
		resultadoHtml.append("Pueden pasar a retirar los documentos solicitados con número de justificante: ")
		.append(docPermDistItvVO.getDocIdPerm()).append(" en las instalaciones del Colegio.");
		resultadoHtml.append("<br>");
		resultadoHtml.append("Un saludo. ");
		resultadoHtml.append("<br>");
		resultadoHtml.append("<br>");
		return resultadoHtml;
	}

	private StringBuffer getResumenEnvio(DocPermDistItvVO docPermDistItvVO) {
		StringBuffer resultadoHtml = new StringBuffer();
		resultadoHtml.append("<br>");
		resultadoHtml.append("Estimado Colegiado, ");
		resultadoHtml.append("<br>");
		if(gestorPropiedades.valorPropertie("jefatura.impresion.impr").contains(docPermDistItvVO.getJefatura())){
			resultadoHtml.append("Pueden pasar a retirar los documentos solicitados con número de justificante: ")
			.append(docPermDistItvVO.getDocIdPerm()).append(" en un plazo mínimo de 48 horas desde la recepción de este correo en su Jefatura Provincial.");
		} else {
			resultadoHtml.append("Pueden pasar a retirar los documentos solicitados con número de justificante: ")
			.append(docPermDistItvVO.getDocIdPerm()).append(".");
		}
		resultadoHtml.append("<br>");
		resultadoHtml.append("Un saludo. ");
		resultadoHtml.append("<br>");
		resultadoHtml.append("<br>");
		return resultadoHtml;
	}
	
}
