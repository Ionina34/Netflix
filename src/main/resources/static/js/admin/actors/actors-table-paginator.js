let page_sort;

function search_staff_by_parameters_sort(data_value, alphabet_value, created_value, startPage) {
	$.ajax({
		type: "GET",
		url: "http://localhost:8000/admin/actors/sort",
		data: {
			data: data_value,
			alphabet: alphabet_value,
			created: created_value,
			page: startPage,
			size: 12
		},
		success: function(response) {
			console.log(response)
			page_sort = true;
			viewActors(response);
		},
		error: function(e) {
			alert("Error: ", e.status);
		}
	});
}

function deleteActor(actorId){
	data={}
	data["id"]=actorId
	
	$.ajax({
		type: "DELETE",
		url: "http://localhost:8000/admin/actors/delete/"+actorId,
		data:JSON.stringify(data),
		success: function() {
			$(location).attr('href',"http://localhost:8000/admin/actors")
		},
		error: function(e) {
			alert("Error: ", e.status);
		}
	});
}

function viewActors(response) {
	var pageNumber = response.pageable.pageNumber;
	var index = 12*pageNumber+1;
	
	$("#actors").empty();
	let row = '<table style="border: 1px solid rgb(0, 0, 0);">' +
		'<thead><tr>' +
		'<th style="border: 1px solid rgb(0, 0, 0);"></th>' +
		'<th style="border: 1px solid rgb(0, 0, 0);">Имя</th>' +
		'<th style="border: 1px solid rgb(0, 0, 0);">Краткая биография</th>' +
		'<th style="border: 1px solid rgb(0, 0, 0);">Дата рождения</th>' +
		'</tr></thead><tbody>';
	$.each(response.content, function(i, actor) {
		let rowActor= '<tr id="'+actor.id+'" >' +
			'<td style="border: 1px solid rgb(0, 0, 0);">' + index + '</td>' +
			'<td style="border: 1px solid rgb(0, 0, 0);">' + actor.name + '</td>' +
			'<td style="border: 1px solid rgb(0, 0, 0);">' + actor.brief_biography + '</td>' +
			'<td style="border: 1px solid rgb(0, 0, 0);">' + moment(actor.birthday).format('DD-MM-YYYY') + '</td>' +
			'<td style="border: 1px solid rgb(0, 0, 0);"><button class="btn btn-warning" '+
			'><a href="/admin/actors/update/'+ actor.id +'">Редактировать</a></button></td>' +
			'<td style="border: 1px solid rgb(0, 0, 0);"><button class="btn btn-danger" onclick="deleteActor('+actor.id+')">Удалить</button></td>' +
			'</tr>';
		row += rowActor;
		index++;
	});
	row += '</tbody></table>';
	$("#actors").append(row)

	$('ul.pagination').empty();
	buildPagination(response);
}

$(document).ready(function() {
	function fetch_actors(startPage) {
		$.ajax({
			type: "GET",
			url: "http://localhost:8000/admin/actors/get",
			data: {
				page: 29,
				size: 12
			},
			xhrFields: {
				withCredentials: true
			},
			success: function(response) {
				viewActors(response);
			},
			error: function(e) {
				$(location).attr('href', "http://localhost:8000/login")
			}
		});
	}

	$(document).on("click", "ul.pagination li a", function() {
		var data = $(this).attr('data');
		let val = $(this).text();
		let data_value = localStorage.getItem('data_value')
		let alphabet_value = localStorage.getItem('alphabet_value')
		let created_value = localStorage.getItem('created_value')

		// click on the NEXT tag
		if (val.toUpperCase() === "« FIRST") {
			let currentActive = $("li.active");
			if (page_sort)
				search_staff_by_parameters_sort(data_value, alphabet_value, created_value, 0)
			else
				fetch_actors(0);
			$("li.active").removeClass("active");
			// add .active to next-pagination li
			currentActive.next().addClass("active");
		} else if (val.toUpperCase() === "LAST »") {
			let currentActive = $("li.active");
			if (page_sort)
				search_staff_by_parameters_sort(data_value, alphabet_value, created_value, totalPages - 1)
			else
				fetch_actors(totalPages - 1);
			$("li.active").removeClass("active");
			// add .active to next-pagination li
			currentActive.next().addClass("active");
		} else if (val.toUpperCase() === "NEXT ›") {
			let activeValue = parseInt($("ul.pagination li.active").text());
			if (activeValue < totalPages) {
				let currentActive = $("li.active");
				startPage = activeValue;
				if (page_sort)
					search_staff_by_parameters_sort(data_value, alphabet_value, created_value, startPage)
				else
					fetch_actors(startPage);
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
				if (page_sort)
					search_staff_by_parameters_sort(data_value, alphabet_value, created_value, startPage)
				else
					fetch_actors(startPage);
				let currentActive = $("li.active");
				currentActive.removeClass("active");
				// add .active to previous-pagination li
				currentActive.prev().addClass("active");
			}
		} else {
			startPage = parseInt(val - 1);
			if (page_sort)
				search_staff_by_parameters_sort(data_value, alphabet_value, created_value, startPage)
			else
				fetch_actors(startPage);
			// add focus to the li tag
			$("li.active").removeClass("active");
			$(this).parent().addClass("active");
			//$(this).addClass("active");
		}
	});


	(function() {
		// get first-page at initial time
		fetch_actors(0);
	})();


})