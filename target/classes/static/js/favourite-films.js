function addAMovieToFavorites(filmId) {
	var getFilm={};
	getFilm["filmId"]=filmId;
	
	$.ajax({
		type: "POST",
		url: "http://localhost:8000/user/films/add",
		contentType: "application/json",
		data: JSON.stringify(getFilm),
		dataType: 'json',
		cache: false,
		succes: function(response) {

		},
		error: function(e) {
			alert("ERROR: ", e);
			console.log("ERROR: ", e);
		}
	});
}
