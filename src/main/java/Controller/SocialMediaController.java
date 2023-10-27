package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.*;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    
    AccountService accountService = new AccountService();
    MessageService messageService = new MessageService();

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    public Javalin startAPI() {
        Javalin app = Javalin.create();

        // app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerAccount);
        app.post("/login", this::loginAccount);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::retrieveAllMessages);
        app.get("/messages/{message_id}", context -> {
            try {
                String messageId = context.pathParam("message_id");

                Message message = messageService.retrieveMessageById(Integer.valueOf(messageId));
    
                context.json(message);
                context.status(200);
            }
            catch(Exception e) {
                context.status(200);
            }
        });
        app.delete("/messages/{message_id}", context -> {
            try {
                String messageId = context.pathParam("message_id");

                Message message = messageService.deleteMessageById(Integer.valueOf(messageId));
    
                context.json(message);
                context.status(200);
            }
            catch(Exception e) {
                context.status(200);
            }
        });
        app.patch("/messages/{message_id}", context -> {

            try {
                ObjectMapper objectMapper = new ObjectMapper();

                String messageId = context.pathParam("message_id");
                String jsonString = context.body();
                Message message = objectMapper.readValue(jsonString, Message.class);
    
                messageService.updateMessageById(Integer.valueOf(messageId), message);

                message = messageService.retrieveMessageById(Integer.valueOf(messageId));
    
                context.json(message);
                context.status(200);
            }
            catch(Exception e) {
                e.printStackTrace();
                context.status(400);
            }
        });
        app.get("/accounts/{account_id}/messages", context -> {

            String accountId = context.pathParam("account_id");
            
            List<Message> message = messageService.retrieveAllMessagesByUser(Integer.valueOf(accountId));
            
            context.json(message);
            context.status(200);
        });

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    // private void exampleHandler(Context context) {

    //     ObjectMapper objectMapper = new ObjectMapper();

    //     String jsonString = context.body();
    //     Account account = objectMapper.readValue(jsonString, Account.class);

    //     context.json(account);
    //     context.json("sample text");
    // }
    

    // public Javalin registerAccount() {
    //     Javalin app = Javalin.create();

    //     app.post("/register", this::registerAccount);
        
    //     return app;
    // }

    private void registerAccount(Context context) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String jsonString = context.body();
            Account account = objectMapper.readValue(jsonString, Account.class);

            account = accountService.registerAccount(account);

            context.json(account);
            context.status(200);
        }
        catch(Exception e) {
            context.status(400);
        }
    }

    private void loginAccount(Context context) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String jsonString = context.body();
            Account account = objectMapper.readValue(jsonString, Account.class);

            account  = accountService.loginAccount(account);

            context.json(account);
            context.status(200);
        }
        catch(Exception e) {
            context.status(401);
        }
    }

    private void createMessage(Context context) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            String jsonString = context.body();
            Message message = objectMapper.readValue(jsonString, Message.class);

            message = messageService.createMessage(message);

            context.json(message);
            context.status(200);
        }
        catch(Exception e) {
            // e.printStackTrace();
            context.status(400);
        }
    }

    private void retrieveAllMessages(Context context) {
        List<Message> messages = messageService.retrieveAllMessages();

        context.json(messages);
        context.status(200);
    }             
}