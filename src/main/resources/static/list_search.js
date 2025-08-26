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


function onChange(roleId, checkBox) {
	if (roleId == 0) { //전체

		console.log("checkBox :: " + $(checkBox).prop("checked"));

		if ($(checkBox).prop("checked")) {
			$('.role').prop('checked', true);
		} else {
			$('.role').prop('checked', false);
		}
	}

}

$(document).ready(function() {

	//    $("input[type='checkbox'].allrole").on('change', function() {
	//        // this.checked는 현재 체크박스의 체크 여부를 반환합니다.
	//        
	//        $("input[type='checkbox'].role")
	//        
	//        if (this.checked) {
	//            console.log("체크박스가 체크되었습니다.");
	//            // 여기서 원하는 로직을 실행합니다.
	////        } else {
	//            console.log("체크박스가 해제되었습니다.");
	//            // 여기서 원하는 로직을 실행합니다.
	//        }
	//    });
});