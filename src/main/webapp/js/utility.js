"use strict";
var tablenames = [
	'segment',
	'keyword',
	'paraphrase',	
	'lexicon',	
	'intent',
	'phatic',
	'qatype',
	'repertoire',
	'service',
	'syntax',
	'toChinese',
	'pretraining',
	];

function request_get(url, data, callback, args) {
	// console.log('data =');
	// console.log(data);
	return $.ajax({
		url: url,
		type: 'get',
		data: data,
		dataType: 'json',
		success: function (res) {
			// console.log('successfully called php! result from php is:');
			// console.log(res);
			if (callback) {
				callback(res, args);
			}
		},

		error: function (xhr, err) {
			console.log('failure in calling java!');
			console.log('xhr = ');
			console.log(xhr);
			console.log('error reason: ' + err + '!');
		}
	});
}

function request_post(url, data, dataType) {
	return $.ajax({
		url: url,
		type: 'post',
		data: data,
		dataType: dataType? dataType : 'json',
// contentType: "application/x-www-form-urlencoded",
		error: function (xhr, err) {
			console.log('failure in calling java!');
			console.log('xhr = ');
			console.log(xhr);
			console.log('error reason: ' + err + '!');
		}
	});
}

function invoke_python(script) {
	return request_post('algorithm/python', script);
}

function input_positive_integer(input) {
	if (input.value.length == 1) {
		input.value = input.value.replace(/[^1-9]/g, '');
	} else {
		input.value = input.value.replace(/\D/g, '');
	}
}

function input_nonnegative_integer(input, max) {	
	input.value = input.value.replace(/\D/g, '');
	if (max){
		if (parseInt(input.value) > max){
			input.value = max;		
		}
	}
}

function input_nonnegative_number(input) {
	input.value = input.value.replace(/[^.0-9]/g, '');

	var digits = input.value.split(/\./);
	if (digits.length > 2) {
		console.log('digits = ')
		console.log(digits)
		console.log('digits.slice(1).join()=' + digits.slice(1).join(''));
		input.value = digits[0] + '.' + digits.slice(1).join('');
	}
}

function input_integer(input) {
	if (input.value.length == 1) {
		input.value = input.value.replace(/[^\-0-9]/g, '');
	} else if (input.value[0] == '-') {
		input.value = '-' + input.value.substr(1).replace(/\D/g, '');
	}
	else {
		input.value = input.value.replace(/\D/g, '');
	}
}

function training_check() {	
	return "and training = " + generateSelectorIndexed(['false', 'true', 'anomaly'], '', 'training');
}

function rand_check(checked) {
	if (checked)
		checked = 'checked'
	else
		checked = '';

	return `order by rand() <input type="checkbox" name=rand ${checked} onchange="changeLimit(this)">`;
}

function limit_check(limit, max) {	
	var limit_fn = max? `input_nonnegative_integer(this, ${max})` : 'input_nonnegative_integer(this)';	
	return `limit <input id=limit type='text' name=limit style='width:2.5em;' value = ${limit} onkeyup= "${limit_fn}" onafterpaste="${limit_fn}">`;
}

function generateComparisonRelation(name) {
	return generateSelector(['=', '!=', '>', '>=', '<', '<='], '=', 'relation_' + name, 'changeInputlength(this, true)', 'width:1em;', "non-arrowed");
}

function generateSelector(selector, fieldSelected, name, onchange, style, classname) {
	onchange = onchange || '';

	name = name || 'service[]';

	var html = `<select id='${name}' name='${name}' onchange='${onchange}' style='${style}' class='${classname}'>`;

	var found = false;
	if (selector)
		for (var i = 0; i < selector.length; i++) {
			var tag = selector[i];

			var selected = '';
			if (tag == fieldSelected) {
				found = true;
				selected = 'selected';
			}

			html += `<option value='${tag}' ${selected} >${tag}</option>`;
		}

	if (!found) {
		html += `<option value='${fieldSelected}' selected>${fieldSelected}</option>`;
	}

	html += "</select>";
	return html;
}

function generateSelectorIndexed(selector, fieldSelected, name, onchange, style, classname) {
	onchange = onchange || '';

	name = name || 'label[]';

	var html = `<select name='${name}' onchange='${onchange}' style='${style}' class='${classname}'>`;

	var found = false;
	if (selector)
		for (var i = 0; i < selector.length; i++) {
			var tag = selector[i];

			var selected = '';
			if (tag == fieldSelected || i === fieldSelected) {
				found = true;
				selected = 'selected';
			}

			html += `<option value=${i} ${selected} >${tag}</option>`;
		}

	if (!found) {
		html += `<option value='' selected>${fieldSelected}</option>`;
	}

	html += "</select>";
	return html;
}

var timestamp_select = 'and updatetime is ' + generateSelector(['null', 'not null', 'today', 'not today', 'this week', 'not this week', 'this month', 'not this month'], '', 'updatetime', '', 'width:5em;');


function like_statement(text, method) {
	var onchange = 'changeInputlength(this, true); ';
	if (method)
		onchange += `if (this.value == "!=") this.nextElementSibling.value = "${method}";`;
	
	var like = generateSelector(['like', 'like binary', 'regexp', 'regexp binary', '=', 'not like', 'not like binary', 'not regexp', 'not regexp binary', '!='], 'regexp', `relation_${text}`, onchange, 'width:3.2em;', "non-arrowed");
	return `${text} ${like}<input id=${text} type=text name=${text} style='width:8em;' onkeydown='changeInputlength(this);'>`;
}

function generateEqualityRelation(label) {
	var onchange = 'changeInputlength(this, true); ';	
	return generateSelector(['=', '!='], '=', `relation_${label}`, onchange, 'width:1em;', "non-arrowed");
}

function tableSelector(table, lang, text) {	
	return generateSelector(['select', 'delete', 'update', 'insert'], 'select', 'cmd', "onchange_cmd(this)", '', "non-arrowed") + "* from tbl_" 
		+ generateSelector(window.tablenames, table, 'table', 
				`onchange_table(this.value, this.nextElementSibling.value);`) 
				+"_"
		+ generateSelector(['cn', 'en', 'fr', 'de', 'ja'], lang, 'lang', '', '', "non-arrowed")
		+ ' where ' + like_statement(text);
}

function onchange_cmd_insert(div){	
	var y_before = div.childNodes[1].nextSibling.nextElementSibling.nextSibling;
	// set seg = replace(seg,
	console.log('y_before = ');
	var set_clause = y_before.textContent;
	var y_before_str = "and " + set_clause.substr(4, set_clause.indexOf("=") - 2);		
	console.log(y_before_str);		

	var y = y_before.nextSibling;
	console.log('y = ');
	console.log(y);	
	
	if (set_clause.indexOf("replace") >= 0){
		var y_after = y.nextSibling;
		console.log('y_after = ');
		console.log(y_after);	
		
		var y_replacement = y_after.nextSibling;
		console.log('y_replacement = ');
		console.log(y_replacement);	

		var y_replacement_after = y_replacement.nextSibling;
		console.log('y_replacement_after = ');
		console.log(y_replacement_after);	
		div.removeChild(y_replacement);
		div.removeChild(y_replacement_after);
		
		var training_rand = y.nextElementSibling;
		while (['training','rand'].indexOf(training_rand.name) < 0){
			training_rand = training_rand.nextElementSibling;
		}
		var x_next = training_rand.previousSibling;
		
		console.log('x_next = ');
		console.log(x_next);	
		
		div.removeChild(y_before);		
		div.removeChild(y);
					
		div.insertBefore(y, x_next);
		div.removeChild(y_after);
		
		var select = document.createElement('select');
		select.setAttribute('name', 'relation_' + y.name);
		select.setAttribute('class', 'non-arrowed');
		select.setAttribute('style', 'width:3.2em;');
		select.setAttribute('onchange', "changeInputlength(this, true)");
		select.appendChild(new Option('like', 'like'));
		select.appendChild(new Option('like binary', 'like binary'));
		select.appendChild(new Option('regexp', 'regexp'));
		select.appendChild(new Option('=', '='));
		
		select.appendChild(new Option('not like', 'not like'));
		select.appendChild(new Option('not like binary', 'not like binary'));
		select.appendChild(new Option('not regexp', 'not regexp'));
		select.appendChild(new Option('!=', '!='));
		select.value = 'regexp';
		
		y_before_str = y_before_str.substr(0, y_before_str.length - 2);				
		div.insertBefore(select, y);
		y = select;

		div.insertBefore(document.createTextNode(y_before_str), y);			
	}
	else{
		div.removeChild(y_before);
		div.removeChild(y);
	}
}

