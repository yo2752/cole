package org.gestoresmadrid.oegamInteve.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoSolInteveVO;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.oegamInteve.view.bean.ResultadoTramiteInteveBean;
import org.gestoresmadrid.oegamInteve.view.bean.ResultadoWSInteveBean;
import org.gestoresmadrid.oegamInteve.view.dto.TramiteTraficoInteveDto;

public interface ServicioTramiteInteve extends Serializable {

	ResultadoTramiteInteveBean obtenerTramitePantalla(TramiteTraficoInteveDto tramiteInteve);

	ResultadoTramiteInteveBean guardarTramiteInteve(TramiteTraficoInteveDto tramiteInteve, Long idUsuario);

	ResultadoTramiteInteveBean validarTramite(BigDecimal numExpediente, Long idUsuario);

	ResultadoTramiteInteveBean solicitarTramite(BigDecimal numExpediente, Long idUsuario);

	ResultadoTramiteInteveBean getSolicitante(String nif, Long idContrato);

	ResultadoTramiteInteveBean getRepresentante(String nif, Long idContrato);

	ResultadoTramiteInteveBean getSolicitudInteve(Long idSolInteve);

	ResultadoTramiteInteveBean obtenerListadoTasas(Long idContrato, String codigoTasa);

	ResultadoTramiteInteveBean solicitarInteveCompletoWS(BigDecimal numExpediente, Long idUsuario);

	void finalizarSolicitudInteveCompleto(List<ResultadoWSInteveBean> listaResultadoWS, Long numErrorWS, Long numOkWS, BigDecimal numExpediente, Long idUsuario);

	void finalizarSolicitudNoRecuperable(BigDecimal numExpediente, String mensaje, Long idUsuario);

	List<TramiteTraficoSolInteveVO> getListaTramitesIntevePorExpediente(BigDecimal numExpediente);

	ResultadoTramiteInteveBean recuperarDocumento(String listaIdsTramites);

	ResultadoTramiteInteveBean reiniciarEstados(BigDecimal numExpediente, Long idUsuario);

	ResultadoBean tratarCreditosInteve(BigDecimal numExpediente, Long idContrato, Long idUsuario, Long numOkWS);

	void borrarZip(File fichero);

	ResultadoTramiteInteveBean obtenerInformesTramites(List<TramiteTraficoSolInteveVO> listaTramitesDescargar);

	ResultadoTramiteInteveBean reiniciarEstadosSolicitudes(String codSeleccionados);

	ResultadoTramiteInteveBean eliminarSolicitudes(String codSeleccionados, Long idContrato, Long idUsuario);

	ResultadoTramiteInteveBean cambioEstado(BigDecimal bigDecimal, String estadoNuevo, Long idUsuario);

	ResultadoTramiteInteveBean eliminar(BigDecimal numExpediente, Long idUsuario);
}
