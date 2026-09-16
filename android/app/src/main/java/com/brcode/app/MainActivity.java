package com.brcode.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.*;

public class MainActivity extends Activity {
    final int BG=Color.rgb(5,9,9), PANEL=Color.rgb(8,16,14), GREEN=Color.rgb(32,245,123), MUTED=Color.rgb(150,170,162);
    LinearLayout root, card; EditText user, pass; TextView message; boolean register=false;
    android.content.SharedPreferences prefs;
    @Override public void onCreate(Bundle b){super.onCreate(b); prefs=getSharedPreferences("brcode",0); showLogin();}
    TextView text(String s,float size,int color){ TextView t=new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color); t.setGravity(Gravity.CENTER); t.setPadding(12,8,12,8); return t; }
    GradientDrawable bg(int color,float r){ GradientDrawable g=new GradientDrawable(); g.setColor(color); g.setCornerRadius(r); g.setStroke(1,Color.rgb(22,75,54)); return g; }
    Button button(String s){ Button b=new Button(this); b.setText(s); b.setTextSize(16); b.setTextColor(Color.rgb(3,19,11)); b.setAllCaps(false); b.setBackground(bg(GREEN,18)); return b; }
    EditText input(String hint, boolean password){ EditText e=new EditText(this); e.setHint(hint); e.setHintTextColor(Color.rgb(110,128,120)); e.setTextColor(Color.WHITE); e.setTextSize(16); e.setSingleLine(true); e.setPadding(18,0,18,0); e.setBackground(bg(Color.rgb(11,17,16),18)); if(password)e.setInputType(0x81); else e.setInputType(1); LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,58); p.setMargins(0,7,0,7); e.setLayoutParams(p); return e; }
    void base(){ root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setGravity(Gravity.CENTER); root.setPadding(26,28,26,28); root.setBackgroundColor(BG); setContentView(root); }
    void showLogin(){ base(); card=new LinearLayout(this); card.setOrientation(LinearLayout.VERTICAL); card.setPadding(28,28,28,28); card.setBackground(bg(PANEL,28)); LinearLayout.LayoutParams cp=new LinearLayout.LayoutParams(-1,-2); cp.gravity=Gravity.CENTER; card.setLayoutParams(cp);
        TextView logo=text("⚡ BRcode",32,Color.WHITE); logo.setTypeface(null,1); card.addView(logo); TextView sub=text(register?"Crie sua conta":"Bem-vindo de volta!",25,Color.WHITE); card.addView(sub);
        TextView info=text(register?"É rápido e fácil começar.":"Entre para continuar.",15,MUTED); card.addView(info);
        LinearLayout tabs=new LinearLayout(this); tabs.setOrientation(LinearLayout.HORIZONTAL); Button a=button("Entrar"), c=button("Cadastrar"); a.setOnClickListener(v->{register=false;showLogin();}); c.setOnClickListener(v->{register=true;showLogin();}); tabs.addView(a,new LinearLayout.LayoutParams(0,52,1)); tabs.addView(c,new LinearLayout.LayoutParams(0,52,1)); card.addView(tabs);
        user=input("Usuário ou e-mail",false); pass=input("Senha",true); card.addView(user); card.addView(pass);
        if(register){ EditText email=input("E-mail",false); card.addView(email); }
        message=text("",13,Color.rgb(255,170,170)); card.addView(message);
        Button enter=button(register?"Criar conta  →":"Entrar  →"); LinearLayout.LayoutParams ep=new LinearLayout.LayoutParams(-1,58); ep.setMargins(0,12,0,4); enter.setLayoutParams(ep); card.addView(enter);
        TextView demo=text("Demo: Caua_ / 123456",12,Color.rgb(80,100,92)); card.addView(demo); root.addView(card);
        enter.setOnClickListener(v->{String u=user.getText().toString().trim(), p=pass.getText().toString(); if(u.isEmpty()||p.isEmpty()){message.setText("Preencha usuário e senha.");return;} if(p.length()<6){message.setText("A senha precisa ter pelo menos 6 caracteres.");return;} if(register){prefs.edit().putString("user",u).putString("pass",p).apply(); showHome(u);return;} String su=prefs.getString("user",""),sp=prefs.getString("pass",""); if((u.equalsIgnoreCase("Caua_")&&p.equals("123456"))||(u.equalsIgnoreCase(su)&&p.equals(sp))) showHome(u); else message.setText("Usuário ou senha inválidos.");});
    }
    void showHome(String name){ base(); TextView logo=text("⚡ BRcode",34,Color.WHITE); logo.setTypeface(null,1); root.addView(logo); TextView online=text("● Online",15,GREEN); root.addView(online); TextView welcome=text("Bem-vindo, "+name+"! 👋",28,Color.WHITE); welcome.setTypeface(null,1); welcome.setPadding(0,35,0,12); root.addView(welcome); TextView desc=text("Seu espaço para conversar, jogar e criar comunidades.",16,MUTED); desc.setPadding(10,0,10,30); root.addView(desc);
        String[] features={"💬  Chat","🎙️  Voz","👥  Comunidades","🤖  Bots"}; for(String f:features){TextView t=text(f,18,Color.WHITE); t.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL); t.setPadding(20,0,0,0); t.setBackground(bg(PANEL,18)); LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,62);p.setMargins(0,6,0,6);root.addView(t,p);} Button out=button("Sair da conta"); LinearLayout.LayoutParams op=new LinearLayout.LayoutParams(-1,58);op.setMargins(0,25,0,0);root.addView(out,op); out.setOnClickListener(v->{prefs.edit().clear().apply();showLogin();}); }
}
