//COULD DO THIS FOR EVERY BUTTON INDIVIDUALLY 
//document.getElementById("curiosity").addEventListener('click', function(){
//	alert('curiousity was clicked!')})

//MORE CONCISE CODE 
//assigning an event listener to all three buttons. we then grab "this" id, cut off the "marsApi" from the id and left with specific id. 
let marsApiButtons = document.querySelectorAll("button[id*='marsApi']")
marsApiButtons.forEach(button => button.addEventListener('click', function() {
		//grabs the id from THIS specific button pushed
	const buttonId = this.id
	console.log(this.id)
	//use built it split function, tells where to split, returns an array. [0, "curiosity"] etc..
	const roverId = buttonId.split("marsApi")[1]
	//confirming it works 
	console.log(roverId + "%^&%$")
	let apiData = document.getElementById('marsApiRoverData')
	apiData.value = roverId
	console.log(apiData.value)
	document.getElementById('apiDataform').submit()
}))

function getUrlParameter(name) {
	name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
	var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
	var results = regex.exec(location.search);
	return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
};


let roverType = getUrlParameter("marsApiRoverData");

let marsSol = getUrlParameter("marsSol");
document.getElementById("marsSol").value = marsSol; 

function highlightButton(roverType){
	if(roverType == ''){
		roverType = "opportunity"
	}
	document.getElementById('marsApi' + roverType).classList.remove('btn-info')
	document.getElementById('marsApi' + roverType).classList.add('btn-outline-info')
}

highlightButton(roverType);
