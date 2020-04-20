package temple.edu.mobisiblemouse;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    String msg = "";
    EditText message;
    Button button;
    static String SERVER_IP = "192.168.110.1";
    static int SERVER_PORT = 8000;
    private static Socket s;
    PrintWriter pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        message = findViewById(R.id.message);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendText(view);
            }
        });
    }
    public void sendText(View view){
        msg = message.getText().toString();
        Messager obj = new Messager();
        obj.execute();
        Toast.makeText(getApplicationContext(),"Sending Message",Toast.LENGTH_LONG).show();
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