function onchange_cmd(self) {
	var div = self.parentElement;
	var from_clause = div.childNodes[1];
	console.log('from_clause = ');
	console.log(from_clause);
	var from_clause_str = from_clause.nodeValue;
	switch (self.value){
	case 'update':
		from_clause.nodeValue = " tbl_";
		
		console.log("update is selected");
		var where_clause = div.childNodes[5];
		console.log('where_clause = ');
		console.log(where_clause);
		
		var y = div.children[div.children.length - 4];
		
		var replaceClause = y.type == 'text' && y.onkeyup == null && y.getAttribute('onafterpaste') == null;		
		
		var y_before = y.previousSibling;
		
		if (replaceClause){
			if (y_before.nodeName != "#text"){			
				div.removeChild(y_before);
				y_before = y.previousSibling;
			}		
			
			var y_after = y.nextSibling;			
			
			console.log('y = ');
			console.log(y);
		
			console.log('y_after = ');
			console.log(y_after);
			
			div.removeChild(y);		
			
			var text_after_y = $(y_after).text();
			console.log("$(y_after).text() = " + text_after_y);
			
			var mid = text_after_y.indexOf("and");
			
			y_after.nodeValue = text_after_y.substr(mid);
			
			if (y_before == where_clause){
				var text_before_y = y_before.textContent;
				var set_clause = " set" + text_before_y.substr(text_before_y.indexOf("where") + 5);
				where_clause.nodeValue = "where";
				where_clause.nextSibling.nodeValue = where_clause.nextSibling.nodeValue.substr(3);
			}
			else{
				div.removeChild(y_before);
				var text_before_y = y_before.textContent;
				var set_clause = " set" + text_before_y.substr(text_before_y.indexOf("and") + 3);
			}
			if (!set_clause.endsWith(" = "))
				set_clause += "= ";	
			
			console.log("y.type = " + y.type);		
			
			console.log("y.name = "+ y.name);

			set_clause += 'replace({0}, '.format(y.name);
			div.insertBefore(document.createTextNode(set_clause), where_clause);
			var replacement = y.cloneNode(false);
			
			replacement.id = 'replacement';
			replacement.setAttribute('name', 'replacement');
			
			div.insertBefore(y, where_clause);
			div.insertBefore(document.createTextNode(", "), where_clause);
			
			text_after_y = ') ';
			
			div.insertBefore(replacement, where_clause);
			div.insertBefore(document.createTextNode(text_after_y), where_clause);
				
			y.focus();			
		}
		else{
			// dependent variable is restricted to number input
			if (y_before.nodeName != "#text"){
				y_before = y_before.previousSibling;
			}		
			
			var y_after = y.nextSibling;			
			
			console.log('y = ');
			console.log(y);
		
			console.log('y_after = ');
			console.log(y_after);
			
			var text_after_y = $(y_after).text();
			console.log("$(y_after).text() = " + text_after_y);
			
			var mid = text_after_y.indexOf("and");
			
			y_after.nodeValue = text_after_y.substr(mid);
			
			var text_before_y = y_before.textContent;
			var set_clause = " set" + text_before_y.substr(text_before_y.indexOf("and") + 3);

			if (!set_clause.endsWith(" = "))
				set_clause += "= ";	
			
			var replacement = y.cloneNode(true);
						
			if (y.type == 'select-one'){
				var selectedIndex = y.selectedIndex + 1;
				console.log('selectedIndex = ' + selectedIndex);
				if (y.children.length - selectedIndex <= 1){
					selectedIndex = 0;					
				}
				console.log('selectedIndex = ' + selectedIndex);
				replacement.selectedIndex = selectedIndex;
			}
			else{
				replacement.value = y.value;	
			}
			
			replacement.name = y.name + '_replacement';
			replacement.id = y.id + '_replacement';
			
			div.insertBefore(document.createTextNode(set_clause), where_clause);
			
			div.insertBefore(replacement, where_clause);
			replacement.focus();
		}
		break;
	case "select":
		if (from_clause_str.indexOf("*") >= 0)
			return;
		from_clause.nodeValue = "* from tbl_";
		if (from_clause_str.indexOf("from") >= 0)			
			return;		
		onchange_cmd_insert(div);
		break;
	case "delete":
		if (from_clause_str.startsWith("from"))
			return;
		from_clause.nodeValue = "from tbl_";
		if (from_clause_str.indexOf("from") >= 0)			
			return;				
		
		onchange_cmd_insert(div);
		break;
	case "insert":
		from_clause.nodeValue = "into tbl_";
		var table = from_clause.nextSibling.value;
		var where_clause = div.childNodes[5];
		var args= [];
		var arg = where_clause;
		while(true){
			var arg = arg.nextSibling;
			if (arg.nodeName == '#text'){
				arg.nodeValue = ", ";
				continue;
			}
			if (arg.name.indexOf("relation") >= 0){
				var relation = arg;
				arg = arg.nextSibling;
				div.removeChild(relation);							
			}
			if (arg.name == 'training')
				break;
			args.push(arg.name);
			arg.setAttribute('onkeyup', `onkeyup_input(event)`);
		}
		
		while (arg){
			var next = arg.nextSibling;
			div.removeChild(arg);
			arg = next;
		}
		div.insertBefore(document.createTextNode("(" + args.join(", ") + ") values("), where_clause);
		div.removeChild(where_clause);
		div.lastChild.nodeValue = "), (";
		
		var file = document.createElement('input');
		file.type = 'file';		
		file.setAttribute('onchange', `handleFiles(this, add_${table}_item)`);
		file.accept = '.txt';
		file.style = 'width: 5em;';		
		div.appendChild(file);
		div.appendChild(document.createTextNode(")"));
		
		div.nextElementSibling.remove();
		mysql.text.focus();
		break;
	}
	
}

function changeLimit(random) {
	if (random.checked)
		mysql.limit.value = 100;
	else
		mysql.limit.value = 0;

}

function onchange_table(tablename, lang) {
	console.log("tablename = " + tablename);
	console.log("lang = " + lang);
	var div = document.getElementById("division");
	switch (tablename) {
		case "intent":
			request_get(
				{
					variable: 'serviceSelector',
				},
				function (serviceSelector) {
					div.innerHTML = tableSelector('intent', lang, 'text')
						+ "and service =" + generateSelector(serviceSelector, '', 'service', 'update_slotSelector(this)', 'width:5em;')
						+ `and intent.<select id='slot' name=slot style='width:5em;'></select> like <input id=entity type='text' name=entity style='width:3em;' onkeydown='changeInputlength(this);'>`
						+ `and training = <input id=training type='text' name=training style='width:2em;' onkeyup= "input_nonnegative_integer(this)" onafterpaste="input_nonnegative_integer(this)">`
						+ `and syntactic = <input id=syntactic type='text' name=syntactic style='width:2em;' onkeyup= "input_nonnegative_integer(this)" onafterpaste="input_nonnegative_integer(this)">`
						+ timestamp_select + rand_check(false) + limit_check(40, 1000)
						+ "acquire from" + generateSelector(['', 'python', '云之声'], '', 'log');
					mysql.text.focus();
				}
			);

			break;
		case "service":
			request_get(
				{
					variable: 'categorycontextSelector',
				},
				function (res) {
					var categorySelector = res[0];
					var contextSelector = res[1];
					var strContextSelector = generateSelector(contextSelector, '', 'context', '', 'width:5em;');

					div.innerHTML = tableSelector('service', lang, 'text')
						+ `and context =${strContextSelector}`
						+ "and service =" + generateSelector(categorySelector, '', 'service', '', 'width:5em;')
						+ training_check() + rand_check(false) + limit_check(40, 1000)
						+ "acquire from" + generateSelector(['', 'log', '云之声'], '', 'log');
					mysql.text.focus();
				}
			);

			break;
		case "repertoire":
			request_get(
				{
					variable: 'serviceSelector',
				},
				function (serviceSelector) {
					div.innerHTML = tableSelector('repertoire', lang, 'text')
						+ "and service =" + generateSelector(serviceSelector, '', 'service', 'update_slotSelector(this)', 'width:5em;')
						+ "and slot =" + generateSelector([''], '', 'slot', 'update_codeSelector(this)', 'width:5em;')
						+ "and code =" + generateSelector([''], '', 'code', '', 'width:5em;')						
						+ `<input id=char_length type='text' name=char_length style='width:2.5em;' onkeyup= "input_positive_integer(this)" onafterpaste="input_positive_integer(this)">`
						+ rand_check(false) + limit_check(40, 1000);
					mysql.text.focus();
				}
			);

			break;
		case "paraphrase":
			div.innerHTML = tableSelector('paraphrase', lang, 'text')
				+ 'and ' + like_statement('paraphrase')
				+ "and score " + generateComparisonRelation('score')
				+ `<input id=score type='text' name=score style='width:2.5em;' onkeyup= "input_positive_integer(this)" onafterpaste="input_positive_integer(this)">`
				+ training_check() + rand_check(false) + limit_check(40, 1000);
			break;
		case "lexicon":
			var labelSelector = generateSelector(['hypernym','hyponym', 'synonym', 'antonym', 'related', 'unrelated'], '', 'label', '');
			var switcher = generateEqualityRelation('label');
			div.innerHTML = tableSelector('lexicon', lang, 'text')
				+ 'and ' + like_statement('reword')
				+ `and label ${switcher}${labelSelector}`
				+ training_check() + rand_check(false) + limit_check(40, 1000);
			break;
		case "toChinese":
			div.innerHTML = tableSelector('toChinese', lang, 'text')
				+ 'and ' + like_statement('translation')
				+ training_check() + rand_check(false) + limit_check(40, 1000);
			break;
		case "syntax":
			div.innerHTML = tableSelector('syntax', lang, 'text')
				+ 'and ' + like_statement('infix')
				+ training_check() + rand_check(false) + limit_check(40, 1000);
			break;
		case "segment":
			div.innerHTML = tableSelector('segment', lang, 'text')
				+ "and " + like_statement('seg')
				+ training_check() + rand_check(false) + limit_check(40, 1000);
			break;
		case "phatic":
			var labelSelector = generateSelector(['NEUTRAL', 'PERTAIN'], '', 'label', '');

			div.innerHTML = tableSelector('phatic', lang, 'text')
				+ `and label = ${labelSelector}`
				+ training_check() + rand_check(false) + limit_check(40, 1000);
			break;
		case "qatype":
			var labelSelector = generateSelector(['QUERY', 'REPLY'], '', 'label', '');

			div.innerHTML = tableSelector('qatype', lang, 'text')
				+ `and label = ${labelSelector}`
				+ training_check() + rand_check(false) + limit_check(40, 1000);
			break;
		case "keyword":
			var labelSelector = generateSelectorIndexed(["untechnical", 'technical'], '', 'label', '');			
			
			div.innerHTML = tableSelector('keyword', lang, 'text')
				+ "and label = " + labelSelector 
				+ training_check() + rand_check(false) + limit_check(40, 1000);
			break;
		case "pretraining":			
			div.innerHTML = tableSelector('pretraining', lang, 'title')
				+ "and " + like_statement('text')				
				+ training_check() + rand_check(false) + limit_check(40, 1000);
			break;
		default:
			var labelSelector = generateSelectorIndexed([], '', 'label', '');
			div.innerHTML = tableSelector('', lang, 'text') + "and label = " + labelSelector + training_check() + rand_check(false) + limit_check(40, 1000);
			break;
	}
	mysql.text.focus();
}

