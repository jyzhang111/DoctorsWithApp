// set up Express
var express = require('express');
var app = express();

// set up EJS
app.set('view engine', 'ejs');

// set up BodyParser
var bodyParser = require('body-parser');
app.use(bodyParser.urlencoded({ extended: true }));

// import the Doctor class from Doctor.js
var Doctor = require('./Doctor.js');

// import the Doctor class from Patient.js
var Patient = require('./Patient.js');

var Medicine = require('./Medicine.js');

/***************************************/

// route for logging in
// this is the action of the "login" form
app.use('/login', (req, res) => {
	var searchName = req.body.name;
	var password = req.body.password;

	// try to find the doctor
	Doctor.findOne( {name: searchName}, (err, doctor) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		}
		else if (!doctor){
		    res.type('html').status(200);
		    res.send("No doctor named " + searchName);
		}
		else{
		    if(doctor.password != password){
			res.type('html').status(200);
			res.write("Password for " + searchName + " incorrect. <p>");
			res.write('<a href="/">Login</a>');
			res.end();
		    }
		    res.render('home', {doctorName : searchName, newAcc : false, additionalMessage : ""});
		}
	    } ); 
    }
    );

// route for creating a new doctor
// this is the action of the Create Account Button
app.use("/create", (req, res) => {
	res.redirect("/public/createAccount.html");
    }
    );

// route after creating a new doctor
// this is the action of the "createAccount" form
app.use("/created", (req, res) => {

	// construct the Doctor from the form data which is in the request body
	var newDoctor = new Doctor ({
		name: req.body.name,
		password: req.body.password,
	    });

	// save the doctor to the database
	newDoctor.save( (err) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		}
		else {
		    // display the "successfully created" page using EJS
		    res.render('home', {doctorName : req.body.name, newAcc : true, additionalMessage : ""});
		}
	    } ); 

    }
    );

app.use('/beforeAddPill', (req, res) => {
	var searchName = req.query.name;

	Doctor.findOne({name: searchName}, (err, doctor) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		}
		else if (!doctor){
		    res.type('html').status(200);
		    res.send("No doctor named " + searchName);
		}
		else{
			//var patientArr = doctor.patientArray;
			//res.render('addPill', {patients : patientArr, doctorName : doctor});
			res.render('addPill', {doctorName: searchName});
		}
	});
});

app.use('/viewPatient', (req, res) => {
	var searchName = req.query.name;
	Doctor.findOne({name: searchName}, (err, doctor) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		}
		else if (!doctor){
		    res.type('html').status(200);
		    res.send("No doctor named " + searchName);
		}
		else{
			var patientArr = doctor.patientArray;
			res.render('wenjie1', {patients : patientArr, doctorName : searchName});
		}
	});
});

app.use('/viewNote', (req, res) =>{
	var patientName = req.query.patientName;
	var doctorName = req.query.name;
	Patient.findOne({username:patientName}, (err, patient) => {
		if(err){
			res.type('html').status(200);
			res.write("uh oh: " + err);
			console.log(err);
			res.end();
		}
		else if(!patient){
			res.type('html').status(200);
			res.send("No patient named " + patientName);
		}
		else if(patient.noteArray.length == 0){
			res.type('html').status(200);
			res.send("No note attached now!");
		}
		else{
			var patientNoteArray = patient.noteArray;
			res.render('wenjie3', {doctorName:doctorName, patientName:patientName, patientNoteArray:patientNoteArray});
		}
	});
});


app.use('/addNote', (req, res) =>{
	var patientName = req.query.patient;
	var doctorName = req.query.name;
	res.render('addPatientNote', {doctorName:doctorName, patient:patientName});
});



app.use('/changePill', (req, res) =>{
	var patientName = req.query.patient;
	var doctorName = req.query.name;
	var pillName = req.query.pill;
	res.render('updatePillInfo', {doctorName:doctorName, patient:patientName, pillName:pillName});
});


