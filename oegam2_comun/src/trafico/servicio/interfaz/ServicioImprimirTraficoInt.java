package trafico.servicio.interfaz;

import java.math.BigDecimal;

import escrituras.beans.ResultBean;

public interface ServicioImprimirTraficoInt {

	ResultBean imprimirNRE06(String[] numExpedientes);

	boolean estaEnEstadoValidoParaImprimirEsteTipo(String[] numExpedientes, String impreso);

	boolean estaEnEstadoValidoParaImprimirMATE(String[] numExpedientes, String impreso);

	ResultBean cambiarEstadoTramiteImprimir(String tipoTramite, String[] numExpedientes, String tipoImpreso, BigDecimal idUsuario);

	ResultBean imprimirSolicitud05(String[] numExpedientes);

}
