package App;

import java.util.NoSuchElementException;
import java.util.Scanner;
/**
 * Класс хранит объекты, управляющие процессом выполнения команд пользователя
 */

public class Commander {

    private Head manager;
    private String userCommand;
    private String[] finalUserCommand;

    Commander (Head manager) {
        this.manager = manager;
    }

    void interactiveMod(){
        manager.read();
        System.out.println("> Ready for work");
        try(Scanner commandReader = new Scanner(System.in)){
            while (userCommand != "exit"){
                userCommand = commandReader.nextLine();
                if (userCommand == null) throw new NoSuchElementException();
                finalUserCommand = userCommand.trim().split(" ", 2);
                try{
                    switch (finalUserCommand[0]){
                        case "" : break;
                        case "help" : manager.help(); manager.addToHistory(finalUserCommand[0]); break;
                        case "info" : manager.info(); manager.addToHistory(finalUserCommand[0]); break;
                        case "show" : manager.show(); manager.addToHistory(finalUserCommand[0]); break;
                        case "add" : manager.add(); manager.addToHistory(finalUserCommand[0]); break;
                        case "update" :
                            try {
                                int i = Integer.parseInt(finalUserCommand[1].trim());
                                manager.update(i);
                            }catch(NumberFormatException e){
                                System.out.println("> Input error (id have to be an integer)");
                            }
                            manager.addToHistory(finalUserCommand[0]);
                            break;
                        case "remove_by_id" :
                            try {
                                int j = Integer.parseInt(finalUserCommand[1].trim());
                                manager.remove_by_id(j);
                            }catch (NumberFormatException e){
                                System.out.println("> Input error (id have to be an integer)");
                            }
                            manager.addToHistory(finalUserCommand[0]);
                            break;
                        case "clear" : manager.clear(); manager.addToHistory(finalUserCommand[0]); break;
                        case "save" : manager.save(); manager.addToHistory(finalUserCommand[0]); break;
                        case "execute_script" :
                            manager.execute_script(finalUserCommand[1].trim());
                            manager.addToHistory(finalUserCommand[0]);
                            break;
                        case "exit" : manager.exit(); manager.addToHistory(finalUserCommand[0]); break;
                        case "remove_head" : manager.remove_head(); manager.addToHistory(finalUserCommand[0]); break;
                        case "add_if_min" : manager.add_if_min(); manager.addToHistory(finalUserCommand[0]); break;
                        case "history" : manager.history(); manager.addToHistory(finalUserCommand[0]); break;
                        case "group_counting_by_from" :
                            manager.group_counting_by_from();
                            manager.addToHistory(finalUserCommand[0]);
                            break;
                        case "filter_contains_name" :
                            manager.filter_contains_name(finalUserCommand[1].trim());
                            manager.addToHistory(finalUserCommand[0]);
                            break;
                        case "print_unique_distance" :
                            manager.print_unique_distance();
                            manager.addToHistory(finalUserCommand[0]);
                            break;
                        default: System.out.println("> Unidentified command - input 'help' for reference");
                    }
                }catch (IndexOutOfBoundsException e){
                    System.out.println("> Missing argument");
                }
            }
        }catch (NoSuchElementException e){
            System.out.println("> No line found");
        }
    }
}
