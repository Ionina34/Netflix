function removeFavFilm(film_p) {
	var film = {};
	film["film"] = film_p;

	$.ajax({
		type: "POST",
		url: "http://localhost:8000/user/films/remove",
		data:JSON.stringify(film),
		contentType: "application/json",
		dataType: 'json',
		cache: false,
		success: function(responce) {
			$("#film_" +film_p.id).empty();

		},
		error: function(e) {
			alert("ERROR: ", e);
			console.log("ERROR: ", e);
		}
	})
}

function estimate(film, value) {
	var info = {};
	info["film"] = film;
	info["rating"] = value;

	$.ajax({
		type: "POST",
		url: "http://localhost:8000/user/film/rating/add",
		data: JSON.stringify(info),
		contentType: "application/json",
		dataType: 'json',
		cache: false,
		success: function(responce) {
			let row = "<p>Спасибо за вашу оценку!</p>";
			$("#ratingGroup-" + film.id).replaceWith(row);
		},
		error: function(e) {
			alert("ERROR: ", e);
			console.log("ERROR: ", e);
		}
	})
}

$(document).ready(function() {
	let totalPages = 1;
	var films = [];

	function getRatedFilmsUser() {
		$.ajax({
			type: "GET",
			url: "http://localhost:8000/user/films/rated",
			success: function(response) {
				films = response;
				fetch_films(0);
			},
			error: function(e) {
				alert("ERROR: ", e);
				console.log("ERROR: ", e);
			}
		})
	}

	function fetch_films(startPage) {
		$.ajax({
			type: "GET",
			url: "http://localhost:8000/user/films/fav",
			data: {
				page: startPage,
				size: 12
			},
			success: function(response) {
				viewFilms(response);
			},
			error: function(e) {
				alert("ERROR: ", e);
				console.log("ERROR: ", e);
			}
		});
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
			let isChecked = films.some(function(f) {
				return JSON.stringify(f) === JSON.stringify(film)
			})

			let noteRow = '<div id="film_' + film.id + '" class="film">' +
				'<div class="card m-1 bg-dark rounded d-flex" style="width: 18rem;height:600px">' +
				'<div id="heart_' + film.id + '" class="heart ms-auto" style="background-color:rgb(185, 3, 3)" onclick="removeFavFilm(' + JSON.stringify(film).replace(/"/g, "'") + ')"></div>' +
				'<div class="card-body">' +
				'<a href="/films/' + film.id + '">' +
				'<img style="height: 400px" src="../images/' + film.image + '"' +
				'class="card-img-top" alt="..."></a>' +
				'<a href="/films/' + film.id + '"><h4 class="card-title">' + film.name + '</h4></a>' +
				'<div id="stars-' + film.id + '" class="full-stars">' +
				'<div id="ratingGroup-' + film.id + '" class="rating-group">' + checkRating(isChecked,film)+'</div>' +
				'</div></div></div>';
			$('#films').append(noteRow);

		});
		$('ul.pagination').empty();
		buildPagination(response);
	}

	function checkRating(isChecked,film) {
		return isChecked ? '<p>Спасибо за вашу оценку!</p>' :

			'   <!-- по умолчанию 0 -->' +
			'  <input name="fst' + film.id + '" value="0" type="radio" disabled checked />' +

			'   <!-- рейтинг 1 -->    ' +
			'   <label for="fst' + film.id + '-1">' +
			'       <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 576 512"><path d="M259.3 17.8L194 150.2 47.9 171.5c-26.2 3.8-36.7 36.1-17.7 54.6l105.7 103-25 145.5c-4.5 26.3 23.2 46 46.4 33.7L288 439.6l130.7 68.7c23.2 12.2 50.9-7.4 46.4-33.7l-25-145.5 105.7-103c19-18.5 8.5-50.8-17.7-54.6L382 150.2 316.7 17.8c-11.7-23.6-45.6-23.9-57.4 0z"/></svg>' +
			'   </label>' +
			'   <input name="fst' + film.id + '" id="fst' + film.id + '-1" value="1" type="radio" onClick="estimate(' + JSON.stringify(film).replace(/"/g, "'") + ', 1)" />' +

			'  <!-- рейтинг 2 -->    ' +
			'  <label for="fst' + film.id + '-2">' +
			'      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 576 512"><path d="M259.3 17.8L194 150.2 47.9 171.5c-26.2 3.8-36.7 36.1-17.7 54.6l105.7 103-25 145.5c-4.5 26.3 23.2 46 46.4 33.7L288 439.6l130.7 68.7c23.2 12.2 50.9-7.4 46.4-33.7l-25-145.5 105.7-103c19-18.5 8.5-50.8-17.7-54.6L382 150.2 316.7 17.8c-11.7-23.6-45.6-23.9-57.4 0z"/></svg>' +
			'  </label>' +
			'  <input name="fst' + film.id + '" id="fst' + film.id + '-2" value="2" type="radio" onClick="estimate(' + JSON.stringify(film).replace(/"/g, "'") + ', 2)"/>' +

			'  <!-- рейтинг 3 -->    ' +
			'  <label for="fst' + film.id + '-3">' +
			'      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 576 512"><path d="M259.3 17.8L194 150.2 47.9 171.5c-26.2 3.8-36.7 36.1-17.7 54.6l105.7 103-25 145.5c-4.5 26.3 23.2 46 46.4 33.7L288 439.6l130.7 68.7c23.2 12.2 50.9-7.4 46.4-33.7l-25-145.5 105.7-103c19-18.5 8.5-50.8-17.7-54.6L382 150.2 316.7 17.8c-11.7-23.6-45.6-23.9-57.4 0z"/></svg>' +
			'   </label>' +
			'    <input name="fst' + film.id + '" id="fst' + film.id + '-3" value="3" type="radio" onClick="estimate(' + JSON.stringify(film).replace(/"/g, "'") + ', 3)"/>' +

			'    <!-- рейтинг 4 -->        ' +
			'     <label for="fst' + film.id + '-4">' +
			'       <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 576 512"><path d="M259.3 17.8L194 150.2 47.9 171.5c-26.2 3.8-36.7 36.1-17.7 54.6l105.7 103-25 145.5c-4.5 26.3 23.2 46 46.4 33.7L288 439.6l130.7 68.7c23.2 12.2 50.9-7.4 46.4-33.7l-25-145.5 105.7-103c19-18.5 8.5-50.8-17.7-54.6L382 150.2 316.7 17.8c-11.7-23.6-45.6-23.9-57.4 0z"/></svg>' +
			'     </label>' +
			'     <input name="fst' + film.id + '" id="fst' + film.id + '-4" value="4" type="radio"onClick="estimate(' + JSON.stringify(film).replace(/"/g, "'") + ', 4)"/>' +

			'     <!-- рейтинг 5 -->    ' +
			'     <label for="fst' + film.id + '-5">' +
			'         <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 576 512"><path d="M259.3 17.8L194 150.2 47.9 171.5c-26.2 3.8-36.7 36.1-17.7 54.6l105.7 103-25 145.5c-4.5 26.3 23.2 46 46.4 33.7L288 439.6l130.7 68.7c23.2 12.2 50.9-7.4 46.4-33.7l-25-145.5 105.7-103c19-18.5 8.5-50.8-17.7-54.6L382 150.2 316.7 17.8c-11.7-23.6-45.6-23.9-57.4 0z"/></svg>' +
			'     </label>' +
			'     <input name="fst' + film.id + '" id="fst' + film.id + '-5" value="5" type="radio" onClick="estimate(' + JSON.stringify(film).replace(/"/g, "'") + ', 5)"/>' +
			' </input>';
	}

	$(document).on("click", "ul.pagination li a", function() {
		var data = $(this).attr('data');
		var filter = $("#search-control").val()
		let val = $(this).text();
		console.log('val: ' + val);

		// click on the NEXT tag
		if (val.toUpperCase() === "« FIRST") {
			let currentActive = $("li.active");
			if (filter != null)
				search_films(filter, 0)
			else
				fetch_films(0);
			$("li.active").removeClass("active");
			// add .active to next-pagination li
			currentActive.next().addClass("active");
		} else if (val.toUpperCase() === "LAST »") {
			fetch_films(totalPages - 1);
			$("li.active").removeClass("active");
			// add .active to next-pagination li
			currentActive.next().addClass("active");
		} else if (val.toUpperCase() === "NEXT ›") {
			let activeValue = parseInt($("ul.pagination li.active").text());
			if (activeValue < totalPages) {
				let currentActive = $("li.active");
				startPage = activeValue;
				if (filter != null)
					search_films(filter, startPage)
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
				if (filter != null)
					search_films(filter, startPage)
				else
					fetch_films(startPage);
				let currentActive = $("li.active");
				currentActive.removeClass("active");
				// add .active to previous-pagination li
				currentActive.prev().addClass("active");
			}
		} else {
			startPage = parseInt(val - 1);
			if (filter != null)
				search_films(filter, startPage)
			else
				fetch_films(startPage);
			// add focus to the li tag
			$("li.active").removeClass("active");
			$(this).parent().addClass("active");
			//$(this).addClass("active");
		}
	});

	(function() {
		// get first-page at initial time
		getRatedFilmsUser();
	})();
})