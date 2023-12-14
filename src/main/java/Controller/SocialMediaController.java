package Controller;

import Model.Account;
import Model.Message;

import Service.AccountService;
import Service.MessageService;
import Service.AccountServiceImp;
import Service.MessageServiceImp;

import java.util.Map;
import java.util.ArrayList;


import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    // Declare members
    private AccountService accountService;

    private MessageService messageService;

    // Controller constructor
    public SocialMediaController() {
        this.accountService = new AccountServiceImp();
        this.messageService = new MessageServiceImp();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {

        // Endpoints handlers for account
        Javalin app = Javalin.create();
        app.post("/register", this::addAccountHandler);
        app.post("/login", this::loginAccountHandler);
        app.get("/accounts", this::getAllAccountsHandler);
        app.get("/accounts/{account_id}", this::getAccountByIdHandler);

        // Endpoints handlers for message
        app.post("/messages", this::addMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.get("accounts/{account_id}/messages", this::getAllMessagesByUserHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessagesByIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */

    // Account creation process
    private void addAccountHandler(Context ctx) {
        // Obtain request data
        Account account = ctx.bodyAsClass(Account.class);

        // Method for call service
        Account accountAdded = accountService.addAccount(account);

        // Send client the result
        if(accountAdded !=null) {
            ctx.json(accountAdded);
        }else {
            ctx.status(400);
        }
    }


     // User login into account
     private void loginAccountHandler(Context ctx) {

        // Obtain request data
        Account account = ctx.bodyAsClass(Account.class);

        // Method for call service
        Account accountLogin = accountService.getAccount(account.getUsername(), account.getPassword());

        // Send client the result
        if(accountLogin != null) {
            ctx.json(accountLogin);
        }else {
            ctx.status(401);
        }
     }


      // Method to get all accounts
      private void getAllAccountsHandler(Context ctx) {
        // Method for call service
        ArrayList<Account> accounts = accountService.getAllAccounts();

        // Send client the result
        if(accounts != null) {
            ctx.json(accounts);
        }else {
            ctx.status(400);
        }
      }


    //  Method to get account by id
    private void getAccountByIdHandler(Context ctx) {

        // Obtain request data
        String stringId = ctx.pathParam("account_id");
        int id = Integer.parseInt(stringId);

        // Method for call service
        Account account = accountService.getAccountById(id);

        // Send client the result
        if(account !=null) {
            ctx.json(account);
        }else {
            ctx.status(401);
        }
    }


    // Method to add Messages
    private void addMessagesHandler(Context ctx) {

        // Obtain request data
        Message message = ctx.bodyAsClass(Message.class);

        // Method for call service
        Message MessageAdded = messageService.addMessage(message);

        // Send client the result
        if(MessageAdded != null) {
            ctx.json(MessageAdded);
        }else {
            ctx.status(400);
        }
    }

    // Method to get all messages
    public void getAllMessagesHandler(Context ctx) {

        // Method for call service
        ArrayList<Message> messages = messageService.getAllMessages();

        // Send client the result
        if(messages != null) {
            ctx.json(messages);
        }else {
            ctx.status(504);
        }
    }


    // Method to get message by id
    private void getMessageByIdHandler(Context ctx) {

        // Obtain request data
        String stringId = ctx.pathParam("message_id");
        int id = Integer.parseInt(stringId);

        // Method for call service
        Message message = messageService.getMessageById(id);

        // Method for call service
        if(message != null) {
            ctx.json(message);
        }else {
            ctx.status(200).json("");
        }
    }


    // Methof to get all user messages
    private void getAllMessagesByUserHandler(Context ctx) {
        
        // Obtaing request data
        String stringId = ctx.pathParam("account_id");
        int id = Integer.parseInt(stringId);
        
        // Method for call service
        ArrayList<Message> userMessages = messageService.getAllMessagesByUser(id);

        // Send client the result
        if(userMessages != null) {
            ctx.json(userMessages);
        }else {
            ctx.status(504);
        }
    }


    // Method for updating message texts
    private void updateMessageByIdHandler(Context ctx) {

        // Obtain request data
        Map<String, String> messageMap = ctx.bodyAsClass(Map.class);
        String messageText = messageMap.get("message_text");
        String stringId = ctx.pathParam("message_id");
        int id = Integer.parseInt(stringId);
        
        // Method for call service
        Message message = messageService.updateMessageById(id, messageText);

        // Send client the result
        if(message != null) {
            ctx.json(message);
        }else {
            ctx.status(400);
        }
    }


    // Method for deleting message by id
    private void deleteMessagesByIdHandler(Context ctx) {

        // Obtain request data
        String stringId = ctx.pathParam("message_id");
        int id = Integer.parseInt(stringId);

        // Method for call service
        Message deletedMessage = messageService.getMessageById(id);
       
        // Check if message exists before delete operation
        if(deletedMessage != null) {
            // Service method call
            messageService.deleteMessagesById(id);

            // Send client the result
            ctx.json(deletedMessage);
        }else {
            ctx.status(200);
            ctx.json("");
        }
    }
}