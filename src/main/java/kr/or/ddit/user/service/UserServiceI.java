package kr.or.ddit.user.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.user.model.UserVo;

public interface UserServiceI {
	List<UserVo> selectAllUser();
	
	//userid에 해당하는 사용자 한명의 정보 조회
	UserVo selectUser(String userid);
	
	Map<String, Object> selectPagingUser(PageVo pageVo);
	
	
	//사용자 전체 수 조회
	int selectAllCnt();
	
	int modifyUser(UserVo userVo);
	
	int insertUser(UserVo userVo);
	
	Map<String, Object> selectPagingSearchUser(Map<String, Object> search);

	
	//사용자 삭제
	int deleteUser(String userid);
	
	
}
