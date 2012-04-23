<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localInclude.jsp"%>
<%@ include file="template/localHeader.jsp"%>

<script type="text/javascript">	
function updateTaskStatus() {
    $.ajax({
        url: 'status.form',
        dataType: 'json',
        success: function (data) {
        	var task = data.task;
			if (task == null) {
				$('#importbutton').removeAttr('disabled');
				$('#statusmsg').text('<spring:message code="@MODULE_ID@.import.ready" />');
			}
			else {
				if (task.completed) {
					$('#importbutton').removeAttr('disabled');
					
					if (task.exception == null)				
						$('#statusmsg').text('<spring:message code="@MODULE_ID@.import.finished" /> (' + task.importedPatients + ' patients imported)');
					else					
						$('#statusmsg').text('<spring:message code="@MODULE_ID@.import.failed" /> (' + task.exception + ')');	
	        	}
				else {
					$('#progressbar').progressbar("value", task.progress);
					$('#statusmsg').text(task.progress + "%");
					$('#importbutton').attr('disabled', 'disabled');
				}
				
				$('#issues').empty();
				for (var i = 0; i < task.issues.length; ++i) {
					var issue = task.issues[i];
					var patUrl = openmrsContextPath + "/patientDashboard.form?patientId=" + issue.patientId;
					$('#issues').append('<a href="' + patUrl + '">Patient ' + issue.patientId + '</a>: ' + issue.message + '<br />');
				}
			}

			setTimeout('updateTaskStatus()', 3000);
        }
    });
}

$(function() {
	$('#progressbar').progressbar();
	
	updateTaskStatus();
});
</script>

<b class="boxHeader"><spring:message code="@MODULE_ID@.import.importCurrentDatabase" /></b>
<form class="box" method="post">
	<table width="100%" border="0">
		<tr>
	    	<td style="font-weight: bold" width="300"><spring:message code="@MODULE_ID@.import.status" /></td>
	    	<td>
	    		<div id="progressbar" style="width: 400px; position: relative;">
	    			<div id="statusmsg" style="width: 400px; position: absolute; left: 0; top: 0; text-align: center; z-index: 10;"></div>
	    		</div>
	    	</td>
	    </tr>
	    <tr>
	    	<td style="font-weight: bold; vertical-align: top;" width="300"><spring:message code="@MODULE_ID@.import.issues" /></td>
	    	<td>
	    		<div id="issues" style="background-color: #EEE; font-style: italic"></div>
	    	</td>
	    </tr>
	</table>
	<input type="submit" id="importbutton" value="<spring:message code="@MODULE_ID@.import.start" />" />
</form>

<%@ include file="/WEB-INF/template/footer.jsp"%>