function strlen(s) {
	var length = 0;
	for (let i = 0; i < s.length; i++) {
		var code = s.charCodeAt(i)
		if (code < 128 || code == 0x2002)
			length += 1;
		else
			length += 2;
	}
	return length;
}

function add_ner_field(div, text, service, slot) {
	console.log('service = ' + service);
	console.log('length = ' + div.children.length);
	var columnSize = 3;
	var $count = Math.floor(div.children.length / columnSize);

	request_get(
		{
			variable: 'slotSelector',
		},
		function (slotSelector) {
			console.log('slotSelector = ');
			console.log(slotSelector);
			console.log('slotSelector[service] = ');
			console.log(slotSelector[service]);

			var fieldSelected;

			if (slot) {
				fieldSelected = Array(slotSelector[service].length).fill('');
				for (var i = 0; i < slotSelector[service].length; i++) {
					var nerTag = slotSelector[service][i];
					if (nerTag == slot) {
						fieldSelected[i] = 'selected';
					}
				}
			}
			else {
				var success = false;
				fieldSelected = Array(slotSelector[service].length).fill('selected');
				for (var i = 1; i < div.children.length; i += columnSize) {

					var obj = div.children[i];
					var index = obj.selectedIndex;

					console.log(obj.options[index].value + ' is already selected.');
					fieldSelected[index] = '';
				}

				for (var i = 0; i < fieldSelected.length; i++) {
					if (fieldSelected[i] == 'selected') {
						for (var j = i + 1; j < fieldSelected.length; j++) {
							fieldSelected[j] = '';
						}
						success = true;
						break;
					}
				}
				if (!success)
					return;
			}

			console.log('fieldSelected = ' + fieldSelected);

			var html = "<select name='select[]' onchange='changeColor(this,this.parentElement.nextElementSibling)' onfocus = 'this.nextElementSibling.nextElementSibling.disabled = true' onblur='reselect_from_tbl_repertoire(this.nextElementSibling)'>";

			for (var i = 0; i < slotSelector[service].length; i++) {
				var $nerTag = slotSelector[service][i];

				html += "<option value='" + $nerTag + "' " + fieldSelected[i] + ">"
					+ $nerTag + "</option>";
			}

			if (text) {
				if (text instanceof Array) {
					text = text.join();
				}

				var style = 'width:' + Math.min(32, strlen(text) / 2 + 1) + 'em;';

			}
			else {
				var style = 'width:2em;';
				text = '';
			}

			html += `</select> = <input type='text' name='fragment[]' style='${style}' onchange='changeColor(this,this.parentElement.nextElementSibling)' onkeydown='entity_onkeydown(event, this)' onfocus = 'this.nextElementSibling.disabled = true' onblur='reselect_from_tbl_repertoire(this)' value = ${text}>
				<input type='checkbox' name='repertoire' disabled onchange='update_tbl_repertoire(this)'>&nbsp;`;

			update_html(div);

			div.innerHTML += html;

			++$count;

			div.children[0].value = $count;
			console.log('count = ' + div.children[0].value);

			var repertoire = div.children[div.children.length - 1];
			fill_tbl_intent_repertoire(repertoire, service, slot, text);

		}
	);
}

function update_html(container) {
	$(container).find(':input').each(function () {
		switch (this.type) {
			case 'text':
				this.setAttribute('value', this.value);
				break;
			case 'checkbox':
			case 'radio':
				if (this.checked)
					this.setAttribute('checked', 'checked');
				else
					this.removeAttribute('checked');
				break;
			case 'select-one':
			case 'select-multiple':
				$(this).find('option').each(function () {
					if (this.selected)
						this.setAttribute('selected', 'selected');
					else
						this.removeAttribute('selected');
				});
				break;
			case 'textarea':
				this.innerHTML = this.value;
				break;
			default:
				console.log('unknown input type:' + this.type);
		}
	});

	// console.log('after updating: container.innerHTML = ');
	// console.log(container.innerHTML);
}

function update_slotSelector(self) {
	var service = self.value;

	var selector = self.nextElementSibling;
	selector.options.length = 0;

	// console.log('service = ' + service);
	if (service) {
		// console.log(window.slotSelector[service]);
		for (var option of window.slotSelector[service]) {
			selector.options.add(new Option(option, option));
		}
		selector.options.add(new Option('', ''));
		selector.value = '';
	}
}

function update_codeSelector(self) {
	var slot = self.value;

	var selector = self.nextElementSibling;
	selector.options.length = 0;

	console.log('slot = ' + slot);
	if (slot) {
		console.log(window.subslotSelector[slot]);
		if (window.subslotSelector[slot])
			for (var option of window.subslotSelector[slot]) {
				selector.options.add(new Option(option, option));
			}
		selector.options.add(new Option('', ''));
		selector.value = '';
	}
}

function update_tbl_repertoire(self) {
	console.log('function update_tbl_repertoire(self)');

	var text = self.previousElementSibling.value;
	var slot = self.previousElementSibling.previousElementSibling.value;
	var service = self.parentElement.previousElementSibling.previousElementSibling.previousElementSibling.value;

	console.log('service = ' + service);
	console.log('text = ' + text);
	console.log('slot = ' + slot);

	request_get({ service: service, slot: slot, text: text, table: 'tbl_repertoire', cmd: self.checked ? 'insert' : 'delete' },
		function (res) {
			console.log('tbl_repertoire updated, with result = ');
			console.log(res);
		});

}

var tbl_repertoire_service = null;

function onfocus_tbl_repertoire_service(self) {
	tbl_repertoire_service = self.value;
}

function onchange_tbl_repertoire_service(self) {
	console.log('previous tbl_repertoire_service = ' + tbl_repertoire_service);

	request_get({ service: tbl_repertoire_service, text: self.previousElementSibling.value, table: 'tbl_repertoire', cmd: 'delete' },
		function (res) {
			console.log('delete from tbl_repertoire, with result = ');
			console.log(res);
		});

	tbl_repertoire_service = self.value;
	changeColor(self, self.nextElementSibling.nextElementSibling.nextElementSibling);
	update_slotSelector(self);
}

function update_tbl_repertoire_by_service(self) {
	console.log('function update_tbl_repertoire(self)');

	var text = self.previousElementSibling.value;
	var slot = self.previousElementSibling.previousElementSibling.value;
	var service = self.parentElement.previousElementSibling.previousElementSibling.previousElementSibling.value;

	console.log('service = ' + service);
	console.log('text = ' + text);
	console.log('slot = ' + slot);

	request_get({ service: service, slot: slot, text: text, table: 'tbl_repertoire', cmd: self.checked ? 'insert' : 'delete' },
		function (res) {
			console.log('tbl_repertoire updated, with result = ');
			console.log(res);
		});

}

function refresh_ner_fields(self, div) {
	if (!div) {
		div = self.nextElementSibling;
		while (div.type != 'div') {
			div = div.nextElementSibling;
		}
	}

	var service = self.value;
	var text = self.previousElementSibling.value;
	var children = div.childNodes; // childNodes to include the children and
	// the text nodes!
	console.log('length = ' + children.length);

	children[0].value = 0;
	for (var i = children.length - 1; i >= 1; i--) {
		div.removeChild(children[i]);
	}

	console.log('count = ' + div.children[0].value);

	function callback(semantic) {
		console.log('in callback: semantic =');
		console.log(semantic);
		if (semantic) {
			var code = semantic['code'];
			console.log('code =' + code);
			var service_pred = semantic['service'];
		} else {
			var code = '';
			var service_pred = '';
		}

		if (service_pred == service) {
			self.nextElementSibling.value = semantic['code'];
			console.log('intent =');
			var intent = semantic['intent']
			console.log(semantic['intent']);

			for (const slot in intent) {
				add_ner_field(div, intent[slot], service, slot);
			}
		}
		else {
			add_ner_field(div, '', service);
		}

		update_service_code_selector(self, code);
	}

	request_get({
		text: text,
		service: service
	}, callback);
}

function add_paraphrase_item(text, paraphrase, score) {
	var array = text.split('/');
	if (array.length == 2) {
		var x = array[0];
		var y = array[1];
	}
	else {
		var x = "";
		var y = "";
	}

	console.log('x = ' + x);
	console.log('y = ' + y);
	console.log('score = ' + score);

	var html = `<input type=text name=text value = '${x}' onkeydown='changeInputlength(this)' onchange='changeColor(this, this.parentElement.children[3])' onblur='update_paraphrase_score(this.parentElement)'> / `;
	html += `<input type=text name=paraphrase value = '${y}' onkeydown='changeInputlength(this)' onchange='changeColor(this, this.parentElement.children[3])' onblur='update_paraphrase_score(this.parentElement)'> = `;
	html += `<input type=text name=score style='width:2.5em;' value = ${score} onkeyup='input_positive_integer(this)' onafterpaste='input_positive_integer(this)' onchange='changeColor(this, this.nextElementSibling)'>`;
	return html;
}

