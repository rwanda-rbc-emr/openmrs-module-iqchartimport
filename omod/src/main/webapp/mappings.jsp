<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<b class="boxHeader"><spring:message code="@MODULE_ID@.mappings.entityMappings" /></b>
<form class="box" method="post">
<sform:form commandName="mappings" cssClass="box" action="mappings.form">
	<table>
		<tr>
	    	<td style="font-weight: bold" width="300"><spring:message code="@MODULE_ID@.mappings.tracnetIdentifierType" /></td>
	    	<td>
	    		<sform:select path="tracnetIDTypeId">
	    			<c:if test="${mappings.tracnetIDTypeId == -1}">
		    			<sform:option value="-1" label="" />
		    		</c:if>
		    		<sform:option value="0">&lt;<spring:message code="@MODULE_ID@.mappings.createNew" />&gt;</sform:option>
					<c:forEach items="${identifierTypes}" var="identifierType">
						<sform:option value="${identifierType.patientIdentifierTypeId}" label="${identifierType.name}" />
					</c:forEach>
				</sform:select>
	    	</td>
	    </tr>
	    <tr>
	    	<td style="font-weight: bold"><spring:message code="@MODULE_ID@.mappings.addressProvince" /></td>
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
	    	<td style="font-weight: bold""><spring:message code="@MODULE_ID@.mappings.hivProgram" /></td>
	    	<td>
	    		<sform:select path="hivProgramId">
	    			<c:if test="${mappings.hivProgramId == -1}">
		    			<sform:option value="-1" label="" />
		    		</c:if>
		    		<c:forEach items="${programs}" var="program">
						<sform:option value="${program.programId}" label="${program.name}" />
					</c:forEach>
				</sform:select>
				<c:if test="${fn:length(programs) == 0}">
					<span class="error"><spring:message code="@MODULE_ID@.mappings.noExistingPrograms" /></span>
				</c:if>
	    	</td>
	    </tr>
	    <tr>
	    	<td style="font-weight: bold" width="300"><spring:message code="@MODULE_ID@.mappings.encounterType" /></td>
	    	<td>
	    		<sform:select path="encounterTypeId">
	    			<c:if test="${mappings.encounterTypeId == -1}">
		    			<sform:option value="-1" label="" />
		    		</c:if>
		    		<sform:option value="0">&lt;<spring:message code="@MODULE_ID@.mappings.createNew" />&gt;</sform:option>
					<c:forEach items="${encounterTypes}" var="encounterType">
						<sform:option value="${encounterType.encounterTypeId}" label="${encounterType.name}" />
					</c:forEach>
				</sform:select>
	    	</td>
	    </tr>
	    <tr>
	    	<td style="font-weight: bold" width="300"><spring:message code="@MODULE_ID@.mappings.encounterLocation" /></td>
	    	<td>
	    		<sform:select path="encounterLocationId">
	    			<c:if test="${mappings.encounterLocationId == -1}">
		    			<sform:option value="-1" label="" />
		    		</c:if>
		    		<c:forEach items="${locations}" var="location">
						<sform:option value="${location.locationId}" label="${location.name}" />
					</c:forEach>
				</sform:select>
	    	</td>
	    </tr>
	</table>
	<input type="submit" value="<spring:message code="general.save" />" />
</sform:form>

<%@ include file="/WEB-INF/template/footer.jsp"%>