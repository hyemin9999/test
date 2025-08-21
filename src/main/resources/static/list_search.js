/**
 * 목록 페이지 페이징과 검색 기능
 */
const page_elements = document.getElementsByClassName("page-link");
Array.from(page_elements).forEach(function(element) {
	element.addEventListener('click', function() {
		document.getElementById('page').value = this.dataset.page;
		document.getElementById('searchForm').submit();
	});
});
const btn_search = document.getElementById("btn_search");
btn_search.addEventListener('click', function() {
	document.getElementById('kw').value = document
		.getElementById('search_kw').value;
	document.getElementById('page').value = 0;
	document.getElementById('searchForm').submit();
});
const search_kw = document.getElementById("search_kw");
search_kw.addEventListener('keypress', function() {
	if (event.keyCode === 13) {
		document.getElementById('kw').value = this.value;
		document.getElementById('page').value = 0;
		document.getElementById('searchForm').submit();
	}
});