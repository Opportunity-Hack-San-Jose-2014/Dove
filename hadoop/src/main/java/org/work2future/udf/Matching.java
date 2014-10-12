package org.work2future.udf;
import org.apache.pig.EvalFunc;
import org.apache.pig.data.Tuple;
import org.apache.pig.data.TupleFactory;
import org.apache.pig.impl.logicalLayer.schema.Schema;
import org.apache.pig.data.DataType;

import java.io.IOException;


/**
 * A filter UDF that determines the distance of two string
 */
public class Matching extends EvalFunc<Float>{

    @Override
    public Float exec(Tuple input) throws IOException  {
        // Make sure the input isn't null and is of the right size.
	    if (input == null || input.size() != 1 )
		    return null;

	    if (input.size() != 1) {
		    throw new RuntimeException("Expecting a single chararray");
	    }
	    Object o = input.get(0);
	    //if (!(o instanceof String)) {
	    //        throw new RuntimeException("Expecting a single chararray");
	    //}
	    //String bio = (String)o;
	    //o = input.get(1);
	    if (!(o instanceof String)) {
		    throw new RuntimeException("Expecting a single chararray");
	    }
	    String [] tokens = ((String)o).split("\\<\\*\\*\\*\\>");
	    if(tokens.length >= 2)
	       return distance(tokens[0], tokens[1]);
	   return 0.0f;
    }
    @Override
   public Schema outputSchema(Schema input) {

		    return new Schema(new Schema.FieldSchema(null, DataType.FLOAT));
    }

    public float distance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        //int i == 0;
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
                costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
                // j == 0; nw = lev(i - 1, j)
                costs[0] = i;
                int nw = i - 1;
                for (int j = 1; j <= b.length(); j++) {
                        int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                        nw = costs[j];
                        costs[j] = cj;
                }
        }
        return 30.0f-(costs[b.length()] * 30.0f / b.length());
    }
}
