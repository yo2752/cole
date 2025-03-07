package org.gestoresmadrid.oegam2comun.consultaDev.model.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.gestoresmadrid.core.consultaDev.model.enumerados.EstadoCif;
import org.gestoresmadrid.core.consultaDev.model.enumerados.EstadoConsultaDev;
import org.gestoresmadrid.core.consultaDev.model.enumerados.EstadoSuscripcion;
import org.gestoresmadrid.core.consultaDev.model.vo.ConsultaDevVO;
import org.gestoresmadrid.core.consultaDev.model.vo.SuscripcionDevVO;
import org.gestoresmadrid.oegam2comun.consultaDev.model.bean.ConsultaDevBean;
import org.gestoresmadrid.oegam2comun.consultaDev.model.bean.ResultadoConsultaDev;
import org.gestoresmadrid.oegam2comun.consultaDev.model.dto.ConsultaDevDto;
import org.gestoresmadrid.oegam2comun.consultaDev.model.service.ServicioConsultaDev;
import org.gestoresmadrid.oegam2comun.consultaDev.model.service.ServicioDev;
import org.gestoresmadrid.oegam2comun.consultaDev.model.service.ServicioSuscripcionDev;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.validaciones.NIFValidator;

 @Service
public class ServicioConsultaDevImpl implements ServicioConsultaDev{

