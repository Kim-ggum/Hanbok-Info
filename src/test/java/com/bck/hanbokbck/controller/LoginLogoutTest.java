package com.bck.hanbokbck.controller;

import com.bck.hanbokbck.domain.Role;
import com.bck.hanbokbck.entity.MemberEntity;
import com.bck.hanbokbck.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class LoginLogoutTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    public void setUpTest() {
        System.out.println("로그인&아웃 테스트 시작");
    }

    @AfterEach
    void afterEach() {
        System.out.println("로그인&아웃 테스트 끝");
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginByEmailSuccessTest() throws Exception {
        // given
        String email = "test@test.com";
        String pwd = "1234";
        MemberEntity member = MemberEntity.builder().memberEmail(email).memberName("김나나").memberPw(passwordEncoder.encode(pwd)).memberRole(Role.ROLE_USER).build();
        memberRepository.save(member);

        // when, then
        mockMvc.perform(post("/member/signin")
                        .param("memberEmail", email)
                        .param("memberPw", pwd)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection()) // redirect 응답을 받았는지
                .andExpect(redirectedUrl("/hanbok/main")) // redirect 했는지
                .andExpect(authenticated()); // 인증 성공했는지
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginByEmailFailedTest() throws Exception {
        // given
        String email = "test@test.com";
        String pwd = "1234";
        MemberEntity member = MemberEntity.builder().memberEmail(email).memberName("김나나").memberPw(passwordEncoder.encode(pwd)).memberRole(Role.ROLE_USER).build();
        memberRepository.save(member);

        // when, then
        mockMvc.perform(post("/member/signin")
                        .param("memberEmail", email)
                        .param("memberPw", "1235")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection()) // redirect 응답을 받았는지
                .andExpect(redirectedUrl("/member/signinform")) // redirect 했는지
                .andExpect(unauthenticated()); // 인증 실패했는지
    }


    @Test
    @DisplayName("로그아웃 성공 테스트")
    @WithMockUser
    public void logoutSuccessTest() throws Exception {
        // when, then
        mockMvc.perform(post("/member/signout")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/hanbok/main"))
                .andExpect(unauthenticated()); // 인증 실패했는지
    }
}
