package trafico.ivtm.servicio;
//TODO MPC. Cambio IVTM. Esta clase es nueva.
import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmMatriculacionDto;

import trafico.beans.TramiteTraficoMatriculacionBean;
import escrituras.beans.ResultBean;

public interface ServicioIVTMMatriculacionIntf {

	public ResultBean puedeGenerarAutoliquidacion(BigDecimal numExpediente);

	public IvtmMatriculacionDto guardarIVTM(IvtmMatriculacionDto ivtmMatriculacionDto);

	public ResultBean validarTramite(BigDecimal numExpediente);
	
	public ResultBean validarTramitePago(BigDecimal numExpediente);
	
	public String crearUrl(boolean desdeConsulta, IvtmMatriculacionDto ivtmMatriculacionDto, TramiteTraficoMatriculacionBean traficoTramiteMatriculacionBean);
	
	public BigDecimal generarIdPeticion(String numColegiado);

	public boolean solicitarIVTM(BigDecimal numExpediente);

	public IvtmMatriculacionDto recuperarPorNumExp(BigDecimal numExpediente);

	public String getIbanTitular(String nifTitular, String numColegiado);
}
