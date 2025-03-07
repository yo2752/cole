package org.gestoresmadrid.oegam2comun.contrato.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.ContratoUsuarioVO;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.oegamComun.accesos.view.dto.AplicacionDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;

import escrituras.beans.ResultBean;
import escrituras.beans.contratos.ContratoBean;
import utilidades.estructuras.FechaFraccionada;
import utilidades.web.Contrato;

public interface ServicioContrato extends Serializable {

	public static String ID_CONTRATO = "idContrato";
	public static String NUEVO_CONTRATO = "nuevoContrato";

	ContratoVO getContrato(BigDecimal idContrato);

	ContratoDto getContratoDto(BigDecimal idContrato);

	ContratoDto detalleContrato(BigDecimal idContrato);

	ResultBean guardarContrato(ContratoDto contrato, BigDecimal idUsuario);

	ResultBean guardarCorpme(String numColegiado, String nif, String apellido1RazonSocial, String usuarioCorpme, String passwordCorpme, String nCorpme, BigDecimal idUsuario);

	List<DatoMaestroBean> getComboContratosHabilitados();

	List<DatoMaestroBean> getComboContratosHabilitadosColegio(BigDecimal idContrato);

	List<DatoMaestroBean> getComboContratosHabilitadosColegiado(String numColegiado);

	ResultBean comprobarMismaViaContratos(ContratoBean contratoBean);

	ResultBean comprobarMismaVia(ContratoVO contratoVO);

	ContratoDto getContratoPorColegiado(String numColegiado);

	List<ContratoDto> getContratosPorColegiado(String numColegiado);

	List<ContratoDto> getContratosPorAplicacion(String codigoAplicacion);

	ResultBean habilitarContrato(BigDecimal idContrato, BigDecimal idUsuario, String motivo, String solicitante);

	ResultBean deshabilitarContrato(BigDecimal idContrato, BigDecimal idUsuario, String motivo, String solicitante);

	ResultBean eliminarContrato(BigDecimal bigDecimal, BigDecimal idUsuario, String motivo, String solicitante);

	List<Contrato> getContratosPorUsuario(BigDecimal idUsuario);

	List<UsuarioDto> getUsuariosPorContrato(Long idContrato);

	ResultBean eliminarUsuarioContrato(String[] idUsuario, Long idContrato);

	void guardarEvolucionUsuario(BigDecimal idUsuario, Timestamp fecha, Long idContrato, TipoActualizacion tipoActualizacion, BigDecimal estadoNuevo, BigDecimal estadoAnt, Long contratoAnt,
			String nif);

	ResultBean asociarUsuarioContrato(String[] idUsuario, Long idContrato);

	ContratoUsuarioVO buscaUsuarioContrato(String idUsuario, Long idContrato);

	ResultBean cargarUsuarioGestoria(String[] idUsuarios, Long idContrato);

	ResultBean actualizarContrato(BigDecimal idContrato, BigDecimal idUsuario, String alias, FechaFraccionada fecha);

	List<ContratoDto> getListaContratosPorId(List<Long> listaIdsContratos);

	Boolean tieneFirmadoLopd(Long idContrato);

	ResultBean firmarLopd(BigDecimal idContrato);

	List<AplicacionDto> getAplicacionesPorContrato(BigDecimal idContrato);

	List<ContratoDto> getListGrupo(String idGrupo);

	Boolean isContratoDeGrupo(Long idContrato, String idGrupo);

}