function add_semantic_item(text, context, category) {
	console.log('context = ' + context);

	console.log('category = ' + category);

	var html = `<input type=text name=text value = '${text}' onchange='changeColor(this, this.parentElement.lastElementChild)'> `;

	html += generateSelector(contextSelector, context, 'context', 'changeColor(this, this.parentElement.lastElementChild)');
	html += ' = ';
	html += generateSelector(categorySelector, category, 'service', 'changeColor(this, this.nextElementSibling)');

	return html;
}

function add_lexicon_item(text, reword, label) {	
	console.log('text = ' + text);
	console.log('reword = ' + reword);
	console.log('label = ' + label);
	
	var html = `<input type=text name=text value='${text}' onchange='changeColor(this, this.parentElement.lastElementChild)'> / `;

	html += `<input type=text name=reword value='${reword}' onchange='changeColor(this, this.parentElement.lastElementChild)'> = `;
	
	html += generateSelector(["hypernym", "hyponym", "synonym", "antonym", "related", "unrelated"], label, 'label', 'changeColor(this, this.nextElementSibling)');
	return html;
}

function add_item(handler, array){
	function html_wrapper(html){
		var training = Math.floor(Math.random() * 2);
		return `<div>${html}<br><input type=hidden name=training value='+${training}'></div>`
	}
	
	var html = "";
	if (array instanceof Array){		
		for (let text of array){
			if (text.length > 0) {
				html += html_wrapper(handler(text));	
			}			
		}		
	}	
	else{
		var element = mysql.lang;
		var args = [];
		while(true){
			element = element.nextElementSibling;
			if (element.type == 'file')
				break;
			args.push(element.value);
		}

		html = html_wrapper(handler.apply(null, args));
	}
	
	for (let div of form.children) {
		if (div.type == 'div' && div.children[0].type != 'text') 
			break;
		update_html(div);
	}

	form.innerHTML = html + form.innerHTML;
}

function add_qatype_item(text, label) {
	console.log('label = ' + label);

	var html = `<input type=text name=text value = '${text}' onchange='changeColor(this, this.parentElement.lastElementChild)'> `;
	html += ' = ';
	html += generateSelector(['QUERY', 'REPLY'], label, 'label', 'changeColor(this, this.nextElementSibling)');
	return html;
}

function add_phatics_item(text, label ) {
	console.log('label = ' + label);

	var html = `<input type=text name=text value='${text}' onchange='changeColor(this, this.parentElement.lastElementChild)'> `;
	html += ' = ';
	html += generateSelector(['NEUTRAL', 'PERTAIN'], label, 'label', 'changeColor(this, this.nextElementSibling)');
	return html;
}

function add_keyword_item(text, label) {
	if (text.startsWith('{') && text.endsWith('}')){
		var json = JSON.parse(text); 
		text = json.text;
		label = json.label;
	}
	
	text = quote_html(text);
	var html = `<input type='text' name='text' value='${text}' onchange='changeColor(this, this.parentElement.lastElementChild)'> = `;
	
	html += generateSelectorIndexed(["untechnical", "technical"], label, 'label', 'changeColor(this, this.nextElementSibling)');
	return html;
}
 
function onchange_segment_text(self){
	var seg = self.nextElementSibling.nextElementSibling;
	var training = seg.nextElementSibling;
	changeColor(self, training);
	request_post('algorithm/segment', self.value).done(res =>{
		seg.value = res['seg'];
		training.value = res['training'];
	});
}

function add_segment_item(text, seg) {
	console.log("text = " + text);
	if (text.startsWith('{') && text.endsWith('}')){
		var json = JSON.parse(text);
		console.log("json = ");
		console.log(json);
		
		text = json.text;
		seg = json.seg;
		console.log("text = " + text);
		console.log("seg = " + seg);		
	}
	
	var style = "width:{0}em;".format(Math.max(strlen(text) / 2 + 1, 32));
	
	var html = `<input type=text name=text style='${style}' value='${text}' class=monospace onchange='onchange_segment_text(this)' onkeydown='changeInputlength(this, true, 32)'><br>`;
	
	style = "width:{0}em;".format(Math.max(strlen(seg) / 2 + 1, 32));
	html += `<input type=text name=seg style='${style}' value='${seg}' class=monospace onchange='onchange_segment(this)' onkeydown='onkeydown_segment(this, event)'>`;
	return html;
}

function onchange_syntax_text(self){
	var div = self.parentElement;
	
	var tree = self.nextElementSibling.nextElementSibling;
	var infix_simplified = tree.nextElementSibling;
	var seg = infix_simplified.nextElementSibling.nextElementSibling;
	var pos = seg.nextElementSibling.nextElementSibling;
	var dep = pos.nextElementSibling.nextElementSibling;
	
	var training = div.lastElementChild;
	
	changeColor(self, training);
	request_post('algorithm/syntax', self.value).done(dict =>{
		console.log("result from java:");
		console.log(dict);
		seg.value = dict['seg'];
		pos.value = dict['pos'];
		dep.value = dict['dep'];
		tree.innerHTML = dict['tree'].replace(/ /g, '&ensp;').replace(/\n/g, '<br>');
		infix_simplified.value = dict['infix'];
		training.value = dict['training'];
	});
}

function add_syntax_item(text) {
	console.log("text = " + text);
	if (text.startsWith('{') && text.endsWith('}')){
		var json = JSON.parse(text);
		console.log("json = ");
		console.log(json);
		
		text = json.text;
		var infix = json.infix;		
		console.log("text = " + text);
		console.log("infix = " + infix);
		
		var arr = parse_for_original_segment(infix);
		var seg = arr[0].join(' ');
		var pos = arr[1].join(' ');
		var dep = arr[2].join(' ');
		var infix_simplified = infix;
		var tree = infix;
	}
	else{
		var seg = '';
		var pos = '';
		var dep = '';
		var infix_simplified = '';
		var infix = '';
		var tree = '';
	}
	
	var style = "width:{0}em;".format(Math.max(strlen(text) / 2 + 1, 50));
	text = quote_html(text);
	
	var html = `<input type=text name=text style='${style}' value='${text}' placeholder='input a natural language sentence and press tab key to view its hierarchical dependency structure' class=monospace onchange='onchange_syntax_text(this)' onkeydown='changeInputlength(this, true, 32)'>`;
	infix = quote_html(infix);
	html += `<input type=hidden name=infix value='${infix}'>`;
	
	tree = str_html(tree);
	html += `<p class=monospace-p name=tree>${tree}</p>`;	
	
	style = "width:{0}em;".format(Math.max(strlen(infix_simplified) / 2 + 1, 32));
	infix_simplified = quote_html(infix_simplified);
	html += `<input type=text name=infix_simplified style='${style}' class=monospace value='${infix_simplified}' onchange='modify_structure(this)'><br>`;
	
	style = "width:{0}em;".format(Math.max(strlen(seg) / 2 + 1, 32));
	seg = quote_html(seg);
	html += `<input type=text name=seg style='${style}' class=monospace value='${seg}' onchange='modify_structure(this)'><br>`;
	
	style = "width:{0}em;".format(Math.max(strlen(pos) / 2 + 1, 32));
	html += `<input type=text name=pos style='${style}' class=monospace value='${pos}' onchange='modify_structure(this)'><br>`;

	style = "width:{0}em;".format(Math.max(strlen(dep) / 2 + 1, 32));
	html += `<input type=text name=dep style='${style}' class=monospace value='${dep}' onchange='modify_structure(this)'><br>`;
	return html;
}

function add_repertoire_item(text, service, slot, code) {
	console.log('text = ' + text);
	console.log('service = ' + service);

	console.log('slot = ' + slot);

	console.log('code = ' + code);

	var html = `<input type='text' name='text' value='${text}' onchange='changeColor(this, this.parentElement.lastElementChild)'> `;

	html += generateSelector(serviceSelector, service, 'service', 'onchange_tbl_repertoire_service(this)', '', 'onfocus_tbl_repertoire_service(this)');
	html += ' = ';
	html += generateSelector(slotSelector[service], slot, 'slot', 'changeColor(this, this.parentElement.lastElementChild); update_codeSelector(this);') + '.';

	html += generateSelector(subslotSelector[slot], code, 'code', 'changeColor(this, this.nextElementSibling)');
	return html;
}

function addListener() {
	var input = document.getElementById("semantic_file");
	input.addEventListener("focus", function () {
		console.log("focus");
	});

	input.addEventListener("mousedown", function () {
		console.log("mousedown");
	});

	input.addEventListener("mouseup", function () {
		console.log("mouseup");
	});

	input.addEventListener("input", function () {
		console.log("input");
	});

	input.addEventListener("change", function () {
		console.log("change");
		console.log(this);
	});

	input.addEventListener("blur", function () {
		console.log("blur");
	});

	input.addEventListener("click", function () {
		console.log("click");
	});
}

function handleFiles(self, handler) {
	var form = self.parentElement.nextElementSibling;

	console.log('function handleFiles() is called!');
	console.log(self);

	var files = self.files;

	console.log(files);
	console.log('files.length = ' + files.length);

	if (files.length) {
		var file = files[0];

		var reader = new FileReader();

		reader.onload = function () {
			add_item(handler, this.result.split(/[\r\n]+/));
		};

		reader.readAsText(file);
	}
}

function changeInputlength(input, exactly, min) {
	input = $(input);
	
	var val = input.type == 'select-one'? input.find("option:selected").text() : input.val();
	
// console.log(val);

	var text_length = strlen(val);
// console.log(text_length);

	if (exactly){
		text_length /= 2.0;		
		if (min)
			text_length = Math.max(text_length,  min); 
		text_length += 0.1;		
	}		
	else{		
		text_length = Math.min(text_length, min? min : 32);
		text_length /= 2.0;
		text_length += 2;
	}

	input.css("width", text_length + "em");
}

