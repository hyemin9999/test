package com.woori.codenova;

import java.util.List;

import org.springframework.web.servlet.HandlerInterceptor;

import com.woori.codenova.entity.Category;
import com.woori.codenova.repository.CategoryRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CategoryInterceptor implements HandlerInterceptor {

	private final CategoryRepository categoryRepository;

	public CategoryInterceptor(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	private List<Category> getCategoryList() {
		return categoryRepository.findAllByName();
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		List<Category> menuList = getCategoryList();
		request.setAttribute("menus", menuList);

		String url = request.getRequestURI();

		System.out.println("===============================================");
		System.out.println("==================== BEGIN ====================");
		System.out.println("Request URI ===> " + request.getRequestURI());
		if (url.startsWith("/board/") || url.startsWith("/admin/board/") || url.startsWith("/admin/notice/")) {

			String[] str = url.split("/");
			if (str.length > 0) {

				request.setAttribute("cid", str[str.length - 1]);
				System.out.println("cidcidcid :: " + str[str.length - 1]);

//				Integer cid = Integer.parseInt(str[str.length - 1]);
//				if (url.startsWith("/board/")) {
//					Category item = menuList.stream().filter(o -> o.getId().equals(cid)).findAny().orElse(null);
//					request.setAttribute("menuName", item.getName());
//				}
//				request.setAttribute("cid", cid);
//				System.out.println("cidcidcid :: " + cid);
			}

		}

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//
//		System.out.println("==================== END ======================");
//		System.out.println("===============================================");
//
////		log.debug("==================== END ======================");
////		log.debug("===============================================");
//		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//	}
//	
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//		System.out.println("응답이 완료된 후에 처리할 작업");
//	}

}