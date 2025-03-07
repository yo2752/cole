package trafico.dao.interfaz;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;

import hibernate.entities.trafico.TramiteTrafico;
import trafico.dao.GenericDao;

public interface TramiteTraficoDaoIntr extends GenericDao<TramiteTrafico> {

	public TramiteTrafico buscarPorExpediente(BigDecimal numExpediente);

	public TramiteTrafico get(Long id, Object... initialized);

	public boolean actualizarEstados(BigDecimal[] numExpediente, EstadoTramiteTrafico estadoNuevo);

	public List<TramiteTrafico> obtenerTramitesNoEstado(Long[] expedientes, EstadoTramiteTrafico estado);

	public void actualizarFechaUltimaModificacion(List<Long> numExpedientes);

	public void actualizarFechaImpresion(List<Long> numExpedientes);
}