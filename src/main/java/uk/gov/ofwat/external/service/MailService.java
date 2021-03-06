package uk.gov.ofwat.external.service;

import uk.gov.ofwat.external.domain.RegistrationRequest;
import uk.gov.ofwat.external.domain.User;

import io.github.jhipster.config.JHipsterProperties;

import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import uk.gov.ofwat.external.repository.NotifyMessageTemplateRepository;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Locale;

/**
 * Service for sending emails.
 * <p>
 * We use the @Async annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";

    private static final String REGISTRATION_REQUEST = "registrationRequest";

    private static final String BASE_URL = "baseUrl";

    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    private final NotifyMessageTemplateRepository notifyMessageTemplateRepository;

    private final NotifyService notifyService;

    public MailService(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender,
                       MessageSource messageSource, SpringTemplateEngine templateEngine, NotifyMessageTemplateRepository notifyMessageTemplateRepository, NotifyService notifyService) {

        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
        this.notifyMessageTemplateRepository = notifyMessageTemplateRepository;
        this.notifyService = notifyService;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.warn("Email could not be sent to user '{}'", to, e);
            } else {
                log.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
            }
        }
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        sendMailFromContext(context, locale, templateName, titleKey, user.getEmail());

/*
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
*/

    }

    @Async
    public void sendEmailFromTemplate(RegistrationRequest registrationRequest, String langKey, String templateName, String titleKey) {
        Locale locale = Locale.forLanguageTag(langKey);
        Context context = new Context(locale);
        context.setVariable(REGISTRATION_REQUEST, registrationRequest);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        sendMailFromContext(context, locale, templateName, titleKey, registrationRequest.getEmail());
/*

        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(registrationRequest.getEmail(), subject, content, false, true);
*/

    }

    private void sendMailFromContext(Context context, Locale locale, String templateName, String titleKey, String email){
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(email, subject, content, false, true);
    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        notifyMessageTemplateRepository.findOneByName("Account activation user").map(notifyMessageTemplate -> {
            HashMap<String, String> personalisation = new HashMap<String, String>();
            //context.setVariable(REGISTRATION_REQUEST, registrationRequest);
            String baseUrl = jHipsterProperties.getMail().getBaseUrl();
            String url = baseUrl  + "/#/activate?key=" + user.getActivationKey();
            personalisation.put("name", user.getLogin());
            personalisation.put("url", baseUrl);
            notifyService.sendMessage(user, notifyMessageTemplate, personalisation);
            return notifyMessageTemplate;
        });
        /*sendEmailFromTemplate(user, "activationEmail", "email.activation.title");*/
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        //sendEmailFromTemplate(user, "passwordResetEmail", "email.reset.title");
        notifyMessageTemplateRepository.findOneByName("Password reset user").map(notifyMessageTemplate -> {
            HashMap<String, String> personalisation = new HashMap<String, String>();
            String baseUrl = jHipsterProperties.getMail().getBaseUrl();
            String url = baseUrl  + "/#/reset/finish?key=" + user.getResetKey();
            personalisation.put("login", user.getLogin());
            personalisation.put("url", url);
            personalisation.put("email", user.getEmail());
            notifyService.sendMessage(user, notifyMessageTemplate, personalisation);
            return notifyMessageTemplate;
        });
    }

    @Async
    public void sendRegistrationRequestUserEmail(RegistrationRequest registrationRequest){
        log.debug("Sending registration request user email to '{}'", registrationRequest.getEmail());
        //sendEmailFromTemplate(registrationRequest, "en",  "registrationRequestUserEmail", "email.registration_request_user.title");
        notifyMessageTemplateRepository.findOneByName("Account request user").map(notifyMessageTemplate -> {
            HashMap<String, String> personalisation = new HashMap<String, String>();
            personalisation.put("login", registrationRequest.getLogin());
            personalisation.put(NotifyService.EMAIL, registrationRequest.getEmail());
            notifyService.sendMessage(notifyMessageTemplate, personalisation);
            return notifyMessageTemplate;
        });
    }

    @Async
    public void sendRegistrationRequestAdminEmail(RegistrationRequest registrationRequest, User adminUser){
        log.debug("Sending registration request admin email to '{}'", adminUser.getEmail());
        //sendEmailFromTemplate(registrationRequest, "en",  "registrationRequestAdminEmail", "email.registration_request_admin.title");
        notifyMessageTemplateRepository.findOneByName("Account request admin").map(notifyMessageTemplate -> {
            HashMap<String, String> personalisation = new HashMap<String, String>();
            personalisation.put("login", registrationRequest.getLogin());
            personalisation.put(NotifyService.EMAIL, adminUser.getEmail());
            notifyService.sendMessage(notifyMessageTemplate, personalisation);
            return notifyMessageTemplate;
        });
    }

    @Async
    public void sendRegistrationRequestApprovalEmail(RegistrationRequest registrationRequest){
        log.info("Sending registration request approval email to '{}'", registrationRequest.getEmail());
        //sendEmailFromTemplate(registrationRequest, "en",  "registrationRequestApprovalEmail", "email.registration_request_approval.title");
        String baseUrl = jHipsterProperties.getMail().getBaseUrl();
        notifyMessageTemplateRepository.findOneByName("Account activation user").map(notifyMessageTemplate -> {
            HashMap<String, String> personalisation = new HashMap<String, String>();
            personalisation.put("name", registrationRequest.getLogin());
            personalisation.put(NotifyService.EMAIL, registrationRequest.getEmail());
            String url = baseUrl + "/#/register?key=" + registrationRequest.getRegistrationKey();
            personalisation.put("url", url);
            notifyService.sendMessage(notifyMessageTemplate, personalisation);
            return notifyMessageTemplate;
        });
    }


}
