package com.reservation.member.service;

import com.reservation.member.dto.MemberDto;
import com.reservation.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(){
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail("qheo@naver.com");
        memberDto.setName("정인호");
        memberDto.setAddress("서울시 마포구 합정동");
        memberDto.setPassword("1234");
        return Member.createMember(memberDto,passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    void saveMember() {
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);

        assertEquals(member.getEmail(),savedMember.getEmail());
        assertEquals(member.getName(),savedMember.getName());
        assertEquals(member.getAddress(),savedMember.getAddress());
        assertEquals(member.getPassword(),savedMember.getPassword());
        assertEquals(member.getRole(),savedMember.getRole());
    }

    @Test
    @DisplayName("중복 회원가입 테스트")
    public void saveDuplicateMemberTest(){
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.saveMember(member1);

        Throwable e = assertThrows(IllegalStateException.class,()->{
                memberService.saveMember(member2);});
        assertEquals("이미 가입된 회원입니다.",e.getMessage());
    }
}