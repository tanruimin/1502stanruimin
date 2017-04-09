package bean;



public class phone {

    private String name;
    private int im;

    public phone(String name, int im) {
        this.name = name;
        this.im = im;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIm() {
        return im;
    }

    public void setIm(int im) {
        this.im = im;
    }
}
