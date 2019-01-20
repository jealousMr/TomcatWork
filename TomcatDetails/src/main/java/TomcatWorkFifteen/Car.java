package TomcatWorkFifteen;

public class Car implements CarMBean{
    private String color="red";
    @Override
    public String getColor() {
        return color;
    }

    @Override
    public void setColor(String color) {
        this.color=color;
    }

    @Override
    public void driver() {
        System.out.println("dirver you car");
    }
}
