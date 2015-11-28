$(document).ready(function() {
	
	$(".row").each(function() {
		
		$(this).find(".btnRed").click(changeColorRed);
		
		$(this).find(".btnYellow").click(changeColorYellow);
		
		$(this).find(".btnDetail").click(getBoardDetail);
	});
	
	
	$("#btnDetailToggle").on("click", function() {
		$("#detailArea").toggle("slow");
	});
});

function changeColorRed() {
	$(this).parent().parent().find("td").css("color", "red");
}

function changeColorYellow() {
	$(this).parent().parent().find("td").css("color", "yellow");
}

function getBoardDetail() {
	var boardNo = $(this).attr("boardNo");
	
	$.ajax({
		type:"GET",	
		url:"/board/detail/json",
		data: "boardNo=" + boardNo,			// parameters as plain object
		datatype:"json",						// html, xml, json, jsonp, script, text
		timeout:10000,
		error:function() {						// Ajax error handler
			alert('ajax failed');
		},
		success:function(data, status) {	// Ajax complete handelr
			
			var textBox = $("<input type='text'>");
			textBox.val(data.contents);
			
			$("#detailArea").empty().append(textBox);
		}
	});
	
	
//	$.ajax({
//		type:"GET",	
//		url:"/board/detail",
//		data: "boardNo=" + boardNo,			// parameters as plain object
//		datatype:"html",						// html, xml, json, jsonp, script, text
//		timeout:10000,
//		error:function() {						// Ajax error handler
//			alert('ajax failed');
//		},
//		success:function(data, status) {	// Ajax complete handelr
//			$("#detailArea").empty().append(data);
//		}
//	});
}

