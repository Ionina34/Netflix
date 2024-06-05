function saveStaff() {
	data={}
	
	let actor={};
	let films=[]
	
	dataCollectionStaff(actor,films);
	
	data["actor"]=actor
	data["films"]=films
	
	$.ajax({
		type:"POST",
		url:"http://localhost:8000/admin/actors/add",
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