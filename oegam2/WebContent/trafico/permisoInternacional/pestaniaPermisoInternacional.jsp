<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="contenido">			
	<div id="idTasaPI">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Permiso Internacional</td>
		</tr>
	</table>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">PERMISO INTERNACIONAL</span>				
			</td>
		</tr>
	</table>
									
	<table cellSpacing="2" class="tablaformbasica" cellPadding="0" align="left">
		<tr>  			
   			<td align="left" nowrap="nowrap">
   				<label for="nombre">Nº Expediente</label>
  			</td>
   			<td align="left" nowrap="nowrap">
	   			<s:textfield name="permisoInternacionalDto.numExpediente" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="17" maxlength="17" readonly="true"/>
   			</td>
   			<td align="left" nowrap="nowrap">
       			<label for="estadoId">Estado:</label>
       		</td>
      		<td align="left" nowrap="nowrap">
       			<s:select id="estadoId" name="permisoInternacionalDto.estado" 
		       		list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaInterga@getInstance().getEstadosInterga()"
					headerKey="-1" headerValue="Iniciado" listKey="valorEnum" listValue="nombreEnum"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="true"/>
   			</td>
   		</tr>
		<tr>        	       			
			<td align="left" nowrap="nowrap">
   				<label for="refPropia">Referencia Propia: </label>
   			</td>
         	<td align="left" nowrap="nowrap">
       			<s:textfield id="refPropia" name="permisoInternacionalDto.refPropia" 
       				disabled="%{flagDisabled}" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
	       			size="35" maxlength="50"/>
   			</td>	
   			<td  align="left" nowrap="nowrap">
	       		<label id="labelTasaPermisoInternacional" for="tasaPermisoInternacional">Tasa: <span class="naranja">*</span></label>
		    </td>
	    	<td  align="left" nowrap="nowrap">
				<s:select id="tasaPermisoInternacional" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" disabled="%{flagDisabled}"
					name="permisoInternacionalDto.codigoTasa" 
					list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTasa@getInstance().getCodigosTasaPermisoInternacional(permisoInternacionalDto)"
					listKey="codigoTasa" listValue="codigoTasa"	headerKey="" headerValue="Seleccione Código de Tasa" />	
			</td>
			<!--<td align="left" nowrap="nowrap" >
					<input type="button" class="boton" id="botonCargaTasa" value = "Ver Todas"
						onclick="javascript:verTodasTasas('idContratoPermInter','tasaPermisoInternacional');"/>
			</td>-->
      	</tr>
      	
      	<tr></tr>
      	<tr></tr>
      	
      	<tr>
      		<td align="left" nowrap="nowrap">
        		<label for="diaPresentacionId">Fecha de Presentación:</label>
        	</td>
			<td align="left" nowrap="nowrap">
				<table>
					<tr>
						<td>
							<s:textfield readonly="true" name="permisoInternacionalDto.fechaPresentacion.dia" id="diaPresentacionId"
								size="2" maxlength="2" onblur="this.className='input2';" 
	       						onfocus="this.className='inputfocus';" onkeypress="return validarNumeros(event)"/>
						</td>
						<td>/</td>
						<td>
							<s:textfield readonly="true" name="permisoInternacionalDto.fechaPresentacion.mes" id="mesPresentacionId"
								size="2" maxlength="2" onblur="this.className='input2';"
	       						onfocus="this.className='inputfocus';" onkeypress="return validarNumeros(event)"/>
						</td>
						<td>/</td>
						<td>
							<s:textfield readonly="true" name="permisoInternacionalDto.fechaPresentacion.anio" id="anioPresentacionId"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="5" maxlength="4"
								onkeypress="return validarNumerosEnteros(event)"/>
						</td>
					</tr>
				</table>
			</td>	
      		<td align="left" nowrap="nowrap">
       			<label for="jefaturaId">Jefatura provincial:</label>
       		</td>
       		<td align="left" nowrap="nowrap">
       			<s:select id="idJefaturaPI" name="permisoInternacionalDto.jefatura" 
       				list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaInterga@getInstance().getJefaturasJPTEnum()"
					headerKey="" headerValue="Seleccione jefatura provincial" listKey="jefatura" listValue="descripcion"
					onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="true"/>
   			</td>
      	</tr>
      	</table>
		<table class="tablaformbasica">
	      	<tr>        	       			
				<td align="left" nowrap="nowrap">
	   				<label for="anotaciones">Observaciones:</label>
	   			</td>
	       		<td align="left" nowrap="nowrap" >
		   			<s:textarea name="permisoInternacionalDto.observaciones" 
	   					disabled="%{flagDisabled}" id="observaciones" onblur="this.className='input2';" 
		   				onfocus="this.className='inputfocus';" rows="5" cols="50"/>
				</td>
	      	</tr>
		</table>
	</div>
</div>