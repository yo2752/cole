package org.gestoresmadrid.oegamComun.tasa.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.tasas.model.vo.TasaInteveVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.oegamComun.tasa.view.bean.ResultadoTasaBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;

public interface ServicioComunTasa extends Serializable {

	public static final String DESASIGNAR = "DESASIGNACION";
	public static final String ASIGNAR = "ASIGNACION";
	public static final String CREAR = "CREACION";
	public static final String CAMBIOFORMATO = "CAMBIO DE FORMATO";
	public static final String BLOQUEO = "BLOQUEO";
	public static final String DESBLOQUEO = "DESBLOQUEO";
	public static final String CAMBIOALMACEN = "CAMBIO DE ALMACEN";

	TasaVO getTasa(String codigoTasa, BigDecimal numExpediente, String tipoTasa);

	List<TasaVO> obtenerTasasContrato(Long idContrato, String tipoTasa);

	ResultadoBean asignarTasaExpediente(String codigoTasaNueva, BigDecimal numExpediente, Long idContrato, String tipoTasa, Long idUsuario);

	ResultadoBean desasignarTasaExpediente(String codigoTasa, BigDecimal numExpediente, Long idContrato, String tipoTasa, Long idUsuario);

	ResultadoTasaBean gestionarTasaTramite(TasaVO tasa, BigDecimal numExpediente, Long idUsuario, String tipoTasa);

	ResultadoBean asignarTasaLibre(BigDecimal numExpediente, String tipoTramite, String tipoTasa, Long idContrato, Long idUsuario);
	
	List<TasaInteveVO> obtenerTasasInteveContrato(Long idContrato, String tipoTasa);

	ResultadoBean desasignarTasaBBDD(String codigoTasa, BigDecimal numExpediente, Long idContrato, String tipoTasa,Long idUsuario);

	void bloquearComboTasa(String codigoTasa, Long idUsuario);

}
