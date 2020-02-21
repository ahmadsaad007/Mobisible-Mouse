package temple.edu.mobisiblemouse;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Mobisible Mouse");
        /*
        * Steps to perform:
        * 1) Get user permission for sound detection, bluetooth and camera usage
        * 2) Either use OpenCV for motion detection, or use microphone, speaker
        * and vibration detection for hand gesture and movement tracking.
        * 3) Map the motion to a 2D plane
        * 4) Send input to the computer over bluetooth
        * 5) Make the computer perform  mouse actions based on this data. Will have
        * to develop a simple software to connect with phone and perform the actions
        * */

    }
}
