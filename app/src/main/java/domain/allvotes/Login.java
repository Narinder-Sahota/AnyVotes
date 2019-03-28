package domain.allvotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText email,password;
    private Button btn_login;
    private Button link_register;
    private static String URL_Login = "http://192.168.0.11/android_register/login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        link_register = findViewById(R.id.btn_link_register);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mEmail = email.getText().toString().trim();
                String mPass = password.getText().toString().trim();

                if (!mEmail.isEmpty() || !mPass.isEmpty()) {
                    Login(mEmail,mPass);
                } else {
                    email.setError("Please insert email");
                    password.setError("Please insert password");
                }
            }
        });

        link_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });
    }

        private void Login(final String email, final String password){
            btn_login.setVisibility(View.GONE);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Login,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {

                                JSONObject jsonResponse = new JSONObject(response);
                                String success = jsonResponse.getString("success");
                                JSONArray jsonArray = jsonResponse.getJSONArray("login");

                                if (success.equals("1")) {
                                    for (int i = 0; i < jsonArray.length();i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        String name = object.getString("name").trim();
                                        String email = object.getString("email").trim();

                                        Intent intent = new Intent(Login.this,Home.class);
                                        intent.putExtra("name",name);
                                        intent.putExtra("email",email);
                                        startActivity(intent);

                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                btn_login.setVisibility(View.VISIBLE);
                                Toast.makeText(Login.this,"Error" + e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){
                            btn_login.setVisibility(View.VISIBLE);
                            Toast.makeText(Login.this,"Error"+error.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parms = new HashMap<>();
                    parms.put("email", email);
                    parms.put("password", password);
                    return parms;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

