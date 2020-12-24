/*+. Создайте три класса Человек, Кот, Робот, которые не наследуются от одного класса.
    Эти классы должны уметь бегать и прыгать (методы просто выводят информацию о действии в консоль).
+. Создайте два класса: беговая дорожка и стена, при прохождении через которые,
    участники должны выполнять соответствующие действия (бежать или прыгать),
    результат выполнения печатаем в консоль (успешно пробежал, не смог пробежать и т.д.).
+. Создайте два массива: с участниками и препятствиями, и заставьте всех участников пройти этот набор препятствий.
4. * У препятствий есть длина (для дорожки) или высота (для стены),
    а участников ограничения на бег и прыжки.
    Если участник не смог пройти одно из препятствий, то дальше по списку он препятствий не идет.*/


package geekbrains.lesson9;

public class App {
    public static void main(String[] args) {

        Wall wall = new Wall();
        RunningTack runningTack = new RunningTack();

        wall.printInfo();
        runningTack.printInfo();

        Human human = new Human("Иван",  35);
        human.printInfo();
        Robot robot = new Robot("Walle");
        robot.printInfo();
        Cat cat = new Cat("Барсик", 2);
        cat.printInfo();

        Obstacles[] obstacles = {wall, runningTack};
        Movable[] players = {human, robot, cat};

       doExcercise(players, obstacles);

    }

    private static void doExcercise(Movable[] players, Obstacles[] obstacles) {
        for (Movable player : players) {
            if(player.jump(obstacles[0].size)){
                player.run(obstacles[1].size);
            }

        }
    }
}
