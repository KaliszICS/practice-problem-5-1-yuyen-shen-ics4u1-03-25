import java.util.ArrayList;
class Child extends Person {

    private Parent parent1;
    private Parent parent2;
    private ArrayList<Child> siblings; 

    public Child(String name, int age, Parent par1, Parent par2) {
        super(name, age);
        this.parent1 = par1;
        this.parent2 = par2;
        this.siblings = new ArrayList<>();
    }
    
    public Parent getParent1() {
        return this.parent1;
    }
    
    public Parent getParent2() {
        return this.parent2;
    }
    
    public void setSiblings(ArrayList<Child> siblings) {
        this.siblings = siblings;
    }
    
    public Child[] getSiblings() {
        return this.siblings.toArray(new Child[siblings.size()]);
    }

    public void addSibling(Child child) {
        this.siblings.add(child);
    }
}