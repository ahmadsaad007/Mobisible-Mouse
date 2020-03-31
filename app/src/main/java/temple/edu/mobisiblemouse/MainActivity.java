package temple.edu.mobisiblemouse;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.LoaderCallbackInterface;
import edu.washington.cs.touchfreelibrary.sensors.*;
import edu.washington.cs.touchfreelibrary.touchemulation.*;


public class MainActivity extends AppCompatActivity implements CameraGestureSensor.Listener{

    public CameraGestureSensor mGestureSensor;
    private boolean mOpenCVInitiated;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Mobisible Mouse");
        mGestureSensor = new CameraGestureSensor(this);
        mGestureSensor.addGestureListener(this);

        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_11, this, mLoaderCallback);

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
    /** OpenCV library initialization. */
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    mOpenCVInitiated = true;
                    CameraGestureSensor.loadLibrary();
                    mGestureSensor.start(); 	// your main gesture sensor object

                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    public void onGestureUp(CameraGestureSensor caller, long gestureLength) {
        textView.setText("UP");
    }

    @Override
    public void onGestureDown(CameraGestureSensor caller, long gestureLength) {
        textView.setText("DOWN");
    }

    @Override
    public void onGestureLeft(CameraGestureSensor caller, long gestureLength) {
        textView.setText("LEFT");
    }

    @Override
    public void onGestureRight(CameraGestureSensor caller, long gestureLength) {
        textView.setText("RIGHT");
    }
}
