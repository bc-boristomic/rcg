function checkEmail(email){
	if(email !== undefined){	
	   var atposition = email.indexOf("@");
	   var dotposition = email.lastIndexOf(".");	
	}
	
	if(atposition < 1 || dotposition < atposition + 2 || dotposition + 2 >= email.length){
		alert("Wrong e-mail address input. Please try again.");

			return false;

		}
	return true;
}


