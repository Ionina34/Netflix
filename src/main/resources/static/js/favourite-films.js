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

function addAMovieToFavorites(film_p) {
	var film = {};
	film["film"] = film_p;

	$.ajax({
		type: "POST",
		url: "http://localhost:8000/user/films/add",
		contentType: "application/json",
		data:JSON.stringify(film),
		dataType: 'json',
		cache: false,
		xhrFields: {
			withCredentials: true
		},
		success: function(response) {
			console.log(film_p.id)
			var bg = $('#heart_' + film_p.id).css('background-color');
			$('#heart_' + film_p.id).css('background-color', setBackground(bg))
		},
		error: function(e) {
			$(location).attr('href',"http://localhost:8000/login")
		}
	});
}

function setBackground(bg) {
	if (bg == 'rgb(185, 3, 3)') return '#B5B5B5'
	else return 'rgb(185, 3, 3)'
}
