package prog03;

public class LinearFib implements Fib{

	@Override
	public double fib(int n) {
		double previous, current, next;
		previous = 0;
		current = 1;
		
		for(int i=0; i<n; i++)
		{
			next = previous + current;
			previous = current;
			current = next;
		}
		
		return previous;
	}

	@Override
	public double O(int n) {
		return n;
	}

}
