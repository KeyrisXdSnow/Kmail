package com.example.kmail.service;

import com.example.kmail.domain.Email;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.StringUtils;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePartHeader;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class GmailService {

    private static  JsonFactory JSON_FACTORY;
    private static  NetHttpTransport HTTP_TRANSPORT ;
    private static final String MESSAGES_ID_PATH = "D:\\Java\\Project\\Kyrsach\\Kmail\\json\\";
    Gmail gmail;

    public GmailService() throws GeneralSecurityException, IOException {
        JSON_FACTORY =  JacksonFactory.getDefaultInstance();
        HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    }

    public void connectToEmail (Email email) throws GeneralSecurityException, IOException {

        GoogleCredential credential = new GoogleCredential().setAccessToken(email.getAccessToken());

        gmail = new Gmail.Builder(HTTP_TRANSPORT,JSON_FACTORY,credential)
                .setApplicationName("KMail")
                .build();
    }


    private ArrayList<com.example.kmail.domain.Message> getMessages (Email email, List<Message> threadMessages) throws IOException {
        ArrayList<com.example.kmail.domain.Message> resultMessage = new ArrayList<>();

        Long index = 0L ;
        for (Message threadMessage : threadMessages){

            Message mes = gmail.users().messages()
                    .get(email.getEmailId(),threadMessage.getId())
                    .setFormat("full")
                    .execute();

            String from ="", to = "", subject = "", date = "";

            for (MessagePartHeader header: mes.getPayload().getHeaders()) {
                if (header.getName().equals("From")) {from = header.getValue();continue;}
                if (header.getName().equals(("To"))) {to = header.getValue();continue;}
                if (header.getName().equals(("Date"))) {
                    Date d = new Date(header.getValue());
                    DateFormat df = new SimpleDateFormat("EEE, dd MMM yyy hh:mm ");
                    date = (df.format(d));
                    continue;
                }
                if (header.getName().equals(("Subject"))) {subject = header.getValue();continue;}
            }

            resultMessage.add(new com.example.kmail.domain.Message(
                    index++,
                    threadMessage.getId(),
                    from,
                    to,
                    subject,
                    mes.getSnippet(),
                    date));
        }

        return resultMessage;
    }

    public com.example.kmail.domain.Message getFullMessage (Email email, String mesId) throws IOException {

        Message mes = gmail.users().messages()
                .get(email.getEmailId(),mesId)
                .setFormat("full")
                .execute();

        String from ="", to = "", subject = "", date = "", body = "";

        for (MessagePartHeader header: mes.getPayload().getHeaders()) {
            if (header.getName().equals("From")) {from = header.getValue();continue;}
            if (header.getName().equals(("To"))) {to = header.getValue();continue;}
            if (header.getName().equals(("Subject"))) {subject = header.getValue();continue;}
        }

        body = mes.getPayload().getBody().getData();
        if (body == null) {
            body = mes.getPayload().getParts().get(1).getBody().getData();
        }
        body = StringUtils.newStringUtf8(Base64.decodeBase64(body));


        return (new com.example.kmail.domain.Message(to, from, subject, body));

    }



    public ArrayList<com.example.kmail.domain.Message> getMessagesToUser (Email email, Long count) throws IOException, ParseException {

        if (gmail == null) return null;

        List<Message> threadMessages = gmail.users().messages().
                list(email.getEmailId())
                .setMaxResults(count)
                .setQ("To:"+email.getEmailName())
                .execute()
                .getMessages();

        return getMessages(email,threadMessages);
    }

    public ArrayList<com.example.kmail.domain.Message> getMessagesFromUser (Email email, Long count) throws IOException {
        List<Message> threadMessages = gmail.users().messages().
                list(email.getEmailId())
                .setMaxResults(count)
                .setQ("From:"+email.getEmailName())
                .execute()
                .getMessages();

        return getMessages(email,threadMessages);
    }

    public void sendMessage(Email eMail, com.example.kmail.domain.Message message) throws MessagingException, IOException {
        MimeMessage mimeMessage = null ;

        if (message.getAttachedFiles() == null)
            mimeMessage = createEmail(message.getTo(), message.getFrom(),message.getSubject(),message.getBody());
        else mimeMessage = createEmailWithAttachment(message.getTo(), message.getFrom(),
                message.getSubject(),message.getBody(),message.getAttachedFiles().get(0));
        sendPreparedMessage(gmail, eMail.getEmailId(),mimeMessage);
    }


    /**
     * Send an email from the user's mailbox to its recipient.
     *
     * @param service Authorized Gmail API instance.
     * @param userId User's email address. The special value "me"
     * can be used to indicate the authenticated user.
     * @param emailContent Email to be sent.
     * @return The sent message
     * @throws MessagingException
     * @throws IOException
     */
    private static Message sendPreparedMessage(Gmail service, String userId, MimeMessage emailContent) throws MessagingException,
            IOException {
        Message message = createMessageWithEmail(emailContent);
        message = service.users().messages().send(userId, message).execute();

        System.out.println("Message id: " + message.getId());
        System.out.println(message.toPrettyString());
        return message;
    }

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to email address of the receiver
     * @param from email address of the sender, the mailbox account
     * @param subject subject of the email
     * @param bodyText body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
    private static MimeMessage createEmail(String to, String from, String subject, String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    /**
     * Create a message from an email.
     *
     * @param emailContent Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws IOException
     * @throws MessagingException
     */
    private static Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to Email address of the receiver.
     * @param from Email address of the sender, the mailbox account.
     * @param subject Subject of the email.
     * @param bodyText Body text of the email.
     * @param file Path to the file to be attached.
     * @return MimeMessage to be used to send email.
     * @throws MessagingException
     */
    private static MimeMessage createEmailWithAttachment(String to, String from, String subject, String bodyText, File file)
            throws MessagingException, IOException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(bodyText, "text/plain");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        mimeBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(file);


        mimeBodyPart.setDataHandler(new DataHandler(source));
        mimeBodyPart.setFileName(file.getName());

        multipart.addBodyPart(mimeBodyPart);
        email.setContent(multipart);

        return email;
    }

    /**
     * Trash the specified message.
     *
     * @param userId User's email address. The special value "me"
     * can be used to indicate the authenticated user.
     * @throws IOException
     */
    public void trashMessage(String userId, String emailId) throws IOException {
        gmail.users().messages().trash(userId, emailId).execute();
    }


}

