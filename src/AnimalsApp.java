/*1. Создать классы Собака и Кот с наследованием от класса Животное.
  2. Животные могут выполнять действия:
    бежать,
    плыть,
    перепрыгивать препятствие.
  В качестве параметра каждому методу передается величина, означающая или длину препятствия
    (для бега и плавания), или высоту (для прыжков).
  3. У каждого животного есть ограничения на действия
    бег: кот 200 м., собака 500 м.;
    прыжок: кот 2 м., собака 0.5 м.;
    плавание: кот не умеет плавать, собака 10 м.
  4. При попытке животного выполнить одно из этих действий, оно должно сообщить результат в консоль.
        (Например, dog1.run(150); -> результат: run: true)
  5. * Добавить животным разброс в ограничениях.
    То есть у одной собаки ограничение на бег может быть 400 м., у другой 600 м.*/



package geekbrains.lesson6;

public class AnimalsApp {

    private static final int[] catRunDistance = {100, 110, 80, 90, 1000};
    private static final double[] catJumpDistance = {0.8, 1.8, 0.3, 1.48, 32.8};
    private static final int[] catSwimDistance = {1, 10, 5};

    private static final int[] dogRunDistance = {200, 400, 80, 550, 5000};
    private static final double[] dogJumpDistance = {0.5, 1.5, 0.1, 0.3, 32.8};
    private static final int[] dogSwimDistance = {10, 30, 5, 300};

    public static void main(String[] args) {
        Cat cat1 = new Cat("Tom", "grey", 10);
        Cat cat2 = new Cat("Сильвестр", "черный",2);
        Dog dog1 = new Dog("Belka", "рыжий", 3);
        Dog dog2 = new Dog("Strelka", "белый", 3);

        cat1.printInfo();
        cat2.printInfo();
        dog1.printInfo();
        dog2.printInfo();

        Animal[] animals = {cat1, cat2, dog1, dog2};

       checkAnimalAbilities(animals);

    }

    private static void checkAnimalAbilities(Animal[] animals) {
        for (Animal animal : animals) {
            if(animal instanceof Cat){
                checkCatRun(animal);
                checkCatJump(animal);
                checkCatSwim(animal);
            } else {
                checkDogRun(animal);
                checkDogJump(animal);
                checkDogSwim(animal);
            }


        }
    }

    private static void checkCatRun(Animal animal) {
        for (int i : catRunDistance) {
            animal.run(i);
        }
    }

    private static void checkCatJump(Animal animal) {
        for (double i : catJumpDistance) {
            animal.jump(i);
        }
    }

    private static void checkCatSwim(Animal animal) {
        for (int i : catSwimDistance) {
            animal.swim(i);
        }
    }

    private static void checkDogRun(Animal animal) {
        for (int i : dogRunDistance) {
            animal.run(i);

        }
    }

    private static void checkDogJump(Animal animal) {
        for (double i : dogJumpDistance) {
            animal.jump(i);

        }
    }

    private static void checkDogSwim(Animal animal) {
        for (int i : dogSwimDistance) {
            animal.swim(i);

        }
    }


}
