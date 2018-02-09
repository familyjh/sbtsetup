package com.itembay.elmo.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class AccountControllerTest {

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void createAccount() throws Exception {

        AccountDto.Create createDto = new AccountDto.Create();
        createDto.setUserName("elmop");
        createDto.setPassword("12341234");

        // 계정 생성 테스트
        ResultActions result = mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)));

        result.andDo(print());
        result.andExpect(status().isCreated());

        // TODO JSON PATH
        //result.andExpect(jsonPath("$.userName", is("elmop")));

        // 계정 생성 시 중복 테스트
        result = mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)));

        result.andDo(print());
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void createAccount_BadRequest() throws Exception {
        AccountDto.Create createDto = new AccountDto.Create();
        createDto.setUserName("  ");
        createDto.setPassword("1234");

        ResultActions result = mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)));

        result.andDo(print());
        result.andExpect(status().isBadRequest());
    }

}
