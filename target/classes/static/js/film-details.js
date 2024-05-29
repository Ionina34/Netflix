if ($("#heart").length) {
	let filmId = $("#heart").attr('value');

	$.ajax({
		type: "GET",
		url: "http://localhost:8000/user/film/fav",
		data: {
			filmId: filmId
		},
		success: function(response) {
			if (response)
				$("#heart").css('background-color', 'rgb(185, 3, 3)')
		},
		error: function(e) {
			alert("ERROR: ", e.status);
		}
	})
}

//Такая функция существует в файле favourite-films.js, здесь поменялся только функция success
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
			var bg = $('#heart').css('background-color');
			$('#heart').css('background-color', setBackground(bg))
		},
		error: function(e) {
				alert("Error: ", e.status);
		}
	});
}

function setBackground(bg) {
	if (bg == 'rgb(185, 3, 3)') return '#B5B5B5'
	else return 'rgb(185, 3, 3)'
}