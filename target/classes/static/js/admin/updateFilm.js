//для изменения картинки 
$("#url").on('input',function(){
	$(".imageFilm").attr('src',$("#url").val())
})

//удаление жанра,страны и актера из соответствующих списков
function deleteInListGnenres(genreId){
	let genre=$("#selected_genre_"+genreId);
	
	let row='<option id="genre_'+genreId+'" value="'+genre.text()+'"></option>';
	$("#genres").append(row);
	
	$("#block_genre_"+genreId).remove();
}

function deleteInListCountries(coutnryId){
	let country=$("#selected_country_"+coutnryId);
	
	let row='<option id="country_'+coutnryId+'" value="'+country.text()+'"></option>';
	$("#counrties").append(row);
	
	$("#block_country_"+coutnryId).remove();
}

function deleteInListActors(actorId){
	let actor=$("#selected_actor_"+actorId);
	
	let row='<option id="actor_'+actorId+'" value="'+actor.text()+'"></option>';
	$("#actors").append(row);
	
	$("#block_actor_"+actorId).remove();
}

//добавление жанра, страны, актера в соответствующие списки
function addGenreInList(){
	let genre=$("#input_genres");
	
	if( genre.val()=="")return;
	
	var genreId = null;
	var options=document.getElementById("genres").getElementsByTagName('option');
	for(var i=0;i<options.length;i++){
		if(options[i].value===genre.val()){
			genreId= options[i].getAttribute('id').split('_')[1];
            break;
		}
	}
	
	let row='<div id="block_genre_'+genreId+'">'+
			'<label id="selected_genre_'+ genreId +'" class="text-dark">'+genre.val()+'</label>'+
			'<span id="selected_genre_remove_'+ genreId +'" onclick="deleteInListGnenres('+genreId +')" class="del"></span></div>';
	
	$(".genre").append(row)
	
	$("#genre_"+genreId).remove()
	genre.val("")
}

function addCountryInList(){
	let country=$("#input_countries");
	
	if( country.val()=="")return;
	
	var countryId = null;
	var options=document.getElementById("counrties").getElementsByTagName('option');
	for(var i=0;i<options.length;i++){
		if(options[i].value===country.val()){
			countryId= options[i].getAttribute('id').split('_')[1];
            break;
		}
	}
	
	let row='<div id="block_country_'+countryId+'">'+
			'<label id="selected_country_'+ countryId +'" class="text-dark">'+country.val()+'</label>'+
			'<span id="selected_country_remove_'+ countryId +'" onclick="deleteInListCountries('+countryId +')" class="del"></span></div>';
	
	$(".country").append(row)
	
	$("#country_"+countryId).remove()
	country.val("")
}

function addActorInList(){
	let actor=$("#input_actors");
	
	if( actor.val()=="")return;
	
	var actorId = null;
	var options=document.getElementById("actors").getElementsByTagName('option');
	for(var i=0;i<options.length;i++){
		if(options[i].value===actor.val()){
			actorId= options[i].getAttribute('id').split('_')[1];
            break;
		}
	}
	
	let row='<div id="block_actor_'+actorId+'">'+
			'<label id="selected_actor_'+ actorId +'" class="text-dark">'+actor.val()+'</label>'+
			'<span id="selected_actor_remove_'+ actorId +'" onclick="deleteInListActors('+actorId +')" class="del"></span></div>';
	
	$(".actor").append(row)
	
	$("#actor_"+actorId).remove()
	actor.val("")
}

function saveChanges(filmId){
	data={}
	
	film={};
	let genres=[]
	let countries=[]
	let actors=[]
	
	dataCollection(film,genres,countries,actors);
	
	data["filmId"]=filmId
	data["film"]=film
	data["genres"]=genres
	data["countries"]=countries
	data["actors"]=actors
	
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

//метод для сбора данных для отправки на сервер
function dataCollection(film,genres,countries,actors){
	film["name"]=$("#name").val()
	film["des"]=$("#des").val()
	film["release_date"]=$("#data_release").val()
	film["length"]=$("#length").val()
	film["image"]=$("#url").val()
	
	//получить все жанры фильма
	let select_genres=$(".genre").children("div")
	for(let i=0;i<select_genres.length;i++){
		let genre={}
		genre["name"]=select_genres[i].querySelector("label").textContent
		genres.push(genre)
	}
	
	let select_countries=$(".country").children("div")
	for(let i=0;i<select_countries.length;i++){
		let country={}
		country["name"]=select_countries[i].querySelector("label").textContent
		countries.push(country)
	}
	
	let select_actors=$(".actor").children("div")
	for(let i=0;i<select_actors.length;i++){
		let actor={}
		actor["name"]=select_actors[i].querySelector("label").textContent
		actors.push(actor)
	}
} 

