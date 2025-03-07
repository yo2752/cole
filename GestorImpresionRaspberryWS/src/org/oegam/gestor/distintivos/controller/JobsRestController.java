package org.oegam.gestor.distintivos.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.HibernateException;
import org.oegam.gestor.distintivos.constants.GestorImprDocConst.EXCEPTIONS;
import org.oegam.gestor.distintivos.constants.GestorImprDocConst.EXCEPTION_MESSAGE;
import org.oegam.gestor.distintivos.controller.exception.CustomHibernateException;
import org.oegam.gestor.distintivos.controller.exception.CustomIOException;
import org.oegam.gestor.distintivos.controller.exception.CustomOegamExcepcion;
import org.oegam.gestor.distintivos.controller.exception.NoDataFoundException;
import org.oegam.gestor.distintivos.integracion.bean.ResultadoCertBean;
import org.oegam.gestor.distintivos.model.Job;
import org.oegam.gestor.distintivos.model.Result;
import org.oegam.gestor.distintivos.model.Status;
import org.oegam.gestor.distintivos.service.CertificateService;
import org.oegam.gestor.distintivos.service.ConsultaJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Controller
@RequestMapping("")
public class JobsRestController extends BaseController {

	private static final ILoggerOegam log = LoggerOegam.getLogger(JobsRestController.class);

