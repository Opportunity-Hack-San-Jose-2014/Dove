/**
 * 
 */

 $(document).delegate('.methodradio','change', function(){
	 console.log("radio clicked");
	 
	 var radioVal = $(this).val();
	 if(radioVal == "schema_call"){
		 console.log("schemer");
		 alert($(this).parent().find('#schemaserverendpoint').text());
		 $(this).find('#schemaserverendpoint').text("http://eazye.qa.ebay.com/ws/api.dll");
		 console.log("schemer212");
//		 var s = $("#serverendpoint").val();
//		 alert(s);
	 }
		 
	 else if (radioVal == "soap_call"){
		 console.log("soaper");
		 $('#serverendpoint').val("http://eazye.qa.ebay.com:8080/wsapi");
//		 var s = $("#serverendpoint").val();
//		 alert(s);
	 }
 });
 
 $(document).delegate('#apiheaders','change', function(){
//	 var text = $('#apiheaders')[2].val();
	 var headerVal = $(this).val();
	 
	 if(headerVal == "admin"){
		 $('#appid').val("AdminApp");
	 }
	 else if(headerVal == "mip"){
		 
	 }
	 else if(headerVal == "fe"){
		 
	 }
	 else if(headerVal == "prod"){
		 
	 }
	 console.log(text);
 });