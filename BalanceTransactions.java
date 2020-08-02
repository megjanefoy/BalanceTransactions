import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; 


/** 
 * Title: Assignment 5 
 * Semester: COP3337 – Fall 2019
 * @author Megan Jane Thompson
 *
 * I affirm that this program is entirely my own work
 * and none of it is the work of any other person.
 * 
 * This program calculates daily transactions in a text file. 
 * It calculates if the actual amount of cash at the end of 
 * the day equals the expected value.
 */
public class BalanceTransactions{
  private Scanner keyboard = new Scanner(System.in);
  private Scanner fileReader;
  private double todaysTrans;
  
  
  /**
   * This method prompts the user for a file name
   * and returns the file name as a String.
   * 
   * @return fileName, being a String of the file name to be search.
  */  
  public String getFileName(){
    String fileName = "";
    
    System.out.println("Enter file name: ");                  //prompts user for file name
    if (keyboard.hasNext()){                                  //checks for user input
      fileName = keyboard.next();                             //if text is entered, fileName is saved
    }
    
    return fileName;                                          //returns fileName as String
  }
  
  
  /**
   * This method accepts the fileName as a String
   * and searches for the file.
   * 
   * @param fileName, being a String of the file name to be searched.
  */
  public void openFile(String fileName){
    try{                                                      //trys to opens fileReader Scanner
      fileReader = new Scanner(new File(fileName));
    }
    catch(FileNotFoundException exception){                   //catches FileNotFoundException
      System.out.print("File not found. ");
    }
  }

  
  /**
   * This method checks to ensure file was found
   * and reads the file when found. The text is broken 
   * into segments and stored to be calculated. It calculates
   * the total daily transactions per log information.
  */
  public void readFile(){
    while (fileReader == null){                              //loops until user enters correct info and file is found
      openFile(getFileName());
    }
    while(fileReader.hasNext()){                             //when file is found, file is read while text is available
      String invoice = fileReader.next();                    //stores first part of line, being invoice info
      String amount = fileReader.next();                     //stores second part of line, being transaction amount
      String type = fileReader.next();                       //store third part of line, being transaction type
      
      todaysTrans = todaysTrans + transactionsBalance(invoice, amount, type);    //calculates total day's transactions
    }
  }
  
  
  /**
   * This method processes the file line information and
   * calculates the transactions as positive or negative
   * depending on transaction type.
   * 
   * @parm invoiceVal, String portion of file containing transaction invoice info
   * @parm amountVal, String portion of file containing transaction amount
   * @parm typeVal, String portion of file containing transaction type
   * @return amount, being a double as negative if Paid out or positive if Received
  */
  public double transactionsBalance(String invoiceVal, String amountVal, String typeVal){
    double transactionAmount = Double.parseDouble(amountVal);        //converts transaction amount from Stringto double
    double amount = 0.0;
    
    if (typeVal.equals("P") || typeVal.equals("p")){                 //checks transaction type, P for paid out
      amount = -transactionAmount;                                   //makes transaction amount negative if paid out
    }
    else{
      amount = transactionAmount;                                    //makes transaction amount positive if received
    }
    
    return amount;                                                   //returns transaction amount depending on type
  }
  
  
  /**
   * This method checks if ending balance matches final balance
   * and returns corresponding message to user.
   * 
   * @param beginBal, double figure for user's beginning balance 
   * @param endingBal, double figure for user's ending balance 
  */
  public void calculateBal(double beginBal, double endingBal){
    double finalBal = beginBal + todaysTrans;                        //calculates correct final balance 
    
    if (Math.abs(finalBal - endingBal) < .01){                       //checks if finalBal and endingBal match
      System.out.println("Transactions are balanced!");              //statement to user if figures match
    }
    else{
      System.out.println("Transactions are not balanced.");          //statement to user if figures don't match
    }
    
  }
  
  
  /**
   * This method prompts the user for the balance from the 
   * beginning of the day, checks if the information entered is 
   * valid, loops if entry is not valid, and returns beginning 
   * balance amount when entry is valid.
   * 
   * @return beginningBal, being a double figure for the beginnging balance 
  */
  public double getBeginBal(){
    double beginningBal = 0.0;
    double beginningBal2;
    String line = keyboard.nextLine();                                //clears keyboard before prompt
    System.out.println("Enter beginning of day balance: ");           //prompts for beginning balance
    if(!keyboard.hasNextDouble()){                                    //checks if keyboard does not contain a double
      System.out.print("Invalid entry. Please enter beginning of day balance. ");  //error message if not a double
      beginningBal2 = getBeginBal();                                  //loops until entry is valid
      return beginningBal2;                                           // final return for beginning balance when valid
    }
    else if(keyboard.hasNextDouble()){                                //entered when loop has valid entry
      beginningBal = keyboard.nextDouble();                           //obtains user's input for beginning balance
    }

    return beginningBal;                                              //return from loop to beginningBal2 when valid
  }
  
  
  /**
   * This method prompts the user for the ebalance from the 
   * end of the day, checks if the information entered is 
   * valid, loops if entry is not valid, and returns ending 
   * balance amount when entry is valid.
   * 
   * @return endingBal, being a double figure for the ending balance 
  */
  public double getEndBal(){
    double endingBal = 0.0;
    double endingBal2;
    String line = keyboard.nextLine();                                //clears keyboard before prompt
    System.out.println("Enter end of day balance: ");                 //prompts for ending balance 
    if(!keyboard.hasNextDouble()){                                    //checks if keyboard does not contain a double
      System.out.print("Invalid entry. Please enter end of day balance. ");  //error message if not a double
      endingBal2 = getEndBal();                                       //loops until entry is valid
      return endingBal2;                                              // final return for ending balance when valid
    }
    else if(keyboard.hasNextDouble()){                                //entered when loop has valid entry
      endingBal = keyboard.nextDouble();                              //obtains user's input for ending balance
    }

    return endingBal;                                                //return from loop to endingBal2 when valid
  }
  
  
  /**
   * This method closes the Scanner fileReader
  */
  public void closeFile() {
    fileReader.close();
  }
  
  
  
  /**
   * This method calls to the BalanceTransaction methods
   * for user input, calculating total daily transactions 
   * and checking that ending balance matches final balance
  */
  public static void main(String[] args){
    BalanceTransactions balTran = new BalanceTransactions();        //creates BalanceTransactions object to use methods
    balTran.openFile(balTran.getFileName());                        //calls to getFileName then to openFile()
    balTran.readFile();                                             //calls to readFile()
    double beginBal = balTran.getBeginBal();                        //calls to getBeingBal()
    double endBal = balTran.getEndBal();                            //calls to getEndBal()
    balTran.calculateBal(beginBal, endBal);                         //calls to calculateBal() with beingBal and endBal
    balTran.closeFile();                                            //calls to closeFile()
    
  }
  
}