function entity_onkeydown(event, self) {
	changeInputlength(self);
	var keyCode = event.keyCode;

	console.log('keyCode = ' + keyCode);
	if (keyCode == 9) {
		console.log("table is pressed!");
		var activeElement = document.activeElement;
		console.log("activeElement = ");
		console.log(activeElement);

		console.log('activeElement.name = ');
		console.log(activeElement.name);

		activeElement = activeElement.nextElementSibling;
		console.log("activeElement.type = ");
		console.log(activeElement.type);

		if (activeElement.type == 'checkbox') {
			activeElement.disabled = false;
		}

	}
}

function changeColor(input, indicator) {
	// console.log(input);
	// https://www.runoob.com/cssref/css-colornames.html
	input.style.color = "red";

	if (indicator) {
		console.assert(indicator.type == 'hidden', 'indicator should be hidden!');
		if (indicator.value[0] != '+')
			indicator.value = '+' + indicator.value;
	}
}

window.onload = function () {
	// currentFunctionKey = window.currentFunctionKey;
	// console.log('register: function (MainKey, value, func)');
	document.onkeyup = function (event) {
		console.log('onkeyup');
		var keyCode = event.keyCode;
		console.log('keyCode = ' + keyCode);
// if (keyCode == currentFunctionKey)
			currentFunctionKey = null;
	}

	document.onkeydown = function (event) {
		var keyCode = event.keyCode;
		console.log('onkeydown');
		console.log('keyCode = ' + keyCode);

		var keyValue = String.fromCharCode(event.keyCode);

		console.log('keyValue = ' + keyValue);
		console.log('currentFunctionKey = ' + currentFunctionKey);

		if (currentFunctionKey == null) {
			currentFunctionKey = keyCode;
			return;			
		}

		switch (currentFunctionKey) {
			case 18: // Alt
				switch (keyValue) {
					// case 'D':
					// toggle_discrepancy();
					// console.log("Alt + D");

					// break;

					case 'L':
						toggle_log();
						console.log("Alt + L");
						break;
					case 'R':
						toggle_random();
						console.log("Alt + R");
						break;
					case 'T':
						toggle_training();
						console.log("Alt + T");
						break;
					case '\r':
					case '\n':
						console.log("Alt + Enter");
						submit();
						break;

				}
				break;
			case 17: // Ctrl
				switch (keyValue) {
					// case 'D':
					// toggle_discrepancy();
					// console.log("Ctrl + D");

					// break;
					case 'L':
						toggle_log();
						console.log("Ctrl + L");
						break;
					case 'R':
						toggle_random();
						console.log("Ctrl + R");
						break;
					case 'T':
						toggle_training();
						console.log("Ctrl + R");
						break;
					case '$':
						$("input[type=text]")[0].focus();
						console.log("Ctrl + Home");
						break;
					case '-':
						insert_into_mysql(document.activeElement);
						console.log("Ctrl + Insert");
						break;
					case '\r':
					case '\n':
						console.log("Ctrl + Enter");
						submit();
						break;

				}
				break;

			case 16: // Shift
				switch (keyValue) {
					case '\r':
					case '\n':
						console.log("Shift + Enter");
						submit();
						break;

				}
				break;
			default:
				break;				
		}
	}

}

function toggle_random() {
	if (mysql.random.checked)
		mysql.random.checked = false;
	else
		mysql.random.checked = true;
	mysql.limit.focus();
}

function toggle_log() {
	if (mysql.log.checked)
		mysql.log.checked = false;
	else
		mysql.log.checked = true;
}

function toggle_discrepancy() {
	if (mysql.discrepancy.checked)
		mysql.discrepancy.checked = false;
	else
		mysql.discrepancy.checked = true;
}

function toggle_training() {
	switch (mysql.training.value) {
		case '':
			mysql.training.value = 0;
			break;
		case '0':
		case 0:
			mysql.training.value = 1;
			break;
		case '1':
		case 1:
			mysql.training.value = -1;
			break;
		case '-1':
		case -1:
			mysql.training.value = '';
			break;
	}
}

function submit() {
	var form = document.activeElement;

	while (form.tagName != 'FORM') {
		console.log('form.tagName = ' + form.tagName);

		console.log('form = ');
		console.log(form);

		form = form.parentElement;
		if (form == null)
			return;
	}

	console.log('form.tagName = ' + form.tagName);

	console.log('form = ');
	console.log(form);

	for (var child of form.children) {
		console.log(child);
		if (child.type == 'submit') {
			child.click();
			break;
		}
	}

	// form.submit();

}

var currentFunctionKey = null;

function insert_tbl_service(self) {
	var code = self.value.toLowerCase();
	var service = self.previousElementSibling.value;
	var category = `${service}.${code}`;
	if (categorySelector.indexOf(category) < 0) {
		category = service;
	}

	var sentence = self.previousElementSibling.previousElementSibling.value;
	request_get({ service: category, training: 1, sentence: sentence, table: 'tbl_service', cmd: 'insert' },
		function (res) {
			console.log('tbl_service updated, with result = ');
			console.log(res);
		});
}

function update_service_code_selector(self, code) {
	request_get(
		{
			variable: 'codeSelector',
		},
		function (codeSelector) {
			var service = self.value;

			console.log('service = ' + service);

			console.log('selectedIndex = ' + self.selectedIndex);

			console.log('self.options[self.selectedIndex].text = ' + self.options[self.selectedIndex].text);

			var selector = self.nextElementSibling;

			selector.options.length = 0;

			if (codeSelector[service]) {
				// if (code)
				// console.assert(window.codeSelector[service].indexOf(code)
				// >= 0);

				for (var option of codeSelector[service]) {
					selector.options.add(new Option(option, option));
				}
			}
			console.log('code = ' + code);
			selector.value = code;

			insert_tbl_service(selector);
		}
	);
}

function fill_tbl_keyword() {
	var children = keyword.children;
	var table_name = children[children.length - 1].name.replace('_submit', '');
	console.log('table_name = ' + table_name);
	var lang = table_name.split('_').pop();
	console.log('lang = ' + lang);
	
	var columnSize = 5;
	for (var j = 1; j < children.length; j += columnSize) {
		var text = children[j - 1].value;
		console.log('text = ' + text);

		request_post('algorithm/keyword', {lang: lang, text: text}, 'text').done((label =>
			res =>
			{
				console.log('res = ' + res);
				var y_pred = parseInt(res);
				console.log('y_pred = ' + y_pred);
				if (y_pred != label.value){
					label.style.color = 'red';						
				}
			}			
		)(children[j]));
	}
}

function fill_tbl_intent() {
	var division = document.getElementsByName('intent_division');

	var columnSize = 3;
	for (var div of division) {
		var code_tag = div.previousElementSibling.previousElementSibling;
		var code = code_tag.value;

		var service_tag = code_tag.previousElementSibling;
		var service = service_tag.value

		var sentence_tag = service_tag.previousElementSibling;
		var sentence = sentence_tag.value;

		if (code) {
			if (code.split(".")[0] != service) {
				service_tag.style.color = 'red';
			}
		}

		var intent = {};

		for (var j = 1; j < div.children.length; j += columnSize) {
			var slot = div.children[j].value;
			// console.log('slot = ' + slot);

			var text = div.children[j + 1].value;
			// console.log('text = ' + text);

			var repertoire = div.children[j + 2];

			fill_tbl_intent_repertoire(repertoire, service, slot, text);

			if (text.indexOf(',') >= 0) {
				text = text.split(/\s*,\s*/);
			}

			intent[slot] = text;
		}

		fill_tbl_service_code(service_tag, code_tag, sentence, intent);

		invoke_python(`nerecognizer.predict('${service}', '${sentence}')`, function (intent_pred, args) {
			var intent = args[0];
			intent_pred = intent_pred['intent'];
			if (!cmp(intent_pred, intent)) {
				console.log("intent from python: " + JSON.stringify(intent_pred));
				console.log("intent from corpus: " + JSON.stringify(intent));
				var error = document.createElement('span');
				error.innerHTML = "from python: " + JSON.stringify(intent_pred) + '<br>';
				var div = args[1];
				div.parentNode.insertBefore(error, div.nextElementSibling.nextElementSibling);
			}
		}, [intent, div]);

		invoke_python(`interface.java('ahocorasick', {'text' : '${sentence}', 'service' : '${service}'})`, function (intent_pred, args) {
			var intent = args[0];
			// intent_pred = intent_pred['intent'];
			if (!cmp(intent_pred, intent)) {
				console.log("intent from repertoire: " + JSON.stringify(intent_pred));
				console.log("intent from corpus: " + JSON.stringify(intent));
				var error = document.createElement('span');
				error.innerHTML = "from repertoire: " + JSON.stringify(intent_pred) + '<br>';
				var div = args[1];
				div.parentNode.insertBefore(error, div.nextElementSibling.nextElementSibling);
			}
		}, [intent, div]);

	}
}

function reselect_from_tbl_repertoire(self) {
	var repertoire = self.nextElementSibling;
	var service = self.parentElement.previousElementSibling.previousElementSibling.previousElementSibling.value;
	var slot = self.previousElementSibling.value;
	var text = self.value;

	console.log('text = ' + text);
	console.log('service = ' + service);
	console.log('slot = ' + slot);
	fill_tbl_intent_repertoire(repertoire, service, slot, text);

}

