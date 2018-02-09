package com.itembay.elmo.accounts;

import com.itembay.elmo.commons.ErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
//@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService service;

    @Autowired
    private AccountRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    //@PostMapping("")
    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public ResponseEntity createAccount(@RequestBody @Valid AccountDto.Create create, BindingResult result) {

        if (result.hasErrors()) {
            // TODO 에러 응답 본문 추가하기
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("잘못된 요청입니다.");
            errorResponse.setCode("bad.request");
            // TODO BindingResult 안에 들어있는 에러 정보 사용하기.
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Account newAccount = service.createAccount(create);
        // createAccount 메소드가 에러가 발생해도 Service단에서 Exception처리를 했기 때문에
        // Controller에서는 별도 에러처리가 필요하지 않음.
        // Exception을 별도로 처리하고 싶은 경우 아래의 @ExceptionHandler(UserDuplicatedException.class) 같이
        // 잡고싶은 Exception을 Handler로 처리한다.

        return new ResponseEntity<>(modelMapper.map(newAccount, AccountDto.Response.class), HttpStatus.CREATED);
    }

    @ExceptionHandler(UserDuplicatedException.class)
    public ResponseEntity handleUserDuplicatedException(UserDuplicatedException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("[" + e.getUserName() + "] 중복된 userName 입니다.");
        errorResponse.setCode("duplicated.userName.exception");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public ResponseEntity getAccounts(Pageable pageable) {
        Page<Account> page = repository.findAll(pageable);
        List<AccountDto.Response> content = page.getContent().parallelStream()
                .map(account -> modelMapper.map(account, AccountDto.Response.class))
                .collect(Collectors.toList());

        //PageImpl<AccountDto.Response> result = new PageImpl<>(content, page, page.getTotalElements());
        PageImpl<AccountDto.Response> result = new PageImpl<>(content, page, page.getTotalElements());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
