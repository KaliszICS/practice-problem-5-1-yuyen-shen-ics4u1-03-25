import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Base test class that provides reflection safety methods
 */
class PracticeProblemTest {
    
    /**
     * Safely checks if a class exists
     * @param className The fully qualified class name
     * @return The Class object or null if it doesn't exist
     */
    protected Class<?> safeGetClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            fail("Class" + className + " does not exist");
            return null;
        }
    }

    protected Class<?> safeGetClass2(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
    
    /**
     * Safely checks if a constructor exists
     * @param clazz The class to check
     * @param parameterTypes The parameter types for the constructor
     * @return The Constructor object or null if it doesn't exist
     */
    protected Constructor<?> safeGetConstructor(Class<?> clazz, Class<?>... parameterTypes) {
        if (clazz == null) return null;
        try {
            return clazz.getDeclaredConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            fail("Constructor does not exist")
            return null;
        }
    }
    
    /**
     * Safely checks if a method exists
     * @param clazz The class to check
     * @param methodName The method name
     * @param parameterTypes The parameter types for the method
     * @return The Method object or null if it doesn't exist
     */
    protected Method safeGetMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        if (clazz == null) return null;
        try {
            return clazz.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            fail("Class" + methodName + " does not exist");
            return null;
        }
    }

    /**
     * Safely checks if a method exists in the class or any of its superclasses
     * @param clazz The class to check
     * @param methodName The method name
     * @param parameterTypes The parameter types for the method
     * @return The Method object or null if it doesn't exist
     */
    protected Method safeGetMethodIncludingInherited(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        if (clazz == null) return null;
        try {
            return clazz.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            fail("Class" + methodName + " was not inherited");
            return null;
        }
    }
    
    /**
     * Safely checks if a field exists
     * @param clazz The class to check
     * @param fieldName The field name
     * @return The Field object or null if it doesn't exist
     */
    protected Field safeGetField(Class<?> clazz, String fieldName) {
        if (clazz == null) return null;
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
    
    /**
     * Safely creates a new instance using a constructor
     * @param constructor The constructor to use
     * @param parameters The parameters to pass to the constructor
     * @return The new instance or null if instantiation fails
     */
    protected Object safeNewInstance(Constructor<?> constructor, Object... parameters) {
        if (constructor == null) return null;
        try {
            return constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }
    
    /**
     * Safely invokes a method
     * @param method The method to invoke
     * @param object The object to invoke the method on (null for static methods)
     * @param parameters The parameters to pass to the method
     * @return The result of the method invocation or null if invocation fails
     */
    protected Object safeInvokeMethod(Method method, Object object, Object... parameters) {
        if (method == null) return null;
        try {
            return method.invoke(object, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }
    
    /**
     * Safely sets a field value
     * @param field The field to set
     * @param object The object to set the field on (null for static fields)
     * @param value The value to set
     * @return true if successful, false otherwise
     */
    protected boolean safeSetField(Field field, Object object, Object value) {
        if (field == null) return false;
        try {
            field.setAccessible(true);
            field.set(object, value);
            return true;
        } catch (IllegalAccessException e) {
            return false;
        }
    }
    
    /**
     * Safely gets a field value
     * @param field The field to get
     * @param object The object to get the field from (null for static fields)
     * @return The field value or null if access fails
     */
    protected Object safeGetField(Field field, Object object) {
        if (field == null) return null;
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            return null;
        }
    }
    
    /**
     * Safely checks if a class is a subclass of another
     * @param clazz The class to check
     * @param superClass The potential superclass
     * @return true if clazz extends superClass, false otherwise
     */
    protected boolean isSubclassOf(Class<?> clazz, Class<?> superClass) {
        if (clazz == null || superClass == null) return false;
        return superClass.isAssignableFrom(clazz);
    }

    
    @Test
    public void testPersonClassExists() {
        Class<?> personClass = safeGetClass("Person");
        assertNotNull(personClass, "Person class should exist");
    }
    
    @Test
    public void testPersonConstructor() {
        Class<?> personClass = safeGetClass("Person");
        Constructor<?> constructor = safeGetConstructor(personClass, String.class, int.class);
        assertNotNull(constructor, "Person should have a constructor with String and int parameters");
    }
    
    @Test
    public void testPersonFields() {
        Class<?> personClass = safeGetClass("Person");
        
        Field nameField = safeGetField(personClass, "name");
        assertNotNull(nameField, "Person should have a name field");
        assertEquals(String.class, nameField.getType(), "Name field should be of type String");
        
        Field ageField = safeGetField(personClass, "age");
        assertNotNull(ageField, "Person should have an age field");
        assertEquals(int.class, ageField.getType(), "Age field should be of type int");
    }
    
    @Test
    public void testPersonGettersAndSetters() {
        Class<?> personClass = safeGetClass("Person");
        
        Method getNameMethod = safeGetMethod(personClass, "getName");
        assertNotNull(getNameMethod, "Person should have a getName method");
        assertEquals(String.class, getNameMethod.getReturnType(), "getName should return String");
        
        Method setNameMethod = safeGetMethod(personClass, "setName", String.class);
        assertNotNull(setNameMethod, "Person should have a setName method");
        assertEquals(void.class, setNameMethod.getReturnType(), "setName should return void");
        
        Method getAgeMethod = safeGetMethod(personClass, "getAge");
        assertNotNull(getAgeMethod, "Person should have a getAge method");
        assertEquals(int.class, getAgeMethod.getReturnType(), "getAge should return int");
        
        Method setAgeMethod = safeGetMethod(personClass, "setAge", int.class);
        assertNotNull(setAgeMethod, "Person should have a setAge method");
        assertEquals(void.class, setAgeMethod.getReturnType(), "setAge should return void");
    }
    
    @Test
    public void testPersonFunctionality() {
        Class<?> personClass = safeGetClass("Person");
        if (personClass == null) return;
        
        Constructor<?> constructor = safeGetConstructor(personClass, String.class, int.class);
        if (constructor == null) return;
        
        Method getNameMethod = safeGetMethod(personClass, "getName");
        Method getAgeMethod = safeGetMethod(personClass, "getAge");
        Method setNameMethod = safeGetMethod(personClass, "setName", String.class);
        Method setAgeMethod = safeGetMethod(personClass, "setAge", int.class);
        if (getNameMethod == null || getAgeMethod == null || 
            setNameMethod == null || setAgeMethod == null) return;
        
        // Test constructor and getters
        Object person = safeNewInstance(constructor, "John", 30);
        assertNotNull(person, "Person instance should be created");
        
        assertEquals("John", safeInvokeMethod(getNameMethod, person), 
                   "getName should return the constructor-initialized name");
        assertEquals(30, safeInvokeMethod(getAgeMethod, person), 
                   "getAge should return the constructor-initialized age");
        
        // Test setters
        safeInvokeMethod(setNameMethod, person, "Jane");
        safeInvokeMethod(setAgeMethod, person, 25);
        
        assertEquals("Jane", safeInvokeMethod(getNameMethod, person), 
                   "getName should return the updated name");
        assertEquals(25, safeInvokeMethod(getAgeMethod, person), 
                   "getAge should return the updated age");
    }

    
    @Test
    public void testParentClassExists() {
        Class<?> parentClass = safeGetClass("Parent");
        assertNotNull(parentClass, "Parent class should exist");
    }
    
    @Test
    public void testParentExtendsPersonClass() {
        Class<?> parentClass = safeGetClass("Parent");
        Class<?> personClass = safeGetClass("Person");
        
        if (parentClass != null && personClass != null) {
            assertTrue(isSubclassOf(parentClass, personClass), 
                    "Parent should extend Person");
        }
    }
    
    @Test
    public void testParentConstructors() {
        Class<?> parentClass = safeGetClass("Parent");
        Class<?> personClass = safeGetClass("Person");
        
        // First constructor with name, age, spouse
        Constructor<?> constructor1 = safeGetConstructor(parentClass, String.class, int.class, parentClass);
        assertNotNull(constructor1, "Parent should have a constructor with String, int, and Parent parameters");
        
        // Second constructor with name, age (same as Person)
        Constructor<?> constructor2 = safeGetConstructor(parentClass, String.class, int.class);
        assertNotNull(constructor2, "Parent should have a constructor with String and int parameters");
    }
    
    @Test
    public void testParentFields() {
        Class<?> parentClass = safeGetClass("Parent");
        Class<?> childClass = safeGetClass("Child");
        
        Field spouseField = safeGetField(parentClass, "spouse");
        assertNotNull(spouseField, "Parent should have a spouse field");
        if (parentClass != null) {
            assertEquals(parentClass, spouseField.getType(), "Spouse field should be of type Parent");
        }
        
        Field childrenField = safeGetField(parentClass, "children");
        assertNotNull(childrenField, "Parent should have a children field");
        assertTrue(childrenField.getType().isArray(), "Children field should be an array");
        if (childClass != null) {
            assertEquals(childClass, childrenField.getType().getComponentType(), 
                      "Children array should contain Child objects");
        }
    }
    
    @Test
    public void testParentGettersAndSetters() {
        Class<?> parentClass = safeGetClass("Parent");
        Class<?> childClass = safeGetClass("Child");
        
        // Test inherited getters and setters - use getMethod instead of getDeclaredMethod
        Method getNameMethod = parentClass == null ? null : 
            safeGetMethodIncludingInherited(parentClass, "getName");
        assertNotNull(getNameMethod, "Parent should have getName method (inherited or overridden)");
        
        Method setNameMethod = parentClass == null ? null :
            safeGetMethodIncludingInherited(parentClass, "setName", String.class);
        assertNotNull(setNameMethod, "Parent should have setName method (inherited or overridden)");
        
        Method getAgeMethod = parentClass == null ? null :
            safeGetMethodIncludingInherited(parentClass, "getAge");
        assertNotNull(getAgeMethod, "Parent should have getAge method (inherited or overridden)");
        
        Method setAgeMethod = parentClass == null ? null :
            safeGetMethodIncludingInherited(parentClass, "setAge", int.class);
        assertNotNull(setAgeMethod, "Parent should have setAge method (inherited or overridden)");
        
        // Test Parent-specific getters and setters
        Method getSpouseMethod = safeGetMethod(parentClass, "getSpouse");
        assertNotNull(getSpouseMethod, "Parent should have a getSpouse method");
        if (parentClass != null) {
            assertEquals(parentClass, getSpouseMethod.getReturnType(), "getSpouse should return Parent");
        }
        
        Method setSpouseMethod = safeGetMethod(parentClass, "setSpouse", parentClass);
        assertNotNull(setSpouseMethod, "Parent should have a setSpouse method");
        assertEquals(void.class, setSpouseMethod.getReturnType(), "setSpouse should return void");
        
        Method getChildrenMethod = safeGetMethod(parentClass, "getChildren");
        assertNotNull(getChildrenMethod, "Parent should have a getChildren method");
        assertTrue(getChildrenMethod.getReturnType().isArray(), "getChildren should return an array");
        
        Method setChildrenMethod = safeGetMethod(parentClass, "setChildren", getChildrenMethod.getReturnType());
        assertNotNull(setChildrenMethod, "Parent should have a setChildren method");
        assertEquals(void.class, setChildrenMethod.getReturnType(), "setChildren should return void");
    }
    
    @Test
    public void testAddChildMethod() {
        Class<?> parentClass = safeGetClass("Parent");
        Class<?> childClass = safeGetClass("Child");
        
        Method addChildMethod = safeGetMethod(parentClass, "addChild", childClass);
        assertNotNull(addChildMethod, "Parent should have an addChild method");
        assertEquals(void.class, addChildMethod.getReturnType(), "addChild should return void");
    }
    
    @Test
    public void testParentFunctionality() {
        Class<?> parentClass = safeGetClass("Parent");
        Class<?> childClass = safeGetClass("Child");
        if (parentClass == null || childClass == null) return;
        
        // Create parent instances
        Constructor<?> parentConstructor = safeGetConstructor(parentClass, String.class, int.class);
        if (parentConstructor == null) return;
        
        Object father = safeNewInstance(parentConstructor, "John", 35);
        Object mother = safeNewInstance(parentConstructor, "Mary", 32);
        assertNotNull(father, "Father instance should be created");
        assertNotNull(mother, "Mother instance should be created");
        
        // Set spouse relationship
        Method setSpouseMethod = safeGetMethod(parentClass, "setSpouse", parentClass);
        Method getSpouseMethod = safeGetMethod(parentClass, "getSpouse");
        if (setSpouseMethod == null || getSpouseMethod == null) return;
        
        safeInvokeMethod(setSpouseMethod, father, mother);
        safeInvokeMethod(setSpouseMethod, mother, father);
        
        assertEquals(mother, safeInvokeMethod(getSpouseMethod, father), 
                   "Father's spouse should be mother");
        assertEquals(father, safeInvokeMethod(getSpouseMethod, mother), 
                   "Mother's spouse should be father");
        
        // Create a child
        Constructor<?> childConstructor = safeGetConstructor(childClass, String.class, int.class, parentClass, parentClass);
        if (childConstructor == null) return;
        
        Object child = safeNewInstance(childConstructor, "Baby", 1, father, mother);
        assertNotNull(child, "Child instance should be created");
        
        // Add child to parents
        Method addChildMethod = safeGetMethod(parentClass, "addChild", childClass);
        Method getChildrenMethod = safeGetMethod(parentClass, "getChildren");
        if (addChildMethod == null || getChildrenMethod == null) return;
        
        safeInvokeMethod(addChildMethod, father, child);
        safeInvokeMethod(addChildMethod, mother, child);
        
        Object[] fatherChildren = (Object[]) safeInvokeMethod(getChildrenMethod, father);
        Object[] motherChildren = (Object[]) safeInvokeMethod(getChildrenMethod, mother);
        
        assertNotNull(fatherChildren, "Father's children array should exist");
        assertNotNull(motherChildren, "Mother's children array should exist");
        
        if (fatherChildren != null && fatherChildren.length > 0) {
            assertEquals(child, fatherChildren[0], "Child should be in father's children array");
        }
        
        if (motherChildren != null && motherChildren.length > 0) {
            assertEquals(child, motherChildren[0], "Child should be in mother's children array");
        }
    }

    
    @Test
    public void testChildClassExists() {
        Class<?> childClass = safeGetClass("Child");
        assertNotNull(childClass, "Child class should exist");
    }
    
    @Test
    public void testChildExtendsPersonClass() {
        Class<?> childClass = safeGetClass("Child");
        Class<?> personClass = safeGetClass("Person");
        
        if (childClass != null && personClass != null) {
            assertTrue(isSubclassOf(childClass, personClass), 
                    "Child should extend Person");
        }
    }
    
    @Test
    public void testChildConstructor() {
        Class<?> childClass = safeGetClass("Child");
        Class<?> parentClass = safeGetClass("Parent");
        
        Constructor<?> constructor = safeGetConstructor(childClass, String.class, int.class, parentClass, parentClass);
        assertNotNull(constructor, "Child should have a constructor with String, int, Parent, and Parent parameters");
    }
    
    @Test
    public void testChildFields() {
        Class<?> childClass = safeGetClass("Child");
        Class<?> parentClass = safeGetClass("Parent");
        
        Field parent1Field = safeGetField(childClass, "parent1");
        assertNotNull(parent1Field, "Child should have a parent1 field");
        if (parentClass != null) {
            assertEquals(parentClass, parent1Field.getType(), "Parent1 field should be of type Parent");
        }
        
        Field parent2Field = safeGetField(childClass, "parent2");
        assertNotNull(parent2Field, "Child should have a parent2 field");
        if (parentClass != null) {
            assertEquals(parentClass, parent2Field.getType(), "Parent2 field should be of type Parent");
        }
        
        Field siblingsField = safeGetField(childClass, "siblings");
        assertNotNull(siblingsField, "Child should have a siblings field");
        assertTrue(siblingsField.getType().isArray(), "Siblings field should be an array");
        if (childClass != null) {
            assertEquals(childClass, siblingsField.getType().getComponentType(), 
                      "Siblings array should contain Child objects");
        }
    }
    
     @Test
    public void testChildGettersAndSetters() {
        Class<?> childClass = safeGetClass("Child");
        Class<?> parentClass = safeGetClass("Parent");
        
        // Test inherited getters and setters - use getMethod instead of getDeclaredMethod
        Method getNameMethod = childClass == null ? null :
            safeGetMethodIncludingInherited(childClass, "getName");
        assertNotNull(getNameMethod, "Child should have getName method (inherited or overridden)");
        
        Method setNameMethod = childClass == null ? null :
            safeGetMethodIncludingInherited(childClass, "setName", String.class);
        assertNotNull(setNameMethod, "Child should have setName method (inherited or overridden)");
        
        Method getAgeMethod = childClass == null ? null :
            safeGetMethodIncludingInherited(childClass, "getAge");
        assertNotNull(getAgeMethod, "Child should have getAge method (inherited or overridden)");
        
        Method setAgeMethod = childClass == null ? null :
            safeGetMethodIncludingInherited(childClass, "setAge", int.class);
        assertNotNull(setAgeMethod, "Child should have setAge method (inherited or overridden)");
        
        // Test Child-specific getters (no setters for parents)
        Method getParent1Method = safeGetMethod(childClass, "getParent1");
        assertNotNull(getParent1Method, "Child should have a getParent1 method");
        if (parentClass != null) {
            assertEquals(parentClass, getParent1Method.getReturnType(), "getParent1 should return Parent");
        }
        
        Method getParent2Method = safeGetMethod(childClass, "getParent2");
        assertNotNull(getParent2Method, "Child should have a getParent2 method");
        if (parentClass != null) {
            assertEquals(parentClass, getParent2Method.getReturnType(), "getParent2 should return Parent");
        }
        
        // Test for absence of parent setters
        Method setParent1Method = safeGetMethod2(childClass, "setParent1", parentClass);
        assertNull(setParent1Method, "Child should NOT have a setParent1 method");
        
        Method setParent2Method = safeGetMethod2(childClass, "setParent2", parentClass);
        assertNull(setParent2Method, "Child should NOT have a setParent2 method");
        
        // Test siblings getter and setter
        Method getSiblingsMethod = safeGetMethod(childClass, "getSiblings");
        assertNotNull(getSiblingsMethod, "Child should have a getSiblings method");
        assertTrue(getSiblingsMethod.getReturnType().isArray(), "getSiblings should return an array");
        
        Method setSiblingsMethod = safeGetMethod(childClass, "setSiblings", getSiblingsMethod.getReturnType());
        assertNotNull(setSiblingsMethod, "Child should have a setSiblings method");
        assertEquals(void.class, setSiblingsMethod.getReturnType(), "setSiblings should return void");
    }
    
    @Test
    public void testAddSiblingMethod() {
        Class<?> childClass = safeGetClass("Child");
        
        Method addSiblingMethod = safeGetMethod(childClass, "addSibling", childClass);
        assertNotNull(addSiblingMethod, "Child should have an addSibling method");
        assertEquals(void.class, addSiblingMethod.getReturnType(), "addSibling should return void");
    }
    
    @Test
    public void testChildFunctionality() {
        Class<?> parentClass = safeGetClass("Parent");
        Class<?> childClass = safeGetClass("Child");
        if (parentClass == null || childClass == null) return;
        
        // Create parent instances
        Constructor<?> parentConstructor = safeGetConstructor(parentClass, String.class, int.class);
        if (parentConstructor == null) return;
        
        Object father = safeNewInstance(parentConstructor, "John", 35);
        Object mother = safeNewInstance(parentConstructor, "Mary", 32);
        assertNotNull(father, "Father instance should be created");
        assertNotNull(mother, "Mother instance should be created");
        
        // Create child instances
        Constructor<?> childConstructor = safeGetConstructor(childClass, String.class, int.class, parentClass, parentClass);
        if (childConstructor == null) return;
        
        Object child1 = safeNewInstance(childConstructor, "Child1", 5, father, mother);
        Object child2 = safeNewInstance(childConstructor, "Child2", 3, father, mother);
        assertNotNull(child1, "Child1 instance should be created");
        assertNotNull(child2, "Child2 instance should be created");
        
        // Test parent getters
        Method getParent1Method = safeGetMethod(childClass, "getParent1");
        Method getParent2Method = safeGetMethod(childClass, "getParent2");
        if (getParent1Method == null || getParent2Method == null) return;
        
        assertEquals(father, safeInvokeMethod(getParent1Method, child1), 
                   "Child1's parent1 should be father");
        assertEquals(mother, safeInvokeMethod(getParent2Method, child1), 
                   "Child1's parent2 should be mother");
        
        // Test addSibling method
        Method addSiblingMethod = safeGetMethod(childClass, "addSibling", childClass);
        Method getSiblingsMethod = safeGetMethod(childClass, "getSiblings");
        if (addSiblingMethod == null || getSiblingsMethod == null) return;
        
        safeInvokeMethod(addSiblingMethod, child1, child2);
        safeInvokeMethod(addSiblingMethod, child2, child1);
        
        Object[] child1Siblings = (Object[]) safeInvokeMethod(getSiblingsMethod, child1);
        Object[] child2Siblings = (Object[]) safeInvokeMethod(getSiblingsMethod, child2);
        
        assertNotNull(child1Siblings, "Child1's siblings array should exist");
        assertNotNull(child2Siblings, "Child2's siblings array should exist");
        
        if (child1Siblings != null && child1Siblings.length > 0) {
            assertEquals(child2, child1Siblings[0], "Child2 should be in Child1's siblings array");
        }
        
        if (child2Siblings != null && child2Siblings.length > 0) {
            assertEquals(child1, child2Siblings[0], "Child1 should be in Child2's siblings array");
        }
        
        // Add children to parents
        Method addChildMethod = safeGetMethod(parentClass, "addChild", childClass);
        Method getChildrenMethod = safeGetMethod(parentClass, "getChildren");
        if (addChildMethod == null || getChildrenMethod == null) return;
        
        safeInvokeMethod(addChildMethod, father, child1);
        safeInvokeMethod(addChildMethod, father, child2);
        safeInvokeMethod(addChildMethod, mother, child1);
        safeInvokeMethod(addChildMethod, mother, child2);
        
        Object[] fatherChildren = (Object[]) safeInvokeMethod(getChildrenMethod, father);
        Object[] motherChildren = (Object[]) safeInvokeMethod(getChildrenMethod, mother);
        
        assertNotNull(fatherChildren, "Father's children array should exist");
        assertNotNull(motherChildren, "Mother's children array should exist");
        
        if (fatherChildren != null && fatherChildren.length >= 2) {
            assertEquals(child1, fatherChildren[0], "Child1 should be in father's children array");
            assertEquals(child2, fatherChildren[1], "Child2 should be in father's children array");
        }
        
        if (motherChildren != null && motherChildren.length >= 2) {
            assertEquals(child1, motherChildren[0], "Child1 should be in mother's children array");
            assertEquals(child2, motherChildren[1], "Child2 should be in mother's children array");
        }
    }
}
