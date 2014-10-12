$(document).delegate('#radioBtn a','click', function(){
    var sel = $(this).data('title');
    var tog = $(this).data('toggle');
//    alert ("toggle: "+tog+"sel: "+sel);
    $('#'+tog).prop('value', sel);
   // $('#'+tog).val(sel);
  //  alert("Value in mode"+$("#mode").val());
    $('a[data-toggle="'+tog+'"]').not('[data-title="'+sel+'"]').removeClass('active').addClass('notActive');
    $('a[data-toggle="'+tog+'"][data-title="'+sel+'"]').removeClass('notActive').addClass('active');
});
//$(document).ready(function() {
$(document).delegate('#add_row','click', function(){
  // $("#add_row").on("click", function() {
        // Dynamic Rows Code
        // Get max row id and set new id
        var newid = 0;
        $.each($("#tab_logic tr"), function() {
            if (parseInt($(this).data("id")) > newid) {
                newid = parseInt($(this).data("id"));
            }
        });
        newid++;
        var tr = $("<tr></tr>", {
            id: "addr"+newid,
            "data-id": newid
        });
        // loop through each td and create new elements with name of newid
        $.each($("#tab_logic tbody tr:nth(0) td"), function() {
            var cur_td = $(this);
            var children = cur_td.children();
            
            // add new td and element if it has a nane
            if ($(this).data("name") != undefined) {
                var td = $("<td></td>", {
                    "data-name": $(cur_td).data("name")
                });
                //alert("Data defined  "+$(td).data("name"));
                //alert("******"+$(cur_td).find($(children[0]).prop('tagName')));
                var c = $(cur_td).find($(children[0]).prop('tagName')).clone().val("");
                c.attr("name", $(cur_td).data("name") + newid);
                //alert("Data defined child  "+$(c).name)
                c.appendTo($(td));
                td.appendTo($(tr));
            } else {
                var td = $("<td></td>", {
                    'text': $('#tab_logic tr').length
                }).appendTo($(tr));
            }
            
        });
        
        // add delete button and td
        /*
        $("<td></td>").append(
            $("<button class='btn btn-danger glyphicon glyphicon-remove row-remove'></button>")
                .click(function() {
                    $(this).closest("tr").remove();
                })
        ).appendTo($(tr));
        */
        
        // add the new row
	   // alert("append tr to tab_logic")
        $(tr).appendTo($('#tab_logic tbody'));
        
        $(tr).find("td button.row-remove").on("click", function() {
        	// alert("you are here")
             $(this).closest("tr").remove();
        });
});




    // Sortable Code
    /*var fixHelperModified = function(e, tr) {
        var $originals = tr.children();
        var $helper = tr.clone();
    
        $helper.children().each(function(index) {
            $(this).width($originals.eq(index).width())
        });
        
        return $helper;
    };
  
    $(".table-sortable tbody").sortable({
        helper: fixHelperModified      
    }).disableSelection();

    $(".table-sortable thead").disableSelection();



    $("#add_row").trigger("click");
});*/