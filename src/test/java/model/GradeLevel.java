package model;

public class GradeLevel {
    private String id;
    private String name;
    private String shortName;
    private String order;
    private Boolean active;
    private GradeLevel nextGradeLevel;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public GradeLevel getNextGradeLevel() {
        return nextGradeLevel;
    }

    public void setNextGradeLevel(GradeLevel nextGradeLevel) {
        this.nextGradeLevel = nextGradeLevel;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}