app.use('/updatePillInfo2', (req, res) => {
	var searchName = req.query.name;
	var medName = req.query.pillName;
	var medPatientName = req.query.patientName;
	var medSideEffect = req.body.sideEffect;
	var medTotal1 = req.body.count;
	var medTime = req.body.timeToTake;
	var medTotal2 = req.body.timePerDay;
	var medReason = req.body.reason;
	var medColor = req.body.color;
	var medIsPastPill = req.body.isPastPill;

	Doctor.findOne( {name: searchName}, (err, doctor) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh 1: ' + err);
		    console.log(err);
		    res.end();
		}
		else if (!doctor){
		    res.type('html').status(200);
		    res.send("No doctor named " + searchName);
		}
		else if(!doctor.patientArray.includes(medPatientName)){
			res.type('html').status(200);
		    	res.send("You are not  monitoring patient: " + medPatientName);
		}
		else {
			Medicine.findOne( {name: medName, patientName: medPatientName}, (err, medicine) => { 
		if (err) {
			res.type('html').status(200);
			res.write('uh oh 2: ' + err);
			console.log(err);
			res.end();
		}
		else if (!medicine){
			res.type('html').status(200);
			res.send("No medicine associated with " + medPatientName);
		}
		else{
			medicine.sideEffect = medSideEffect;
			medicine.count = medTotal1;
			medicine.timeToTake = medTime;
			medicine.timePerDay = medTotal2;
			medicine.reason = medReason;
			medicine.color = medColor;
			medicine.isPastPill = medIsPastPill;

			medicine.save( (err) => {
				if (err) {
					res.type('html').status(500);
					res.send('Error: ' + err);
				} else {
		    var additionalMessage = "Updated " + medName + " information of " + medPatientName + " successfully!" ;
		    res.render('home', {doctorName : searchName, additionalMessage : additionalMessage});
				}
			});
		}
	});
		}
	});
});

app.use('/deletePill', (req, res) => {
	var searchName = req.query.doctorName;
	var medName = req.query.pillName;
	var medPatientName = req.query.patientName;
	var medSideEffect = req.body.sideEffect;
	var medTotal1 = req.body.count;
	var medTime = req.body.timeToTake;
	var medTotal2 = req.body.timePerDay;
	var medReason = req.body.reason;
	var medColor = req.body.color;
	var medIsPastPill = req.body.isPastPill;

	Doctor.findOne( {name: searchName}, (err, doctor) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh 1: ' + err);
		    console.log(err);
		    res.end();
		}
		else if (!doctor){
		    res.type('html').status(200);
		    res.send("No doctor named " + searchName);
		}
		else if(!doctor.patientArray.includes(medPatientName)){
			res.type('html').status(200);
		    	res.send("You are not  monitoring patient: " + medPatientName);
		}
		else {
			Medicine.findOne( {name: medName, patientName: medPatientName}, (err, medicine) => { 
		if (err) {
			res.type('html').status(200);
			res.write('uh oh 2: ' + err);
			console.log(err);
			res.end();
		}
		else if (!medicine){
			res.type('html').status(200);
			res.send("No medicine associated with " + medPatientName);
		}
		else{
			Medicine.deleteOne( {name: medName, patientName: medPatientName}, (err) => { 
			if (err) {
				res.type('html').status(200);
				res.write('uh oh: ' + err);
				console.log(err);
				res.end();
			}
			else{
				var additionalMessage = "Removed " + medName + " from the database of " + medPatientName +"!";
				res.render('home', {doctorName : searchName, additionalMessage : additionalMessage});
			}
		    });
		}
	});
		}
	});
});

app.use('/viewChange', (req, res) =>{
	var patientName = req.query.patientName;
	var doctorName = req.query.name;
	Patient.findOne({username:patientName}, (err, patient) => {
		if(err){
			res.type('html').status(200);
			res.write("uh oh: " + err);
			console.log(err);
			res.end();
		}
		else if(!patient){
			res.type('html').status(200);
			res.send("No patient named " + patientName);
		}
		else if(patient.noteArray.length == 0){
			res.type('html').status(200);
			res.send("No note attached now!");
		}
		else{
			res.render('seeUpdatedPill', {doctorName:doctorName, patientName:patientName});
		}
	});
});


