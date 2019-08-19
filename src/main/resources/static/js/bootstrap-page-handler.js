/**
 * bootstrap v4.0 分页插件
 * 分页样式，每页记录数，参数过滤
 * @author yangjian
 * @since 18-12-25 上午11:37.
 */
$.fn.extend({
	pageHandler: function(options) {
		options = $.extend({
			total: 0, // 总记录数
			pageNo: 1, // 当前页面
			pageSize: 12, // 每页显示记录数
			align: 'center', // 页码排列方式，默认居中显示
			outputNum: 3, // 输出页码数
			changePage: function(pageNo, pageSize) {}
		}, options);

		// 计算总页数
		var totalPage = Math.ceil(options.total/options.pageSize);
		if (totalPage <= 1) { // 少于一页
			$(this).empty();
			return;
		}
		var root = this;
		render(options.pageNo);

		/**
		 * 渲染分页组件
		 * @param pageNo
		 */
		function render(pageNo) {
			var $pageUL = $('<ul class="pagination"></ul>');
			$pageUL.addClass("justify-content-"+options.align);
			// 输出上一页
			var $pagePrev = $('<li class="page-item"></li>'), $prevIcon = $('<em class="oi oi-chevron-left"></em>'), $pageLink;
			if (pageNo == 1) {
				$pagePrev.addClass('disabled');
				$pageLink = $('<span class="page-link" title="上一页"></span>');
			} else {
				$pageLink = $('<a href="javascript:void(0);" data-page="'+(pageNo - 1)+'" class="page-link" title="上一页"></a>');
				// 绑定上一页事件
				$pageLink.click(function() {
					options.changePage(pageNo-1, options.pageSize);
					render(pageNo-1);
				});
			}
			$pageLink.append($prevIcon);
			$pagePrev.append($pageLink);
			$pageUL.append($pagePrev);
			// 提取要输出的页码
			var pages = [], p = options.outputNum * 2 + 1;
			if (totalPage <= p) { // output all pages
				for (var i = 1; i <= totalPage; i++) {
					pages.push(i);
				}
			} else {
				if (pageNo > 1) { // output first page
					pages.push(1);
				}
				// output left handle of pagination
				if (pageNo > options.outputNum) {
					if (pageNo > options.outputNum + 2) {
						pages.push('dot'); // 加入占位符
					}
					for (var j = pageNo - options.outputNum; j < pageNo; j++) {
						if (j == 1) continue;
						pages.push(j);
					}
				} else {
					for (var j = 2; j < pageNo; j++) {
						pages.push(j);
					}
				}
				// output the right handle of pagination
				if (totalPage > pageNo + options.outputNum+1) {
					for (var j = pageNo; j <= pageNo + options.outputNum; j++) {
						pages.push(j);
					}
					pages.push('dot'); // 加入占位符
				} else {
					for (var j = pageNo; j < totalPage; j++) {
						pages.push(j);
					}
				}
				pages.push(totalPage);
			}

			// 输出页码
			for (var i = 0; i < pages.length; i++) {
				var k = pages[i];
				var $li = $('<li class="page-item"></li>'), $link;
				if (k == 'dot') {
					$li.addClass('disabled');
					$li.append('<span class="page-link"><em class="oi oi-ellipses"></em></span>');
					$pageUL.append($li);
					continue;
				}
				if (k == pageNo) { // 当前页码
					$li.addClass('active');
					$link = $('<span class="page-link">'+k+'</span>');
				} else {
					$link = $('<a class="page-link" href="javascript:void(0);" data-page="'+k+'">'+k+'</a>');
					$link.click(function() {
						var _pageNo = parseInt($(this).attr('data-page'));
						options.changePage(_pageNo, options.pageSize);
						render(_pageNo);
					});
				}
				$li.append($link);
				$pageUL.append($li);
			}
			// 输出下一页
			var $pageNext = $('<li class="page-item"></li>'), $nextIcon = $('<em class="oi oi-chevron-right"></em>'), $pageLinkNext;
			if (pageNo == totalPage) {
				$pageNext.addClass('disabled');
				$pageLinkNext = $('<span class="page-link" title="下一页"></span>');
			} else {
				$pageLinkNext = $('<a href="javascript:void(0);" data-page="'+(pageNo + 1)+'" class="page-link" title="下一页"></a>');
				// 绑定下一页事件
				$pageLinkNext.click(function() {
					options.changePage(pageNo+1, options.pageSize);
					render(pageNo+1);
				});
			}
			$pageLinkNext.append($nextIcon);
			$pageNext.append($pageLinkNext);
			$pageUL.append($pageNext);
			$(root).empty().append($pageUL);
		}
	}
});

