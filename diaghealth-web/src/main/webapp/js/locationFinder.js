
var map;
var marker;
var myLatlng; 
var geocoder = new google.maps.Geocoder();
var infowindow = new google.maps.InfoWindow();
var placeSearch, autocomplete;
var place;
function initialize(){
			
myLagLng = new google.maps.LatLng(28.6139391, 77.20902120000005); 
if (!isNaN($('#latitude').val()) && !isNaN($('#longitude').val())){
	myLagLng = new google.maps.LatLng($('#latitude').val(), $('#longitude').val()); 
}
var mapOptions = {
zoom: 12,
center: myLatlng,
mapTypeId: google.maps.MapTypeId.ROADMAP
};

autocomplete = new google.maps.places.Autocomplete(
      (document.getElementById('location')),
      { types: ['geocode'] });
// Create the autocomplete object, restricting the search
  // to geographical location types.
  autocomplete = new google.maps.places.Autocomplete(
      (document.getElementById('location')),
      { types: ['geocode'] });
  // When the user selects an address from the dropdown,
  // populate the address fields in the form.
  google.maps.event.addListener(autocomplete, 'place_changed', function() {
    fillInAddress();
  });

map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);

marker = new google.maps.Marker({
map: map,
position: myLatlng,
draggable: true 
}); 

geocoder.geocode({'latLng': myLatlng }, function(results, status) {
if (status == google.maps.GeocoderStatus.OK) {
if (results[0]) {
$('#latitude,#longitude').show();
$('#location').val(results[0].formatted_address);
$('#latitude').val(marker.getPosition().lat());
$('#longitude').val(marker.getPosition().lng());
//infowindow.setContent(results[0].formatted_address);
//infowindow.open(map, marker);
}
}
});

google.maps.event.addListener(marker, 'dragend', function() {

geocoder.geocode({'latLng': marker.getPosition()}, function(results, status) {
if (status == google.maps.GeocoderStatus.OK) {
if (results[0]) {
$('#location').val(results[0].formatted_address);
$('#latitude').val(marker.getPosition().lat());
$('#longitude').val(marker.getPosition().lng());
//infowindow.setContent(results[0].formatted_address);
//infowindow.open(map, marker);
}
}
});
});

}

// [START region_fillform]
function fillInAddress() {
  // Get the place details from the autocomplete object.
  place = autocomplete.getPlace();
  document.getElementById('latitude').value = place.geometry.location.lat();
  document.getElementById('longitude').value = place.geometry.location.lng();
  var location    =   new google.maps.LatLng(place.geometry.location.lat(),place.geometry.location.lng());
  marker.setPosition(location);
  map.setCenter(location);
}
// [END region_fillform]

// Bias the autocomplete object to the user's geographical location,
// as supplied by the browser's 'navigator.geolocation' object.
function geolocate() {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(function(position) {
      var geolocation = new google.maps.LatLng(
          position.coords.latitude, position.coords.longitude);
      var circle = new google.maps.Circle({
        center: geolocation,
        radius: position.coords.accuracy
      });
      autocomplete.setBounds(circle.getBounds());
    });
  }
}
// [END region_geolocation]

google.maps.event.addDomListener(window, 'load', initialize);