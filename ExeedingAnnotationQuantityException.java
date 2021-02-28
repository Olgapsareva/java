package lesson7;

public class ExeedingAnnotationQuantityException extends RuntimeException{
    public ExeedingAnnotationQuantityException(){
        super("превышено количество аннотаций данного типа");
    }
}
