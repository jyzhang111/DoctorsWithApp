var mongoose = require('mongoose');

mongoose.connect('mongodb://localhost:27017/myDatabase');

var Schema = mongoose.Schema;

var medicineSchema = new Schema({
	name: {type: String, required: true},
	patientName:  {type: String, required: true},
	isPastPill: {type: Boolean, required: false},
	sideEffect: {type: String, required: true},
	count: {type: Number, required: true},
	timeToTake: {type: String, required: true},
	reason: {type: String, required: false},
	timePerDay: {type: Number, required: true}
});

module.exports = mongoose.model('Medicine', medicineSchema);