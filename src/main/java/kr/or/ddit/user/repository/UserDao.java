package kr.or.ddit.user.repository;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.common.model.PageVo;
import kr.or.ddit.db.MybatisUtil;
import kr.or.ddit.user.model.UserVo;

@Repository("userDao")
public class UserDao implements UserDaoI{
	
	@Resource(name="sqlSessionTemplate")
	private SqlSessionTemplate template;
	
	@Override
	public List<UserVo> selectAllUser() {
		return template.selectList("users.selectAllUser");
	}

	@Override
	public UserVo selectUser(String userid) {
		return template.selectOne("users.selectUser", userid);
	}

	@Override
	public List<UserVo> selectPagingUser(PageVo vo) {
		return template.selectList("users.selectPagingUser", vo);
	}
	
	@Override
	public List<UserVo> selectPagingSearchUser(Map<String, Object> search) {
		return template.selectList("users.selectPagingSearchUser", search);
	}
	
	@Override
	public int selectPagingSearchUserCnt(Map<String, Object> map) {
		return template.selectOne("users.selectPagingSearchUserCnt", map);
	}

	@Override
	public int selectAllCnt() {
		return template.selectOne("users.selectAllCnt");
	}

	@Override
	public int selectAllMemCnt() {
		return template.selectOne("users.selectAllMemCnt");
	}


	@Override
	public int modifyUser(UserVo userVo) {
		
		return template.update("users.modifyUser", userVo);
	}

	@Override
	public int insertUser(UserVo userVo) {
		
		return template.insert("users.insertUser", userVo);
	}

	@Override
	public int deleteUser(String userid) {
		
		return template.delete("users.deleteUser", userid);
	}

	

	

	
	
}





















