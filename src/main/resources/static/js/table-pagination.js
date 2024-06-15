let totalPages;
let page_films = false;
let page_genre = false;
let page_country = false;
let page_sort = false;

function search_films_by_parameters_sort(data_value, alphabet_value, selectedGenre, selectedCountry, startPage) {
	$.ajax({
		type: "GET",
		url: "http://localhost:8000/films/sort",
		data: {
			data: data_value,
			alphabet: alphabet_value,
			genre: selectedGenre,
			country: selectedCountry,
			page: startPage,
			size: 12
		},
		success: function(response) {
			page_sort = true;
			viewFilms(response);
		},
		error: function(e) {
			alert("Error: ", e.status);
		}
	});
}

function viewFilms(response) {
	$('#films').empty();
	$.each(response.content, function(i, film) {
		let isChecked = false;
		if (films[0] != '<') {
			isChecked = films.some(function(f) {
				return JSON.stringify(f) === JSON.stringify(film)
			})
		}
		let noteRow = '<div>' +
			'<div class="card m-1 bg-dark rounded d-flex" style="width: 18rem">' +
			'<div id="heart_' + film.id + '" class="heart ms-auto" style="background-color:' + getColor(isChecked) + '" onclick="addAMovieToFavorites(' + JSON.stringify(film).replace(/"/g, "'") + ')"></div>' +
			'<div class="card-body">' +
			'<a href="/films/' + film.id + '">' +
			'<img style="height: 400px" src="../images/' + film.image + '"' +
			'class="card-img-top" alt="..."></a>' +
			'<a href="/films/' + film.id + '"><h4 class="card-title">' + film.name + '</h4></a>' +
			'<h5 class="fs-6">' + moment(film.release_date).format('DD-MM-YYYY') + '</h5>' +
			'<div  style="height: 150px"' +
			'class="overflow-auto">' + film.des + '</div>' +
			'</div></div></div>';
		$('#films').append(noteRow);

	});
	$('ul.pagination').empty();
	buildPagination(response);
}


function getColor(isChecked) {
	return isChecked ? 'rgb(185, 3, 3)' : '#B5B5B5';
}

