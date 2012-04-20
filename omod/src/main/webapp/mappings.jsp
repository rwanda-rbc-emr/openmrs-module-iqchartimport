<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localInclude.jsp"%>
<%@ include file="template/localHeader.jsp"%>

<script type="text/javascript">
$(function() {
	$("#createProviderButton").click(function() {
		$("#createProvider").val("1");
		$("#mappingsForm").submit();
	});
});
</script>

<b class="boxHeader">
	<spring:message code="iqchartimport.mappings.entityMappings" />
</b>
<sform:form id="mappingsForm" commandName="mappings" cssClass="box" action="mappings.form" cssStyle="margin-bottom: 20px">
	<table>
		<tr>
	    	<td style="font-weight: bold" width="300"><spring:message code="iqchartimport.mappings.tracnetIdentifierType" /></td>
	    	<td>
	    		<sform:select path="tracnetIDTypeId">
	    			<c:if test="${mappings.tracnetIDTypeId == -1}">
		    			<sform:option value="-1" label="" />
		    		</c:if>
		    		<c:forEach items="${identifierTypes}" var="identifierType">
						<sform:option value="${identifierType.patientIdentifierTypeId}" label="${identifierType.name}" />
					</c:forEach>
				</sform:select>
	    	</td>
	    </tr>
	    <tr>
	    	<td style="font-weight: bold"><spring:message code="iqchartimport.mappings.addressProvince" /></td>
	    	<td>
	    		<sform:select path="addressProvince">
	    			<sform:option value="">&lt;<spring:message code="general.none" />&gt;</sform:option>
		    		<c:forEach items="${allProvinces}" var="province">
						<sform:option value="${province}" label="${province}" />
					</c:forEach>
				</sform:select>
	    	</td>
	    </tr>
	    <tr>
	    	<td style="font-weight: bold""><spring:message code="iqchartimport.mappings.hivProgram" /></td>
	    	<td>
	    		<sform:select path="HIVProgramId">
	    			<c:if test="${mappings.HIVProgramId == -1}">
		    			<sform:option value="-1" label="" />
		    		</c:if>
		    		<c:forEach items="${programs}" var="program">
						<sform:option value="${program.programId}" label="${program.name}" />
					</c:forEach>
				</sform:select>
				<c:if test="${fn:length(programs) == 0}">
					<span class="error"><spring:message code="iqchartimport.mappings.noExistingPrograms" /></span>
				</c:if>
	    	</td>
	    </tr>
	    <tr>
	    	<td style="font-weight: bold""><spring:message code="iqchartimport.mappings.tbProgram" /></td>
	    	<td>
	    		<sform:select path="TBProgramId">
	    			<c:if test="${mappings.TBProgramId == -1}">
		    			<sform:option value="-1" label="" />
		    		</c:if>
		    		<c:forEach items="${programs}" var="program">
						<sform:option value="${program.programId}" label="${program.name}" />
					</c:forEach>
				</sform:select>
				<c:if test="${fn:length(programs) == 0}">
					<span class="error"><spring:message code="iqchartimport.mappings.noExistingPrograms" /></span>
				</c:if>
	    	</td>
	    </tr>
	    <tr>
	    	<td style="font-weight: bold" width="300"><spring:message code="iqchartimport.mappings.siteLocation" /></td>
	    	<td>
	    		<sform:select path="siteLocationId">
	    			<c:if test="${mappings.siteLocationId == -1}">
		    			<sform:option value="-1" label="" />
		    		</c:if>
		    		<c:forEach items="${locations}" var="location">
						<sform:option value="${location.locationId}" label="${location.name}" />
					</c:forEach>
				</sform:select>
	    	</td>
	    </tr>
	    <tr>
	    	<td style="font-weight: bold" width="300"><spring:message code="iqchartimport.mappings.encounterProvider" /></td>
	    	<td>
	    		<c:choose>
	    			<c:when test="${encounterProvider != null}">
	    				<c:out value="${encounterProvider.personName}" />
	    			</c:when>
	    			<c:otherwise>
	    				<spring:message code="general.none"/>
	    				<input type="hidden" id="createProvider" name="createProvider" value="0" />
	    				<input type="button" id="createProviderButton" value="<spring:message code="iqchartimport.mappings.create"/>" />
	    			</c:otherwise>
	    		</c:choose>
	    	</td>
	    </tr>
	</table>
	<input type="submit" value="<spring:message code="general.save" />" />
</sform:form>

<b class="boxHeader">
	<spring:message code="iqchartimport.mappings.arvDrugMappings" />
</b>
<form id="arvDrugsForm" class="box" action="mappings.form">
	<table>
		<tr>
			<th>IQChart regimen component</th>
			<th>OpenMRS drug</th>
		</tr>
		<c:forEach items="${regComponents}" var="regComponent">
			<tr>
				<td>${regComponent.name}</td>
				<td>?</td>
			</tr>
		</c:forEach>
	</table>
</form>

<%@ include file="/WEB-INF/template/footer.jsp"%>