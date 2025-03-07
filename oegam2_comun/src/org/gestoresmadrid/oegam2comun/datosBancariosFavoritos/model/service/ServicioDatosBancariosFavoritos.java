package org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.datosBancariosFavoritos.model.vo.DatosBancariosFavoritosVO;
import org.gestoresmadrid.core.evolucionDatosBancariosFavoritos.modelo.vo.EvolucionDatosBancariosFavoritosVO;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.bean.DatosBancariosBean;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;

import escrituras.beans.ResultBean;

public interface ServicioDatosBancariosFavoritos extends Serializable{

	public static String DATOS_BANCARIOS = "datosBancariosVO";

	List<DatosBancariosFavoritosDto> getListaDatosBancariosFavoritosPorContrato(Long idContrato);

	ResultBean getDatoBancario(Long idDatoBancario);

	String cifrarNumCuentaPantalla(DatosBancariosFavoritosDto datosBancarios);

	ResultBean guardarDatosBancariosFavoritos(DatosBancariosFavoritosVO datosBancariosVO, BigDecimal idUsuario);

	String descifrarNumCuenta(String datosBancarios);

	void desencriptarDatosBancariosVoToDto(DatosBancariosFavoritosVO datosBancariosFavoritosVO, DatosBancariosFavoritosDto datosBancariosFavoritosDto);

	List<DatosBancariosBean> convertirListaPantalla(List<DatosBancariosFavoritosVO> lista);

	ResultBean guardarDatosPantalla(DatosBancariosFavoritosDto datosBancarios, Boolean esModificado, BigDecimal idUsuario);

	List<DatosBancariosFavoritosDto> getListaDatosBancariosFavoritosModeloPorContrato(Long idContrato);

	Boolean esModificada(DatosBancariosFavoritosVO datosBancariosFavoritosBBDD, DatosBancariosFavoritosVO datosBancariosFavoritosVO, EvolucionDatosBancariosFavoritosVO evolucionDatosBancariosFavoritosVO);

	String cifrarNumCuentaPantallaRegistradores(DatosBancariosFavoritosDto datosBancariosDto);

	void desencriptarDatosBancariosRegistradores(DatosBancariosFavoritosVO datosBancariosFavoritosVO, DatosBancariosFavoritosDto datosBancariosFavoritosDto);

	List<DatosBancariosFavoritosDto> getListaDatosBancariosFavoritosRegistradoresPorContrato(Long idContrato);

	ResultBean getDatoBancarioRegistradores(Long idDatoBancario);

	ResultRegistro validarDatosBancariosCuentaPantallaRegistradores(DatosBancariosFavoritosDto datosBancarios);

	void desencriptarDatosBancariosRegistradoresAsterisco(DatosBancariosFavoritosVO datosBancariosFavoritosVO,
			DatosBancariosFavoritosDto datosBancariosFavoritosDto);

	ResultBean getDatoBancarioRegistradoresAsterisco(Long idDatoBancario);

}
