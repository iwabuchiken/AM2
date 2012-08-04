package am2.main;

import android.app.Activity;
import android.os.Bundle;

public class MainActv extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        this.setTitle(this.getClass().getName());
        
    }//public void onCreate(Bundle savedInstanceState)
    
}//public class MainActv extends Activity