app.use('/deleteNote', (req,res)=>{
	var doctorName = req.query.doctorName;
	var patientName = req.query.patientName;
	var num = req.body.num;
	Patient.findOne({username:patientName}, (err, patient) =>{
		if(err){
			res.type('html').status(200);
			res.write("uh oh: " + err);
			res.end();
		}
		else if(!patient){
			res.type('html').status(200);
			res.send('No patient named ' + patientName);
		}
		else if(!(isNaN(num)) && ((num)>=1) && ((num)<=patient.noteArray.length)){
			patient.noteArray.splice(num-1,1);
			patient.save((err) => {
				if(err){
					res.type('html').status(500);
					res.send('Error: ' + err);
				}
				else{
					var additionalMessage = "Note has been deleted successfully!";
					res.render('home', {doctorName: doctorName, additionalMessage : additionalMessage}); 
				}
			});
		}
		else{
			res.type('html').status(200);
			res.send('Invalid number, please type again!');
		}
	});
});

app.use('/addPatientNote2', (req, res) =>{
	var patientName = req.query.patientName;
	var doctorName = req.query.name;
	var patientNote = req.body.note;
	var i;
	Patient.findOne({username:patientName}, (err, patient)=>{
		if(err){
			res.type('html').status(200);
			res.write("uh oh: " + err);
			res.end();
		}
		else if(!patient){
			res.type('html').status(200);
			res.send("No patient named " + patientName);
		}
		else{
			patient.noteArray.push(patientNote);
			patient.save((err) => {
				if(err){
					res.type('html').status(500);
					res.send('Error: ' + err);
				}
				else{
					var additionalMessage = "Note has been added successfully!";
					res.render('home', {doctorName: doctorName, additionalMessage : additionalMessage}); 
				}
			});
		}
	});
});




app.use('/viewPatientDetail', (req, res) => {
	var doctorName = req.query.name;
	var patientName = req.query.patientName;
	var patientName2 = req.body.patientName;
	if(patientName2){
		patientName = patientName2;
	}
	Patient.findOne({username: patientName}, (err, patient) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		}
		else if (!patient){
		    res.type('html').status(200);
		    res.send("No patient named " + patientName);
		}
		else{
			//console.log(patient);			
			res.render('wenjie2', {patient : patientName, doctorName : doctorName});
		}
	});
});

app.use('/viewPill', (req, res) => {
	var searchName = req.query.name;

	Doctor.findOne({name: searchName}, (err, doctor) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		}
		else if (!doctor){
		    res.type('html').status(200);
		    res.send("No doctor named " + searchName);
		}
		else{
			var patientArr = doctor.patientArray;
			res.render('showMyPatients', {patients : patientArr, doctorName : searchName});
		}
	});
});

app.use('/viewPill2', (req, res) => {
	var searchName = req.query.name;

	Doctor.findOne({name: searchName}, (err, doctor) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		}
		else if (!doctor){
		    res.type('html').status(200);
		    res.send("No doctor named " + searchName);
		}
		else{
			var patientArr = doctor.patientArray;
			res.render('showMyPatients2', {patients : patientArr, doctorName : doctor});
		}
	});
});

app.use('/showPatientPill', (req, res) => {
	var searchName = req.query.name;
	var patientUsername = req.query.patientName;
	var patientsMedication = [];

	Medicine.find({},(err, allMedicines) => {
		if (err) {
			res.type('html').status(500);
		    res.send('Error: ' + err);
		    res.end();
		}
		else {
			for (var i = 0; i < allMedicines.length; i++) {
				if(allMedicines[i].patientName == patientUsername && !allMedicines[i].isPastPill) {
					patientsMedication.push(allMedicines[i]);
				}
			}
			if (patientsMedication.length ==0) {
				res.type('html').status(200);
		   	    res.send('There is no assigned medication for this patient');
			}
			else {
				res.render('showMedication', {medicines: patientsMedication, doctorName: searchName, patientName: patientUsername});
			}
		}
	});
});