function ready() {

	function fetch_films(startPage) {
		localStorage.removeItem('data_value')
		localStorage.removeItem('alphabet_value')
		localStorage.removeItem('selectedGenre')
		localStorage.removeItem('selectedCountry')

		$.ajax({
			type: "GET",
			url: "http://localhost:8000/films/all/get",
			data: {
				page: startPage,
				size: 12
			},
			xhrFields: {
				withCredentials: true
			},
			success: function(response) {
				viewFilms(response);
			},
			error: function(e) {
				$(location).attr('href', "http://localhost:8000/login")
			}
		});
	}

	function search_films(filter, startPage) {
		$.ajax({
			type: "GET",
			url: "http://localhost:8000/films/search",
			data: {
				filter: filter,
				page: startPage,
				size: 12
			},
			success: function(response) {
				page_films = true;
				viewFilms(response);
			},
			error: function(e) {
				alert("ERROR: ", e.status);
			}
		});
	}

	function search_films_by_genre(genre, startPage) {
		$.ajax({
			type: "GET",
			url: "http://localhost:8000/films/search/genre",
			data: {
				genre: genre,
				page: startPage,
				size: 12
			},
			success: function(response) {
				page_genre = true;
				viewFilms(response);
			},
			error: function(e) {
				alert("ERROR: ", e.status);
			}
		})
	}

	function search_films_by_country(country, startPage) {
		$.ajax({
			type: "GET",
			url: "http://localhost:8000/films/search/country",
			data: {
				country: country,
				page: startPage,
				size: 12
			},
			success: function(response) {
				page_country = true;
				viewFilms(response);
			},
			error: function(e) {
				alert("ERROR: ", e.status);
			}
		})
	}

	$(document).on("click", "ul.pagination li a", function() {
		var data = $(this).attr('data');
		var filter = $("#search-control").val()
		let val = $(this).text();
		let data_value = localStorage.getItem('data_value')
		let alphabet_value = localStorage.getItem('alphabet_value')
		let selectedGenre = localStorage.getItem('selectedGenre')
		let selectedCountry = localStorage.getItem('selectedCountry')

		// click on the NEXT tag
		if (val.toUpperCase() === "« FIRST") {
			let currentActive = $("li.active");
			if (page_films)
				search_films(filter, 0)
			else if (page_genre)
				search_films_by_genre(filter, 0)
			else if (page_country)
				search_films_by_country(filter, 0)
			else if (page_sort)
				search_films_by_parameters_sort(data_value, alphabet_value, selectedGenre, selectedCountry, 0)
			else
				fetch_films(0);
			localStorage.setItem('page', 0)
			$("li.active").removeClass("active");
			// add .active to next-pagination li
			currentActive.next().addClass("active");
		} else if (val.toUpperCase() === "LAST »") {
			let currentActive = $("li.active");
			if (page_films)
				search_films(filter, totalPages - 1)
			else if (page_genre)
				search_films_by_genre(filter, totalPages - 1)
			else if (page_country)
				search_films_by_country(filter, totalPages - 1)
			else if (page_sort)
				search_films_by_parameters_sort(data_value, alphabet_value, selectedGenre, selectedCountry, totalPages - 1)
			else
				fetch_films(totalPages - 1);
			localStorage.setItem('page', totalPages - 1)
			$("li.active").removeClass("active");
			// add .active to next-pagination li
			currentActive.next().addClass("active");
		} else if (val.toUpperCase() === "NEXT ›") {
			let activeValue = parseInt($("ul.pagination li.active").text());
			if (activeValue < totalPages) {
				let currentActive = $("li.active");
				startPage = activeValue;
				if (page_films)
					search_films(filter, startPage)
				else if (page_genre)
					search_films_by_genre(filter, startPage)
				else if (page_country)
					search_films_by_country(filter, startPage)
				else if (page_sort)
					search_films_by_parameters_sort(data_value, alphabet_value, selectedGenre, selectedCountry, startPage)
				else
					fetch_films(startPage);
				localStorage.setItem('page', startPage)
				// remove .active class for the old li tag
				$("li.active").removeClass("active");
				// add .active to next-pagination li
				currentActive.next().addClass("active");
			}
		} else if (val.toUpperCase() === "‹ PREV") {
			let activeValue = parseInt($("ul.pagination li.active").text());
			if (activeValue > 1) {
				// get the previous page
				startPage = activeValue - 2;
				if (page_films)
					search_films(filter, startPage)
				else if (page_genre)
					search_films_by_genre(filter, startPage)
				else if (page_country)
					search_films_by_country(filter, startPage)
				else if (page_sort)
					search_films_by_parameters_sort(data_value, alphabet_value, selectedGenre, selectedCountry, startPage)
				else
					fetch_films(startPage);
				localStorage.setItem('page', startPage)
				let currentActive = $("li.active");
				currentActive.removeClass("active");
				// add .active to previous-pagination li
				currentActive.prev().addClass("active");
			}
		} else {
			startPage = parseInt(val - 1);
			if (page_films)
				search_films(filter, startPage)
			else if (page_genre)
				search_films_by_genre(filter, startPage)
			else if (page_country)
				search_films_by_country(filter, startPage)
			else if (page_sort)
				search_films_by_parameters_sort(data_value, alphabet_value, selectedGenre, selectedCountry, startPage)
			else
				fetch_films(startPage);
			localStorage.setItem('page', startPage)
			// add focus to the li tag
			$("li.active").removeClass("active");
			$(this).parent().addClass("active");
			//$(this).addClass("active");
		}
	});


	var loading = true;
	if (localStorage.getItem('filter') !== null) {
		$("#search-control").val(localStorage.getItem('filter'));
		search_films(localStorage.getItem('filter'), 0);
		loading = false;
		localStorage.removeItem("filter");
	}
	else if (localStorage.getItem('genre') !== null) {
		$("#search-control").val(localStorage.getItem('genre'));
		search_films_by_genre(localStorage.getItem('genre'), 0);
		loading = false;
		localStorage.removeItem('genre');
	}
	else if (localStorage.getItem('country') !== null) {
		$("#search-control").val(localStorage.getItem('country'));
		search_films_by_country(localStorage.getItem('country'), 0);
		loading = false;
		localStorage.removeItem('country');
	}

	if (loading)
		(function() {
			// get first-page at initial time
			let page = localStorage.getItem('page')
			console.log(page)
			if (performance.navigation.type !== performance.navigation.TYPE_RELOAD
				&& page !== null) {
				let data_value = localStorage.getItem('data_value')
				let alphabet_value = localStorage.getItem('alphabet_value')
				let selectedGenre = localStorage.getItem('selectedGenre')
				let selectedCountry = localStorage.getItem('selectedCountry')
				if (data_value !== null || alphabet_value !== null || selectedGenre !== null || selectedCountry !== null)
					search_films_by_parameters_sort(data_value, alphabet_value, selectedGenre, selectedCountry, page)
				else
					fetch_films(page);
			}
			else
				fetch_films(0);
		})();


	$("#search-form").submit(function(event) {
		event.preventDefault();
		search_films($("#search-control").val(), 0);
	});

};