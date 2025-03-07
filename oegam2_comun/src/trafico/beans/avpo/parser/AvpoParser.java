package trafico.beans.avpo.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;

import trafico.beans.avpo.ImpresionAnuntisAvpo;
import trafico.beans.avpobastigest.AvpoBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class AvpoParser {
	private static final String REG_BASTIDOR = "TIPO  :.*BASTIDOR:%";
	private static final String REG_MATRICULA = "MATRICULA:% F\\.MATR:.*";
	private static final String REG_PRIMERA_MATRICULACION = "F.PRIMERA MATR:  %";
	private static final String REG_MATRICULACION = ".* F\\.MATR:%JEF\\.MATR:.*";
	private static final String REG_MATRICULACION_2 = "MATRICULA:.* F\\.MATR:%";
	private static final String REG_MARCA = "MARCA :%MODELO  :.*";
	private static final String REG_MODELO = ".*MODELO  :%";
	private static final String REG_PROCEDENCIA = ".*PROCED:%SERVIC:.*";
	private static final String REG_PROPULSION = ".*PROPULSION :%AUT\\.TRANS:.*";
	private static final String REG_PLAZAS = "PLAZAS:% PROPULSION :.*";
	private static final String REG_CVF = "NO\\.CVF:%CILINDRADA :.*";
	private static final String REG_CILINDRADA = ".*CILINDRADA :%M\\.MAXIMA :.*";
	private static final String REG_M_MAXIMA = ".*M\\.MAXIMA :%TARA:.*";
	private static final String REG_TIPO_VARIANTE_VERSION = "TIPO/VARIANTE/VERSION:%";
	private static final String REG_POTENCIA_NETA_MAXIMA = "POTENCIA NETA MAXIMA:%RELACION POTENCIA/PESO:.*";
	private static final String REG_MASA_CIRCULACION = ".*MASA EN CIRCULACION :%";
	private static final String REG_MMA = "MASA MAXIMA EN CARGA :%MASA EN CIRCULACION :.*";
	private static final String REG_CO2 = ".*EMISIONES CO2 \\(G/KM\\):%";
	private static final String REG_PLAZA_PIE = ".*N\\. PLAZAS PIE:%";
	private static final String REG_PROVINCIA = ".*PROVIN:%COD\\.POSTAL:.*";
	private static final String REG_NUM_TRANSFERENCIAS = "NO.TRANSFERENCIAS :%";

	private static final String LINEA_HISTORIAL_ITV = "- - - - - HISTORIAL DE INSPECCIONES TECNICAS - - - - - - - - - - - - - - - - - -";
	private static final String LINEA_CABECERA_ITV = "    CONCEPTO  FECHA   F.CADUC.  ESTACION PROVIN. MOTIVO     DEFECTOS CALIFIC.   ";
	
	private static final int INDEX_ITV_CONCEPTO = 3;
	private static final int INDEX_ITV_FECHA = 13;
	private static final int INDEX_ITV_F_CADUC = 22;
	private static final int INDEX_ITV_ESTACION = 33;
	private static final int INDEX_ITV_PROVIN = 43;
	private static final int INDEX_ITV_MOTIVO = 47;
	private static final int INDEX_ITV_DEFECTOS = 60;

	private static final String LINEA_LIMITACIONES_DISPOSICION = "- - - - - LIMITACIONES DE DISPOSICION  - - - - - - - - - - - - - - - - - - - - -";

	private static final ILoggerOegam log = LoggerOegam.getLogger(AvpoParser.class);
	
	public static ImpresionAnuntisAvpo valueOf(AvpoBean bean){
		if( bean == null || bean.getListaDatos() == null ){
			return null;
		}

		SimpleDateFormat sdfDGT = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat sdfAnuntis = new SimpleDateFormat("dd/MM/yyyy");

		ImpresionAnuntisAvpo impresionAnuntisAvpo = new ImpresionAnuntisAvpo();
		for( String linea: bean.getListaDatos() ){
			// DATOS TÉCNICOS DEL VEHÍCULO
			if( Pattern.matches(REG_BASTIDOR.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setBastidor(trimRegex(linea, REG_BASTIDOR));
			}
			if( Pattern.matches(REG_MATRICULA.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setMatricula(trimRegex(linea, REG_MATRICULA));
			}
			if( Pattern.matches(REG_PRIMERA_MATRICULACION.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setFechaPrimeraMatriculacion(trimRegex(linea, REG_PRIMERA_MATRICULACION));
			}
			// Se formatea la fecha de primera matriculación
			if( impresionAnuntisAvpo.getFechaPrimeraMatriculacion() != null && !impresionAnuntisAvpo.getFechaPrimeraMatriculacion().isEmpty() ){
				try { impresionAnuntisAvpo.setFechaPrimeraMatriculacion(sdfAnuntis.format(sdfDGT.parse(impresionAnuntisAvpo.getFechaPrimeraMatriculacion()))); } catch (Exception e) {}
			}
			if( Pattern.matches(REG_MATRICULACION.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setFechaMatriculacion(trimRegex(linea, REG_MATRICULACION));
			} else if( Pattern.matches(REG_MATRICULACION_2.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setFechaMatriculacion(trimRegex(linea, REG_MATRICULACION_2));
			}
			// Se formatea la fecha de matriculación
			if( impresionAnuntisAvpo.getFechaMatriculacion() != null && !impresionAnuntisAvpo.getFechaMatriculacion().isEmpty() ){
				try { impresionAnuntisAvpo.setFechaMatriculacion(sdfAnuntis.format(sdfDGT.parse(impresionAnuntisAvpo.getFechaMatriculacion()))); } catch (Exception e) {}
			}
			if( Pattern.matches(REG_MARCA.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setMarca(trimRegex(linea, REG_MARCA));
			}
			if( Pattern.matches(REG_MODELO.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setModelo(trimRegex(linea, REG_MODELO));
			}
			if( Pattern.matches(REG_PROCEDENCIA.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setProcedencia(trimRegex(linea, REG_PROCEDENCIA));
			}
			if( Pattern.matches(REG_PROPULSION.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setPropulsion(trimRegex(linea, REG_PROPULSION));
			}
			if( Pattern.matches(REG_PLAZAS.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setPlazas(trimRegex(linea, REG_PLAZAS));
			}
			if( Pattern.matches(REG_CVF.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setCvf(trimRegex(linea, REG_CVF));
			}
			if( Pattern.matches(REG_CILINDRADA.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setCilindrada(trimRegex(linea, REG_CILINDRADA));
			}
			if( Pattern.matches(REG_M_MAXIMA.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setmMaxima(trimRegex(linea, REG_M_MAXIMA));
			}
			if( Pattern.matches(REG_TIPO_VARIANTE_VERSION.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setTipoVarianteVersion(trimRegex(linea, REG_TIPO_VARIANTE_VERSION));
			}
			if( Pattern.matches(REG_POTENCIA_NETA_MAXIMA.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setPotenciaNetaMaxima(trimRegex(linea, REG_POTENCIA_NETA_MAXIMA));
				if(impresionAnuntisAvpo.getPotenciaNetaMaxima() != null && !impresionAnuntisAvpo.getPotenciaNetaMaxima().trim().isEmpty()){
					impresionAnuntisAvpo.setPotenciaNetaMaxima(impresionAnuntisAvpo.getPotenciaNetaMaxima() + " (KW)"); 
				}
			}
			if( Pattern.matches(REG_MASA_CIRCULACION.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setMasaCirculacion(trimRegex(linea, REG_MASA_CIRCULACION));
			}
			if( Pattern.matches(REG_MMA.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setMma(trimRegex(linea, REG_MMA));
			}
			if( Pattern.matches(REG_CO2.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setCo2(trimRegex(linea, REG_CO2));
			}
			if( Pattern.matches(REG_PLAZA_PIE.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setPlazasPie(trimRegex(linea, REG_PLAZA_PIE));
			}
			// DATOS DE LOCALIZACION VEHÍCULO
			if( Pattern.matches(REG_PROVINCIA.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setProvincia(trimRegex(linea, REG_PROVINCIA));
			}
			if( Pattern.matches(REG_NUM_TRANSFERENCIAS.replaceFirst("%", ".*"), linea) ){
				impresionAnuntisAvpo.setNumTransferencias(trimRegex(linea, REG_NUM_TRANSFERENCIAS));
			}

		}
		// HISTORIAL DE INSPECCIONES TÉCNICAS
		try {
			getDatosItv(impresionAnuntisAvpo, bean.getListaDatos());
		} catch (ParseException e) {
			log.error("Se ha producido un error al obtener el historial de inspecciones tecnicas.", e);
		}
		// INCIDENCIAS DEL VEHÍCULO
		impresionAnuntisAvpo.setIncidencias(getIncidencias(bean.getListaDatos()));

		return impresionAnuntisAvpo;
	}

	private static void getDatosItv(ImpresionAnuntisAvpo impresionAnuntisAvpo, List<String> listaDatos) throws ParseException {
		SimpleDateFormat sdfDGT = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat sdfAnuntis = new SimpleDateFormat("dd/MM/yyyy");
		for (int i = 0; i < listaDatos.size() - 2; i++) {
			if( LINEA_HISTORIAL_ITV.equals(listaDatos.get(i)) && LINEA_CABECERA_ITV.equals(listaDatos.get(i+1)) ){
				String linea = listaDatos.get(i+2);
				if( linea != null && !linea.isEmpty() ){
					// Parseo de datos
					String concepto = linea.length() > INDEX_ITV_FECHA ? linea.substring(INDEX_ITV_CONCEPTO, INDEX_ITV_FECHA).trim() : null;
					String fecha = linea.length() > INDEX_ITV_F_CADUC ? linea.substring(INDEX_ITV_FECHA, INDEX_ITV_F_CADUC).trim() : null;
					String fechaCaduc = linea.length() > INDEX_ITV_ESTACION ? linea.substring(INDEX_ITV_F_CADUC, INDEX_ITV_ESTACION).trim() : null;
					String estacion = linea.length() > INDEX_ITV_PROVIN ? linea.substring(INDEX_ITV_ESTACION, INDEX_ITV_PROVIN).trim() : null;
					String provincia = linea.length() > INDEX_ITV_MOTIVO ? linea.substring(INDEX_ITV_PROVIN, INDEX_ITV_MOTIVO).trim() : null;
					String motivo = linea.length() > INDEX_ITV_DEFECTOS ? linea.substring(INDEX_ITV_MOTIVO, INDEX_ITV_DEFECTOS).trim() : null;
					String defectos = linea.length() > INDEX_ITV_DEFECTOS ? linea.substring(INDEX_ITV_DEFECTOS).trim() : null;
					
					// Se formatean las fechas
					if( fecha != null && !fecha.isEmpty() ){
						try { fecha = sdfAnuntis.format(sdfDGT.parse(fecha)); } catch (Exception e) {}
					}
					if( fechaCaduc != null && !fechaCaduc.isEmpty() ){
						try { fechaCaduc = sdfAnuntis.format(sdfDGT.parse(fechaCaduc)); } catch (Exception e) {}
					}

					// Se actualiza el bean
					impresionAnuntisAvpo.setConceptoItv(concepto);
					impresionAnuntisAvpo.setFechaItv(fecha);
					impresionAnuntisAvpo.setFechaCaducidadItv(fechaCaduc);
					impresionAnuntisAvpo.setEstacionItv(estacion);
					impresionAnuntisAvpo.setProvinciaItv(provincia);
					impresionAnuntisAvpo.setMotivoItv(motivo);
					impresionAnuntisAvpo.setDefectosItv(defectos);
					break;
				}
			}
			
		}
	}

	private static String getIncidencias(List<String> listaDatos) {
		StringBuffer sb = null;
		for(String linea: listaDatos){
			if( sb!=null ){
				if ( linea == null || linea.trim().isEmpty() ){
					break;
				} else {
					sb.append(linea).append("\n");
				}
			} else if (LINEA_LIMITACIONES_DISPOSICION.equals(linea) ) {
				sb = new StringBuffer(linea).append("\n");
			}
		}
		return sb != null ? sb.toString() : null;
	}

	private static String trimRegex(String linea, String regex){
		String[] split = regex.split("%");
		linea = linea.replaceFirst(".*" + split[0], "").trim();
		if( split.length == 2 && split[1] != null && !split[1].trim().isEmpty() ){
			linea = linea.replaceFirst(split[1].endsWith(".*")?split[1]:(split[1] + ".*"), "").trim();
		}
		return linea;
	}

}
