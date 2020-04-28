package App;

/**
 * Класс хранит объекты, которые задают начальныю и конечную точку пути
 */
public class Location{
    private String name; //Поле не может быть null
    private double x;
    private float y;

    Location(String name, double  x, float y){
        this.name = name;
        this.x = x;
        this.y = y;
    }

    String getName(){return this.name;}
    double getX(){return this.x;}
    float getY(){return this.y;}
}