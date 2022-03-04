/*
$(function() {
	$('#dateFormat').val("yyyy-MM-dd HH:mm:ss.SSSXXX");
	$('#submitButton').click(function(){
		var fmt = $('#dateFormat').val();
		var url = "../../webresources/webapi/date";
		$.ajax({
			method: "GET",
			url: url,
			data: { dateFormat: fmt }
		})
		.done(function(msg) {
			$('#errOutput').html(msg.errMsg);
			$('#dateOutput').html("Current date/time: " +
			msg.date);
		})
		.fail(function(jqXHR, textStatus, errorThrown) {
			$('#errOutput').html("AJAX error: " +
			errorThrown);
		});
	});
})
*/

var basePath = "../webresources/"
var memberPath = basePath + "member/"

$(function() {
	makeForm();
	loadList();
})

function showEntry(entity) {
	$('#lastName').val(entity.lastName);
	$('#firstName').val(entity.firstName);
	$('#birthday').val(entity.birthday);
	$('#idView').html( (entity.id && entity.id !="") ? 'ID: ' + entity.id : '');
}

function clearEntry() {
	$('#lastName').val("");
	$('#firstName').val("");
	$('#birthday').val("");
	$('#idView').html("");
}

function makeForm() {
	function formLine(label, id) {
		return '<tr>' +
		'<td>' + label + ':</td><td><input id="' + id
		+ '" type="text"/></td>' +
		'</tr>';
	}
	$('#memberEntry').html(
		'<table><tbody> \
		' + formLine("Last name", "lastName") + '\
		' + formLine("First name", "firstName") + '\
		' + formLine("Birthday", "birthday") + '\
		</tbody></table>'
	)
	.append(
		'<span>' +
		'<button id="clearButton"' +
		' onclick="clearEntry()">Clear</button>' +
		'<button id="submitButton"' +
		' onclick="submit()">Submit</button> \
		</span>'
	)
	.append(
		'<span id="idView"></span> \
		<div class="clearfloat"></div>'
	);
}

function showErr(msg) {
	$('#errMsg').html(msg);
}

function clearErr() {
	$('#errMsg').html("");
}

function clearList() {
	$('#memberList').html("");
}
function makeList(data) {
	clearList();

	function tableRow(lastName, firstName, birthday, id) {
	return '<tr id="tab-'+id+'"> \
		<td>'+lastName+'</td> \
		<td>'+firstName+'</td> \
		<td>'+birthday+'</td> \
		<td><button onclick="edit('+id+')">EDIT</button></td> \
		<td><button onclick="del('+id+')">DEL</button></td> \
		</tr>';
	}

	var tab = $('<table class="listTable"></table>');
	tab.html('<tbody>');

	$.each(data, function(ind,val) {
		tab.append(tableRow(val.lastName, val.firstName,
		val.birthday, val.id));
	});
	tab.append('</tbody>');
	$('#memberList').append(tab);
}

function removeEntry(id) {
	$('#tab-' + id).remove();
}

function submit() {
	var id = $('#idView').html();
	if(id.length > 4) id = id.substring(4);
	var lastName = $('#lastName').val();
	var firstName = $('#firstName').val();
	var birthday = $('#birthday').val();
	var url = (id == "") ? memberPath : memberPath + id;
	var meth = (id == "") ? "POST" : "PUT";
	$.ajax({
		method: meth,
		url: url,
		data: { lastName:lastName,
		firstName:firstName,
		birthday:birthday }
	})
	.done(function(msg) {
		clearErr();
		loadList();
	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		showErr("AJAX SUBMIT: " + errorThrown);
	});
}

function loadList() {
	var url = memberPath + "getlist";
	$.ajax({
		method: "GET",
		url: url
	})
	.done(function(msg) {
		clearErr();
		makeList(msg);
	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		showErr("AJAX LOAD: " + errorThrown);
	});
}

function edit(id) {
	var url = memberPath + id;
	$.ajax({
	method: "GET",
	url: url
	})
	.done(function(msg) {
		clearErr();
		showEntry(msg);
	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		showErr("AJAX EDIT: " + errorThrown);
	});
}

function del(id) {
	clearEntry();
	var url = memberPath + id;
	$.ajax({
	method: "DELETE",
	url: url
	})
	.done(function(msg) {
		clearErr();
		removeEntry(id);
	})
	.fail(function(jqXHR, textStatus, errorThrown) {
		showErr("AJAX DEL: " + errorThrown);
	});
}