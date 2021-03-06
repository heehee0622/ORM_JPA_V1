package com.syshology.jpa.service;

import com.syshology.jpa.entity.Member;
import com.syshology.jpa.repository.MemberRepository;
import com.syshology.jpa.service.MemberService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 회원가입(){
        //Given
        Member member = Member.builder().name("kim").build();
        //when
        Long savedId = memberService.join(member);
        //then
        Assert.assertEquals(member, memberRepository.findOne(savedId).orElseGet(Member::new));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
        //Given
        Member member1 = Member.builder().name("kim").build();
        Member member2 = Member.builder().name("kim").build();
        //When
        memberService.join(member1);
        memberService.join(member2);
        //Then
        Assert.fail("예외가 발생해야 한다.");
    }

    @Test
    public void 모두조회(){
        memberRepository.findOne(1L);
    }
    @Test
    public void 모두조회WithJpql(){
        memberRepository.findOneWithJpql("userA");
    }
    @Test
    @Commit
    public void 저장(){
        Member member = Member.builder().name("야옹").build();
        System.out.println("==========================");
        memberRepository.save(member);
        System.out.println("==========================");
    }

}
