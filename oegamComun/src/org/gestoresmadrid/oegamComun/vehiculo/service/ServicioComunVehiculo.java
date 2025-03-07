package org.gestoresmadrid.oegamComun.vehiculo.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegamComun.vehiculo.view.bean.ResultadoVehiculoBean;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;

public interface ServicioComunVehiculo extends Serializable {

	public static String CONVERSION_VEHICULO_PREVER = "vehiculoPrever";
	public static String CONVERSION_VEHICULO_MATRICULACION = "vehiculoMatriculacion";
	public static String CONVERSION_VEHICULO_BAJAS = "vehiculoBaja";
	public static String CONVERSION_VEHICULO_DUPLICADO = "vehiculoDuplicado";
	
	public static String TIPO_TRAMITE_MANTENIMIENTO = "MTO";
	public static String TIPO_TRAMITE_MATE_EITV = "MITV";
	public static String MATRICULAR = "MATRICULAR";
	
	public static final String TIPO_ACTUALIZACION_CRE = "CREACIÓN";
	public static final String TIPO_ACTUALIZACION_MOD = "MODIFICACIÓN";
	public static final String TIPO_ACTUALIZACION_COP = "COPIA";
	
	String obtenerTipoVehiculoDescripcion(String tipoVehiculo);

	String obtenerNombreMarca(String codigoMarca, boolean versionMatw);

	String obtenerServicioDescripcion(String idServicio);

	String obtenerSinCodig(Long idVehiculo, BigDecimal numExpediente);

	String obtenerDescripcionMotivoItv(String idMotivoItv);

	ResultadoVehiculoBean guardarVehiculoConPrever(VehiculoVO vehiculoVO, BigDecimal numExpediente, String tipoTramite, 
			Long idUsuario, Date fechaPresentacion, String conversionVehiculo,VehiculoDto vehiculoPrever);

	ResultadoVehiculoBean guardarVehiculo(VehiculoVO vehiculoPantalla, BigDecimal numExpediente, String tipoTramite,
			Long idUsuario, Date fechaPresentacion, String conversionVehiculo, Boolean esPrever,
			Boolean guardarIntegrador, EvolucionPersonaVO evolucionIntegrador);
	
	ResultadoVehiculoBean getVehiculo(Long idVehiculo, String numColegiado, String matricula, String bastidor, String nive, EstadoVehiculo estadoVehiculo);

}