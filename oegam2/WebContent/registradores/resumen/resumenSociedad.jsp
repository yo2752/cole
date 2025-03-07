<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table border="0" class="tablaformbasicaTC">
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="cif">CIF:</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:if test="tramiteRegistro.sociedad.nif != null && tramiteRegistro.sociedad.nif != ''">
				<s:textfield name="tramiteRegistro.sociedad.nif" id="resumenCif" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="9" disabled="true"/>
			</s:if>
			<s:else>
				<s:textfield name="cif" id="resumenCif" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="9"
					value="REQUERIDO" disabled="true" cssStyle="color:red"/>
			</s:else>
		</td>
		<td align="right" nowrap="nowrap">
			<label for="denominacionSocial">Denominaci&oacute;n social:</label>
		</td>
		<td align="left" nowrap="nowrap" colspan="6">
			<s:if test="tramiteRegistro.sociedad.apellido1RazonSocial != null && tramiteRegistro.sociedad.apellido1RazonSocial != ''">
				<s:textfield name="tramiteRegistro.sociedad.apellido1RazonSocial" id="resumenDenominacionSocial" 
					onblur="this.className='input2';" disabled="true"
					onfocus="this.className='inputfocus';" size="40" maxlength="40"/>
			</s:if>
			<s:else>
				<s:textfield name="denominacionSocial" id="resumenDenominacionSocial" 
					onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="40" maxlength="40"
					value="REQUERIDO" disabled="true" cssStyle="color:red"/>
			</s:else>		
		</td>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="ius">IUS:</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:if test="tramiteRegistro.sociedad.ius != null">
				<s:textfield name="tramiteRegistro.sociedad.ius" id="resumenIus" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="19" maxlength="15" disabled="true"/>
			</s:if>
			<s:else>
				<s:textfield name="ius" id="resumenIus" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="19" maxlength="15"
					value="No requerido" cssStyle="color:red" disabled="true" 
					onkeypress="return validarNumerosEnteros(event)" />
			</s:else>
		</td>
		<td align="right" nowrap="nowrap">
			<label for="seccion">Secci&oacute;n:</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:if test="tramiteRegistro.sociedad.seccion != null">
				<s:textfield name="tramiteRegistro.sociedad.seccion" id="resumenSeccion" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="10" disabled="true"/>
			</s:if>
			<s:else>
				<s:textfield name="seccion" id="resumenSeccion" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="10"
					value="No requerido" cssStyle="color:red" disabled="true"/>
			</s:else>
		</td>
		<td align="right" nowrap="nowrap">
			<label for="hoja">Hoja:</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:if test="tramiteRegistro.sociedad.hoja != null">
				<s:textfield name="tramiteRegistro.sociedad.hoja" id="resumenHoja" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="10" disabled="true"/>
			</s:if>
			<s:else>
				<s:textfield name="hoja" id="resumenHoja" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="10"
					value="No requerido" disabled="true" cssStyle="color:red"/>
			</s:else>		
		</td>
		<td align="right" nowrap="nowrap">
			<label for="hojaBis">Hoja bis:</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:if test="tramiteRegistro.sociedad.hojaBis != null && tramiteRegistro.sociedad.hojaBis!=''">
				<s:textfield name="tramiteRegistro.sociedad.hojaBis" id="resumenHojaBis" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="10" disabled="true"/>
			</s:if>
			<s:else>
				<s:textfield name="hojaBis" id="resumenHojaBis" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="10" maxlength="10"
					value="No requerido" disabled="true" cssStyle="color:red"/>
			</s:else>
		</td>
	</tr>
	<tr>
		<td align="right" nowrap="nowrap">
			<label for="tipoPersona">Tipo persona:</label>
		</td>
		<td align="left" nowrap="nowrap">
			<s:if test="tramiteRegistro.sociedad.subtipo != null && tramiteRegistro.sociedad.subtipo != ''">
				<s:textfield name="tramiteRegistro.sociedad.subtipo" id="resumenSubtipo" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="18" maxlength="18" disabled="true"/>
			</s:if>
			<s:else>
				<s:textfield name="subtipo" id="resumenSubtipo" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="18" maxlength="18"
					value="No requerido" disabled="true" cssStyle="color:red"/>
			</s:else>
		</td>
		<td align="right" nowrap="nowrap">
			<label for="email">Correo Electrónico:</label>
		</td>
		<td align="left" nowrap="nowrap" colspan="5">
			<s:if test="tramiteRegistro.sociedad.correoElectronico != null && tramiteRegistro.sociedad.correoElectronico != ''">
				<s:textfield  name="tramiteRegistro.sociedad.correoElectronico" id="resumenEmail" onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" size="40" maxlength="40" disabled="true"/>
			</s:if>
			<s:else>
				<s:textfield  name="email" id="resumenEmail" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" size="40" maxlength="40"
					value="No requerido" cssStyle="color:red" disabled="true" />
			</s:else>	
		</td>
	</tr>
</table>
