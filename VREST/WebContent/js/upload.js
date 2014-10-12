
$(function() {
	
	$(document).delegate('#step_upload','click', function(){
    	alert("This is upload shit");
    	var files = $(this).find("step_upload").val();
    	alert(files);
	});
    
});