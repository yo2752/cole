package org.gestoresmadrid.oegam2comun.materiales.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class MaterialStockFilterBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -8684625827180209289L;
		
		
	
		@FilterSimpleExpression(name="tipoMaterial")
		private String tipoMaterial;
		
		@FilterSimpleExpression(name="jefatura")
		private String jefatura;

		public String getTipoMaterial() {
			return tipoMaterial;
		}

		public void setTipoMaterial(String tipoMaterial) {
			this.tipoMaterial = tipoMaterial;
		}

		public String getJefatura() {
			return jefatura;
		}

		public void setJefatura(String jefatura) {
			this.jefatura = jefatura;
		}
		



	}
