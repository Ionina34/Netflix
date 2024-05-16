$("#linkWithGenres a").on("click",function(event){
	event.preventDefault()
	
	$(location).attr('href',"http://localhost:8000/films/all")
	localStorage.setItem('genre',$(this).text().replace(', ',''));
})

$("#linkWithCountry a").on("click",function(event){
	event.preventDefault();
	
	$(location).attr('href',"http://localhost:8000/films/all")
	localStorage.setItem('country',$(this).text().replace(', ',''));
})