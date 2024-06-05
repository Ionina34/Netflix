function saveChanges(directorId){
	let data={}
	
	let director={}
	let films=[]

	dataCollectionStaff(director,films)
	
	data["directorId"]=directorId
	data["director"]=director
	data["films"]=films
	
	$.ajax({
		type:"PUT",
		url:"http://localhost:8000/admin/directors/update",
		contentType: "application/json",
		data:JSON.stringify(data),
		dataType: 'json',
		cache: false,
		xhrFields: {
			withCredentials: true
		},
		success: function(response) {
			$(location).attr('href',"http://localhost:8000/admin/directors")
		},
		error: function(e) {
			//$(location).attr('href',"http://localhost:8000/login")
		}
	})
}
