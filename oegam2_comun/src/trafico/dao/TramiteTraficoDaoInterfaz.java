package trafico.dao;

import hibernate.entities.trafico.TramiteTrafico;

import java.math.BigDecimal;
import java.util.List;

public interface TramiteTraficoDaoInterfaz extends GenericDao<TramiteTrafico> {

	/**
	 * 
	 * @param tramiteTrafico
	 * @return
	 */
	public BigDecimal guardar(TramiteTrafico tramiteTrafico);

	/**
	 * 
	 * @param numExpediente
	 * @param estadosCorrectos
	 * @return
	 */
	public List noExisteNumExpedienteEstados(Long[] numExpediente, List<BigDecimal> estadosCorrectos);

	/**
	 * 
	 * @param numExpediente
	 * @param estadosCorrectos
	 * @return
	 */
	public List existeNumExpedienteEstados(Long[] numExpedientes, List<BigDecimal> estadosCorrectos);
}