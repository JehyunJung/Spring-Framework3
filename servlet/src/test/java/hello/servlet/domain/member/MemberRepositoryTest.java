package hello.servlet.domain.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {
    MemberRepository memberRepository=MemberRepository.getInstance();

    @AfterEach
    void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void save(){
        //given
        Member member=new Member("hello",20);
        //when
        Member saved=memberRepository.save(member);

        //then
        assertThat(saved).isEqualTo(member);
    }
    @Test
    void findAll(){
        //given
        Member member1=new Member("hello1",20);
        Member member2=new Member("hello2",30);

        //when

        Member saved1=memberRepository.save(member1);
        Member saved2=memberRepository.save(member2);

        //then
        List<Member> results= memberRepository.findAll();
        assertThat(results.size()).isEqualTo(2);
        assertThat(results).contains(member1,member2);
    }

}