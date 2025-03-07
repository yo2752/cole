package org.gestoresmadrid.oegam2.documentos.base.beans.paginacion;

import java.math.BigDecimal;

import org.gestoresmadrid.core.annotations.FilterRelationship;
import org.gestoresmadrid.core.annotations.FilterRelationships;
import org.gestoresmadrid.core.annotations.FilterSimpleExpression;
import org.gestoresmadrid.core.annotations.enumeration.FilterSimpleExpressionRestriction;
import org.hibernate.criterion.CriteriaSpecification;

import utilidades.estructuras.FechaFraccionada;

/**
 * Bean que funciona como criterio de búsqueda para buscar documentos base.
 * 
 * @author ext_ihgl
 *
 */
public class BusquedaDocumentosBaseBean {

	/* INICIO ATRIBUTOS */

	@FilterSimpleExpression(name = "fechaPresentacion", restriction = FilterSimpleExpressionRestriction.BETWEEN)
	private FechaFraccionada fechaPresentacion;

	@FilterSimpleExpression(name="numColegiado")
	@FilterRelationships (
			value = {
					@FilterRelationship(name = "contrato", joinType = CriteriaSpecification.INNER_JOIN),
					@FilterRelationship(name = "colegiado", joinType = CriteriaSpecification.INNER_JOIN)
					}
						  )
	private String numColegiado;

	@FilterSimpleExpression(name = "numExpediente")
	@FilterRelationships (
			value = {
					@FilterRelationship(name = "tramitesTrafico", joinType = CriteriaSpecification.INNER_JOIN)
					}
						  )
	private BigDecimal numExpedienteB;

	// Campo de pantalla para soportar el BigDecimal de 'numExpedienteB'
	private String numExpediente;

	@FilterSimpleExpression(name="matricula")
	@FilterRelationships (
			value= {
					@FilterRelationship(name = "tramitesTrafico", joinType = CriteriaSpecification.INNER_JOIN),
					@FilterRelationship(name = "vehiculo", joinType = CriteriaSpecification.INNER_JOIN)
					}
							)
	private String matricula;

	@FilterSimpleExpression(name = "carpeta")
	private String tipoDocumento;

	@FilterSimpleExpression(name = "estado")
	private String estado;

	/* FIN ATRIBUTOS */

	/* INICIO GETTERS & SETTERS */

	public FechaFraccionada getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(FechaFraccionada fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

	public BigDecimal getNumExpedienteB() {
		return numExpedienteB;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
		this.numExpedienteB = (numExpediente != null && !"".equals(numExpediente)) ? new BigDecimal(numExpediente) : null;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	/* FIN GETTERS & SETTERS */

}