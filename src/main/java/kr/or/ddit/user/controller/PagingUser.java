//package kr.or.ddit.user.controller;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import kr.or.ddit.common.model.PageVo;
//import kr.or.ddit.user.model.UserVo;
//import kr.or.ddit.user.service.UserService;
//import kr.or.ddit.user.service.UserServiceI;
//
//@WebServlet("/pagingUser")
//public class PagingUser extends HttpServlet{
//	private static final Logger logger = LoggerFactory.getLogger(PagingUser.class);
//	private UserServiceI userService = new UserService();
//	
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		
//		int page = 1;
//		
//		page = req.getParameter("page") == null ? 1: Integer.parseInt(req.getParameter("page"));
//		
//		int pageSize = 0;
//		
//		if(req.getParameter("pageSize") == null || req.getParameter("pageSize") == "" || Integer.parseInt(req.getParameter("pageSize"))==5) {
//			pageSize = 5;
//		}else if(Integer.parseInt(req.getParameter("pageSize"))==3) {
//			pageSize = 3;
//		}else {
//			pageSize = 7;
//		}
//		
//		
//		String field = req.getParameter("field");;
//		String keyword = req.getParameter("keyword");
//		
//		if(req.getParameter("field")!=null && req.getParameter("keyword")!=null) {
//			
//			Map<String, Object> map1 = new HashMap<>();
//			if(field.equals("i")) {
//				map1.put("field", "userid");
//			}else if(field.equals("m")) {
//				map1.put("field", "usernm");
//			}else {
//				map1.put("field", "alias");
//			}
//			map1.put("data", keyword);
//			map1.put("page", page);
//			map1.put("pageSize", pageSize);
//			
//			PageVo pageVo = new PageVo(page, pageSize);
//			
//			Map<String, Object> map2 = userService.selectPagingSearchUser(map1);
//			
//			List<UserVo> userSearchList = (List<UserVo>)map2.get("userSearchList");
//			
//			int userCnt = (int)map2.get("userSearchCnt");
//			int pagination = (int)Math.ceil((double) userCnt/ pageSize);
//			
//			
//			
//			req.setAttribute("userList", userSearchList);
//			req.setAttribute("pagination", pagination);
//			req.setAttribute("pageVo", pageVo);
//			
//			req.getRequestDispatcher("/memberList.jsp").forward(req, resp);
//		}
//		else {
//		
//			PageVo pageVo = new PageVo(page, pageSize);
//			
//			Map<String, Object> map = userService.selectPagingUser(pageVo);
//			
//			List<UserVo> userList = (List<UserVo>)map.get("userList");
//			
//			int userCnt = (int)map.get("userCnt");
//			int pagination = (int)Math.ceil((double) userCnt/ pageSize);
//			
//			req.setAttribute("userList", userList);
//			req.setAttribute("pagination", pagination);
//			req.setAttribute("pageVo", pageVo);
//			
//			req.getRequestDispatcher("/memberList.jsp").forward(req, resp);
//		}
//	}
//	
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		doGet(req, resp);
//	}
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
