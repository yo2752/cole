package org.gestoresmadrid.oegam2comun.tasas.model.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.oegam2comun.tasas.view.beans.CertificadoTasasBean;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResultadoCertificadoTasasBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTraficoDto;

public interface ServicioConsultaTasa extends Serializable{

	ResultadoCertificadoTasasBean generarCertificadoTasas(String listaChecksConsultaTasa, String listaChecksConsultaTasaPegatinas, BigDecimal idContrato);

	File getFicheroCertificadosTasa(String nombreFichero);

	void generarCertificadoTasaCambSer(TramiteTraficoDto tramiteTraficoDto, TramiteTrafTranDto tramiteTrafTranDto, List<CertificadoTasasBean> listaCertificados, ResultadoCertificadoTasasBean resultado);

	void generarCertificadoTasaConsultaTramite(TramiteTraficoDto tramiteTraficoDto, List<CertificadoTasasBean> listaCertificados, ResultadoCertificadoTasasBean resultado);

	void generarCertificadoSolInfoDesdeConsultaTramite(TramiteTraficoDto tramiteTraficoDto, List<CertificadoTasasBean> listaCertificados, ResultadoCertificadoTasasBean resultado);

	void generarPdfCertificadosTasas(List<CertificadoTasasBean> listaCertificados, ResultadoCertificadoTasasBean resultado, BigDecimal idContrato);

}
