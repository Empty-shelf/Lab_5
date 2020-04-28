package App;

/**
 * Класс хранит объекты, которые задают координаты местонахождения пользователя
 */
public class Coordinates {
    private double x; //Значение поля должно быть больше -808
    private Integer y; //Поле не может быть null

    Coordinates(double x, Integer y){
        this.x = x;
        this.y = y;
    }

    double getX() {return this.x;}
    Integer getY(){return this.y;}

}