function fill_tbl_intent_repertoire(repertoire, service, slot, text) {
	request_get({ service: service, slot: slot, text: text, table: 'tbl_repertoire', cmd: 'select' },
		function (res, repertoire) {
			console.log('res = ');
			console.log(res);

			console.log('res.length = ' + res.length);

			var activeElement = document.activeElement;
			if (activeElement == repertoire.previousElementSibling || activeElement == repertoire.previousElementSibling.previousElementSibling) {
				repertoire.disabled = true;
			}
			else
				repertoire.disabled = false;
			if (res.length > 0) {

				if (text.indexOf(',') >= 0) {
					text = text.split(/\s*,\s*/);
					for (let dict of res) {
						console.assert(text.indexOf(dict.text) >= 0);
					}

					console.log('text.length = ' + text.length);

					if (res.length == text.length) {
						console.log('slots detected:');
						console.log(res);
						repertoire.checked = true;
					}
					else {
						console.log('not all the slots are detected:');
						console.log(res);
						repertoire.checked = false;
					}
				} else {
					console.log('slot detected:');
					res = res[0];
					console.log(res);
					console.assert(res.text == text);
					repertoire.checked = true;
				}

			}
			else {
				repertoire.checked = false;
			}
		},
		repertoire);
}

function fill_tbl_service_code(service_tag, code_tag, sentence, intent) {
	request_get({ sentence: sentence, table: 'tbl_service', cmd: 'select' },
		function (res, arr) {
			var service_tag = arr[0];
			var code_tag = arr[1];
			var intent = arr[2];

			// console.log('res = ');
			// console.log(res);
			var caculateFromPython = true;
			if (!(res instanceof Array)) {
				var service_code = res['service'];
				service_code = service_code.split('.');
				if (service_code[0] != service_tag.value) {
					service_tag.value = service_code[0];
					service_tag.style.color = 'red';
				}
				if (service_code.length > 1) {
					console.log('service_code = ');
					console.log(service_code);
					code_tag.value = service_code[1].toUpperCase();
					caculateFromPython = false;
				}
			}

			if (caculateFromPython) {
				var service = service_tag.value;
				intent = JSON.stringify(intent);
				var python = `nerecognizer.code_disambiguate('${service}', ${intent})`;
				// console.log('python code: ' + python);
				invoke_python(python, function (res, code_tag) {
					// console.log('result from python: ' + res);
					code_tag.value = res;

					// console.log('code_tag: ');
					// console.log(code_tag);

				}, code_tag);
			}
		},
		[service_tag, code_tag, intent]);
}

function mysql_execute(self) {
	// console.log(self.innerHTML);
	request_post('algorithm/mysql', self.innerHTML.replace(/&lt;/g, "<").replace(/&gt;/g, ">")).done(result => {
		if (result instanceof Array) {
			var array = [];
			for (let dict of result) {
				console.log(dict);
				array.push(JSON.stringify(dict));
			}
			result = array.join('<br>');						
		}
		else{
			result = "select row_count() = " + result;	
		}	
		self.innerHTML = result;
		self.removeAttribute('ondblclick');
	});
	
	if (self.innerHTML.startsWith('select')) {
		data_clipboard_target(self);
// data_clipboard_text(self);
	}
}

function cmp(x, y) {
	// If both x and y are null or undefined and exactly the same
	if (x === y) {
		return true;
	}

	// If they are not strictly equal, they both need to be Objects
	if (!(x instanceof Object) || !(y instanceof Object)) {
		return false;
	}

	// They must have the exact same prototype chain,the closest we can do is
	// test the constructor.
	if (x.constructor !== y.constructor) {
		return false;
	}

	for (var p in x) {
		// Inherited properties were tested using x.constructor ===
		// y.constructor
		if (x.hasOwnProperty(p)) {
			// Allows comparing x[ p ] and y[ p ] when set to undefined
			if (!y.hasOwnProperty(p)) {
				return false;
			}

			// If they have the same strict value or identity then they are
			// equal
			if (x[p] === y[p]) {
				continue;
			}

			// Numbers, Strings, Functions, Booleans must be strictly equal
			if (typeof (x[p]) !== "object") {
				return false;
			}

			// Objects and Arrays must be tested recursively
			if (!cmp(x[p], y[p])) {
				return false;
			}
		}
	}

	for (var p in y) {
		// allows x[ p ] to be set to undefined
		if (y.hasOwnProperty(p) && !x.hasOwnProperty(p)) {
			return false;
		}
	}
	return true;
};

function matchAll(str, reg) {
	var res = [];
	var match;
	while (match = reg.exec(str)) {
		res.push(match);
	}
	return res;
}

function parse_for_segment(infix) {
	var word = [];
	var infix = infix.replace(/\s+/g, '');

	var arr = matchAll(infix, /\(*([^()]+)\)*/g);
	for (let m of arr) {
		var lexeme = m[1];
		if (lexeme.length)
			word.push(lexeme);
	}

	return word
}

function counterDict(arr) {
	var dic = {};
	for (let x of arr) {
		if (x in dic)
			dic[x] += 1;
		else
			dic[x] = 1;
	}
	return dic;
}

function parse_for_original_segment(infix) {
	var word = [];
	var pos = [];
	var dep = [];
	// var infix = infix.replace(/\s+/g, '');

	var arr = matchAll(infix, /\(*([^()]+)\)*/g);

	for (var i = 0; i < arr.length; i++) {
		arr[i] = arr[i][1].split('/');
		word.push(arr[i][0]);
		pos.push(arr[i][1]);
		dep.push(arr[i][2]);
		if (arr[i].length == 4) {
			arr[i] = parseInt(arr[i][3]);
		}
	}

	if (Number.isInteger(arr[0])) {
		var _word = new Array(word.length);
		var _pos = new Array(pos.length);
		var _dep = new Array(dep.length);
		for (var i = 0; i < arr.length; i++) {
			var j = arr[i];
			_word[j] = word[i];
			_pos[j] = pos[i];
			_dep[j] = dep[i];
		}
		word = _word;
		pos = _pos;
		dep = _dep;
	}

	return [word, pos, dep]
}

function modify_infix(infix, infixExpression) {
	var seg = parse_for_segment(infix);
	var arr = parse_for_original_segment(infixExpression);
	var seg_original = arr[0];
	var pos_original = arr[1];

	console.assert(cmp(counterDict(seg_original), counterDict(seg)), "counterDict(seg_original) != counterDict(seg)");

	var dic = {};
	for (var i = 0; i < seg_original.length; i++) {
		var word = seg_original[i];
		if (word in dic)
			dic[word].push(i);
		else
			dic[word] = [i];
	}

	var index = [];
	for (let word of seg)
		index.push(dic[word].shift());

	pos_original = JSON.stringify(pos_original);
	index = JSON.stringify(index);
	return [pos_original, index];
}

