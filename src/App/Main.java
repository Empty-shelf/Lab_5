package App;
/**
 * @author Полозова Екатерина
 */
public class Main {
    public static void main(String[] args){
        Commander commander = new Commander(new Head(System.getenv("Coll_Path")));
        commander.interactiveMod();
    }
}