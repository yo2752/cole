package org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.constantes;

public class ConstantesPlacasMatriculacion {
	
	/* Action results */
	public static final String SOLICITUD_REALIZADA = "solicitudRealizada";
	public static final String ALTA_SOLICITUD = "altaSolicitud";
	public static final String INICIO_SOLICITUD = "inicioSolicitud";
	public static final String LISTAR_SOLICITUDES = "buscarSolicitud";
	public static final String REFRESCAR_SOLICITUD = "refrescarSolicitud";
	public static final String DESCARGAR_PDF = "descargarPDF";
	public static final String RECARGAR_LISTADO = "recargarListado";
	public static final String ESTADISTICAS_SOLICITUD = "estadisticasSolicitud";
	
	/* Action otras */
	public static final String PLACAS_MATR_CSS = "css/placasMate.css";
	public static final String PLACAS_MATR_JS_BOTTOM = "js/placasMateBottom.js";
	public static final String PLACAS1_MATR_JS_BOTTOM = "js/placas1MateBottom.js";
	public static final String NO_SE_PUDO_RECUPERAR_NINGUN_EXPEDIENTE_PARA_CREAR_LA_SOLICITUD_DE_PLACAS = "No se pudo recuperar ningún expediente, para crear la solicitud de placas de matriculación.";
	public static final String ALGUNO_DE_LOS_EXPEDIENTES_NO_TIENE_MATRICULA = "Alguno de los expedientes seleccionados no tiene matrícula. Imposible crear la solicitud.";
	public static final String VEHICULO_Y_COLEGIADO_DE_SESION_NO_RELACIONADOS = "Vehículo y colegiado de sesión no relacionados.";
	
	/* Tipos de matrícula */
	public static final String TIPO_MATRICULA_ORDINARIA =	"ordinaria";
	public static final String TIPO_MATRICULA_TRACTOR =		"tractor";
	public static final String TIPO_MATRICULA_CICLOMOTOR =	"ciclomotor";
	
	/* Enteros */
	public static final Integer CERO =		0;
	public static final Integer UNO =		1;
	public static final Integer DOS =		2;
	public static final Integer TRES =		3;
	public static final Integer CUATRO =	4;
	public static final Integer CINCO =		5;
	public static final Integer SEIS =		6;
	public static final Integer SIETE =		7;
	public static final Integer OCHO =		8;
	public static final Integer NUEVE =		9;
	public static final Integer DIEZ =		10;
	
	// Estas constantes son las del envío de correo al colegio cuando se produce un pedido de placas
	public static final String ORDER_MAIL_SUBJECT 			= "Nueva solicitud de placas de matrícula";
	public static final String ORDER_MAIL_COLEGIO_RECIPIENT	= "placas.pedido.correo.recipient";
	public static final String ORDER_MAIL_COLEGIO_BCC		= "placas.pedido.correo.bcc";
	public static final String ORDER_MAIL_JEFATURA_MADRID	= "placas.pedido.correo.jefaturaMadrid";
	public static final String ORDER_MAIL_JEFATURA_ALCORCON	= "placas.pedido.correo.jefaturaAlcorcon";
	public static final String ORDER_MAIL_JEFATURA_ESTANDAR = "placas.pedido.correo.jefaturaEstandar";
	
	// Estas constantes son las del envío de correo al usuario cuando se finaliza una solicitud de placas
	public static final String NOTIFICATION_MAIL_SUBJECT	= "placas.notif.correo.subject";
	public static final String NOTIFICATION_MAIL_RECIPIENT	= "placas.notif.correo.recipient";
	public static final String NOTIFICATION_MAIL_BCC		= "placas.notif.correo.bcc";
	public static final String NOTIFICATION_MAIL_HABILITADO	= "placas.notif.correo.habilitado";
	public static final String NOTIFICACION_FINALIZADO		= "Solicitud de placas finalizada correctamente";

}
