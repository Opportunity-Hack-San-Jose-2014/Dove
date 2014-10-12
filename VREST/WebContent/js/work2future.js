
  $(function() {
     $( "#from" ).datepicker({
      defaultDate: "+1w",
      changeMonth: true,
      numberOfMonths: 3,
      onClose: function( selectedDate ) {
        $( "#to" ).datepicker( "option", "minDate", selectedDate );
      }
    });
    $( "#to" ).datepicker({
      defaultDate: "+1w",
      changeMonth: true,
      numberOfMonths: 3,
      onClose: function( selectedDate ) {
        $( "#from" ).datepicker( "option", "maxDate", selectedDate );
      }
    });
	
	$( "#from2" ).datepicker({
      defaultDate: "+1w",
      changeMonth: true,
      numberOfMonths: 3,
      onClose: function( selectedDate ) {
        $( "#to2" ).datepicker( "option", "minDate", selectedDate );
      }
    });
    $( "#to2" ).datepicker({
      defaultDate: "+1w",
      changeMonth: true,
      numberOfMonths: 3,
      onClose: function( selectedDate ) {
        $( "#from2" ).datepicker( "option", "maxDate", selectedDate );
      }
    });
	
	$( "#dob" ).datepicker({
      changeMonth: true,
      changeYear: true
    });
	
	
	
	
	
	
	
	$(document).delegate('#add_header', 'click', function()
	{
		$("#required").append(' <input type="text" id= "requiredskills" class="form-control" placeholder="communication"><br>');
		
	});
	
	$(document).delegate('#seekers', 'click', function()
	{
		$('#candidates-table tr').has('td').remove();
		$.ajax({ 
	        type: "GET",
	        url: "http://localhost:8080/VREST/api/v1/service/allcandidates",
	        success: function(response){        
	           var i = 1;
	           //response = response.candidates;
	           $.each(response.candidates, function (i, item) {
	        	   $('#candidates-table').append('<tr><td>'+item.firstname+' </td><td>'+item.lastname+'</td><td>'+item.phoneno+'</td><td><a href="#" class="findjobs" id="'+item.phoneno+'"> Find jobs</a></td></tr>');
	           });
	        	
	        },
		 	error:function(data){
	       		
		 	}
		});
	});
	
	$(document).delegate('.findjobs', 'click', function()
	{
		var id = $(this).attr('id');
		$('#bestjobstable tr').has('td').remove();
		$.ajax({ 
	        type: "GET",
	        url: "http://localhost:8080/VREST/api/v1/service/bestjobs/"+id,
	        success: function(response){        
	           $.each(response.jobs, function (i, item) {
	        	   
	        	   $('#bestjobstable').append('<tr><td>'+item.companyname+' </td><td>'+item.jobname+'</td><td>'+item.jobaddress+'</td><td>'+item.email+'</td></tr>');
	           });
	        	
	
	        	$('#bestjobs').trigger('click');
	        	
	        },
		 	error:function(data){
	        	alert("failure");
		 	}
		});
	});
	
    $( "#tabs" ).tabs({
      event: "mouseover"
    });
  
	$(document).delegate('#jobopening', 'click', function()
	{
		var s = "";
		var jsonObj= { posts:[]};
		jsonObj.posts.push({
			"_id" : $(document).find("#jobtitle").val(),
			"jobname" : $(document).find("#jobtitle").val(),
			"jobdescription" : $(document).find("#jobdescription").val(),
			"phoneno" : $(document).find("#jobphoneno").val(),
			"email" : $(document).find("#jobemail").val(),
			"salary" : $(document).find("#salary").val(),
			"jobaddress" : $(document).find("#jobaddress").val(),
			"jobdate":[],
			"from" : $(document).find("#from2").val(),
			"to" : $(document).find("#to2").val(),
			"skills":[]
		});
		s = JSON.stringify(jsonObj,undefined,2);
		alert(s);
		
		
		 $.ajax({ 
             type: "POST",
             dataType: 'json',
		 'contentType': 'application/json',
             data: s,
             url: "http://localhost:8080/VREST/api/v1/service/job",
             success: function(data){        
                var i = 1;
                $('#closejobopening').trigger('click');
                $( "#dialog-message" ).dialog({
					modal: true,
					buttons: {
					Ok: function() {
							$( this ).dialog( "close" );
						}
					}
				});
             	
             },
		 	error:function(data){
            	 $('#closejobopening').trigger('click');
            	 $( "#dialog-message" ).dialog({
 					modal: true,
 					buttons: {
 					Ok: function() {
 							$( this ).dialog( "close" );
 						}
 					}
 				});
		 	}
         });
	});
	
	
	$(document).delegate('#jobrequest', 'click', function()
	{
		var s = "";
		var jsonObj= { candidate:[]};
		jsonObj.candidate.push({
			"_id" : $(document).find("#phoneno").val(),
			"firstname" : $(document).find("#firstname").val(),
			"lastname" : $(document).find("#lastname").val(),
			"dob" : $(document).find("#dob").val(),
			"phoneno" : $(document).find("#phoneno").val(),
			"currenteducation" : $(document).find("#currenteducation").val(),
			"bio" : $(document).find("#bio").val(),
			"homeaddress" : $(document).find("#homeaddress").val(),
			"jobdate":[],
			"from" : $(document).find("#from").val(),
			"to" : $(document).find("#to").val(),
			"skills":[]
		});
		s = JSON.stringify(jsonObj,undefined,2);
		alert(s);
		
		
		 $.ajax({ 
             type: "POST",
             dataType: 'json',
		 'contentType': 'application/json',
             data: s,
             url: "http://localhost:8080/VREST/api/v1/service/candidate",
             success: function(data){        
                var i = 1;
                $('#closejobrequest').trigger('click');
                $( "#dialog-message" ).dialog({
					modal: true,
					buttons: {
					Ok: function() {
							$( this ).dialog( "close" );
						}
					}
				});
             	
             },
		 	error:function(data){
            	 $('#closejobrequest').trigger('click');
            	 $( "#dialog-message" ).dialog({
 					modal: true,
 					buttons: {
 					Ok: function() {
 							$( this ).dialog( "close" );
 						}
 					}
 				});
		 	}
         });
	});
	
});