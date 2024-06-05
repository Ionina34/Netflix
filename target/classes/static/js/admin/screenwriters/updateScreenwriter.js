function saveChanges(screenwriterId){
	let data={}
	
	let screenwriter={}
	let films=[]

	dataCollectionStaff(screenwriter,films)
	
	data["screenwriterId"]=screenwriterId
	data["screenwriter"]=screenwriter
	data["films"]=films
	
	$.ajax({
		type:"PUT",
		url:"http://localhost:8000/admin/screenwriters/update",
		contentType: "application/json",
		data:JSON.stringify(data),
		dataType: 'json',
		cache: false,
		xhrFields: {
			withCredentials: true
		},
		success: function(response) {
			$(location).attr('href',"http://localhost:8000/admin/screenwriters")
		},
		error: function(e) {
			//$(location).attr('href',"http://localhost:8000/login")
		}
	})
}
