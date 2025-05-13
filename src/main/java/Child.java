class Child extends Person {

    private Parent parent1;
    private Parent parent2;
    private Child[] siblings; 

    public Child(String name, int age, Parent par1, Parent par2) {
        super(name, age);
        this.parent1 = par1;
        this.parent2 = par2;
        this.siblings = new Child[0];
    }
    
    public Parent getParent1() {
        return this.parent1;
    }
    
    public Parent getParent2() {
        return this.parent2;
    }
    
    public void setSiblings(Child[] siblings) {
        this.siblings = siblings;
    }
    
    public Child[] getSiblings() {
        return this.siblings;
    }

    public void addSibling(Child child) {
        Child[] newSiblings = new Child[this.siblings.length + 1];
        for (int i = 0; i < this.siblings.length; i++) {
            newSiblings[i] = this.siblings[i];
        }
        newSiblings[this.siblings.length] = child;
        this.siblings = newSiblings;
    }
}