app.use('/showPatientPill2', (req, res) => {
	var searchName = req.query.name;
	var patientUsername = req.query.patientName;
	var patientsMedication = [];

	Medicine.find({},(err, allMedicines) => {
		if (err) {
			res.type('html').status(500);
		    res.send('Error: ' + err);
		    res.end();
		}
		else {
			for (var i = 0; i < allMedicines.length; i++) {
				if(allMedicines[i].patientName == patientUsername && allMedicines[i].isPastPill) {
					patientsMedication.push(allMedicines[i]);
				}
			}
			if (patientsMedication.length ==0) {
				res.type('html').status(200);
		   	    res.send('There is no assigned past medication for this patient');
			}
			else {
				res.render('showMedication2', {medicines: patientsMedication, doctorName: searchName, patientName: patientUsername});
			}
		}
	});
});

// route for adding new patient to monitors
app.use('/addNewMonitorPatient', (req, res) => {res.render('addNewMonitorPatient', {doctorName : req.query.name});});

app.use('/viewAllPatientsNotMonitor', (req, res) => {
	var searchName = req.query.name;

	// try to find the doctor
	Doctor.findOne( {name: searchName}, (err, doctor) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		}
		else if (!doctor){
		    res.type('html').status(200);
		    res.send("No doctor named " + searchName);
		}
		else{
		    var patientNameArray = [];
		    var toRemoveArray = [];
		    Patient.find({}, (err, allPatients) => {
			if (err) { 
				res.type('html').status(500);
				res.send('Error: ' + err); 
			}
			else if (allPatients.length == 0) {
				res.type('html').status(200);
				res.send('There are no patients');
			}
		        else {
				for (var i = 0; i < allPatients.length; i++) {
					patientNameArray.push(allPatients[i].username);
				}

				for(var i = 0; i < doctor.patientArray.length; i++){
					toRemoveArray.push(doctor.patientArray[i]);
		    		}

		   		patientNameArray = patientNameArray.filter( function( el ) {
					return toRemoveArray.indexOf( el ) < 0;
				} );

				res.render('viewAllPatientsNotMonitor', {patients : patientNameArray, doctor : doctor});
		        }
		    });
		}
	    } ); 
});

app.use('/addNewMonitorPatient2', (req, res) => {
	var patientUsername = req.body.username;
	var searchName = req.query.name;

	Doctor.findOne( {name: searchName}, (err, doctor) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		}
		else if (!doctor){
		    res.type('html').status(200);
		    res.send("No doctor named " + searchName);
		}
		else{
		    if(doctor.patientArray.includes(patientUsername)){
			res.type('html').status(200);
		    	res.send("You are already monitoring patient: " + patientUsername);
		    }
		    else{
			Patient.findOne( {username: patientUsername}, (err, patient) => { 
				if (err) {
					res.type('html').status(200);
					res.write('uh oh: ' + err);
					console.log(err);
					res.end();
				}
				else if (!patient){
					res.type('html').status(200);
					res.send("No patient named " + patientUsername);
				}
				else{
					//patient.doctorArray.push(searchName);
					doctor.patientArray.push(patientUsername);
					doctor.save( (err) => { 
						if (err) { 
							res.type('html').status(500);
							res.send('Error: ' + err);
						}
						else{
							patient.doctorArray.push(searchName);
							patient.save( (err) => { 
								if (err) { 
									res.type('html').status(500);
									res.send('Error: ' + err);
								}
								else{
									var additionalMessage = "Added " + patientUsername + " to be monitored!";
									res.render('home', {doctorName : searchName, additionalMessage : additionalMessage});
								}
		    					});
						}
					});
				}
				
			});
		    }
		}
	} ); 	
});

