function saveChanges(filmId){
	data={}
	
	film={};
	let genres=[]
	let countries=[]
	let actors=[]
	let directors=[]
	let screenwriters=[]
	
	dataCollection(film,genres,countries,actors,directors,screenwriters);
	
	data["filmId"]=filmId
	data["film"]=film
	data["genres"]=genres
	data["countries"]=countries
	data["actors"]=actors
	data["directors"]=directors
	data["screenwriters"]=screenwriters
	
	$.ajax({
		type:"PUT",
		url:"http://localhost:8000/admin/films/update",
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

