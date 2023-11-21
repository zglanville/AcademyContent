package za.co.wethinkcode.robotworlds.Server.Web;
//TODO: remove/integrate this file
public class World {
    private Integer id;
    private String text;
    private String name;
    private String obstacles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setObstacles(String obstacles) {
        this.obstacles = obstacles;
    }

    public String getObstacles() {
        return obstacles;
    }

//    public static World create(String obstacles, String name) {
//        World testworld = new World();
//        testworld.setObstacles(obstacles);
//        testworld.setName(name);
//        return testworld;
//    }
}
