package administracion.beans.paginacion;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;

public class ConsultaPropertiesBean implements Serializable {

	private static final long serialVersionUID = -6303240616384742383L;

	@FilterSimpleExpression(name = "idContext")
	private String entorno;

	@FilterSimpleExpression(name = "nombre", restriction = FilterSimpleExpressionRestriction.ILIKE_PORCENTAJE)
	private String nombre;

	@FilterSimpleExpression(name = "valor", restriction = FilterSimpleExpressionRestriction.ILIKE_PORCENTAJE)
	private String valor;

	public String getEntorno() {
		return entorno;
	}

	public void setEntorno(String entorno) {
		this.entorno = entorno;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
