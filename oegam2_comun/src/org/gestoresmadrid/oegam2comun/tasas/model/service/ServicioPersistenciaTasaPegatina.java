package org.gestoresmadrid.oegam2comun.tasas.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.exceptions.TransactionalException;
import org.gestoresmadrid.core.tasas.model.vo.TasaPegatinaVO;
import org.gestoresmadrid.oegam2comun.tasas.model.dto.RespuestaTasas;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;
import org.gestoresmadrid.oegam2comun.tasas.view.dto.TasaPegatinaDto;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;

import escrituras.beans.ResultBean;

public interface ServicioPersistenciaTasaPegatina {

	/**
	 * Guarda una tasa de pegatina
	 * @param tasa
	 * @return RespuestaTasas
	 * @throws TransactionalException
	 */
	RespuestaTasas guardarTasa(Tasa tasa);

	TasaPegatinaVO getTasaVO(String codigoTasa);

	TasaPegatinaDto getTasaDto(String codigoTasa);

	/**
	 * Elimina una tasa de pegatina por su codigo de tasa, si todo va bien devuelve "OK", y si no, mensaje de error
	 * @param codigoTasa
	 * @return
	 */
	String eliminar(String codigoTasa);

	ArrayList<TasaPegatinaDto> obtenerTasasPegatinaContrato(Long idContrato, String tipoTasa);

	ResultBean actualizarTasaPegatinaFechaAplicacion(String codigoTasa, Date fechaFacturacion);

	List<TasaPegatinaVO> getListaTasasPorCodigos(List<String> listaTasas);

	ResultBean guardarTasa(TasaPegatinaVO tasaPegationa);

	ResultadoBean cambiarFormatoATasaPegatina(TasaPegatinaVO tasaPegatina, Long idUsuario);
}