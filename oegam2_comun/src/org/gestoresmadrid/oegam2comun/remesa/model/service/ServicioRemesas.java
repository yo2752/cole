package org.gestoresmadrid.oegam2comun.remesa.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.modelos.model.enumerados.Modelo;
import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;
import org.gestoresmadrid.oegam2comun.datosBancariosFavoritos.view.dto.DatosBancariosFavoritosDto;
import org.gestoresmadrid.oegam2comun.intervinienteModelos.view.dto.IntervinienteModelosDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ModeloDto;
import org.gestoresmadrid.oegam2comun.remesa.view.bean.RemesaBean;
import org.gestoresmadrid.oegam2comun.remesa.view.dto.RemesaDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

import escrituras.beans.ResultBean;

public interface ServicioRemesas extends Serializable{
	
	public static String TIPO_DOC_PUBLICO = "PUB";
	
	ResultBean buscarPersona(String nif, BigDecimal idContrato, Long idRemesa, String tipoInterviniente);

	IntervinienteModelosDto getPresentador(BigDecimal idContrato);

	BigDecimal generarNumExpediente(String numColegiado) throws Exception;

	ModeloDto getModelo(Modelo modelo);

	String getMontoBonificacion(String codigo);

	String getGrupoValidacion(String codigo);

	ResultBean guardarRemesa(RemesaDto remesa, String codigosPorct, String porctj, String numColegiado, BigDecimal idUsuario);

	/**
	 * metodo que te devuelve la remesa completa por su numero de expediente dependiendo del booleano 
	 * @param numExpediente numero de expediente de la remesa
	 * @param remesaCompleta boolean para obtener la remesa completa
	 * @return
	 */
	ResultBean getRemesaPorNumExpediente(BigDecimal numExpediente, boolean remesaCompleta);
	
	ResultBean eliminarIntervinientesRemesa(Long idRemesa, String codigos);

	List<RemesaBean> convertirListaEnBeanPantalla(List<RemesaVO> lista);

	ResultBean guardarBienesPantalla(RemesaDto remesa, String codigo, BigDecimal idUsuario, String numColegiado);

	ResultBean eliminarBien(Long idRemesa, String codigo);

	ResultBean generarModelo(BigDecimal numExpediente, BigDecimal idUsuario);

	ResultBean validarRemesa(BigDecimal numExpediente, BigDecimal idUsuario);

	ResultBean gestionarConceptoRemesa(Long attachment);

	ResultBean actualizarRemesa(RemesaVO remesaVO);

	ResultBean cambiarEstado(BigDecimal numExpediente, BigDecimal estadoNuevo,	BigDecimal idUsuario, Boolean acciones);

	ResultBean comprobarEstadosModelosGenerarPorBloque(String idModelos);

	ResultBean presentarModelos(String codigos, DatosBancariosFavoritosDto datosBancariosDto, BigDecimal idUsuario);

	ResultBean validarContratosPresentacionEnBloque(String codigo, BigDecimal idContrato);

	ResultBean presentarRemesas(RemesaVO remesa, DatosBancariosFavoritosDto datosBancarios, BigDecimal idUsuario);

	List<RemesaDto> getListaRemesasPorExpedientesYContrato(String codSeleccionados, Long idContrato);

	RemesaVO getRemesaVoPorExpediente(BigDecimal numExpediente, boolean completa);

	ResultBean validarRemesaImpresion(BigDecimal numExpediente);

	ResultBean esEstadoAnulado(BigDecimal numExpediente);
	
	public IntervinienteModelosDto getPresentadorContrato(ContratoDto contrato);

}