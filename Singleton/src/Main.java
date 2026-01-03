// Классическая реализация (ленивая инициализация)
class ClassicSingleton {
    private static ClassicSingleton instance;

    // Приватный конструктор
    private ClassicSingleton() {
        // Инициализация
        System.out.println("ClassicSingleton создан!");
    }

    // Публичный метод для получения экземпляра
    public static ClassicSingleton getInstance() {
        if (instance == null) {
            instance = new ClassicSingleton();
        }
        return instance;
    }

    public void showMessage() {
        System.out.println("Hello from ClassicSingleton!");
    }
}

// Потокобезопасная реализация с двойной проверкой
class ThreadSafeSingleton {
    private static volatile ThreadSafeSingleton instance;

    private ThreadSafeSingleton() {
        System.out.println("ThreadSafeSingleton создан!");
    }

    public static ThreadSafeSingleton getInstance() {
        if (instance == null) {
            synchronized (ThreadSafeSingleton.class) {
                if (instance == null) {
                    instance = new ThreadSafeSingleton();
                }
            }
        }
        return instance;
    }

    public void showMessage() {
        System.out.println("Hello from ThreadSafeSingleton!");
    }
}

// Реализация через enum (рекомендуемый способ)
enum EnumSingleton {
    INSTANCE;

    private EnumSingleton() {
        System.out.println("EnumSingleton создан!");
    }

    public void showMessage() {
        System.out.println("Hello from EnumSingleton!");
    }

    // Дополнительные методы
    public void doSomething() {
        System.out.println("Выполняем какую-то работу...");
    }
}

// Реализация с eager инициализацией
class EagerSingleton {
    private static final EagerSingleton instance = new EagerSingleton();

    private EagerSingleton() {
        System.out.println("EagerSingleton создан!");
    }

    public static EagerSingleton getInstance() {
        return instance;
    }

    public void showMessage() {
        System.out.println("Hello from EagerSingleton!");
    }
}

// Использование
public class Main {
    public static void main(String[] args) {
        // Classic Singleton
        ClassicSingleton classic1 = ClassicSingleton.getInstance();
        ClassicSingleton classic2 = ClassicSingleton.getInstance();
        classic1.showMessage();
        System.out.println("Тот же экземпляр? " + (classic1 == classic2));

        // Thread Safe Singleton
        ThreadSafeSingleton threadSafe1 = ThreadSafeSingleton.getInstance();
        ThreadSafeSingleton threadSafe2 = ThreadSafeSingleton.getInstance();
        threadSafe1.showMessage();
        System.out.println("Тот же экземпляр? " + (threadSafe1 == threadSafe2));

        // Enum Singleton
        EnumSingleton enum1 = EnumSingleton.INSTANCE;
        EnumSingleton enum2 = EnumSingleton.INSTANCE;
        enum1.showMessage();
        enum1.doSomething();
        System.out.println("Тот же экземпляр? " + (enum1 == enum2));

        // Eager Singleton
        EagerSingleton eager1 = EagerSingleton.getInstance();
        EagerSingleton eager2 = EagerSingleton.getInstance();
        eager1.showMessage();
        System.out.println("Тот же экземпляр? " + (eager1 == eager2));
    }
}