// route for creating new patient
app.use('/createPatient', (req, res) => {res.render('createPatient', {doctorName : req.query.name});});

// route for canceling create patient page
app.use('/cancelCreatePatient', (req, res) => {res.render('home', {doctorName : req.query.name, newAcc : false, additionalMessage : ""});});

app.use('/createPatient2', (req, res) => {
	var error = "";
	
	var patientNameArray = [];
	Patient.find({}, (err, allPatients) => {
		if (err) { 
		    res.type('html').status(500);
		    res.send('Error: ' + err);
		    res.end();
		}
		else {
		    for (var i = 0; i < allPatients.length; i++) {
			patientNameArray.push(allPatients[i].username);
		    }
		}
	});

	var patientUsername = req.body.username;
	if (patientNameArray.includes(patientUsername)){
		error = error + "Username " + patientUsername + " is already taken.\n";
	}
	var patientPassword = req.body.password;
	var patientName = req.body.name;
	var patientAge = req.body.age;
	if (patientAge >= 150) {
		error = error + "Your age is too great.\n";
	}
	else if (patientAge < 16){
		error = error + "You are a minor. You cannot create an account.\n"
	}
	var patientGender = req.body.gender;
	var patientInsuranceCompany = req.body.insuranceCompany;
	var patientInsuranceNumber = req.body.insuranceNumber;
	var patientAllergies = req.body.allergies;
	var patientPastSurgeries = req.body.pastSurgeries;
	var searchName = req.query.name;
	var patientDoctors = [searchName];

	if(error !== ""){
		res.render('errorCreateNewPatient', {doctorName : searchName, errorMessage : error});
		res.end();
	}

	// construct the Patient from the form data which is in the request body
	var newPatient = new Patient ({
		username: patientUsername,
		password: patientPassword,
		name: patientName,
		age: patientAge,
		gender: patientGender,
		doctorArray: patientDoctors,
		insuranceCompany: patientInsuranceCompany,
		insuranceNumber: patientInsuranceNumber,
		allergies: patientAllergies,
		pastSurgeries: patientPastSurgeries
	    });

	newPatient.save( (err) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		}
	} ); 

	Doctor.findOne( {name: searchName}, (err, doctor) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		}
		else if (!doctor){
		    res.type('html').status(200);
		    res.send("No doctor named " + searchName);
		    res.end();
		}
		else{
		    doctor.patientArray.push(patientUsername);
		    doctor.save( (err) => { 
			if (err) { 
				res.type('html').status(500);
				res.send('Error: ' + err);
			}
		    });
		    var additionalMessage = "Created " + patientUsername + ", to be monitored by you!";
		    res.render('home', {doctorName : searchName, additionalMessage : additionalMessage});
		}
	} );
} );

app.use('/remove', (req, res) => {
	var searchName = req.query.name;

	var patientArray = [];

	Doctor.findOne( {name: searchName}, (err, doctor) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		}
		else if (!doctor){
		    res.type('html').status(200);
		    res.send("No doctor named " + searchName);
		}
		else{
		    for(var i = 0; i < doctor.patientArray.length; i++){
			patientArray.push(doctor.patientArray[i]);
		    }
		    res.render('remove', {doctorName : req.query.name, patients : patientArray});
		}
	} );
} );

