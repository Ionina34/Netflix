$("input:text").on('input',function(){
	this.style.borderColor = "black";
})

$("textarea").on('input',function(){
	this.style.borderColor = "black";
})

function valid(){
	let dataFilm = $(".film").children();
	let check = true;
	for (let i = 0; i < dataFilm.length; i++) {
		if (dataFilm[i].tagName.toLowerCase() === 'p') {
			let textarea = dataFilm[i].querySelector('textarea');
			if (textarea) {
				console.log(textarea.value)
				if (textarea.value == " ") {
					textarea.style.borderColor = "red";
					if (check) {
						alert("Заполните все поля для фильма");
					}
					check == false;
				}
			}
		}
		else if (dataFilm[i].tagName.toLowerCase() === 'input') {
			if (dataFilm[i].value === "") {
				dataFilm[i].style.borderColor = "red";
				if (check) {
					alert("Заполните все поля для фильма");
				}
				check = false;
			}
		}
	}
	if(check) saveFilm();
}

function saveFilm() {
	data={}
	
	film={};
	let genres=[]
	let countries=[]
	let actors=[]
	let directors=[]
	let screenwriters=[]
	
	dataCollection(film,genres,countries,actors,directors,screenwriters);
	
	data["film"]=film
	data["genres"]=genres
	data["countries"]=countries
	data["actors"]=actors
	data["directors"]=directors
	data["screenwriters"]=screenwriters
	
	$.ajax({
		type:"POST",
		url:"http://localhost:8000/admin/films/add",
		contentType: "application/json",
		data:JSON.stringify(data),
		dataType: 'json',
		cache: false,
		xhrFields: {
			withCredentials: true
		},
		success: function(response) {
			$(location).attr('href',"http://localhost:8000/admin/films")
		},
		error: function(e) {
			//$(location).attr('href',"http://localhost:8000/login")
		}
	})
}