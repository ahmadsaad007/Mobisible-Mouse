package temple.edu.mobisiblemouse;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    TextView txt;
    float yDisplacement, prevValue;
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
        txt = findViewById(R.id.tv);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);


    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY){
            float currentValue = sensorEvent.values[0];
            //yDisplacement = prevValue - currentValue;
            //Toast.makeText(this, currentValue+"",Toast.LENGTH_SHORT).show();
            txt.setText(sensorEvent.values[0]+"");
            //txt.setText("The finger moved: " + yDisplacement + " cm!");
            prevValue = currentValue;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
