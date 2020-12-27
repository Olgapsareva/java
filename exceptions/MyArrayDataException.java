package geekbrains.lesson10;

public class MyArrayDataException extends Exception{

    private static int row;
    private static int column;

    public MyArrayDataException(int row, int column, Throwable e){
        super(e);
        this.row = row;
        this.column = column;
    }

    @Override
    public String getMessage() {
        return String.format("ошибка типа данных в ячейке с индексом [%d][%d]", row, column);
    }


}
