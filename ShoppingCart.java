/*
Class Name: ShoppingCart
Author: Eric Fang
Date: Dec 18, 2019
School: A.Y.Jackson Secondary School
Purpose: Holds item that user has added and itends to purchase
*/
import java.io.*;
import java.util.ArrayList;
public class ShoppingCart extends SavedProducts{
   protected ArrayList<Product> shoppingCart = new ArrayList<Product>();
   private String filePath;
   public int length;
   
   /*
   Name: ShoppingCart
   Purpose: Initiates ShoppingCart
   Description: Passes in filePath in order to read in the contents and saves 
   it for future use 
   */
   public ShoppingCart(String filePath){
      this.filePath = filePath;
      Product parsedProduct;
      shoppingCart = new ArrayList<Product>();
      try {
         BufferedReader in = new BufferedReader(new FileReader(this.filePath));
         String input;
         while ((input = in.readLine()) != null) {
            length = Integer.parseInt(input)-1;
            for (int i = 0; i < length; i++) {
               parsedProduct = new Product(in.readLine(), Double.parseDouble(in.readLine()), in.readLine(), in.readLine(), Double.parseDouble(in.readLine()),Integer.parseInt(in.readLine()));
               shoppingCart.add(parsedProduct);
            }
         }
         in.close();
      } catch (IOException iox) {
         System.out.println(" ____________________________________");
         System.out.println("|                                    |");
         System.out.println("| File Reading Error!                |");
         System.out.println("|                                    |");
         System.out.println("|____________________________________|");
         System.out.println("Error: " + iox + "\n");
      }
   }
   
   /*
   Name: addToWishList
   Purpose: moves a product from ShoppingCart to WishList
   Description: passes in ArrayList(from WishList) and the position of product
   in ShoppingCart
   */
   public void addToWishList(int position, ArrayList<Product> wishList){
      wishList.add(shoppingCart.get(position));
      removeProduct(position);
      updateFile();
   }
   
   /*
   Name: addProduct
   Purpose: Adds product to array
   Description: Passes in product to be added
   */
   public boolean addProduct(Product newProduct) {
      try {
         shoppingCart.add(newProduct);
         length++;
         updateFile();
         return true;
      } catch (Exception e) {
         System.out.println(e);
         return false;
      }
   }

   /*
   Name: removeProduct
   Purpose: Removes a product from the Array
   Description: passes in index of Product to remove
   */
   public boolean removeProduct(int index) {
      try {
         shoppingCart.remove(index);
         length--;
         updateFile();
         return true;
      } catch (Exception e) {
         return false;
      }
   }
   
   /*
   Name: getProductWithId
   Purpose: finds a Product using Product Id
   Description: passes in id to search with
   */
   public Product getProductWithId(int id) {
      for (int i = 0 ; i < length ; i ++){
         if (shoppingCart.get(i).getId() == id)
            return shoppingCart.get(i);
      }
      return null;
   }
   
   /*
   Name: getProductWithIndex
   Purpose: finds a Product using index of Array
   Description: passes in index of object 
   */
   public Product getProductWithIndex(int index) {
      if (index > this.shoppingCart.size() - 1) {
         return null;
      } else {
         return this.shoppingCart.get(index);
      }
   }

   /*
   Name: updateFile
   Purpose: Updates file whenever a change is made
   Description: Uses filePath which was previously saved during intialization 
   */
   void updateFile(){
      try {
         BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
         out.write(""+length);
         out.newLine();
         for (int i = 0 ; i < length; i ++){
            out.write(shoppingCart.get(i).toString(false));
            out.newLine();
         }
         out.close();
      }
      catch (IOException iox){
      }
   }
}
