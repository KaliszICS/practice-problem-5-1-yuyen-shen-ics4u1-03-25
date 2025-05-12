import java.util.ArrayList;
class Parent extends Person {

    private Parent spouse;
    private Child[] children;

    public Parent(String name, int age) {
        super(name, age);
        this.spouse = null;
        this.children = new Child[0];
    }
    
    public Parent(String name, int age, Parent spouse) {
        super(name, age);
        this.spouse = spouse;
        this.children = new Child[0];
    }

    public Parent getSpouse() {
        return this.spouse;
    }

    public void setSpouse(Parent spouse) {
        this.spouse = spouse;
    }

    public Child[] getChildren() {
        return this.children;
    }

    public void setChildren(Child[] children) {
        this.children = children;
    }

    public void addChild(Child child){
        Child[] newChildren = new Child[this.children.length + 1];
        for (int i = 0; i < this.children.length; i++) {
            newChildren[i] = children[i];
        }
        newChildren[this.children.length] = child;
        this.children = newChildren;
    }
}
