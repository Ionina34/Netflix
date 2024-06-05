let index_genre = 0;
let index_country = 0;
let index_director = 0;
let index_actor = 0;
let index_screenwriter = 0;
let index_film = 0;

//для изменения картинки 
$("#url").on('input', function() {
	$(".image").attr('src', $("#url").val())
})

//удаление жанра,страны и актера из соответствующих списков
function deleteInListGnenres(genreId) {
	let genre = $("#selected_genre_" + genreId);
	console.log(genreId)

	let row = '<option id="genre_' + genreId + '" value="' + genre.text() + '"></option>';
	$("#genres").append(row);

	$("#block_genre_" + genreId).remove();
}

function deleteInListCountries(coutnryId) {
	let country = $("#selected_country_" + coutnryId);

	let row = '<option id="country_' + coutnryId + '" value="' + country.text() + '"></option>';
	$("#counrties").append(row);

	$("#block_country_" + coutnryId).remove();
}

function deleteInListActors(actorId) {
	let actor = $("#selected_actor_" + actorId);

	let row = '<option id="actor_' + actorId + '" value="' + actor.text() + '"></option>';
	$("#actors").append(row);

	$("#block_actor_" + actorId).remove();
}

function deleteInListDirectors(directorId) {
	let director = $("#selected_director_" + directorId);

	let row = '<option id="director_' + directorId + '" value="' + director.text() + '"></option>';
	$("#directors").append(row);

	$("#block_director_" + directorId).remove();
}

function deleteInListScreenwriters(screenwriterId) {
	let screenwriter = $("#selected_screenwriter_" + screenwriterId);

	let row = '<option id="screenwriter_' + screenwriterId + '" value="' + screenwriter.text() + '"></option>';
	$("#screenwriters").append(row);

	$("#block_screenwriter_" + screenwriterId).remove();
}

function deleteInListFilms(filmId) {
	let film = $("#selected_film_" + filmId);

	let row = '<option id="film_' + filmId + '" value="' + film.text() + '"></option>';
	$("#films").append(row);

	$("#block_film_" + filmId).remove();
}

//добавление жанра, страны, актера в соответствующие списки
function addGenreInList() {
	let genre = $("#input_genres");

	if (genre.val() == "") return;

	var genreId = null;
	var options = document.getElementById("genres").getElementsByTagName('option');
	for (var i = 0; i < options.length; i++) {
		if (options[i].value === genre.val()) {
			genreId = options[i].getAttribute('id').split('_')[1];
			break;
		}
	}

	if (genreId == null) {
		index_genre++;
		genreId = index_genre * (-1)
	}

	let row = '<div id="block_genre_' + genreId + '">' +
		'<label id="selected_genre_' + genreId + '" class="text-dark">' + genre.val() + '</label>' +
		'<span id="selected_genre_remove_' + genreId + '" onclick="deleteInListGnenres(' + genreId + ')" class="del"></span></div>';

	$(".genre").append(row)

	$("#genre_" + genreId).remove()
	genre.val("")
}

function addCountryInList() {
	let country = $("#input_countries");

	if (country.val() == "") return;

	var countryId = null;
	var options = document.getElementById("counrties").getElementsByTagName('option');
	for (var i = 0; i < options.length; i++) {
		if (options[i].value === country.val()) {
			countryId = options[i].getAttribute('id').split('_')[1];
			break;
		}
	}

	if (countryId == null) {
		index_country++
		countryId = index_country * (-1)
	}

	let row = '<div id="block_country_' + countryId + '">' +
		'<label id="selected_country_' + countryId + '" class="text-dark">' + country.val() + '</label>' +
		'<span id="selected_country_remove_' + countryId + '" onclick="deleteInListCountries(' + countryId + ')" class="del"></span></div>';

	$(".country").append(row)

	$("#country_" + countryId).remove()
	country.val("")
}

function addActorInList() {
	let actor = $("#input_actors");

	if (actor.val() == "") return;

	var actorId = null;
	var options = document.getElementById("actors").getElementsByTagName('option');
	for (var i = 0; i < options.length; i++) {
		if (options[i].value === actor.val()) {
			actorId = options[i].getAttribute('id').split('_')[1];
			break;
		}
	}

	if (actorId === null) {
		index_actor++
		actorId = index_actor * (-1)
	}

	let row = '<div id="block_actor_' + actorId + '">' +
		'<label id="selected_actor_' + actorId + '" class="text-dark">' + actor.val() + '</label>' +
		'<span id="selected_actor_remove_' + actorId + '" onclick="deleteInListActors(' + actorId + ')" class="del"></span></div>';

	$(".actor").append(row)

	$("#actor_" + actorId).remove()
	actor.val("")
}

function addDirectorInList() {
	let director = $("#input_directors");

	if (director.val() == "") return;

	var directorId = null;
	var options = document.getElementById("directors").getElementsByTagName('option');
	for (var i = 0; i < options.length; i++) {
		if (options[i].value === director.val()) {
			directorId = options[i].getAttribute('id').split('_')[1];
			break;
		}
	}

	if (directorId == null) {
		index_director
		directorId = index_director * (-1)
	}

	let row = '<div id="block_director_' + directorId + '">' +
		'<label id="selected_director_' + directorId + '" class="text-dark">' + director.val() + '</label>' +
		'<span id="selected_director_remove_' + directorId + '" onclick="deleteInListDirector(' + directorId + ')" class="del"></span></div>';

	$(".director").append(row)

	$("#director_" + directorId).remove()
	director.val("")
}

function addScreenwriterInList() {
	let screenwriter = $("#input_screenwriters");

	if (screenwriter.val() == "") return;

	var screenwriterId = null;
	var options = document.getElementById("screenwriters").getElementsByTagName('option');
	for (var i = 0; i < options.length; i++) {
		if (options[i].value === screenwriter.val()) {
			screenwriterId = options[i].getAttribute('id').split('_')[1];
			break;
		}
	}

	if (screenwriterId == null) {
		index_screenwriter++
		screenwriterId = index_screenwriter * (-1)
	}

	let row = '<div id="block_screenwriter_' + screenwriterId + '">' +
		'<label id="selected_screenwriter_' + screenwriterId + '" class="text-dark">' + screenwriter.val() + '</label>' +
		'<span id="selected_screenwriter_remove_' + screenwriterId + '" onclick="deleteInListScreenwriter(' + screenwriterId + ')" class="del"></span></div>';

	$(".screenwriter").append(row)

	$("#screenwriter_" + screenwriterId).remove()
	screenwriter.val("")
}

function addFilmInList() {
	let film = $("#input_films");

	if (film.val() == "") return;

	var filmId = null;
	var options = document.getElementById("films").getElementsByTagName('option');
	for (var i = 0; i < options.length; i++) {
		if (options[i].value === film.val()) {
			filmId = options[i].getAttribute('id').split('_')[1];
			break;
		}
	}

	if (filmId == null) {
		index_film++
		filmId = index_film * (-1)
	}

	let row = '<div class="d-flex align-items-center" id="block_film_' + filmId + '" style="width: 230px;">' +
		'<label id="selected_film_' + filmId + '" class="text-dark">' + film.val() + '</label>' +
		'<span id="selected_film_remove_' + filmId + '" onclick="deleteInListFilms(' + filmId + ')" class="del"></span></div>';

	$(".film").append(row)

	$("#film_" + filmId).remove()
	film.val("")
}
