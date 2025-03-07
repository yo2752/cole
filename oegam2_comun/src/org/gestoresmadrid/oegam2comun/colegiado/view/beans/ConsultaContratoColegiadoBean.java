package org.gestoresmadrid.oegam2comun.colegiado.view.beans;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterRelationships;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.Criteria;

public class ConsultaContratoColegiadoBean {

	@FilterSimpleExpression(name = "nif")
	@FilterRelationships(value = { @FilterRelationship(name = "colegiado", joinType = Criteria.LEFT_JOIN), @FilterRelationship(name = "usuario", joinType = Criteria.LEFT_JOIN) })
	private String nif;

	@FilterSimpleExpression(name = "apellidosNombre", restriction = FilterSimpleExpressionRestriction.LIKE)
	@FilterRelationships(value = { @FilterRelationship(name = "colegiado", joinType = Criteria.LEFT_JOIN), @FilterRelationship(name = "usuario", joinType = Criteria.LEFT_JOIN) })
	private String apellidosNombre;

	@FilterSimpleExpression(name = "colegiado.numColegiado", restriction = FilterSimpleExpressionRestriction.LIKE)
	private String numColegiado;

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getApellidosNombre() {
		if(apellidosNombre != null && !apellidosNombre.isEmpty()){
			if(apellidosNombre.contains("%")) {
				apellidosNombre = apellidosNombre.replace("%", "");
			}
		}
		return apellidosNombre;
	}

	public void setApellidosNombre(String apellidosNombre) {
		this.apellidosNombre = apellidosNombre;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}
}