package kr.or.ddit.user.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import kr.or.ddit.user.model.UserVo;
import kr.or.ddit.user.service.UserService;
import kr.or.ddit.user.service.UserServiceI;
import kr.or.ddit.util.FileUtil;

@MultipartConfig
@WebServlet("/userResist")
public class UserResist extends HttpServlet{
	UserServiceI userService = new UserService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/memberRegist.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String userid = req.getParameter("userid");
		String usernm = req.getParameter("usernm");
		String pass = req.getParameter("pass");
		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
//		Date reg_dt = null;
//		
//		try {
//			reg_dt = sdf.parse(req.getParameter("reg_dt"));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		String alias = req.getParameter("alias");
		String addr1 = req.getParameter("addr1");
		String addr2 = req.getParameter("addr2");
		String zipcode = req.getParameter("zipcode");
		
		Part profile = req.getPart("profile");
		String filename = "";
		String realfilename = "";
		if(profile.getSize() > 0) {
			filename = FileUtil.getFileName(profile.getHeader("Content-Disposition"));
			String fileExtension = FileUtil.getFileExtension(filename);
			//brown / brown.png
			realfilename = UUID.randomUUID().toString() + fileExtension;
			
			profile.write("d:\\upload\\" + realfilename);
		}
		
		UserVo userVo = new UserVo(userid, usernm, pass, new Date(), alias, addr1, addr2, zipcode, filename, realfilename );
		
		int insertCnt = 0;
		try {
			insertCnt = userService.insertUser(userVo);
		} catch (Exception e) {
			insertCnt = 0;
		}
		
		System.out.println(insertCnt);
		
		if(insertCnt == 1) {
			resp.sendRedirect(req.getContextPath()+"/pagingUser");
		}
		//사용자 수정이 비정상적으로 된 경우 ==> 해당 사용자의 정보 수정 페이지로 이동
		else {
			req.setAttribute("userInfo", userVo);
			req.getRequestDispatcher("/memberRegist.jsp").forward(req, resp);
		}
	}
}

























