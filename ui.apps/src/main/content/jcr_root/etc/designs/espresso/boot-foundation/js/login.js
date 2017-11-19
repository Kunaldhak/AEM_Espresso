
$(function() {
checkCookie();
  
$("#inputPassword").keyup(function(event){
if(event.keyCode == 13){
$("#loginButton").click();
}
})
  
$("#loginButton").click(function(e){
  
e.preventDefault();
if($('#rememberCheckbox').is(":checked")) {
user = $("#userId").val();
if (user != "" && user != null) {
setCookie("usr_c",user,30);
}
}
  
var valid = validateForm();
if(valid){
$.ajax({type: "POST",
url: $('#url').val(),
data: { j_username: $("#userId").val(), j_password: $("#inputPassword").val(),j_validate: "true" },
success:function(data,textStatus,jqXHR ){;
window.location.href=getRedirectPath();
},
error: function(XMLHttpRequest, textStatus, errorThrown) {
$("#errordiv").val("Invalid User Name or Password");
}});
} else{
$("#errordiv").val("Invalid User Name or Password");
}
});
});
  
//Function to redirect to homepage after login
var getRedirectPath = function () {
return "/content/summit-toys/en.html";
}
  
// Function for setting cookies at login page
var setCookie = function (cname,cvalue,exdays) {
var d = new Date();
d.setTime(d.getTime() + (exdays*24*60*60*1000));
var expires = "expires=" + d.toGMTString();
document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}
  
// Function for getting cookies at login page
var getCookie = function (cname) {
var name = cname + "=";
var decodedCookie = decodeURIComponent(document.cookie);
var ca = decodedCookie.split(';');
for(var i = 0; i < ca.length; i++) {
var c = ca[i];
while (c.charAt(0) == ' ') {
c = c.substring(1);
}
if (c.indexOf(name) == 0) {
return c.substring(name.length, c.length);
}
}
return "";
}
  
// Function for checking cookies at login page
var checkCookie = function () {
var user=getCookie("usr_c");
if (user != "") {
$("#userId").val(user);
}
}
  
// Function for validating Login form
var validateForm = function() {
var valid = false;
if(($("#userId").val().length >0) || ($("#inputPassword").val().length >0)){
valid = true;
}
return valid;
}