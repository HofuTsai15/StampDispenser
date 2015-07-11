package StampDispenser;
import java.util.*;

/**
 * Facilitates dispensing stamps for a postage stamp machine.
 */
public class StampDispenser
{
	// List for storing stamp values
    List<Integer> stamps = new ArrayList<Integer>();
    // Set for eliminating duplicate stamp values
    Set<Integer> set = new HashSet<Integer>();

    /**
     * Constructs a new StampDispenser that will be able to dispense the given 
     * types of stamps.
     *
     * @param stampDenominations The values of the types of stamps that the 
     *     machine should have.  Should be sorted in descending order and 
     *     contain at least a 1.
     */
    public StampDispenser(int[] stampDenominations) throws InputInvalidException
    {
        // First validate the user input
        // In the input, we should not allow negative integer and 0.
        // There must be at least a 1. 
        // There should not be duplicate integers.

        for (int value : stampDenominations) {
            if (value <= 0) {
                throw new InputInvalidException("This array contains element less or equal to 0.");
            } else if (!set.add(value)) {
                throw new InputInvalidException("This array contains duplicate elements.");
            }
        }

        if (!set.contains(1)) 
            throw new InputInvalidException("This array does not contain at least a 1.");

        // Assign the values in the hashset back to the list
        stamps.addAll(set);

        // Sort the array in reverse order
        Collections.sort(stamps, Collections.reverseOrder());
    }
 
    /**
     * Returns the minimum number of stamps that the machine can dispense to
     * fill the given request.
     *
     * @param request The total value of the stamps to be dispensed.
     */
    public int calcMinNumStampsToFillRequest(int request)
    {  
    	// First check for special cases
    	if (request < 0) return -1;
    	else if (request == 0) return 0;
    	// Implement a dynamic programming method
    	else {
    		return dynamicProgramming(request);
    	}
    }
    
    /**
     * A helper function for calcMinNumStampsToFillRequest
     * Returns the minimum number of stamps that the machine can dispense to
     * fill the given request. Special cases of the request have been taken care of. 
     *
     * @param n The total value of the stamps to be dispensed.
     */
    
    public int dynamicProgramming(int n) {
    	int len = stamps.size();
    	/* A array for dynamic programming: 
    	 * Each element representing the minimum number of stamp used 
    	 * or values of its index 
         */
    	int[] numStamp = new int[n+1];
    	// Initialize the initial condition
    	numStamp[0] = 0;
    	
    	/* For each value i, iterate through the stamp values
    	 * If value i is larger or equal to current stamp value, stamps.get(j), 
    	 * meaning value i can be filled by 1 + (the number of stamps needed for 
    	 * value i-stamps.get(j)) number of stamps. 
    	 * Then, compare with the current min value, and update min if necessary
    	 */
    	
    	for (int i = 1; i <= n; i++) {
    		int min = Integer.MAX_VALUE;
    		for (int j = 0; j < len; j++) {
    			if (i >= stamps.get(j)) {
    				if (1 + numStamp[i-stamps.get(j)] < min) {
    					min = 1 + numStamp[i-stamps.get(j)];
    				}
    			}
    		}
    		numStamp[i] = min;
    	}
    	return numStamp[n];
    }
    
    public static void main(String[] args) throws InputInvalidException
    {
        int[] denominations = { 30, 10, 24, 1, 6, 2, 90};
        StampDispenser stampDispenser = new StampDispenser(denominations);

        // Wrong input
        assert(stampDispenser.calcMinNumStampsToFillRequest(-10) == -1);
        assert(stampDispenser.calcMinNumStampsToFillRequest(-1) == -1);
        assert(stampDispenser.calcMinNumStampsToFillRequest(0) == 0);

        // Additional tests
        assert(stampDispenser.calcMinNumStampsToFillRequest(1) == 1);
        assert(stampDispenser.calcMinNumStampsToFillRequest(2) == 1);
        assert(stampDispenser.calcMinNumStampsToFillRequest(5) == 3);
        assert(stampDispenser.calcMinNumStampsToFillRequest(18) == 3);
        assert(stampDispenser.calcMinNumStampsToFillRequest(34) == 2);
        assert(stampDispenser.calcMinNumStampsToFillRequest(72) == 3);
    }
}
