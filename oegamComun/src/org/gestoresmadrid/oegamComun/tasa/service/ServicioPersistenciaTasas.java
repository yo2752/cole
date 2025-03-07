package org.gestoresmadrid.oegamComun.tasa.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.tasas.model.vo.EvolucionTasaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaBajaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaCtitVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaDuplicadoVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaInteveVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaMatwVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaPermInternVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.oegamComun.tasa.view.bean.ResultadoTasaBean;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;

public interface ServicioPersistenciaTasas extends Serializable {
	
	TasaVO getTasa(String codigoTasa, BigDecimal numExpediente, String tipoTasa);

	void actualizar(TasaVO tasa);

	TasaVO guardarActualizarConEvo(TasaVO tasa, EvolucionTasaVO evolucionTasa);

	ResultadoTasaBean desasignarYAsignarTasasTramite(String codigoTasaAsignar, String tipoTasaAsignar, TasaVO tasaDesasignar, BigDecimal numExpediente, Long idUsuario);

	List<TasaVO> obtenerTasasContrato(Long idContrato, String tipoTasa, int maxResult);

	ResultadoTasaBean asignarTasasTramite(String codigoTasaAsignar, String tipoTasaAsignar, BigDecimal numExpediente, Long idUsuario);

	TasaVO getTasaLibre(Long idContrato, String tipoTasa);

	void borrarTasa(String codigoTasa);

	ResultadoBean cambiarFormatoATasaElectronica(TasaVO tasa, Long idUsuario);

	void guardarTasaAlmacenMatw(TasaMatwVO tasaMatwVO);

	void guardarTasaAlmacenBaja(TasaBajaVO tasaBajaVO);

	void guardarTasaAlmacenCtit(TasaCtitVO tasaCtitVO);

	void guardarTasaAlmacenDuplicado(TasaDuplicadoVO tasaDuplicadoVO);

	void guardarTasaAlmacenInteve(TasaInteveVO tasaInteveVO);

	void guardarTasaAlmacenPermIntern(TasaPermInternVO tasaPermInterVO);

	List<TasaInteveVO> obtenerTasasInteveContrato(Long idContrato, String tipoTasa, int maxResult);

	List<TasaVO> getTasasLibres(Long idContrato, String tipoTasa);

	List<TasaVO> getTasasLibresMatriculacion(Long idContrato);

	ResultadoBean desasignarTasaBBDD(String codigoTasa, BigDecimal numExpediente, String tipoTasa, Long idContrato, Long idUsuario);

	//void bloquearComboTasa(String codigoTasa, Long idUsuario);
}
