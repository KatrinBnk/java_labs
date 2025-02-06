interface IConst1 {
    static final int verConst = 101;
}

interface IConst2 {
    int verConst = 102;
}

interface IConst_2 extends IConst1, IConst2 {
    static final int verConst = 100;
    int get(boolean b);
}

class ClassInt_2 implements IConst_2 {
    public int get(boolean b) {
        return b ? IConst1.verConst : IConst2.verConst;
    }
}

class TestInterface2 {
    public static void main(String[] args) {
        ClassInt_2 cl = new ClassInt_2();
        System.out.println("cl.verConst=" + cl.verConst);
        System.out.println("ClassInt.verConst=" + ClassInt.verConst);

        IConst_2 iC1 = cl;
        System.out.println("iC1.verConst= " + iC1.verConst);

        IConst2 iC2 = cl;
        System.out.println("iC2.verConst= " + iC2.verConst);

        IConst_2 iC = cl;
        System.out.println("iC.verConst= " + iC.verConst);

        System.out.println("IConst1.verConst=" + IConst1.verConst);
        System.out.println("IConst2.verConst=" + IConst2.verConst);
        System.out.println("IConst.verConst=" + IConst.verConst);

        System.out.println("cl.get(true)=" + cl.get(true) + " cl.get(false)=" + cl.get(false));
    }
}
