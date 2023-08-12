package com.fmatheus.app.controller.rule;


import com.fmatheus.app.controller.enumerable.MessagesEnum;
import com.fmatheus.app.controller.exception.UserInactiveException;
import org.springframework.stereotype.Component;

@Component
public class MessageResponseRule {
    public UserInactiveException errorUserInactiveException() {
        throw new UserInactiveException(MessagesEnum.ERROR_USER_INATIVE.getMessage());
    }

}
