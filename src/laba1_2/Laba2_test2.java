interface IFunc {
    public int getF();
}

interface IConst {
    public static final int verConst = 100;
}

class ClassInt implements IFunc, IConst {
    public int getF() {
        return verConst;
    }
}

class TestInterface {
    public static void main(String[] args) {
        ClassInt cl = new ClassInt();
        System.out.println("verConst = " + cl.getF());
        IFunc iF = cl;
        System.out.println("verConst = " + iF.getF());
        IConst iC = cl;
        System.out.println("verConst = " + iC.verConst);
        System.out.println("verConst = " + ClassInt.verConst);
    }
}
