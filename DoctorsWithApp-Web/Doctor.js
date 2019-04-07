var mongoose = require('mongoose');

// the host:port must match the location where you are running MongoDB
// the "myDatabase" part can be anything you like
mongoose.connect('mongodb://localhost:27017/myDatabase');

var Schema = mongoose.Schema;

var doctorSchema = new Schema({
	name: {type: String, required: true, unique: true},
	password: {type: String, required: true},
	patientArray: [String]
    });

// export doctorSchema as a class called Doctor
module.exports = mongoose.model('Doctor', doctorSchema);
