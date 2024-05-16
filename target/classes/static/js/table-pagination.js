function ready() {
	console.log("ss")
	let totalPages = 1;

	let page_films = false;
	let page_genre = false;
	let page_country = false;

	function fetch_films(startPage) {
		$.ajax({
			type: "GET",
			url: "http://localhost:8000/films/all/get",
			data: {
				page: startPage,
				size: 12
			},
			success: function(response) {
				viewFilms(response);
			},
			error: function(e) {
				alert("ERROR: ", e.status);
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

	function buildPagination(response) {
		totalPages = response.totalPages;

		var pageNumber = response.pageable.pageNumber;

		var numLinks = 5;

		// print 'previous' link only if not on page one
		var first = '';
		var prev = '';
		if (pageNumber > 0) {
			if (pageNumber !== 0) {
				first = '<li class="page-item"><a class="page-link">« First</a></li>';
			}
			prev = '<li class="page-item"><a class="page-link">‹ Prev</a></li>';
		} else {
			prev = ''; // on the page one, don't show 'previous' link
			first = ''; // nor 'first page' link
		}

		// print 'next' link only if not on the last page
		var next = '';
		var last = '';
		if (pageNumber < totalPages) {
			if (pageNumber !== totalPages - 1) {
				next = '<li class="page-item"><a class="page-link">Next ›</a></li>';
				last = '<li class="page-item"><a class="page-link">Last »</a></li>';
			}
		} else {
			next = ''; // on the last page, don't show 'next' link
			last = ''; // nor 'last page' link
		}

		var start = pageNumber - (pageNumber % numLinks) + 1;
		var end = start + numLinks - 1;
		end = Math.min(totalPages, end);
		var pagingLink = '';

		for (var i = start; i <= end; i++) {
			if (i == pageNumber + 1) {
				pagingLink += '<li class="page-item active"><a class="page-link"> ' + i + ' </a></li>'; // no need to create a link to current page
			} else {
				pagingLink += '<li class="page-item"><a class="page-link"> ' + i + ' </a></li>';
			}
		}

		// return the page navigation link
		pagingLink = first + prev + pagingLink + next + last;

		$("ul.pagination").append(pagingLink);
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
				'<div id="heart_' + film.id + '" class="heart ms-auto" style="background-color:' + getColor(isChecked) + '" onclick="addAMovieToFavorites(' + film.id + ')"></div>' +
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

	$(document).on("click", "ul.pagination li a", function() {
		var data = $(this).attr('data');
		var filter = $("#search-control").val()
		let val = $(this).text();
		console.log('val: ' + val);

		// click on the NEXT tag
		if (val.toUpperCase() === "« FIRST") {
			let currentActive = $("li.active");
			if (page_films)
				search_films(filter, 0)
			else if (page_genre)
				search_films_by_genre(filter, 0)
			else if (page_country)
				search_films_by_country(filter, 0)
			else
				fetch_films(0);
			$("li.active").removeClass("active");
			// add .active to next-pagination li
			currentActive.next().addClass("active");
		} else if (val.toUpperCase() === "LAST »") {
			if (page_films)
				search_films(filter, totalPages - 1)
			else if (page_genre)
				search_films_by_genre(filter, totalPages - 1)
			else if (page_country)
				search_films_by_country(filter, totalPages - 1)
			else
				fetch_films(totalPages - 1);
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
				else
					fetch_films(startPage);
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
				else
					fetch_films(startPage);
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
			else
				fetch_films(startPage);
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
			fetch_films(0);
		})();


	$("#search-form").submit(function(event) {
		event.preventDefault();
		search_films($("#search-control").val(), 0);
	});

};

