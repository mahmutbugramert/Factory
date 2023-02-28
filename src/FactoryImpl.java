import java.util.ArrayList;
import java.util.NoSuchElementException;

public class FactoryImpl implements Factory{
	private Holder first;
	private Holder last;
	private Integer size;
	
	public FactoryImpl() {
		
	}
	@Override
	public void addFirst(Product product) {
		Holder firstHolder = new Holder(null, product, null);
		if(first != null) {
			firstHolder.setNextHolder(first);
			first.setPreviousHolder(firstHolder);
			first = firstHolder;
			size++;
			return;
		}
		first = firstHolder;
		last = firstHolder;
		size = 1;
		return;
	}	
	@Override
	public void addLast(Product product) {
		Holder lastHolder = new Holder(null, product, null);
		if(first != null) {
			lastHolder.setPreviousHolder(last);
			last.setNextHolder(lastHolder);
			last = lastHolder;
			size++;
			return;
		}
		first = lastHolder;
		last = lastHolder;
		size = 1;
		return;
	}
	@Override
	public Product removeFirst() throws NoSuchElementException {
		if(size != null) {
			if (size == 1) {
				Product firstProduct = first.getProduct();
				first = null;
				last = null;
				size = null;
				return firstProduct;
			}
			else if(size > 1) {
				Product firstProduct = first.getProduct();
				first = first.getNextHolder();
				first.setPreviousHolder(null);
				size--;
				return firstProduct;
			}
		}
		throw new NoSuchElementException("Factory is empty.");
	}
	@Override
	public Product removeLast() throws NoSuchElementException {
		if(size != null) {
			if(size == 1) {
				Product lastProduct = last.getProduct();
				first = null;
				last = null;
				size = null;
				return lastProduct;
			}
			if(size > 1) {
				Product lastProduct = last.getProduct();
				last = last.getPreviousHolder();
				last.setNextHolder(null);
				size--;
				return lastProduct;
			}
		}	
		throw new NoSuchElementException("Factory is empty.");
	}
	@Override
	public Product find(int id) throws NoSuchElementException {
		if(size != null) {
			Holder currentHolder = first;
			for(int i = 0; i< size; i++) {
				if(currentHolder.getProduct().getId() == id) {
					return currentHolder.getProduct();
				}
				else if(currentHolder.getNextHolder() != null){
					currentHolder = currentHolder.getNextHolder();
				}
			}
		}	
		throw new NoSuchElementException("Product not found.");
		
	}
	@Override
	public Product update(int id, Integer value) throws NoSuchElementException {
		Product myProduct = find(id);
		if(myProduct != null) {
			Product lastProduct = new Product(id, myProduct.getValue());
			Holder currentHolder = first;
			for(int i = 0; i< size; i++) {
				if(currentHolder.getProduct().getId() == id) {
					currentHolder.getProduct().setValue(value);
				}
				else if(currentHolder.getNextHolder() != null){
					currentHolder = currentHolder.getNextHolder();
				}
			}
			return lastProduct;
		}
		throw new NoSuchElementException("Product not found.");
	}
	@Override
	public Product get(int index) throws IndexOutOfBoundsException {
		if(size == null || size == 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		Holder currentHolder = first;
		for(int i = 0; i< size; i++) {
			if(i == index) {
				return currentHolder.getProduct();
			}
			else if(currentHolder.getNextHolder() != null){
				currentHolder = currentHolder.getNextHolder();
			}
		}
		throw new IndexOutOfBoundsException("Index out of bounds.");
	}
	@Override
	public void add(int index, Product product) throws IndexOutOfBoundsException {
		if(index == 0) {
			addFirst(product);
			return;
		}
		if( size == null || index > size) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
	
		if(index == size) {
			addLast(product);
			return;
		}
		Holder currentHolder = first.getNextHolder();
		for(int i = 1; i< size; i++) {
			if(i == index) {
				Holder newHolder = new Holder(currentHolder.getPreviousHolder(), product, currentHolder);
				currentHolder.getPreviousHolder().setNextHolder(newHolder);
				currentHolder.setPreviousHolder(newHolder);
				size++;
				return;
			}
			else if(currentHolder.getNextHolder() != null){
				currentHolder = currentHolder.getNextHolder();
			}
		}
	}
	@Override
	public Product removeIndex(int index) throws IndexOutOfBoundsException {
		if(size == null || index > size - 1 || size == 0) {
			throw new IndexOutOfBoundsException("Index out of bounds.");
		}
		if(index == 0) {
			return removeFirst();
		}
		if(index == size - 1) {
			return removeLast();
		}
		Holder currentHolder = first.getNextHolder();
		for(int i = 1; i< size - 1; i++) {
			if(i == index) {
				currentHolder.getNextHolder().setPreviousHolder(currentHolder.getPreviousHolder());
				currentHolder.getNextHolder().getPreviousHolder().setNextHolder(currentHolder.getNextHolder());
				size--;
				return currentHolder.getProduct();
			}
			else if(currentHolder.getNextHolder() != null){
				currentHolder = currentHolder.getNextHolder();
			}
		}
		throw new IndexOutOfBoundsException("Index out of bounds.");
	}
	@Override
	public Product removeProduct(int value) throws NoSuchElementException {
		if(size == null || size == 0) {
			throw new NoSuchElementException("Product not found.");
		}
		Holder currentHolder = first;
		for(int i = 0; i < size; i++) {
			if(currentHolder.getProduct().getValue() == value && currentHolder == first) {
				removeFirst();
				return currentHolder.getProduct();
			}
			if(currentHolder.getProduct().getValue() == value && currentHolder == last) {
				removeLast();
				return currentHolder.getProduct();
			}
			if(currentHolder.getProduct().getValue() == value) {
				currentHolder.getNextHolder().setPreviousHolder(currentHolder.getPreviousHolder());
				currentHolder.getNextHolder().getPreviousHolder().setNextHolder(currentHolder.getNextHolder());
				size--;
				return currentHolder.getProduct();
			}
			else if(currentHolder.getNextHolder() != null){
				currentHolder = currentHolder.getNextHolder();
			}
		}
		throw new NoSuchElementException("Product not found.");
	}
	@Override
	public int filterDuplicates() {
		if(size == null || size == 0) {
			return 0;
		}
		Holder currentHolder = first;
		ArrayList<Integer> valuesOfProduct = new ArrayList<>();
		int counter = 0;
		for(int i = 0; i < size; i++) {
			if(valuesOfProduct.contains(currentHolder.getProduct().getValue())) {
				removeIndex(i);
				counter++;
				i--;
			}
			else {
				valuesOfProduct.add(currentHolder.getProduct().getValue());
			}
			if(currentHolder.getNextHolder() != null) {
				currentHolder=currentHolder.getNextHolder();
			}
			else {
				return counter;
			}
		}
		return counter;
	}
	@Override
	public void reverse() {
		if(size == null || size == 0) {
			return;
		}
		ArrayList<Product> listOfProducts = new ArrayList<>();
		Holder currentHolder = first;
		for(int i = 0; i < size; i++) {
			listOfProducts.add(currentHolder.getProduct());
			if(currentHolder.getNextHolder() != null) {
				currentHolder = currentHolder.getNextHolder();
			}
		}
		first = null;
		last = null;
		for(int j = size - 1; j >= 0; j--){
			addLast(listOfProducts.get(j));
		}
	}
	public Integer getSize() { //to use size in Project1
		return size;
	}
	

	
	
	
}