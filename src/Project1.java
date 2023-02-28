import java.io.File;  
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Project1 {
	public static void main(String[] args) {
		String data = null;
		ArrayList<String> listOfInputs = new ArrayList<String>(); 
		try {// reads input file
			File myObj = new File(args[0]);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				data = myReader.nextLine();
				listOfInputs.add(data);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		FactoryImpl factoryLine = new FactoryImpl();
		FileWriter myWriter = null; // to write into output file
		try {
			myWriter = new FileWriter(args[1]);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		for(int i = 0; i<listOfInputs.size();i++) {// loop and if else statements traverse the input
			if(listOfInputs.get(i).length() == 1) {
				if(listOfInputs.get(i).equals("P")) {
					writeForPrint(myWriter, factoryLine);
				}
				if(listOfInputs.get(i).equals("R")) {
					factoryLine.reverse();
					writeForPrint(myWriter, factoryLine);
				}
			}	
			if(listOfInputs.get(i).length() == 2) {
				if(listOfInputs.get(i).equals("RF")) {
					try {
						writeTheOutput(factoryLine.removeFirst().toString(), myWriter);
					} 
					catch (Exception e) {
						writeTheError("Factory is empty.", myWriter);
					}
				}
				if(listOfInputs.get(i).equals("RL")) {
					try {
						writeTheOutput(factoryLine.removeLast().toString(), myWriter);
					} catch (Exception e) {
						writeTheError("Factory is empty.", myWriter);
						}
				}	
				if(listOfInputs.get(i).equals("FD")) {
					try {
						writeTheOutput(Integer.toString(factoryLine.filterDuplicates()), myWriter);
					} 
					catch (Exception e) {
						
					}
				}
			}
			else {
				String[] arrayOfValues = listOfInputs.get(i).split(" ");
				if(arrayOfValues[0].equals("AF")) {
					Product newProduct = new Product( Integer.valueOf(arrayOfValues[1]), Integer.valueOf(arrayOfValues[2]));
					factoryLine.addFirst(newProduct);
				}
				if(arrayOfValues[0].equals("AL")) {
					Product newProduct = new Product( Integer.valueOf(arrayOfValues[1]), Integer.valueOf(arrayOfValues[2]));
					factoryLine.addLast(newProduct);
				}
				if(arrayOfValues[0].equals("A")) {
					int index = Integer.valueOf(arrayOfValues[1]);
					Product newProduct = new Product( Integer.valueOf(arrayOfValues[2]), Integer.valueOf(arrayOfValues[3]));
					try {
						factoryLine.add(index, newProduct);
					} catch (Exception e) {
						writeTheError("Index out of bounds.", myWriter);
						}
				}
				if(arrayOfValues[0].equals("RI")) {
					try {
						writeTheOutput(factoryLine.removeIndex(Integer.valueOf(arrayOfValues[1])).toString(), myWriter);
					} catch (Exception e) {
						writeTheError("Index out of bounds.", myWriter);
						}
				}
				if(arrayOfValues[0].equals("RP")) {
					try {
						writeTheOutput(factoryLine.removeProduct(Integer.valueOf(arrayOfValues[1])).toString(), myWriter);
					} catch (Exception e) {
						writeTheError("Product not found.", myWriter);
						}
				}
				if(arrayOfValues[0].equals("F")) {
					try {
						writeTheOutput(factoryLine.find(Integer.valueOf(arrayOfValues[1])).toString(), myWriter);
					} 
					catch (Exception e) {
						writeTheError("Product not found.", myWriter);
					}
				}
				if(arrayOfValues[0].equals("G")) {
					try {
						writeTheOutput(factoryLine.get(Integer.valueOf(arrayOfValues[1])).toString(), myWriter);
					} 
					catch (Exception e) {
						writeTheError("Index out of bounds.", myWriter);
					}
				}
				if(arrayOfValues[0].equals("U")) {
					try {
						writeTheOutput(factoryLine.update(Integer.valueOf(arrayOfValues[1]), Integer.valueOf(arrayOfValues[2])).toString(), myWriter);
					} 
					catch (Exception e) {
						writeTheError("Product not found.", myWriter);
					}
				}	
			}
		}
		try {
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void writeTheOutput(String toWrite, FileWriter myWriter) {//writes methods output
		try {
			myWriter.write(toWrite+"\n");
		}
		catch (Exception e) {
			
		}	
	}
	public static void writeForPrint2(String toWrite, FileWriter myWriter) {// writes print method to output
		try {;
			myWriter.write(toWrite);
		}
		catch (Exception e) {
			
		}	
	}
	public static void writeTheError(String error, FileWriter myWriter) {// writes errors to output
		try {
			myWriter.write(error+"\n");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public static void writeForPrint(FileWriter myWriter, FactoryImpl factoryLine) {// writes print method to output
		Integer sizeOfLine = factoryLine.getSize();
		if(sizeOfLine == null || sizeOfLine == 0) {
			try {
				writeForPrint2("{}\n", myWriter);
			} 
			catch (Exception e) {
				
			}
		}
		else {	
			try {
		
				writeForPrint2("{", myWriter);
			} 
			catch (Exception e) {
			
			}	
			for(int k = 0; k < sizeOfLine; k++) {
				if(k == sizeOfLine -1) {
					try {
						writeForPrint2(factoryLine.get(k).toString(), myWriter);
					} 
					catch (Exception e) {
						
					}
				}
				else{
					if (k == sizeOfLine) {
						try {
							writeForPrint2(factoryLine.get(k).toString(), myWriter);
						} 
						catch (Exception e) {
							
						}
					}
					try {
						writeForPrint2(factoryLine.get(k).toString()+",", myWriter);
					} 
					catch (Exception e) {
						
					}
				}
			}
			try {
				writeForPrint2("}\n", myWriter);
			} 
			catch (Exception e) {
			
			}
		}	
	}	
}
	