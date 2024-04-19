package com.mysite.banking.view.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mysite.banking.dto.ClientDto;
import com.mysite.banking.facade.ClientFacade;
import com.mysite.banking.facade.impl.ClientFacadeImpl;
import com.mysite.banking.model.ClientType;
import com.mysite.banking.model.FileType;
import com.mysite.banking.service.exception.*;

import java.util.List;
import java.util.function.Function;

public class ClientConsole extends BaseConsole{
    final ClientFacade clientFacade;


    public ClientConsole() {
        clientFacade = ClientFacadeImpl.getInstance();
    }

    public void clientMenu(){
        int choice ;
        do {
            printClientMenu();
            choice = scannerWrapper.getUserInput("Enter your choice:", Integer::valueOf);
            try {
                switch (choice) {
                    case 0:
                        break;
                    case 1:
                        addClient();
                        break;
                    case 2:
                        printAllClient();
                        break;
                    case 3:
                        searchClientByName();
                        break;
                    case 4:
                        searchClientByFamily();
                        break;
                    case 5:
                        editClientById();
                        break;
                    case 6:
                        deleteClientById();
                        break;
                    case 7:
                        printAllDeletedClient();
                        break;
                    case 8:
                        login();
                        break;
                    case 9:
                        exportJson();
                        break;
                    default:
                        System.out.println("invalid Number!");
                }
            }catch (ClientNotFindException | EmptyClientException  exception) {
                System.out.println(exception.getMessage());
            }
        }while (choice!=0);
    }

    private void exportJson() {
        String fileName = scannerWrapper.getUserInput("Enter the file name:",Function.identity());
        clientFacade.exportJson(fileName);
    }

    private void login(){
        String userName = scannerWrapper.getUserInput("Enter your email:",Function.identity());
        String password = scannerWrapper.getUserInput("Enter your password:",Function.identity());
        Boolean validate = clientFacade.login(userName,password);
        if (validate){
            System.out.println("Welcome to the system.");
        }else {
            System.out.println("User or password is wrong!");
        }

    }
    private void editClientById() throws ClientNotFindException {
        String id = scannerWrapper.getUserInput("Enter the customer id: ", Function.identity());
        ClientDto clientDto = clientFacade.getClientById(Integer.valueOf(id));
        System.out.println(clientDto);
        AbstractClientUi
                .fromClientType(clientDto.getType())
                .editClient(clientDto);
        try {
            clientFacade.updateClient(clientDto);
        } catch (ValidationException e){
            System.out.println(e.getMessage());
            editClientById();
        }
    }

    private void searchClientByFamily() {
        String family = scannerWrapper.getUserInput("Enter the family: ", Function.identity());
        List<ClientDto> clients = clientFacade.searchClientByFamily(family);
        clients.forEach(System.out::println);
    }

    private void printClientMenu() {
        System.out.println("Menu:");
        System.out.println("0. Back");
        System.out.println("1. Add Client");
        System.out.println("2. Print All Clients");
        System.out.println("3. Search clients by name");
        System.out.println("4. Search clients by family");
        System.out.println("5. Edit client by id");
        System.out.println("6. Delete client by id");
        System.out.println("7. Print All Deleted Clients");
        System.out.println("8. Login");
        System.out.println("9. Export data as JSON");
        System.out.println();
    }
    private void addClient()  {
        System.out.println("Client Type:");
        System.out.println("1.Real");
        System.out.println("2.Legal");
        int choice = scannerWrapper.getUserInput("Enter your choice:", Integer::valueOf);
        try {
            clientFacade.addClient(AbstractClientUi
                    .fromClientType(ClientType
                            .fromValue(choice))
                    .generateClient());
        } catch (DuplicateClientException e) {
            System.out.println("You can't use two name or family");
            addClient();
        }catch (InvalidType invalidType) {
            System.out.println("Invalid Client type ");
            addClient();
        }catch (ValidationException exception){
            System.out.println(exception.getMessage());
            addClient();
        }
        System.out.println("Client added successfully!");
    }
    private void printAllClient() throws EmptyClientException {
        List<ClientDto> allClient = clientFacade.getActiveClients();
        System.out.println("All Client:");
        for (ClientDto client : allClient) {
            try {
                System.out.println(objectMapper.writeValueAsString(client));
            } catch (JsonProcessingException e) {
                System.out.println("Error on print client id" + client.getId());
            }
        }
    }
    private void printAllDeletedClient() throws EmptyClientException {
        List<ClientDto> allClient = clientFacade.getDeletedClients();
        System.out.println("All Deleted Client:");
        for (ClientDto client : allClient) {
            System.out.println(client);
        }
    }

    private void deleteClientById() throws ClientNotFindException {
        String id = scannerWrapper.getUserInput("Enter the customer id: ", Function.identity());
        clientFacade.deleteClientById(Integer.valueOf(id));
    }
    private void searchClientByName() throws ClientNotFindException {
        String name = scannerWrapper.getUserInput("Enter the name:",Function.identity());
        List<ClientDto> clients = clientFacade.searchClientByName(name);
        clients.forEach(System.out::println);
    }
}
