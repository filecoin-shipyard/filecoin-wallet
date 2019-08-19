/**
 * 发送异步 http GET 请求
 * @param url
 * @param data
 * @param success
 * @param fail
 */
function httpGet(url, data, success, fail, delay) {

	if (isObject(data)) { //传入了 data 参数
		url = buildQueryUrl(url, data);
	}
	if (typeof data == "function") {
		delay = fail;
		fail = success;
		success = data;
	}
	request(url, "GET", true, null, success, fail, delay);
}

/**
 * 发送异步 http POST 请求
 * @param url
 * @param data
 * @param success
 * @param fail
 * @param delay
 */
function httpPost(url, data, success, fail, delay) {

	if (typeof data == "function") {
		delay = fail;
		fail = success;
		success = data;
		data = {};
	}
	request(url, "POST", true, data, success, fail, delay);
}

/**
 * 发送 http 请求
 * @param url 地址
 * @param method 请求方式, POST, GET
 * @param async 是否同步
 * @param data 数据
 * @param success 成功时候回调
 * @param fail 失败时候回调
 * @param delay 是否延时发送请求（为了解决响应速度过快导致弹框一闪而过）
 * @returns {*}
 */
function request(url, method, async, data, success, fail, delay) {

	if (typeof success != "function") {
		success = function() {}
	}
	if (typeof fail != "function") {
		fail = function() {}
	}
	var options = {
		type: method,
		url: url,
		async: async,
		headers: {
			appId: "016726a0f24a0001a0",
			apiKey: "dfb28a624f0b4e2e2b45b1eddc81c3f0",
		},
		data: data,
		success: function(result) {
			if (result.code == "000") {
				success(result);
			} else {
				fail(result.message);
			}
		},
		dataType: "json",
		error: function(error) {
			fail(error);
		}
	};
	if (delay) {
		setTimeout(function () {
			$.ajax(options);
		}, 500);
	} else {
		$.ajax(options);
	}
}

/**
 * 使用 json 传参
 * @param url
 * @param data
 * @param success
 * @param fail
 */
function postJson(url, data, success, fail) {

	if (typeof success != "function") {
		success = function() {}
	}
	if (typeof fail != "function") {
		fail = function() {}
	}
	var options = {
		type: "POST",
		url: url,
		headers: {
			appId: "016726a0f24a0001a0",
			apiKey: "dfb28a624f0b4e2e2b45b1eddc81c3f0",
		},
		data: JSON.stringify(data),
		contentType: 'application/json;charset=utf-8',
		success: function(result) {
			if (result.code == "000") {
				success(result);
			} else {
				fail(result.message);
			}
		},
		dataType: "json",
		error: function(error) {
			fail(error);
		}
	};
	$.ajax(options);
}

/* 合并对象，使用 dist 覆盖 src */
function mergeObject(src, dist) {

	if (!isObject(src) || !isObject(dist)) {
		return;
	}
	for (var key in dist) {
		src[key] = dist[key];
	}
}

/* 判断是否是 javascript 对象 */
function isObject(obj) {
	return Object.prototype.toString.call(obj) == "[object Object]";
}

/* 判断是否是 javascript 对象 */
function isArray(arr) {
	return Object.prototype.toString.call(arr) == "[object Array]";
}

/* build query url */
function buildQueryUrl(url, params) {
	if (url.indexOf("?") == -1) {
		url += "?" + $.param(params);
	} else {
		url += "&" + $.param(params);
	}
	return url;
};

/**
 * 成功提示
 * @param message
 * @param callback 弹框回调函数
 * @param time
 */
function messageOk(message, callback, time) {
	if (callback == 'reload') {
		callback = function () {
			location.reload();
		}
	} else if (typeof  callback == 'string') {
		callback = function (c) {
			return function () {
				location.href = c;
			}
		}(callback);
	}
	time = time ? time : 2000;
	JDialog.msg({
		type: "ok",
		content: message,
		timer: time,
		offset: "tc",
		callback: callback
	});
}
// 失败提示
function messageError(message, callback) {
	JDialog.msg({
		type: "error",
		content: message,
		timer: 2000,
		offset: "tc",
		callback: callback
	});
	return false;
}
function messageInfo(message, callback) {
	JDialog.msg({
		type: "info",
		content: message,
		timer: 1500,
		offset: "tc",
		callback: callback
	});
	return false;
}
// 加载提示
function messageLoading() {
	return JDialog.msg({
		type:'loading',
		content : '正在处理中，请稍后...',
		timer: 0,
		offset: "tc",
		lock : true
	});
}

/**
 * 获取表单数据
 * @param formId
 * @returns {{}}
 */
function getFormData(formId) {
	var data = {};
	$.each($('#'+formId).serializeArray(), function(i,item) {
		data[item.name] = item.value;
	});
	return data;
}

/**
 * 拷贝对象
 * @param src
 */
function copyObj(src) {
	var dist = {};
	for (var key in src) {
		dist[key] = src[key];
	}
	return dist;
}

/**
 * 删除数据中的某个元素
 * @param arr
 * @param value
 * @param comparator
 */
function removeArrayElement(arr, value, comparator) {
	var index = arrayIndexOf(arr, value, comparator);
	if (index > -1) {
		arr.splice(index, 1);
	}
}
function arrayIndexOf(arr, val, comparator) {
	for (var i = 0; i < arr.length; i++) {
		if (comparator(arr[i], val)) return i;
	}
	return -1;
};

