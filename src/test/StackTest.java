package test;
import junit.framework.TestCase;
import model.Stack;
import model.HeapSorting;
import model.Patient;

public class StackTest extends TestCase {

    private Stack<Patient> stack;

    public void setupStage1(){
        stack= new Stack("setup1", 1);
    }

    public void setupStage2(){
        reci= new Recipe("setup2",2);

        reci.addIngredient("Cebolla", 315);
        reci.addIngredient("Ajo", 58);
        reci.addIngredient("Arroz", 520);
    }

    public void testAddWeight1 (){
        setupStage1();

        ingredient.tryAddWeight(54);
        assertEquals(ingredient.getWeight(),299.0);
    }

    public  void testAddNegativeWeight (){

        setupStage1();

        ingredient.tryAddWeight(-100);
        assertEquals(ingredient.getWeight(),245.0);
    
    }

    
    public  void testRemoveWeight1 (){

        setupStage1();

        ingredient.TryRemoveWeight(45);
        assertEquals(ingredient.getWeight(),200.0);
    
    }
    
    public  void testRemoveNegativeWeight (){

        setupStage1();

        ingredient.TryRemoveWeight(-100);
        assertEquals(ingredient.getWeight(),245.0);
    
    }

    public  void testAddIngredient (){

        setupStage1();

        reci.addIngredient("Sal", 12.0);
        assertEquals(reci.getIngredients().size(),1);
        assertEquals(reci.getIngredients().get(0).getName(),"Sal");
        assertEquals(reci.getIngredients().get(0).getWeight(),12.0);

    }

    public  void testAddIngredient2 (){

        setupStage2();

        reci.addIngredient("Pimienta", 6.0);
        assertEquals(reci.getIngredients().size(),4);
        assertEquals(reci.getIngredients().get(3).getName(),"Pimienta");
        assertEquals(reci.getIngredients().get(3).getWeight(),6.0);

    }

    public  void testAddIngredient3 (){

        setupStage2();

        reci.addIngredient("Ajo", 21.0);
        assertEquals(reci.getIngredients().size(),3);
        assertEquals(reci.getIngredients().get(1).getName(),"Ajo");
        assertEquals(reci.getIngredients().get(1).getWeight(),79.0);

    }

    public  void testRemoveIngredientSetup2 (){

        setupStage2();

        reci.removeIngredient(1);
        assertEquals(reci.getIngredients().size(),2);
        assertEquals(reci.getIngredients().get(0).getName(),"Cebolla");
        assertEquals(reci.getIngredients().get(1).getName(),"Arroz");
        

    }






    






    
}
