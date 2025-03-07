package org.gestoresmadrid.oegam2comun.materiales.view.bean;

import java.io.Serializable;

import org.gestoresmadrid.core.annotations.FilterSimpleExpression;

public class EvolucionMaterialStockFilterBean implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -8684625827180209289L;
		
		@FilterSimpleExpression(name="idStock")
		private Long idStock;

		public Long getIdStock() {
			return idStock;
		}

		public void setIdStock(Long idStock) {
			this.idStock = idStock;
		}

	}