app.use('/addPill', (req, res) => {
	var searchName = req.query.name;

	var medName = req.body.name;
	var medPatientName = req.body.patientName;
	var medSideEffect = req.body.sideEffect;
	var medTotal = req.body.count;
	var medTime = req.body.timeToTake;
	var medTotal = req.body.timePerDay;
	var medReason = req.body.reason;
	var medColor = req.body.color;

	Doctor.findOne( {name: searchName}, (err, doctor) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		}
		else if (!doctor){
		    res.type('html').status(200);
		    res.send("No doctor named " + searchName);
		}
		else if(!doctor.patientArray.includes(medPatientName)){
			res.type('html').status(200);
		    	res.send("You are not  monitoring patient: " + medPatientName);
		}
		else {
			Patient.findOne( {username: medPatientName}, (err, patient) => { 
		if (err) {
			res.type('html').status(200);
			res.write('uh oh: ' + err);
			console.log(err);
			res.end();
		}
		else if (!patient){
			res.type('html').status(200);
			res.send("No patient named " + medPatientName);
		}
		else{
			var newMedicine = new Medicine ({
				name: medName,
				patientName: medPatientName,
				sideEffect: medSideEffect,
				count: medTotal,
				timeToTake: medTime,
				timePerDay: medTotal,
				reason: medReason,
				color: medColor
			});

			newMedicine.save( (err) => {
				if (err) {
					res.type('html').status(200);
		    		res.write('uh oh: ' + err);
		    		console.log(err);
		    		res.end();
				}
				else {
					var additionalMessage = "Added " + medName + " to patient " + medPatientName;
					res.render('home', {doctorName : searchName, newAcc : false, additionalMessage : additionalMessage});
				}
			});
		}
	});
		}
	});
});



	
app.use('/remove2', (req, res) => {
	var searchName = req.query.name;
	var patientUsername = req.query.patientName;

	Doctor.findOne( {name: searchName}, (err, doctor) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		}
		else if (!doctor){
		    res.type('html').status(200);
		    res.send("No doctor named " + searchName);
		    res.end();
		}
		else{
		    for(var i = 0; i < doctor.patientArray.length; i++){ 
 			if (doctor.patientArray[i] == patientUsername) {
				doctor.patientArray.splice(i, 1); 
			}
		    }
		    doctor.save( (err) => { 
			if (err) { 
				res.type('html').status(500);
				res.send('Error: ' + err);
				res.end();
			}
		    });
		    Patient.findOne( {username: patientUsername}, (err, patient) => { 
			if (err) {
				res.type('html').status(200);
				res.write('uh oh: ' + err);
				console.log(err);
				res.end();
			}
			else if (!patient){
				res.type('html').status(200);
				res.send("No patient named " + patientUsername);
				res.end();
			}
			else{
				for(var i = 0; i < patient.doctorArray.length; i++){ 
 					if (patient.doctorArray[i] == searchName) {
						patient.doctorArray.splice(i, 1); 
					}
		    		}
				patient.save( (err) => { 
					if (err) { 
						res.type('html').status(500);
						res.send('Error: ' + err);
						res.end();
					}
					else{
						var additionalMessage = "Removed " + patientUsername + " from monitoring.";
		    				res.render('home', {doctorName : searchName, additionalMessage : additionalMessage});
					}
		    		});
			}
		    });
		}
	} ); 	
} );

// route for adding new patient to monitor
app.use('/removeFromAll', (req, res) => {res.render('removeFromAll', {doctorName : req.query.name});});

app.use('/viewAllPatients', (req, res) => {
	var patientNameArray = [];

	Patient.find({}, (err, allPatients) => {
		if (err) { 
		    res.type('html').status(500);
		    res.send('Error: ' + err); 
		}
		else if (allPatients.length == 0) {
		    res.type('html').status(200);
		    res.send('There are no patients');
		}
		else {
		    for (var i = 0; i < allPatients.length; i++) {
			patientNameArray.push(allPatients[i].username);
		    }
		    res.render('viewAllPatients', {patients : patientNameArray, doctorName : req.query.name});
		}
	});
});

