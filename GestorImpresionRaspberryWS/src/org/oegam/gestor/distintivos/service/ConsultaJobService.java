package org.oegam.gestor.distintivos.service;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.oegam.gestor.distintivos.controller.exception.CustomGenericException;
import org.oegam.gestor.distintivos.model.Emisor;
import org.oegam.gestor.distintivos.model.Result;

import utilidades.web.OegamExcepcion;

public interface ConsultaJobService {

	public static final String SUBJECT_PERMISO_IMPRESO = "subject.generar.permiso.impreso";
	public static final String SUBJECT_DISTINTIVO_IMPRESO = "subject.generar.distintivo.impreso";
	public static final String SUBJECT_FICHA_PERMISOS = "subject.generar.fichaPermisos";

	Result obtenerAllJobs() throws HibernateException;

	Result obtenerAllJobsByJefatura(String jefatura) throws HibernateException;

	Result obtenerAllJobsByColegio(Boolean colegio) throws HibernateException;

	Result obtenerJob(Long idjob, String output, String jefatura) throws OegamExcepcion, HibernateException, IOException, CustomGenericException;

	Result actualizarJob(Long idjob, String jefatura) throws HibernateException, CustomGenericException, OegamExcepcion;

	Result obtenerJobPorJefatura(Emisor jefatura, String tipoRassb);
}