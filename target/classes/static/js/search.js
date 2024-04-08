$(document).ready(function() {
	let totalPages = 1;

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
				$(location).attr('href','http://localhost:8000/films/all');
				localStorage.setItem('filter',filter);
			},
			error: function(e) {
				alert("ERROR: ", e);
				console.log("ERROR: ", e);
			}
		});
	}
	
	$("#search-form").submit(function(event) {
		event.preventDefault();
		search_films($("#search-control").val(), 0);
	});
})