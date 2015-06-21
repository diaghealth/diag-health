(function( $ ) {
// Create a jquery plugin that prints the given element.
$.fn.print = function(args){
	// NOTE: We are trimming the jQuery collection down to the
	// first element in the collection.
	if (this.size() > 1){
		this.eq( 0 ).print();
		return;
	} else if (!this.size()){
		return;
	}
	 
	// ASSERT: At this point, we know that the current jQuery
	// collection (as defined by THIS), contains only one
	// printable element.
	 
	// Create a random name for the print frame.
	var strFrameName = ("printer-" + (new Date()).getTime());
	 
	// Create an iFrame with the new name.
	var jFrame = $( "<iframe name='" + strFrameName + "'>" );
	 
	// Hide the frame (sort of) and attach to the body.
	jFrame
	.css( "width", "1px" )
	.css( "height", "1px" )
	.css( "position", "absolute" )
	.css( "left", "-9999px" )
	.appendTo( $( "body:first" ) )
	;
	 
	// Get a FRAMES reference to the new frame.
	var objFrame = window.frames[ strFrameName ];
	 
	// Get a reference to the DOM in the new frame.
	var objDoc = objFrame.document;
	 
	// Grab all the style tags and copy to the new
	// document so that we capture look and feel of
	// the current document.
	 
	// Create a temp document DIV to hold the style tags.
	// This is the only way I could find to get the style
	// tags into IE.
	var jStyleDiv = $( "<div>" ).append(
	$( "style" ).clone()
	);
	 
	// Write the HTML for the document. In this, we will
	// write out the HTML of the current element.
	objDoc.open();
	objDoc.write( "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" );
	objDoc.write( "<html>" );
	objDoc.write( "<body>" );
	objDoc.write( "<head>" );
	objDoc.write( "<title>" );
	objDoc.write( document.title );
	objDoc.write( "</title>" );
	objDoc.write( jStyleDiv.html() );
	objDoc.write( "</head>" );	
	//objDoc.write( this.html() );
	objDoc.write( args );
	objDoc.write( "</body>" );
	objDoc.write( "</html>" );
	objDoc.close();
	 
	// Print the document.
	objFrame.focus();
	objFrame.print();
	 
	// Have the frame remove itself in about a minute so that
	// we don't build up too many of these frames.
	setTimeout(function(){jFrame.remove();},(60 * 1000));
};
}( jQuery ));

$(function() {
	$('#printReport').on('click', function() {
		$('#printable').print(reportContent());
	});
	$('#printPriceReceipt').on('click', function() {
		$('#printable').print("");
	});
	$('#printReceipt').on('click', function() {
		$('#printable').print("");
	});
});

reportContent = function() {
	var element = "";
	$( "#receiptUserDetails > h3" ).each(function() {
		  element += $( this ).html();
		  element += "<br>";
	});
	element += "<table><tr><th>Test Name</th><th>Result</th><th>Range</th><th>Date</th></tr>";
	var header = 0;
	$( "#displayTestsTable > tr").each(function() {
		/*if(header == 0)	
			header++;
		else {*/
		element += ("<tr>" + 
				"<td>" +  $(this).find(".name").val() + "</td>" +
				"<td>" +  $(this).find(".resultValue").val() + "</td>" +
				
				"<td>" +  $(this).find(".refLower").val() + " - " +  $(this).find(".refUpper").val() + " " +
				$(this).find(".unit").val() + "</td>" +
				
				"<td>" +  $(this).find(".dateCreated").val() + "</td>" +
				"</tr>");
		//}
	});
	element += "</table>";
	return element;
};

receiptContent = function() {
	var element = $("#receiptUserDetails").text();
	return element;
}