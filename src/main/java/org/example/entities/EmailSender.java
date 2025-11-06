package org.example.entities;
import jakarta.mail .*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;
    /**
     * Simple email sender using Jakarta Mail (SMTP).
     * Holds all SMTP config and exposes one send(...) method.
     */
    public class EmailSender {
        // SMTP server hostname, e.g. "smtp-relay.brevo.com"
        private final String host;
        // SMTP port, e.g. 587
        private final int port;
        // SMTP username / login
        private final String username;
        // SMTP password or API key (depending on provider)
        private final String password;
        // default "from" address that will appear in the email
        private final String from;

        /**
         * Constructor that injects all SMTP settings.
         */
        public EmailSender(String host, int port, String username, String password, String from) {
            this.host = host;
            this.port = port;
            this.username = username;
            this.password = password;
            this.from = from;
        }

        /**
         * Send a plain text email.
         *
         * @param to      receiver email
         * @param subject email subject
         * @param body    email body (text)
         */
        public void send(String to, String subject, String body) {
            // SMTP properties (how to talk to the mail server)
            Properties props = new Properties();
            // we want to authenticate with username/password
            props.put("mail.smtp.auth", "true");
            // use STARTTLS (common for port 587)
            props.put("mail.smtp.starttls.enable", "true");
            // which SMTP server to use
            props.put("mail.smtp.host", host);
            // which port to connect to
            props.put("mail.smtp.port", String.valueOf(port));

            // create a mail session with our properties and authentication
            Session session = Session.getInstance(
                    props,
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            // this is what the SMTP server will check
                            return new PasswordAuthentication(username, password);
                        }
                    }
            );

            try {
                // build the actual email message
                Message message = new MimeMessage(session);
                // set "from" header
                message.setFrom(new InternetAddress(from));
                // set "to" header (can parse multiple addresses if needed)
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                // set subject line
                message.setSubject(subject);
                // set body as plain text
                message.setText(body);

                // actually send the message via SMTP
                Transport.send(message);

                // you said you don't need a success print, so we keep it silent
            } catch (MessagingException e) {
                // if something goes wrong (bad login, bad host, etc.)
                System.out.println("Kunde inte skicka e-post: " + e.getMessage());
            }
        }
    }

