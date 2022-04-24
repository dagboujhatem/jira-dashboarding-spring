package com.vermeg.controllers;

import com.vermeg.exceptions.BadRequestException;
import com.vermeg.payload.requests.ForgotPasswordRequest;
import com.vermeg.payload.requests.ResetPasswordRequest;
import com.vermeg.payload.responses.ApiResponse;
import com.vermeg.payload.responses.TokenResponse;
import com.vermeg.service.impl.ForgotPasswordServiceImpl;
import com.vermeg.utils.Email;
import com.vermeg.utils.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ForgotPasswordController {

    @Autowired
    ForgotPasswordServiceImpl forgotPasswordService;

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "forgot-password", method = RequestMethod.POST)
    public ApiResponse<TokenResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) throws MessagingException, BadRequestException {
        forgotPasswordService.forgotPassword(forgotPasswordRequest.getEmail());
        String messageResponse = messageSource.getMessage("common.forgotPassword",
                null, LocaleContextHolder.getLocale());
        return new ApiResponse<>(200, messageResponse,null);
    }

    @RequestMapping(value = "reset-password", method = RequestMethod.POST)
    public ApiResponse<TokenResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest){
        String messageResponse = messageSource.getMessage("common.resetPassword",
                null, LocaleContextHolder.getLocale());
        return new ApiResponse<>(200, messageResponse,null);
    }

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/email/send/html")
    public void sendHtmlMessage(@RequestBody Email email) throws MessagingException {
        emailSenderService.sendHtmlMessage(email);
    }

    @PostMapping("email/send")
    public void sendSimpleMessage(@RequestBody Email email) {
        emailSenderService.sendSimpleMessage(email);
    }
}
