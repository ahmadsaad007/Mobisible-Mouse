package temple.edu.mobisiblemouse;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private String msg = "";
    private TextView message;
    private Button buttonLeft, buttonRight, buttonMid;
    private static String SERVER_IP = "192.168.110.1";
    private static int SERVER_PORT =8000;
    private static Socket s;
    private PrintWriter pw;

    private float lastX = 0.0f, lastY = 0.0f;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private float deltaXMax = 0.0f;
    private float deltaYMax = 0.0f;
    private float deltaX = 0.0f;
    private float deltaY = 0.0f;
    private String data = "";

    private TextView currentX, currentY, maxX, maxY;
    private String direction = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        message = (TextView)findViewById(R.id.message);
        buttonLeft = findViewById(R.id.button);

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data = "LClick";
                sendText(data);
            }
        });
        buttonRight = findViewById(R.id.button3);
        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data = "RClick";
                sendText(data);
            }
        });
        buttonMid= findViewById(R.id.button4);
        buttonMid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data = "MClick";
                sendText(data);
            }
        });
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, 10000000,10000);
        } else{
            Log.e("Error", "No Sensor");
        }
    }
    public void sendText(String data){
        msg = data;
        message.setText(msg);
        Log.e("Message", msg);
        Messager obj = new Messager();
        obj.execute();
    }

    public void initializeViews() {
        currentX = (TextView) findViewById(R.id.currentX);
        currentY = (TextView) findViewById(R.id.currentY);
        maxX = (TextView) findViewById(R.id.maxX);
        maxY = (TextView) findViewById(R.id.maxY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, 10000000);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // display the current x,y,z accelerometer values
        displayCurrentValues();
        // display the max x,y,z accelerometer values
        displayMaxValues();

        // get the change of the x,y values of the accelerometer
        deltaX = lastX - event.values[0];
        deltaY = lastY - event.values[1];

        lastX = event.values[0];
        lastY = event.values[1];

        // if the change is below 1, it is just plain noise
        if (deltaX < 1 && deltaX > -1 )
            deltaX = 0.0f;
        else{
            if(deltaX>1)
                direction = "Right";
            else
                direction = "Left";
            data = deltaX + " " + direction;
        }
        if (deltaY < 1 && deltaY > -1)
            deltaY = 0.0f;
        else{
            if(deltaX>1)
                direction = "Down";
            else
                direction = "Up";
            data = deltaY + " " + direction;
        }
        if(direction.compareTo("")!=0){
            Toast.makeText(getApplicationContext(),direction,Toast.LENGTH_SHORT).show();
            direction = "";
            sendText(data);
            Log.e("DATA1",data);
        }
    }

    public void displayCurrentValues() {
        currentX.setText(Float.toString(deltaX));
        currentY.setText(Float.toString(deltaY));
    }

    public void displayMaxValues() {
        if (deltaX > deltaXMax) {
            deltaXMax = deltaX;
            maxX.setText(Float.toString(deltaXMax));
        }
        if (deltaY > deltaYMax) {
            deltaYMax = deltaY;
            maxY.setText(Float.toString(deltaYMax));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public class Messager extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void ... params) {
            try {


                s = new Socket(SERVER_IP, SERVER_PORT);
                //Toast.makeText(getApplicationContext(), "Connecting to IP", Toast.LENGTH_LONG).show();
                pw = new PrintWriter(s.getOutputStream());
                pw.write(msg);
                pw.flush();
                pw.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}