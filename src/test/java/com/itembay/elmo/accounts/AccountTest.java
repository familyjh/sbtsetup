package com.itembay.elmo.accounts;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AccountTest {

    @Test
    public void getterSetter() {
        Account account = new Account();
        account.setUserName("elmop");
        account.setPassword("1234");
        assertThat(account.getUserName(), is("elmop"));
    }
}
