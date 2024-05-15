var films=[];

$(document).ready(function(){
	$.ajax({
		type:"GET",
		url:"http://localhost:8000/user/films",
		success:function(response){
			films=response
			ready()
		},
		error: function(e) {
			alert("ERROR: ", e);
			console.log("ERROR: ", e);
		}
	})
})

function addAMovieToFavorites(filmId) {
	var getFilm={};
	getFilm["filmId"]=filmId;
	console.log(filmId);
	
	$.ajax({
		type: "POST",
		url: "http://localhost:8000/user/films/add",
		contentType: "application/json",
		data: JSON.stringify(getFilm),
		dataType: 'json',
		cache: false,
		success: function(response) {
			var bg=$('#heart_'+filmId).css('background-color');
			$('#heart_'+filmId).css('background-color',setBackground(bg))
		},
		error: function(e) {
			alert("ERROR: ", e);
			console.log("ERROR: ", e);
		}
	});
}

function setBackground(bg){
	if(bg=='rgb(185, 3, 3)') return '#B5B5B5'
	else return 'rgb(185, 3, 3)'
}
