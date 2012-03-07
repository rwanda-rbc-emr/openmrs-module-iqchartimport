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
					<c:forEach items="${idTypes}" var="idType">
						<sform:option value="${idType.patientIdentifierTypeId}" label="${idType.name}" />
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
	</table>
	<input type="submit" value="<spring:message code="general.save" />" />
</sform:form>

<%@ include file="/WEB-INF/template/footer.jsp"%>