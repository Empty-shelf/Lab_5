package App;

import java.io.*;
import java.util.*;
/**
 * Класс хранит объекты, которые являются "исполнителями" команд пользователя
 */
public class Head {
    private ArrayDeque<Route> ways; //коллекция
    private Date dateOfCreation;
    private String[][] commands; //массив команд и их описаний
    private File csvFile;
    private ArrayDeque<String> history;  //история
    ArrayList<String> rec;

    {
        ways = new ArrayDeque<>();
        history = new ArrayDeque<String>();
        commands = new String[16][1];
        rec = new ArrayList<>();
    }

    /**
     * Инициализация объекта
     * Загрузка коллекции из файла
     * @param collPath - переменная окружения
     */
    Head(String collPath){
        try {
            if (collPath == null) throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
            System.out.println("> File not found (environment variable is empty)");
            System.exit(1);
        }
        File file = new File(collPath);
        try {
            if (file.exists()) {
                this.csvFile = file;
                load();
            } else throw new FileNotFoundException();
        } catch (FileNotFoundException e) {
            System.out.println("> File not found");
            System.exit(1);
        }
        dateOfCreation = new Date();
    }

    /**
     * Чтение файла, добавление элементов в коллекцию
     */
    private void load() {
        boolean flag = true;
        try {
            if (!csvFile.canRead() || !csvFile.canWrite()) throw new SecurityException();
        } catch (SecurityException e) {
            System.out.println("> File access denied");
            System.exit(1);
        }
        if (csvFile.length() == 0) {
            System.out.println("> File is empty");
            return;
        }
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile)))){
            while (flag) {
                String[] fields;
                String s = in.readLine();
                if (s != null) {
                    fields = s.trim().split(",", 10);
                    double distance = Double.parseDouble(fields[0]);
                    String name = fields[1];
                    if (name.trim().length()==0) {
                        System.out.println("> Empty string entered");
                        throw new IOException();
                    }
                    double x = Double.parseDouble(fields[2]);
                    Integer y = Integer.valueOf(fields[3]);
                    String nameLocationFrom = fields[4];
                    double fromX = Double.parseDouble(fields[5]);
                    float fromY = Float.parseFloat(fields[6]);
                    String nameLocationTo = fields[7];
                    double ToX = Double.parseDouble(fields[8]);
                    float ToY = Float.parseFloat(fields[9]);
                    Coordinates coordinates = new Coordinates(x, y);
                    Location from = new Location(nameLocationFrom, fromX, fromY);
                    Location to = new Location(nameLocationTo, ToX, ToY);
                    Route route = new Route(distance, name, coordinates, from, to);
                    ways.add(route);
                } else flag = false;
            }
        } catch (FileNotFoundException e) {
            System.out.println("> File not found");
            System.exit(1);
        } catch (NumberFormatException e) {
            System.out.println("> Invalid argument format in file");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("> Input error");
            System.exit(1);
        }
        sort();
        System.out.println("> Collection uploaded");
    }
    /**
     * Чтение файла с набором доступных комманд и их описанием в двумерный массив
     */
    void read() {
        try (Scanner in = new Scanner(new File("file_commands.txt"))){
            String s;
            int i = 0;
            while (in.hasNextLine()) {
                s = in.nextLine();
                commands[i] = s.split(":");
                i++;
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("> File (with commands) not found");
            System.exit(1);
        }
    }
    /**
     * Сортировка коллекции
     */
    private void sort() {
        Route[] array = ways.toArray(new Route[0]);
        boolean flag = false;
        Route k;
        while (!flag) {
            flag = true;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i].compareTo(array[i + 1]) > 0) {
                    flag = false;
                    k = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = k;
                }
            }
        }
        ways = new ArrayDeque<Route>(Arrays.asList(array));
    }
    /**
     * Вывод справки по доступным командам
     */

    void help() {
        for (int i = 0; i < 16; i++) {
            System.out.println("> " + commands[i][0] + " : " + commands[i][1]);
        }
    }

    /**
     * Вывод информации о коллекции
     */
    void info() {
        System.out.println("Collection type: " + ways.getClass().getName() + ",\nCreation date: " +
                dateOfCreation + ",\nAmount of elements: " + ways.size());
    }

    /**
     * Вывод элементов коллекции в строковом представлении
     */
    void show() {
        for (Route route : ways) {
            System.out.println(route);
        }
    }

    /**
     * Метод для создания элемента коллекции (использование данных, вводимых пользователем (через консоль))
     * @param id - идентификационный номер
     * @return объект класса Route
     */
    private Route constructorSystemIn(int id) {
        while (true) {
            try {
                Scanner field = new Scanner(System.in);
                System.out.println("> Input distance:");
                double distance = field.nextDouble();
                System.out.println("> Input route's name:");
                if (field.nextLine() == null) throw new InputMismatchException();
                String name = field.nextLine();
                if (name.trim().length()==0){
                    System.out.println("> Empty string entered");
                    throw new InputMismatchException();
                }
                System.out.println("> Input x coordinate:");
                double x = field.nextDouble();
                if (x <= -808) throw new InputMismatchException();
                System.out.println("> Input y coordinate:");
                Integer y = field.nextInt();
                System.out.println("> Input name of location from:");
                if (field.nextLine() == null) throw new InputMismatchException();
                String locFrom = field.nextLine();
                System.out.println("> Input x coordinate of location from:");
                double fromX = field.nextDouble();
                System.out.println("> Input y coordinate of location from:");
                float fromY = field.nextFloat();
                System.out.println("> Input name of location to:");
                if (field.nextLine() == null) throw new InputMismatchException();
                String locTo = field.nextLine();
                System.out.println("> Input x coordinate of location to:");
                double toX = field.nextDouble();
                System.out.println("> Input y coordinate of location from:");
                float toY = field.nextFloat();
                Coordinates coordinates = new Coordinates(x, y);
                Location locationFrom = new Location(locFrom, fromX, fromY);
                Location locationTo = new Location(locTo, toX, toY);
                if (id != 0) {
                    Route route =  new Route(distance, name, coordinates, locationFrom, locationTo);
                    route.setId(id);
                    return route;
                } else return new Route(distance, name, coordinates, locationFrom, locationTo);
            }catch (InputMismatchException e){
                System.out.println("> Input error\n\u001B[34mReference:\u001B[0m\n\u001B[31mfraction :\u001B[0m" +
                        " distance, x coordinate \u001B[31m(have to be more than -808)\u001B[0m," +
                        " coordinates of locations(from/to)\n\u001B[31minteger :\u001B[0m y coordinate\n"+
                        "\u001B[31mstring (not null) :\u001B[0m route's name (not empty), locations'(from/to) names");
            }
        }
    }

    /**
     * Метод для создания элемента коллекции (использование данных скрипта)
     * @param j - номер элемента коллекции
     * @param script - коллекция-скрипт
     * @return объект класса Route
     */
    private Route constructorFile(int j, ArrayList<String[]> script) {
        double distance = Double.parseDouble(script.get(j + 1)[0]);
        String name = script.get(j + 2)[0];
        try {
            if (name.trim().length()==0) {
                System.out.println("> Empty string entered");
                throw new InputMismatchException();
            }
        }catch (InputMismatchException e){
            System.out.println("> Wrong format\n" +
                    "\u001B[31mstring (not null) :\u001B[0m route's name (not empty)");
            System.exit(1);
        }
        double x = Double.parseDouble(script.get(j + 3)[0]);
        Integer y = Integer.valueOf(script.get(j + 4)[0]);
        String locFrom = script.get(j + 5)[0];
        double fromX = Double.parseDouble(script.get(j + 6)[0]);
        float fromY = Float.parseFloat(script.get(j + 7)[0]);
        String locTo = script.get(j + 8)[0];
        double toX = Double.parseDouble(script.get(j + 9)[0]);
        float toY = Float.parseFloat(script.get(j + 10)[0]);
        Coordinates coordinates = new Coordinates(x, y);
        Location locationFrom = new Location(locFrom, fromX, fromY);
        Location locationTo = new Location(locTo, toX, toY);
        if (script.get(j)[0].equals("update")) {
            Route route =  new Route(distance, name, coordinates, locationFrom, locationTo);
            route.setId(Integer.parseInt(script.get(j)[1]));
            return route;
        } else return new Route(distance, name, coordinates, locationFrom, locationTo);
    }

    /**
     * Добавление нового элемента в коллекцию
     */
    void add() {
        ways.add(constructorSystemIn(0));
        sort();
        System.out.println("> Element added");
    }

    /**
     * Обновление значения элемента коллекции (по заданному id)
     * @param id - идентификационный номер
     */
    void update(int id) {
        boolean flag = false;
        ArrayDeque<Route> buf = new ArrayDeque<>();
        for (Route route : ways) {
            if (route.getId() != id) {
                buf.add(route);
            } else {
                flag = true;
                buf.add(constructorSystemIn(id));
            }
        }
        if (flag) {
            ways = buf;
            sort();
            System.out.println("> Element updated");
        } else {
            System.out.println("> Element with given id doesn't exist");
        }
    }

    /**
     * Удаление элемента из коллекции (по заданному id)
     * @param id - идентификационный номер
     */
    void remove_by_id(int id) {
        boolean flag = false;
        for (Route route : ways) {
            if (route.getId() == id) {
                ways.remove(route);
                flag = true;
                System.out.println("> Element removed");
                break;
            }
        }
        if (!flag) System.out.println("> Element with given id does not exist");
    }

    /**
     * Очистка коллекции
     */
    void clear() {
        ways.clear();
        System.out.println("> Collection cleared");
    }

    /**
     * Возвращение строкового представления элемента в формате csv
     * @param route - объект класса Route
     * @return String
     */
    private String write(Route route) {
        return route.getDistance() + "," + route.getName() + "," +
                route.getCoordinates().getX() + "," + route.getCoordinates().getY() +
                "," + route.getFrom().getName() + "," + route.getFrom().getX() + "," +
                route.getFrom().getY() + "," + route.getTo().getName() + "," + route.getTo().getX() +
                "," + route.getTo().getY();
    }

    /**
     * Сохранение коллекции в файл
     */
    void save() {
        try (PrintWriter out = new PrintWriter(new FileOutputStream(csvFile))) {
            for (Route route : ways) {
                out.println(write(route));
            }
        } catch (FileNotFoundException e) {
            System.out.println("> File not found");
        }
        System.out.println("> Collection saved");
    }

    /**
     * Считывание и исполнеине скрипта из указанного файла
     * @param file_path - путь к файлу
     */
    void execute_script(String file_path) {
        File file = new File(file_path);
        try {
            for (String str : rec) {
                if (file.getName().equals(str)) {
                    throw new InputMismatchException();
                }
            }
        }catch (InputMismatchException e){
            System.out.println("> Recursion cannot work with the same file");
            return;
        }
        rec.add(file.getName());
        try {
            ArrayList<String[]> script = new ArrayList<>();
            Scanner in = new Scanner(new File(file_path));
            String s;
            while (in.hasNextLine()) {
                s = in.nextLine();
                script.add(s.split(" ", 2));
            }
            for (int j = 0; j < script.size(); j++) {
                try {
                    System.out.println("\u001B[32m" + script.get(j)[0] + "\u001B[0m");
                    switch (script.get(j)[0]) {
                        case "": break;
                        case "help": help(); break;
                        case "info": info(); break;
                        case "show": show(); break;
                        case "add":
                            ways.add(constructorFile(j, script));
                            sort();
                            System.out.println("> Element added");
                            j+=10;
                            break;
                        case "update":
                            int id = Integer.parseInt(script.get(j)[1]);
                            boolean flag = false;
                            ArrayDeque<Route> buf = new ArrayDeque<>();
                            for (Route route : ways) {
                                if (route.getId() != id) {
                                    buf.add(route);
                                } else {
                                    flag = true;
                                    buf.add(constructorFile(j, script));
                                }
                            }
                            if (flag) {
                                ways = buf;
                                sort();
                                System.out.println("> Element updated");
                            } else {
                                System.out.println("> Element with given id doesn't exist");
                            }
                            j+=10;
                            break;
                        case "remove_by_id":
                            int k = Integer.parseInt(script.get(j)[1]);
                            remove_by_id(k);
                            break;
                        case "clear": clear(); break;
                        case "save": save(); break;
                        case "execute_script":
                           // try {
                               // if (script.get(j)[1].equals(file_name)) throw new InputMismatchException();
                                execute_script(script.get(j)[1]);
                          //  }catch (InputMismatchException e){
                          //      System.out.println("> Recursion cannot work with the same file");
                           // }
                            break;
                        case "exit":
                            exit();
                            break;
                        case "remove_head":
                            remove_head();
                            break;
                        case "add_if_min":
                            try {
                                if (ways.isEmpty()) throw new NoSuchElementException("> Collection is empty");
                                Route route = constructorFile(j, script);
                                if (ways.peekFirst().compareTo(route) > 0) {
                                    ways.addFirst(route);
                                    System.out.println("> Element added");
                                }
                            } catch (NoSuchElementException e) {
                                System.out.println(e);
                            }
                            j+=10;
                            break;
                        case "history": history(); break;
                        case "group_counting_by_from": group_counting_by_from(); break;
                        case "filter_contains_name": filter_contains_name(script.get(j)[1]); break;
                        case "print_unique_distance": print_unique_distance(); break;
                        default:
                            System.out.println("> Unidentified command");
                            break;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("> Missing argument");
                    rec.clear();
                    System.exit(1);
                }catch (NumberFormatException e) {
                    System.out.println("> Wrong format, check your file\n" +
                            "> \u001B[32mReference:\u001B[0m\n\u001B[31mfraction :\u001B[0m distance, " +
                            "x coordinate \u001B[31m(have to be more than -808)\u001B[0m," +
                            " coordinates of locations(from/to)\n\u001B[31minteger :\u001B[0m y coordinate\n" +
                            "\u001B[31mstring (not null) :\u001B[0m route's name (not empty), " +
                            "locations'(from/to) names\n" +
                            "(if you use id like an argument - it \u001B[31mhave to be an integer\u001B[0m)");
                    rec.clear();
                    System.exit(1);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("> File (script) not found");
            rec.clear();
        }
        rec.clear();
    }

    /**
     * Завершение программы без сохранения в файл
     */
    void exit() {
        System.exit(0);
    }

    /**
     * Вывод и удаление первого элемента коллекции
     */
    void remove_head() {
        try {
            if (ways.isEmpty()) throw new NoSuchElementException();
            ways.removeFirst();
            System.out.println("> Element removed");
        } catch (NoSuchElementException e) {
            System.out.println("> Collection is empty");
        }
    }

    /**
     * Добавление нового элемента в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции
     */
    void add_if_min() {
        try {
            if (ways.isEmpty()) throw new NoSuchElementException();
            Route route = constructorSystemIn(0);
            if (ways.peekFirst().compareTo(route) > 0) {
                ways.addFirst(route);
                System.out.println("> Element added");
            }
            else System.out.println("> Not minimal");
        } catch (NoSuchElementException e) {
            System.out.println("> Collection is empty");
        }
    }

    /**
     * Вывод последних 11 команд (история)
     */
    void history() {
        if (!history.isEmpty()) {
            for (String str : history) {
                System.out.println(str);
            }
        } else System.out.println("> History is empty");
    }

    /**
     * Создание истории вызова команд (11)
     * @param string - имя команды
     */
    void addToHistory(String string){
        if (history.size()<11) history.add(string);
        else {
            history.pop();
            history.add(string);
        }
    }

    /**
     * Группировака элементов коллекции по значению поля from, вывод количества элементов в каждой группе
     */
    void group_counting_by_from() {
        //подсчет количества уникальных значений поля from
        boolean flag = true;
        ArrayList<Route> fr = new ArrayList<>();
        for (Route route : ways) {
            if (!fr.isEmpty()) {
                for (Route routeUn : fr) {
                    if (route.getFrom().getName().equals(routeUn.getFrom().getName())) {
                        flag = false;
                        break;
                    }
                }
            } else {
                fr.add(route);
                flag = false;
            }
            if (flag) fr.add(route);
            flag = true;
        }
        //подсчет элементов в каждой группе
        int t = 0;
        for (int i = 0; i < fr.size(); i++) {
            for (Route route : ways) {
                if (route.getFrom().getName().equals(fr.get(i).getFrom().getName())) t++;
            }
            System.out.println(fr.get(i).getFrom().getName() + " [" + t + "]");
            t = 0;
        }
    }

    /**
     * Вывод элементов, значение поля 'name' которых содержит заданную подстроку
     * @param name - заданная подстрока
     */
    void filter_contains_name(String name) {
        boolean flag = true;
        for (Route route : ways){
            if (route.getName().contains(name)) {
                System.out.println(route);
                flag = false;
            }
        }
        if (flag) System.out.println("> No matches found");
    }

    /**
     * Вывод уникальных значений поля distance
     */
    void print_unique_distance() {
        boolean flag = true;
        ArrayList<Route> dis = new ArrayList<>();
        for (Route route : ways) {
            if (!dis.isEmpty()) {
                for (Route routeUn : dis) {
                    if (route.getDistance() == routeUn.getDistance()) {
                        flag = false;
                        break;
                    }
                }
            } else {
                dis.add(route);
                flag = false;
            }
            if (flag) dis.add(route);
            flag = true;
        }
        for (Route route : dis) {
            System.out.println(route.getDistance());
        }
    }
}



