class Dan {
    public int xSup, xSub;
    public boolean b;

    public Dan(int Xsub, int Xsup, boolean B) {
        xSub = Xsub;
        xSup = Xsup;
        b = B;
    }
}

class SuperClass {
    int x;
    private boolean b;

    public SuperClass(int X, boolean B) {
        x = X;
        b = B;
    }

    public boolean getB() {
        return b;
    }
}

class SubClass extends SuperClass {
    int x;

    public SubClass(int Xsup, int Xsub, boolean B) {
        super(Xsub, B);
        x = Xsup;
    }

    public Dan get() {
        return new Dan(x, super.x, super.getB());
    }

    public void get (Dan d) {d.xSub= x; d.xSup= super.x; d.b= super.getB();}
}

class Laba2_test1 {
    public static void main(String[] args) {
        SuperClass supC = new SuperClass(5, true);
        System.out.println("supC.x=" + supC.x + " supC.b=" + supC.getB());

        SubClass subC = new SubClass(55, 55, false);
        Dan dx = subC.get();
        System.out.println("subC.x=" + dx.xSub + " supC.x=" + dx.xSup + " subC.b=" + dx.b);

        //Дополнение из раздела "О функциях класса"
        Dan d1= new Dan (0, 0, true);
        subC.get(d1);
        System.out.println ("d1: subC.x= "+ d1.xSub +" supC.x= "+ d1.xSup + " subC.b= " + d1.b);
    }
}

