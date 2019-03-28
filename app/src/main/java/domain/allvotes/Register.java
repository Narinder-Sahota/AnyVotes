package domain.allvotes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText name,email,password,c_password;
    private Button btn_register;
    private static String url_reg = "http://192.168.0.11/android_register/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.confirm_password);
        btn_register = findViewById(R.id.btn_reg);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }
    private void register(){
        btn_register.setVisibility(View.GONE);
        final  String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_reg,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(Register.this,"Resigter Success!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Register.this,"Register Error!" + e.toString(), Toast.LENGTH_SHORT).show();
                            btn_register.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Register.this,"Register Error!" + error.toString(), Toast.LENGTH_SHORT).show();
                        btn_register.setVisibility(View.VISIBLE);
                    }
                })
        {
            @Override
            protected Map<String,String> getParams()throws AuthFailureError{
                Map<String,String> params = new HashMap<>();
                params.put("name",name);
                params.put("email",email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
