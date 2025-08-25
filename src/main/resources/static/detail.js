/**
 * 상세페이지 게시글
 */

const delete_elements = document.getElementsByClassName("delete");
if (delete_elements.length > 0) {
	Array.from(delete_elements).forEach(function(element) {
		element.addEventListener('click', function() {
			if (confirm("정말로 삭제하시겠습니까?")) {
				location.href = this.dataset.uri;
			}
		});
	});
}

const recommend_elements = document.getElementsByClassName("recommend");
Array.from(recommend_elements).forEach(function(element) {
	element.addEventListener('click', function() {
		if (confirm("정말로 추천하시겠습니까?")) {
			location.href = this.dataset.uri;
		}
	});
});

const favorite_elements = document.getElementsByClassName("favorite");
Array.from(favorite_elements).forEach(function(element) {
	element.addEventListener('click', function() {
		if (confirm("정말로 즐겨찾기하시겠습니까?")) {
			location.href = this.dataset.uri;
		}
	});
});

const viewerElement = document.querySelector('#viewer');
if (viewerElement) {
	//	const viewer = new 
	toastui.Editor.factory({
		el: viewerElement,
		viewer: true,
		initialValue: document.querySelector('#ecp1').value
	});
}


$(document).ready(function() {
	const message = /*[[${message}]]*/'';

	if (message.length != 0) {

		$('#message').text(message);
		$('#modal').modal('show');
	}

	$('#modal').on('hide.bs.modal', function(event) {
		console.log('dddd');
		//window.location.href = '/admin/user';
	});
});