	@Autowired ConsultaJobService consultaJobService;
	@Autowired CertificateService certificateService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/jobs", method = RequestMethod.GET)
	public @ResponseBody List<Job> obtenerAllJobs(HttpServletRequest request)
					throws CustomHibernateException, NoDataFoundException {

		Result result = new Result(Result.OK);
		List<Job> jobs = null;

		try {
			ResultadoCertBean resultado = certificateService.obtenerDatosCertificado(request);
			if (resultado.getError()) {
				String msg = String.format(EXCEPTION_MESSAGE.JEFANOTEXISTS);
				log.error("Error ==> " + EXCEPTIONS.JEFANOTEXISTS + " - " + msg);
				throw new NoDataFoundException(EXCEPTIONS.JEFANOTEXISTS, msg, HttpStatus.BAD_REQUEST);
			} else {
				log.info("Jefatura ==> " + resultado.getJefatura().getNombreEnum());

				result = consultaJobService.obtenerJobPorJefatura(resultado.getJefatura(), resultado.getTipoRassb());

				if (result.getStatus() == Result.OK) {
					jobs = (List<Job>) result.getMsg().get("jobs");
				} else {
					String msg = String.format(EXCEPTION_MESSAGE.NO_DATA_FOUND);
					log.error("Error ==> " + EXCEPTIONS.NO_DATA_FOUND + " - " + msg);
					throw new NoDataFoundException(EXCEPTIONS.NO_DATA_FOUND, msg, HttpStatus.NO_CONTENT);
				}

				log.info("Recuperados todos los jobs pendientes");
			}
		} catch (HibernateException ex) {
			Throwable cause = ex.getCause();
			String message = "";
			if (cause instanceof SQLException) {
				SQLException sqlCause = (SQLException) cause;
				String codigo = new Integer(sqlCause.getErrorCode()).toString();
				log.error("Error ==> " + codigo + " - " + sqlCause.getMessage());
				log.error("Error ==> " + ex.getStackTrace());
				throw new CustomHibernateException(codigo, sqlCause.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			} else if (cause == null) {
				if (ExceptionUtils.getRootCauseMessage(ex) != null) {
					message = ExceptionUtils.getRootCauseMessage(ex);
				}
				log.error(EXCEPTIONS.HIBERNATE + " - " + message);
				throw new CustomHibernateException(EXCEPTIONS.HIBERNATE, message  , HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return jobs;
	}

	@RequestMapping(value={"/jobs/{idjob}"}, method = RequestMethod.GET)
	public Object obtenerJobPdf(
							@PathVariable(value="idjob") Long idjob,
							@ModelAttribute(value="output") String output,
							HttpServletRequest request)
						throws CustomHibernateException, CustomIOException, CustomOegamExcepcion, NoDataFoundException {

		byte[] document = null;
		Result result;
		Object response = null;
		Job job = null; 

		try {
			ResultadoCertBean resultado = certificateService.obtenerDatosCertificado(request);
			if (resultado.getError()) {
				String msg = String.format(EXCEPTION_MESSAGE.JEFANOTEXISTS);
				log.error("Error ==> " + EXCEPTIONS.JEFANOTEXISTS + " - " + msg);
				throw new NoDataFoundException(EXCEPTIONS.JEFANOTEXISTS, msg, HttpStatus.BAD_REQUEST);
			} else {
				log.info("Jefatura ==> " + resultado.getJefatura().getValorEnum());

				result = consultaJobService.obtenerJob(idjob, output, resultado.getJefatura().getValorEnum());
				if (result.getStatus() == Result.OK) {
					if (output != null && "pdf".equals(output)) {
						document =  (byte[]) result.getMsg().get("ficheros");
						if (document.length == 0) {
							String msg = String.format(EXCEPTION_MESSAGE.NO_DATA_FOUND, idjob);
							log.error("Error ==> " + EXCEPTIONS.NO_DATA_FOUND + " - " + msg);
							throw new NoDataFoundException(EXCEPTIONS.NO_DATA_FOUND, msg, HttpStatus.NO_CONTENT);
						}

						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.parseMediaType("application/pdf"));
						headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
						response = new ResponseEntity<byte[]>(document, headers, HttpStatus.OK);

						log.info("Se han mandado los ficheros correspondientes a imprimir ");
					} else {
						job = (Job) result.getMsg().get("job");
						log.info("Recuperado información del job " + idjob);

						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.parseMediaType("application/json"));
						headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
						response =  new ResponseEntity<Job>(job, headers, HttpStatus.OK);
					}

				} else {
					throw new NoDataFoundException(EXCEPTIONS.NO_DATA_FOUND, String.format(EXCEPTION_MESSAGE.NO_DATA_FOUND, ""), HttpStatus.NO_CONTENT);
				}
			}
		} catch (HibernateException ex) {
			Throwable cause = ex.getCause();
			String message = "";
			if (cause instanceof SQLException) {
				SQLException sqlCause = (SQLException) cause;
				String codigo = new Integer(sqlCause.getErrorCode()).toString();
				log.error("Error ==> " + codigo + " - " + sqlCause.getMessage());
				log.error("Error ==> " + ex.getStackTrace());
				throw new CustomHibernateException(codigo, sqlCause.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			} else if (cause == null) {
				if (ExceptionUtils.getRootCauseMessage(ex) != null) {
					message = ExceptionUtils.getRootCauseMessage(ex);
				}
				log.error(EXCEPTIONS.HIBERNATE + " - " + message);
				throw new CustomHibernateException(EXCEPTIONS.HIBERNATE, message, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (IOException ex) {
			log.error("Error ==> " + EXCEPTIONS.IOEXCEPTION + " - " + ExceptionUtils.getRootCauseMessage(ex));
			log.error("Error ==> " + ex.getStackTrace());
			throw new CustomIOException(EXCEPTIONS.IOEXCEPTION, ExceptionUtils.getRootCauseMessage(ex), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (OegamExcepcion ex) {
			log.error("Error ==> " + EXCEPTIONS.OEGAM + " - " + ExceptionUtils.getRootCauseMessage(ex));
			log.error("Error ==> " + ex.getStackTrace());
			throw new CustomOegamExcepcion(EXCEPTIONS.OEGAM, ex.getMensajeError1(), HttpStatus.INTERNAL_SERVER_ERROR);
		} 

		return response;
	}

	@RequestMapping(value="/jobs/{idjob}", method = RequestMethod.PUT) 
	public @ResponseBody Status actualizarJob(
			@PathVariable Map<String, String> pathVariables,
			HttpServletRequest request) throws CustomHibernateException, NoDataFoundException {

		Long idjob = (pathVariables.containsKey("idjob"))? new Long(pathVariables.get("idjob")) : null;
		if (idjob == null) {
			throw new NoDataFoundException(EXCEPTIONS.BADREQUEST, EXCEPTION_MESSAGE.BADREQUEST, HttpStatus.BAD_REQUEST);
		}
		@SuppressWarnings("unused")
		Job job = null;
		Status status = null;
		try {
			ResultadoCertBean resultado = certificateService.obtenerDatosCertificado(request);
			if (resultado.getError()){
				String msg = String.format(EXCEPTION_MESSAGE.JEFANOTEXISTS);
				log.error("Error ==> " + EXCEPTIONS.JEFANOTEXISTS + " - " + msg);
				throw new NoDataFoundException(EXCEPTIONS.JEFANOTEXISTS, msg, HttpStatus.BAD_REQUEST);
			} else {
				log.info("Jefatura ==> " + resultado.getJefatura().getValorEnum());

				Result result = consultaJobService.actualizarJob(idjob, resultado.getJefatura().getValorEnum());
				if (result.getStatus() == Result.OK) {
					job = (Job) result.getMsg().get("job");
					Long statusResult = (Long) result.getMsg().get("status");
					status = new Status(statusResult);
				} 
			}
		} catch (Throwable ex) {
			log.error("Error ==> ",ex);
		}

		return status;
	}

}