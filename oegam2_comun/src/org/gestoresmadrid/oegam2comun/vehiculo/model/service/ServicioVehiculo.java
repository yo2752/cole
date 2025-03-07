package org.gestoresmadrid.oegam2comun.vehiculo.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoTramiteTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.EvolucionVehiculoDto;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.MarcaDgtDto;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.MarcaFabricanteDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;

import escrituras.beans.ResultBean;
import utilidades.web.OegamExcepcion;

public interface ServicioVehiculo extends Serializable {

	public static String VEHICULO = "vehiculo";
	public static String ID_VEHICULO = "idVehiculo";

	public String TIPO_TRAMITE_MANTENIMIENTO = "MTO";
	public String TIPO_TRAMITE_MATE_EITV = "MITV";
	public String MATRICULAR = "MATRICULAR";

	public String CONVERSION_VEHICULO_BAJAS = "vehiculoBaja";
	public String CONVERSION_VEHICULO_DUPLICADO = "vehiculoDuplicado";
	public String CONVERSION_VEHICULO_CAMBIO_SERVICIO = "vehiculoCambioServicio";
	public String CONVERSION_VEHICULO_MATRICULACION = "vehiculoMatriculacion";
	public String CONVERSION_VEHICULO_MANTENIMIENTO = "vehiculoMantenimiento";
	public String CONVERSION_VEHICULO_PREVER = "vehiculoPrever";
	public String CONVERSION_VEHICULO_JUSTIFICANTE = "vehiculoJustificante";
	public String CONVERSION_VEHICULO_MATE_EITV = "vehiculoMateEITV";
	public String CONVERSION_VEHICULO_SOL_INFO = "vehiculoSolInfo";

	VehiculoDto getVehiculoDto(Long idVehiculo, String numColegiado, String matricula, String bastidor, String nive, EstadoVehiculo estadoVehiculo);

	VehiculoVO getVehiculoVO(Long idVehiculo, String numColegiado, String matricula, String bastidor, String nive, EstadoVehiculo estadoVehiculo);

	ResultBean guardarVehiculoConPrever(VehiculoDto vehiculoDto, BigDecimal numExpediente, String tipoTramite, UsuarioDto usuario, Date fechaPresentacion, String tipoConversion,
			VehiculoDto vehiculoPreverDto, boolean admin);

	ResultBean guardarVehiculo(VehiculoVO vehiculoPantalla, BigDecimal numExpediente, String tipoTramite, UsuarioDto usuario, Date fechaPresentacion, String conversion, boolean prever, boolean admin);

	MarcaDgtDto getMarcaDgt(String codigoMarca, String marca, Boolean versionMatw);

	String obtenerNombreMarca(String codigoMarca, boolean versionMatw);

	String obtenerFabricante(String fabricante);

	MarcaFabricanteDto obtenerMarcaFabricante(String fabricante, String codigoMarca);

	String obtenerTipoVehiculoDescripcion(String tipoVehiculo);

	void desactivarVehiculo(VehiculoVO vehiculo, BigDecimal numExpediente, String tipoTramite, Long idVehiculoNuevo, UsuarioDto usuario, EvolucionVehiculoDto evolucionDto) throws OegamExcepcion;

	Long copiaVehiculo(VehiculoVO vehiculo, Long idVehiculoAntiguo, BigDecimal numExpediente, String tipoTramite, UsuarioDto usuario) throws OegamExcepcion;

	ResultBean consultaDatosItv(VehiculoDto vehiculoDto);

	/**
	 * return: 0 - No modificado; 1 - Modificado; 2 - Modificado y creación de copia
	 */
	int esModificada(VehiculoVO vehiculoVO, VehiculoVO vehiculoBBDD, EvolucionVehiculoDto evolucionDto);

	boolean isBastidorDuplicado(String bastidor, String colegiado);

	void eliminar(Long idVehiculo);

	void modificarDatos(VehiculoVO vehiculoVO, String tipoTramite, Date fechaPresentacion, EvolucionVehiculoDto evolucionDto);

	VehiculoVO crearVehiculoNuevoConEvolucion(VehiculoVO vehiculoVO, EvolucionVehiculoDto evolucionDto, BigDecimal numExpediente, String tipoTramite, UsuarioDto usuario, String tipoActualizacion)
			throws OegamExcepcion;

	ResultBean liberarNive(Long idVehiculo, String numColegiado, BigDecimal numExpediente, String tipoTramite, UsuarioDto usuarioDto);

	ResultBean guardarVehiculoMatriculaManual(VehiculoDto vehiculoPantalla, BigDecimal numExpediente, String tipoTramite, UsuarioDto usuario, Date fechaPresentacion, String conversion,
			VehiculoDto vehiculoPreverDto, boolean admin);

	String obtenerMatriculaPorBastidor(String bastidor);

	VehiculoDto primeraMatricula(Date fechaActual);

	VehiculoDto ultimaMatricula(Date fechaActual);

	ResultBean actualizarMatricula(BigDecimal numExpediente, String matricula, Date fechaMatriculacion, UsuarioDto usuario);

	//DVV
	void actualizarEsSiniestro(BigDecimal numExpediente, BigDecimal esSiniestro);
	
	void actualizarVelocidadMaxima(BigDecimal numExpediente, BigDecimal esSiniestro);

	//DVV
//	void actualizarTieneCargaFinanciera(BigDecimal numExpediente, BigDecimal tieneCargaFinanciera);

	VehiculoTramiteTraficoVO getVehiculoTramite(BigDecimal numExpediente, Long idVehiculo);

	Boolean esCambioServicioPermitido(String idServicio, String idServicioAnterior);

	Boolean esTipoVehiculoMotoOCiclomotor(String tipoVehiculo);

	Boolean esRemolqueOSemiRemlqOExpecial(String tipoVehiculo);
}