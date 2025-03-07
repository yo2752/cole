package org.gestoresmadrid.oegam2comun.trafico.inteve.model.service;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.trafico.inteve.view.bean.ResultInteveBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafSolInfoDto;

import escrituras.beans.ResultBean;

public interface ServicioInteve extends Serializable {

	ResultInteveBean solicitarInforme(TramiteTrafSolInfoDto tramiteTrafSolInfoDto, BigDecimal idUsuario);

	byte[] descargarZip(byte[] bytesInforme) throws IOException;

	void actualizarTramite(TramiteTrafSolInfoDto tramiteTrafSolInfoDto, BigDecimal idUsuario);

	void actualizarTramiteConEstado(TramiteTrafSolInfoDto tramiteTrafSolInfoDto, BigDecimal idUsuario, String estado);

	ResultBean importarInteve(File fichero, BigDecimal idContrato, BigDecimal idUsuarion);

	ResultBean solicitudInfoApp(BigDecimal numExpediente, BigDecimal idContrato, BigDecimal idUsuario);

	ResultBean descargarXmlApp(BigDecimal numExpediente);

}