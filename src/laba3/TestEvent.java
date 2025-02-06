package laba3;

interface IEv {void Handler();} // Интерфейс события

class Source {// Класс источника события
    IEv iEv;
    Source (IEv iEv) {
        this.iEv= iEv;
    } // Конструктор
    void genEv() {
        iEv.Handler();
    } // Генерировать событие
}

class Receiver implements IEv {// Класс приёмника события
    public void Handler() {
        System.out.println ("OK");
    }// Обработчик
}

class TestEvent {
    public static void main (String[] args) {
        Receiver receiver = new Receiver();// Создать объект приемника
        Source source = new Source(receiver);// Создать объект источника
        source.genEv();
    }
}