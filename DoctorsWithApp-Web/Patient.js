var mongoose = require('mongoose');

// the host:port must match the location where you are running MongoDB
// the "myDatabase" part can be anything you like
mongoose.connect('mongodb://localhost:27017/myDatabase');

var Schema = mongoose.Schema;

var patientSchema = new Schema({
	username: {type: String, required: true, unique: true},
	password: {type: String, required: true},
	name: {type: String, required: true},
	age: {type: Number, require: true},
	gender: {type: String, enum: ["male", "female", "other"]},
	doctorArray: [String],
	insuranceCompany: String,
	insuranceNumber: String,
	allergies: String,
	pastSurgeries: String,
	noteArray: [String]
    });

// export patientSchema as a class called Patient
module.exports = mongoose.model('Patient', patientSchema);