function quote_mysql(param) {
	return param.replace(/'/g, "''").replace(/\\/, "\\\\");
}

function quote_html(param) {
	return param.replace(/&/g, "&amp;").replace(/'/g, "&apos;").replace(/\\/, "\\\\");
}

function str_html(param) {
	return param.replace(/&/g, "&amp;").replace(/<(?=[a-zA-Z!/])/g, "&lt;");
}

function quote(param) {
	return param.replace(/\\/, "\\\\").replace(/'/g, "\\'");
}

function onsubmit_segment(self){	
	console.log("check inconsistency!");
	var children = self.children;

	var list = [];
	for (var i = 1; i < children.length; i += 6) {
		var text = children[i].previousElementSibling;
		var _text = children[i].previousSibling;
		if (_text.textContent == _text.previousSibling.value){
			continue;
		}
		_text = quote_mysql(_text.textContent);
		var sql = "delete from tbl_segment_cn where text = '{0}'".format(_text);
		list.push(sql);
	}
	
	request_post('algorithm/mysql/batch', JSON.stringify(list), 'text');
}

function onkeyup_input(event){
	if (event.keyCode === 13) {		
		console.log("event.keyCode = " + event.keyCode);		
		console.log("document.forms.length = " + document.forms.length);
		var lang = mysql.lang.value;
		var task = mysql.table.value;
		
		if (document.forms.length == 1){
			var form = document.createElement('form');
			form.name = 'form';
			form.setAttribute('method', 'post');
			
			var submit = document.createElement('input');
			submit.type = 'submit';			
			submit.name = `${task}_${lang}_submit`;
			submit.value = 'submit';
			form.appendChild(submit);
			document.body.appendChild(form);			
		}
		add_item(eval(`add_${task}_item`));
	}
}

function onkeydown_segment(self, event){
	changeInputlength(self, true, 32);
	if (event.keyCode === 13) {
		modify_segment(self);
		changeColor(self, self.nextElementSibling);		
	}
}

function modify_segment(self){
	var hidden_text = self.previousElementSibling.previousElementSibling;	
	console.log(hidden_text.value);
	console.log(self.value);
	
	var seg = self.value;
	var text = hidden_text.value;
	
	var arr = convertToSegmentation(seg);
	console.log(arr);
	
	var _text = convertToOriginal(arr);
	console.log("_text = " + _text);
	
	if (_text != text){		
		hidden_text.value = _text;
// hidden_text.parentElement.replaceChild(document.createTextNode(_text),
// hidden_text.nextSibling);
	};
}

function onchange_segment(self) {
	modify_segment(self);
	changeColor(self, self.nextElementSibling);
}

function modify_structure(self) {
	for (let element of self.parentElement.children) {
		var name = element.getAttribute('name');
		// var name = element.name;
		switch (name) {
			case 'infixExpression':
				var infixExpression = element;
				break;
			case 'infix_simplified':
				var infix_simplified = element;
				break;
			case 'seg':
				var seg = element;
				break;
			case 'pos':
				var pos = element;
				break;
			case 'dep':
				var dep = element;
				break;
			case 'tree':
				var tree = element;
				break;
			case 'infix':
				var infixExpression = element;
				break;
			default:
				// console.log("element.getAttribute('class') = " +
				// element.getAttribute('class'));
				// console.log("element.getAttribute('name') = " +
				// element.getAttribute('name'));
				continue;
		}
		console.log(element);
	}

	if (infix_simplified == self) {
		var infix = infix_simplified.value;
		var res = modify_infix(infix, infixExpression.value);
		var pos_original = res[0];
		var index = res[1];

		invoke_python(`compiler.compile('${infix}', ${pos_original}, index=${index}).__str__(return_dict=True)`).done(dict => {
			console.log('dict from python =');
			console.log(dict);
			tree.innerHTML = dict['tree'].replace(/ /g, '&ensp;').replace(/\n/g, '<br>');
			
			infixExpression.value = dict['infixExpression'];

			infix_simplified.value = dict['infix'];
			seg.value = dict['seg'];

			pos.value = dict['pos'];
			changeInputlength(pos, true);

			dep.value = dict['dep'];
			changeInputlength(dep, true);
		});
	}
	else if (pos == self) {
		var segArr = seg.value.trim().split(/\s+/g);
		var posArr = pos.value.trim().split(/\s+/g);

		var arr = matchAll(infixExpression.value, /\(*([^()]+)\)*/g);
		console.log('matchAll result =');
		console.log(arr);
		for (var i = 0; i < arr.length; i++) {
			arr[i] = arr[i][1].split('/');
			if (arr[i].length == 4) {
				arr[i] = parseInt(arr[i][3]);
			}
		}

		if (Number.isInteger(arr[0])) {
			var _segArr = new Array(segArr.length)
			var _posArr = new Array(posArr.length)
			for (var i = 0; i < arr.length; i++) {
				var j = arr[i];
				_segArr[j] = segArr[i];
				_posArr[j] = posArr[i];
			}
			segArr = _segArr;
			posArr = _posArr;
		}

		segArr = JSON.stringify(segArr);
		posArr = JSON.stringify(posArr);

		invoke_python(`compiler.parse_from_segment(${segArr}, ${posArr}).__str__(return_dict=True)`).done(dict => {
			console.log('dict from python =');
			console.log(dict);
			tree.innerHTML = dict['tree'].replace(/ /g, '&ensp;').replace(/\n/g, '<br>');
			
			infixExpression.value = dict['infixExpression'];

			infix_simplified.value = dict['infix'];
			seg.value = dict['seg'];

			pos.value = dict['pos'];
			changeInputlength(pos, true);

			dep.value = dict['dep'];
			changeInputlength(dep, true);
		});
	}
	else if (dep == self) {

		var infix = infixExpression.value;
		var depArr = dep.value;
		invoke_python(`compiler.compile('${infix}', dep='${depArr}'.split()).__str__(return_dict=True)`).done(dict => {
			console.log('dict from python =');
			console.log(dict);
			
			infixExpression.value = dict['infixExpression'];
			dep.value = dict['dep'];
			changeInputlength(dep, true);
		});

	}
	else if (seg == self) {

		var infix = infixExpression.value;
		var segArr = seg.value;
		invoke_python(`compiler.parse_from_segment('${segArr}'.split()).__str__(return_dict=True)`).done(dict => {
			console.log('dict from python =');
			console.log(dict);
			tree.innerHTML = dict['tree'].replace(/ /g, '&ensp;').replace(/\n/g, '<br>');

			infixExpression.value = dict['infixExpression'];

			infix_simplified.value = dict['infix'];
			changeInputlength(infix_simplified, true);

			seg.value = dict['seg'];
			changeInputlength(seg, true);

			pos.value = dict['pos'];
			changeInputlength(pos, true);

			dep.value = dict['dep'];
			changeInputlength(dep, true);
		});

	}

	changeColor(self, self.parentElement.lastChild);
}

function data_clipboard_target(self) {
	var button = document.createElement('button');
	button.innerHTML = "copy to clipboard";
	button.setAttribute('class', 'btn');
	button.setAttribute('data-clipboard-action', 'copy');
	
	console.log('self.nodeName = ' + self.nodeName);
	var data_clipboard_target = self.nodeName;
	
	console.log('self.className = ' + self.className);
	if (self.className != null)
		data_clipboard_target += '.' + self.className
		
	button.setAttribute('data-clipboard-target', data_clipboard_target);
	
	self.parentElement.insertBefore(button, self)

	var clipboard = new ClipboardJS('.btn');

	clipboard.on('success', function (e) {
		console.log(e);
	});

	clipboard.on('error', function (e) {
		console.log(e);
	});
}

function data_clipboard_text(self) {
	var button = document.createElement('button');
	button.innerHTML = "copy to clipboard";
	button.setAttribute('class', 'btn');
	button.setAttribute('data-clipboard-action', 'copy');
// button.setAttribute('data-clipboard-text', self.innerHTML);
	button.setAttribute('data-clipboard-text', "this is the text to copy!");

	self.parentElement.insertBefore(button, self)

	var clipboard = new ClipboardJS('.btn');

	clipboard.on('success', function (e) {
		console.log(e);
	});

	clipboard.on('error', function (e) {
		console.log(e);
	});
}

function accuracy_numeric(y_true, y_pred) {
	if ((90 - y_true) * (90 - y_pred) >= 0 && (70 - y_true) * (70 - y_pred) >= 0 && (40 - y_true) * (40 - y_pred) >= 0)
		return true;
	if (y_true >= 90 || y_pred >= 90)
		return Math.abs(y_true - y_pred) <= 2;

	if (y_true >= 70 || y_pred >= 70)
		return Math.abs(y_true - y_pred) <= 6;

	if (y_true >= 40 || y_pred >= 40)
		return Math.abs(y_true - y_pred) <= 18;

	console.assert(false, 'impossible to occur!');
}

function update_paraphrase_score(div) {
	var x = div.children[0].value;
	var y = div.children[1].value;

	if (x.match(/^\s/) || x.match(/\s$/)) {
		x = x.trim();
		div.children[0].value = x;
	}
	if (y.match(/^\s/) || y.match(/\s$/)) {
		y = y.trim();
		div.children[1].value = y;
	}

	if (!x || !y) {
		return;
	}

	var mysql = `select score from tbl_paraphrase_cn where x = '${x}' and y = '${y}'`;

	request_get({ mysql: mysql }, function (dict, div) {
		if (dict.length == 1) {
			div.children[2].value = dict[0].score;
		}
		paraphrase_predict(div);
	}, div);
}

function insert_into_mysql(input) {
	console.log('calling delete_from_mysql!');
	console.log(input);
	var div = input.parentElement;
	console.log(div);
	console.log('div.name = ' + div.getAttribute('name'));
	switch (div.getAttribute('name')) {
		case "paraphrase_division":
			console.log(div.childNodes);
			div.removeChild(div.childNodes[2]);
			console.log(div.childNodes);
			div.children[0].setAttribute('type', 'text');
			div.children[1].setAttribute('type', 'text');

			div.children[0].setAttribute('onkeydown', 'changeInputlength(this)');
			div.children[1].setAttribute('onkeydown', 'changeInputlength(this)');

			div.children[0].setAttribute('onchange', 'changeColor(this, this.parentElement.children[3])');
			div.children[1].setAttribute('onchange', 'changeColor(this, this.parentElement.children[3])');

			div.children[0].setAttribute('onblur', 'update_paraphrase_score(this.parentElement)');
			div.children[1].setAttribute('onblur', 'update_paraphrase_score(this.parentElement)');

			div.insertBefore(document.createTextNode(' / '), div.children[1]);
			div.insertBefore(document.createTextNode(' = '), div.children[2]);

			var x = div.children[0].value;
			var y = div.children[1].value;

			x = quote_mysql(x);
			y = quote_mysql(y);

			request_get({ mysql: `delete from tbl_paraphrase_cn where x = '${x}' and y = '${y}'` });

			break;

	}
}

function insert_diagnal_attention(self) {
	if (Number.parseFloat(self.value)) {
		if (self.nextElementSibling.name != 'diagnal_attention') {
			var text = document.createTextNode(' diagnal_attention');
			var checkbox = document.createElement('input');
			checkbox.setAttribute('type', 'checkbox');
			checkbox.setAttribute('name', 'diagnal_attention');
			checkbox.checked = true;

			self.parentElement.insertBefore(checkbox, self.nextElementSibling);
			self.parentElement.insertBefore(text, checkbox);
		}
	}
	else {
		if (self.nextElementSibling.name == 'diagnal_attention') {
			self.parentElement.removeChild(self.nextElementSibling);
			self.parentElement.removeChild(self.nextSibling);
		}
	}
}

function insert_factorization_on_word_embedding_only(self) {
	if (self.checked) {
		var text = document.createTextNode(' factorization_on_word_embedding_only');
		var checkbox = document.createElement('input');
		checkbox.setAttribute('type', 'checkbox');
		checkbox.setAttribute('name', 'factorization_on_word_embedding_only');
		checkbox.checked = true;

		self.parentElement.insertBefore(checkbox, self.nextElementSibling);
		self.parentElement.insertBefore(text, checkbox);
	}
	else {
		self.parentElement.removeChild(self.nextElementSibling);
		self.parentElement.removeChild(self.nextSibling);
	}
}


function paraphrase_predict(div) {
	var x = div.children[0].value;
	var y = div.children[1].value;
	var score = parseInt(div.children[2].value);

	x = quote(x);
	y = quote(y);

	invoke_python(`similarity('${x}', '${y}')`, function (score_pred, args) {
		var score = args[0];
		score_pred = parseFloat(score_pred);
		var discrepancy = !accuracy_numeric(score, score_pred);

		score_pred = Math.round(score_pred);
		var div = args[1];
		if (discrepancy) {
			console.log("score from python: " + score_pred);
			console.log("score from corpus: " + score);

			div.children[2].style.color = 'red';
			var error_info = " != " + score_pred;
			if (div.children.length == 4) {
				var error = document.createElement('span');
				error.innerHTML = error_info;
				error.setAttribute('style', 'red');
				div.appendChild(error);
			}
			else {
				div.children[4].innerHTML = error_info;
			}
		}
		else {
			if (div.children.length > 4) {
				div.removeChild(div.children[4]);
			}
		}
	}, [score, div]);
}

function fill_tbl_paraphrase() {
	for (var div of form.children) {
		paraphrase_predict(div);
	}
}

function set_value(input, value) {
	input.value = value;
	changeInputlength(input, true);
}

function isEnglish(ch) {
	return ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= 'ａ' && ch <= 'ｚ' || ch >= 'Ａ' && ch <= 'Ｚ'
		|| ch >= '0' && ch <= '9' || ch >= '０' && ch <= '９';
}

function convertToOriginal(arr) {
	var str = "";
	var i = 0;
	for (; i < arr.length - 1; ++i) {
		str += arr[i];
		if (isEnglish(arr[i].charAt(arr[i].length - 1)) && isEnglish(arr[i + 1].charAt(0))) {
			str += " ";
			continue;
		}
	}
	str += arr[i];
	return str;
}

function convertToSegmentation(str) {
	str = str.replace(/([,.:;!?()\[\]{}'"=<>，。：；！？（）「」『』【】～‘’′”“《》、…．·])/g, " $1 ");

// str = str.replace(/(?<=[\d]+)( +([\.．：:]) +)(?=[\d]+)/g, "$2");
	str = str.replace(/([\d]+)( +([\.．：:]) +)(?=[\d]+)/g, "$1$2");

	var length = str.length;
	while (true) {
		str = str.replace(/([,.:;!?()\[\]{}'"=<>，。：；！？（）「」『』【】～‘’′”“《》、…．·]) +\\1/g, "$1$1");
		if (str.length < length) {
			length = str.length;
			continue;
		} else
			break;
	}

	return str.trim().split(/[\u2002\s]+/);
}

function split_into_sentences(document) {	
	var texts = [];

	for (let m of matchAll(document, /[^;!?：；！？…。\r\n]+[;!?：；！？…。\r\n]*/g)) {
		var line = m[0].trim();
// # if the current sentence is correct, skipping processing!
		if (!line.match(/^[’”]/) || texts.length == 0) {
// # sentence boundary detected!
			texts.push(line);
			continue;
		}
		if (line.match(/^.[,)\\]}，）】》、的]/)) {
// # for the following '的 ' case, this sentence should belong to be previous
// one:
// # ”的文字，以及选项“是”和“否”。
			if (line.substring(1, 3) == "的确") {
// # for the following special case:
// # ”的确， GPS和其它基于卫星的定位系统为了商业、公共安全和国家安全的用 途而快速地发展。
				texts[texts.length - 1] += line.charAt(0);

// # sentence boundary detected! insert end of line here
				texts.push(line.substring(1).ltrim());
				continue;
			}
// # for the following comma case:
// # ”,IEEE Jounalon Selected Areas in
// Communications,Vol.31,No.2,Feburary2013所述。
			texts[texts.length - 1] += line;
			continue;
		}
		var _m = /^.[;.!?:；。！？：…\r\n]+/.exec(line);		
		if (_m)
			var boundary_index = _m.lastIndex;
		else
			var boundary_index = 1;
// # considering the following complex case:
// # ”!!!然后可以通过家长控制功能禁止观看。
// # sentence boundary detected! insert end of line here
		texts[texts.length - 1] += line.substring(0, boundary_index);
		if (boundary_index < line.length)
			texts.push(line.substring(boundary_index).ltrim());
	}
	return texts;
}

function add_segment_from_solr(text, cache_index){
	console.log("text = " + text);
	console.log("cache_index = " + cache_index);
	
	var url=window.location.href; 
	if (url.indexOf("?") != -1){
		url = url.replace(/(\?|#)[^']*/, '');
		window.history.pushState({}, 0, url);
	}	
	
	mysql.text.value = text;
	mysql.limit.value = 0;
	
	var form = document.createElement('form');
	form.name = 'form';
	form.setAttribute('method', 'post');
	form.setAttribute('onsubmit', 'onsubmit_segment(this)');
	form.setAttribute('class', 'monospace');
	
	var submit = document.createElement('input');
	submit.type = 'submit';
	submit.name = 'tbl_segment_cn_submit';
	submit.value = 'submit';
	form.appendChild(submit);
	document.body.appendChild(form);
	request_post('algorithm/cache', {text : text, cache_index : cache_index, lang: 'cn'}).done(res =>{		
		for (let doc of res){
// console.log(doc);
			var a = document.createElement('a');
			a.href = "http://s-gateway-qa.k8s.zhihuiya.com/s-search-patent-solr/patsnap/PATENT/select?q=_id:" + doc['_id'];
			a.innerHTML = doc['TTL'];
			
			form.insertBefore(a, submit);			
			form.insertBefore($('<br>')[0], submit);		
						
// form.insertBefore(document.createTextNode(doc['ABST']), submit);
			form.insertBefore($('<br>')[0], submit);
			
			for (let text of split_into_sentences(doc['ABST'])){				
				text = text.replace(/^(\(\d+\)|[\d〇零一二两三四五六七八九十]+[,:.、，；：])/, "");
				
				var hidden = document.createElement('input');
				hidden.type = 'hidden';
				hidden.name = 'text';
				hidden.value = text;
				form.insertBefore(hidden, submit);
				form.insertBefore(document.createTextNode(text), submit);
				form.insertBefore($('<br>')[0], submit);
				
				var seg = document.createElement('input');
				seg.type = 'text';
				seg.name = 'seg';				
// seg.placeholder = '%s';
				seg.setAttribute('class', 'monospace');
				seg.setAttribute('onchange', 'onchange_segment(this)');
				seg.setAttribute('onkeydown', 'onkeydown_segment(this, event)');					
				if (text.length > 128){
					seg.setAttribute('disabled', true);
				}
				
				form.insertBefore(seg, submit);
				
				var training = document.createElement('input');
				training.type = 'hidden';
				training.name = 'training';
				
				training.value = Math.floor(Math.random() * 2);
				form.insertBefore(training, submit);
				

				form.insertBefore($('<br>')[0], submit);
				form.insertBefore($('<br>')[0], submit);
				
				request_post('algorithm/segment', text).done((seg => res =>{
					console.log(res);
					seg.value = res['seg'];
					var length = Math.max(32, strlen(res['seg']) / 2 + 1);
					seg.style = `width:${length}em;`;					
					if (!seg.disabled){
						seg.nextElementSibling.value = res['training'];						
					}
				})(seg));
			}

			form.insertBefore($('<br>')[0], submit);
		}		
	});
}

function add_segment_en_from_solr(text, cache_index){
	console.log("text = " + text);
	console.log("cache_index = " + cache_index);
	
	mysql.text.value = text;
	mysql.limit.value = 0;
	
	
	var url = window.location.href; 
	if (url.indexOf("?") != -1){
		url = url.replace(/(\?|#)[^']*/, '');
		window.history.pushState({}, 0, url);
	}	
	
	request_post('algorithm/cache', {text : text, cache_index : cache_index, lang: 'en'}).done(res =>{		
		for (let doc of res){
			console.log(doc);
			var a = document.createElement('a');
			a.href = "http://s-gateway-qa.k8s.zhihuiya.com/s-search-patent-solr/patsnap/PATENT/select?q=_id:" + doc['_id'];
			a.innerHTML = doc['TTL'];
			
			document.body.appendChild(a);
			document.body.appendChild(document.createElement("br"));
			
			document.body.appendChild(document.createTextNode(doc['ABST']));
			document.body.appendChild(document.createElement("br"));
		}		
	});
}

String.prototype.format = function()
{
    var args = arguments;
    return this.replace(/\{(\d+)\}/g,                
        function(m,i){
            return args[i];
        });
}

String.prototype.ltrim = function() 
{ 
	return this.replace(/(^\s*)/g, ""); 
} 

String.prototype.rtrim = function() 
{ 
	return this.replace(/(\s*$)/g, ""); 
} 

function onmouseover_solrText(self, event){
	var hover = self.previousElementSibling.previousElementSibling;
	hover.style.display = "block";
	hover.style.left = event.pageX || event.clientX + document.body.scroolLeft;
	hover.style.top = event.pageY || event.clientY + document.body.scrollTop;
}

function onmouseout_solrText(self, event){
	var hover = self.previousElementSibling.previousElementSibling;
	hover.style.display = "none";
}

function add_solr_item(){	
	var submit = $(mysql).find("input[type=submit]")[0];
	var div = submit.previousElementSibling
	var _div = div.cloneNode(true);
	_div.children[0].value = div.children[0].value; 
	mysql.insertBefore(_div, submit);
}

function concurrency_test(){
	console.log("function concurrency_test()");
	for (var i = 1; i < mysql.children.length - 1; ++i){
		var lang = mysql.children[i].children[0].value;
		var text = mysql.children[i].children[1].value;
		var rows = mysql.children[i].children[2].value;
		console.log("lang = " + lang);
		console.log("text = " + text);
		console.log("rows = " + rows);
		
		request_post('algorithm/clustering', {lang: lang, text:text, rows: rows}).done((element => res =>{
			console.log(res);
			var info = document.createElement("span");
			info.innerHTML = "list = " + JSON.stringify(res['list']) + '<br>';			
			info.innerHTML += "solr_duration = " + res['solr_duration'] + '<br>';
			info.innerHTML += "clustering_duration = " + res['clustering_duration'] + '<br>';
			info.innerHTML += "postprocessing_duration = " + res['postprocessing_duration'] + '<br>';
			info.innerHTML += "numFromSolr = " + res['numFromSolr'] + '<br>';
			
			mysql.insertBefore(info, element);
		})(mysql.children[i+1]));		
	}	
}
