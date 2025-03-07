package org.gestoresmadrid.core.model.transformer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class ResultTransformerDetalleVehiculoExpediente implements BeanResultTransformPaginatedList {

	private static final long serialVersionUID = -3736639191934056534L;
	private Long numExpediente;
	private String tipoTramite;
	private BigDecimal estado;
	private Date fechaPresentacion;
	private String nif;
	private String datosTitular;
	
	public Long getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(Long numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getTipoTramite() {
		return tipoTramite;
	}

	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Date getFechaPresentacion() {
		return fechaPresentacion;
	}

	public void setFechaPresentacion(Date fechaPresentacion) {
		this.fechaPresentacion = fechaPresentacion;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getDatosTitular() {
		return datosTitular;
	}

	public void setDatosTitular(String datosTitular) {
		this.datosTitular = datosTitular;
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		ResultTransformerDetalleVehiculoExpediente rt = new ResultTransformerDetalleVehiculoExpediente();
		if (tuple[0]!=null){
			rt.setNumExpediente((Long)tuple[0]);
		}
		if (tuple[1]!=null){
			rt.setTipoTramite((String)tuple[1]);
		}
		if (tuple[2]!=null){
			rt.setEstado((BigDecimal)tuple[2]);
		}
		if (tuple[3]!=null){
			rt.setFechaPresentacion((Date)tuple[3]);
		}
		if (tuple[5]!=null){
			String tipo = ((String) tuple[5]);
			if ("018".equals(tipo) || "005".equals(tipo) || "004".equals(tipo)){
				if (tuple[4]!=null){
					rt.setNif((String)tuple[4]);
					if(null!=tuple[6] && !((String)tuple[6]).isEmpty()){
						rt.setDatosTitular((String)tuple[6]);
						if (null!=tuple[7] && !((String)tuple[7]).isEmpty()){
							rt.setDatosTitular(rt.getDatosTitular()+" "+((String)tuple[7]));
						}
					}
				}
			}
		}
		return rt;
	}

	@Override
	public List transformList(List collection) {
		return collection;
	}

	@Override
	public Vector<String> crearProyecciones() {
		Vector<String> proyecciones = new Vector<String>();
		proyecciones.add("numExpediente");
		proyecciones.add("tipoTramite");
		proyecciones.add("estado");
		proyecciones.add("fechaPresentacion");
		proyecciones.add("interviniente.id.nif");
		proyecciones.add("interviniente.tipoIntervinienteBean.tipoInterviniente");
		proyecciones.add("persona.apellido1RazonSocial");
		proyecciones.add("persona.apellido2");
		return proyecciones;
	}

}
