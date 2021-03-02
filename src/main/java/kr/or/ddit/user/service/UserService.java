package kr.or.ddit.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.user.model.UserVo;
import kr.or.ddit.user.repository.UserDao;
import kr.or.ddit.user.repository.UserDaoI;

@Service("userService")
public class UserService implements UserServiceI{
	
	@Resource(name="userDao")
	private UserDao userDao;
	
	public UserService() {}
	
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public List<UserVo> selectAllUser() {
		return userDao.selectAllUser();
	}

	@Override
	public UserVo selectUser(String userid) {
		return userDao.selectUser(userid);
	}

	@Override
	public Map<String, Object> selectPagingUser(PageVo pageVo) {
		Map<String, Object> map = new HashMap<>();
		
		List<UserVo> userList = userDao.selectPagingUser(pageVo);
		int userCnt = userDao.selectAllCnt();
		
		map.put("userList", userList);
		map.put("userCnt", userCnt);
		
		return map;	
	}
	
	@Override
	public Map<String, Object> selectPagingSearchUser(Map<String, Object> search) {
		Map<String, Object> map = new HashMap<>();
		
		List<UserVo> userSearchList = userDao.selectPagingSearchUser(search);
		
		Map<String, Object> map1 = new HashMap<>();
		map1.put("field", search.get("field"));
		map1.put("data", search.get("data"));
		System.out.println(map1.get("data"));
		System.out.println(map1.get("field"));
		int userSearchCnt = userDao.selectPagingSearchUserCnt(map1);
		
		map.put("userSearchList", userSearchList);
		map.put("userSearchCnt", userSearchCnt);
		
		return map;
	}


	@Override
	public int modifyUser(UserVo userVo) {
		return userDao.modifyUser(userVo);
	}

	@Override
	public int insertUser(UserVo userVo) {
		return userDao.insertUser(userVo);
	}

	@Override
	public int deleteUser(String userid) {
		return userDao.deleteUser(userid);
	}

	@Override
	public int selectAllCnt() {
		return userDao.selectAllCnt();
	}

	

	
	
}
