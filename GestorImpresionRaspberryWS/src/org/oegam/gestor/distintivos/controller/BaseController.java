package org.oegam.gestor.distintivos.controller;

import org.oegam.gestor.distintivos.controller.exception.CustomGenericException;
import org.oegam.gestor.distintivos.controller.exception.CustomHibernateException;
import org.oegam.gestor.distintivos.controller.exception.CustomIOException;
import org.oegam.gestor.distintivos.controller.exception.CustomOegamExcepcion;
import org.oegam.gestor.distintivos.controller.exception.NoDataFoundException;
import org.oegam.gestor.distintivos.model.Error;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class BaseController {

	@ExceptionHandler(CustomGenericException.class)
	public @ResponseBody Error handleCustomException(CustomGenericException ex) {
		Error error = new Error();
		error.setName(ex.getErrName());
		error.setCode(ex.getErrCode());
		error.setMessage(ex.getErrCode());
		return error;
	}

	@ExceptionHandler(NoDataFoundException.class)
	public @ResponseBody Error handleNoDataFoundException(NoDataFoundException ex) {
		Error error = new Error();
		error.setName(ex.getErrName());
		error.setCode(ex.getErrCode());
		error.setMessage(ex.getErrMsg());
		error.setStatus(ex.getStatus().toString());
		return error;
	}

	@ExceptionHandler(CustomHibernateException.class)
	public @ResponseBody Error handleCustomHibernateException(CustomHibernateException ex) {
		Error error = new Error();
		error.setName(ex.getErrName());
		error.setCode(ex.getErrCode());
		error.setMessage(ex.getErrMsg());
		error.setStatus(ex.getStatus().toString());
		return error;
	}

	@ExceptionHandler(CustomIOException.class)
	public @ResponseBody Error handleCustomHibernateException(CustomIOException ex) {
		Error error = new Error();
		error.setName(ex.getErrName());
		error.setCode(ex.getErrCode());
		error.setMessage(ex.getErrMsg());
		error.setStatus(ex.getStatus().toString());
		return error;
	}

	@ExceptionHandler(CustomOegamExcepcion.class)
	public @ResponseBody Error handleCustomHibernateException(CustomOegamExcepcion ex) {
		Error error = new Error();
		error.setName(ex.getErrName());
		error.setCode(ex.getErrCode());
		error.setMessage(ex.getErrMsg());
		error.setStatus(ex.getStatus().toString());
		return error;
	}

}