package com.itembay.elmo.accounts;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private ModelMapper modelMapper;


    public Account createAccount(AccountDto.Create dto) {
        //Account account = new Account();
        //account.setUserName(dto.getUserName());
        //account.setPassword(dto.getPassword());

        Account account = modelMapper.map(dto, Account.class);

        // TODO 유효한 userName인지 판단
        String userName = dto.getUserName();
        if (repository.findByUserName(userName) != null) {
            // 에러가 발생했을 때 Controller에 Exception 던지는게 방식이 좋음.
            // 에러처리에 대한 코드도 없고 코드가 깔끔함.
            throw new UserDuplicatedException(userName);
        }


        // TODO password 해싱

        Date now = new Date();
        account.setJoined(now);
        account.setUpdated(now);

        return repository.save(account);
    }
}
