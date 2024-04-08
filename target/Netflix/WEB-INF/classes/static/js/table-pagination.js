$(document).ready(function() {
	let totalPages = 1;

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
				alert("ERROR: ", e);
				console.log("ERROR: ", e);
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
			let noteRow = '<div>' +
				'<div class="card m-1" style="width: 18rem">' +
				'<img style="height: 400px" src="../' + film.image + '"' +
				'class="card-img-top" alt="...">' +
				'<div class="card-body">' +
				'<h5 class="card-title"' + film.name + '</h5>' +
				'<h5 class="card-title">' + moment(film.release_date).format('DD-MM-YYYY') + '</h5>' +
				'<div  style="height: 150px"' +
				'class="overflow-auto">' + film.des + '</div>' +
				'</div></div></div>';
			$('#films').append(noteRow);
		});

		$('ul.pagination').empty();
		buildPagination(response);
	}

	$(document).on("click", "ul.pagination li a", function() {
		var data = $(this).attr('data');
		var filter = $("#search-control").val()
		let val = $(this).text();
		console.log('val: ' + val);

		// click on the NEXT tag
		if (val.toUpperCase() === "« FIRST") {
			let currentActive = $("li.active");
			if(filter!=null)
				search_films(filter,0)
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
		fetch_films(0);
	})();

	$("#search-form").submit(function(event) {
		event.preventDefault();
		search_films($("#search-control").val(), 0);
	});

});

