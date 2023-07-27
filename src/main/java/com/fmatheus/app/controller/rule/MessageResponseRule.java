package com.fmatheus.app.controller.rule;


import com.fmatheus.app.controller.enumerable.MessagesEnum;
import com.fmatheus.app.controller.exception.BadRequestException;
import com.fmatheus.app.controller.exception.ForbiddenException;
import com.fmatheus.app.controller.exception.handler.MessageResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public class MessageResponseRule {
    @Autowired
    private MessageSource messageSource;


    public MessageResponseHandler messageResponse(MessagesEnum messagesEnum) {
        String message = this.messageSource.getMessage(messagesEnum.getMessage(), (Object[]) null, LocaleContextHolder.getLocale());
        return new MessageResponseHandler(messagesEnum, messagesEnum.getHttpSttus().getReasonPhrase(), message);
    }

    public MessageResponseHandler messageSuccessUpdate() {
        return this.messageResponse(MessagesEnum.SUCCESS_UPDATE);
    }

    public MessageResponseHandler messageSuccessCreate() {
        return this.messageResponse(MessagesEnum.SUCCESS_CREATE);
    }

    public MessageResponseHandler messageSuccessDelete() {
        return this.messageResponse(MessagesEnum.SUCCESS_DELETE);
    }

    public BadRequestException errorBadRequest(MessagesEnum messagesEnum) {
        return new BadRequestException(messagesEnum);
    }

    public BadRequestException errorExistDocument() {
        return new BadRequestException(MessagesEnum.ERROR_EXIST_DOCUMENT);
    }

    public BadRequestException errorExistEmail() {
        return new BadRequestException(MessagesEnum.ERROR_EXIST_EMAIL);
    }

    public BadRequestException errorRecordNotExist() {
        return new BadRequestException(MessagesEnum.ERROR_RECORD_NOT_EXIST);
    }

    public BadRequestException errorExistRecord() {
        return new BadRequestException(MessagesEnum.ERROR_EXIST_RECORD);
    }

    public ForbiddenException errorForbidden() {
        return new ForbiddenException(MessagesEnum.ERROR_FORBIDDEN.getMessage());
    }

    public DataIntegrityViolationException errorDataIntegrityViolationException() {
        throw new DataIntegrityViolationException(MessagesEnum.ERROR_DATA_INTEGRITY_VIOLATION.getMessage());
    }
}
