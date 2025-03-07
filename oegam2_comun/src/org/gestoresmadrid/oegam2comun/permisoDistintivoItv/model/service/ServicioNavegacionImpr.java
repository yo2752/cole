package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;

public interface ServicioNavegacionImpr extends Serializable{

	public static final String EITV_URL_LOGIN = "eitv.web.url";
	public static final String EITV_LOGIN_FORM_USER = "eitv.login.form.usuario";
	public static final String EITV_LOGIN_FORM_PASS = "eitv.login.form.pass";
	public static final String ETIV_LOGIN_FORM_SUBMIT = "eitv.login.form.submit";
	
	//CREDENCIALES
	public static final String EITV_LOGIN_USER = "eitv.login.usuario";
	public static final String EITV_LOGIN_PASS = "eitv.login.pass";
	public static final String EITV_LOGIN_USER_MADRID = "eitv.login.usuario.madrid";
	public static final String EITV_LOGIN_PASS_MADRID = "eitv.login.pass.madrid";
	public static final String EITV_LOGIN_USER_ALCORCON = "eitv.login.usuario.alcorcon";
	public static final String EITV_LOGIN_PASS_ALCORCON = "eitv.login.pass.alcorcon";
	public static final String EITV_LOGIN_USER_ALCALA = "eitv.login.usuario.alcala";
	public static final String EITV_LOGIN_PASS_ALCALA = "eitv.login.pass.alcala";
	public static final String EITV_LOGIN_USER_AVILA = "eitv.login.usuario.avila";
	public static final String EITV_LOGIN_PASS_AVILA = "eitv.login.pass.avila";
	public static final String EITV_LOGIN_USER_SEGOVIA = "eitv.login.usuario.segovia";
	public static final String EITV_LOGIN_PASS_SEGOVIA = "eitv.login.pass.segovia";
	public static final String EITV_LOGIN_USER_CUENCA = "eitv.login.usuario.cuenca";
	public static final String EITV_LOGIN_PASS_CUENCA = "eitv.login.pass.cuenca";
	public static final String EITV_LOGIN_USER_CDREAL = "eitv.login.usuario.cdReal";
	public static final String EITV_LOGIN_PASS_CDREAL = "eitv.login.pass.cdReal";
	public static final String EITV_LOGIN_USER_GUADALAJARA = "eitv.login.usuario.guadalajara";
	public static final String EITV_LOGIN_PASS_GUADALAJARA = "eitv.login.pass.guadalajara";
	public static final String EITV_LOGIN_USER2 = "eitv.login.usuario2";
	public static final String EITV_LOGIN_PASS2 = "eitv.login.pass2";
	public static final String EITV_BOTON_PERMISOS_MATW = "eitv.boton.permiso.circulacion.matw";
		
	public static final String EITV_LOGIN_FORM2_USER = "eitv.login.form2.usuario";
	public static final String EITV_LOGIN_FORM2_PASS = "eitv.login.form2.pass";
	public static final String EITV_LOGIN_FORM2_SUBMIT = "eitv.login.form2.submit";
	
	//COMPROBAR SESION
	public static final String EITV_LOGIN_CHECK_SESION = "eitv.login.form.checkSesion";
	public static final String EITV_TEXT_WEB_IMPRESION = "eitv.texto.web.impresion";
		
	//FORMULARIO BUSQUEDA
	public static final String EITV_URL_BUSCAR_FICHAS = "eitv.url.buscar.fichas";
	public static final String EITV_URL_BUSCAR_INPUT_DOI = "eitv.url.buscar.input.doi";
	public static final String EITV_URL_BUSCAR_INPUT_FECHA_INICIO = "eitv.url.buscar.input.fechaInicio";
	public static final String EITV_URL_BUSCAR_INPUT_FECHA_FIN = "eitv.url.buscar.input.fechaFin";
	public static final String EITV_URL_BUSCAR_INPUT_MATRICULAS = "eitv.url.buscar.input.matriculas";
	public static final String EITV_URL_BUSCAR_FICHAS_BOTON = "eitv.url.buscar.boton.buscar";
	public static final String EITV_URL_BUSCAR_FICHAS_TABLA_RESULTADOS = "eitv.url.buscar.tabla.resultados";
	public static final String EITV_URL_BUSCAR_FICHAS_TABLA_INPUT = "eitv.url.buscar.tabla.input";
	public static final String EITV_URL_BUSCAR_FICHAS_BOTON_IMPRIMIR = "eitv.url.buscar.boton.imprimir";
	public static final String EITV_BOTON_FICHAS_TECNICAS = "eitv.boton.ficha.tecnica";
	public static final String EITV_BOTON_PERMISOS = "eitv.boton.permiso.circulacion";
	public static final String EITV_BOTON_PERMISOS2 = "eitv.boton.permiso.circulacion2";
	public static final String EITV_URL_VOLVER_IMPR_BOTON = "eitv.boton.volver.impresion";
	public static final String EITV_URL_CONF_VOLVER_IMPR_BOTON = "eitv.boton.conf.volver.impresion";
	
	public static final String MIME_TYPE_PDF = "application/PDF";
	
	public static final String FICHAS = "fichas";
	public static final String PERMISOS = "permisos";
	
	public static final String DEMANDA = "demanda";
	public static final String PROCESO = "proceso";
	
	ResultadoPermisoDistintivoItvBean iniciarNavegador(ResultadoPermisoDistintivoItvBean resultado, String tipo);

	ResultadoPermisoDistintivoItvBean doLogin(ResultadoPermisoDistintivoItvBean resultado, String jefatura);

	ResultadoPermisoDistintivoItvBean comprobarSessionActivaYSegundoLogin(ResultadoPermisoDistintivoItvBean resultado, String jefatura);

	ResultadoPermisoDistintivoItvBean obtenerFichaDemanda(ResultadoPermisoDistintivoItvBean resultado, String tipoEjecucion);

	ResultadoPermisoDistintivoItvBean cerrarSession();

	ResultadoPermisoDistintivoItvBean obtenerPermisoDemanda(ResultadoPermisoDistintivoItvBean resultado, String tipoEjecucion);

	ResultadoPermisoDistintivoItvBean buscarDocImpr(String matricula, String dniColegiado, BigDecimal numExpediente, ResultadoPermisoDistintivoItvBean resultado,  String tipoTramite, Date fecha);

	ResultadoPermisoDistintivoItvBean buscarDocImprNocturno(List<String> listaMatriculas, BigDecimal idContrato, String nif, ResultadoPermisoDistintivoItvBean resultado, String tipoTramite, Date fecha, String tipoTramiteSolicitud);

	ResultadoPermisoDistintivoItvBean buscarDocImprNocturnoError(List<String> listaMatriculas, BigDecimal idContrato,
			String nif, ResultadoPermisoDistintivoItvBean resultado, String tipoTramite, String tipoDocumento);
}
