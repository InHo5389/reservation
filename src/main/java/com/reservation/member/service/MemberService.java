package com.reservation.member.service;

import com.reservation.member.dto.MemberDto;
import com.reservation.member.entity.Member;
import com.reservation.member.repository.MemberRepository;
import com.reservation.member.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveMember(MemberDto parameter, PasswordEncoder passwordEncoder){
        Member findMember = memberRepository.findByEmail(parameter.getEmail());
        if (findMember!=null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
        String password = passwordEncoder.encode(parameter.getPassword());
        Member member = Member.builder()
                .name(parameter.getName())
                .email(parameter.getEmail())
                .password(password)
                .address(parameter.getAddress())
                .role(Role.USER)
                .build();
        return memberRepository.save(member);
    }
}
