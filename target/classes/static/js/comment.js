function submit(filmId) {
	let comment = $("#printComment").val()

	if (comment !== "") {
		var stars = $(".rating-group").children()
		var rating = "";
		for (var i = 0; i < stars.length; i++) {
			if (stars[i].checked) {
				rating = stars[i].value;
			}
		}

		let data = {}
		data["filmId"] = filmId
		data["comment"] = comment
		data["rating"] = rating

		$.ajax({
			type: "POST",
			url: "http://localhost:8000/user/film/comment",
			data: JSON.stringify(data),
			contentType: "application/json",
			dataType: "json",
			success: function(responce) {
				console.log(responce)
				addComment(responce);
			},
			error: function(e) {
				$(location).attr('href', "http://localhost:8000/login")
			}
		})
	}
}

function addComment(responce) {
	let row = '<div class="card mb-4">' +
		'<div class="div-background card-body bg-light">' +
		'<div class="w-100 d-flex flex-start align-items-center">' +
		'<img class="rounded-circle shadow-1-strong me-3" src="../logo/avatar.png"' +
		'alt="avatar" width="60" height="60" />' +
		'<div class="w-25">' +
		'<h6 id="name_user" class="fw-bold mb-1">' + responce.comment.user.name + '</h6>' +
		'<p id="data_comment" class="text-muted small mb-0">' + responce.comment.created_at + '</p>' +
		'</div> <div class="w-100 d-flex justify-content-end">' +
		'<label> <svg class="' + (responce.comment.rating >= 1 ? "checked" : "") + '"' +
		'xmlns="http://www.w3.org/2000/svg" viewBox="0 0 576 512">' +
		'<path d="M259.3 17.8L194 150.2 47.9 171.5c-26.2 3.8-36.7 36.1-17.7 54.6l105.7 103-25 145.5c-4.5 26.3 23.2 46 46.4 33.7L288 439.6l130.7 68.7c23.2 12.2 50.9-7.4 46.4-33.7l-25-145.5 105.7-103c19-18.5 8.5-50.8-17.7-54.6L382 150.2 316.7 17.8c-11.7-23.6-45.6-23.9-57.4 0z" /></svg>' +
		'</label> <label > <svg class="' + (responce.comment.rating >= 2 ? "checked" : "") + '"' +
		'xmlns="http://www.w3.org/2000/svg" viewBox="0 0 576 512">' +
		'<path d="M259.3 17.8L194 150.2 47.9 171.5c-26.2 3.8-36.7 36.1-17.7 54.6l105.7 103-25 145.5c-4.5 26.3 23.2 46 46.4 33.7L288 439.6l130.7 68.7c23.2 12.2 50.9-7.4 46.4-33.7l-25-145.5 105.7-103c19-18.5 8.5-50.8-17.7-54.6L382 150.2 316.7 17.8c-11.7-23.6-45.6-23.9-57.4 0z" /></svg>' +
		'</label> <label> <svg class="' + (responce.comment.rating >= 3 ? "checked" : "") + '"' +
		'xmlns="http://www.w3.org/2000/svg" viewBox="0 0 576 512">' +
		'<path d="M259.3 17.8L194 150.2 47.9 171.5c-26.2 3.8-36.7 36.1-17.7 54.6l105.7 103-25 145.5c-4.5 26.3 23.2 46 46.4 33.7L288 439.6l130.7 68.7c23.2 12.2 50.9-7.4 46.4-33.7l-25-145.5 105.7-103c19-18.5 8.5-50.8-17.7-54.6L382 150.2 316.7 17.8c-11.7-23.6-45.6-23.9-57.4 0z" /></svg>' +
		'</label> <label > <svg class="' + (responce.comment.rating >= 4 ? "checked" : "") + '"' +
		'xmlns="http://www.w3.org/2000/svg" viewBox="0 0 576 512">' +
		'<path d="M259.3 17.8L194 150.2 47.9 171.5c-26.2 3.8-36.7 36.1-17.7 54.6l105.7 103-25 145.5c-4.5 26.3 23.2 46 46.4 33.7L288 439.6l130.7 68.7c23.2 12.2 50.9-7.4 46.4-33.7l-25-145.5 105.7-103c19-18.5 8.5-50.8-17.7-54.6L382 150.2 316.7 17.8c-11.7-23.6-45.6-23.9-57.4 0z" /></svg>' +
		'</label> <label> <svg class="' + (responce.comment.rating >= 5 ? "checked" : "") + '"' +
		'xmlns="http://www.w3.org/2000/svg" viewBox="0 0 576 512">' +
		'<path d="M259.3 17.8L194 150.2 47.9 171.5c-26.2 3.8-36.7 36.1-17.7 54.6l105.7 103-25 145.5c-4.5 26.3 23.2 46 46.4 33.7L288 439.6l130.7 68.7c23.2 12.2 50.9-7.4 46.4-33.7l-25-145.5 105.7-103c19-18.5 8.5-50.8-17.7-54.6L382 150.2 316.7 17.8c-11.7-23.6-45.6-23.9-57.4 0z" /></svg>' +
		'</label> </div> </div>' +
		'<p class="mt-3 mb-4 pb-2 text-start fs-5" id="comment">' + responce.comment.comment + '</p>' +
		'<div class="small d-flex justify-content-start"> ' +
		'<a href="#!" class="d-flex align-items-center me-3"> <i class="far fa-thumbs-up me-2"></i>' +
		'</a> <a href="#!" class="d-flex align-items-center me-3"> <i' +
		'class="far fa-thumbs-up me-2"></i> </a> </div> </div> </div>';
	$("#block-comments").append(row)
	console.log(row)
}

function clean() {
	$("#printComment").val("")
}