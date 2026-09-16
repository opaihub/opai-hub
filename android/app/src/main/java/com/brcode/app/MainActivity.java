package com.brcode.app;

import android.app.*;import android.os.*;import android.content.*;import android.graphics.Color;import android.view.*;import android.widget.*;import java.util.*;

public class MainActivity extends Activity {
  LinearLayout root, form; EditText user, pass; android.content.SharedPreferences prefs;
  int green=Color.rgb(0,255,102), dark=Color.rgb(5,5,5), panel=Color.rgb(13,17,15);
  TextView title(String t,int size){TextView v=new TextView(this);v.setText(t);v.setTextColor(green);v.setTextSize(size);v.setGravity(17);v.setPadding(8,12,8,12);return v;}
  Button btn(String t){Button b=new Button(this);b.setText(t);b.setTextColor(Color.WHITE);b.setAllCaps(false);return b;}
  public void onCreate(Bundle b){super.onCreate(b);prefs=getSharedPreferences("brcode",0);showAuth();}
  void base(){root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setGravity(Gravity.CENTER);root.setPadding(32,32,32,32);root.setBackgroundColor(dark);setContentView(root);}
  void showAuth(){base();root.addView(title("BRcode",34));TextView sub=title("Conecte. Converse. Compartilhe.",15);sub.setTextColor(Color.LTGRAY);root.addView(sub);form=new LinearLayout(this);form.setOrientation(LinearLayout.VERTICAL);form.setPadding(0,24,0,0);user=new EditText(this);user.setHint("Usuário");user.setTextColor(Color.WHITE);user.setHintTextColor(Color.GRAY);form.addView(user);pass=new EditText(this);pass.setHint("Senha");pass.setInputType(129);pass.setTextColor(Color.WHITE);pass.setHintTextColor(Color.GRAY);form.addView(pass);Button login=btn("Entrar");login.setOnClickListener(v->login());form.addView(login);Button reg=btn("Criar conta");reg.setOnClickListener(v->register());form.addView(reg);root.addView(form);}
  void login(){String u=user.getText().toString().trim(),p=pass.getText().toString();if(u.isEmpty()||p.isEmpty()){toast("Preencha usuário e senha");return;}if(!prefs.getString("u","").equals(u)||!prefs.getString("p","").equals(p)){toast("Usuário ou senha inválidos");return;}showHome(u);}
  void register(){String u=user.getText().toString().trim(),p=pass.getText().toString();if(u.isEmpty()||p.isEmpty()){toast("Preencha usuário e senha");return;}prefs.edit().putString("u",u).putString("p",p).apply();showHome(u);}
  void showHome(String u){base();root.addView(title("BRcode",30));TextView w=new TextView(this);w.setText("Olá, "+u);w.setTextColor(Color.WHITE);w.setTextSize(20);w.setGravity(17);root.addView(w);String[] f={"💬  Chat","🎙️  Voz","📹  Vídeo","🏠  Comunidades","👥  Amigos","🤖  Bots","📎  Arquivos","🛡️  Moderação"};for(String s:f){Button x=btn(s);root.addView(x,new LinearLayout.LayoutParams(-1,58));}Button out=btn("Sair");out.setOnClickListener(v->showAuth());root.addView(out);}
  void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}
}
