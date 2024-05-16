$(document).ready(function() {
	let totalPages = 1;

	function search_films(filter) {
		$(location).attr('href', 'http://localhost:8000/films/all');
		localStorage.setItem('filter', filter);
	}

	$("#search-form").submit(function(event) {
		event.preventDefault();
		search_films($("#search-control").val());
	});
})