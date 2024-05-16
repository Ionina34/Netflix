var films = [];

$(document).ready(function() {
	$.ajax({
		type: "GET",
		url: "http://localhost:8000/user/films",
		success: function(response) {
			films = response
			ready()
		},
		error: function(e) {
			alert("ERROR: ", e.status);
		}
	})
})

function addAMovieToFavorites(filmId) {
	var getFilm = {};
	getFilm["filmId"] = filmId;

	$.ajax({
		type: "POST",
		url: "http://localhost:8000/user/films/add",
		contentType: "application/json",
		data: JSON.stringify(getFilm),
		dataType: 'json',
		cache: false,
		xhrFields: {
			withCredentials: true
		},
		success: function(response) {
			var bg = $('#heart_' + filmId).css('background-color');
			$('#heart_' + filmId).css('background-color', setBackground(bg))
		},
		error: function(e) {
			if (e.status == 302) {
				// Обработка ошибки 302
				alert("Redirected: ", e.status);
			}
		}
	});
}

function setBackground(bg) {
	if (bg == 'rgb(185, 3, 3)') return '#B5B5B5'
	else return 'rgb(185, 3, 3)'
}
