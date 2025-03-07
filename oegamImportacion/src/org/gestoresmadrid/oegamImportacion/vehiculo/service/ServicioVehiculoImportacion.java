package org.gestoresmadrid.oegamImportacion.vehiculo.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaFabricanteVO;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoVehiculoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;

public interface ServicioVehiculoImportacion extends Serializable {

	public String CONVERSION_VEHICULO_BAJAS = "vehiculoBaja";
	public String CONVERSION_VEHICULO_DUPLICADO = "vehiculoDuplicado";
	public String CONVERSION_VEHICULO_CTIT = "vehiculoCtit";
	public String CONVERSION_VEHICULO_MATRICULACION = "vehiculoMatriculacion";
	public String CONVERSION_VEHICULO_PREVER = "vehiculoPrever";
	public String CONVERSION_VEHICULO_SOLICITUD_MATR = "vehiculoSolicitudMatricula";
	public String CONVERSION_VEHICULO_SOLICITUD_BAST = "vehiculoSolicitudBastidor";

	ResultadoBean guardarVehiculo(VehiculoVO vehiculo, BigDecimal numExpediente, ContratoVO contrato, Long idUsuario, String tipoTramite, boolean prever);

	ResultadoBean guardarVehiculoMatriculacion(VehiculoVO vehiculo, BigDecimal numExpediente, ContratoVO contrato, Long idUsuario, Date fechaAlta);

	VehiculoVO getVehiculoVO(Long idVehiculo, String numColegiado, String matricula, String bastidor, String nive, EstadoVehiculo estadoVehiculo);

	String obtenerNombreMarca(String codigoMarca, boolean versionMatw);

	boolean isBastidorDuplicado(String bastidor, String colegiado);

	MarcaFabricanteVO obtenerMarcaFabricante(String fabricante, String codigoMarca);

	TipoVehiculoVO obtenerTipoVehiculo(String tipoVehiculo);
}