app.use('/removeFromAll2', (req, res) => {
	var patientUsername = req.body.username;
	var searchName = req.query.name;

	Doctor.find({}, (err, allDoctors) => {
		if (err) { 
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		}
		else {
		    for (var i = 0; i < allDoctors.length; i++) {
			for(var j = 0; j < allDoctors[i].patientArray.length; j++){ 
 				if (allDoctors[i].patientArray[j] == patientUsername) {
					allDoctors[i].patientArray.splice(j, 1); 
				}
			}
		    }
		}
	});

	Doctor.findOne( {name: searchName}, (err, doctor) => { 
		if (err) {
		    res.type('html').status(200);
		    res.write('uh oh: ' + err);
		    console.log(err);
		    res.end();
		}
		else if (!doctor){
		    res.type('html').status(200);
		    res.send("No doctor named " + searchName);
		}
		else{
		    for(var i = 0; i < doctor.patientArray.length; i++){ 
 			if (doctor.patientArray[i] == patientUsername) {
				doctor.patientArray.splice(i, 1); 
			}
		    }
		    doctor.save( (err) => { 
			if (err) { 
				res.type('html').status(500);
				res.send('Error: ' + err);
			}
		    });
		    Patient.deleteOne( {username: patientUsername}, (err) => { 
			if (err) {
				res.type('html').status(200);
				res.write('uh oh: ' + err);
				console.log(err);
				res.end();
			}
			else{
				var additionalMessage = "Removed " + patientUsername + " from the database.";
				res.render('home', {doctorName : searchName, additionalMessage : additionalMessage});
			}
		    });
		}
	} ); 	
});

// route for showing all the doctors
app.use('/all', (req, res) => {
    
	// find all the Doctor objects in the database
	Doctor.find( {}, (err, doctors) => {
		if (err) {
		    res.type('html').status(200);
		    console.log('uh oh' + err);
		    res.write(err);
		    res.end();
		}
		else {
		    if (doctors.length == 0) {
			res.type('html').status(200);
			res.write('There are no doctors');
			res.end();
			return;
		    }
		    // use EJS to show all the Doctor
		    res.render('all', { doctors: doctors });

		}
	    }).sort({ 'name': 'asc' }); // this sorts them BEFORE rendering the results
    });

// route for accessing data via the web api
// to use this, make a request for /api to get an array of all Person objects
// or /api?name=[whatever] to get a single object
app.use('/api', (req, res) => {
	console.log("LOOKING FOR SOMETHING?");

	// construct the query object
	var queryObject = {};
	if (req.query.name) {
	    // if there's a name in the query parameter, use it here
	    queryObject = { "name" : req.query.name };
	}
    
	Person.find( queryObject, (err, persons) => {
		console.log(persons);
		if (err) {
		    console.log('uh oh' + err);
		    res.json({});
		}
		else if (persons.length == 0) {
		    // no objects found, so send back empty json
		    res.json({});
		}
		else if (persons.length == 1 ) {
		    var person = persons[0];
		    // send back a single JSON object
		    res.json( { "name" : person.name , "age" : person.age } );
		}
		else {
		    // construct an array out of the result
		    var returnArray = [];
		    persons.forEach( (person) => {
			    returnArray.push( { "name" : person.name, "age" : person.age } );
			});
		    // send it back as JSON Array
		    res.json(returnArray); 
		}
		
	    });
    });

// route for cleaning everything
app.use('/clean', (req, res) =>{ res.render('clean'); });

app.use('/clean2', (req, res) =>{ 
	Patient.deleteMany({}, (err) => {
		if(err) {
		    res.type('html').status(200);
		    console.log('uh oh' + err);
		    res.write("Can't delete Patient.");
		    res.end();
		}
	});

	Doctor.deleteMany({}, (err) => {
		if(err) {
		    res.type('html').status(200);
		    console.log('uh oh' + err);
		    res.write("Can't delete Doctor.");
		    res.end();
		}
	});

	Medicine.deleteMany({}, (err) => {
		if(err) {
		    res.type('html').status(200);
		    console.log('uh oh' + err);
		    res.write("Can't delete Medicine.");
		    res.end();
		}
	});

	res.redirect('/public/login.html');
});

