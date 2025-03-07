package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoExcelKOBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

public interface ServicioGestionImpr extends Serializable{

	public static final String NOMBRE_HOST = "nombreHostProceso";
	public static final String EITV_CORREO_RESULTADO_PARA = "eitv.correo.resultado.para";
	public static final String DEMANDA = "demanda";
	public static final String NOCTURNO = "proceso";
	public static final String NOMBRE_FICHAS = "_FICHA_TECNICA";
	public static final String NOMBRE_PERMISOS = "_PERMISO";
	public static final String IMPRKOGESTORES = "IMPR_KO_GESTORES";
	
	ResultadoPermisoDistintivoItvBean gestionColasImpr();

	ResultadoPermisoDistintivoItvBean imprDemanda(ColaDto solicitud);

	void actualizarEstadoPeticion(ColaDto solicitud, EstadoPermisoDistintivoItv docRecibido);

	ResultadoPermisoDistintivoItvBean imprNocturno(ColaDto solicitud);

	ResultadoPermisoDistintivoItvBean crearPeticionImpr(ContratoDto contratoDto, String proceso, String datos, String tipoTramite);

	ResultadoPermisoDistintivoItvBean imprErrores(ColaDto solicitud);

	ResultadoPermisoDistintivoItvBean crearSolicitudImprPantalla(Long idContrato, String tipoTramite,
			Date fechaPresentacion, String ipConexion);

	void finalizarErrorColaImprNocturnoNoExisteDocImpr(ColaDto solicitud, String mensaje);

	ResultadoExcelKOBean generarExcelKO(JefaturasJPTEnum jefatura);

	ResultadoPermisoDistintivoItvBean crearSolicitudImprFichasPantalla(Long idContrato, String tipoTramite, Date fechaInicio, String ipConexion);

	Boolean esColegiadoSuperTelematico(BigDecimal idContrato);

}
