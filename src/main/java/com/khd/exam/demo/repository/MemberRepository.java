package com.khd.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.khd.exam.demo.vo.Member;

@Mapper
public interface MemberRepository { // class는 사용 불가능 100% 추상메서드인 interface로 바꿔야 사용가능 , 모든 구현부 제거
	
	@Insert("""
			INSERT INTO `member`
			SET regDate = NOW(),
			updateDate = NOW(),
			loginId = #{loginId},
			loginPw = #{loginPw},
			`name` = #{name},
			nickname = #{nickname},
			cellphoneNum = #{cellphoneNum},
			email = #{email}
			""")
	public void doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email);
	
	@Select("SELECT LAST_INSERT_ID()")
	public int getLastInsertId();

	@Select("""
			SELECT *
			FROM `member`
			WHERE id = #{id}
			""")
	public Member getMemberById(int id);
	
	@Select("""
			SELECT *
			FROM `member`
			WHERE loginId = #{id}
			""")
	public Member getMemberByLoginId(String loginId);

	@Select("""
			SELECT *
			FROM `member`
			WHERE name = #{name}
			AND email = #{email}
			""")
	public Member getMemberByNameAndEmail(String name, String email);
	
	@Update("""
			<script>
				UPDATE `member`
					<set>
						updateDate = NOW(),
						<if test="nickname != null">
							nickname = #{nickname},
						</if>
						<if test="cellphoneNum != null">
							cellphoneNum = #{cellphoneNum},
						</if>
						<if test="email != null">
							email = #{email}
						</if>
					</set>
					WHERE id = #{loginedMemberId}
				</script>
			""")
	public void doModify(int loginedMemberId, String nickname, String cellphoneNum, String email);

	@Update("""
			UPDATE `member`
				SET updateDate = NOW(),
				loginPw = #{loginPw}
				WHERE id = #{loginedMemberId}
			""")
	public void doPassWordModify(int loginedMemberId, String loginPw);
	
	@Select("""
			<script>
				SELECT COUNT(*)
					FROM `member`
					WHERE 1 = 1
					<if test="authLevel != 0">
						AND authLevel = #{authLevel}
					</if>
					<if test="searchKeyword != ''">
						<choose>
							<when test="searchKeywordTypeCode == 'loginId'">
								AND loginId LIKE CONCAT('%', #{searchKeyword}, '%')
							</when>
							<when test="searchKeywordTypeCode == 'name'">
								AND name LIKE CONCAT('%', #{searchKeyword}, '%')
							</when>
							<when test="searchKeywordTypeCode == 'nickname'">
								AND nickname LIKE CONCAT('%', #{searchKeyword}, '%')
							</when>
							<otherwise>
								AND (
										loginId LIKE CONCAT('%', #{searchKeyword}, '%')
										OR name LIKE CONCAT('%', #{searchKeyword}, '%')
										OR nickname LIKE CONCAT('%', #{searchKeyword}, '%')
									)
							</otherwise>
						</choose>
					</if>
			</script>
			""")
	public int getMembersCount(String authLevel, String searchKeywordTypeCode, String searchKeyword);

	@Select("""
			<script>
				SELECT *
					FROM `member`
					WHERE 1 = 1
					<if test="authLevel != 0">
						AND authLevel = #{authLevel}
					</if>
					<if test="searchKeyword != ''">
						<choose>
							<when test="searchKeywordTypeCode == 'loginId'">
								AND loginId LIKE CONCAT('%', #{searchKeyword}, '%')
							</when>
							<when test="searchKeywordTypeCode == 'name'">
								AND name LIKE CONCAT('%', #{searchKeyword}, '%')
							</when>
							<when test="searchKeywordTypeCode == 'nickname'">
								AND nickname LIKE CONCAT('%', #{searchKeyword}, '%')
							</when>
							<otherwise>
								AND (
										loginId LIKE CONCAT('%', #{searchKeyword}, '%')
										OR name LIKE CONCAT('%', #{searchKeyword}, '%')
										OR nickname LIKE CONCAT('%', #{searchKeyword}, '%')
									)
							</otherwise>
						</choose>
					</if>
					ORDER BY id DESC
					LIMIT #{limitStart}, #{itemsInAPage}
			</script>
			""")
	public List<Member> getMembers(String authLevel, String searchKeywordTypeCode, String searchKeyword, int limitStart,
			int itemsInAPage);

	@Update("""
			<script>
				UPDATE `member`
					<set>
						updateDate = NOW(),
						delStatus = 1,
						delDate = NOW()
					</set>
					WHERE id = #{id}
			</script>
			""")
	public void deleteMember(int id);
		

}

