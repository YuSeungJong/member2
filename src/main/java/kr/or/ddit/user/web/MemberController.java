package kr.or.ddit.user.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.user.model.UserVo;
import kr.or.ddit.user.service.UserServiceI;
import kr.or.ddit.util.FileUtil;

@RequestMapping("member")
@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Resource(name = "userService")
	private UserServiceI userService;
	
	@RequestMapping(path = "view", method = RequestMethod.GET)
	public String view() {
		return "login";
	}
	
	@RequestMapping(path="loginController", method=RequestMethod.POST)
	public String loginController(String userid, String pass, HttpSession session) {
		
		UserVo user = userService.selectUser(userid);
		
		if(user !=null && pass.equals(user.getPass())) {
			session.setAttribute("S_USER", user);
			return "redirect:/member/pagingUser";
		}else {
			return "login";
		}
	}
	
	@RequestMapping(path="pagingUser")
	public String pasingUser(PageVo pageVo, Model model, @RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "5") int pageSize
			, String field, String keyword) {
		
		pageVo.setPage(page);
		pageVo.setPageSize(pageSize);
		
		if(field!=null && keyword!=null) {
			Map<String, Object> map1 = new HashMap<>();
			if(field.equals("i")) {
				map1.put("field", "userid");
			}else if(field.equals("m")) {
				map1.put("field", "usernm");
			}else {
				map1.put("field", "alias");
			}
			map1.put("data", keyword);
			map1.put("page", page);
			map1.put("pageSize", pageSize);
			
			Map<String, Object> map2 = userService.selectPagingSearchUser(map1);
			
			List<UserVo> userSearchList = (List<UserVo>)map2.get("userSearchList");
			
			int userCnt = (int)map2.get("userSearchCnt");
			int pagination = (int)Math.ceil((double) userCnt/ pageSize);
			
			model.addAttribute("userList", userSearchList);
			model.addAttribute("pagination", pagination);
			model.addAttribute("pageVo", pageVo);
			model.addAttribute("keyword", keyword);
			model.addAttribute("field", field);
			
			return "user/pagingUser";
		}
		
		else {
		
		Map<String, Object> map = userService.selectPagingUser(pageVo);
		
		List<UserVo> userList = (List<UserVo>)map.get("userList");
		
		int userCnt = (int)map.get("userCnt");
		logger.debug("userCnt : {}" ,userCnt);
		logger.debug("pageVo.getPageSize() : {}" , pageVo.getPageSize());
		int pagination = (int)Math.ceil((double)userCnt/pageVo.getPageSize());
		
		model.addAttribute("userList", userList);
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageVo", pageVo);
		
		return "user/pagingUser"; 
	}
	}	
	
	@RequestMapping(path="registUser",  method = RequestMethod.GET)
	public String registUser() {
		return "user/registUser";
	}
	
	@RequestMapping(path="registUser", method=RequestMethod.POST)
	public String registUser(@Valid UserVo uservo, BindingResult result, Model model, MultipartFile profile) {
		
		try {
			String fileExtension = FileUtil.getFileExtension(profile.getOriginalFilename());
			String realFileName = UUID.randomUUID().toString()+fileExtension;
			
			profile.transferTo(new File("d:/upload/" + realFileName));
			uservo.setFilename(profile.getOriginalFilename());
			
			uservo.setRealfilename(realFileName);
			
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		int insertCnt = 0;
		
		try {
			insertCnt = userService.insertUser(uservo);
		} catch (Exception e) {
			insertCnt= 0;
		}
		
		
		if(insertCnt==1) {
			return "redirect:/member/pagingUser";
		}else {
			model.addAttribute("user", uservo);
			return "user/registUser";
		}
		
	}
	
	
	@RequestMapping("userInfo")
	public String userInfo(String userid, Model model) {
		
		model.addAttribute("user", userService.selectUser(userid));
		
		
		return "user/user";
	}
	
	@RequestMapping("profile")
	public void profile(HttpServletResponse resp, String userid, HttpServletRequest req) {

		resp.setContentType("image");
		
		UserVo userVo = userService.selectUser(userid);
		
		String path = "";
		if(userVo.getRealfilename() == null) {
			path = req.getServletContext().getRealPath("/image/unknown.png");
		}else {
			path = userVo.getRealfilename();
		}
		logger.debug("path : {} ", path);
		
		try {
			FileInputStream fis = new FileInputStream("d:/upload/" + path);
			ServletOutputStream sos = resp.getOutputStream();
			
			byte[] buff = new byte[512];
			while(fis.read(buff)!=-1) {		
				sos.write(buff);	
			}
			
			fis.close();
			sos.flush();
			sos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(path="deleteUser",method = RequestMethod.POST)
	public String deleteUser(String userid, RedirectAttributes ra) {
		
		int deleteCnt = 0;
		
		try {
			deleteCnt = userService.deleteUser(userid);
		} catch (Exception e) {
			e.printStackTrace();
			deleteCnt= 0;
		}
		
		if(deleteCnt==1) {
			return "redirect:/member/pagingUser";
		}else {
			ra.addAttribute("userid", userid);
			return "redirect:member/user";
		}
		
		
	}
	
	
	
	@RequestMapping(path="modifyUser",method = RequestMethod.GET)
	public String modifyUserView(String userid, Model model) {
		
		model.addAttribute("user", userService.selectUser(userid));
		
		return "user/modifyUser";
	}
	
	@RequestMapping(path="modifyUser",method = RequestMethod.POST)
	public String modifyUser(UserVo userVo, Model model,RedirectAttributes ra, MultipartFile profile) {
		userVo.setFilename("");
		UserVo vo = userService.selectUser(userVo.getUserid());
		
		
		if(userVo.getFilename()==null) {
			userVo.setFilename(vo.getFilename());
			
			if(userVo.getFilename()==null) {
				userVo.setFilename("");
			}
		}
		
		
		if(userVo.getRealfilename()==null) {
			userVo.setRealfilename(vo.getRealfilename());
			
			if(userVo.getRealfilename()==null) {
				userVo.setRealfilename("");
			}
		}
		
		
		try {
			String fileExtension = FileUtil.getFileExtension(profile.getOriginalFilename());
			String realFileName = UUID.randomUUID().toString()+fileExtension;
			
			profile.transferTo(new File("d:/upload/" + realFileName));
			userVo.setFilename(profile.getOriginalFilename());
			
			userVo.setRealfilename(realFileName);
			
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		int updateCnt = 0;
		
		try {
			updateCnt = userService.modifyUser(userVo);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			updateCnt= 0;
		}
		
		 logger.debug("updateCnt : {}", updateCnt);
		
		if(updateCnt==1) {
			ra.addAttribute("userid", userVo.getUserid());
			return "redirect:/member/userInfo";
		}else {
			model.addAttribute("user", userVo);
			return "user/modifyUser";
		}
		
	}
	
	 
	
	
}


















