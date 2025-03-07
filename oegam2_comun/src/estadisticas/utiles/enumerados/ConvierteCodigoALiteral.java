package estadisticas.utiles.enumerados;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.core.enumerados.TipoCreacion;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioMarcaDgt;

import trafico.utiles.enumerados.Combustible;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import trafico.utiles.enumerados.TipoTransferencia;

public class ConvierteCodigoALiteral {

	private ServicioMunicipio getServicioMunicipio() {
		return ContextoSpring.getInstance().getBean(ServicioMunicipio.class);
	}

	private ServicioMarcaDgt getServicioMarcaDgt() {
		return ContextoSpring.getInstance().getBean(ServicioMarcaDgt.class);
	}

	public String getLiteral(String codigo, BigDecimal tipoAgrupacionNum) {
		Integer tipoAgrupacion = tipoAgrupacionNum.intValueExact();
		String returnObject = codigo;

		switch (tipoAgrupacion) {
			case 4:
				return returnObject = getFranjaHoraria(codigo);
			case 5:
				return returnObject = getCombustibleLiteral(codigo);
			case 6:
				return returnObject = getTipoTransferenciaLiteral(codigo);
			case 10:
				return returnObject = getTipoTramiteTraficoLiteral(codigo);
			case 11:
				return returnObject = getEstadoLiteral(codigo);
			default:
				return returnObject;
		}
	}
	
	public String getLiteralAgrupacion(String codigo, BigDecimal tipoAgrupacionNum) {
		Integer tipoAgrupacion = tipoAgrupacionNum.intValueExact();
		String returnObject = codigo;

		switch (tipoAgrupacion) {
			case 4:
				return returnObject = getFranjaHoraria(codigo);
			case 5:
				return returnObject = getCombustibleLiteral(codigo);
			case 6:
				return returnObject = getTipoTransferenciaLiteral(codigo);
			case 7:
				return returnObject = getNombreMunicipio(codigo);
			case 10:
				return returnObject = getTipoTramiteTraficoLiteral(codigo);
			case 11:
				return returnObject = getEstadoLiteral(codigo);
			case 12:
				return returnObject = getNombreMarca(codigo);
			case 14:
				return returnObject = getTipoCreacionLiteral(codigo);
			default:
				return returnObject;
		}
	}

//	Metodo que devuelve el literal de un TipoTramiteTrafico al pasarle el codigo. Lo obtiene del enum TipoTramiteTrafico
	public String getTipoTramiteTraficoLiteral(String codigo) {

		return TipoTramiteTrafico.convertirTexto(codigo);
	}

//	Metodo que devuelve el literal de un EstadoTramiteTrafico al pasarle el codigo. Lo obtiene del enum EstadoTramiteTrafico
	public String getEstadoLiteral(String codigo) {

		return EstadoTramiteTrafico.convertirTexto(codigo);
	}

//	Metodo que devuelve el literal de un Combustible al pasarle el codigo. Lo obtiene del enum Combustible
	public String getCombustibleLiteral(String codigo) {

		return Combustible.convertirAString(codigo);
	}

//	Metodo que devuelve la Franja Horaria del tramite para cada hora
	public String getFranjaHoraria(String codigo) {

		if ("00".equals(codigo)) {
			return "00:00 - 01:00";
		} else if ("01".equals(codigo)) {
			return "01:00 - 02:00";
		} else if ("02".equals(codigo)) {
			return "02:00 - 03:00";
		} else if ("03".equals(codigo)) {
			return "03:00 - 04:00";
		} else if ("04".equals(codigo)) {
			return "04:00 - 05:00";
		} else if ("05".equals(codigo)) {
			return "05:00 - 06:00";
		} else if ("06".equals(codigo)) {
			return "06:00 - 07:00";
		} else if ("07".equals(codigo)) {
			return "07:00 - 08:00";
		} else if ("08".equals(codigo)) {
			return "08:00 - 09:00";
		} else if ("09".equals(codigo)) {
			return "09:00 - 10:00";
		} else if ("10".equals(codigo)) {
			return "10:00 - 11:00";
		} else if ("11".equals(codigo)) {
			return "11:00 - 12:00";
		} else if ("12".equals(codigo)) {
			return "12:00 - 13:00";
		} else if ("13".equals(codigo)) {
			return "13:00 - 14:00";
		} else if ("14".equals(codigo)) {
			return "14:00 - 15:00";
		} else if ("15".equals(codigo)) {
			return "15:00 - 16:00";
		} else if ("16".equals(codigo)) {
			return "16:00 - 17:00";
		} else if ("17".equals(codigo)) {
			return "17:00 - 18:00";
		} else if ("18".equals(codigo)) {
			return "18:00 - 19:00";
		} else if ("19".equals(codigo)) {
			return "19:00 - 20:00";
		} else if ("20".equals(codigo)) {
			return "20:00 - 21:00";
		} else if ("21".equals(codigo)) {
			return "21:00 - 22:00";
		} else if ("22".equals(codigo)) {
			return "22:00 - 23:00";
		} else if ("23".equals(codigo)) {
			return "23:00 - 00:00";
		} else {
			return null;
		}
	}

//	Metodo que devuelve el literal de un TipoTransferencia al pasarle el codigo. Lo obtiene del enum TipoTransferencia
	public String getTipoTransferenciaLiteral(String codigo) {
		return TipoTransferencia.convertirTexto(codigo);
	}

//	Metodo que devuelve el literal de un Tipo creación al pasarle el codigo. Lo obtiene del enum TipoCreacion
	public String getTipoCreacionLiteral(String codigo) {
		return TipoCreacion.convertirTexto(codigo);
	}

//	Metodo que obtiene el nombre del municipio a partir del id municipio e id provincia
	public String getNombreMunicipio(String codigo) {

		if (StringUtils.isNotBlank(codigo)) {
			String idProvincia = codigo.substring(0, 2);
			String idMunicipio = codigo.substring(2);

			MunicipioVO municipio = getServicioMunicipio().getMunicipio(idMunicipio, idProvincia);

			if (municipio != null) {
				return municipio.getNombre();
			}
		}

		return "";
	}

//	Método que obtiene el nombre de la marca 
	public String getNombreMarca(String codigo) {
		if (StringUtils.isNotBlank(codigo)) {
			MarcaDgtVO marca = getServicioMarcaDgt().getMarcaDgtCodigo(Long.parseLong(codigo));
			if (marca != null) {
				return marca.getMarca();
			}
		}
		return "";
	}

}