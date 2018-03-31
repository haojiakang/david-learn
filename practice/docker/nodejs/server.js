'use strict';

var connect = require('connect');
var serverStatic = require('server-static');

var app = connect();
app.use('/', serverStatic('.', {'index': ['index.html']}));
app.listen(8080);

console.log('MyApp is ready at http://localhost:8080');