function saveChanges(actorId){
	let data={}
	
	let actor={}
	let films=[]

	dataCollectionStaff(actor,films)
	
	data["actorId"]=actorId
	data["actor"]=actor
	data["films"]=films
	
	$.ajax({
		type:"PUT",
		url:"http://localhost:8000/admin/actors/update",
		contentType: "application/json",
		data:JSON.stringify(data),
		dataType: 'json',
		cache: false,
		xhrFields: {
			withCredentials: true
		},
		success: function(response) {
			$(location).attr('href',"http://localhost:8000/admin/actors")
		},
		error: function(e) {
			//$(location).attr('href',"http://localhost:8000/login")
		}
	})
}
