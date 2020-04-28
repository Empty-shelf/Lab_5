package App;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * Класс, коллекцией экземпляров которого управляет программа
 */
public class Route implements Comparable<Route> {
    private int id;//больше 0 (униальное значение, генерируется автоматически)
    private static AtomicInteger i = new AtomicInteger();
    private String name; //не может быть null, строка не может быть пустой
    private Coordinates coordinates; //не может быть null
    private java.time.LocalDateTime creationDate; //не может быть null (генерируется автоматически)
    private Location from; //не может быть null
    private Location to; //может быть null
    private double distance; //больше 1

    Route(double distance, String name, Coordinates coordinates, Location from, Location to) {
        this.distance = distance;
        this.name = name;
        this.coordinates = coordinates;
        this.from = from;
        this.to = to;
        creationDate = LocalDateTime.now();
        id = i.incrementAndGet();
    }
    /**
     * Сравнение объектов
     * @param route - объект класса Route, с которым сравнивается текущий
     * @return 1, если текущий объект больше, -1, если текущий объект меньше, 0, если объекты равны
     */
    @Override public int compareTo(Route route) {
        int result = this.name.compareTo(route.name);
        if (result == 0){
            if (this.distance - route.distance > 0) return 1;
            else if(this.distance - route.distance < 0) return -1;
            else return 0;
        }
        else return result;
    }

    int getId(){return this.id;}
    void setId(int id){this.id = id;}
    String getName(){return this.name;}
    Coordinates getCoordinates(){return this.coordinates;}
    Location getFrom(){return this.from;}
    Location getTo(){return this.to;}
    double getDistance(){return this.distance;}

    /**
     * Строковое представление элемента коллекции
     * @return String
     */
    @Override public String toString(){
        return this.name + " (" + this.id + ") " + ": from " + this.from.getName() +
                " to " + this.to.getName() + ", distance - " + this.distance + ", coordinates - (" +
                this.coordinates.getX() + "," + this.coordinates.getY() + ")";
    }
}
