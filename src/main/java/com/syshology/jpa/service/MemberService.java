package com.syshology.jpa.service;

import com.syshology.jpa.entity.Member;
import com.syshology.jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) throw  new IllegalStateException("이미 존재하는 회원 입니다.");
    }
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    public Member findOne(Long id){
        return memberRepository.findOne(id).orElseThrow(() -> new NullPointerException("회원이 존재하지 않습니다."));
    }
}