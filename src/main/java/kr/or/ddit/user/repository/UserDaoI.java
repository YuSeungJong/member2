package kr.or.ddit.user.repository;

import java.util.List;
import java.util.Map;

import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.user.model.UserVo;

public interface UserDaoI {
	
	// 전체 사용자 정보 조회
	/* SELECT *
	 * FROM users;
	 */
	
	List<UserVo> selectAllUser();
	
	//userid에 해당하는 사용자 한명의 정보 조회
	UserVo selectUser(String userid);
	
	//사용자 페이징 조회
	List<UserVo> selectPagingUser(PageVo pageVo);
	
	//사용자 페이징 검색
	List<UserVo> selectPagingSearchUser(Map<String, Object> search);
	
	//검색 조건 수
	int selectPagingSearchUserCnt(Map<String, Object> map);
	
	//사용자 전체 수 조회
	int selectAllCnt();
	
	//member 전체 수 조회
	int selectAllMemCnt();
	
	//사용자 정보 수정
	int modifyUser(UserVo userVo);
	
	//사용자 정보 추가
	int insertUser(UserVo userVo);

	int deleteUser(String userid);
}

























