
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

var receiptColumn = {
	    TYPE : 0,
	    NAME : 1,
	    GENDER : 2,
	    PRICE : 3,
	    DISCOUNT : 4,
	    RESULT : 5,
	    LOWERREF : 6,
	    UPPERREF : 7,
	    UNIT : 8,
	    COMMENTS : 9,
	    DATE : 10
	};

$(function() {
	$('#printReport').on('click', function() {
		$('#printable').print(reportContent());
	});
	$('#printPriceReceipt').on('click', function() {
		$('#printable').print(receiptPriceContent());
	});
	$('#printReceipt').on('click', function() {
		$('#printable').print(receiptContent());
	});
});

userDetails = function(){
	var element = "";
	$( "#receiptUserDetails > h3" ).each(function() {
		  element += $( this ).html();
		  element += "<br>";
	});
	//element += "<h3>Date: " + $.datepicker.formatDate('dd-MM-yy', new Date()) + "</h3";
	return element;
};

reportContent = function() {
	var element = "";
	element += userDetails();
	element += "<br><table border=1 width=100%><tr><th>Test Name</th><th>Result</th><th>Range</th><th>Comments</th></tr>";
	$( "#displayTestsTable > tbody > tr").each(function() {
		
		if(!($('td:eq(' + receiptColumn.NAME + ') input', this).val() === undefined)){
			element += ("<tr>" + 
				"<td>" +  $('td:eq(' + receiptColumn.NAME + ') input', this).val() + "</td>"); //name
				if($('td:eq(' + receiptColumn.RESULT + ') input', this).val() < $('td:eq(' + receiptColumn.LOWERREF + ') input', this).val() ||
						$('td:eq(' + receiptColumn.RESULT + ') input', this).val() > $('td:eq(' + receiptColumn.UPPERREF + ') input', this).val()){
					element += ("<td style='color: red;	font-weight: bold'>");
				}
				else{
					element += ("<td>");
				}	
				element += ($('td:eq(' + receiptColumn.RESULT + ') input', this).val() + "</td>" + //result
				
				"<td>" +  $('td:eq(' + receiptColumn.LOWERREF + ') input', this).val() + " - " +  $('td:eq(' + receiptColumn.UPPERREF + ') input', this).val() + " " +
				$('td:eq(' + receiptColumn.UNIT + ') input', this).val() + "</td>" + // lower - upper unit
				
				"<td>" +  $('td:eq(' + receiptColumn.COMMENTS + ') input', this).val() + "</td>" + //date
				"</tr>");
		}
	});
	element += "</table><br>";
	return element;
};

receiptPriceContent = function() {
	var element = "";
	element += userDetails();
	element += "<br><table border=1 width=100%><tr><th>Test Name</th><th>Price</th><th>Discount</th><th>Date</th></tr>";
	$( "#displayTestsTable > tbody > tr").each(function() {
		
		if(!($('td:eq(1) input', this).val() === undefined)){
		element += ("<tr>" + 
				"<td>" +  $('td:eq(' + receiptColumn.NAME + ') input', this).val() + "</td>" + //name
				"<td>" +  $('td:eq(' + receiptColumn.PRICE + ') input', this).val() + "</td>" + //price
				
				"<td>" +  $('td:eq(' + receiptColumn.DISCOUNT + ') input', this).val() + "</td>" + //discount
				
				"<td>" +  $('td:eq(' + receiptColumn.DATE + ') input', this).val() + "</td>" + //date
				"</tr>");
		}
	});
	element += "</table><br>";
	return element;
};

receiptConent = function() {
	var element = "";
	$( "#receiptUserDetails > h3" ).each(function() {
		  element += $( this ).html();
		  element += "<br>";
	});
	return element;
};