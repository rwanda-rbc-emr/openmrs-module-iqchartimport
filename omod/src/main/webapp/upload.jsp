<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

<b class="boxHeader"><spring:message code="@MODULE_ID@.upload.uploadNewDatabase" /></b>
<form class="box" method="post" enctype="multipart/form-data" style="margin-bottom: 20px">
	<table>
		<tr>
	    	<td style="font-weight: bold" width="300"><spring:message code="@MODULE_ID@.upload.mdbFile" /></td>
	    	<td>
	    		<input type="file" name="mdbfile" size="40" />
	    	</td>
	    </tr>
	</table>
	<input type="submit" value="<spring:message code="@MODULE_ID@.general.upload" />" />
	<c:if test="${not empty uploaderror}">
		<span class="error"><c:out value="${uploaderror}" /></span>
	</c:if>
</form>

<c:if test="${database != null}">
	<b class="boxHeader"><spring:message code="@MODULE_ID@.upload.currentDatabase" /></b>
	<form method="post" class="box">
		<table width="100%" cellspacing="0">
			<tr>
				<td style="font-weight: bold" width="300"><spring:message code="@MODULE_ID@.upload.filename" /></td>
				<td><c:out value="${database.originalFilename}" /></td>
			</tr>
			<tr>
				<td style="font-weight: bold"><spring:message code="@MODULE_ID@.general.patients" /></td>
				<td>${patientCount}</td>
			</tr>
		</table>
	
		<input type="button" value="<spring:message code="@MODULE_ID@.general.review" />..." onclick="location.href='patients.list'" />
		<input type="button" value="<spring:message code="@MODULE_ID@.general.import" />..." onclick="location.href='import.form'" />
		
		<c:if test="${not empty parseerror}">
			<span class="error"><c:out value="${parseerror}" /></span>
		</c:if>
	</form>
</c:if>

<%@ include file="/WEB-INF/template/footer.jsp"%>