app.use('/apiDoctor', (req, res) => {
	console.log("Looking for doctor");

	var queryObject = {};
	if (req.query.name) {
		queryObject = { "name" : req.query.name};
	}

	Doctor.find( queryObject, (err, persons) => {
		console.log(persons);
		if(err) {
			console.log('uh oh' + err);
			res.json({});
		}
		else if (persons.length == 0) {
			res.json({});
		} else if (persons.length == 1) {
			var person = persons[0];
			res.json( {"name" : person.name, "docPassword" : person.password, "patArray": person.patientArray});
		} else {
			var returnArray = [];
			persons.forEach((person) => { 
				returnArray.push( {"name": person.name, "docPassword" : person.password, "patArray": person.patientArray})
			});
			res.json(returnArray);
		}
	});
});

app.use('/apiPatient', (req, res) => {
	console.log("Looking for patient");

	var queryObject = {};
	if (req.query.name) {
		queryObject = { "name" : req.query.name};
	}

	Patient.find( queryObject, (err, persons) => {
		console.log(persons);
		if(err) {
			console.log('uh oh' + err);
			res.json({});
		}
		else if (persons.length == 0) {
			res.json({});
		} else if (persons.length == 1) {
			var person = persons[0];
			res.json( {"name" : person.name, "doctorArray" : person.doctorArray, "username": person.username, 
						"password": person.password, "age": person.age, "gender": person.gender, 
						"insComp": person.insuranceCompany, "insNum": person.insuranceNumber,
						"allergies": person.allergies, "pastSurg": person.pastSurgeries});
		} else {
			var returnArray = [];
			persons.forEach((person) => { 
				returnArray.push( {"name": person.name, "doctorArray" : person.doctorArray,"username": person.username, 
						"password": person.password, "age": person.age, "gender": person.gender, 
						"insComp": person.insuranceCompany, "insNum": person.insuranceNumber,
						"allergies": person.allergies, "pastSurg": person.pastSurgeries});
			});
			res.json(returnArray);
		}
	});
});

app.use('/apiViewMedications', (req, res) => {
	console.log("Looking for medicine");
	var queryObject = {};
	if (req.query.patientName) {
		queryObject = { "patientName" : req.query.patientName };
	}
	// var searchName = req.query.name;
	// var medPatientName = req.query.patientName;
	
	// Doctor.findOne( {name: searchName}, (err, doctor) => { 
		// if (err) {
		//     res.type('html').status(200);
		//     res.write('uh oh 1: ' + err);
		//     console.log(err);
		//     res.end();
		// }
		// else if (!doctor){
		//     res.type('html').status(200);
		//     res.send("No doctor named " + searchName);
		// }
		// else if(!doctor.patientArray.includes(medPatientName)){
		// 	res.type('html').status(200);
		//     	res.send("You are not  monitoring patient: " + medPatientName);
		// }
		// else {


			Medicine.find( queryObject, (err, medicines) => { 
		if (err) {
			res.type('html').status(200);
			res.write('uh oh 2: ' + err);
			console.log(err);
			res.end();
		} else if (medicines.length == 0) {
			res.json({});
		} else if (medicines.length == 1) {
			var medicine = medicines[0];
			res.json( { "name" : medicine.name, "patientName" : medicine.patientName,
						"sideEffect" : medicine.sideEffect, "count" : medicine.count,
					   "timeToTake" : medicine.timeToTake, "timePerDay" : medicine.timePerDay,
					   "reason" : medicine.reason, "color" : medicine.color,
					   "isPastPill" : medicine.isPastPill }	);
		} else {
			var returnArray = [];
			medicines.forEach( (medicine) => {
				returnArray.push( { "name" : medicine.name, "patientName" : medicine.patientName,
									"sideEffect" : medicine.sideEffect, "count" : medicine.count,
					   				"timeToTake" : medicine.timeToTake, "timePerDay" : medicine.timePerDay,
					   				"reason" : medicine.reason, "color" : medicine.color,
					   				"isPastPill" : medicine.isPastPill } );
			});
			res.json(returnArray); 
		}
	});
});

/*************************************************/

app.use('/public', express.static('public'));

app.use('/', (req, res) => { res.redirect('/public/login.html'); } );

app.listen(3000,  () => {
	console.log('Listening on port 3000');
    });