	private static final long serialVersionUID = 2597684887243212424L;

	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ServicioConsultaDevImpl.class);
	
	
	@Autowired
	Conversor conversor;
	
	@Autowired
	ServicioDev servicioDev;
	
	@Autowired
	ServicioSuscripcionDev servicioSuscripcion;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	private Utiles utiles;

	@Override
	public List<ConsultaDevBean> convertirListaEnBeanPantalla(List<ConsultaDevVO> lista) {
		List<ConsultaDevBean> listaBean = null;
		if(lista != null && !lista.isEmpty()){
			listaBean = new ArrayList<ConsultaDevBean>();
			for(ConsultaDevVO consultaDevVO : lista){
				ConsultaDevBean consultaDevBean = conversor.transform(consultaDevVO, ConsultaDevBean.class);
				consultaDevBean.setEstado(EstadoConsultaDev.convertirEstadoBigDecimal(consultaDevVO.getEstado()));
				consultaDevBean.setEstadoCif(EstadoCif.convertirEstadoBigDecimal(consultaDevVO.getEstadoCif()));
				consultaDevBean.setNumColegiado(consultaDevVO.getContrato().getColegiado().getNumColegiado());
				String desContrato = consultaDevVO.getContrato().getProvincia().getNombre() + " - " + consultaDevVO.getContrato().getVia();
				consultaDevBean.setDescContrato(desContrato);
				listaBean.add(consultaDevBean);
			}
		}else{
			listaBean = Collections.emptyList();
		}
		return listaBean;
	}
	
	@Override
	public ResultadoConsultaDev cambiarEstado(String codSeleccionados, String estadoNuevo, BigDecimal idUsuario) {
		ResultadoConsultaDev resultadoConsultaDev = new ResultadoConsultaDev();
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			String[] idsConsultasDev = codSeleccionados.split("_");
			for(String idConsultaDev : idsConsultasDev){
				ResultBean resultCambioEstado = servicioDev.cambiarEstado(Long.parseLong(idConsultaDev), idUsuario, new BigDecimal(estadoNuevo) , Boolean.TRUE);
				if(resultCambioEstado.getError()){
					resultadoConsultaDev.addError();
					resultadoConsultaDev.addListaError(resultCambioEstado.getMensaje());
				}else{
					resultadoConsultaDev.addOk();
					resultadoConsultaDev.addListaOk(resultCambioEstado.getMensaje());
				}
			}
		}else{
			resultadoConsultaDev.setError(Boolean.TRUE);
			resultadoConsultaDev.setMensaje("Debe seleccionar alguna consulta para cambiar su estado.");
		}
		return resultadoConsultaDev;
	}
	
	@Override
	public ResultadoConsultaDev eliminar(String codSeleccionados, BigDecimal idUsuario) {
		ResultadoConsultaDev resultadoConsultaDev = new ResultadoConsultaDev();
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			String[] idsConsultasDev = codSeleccionados.split("_");
			for(String idConsultaDev : idsConsultasDev){
				ResultBean resultEliminar = servicioDev.cambiarEstado(Long.parseLong(idConsultaDev), idUsuario, new BigDecimal(EstadoConsultaDev.Anulada.getValorEnum()) , Boolean.TRUE);
				if(resultEliminar.getError()){
					resultadoConsultaDev.addError();
					resultadoConsultaDev.addListaError(resultEliminar.getMensaje());
				}else{
					String cif = (String) resultEliminar.getAttachment(ServicioDev.cifConsultaDevDto);
					resultadoConsultaDev.addOk();
					resultadoConsultaDev.addListaOk("La consulta con CIF: " + cif + " se ha anulado correctamente.");
				}
			}
		}else{
			resultadoConsultaDev.setError(Boolean.TRUE);
			resultadoConsultaDev.setMensaje("Debe seleccionar alguna consulta para cambiar su estado.");
		}
		return resultadoConsultaDev;
	}
	
	@Override
	public ResultadoConsultaDev consultar(String codSeleccionados, BigDecimal idUsuario) {
		ResultadoConsultaDev resultadoConsultaDev = new ResultadoConsultaDev();
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			String[] idsConsultasDev = codSeleccionados.split("_");
			for(String idConsultaDev : idsConsultasDev){
				ResultBean resultGetConsultaDev = servicioDev.getConsultaDev(Long.parseLong(idConsultaDev));
				if(resultadoConsultaDev.getError()){
					resultadoConsultaDev.addError();
					resultadoConsultaDev.addListaError(resultadoConsultaDev.getMensaje());
				}else{
					ConsultaDevDto consultaDevDto = (ConsultaDevDto) resultGetConsultaDev.getAttachment(ServicioDev.descConsultaDevDto);
					ResultBean resultConsultar = servicioDev.consultar(consultaDevDto, idUsuario,true);
					if(resultConsultar.getError()){
						resultadoConsultaDev.addError();
						resultadoConsultaDev.addListaError("La consulta Dev con CIF: " + consultaDevDto.getCif() + ", ha fallado por la siguiente razon: " + resultConsultar.getMensaje());
					}else{
						resultadoConsultaDev.addOk();
						resultadoConsultaDev.addListaOk("La consulta Dev con CIF: " + consultaDevDto.getCif() + ", se encuentra " + EstadoConsultaDev.Pdte_Consulta_Dev.getNombreEnum() + ".");
					}
				}
			}
		}else{
			resultadoConsultaDev.setError(Boolean.TRUE);
			resultadoConsultaDev.setMensaje("Debe seleccionar alguna consulta para cambiar su estado.");
		}
		return resultadoConsultaDev;
	}
	
	@Override
	public Boolean getEstadoEstadoAnulado(Long idConsultaDev) {
		ResultBean resultGetConsultaDev = servicioDev.getConsultaDev(idConsultaDev);
		if(!resultGetConsultaDev.getError()){
			ConsultaDevDto consultaDevDto = (ConsultaDevDto) resultGetConsultaDev.getAttachment(ServicioDev.descConsultaDevDto);
			if(EstadoConsultaDev.Anulada.getValorEnum().equals(consultaDevDto.getEstado())){
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	@Override
	public ResultadoConsultaDev altaMasivaConsultasDev(File fichero, String nombreFichero, String idContrato, BigDecimal idUsuario) {
		ResultadoConsultaDev resultado = new ResultadoConsultaDev();
		try {
			resultado = validarDatosAltaMasiva(fichero,idContrato);
			if(!resultado.getError()){
				List<String> lineasAltasMasivas = utiles.obtenerLineasFicheroTexto(fichero);
				if(lineasAltasMasivas != null && !lineasAltasMasivas.isEmpty()){
					boolean esOkNumLineas = true;
					String numLineasPermitidas = gestorPropiedades.valorPropertie("numero.lineas.permitidas.masivasDev");
					if(numLineasPermitidas != null && !numLineasPermitidas.isEmpty()){
						if(Integer.parseInt(numLineasPermitidas) < lineasAltasMasivas.size()){
							esOkNumLineas = false;
						}
					}
					if(esOkNumLineas){
						BigDecimal bIdContrato = new BigDecimal(idContrato);
						int lineas = 0;
						for(String cif : lineasAltasMasivas){
							if(cif != null && !cif.isEmpty()){
								lineas++;
								int tipo = NIFValidator.isValidDniNieCif(cif.toUpperCase());
								if(NIFValidator.JURIDICA_PRIVADA == tipo || NIFValidator.JURIDICA_PUBLICA == tipo){
									ConsultaDevDto consultaDev = new ConsultaDevDto();
									consultaDev.setCif(cif);
									ContratoDto contratoDto = new ContratoDto();
									contratoDto.setIdContrato(bIdContrato);
									consultaDev.setContrato(contratoDto);
									ResultBean resultGuardar = servicioDev.guardar(consultaDev, idUsuario);
									if(resultGuardar.getError()){
										resultado.addError();
										resultado.addListaError("Error al dar de alta el CIF: " + consultaDev.getCif() + ": " + resultGuardar.getMensaje());
									}else{
										resultado.addOk();
										resultado.addListaOk("Se ha dado de alta el CIF:  " + consultaDev.getCif() + " quedándose en estado Iniciado.");
									}
								}else{
									resultado.addError();
									resultado.addListaError("Error al dar de alta el CIF: " + cif + ": El CIF introducido no tiene un formato correcto.");
								}
							}
						}
						if(lineas == 0){
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("El fichero no contiene lineas para poder dar de alta las consultas Masivas Dev.");
							resultado.addListaError("El fichero no contiene lineas para poder dar de alta las consultas Masivas Dev.");
						}
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("El fichero contiene mas lineas de las permitidas, por favor importe un fichero con un máximo de " + numLineasPermitidas + " lineas.");
						resultado.addListaError("El fichero contiene mas lineas de las permitidas, por favor importe un fichero con un máximo de " + numLineasPermitidas + " lineas.");
					}
				}else{
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El fichero no contine lineas para poder dar de alta las consultas Masivas Dev.");
					resultado.addListaError("El fichero no contine lineas para poder dar de alta las consultas Masivas Dev.");
				}
			}
			if(!resultado.getError()){
				servicioDev.guardarFicheroAltaMasiva(fichero,new BigDecimal(idContrato));
			}
		} catch (Exception e) {
			LOG.error("Ha sucedido un error a la hora de dar de alta las consultas Masivas,error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de dar de alta las consultas Masivas Dev.");
			resultado.addListaError("Ha sucedido un error a la hora de dar de alta las consultas Masivas Dev.");
		} catch (Throwable e) {
			LOG.error("Ha sucedido un error a la hora de leer las lineas del fichero,error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de leer las lineas del fichero.");
			resultado.addListaError("Ha sucedido un error a la hora de leer las lineas del fichero.");
		}
		return resultado;
	}

	private ResultadoConsultaDev validarDatosAltaMasiva(File fichero, String idContrato) {
		ResultadoConsultaDev resultadoConsultaDev = new ResultadoConsultaDev();
		if(fichero == null){
			resultadoConsultaDev.setError(Boolean.TRUE);
			resultadoConsultaDev.setMensaje("El fichero de alta no puede ser nulo.");
		}else if(idContrato == null || idContrato.isEmpty()){
			resultadoConsultaDev.setError(Boolean.TRUE);
			resultadoConsultaDev.setMensaje("Debe seleccionar un contrato para poder dar de alta las consultas Dev masivamente.");
		}
		return resultadoConsultaDev;
	}

	@Override
	public ResultBean exportar(String codSeleccionados, BigDecimal idUsuario) {
		ResultBean salida = null;
		List<SuscripcionDevVO> resultado = new ArrayList<SuscripcionDevVO>();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		if(codSeleccionados != null && !codSeleccionados.isEmpty()){
			String[] idsConsultasDev = codSeleccionados.split("_");
			for(String idConsultaDev : idsConsultasDev){
				
				salida = servicioSuscripcion.getSuscripcionesConsultaDev(Long.parseLong(idConsultaDev));			
			
				for(SuscripcionDevVO item : (List<SuscripcionDevVO>) salida.getAttachment("listaSuscripcion")){
					
					resultado.add(item);
				}
				
			}
			
			
	     	// Se crea el libro y las cabeceras
			
	        HSSFWorkbook libro = new HSSFWorkbook();
	        HSSFSheet hoja =  libro.createSheet("ICOGAM");    
	        
	        
	        //Estilo para cabeceras
	        HSSFCellStyle style = libro.createCellStyle();
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	       
	        
	        HSSFFont font = libro.createFont();
	        font.setColor(HSSFColor.BLACK.index);
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        style.setFont(font);
	        
	        //Estilo para informacion
	        HSSFCellStyle style2 = libro.createCellStyle();
	        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			
	        for(int f=0; f<=resultado.size() ;f++){
	            Row fila = hoja.createRow(f);
	            for(int c=0;c<=7;c++){
	            	
	                Cell celda = fila.createCell(c);
	                
	                if(f==0){
               	 
	 	               switch (c) {
	               		case 0:
	               			celda.setCellValue("Datos Personales");
	               			
	               			break;
	               		case 1:
	               			celda.setCellValue("Emisor");
	               			
	               			break;
	               		case 2:
	               			celda.setCellValue("Cod.Procedimiento");
	               			break;
	               		case 3:
	               			celda.setCellValue("Desc.Procedimiento");
	               			break;
	               		case 4:
	               			celda.setCellValue("Fecha Suscripción");
	               			break;
	               		case 5:
	               			celda.setCellValue("Estado");
	               			break;
	               		case 6:
	               			celda.setCellValue("Fecha Consulta");
	               			break;
	               		case 7:
	               			celda.setCellValue("CIF");
	               			break;
						default:
							break;
	 	               }
	 	              celda.setCellStyle(style);
	                }else{
	                	
		               switch (c) {
		               		case 0:
		               			celda.setCellValue(resultado.get(f-1).getDatosPersonales());
		               			celda.setCellStyle(style2);
		               			hoja.autoSizeColumn(c);
		               			break;
		               		case 1:
		               			celda.setCellValue(resultado.get(f-1).getEmisor());
		               			celda.setCellStyle(style2);
		               			hoja.autoSizeColumn(c);
		               			break;
		               		case 2:
		               			celda.setCellValue(resultado.get(f-1).getCodProcedimiento());
		               			celda.setCellStyle(style2);
		               			hoja.autoSizeColumn(c);
		               			break;
		               		case 3:
		               			celda.setCellValue(resultado.get(f-1).getDescProcedimiento());
		               			celda.setCellStyle(style2);
		               			hoja.autoSizeColumn(c);
		               			break;
		               		case 4:
		               			celda.setCellValue(format.format(resultado.get(f-1).getFechaSuscripcion()));
		               			celda.setCellStyle(style2);
		               			hoja.autoSizeColumn(c);
		               			break;
		               		case 5:
		               			celda.setCellValue(EstadoSuscripcion.convertirTexto(resultado.get(f-1).getCodEstado()));
		               			celda.setCellStyle(style2);
		               			hoja.autoSizeColumn(c);
		               			break;
		               				               			
		               		case 6:
		               			celda.setCellValue(format.format(resultado.get(f-1).getConsultaDev().getFechaAlta()));
		               			celda.setCellStyle(style2);
		               			hoja.autoSizeColumn(c);
		               			break;
		               		case 7:
		               			
		               			celda.setCellValue(resultado.get(f-1).getConsultaDev().getCif());
		               			celda.setCellStyle(style2);
		               			hoja.autoSizeColumn(c);
		               			break;

		               			
							default:
								break;
		               }
	                }
	              
	            }
	            
	        }
	        
	       
			// Se salva el libro.
	        try {
	        	File file = new File(gestorPropiedades.valorPropertie(ServicioDev.RUTA_FICH_TEMP) + "excel" + System.currentTimeMillis() + ".xls");
	            FileOutputStream elFichero = new FileOutputStream(file);
	            libro.write(elFichero);
	            elFichero.close();
	            libro.close();
	            salida.addAttachment("excel", file);
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		return salida;
	}
}
